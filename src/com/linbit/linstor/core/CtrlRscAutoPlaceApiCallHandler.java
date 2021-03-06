package com.linbit.linstor.core;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.linbit.ImplementationError;
import com.linbit.linstor.Node;
import com.linbit.linstor.ResourceDefinitionData;
import com.linbit.linstor.StorPool;
import com.linbit.linstor.StorPoolDefinition;
import com.linbit.linstor.StorPoolName;
import com.linbit.linstor.Volume;
import com.linbit.linstor.VolumeDefinition;
import com.linbit.linstor.annotation.ApiContext;
import com.linbit.linstor.annotation.PeerContext;
import com.linbit.linstor.api.ApiCallRc;
import com.linbit.linstor.api.ApiCallRcImpl;
import com.linbit.linstor.api.ApiConsts;
import com.linbit.linstor.api.interfaces.serializer.CtrlStltSerializer;
import com.linbit.linstor.core.CoreModule.ResourceDefinitionMap;
import com.linbit.linstor.core.CoreModule.StorPoolDefinitionMap;
import com.linbit.linstor.logging.ErrorReporter;
import com.linbit.linstor.netcom.Peer;
import com.linbit.linstor.security.AccessContext;
import com.linbit.linstor.security.AccessDeniedException;
import com.linbit.linstor.security.AccessType;
import com.linbit.linstor.storage.DisklessDriverKind;
import com.linbit.linstor.storage.StorageDriverKind;
import com.linbit.linstor.transaction.TransactionMgr;

public class CtrlRscAutoPlaceApiCallHandler extends AbsApiCallHandler
{
    private String currentRscName;

    private final ResourceDefinitionMap rscDfnMap;
    private final StorPoolDefinitionMap storPoolDfnMap;
    private final CtrlRscApiCallHandler rscApiCallHandler;

    @Inject
    public CtrlRscAutoPlaceApiCallHandler(
        ErrorReporter errorReporterRef,
        CtrlStltSerializer interComSerializer,
        @ApiContext AccessContext apiCtxRef,
        // @Named(ControllerSecurityModule.STOR_POOL_DFN_MAP_PROT) ObjectProtection storPoolDfnMapProtRef,
        CoreModule.ResourceDefinitionMap rscDfnMapRef,
        CoreModule.StorPoolDefinitionMap storPoolDfnMapRef,
        CtrlObjectFactories objectFactories,
        CtrlRscApiCallHandler rscApiCallHandlerRef,
        Provider<TransactionMgr> transMgrProviderRef,
        @PeerContext AccessContext peerAccCtxRef,
        Provider<Peer> peerRef
    )
    {
        super(
            errorReporterRef,
            apiCtxRef,
            ApiConsts.MASK_RSC,
            interComSerializer,
            objectFactories,
            transMgrProviderRef,
            peerAccCtxRef,
            peerRef
        );
        rscDfnMap = rscDfnMapRef;
        storPoolDfnMap = storPoolDfnMapRef;
        rscApiCallHandler = rscApiCallHandlerRef;
    }

    public ApiCallRc autoPlace(
        String rscNameStr,
        int placeCount,
        String storPoolNameStr,
        List<String> notPlaceWithRscListRef,
        String notPlaceWithRscRegexStr
    )
    {
        // TODO extract this method into an own interface implementation
        // that the controller can choose between different auto-place strategies
        List<String> notPlaceWithRscList = notPlaceWithRscListRef.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());

        if (notPlaceWithRscRegexStr != null)
        {
            Pattern notPlaceWithRscRegexPattern = Pattern.compile(
                notPlaceWithRscRegexStr,
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL
            );
            notPlaceWithRscList.addAll(
                rscDfnMap.keySet().stream()
                    .map(rscName -> rscName.value)
                    .filter(rscName -> notPlaceWithRscRegexPattern.matcher(rscName).find())
                    .collect(Collectors.toList())
            );
        }

