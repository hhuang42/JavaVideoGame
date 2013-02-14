
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

public class JamesAttack extends BossScript {

    int moveDistance;
    int breakTime;
    int bulletNumber;
    int sides;
    float v0;
    Basic.BasicScript spell;

    public void start() {
        super.initializeBoss();
        breakTime = 60;
        moveDistance = 60;
        //bulletNumber = 6;
        switch (difficulty) {
            case LUNATIC:
                bulletNumber = 18;
                breakTime = 120;
        moveDistance = 120;
                break;
            case HARD:
                bulletNumber = 18;
                breakTime = 60;
        moveDistance = 60;
                bulletNumber = 18;
                break;
            case NORMAL:
                bulletNumber = 18;
                bulletNumber = 18;
                breakTime = 60;
        moveDistance = 60;
                break;
            case EASY:
                bulletNumber = 18;
                bulletNumber = 18;
                breakTime = 120;
        moveDistance = 120;
                break;
        }
        

        Basic.BasicScript zio = new Basic.BasicScript() {

            float angle = (float) 2;
            int sign2 = 1;

            public void fill() {
                final Boss thisB = (Boss) basic;
                this.addPause(120);

                this.addLoopStart();
                this.addPause(breakTime / 2);
                this.actionArray.add(
                        new Action() {

                            public void start() {

                                int dx = 60;
                                int border = 60;
                                double x = dx * Math.signum(Char.ch.x - thisBoss.x) + thisBoss.x;
                                if (x >= scrWidth - border || x < border) {
                                    x = -dx * Math.signum(Char.ch.x - thisBoss.x) + thisBoss.x;
                                }
                                thisBoss.moveTo(breakTime / 2, toF(toV(x, randomFloat(90, 150))));
                            }
                        });

                this.addPause(breakTime / 2);
                this.actionArray.add(
                        new Action() {

                            public void start() {
                                int sign = 1;
                                thisB.stop();
                                float angle = randomFloat(0, 2 * (float) PI);
                                for (double a = 0; a < 1; a += 1. / bulletNumber) {
                                    Bullet.reset0();
                                    Bullet.position0(thisB.position());
                                    Bullet.velocity0(toXY(toV(.5f + .1f * sign, (float) (2 * a * PI) + angle)));
                                    Bullet.type0(Bullet.Type.Outlined);
                                    Bullet.color0(Col.YELLOW);
                                    if (sign == -1) {
                                        new Bullet().add();
                                    } else {
                                        new Bullet() {

                                            public void move() {
                                                super.move();
                                                if (age == 120) {
                                                    Bullet.reset0();
                                                    Bullet.position0(this.position());
                                                    Bullet.velocity0(toV(0, 0));
                                                    Bullet.acceleration0(toXY(toV(.005f, Char.getAngle(x, y))));
                                                    Bullet.shoot0(SE.KIRA);
                                                    Bullet.type0(Type.Arrow);
                                                    Bullet.color0(this.color);
                                                    new Bullet().add();
                                                    this.status = Status.Fade;
                                                }
                                            }
                                        }.add();
                                    }
                                    sign *= -1;

                                }
                            }
                        });
                this.addLoopEnd();
            }
        };

        Basic.BasicScript magaru = new Basic.BasicScript() {

            float angle = (float) 2;
            int sign2 = 1;

            public void fill() {
                final Boss thisB = (Boss) basic;
                this.addPause(120);

                this.addLoopStart();
                this.addPause(breakTime / 2);
                this.actionArray.add(
                        new Action() {

                            public void start() {

                                int dx = 60;
                                int border = 60;
                                double x = dx * Math.signum(Char.ch.x - thisBoss.x) + thisBoss.x;
                                if (x >= scrWidth - border || x < border) {
                                    x = -dx * Math.signum(Char.ch.x - thisBoss.x) + thisBoss.x;
                                }
                                thisBoss.moveTo(breakTime / 2, toF(toV(x, randomFloat(120, 150))));
                            }
                        });

                this.addPause(breakTime / 2);
                this.actionArray.add(
                        new Action() {

                            public void start() {
                                thisB.stop();
                                for(double a = 0;a<1;a+=1./3)
                                {
                                    float angle = (float)(PI+2*PI*a + Char.getAngle(thisBoss.x, thisBoss.y));
                                    Bullet.reset0();
                                    Bullet.position0(thisB.position());
                                    Bullet.velocity0(toXY(toV(3,angle)));
                                    Bullet.color0(Col.GREEN);
                                    Bullet.type0(Bullet.Type.MetalF);
                                new Bullet() {

                                    public void move() {
                                        super.move();
                                        if (age == 30) {
                                            SE.POP.play();
                                            this.status=Status.Fade;
                                            int sign = 1;
                                            float angle = randomFloat(0, 2 * (float) PI);
                                            for (double a = 0; a < 1; a += 1. / bulletNumber) {
                                                Bullet.reset0();
                                                Bullet.position0(this.position());
                                                Bullet.velocity0(toXY(toV(.7f + .1f * sign, (float) (2 * a * PI) + angle)));
                                                Bullet.type0(Bullet.Type.Medium);
                                                Bullet.color0(this.color);
                                                if (sign == -1) {
                                                    new Bullet().add();
                                                } else {
                                                    new Bullet() {

                                                        public void move() {
                                                            super.move();
                                                            if (age == 60) {
                                                                Bullet.reset0();
                                                                Bullet.position0(this.position());
                                                                Bullet.velocity0(toV(0, 0));
                                                                Bullet.acceleration0(toXY(toV(.005f, Char.getAngle(x, y))));
                                                                Bullet.shoot0(SE.KIRA);
                                                                Bullet.type0(Type.Pellet);
                                                                Bullet.color0(this.color);
                                                                new Bullet().add();
                                                                this.status = Status.Fade;
                                                            }
                                                        }
                                                    }.add();
                                                }
                                                sign *= -1;

                                            }
                                        }
                                    }
                                }.add();
                                }
                            }
                        });
                this.addLoopEnd();
            }
        };
        
        Basic.BasicScript bufula = new Basic.BasicScript() {

            float angle = (float) 2;
            int sign2 = 1;

            public void fill() {
                final Boss thisB = (Boss) basic;
                this.addPause(120);

                this.addLoopStart();
                this.addPause(breakTime / 2);
                this.actionArray.add(
                        new Action() {

                            public void start() {

                                int dx = 60;
                                int border = 60;
                                double x = dx * Math.signum(Char.ch.x - thisBoss.x) + thisBoss.x;
                                if (x >= scrWidth - border || x < border) {
                                    x = -dx * Math.signum(Char.ch.x - thisBoss.x) + thisBoss.x;
                                }
                                thisBoss.moveTo(breakTime / 2, toF(toV(x, randomFloat(120, 150))));
                            }
                        });

                this.addPause(breakTime / 2);
                this.actionArray.add(
                        new Action() {

                            public void start() {
                                int sign = 1;
                                thisB.stop();
                                float angle = randomFloat(0, 2 * (float) PI);
                                for (double a = 0; a < 1; a += 1. / bulletNumber) {
                                    Bullet.reset0();
                                    Bullet.position0(thisB.position());
                                    Bullet.velocity0(toXY(toV(2.3f + 1f * sign, (float) (2 * a * PI) + angle)));
                                    Bullet.type0(Bullet.Type.MetalF);
                                    Bullet.color0(Col.CYAN);
                                    if (sign == -1) {
                                        new Bullet().add();
                                    } else {
                                        //final float currentAngle = Char.getAngle(thisBoss.x, thisBoss.y);
                                        
                                        new Bullet() {

                                            public void move() {
                                                super.move();
                                                if (age == 30) {
                                                    for(int sign2 = -1;sign2<=1;sign2++)
                                        {
                                                    Bullet.reset0();
                                                    Bullet.position0(this.position());
                                                    Bullet.velocity0(toV(0, 0));
                                                    Bullet.acceleration0(toXY(toV(.005f, 
                                                            Char.getAngle(x, y)+sign2*(float)PI*.15f)));
                                                    Bullet.shoot0(SE.KIRA);
                                                    Bullet.type0(Type.Arrow);
                                                    Bullet.color0(this.color);
                                                    new Bullet().add();
                                                    this.status = Status.Fade;
                                        }
                                                }
                                            }
                                        }.add();
                                    }
                                    Bullet.velocity0(toXY(toV(.7f + .1f * sign, (float) (2 * a * PI) + angle)));
                                    Bullet.type0(Bullet.Type.Outlined);
                                    new Bullet().add();
                                    sign *= -1;

                                }
                            }
                        });
                this.addLoopEnd();
            }
        };
        
        Basic.BasicScript maragidyne = new Basic.BasicScript() {

            float angle = (float) 2;
            int sign2 = 1;

            public void fill() {
                final Boss thisB = (Boss) basic;
                this.addPause(120);

                this.addLoopStart();
                this.addPause(breakTime / 2);
                this.actionArray.add(
                        new Action() {

                            public void start() {

                                int dx = 60;
                                int border = 60;
                                double x = dx * Math.signum(Char.ch.x - thisBoss.x) + thisBoss.x;
                                if (x >= scrWidth - border || x < border) {
                                    x = -dx * Math.signum(Char.ch.x - thisBoss.x) + thisBoss.x;
                                }
                                thisBoss.moveTo(breakTime / 2, toF(toV(x, randomFloat(120, 150))));
                            }
                        });

                this.addPause(breakTime / 2);
                this.actionArray.add(
                        new Action() {

                            public void start() {
                                thisB.stop();
                                for(double a = 0;a<1;a+=1./5)
                                {
                                    float angle = (float)(PI+2*PI*a + Char.getAngle(thisBoss.x, thisBoss.y));
                                    Bullet.reset0();
                                    Bullet.position0(thisB.position());
                                    Bullet.velocity0(toXY(toV(2,angle)));
                                    Bullet.color0(Col.RED);
                                    Bullet.type0(Bullet.Type.MetalF);
                                new Bullet() {

                                    public void move() {
                                        super.move();
                                        if (age == 60) {
                                            SE.POP.play();
                                            this.status=Status.Fade;
                                            int sign = 1;
                                            float angle = randomFloat(0, 2 * (float) PI);
                                            for (double a = 0; a < 1; a += 1. / bulletNumber) {
                                                Bullet.reset0();
                                                Bullet.position0(this.position());
                                                Bullet.velocity0(toXY(toV(1.7f + .4f * sign, (float) (2 * a * PI) + angle)));
                                                Bullet.type0(Bullet.Type.Outlined);
                                                Bullet.color0(this.color);
                                                if (sign == -1) {
                                        new Bullet().add();
                                    } else {
                                        new Bullet() {

                                            public void move() {
                                                super.move();
                                                if (age == 30) {
                                                    for(int sign2 = -1;sign2<=1;sign2++)
                                        {
                                                    Bullet.reset0();
                                                    Bullet.position0(this.position());
                                                    Bullet.velocity0(toV(0, 0));
                                                    Bullet.acceleration0(toXY(toV(.005f, 
                                                            Char.getAngle(x, y)+sign2*(float)PI*.15f)));
                                                    Bullet.shoot0(SE.KIRA);
                                                    Bullet.type0(Type.Pellet);
                                                    Bullet.color0(this.color);
                                                    new Bullet().add();
                                                    this.status = Status.Fade;
                                        }
                                                }
                                            }
                                        }.add();
                                    }
                                    Bullet.velocity0(toXY(toV(.6f + .2f * sign, (float) (2 * a * PI) + angle)));
                                    if(sign==-1)
                                    {Bullet.color0(Col.YELLOW);
                                    }
                                    else{
                                                    Bullet.color0(Col.ORANGE);
                                    }
                                    Bullet.type0(Bullet.Type.Medium);
                                    new Bullet().add();
                                                sign *= -1;

                                            }
                                        }
                                    }
                                }.add();
                                }
                            }
                        });
                this.addLoopEnd();
            }
        };
        switch (difficulty) {
            case LUNATIC:
        spell = maragidyne;
                break;
            case HARD:
                spell = bufula;
                break;
            case NORMAL:
        spell = magaru;
                break;
            case EASY:
        spell = zio;
                break;
        }
        thisBoss.setScript(spell);
    }

    public void fill() {
        this.declare(120, 10000);
        this.addLoopStart();
        //this.actionArray.add();
        this.addPause(breakTime);

        this.stopBoss();

        this.addLoopEnd();
    }
}
