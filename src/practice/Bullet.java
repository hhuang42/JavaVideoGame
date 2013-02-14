/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import static java.lang.Math.*;
import java.util.*;
import static practice.Calc.*;

/**
 *
 * @author Student
 */
public class Bullet extends Basic {
static public ArrayList Blist = new ArrayList();
static public ArrayList newBlist = new ArrayList();
static final Object BlistLock = new Object();
static final Object newBlistLock = new Object();
public static Col[] colorList = Col.values();


    /*public float x;
    public float y;
    public float xv;
    public float yv;
    public float xa;
    public float ya;*/
    public float w;
    /*public float hitSize;
    float age = 0;*/
    //public float semiWidth;
    //public float semiHeight;
    public int delay;
    //boolean slowing;
    boolean grazed;
    /*boolean dead;*/
    boolean active;
    public int color = 1;
    public int tolerance = 20;
    public Type type = Type.Outlined;
    public Status status = Status.Form;
    static AffineTransform tSetting;
    static AlphaComposite cSetting;
    static float[] position0 = toV(0,0);
    static float[] velocity0 = toV(0,0);
    static float[] acceleration0 = toV(0,0);
    static float w0 = 0;
    static int tolerance0 = 10;
    static SE shoot0 = SE.ESHOOT2;
    static Type type0 = Type.Outlined;
    static int color0 = 1;
    static int delay0 = 10;
    static boolean clearAll = false;
    final static int fadeLength=10;
    static boolean flip0 = false;
    public SE shoot;
    public enum Status{
        Form,
        Active,
        Fade,
        Dead
    }
    public static void init(){
        Blist = new ArrayList();
    newBlist = new ArrayList();

    position0 = toV(0,0);
    velocity0 = toV(0,0);
    acceleration0 = toV(0,0);
    w0 = 0;
    tolerance0 = 20;
    shoot0 = SE.ESHOOT2;
    type0 = Type.Outlined;
    color0 = 1;
    delay0 = 10;
    clearAll = false;
    flip0 = false;
    }
    /*public ArrayList<bEvent> Evlist = new ArrayList<bEvent>();
    public abstract class bEvent extends Event{
        Bullet thisB;
            bEvent(Bullet thisB){
                this.thisB = thisB;
            }
        
    }*/
    
    static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    static GraphicsDevice device = env.getDefaultScreenDevice();
    static GraphicsConfiguration config = device.getDefaultConfiguration();
   

    public static void reset0()
    {
        position0 = toV(0,0);
        velocity0 = toV(0,0);
        acceleration0 = toV(0,0);
        type0 = Type.Outlined;
        color0 = 1;
        delay0 = 10;
        w0 = 0;
        flip0 = false;
        shoot0 = SE.ESHOOT2;
        tolerance0 = 20;
    }
    public void reset()
    {
        position1 ( toV(0,0));
        velocity1 ( toV(0,0));
        acceleration1(toV(0,0));
        type1 ( Type.Outlined);
        color1 ( 1);
        delay = 10;
        w = 0;
        flip0 = false;
        shoot = SE.ESHOOT2;
        tolerance = 20;
    }
    public static void flip0(boolean flip)
    {
        flip0 = flip;
    }
    public static void position0(float[] newposition)
    {
        position0 = newposition;
    }
    public static void velocity0(float[] newvelocity)
    {
        velocity0 = ((flip0)?toV(-newvelocity[0],newvelocity[1]):newvelocity);
    }
    public static void acceleration0(float[] newacceleration)
    {
        acceleration0 = ((flip0)?toV(-newacceleration[0],newacceleration[1]):newacceleration);
    }
    public static void type0(Type newtype)
    {
        type0 = newtype;
    }
    public static void shoot0(SE newSE){
        shoot0 = newSE;
    }
    public void type1(Type newtype)
    {
        type = newtype;
    }
    public static void color0(int newcolor)
    {
        color0=max(min(newcolor,Col.values().length),1);
    }
    public void color1(int newcolor)
    {
        this.color=max(min(newcolor,Col.values().length),1);
    }
    public static void color0(Col newcolor)
    {
        color0 = newcolor.ordinal()+1;
    }
    public void color1(Col newcolor)
    {
        this.color=newcolor.ordinal()+1;
    }
    public static void delay0(int newdelay)
    {
        delay0 = newdelay;
    }
    public static void w0(float neww)
    {
        w0 = neww;
    }
    public void shoot(SE newSE){
        shoot = newSE;
    }
    public static void tolerance0(int newtolerance)
    {
        tolerance0 = newtolerance;
    }
    public void tolerance1(int newtolerance)
    {
        tolerance = newtolerance;
    }

