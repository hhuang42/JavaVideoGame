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
import practice.Save.Record;

/**
 *
 * @author Henry
 */
public class DifficultyMenu extends Menu {
    
    public DifficultyMenu(Engine thisE){
        this.type=Type.MAIN;
        
        this.thisE=thisE;
    }
    
    public void init(){
        dx = 1f;
        this.previous=Engine.thisMM;
        options = new Option[4];
        for(int i = 0;i<Difficulty.values().length;i++){
            final int i0 = i;
        options[i] = new Option(){
            {
                this.optionName=Difficulty.values()[i0].toString();
            }
            public Menu action(){
                BossScript.difficulty=Difficulty.values()[i0];
                thisE.thisGE.setScript(MainScript.allScript());
                thisE.menu = false;
                
                return null;
            }
            public void draw(Graphics2D g, int x, int y, int fontSize) {
                    super.draw(g, x, y, fontSize);
                    if (round(index)==i0) {
                        Calc.drawTextRectangle(g, 10, 140, 390, 240,""
                                );
                        g.setColor(new Color[]{Color.GREEN,Color.BLUE,Color.ORANGE,Color.RED}[i0]);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
                        g.drawString(Difficulty.values()[i0].name(),
                                (400 - g.getFontMetrics().stringWidth(Difficulty.values()[i0].name())) / 2, 180);
                        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 24));
                        g.drawString(Difficulty.values()[i0].description,
                                (400 - g.getFontMetrics().stringWidth(Difficulty.values()[i0].description)) / 2, 220);
                        int leastLives = Engine.save.deathRecord(Difficulty.values()[i0]);
                        
                        Record mostCapture = Engine.save.playRecord(Difficulty.values()[i0]);
                        
                        Calc.drawTextRectangle(g, 10, 280, 390, 400,
                                "Least Lives Used: " + (leastLives!=-1?leastLives:"---") + "\n\n"+
                                
                                "Record Spells Captured: " + (mostCapture.success())+ " / " + mostCapture.attempt() + "\n"
                                );
                    }
                }
        };
        options[(int)(index)].active=true;
    }
    }
    
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        paintBackground(g);
        for(int i=0;i<options.length;i++){
            Option thisO = options[(i+options.length)%(options.length)];
            thisO.draw(g2, (int)(420),  (int)(thisE.fHeight*(.6*(i+2.5)/options.length)), 50);
            
        g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        g2.setComposite(Calc.opacity(1));
        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 64));
        g2.drawString("Difficulty", (400 - g2.getFontMetrics().stringWidth("Difficulty")) / 2, 100);
            /*g2.setColor((thisO.active)?Color.BLACK:Color.BLACK);
            if(thisO.active)
            {
                g2.setComposite(Calc.opacity(1));
            }
            else
            {
            g2.setComposite(Calc.opacity(.4));
            }
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font(Font.SANS_SERIF,(thisO.active)?Font.BOLD:Font.BOLD,50));
            g2.drawString(thisO.optionName, (int)(600-200*cos((i-index)*.2)), (int)(thisE.fHeight*(1./2+ 1.*sin((i-index)*.2))));*/
        }
    };
    
}
