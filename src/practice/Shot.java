/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.image.*;
import java.awt.geom.*;
import java.awt.*;
import java.util.*;
import static java.lang.Math.*;
import static practice.Calc.*;

/**
 *
 * @author Student
 */
public class Shot extends Basic {

    /*float x;
    float y;
    float xv;
    float yv;
    float xa;
    float ya;*/
    float power;
    float semiHeight;
    float semiWidth;
    int hits;
    //boolean dead;
    public static ArrayList Slist = new ArrayList();
    public static ArrayList BufferList = new ArrayList();
    static AffineTransform tSetting;
    static AlphaComposite cSetting;
    final static BufferedImage[] defaultSpriteList = spriteList();
    static BufferedImage[] spriteList0 = defaultSpriteList;
    BufferedImage[] spriteList = spriteList0;
    static float[] position0 = toV(0,0);
    static float[] velocity0 = toV(0,0);
    static float[] acceleration0 = toV(0,0);
    static float[] semiDim0 = toV(5,10);
    static int hits0 = 1;
    static float power0 = 1;
    public static void init(){
        Slist = new ArrayList();
    BufferList = new ArrayList();
    position0 = toV(0,0);
    velocity0 = toV(0,0);
    acceleration0 = toV(0,0);
    semiDim0 = toV(5,10);
    hits0 = 1;
    power0 = 1;
    }
    Shot(){
        x = position0[0];
        y = position0[1];
        xv = velocity0[0];
        yv = velocity0[1];
        xa = acceleration0[0];
        ya = acceleration0[1];
        semiWidth = semiDim0[0];
        semiHeight = semiDim0[1];
        hits = hits0;
        power = power0;
        status = status.Active;
    }
    public static void reset0()
    {
        position0 = toV(0,0);
        velocity0 = toV(0,0);
        acceleration0 = toV(0,0);
        spriteList0 = defaultSpriteList;
        semiDim0 = toV(5,10);
        hits0 = 1;
        power0 = 1;
    }
    public static void position0(float[] newposition)
    {
        position0 = newposition;
    }
    public static void velocity0(float[] newvelocity)
    {
        velocity0 = newvelocity;
    }
    public static void acceleration0(float[] newacceleration)
    {
        acceleration0 = newacceleration;
    }
    public static void spriteList0(BufferedImage[] newspriteList)
    {
        spriteList0 = newspriteList;
    }
    public static void semiDim0(float[] newsemiDim)
    {
        semiDim0 = newsemiDim;
    }
    public static void hits0(int newhits)
    {
        hits0 = newhits;
    }
    public static void power0(float newpower)
    {
        power0 = newpower;
    }
    
    public void spriteList1(BufferedImage[] newspriteList)
    {
        spriteList = newspriteList;
    }
    public void semiDim1(float[] newsemiDim)
    {
        semiWidth = newsemiDim[0];
        semiHeight = newsemiDim[1];
    }
    public void hits1(int newhits)
    {
        hits = newhits;
    }
    public void power1(float newpower)
    {
        power = newpower;
    }


    Shot(double[] position, double[] velocity, double[] accel, double power, double[] dimension, int hits) {
        x = (float) position[0];
        y = (float) position[1];
        xv = (float) velocity[0];
        yv = (float) velocity[1];
        xa = (float) accel[0];
        ya = (float) accel[1];
        this.power = (float) power;
        semiWidth = (float) dimension[0];
        semiHeight = (float) dimension[1];
        this.hits=hits;
        //dead = false;
        spriteList = spriteList0;
        status = status.Active;

    }

    Shot(double[] position, double[] velocity, double power, double[] dimension, int hits) {
        this(position, velocity, Calc.toV((double) 0, (double) 0), power, dimension, hits);
    }

    Shot(double[] position, double[] velocity, double power, double[] dimension) {
        this(position, velocity, power, dimension, 1);
    }

    Shot(double[] position, double[] velocity, double[] accel, double power, double[] dimension) {
        this(position, velocity, accel, power, dimension, 1);
    }

