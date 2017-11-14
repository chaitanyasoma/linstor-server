package com.linbit.drbdmanage.api.protobuf.common;

import java.io.IOException;
import java.io.InputStream;

import com.linbit.drbdmanage.CoreServices;
import com.linbit.drbdmanage.api.protobuf.BaseProtoApiCall;
import com.linbit.drbdmanage.api.protobuf.ProtobufApiCall;
import com.linbit.drbdmanage.netcom.Message;
import com.linbit.drbdmanage.netcom.Peer;
import com.linbit.drbdmanage.security.AccessContext;

@ProtobufApiCall
public class Pong extends BaseProtoApiCall
{
    public Pong(CoreServices coreServices)
    {
        super(coreServices.getErrorReporter());
    }

    @Override
    public String getName()
    {
        return Pong.class.getSimpleName();
    }

    @Override
    public String getDescription()
    {
        return "Updates the Pong-received timestamp";
    }

    @Override
    public void executeImpl(
        AccessContext accCtx,
        Message msg,
        int msgId,
        InputStream msgDataIn,
        Peer client
    )
        throws IOException
    {
        client.pongReceived();
    }

}