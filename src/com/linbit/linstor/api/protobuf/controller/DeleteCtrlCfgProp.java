package com.linbit.linstor.api.protobuf.controller;

import javax.inject.Inject;
import com.linbit.linstor.api.ApiCall;
import com.linbit.linstor.api.ApiCallRc;
import com.linbit.linstor.api.ApiConsts;
import com.linbit.linstor.api.protobuf.ApiCallAnswerer;
import com.linbit.linstor.api.protobuf.ProtobufApiCall;
import com.linbit.linstor.core.CtrlApiCallHandler;
import com.linbit.linstor.proto.MsgDelCtrlCfgPropOuterClass.MsgDelCtrlCfgProp;

import java.io.IOException;
import java.io.InputStream;

@ProtobufApiCall(
    name = ApiConsts.API_DEL_CFG_VAL,
    description = "Deletes a controller config property (if it exists)"
)
public class DeleteCtrlCfgProp implements ApiCall
{
    private final CtrlApiCallHandler apiCallHandler;
    private final ApiCallAnswerer apiCallAnswerer;

    @Inject
    public DeleteCtrlCfgProp(CtrlApiCallHandler apiCallHandlerRef, ApiCallAnswerer apiCallAnswererRef)
    {
        apiCallHandler = apiCallHandlerRef;
        apiCallAnswerer = apiCallAnswererRef;
    }

    @Override
    public void execute(InputStream msgDataIn)
        throws IOException
    {
        MsgDelCtrlCfgProp protoMsg = MsgDelCtrlCfgProp.parseDelimitedFrom(msgDataIn);
        ApiCallRc apiCallRc = apiCallHandler.deleteCtrlCfgProp(
            protoMsg.getKey(),
            protoMsg.getNamespace()
        );
        apiCallAnswerer.answerApiCallRc(apiCallRc);
    }

}