    public void add() {
        synchronized (BufferList) {

            BufferList.add(this);
        }

    }
    static public void UpdateShot() {
        synchronized (Slist)
        {
        Iterator count = Slist.iterator();

        while (count.hasNext()) {
            Shot thisS = (Shot) count.next();

            thisS.update();
            if (thisS.status == Status.Dead) {

                    count.remove();

                }

            }
        synchronized(BufferList){
        Slist.addAll(BufferList);
        BufferList.clear();
            }
        }

    }

    static void RenderShot(Graphics2D g) {
        Object[] tempList;
        /*synchronized(Slist){
        Slistn= Slist;
    }*/
        tempList = Slist.toArray();
        //synchronized (Slistn) {
         //   Iterator count = Slistn.iterator();
            for (int i = 0;i<tempList.length;i++) {

                Shot thisS = (Shot) tempList[i];
                 //if (thisS.status==Status.Dead) {

                 //   count.remove();

               // }
 //else
     tSetting = g.getTransform();
     cSetting = (AlphaComposite)g.getComposite();
     if(thisS!=null)
     {
     thisS.draw(g);
     }
     g.setTransform(tSetting);
     g.setComposite(cSetting);
 


            }
       // }

    }

    public void move() {
        super.move();
        if (Calc.outBound((float) x, (float) y, max(max(semiHeight, semiWidth),50))) {
            status = Status.Dead;
        }
    }

    public void accelerate() {
        super.accelerate();
    }

    public float orient() {
        return (float) (atan2(yv, xv) + PI / 2);
    }
    public boolean checkHit(Enemy thisE)
    {
        return Calc.collide(toV(thisE.x,thisE.y), thisE.hitSize, this.position(), toV(semiWidth,semiHeight), this.orient());
    }

    public void collide() {
        //float[] distance;
        Object[] tempList=Enemy.Elist.toArray();
        synchronized (Enemy.Elist) {
            //Iterator count = Enemy.Elist.iterator();
            for (int i = 0;i<tempList.length && hits != 0;i++) {
                Enemy thisE = (Enemy) tempList[i];
                /*float[] distance = {x - thisE.x, y - thisE.y};
                distance = Calc.rotate(distance, -orient());
                distance[0] = distance[0] * (height + thisE.hitsize);
                distance[1] = distance[1] * (width + thisE.hitsize);*/

                if(thisE==null)
                    System.out.println(4);
                if(thisE!=null)
                if(thisE.status==Status.Active)
                {
                if (/*Calc.distanceSquare(distance) <= pow((height + thisE.hitsize)*(width + thisE.hitsize),2)*/
                        checkHit(thisE)) {
                    //Engine.junk=hits;
                    hits--;
                    thisE.takeDamage(power);

                }
                }
            }
        }
        if(hits==0)
        {
            status = Status.Dead;
        }
    }

    public void update() {
        countage();
        shotAction();
        move();
        accelerate();
        collide();

    }
    public void shotAction()
    {}

    public void draw(Graphics2D g) {
        
        
        /*g.setColor(Color.pink);
        g.rotate(orient(),x,y);
        g.setComposite(Calc.opacity(.4));
        g.fillOval(
                round(x - semiWidth),
                round(y - semiHeight),
                round(2 * semiWidth),
                round(2 * semiHeight));*/
        g.drawImage(
                sprite(orient()),
                null,
                round(x-sprite(0).getWidth()/2),
                round(y-sprite(0).getWidth()/2));

        //g.rotate(PI/32,x,y);
        
    }
    public BufferedImage sprite(double theta){
            int angle=spriteList.length;
            return spriteList[(int)((theta/(2*PI)*angle)+angle)%angle];
        }
    public static BufferedImage[] spriteList(){
                    int angle = 60;
            BufferedImage[] imageList = new BufferedImage[angle];

            
                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(20, 20);
                Graphics2D tempg = thisI.createGraphics();
                tempg.setComposite(Calc.opacity(.6));
                tempg.rotate(2*PI/angle*t, 10, 10);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(new Color(200,50,255));
                fillEllipse(tempg,10,10,5,10);
                
                imageList[t] = thisI;
                }
            return imageList;
    }
}