    public Bullet() {
        shoot=shoot0;
        
        grazed=false;
        //dead = false;
        active = true;
        x = position0[0];
        y = position0[1];
        xv = velocity0[0];
        yv = velocity0[1];
        xa = acceleration0[0];
        ya = acceleration0[1];
        type = type0;
        color = color0;
        delay = delay0;
        w=w0;
        tolerance = tolerance0;
        //slowing = false;
        age = 0;
        status = Status.Form;

    }


    public Bullet(float x0, float y0, float xv0, float yv0, float hitSize0) {
        this(Calc.toV(x0, y0), Calc.toV(xv0, yv0), hitSize0, Calc.toV(0, 0), 0, 0);

    }

    public Bullet(float[] position, float[] velocity, float hitSize0) {
        this(position[0], position[1], velocity[0], velocity[1], hitSize0, 0, 0, 0, 0);
    }

    public Bullet(float[] position, float[] velocity, float hitSize0, float[] acceleration, float limitv0, float w0) {
        this(position[0], position[1], velocity[0], velocity[1], hitSize0, acceleration[0], acceleration[1], limitv0, w0);
    }

    public Bullet(float x0, float y0, float xv0, float yv0, float hitSize0, float xa0, float ya0, float limitv0, float w0) {
        x = x0;
        y = y0;
        xv = xv0;
        yv = yv0;
        hitSize = hitSize0;
        xa = xa0;
        ya = ya0;
        w = w0;
        
        grazed = false;
        //dead = false;
        active = true;
        delay = delay0;
        age = 0;

    }
    
    public void setXY(double[] position, double[] velocity, double hitSize0) {
        set((float)position[0], (float)position[1],(float) velocity[0], (float)velocity[1],(float) hitSize0, 0, 0, 0, 0);
    }

    public void setXY(double[] position, double[] velocity, double hitSize0, double[] acceleration, double limitv0, double w0) {
        set((float)position[0],(float) position[1], (float)velocity[0], (float)velocity[1], (float)hitSize0, (float)acceleration[0], (float)acceleration[1], (float)limitv0, (float)w0);
    }

    public void setRT(double[] position, double r, double theta, double hitSize) {
        set((float)position[0],(float) position[1], (float) (r * cos(theta)), (float) (r * sin(theta)), (float) hitSize,0,0,0,0);


    }

    public void setRT(double[] position, double r, double theta, double hitSize, double a, double limitv0, double w) {
        set((float)position[0],(float) position[1], (float) (r * cos(theta)), (float) (r * sin(theta)), (float) hitSize, (float) (a * cos(theta)), (float) (a * sin(theta)), (float) limitv0, (float) w);

    }

    public void set(float x0, float y0, float xv0, float yv0, float hitSize0, float xa0, float ya0, float limitv0, float w0) {
        x = x0;
        y = y0;
        xv = xv0;
        yv = yv0;
        hitSize = hitSize0;
        xa = xa0;
        ya = ya0;
        w = w0;
        grazed = false;
        //dead = false;
        active = true;
        
        delay = delay0;
        age = 0;

    }
    

