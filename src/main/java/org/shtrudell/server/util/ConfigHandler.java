package org.shtrudell.server.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigHandler {
    String configFilePath = "conf.ini";

    public Configs load() {
        try {
            Properties properties = new Properties();
            FileInputStream propsInput = new FileInputStream(configFilePath);
            properties.load(propsInput);
            return new ConfigsImpl(Integer.parseInt(properties.getProperty("PORT")));
        } catch (IOException e) {
            return null;
        }

    }

    public void write(Configs configs) {
        try {
            Properties properties = new Properties();
            FileOutputStream propsOutput = new FileOutputStream(configFilePath);
            properties.setProperty("PORT", Integer.toString(configs.getPort()));
            properties.store(propsOutput, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
