package org.tomek.podcaster;

import org.tomek.podcaster.runner.ExternalAppRunner;

public class Main {
    public static void main(String[] args) {
        ExternalAppRunner runner = new ExternalAppRunner("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe", "http://lodz.radio.pionier.net.pl:8000/pl/tuba10-1.mp3");
        runner.run();
    }
}
