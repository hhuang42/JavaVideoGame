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
 * @author Henry
 */
public class GameEngine {

    static int i;
    static Engine thisE;
    Image offscreenImage;
    Graphics2D offscr;
    static int graze;
    static int points;
    public static int deathCount;
    boolean right;
    boolean left;
    boolean down;
    boolean up;
    static boolean paused;
    final static int scrWidth = 6 * 64;
    final static int scrHeight = 7 * 64;
    final static int widthOffset = (Engine.fWidth*2/3-scrWidth)/2;
    final static int heightOffset = (Engine.fHeight-scrHeight)/2;
    static Script script = null;
    public boolean menu;
    public Menu thisM;
    final static int bottom = 20;

    public void init(Engine thisE) {
        /*
         * graze = 0; points = 0;
         * //BossScript.difficulty=BossScript.Difficulty.LUNATIC;
         * //script.init(); Bullet.init(); Char.init(); Enemy.init();
         * Shot.init(); Item.init();
        script.init();
         */
        this.thisE = thisE;
        Bullet.init();
        Char.init();
        Enemy.init();
        Shot.init();
        Item.init();

    }

    public void setScript(Script script) {
        this.script = script;
        reset();
    }

    public void reset() {
        graze = 0;
        points = 0;
        deathCount = 0;
        right = false;
        left = false;
        up = false;
        down = false;
        menu = false;
        //BossScript.difficulty=BossScript.Difficulty.LUNATIC;
        //script.init();
        Bullet.init();
        Char.init();
        Enemy.init();
        Shot.init();
        Item.init();
        if (script != null) {
            script.init();
        }
        BGM.SEB.play();
        thisM = null;


    }

    public static void graze() {
        graze++;
    }

    public static void point() {
        points++;
    }

    public static void deathCount() {
        deathCount++;
    }

    public void gameDraw(Graphics g) {

        DecimalFormat format = new DecimalFormat("0.00");
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 2 * Engine.fWidth, 2 * Engine.fHeight);
        Calc.drawBackground(g, Images.FARBG.image, 0, 0);
        offscreenImage = Calc.newBImage(scrWidth, scrHeight);
        offscr = (Graphics2D) offscreenImage.getGraphics();
        offscr.setColor(new Color((int) (sin(i / 210.f) * 30 + 90), (int) (sin(i / 250.f) * 30 + 90), (int) (sin(i / 230.f) * 30 + 90)));
        offscr.fillRect(0, 0, scrWidth, scrHeight);
        int x = (int) (scrWidth * (cos(1. * i / scrWidth) + 2)) % scrWidth;
        int y = (i * 7 / 3) % scrHeight;
        BufferedImage image = Images.BG5.image;
        offscr.drawImage(image, null, x, y);
        offscr.drawImage(image, null, x - scrWidth, y);
        offscr.drawImage(image, null, x, y - scrHeight);
        offscr.drawImage(image, null, x - scrWidth, y - scrHeight);
        Shot.RenderShot(offscr);
        Item.RenderItem(offscr);
        Char.drawChar(offscr);

        Enemy.RenderEnemy(offscr);
        //offscr.setComposite(Calc.opacity(.1));
        Bullet.RenderBullet(offscr);
        Char.drawHitBox(offscr);
        script.paint(offscr);
        g2.drawImage(offscreenImage, widthOffset, heightOffset, null);
        Boss.drawIndicatorBar(g2);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.YELLOW.darker().darker().darker().darker().darker());
        g2.setFont(new Font("serif", Font.BOLD, 24));
        int left = 450;
        g2.drawString("Death Count: " + deathCount, left, 160);
        if(MainScript.mainRecord!=null){
        g2.drawString("Captured: " + MainScript.mainRecord.success() + " / " + MainScript.mainRecord.attempt(), left, 200);
        }
        ///g2.drawString("Bullets: " + Bullet.Blist.size(), left, 240);

        //g2.drawString("Power: " + format.format(Char.ch.power / 100.) + "  ", 500, 100);
        //g2.drawString("Bombs: " + Char.ch.bombCount, 500, 120);
        //g2.drawString("Points: " + points, 500, 140);

        g2.setColor(Color.WHITE);
        BufferedImage diff = Images.Images(BossScript.difficulty).image;
        int center = (Engine.fWidth-widthOffset-scrWidth)/2+scrWidth+widthOffset;
        g2.drawImage(diff, center-diff.getWidth()/2, 20, null);
        diff = Images.MINITITLE.image;
        g2.drawImage(diff, center-diff.getWidth()/2, Engine.fHeight-160, null);
        g2.setFont(new Font("serif", Font.PLAIN, 12));
        g2.drawString("FPS: " + thisE.fps,
                Engine.fWidth-g2.getFontMetrics().stringWidth("FPS: " + thisE.fps), Engine.fHeight);
        //g2.drawString("w/e: " + (thisE.junk), 500, 220);
        if (i % 2 == 0) {
            //g2.fillRect(600, 100, 5, 5);
        }
    }

    public void paint(Graphics g) {
        if (menu) {
            thisM.paint(g);
        } else {
            gameDraw(g);
        }
    }

    public void gameKeyPressed(int key) {
        switch (key) {
            case 16:
                Char.setFocus(true);
                break;
            case 90:
                Char.fire(true);
                break;
            case 88:
                //Char.ch.bomb();
                break;
            case 73:
                //Char.ch.invincible = !Char.ch.invincible;
                break;
            case 38:
                up = true;
                break;
            case 40:
                down = true;
                break;
            case 37:
                left = true;
                break;
            case 39:
                right = true;
                break;
            case 17:
                //thisE.speedup = true;
                break;
            default:
                break;

        }

    }

    public void gameKeyReleased(int key) {


        switch (key) {
            case 16:
                Char.setFocus(false);
                break;
            case 27:
                this.pause();
                //paused = !paused;
                break;
            case 90:
                Char.fire(false);
                break;
            case 38:
                up = false;
                break;
            case 40:
                down = false;
                break;
            case 37:
                left = false;
                break;
            case 39:
                right = false;
                break;
            case 17:
                //thisE.speedup = false;
                //thisE.elapsed = System.nanoTime();
                break;

            case 77:
                //BGM.mute(!BGM.muted);
                break;
            default:
                break;
        }
    }

    public void keyPressed(int key) {
        if (menu) {
            thisM.keyPressed(key);
        } else {
            gameKeyPressed(key);
        }

    }

    public void keyReleased(int key) {
        if (menu) {
            thisM.keyReleased(key);
        } else {
            gameKeyReleased(key);
        }
    }

    public void CharV() {
        int x = 0;
        int y = 0;
        if (right) {
            x = 1;
        }
        if (left) {
            x = -1;
        }
        if (down) {
            y = 1;
        }
        if (up) {
            y = -1;
        }

        Char.setVel(x, y);
    }

    public void pause() {
        SE.PSHOOT.loop(false);
        thisM = new PauseMenu(thisE);
        thisM.init();
        menu = true;
        BGM.pause();


    }

    public void resume() {
        menu = false;
        BGM.resume();


    }

    public void clearAll() {
        Bullet.ClearAll();
        Enemy.ClearAll();
    }

    public void run() {

        if (menu) {
            thisM.run();
        } else {

            script.run();

            CharV();
            Char.updateChar();

            Bullet.UpdateBullet();
            Enemy.UpdateEnemy();
            Item.UpdateItem();


            if (script.isDone()) {
                script.finish();

            }
            i++;
        }

    }
}