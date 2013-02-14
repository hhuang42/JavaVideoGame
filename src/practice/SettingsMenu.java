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
public class SettingsMenu extends Menu {

    public static int vBGM = 10;
    public static int vSE = 8;
    public static int fpsIndex = Engine.FrameRate.values().length - 1;

    public SettingsMenu() {
        this.type = Type.MAIN;
    }

    public static void setSEVolume(int vSE) {
        SettingsMenu.vSE = vSE;

        SE.setVolume(-50 + 5 * vSE);
        SE.mute(vSE == 0);
    }

    public static void setFPS(int fpsIndex) {
        SettingsMenu.fpsIndex = fpsIndex;


        Engine.thisE.frameRate = Engine.FrameRate.values()[fpsIndex];
    }

    public static void setBGMVolume(int vBGM) {
        SettingsMenu.vBGM = vBGM;

        BGM.setVolume(-50 + 5 * vBGM);
        BGM.mute(vBGM == 0);
    }

    public void init() {
        dx = 1f;
        this.previous = Engine.thisMM;
        options = new SettingsOption[4];
        options[0] = new SettingsOption() {

            {
                this.optionName = "BGM Volume :";
            }

            public Menu action() {

                return null;
            }

            @Override
            public void flipLeft() {
                setBGMVolume(max(vBGM - 1, 0));
                

            }

            @Override
            public void flipRight() {

                setBGMVolume(min(vBGM + 1, 10));
            }

            public void draw(Graphics2D g, int x, int y,int fontSize) {
                super.draw(g, x, y, fontSize, vBGM * 10 + "%");
                /*Graphics2D g2 = (Graphics2D) g;
                g2.setColor((this.active) ? Color.BLACK : Color.BLACK);
                if (this.active) {
                    g2.setComposite(Calc.opacity(1));
                } else {
                    g2.setComposite(Calc.opacity(.4));
                }
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setFont(new Font(Font.SANS_SERIF, (this.active) ? Font.BOLD : Font.BOLD, fontSize));
                g2.drawString(this.optionName + " :", x, y);
                g2.drawString(vBGM * 10 + "%", x + 150, y);*/
            }
        };

        options[1] = new SettingsOption() {

            {
                this.optionName = "SE Volume :";
            }

            public Menu action() {

                return null;
            }

            @Override
            public void flipLeft() {
                setSEVolume(max(vSE - 1, 0));
                SE.SELECT.play();
            }

            @Override
            public void flipRight() {

                setSEVolume(min(vSE + 1, 10));

                SE.SELECT.play();
            }

            public void draw(Graphics2D g, int x, int y,int fontSize) {
                super.draw(g, x, y, fontSize, vSE * 10 + "%");
                /*Graphics2D g2 = (Graphics2D) g;
                g2.setColor((this.active) ? Color.BLACK : Color.BLACK);
                if (this.active) {
                    g2.setComposite(Calc.opacity(1));
                } else {
                    g2.setComposite(Calc.opacity(.4));
                }
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setFont(new Font(Font.SANS_SERIF, (this.active) ? Font.BOLD : Font.BOLD, 20));
                g2.drawString(this.optionName + " :", x, y);
                g2.drawString(vSE * 10 + "%", x + 150, y);*/
            }
        };

        options[2] = new SettingsOption() {

            {
                this.optionName = "FPS :";
            }

            public Menu action() {

                return null;
            }

            @Override
            public void flipLeft() {
                setFPS(max(fpsIndex - 1, 0));

            }

            @Override
            public void flipRight() {

                setFPS(min(fpsIndex + 1, Engine.FrameRate.values().length - 1));
            }

            public void draw(Graphics2D g, int x, int y,int fontSize) {
                super.draw(g, x, y, fontSize,Engine.thisE.frameRate.rate + " fps");
                /*Graphics2D g2 = (Graphics2D) g;
                g2.setColor((this.active) ? Color.BLACK : Color.BLACK);
                if (this.active) {
                    g2.setComposite(Calc.opacity(1));
                } else {
                    g2.setComposite(Calc.opacity(.5));
                }
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setFont(new Font(Font.SANS_SERIF, (this.active) ? Font.BOLD : Font.BOLD, 20));
                g2.drawString(this.optionName + " :", x, y);
                g2.drawString(, x + 150, y);*/
            }
        };
        options[3] = new SettingsOption() {
            int i = 0;
            String[] states = {"Reset All Data","Are You Sure?","THIS CANNOT BE UNDONE","COMPLETELY SURE?","RESET ALL DATA"};
            {
                
                this.optionName = states[i];
            }
            public void init(){
                super.init();
                i=0;
            }

            public Menu action() {
                i++;
                if(i==states.length){
                    Engine.save.init();
                    i=0;
                }
                optionName = states[i];
                return null;
            }

            @Override
            public void flipLeft() {
                //setFPS(max(fpsIndex - 1, 0));

            }

            @Override
            public void flipRight() {

                //setFPS(min(fpsIndex + 1, Engine.FrameRate.values().length - 1));
            }

            public void draw(Graphics2D g, int x, int y,int fontSize) {
                super.draw(g, x, y, fontSize,"");
                /*Graphics2D g2 = (Graphics2D) g;
                g2.setColor((this.active) ? Color.BLACK : Color.BLACK);
                if (this.active) {
                    g2.setComposite(Calc.opacity(1));
                } else {
                    g2.setComposite(Calc.opacity(.5));
                }
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setFont(new Font(Font.SANS_SERIF, (this.active) ? Font.BOLD : Font.BOLD, 20));
                g2.drawString(this.optionName + " :", x, y);
                g2.drawString(, x + 150, y);*/
            }
        };
        options[(int) (index)].active = true;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        paintBackground(g);
        for (int i = 0; i < options.length; i++) {
            SettingsOption thisO = (SettingsOption) options[(i + options.length) % (options.length)];
            thisO.draw(g2, 300, 200 + 50 * i,20);
            /*
             * g2.setColor((thisO.active)?Color.BLACK:Color.BLACK);
             * if(thisO.active) { g2.setComposite(Calc.opacity(1)); } else {
             * g2.setComposite(Calc.opacity(.4)); }
             * g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
             * RenderingHints.VALUE_ANTIALIAS_ON); g2.setFont(new
             * Font(Font.SANS_SERIF,(thisO.active)?Font.BOLD:Font.BOLD,50)); g2.
             * g2.drawString(thisO.optionName, (int)(600-200*cos((i-index)*.2)),
             * (int)(thisE.fHeight*(1./2+ 1.*sin((i-index)*.2))));
             */        }
    }

    public void keyPressed(int key) {
        super.keyPressed(key);
        switch (key) {
            case 37:
                ((SettingsOption) options[round(this.index)]).flipLeft();
                break;
            case 39:
                ((SettingsOption) options[round(this.index)]).flipRight();
                break;
        }
    }

    ;
    public abstract class SettingsOption extends Option {
        public abstract void flipLeft();

        public abstract void flipRight();
        
        public void draw(Graphics2D g2,int x, int y, int fontSize,String value){
            super.draw(g2, x, y, fontSize);
            g2.drawString(value, x+150, y);
            
        }
    }
}
