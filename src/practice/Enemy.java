/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.image.*;
import java.awt.*;
import static java.lang.Math.*;
import java.util.*;
import static practice.Calc.*;
import static practice.Item.Type.*;

public class Enemy extends Basic {

    /*static final Color[] colorList = {
    Color.red, Color.blue,Color.yellow,Color.green};*/


    public static void CreateEnemy(Move move, int type) {
        synchronized(Elist){
        switch (type) {
            case 7:

                Enemy.G typeG = new Enemy.G(move);
                Elist.add(typeG);
                break;
            case 6:

                Enemy.F typeF = new Enemy.F(move);
                Elist.add(typeF);
                break;

            case 5:
                Enemy.E typeE = new Enemy.E(move);
                Elist.add(typeE);
                break;
            case 4:
                Enemy.D typeD = new Enemy.D(move);
                Elist.add(typeD);
                break;

            case 3:
                Enemy.C typeC = new Enemy.C(move);
                Elist.add(typeC);
                break;
            case 2:
                Enemy.B typeB = new Enemy.B(move);
                Elist.add(typeB);
                break;
            case 1:
                Enemy.A typeA = new Enemy.A(move);
                Elist.add(typeA);
                break;
            default:
                Enemy base = new Enemy(move);
                Elist.add(base);
                break;
        }
        }
    }

    public static void UpdateEnemy() {
        //System.out.println(Engine.script.thisT.getState());
        synchronized(Elist){
        Iterator count = Elist.iterator();
        while (count.hasNext()) {
            Enemy thisE = (Enemy) count.next();
            thisE.update();
if (thisE.status==Status.Dead) {
                count.remove();
            }
        }
        }

    }

    public static void RenderEnemy(Graphics2D g) {
        synchronized(Elist){
        Iterator count = Elist.iterator();
        while (count.hasNext()) {
            Enemy thisE = (Enemy) count.next();
             
                thisE.draw(g);
            }
        }
    }

    public static class G extends Enemy{
        public G(Move move){
            super(move);
            HP=80;
        }
        public void shoot()
        {
            Bullet.reset0();
            Bullet.position0(toV(this.x,this.y));
            Bullet.type0(Bullet.Type.Pellet);
            //Bullet.acceleration0(toV(0,(float).01));
            if(age%60==0&&age>0)
            {
                

                    

                    for(double i = 0;i<12./12;i+=1/12.)
  {
                        Bullet.velocity0(toXY(toV((float)(1),(float)(9*age*PI/60/8.+2*PI*i+PI/48*(age/90)))));
                    if(i%(1./3)==0)
                    {
                        Bullet.color0(5);
                    Bullet thisB = new Bullet(){



                        void update()
                        {
                            super.update();
                            if(age==60)
                            {
                                xa = (float)(.010*cos(toAngle(toD(position()),toD(Char.charPosition()))));
                                ya = (float)(.010*sin(toAngle(toD(position()),toD(Char.charPosition()))));
                                xv = 0;
                                yv = 0;
                            }
                        }

                    };
                    thisB.add();
                }
 else
                    {
                    Bullet.color0(4);
                    Bullet thisB = new Bullet();
                    thisB.add();
                }
                }
            }
        }
    }

    public static class F extends Enemy{
        public F(Move move){
            super(move);
            HP=120;
        }
        public void shoot()
        {
            Bullet.reset0();
            Bullet.position0(toV(this.x,this.y));
            
            if(age%70==0&&age>0)
            {
            for(float i=0;i<1;i+=1./8)
            {
                Bullet.color0((int)(random()*7)+1);
                //Bullet.NewBulletRT(x,y,(float)2.0+.2*sin((float)age/120*2*PI),(float)i*2*PI+((float)age/8),3);
                //Bullet.NewBulletRT(x,y,(float)1.8,(float)i*2*PI+((float)age/50),3);
                //Bullet.NewBulletRT(x,y,(float)2.5,(float)i*2*PI+((float)cos(age/360*2*PI)*PI/8),3);
                for(float s=0;s<2;s+=1/3.)
                    {
                    if(age%140==0)
                    {
                    Bullet.velocity0(toXY(toV((float)(1+s/2),(float)(i*2*PI+s*PI/8.+PI/48*(age/90)))));
                        }
 else
                    {
                        Bullet.velocity0(toXY(toV((float)(1+s/2),(float)(i*2*PI-s*PI/8.+PI/48*(age/90)))));
 }
                    Bullet.type0(Bullet.Type.Arrow);
                    new Bullet().add();
                    
                //Bullet.NewBulletRT(x,y,(float)2.+1.5*s,(float)i*2*PI+s*PI/8.+PI/8,3);
                }
            }
            }
        }
    }