    static void NewBulletXY(double x, double y, double xv, double yv, double hitSize) {

        Bullet thisB = new Bullet((float) x, (float) y, (float) xv, (float) yv, (float) hitSize);
        synchronized(BlistLock){
        Blist.add(thisB);
        }

    }

    static void NewBulletXY(double x, double y, double xv, double yv, double hitSize, double xa0, double ya0, double limitv0, double w) {

        Bullet thisB = new Bullet((float) x, (float) y, (float) xv, (float) yv, (float) hitSize, (float) xa0, (float) ya0, (float) limitv0, (float) w);
        synchronized(BlistLock){
        Blist.add(thisB);
        }


    }

    static void NewBulletRT(double x, double y, double r, double theta, double hitSize) {
        Bullet thisB = new Bullet((float) x, (float) y, (float) (r * cos(theta)), (float) (r * sin(theta)), (float) hitSize);

        synchronized(BlistLock){
        Blist.add(thisB);
        }

    }

    static void NewBulletRT(double x, double y, double r, double theta, double hitSize, double a, double limitv0, double w) {
        Bullet thisB = new Bullet((float) x, (float) y, (float) (r * cos(theta)), (float) (r * sin(theta)), (float) hitSize, (float) (a * cos(theta)), (float) (a * sin(theta)), (float) limitv0, (float) w);
        synchronized(BlistLock){
        Blist.add(thisB);
        }
    }

    /*static boolean CollideBullet(float x, float y, float hitSize) {
        synchronized(BlistLock){
        ListIterator count = Blist.listIterator();
        boolean hit = false;
        float distance;
        while (count.hasNext()) {
            Bullet thisB = (Bullet) count.next();
            distance = thisB.distance(x, y);
            if (C.hit(distance)) {
                thisB.dead = true;
                hit = true;
            } else if (thisB.grazed == false) {
                if (C.graze(distance)) {
                    thisB.grazed = true;
                }
            }


        }
        return hit;
        }
    }*/

    public void collide(/*float[] position,float radius*/){
        if(Calc.collide(Char.charPosition(), Char.getHitboxSize(), position(), toV(type.SemiWidth,type.SemiHeight), orient()))
        {
            Char.hit();
            status=Status.Fade;
        }
 else
        if(grazed==false&&Calc.collide(Char.charPosition(), Char.getGrazeboxSize(), position(), toV(type.SemiWidth,type.SemiHeight), orient()))
        {
            GameEngine.graze();
            grazed=true;
        }
    }

    void form(){
        if(age>=delay)
                {
                    status=Status.Active;
                }
    }
    void fade(){
        if(age>0)
        {
            age= -fadeLength;
        }
        if(age==0)
        {
            status=Status.Dead;
        }
    }

    void update() {

        /*countage();
        if(age>delay)
        {
        collide();
        }

        move();
        accelerate();
        rotate();*/
        countage();
        switch(status){
            case Form:
                move();
                form();
                break;
            case Active:
                
                collide();
                move();
                if(clearAll)
                {
                    this.status=Status.Fade;
                }
                break;
            case Fade:
                fade();
                break;
            default:
                break;



        }
    }

    static void UpdateBullet() {
        //BufferedImage buffB = config.createCompatibleImage(Engine.scrWidth, Engine.scrHeight, Transparency.TRANSLUCENT);
        //buffB = new BufferedImage(Engine.scrWidth,Engine.scrHeight,BufferedImage.TYPE_4BYTE_ABGR);
        
        /*g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
g2.fillRect(0, 0, Engine.scrWidth, Engine.scrHeight);
g2.setComposite(Calc.opacity(1));*/

        synchronized (BlistLock)
        {
            
            Iterator count = Blist.iterator();

        while (count.hasNext()) {
            Bullet thisB = (Bullet) count.next();

            thisB.update();
           // thisB.draw(g2);

            if (thisB.status==Status.Dead) {

                    count.remove();

                }

            }
        clearAll = false;
        synchronized(newBlistLock)
        {
        Blist.addAll(newBlist);
        newBlist.clear();
        }
        }
        //return buffB;
        /*Object[] tempList = Blist.toArray();
        for(int i=0;i<tempList.length;i++)
        {
            Bullet thisB = (Bullet) tempList[i];
            thisB.update();
            if (thisB.status==Status.Dead) {

                    thisB = null;

                }
        }
        Collection tempC = Arrays.asList(tempList);
        Object[] a = {null};
        tempC.remove(a[0]);
        synchronized(BlistLock){
            Blist.clear();

            Blist.addAll(tempC);
        }*/
    }

