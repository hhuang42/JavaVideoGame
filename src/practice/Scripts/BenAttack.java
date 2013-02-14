/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice.Scripts;

import practice.*;
import static java.lang.Math.*;
import java.util.ArrayList;
import practice.Basic.BasicScript;
import static practice.Calc.*;

/**
 *
 * @author Henry
 */
public class BenAttack extends BossScript {
    {
        String[] nameSet = {"Dark Firaga - E","Dark Firaga - N",
            "Dark Barrage - H","Dark Barrage - L"};
        names = nameSet;
    }

    int bulletNumber;
    int attackNumber;
    float radius = 30;
    boolean darkFiraga;
    boolean darkBarrage;
    int moveDistance;

    public void start() {
        super.initializeBoss();
        //difficulty = difficulty.NORMAL;
        bulletNumber = 8;
        radius = 30;
        moveDistance = 60;
        darkFiraga = false;
        darkBarrage = false;
        switch (difficulty) {
            case LUNATIC:
                
                bulletNumber = 12;
                radius = 15;
                darkBarrage = true;
                break;
            case HARD:
                
                bulletNumber = 9;
                radius = 12;
                darkBarrage = true;
                break;
            case NORMAL:
                bulletNumber = 12;
                radius = 25;
                darkFiraga = true;
                break;
            case EASY:
                bulletNumber = 8;
                radius = 20;
                darkFiraga = true;
                break;
        }
    }

    public void fill() {
        this.declare(60, 6000);
        this.addLoopStart();

        this.addPause(30);
        //this.moveBossTowardsPlayer(120, 120, 60);
        
        for(int sign = -1;sign<=1;sign+=2)
        {
            for (double t = 0; t <= 1; t += 1. / (5-1)) {
            this.actionArray.add(
                    new Action() {

                        public void start() {
                            if(darkFiraga)
                            {
                            float angle = Char.getAngle(thisBoss.x, thisBoss.y);
                            
                            Bullet.reset0();
                            Bullet.position0(thisBoss.position());
                            Bullet.velocity0(toXY(toV(1.5f, angle)));
                            Bullet.acceleration0(toXY(toV(.06f, angle)));
                            new DarkFiraga().add();
                            }
                            else
                            {
                                float angle = Char.getAngle(thisBoss.x, thisBoss.y);
                            
                                Bullet.reset0();
                                Bullet.position0(toV(Char.charPosition()[0],-30));
                                Bullet.velocity0(toV(0,.5f));
                                Bullet.acceleration0(toV(0,.12f));
                                new DarkBarrage().add();
                            }
                        }
                    });
            
            this.moveBossTowardsPlayer(60, 30, moveDistance/10,60);
            this.addPause(30);
        }
        
        this.moveBossTowardsPlayer(60, 150, moveDistance,60);
        this.addPause(150);
        this.stopBoss();
        for (double t = 0; t <= 1; t += 1. / (5-1)) {
            
            final float angle = (float)(PI*(.8f*sign*(t-.5f)));
            final float xPosition = (float)(scrWidth*(.5f+.8f*sign*(t-.5f)));
            this.actionArray.add(
                    new Action() {

                        public void start() {
                            
                            if(darkFiraga){
                                
                            float c = Char.getAngle(thisBoss.x, thisBoss.y);
                            Bullet.reset0();
                            Bullet.position0(thisBoss.position());
                            Bullet.velocity0(toXY(toV(2f, angle+c)));
                            Bullet.acceleration0(toXY(toV(.04f, angle+c)));
                            new DarkFiraga().add();
                            }
                            else
                            {
                                Bullet.reset0();
                                Bullet.position0(toV(xPosition,-30));
                                Bullet.velocity0(toV(0,.5f));
                                Bullet.acceleration0(toV(0,.12f));
                                new DarkBarrage().add();
                            }
                        }
                    });
            this.moveBossTowardsPlayer(60, 30, moveDistance/10,60);
            this.addPause(30);
        }
        this.moveBossTowardsPlayer(60, 150, moveDistance,60);
        this.addPause(150);
        this.stopBoss();

        }

        this.addLoopEnd();
    }
    public class DarkFiraga extends Bullet{
        {
            
                            
                            shoot(SE.ESHOOT0);
                            type1(Bullet.Type.MetalF);
                            color1(Col.CYAN);
        }
        
        public void move() {
                                    super.move();


                                    if (age % 5 == 0) {
                                        for(int t = 0;t<bulletNumber;t++){
                                            final float i = randomFloat(-1,1);
                                            
                                            
                                            float angle = Calc.toAngle(toV(0,0), velocity()) +(float)PI/2*i;
                                            float[] offset = toXY(toV(radius, angle));
                                            float speed = .1f+.9f*(float)pow(randomFloat(0, 1f),.5);
                                            Bullet.reset0();
                                            Bullet.delay0(30);
                                            Bullet.position0(sum(this.position(),offset));
                                            Bullet.velocity0(sum(product(velocity(), speed), toXY(toV(-radius/75, angle))));
                                            Bullet.type0(Bullet.Type.Medium);
                                            Bullet.color0(abs(i) < .8 ? Col.CYAN : abs(i) > .8 ? Col.PURPLE : Col.BLUE);
                                            new Bullet() {

                                                public void move() {
                                                    super.move();
                                                    if (age == 55/2) {
                                                        
                                                        color1(abs(i) < .3 ? Col.CYAN : abs(i) > .9 ? Col.BLACK :abs(i) > .7 ? Col.PURPLE:Col.BLUE);
                                                        //color1(randomFloat(0, 1) < .2 ? Col.CYAN : randomFloat(0, 1) > .5 ? Col.PURPLE : Col.BLUE);
                                                        type1(Type.Small);
                                                    }
                                                }
                                            }.add();
                                        }
                                    }
                                }
        
        
    }
    public class DarkBarrage extends Bullet{
        int sign = randomFloat(0,1)>.5?-1:1;
        {
                            shoot(SE.ESHOOT0);
                            type1(Bullet.Type.DarkPellet);
                            color1(Col.BLUE);
                            tolerance1(40);
        }
        
        public void move() {
                                    super.move();


                                    if (age % 5 == 0) {
                                        for(int t = 0;t<bulletNumber;t++){
                                            final float i = randomFloat(-1,1);
                                            
                                            
                                            float angle = Calc.toAngle(toV(0,0), velocity()) +(float)PI/2*i;
                                            float[] offset = product(toXY(toV(radius, angle)),toV(1,4));
                                            float speed = .1f+.9f*(float)pow(randomFloat(0, 1f),.3);
                                            Bullet.reset0();
                                            Bullet.delay0(10);
                                            Bullet.position0(sum(this.position(),offset));
                                            Bullet.velocity0(sum(product(velocity(), speed), product(offset,-1./60)));
                                            Bullet.type0(this.type);
                                            Bullet.color0(i*sign < .5 ?(randomFloat(0,1)<1? Col.BLUE:Col.BLACK) : (randomFloat(0,1)<1? Col.BLUE:Col.BLACK));
                                            new Bullet() {

                                                public void move() {
                                                    super.move();
                                                    if(age==(int)(10+15*abs(i))){
                                                        color1(Col.RED);
                                                    }
                                                    if (age == 55/2) {
                                                        this.type1(Type.Pellet);
                                                        color1(abs(i) < .3 ? Col.BLACK : abs(i) < .6 ? Col.BLUE : Col.PURPLE);
                                                    }
                                                }
                                            }.add();
                                        }
                                    }
                                }
        
        
    }
}
