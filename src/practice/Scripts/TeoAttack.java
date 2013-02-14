
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice.Scripts;

/**
 *
 * @author Henry
 */
import practice.*;
import static java.lang.Math.*;
import java.util.ArrayList;
import practice.Basic.BasicScript;
import static practice.Calc.*;

public class TeoAttack extends BossScript {

    int moveDistance;
    int breakTime;
    int sign;
    int bulletNumber;
    int count;
    float speed;
    float bulletReach;
    int arm;
    int depth;
    float height;
    Col[] colorList;
    Action diff;
    
    

    public void start() {
        super.initializeBoss();
        moveDistance = 0;
        breakTime = 75;
        count = 0;
        bulletNumber = 25;
        speed = 2f;
        bulletReach = 120;
        sign = 1;
        height = 180;
        
        switch (difficulty) {
            case LUNATIC:
                
        height = 180;
        breakTime = 55;
        speed = 1.5f;
        bulletNumber = 20;
        
        bulletReach = 165;
                break;
            case HARD:
                
        height = 120;
        breakTime = 120;
        speed = 1.5f;
        bulletNumber = 40;
        //bulletReach = 300;
                break;
            case NORMAL:
                
        height = 180;
        breakTime = 60;
        speed = 1.f;
        bulletReach = 300;
        arm = 6;
        depth = 8;
        bulletNumber = depth*arm;
                break;
            case EASY:
                
        height = 180;
        breakTime = 60;
        bulletNumber = 25;
        speed = 1.5f;
        bulletReach = 120;
                break;
        }
        
        Action easy = new Script.Action() {

                    public void start() {
                        sign *= -1;
                        float rand = randomFloat(0,2*(float)PI);
                        Bullet.reset0();
                        Bullet.type0(Bullet.Type.MetalF);
                        Bullet.position0(thisBoss.position());
                                for (double t = 0; t < 1; t += 1. / bulletNumber) {
                                    float angle = (float)(2*PI*t)+rand;
                                    Bullet.velocity0(toXY(toV(speed,angle)));
                                    Bullet.tolerance0(300);
                                    for(sign = -1;sign<=1;sign+=2)
                                    {
                                        final int s = sign;
                                        Bullet.color0(sign==-1?Col.RED:Col.GREEN);
                                    new Bullet(){
                                        int sign = s;
                                        public void move(){
                                            super.move();
                                            if(age==bulletReach/speed){
                                                w= .01f*sign;
                                            }
                                            if(age==bulletReach/speed+30){
                                                w=0;
                                            }
                                        }
                                    }.add();
                                    }
                            }
                            }
                };
        
        Action normal = new Script.Action() {

                    public void start() {
                        sign *= -1;
                        float rand = randomFloat(0,2*(float)PI);
                        Bullet.reset0();
                        Bullet.type0(Bullet.Type.Outlined);
                        Bullet.position0(thisBoss.position());
                                for (double t = 0; t < 1; t += 1. / bulletNumber) {
                                    
                        count= (count+sign+depth)%depth;
                                    float angle = (float)(2*PI*t)+rand;
                                    Bullet.velocity0(toXY(toV(speed,angle)));
                                        Bullet.color0(sign==-1?Col.RED:Col.GREEN);
                                    new Bullet(){
                                        double ratio = .1+.9*(1-(float)count/depth);
                                        public void move(){
                                            super.move();
                                            if(age==(int)(bulletReach/speed*ratio)+1){
                                                velocity1(norm(velocity(),.0001f));
                                            }
                                            if(age==(int)(bulletReach/speed*ratio)+1+120){
                                                acceleration1(norm(velocity(),.015f));
                                            }
                                        }
                                    }.add();
                                    
                            }
                            }
                };
        Action hard = new Script.Action() {

                    public void start() {
                        sign *= -1;
                        float rand = randomFloat(0,2*(float)PI);
                        Bullet.reset0();
                        Bullet.type0(Bullet.Type.MetalF);
                        Bullet.position0(thisBoss.position());
                                for (double t = 0; t < 1; t += 1. / bulletNumber) {
                                    float angle = (float)(2*PI*t)+rand;
                                    Bullet.velocity0(toXY(toV(speed,angle)));
                                    Bullet.tolerance0(0);
                                        Bullet.color0(sign==-1?Col.RED:Col.GREEN);
                                    new Bullet(){
                                        public void move(){
                                            super.move();
                                            if(Calc.outBound(x, y, 0)){
                                                Bullet.reset0();
                                                Bullet.position0(this.position());
                                                Bullet.color0(this.color);
                                                Bullet.shoot0(SE.KIRA);
                                                Bullet.type0(Bullet.Type.Medium);
                                                float[] velocity = product(velocity(),toV(1,-1));
                                                for(int sign = -1;sign<=1;sign+=2){
                                                    Bullet.velocity0(norm(velocity,.5f*sign));
                                                    new Bullet().add();
                                                }
                                                /*new Bullet(){
                                                    public void move(){
                                                        super.move();
                                                        if(age==60){
                                                            this.velocity1(toXY(toV(1,Char.getAngle(x, y))));
                                                        }
                                                    }
                                                }.add();*/
                                            }
                                        }
                                    }.add();
                                    
                            }
                            }
                };
        
        Action lunatic = new Script.Action() {

                    public void start() {
                        sign *= -1;
                        float rand = randomFloat(0,2*(float)PI);
                        Bullet.reset0();
                        Bullet.type0(Bullet.Type.Outlined);
                        Bullet.position0(thisBoss.position());
                                for (double t = 0; t < 1; t += 1. / bulletNumber) {
                                    float angle = (float)(2*PI*t)+rand;
                                    Bullet.velocity0(toXY(toV(speed,angle)));
                                   // Bullet.tolerance0(300);
                                    for(int sign2 = -1;sign2<=1;sign2+=2)
                                    {
                                        final int s = sign2;
                                        Bullet.color0(sign==sign2?Col.RED:Col.GREEN);
                                        for(double i = 0;i<1;i+=1./3
                                                )
                                        {
                                            final double i2 = 1-i;
                                    new Bullet(){
                                        int sign = s;
                                        public void move(){
                                            super.move();
                                            if(age==(int)(bulletReach/speed*i2)){
                                                w= .05f*sign;
                                            }
                                            if(age==(int)(bulletReach/speed*i2)+60){
                                                w=0;
                                            }
                                        }
                                    }.add();
                                        }
                                    }
                            }
                            }
                };
        switch (difficulty) {
            case LUNATIC:
                diff = lunatic;
                break;
            case HARD:
                diff = hard;
                break;
            case NORMAL:
                diff = normal;
                break;
            case EASY:
                diff = easy;
                break;
        }
    }