    public static class E extends Enemy{
        public E(Move move){
            super(move);
            HP=40;
        }
        float count = 4;
        public void shoot()
        {
            Bullet.reset0();
            Bullet.position0(toV(x,y));
            Bullet.color0(1);
            Bullet.type0(Bullet.Type.Arrow);
            if(age>=60&&age%10==0&&age<=100)
            {
            for(float i=-count;i<=count;i+=2)
            {
                
                Bullet.velocity0(toXY(toF(toV(3./(abs(count/3.)+1),Char.getAngle(x, y)+i*PI/60))));
                new Bullet().add();
                //Bullet.NewBulletRT(x,y,(float)4/(abs(count/2)+1),(float)Char.getAngle(x, y)+i*PI/60,3);
            }
            count--;
            }
        }
    }

    public static class D extends Enemy{
        public D(Move move){
            super(move);
            HP=240;
        }
        float[] aim;
        public void shoot()
        {
            Bullet.reset0();
            Bullet.position0(toV(x,y));
            Bullet.color0(6);
            if(age==60)
            {
                aim=Char.charPosition();
            }
            if(age>=60&&age%15==0&&age<=240)
            {
            for(float i=-2;i<=2;i+=(2./2))
            {
                Bullet.velocity0(toXY(toF(toV(.25+5*pow(age/240,2),toAngle(toD(toV(x,y)),toD(aim))+i*PI/6))));
                new Bullet().add();
                //Bullet.NewBulletRT(x,y,.25+5*pow(age/240,2),(float)toAngle(toV((double)x,y),toD(aim))+i*PI/6,3);
                //Bullet.NewBulletRT(x,y,4.0,(float)toAngle(toV((double)x,y),toD(aim))+i*PI/6,2);
            }
            }
        }
    }
    public static class C extends Enemy{
        public C(Move move){
            super(move);
            HP=600;
        }
        public void shoot()
        {
            if(age>=60&&age%48==0)
            {
            for(float i=0;i<1;i+=(1./6))
            {
                Bullet.NewBulletRT(x,y,1.,(float)Char.getAngle(x, y)+i*2*PI+0*age*PI/180+PI/6,2);
                Bullet.NewBulletRT(x,y,2.0,(float)Char.getAngle(x, y)+i*2*PI+0*age*PI/180,2);
                //Bullet.NewBulletRT(x,y,(float)2.5,-PI/2+i*pow(age,2)*.000+i*age*.03,2);
            }
            }
        }
    }

    public static class B extends Enemy{
        public B(Move move) {
            super(move);
            HP = 40;
        }
        float aim=0;
        public void shoot()
        {
            cycle=(age>300)?0:age%100;
            if(cycle==120&&cycle>=100)
            {
                
            }
            if(cycle==80)
            {
                for(int s=1;s<10;s++)
                {
                    Bullet.reset0();
                    Bullet.color0(4);
                    Bullet.type0(Bullet.Type.Arrow);
                    Bullet.position0(position());
                    Bullet.velocity0(toXY(toF(toV(1.5+s/5., Char.getAngle(x, y)))));
                    //Bullet.NewBulletRT(x, y, 1.5+s/5., Char.getAngle(x, y), 2);
                    new Bullet().add();

                }
                for(float theta = -1;theta<=1;theta+=2./8)
                {
                    Bullet.reset0();
                    Bullet.color0(4);
                    Bullet.type0(Bullet.Type.Outlined);
                    Bullet.position0(position());

                    if(abs(theta)>=.3)
                    {
                        Bullet.velocity0(toXY(toF(toV(2.0, Char.getAngle(x, y)+PI/5*(theta)))));
                        new Bullet().add();
                        Bullet.velocity0(toXY(toF(toV(1.0, Char.getAngle(x, y)+PI/5*(theta)))));
                        new Bullet().add();
                    //Bullet.NewBulletRT(x, y,2.0, Char.getAngle(x, y)+PI/5*(theta), 2);
                    //Bullet.NewBulletRT(x, y,1.0, Char.getAngle(x, y)+PI/5*(theta), 2);
                    }
                }

                
            }
        }
    }

    public static class A extends Enemy {
        int count=0;

        public A(Move move) {
            super(move);
            HP=2100;
            
        }

