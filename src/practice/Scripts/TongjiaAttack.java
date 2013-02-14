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

public class TongjiaAttack extends BossScript {
    {
        String[] nameSet = {"砂鉄結襲 - E","砂鉄結襲 - N","砂鉄界法 - H","砂鉄界法 - L"};
        names = nameSet;
    }
    int moveDistance;
    int breakTime;
    int sign;
    int round;
    int maxRound;
    int pauseTime;
    int bulletNumber;
    int sandCharge;
    boolean gathering;
    float accel;
    double angle;

    public void start() {
        super.initializeBoss();
        moveDistance = 60;
        breakTime = 360;
        bulletNumber = 4;
        sandCharge = 60;
        sign = 1;
        pauseTime = 6;
        maxRound = 17;
        gathering = false;

        switch (difficulty) {
            case LUNATIC:
        breakTime = 300;
                break;
            case HARD:
        breakTime = 480;
                break;
            case NORMAL:
                moveDistance = 30;
                breakTime = 90;
                gathering = true;
                pauseTime = 2;
                
        accel = .10f;
                break;
            case EASY:
                moveDistance = 30;
                breakTime = 120;
                gathering = true;
                pauseTime = 10;
                
        accel = .05f;
                break;
        }
    }

    public void fill() {
        this.declare(120, 8000);
        this.moveBoss(Script.semiWidth, 120, 120);
        this.addPause(120);
        this.stopBoss();
        this.addLoopStart();
        this.actionArray.add(new Action() {

            public void start() {

                angle = Char.getAngle(thisBoss.x, thisBoss.y);
            }
        });
        final double da = 1. / 20;
        if (gathering) {
            for (double a = da; a < 1; a += da) {
                final double a0 = a;
                this.actionArray.add(new Action() {

                    public void start() {
                        sign *= -1;
                        round = (round + 1) % maxRound;
                        Bullet.reset0();
                        Bullet.tolerance0(50);
                        Bullet.position0(thisBoss.position());
                        for (int sign = -1; sign <= 1; sign += 2) {
                            Bullet.velocity0(toXY(toV(1, (float) (angle + sign * (a0) * PI))));
                            if (round == 0) {
                                new IronSandT() {

                                    public void move() {
                                        super.move();
                                        if (age == 31 + (int) (30 * a0)) {
                                            velocity1(toXY(toF(toV(.1, Char.getAngle(x, y)))));
                                            explode();
                                        }
                                    }
                                }.add();
                            } else {
                                new IronSandT().add();
                            }

                            Bullet.velocity0(toXY(toV(-1, (float) (angle + sign * (a0) * PI))));
                            if (round == 0) {
                                new IronSandR() {

                                    public void move() {
                                        super.move();
                                        if (age == 91 + (int) (30 * a0)) {
                                            velocity1(toXY(toF(toV(.1, Char.getAngle(thisBoss.x, thisBoss.y)))));
                                            explode();
                                        }
                                    }
                                }.add();
                            } else {
                                new IronSandT().add();
                            }
                        }

                    }
                });
                this.addPause(pauseTime);
            }
        } else {
            this.actionArray.add(new Action() {

                public void start() {
                    sign*=-1;
                    Bullet.reset0();
                    Bullet.delay0(0);
                    Bullet.position0(thisBoss.position());
                    new IronSandWO() {

                        {
                            explode();
                        }
                    }.add();

                }
            });
        }
        this.addPause(breakTime / 2);
        this.moveBossTowardsPlayer(toV(120, 150), breakTime / 2, moveDistance, 60);
        this.addPause(breakTime / 2);

        this.stopBoss();


        this.addLoopEnd();
    }

    public class IronSandWO extends IronSand {

        int branch = 6;

        public void explode() {

            this.status = Status.Fade;

            float rand = Char.getAngle(thisBoss.x, thisBoss.y);
            for (int a = 0; a < branch; a += 1) {
                Bullet.reset0();
                Bullet.position0(this.position());
                Bullet.velocity0(toXY(toV(3*4f/5, rand + 2 * (float) (PI * (randomFloat(-.12f,.12f)+a+0*.5)/branch))));
                new IronSandW(0,4).add();
            }
        }
    ;

    }
    public class IronSandW extends IronSand {
        
        double spin;
        int lifeSpan;
        boolean exploded = false;
        int bN;

        public IronSandW(double spin,int bN) {
            this.bN = bN;
            this.spin = spin;
            lifeSpan = (int)(randomInt(90, 120)/mag(velocity()));
        }

