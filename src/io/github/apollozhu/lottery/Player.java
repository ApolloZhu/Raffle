package io.github.apollozhu.lottery;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

public class Player {
    public static void play(String path) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                try (AudioInputStream stream = AudioSystem.getAudioInputStream(new File(path))) {
                    clip.open(stream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
