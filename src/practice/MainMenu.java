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
public class MainMenu extends Menu {
    
    public void init(){
        this.type=Type.MAIN;
        index = 0;
        dx = 1f;
        this.previous=Engine.thisMM;
        options = new Option[3];
        options[0] = new Option(){
            {
                this.optionName=
                        "Game Start";
            }
            public Menu action(){
                return new DifficultyMenu(Engine.thisE);
            }
        };
        options[1] = new Option(){
            {
                this.optionName=
                        "Spell Practice";
            }
            public Menu action(){
                return Engine.thisSC;
            }
        };
        
        options[2] = new Option(){
            {
                this.optionName=
                        "Options";
            }
            public Menu action(){
                return Engine.thisSM;
            }
        };
        options[(int)(index)].active=true;
    }
    
    
    
    
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        paintBackground(g);
        
        g2.drawImage(Images.TITLE.image, 70, 40, null);
        for(int i=0;i<options.length;i++){
            Option thisO = options[(i+options.length)%(options.length)];
            g2.setColor((thisO.active)?Color.BLACK:Color.BLACK);
            if(thisO.active)
            {
                g2.setComposite(Calc.opacity(1));
            }
            else
            {
            g2.setComposite(Calc.opacity(.5));
            }
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font(Font.SANS_SERIF,(thisO.active)?Font.BOLD:Font.BOLD,40));
            g2.drawString(thisO.optionName, (int)(50), (int)(thisE.fHeight*(.7+ .1*i)));
        }
    };
    
}