        public void move() {
            super.move();
            if (age % 5 == 1&&bN>0) {
                Bullet.reset0();
                Bullet.type0(Bullet.Type.DarkPellet);
                Bullet.delay0(0);
                Bullet.position0(this.position());
                spin += 2.60;
                for (double a = 0; a < 1; a += 1. / bN) {

                    colorSand();
                    final float[] velocity = product(sum(toXY(toF(toV(.2f, spin + a * 2 * PI))), norm(velocity(), .25f)),.25);
                    Bullet.velocity0(velocity);
                    new Bullet() {

                        public void move() {
                            super.move();
                            if (age == 180) {
                                acceleration1(product(velocity,1./20));
                                this.type1(Bullet.Type.Pellet);
                            }
                        }
                    }.add();

                }
            }
            if (bN>=2&&status==Status.Active&&age == lifeSpan) {
                explode();
            }

        }

        public void explode() {
            this.status = Status.Dead;
            Bullet.reset0();
            Bullet.position0(this.position());
            for (int sign = -1; sign <= 1; sign += 2) {
                Bullet.delay0(0);
                Bullet.velocity0(Calc.rotate(this.velocity(), sign * PI / 20 * randomFloat(3, 4)));
                new IronSandW(spin,bN-1).add();
            }

        }
    ;

    }

    public class IronSandT extends IronSand {

        int growthPeriod = 60;
        int size = 8;
        int current;
        float rSpacing = 8;
        float cSpacing = 4;
        //  int releaseTime = 1;

        {
            current = 1;
        }

        public void explode() {

            this.status = Status.Fade;
            Bullet.reset0();
            Bullet.position0(this.position());
            Bullet.type0(Bullet.Type.DarkPellet);
            for (int r = -size + 1; r <= size - 1; r += 2) {
                for (int c = -current + 1; c <= current - 1; c += 2) {

                    colorSand();
                    float[] velocity = Calc.rotate(
                            product(
                            sum(toV(c, r), product(toV(randomFloat(-1, 1), randomFloat(-1, 1)), .7)), product(toV(cSpacing, rSpacing), 1. / (growthPeriod + 1))),
                            this.orient());
                    float[] acceleration = product(velocity, -1. / growthPeriod);
                    Bullet.delay0(0);
                    Bullet.shoot0(SE.KIRA);
                    Bullet.tolerance0(50);
                    Bullet.velocity0(sum(velocity, norm(velocity(), .2f)));
                    Bullet.acceleration0(acceleration);
                    final float[] v = velocity();
                    //        final int remain = growthPeriod;
                    new Bullet() {

                        public void move() {
                            super.move();
                            if (age == growthPeriod) {
                                this.acceleration1(toV(0, 0));

                                this.acceleration1(norm(v, accel));
                            }
                            if (age == growthPeriod + 30) {
                                this.acceleration1(toV(0, 0));
                            }
                        }
                    }.add();
                }
                current++;
            }
        }
    }

    public class IronSandR extends IronSand {

        int growthPeriod = 60;
        int row = 6;
        int column = 9;
        float spacing = 6;
        //  int releaseTime = 1;

        public void explode() {

            this.status = Status.Fade;
            Bullet.reset0();
            Bullet.position0(this.position());
            Bullet.type0(Bullet.Type.DarkPellet);
            for (int r = -row + 1; r <= row - 1; r += 2) {
                for (int c = -column + 1; c <= column - 1; c += 2) {

                    colorSand();
                    float[] velocity = Calc.rotate(
                            product(sum(toV(c, r), product(toV(randomFloat(-1, 1), randomFloat(-1, 1)), .7)), spacing * 1. / (growthPeriod + 1)),
                            this.orient());
                    float[] acceleration = product(velocity, -1. / growthPeriod);
                    Bullet.delay0(0);
                    Bullet.tolerance0(50);
                    Bullet.shoot0(SE.KIRA);
                    Bullet.velocity0(sum(velocity, norm(velocity(), .2f)));
                    Bullet.acceleration0(acceleration);
                    final float[] v = velocity();
                    //        final int remain = growthPeriod;
                    new Bullet() {

                        public void move() {
                            super.move();
                            if (age == growthPeriod) {
                                this.acceleration1(toV(0, 0));

                                this.acceleration1(norm(v, accel));
                            }
                            if (age == growthPeriod + 30) {
                                this.acceleration1(toV(0, 0));
                            }
                        }
                    }.add();
                }
            }
        }

        public void move() {
            super.move();

        }
    }

    public abstract class IronSand extends Bullet {
        //  int releaseTime = 1;

        {
            type1(Bullet.Type.DarkPellet);
            color1(Col.GRAY);
        }

        public abstract void explode();
    }
    
    public void colorSand(){
        
                    Bullet.color0(randomFloat(0, 1) < .0 ? Col.PURPLE : randomFloat(0, 1) < .1 ? Col.GRAY : Col.BLACK);
    }
}
