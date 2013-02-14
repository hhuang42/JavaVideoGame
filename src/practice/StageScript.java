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
import static practice.Item.Type;
import static practice.Item.Type.*;

/**
 *
 * @author Student
 */
public class StageScript extends Script {

    
    static int diff = 4;
    
    /*ArrayList<sEvent> Evlist = new ArrayList<sEvent>();

    public abstract class sEvent extends Event {

        public double start;

        public boolean check() {
            return i == (int) start;
        }

        public void add() {
            Evlist.add(this);
        }
    }
    public void run(){
        BGM.S3S.play();
        System.out.println(3);
        wait(150);
        for(int t = -3;t<=3;t+=2)
        {
            new Enemy5(toV(-10, 50),false).add();
            wait(30);
            new Enemy5(toV(scrWidth+10, 50),true).add();
            wait(30);
        }
        
            wait(60);
        for(int t = -3;t<=3;t++)
        {
            new Enemy3(toV(semiWidth + .8f*(float)t/3*semiWidth, -10)).add();

        }
        wait(120);
        for(int t = 0;t<=10;t++)
        {
        new Enemy1(toV(semiWidth + .9f*(float)t/10*semiWidth, -10), false).add();
        wait(30);
        new Enemy1(toV(semiWidth - .9f*(float)t/10*semiWidth, -10), true).add();
        wait(30);
        }
        wait(180);
        for(int t = 0;t<=5;t++)
        {
            for(int p = 1; p>=-1;p-=2)
            {
            new Enemy2(toV(semiWidth - p*.6f*semiWidth, -10)).add();
            new Enemy2(toV(semiWidth - p*.8f*semiWidth, -10)).add();
            new Enemy1(toV(semiWidth + p*.7f*semiWidth, -10),(p!=1)).add();
            wait(90-t*12);
            new Enemy3(toV(semiWidth + p*.4f*semiWidth, -10)).add();
            new Enemy3(toV(semiWidth - p*.2f*semiWidth, -10)).add();
            new Enemy3(toV(semiWidth + p*.1f*semiWidth, -10)).add();
            wait(90-t*12);
            }
        }
        wait(90);
        for(int t = -3;t<=3;t++)
        {
            new Enemy3(toV(semiWidth + .8f*(float)t/3*semiWidth, -10)).add();

        }
        wait(15);
        for(int t = -3;t<=3;t++)
        {
            new Enemy3(toV(semiWidth + .8f*(float)t/3*semiWidth, -10)).add();

        }
        wait(15);
        for(int t = -5;t<=5;t++)
        {
            new Enemy3(toV(semiWidth + .8f*(float)t/5*semiWidth, -10)).add();

        }
        wait(120);
        for(int t = 0;t<=10;t++)
        {
        new Enemy1(toV(semiWidth + .9f*(float)t/10*semiWidth, -10), false).add();
        new Enemy3(toV(semiWidth - .9f*(float)semiWidth, -10)).add();
        
        wait(30);
        new Enemy1(toV(semiWidth - .9f*(float)t/10*semiWidth, -10), true).add();
        new Enemy3(toV(semiWidth + .9f*(float)semiWidth, -10)).add();
        wait(30);
        new Enemy2(toV(semiWidth - .4f*(float)semiWidth, -10)).add();
        new Enemy2(toV(semiWidth + .4f*(float)semiWidth, -10)).add();
        wait(30);
        }
        wait(600);
        new testBoss().add();
        
    };
    public boolean inRange(int start, int max, int dx) {
        return start <= i && i <= max && (i - start) % dx == 0;
    }

    public boolean inRange(int start, int max, int dx, int value) {
        return start <= i && i <= max && (i - start) % dx == 0;
    }

    public void restart() {
        i = 0;
    }*/

    public float[] reflect(float[] vector) {
        return toV(reflect(vector[0]), vector[1]);
    }

    public float reflect(float x) {
        return scrWidth - x;
    }

    public static class Enemy1 extends Enemy {

        Enemy1(float[] position, boolean flip) {
            position1(position);
            //float[][][] moves = {{{0, 0}, {0}}, {{0, 120}, {90}}, {{-100, 150}, {90}}, {{-250, 200}, {60}}};
            //move = new Move(moves).translate(position);
            color = ColorType.Red;
            shift = position;
            Type[] itemList = {Power, Power};
            this.itemList = itemList;
            HP = 40;
            if (flip) {
                this.flip = toV(-1, 1);
            } else {
                this.flip = toV(1, 1);
            }
        }

