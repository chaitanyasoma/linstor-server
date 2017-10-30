package com.linbit.drbdmanage.api.controller;

import java.io.IOException;
import java.io.InputStream;

import com.linbit.drbdmanage.ApiCallRc;
import com.linbit.drbdmanage.ApiConsts;
import com.linbit.drbdmanage.api.BaseApiCall;
import com.linbit.drbdmanage.core.Controller;
import com.linbit.drbdmanage.netcom.Message;
import com.linbit.drbdmanage.netcom.Peer;
import com.linbit.drbdmanage.proto.MsgDelStorPoolOuterClass.MsgDelStorPool;
import com.linbit.drbdmanage.security.AccessContext;

public class DeleteStorPool extends BaseApiCall
{
    private Controller controller;

    public DeleteStorPool(Controller controllerRef)
    {
        super(controllerRef.getErrorReporter());
        controller = controllerRef;
    }

    @Override
    public String getName()
    {
        return ApiConsts.API_DEL_STOR_POOL;
    }

    @Override
    public String getDescription()
    {
        return "Deletes a storage pool name registration";
    }

    @Override
    protected void executeImpl(
        AccessContext accCtx,
        Message msg,
        int msgId,
        InputStream msgDataIn,
        Peer client
    )
        throws IOException
    {
        MsgDelStorPool msgDeleteStorPool= MsgDelStorPool.parseDelimitedFrom(msgDataIn);

        ApiCallRc apiCallRc = controller.getApiCallHandler().deleteStoragePool(
            accCtx,
            client,
            msgDeleteStorPool.getNodeName(),
            msgDeleteStorPool.getStorPoolName()
        );
        super.answerApiCallRc(accCtx, client, msgId, apiCallRc);
    }

}
