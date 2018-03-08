package com.linbit.linstor.core;

import com.linbit.ImplementationError;
import com.linbit.linstor.LinStorDataAlreadyExistsException;
import com.linbit.linstor.NodeData;
import com.linbit.linstor.Resource;
import com.linbit.linstor.ResourceConnectionData;
import com.linbit.linstor.ResourceConnectionDataFactory;
import com.linbit.linstor.ResourceName;
import com.linbit.linstor.annotation.ApiContext;
import com.linbit.linstor.api.ApiCallRc;
import com.linbit.linstor.api.ApiCallRcImpl;
import com.linbit.linstor.api.ApiConsts;
import com.linbit.linstor.api.interfaces.serializer.CtrlStltSerializer;
import com.linbit.linstor.logging.ErrorReporter;
import com.linbit.linstor.netcom.Peer;
import com.linbit.linstor.propscon.Props;
import com.linbit.linstor.security.AccessContext;
import com.linbit.linstor.security.AccessDeniedException;
import com.linbit.linstor.transaction.TransactionMgr;

import javax.inject.Inject;
import javax.inject.Provider;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

class CtrlRscConnectionApiCallHandler extends AbsApiCallHandler
{
    private final ThreadLocal<String> currentNodeName1 = new ThreadLocal<>();
    private final ThreadLocal<String> currentNodeName2 = new ThreadLocal<>();
    private final ThreadLocal<String> currentRscName = new ThreadLocal<>();
    private final ResourceConnectionDataFactory resourceConnectionDataFactory;

    @Inject
    CtrlRscConnectionApiCallHandler(
        ErrorReporter errorReporterRef,
        CtrlStltSerializer interComSerializer,
        @ApiContext AccessContext apiCtxRef,
        CtrlObjectFactories objectFactories,
        ResourceConnectionDataFactory resourceConnectionDataFactoryRef,
        Provider<TransactionMgr> transMgrProviderRef
    )
    {
        super(
            errorReporterRef,
            apiCtxRef,
            ApiConsts.MASK_RSC_CONN,
            interComSerializer,
            objectFactories,
            transMgrProviderRef
        );
        super.setNullOnAutoClose(
            currentNodeName1,
            currentNodeName2,
            currentRscName
        );
        resourceConnectionDataFactory = resourceConnectionDataFactoryRef;
    }

    public ApiCallRc createResourceConnection(
        AccessContext accCtx,
        Peer client,
        String nodeName1Str,
        String nodeName2Str,
        String rscNameStr,
        Map<String, String> rscConnPropsMap
    )
    {
        ApiCallRcImpl apiCallRc = new ApiCallRcImpl();

        try (
            AbsApiCallHandler basicallyThis = setContext(
                accCtx,
                client,
                ApiCallType.CREATE,
                apiCallRc,
                nodeName1Str,
                nodeName2Str,
                rscNameStr
            );
        )
        {
            ResourceConnectionData rscConn = createRscConn(nodeName1Str, nodeName2Str, rscNameStr);
            getProps(rscConn).map().putAll(rscConnPropsMap);
            commit();

            updateSatellites(rscConn);
            reportSuccess(rscConn.getUuid());
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
                getObjectDescriptionInline(nodeName1Str, nodeName2Str, rscNameStr),
                getObjRefs(nodeName1Str, nodeName2Str, rscNameStr),
                getVariables(nodeName1Str, nodeName2Str, rscNameStr),
                apiCallRc,
                accCtx,
                client
            );
        }

