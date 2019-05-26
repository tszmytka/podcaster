package dev.tomek.podcaster.tokfm.dal;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PodcastUrlsTest {
    private static final Gson GSON = new Gson();

    private PodcastUrls podcastUrls;

    @Mock
    private URLStreamHandler urlStreamHandler;

    @Mock
    private HttpURLConnection urlConnection;

    @Mock
    private OutputStream outputStream;

    @Mock
    private InputStream inputStream;


    @BeforeEach
    void setUp() throws MalformedURLException {
        MockitoAnnotations.initMocks(this);
        podcastUrls = new PodcastUrls(new URL("http://", "www.example.com", 80, "/gets", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) throws IOException {
                return urlConnection;
            }
        }));
    }

    @Test
    void canFetchPodcastUrls() throws Exception {
        when(urlConnection.getOutputStream()).thenReturn(outputStream);
        when(urlConnection.getInputStream()).thenReturn(inputStream);
        String podcastId = "podcast123";
        String url = "http://www.example.com/path/to/podcast123.mp3";
        when(inputStream.read(any(byte[].class), anyInt(), anyInt())).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            String outParams = "{" +
                "    \"url\": \"" + url + "\"" +
                "}\n";
            int i = 0;
            for (byte aByte : outParams.getBytes()) {
                buffer[i++] = aByte;
            }
            return outParams.length();
        });

        assertEquals(url, podcastUrls.fetchPodcastUrl(podcastId));
        ArgumentCaptor<Integer> outputStreamCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(outputStream, atLeastOnce()).write(outputStreamCaptor.capture());
        List<Integer> allValues = outputStreamCaptor.getAllValues();
        byte[] bytes = new byte[allValues.size()];
        int i = 0;
        for (Integer allValue : allValues) {
            bytes[i++] = allValue.byteValue();
        }
        Map map = GSON.fromJson(new String(bytes), Map.class);
        assertEquals(podcastId, map.get("pid"));
    }


    @Test
    void canSurviveIOException() throws Exception {
        when(urlConnection.getOutputStream()).thenThrow(IOException.class);
        assertEquals("", podcastUrls.fetchPodcastUrl("podcastABC"));
    }
}