        Bullet currentB()
        {
            return new EnemyBullet();
        }

        @Override
        public void shoot() {
            double[] position = {x,y};
            Bullet.reset0();
            Bullet.type0(Bullet.Type.Arrow);
            Bullet.color0((int)(count+1));
            if ((age+6)%12  == 0 && /*(age+15)%30<=12*/ age-6 >= 90&&age-6<900) {
                for (int theta = 0; theta < 20; theta++) {
                    BulletRT(toF(position), 3, (float)(2 * PI * theta / 20 + age*-.002), 2,0,0,-.08);

                }
            }
            if (age%12 == 0&&/* age%30<=12 &&*/ age >= 90&&age<900) {
                for (int theta = 0; theta < 20; theta++) {
                    BulletRT(toF(position), 3,(float)( 2 * PI * theta / 20 - age*-.002), 2,0,0,.08);
                }
            }
            if(age%60==30&&age>90)
            {
                count = (count+1)%Bullet.colorList.length;
            }
        }

        public static class EnemyBullet extends Bullet
        {
            

            /*@Override*/
            void update()
            {
                super.update();
                if(age==40)
                {
                    w=-w;
                }
                if(age==80)
                {
                    w=0;
                    xv=xv*2/3;
                    yv=yv*2/3;

                }

            }


           /* static void NewBulletRT(double[] position, double r, double theta, double hitSize, double a, double limitv0, double w) {
                EnemyBullet thisB = new EnemyBullet();
                thisB.setRT(position, r, theta, 2, 0, 0, w);

                synchronized (Bullet.Blist) {
                    
                    Blist.add(thisB);
                }
            }*/
        }
    }

    //public static class Base {


    public static ArrayList<Enemy> Elist = new ArrayList<Enemy>();
    public static ArrayList<Enemy> newElist = new ArrayList<Enemy>();
        public float HP=100;
        public float hitSize=10;
        public float cycle;
        public int mtime = 1;
        public int mstep = 0;
        public int fade = 15;
        public static Item.Type[] itemList0 = {};
        public Item.Type[] itemList = itemList0;
        //boolean dead;
        //Status status = Status.Active;
        public float[][] hermiteData = new float[2][2];
        public float[] shift = new float[2];
        public float[] flip = toV(1,1);
        public ArrayList<Loop> loopList = new ArrayList<Loop>();
        public ColorType color = (random()<.5?ColorType.Green:ColorType.Yellow);

        //Object[][] bulletS;
        public Move move;
        static BufferedImage[][] sprites = sprites();
        static BufferedImage[][] fadesprites = fadeSprites();
        public static void init(){
            
    Elist = new ArrayList<Enemy>();
    newElist = new ArrayList<Enemy>();
        }
        public enum ColorType{
            Red(new Color(255,50,50)),
            Blue(Color.blue),
            Green(Color.green),
            Yellow(Color.yellow);
            Color thisC;

            ColorType(Color thisC){
                this.thisC = thisC;
            }
            public Color getC(){
                return thisC;
            }

        }
        //final Bullet currentB = new Bullet();

        static void BulletXY(float[] position, float[] velocity, float hitSize) {

        Bullet thisB = new Bullet(position, velocity, hitSize);
        synchronized(Bullet.Blist){
        Bullet.Blist.add(thisB);
        }

    }
        public Enemy(){
            status = Status.Active;
        }
        
        public static void itemList0(Item.Type[] itemList){
            itemList0 = itemList;
        }
        
        public static void itemList0(Item.Type item){
            itemList0 = new Item.Type[1];
            itemList0[0] = item;
        }

     public void BulletRT(float[] position, float r, float theta, double hitSize, double a, double limitv0, double w) {
        Bullet thisB = new Bullet();

                thisB.setXY(
                        Calc.toD(position),
                Calc.toV( (r * cos(theta)),  (r * sin(theta))),
                (float) hitSize,
                Calc.toV( (a * cos(theta)),  (a * sin(theta))),
                (float) limitv0,
                (float) w);
        synchronized(Bullet.Blist){
        Bullet.Blist.add(thisB);

        }
    }

        public static class EnemyBullet extends Bullet
        {
            @Override
            void update()
            {
                if(age==60)
                {
                    status=Status.Dead;
                }
            }

        }

        public Enemy(Move move) {
            this.move = move;
            status = Status.Active;
        }


        public void add(){
            //position1(this.move.update());
            //System.out.println(this.status);
            synchronized(Elist)
            {
            Elist.add(this);
            }
        }