    static void ClearAll(){
        Object[] temp;
        //synchronized(BlistLock){
            temp=Blist.toArray();

        //while (count.hasNext()) {
            //Bullet thisB = (Bullet) count.next();
            for(int i=0;i<temp.length;i++)
            {
                Bullet thisB = (Bullet) temp[i];
            thisB.status = Status.Fade;
            }
            
            temp=newBlist.toArray();
            for(int i=0;i<temp.length;i++)
            {
                Bullet thisB = (Bullet) temp[i];
            thisB.status = Status.Fade;
            }

            //}
        //}
    }

    static void RenderBullet(Graphics2D g) {
        Object[] tempList = Blist.toArray();
        /*synchronized (Blistn) {
            ListIterator count = Blist.listIterator();
            while (count.hasNext()) {

                Bullet thisB = (Bullet) count.next();
                 if (false) {

                    count.remove();

                }
 else
                 {
     
     thisB.draw(g);
                }
      

            }
        }*/
        for(int i=0;i<tempList.length;i++)
        {
            Bullet thisB = (Bullet) tempList[i];
            if(thisB!=null)
            {
                
            thisB.draw(g);
            }
        }
        /*synchronized (Bullet.Blist) {
            Bullet.Blist.clear();
            Bullet.Blist.addAll(Arrays.asList(tempList));
        }*/
        //g.drawImage(buffB, null, 0, 0);
        //g.drawImage(buffB, null, 0, 0);
    }

    /*public void countage() {
        age++;
    }*/

    public void add(){
        if(shoot!=null)
        {
            shoot.play();
        }
        synchronized(newBlistLock)
        {
        newBlist.add(this);
        }
    }
    public static void add(Bullet thisB){
        thisB.shoot.play();
        synchronized(BlistLock)
        {
        Blist.add(thisB);
        }
    }
    public void move() {
        
        accelerate();
        
        super.move();
        rotate();
        if (Calc.outBound((float) x, (float) y, tolerance)) {
            status = status.Dead;
        }
        

    }
    public void accelerate() {
        if (xa != 0 || ya != 0) {
            //xv += xa;
            //yv += ya;
            super.accelerate();
            /*if (slowing && (xv * xv + yv * yv < pow(limitv, 2)) || ((!slowing) && (xv * xv + yv * yv > pow(limitv, 2)))) {
                float ratio = (float) (limitv / pow((xv * xv + yv * yv), .5));
                xv = ratio * xv;
                yv = ratio * yv;
                xa = 0;
                ya = 0;
            }*/
        }
    }
    public void rotate(){
        if (w != 0) {
            float xv0 = xv * (float) cos(w) - yv * (float) sin(w);
            float yv0 = yv * (float) cos(w) + xv * (float) sin(w);
            xv = xv0;
            yv = yv0;
        }
    }
    public void changeSpeed(int start, int end, float speed){
        if(age == start){
            //this.acceleration1(Calc.norm(velocity(), (speed-mag(velocity()))/(end-start+1)));
            velocity1(norm(velocity(),mag(velocity())+(speed-mag(velocity()))/(end-start+1)));
        }
        if(age<=end&&age>start)
            velocity1(norm(velocity(),mag(velocity())+(speed-mag(velocity()))/(end-age+1)));
       /* if(age==end+1){
            acceleration1(toV(0,0));
        }*/
    }
    public void changeSpeed(int start, int end, float[] speed){
        if(age == start){
            this.acceleration1(product(Calc.diff(velocity(), speed),1.f/(end-start+1)));
        }
         
        if(age==end+1){
            acceleration1(toV(0,0));
        }
    }
    
