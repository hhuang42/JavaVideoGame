/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.Event;
import java.util.*;
import static java.lang.Math.*;
import static practice.Calc.*;

/**
 *
 * @author Student
 */


    public class Char extends Basic {

        /*float x;
        float y;
        float xv;
        float yv;
        float age;*/
        float uFSpd = (float) 3.0;
        float FSpd = (float) 1.2;
        /*float hitSize = 3;*/
        float grazeSize = 24;
        boolean focus = false;
        public boolean invincible = false;
        boolean onHit = false;
        BufferedImage hitBoxSprite;
        Status status = Status.Active;
        double mag;
        float unFocusDraw = 28;
        float FocusDraw = 48;
        float collectSize = 7;
        int firing;
        int bombCount;
        int bombTimer;
        int power;
        int powerLevel;
        int respawnTime = 30;
        int invincibleTime = 120;
        public boolean forbidRespawn;
        
        public static Char ch = new Char();
        ArrayList<Option> options = new ArrayList<Option>();
        static BufferedImage[] Shot1;
        static BufferedImage[] Shot2;
        static BufferedImage[] Bomb1;
        static BufferedImage[] Bomb2;
        static BufferedImage[] Bomb3;
        
        BombStatus bombStatus = BombStatus.None;
        public static void init(){
            
            ch.forbidRespawn = false;
            ch.focus = false;
            ch.respawn();
            ch.x = GameEngine.scrWidth / 2;
            ch.y = GameEngine.scrHeight - 30;
        }
        
        static Sprite charSprite = new Sprite(){
            int fade = 60;
            public BufferedImage[] drawSprite(){
                
            BufferedImage[] imageList = new BufferedImage[fade];


                for(int t = 0; t < fade; t++)
                {
                BufferedImage thisI = newBImage(40, 40);
                Graphics2D tempg = thisI.createGraphics();
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(Color.pink);
                tempg.setComposite(opacity((double)t/(fade-1.)));
                Calc.fillEllipse(tempg, 20, 20, 8, 16);

                imageList[t] = thisI;
                }
            return imageList;
            }
            public BufferedImage image(double opac){
                return super.image((fade-1)*(opac));
            }
        }.set();
        public enum BombStatus{
            Focus,unFocus,None
        }

        public static class Option extends Basic{
            public void countage()
            {
                age= ch.age;
            }

            public void shoot(){
                if(age%5==0)
                {
                }
            }
            public void update(){
                this.countage();
                move();
            }

        }

        public void shootOption(){
            Option[] temp = options.toArray(new Option[0]);
            for(int i=0;i<temp.length;i++)
            {
                temp[i].shoot();
            }
        }

        public void updateOption(){
            while(options.size()<power/100)
                {
                    options.add(new Option(){
                        int num = options.size()+1;
                        double dist = 40;
                        public void move(){
                            //position1(toV(ch.x+20*(num-(ch.power/100+1)/2.f),ch.y-30));
                            double angle = (double)num/(ch.power/100)*2*PI+age/10.f;
                            dist = min(max((focus?(dist - 4):(dist + 4)),30),60);
                            position1(toF(toV(ch.x+dist*cos(angle),ch.y+dist*sin(angle))));
                        }
                        public void draw(Graphics2D g){
                            g.setColor(Color.red);
                            Calc.fillCircle(g, x, y, 6);
                        }
                        public void shoot(){
                            if(age%5==0)
                            {
                            Shot.reset0();
                Shot.spriteList0(Shot2);
                Shot.power0(3);
                Shot.semiDim0(toV(15,15));
                Shot.velocity0(toV(0,-15));
                Shot.position0(toV(x,y));
                Shot.acceleration0(toV(.2f,0));
                new Shot().add();
                Shot.acceleration0(toV(-.2f,0));
                new Shot().add();
                            }
                        }
                    });
                }
            while(options.size()>power/100)
            {
                options.remove(options.size()-1);
            }
            Option[] temp = options.toArray(new Option[0]);
            for(int i=0;i<temp.length;i++)
            {
                temp[i].update();
            }

        }

        public Char() {
            respawn();
            hitSize = 3;
            hitBoxSprite = hitBoxSprite();
            Shot1 = spriteList1();
            Shot2 = spriteList2();
            Bomb1 = BombList1();
            Bomb2 = BombList2();
            Bomb3 = BombList3();
            bombTimer = 0;
            status=Status.Active;
            power = 00;
        }
        public static void updateChar() {
        Shot.UpdateShot();
        
        ch.update();
        /*if(ch.onHit)
        {
            ch.respawn();
            ch.onHit=false;
        }*/

    }

        public void bomb(){
            if(bombCount!=0&&bombStatus==bombStatus.None&&(status==status.Fade||status==status.Active))
            {
            bombCount--;
            bombTimer = 0;
            onHit = false;
            status = status.Active;
            invincible=true;
            age=0;
            Item.setAllHome(true);
            if(focus)
            {
                bombStatus=bombStatus.Focus;
            } else {
                bombStatus=bombStatus.unFocus;
            }
            }
        }
        public static void addPower(int change){
            ch.power = max(min(ch.power+change,400),0);
            if(ch.power/100!=ch.powerLevel){
                ch.powerLevel= ch.power/100;
            }
        }

        public void focusBomb()
        {
            switch(bombTimer)
            {
                case 1:
                case 21:
                case 41:
                case 61:
                    for(double a= -PI/(3);a<=PI/(3);a+=PI/(6))
            {
             BombShot.reset0();
                BombShot thisB = new BombShot(){
                    Enemy target = null;
                    float closeDist;
                    boolean search = false;
                    public void update(){
                        super.update();
                        turn();
                    }

                    public void shotAction(){
                        closeDist = 0;
                        if(target!=null&&age>20)
                        {
                        if(target.status==target.status.Active)
                        {
                            //velocity1(Calc.homeConstVel(target.position(), position0, velocity0, .01));
                            //acceleration1(Calc.norm(diff(position(),target.position()), .5f/((age-10)/10)));
                            //velocity1(Calc.norm(velocity(), 4));
                            w1(copySign(
                                    (float)((PI/60)/((age+30)/50)),
                                    angle0(diffAngle(diff(position(),target.position()),velocity()))));
                            search = false;
                        }
 else
                        {
                            search = true;
                            target = null;
                            w1(0);
 }}
                    }
                    public boolean checkHit(Enemy thisE){
                        if(search = true)
                        {
                            if(distance(thisE.position(),position())<closeDist||closeDist==0)
                            {
                                closeDist = distance(thisE.position(),position());
                                target = thisE;
                            }
                        }
                        return super.checkHit(thisE);

                    }
                };
            thisB.hits1(6);

            thisB.power1(30);
            thisB.position1(position());
            thisB.semiDim1(toV(25,25));
            thisB.spriteList1(Bomb3);
            thisB.add();
            thisB.velocity1(rotate(toV(0,-3.5f),a));
            //thisB.acceleration1(toV(0,-.02f));
            thisB.add();
                }
                    break;
                    case 150:
                    endBomb();
                    break;
                default:
                    break;
            }
        }
        public void unfocusBomb()
        {
            switch(bombTimer)
            {
                case 1:
                    for(double i = 0;i<2*PI;i+=2*PI/4)
            {
                BombShot.reset0();
                BombShot thisB = new BombShot1();
            thisB.hits1(-1);
            
            thisB.power1(5);
            thisB.position1(position());
            thisB.semiDim1(toV(35,35));
            thisB.spriteList1(Bomb1);
            thisB.velocity1(toXY(toF(toV(6,i+PI/4))));
            thisB.add();

                }
                    break;
                case 90:
                    endBomb();
                    break;
                default:
                    break;
            }
        }
        public void endBomb()
        {
            bombStatus=BombStatus.None;
            bombTimer = 0;
            invincible=false;
        }
        public void bombLoop()
        {
            if(bombStatus!=BombStatus.None)
            {
                bombTimer++;
                if(bombStatus==BombStatus.Focus)
                {
                    focusBomb();
                }else{
                    unfocusBomb();
                }
                
            }

        }

        public void move() {
            x = max(min(x + xv, GameEngine.scrWidth - 10), 10);
            y = max(min(y + yv, GameEngine.scrHeight - 10), 10);
        }
        public static void fire(boolean firing){
            if(firing)
            {
                ch.firing = -1;
            }
            else
            {
                ch.firing = 20;
            }
        }

        public void Focus(boolean set) {
            focus = set;
        }

        public void respawn() {
            /*if(age>1)
            for(float i=-1;i<=1;i+=2/7.)
            {
                final float t = i;
                new Item(){
                    {
                        setHome(false);
                        position1(Char.ch.position());
                        velocity1(toV((Script.semiWidth-Char.ch.x+t*Script.semiWidth*.5f)/30,(40-Char.ch.y)/30));
                    }
                    public void update(){
                        super.update();
                        if(age==30)
                        {
                            setHome(true);
                            setHome(false);
                        }
                    }
                }.add();
            }*/
            if(!forbidRespawn)
            {
            invincible = false;
            if(false)
            {
                
            x = GameEngine.scrWidth / 2;
            y = GameEngine.scrHeight - 30;
            }
            
                status = status.Active;
            onHit = false;
            bombCount = -1;
            xv = 0;
            yv = 0;
            power = max(power - 100,00);
            }
            //Bullet.ClearAll();
        }
        
        public void update() {
            bombLoop();
            switch (status) {
                case Active:
                    move();
                    shoot();
                    collect();
                    updateOption();
                    active();
                    
            countage();
                    break;


                case Fade:
                    
                    fade();
                    
            countage();
                    break;
                case Dead:
                    dead();
                    if(!(age>0))
                    {
            countage();}
                    break;
            }
        }
        public void active(){
            if(age==0){
                this.invincible=false;
            }
            if(onHit)
            {
                
                GameEngine.deathCount();
                onHit= false;
                status=status.Fade;
                age = -borderTime();
                SE.DEAD.play();
            }
        }
        public void fade(){
            if(age>0)
            {
                status = status.Dead;
                age = -respawnTime;
                Item.setAllHome(false);
            }
            
        }
        public void dead(){
            if(age>0&&!forbidRespawn){
                respawn();
                age = -invincibleTime;
                invincible=true;
            }
        }

        public void shoot() {
            if (firing != 0) {
                SE.PSHOOT.loop(true);
                regShoot();
                shootOption();
                if (focus) {
                    //focusShoot();
                } else {
                    //unfocusShoot();
                }

                firing--;
            }
            else{
                SE.PSHOOT.loop(false);
            }



        }
        public int borderTime(){
            return 10;
        }
        public void focusShoot(){
            Shot.reset0();
            Shot.position0(position());
            if (age % 8 == 0) {
                    for (int i = -1; i <= 1; i++) {
                        Shot.power0(4);
                        Shot.semiDim0(toV(15,15));
                        Shot.spriteList0(Shot2);
                        Shot.velocity0(toXY(toF(toV(15, PI / 48 * i-PI/2))));
                        Shot thisS= new Shot();
                        thisS.add();
                    }
            }
                    if(age%5==0){
                    for (int i = 0; i <= 0; i++) {
                        Shot.semiDim0(toV(30,30));
                        Shot.power0(15);
                        Shot.spriteList0(Shot1);
                        Shot.velocity0(toXY(toF(toV(15, -PI/2))));
                        Shot thisS= new Shot();
                        thisS.add();
                    }
                }
                
        }
        public void unfocusShoot(){
            Shot.reset0();
            Shot.spriteList0(Shot2);
            Shot.semiDim0(toV(15,15));
            Shot.power0(5);
            if (age % 8 == 0) {
                    for (int i = -2; i <= 2; i++) {
                        Shot.position0(position());
                        Shot.velocity0(toXY(toF(toV(15, PI / 32 * i-PI/2))));
                        new Shot().add();
                }
            }
            if(age%7==0){
                    for (int i = -2; i <= 2; i++) {
                        Shot.position0(Calc.toV(ch.x + i * 15, ch.y));
                        Shot.velocity0(Calc.toV(0, -15));
                        new Shot().add();
                    }
            }
                
        }
        public void regShoot(){
            if(age%5==0){
                Shot.reset0();
                Shot.spriteList0(Shot2);
                Shot.power0(20);
                Shot.semiDim0(toV(15,15));
                Shot.velocity0(toV(0,-15));
                Shot.position0(toV(x+10,y));
                new Shot().add();
                Shot.position0(toV(x-10,y));
                new Shot().add();
            }
        };
        public void newShot(double[] position,double[] velocity)
        {
            //Shot.reset0();
            //Shot.position0();
            Shot thisS = new Shot(position,velocity, 1,Calc.toV((double)5, 10));
            thisS.add();
        }
        

        /**static void collide(boolean hit)
        {
        if(hit&&(!invincible))
        {
        onHit = true;

        }
        }*/
        void Vel(float x, float y) {
            mag = max(pow(abs(x) + abs(y), .5), 1);
            if (focus) {
                xv = (float) (FSpd * x / mag);
                yv = (float) (FSpd * y / mag);
            } else {
                xv = (float) (uFSpd * x / mag);
                yv = (float) (uFSpd * y / mag);
            }
        }

        public void draw(Graphics2D g) {
            
            switch(status) {
                case Active:
                case Fade:
                    Calc.drawImage(g, charSprite.image(1), x, y);
                    break;
                case Dead:
                    Calc.drawImage(g, charSprite.image(1-5*(1-(double)age/(-respawnTime))), x, y);
                    break;
            }
        }
        public static void drawChar(Graphics2D g) {
        ch.draw(g);
        Option[] temp = ch.options.toArray(new Option[0]);
            for(int i=0;i<temp.length;i++)
            {
                temp[i].draw(g);
            }
    }
        public void drawBox(Graphics2D g){
            /*g.setColor(Color.white);
            g.fillOval(round(x - hitSize), round(y - hitSize), round(2 * hitSize), round(2 * hitSize));
            g.drawOval(round(x - grazeSize), round(y - grazeSize), round(2 * grazeSize), round(2 * grazeSize));
            g.setColor(Color.red);
            g.drawOval(round(x - hitSize), round(y - hitSize), round(2 * hitSize), round(2 * hitSize));
            g.drawOval(round(x - hitSize)-1, round(y - hitSize)-1, round(2 * hitSize)+2, round(2 * hitSize)+2);*/
            g.drawImage(hitBoxSprite, null, round(x-hitBoxSprite.getWidth()/2), round(y-hitBoxSprite.getHeight()/2));
        }
        public static void drawHitBox(Graphics2D g){
            ch.drawBox(g);
        }
        public static void hit() {

            if(!ch.invincible){
            ch.onHit=true;
            }
    }
public void collect(){
    if(y<=150){
        Item.setAllHome(true);
    }
    Item[] tempList = Item.Ilist.toArray(new Item[0]);
    for(int i = 0;i<tempList.length;i++){
        if(Calc.distance(position(), tempList[i].position())<=((focus)?FocusDraw:unFocusDraw))
        {
            tempList[i].setHome(true);
        }
        if(Calc.distance(position(), tempList[i].position())<=this.collectSize)
        {
            tempList[i].collect();
            SE.COLLECT.play();
        }
    }
}

    public static void setVel(float x, float y) {
        ch.Vel(x, y);
    }

    public static void setFocus(boolean focus) {
        ch.Focus(focus);
    }



    public static float getAngle(double x, double y) {
        return (float) atan2(ch.y - y, ch.x - x);
    }
    public static float[] charPosition()
    {
        return ch.position();
    }
    public static float getDistanceSquare(double x, double y)
    {
        return (float) (pow(x-ch.x,2)+pow(y-ch.y,2));
    }
    public static float getHitboxSize()
    {
        return ch.hitSize;
    }
    public static float getGrazeboxSize()
    {
        return ch.grazeSize;
    }

    public BufferedImage hitBoxSprite()
    {
        BufferedImage thisI = Calc.newBImage((round(hitSize)+1)*2,(round(hitSize)+1)*2);
        Graphics2D tempg = thisI.createGraphics();
        tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        tempg.setColor(Color.red);
        Calc.fillCircle(tempg, hitSize+1, hitSize+1, hitSize+1);
        tempg.setColor(Color.white);
        Calc.fillCircle(tempg, hitSize+1, hitSize+1, hitSize);
        return thisI;
    }
    public static BufferedImage[] spriteList1(){
                    int angle = 1;
            BufferedImage[] imageList = new BufferedImage[angle];


                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(50, 50);
                Graphics2D tempg = thisI.createGraphics();
                tempg.setComposite(Calc.opacity(.1));
                tempg.rotate(2*PI/angle*t, 25, 25);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(new Color(200,50,255));
                /*float[][] coord = {{16,14},{14,16},{6,16},{4,14},{4,6},{6,4},{14,4},{16,6}};
                fillPolygon(tempg,coord);*/
                fillEllipse(tempg,25,25,20,25);
                tempg.setColor(new Color(255,215,0));
                tempg.setComposite(opacity(.05));
                fillEllipse(tempg,25,25,20,25);
                fillEllipse(tempg,25,25,18,20);
                for(int i=0;i<30;i++)
                    {
                    fillEllipse(tempg,25,30-i,15-i*1/2,16-i/2);
                    }
                    tempg.setComposite(opacity(.1));
                    tempg.setColor(new Color(255,215,0).brighter());
                    //fillEllipse(tempg,25,20,10,12);
                /*tempg.setComposite(clear());
                fillEllipse(tempg,20,10,8,4);
                tempg.setComposite(opacity(.4));
                fillEllipse(tempg,20,10,8,4);*/

                imageList[t] = thisI;
                }
            return imageList;
    }
