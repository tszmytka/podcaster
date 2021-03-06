package dev.tomek.podcaster.tokfm.dal;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PodcastUrls {
    private static final Logger LOGGER = LoggerFactory.getLogger(Podcasts.class);
    private static final Gson GSON = new Gson();

    private final URL podcastUrl;


    public PodcastUrls(URL podcastUrl) {
        this.podcastUrl = podcastUrl;
    }

    public String fetchPodcastUrl(String podcastId) {
        try {
            HttpURLConnection connection = (HttpURLConnection) podcastUrl.openConnection();
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
            LOGGER.error("Cannot get podcast url", e);
        }
        return "";
    }
}