    public float distance(float cx, float cy) {

        double distance = (float) pow(pow(x - cx, 2) + pow(y - cy, 2), .5) - hitSize;

        return (float) distance;
    }

    public void draw(Graphics2D g) {
        tSetting = g.getTransform();
     cSetting = (AlphaComposite)g.getComposite();
       /* if (age <= delay) {
           chargeDraw(g);
        } else {
            bulletDraw(g);
        }*/
     switch(status){
         case Form:
             chargeDraw(g);
             break;
         case Active:
             bulletDraw(g);
             break;
         case Fade:
             fadeDraw(g);
             break;
         case Dead:
             break;
         default:
             break;

     }

     g.setTransform(tSetting);
     g.setComposite(cSetting);

    }
    public void chargeDraw(Graphics2D g){
        /*//g.setComposite(Calc.opacity(1-((double)10./(age+10))));
        g.setColor(Color.red);
        
        g.setComposite(Calc.opacity(.05));
        fillCircle(g,x,y,8);
        fillCircle(g,x,y,9);
        fillCircle(g,x,y,10);
        fillCircle(g,x,y,11);
        fillCircle(g,x,y,12);
        fillCircle(g,x,y,7);
        g.setComposite(Calc.opacity(1));
        g.setColor(Color.WHITE);
        fillCircle(g,x,y,6);
        //super.draw(g);*/
        double per = (double)age/(delay+1);
        g.drawImage(
                type.formSprite(color,per),
                null,
                round(x-.5f-type.formSprite(color,per).getWidth()/2),
                round(y-.5f-type.formSprite(color,per).getWidth()/2));
    }
    public void bulletDraw(Graphics2D g){
        g.drawImage(
                type.sprite(color,orient()),
                null,
                round(x-.5f-type.sprite(color,0).getWidth()/2),
                round(y-.5f-type.sprite(color,0).getWidth()/2));
    }
    public void fadeDraw(Graphics2D g){
        /*g.setColor(Color.red);

        g.setComposite(Calc.opacity(.05));
        fillCircle(g,x,y,8);
        fillCircle(g,x,y,9);
        fillCircle(g,x,y,10);
        fillCircle(g,x,y,11);
        fillCircle(g,x,y,12);
        fillCircle(g,x,y,7);
        g.setComposite(Calc.opacity(1));
        g.setColor(Color.WHITE);
        fillCircle(g,x,y,6);
        g.setComposite(Calc.clear());
        fillCircle(g,6,6,12);*/
        g.drawImage(
                type.fadeSprite(color,(double)abs(age)/fadeLength),
                null,
                round(x-.5f-type.fadeSprite(color,(double)abs(age)/fadeLength).getWidth()/2),
                round(y-.5f-type.fadeSprite(color,(double)abs(age)/fadeLength).getWidth()/2));
    }

     


    public enum Type {
        Outlined(formList(5),Outlined(),fadeList(5),toV((float)3,3),false),
        Medium(formList(5),Medium(),fadeList(5),toV((float)2,2),false),
        
        Small(formList(3),Small(),fadeList(3),toV((float)1,1),false),
        Arrow(formList(5),Arrow(),fadeList(5),toV((float)2,2),false),
        Pellet(formList(4),Pellet(),fadeList(4),toV((float)1,2),false),
        
        DarkPellet(formList(4),DarkPellet(),fadeList(4),toV((float)1,2),false),
        Laser(formList(10),Laser(),fadeList(8),toV((float)2,18),false),
        MetalF(formList(10),MetalF(),fadeList(10),toV((float)6,6),false)
                ;
        Bullet thisB;
        final BufferedImage[][] spriteList;
        final BufferedImage[][] fadeList;
        final BufferedImage[][] formList;
        float SemiWidth;
        float SemiHeight;
        boolean doRotate;
        int currentcolor = 1;
        
