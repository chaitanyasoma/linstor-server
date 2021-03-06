package com.linbit.linstor.api.protobuf.controller;

import javax.inject.Inject;
import com.linbit.linstor.api.ApiCall;
import com.linbit.linstor.api.ApiCallRc;
import com.linbit.linstor.api.ApiConsts;
import com.linbit.linstor.api.protobuf.ApiCallAnswerer;
import com.linbit.linstor.api.protobuf.ProtobufApiCall;
import com.linbit.linstor.core.CtrlApiCallHandler;
import com.linbit.linstor.proto.MsgDelNetInterfaceOuterClass.MsgDelNetInterface;

import java.io.IOException;
import java.io.InputStream;

@ProtobufApiCall(
    name = ApiConsts.API_DEL_NET_IF,
    description = "Deletes a network interface from a given node"
)
public class DeleteNetInterface implements ApiCall
{
    private final CtrlApiCallHandler apiCallHandler;
    private final ApiCallAnswerer apiCallAnswerer;

    @Inject
    public DeleteNetInterface(CtrlApiCallHandler apiCallHandlerRef, ApiCallAnswerer apiCallAnswererRef)
    {
        apiCallHandler = apiCallHandlerRef;
        apiCallAnswerer = apiCallAnswererRef;
    }

    @Override
    public void execute(InputStream msgDataIn)
        throws IOException
    {
        MsgDelNetInterface protoMsg = MsgDelNetInterface.parseDelimitedFrom(msgDataIn);
        ApiCallRc apiCallRc = apiCallHandler.deleteNetInterface(
            protoMsg.getNodeName(),
            protoMsg.getNetIfName()
        );

        apiCallAnswerer.answerApiCallRc(apiCallRc);
    }

}