        public void moveScript() {
            switch (age) {
                case 1:
                    moveTo(90, toV(0, 120));
                    break;
                case 91:
                    moveToH(150, toV(-250, 200), toV(-.5f, .1f));
                    break;
            }
        }

        public void shoot() {
            if (age == 60) {
                Bullet.reset0();

                Bullet.type0(Bullet.Type.Outlined);

                Bullet.position0(position());
                for (double angle = -.5 * (diff - 2); angle <= .5 * (diff - 2); angle++) {
                    Bullet.color0(2);
                    for (double vel = 1; vel <= 2; vel++) {
                        Bullet.velocity0(toXY(toF(
                                toV((1 + vel * 1 / 10) / 2, angle * PI / 20 + toAngle(toD(position()), toD(Char.charPosition()))))));
                        Bullet.acceleration0(toXY(toF(
                                toV((1 + vel * 1 / 10) / 100, angle * PI / 20 + toAngle(toD(position()), toD(Char.charPosition()))))));
                        new Bullet().add();
                    }
                }
                for (double angle = -.5 * (diff - 1); angle <= .5 * (diff - 1); angle++) {
                    Bullet.color0(1);
                    for (double vel = 1; vel <= 2; vel++) {
                        Bullet.acceleration0(toV(0, 0));
                        Bullet.velocity0(toXY(toF(
                                toV((1 + vel * 1 / 10) * 1.2, angle * PI / 20 + toAngle(toD(position()), toD(Char.charPosition()))))));
                        new Bullet().add();
                    }
                }

            }
        }
    }

    public static class Enemy2 extends Enemy {

        Enemy2(float[] position) {
            //float[][][] moves = {{{0, -10}, {0}}, {{0, 150}, {180}}, {{0, 200}, {180}}, {{00, scrHeight}, {120}}};
            //move = new Move(moves);
            position1(position);
            //move = new Move(moves).translate(position);
            shift = position;
            color = ColorType.Yellow;

            Type[] itemList = {Point};
            this.itemList = itemList;
            HP = 20;
        }

        public void moveScript() {
            switch (age) {
                case 1:
                    moveTo(180, toV(0, 150));
                    break;
                case 181:
                    moveToH(180, toV(0, 200), toV(0, .1f));
                    break;
                case 301:

                    moveToH(180, toV(0, scrHeight), toV(0, 1f));
                    break;
            }
        }

        public void shoot() {
            if (age % 80 == 30 && age <= 270) {
                Bullet.reset0();

                Bullet.type0(Bullet.Type.Outlined);

                Bullet.position0(position());
                for (double i = -1; i <= 1; i += 2) {
                    Bullet.color0(3);
                    for (double vel = 2; vel <= diff; vel++) {
                        Bullet.velocity0(toXY(toF(
                                toV((1.5 + vel * 1 / 2) * .8 * 1.5, i * PI / 8 + toAngle(toD(position()), toD(Char.charPosition()))))));
                        new Bullet() {
                            float[] v0 = velocity0;
                            public void move() {
                                super.move();
                                changeSpeed(1, 30, .5f * mag(v0));

                            }
                        }.add();
                    }
                }
            }
        }
    }

    public static class Enemy3 extends Enemy {

        Enemy3(float[] position) {
            //float[][][] moves = {{{0, -10}, {0}}, {{0, 100}, {80}}, {{00, -10}, {80}}};
            //move = new Move(moves);
            position1(position);
            shift = position;
            //move = new Move(moves).translate(position);
            color = ColorType.Blue;
            Type[] itemList = {Point, Point};
            this.itemList = itemList;
            HP = 20;
        }

        public void moveScript() {
            switch (age) {
                case 1:
                    moveToH(80, toV(0, 100));
                    break;
                case 81:
                    moveToH(80, toV(0, -10), toV(0, -.1f));
                    break;
            }
        }

        public void shoot() {
            if (age == 60) {
                Bullet.reset0();

                Bullet.type0(Bullet.Type.Outlined);

                Bullet.position0(position());
                for (double i = -6; i <= 6; i += 4. / diff) {
                    Bullet.color0(5);
                    Bullet.velocity0(toXY(toF(
                            toV(2 * 2, i * PI / 24 + toAngle(toD(position()), toD(Char.charPosition()))))));
                    new Bullet() {
                        float[] v0 = velocity0;

                        public void move() {
                            
                            super.move();
                            changeSpeed(1, 60, mag(v0) * .4f);
                        }
                    ;
                }
            
        
    

    .add();
                            }
                        }
                    }
                }
    public static class Enemy4 extends Enemy {

