package com.yada.ssp.apiServer.net;

import com.yada.sdk.net.IPackageSplitterFactory;
import com.yada.sdk.net.TcpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class TcpSspClient implements SspClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private InetSocketAddress hostAddress;
    private IPackageSplitterFactory pkgSplitterFactory;
    private int timeout;

    public TcpSspClient(InetSocketAddress hostAddress, IPackageSplitterFactory pkgSplitterFactory, int timeout) {
        this.hostAddress = hostAddress;
        this.pkgSplitterFactory = pkgSplitterFactory;
        this.timeout = timeout;
    }

    @Override
    public ByteBuffer send(ByteBuffer req) throws IOException {
        TcpClient client = new TcpClient(hostAddress, pkgSplitterFactory, timeout);
        try {
            logger.info("send to Ssp: [{}]", new String(req.array()));
            client.open();
            ByteBuffer resp = client.send(req);
            logger.info("resp from Ssp: [{}]", new String(resp.array()));
            return resp;
        } catch (IOException e) {
            logger.warn("[{}] has a error: [{}]", hostAddress.toString(), e);
            throw e;
        } finally {
            client.close();
        }
    }
}
