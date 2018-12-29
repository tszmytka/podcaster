package org.tomek.podcaster.runner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PodcastPlayerRunnerTest {
    private PodcastPlayerRunner podcastPlayerRunner;


    @BeforeEach
    void setUp() {
        podcastPlayerRunner = new PodcastPlayerRunner("/path/to/player.exe");
    }

    @Test
    void willNotRunIfPodcastPathNotSet() {
        assertThrows(NullPointerException.class, () -> {
            podcastPlayerRunner.run();
        });
    }
}