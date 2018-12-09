package org.tomek.podcaster.runner;

import java.io.IOException;

public class PodcastPlayerRunner implements Runnable {
    private final String applicationPath;
    private String podcastPath;

    public PodcastPlayerRunner(String applicationPath) {
        this.applicationPath = applicationPath;
    }

    public void setPodcastPath(String podcastPath) {
        this.podcastPath = podcastPath;
    }

    @Override
    public void run() {
        if (podcastPath == null) {
            throw new IllegalStateException("Cannot run player runner with no podcast path set.");
        }
        try {
            new ProcessBuilder(applicationPath, podcastPath).start();
        } catch (IOException e) {
            System.out.println("Error while running external application: " + e.getMessage());
        }
    }
}
