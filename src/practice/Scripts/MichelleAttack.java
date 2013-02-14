
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

public class MichelleAttack extends BossScript {

    int moveDistance;
    int breakTime;
    int count;
    int sign;
    int bulletNumber;
    int bulletNumber2;
    int returnPeriod;
    float speed;
    float dAngle;
    float height;
    float w0;
    Action diff;

    public void start() {
        super.initializeBoss();
        moveDistance = 0;
        bulletNumber = 10;
        count = 0;
        sign = 1;
        height = 180;
        breakTime = 60;
        speed = 1.5f;
        dAngle = (float) .03;
        w0 = 0f;
        switch (difficulty) {
            case LUNATIC:


                bulletNumber = 15;
                returnPeriod = 20;
                bulletNumber2 = 30;
                breakTime = 10;
                speed = 1.0f;
                dAngle = (float) .035;
                w0 = -.0035f;
                break;
            case HARD:

                bulletNumber = 15;
                returnPeriod = 20;
                bulletNumber2 = 20;
                breakTime = 20;
                speed = .9f;
                dAngle = (float) .025;
                w0 = -.0035f;
                break;
            case NORMAL:
                bulletNumber = 10;
                returnPeriod = 15;
                bulletNumber2 = 20;
                breakTime = 20;
                speed = 1f;
                dAngle = (float) .05;
                break;
            case EASY:
                bulletNumber = 10;
                returnPeriod = 15;
                bulletNumber2 = 10;
                breakTime = 40;
                speed = .5f;
                dAngle = (float) .04;
                break;
        }
    }

    public void fill() {


        diff = new Script.Action() {

            public void start() {
                count++;
                sign *= -1;
                float rand = randomFloat(0, 2 * (float) PI);
                Bullet.reset0();
                Bullet.type0(Bullet.Type.Medium);
                final float distance = (float) (w0==0?400:(sqrt(2)/2*speed / cos((PI + w0) / 2)));
                Bullet.tolerance0((int) distance);
                Bullet.position0(thisBoss.position());
                for (double t = 0; t < 1; t += 1. / bulletNumber) {

                    float angle = (float) (2 * PI * t) + sign * (count + .5f) * dAngle;

                    Bullet.position0(sum(thisBoss.position(), toXY(toV(distance, angle))));
                    Bullet.velocity0(toXY(toV(-speed, angle+(w0==0?0:(float)PI/4*sign))));
                    final int s = sign;
                    Bullet.w0(w0 * sign);
                    Bullet.color0(sign == -1 ? Col.YELLOW : Col.BLUE);
                    new Bullet() {
                        public void move() {
                            super.move();
                            if((w==0&&age==(int)(distance/speed))||(w!=0&&age == (int)abs(PI/2/w0))) {
                                this.status=Status.Fade;
                            }
                        }
                    }.add();

                }

                if (count % returnPeriod == 1) {
                    Bullet.reset0();
                    Bullet.type0(Bullet.Type.MetalF);
                    final float newSpeed = .3f;
                    for (double t = 0; t < 1; t += 1. / bulletNumber2) {
                        float angle = (float) (2 * PI * t) + sign * (count + .5f) * dAngle;

                        Bullet.position0(thisBoss.position());
                        Bullet.velocity0(toXY(toV(newSpeed, angle)));
                        Bullet.color0(Col.GRAY);
                        new Bullet() {

                            public void move() {
                                super.move();
                            }
                        }.add();

                    }
                }
            }
        };
        this.declare(height, 10000);
        this.addLoopStart();
        this.addPause(breakTime / 2);
        this.moveBossTowardsPlayer(toV(90, 120), breakTime / 2, moveDistance, 60);

        this.addPause(breakTime / 2);
        this.actionArray.add(diff);

        this.stopBoss();

        this.addLoopEnd();
    }
}
