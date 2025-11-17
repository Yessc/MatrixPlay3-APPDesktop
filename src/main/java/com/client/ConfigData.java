package com.client;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigData {//acordarme de mirar

    private String playerName;
    private String clientType;
    private List<Integer> scores; // lista de puntuaciones futura
    private double posY;


    private static final String FILE_NAME = "config.json";

    public ConfigData() {
        this.playerName = "";
        this.clientType = "";
        this.scores = new ArrayList<>();
        this.posY = 0.0;
    }
 
    // Getters y setters
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }


    public void save() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write("{\n");
            writer.write("  \"playerName\": \"" + escape(playerName) + "\",\n");
            writer.write("  \"clientType\": \"" + escape(clientType) + "\",\n");
            /*writer.write("  \"scores\": [");
            for (int i = 0; i < scores.size(); i++) {
                writer.write(String.valueOf(scores.get(i)));
                if (i < scores.size() - 1)
                    writer.write(",");
            }
            writer.write("]\n");*/
            writer.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ConfigData load() {
        ConfigData config = new ConfigData();
        File file = new File(FILE_NAME);
        if (!file.exists())
            return config;

        try (FileReader reader = new FileReader(file)) {
            char[] buffer = new char[(int) file.length()];
            reader.read(buffer);
            String json = new String(buffer);


            String nameKey = "\"playerName\":";
            int nameStart = json.indexOf(nameKey);
            if (nameStart != -1) {
                int start = json.indexOf("\"", nameStart + nameKey.length()) + 1;
                int end = json.indexOf("\"", start);
                config.setPlayerName(unescape(json.substring(start, end)));
            }

            String urlKey = "\"serverURL\":";
            int urlStart = json.indexOf(urlKey);
            if (urlStart != -1) {
                int start = json.indexOf("\"", urlStart + urlKey.length()) + 1;
                int end = json.indexOf("\"", start);
                config.setClientType(unescape(json.substring(start, end)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return config;
    }

 
    public static void clear() {
        File file = new File(FILE_NAME);
        if (file.exists())
            file.delete();
    }


    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String unescape(String s) {
        return s.replace("\\\"", "\"").replace("\\\\", "\\");
    }
}
