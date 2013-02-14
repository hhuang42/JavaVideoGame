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
public abstract class Menu {

    Option[] options;
    Image background;
    float index = 0;
    float dx = .1f;
    float di = 0;
    Engine thisE;
    Menu previous;
    boolean acting = false;
    public Type type;
    
    public enum Type{
        MAIN,
        GAME;
    }

    public abstract class Option {
        
        String optionName = "optionName";
        Image optionImage;
        public boolean available = true;
        public boolean active = false;

        public abstract Menu action();
        public void init(){
            optionImage = Calc.textToImageR(optionName, Color.BLACK, 300);
            
        }
        public void draw(Graphics2D g2, int x, int y,int fontSize) {
        
            g2.setColor((this.active)?Color.BLACK:Color.GRAY.darker());
            if(this.active)
            {
                g2.setComposite(Calc.opacity(1));
            }
            else
            {
            g2.setComposite(Calc.opacity(.6));
            }
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font(Font.SANS_SERIF,(this.active)?Font.BOLD:Font.BOLD,fontSize));
            g2.drawString(this.optionName, x, y);
                }
    }
    public void paintBackground(Graphics g){
         int x=(int)((4*Engine.i)%Engine.fWidth+Engine.fWidth)%Engine.fWidth;
        int y=(int)((1000*sin(Engine.i/1000.))%Engine.fHeight+Engine.fHeight)%Engine.fHeight;
        BufferedImage im = Images.MENUBACK2.getImage();
        Calc.drawBackground(g,im ,x, y);
        
        Calc.drawBackground(g, im,x-Engine.fWidth, y);
        
        Calc.drawBackground(g, im,x, y-Engine.fHeight);
        
        Calc.drawBackground(g, im,x-Engine.fWidth, y-Engine.fHeight);
        
        Calc.drawBackground(g, Images.MENUCOVER.getImage(), 0, 0);
        
    }
    public void init(){}
    public void optionsInit(){
        for(Option option : options){
            option.init();
        }
        options[(int)(index)].active=true;
    }

    public void paint(Graphics g){
        
    };

    public void run() {
        index=(index + options.length + di) % options.length;
        if(abs(index-round(index))<abs(di/2)){
            di = 0;
            index = (int)round(index)%options.length;
            SE.SELECT.play();
        }
        else{
            
        }
        
        for(Option a:options){
            a.active=false;
        }
        if(index==round(index)){
            
            options[(int)(index)].active=true;
        }
        if(di==0&&acting){
            if(type == Type.MAIN)
            {
               Menu menu = options[(int)(index)].action();
               if(menu!=null)
               {
            Engine.thisM =menu;
            Engine.thisM.init();
            
            }
            }
            else
            {
                Menu menu = options[(int)(index)].action();
               if(menu!=null)
               {
            Engine.thisGE.thisM = options[(int)(index)].action();
            Engine.thisGE.thisM.init();
               }
            }
        }
        acting = false;
    }

    public void scrollUp() {
        if(di==0){
        }
        di = dx;
        
            
        //index = (index + options.length + 1) % options.length;
    }

    public void scrollDown() {
        if(di==0){
        }
        di = -dx;
        
            
        //index = (index + options.length - 1) % options.length;
    }
    public void select(){
        if(di==0)
        {
            if(options[(int)(index)].available)
            {
            acting = true;
            SE.CONFIRM.play();
            }
            else{
                SE.INVALID.play();
            }
        }
    }
    public void cancel(){
        SE.CANCEL.play();
        if(type == Type.MAIN)
            {
               if(previous!=null)
               {
            Engine.thisM =previous;
            
            }
            }
            else
            {
               if(previous!=null)
               {
            Engine.thisGE.thisM = previous;
               }
            }
    }

    public void keyPressed(int key) {
            switch (key) {
                case 90:
                    this.select();
                    break;
                case 88:
                    this.cancel();
                    break;
                case 40:
                    this.scrollUp();
                    break;
                case 38:
                    this.scrollDown();
                    break;
                default:
                    break;
            
        
        }

    }

    public void keyReleased(int key) {
        switch (key) {
        }
    }
}