        public void shoot() {
            synchronized(Bullet.Blist){
            if (age == 120) {
                for (int theta = -2; theta <= 2; theta++) {
                    for (float speed = 1; speed <= 3; speed += 1) {
                        EnemyBullet.NewBulletRT(x, y, 3 / speed, Char.getAngle(x, y) + theta * PI / 4, 2);
                    }
                }
            }
            }
        }
        public float[] adjust(float[] location){
            return sum(product(location,flip),shift);
        }
        public void moveToH(final int duration, final float[] location, final float[] tangent) {

        this.move =
                new Move(this) {
                    
                    float[] position1 = adjust(location);
                    float[] tangent1 = product(tangent,flip);
                    float[] position0 = position();
                    float[] tangent0 = velocity();

                    public void action() {
                        super.action();
                        if (age <= duration) {
                            thisB.velocity1(
                                    diff(
                                    position(),
                                    Calc.hermiteT(
                                    position0,
                                    position1,
                                    product(tangent0, duration),
                                    product(tangent1, duration),
                                    (double) (age) / duration)));
                        }
                    }
                };
    }

    ;
        public void moveToH(int duration, float[] location){
            moveToH(duration,location,toV(0,0));
        };
        public void moveTo(final int duration,final float[] location){
            this.move = new Move(this){
                
                float[] position1 = adjust(location);
                float[] position0 = position();
                
                public void action(){
                    thisB.acceleration1(toV(0,0));
                    thisB.velocity1(product(diff(position0,position1),1f/duration));
                }
            };
        };
        public void stop(int end){
            if(age==end){
                velocity1(toV(0,0));
                acceleration1(toV(0,0));
                move= null;
            }
            
        }
        public void stop(){
                velocity1(toV(0,0));
                acceleration1(toV(0,0));
                move= null;
            
        }
        
        static void ClearAll(){
        Object[] temp;
        //synchronized(BlistLock){
            temp=Elist.toArray();

        //while (count.hasNext()) {
            //Bullet thisB = (Bullet) count.next();
            for(int i=0;i<temp.length;i++)
            {
                Enemy thisE = (Enemy) temp[i];
                thisE.toFade();
            }

            //}
        //}
    }

        public float getDistanceSquared(double x, double y){
            return (float)(pow(x-this.x,2)+pow(y-this.y,2));
        }

        public void takeDamage(double power){
            HP-=power;
        }
        public void deathAction(){}

        public void checkDead()
        {
            if (Calc.outBound(x, y, 50)) {
                status=Status.Dead;
            }
            if(HP<=0){
                toFade();
            }
        }

        public void update() {
            countage();
            switch(status){
                case Active:
                    active();
                    break;
                case Fade:
                    fade();
                    break;
                default:
                    break;

            }

        }
        public void active(){
            moveScript();
            move();
            
            collide();
            shoot();
            checkDead();

        }
        public void fade(){
            if(age==0)
            {
                status=status.Dead;
            }
        }
        public void toFade(){
            status = status.Fade;
            age = -fade;
            SE.POP.play();
            deathAction();
            drop();
        }
        public void drop(){
           /* new Item(position(),
                    (random()<.98)?(
                    (random()<.5)?Item.Type.Power:Item.Type.Point):
                        (
                        (random()<.5)?Item.Type.LPower:Item.Type.Bomb)
                        ).add();*/
            float blah = Calc.randomFloat(0, 2*(float)PI);
            for(int i = 0;i< itemList.length;i++){
                double angle = 2*PI*i/itemList.length+blah;
                new Item(
                        toF(toV(
                        x+(2+3*itemList.length)*sin(angle),
                        y+(2+3*itemList.length)*cos(angle))),
                        itemList[i]
                        ).add();
            }
        }
        public void collide(){
            if(Calc.collide(Char.charPosition(), Char.getHitboxSize(), position(), hitSize))
        {
            Char.hit();
        }
        }
        public Enemy flip(){
          //  move.reflect(x);
            return this;
        }
        public Enemy flip(int axis){
          //  move.reflect(x);
            x=2*axis-x;
            return this;
        }
        public void moveScript(){};
        public void move(){
            /*if (mstep < moveS.length - 1) {
                if (moveS[mstep][2] >= mtime) {
                    mtime++;


                } else {
                    mstep++;
                    mtime = 1;
                    xv = (moveS[mstep][0] - x) / moveS[mstep][2];
                    yv = (moveS[mstep][1] - y) / moveS[mstep][2];
                }
            }
            x += xv;
            y += yv;*/
           // float[] aimposition = move.update();
            //if(aimposition!=null)
           // {
            //velocity1(diff(position(),aimposition));
          //  }
            if(move!=null)
            {
                move.action();
                move.age();
            }
            
            super.accelerate();
            super.move();
            
        }

