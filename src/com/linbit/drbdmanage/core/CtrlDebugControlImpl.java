package com.linbit.drbdmanage.core;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.linbit.ServiceName;
import com.linbit.SystemService;
import com.linbit.drbdmanage.Node;
import com.linbit.drbdmanage.NodeName;
import com.linbit.drbdmanage.ResourceDefinition;
import com.linbit.drbdmanage.ResourceName;
import com.linbit.drbdmanage.netcom.Peer;
import com.linbit.drbdmanage.propscon.Props;
import com.linbit.drbdmanage.proto.CommonMessageProcessor;
import com.linbit.drbdmanage.security.AccessContext;
import com.linbit.drbdmanage.security.AccessDeniedException;

class CtrlDebugControlImpl implements CtrlDebugControl
{
    private final Controller controller;
    private final Map<ServiceName, SystemService> systemServicesMap;
    private final Map<String, Peer> peerMap;
    private final CommonMessageProcessor msgProc;

    CtrlDebugControlImpl(
        Controller controllerRef,
        Map<ServiceName, SystemService> systemServicesMapRef,
        Map<String, Peer> peerMapRef,
        CommonMessageProcessor msgProcRef)
    {
        controller = controllerRef;
        systemServicesMap = systemServicesMapRef;
        peerMap = peerMapRef;
        msgProc = msgProcRef;
    }

    @Override
    public DrbdManage getInstance()
    {
        return controller;
    }

    @Override
    public Controller getModuleInstance()
    {
        return controller;
    }

    @Override
    public String getProgramName()
    {
        return Controller.PROGRAM;
    }

    @Override
    public String getModuleType()
    {
        return Controller.MODULE;
    }

    @Override
    public String getVersion()
    {
        return Controller.VERSION;
    }

    @Override
    public Map<ServiceName, SystemService> getSystemServiceMap()
    {
        Map<ServiceName, SystemService> svcCpy = new TreeMap<>();
        svcCpy.putAll(systemServicesMap);
        return svcCpy;
    }

    @Override
    public Peer getPeer(String peerId)
    {
        Peer peerObj = null;
        synchronized (peerMap)
        {
            peerObj = peerMap.get(peerId);
        }
        return peerObj;
    }

    @Override
    public Map<String, Peer> getAllPeers()
    {
        TreeMap<String, Peer> peerMapCpy = new TreeMap<>();
        synchronized (peerMap)
        {
            peerMapCpy.putAll(peerMap);
        }
        return peerMapCpy;
    }

    @Override
    public Set<String> getApiCallNames()
    {
        return msgProc.getApiCallNames();
    }

    @Override
    public Map<NodeName, Node> getNodesMap()
    {
        return controller.nodesMap;
    }

    @Override
    public Map<ResourceName, ResourceDefinition> getRscDfnMap()
    {
        return controller.rscDfnMap;
    }

    @Override
    public Props getConf()
    {
        // FIXME: return the satellite's configuration
        return null;
    }

    @Override
    public void shutdown(AccessContext accCtx)
    {
        try
        {
            controller.shutdown(accCtx);
        }
        catch (AccessDeniedException accExc)
        {
            controller.getErrorReporter().reportError(accExc);
        }
    }
}