        static public BufferedImage[][] Laser() {
        int angle = 240;
        double size = 20;
        BufferedImage[][] imageList = new BufferedImage[colorList.length][angle];

        for (int i = 0; i < colorList.length; i++) {
            for (int t = 0; t < angle; t++) {
                BufferedImage thisI = newBImage(2 * size, 2 * size);
                Graphics2D tempg = thisI.createGraphics();
                tempg.rotate(2 * PI / angle * t, size, size);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(colorList[i].c);
                for (double s = 0; s <= 1; s += 1. / 16) {
                    tempg.setComposite(Calc.opacity(.0));

                    fillEllipse(tempg, size+2, size, s * 2., size );
                    fillEllipse(tempg, size-2, size, s * 2., size );
                }
                

                for (double u = 7; u <= size; u += 1) {
                    tempg.setComposite(Calc.opacity(.1));
                    tempg.fillRect((int) (size - (size - u) / 3), (int) (size - u), (int) (2 * (size - u) / 3), (int) (2 * u)-1);
                }
                tempg.setColor(Color.white);
                for (double u = 9; u <= size; u += 1) {
                    tempg.setComposite(Calc.opacity(pow((u - 9) / (size - 9), 2.)));
                    tempg.fillRect((int) (size - (size - u) / 3), (int) (size - u), (int) (2 * (size - u) / 3), (int) (2 * u));
                }
                imageList[i][t] = thisI;
            }
        }
        return imageList;

    }

        
static public BufferedImage[][] MetalF() {
            int angle = 1;
            BufferedImage[][] imageList = new BufferedImage[colorList.length][angle];
            double size = 14;
            for (int i = 0; i < colorList.length; i++) {
                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(size*2, size*2);
                Graphics2D tempg = thisI.createGraphics();
                tempg.rotate(2*PI/angle*t, size, size);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(Color.white);
                fillCircle(tempg,size,size,size);
                tempg.setComposite(opacity(1));
                tempg.setColor(colorList[i].c);
                fillCircle(tempg,size,size,size);
                tempg.setComposite(opacity(.7));

                tempg.setColor(Color.white);
                fillCircle(tempg,size,size,size-1);
                tempg.setComposite(opacity(.7));

                tempg.setColor(Color.white);
                fillCircle(tempg,size,size,size-3);
                //tempg.drawLine(8, 8, 8, 16);
                
                imageList[i][t] = thisI;
                }
            }
            return imageList;

        }
        static public BufferedImage[][] Outlined() {
            int angle = 1;
            BufferedImage[][] imageList = new BufferedImage[colorList.length][angle];

            for (int i = 0; i < colorList.length; i++) {
                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(16, 16);
                Graphics2D tempg = thisI.createGraphics();
                tempg.rotate(2*PI/angle*t, 8, 8);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                tempg.setColor(colorList[i].c);
                
                fillCircle(tempg,8,8,8);
                tempg.setComposite(Calc.clear());
                fillCircle(tempg,8,8,7);
                tempg.setComposite(opacity(1));
                tempg.setColor(colorList[i].c);

                fillCircle(tempg,8,8,6);
                tempg.setColor(Color.white);
                fillCircle(tempg,8,8,(float)5);
                //tempg.drawLine(8, 8, 8, 16);
                
                imageList[i][t] = thisI;
                }
            }
            return imageList;

        }
        static public BufferedImage[][] Medium() {
            int angle = 1;
            BufferedImage[][] imageList = new BufferedImage[colorList.length][angle];

            for (int i = 0; i < colorList.length; i++) {
                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(16, 16);
                Graphics2D tempg = thisI.createGraphics();
                tempg.rotate(2*PI/angle*t, 8, 8);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                tempg.setComposite(opacity(1));
                tempg.setColor(colorList[i].c);

                fillCircle(tempg,8,8,7);
                tempg.setColor(Color.white);
                fillCircle(tempg,8,8,5);
                //tempg.drawLine(8, 8, 8, 16);
                
                imageList[i][t] = thisI;
                }
            }
            return imageList;

        }
        static public BufferedImage[][] Small() {
            int angle = 1;
            BufferedImage[][] imageList = new BufferedImage[colorList.length][angle];

            for (int i = 0; i < colorList.length; i++) {
                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(10, 10);
                Graphics2D tempg = thisI.createGraphics();
                tempg.rotate(2*PI/angle*t, 5, 5);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                tempg.setComposite(opacity(1));
                tempg.setColor(colorList[i].c);

                fillCircle(tempg,5,5,4);
                tempg.setColor(Color.white);
                fillCircle(tempg,5,5,3);
                //tempg.drawLine(8, 8, 8, 16);
                
                imageList[i][t] = thisI;
                }
            }
            return imageList;

        }
                static public BufferedImage[][] Arrow() {
                    int angle = 120;
            BufferedImage[][] imageList = new BufferedImage[colorList.length][angle];

            for (int i = 0; i < colorList.length; i++) {
                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(18, 18);
                Graphics2D tempg = thisI.createGraphics();
                tempg.rotate(2*PI/angle*t, 9, 9);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(colorList[i].c);
                tempg.setComposite(Calc.opacity(.9));
                fillEllipse(tempg,10,13,6,12.5,0,PI);
                tempg.setColor(Color.white);
                tempg.setComposite(Calc.opacity(.9));
                fillEllipse(tempg,10,13,6,11.5,0,PI);
                tempg.setColor(colorList[i].c);
                tempg.setComposite(Calc.opacity(1));
                fillEllipse(tempg,10,13,4,9.5,0,PI);
                tempg.setComposite(Calc.clear());
                fillEllipse(tempg,10,14,5,10,PI,2*PI);
                tempg.setComposite(Calc.opacity(1));
                tempg.setColor(colorList[i].c);
                fillEllipse(tempg,10,10,4,5,-PI/4,3*PI/2);
                
                tempg.setComposite(Calc.opacity(.0));
                tempg.setColor(colorList[i].c);
                fillEllipse(tempg,10,9,3,5);
                tempg.setComposite(Calc.opacity(1));
                tempg.setColor(Color.white);
                fillEllipse(tempg,10,10.5,2,3.5);
                imageList[i][t] = thisI;
                }
            }
            return imageList;

        }
                static public BufferedImage[][] Pellet() {
                    int angle = 120;
            BufferedImage[][] imageList = new BufferedImage[colorList.length][angle];

            for (int i = 0; i < colorList.length; i++) {
                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(20, 20);
                Graphics2D tempg = thisI.createGraphics();
                tempg.rotate(2*PI/angle*t, 10, 10);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(colorList[i].c);
                fillEllipse(tempg,10,10,3,5);
                tempg.setColor(Color.white);
                fillEllipse(tempg,10,10,2,4);
                imageList[i][t] = thisI;
                }
            }
            return imageList;

        }
                
                
                static public BufferedImage[][] DarkPellet() {
                    int angle = 120;
            BufferedImage[][] imageList = new BufferedImage[colorList.length][angle];

            for (int i = 0; i < colorList.length; i++) {
                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(20, 20);
                Graphics2D tempg = thisI.createGraphics();
                tempg.rotate(2*PI/angle*t, 10, 10);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                tempg.setComposite(Calc.opacity(.2));
                tempg.setColor(Color.WHITE);
                
                fillEllipse(tempg,10,10,4,6);
                
                tempg.setComposite(Calc.opacity(1));
                tempg.setColor(colorList[i].c);
                fillEllipse(tempg,10,10,3,5);
                tempg.setComposite(Calc.opacity(.5));
                tempg.setColor(Color.BLACK);
                fillEllipse(tempg,10,10,2,4);
                tempg.setComposite(Calc.opacity(1));
                tempg.setColor(Color.BLACK);
                fillEllipse(tempg,10,10,1,3);
                imageList[i][t] = thisI;
                }
            }
            return imageList;

        }
                static public BufferedImage[][] fadeList(int r) {
                    int fading=20;
                    BufferedImage[][] imageList = new BufferedImage[colorList.length][fading];
            for (int i = 0; i < colorList.length; i++) {
                for(int n = 0;n<fading;n++)
  {
                int u = r+r*n/fading;
                BufferedImage thisI = newBImage((u+4)*2, (u+4)*2);
                Graphics2D tempg = thisI.createGraphics();
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(colorList[i].c);
                tempg.setComposite(Calc.opacity(1.-(double)n/fading));
                fillCircle(tempg, u+4, u+4, u+4);
                tempg.setComposite(Calc.opacity(1));
                tempg.setComposite(Calc.clear());
                fillCircle(tempg, u+4, u+4, u+2);
                tempg.setComposite(Calc.opacity(1.-(double)n/fading));
                tempg.setColor(Color.WHITE);
                fillCircle(tempg, u+4, u+4, u+2);
                tempg.setComposite(Calc.opacity(1));
                tempg.setComposite(Calc.clear());
                fillCircle(tempg, u+4, u+4, u);
                imageList[i][fading-n-1]=thisI;
                }
            }
                    return imageList;
        }
                static public BufferedImage[][] formList(int r) {
                    int forming=20;
                    BufferedImage[][] imageList = new BufferedImage[colorList.length][forming];
            for (int i = 0; i < colorList.length; i++) {
                for(int n = 0;n<forming;n++)
  {
                int u = r+r*n/forming;
                BufferedImage thisI = newBImage((u+6)*2, (u+6)*2);
                Graphics2D tempg = thisI.createGraphics();
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(colorList[i].c);
                tempg.setComposite(Calc.opacity((1.-(double)n/forming)*.3));
                fillCircle(tempg, u+6, u+6, u+4);
                tempg.setComposite(Calc.opacity(1));
                tempg.setComposite(Calc.clear());
                fillCircle(tempg, u+6, u+6, u+2);
                tempg.setComposite(Calc.opacity((1.-(double)n/forming)*.6));
                tempg.setColor(Color.WHITE);
                fillCircle(tempg, u+6, u+6, u+2);
                tempg.setColor(colorList[i].c);
                tempg.setComposite(Calc.opacity((1.-(double)n/forming)*.1));
                fillCircle(tempg, u+6, u+6, u+6);
                //tempg.setComposite(Calc.clear());
                //fillCircle(tempg, u+4, u+4, u);
                imageList[i][forming-n-1]=thisI;
                }
            }
                    return imageList;
        }
        Type(BufferedImage[][] formList,BufferedImage[][] spriteList,BufferedImage[][] fadeList,float[] hitWidHei,boolean doRotate) {
            this.spriteList=spriteList;
            SemiWidth=hitWidHei[0];
            SemiHeight=hitWidHei[1];
            this.doRotate=doRotate;
            this.fadeList=fadeList;
            this.formList=formList;
        }

        public void SetColor(int i)
        {
            currentcolor = max(min(i,colorList.length),0);
        }
        public BufferedImage sprite(int color, double theta){
            int angle=spriteList[0].length;
            return spriteList[(color-1)%colorList.length][(int)((theta/(2*PI)*angle)+angle)%angle];
        }

       public BufferedImage formSprite(int color,double form){
            return formList[(color-1)%colorList.length][(int)(formList[0].length*(form%1))];
        }
       public BufferedImage fadeSprite(int color,double fade){
            return fadeList[(color-1)%colorList.length][(int)(fadeList[0].length*(fade%1))];
        }


        public void draw(float[] position, float orientation){};
    }
};
