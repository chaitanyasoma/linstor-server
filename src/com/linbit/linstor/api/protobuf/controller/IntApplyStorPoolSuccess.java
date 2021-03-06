package com.linbit.linstor.api.protobuf.controller;

import javax.inject.Inject;
import com.linbit.linstor.InternalApiConsts;
import com.linbit.linstor.api.ApiCall;
import com.linbit.linstor.api.pojo.FreeSpacePojo;
import com.linbit.linstor.api.protobuf.ProtobufApiCall;
import com.linbit.linstor.core.CtrlApiCallHandler;
import com.linbit.linstor.netcom.Peer;
import com.linbit.linstor.proto.StorPoolFreeSpaceOuterClass.StorPoolFreeSpace;
import com.linbit.linstor.proto.javainternal.MsgIntApplyStorPoolSuccessOuterClass.MsgIntApplyStorPoolSuccess;
import com.linbit.linstor.proto.javainternal.MsgIntFullSyncSuccessOuterClass.MsgIntFullSyncSuccess;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ProtobufApiCall(
    name = InternalApiConsts.API_APPLY_STOR_POOL_SUCCESS,
    description = "Satellite successfully applied a storage pool"
)
public class IntApplyStorPoolSuccess implements ApiCall
{
    private final CtrlApiCallHandler apiCallHandler;
    private final Peer satellite;

    @Inject
    public IntApplyStorPoolSuccess(
        CtrlApiCallHandler apiCallHandlerRef,
        Peer satelliteRef
    )
    {
        apiCallHandler = apiCallHandlerRef;
        satellite = satelliteRef;
    }

    @Override
    public void execute(InputStream msgDataIn)
        throws IOException
    {
        MsgIntApplyStorPoolSuccess successMsg = MsgIntApplyStorPoolSuccess.parseDelimitedFrom(msgDataIn);

        StorPoolFreeSpace freeSpaceProto = successMsg.getFreeSpace();
        apiCallHandler.updateRealFreeSpace(
            satellite,
            new FreeSpacePojo(
                UUID.fromString(freeSpaceProto.getStorPoolUuid()),
                freeSpaceProto.getStorPoolName(),
                freeSpaceProto.getFreeSpace()
            )
        );
    }

}