        public void draw(Graphics2D g) {
            //g.setColor(Color.orange);
            //g.fillOval(round(x - 10), round(y - 10), round(2*10), round(2*10));
            //g.setColor(Color.red);
            //g.drawOval(round(x - 10), round(y - 10), round(2*10), round(2*10));
            switch(status){
                case Active:
                    activeDraw(g);
                    break;
                case Fade:
                    fadeDraw(g);
                    break;
                default:
                    break;
            }
        }
        public void activeDraw(Graphics2D g)
    {
            drawImage(g,sprites[color.ordinal()][(int)age%sprites[0].length],x,y);
        }
        public void fadeDraw(Graphics2D g)
    {
            drawImage(g,fadesprites[color.ordinal()][(int)((1.-abs((double)age/fade))%1*fadesprites[0].length)],x,y);
        }
        public static BufferedImage[][] sprites() {
        int angle = 60;
        double size = 36;
        int aura = 6;
        int index = 0;
        BufferedImage[][] imageList = new BufferedImage[ColorType.values().length][angle];


        for (ColorType c : ColorType.values()) {
            for (int t = 0; t < angle; t++) {
                BufferedImage thisI = newBImage(size, size);
                Graphics2D tempg = thisI.createGraphics();
                tempg.setComposite(Calc.opacity(1));
                tempg.rotate(2 * PI / angle * t, size / 2, size / 2);


                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(c.getC());
                tempg.setComposite(opacity(.1));
                for (int i = 0; i <= aura; i++) {
                    Calc.fillCircle(tempg, size / 2, size / 2, size / 2 - i);

                }
                tempg.setComposite(opacity(1));
                tempg.setColor(Color.BLACK);
                Calc.fillEllipse(tempg, size / 2, size / 2, size / 2 - aura, size / 2 - aura, 0, PI);
                tempg.setColor(Color.WHITE);
                Calc.fillEllipse(tempg, size / 2, size / 2, size / 2 - aura, size / 2 - aura, PI, PI);
                tempg.setColor(Color.WHITE);
                Calc.fillEllipse(tempg, size / 2 - (size / 2 - aura) / 2, size / 2, (size / 2 - aura) / 2, (size / 2 - aura) / 2, 0, PI);
                tempg.setColor(Color.BLACK);
                Calc.fillEllipse(tempg, size / 2 + (size / 2 - aura) / 2, size / 2, (size / 2 - aura) / 2, (size / 2 - aura) / 2, PI, PI);
                tempg.setColor(Color.BLACK);
                Calc.fillEllipse(tempg, size / 2 - (size / 2 - aura) / 2, size / 2, (size / 2 - aura) / 6, (size / 2 - aura) / 6);
                tempg.setColor(Color.WHITE);
                Calc.fillEllipse(tempg, size / 2 + (size / 2 - aura) / 2, size / 2, (size / 2 - aura) / 6, (size / 2 - aura) / 6);
                imageList[index][t] = thisI;
            }
            index++;
        }
        return imageList;
    }


        public static BufferedImage[][] fadeSprites() {
    //    int angle = 1;
        double MaxSize = 80;
        int index = 0;
        BufferedImage[][] imageList = new BufferedImage[ColorType.values().length][(int)MaxSize];


        for (ColorType c : ColorType.values()) {
            for (double count = 0; count < MaxSize; count++) {
                double size = count+10;
                BufferedImage thisI = newBImage(size, size);
                Graphics2D tempg = thisI.createGraphics();
                tempg.setComposite(Calc.opacity(1));
                //tempg.rotate(2 * PI / angle * t, size / 2, size / 2);


                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(c.getC());
                
                for(double i=pow(size/2,3);i>0;i-=pow(size/2,3)/10)
                {
                    tempg.setComposite(opacity(.3*(1-count/MaxSize)));
                    tempg.setColor(c.getC());
                    fillCircle(tempg,size/2,size/2,size/2);
                    tempg.setComposite(clear());
                    fillCircle(tempg,size/2,size/2,pow(i,1./3));
                }
                imageList[index][(int)(count)] = thisI;
            }
            index++;
        }
        return imageList;
    }
    //}
}
