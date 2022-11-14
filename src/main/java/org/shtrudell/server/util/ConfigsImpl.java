package org.shtrudell.server.util;

class ConfigsImpl implements  Configs {
    private final int port;

    public ConfigsImpl(int port) {
        this.port = port;
    }

    @Override
    public int getPort() {
        return this.port;
    }
}