        return apiCallRc;
    }

    public ApiCallRc modifyRscConnection(
        AccessContext accCtx,
        Peer client,
        UUID rscConnUuid,
        String nodeName1,
        String nodeName2,
        String rscNameStr,
        Map<String, String> overrideProps,
        Set<String> deletePropKeys
    )
    {
        ApiCallRcImpl apiCallRc = new ApiCallRcImpl();

        try (
            AbsApiCallHandler basicallyThis = setContext(
                accCtx,
                client,
                ApiCallType.MODIFY,
                apiCallRc,
                nodeName1,
                nodeName2,
                rscNameStr
            );
        )
        {
            ResourceConnectionData rscConn = loadRscConn(nodeName1, nodeName2, rscNameStr);

            if (rscConnUuid != null && !rscConnUuid.equals(rscConn.getUuid()))
            {
                addAnswer(
                    "UUID-check failed",
                    ApiConsts.FAIL_UUID_RSC_CONN
                );
                throw new ApiCallHandlerFailedException();
            }

            Props props = getProps(rscConn);
            Map<String, String> propsMap = props.map();

            propsMap.putAll(overrideProps);

            for (String delKey : deletePropKeys)
            {
                propsMap.remove(delKey);
            }

            commit();

            reportSuccess(rscConn.getUuid());
            updateSatellites(rscConn);
        }
        catch (ApiCallHandlerFailedException ignore)
        {
            // a report and a corresponding api-response already created. nothing to do here
        }
        catch (Exception | ImplementationError exc)
        {
            reportStatic(
                exc,
                ApiCallType.MODIFY,
                getObjectDescriptionInline(nodeName1, nodeName2, rscNameStr),
                getObjRefs(nodeName1, nodeName2, rscNameStr),
                getVariables(nodeName1, nodeName2, rscNameStr),
                apiCallRc,
                accCtx,
                client
            );
        }

        return apiCallRc;
    }

    public ApiCallRc deleteResourceConnection(
        AccessContext accCtx,
        Peer client,
        String nodeName1Str,
        String nodeName2Str,
        String rscNameStr
    )
    {
        ApiCallRcImpl apiCallRc = new ApiCallRcImpl();

        try (
            AbsApiCallHandler basicallyThis = setContext(
                accCtx,
                client,
                ApiCallType.DELETE,
                apiCallRc,
                nodeName1Str,
                nodeName2Str,
                rscNameStr
            );
        )
        {
            ResourceConnectionData rscConn = loadRscConn(nodeName1Str, nodeName2Str, rscNameStr);
            UUID rscConnUuid = rscConn.getUuid();
            delete(rscConn);

            commit();

            updateSatellites(rscConn);
            reportSuccess(rscConnUuid);
        }
        catch (ApiCallHandlerFailedException ignore)
        {
            // a report and a corresponding api-response already created. nothing to do here
        }
        catch (Exception | ImplementationError exc)
        {
            reportStatic(
                exc,
                ApiCallType.DELETE,
                getObjectDescriptionInline(nodeName1Str, nodeName2Str, rscNameStr),
                getObjRefs(nodeName1Str, nodeName2Str, rscNameStr),
                getVariables(nodeName1Str, nodeName2Str, rscNameStr),
                apiCallRc,
                accCtx,
                client
            );
        }
        return apiCallRc;
    }

    private AbsApiCallHandler setContext(
        AccessContext accCtx,
        Peer client,
        ApiCallType type,
        ApiCallRcImpl apiCallRc,
        String nodeName1,
        String nodeName2,
        String rscNameStr
    )
    {
        super.setContext(
            accCtx,
            client,
            type,
            apiCallRc,
            true, // autoClose
            getObjRefs(nodeName1, nodeName2, rscNameStr),
            getVariables(nodeName1, nodeName2, rscNameStr)
        );

        currentNodeName1.set(nodeName1);
        currentNodeName2.set(nodeName2);
        currentRscName.set(rscNameStr);

        return this;
    }

    @Override
    protected String getObjectDescription()
    {
        return "Resource connection between nodes " + currentNodeName1.get() + " and " +
            currentNodeName2.get() + " for resource " + currentRscName.get();
    }

    @Override
    protected String getObjectDescriptionInline()
    {
        return getObjectDescriptionInline(
            currentNodeName1.get(),
            currentNodeName2.get(),
            currentRscName.get()
        );
    }


    private String getObjectDescriptionInline(String nodeName1, String nodeName2, String rscName)
    {
        return "resource connection between nodes '" + nodeName1 + "' and '" +
            nodeName2 + "' for resource '" + rscName + "'";
    }

    private Map<String, String> getObjRefs(String nodeName1, String nodeName2, String rscNameStr)
    {
        Map<String, String> map = new TreeMap<>();
        map.put(ApiConsts.KEY_1ST_NODE, nodeName1);
        map.put(ApiConsts.KEY_2ND_NODE, nodeName2);
        map.put(ApiConsts.KEY_RSC_DFN, rscNameStr);
        return map;
    }

    private Map<String, String> getVariables(String nodeName1, String nodeName2, String rscNameStr)
    {
        Map<String, String> map = new TreeMap<>();
        map.put(ApiConsts.KEY_1ST_NODE_NAME, nodeName1);
        map.put(ApiConsts.KEY_2ND_NODE_NAME, nodeName2);
        map.put(ApiConsts.KEY_RSC_NAME, rscNameStr);
        return map;
    }

    private ResourceConnectionData createRscConn(
        String nodeName1Str,
        String nodeName2Str,
        String rscNameStr
    )
    {
        NodeData node1 = loadNode(nodeName1Str, true);
        NodeData node2 = loadNode(nodeName2Str, true);
        ResourceName rscName = asRscName(rscNameStr);

        Resource rsc1 = loadRsc(node1, rscName);
        Resource rsc2 = loadRsc(node2, rscName);

        ResourceConnectionData rscConn;
        try
        {
            rscConn = resourceConnectionDataFactory.getInstance(
                currentAccCtx.get(),
                rsc1,
                rsc2,
                true,
                true
            );
        }
        catch (AccessDeniedException accDeniedExc)
        {
            throw asAccDeniedExc(
                accDeniedExc,
                "creating " + getObjectDescription(),
                ApiConsts.FAIL_ACC_DENIED_RSC_CONN
            );
        }
        catch (LinStorDataAlreadyExistsException dataAlreadyExistsExc)
        {
            throw asExc(
                dataAlreadyExistsExc,
                "The " + getObjectDescriptionInline() + " already exists.",
                ApiConsts.FAIL_EXISTS_RSC_CONN
            );
        }
        catch (SQLException sqlExc)
        {
            throw asSqlExc(
                sqlExc,
                "creating " + getObjectDescription()
            );
        }
        return rscConn;
    }

    private ResourceConnectionData loadRscConn(
        String nodeName1,
        String nodeName2,
        String rscNameStr
    )
        throws ApiCallHandlerFailedException
    {
        NodeData node1 = loadNode(nodeName1, true);
        NodeData node2 = loadNode(nodeName2, true);
        ResourceName rscName = asRscName(rscNameStr);

        Resource rsc1 = loadRsc(node1, rscName);
        Resource rsc2 = loadRsc(node2, rscName);

        ResourceConnectionData rscConn;
        try
        {
            rscConn = resourceConnectionDataFactory.getInstance(
                currentAccCtx.get(),
                rsc1,
                rsc2,
                false,
                false
            );
        }
        catch (AccessDeniedException accDeniedExc)
        {
            throw asAccDeniedExc(
                accDeniedExc,
                "loading " + getObjectDescription(),
                ApiConsts.FAIL_ACC_DENIED_RSC_CONN
            );
        }
        catch (LinStorDataAlreadyExistsException dataAlreadyExistsExc)
        {
            throw asImplError(dataAlreadyExistsExc);
        }
        catch (SQLException sqlExc)
        {
            throw asSqlExc(
                sqlExc,
                "loading " + getObjectDescription()
            );
        }
        return rscConn;
    }

    private Resource loadRsc(NodeData node, ResourceName rscName) throws ApiCallHandlerFailedException
    {
        Resource rsc;
        try
        {
            rsc = node.getResource(
                currentAccCtx.get(),
                rscName
            );
        }
        catch (AccessDeniedException accDeniedExc)
        {
            throw asAccDeniedExc(
                accDeniedExc,
                "loading resource '" + rscName.displayValue + "' from node '" + node.getName().displayValue + "'.",
                ApiConsts.FAIL_ACC_DENIED_NODE
            );
        }
        return rsc;
    }

    private Props getProps(ResourceConnectionData rscConn) throws ApiCallHandlerFailedException
    {
        Props props;
        try
        {
            props = rscConn.getProps(currentAccCtx.get());
        }
        catch (AccessDeniedException accDeniedExc)
        {
            throw asAccDeniedExc(
                accDeniedExc,
                "accessing properties of " + getObjectDescriptionInline(),
                ApiConsts.FAIL_ACC_DENIED_RSC_CONN
            );
        }
        return props;
    }

    private void updateSatellites(ResourceConnectionData rscConn)
    {
        try
        {
            updateSatellites(rscConn.getSourceResource(apiCtx));
            updateSatellites(rscConn.getTargetResource(apiCtx));
        }
        catch (AccessDeniedException implErr)
        {
            throw asImplError(implErr);
        }
    }

    private void delete(ResourceConnectionData rscConn)
    {
        try
        {
            rscConn.delete(currentAccCtx.get());
        }
        catch (AccessDeniedException accDeniedExc)
        {
            throw asAccDeniedExc(
                accDeniedExc,
                "delete " + getObjectDescriptionInline(),
                ApiConsts.FAIL_ACC_DENIED_RSC_CONN
            );
        }
        catch (SQLException sqlExc)
        {
            throw asSqlExc(
                sqlExc,
                "deleting " + getObjectDescriptionInline()
            );
        }
    }
}
