package com.alphawang.zookeeper.curator.listener.checkconfig;

public class Client1 extends AbstractClient {

    public static void main(String[] args) throws Exception {
        new Client1().listen();
    }

    @Override
    protected String getClientName() {
        return "client-1";
    }
}
