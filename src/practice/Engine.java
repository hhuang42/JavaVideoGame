/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import practice.Scripts.*;
import java.text.DecimalFormat;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.*;
import static java.lang.Math.*;

/**
 *
 * @author Student
 */
public class Engine implements WindowListener, KeyListener/*, Runnable*/ {

    Thread t;
    static int i;
    static double junk;
    static String message;
    static boolean frame;
    
    long timer;
    long elapsed;
    int fps = 0;
    int fpsC;
    boolean ready = false;
    final static int fWidth = 680;
    final static int fHeight = 480;
    Canvas canvas = new Canvas();
    JFrame app = new JFrame("All Star Rush!");
    BufferStrategy buffer;
    public static Menu thisM;
    static boolean menu = true;
    boolean speedup = false;
    public FrameRate frameRate = FrameRate.fps60;
    public static Engine thisE;
    public static GameEngine thisGE = new GameEngine();
    public static SpellCardMenu thisSC = new SpellCardMenu(thisE);
    public static IndividualDifficultyMenu thisIDM = new IndividualDifficultyMenu();
    public static DifficultyMenu thisDM = new DifficultyMenu(thisE);
    public static MainMenu thisMM = new MainMenu();
    public static SettingsMenu thisSM = new SettingsMenu();
    public static Save save = new Save();

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        if(ready)
        {
        save.copyToSave();}
        app.dispose();
    }

    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }

    public void windowIconified(WindowEvent e) {
        if(!menu&&!thisGE.menu){
            thisGE.pause();
        }
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
        
        if(!menu&&!thisGE.menu){
            thisGE.pause();
        }
    }

    public enum FrameRate{
        fps20(20),
        fps30(30),
        fps40(40),
        fps60(60);
        int rate;
        FrameRate(int rate){
            this.rate = rate;
        }
        
        
    }
    public static void main(String[] args) {
        thisE = new Engine();
    }

    Engine() {

        init();
        run();

    }

    public void init() {

        /*scrWidth = 6*64;
        scrHeight = 7*64;
        fWidth = 640;
        fHeight = 480;*/
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        frame = true;
        app.setIgnoreRepaint(true);
        app.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        app.addWindowListener(this);
        canvas.setIgnoreRepaint(true);
        app.add(canvas);
        app.pack();
        
        canvas.setSize(fWidth, fHeight);
        app.pack();
        //app.setSize(fWidth, fHeight);
        app.setVisible(true);
        app.setResizable(false);
        canvas.createBufferStrategy(2);
        
        //canvas.setSize(fWidth, fHeight);
        //app.pack();
        buffer = canvas.getBufferStrategy();
        
        //app.setSize(fWidth, fHeight);
        timer = 0;
        junk = 0;
        canvas.setBackground(Color.black);
        
        elapsed = System.nanoTime();
        timer = System.nanoTime();
        canvas.addKeyListener(this);
        thisE = this;
        
        canvas.setSize(fWidth, fHeight);
        app.pack();
        final Thread current = Thread.currentThread();
        Thread initializer = new Thread(){
            public void run(){
        thisM = thisMM;
        thisGE.init(thisE);
        thisM.init();
        MainScript.initialize();
        //thisMM.init();
        //thisDM.init();
        SE.init();
        Person.init();
        //thisSC.init();
        Images.values();
        thisSM.init();
        
        returnToMenu();
        save.init();
        
        save.copyFromSave();
        
        current.setPriority(Thread.MAX_PRIORITY);
        
        ready = true;}
        };
        initializer.setPriority(Thread.MAX_PRIORITY);
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        initializer.start();
        i = 0;
        



        
    }
    
    public static void returnToMenu(){
        menu = true;
        BGM.MENU.play();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        message = "value = " + key;
        if(menu)
        {
            this.thisM.keyPressed(key);
        }
        else
        {
        thisGE.keyPressed(key);
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        message = "value = " + key;
        if(menu)
        {
            this.thisM.keyReleased(key);
        }
        else
        {
        thisGE.keyReleased(key);
        }
    }

    public void run() {
        while (true) {

            if (ready) {
                if(menu)
                {
                    thisM.run();
                }
                else
                {
                thisGE.run();
                }
            }
                if (buffer != null&&
                        (
                        frameRate==FrameRate.fps60||
                        (frameRate==FrameRate.fps40&&i%3!=1)||
                        
                        (frameRate==FrameRate.fps30&&i%2!=1)||
                        
                        (frameRate==FrameRate.fps20&&i%3==0)
                        )
                        ) {
                    Graphics a;
                    int count = -1;
                    do {
                        for (int l = 0; l < 1; l++) {
                            do {
                                a = buffer.getDrawGraphics();
                                paint(a);


                                count++;
                                a.dispose();
                            } while (buffer.contentsRestored());

                            for (int k = 0; k < 5; k++) {
                                buffer.show();

                                canvas.getToolkit().sync();
                                app.getToolkit().sync();
                            }
                        }
                    } while (buffer.contentsLost());
                    Engine.junk += count;
                    fpsC++;

                }


                if ((!speedup) && ((float) pow(10, 9) / 60. + 1 * (elapsed - System.nanoTime())) / pow(10, 9) > 0) {
                    try {
                        Thread.sleep(max((int) (((float) pow(10, 9) / 60. + 1 * (elapsed - System.nanoTime())) / pow(10, 6)), 0));
                    } catch (InterruptedException e) {
                        ;
                    }
                } else {
                    Thread.yield();
                }
                frame = !frame;

                elapsed = max((int) pow(10, 9) / 60 + elapsed, System.nanoTime() - (int) pow(10, 9) / 60);
                if (System.nanoTime() % pow(10, 9) >= (timer) % pow(10, 9)) {
                    timer = System.nanoTime();

                } else {
                    timer = System.nanoTime();
                    fps = fpsC;
                    fpsC = 0;
                }

                i++;
        }
        
    }

    

    public void paint(Graphics g) {
        if(app.getContentPane().getSize().width!=fWidth)
        {
        canvas.setSize(fWidth, fHeight);
        app.pack();
        }
        canvas.getToolkit().sync();
        app.getToolkit().sync();
        if(ready)
        {
        if(menu)
                {
                    if(thisM.options!=null)
                    thisM.paint(g);
                }
                else
                {
                thisGE.paint(g);
                }
        }
        else{
            loadPaint(g);
        }
        
        g.dispose();
        //createImage(20,20);

    }
    public void loadPaint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(app.getInsets().left-3,0, fWidth, fHeight);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(Calc.opacity(sin(i*.1)*.5+.5));
        
        g2.drawString("LOADING",fWidth-g2.getFontMetrics().stringWidth("LOADING"), fHeight);
    }

    public void update(Graphics g) {
        paint(g);

    }

    public void keyTyped(KeyEvent e) {
    }
}
