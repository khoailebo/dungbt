import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Song {
    Clip clip;
    private static URL soundURL[] = new URL[10];
    private static boolean setted = false;
    public Song() {
        if(!setted)setSongUrl();
    }
    private void setSongUrl(){
        soundURL[0] = getClass().getResource("/sound/BeepBox-Song.wav");
        soundURL[1] = getClass().getResource("/sound/collision_click.wav");
        soundURL[2] = getClass().getResource("/sound/furture_click.wav");
        soundURL[3] = getClass().getResource("/sound/touch_paddles.wav");
        setted = true;
    }
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void start(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