        Enemy4(float[] position, boolean flip) {
            float[][][] moves = {{{0, 0}, {0}}, {{semiWidth / 3, 0}, {120}}, {{semiWidth, 0}, {750}}, {{scrWidth + 10, 0}, {120}}};
            shift = position;
            position1(position);
            move = null;
            color = ColorType.Green;
            Type[] itemList = {Point, Power, Power, Point, Power, Power, Point, Power, Power};
            this.itemList = itemList;
            HP = 560;
            if (flip) {
                this.flip = toV(-1, 1);
            } else {
                this.flip = toV(1, 1);
            }
        }

        public void moveScript() {
            switch (age) {
                case 1:
                    moveToH(120, toV(semiWidth / 3, 0), toV(.1f, 0));
                    break;
                case 121:
                    moveToH(750, toV(semiWidth, 0), toV(.1f, 0));
                    break;
                case 871:
                    moveToH(120, toV(scrWidth + 10, 0), toV(2f, 0));
                    break;
            }
        }

        public void shoot() {
            if (age >= 130 && age % 50 == 30) {
                Bullet.reset0();

                Bullet.type0(Bullet.Type.Outlined);

                Bullet.position0(position());
                for (double i = 0; i < diff; i++) {
                    for (int a = -1; a <= 1; a += 2) {
                        Bullet.color0(4);
                        Bullet.velocity0(toXY(toF(
                                toV((2 - i / 15) * 1.2 * 2, a * PI / 60 * (i) + toAngle(toD(position()), toD(Char.charPosition()))))));
                        new Bullet() {
                            float[] v0 = velocity0;
                            public void move() {
                                super.move();
                                changeSpeed(1, 20, mag(v0) * .5f);
                            }
                        ;
                        }.add();
                                    Bullet.color0(4);
                        Bullet.velocity0(toXY(toF(
                                toV((1.3 + i / 10) * .9 * 2, a * PI / 60 * (i + 8) + toAngle(toD(position()), toD(Char.charPosition()))))));
                        new Bullet() {
                            float[] v0 = velocity0;
                            public void move() {
                                super.move();
                                changeSpeed(1, 20, mag(v0) * .5f);
                            }
                        ;
                    }
                
            
        
    

    .add();
                            }
                            }
                        }
                    }
                }
    public static class Enemy5 extends Enemy {

        public int count;

        Enemy5(float[] position,boolean flip) {
            //float[][][] moves = {{{0, 0}, {0}}, {{5, 150}, {120}},{{-5, 150}, {250}},{{5, 150}, {250}}, {{-5, 150}, {250}},{{0, -10}, {60}}};
            //move = new Move(moves);
            if(flip)
                this.flip=toV(-1,1);
            shift = position;
            position1(position);
            move = null;
            color = ColorType.Green;
            Type[] itemList = {Point, Power};
            this.itemList = itemList;
            HP = 60;
        }

        public void moveScript() {
            if (age == 1) {
                this.moveToH(180, toV(300, 100));
            }
            //this.stop(180);
            if (age == 181) {
                this.moveToH(180, toV(-10, 200));
            }
        }

        public void shoot() {
            if (Calc.inRange(age, 0, 240, 30, 900)) {
                Bullet.reset0();

                Bullet.type0(Bullet.Type.Outlined);
                if (age % 300 == 150) {
                    count = 0;
                }
                count++;


                for (double i = -1; i <= 1; i += 2. / (1 + diff/2)) {
                        Bullet.position0(position());
                        Bullet.color0(6);
                        Bullet.velocity0(toXY(toF(
                                toV(1.5, Calc.toAngle(this.position(), Char.charPosition())+(i)*PI/12))));
                        new Bullet() {
                        }.add();
                                    Bullet.color0(4);
                        float angle = toAngle(position(), Char.charPosition());
                        Bullet.velocity0(toXY(toV(1.5f, angle + (float) PI + Calc.randomFloat(-(float) PI * 3 / 3, (float) PI * 3 / 3))));
                        //new Bullet().add();
                    }
            }
        }
    }

