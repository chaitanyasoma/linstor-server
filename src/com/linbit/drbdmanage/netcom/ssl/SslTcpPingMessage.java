package com.linbit.drbdmanage.netcom.ssl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import javax.net.ssl.SSLEngine;
import com.linbit.drbdmanage.netcom.IllegalMessageStateException;
import com.linbit.drbdmanage.netcom.TcpPingMessage;

public class SslTcpPingMessage extends TcpPingMessage
{
    private SSLEngine sslEngine;
    private ByteBuffer encryptedBuffer;

    void setSslEngine(SSLEngine sslEngineRef)
    {
        sslEngine = sslEngineRef;
        encryptedBuffer = ByteBuffer.allocateDirect(sslEngine.getSession().getPacketBufferSize());
    }

    @Override
    protected WriteState write(SocketChannel outChannel) throws IllegalMessageStateException, IOException
    {
        synchronized (this)
        {
            if (!encryptedBuffer.hasRemaining())
            {
                sslEngine.wrap(pingByteBuffer, encryptedBuffer);
                encryptedBuffer.flip();
                pingByteBuffer.flip();
            }
            outChannel.write(encryptedBuffer);
            if (!encryptedBuffer.hasRemaining())
            {
                encryptedBuffer.clear();
            }
        }
        return WriteState.FINISHED;
    }
}