public static BufferedImage[] spriteList2(){
                    int angle = 1;
            BufferedImage[] imageList = new BufferedImage[angle];


                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(20, 20);
                Graphics2D tempg = thisI.createGraphics();
                
                tempg.rotate(2*PI/angle*t, 10, 10);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(new Color(255,215,0));
                tempg.setComposite(Calc.opacity(.1));
                for(int i=5;i<10;i++)
                {
                    fillEllipse(tempg,10,10,i,i);
                }
                tempg.setComposite(Calc.opacity(.5));
                tempg.setColor(new Color(200,50,255));
                fillEllipse(tempg,10,10,5,5);
                tempg.setComposite(Calc.opacity(.1));
                tempg.setColor(new Color(255,215,0));
                fillEllipse(tempg,10,10,3,3);

                imageList[t] = thisI;
                }
            return imageList;
    }
public static BufferedImage[] BombList1(){
                    int angle = 60;
                    int size = 60;
            BufferedImage[] imageList = new BufferedImage[angle];


                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(size, size);
                Graphics2D tempg = thisI.createGraphics();

                tempg.rotate(2*PI/angle*t, size/2, size/2);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(new Color(200,50,255));
                tempg.setComposite(Calc.opacity(.05));
                for(int i = 1;i<=size/2;i+=size/60)
                {
                fillEllipse(tempg,size/2,size/2,i*3./4,i);
                
                }

                imageList[t] = thisI;
                }
            return imageList;
    }
