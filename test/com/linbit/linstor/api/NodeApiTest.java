package com.linbit.linstor.api;

import com.google.inject.Key;
import com.linbit.linstor.NetInterface.NetInterfaceApi;
import com.linbit.linstor.Node.NodeType;
import com.linbit.linstor.NodeData;
import com.linbit.linstor.NodeName;
import com.linbit.linstor.SatelliteConnection.SatelliteConnectionApi;
import com.linbit.linstor.annotation.PeerContext;
import com.linbit.linstor.api.utils.AbsApiCallTester;
import com.linbit.linstor.core.ApiTestBase;
import com.linbit.linstor.core.CtrlNodeApiCallHandler;
import com.linbit.linstor.core.DoNotSeedDefaultPeer;
import com.linbit.linstor.netcom.Peer;
import com.linbit.linstor.security.AccessContext;
import com.linbit.linstor.security.AccessType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class NodeApiTest extends ApiTestBase
{
    @Inject private Provider<CtrlNodeApiCallHandler> nodeApiCallHandlerProvider;

    private NodeName testNodeName;
    private NodeType testNodeType;
    private NodeData testNode;

    @Mock
    protected Peer mockSatellite;

    public NodeApiTest() throws Exception
    {
        super();
        testNodeName = new NodeName("TestController");
        testNodeType = NodeType.CONTROLLER;
    }

    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        testNode = nodeDataFactory.getInstance(
            BOB_ACC_CTX,
            testNodeName,
            testNodeType,
            null,
            true,
            true
        );
        testNode.setPeer(SYS_CTX, mockSatellite);
        nodesMap.put(testNodeName, testNode);
        commit();
    }

    @Test
    public void crtSuccess() throws Exception
    {
        evaluateTest(
            new CreateNodeCall(ApiConsts.CREATED)
                .expectStltConnectingAttempt()
        );
    }

    @Test
    public void crtSecondAccDenied() throws Exception
    {
        // FIXME: this test only works because the first API call succeeds.
        // if it would fail, the transaction is currently NOT rolled back.
        evaluateTest(
            new CreateNodeCall(ApiConsts.CREATED)
                .expectStltConnectingAttempt()
        );
        evaluateTest(
            new CreateNodeCall(ApiConsts.FAIL_EXISTS_NODE)
        );
    }

    @Test
    @DoNotSeedDefaultPeer
    public void crtFailNodesMapViewAccDenied() throws Exception
    {
        nodesMapProt.delAclEntry(SYS_CTX, PUBLIC_CTX.subjectRole);
        nodesMapProt.addAclEntry(SYS_CTX, PUBLIC_CTX.subjectRole, AccessType.VIEW);

        testScope.seed(Key.get(AccessContext.class, PeerContext.class), PUBLIC_CTX);
        testScope.seed(Peer.class, mockPeer);

        evaluateTest(
            new CreateNodeCall(ApiConsts.FAIL_ACC_DENIED_NODE)
        );
    }

    @Test
    public void crtMissingNetcom() throws Exception
    {
        evaluateTest(
            new CreateNodeCall(ApiConsts.FAIL_MISSING_NETCOM)
                .clearNetIfApis()
        );
    }

    @Test
    public void crtMissingStltConn() throws Exception
    {
        evaluateTest(
            new CreateNodeCall(ApiConsts.FAIL_MISSING_STLT_CONN)
                .clearStltApis()
        );
    }

    @Test
    public void crtInvalidNodeName() throws Exception
    {
        evaluateTest(
            new CreateNodeCall(ApiConsts.FAIL_INVLD_NODE_NAME)
                .setNodeName("Test Node") // blank is not allowed
        );
    }

    @Test
    public void crtInvalidNodeType() throws Exception
    {
        evaluateTest(
            new CreateNodeCall(ApiConsts.FAIL_INVLD_NODE_TYPE)
                .setNodeType("special satellite")
        );
    }

    @Test
    public void crtInvalidNetIfName() throws Exception
    {
        evaluateTest(
            new CreateNodeCall(ApiConsts.FAIL_INVLD_NET_NAME)
                .clearNetIfApis()
                .clearStltApis()
                .addNetIfApis("invalid net if name", "127.0.0.1")
                .addStltApis("invalid net if name")
        );
    }

    @Test
    public void crtInvalidNetAddr() throws Exception
    {
        evaluateTest(
            new CreateNodeCall(ApiConsts.FAIL_INVLD_NET_ADDR)
                .clearNetIfApis()
                .clearStltApis()
                .addNetIfApis("net0", "127.0.0.1.42")
                .addStltApis("net0")
        );
    }

    @Test
    public void ctrInvalidNetAddrV6() throws Exception
    {
        evaluateTest(
            new CreateNodeCall(ApiConsts.FAIL_INVLD_NET_ADDR)
                .clearNetIfApis()
                .clearStltApis()
                .addNetIfApis("net0", "0::0::0")
                .addStltApis("net0")
        );
    }

    @Test
    public void modSuccess() throws Exception
    {
        evaluateTest(
            new ModifyNodeCall(ApiConsts.MODIFIED) // nothing to do
        );
    }

    @Test
    @DoNotSeedDefaultPeer
    public void modDifferentUserAccDenied() throws Exception
    {
        testScope.seed(Key.get(AccessContext.class, PeerContext.class), ALICE_ACC_CTX);
        testScope.seed(Peer.class, mockPeer);

        evaluateTest(
            new ModifyNodeCall(ApiConsts.FAIL_ACC_DENIED_NODE)
        );
    }

    private class CreateNodeCall extends AbsApiCallTester
    {
        String nodeName;
        String nodeType;
        List<NetInterfaceApi> netIfApis;
        List<SatelliteConnectionApi> stltApis;
        Map<String, String> props;

        public CreateNodeCall(long expectedRc)
        {
            super(
                // peer
                ApiConsts.MASK_NODE,
                ApiConsts.MASK_CRT,
                expectedRc
            );

            nodeName = "TestNode";
            nodeType = ApiConsts.VAL_NODE_TYPE_STLT;
            netIfApis = new ArrayList<>();
            netIfApis.add(
                createNetInterfaceApi("tcp0", "127.0.0.1")
            );
            stltApis = new ArrayList<>();
            stltApis.add(
                createStltConnApi("tcp0")
            );
            props = new TreeMap<>();
        }

        @Override
        public ApiCallRc executeApiCall()
        {
            return nodeApiCallHandlerProvider.get().createNode(
                nodeName,
                nodeType,
                netIfApis,
                stltApis,
                props
            );
        }

        public AbsApiCallTester setNodeName(String nodeName)
        {
            this.nodeName = nodeName;
            return this;
        }

        public AbsApiCallTester setNodeType(String nodeType)
        {
            this.nodeType = nodeType;
            return this;
        }

        public CreateNodeCall clearNetIfApis()
        {
            this.netIfApis.clear();
            return this;
        }

        public CreateNodeCall addNetIfApis(String name, String address)
        {
            this.netIfApis.add(createNetInterfaceApi(name, address));
            return this;
        }

        public CreateNodeCall clearStltApis()
        {
            this.stltApis.clear();
            return this;
        }

        public AbsApiCallTester addStltApis(String name)
        {
            this.stltApis.add(createStltConnApi(name));
            return this;
        }

        public AbsApiCallTester clearProps()
        {
            this.props.clear();
            return this;
        }

        public AbsApiCallTester setProps(String key, String value)
        {
            this.props.put(key, value);
            return this;
        }
    }

    private class ModifyNodeCall extends AbsApiCallTester
    {

        private java.util.UUID nodeUuid;
        private String nodeName;
        private String nodeType;
        private Map<String, String> overrideProps;
        private Set<String> deletePropKeys;

        public ModifyNodeCall(long retCode)
        {
            super(
                // peer
                ApiConsts.MASK_NODE,
                ApiConsts.MASK_MOD,
                retCode
            );

            nodeUuid = null; // default: do not check against uuid
            nodeName = testNodeName.displayValue;
            nodeType = null; // default: do not update nodeType
            overrideProps = new TreeMap<>();
            deletePropKeys = new TreeSet<>();
        }

        public AbsApiCallTester nodeUuid(java.util.UUID uuid)
        {
            this.nodeUuid = uuid;
            return this;
        }

        public AbsApiCallTester nodeName(String nodeName)
        {
            this.nodeName = nodeName;
            return this;
        }

        public AbsApiCallTester nodeType(String nodeType)
        {
            this.nodeType = nodeType;
            return this;
        }

        public AbsApiCallTester overrideProps(String key, String value)
        {
            overrideProps.put(key, value);
            return this;
        }

        public AbsApiCallTester deleteProp(String key)
        {
            deletePropKeys.add(key);
            return this;
        }

        @Override
        public ApiCallRc executeApiCall()
        {
            return nodeApiCallHandlerProvider.get().modifyNode(
                nodeUuid,
                nodeName,
                nodeType,
                overrideProps,
                deletePropKeys
            );
        }

    }
}
