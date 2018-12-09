package org.tomek.podcaster.tokfm.dal;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PodcastUrls {
    private final static String SOURCE_URL = "https://audycje.tokfm.pl/gets";
    private final static Gson GSON = new Gson();

    public String fetchPodcastUrl(String podcastId) {
        try {
            URL url = new URL(SOURCE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                HashMap<String, String> inParams = new HashMap<>();
                inParams.put("st", "tokfm");
                inParams.put("pid", podcastId);
                outputStream.writeBytes(GSON.toJson(inParams));
            }

            try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                Map outParams = GSON.fromJson(inputReader.readLine(), Map.class);
                return (String) outParams.get("url");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}