public static BufferedImage[] BombList2(){
                    int angle = 60;
            BufferedImage[] imageList = new BufferedImage[angle];
            int size = 60;


                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(size, size);
                Graphics2D tempg = thisI.createGraphics();

                tempg.rotate(2*PI/angle*t, size/2, size/2);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                /*tempg.setColor(new Color(200,50,255));
                tempg.setComposite(Calc.opacity(.05));
                for(int i = 0;i<15;i+=1)
                {
                    tempg.fillOval(0,0,60,4*i);
                }*/
                tempg.setColor(new Color(200,50,255));
                tempg.setComposite(Calc.opacity(.05));
                for(int i = size;i>=0;i-=size/15)
                {
                    for(int l=0;l<size/2;l+=size/15)
                    {
                        tempg.fillOval(l, 0, size-2*l, round(i*1f));
                    }
                }

                tempg.setColor(new Color(255,215,0));
                tempg.setComposite(Calc.clear());
                for(int i = size*3/8;i>=1;i-=size/10)
                {
                    for(int l=0;l<size/2;l+=size/10)
                    {
                        tempg.fillOval(l, 0, size-2*l, round(i*2f));
                    }
                }
                tempg.setComposite(Calc.opacity(.01));
                for(int i = size*3/8;i>=1;i-=size/30)
                {
                    for(int l=0;l<size/2;l+=size/30)
                    {
                        tempg.fillOval(l, 0, size-2*l, round(i*2f));
                    }
                }
                /*tempg.setComposite(Calc.opacity(.25));
                tempg.setColor(new Color(255,215,0));
                for(int i = 4; i < 15; i++)
                {
                fillEllipse(tempg,30,22-i,i*1,18-i);
                    }*/

                imageList[t] = thisI;
                }
            return imageList;
    }
