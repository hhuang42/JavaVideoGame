
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

public class DaEunAttack extends BossScript {
    {
        String[] nameSet = {"Pizzicato","Vibrato","Tremelo","Arpeggio"};
        names = nameSet;
    }

    int moveDistance;
    int breakTime;
    int sign;
    int bulletNumber;
    boolean pizzicato;
    boolean vibrato;
    boolean tremelo;

    public void start() {
        super.initializeBoss();
        moveDistance = 30;
        breakTime = 120;
        bulletNumber = 30;
        sign = 1;
        vibrato = false;
        pizzicato = false;
        tremelo = false;
        switch (difficulty) {
            case LUNATIC:
                break;
            case HARD:
                tremelo = true;
        moveDistance = 30;
        breakTime = 180;
        bulletNumber = 25;
                break;
            case NORMAL:
                vibrato = true;
                moveDistance = 30;
        breakTime = 120;
        bulletNumber = 20;
                break;
            case EASY:
                
        moveDistance = 15;
                breakTime = 45;
                bulletNumber = 20;
                pizzicato = true;
                break;
        }
    }

    public void fill() {
        this.declare(.5*semiHeight, 10000);
        this.addLoopStart();
                this.addPause(breakTime / 2);
                this.moveBossTowardsPlayer(toV(90,150), breakTime/2, toV(moveDistance,moveDistance*1.5), 60);

                this.addPause(breakTime / 2);
        this.actionArray.add(
                new Action() {

                    public void start() {
                        sign *= -1;
                        float rand = randomFloat(0, 2 * (float) PI);

                        for (double t = 0; t < 1; t += 1. / bulletNumber) {
                            float angle = rand + (float) (t * 2 * PI);
                            if (pizzicato) {
                                float offAngle = (float) (PI * .8);
                                float[] velocity = toXY(toV(3, angle + sign * offAngle));

                                Bullet.reset0();
                                Bullet.tolerance0(100);
                                Bullet.position0(sum(thisBoss.position(), toXY(toV(40, angle))));
                                Bullet.type0(Bullet.Type.Medium);
                                Bullet.color0(Col.YELLOW);
                                Bullet.velocity0(velocity);
                                Bullet.acceleration0(product(velocity, -.02));
                                new Bullet() {

                                    public void move() {
                                        super.move();
                                        if (age == 30) {
                                            acceleration1(toV(0, 0));

                                        }
                                    }
                                }.add();
                            } else if(vibrato) {
                                final int releaseTime = 60;
                                Bullet.reset0();
                                Bullet.tolerance0(50);
                                Bullet.position0(thisBoss.position());
                                Bullet.type0(Bullet.Type.Outlined);
                                Bullet.color0(Col.YELLOW);
                                Bullet.velocity0(toXY(toV(4, angle)));
                                Bullet.w0(sign*2 * (float) (PI*3 /4/ releaseTime));
                                new Bullet() {

                                    public void move() {
                                        super.move();
                                        if (age % 5 == 0 && age < releaseTime) {
                                            Bullet.reset0();
                                            
                                Bullet.tolerance0(50);
                                            Bullet.position0(this.position());
                                            Bullet.type0(Bullet.Type.Pellet);
                                            Bullet.color0(Col.PURPLE);
                                            for (double a = 0; a < 1; a += 1. / 1) {
                                                Bullet.velocity0(Calc.rotate(norm(velocity(), -1f), a * 2 * PI));
                                                new Bullet().add();
                                            }
                                        }
                                        if (age == releaseTime) {
                                            w = 0;
                                            velocity1(norm(velocity(),.5f));
                                        }
                                    }
                                }.add();

                            }else if (tremelo) {
                                final int releaseTime = 180;
                                Bullet.reset0();
                                Bullet.tolerance0(50);
                                Bullet.position0(thisBoss.position());
                                Bullet.type0(Bullet.Type.MetalF);
                                Bullet.color0(Col.YELLOW);
                                Bullet.velocity0(toXY(toV(.8f, angle)));
                                Bullet.w0(sign*2 * (float) (PI*2 /4/ releaseTime));
                                new Bullet() {
                                    int sign2 = -1;
                                    public void move() {
                                        super.move();
                                        if (age % 8 == 0 && age < releaseTime) {
                                            sign2 = -sign2;
                                            Bullet.reset0();
                                Bullet.tolerance0(50);
                                            Bullet.position0(this.position());
                                            Bullet.type0(Bullet.Type.Medium);
                                            if(sign2==1)
                                            {
                                            Bullet.color0(Col.BLUE);
                                            }
                                            else{
                                                Bullet.color0(Col.GREEN);
                                            }
                                            for (double a = 0; a < 1; a += 1. / 1) {
                                                Bullet.velocity0(Calc.rotate(norm(velocity(), -1.5f),PI/2-sign2*PI/2));
                                                new Bullet().add();
                                            }
                                        }
                                        if (age == releaseTime) {
                                            w = 0;
                                            velocity1(norm(velocity(),.5f));
                                        }
                                    }
                                }.add();

                            } else{
                                final float v0 = 3f;
                                final int releaseTime = 90;
                                Bullet.reset0();
                                Bullet.tolerance0(50);
                                Bullet.position0(thisBoss.position());
                                Bullet.type0(Bullet.Type.MetalF);
                                if(sign==1)
                                {
                                Bullet.color0(Col.YELLOW);
                                }
                                else{
                                    Bullet.color0(Col.PURPLE);
                                }
                                Bullet.velocity0(toXY(toV(v0, angle)));
                                Bullet.w0(sign*2 * (float) (PI*10 /4/ releaseTime));
                                new Bullet() {
                                    int sign2 = sign;
                                    float vMag = v0;
                                    public void move() {
                                        super.move();
                                        
                                            vMag+=.2f;
                                        if (age % 8 == 0 && age < releaseTime) {
                                            velocity1(norm(velocity(),vMag));
                                            Bullet.reset0();
                                Bullet.tolerance0(50);
                                            Bullet.position0(this.position());
                                            Bullet.type0(Bullet.Type.Medium);
                                            if(sign2==1)
                                            {
                                            Bullet.color0(Col.BLUE);
                                            }
                                            else{
                                                Bullet.color0(Col.ORANGE);
                                            }
                                            for (double a = 0; a < 1; a += 1. / 1) {
                                                Bullet.velocity0(Calc.rotate(product(velocity(), -.1f), -sign2*PI/2));
                                                new Bullet().add();
                                            }
                                        }
                                        if (age == releaseTime) {
                                            w = 0;
                                            velocity1(norm(velocity(),.6f));
                                        }
                                    }
                                }.add();

                            }
                        }
                    }
                });

        this.stopBoss();

        this.addLoopEnd();
    }
}
