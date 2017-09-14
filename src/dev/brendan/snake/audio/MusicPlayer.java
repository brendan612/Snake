package dev.brendan.snake.audio;

import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class MusicPlayer implements Runnable{

    private ArrayList<String> audioFiles;
    private int currentAudioIndex;
    
    public MusicPlayer(String... files){
        audioFiles = new ArrayList<String>();
        for (String file : files)
            audioFiles.add("./res/audio/" + file + ".wav");
    }
    
    private void playSound(String fileName){
        try{
            File soundFile = new File(fileName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip)AudioSystem.getLine(info);
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10);
            clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        playSound(audioFiles.get(currentAudioIndex));
    }

}