public static BufferedImage[] BombList3(){
                    int angle = 60;
            BufferedImage[] imageList = new BufferedImage[angle];


                for(int t = 0; t < angle; t++)
                {
                BufferedImage thisI = newBImage(60, 60);
                Graphics2D tempg = thisI.createGraphics();

                tempg.rotate(2*PI/angle*t, 30, 30);
                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.drawImage(Bomb2[0], 10, 10, 40, 40, null);

                imageList[t] = thisI;
                }
            return imageList;
    }
public class BombShot1 extends BombShot{
    public void shotAction(){
        if(age%10==5)
        {
            BombShot.reset0();
            BombShot.hits0(-1);
            BombShot.velocity0(rotate(toV(0,-6),PI/4+orient()));
            BombShot.acceleration0(rotate(toV(0,0*-.1f),PI/4+orient()));
            BombShot.power0(10);
            BombShot.position0(position());
            BombShot.semiDim0(toV(35,35));
            BombShot.spriteList0(Bomb2);
            new BombShot().add();
            /*BombShot.velocity0(toV(0,-3));
            BombShot.acceleration0(toV(0,-.05f));
            new BombShot().add();*/
        }
        if(age%10==5)
        {
            BombShot.reset0();
            BombShot.hits0(-1);
            BombShot.velocity0(rotate(toV(0,-6),orient()-PI/4));
            BombShot.acceleration0(rotate(toV(0,0*-.1f),orient()-PI/4));
            BombShot.power0(20);
            BombShot.position0(position());
            BombShot.semiDim0(toV(35,35));
            BombShot.spriteList0(Bomb2);
            new BombShot().add();
            /*BombShot.velocity0(toV(0,-3));
            BombShot.acceleration0(toV(0,-.05f));
            new BombShot().add();*/
        }
    }
}

    }
    