        ApiCallRcImpl apiCallRc = new ApiCallRcImpl();
        try (
            AbsApiCallHandler basicallyThis = setContext(
                ApiCallType.CREATE,
                apiCallRc,
                rscNameStr
            );
        )
        {
            // calculate the estimated size of the given resource
            final long rscSize = calculateResourceDefinitionSize(rscNameStr);

            // build a map of storage pools the user has access to and have enough free space
            // and are not diskless
            Map<StorPoolName, List<StorPool>> storPools = storPoolDfnMap.values().stream()
                .filter(storPoolDfn -> storPoolDfn.getObjProt().queryAccess(peerAccCtx).hasAccess(AccessType.USE))
                .flatMap(this::getStorPoolStream)
                .filter(storPool -> !(getDriverKind(storPool) instanceof DisklessDriverKind))
                .filter(storPool -> storPool.getNode().getObjProt().queryAccess(peerAccCtx).hasAccess(AccessType.USE))
                .filter(storPool -> getFreeSpace(storPool).orElse(0L) >= rscSize)
                .collect(
                    Collectors.groupingBy(StorPool::getName)
                );

            // apply the storage pool filter
            if (storPoolNameStr != null)
            {
                StorPoolName storPoolName = asStorPoolName(storPoolNameStr);
                storPools.keySet().retainAll(Arrays.asList(storPoolName));
            }

            List<Candidate> candidates;
            // try to consider the "do not place with resource" argument
            candidates = filterCandidates(placeCount, notPlaceWithRscList, storPools);
            if (candidates.isEmpty())
            {
                // if that didn't work, try to ignore the "do not place with resource" argument
                // but we have to avoid the storage pools used by the given set of resources.
                for (Entry<StorPoolName, List<StorPool>> entry : storPools.entrySet())
                {
                    List<StorPool> storPoolsToRemove = new ArrayList<>();
                    for (StorPool storPool : entry.getValue())
                    {
                        Collection<Volume> volumes = storPool.getVolumes(apiCtx);
                        for (Volume vlm : volumes)
                        {
                            if (notPlaceWithRscList.contains(vlm.getResourceDefinition().getName().value))
                            {
                                storPoolsToRemove.add(storPool);
                                break;
                            }
                        }
                    }
                    entry.getValue().removeAll(storPoolsToRemove);
                }

                // retry with the reduced storPools but without "do not place with resource" limitation
                candidates = filterCandidates(placeCount, Collections.emptyList(), storPools);
            }

            if (candidates.isEmpty())
            {
                addAnswer(
                    "Not enough available nodes",
                    null, // cause
                    "Not enough nodes fulfilling the following auto-place criteria:\n" +
                    " * has a deployed storage pool named '" + storPoolNameStr + "'\n" +
                    " * the storage pool '" + storPoolNameStr + "' has to have at least '" +
                    rscSize + "' free space\n" +
                    " * the current access context has enough privileges to use the node and the storage pool",
                    null, // correction.... "you must construct additional servers"
                    ApiConsts.FAIL_NOT_ENOUGH_NODES
                );
                throw new ApiCallHandlerFailedException();
            }
            else
            {
                // we might have a list of candidates and have to choose.
                Collections.sort(
                    candidates,
                    this::mostRemainingSpaceStrategy
                );

                Candidate candidate = candidates.get(0);

                Map<String, String> rscPropsMap = new TreeMap<>();
                rscPropsMap.put(ApiConsts.KEY_STOR_POOL_NAME, candidate.storPoolName.displayValue);

                for (Node node : candidate.nodes)
                {
                    rscApiCallHandler.createResource(
                        node.getName().displayValue,
                        rscNameStr,
                        Collections.emptyList(),
                        rscPropsMap,
                        Collections.emptyList(),
                        false, // createResource api should NOT autoClose the current transaction
                        // we will close it when we are finished with the autoPlace
                        apiCallRc
                    );
                }
                reportSuccess(
                    "Resource '" + rscNameStr + "' successfully autoplaced on " + placeCount + " nodes",
                    "Used storage pool: '" + candidate.storPoolName.displayValue + "'\n" +
                    "Used nodes: '" + candidate.nodes.stream()
                        .map(node -> node.getName().displayValue)
                        .collect(Collectors.joining("', '")) + "'"
                );
            }
        }
        catch (ApiCallHandlerFailedException ignore)
        {
            // a report and a corresponding api-response already created. nothing to do here
        }
        catch (Exception | ImplementationError exc)
        {
            reportStatic(
                exc,
                ApiCallType.CREATE,
                getObjectDescriptionInline(rscNameStr),
                getObjRefs(rscNameStr),
                getVariables(rscNameStr),
                apiCallRc
            );
        }
        return apiCallRc;
    }

    private StorageDriverKind getDriverKind(StorPool storPool)
    {
        StorageDriverKind driverKind;
        try
        {
            driverKind = storPool.getDriverKind(apiCtx);
        }
        catch (AccessDeniedException exc)
        {
            throw asImplError(exc);
        }
        return driverKind;
    }

    private List<Candidate> filterCandidates(
        final int placeCount,
        List<String> notPlaceWithRscList,
        Map<StorPoolName, List<StorPool>> storPools
    )
    {
        List<Candidate> ret = new ArrayList<>();
        for (Entry<StorPoolName, List<StorPool>> entry: storPools.entrySet())
        {
            List<Node> nodeCandidates = entry.getValue().stream()
                .sorted((sp1, sp2) -> getFreeSpace(sp1).orElse(0L).compareTo(getFreeSpace(sp2).orElse(0L)))
                .map(StorPool::getNode)
                .filter(node -> hasNoResourceOf(node, notPlaceWithRscList))
                .limit(placeCount)
                .collect(Collectors.toList());

            if (nodeCandidates.size() == placeCount)
            {
                ret.add(new Candidate(entry.getKey(), nodeCandidates));
            }
        }
        return ret;
    }

    private int mostRemainingSpaceStrategy(Candidate cand1, Candidate cand2)
    {
        // the node-lists are already sorted by their storPools.
        // that means, we only have to compare the freeSpace of the first nodes of cand1 and cand2
        int cmp = 0;
        try
        {
            cmp = Long.compare(
                cand2.nodes.get(0).getStorPool(peerAccCtx, cand2.storPoolName)
                    .getFreeSpace(peerAccCtx).orElse(0L),
                cand1.nodes.get(0).getStorPool(peerAccCtx, cand1.storPoolName)
                    .getFreeSpace(peerAccCtx).orElse(0L)
            );
            // compare(cand2, cand1) so that the candidate with more free space comes before the other
        }
        catch (AccessDeniedException exc)
        {
            // this exception should have been thrown long ago
            throw asImplError(exc);
        }
        return cmp;
    }

    private long calculateResourceDefinitionSize(String rscNameStr)
    {
        long size = 0;
        try
        {
            ResourceDefinitionData rscDfn = loadRscDfn(rscNameStr, true);
            Iterator<VolumeDefinition> vlmDfnIt = rscDfn.iterateVolumeDfn(peerAccCtx);
            while (vlmDfnIt.hasNext())
            {
                VolumeDefinition vlmDfn = vlmDfnIt.next();
                size += vlmDfn.getVolumeSize(peerAccCtx);
            }
        }
        catch (AccessDeniedException accDeniedExc)
        {
            throw asAccDeniedExc(
                accDeniedExc,
                "access " + CtrlRscDfnApiCallHandler.getObjectDescriptionInline(rscNameStr),
                ApiConsts.FAIL_ACC_DENIED_RSC_DFN
            );
        }
        return size;
    }

    private Stream<StorPool> getStorPoolStream(StorPoolDefinition storPoolDefinition)
    {
        Stream<StorPool> stream;
        try
        {
            stream = storPoolDefinition.streamStorPools(peerAccCtx);
        }
        catch (AccessDeniedException exc)
        {
            throw asAccDeniedExc(
                exc,
                "stream storage pools of storage pool definition '" +
                    storPoolDefinition.getName().displayValue + "'.",
                ApiConsts.FAIL_ACC_DENIED_STOR_POOL_DFN
            );
        }
        return stream;
    }

    private Optional<Long> getFreeSpace(StorPool storPool)
    {
        Optional<Long> freeSpace;
        try
        {
            freeSpace = storPool.getFreeSpace(peerAccCtx);
        }
        catch (AccessDeniedException exc)
        {
            throw asAccDeniedExc(
                exc,
                "query free space of " + CtrlStorPoolApiCallHandler.getObjectDescriptionInline(storPool),
                ApiConsts.FAIL_ACC_DENIED_STOR_POOL
            );
        }
        return freeSpace;
    }

    private boolean hasNoResourceOf(Node node, List<String> notPlaceWithRscList)
    {
        boolean hasNoResourceOf = false;
        try
        {
            hasNoResourceOf = node.streamResources(peerAccCtx)
                .map(rsc -> rsc.getDefinition().getName().value)
                .noneMatch(notPlaceWithRscList::contains);
        }
        catch (AccessDeniedException accDeniedExc)
        {
            throw asImplError(accDeniedExc);
        }
        return hasNoResourceOf;
    }

    private AbsApiCallHandler setContext(
        ApiCallType type,
        ApiCallRcImpl apiCallRc,
        String rscNameStr
    )
    {
        super.setContext(
            type,
            apiCallRc,
            true,
            getObjRefs(rscNameStr),
            getVariables(rscNameStr)
        );
        currentRscName = rscNameStr;
        return this;
    }

    private Map<String, String> getObjRefs(String rscNameStr)
    {
        Map<String, String> map = new TreeMap<>();
        map.put(ApiConsts.KEY_RSC_DFN, rscNameStr);
        return map;
    }

    private Map<String, String> getVariables(String rscNameStr)
    {
        Map<String, String> map = new TreeMap<>();
        map.put(ApiConsts.KEY_RSC_NAME, rscNameStr);
        return map;
    }

    @Override
    protected String getObjectDescription()
    {
        return "Auto-placing resource: " + currentRscName;
    }

    @Override
    protected String getObjectDescriptionInline()
    {
        return getObjectDescriptionInline(currentRscName);
    }

    private String getObjectDescriptionInline(String rscNameStr)
    {
        return "auto-placing resource: '" + rscNameStr + "'";
    }

    private class Candidate
    {
        StorPoolName storPoolName;
        List<Node> nodes;

        Candidate(StorPoolName storPoolNameRef, List<Node> nodesRef)
        {
            storPoolName = storPoolNameRef;
            nodes = nodesRef;
        }
    }
}