    /*public static class testBoss  extends Boss {

        public testBoss() {
            HPBar temp = new HPBar();
            this.position1(toV(semiWidth, -10));
            temp.add(
                    new SpellCard(this) {

                        {
                            maxHP = 3000;
                            thisT = thisT.SpellCard;
                        }
                        public int we = 0;
                        public boolean we2 = false;

                        public void update() {
                            if (age == 1) {
                                thisB.moveToH(120, toV(semiWidth, 120));
                            }
                            if (age == 120) {
                                thisB.stop();
                            }
                            if (age % 300 == 100) {
                                thisB.moveToH(200, sum(toV(semiWidth, 120), toV(randomFloat(-20, 20), randomFloat(-10, 10))));
                            }
                            if (Calc.inRange(age, 100, 150, 50, 300) && age >= 300) {
                                Bullet.reset0();
                                Bullet.position0(thisB.position());
                                Bullet.type0(Bullet.Type.Outlined);
                                Bullet.color0(3);
                                for (double i = 0; i < 1; i += 1. / 20) {
                                    Bullet.velocity0(toXY(toF(toV(1, i * 2 * PI + toAngle(thisB.position(), Char.charPosition())))));
                                    new Bullet() {

                                        public void move() {
                                            changeSpeed(1, 30, mag(velocity()));
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                            if (Calc.inRange(age, 0, 60, 12, 300) && age >= 300) {
                                if (age % 300 == 0) {
                                    we = 0;
                                }
                                we++;
                                Bullet.reset0();
                                Bullet.position0(thisB.position());
                                Bullet.type0(Bullet.Type.Arrow);
                                Bullet.color0(2);
                                double rand = 0;
                                we2 = !we2;
                                for (double i = 0; i < 1; i += 1. / 60) {
                                    we2 = !we2;
                                    Bullet.velocity0(toXY(toF(toV((2.4f - .3f * we) * 1.5f, i * 2 * PI))));
                                    new Bullet() {

                                        public boolean we3 = we2;
                                        public int we4 = we;
                                        public float mag = mag(velocity());

                                        public void move() {
                                            if (age == 1) {
                                                w = .01f;
                                                if (we3) {
                                                    w = -.01f;
                                                }
                                            }
                                            if (age == 60) {
                                                w = 0;
                                            }
                                            this.changeSpeed(1, 60, .5f * mag);
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                            super.update();
                        }
                    });
            temp.add(
                    new SpellCard(this) {

                        {
                            maxHP = 3000;
                            thisT = thisT.SpellCard;
                        }
                        public int we = 0;

                        public void update() {
                            if (age == 1) {
                                thisB.moveToH(120, toV(semiWidth, 120));
                            }
                            if (age == 120) {
                                thisB.stop();
                            }
                            if (age % 300 == 0) {
                                thisB.moveToH(300, sum(toV(Char.charPosition()[0], 120), toV(randomFloat(-20, 20), randomFloat(-10, 10))));
                            }
                            if (Calc.inRange(age, 0, 180, 60, 900) && age >= 300) {
                                we++;
                                Bullet.reset0();

                                Bullet.type0(Bullet.Type.Outlined);
                                Bullet.color0(2);
                                Bullet.position0(position());
                                for (int i2 = -1; i2 <= 1; i2 += 2) {
                                    final int p2 = i2;
                                    Bullet.velocity0(rotate(toV(0, 2),i2*randomFloat((float)-PI/10,(float)PI/3) + i2* PI/2+0*toAngle(thisB.position(), Char.charPosition())));
                                    Bullet.acceleration0(toV(0,.02f));
                                    new Bullet() {

                                        public int we2 = we;
                                        public int p = p2;

                                        public void move() {
                                            if (Calc.inRange(age, 1, 0, 1, 11)&&age<=180) {
                                                Bullet.reset0();

                                                Bullet.type0(Bullet.Type.Pellet);
                                                Bullet.color0(1);
                                                
                                                Bullet.position0(position());
                                                for (double i = 0; i < 1; i += 1. / 11) {
                                                    Bullet.velocity0(toXY(toF(toV(1.5, p * (2 * PI * i + (age) * PI / 37 + we2 * PI / 4)))));
                                                    final float age1 = age;
                                                    new Bullet() {

                                                        public int age2 = (int)age1;

                                                        public void move() {
                                                            this.changeSpeed(1, 10, 0.001f);
                                                            if (age == 11) {
                                                                this.color = 8;
                                                            }
                                                            if (age == 211-age2) {
                                                                this.color = 7;
                                                                SE.KIRA.play();
                                                            }
                                                            this.changeSpeed(211-age2, 240-age2, min(1.f + 0*age2 * .005f, 4));

                                                            super.move();
                                                        }
                                                    }.add();
                                                }
                                            }
                                            super.move();
                                        }
                                    }.add();
                                }


                            }
                            super.update();
                        }
                    });
            this.add(temp);
            temp = new HPBar();
            temp.add(
                    new SpellCard(this) {

                        {
                            maxHP = 3000;
                            thisT = thisT.SpellCard;
                        }
                        double blah = 0;
                        final int d = 12;

                        public void update() {
                            if (age == 1) {
                                thisB.moveToH(120, toV(semiWidth, 120));
                            }
                            if (age == 120) {
                                thisB.stop();
                            }
                            if (age % 200 == 00) {
                                thisB.moveTo(200, sum(toV(semiWidth, 120), toV(randomFloat(-30, 30), randomFloat(-20, 20))));
                            }
                            if (Calc.inRange(age, 0, 360, 12, 600)) {
                                if (age % 600 == 0) {
                                    blah = 0;
                                }
                                blah += 1;
                                for (double i = 0; i < 1; i += 1. / 7) {

                                    for (int p = -1; p <= 1; p += 2) {
                                        double angle = blah * PI / 150;
                                        Bullet.reset0();
                                        Bullet.type0(Bullet.Type.Pellet);
                                        Bullet.position0(sum(thisB.position(), toXY(toF(toV(60 * cos(angle * 0) * 0 + 2 * blah + 30, PI / 2 + p * (angle + i * 2 * PI))))));
                                        Bullet.velocity0(toXY(toF(toV(2.1, p * (angle * 12 + i * 2 * PI + PI) + PI / 2))));
                                        Bullet.color0((int) 5);
                                        new Bullet().add();
                                        Bullet.velocity0(toXY(toF(toV(-1.5, p * (angle * 12 + i * 2 * PI + PI) + PI / 2))));
                                        Bullet.type0(Bullet.Type.Outlined);
                                        Bullet.color0((int) 6);
                                        new Bullet().add();
                                    }
                                }
                            }
                            super.update();
                        }
                    });

            temp.add(
                    new SpellCard(this) {

                        {
                            maxHP = 3000;
                            thisT = thisT.SpellCard;
                        }
                        boolean we = true;
                        boolean t = true;

                        public void update() {
                            if (age == 1) {
                                thisB.moveToH(120, toV(semiWidth, 120));
                            }
                            if (age == 120) {
                                thisB.stop();
                            }
                            if (age % 200 == 00) {
                                //thisB.moveTo(200, sum(toV(semiWidth,120),toV(randomFloat(-30,30),randomFloat(-20,20))));
                            }
                            if (Calc.inRange(age, 0, 0, 1, 240)) {
                                t = !t;

                                for (double i = 0; i < 1; i += 1. / 5) {

                                    for (double p = 0; p < 1; p += 1. / 36) {
                                        we = !we;
                                        double angle = 2 * PI * p + i * PI / 24;
                                        if (!t) {
                                            angle = -angle;
                                        }
                                        final double speed = 1 + .7 * i;
                                        Bullet.reset0();
                                        Bullet.type0(Bullet.Type.Pellet);
                                        Bullet.color0(6);
                                        Bullet.position0(thisB.position());
                                        Bullet.type0(Bullet.Type.Arrow);
                                        Bullet.velocity0(toXY(toF(toV(speed, angle))));
                                        Bullet.velocity0(toXY(toF(toV(1.8f * speed, angle))));
                                        new Bullet() {

                                            float speed0 = (float) speed;

                                            public void move() {
                                                this.changeSpeed(10, 30, speed0);
                                                super.move();
                                            }
                                        }.add();
                                        {
                                            Bullet.color0(1);
                                            new Bullet() {

                                                float speed0 = (float) speed;
                                                int p = -1;

                                                {
                                                    if (t) {
                                                        p = 1;
                                                    }
                                                }

                                                public void move() {

                                                    if (age == 1) {
                                                        w = (float) -PI / 45 * p;
                                                    }
                                                    if (age == 45) {
                                                        w = (float) -PI / 90 * p;
                                                    }
                                                    if (age == 90) {
                                                        w = (float) 0;
                                                        SE.KIRA.play();
                                                    }
                                                    //this.changeSpeed(30, 90,speed0*1.8f);


                                                    super.move();

                                                }
                                            }.add();
                                        }
                                    }
                                }
                            }
                            super.update();
                        }
                    });
            temp.add(
                    new SpellCard(this) {

                        {
                            maxHP = 3000;
                            thisT = thisT.SpellCard;
                        }
                        final int layer = 14;
                        final int ring = 20;
                        int t = 1;

                        public void update() {
                            if (age == 1) {
                                thisB.moveToH(120, toV(semiWidth, 120));
                            }
                            if (age == 120) {
                                thisB.stop();
                            }
                            if (age % 200 == 00) {
                                //thisB.moveTo(200, sum(toV(semiWidth,120),toV(randomFloat(-30,30),randomFloat(-20,20))));
                            }
                            if (Calc.inRange(age, 0, 210, 105, 420)) {
                                t = -t;


                                Bullet.reset0();
                                Bullet.position0(thisB.position());
                                for (int r = 0; r < ring; r++) {
                                    for (int i = 0; i < layer; i += 1) {
                                        final double angle = t * (2 * PI * r / ring + 2 * PI / ring * i / layer)+PI+Calc.toAngle(thisB.position(), Char.charPosition());
                                        final int layer0 = i;
                                        final double newangle = angle + t * (PI / 2 - PI / 2 *(((double)(i)/layer*(2*r-ring)+ring)%1) );
                                        Bullet.velocity0(toXY(toF(toV(3, angle))));
                                        new Bullet() {

                                            double angle0 = newangle;
                                            double t0 = t;
                                            

                                            public void move() {
                                                this.changeSpeed(1, 60, 0);
                                                this.changeSpeed(61, 150, toXY(toF(toV(1.8, angle0))));
                                                if(age==61)
                                                
                                                {
                                                    SE.KIRA.play();
                                                }
                                                super.move();
                                            }
                                        }.add();

                                    }

                                }
                            }
                            super.update();
                        }
                    });
            temp.add(
                    new SpellCard(this) {

                        {
                            maxHP = 1000000;
                            thisT = thisT.SpellCard;
                        }
                        final int layer = 5;
                        final int ring = 4;
                        final int thick = 14;
                        int p = 1;

                        public void update() {
                            if (age == 1) {
                                thisB.moveToH(120, toV(semiWidth, 120));
                            }
                            if (age == 120) {
                                thisB.stop();
                            }
                            if (age % 200 == 00) {
                                //thisB.moveTo(200, sum(toV(semiWidth,120),toV(randomFloat(-30,30),randomFloat(-20,20))));
                            }
                            if (Calc.inRange(age, 0, 180, 90, 480)) {
                                p = -p;


                                Bullet.reset0();
                                Bullet.position0(thisB.position());
                                for (int r = 0; r < ring; r++) {
                                    for (int t = 0; t < thick; t++) {
                                        for (int i = 0; i < layer; i += 1) {
                                            final double angle = Calc.toAngle(thisB.position(),Char.charPosition())+PI+p*PI/ring/3+ p * (2 * PI * r / ring + 2 * PI / ring * t / thick + 2 * PI / ring / thick * i/layer);
                                            final int i0 = i;
                                            final int t0 = t;
                                            Bullet.velocity0(toXY(toF(toV(3, angle))));
                                                    Bullet.color0(6);
                                            //Bullet.color0(i+1);
                                            new Bullet() {

                                                double angle0 = angle;
                                                double p0 = p;
                                                final int layer0 = (i0*2)%layer;
                                                
                                                public void move() {
                                                    this.changeSpeed(1, 60, 0);
                                                    this.changeSpeed(61, 150, toXY(toF(toV(1.6+.8*((double)layer0*2/layer-1)*pow(sin(2*PI*(layer0+t0*layer)/(layer*thick)),2), angle0))));
                                                    if(age==61)
                                                
                                                {
                                                    SE.KIRA.play();
                                                }
                                                    super.move();
                                                }
                                            }.add();

                                        }
                                    }
                                }
                            }
                            super.update();
                        }
                    });
            this.add(temp);
        }

        @Override
        public void formScript() {
            this.toActive();
            BGM.S3B.play();
        }

        @Override
        public void fadeScript() {
        }
    }*/
}
