package dev.tomek.podcaster.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class PodcastPlayerRunner implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PodcastPlayerRunner.class);
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
        Objects.requireNonNull(podcastPath, "Cannot run player runner with no podcast path set.");
        try {
            new ProcessBuilder(applicationPath, podcastPath).start();
        } catch (IOException e) {
            LOGGER.error("Cannot run external application", e);
        }
    }
}
