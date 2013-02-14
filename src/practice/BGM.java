/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

/**
 *
 * @author Henry
 */
//import EasyOgg.src.org.newdawn.easyogg.OggClip;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.sound.sampled.Mixer.Info;

public enum BGM {
    //S3S("BGM/th07_06.wav"),
    //S3B("BGM/th07_07.wav"),
    //S4B("BGM/th07_09.wav");
    //SEB("BGM/29 Futatsuiwa from Sado.ogg");

    SEB("BGM/29 Futatsuiwa from Sado.ogg", 13.26),
    MENU("BGM/27-Electronica In Velvet Room(cut).ogg",6.25)
    ;
    public static BGM current = null;
    public static Thread currentPlayer;
    public static boolean paused = false;
    final public static Object waitObject = new Object();
    public static float gain = 0;
    public static boolean muted = false;

    public static void main(String[] args) {
        //BGM.SEB.play();
        BGM.MENU.play();
        try {
            Thread.sleep(100000);
        } catch (Exception e) {
        }
        /*
         * for(Info a: AudioSystem.getMixerInfo()) { Mixer b =
         * AudioSystem.getMixer(a); System.out.println(a); for(Line
         * c:b.getSourceLines()) { System.out.println(" - " + c.getLineInfo());
         * for(Control d:c.getControls()) { System.out.println(" - " +
         * d.getType()); } } }
         */
        /*
         * FloatControl v
         * =((FloatControl)S4B.line.getControl(FloatControl.Type.MASTER_GAIN));
         * System.out.println(v.getUnits()); System.out.println(v.getMinimum());
         * System.out.println(v.getMaximum());
         */

    }
    public InputStream stream;
    public AudioInputStream prepared = null;
    AudioFormat decodedFormat;
    String filename;
    FloatControl volume;
    BooleanControl mute;
    double loopOffset;
    SourceDataLine line;

    BGM(String soundFileName, double loopOffset) {
        filename = soundFileName;
        this.loopOffset = loopOffset;
        try {
            stream = this.getClass().getClassLoader().getResourceAsStream(soundFileName);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        pause();
        currentPlayer = null;
    }

    public static void play(BGM track) {
        stop();
        current = track;
        try {
            currentPlayer = new Thread() {

                public void run() {
                    
                    paused = false;
                    current.testPlay();
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }
        currentPlayer.setDaemon(true);
        currentPlayer.start();
    }

    public void testPlay() {
        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filename);
            // Get AudioInputStream from given file.
            AudioInputStream in = AudioSystem.getAudioInputStream(stream);
            AudioInputStream din = null;

            if (in != null) {
                AudioFormat baseFormat = in.getFormat();
                decodedFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        baseFormat.getSampleRate(),
                        16,
                        baseFormat.getChannels(),
                        baseFormat.getChannels() * 2,
                        baseFormat.getSampleRate(),
                        false);

                // Get AudioInputStream that will be decoded by underlying VorbisSPI

                // Play now !

                din = AudioSystem.getAudioInputStream(decodedFormat, in);
                //rawplay(decodedFormat, din);

                byte[] data = new byte[4096];


                line = getLine(decodedFormat);
                
                if (line != null) {
                    // Start
                    line.start();
                    prepared= null;
                    while (currentPlayer == Thread.currentThread()) {

                        int nBytesRead = 0, nBytesWritten = 0;
                        if (prepared == null) {
                            int toRead = (int) (din.getFormat().getFrameSize() * (int) (0 * din.getFormat().getFrameRate()));
                            
                            byte[] blank = new byte[toRead];
                            while (toRead > 0) {
                            toRead -= din.read(blank, 0, toRead);
                            }
                        } else {
                            din = prepared;
                        }
                        prepareStream(decodedFormat, filename, loopOffset);
                        while (currentPlayer == Thread.currentThread()&&nBytesRead != -1) {
                            if (prepared != null);
                            
                            if(!paused)
                            {
                                line.start();
                            nBytesRead = din.read(data, 0, data.length);
                            while (nBytesRead % 4 != 0 && nBytesRead != -1) {
                                nBytesRead = nBytesRead + din.read(data, nBytesRead, data.length - nBytesRead);
                            }
                            if (nBytesRead != -1) {
                                nBytesWritten = line.write(data, 0, nBytesRead);
                            }}
                            else{
                                line.stop();
                                Thread.yield();
                            }
                        }

                        // Stop

                        din.close();
          
                    
                    }
                    line.drain();
                    line.stop();
                    line.close();
                }
                //in.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareStream(final AudioFormat decodedFormat, final String filename, final double time) {

        Thread thisT = new Thread() {

            public void run() {
                try {
                    InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filename);
                    // Get AudioInputStream from given file.	
                    AudioInputStream in = AudioSystem.getAudioInputStream(stream);
                    AudioInputStream din = AudioSystem.getAudioInputStream(decodedFormat, in);

                    int toRead = (int) (din.getFormat().getFrameSize() * (int) (time * din.getFormat().getFrameRate()));

                    while (toRead > 0) {
                        byte[] blank = new byte[toRead];
                        toRead -= din.read(blank, 0, toRead);
                    }

                    prepared = din;
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };
        thisT.setDaemon(true);
        thisT.start();
    }

    private void rawplay(AudioFormat targetFormat,
            AudioInputStream din) throws IOException, LineUnavailableException {
        byte[] data = new byte[4096];


        SourceDataLine line = getLine(targetFormat);
        if (line != null) {
            // Start
            line.start();
            while (true) {

                int nBytesRead = 0, nBytesWritten = 0;
                if (prepared == null) {
                    int toRead = (int) (din.getFormat().getFrameSize() * (int) (130 * din.getFormat().getFrameRate()));

                    byte[] blank = new byte[toRead];
                    while (toRead > 0) {
                        toRead -= din.read(blank, 0, toRead);
                    }
                } else {
                    din = prepared;
                }
                prepareStream(decodedFormat, filename, loopOffset);
                while (nBytesRead != -1) {
                    System.out.println(nBytesRead = din.read(data, 0, data.length));
                    while (nBytesRead % 4 != 0 && nBytesRead != -1) {
                        nBytesRead = nBytesRead + din.read(data, nBytesRead, data.length - nBytesRead);
                    }
                    if (nBytesRead != -1) {
                        nBytesWritten = line.write(data, 0, nBytesRead);
                    }
                }

                // Stop

                din.close();
            }

            //line.drain();
            //line.stop();
            //line.close();
        }
    }

    private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
        SourceDataLine res = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        volume = (FloatControl) res.getControl(FloatControl.Type.MASTER_GAIN);
        mute = (BooleanControl) res.getControl(BooleanControl.Type.MUTE);

        mute.setValue(muted);
        volume.setValue(gain);
        return res;
    }

    public boolean paused() {
        return false;
    }

    public boolean stopped() {
        return false;
    }

    public void play() {
        play(this);
    }

    public static void pause() {
        if(current!=null&&current.line!=null)
        {
        current.line.stop();
        }
        paused = true;
    }

    public static void resume() {
        paused = false;
    }

    public static void setVolume(float gain) {
        BGM.gain = gain;
        if (current != null && current.volume != null) {
            current.volume.setValue(gain);
        }
    }

    public static void mute(boolean muted) {
        BGM.muted = muted;
        if (current != null && current.mute != null) {
            current.mute.setValue(muted);
        }
    }
    // Optional static method to pre-load all the sound files.

    static void init() {
        values(); // calls the constructor for all the elements
    }
}