    public void fill() {
        
        /*Action lunatic = new Script.Action() {

                    public void start() {
                        sign *= -1;
                        float rand = randomFloat(0,2*(float)PI);
                        Bullet.reset0();
                        Bullet.type0(Bullet.Type.MetalF);
                        Bullet.position0(thisBoss.position());
                                for (double t = 0; t < 1; t += 1. / bulletNumber) {
                                    float angle = (float)(2*PI*t)+rand;
                                    Bullet.velocity0(toXY(toV(speed,angle)));
                                        Bullet.color0(sign==-1?Col.RED:Col.GREEN);
                                    new Bullet(){
                                        public void move(){
                                            super.move();
                                            if(Calc.outBound(x, y, 9)){
                                                this.status=Status.Fade;
                                                Bullet.reset0();
                                                Bullet.position0(diff(this.position(),toV(scrWidth,scrHeight)));
                                                Bullet.color0(this.color);
                                                Bullet.shoot0(SE.KIRA);
                                                Bullet.type0(Bullet.Type.Medium);
                                                Bullet.velocity0(product(this.velocity(),2));
                                                new Bullet().add();
                                            }
                                        }
                                    }.add();
                                    
                            }
                            }
                };*/
        this.declare(120,10000);
        this.actionArray.add(
                new Action(this){
                    public void start(){
                        thisBoss.moveTo(40, Char.charPosition());
                    }
                }
                );
        this.addPause(40);
        this.stopBoss();
        
        this.addPause(20);
        this.moveBoss(Script.semiWidth, height, 60);
        this.addPause(60);
        this.stopBoss();
        this.addLoopStart();
                this.addPause(breakTime / 2);
                this.moveBossTowardsPlayer(toV(90,120), breakTime/2, moveDistance, 60);

                this.addPause(breakTime / 2);
        this.actionArray.add(diff
                );

        this.stopBoss();

        this.addLoopEnd();
    }
}
