package com.linbit.linstor.api.interfaces.serializer;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.linbit.linstor.Node;
import com.linbit.linstor.Resource;
import com.linbit.linstor.ResourceDefinition;
import com.linbit.linstor.StorPool;
import com.linbit.linstor.StorPoolDefinition;
import com.linbit.linstor.api.pojo.ResourceState;

public interface CtrlClientSerializer
{
    Builder builder(String apiCall, int msgId);

    public interface Builder
    {
        byte[] build();

        /*
         * Controller -> Client
         */
        Builder nodeList(List<Node.NodeApi> nodes);
        Builder storPoolDfnList(List<StorPoolDefinition.StorPoolDfnApi> storPoolDfns);
        Builder storPoolList(List<StorPool.StorPoolApi> storPools);
        Builder resourceDfnList(List<ResourceDefinition.RscDfnApi> rscDfns);
        Builder resourceList(List<Resource.RscApi> rscs, Collection<ResourceState> rscStates);

        Builder apiVersion(long features, String controllerInfo);

        Builder ctrlCfgSingleProp(String namespace, String key, String value);
        Builder ctrlCfgProps(Map<String, String> map);
    }
}
