
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

public class YukunAttack extends BossScript {

    int moveDistance;
    int breakTime;
    int sign;
    int bulletNumber;
    int xRank;
    int yRank;
    int outpostNumber;
    int gap;
    float xDistance = 60;
    float yDistance = 30;

    public void start() {
        super.initializeBoss();
        moveDistance = 60;
        breakTime = 150;
        gap = 50;
        //bulletNumber = 30;
        xRank = 4;
        yRank = 3;
        sign = 1;
        outpostNumber = 1;
        switch (difficulty) {
            case LUNATIC:
                xDistance = 60;
                yDistance = 40;
                moveDistance = 60;
                breakTime = 150;
                
                outpostNumber = 3;
                //bulletNumber = 30;
                xRank = 6;
                yRank = 2;
                break;
            case HARD:
                xDistance = 60;
                yDistance = 40;
                moveDistance = 60;
                breakTime = 180;
                
                outpostNumber = 2;
                //bulletNumber = 30;
                xRank = 5;
                yRank = 2;
                break;
            case NORMAL:
                
                xDistance = 60;
                yDistance = 40;
                moveDistance = 60;
                breakTime = 180;
                
                outpostNumber = 1;
                //bulletNumber = 30;
                xRank = 4;
                yRank = 5;
                break;
            case EASY:
                xDistance = 60;
                yDistance = 40;
                moveDistance = 60;
                breakTime = 180;
                outpostNumber = 0;
                xRank = 4;
                yRank = 5;
                break;
        }
    }

    public void fill() {
        this.declare(semiHeight*.75, 8000);
        this.addLoopStart();
        this.moveBossTowardsPlayer(toV(60, 120), breakTime / 2, moveDistance, 60);

        this.addPause(breakTime / 2);
        this.actionArray.add(
                new Action() {

                    public void start() {
                        sign *= -1;
                        float rand = randomFloat(0, 2 * (float) PI);
                        final float range = 15;
                        for (int sign2 = -1; sign2 <= 1; sign2 += 2) {
                            Bullet.reset0();
                            Bullet.velocity0(toV(sign2 * 10, 0));

                            Bullet.color0(Col.CYAN);
                            Bullet.position0(sum(thisBoss.position(), toV(sign2 * gap, 0)));
                            Bullet.type0(Bullet.Type.MetalF);
                            new Bullet() {

                                public void move() {
                                    super.move();
                                    if (age % 2 == 0) {
                                        for (int i = 0; i < 4; i++) {
                                            float[] position = sum(this.position(), toXY(toV(0, randomFloat(0, 2 * (float) PI))));
                                            Bullet.reset0();
                                            Bullet.position0(position);
                                            Bullet.velocity0(sum(toV(0, 1), toXY(toV(.2f, randomFloat(0, 2 * (float) PI)))));
                                            Bullet.color0(Col.CYAN);
                                            Bullet.type0(Bullet.Type.Outlined);
                                            new Bullet().add();
                                            
                                            Bullet.velocity0(sum(toV(0, -1), toXY(toV(.9f, randomFloat(0, 2 * (float) PI)))));
                                            new Bullet().add();
                                        }
                                    }
                                }
                            }.add();
                        }
                    }
                });

        this.stopBoss();

        this.addPause(breakTime / 4);
        this.actionArray.add(
                new Action() {

                    public void start() {
                        Bullet.reset0();
                        Bullet.position0(thisBoss.position());
                        Bullet.color0(Col.RED);
                        Bullet.type0(Bullet.Type.Medium);
                        final float time = 30;
                        for (float x = -1; x <= 1; x += (2. / (xRank - 1))) {
                            for (float y = -1; y <= 1; y += (2. / (yRank - 1))) {
                                float[] velocity = sum(toV(0, 3), product(toV((x * (1 - .2f * y)) * xDistance, y * yDistance), 2. / time));
                                float[] acceleration = product(velocity, -1. / time);
                                Bullet.velocity0(velocity);
                                Bullet.acceleration0(acceleration);
                                new Bullet() {

                                    public void move() {
                                        super.move();
                                        if (age == time) {
                                            velocity1(toV(0, 1));
                                            acceleration1(toV(0, 0));
                                        }
                                    }
                                }.add();
                            }
                        }
                        
                        Bullet.reset0();
                        Bullet.position0(thisBoss.position());
                        Bullet.color0(Col.BROWN);
                        Bullet.type0(Bullet.Type.MetalF);
                        for(int sign = -1;sign<=1;sign+=2){
                            for(int x = 0;x<outpostNumber;x++)
                            {
                            float[] velocity = sum(toV(0, 1), product(toV(sign*(x * 30+2f*gap), 1), 2. / time));
                            final int x0 = x;
                                float[] acceleration = product(velocity, -1. / time);
                                Bullet.velocity0(velocity);
                                Bullet.acceleration0(acceleration);
                                new Bullet(){
                                    int period = 240;
                                    public void move() {
                                        super.move();
                                        if (age == time) {
                                            velocity1(toV(0, 1));
                                            acceleration1(toV(0, 0));
                                        }
                                        if(age>=time&&(age-time)%period==period*x0/outpostNumber){
                                            float angle = Char.getAngle(this.x, this.y);
                                            Bullet.reset0();
                                            Bullet.position0(this.position());
                                            Bullet.color0(Col.GRAY);
                                            Bullet.type0(Bullet.Type.Arrow);
                                            for(double a = -1;a<=1;a+=2./(3-1))
                                            {
                                                
                                            Bullet.velocity0(toXY(toV(1f,(float)(angle+0*a*PI/4))));
                                            new Bullet().add();
                                            }
                                        }
                                    }
                                }.add();
                            }
                        }
                    }
                });
        this.addPause(breakTime / 4);
        this.addLoopEnd();
    }
}
