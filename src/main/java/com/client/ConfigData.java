package com.client;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigData {
    private String playerName;
    private String clientType;
    private List<Integer> scores; // lista de puntuaciones futura
    private int goalScored;
    private double posY;


    private static final String FILE_NAME = "config.json";

    public ConfigData() {
        this.playerName = "";
        this.clientType = "";
        this.scores = new ArrayList<>();
        this.posY = 0.0;
        this.goalScored = 0;
    }
 
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

    public int getGoalScored() {
        return goalScored;
    }

    public void setGoalScored(int goalScored) {
        this.goalScored = goalScored;
    }


    public void save() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write("{\n");
            writer.write("  \"playerName\": \"" + escape(playerName) + "\",\n");
            writer.write("  \"clientType\": \"" + escape(clientType) + "\",\n");
            writer.write("  \"goalScored\": " + goalScored + "\n");
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

            String goalScoredKey = "\"goalScored\":";
            int goalScoredStart = json.indexOf(goalScoredKey);
            if (goalScoredStart != -1) {
                int start = goalScoredStart + goalScoredKey.length();
                int end = json.indexOf(",", start);
                if (end == -1)
                    end = json.indexOf("}", start);
                String goalScoredString = json.substring(start, end).trim();
                config.setGoalScored(Integer.parseInt(goalScoredString));
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
