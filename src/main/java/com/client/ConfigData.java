package com.client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigData {
    private String playerName;
    private String serverURL;

    public ConfigData() {
        
        this.playerName = "";
        this.serverURL = "";
    }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getServerURL() { return serverURL; }
    public void setServerURL(String serverURL) { this.serverURL = serverURL; }

    public void save() throws IOException {
        Properties props = new Properties();
        props.setProperty("playerName", playerName);
        props.setProperty("serverURL", serverURL);

        try (FileOutputStream out = new FileOutputStream("config.properties")) {
            props.store(out, "Configuration Player");
        }
    }


    public static ConfigData load() {
        ConfigData config = new ConfigData();
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("config.properties")) {
            props.load(in);
            config.setPlayerName(props.getProperty("playerName", ""));
            config.setServerURL(props.getProperty("serverURL", ""));
        } catch (IOException e) {
            
        }
        return config;
    }
}
