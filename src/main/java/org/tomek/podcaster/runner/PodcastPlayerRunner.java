package org.tomek.podcaster.runner;

import java.io.IOException;

public class PodcastPlayerRunner implements Runnable {
    private final String applicationPath;
    private final String podcastPath;

    public PodcastPlayerRunner(String applicationPath, String podcastPath) {
        this.applicationPath = applicationPath;
        this.podcastPath = podcastPath;
    }

    @Override
    public void run() {
        try {
            new ProcessBuilder(applicationPath, podcastPath).start();
        } catch (IOException e) {
            System.out.println("Error while running external application: " + e.getMessage());
        }
    }
}
