package org.tomek.podcaster.runner;

import java.io.IOException;

public class ExternalAppRunner implements Runnable {
    private final String applicationPath;
    private final String podcastPath;

    public ExternalAppRunner(String applicationPath, String podcastPath) {
        this.applicationPath = applicationPath;
        this.podcastPath = podcastPath;
    }

    @Override
    public void run() {
        try {
            Process start = new ProcessBuilder(applicationPath, podcastPath).start();
        } catch (IOException e) {
            System.out.println("Error while running external application: " + e.getMessage());
        }
    }
}
