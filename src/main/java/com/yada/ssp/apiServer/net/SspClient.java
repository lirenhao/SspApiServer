package com.yada.ssp.apiServer.net;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface SspClient {

    public ByteBuffer send(ByteBuffer req) throws IOException;
}
