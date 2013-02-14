/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

/**
 *
 * @author Henry
 */

import practice.Scripts.*;
import java.text.DecimalFormat;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.*;
import static java.lang.Math.*;

public class PauseMenu extends Menu {
        public PauseMenu(Engine thisE){
        this.thisE=thisE;
    }
    
    public void init(){
        dx = 1f;
        this.type = Type.GAME;
        this.previous=null;
        final Menu menu = this;
        options = new Option[3];
        options[0] = new Option(){
            {
                this.optionName="Return to Game";
            }
            public Menu action(){
                Engine.thisGE.resume();
                return null;
            }
        };
        options[1] = new Option(){
            {
                this.optionName="Retry";
            }
            public Menu action(){
                thisE.thisGE.reset();
                return null;
            }
        };
        options[2] = new Option(){
            {
                this.optionName="Back to Menu";
            }
            public Menu action(){
                Engine.returnToMenu();
                return null;
            }
        };
        options[(int)(index)].active=true;
    
    }
    
    public void paint(Graphics g){
        
        Graphics2D g2 = (Graphics2D) g;
        
        Engine.thisGE.gameDraw(g);
        
        g2.setColor(Color.BLACK);
        
            g2.setComposite(Calc.opacity(.5));
        g2.fillRect(0, 0, Engine.fWidth + 600, Engine.fHeight + 600);
        for(int i=0;i<options.length;i++){
            Option thisO = options[(i+options.length)%(options.length)];
            g2.setColor((thisO.active)?Color.YELLOW:Color.WHITE);
            if(thisO.active)
            {
                g2.setComposite(Calc.opacity(1));
            }
            else
            {
            g2.setComposite(Calc.opacity(.5));
            }
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font(Font.SANS_SERIF,(thisO.active)?Font.BOLD:Font.BOLD,20));
            g2.drawString(thisO.optionName, (int)(120), (int)(200+i*50));
        }
    };
    
}
