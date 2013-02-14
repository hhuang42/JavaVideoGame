/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

/**
 *
 * @author Henry
 */
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.sound.sampled.Mixer.Info;
import static java.lang.Math.*;
   
/**
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
 * 3. You might optionally invoke the static method SoundEffect.init() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can use the static variable SoundEffect.volume to mute the sound.
 */
public enum SE {
   KIRA("SoundEffects/ZUN/se_kira00.wav"),   // explosion
   ESHOOT0("SoundEffects/ZUN/se_tan00.wav"),
   SELECT("SoundEffects/ZUN/se_select00.wav"),
   DECLARE("SoundEffects/ZUN/se_cat00.wav"),
   SLASH("SoundEffects/ZUN/se_slash.wav"),
   ESHOOT1("SoundEffects/ZUN/se_tan01.wav"),
   ESHOOT2("SoundEffects/ZUN/se_tan02.wav"),
   PSHOOT("SoundEffects/ZUN/se_plst00.wav"),
   CONFIRM("SoundEffects/ZUN/se_ok00.wav"),
   CANCEL("SoundEffects/ZUN/se_cancel00.wav"),
   INVALID("SoundEffects/ZUN/se_invalid.wav"),
   DEAD("SoundEffects/ZUN/se_pldead00.wav"),
   POP("SoundEffects/ZUN/se_enep00.wav"),
   EXPLODE("SoundEffects/ZUN/se_enep01.wav"),
   COLLECT("SoundEffects/ZUN/se_item00.wav"),
   LASER("SoundEffects/ZUN/se_lazer00.wav"),
   CAPTURE("SoundEffects/ZUN/se_cardget.wav");

   
   // Nested class for specifying volume
   public static Thread thread;
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }
   
   public static void main(String[] args){
       SE.init();
       for(Mixer.Info a:AudioSystem.getMixerInfo()){
           try{
               
               for(Control b:AudioSystem.getClip(a).getControls()){
                   System.out.println( b);
               }
           }
           catch(Exception e){};
       }
   }
   
   public static Volume volume = Volume.HIGH;
   
   // Each sound effect has its own clip, loaded with its own sound file.
   public Clip clip;
   private boolean start;
   public boolean loop = false;
   static int test = 0;
   FloatControl v;
   BooleanControl m;
   
   // Constructor to construct each element of the enum with its own sound file.
   SE(String soundFileName) {
      try {
         // Use URL (instead of File) to read from disk and JAR.
         URL url = this.getClass().getClassLoader().getResource(soundFileName);
         // Set up an audio input stream piped from the sound file.

         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         // Get a clip resource.
         clip = AudioSystem.getClip();
         
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioInputStream);
         v = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
         v.setValue(-10f);
         m = (BooleanControl)clip.getControl(BooleanControl.Type.MUTE);
         
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
      start = true;
   }
   public static void setVolume(float v){
       for(SE s: values())
       {
           s.v.setValue(v);
       
       }
   }
   public static void mute(boolean m){
       for(SE s: values())
       {
           s.m.setValue(m);
       
       }
   }
   public void loop(boolean loop) {
       if(this.loop!=loop)
       {
            this.loop = loop;
            if(loop)
                play();
            else
                clip.stop();
       }
   }
   public static void update(){
       for(SE s: values())
       {
       if(s.start)
       {
           
           s.playSound();
           s.start= false;
       }
       
       }
   }
   private void playSound(){
           int count = 0;
           
         if (clip.isActive())
         {clip.stop();
         }   // Stop the player if it is still running
         
         // rewind to the beginning
         clip.flush();
         clip.setFramePosition(0);
         
         while(!clip.isActive())
         {
             if(loop)
             {
             clip.loop(Clip.LOOP_CONTINUOUSLY);
             }
             else
             {
                 
                clip.start();
             }
             count++;
         }
         //Engine.junk = count;
          
       
   }
   
   // Optional static method to pre-load all the sound files.
   static void init() {
      values(); // calls the constructor for all the elements
      thread = new Thread(){
          public void run(){
              while(true)
              {
              update();
              try{
              Thread.sleep((long)(1000/60.));
              }
              catch(Exception e){}
              }
              
          }
      };
      thread.setDaemon(true);
      thread.start();
   }
}