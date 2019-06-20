package com.alphawang.zookeeper.curator.listener.checkconfig;

public class Client3 extends AbstractClient {

    public static void main(String[] args) throws Exception {
        new Client3().listen();
    }

    @Override
    protected String getClientName() {
        return "client-3";
    }
}
