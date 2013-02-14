/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package practice;
import java.awt.*;
import java.util.*;
import static java.lang.Math.*;
import static practice.Calc.*;

/**
 *
 * @author Student
 */
public class Basic {
    
    public static class BasicScript extends Script{
        public Basic basic;
        public void run(){
            super.run();
        }
        public void setBasic(Basic basic){
            this.basic = basic;
        }
        
    }



    public float x;
    public float y;
    public float xv;
    public float yv;
    public float xa;
    public float ya;
    public float w;
    public int age;
    float hitSize;
    public static GameEngine thisGE;
   // boolean dead;
    public Status status = Status.Active;
    public BasicScript thisS = null;
    
    public static void init(){
        
    }
    
    public Basic(BasicScript thisS){
        this.thisS=thisS;
        this.thisS.basic=this;
        this.thisS.init();
    }
    
    public Basic(){
        this.thisS = null;
    }
    
    public void position1(float[] newposition)
    {
        x = newposition[0];
        y = newposition[1];
    }
    public void velocity1(float[] newvelocity)
    {
        xv = newvelocity[0];
        yv = newvelocity[1];
    }
    public void acceleration1(float[] newacceleration)
    {

        xa = newacceleration[0];
        ya = newacceleration[1];
    }
    public void w1(float neww)
    {
        w = neww;
    }

    public void run() {
        while (status!=Status.Dead)
        {
            if(thisS!=null)
            {
                thisS.run();
            }
            update();
            
        }
    }
    public enum Status{
        Form,
        Active,
        Fade,
        Dead
    }
    void update(){
        move();
    }

    void countage() {
        age++;
    }
    public float orient(){
        if(yv==xv&&xv==0)
        {
            return (float) (atan2(ya, xa) + PI / 2);
        }
 else
        {
        return (float) (atan2(yv, xv) + PI / 2);
        }
    }

    void move() {
        x += (float) xv;
        y += (float) yv;

    }

    void accelerate() {
            xv += xa;
            yv += ya;


        
    }


    void turn() {
        velocity1(rotate(velocity(),w));

    }
    void draw(Graphics2D g) {
        g.fillOval(
                round(x - hitSize),
                round(y - hitSize),
                round(2 * hitSize),
                round(2 * hitSize));

    }
    public float[] position(){
        return toV(x,y);
    }
    public  float[] velocity(){
        return toV(xv,yv);
    }
    public float[] acceleration(){
        return toV(xa,ya);
    }
    public float getAngle(float[] position){
        return (float)atan2(y-position[1],x-position[0]);
    }

}
