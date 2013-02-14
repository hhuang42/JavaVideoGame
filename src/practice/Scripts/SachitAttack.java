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
public class SachitAttack extends BossScript {

    Familiar[] disco;
    int discoCount;
    int discoSpacing;
    int discoHeight;
    double discoRadius;
    int speakerCount;
    int speakerMainSpacing;
    int speakerMainHeight;
    int speakerSecSpacing;
    int speakerSecHeight;
    boolean speakerSecOn;
    boolean subBeatsOn;

    public void start() {
        super.initializeBoss();
        discoCount = 2;
        discoSpacing = 270;
        discoHeight = 210;
        discoRadius = 45;
        speakerCount = 2;
        speakerMainSpacing = 160;
        speakerMainHeight = 60;
        speakerSecSpacing = 300;
        speakerSecHeight = scrHeight - 90;
        speakerSecOn = true;
        subBeatsOn = true;
        //difficulty = difficulty.NORMAL;
        switch (difficulty) {
            case LUNATIC:
                break;
            case HARD:
                discoCount = 2;
                speakerMainSpacing = 160;
                discoHeight = 210;
                speakerMainHeight = 60;
                speakerSecOn = false;
                break;
            case NORMAL:
                discoCount = 1;
                speakerMainSpacing = 270;
                discoHeight = 180;
                speakerMainHeight = 60;
                speakerSecOn = false;
                break;
            case EASY:
                discoCount = 1;
                speakerMainSpacing = 180;
                discoHeight = 90;
                speakerMainHeight = 60;
                subBeatsOn = false;
                speakerSecOn = false;
                break;
        }
    }

    public void fill() {

        Action startDisco = new Action() {

            public void start() {
                disco = new Familiar[discoCount];
                int i = 0;
                for (int n = -(discoCount - 1); n <= (discoCount - 1); n += 2) {

                    disco[i] = new Familiar() {
                        double a = 0;
                        public void move() {
                            if (age == 121) {
                                velocity1(toV(0, 0));
                                acceleration1(toV(0, 0));
                            }
                            if (age >= 121 && age % 10 == 0) {
                                for (double t = .8; t >= .2; t -= .1) {
                                    final double angle = PI * (t - .5);
                                    Bullet.reset0();
                                    Bullet.delay0(0);
                                    Bullet.shoot0(null);
                                    float rand = randomFloat(0, 1);
                                    Bullet.color0(10);
                                    Bullet.position0(sum(position(), toXY(toF(toV(discoRadius, angle)))));
                                    Bullet.velocity0(product(toV(-(float) discoRadius / 30, 1), cos(angle)));
                                    Bullet.acceleration0(product(toV(0, -1), cos(angle) / 30));
                                    new Bullet() {

                                        public void move() {
                                            if (age == 61) {
                                                velocity1(product(toV(-(float) discoRadius / 30, 1), -cos(angle)));
                                                acceleration1(product(toV(0, -1), -cos(angle) / 30));
                                            }
                                            if (age == 121) {
                                                this.status = status.Dead;
                                            }
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                            if (age >= 211 && age % 10 == 0) {
                                
                                    a++;
                                for (double t = 0; t < 1; t+=1./3) {
                                    Laser.reset0();
                                    Laser.shoot0(SE.KIRA);
                                    Laser.length0(30);
                                    double angle = randomFloat(0, 2 * (float) PI/12)+t*2*PI+a*PI/6;
                                    Laser.position0(sum(position(),
                                            toXY(toF(toV(randomFloat((float) discoRadius / 2, (float) discoRadius / 2), angle)))));
                                    Laser.velocity0(toXY(toF(toV(randomFloat(1.5f, 2.5f), angle))));
                                    Laser.color0(randomInt(1, 10));
                                    new Laser().add();
                                }
                            }
                            super.move();
                        }
                    };
                    disco[i].position1(toV(n * discoSpacing / 2 + semiWidth, -20));
                    disco[i].velocity1(toV(0, (20 + discoHeight) / 120f));
                    disco[i].color = (disco[i].color.Yellow);
                    disco[i].add();
                    i++;
                }
            }
        };
        Action startSpeaker = new Action() {

            public void start() {
                int i = 0;
                for (int n = -(speakerCount - 1); n <= (speakerCount - 1); n += 2) {
                    final double n0 = n;
                    Familiar speaker = new Familiar() {

                        int i = 0;
                        final double v0 = 6;
                        final double vfinal = 1.5;
                        final int time = 10;
                        final int count = 64;

                        public void move() {
                            if (age == 30) {
                                velocity1(toV(0, 0));
                                acceleration1(toV(0, 0));
                            }
                            if (subBeatsOn&&age >= 31 && age % count == (count * 3) / 4) {
                                for (double t = 0; t < 1; t += 1. / 2) {
                                    final double angle = 2 * PI * ((t + .25) + i * n0 / 21);
                                    Bullet.reset0();
                                    float rand = randomFloat(0, 1);
                                    Bullet.color0(1);
                                    Bullet.delay0(0);
                                    Bullet.shoot0(SE.ESHOOT0);
                                    Bullet.type0(Bullet.Type.MetalF);
                                    Bullet.position0(position());
                                    Bullet.velocity0(toXY(toF(toV(vfinal + v0, angle))));
                                    Bullet.acceleration0(toXY(toF(toV(-v0 / time, angle))));
                                    new Bullet() {

                                        public void move() {
                                            if (age == time + 1) {
                                                acceleration1(toV(0, 0));
                                            }
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                            if (subBeatsOn&&age >= 31 && age % count == (count * 7) / 8) {

                                for (double t = 0; t < 1; t += 1. / 2) {
                                    final double angle = 2 * PI * (t + i * n0 / 21);
                                    Bullet.reset0();
                                    float rand = randomFloat(0, 1);
                                    Bullet.color0(1);
                                    Bullet.delay0(0);
                                    Bullet.shoot0(SE.ESHOOT0);
                                    Bullet.type0(Bullet.Type.MetalF);
                                    Bullet.position0(position());
                                    Bullet.velocity0(toXY(toF(toV(vfinal + v0, angle))));
                                    Bullet.acceleration0(toXY(toF(toV(-v0 / time, angle))));
                                    new Bullet() {

                                        public void move() {
                                            if (age == time + 1) {
                                                acceleration1(toV(0, 0));
                                            }
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                            if (age >= 31 && age % count == count / 4) {
                                for (double t = 0; t < 1; t += 1. / 5) {
                                    final double angle = 2 * PI * (t + i * n0 / 21);
                                    Bullet.reset0();
                                    float rand = randomFloat(0, 1);
                                    Bullet.color0(6);
                                    Bullet.delay0(0);
                                    Bullet.shoot0(SE.ESHOOT1);
                                    Bullet.type0(Bullet.Type.MetalF);
                                    Bullet.position0(position());
                                    Bullet.velocity0(toXY(toF(toV(vfinal + v0, angle))));
                                    Bullet.acceleration0(toXY(toF(toV(-v0 / time, angle))));
                                    new Bullet() {

                                        public void move() {
                                            if (age == time + 1) {
                                                acceleration1(toV(0, 0));
                                            }
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                            if (age >= 31 && age % (count / 2) == 0) {
                                i++;
                                for (double t = 0; t < 1; t += 1. / 3) {
                                    final double angle = 2 * PI * (t + i * n0 / 21);
                                    Bullet.reset0();
                                    float rand = randomFloat(0, 1);
                                    Bullet.color0(11);
                                    Bullet.delay0(0);
                                    Bullet.type0(Bullet.Type.MetalF);
                                    Bullet.position0(position());
                                    Bullet.velocity0(toXY(toF(toV(vfinal + v0, angle))));
                                    Bullet.acceleration0(toXY(toF(toV(-v0 / time, angle))));
                                    new Bullet() {

                                        public void move() {
                                            if (age == time + 1) {
                                                acceleration1(toV(0, 0));
                                            }
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                            super.move();
                        }
                    };
                    speaker.position1(thisBoss.position());
                    speaker.velocity1(
                            product(diff(thisBoss.position(),
                            toV(n * speakerMainSpacing / 2 + semiWidth, speakerMainHeight)), 1. / (30 / 2.)));
                    speaker.acceleration1(
                            product(diff(thisBoss.position(),
                            toV(n * speakerMainSpacing / 2 + semiWidth, speakerMainHeight)), -1. / (30 / 2.) / (30. + 1)));
                    speaker.color = (speaker.color.Blue);
                    speaker.add();
                    i++;
                }
            }
        };

        Action startMiniSpeaker = new Action() {

            public void start() {
                int i = 0;
                for (int n = -(speakerCount - 1); n <= (speakerCount - 1); n += 2) {
                    final double n0 = n;
                    Familiar speaker = new Familiar() {

                        int i = 0;
                        final double v0 = 6;
                        final double vfinal = .6;
                        final int time = 11;
                        final int count = 64;

                        public void move() {
                            if (age == 30) {
                                velocity1(toV(0, 0));
                                acceleration1(toV(0, 0));
                            }
                            if (false && age >= 31 && age % count == (count * 3) / 4) {
                                for (double t = 0; t < 1; t += 1. / 2) {
                                    final double angle = 2 * PI * ((t + .25) + i * n0 / 21);
                                    Bullet.reset0();
                                    float rand = randomFloat(0, 1);
                                    Bullet.color0(1);
                                    Bullet.delay0(0);
                                    Bullet.shoot0(SE.ESHOOT1);
                                    Bullet.type0(Bullet.Type.Outlined);
                                    Bullet.position0(position());
                                    Bullet.velocity0(toXY(toF(toV(vfinal + v0, angle))));
                                    Bullet.acceleration0(toXY(toF(toV(-v0 / time, angle))));
                                    new Bullet() {

                                        public void move() {
                                            if (age == time + 1) {
                                                acceleration1(toV(0, 0));
                                            }
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                            if (false && age >= 31 && age % count == (count * 7) / 8) {

                                for (double t = 0; t < 1; t += 1. / 2) {
                                    final double angle = 2 * PI * (t + i * n0 / 21);
                                    Bullet.reset0();
                                    float rand = randomFloat(0, 1);
                                    Bullet.color0(1);
                                    Bullet.delay0(0);
                                    Bullet.shoot0(SE.ESHOOT1);
                                    Bullet.type0(Bullet.Type.Outlined);
                                    Bullet.position0(position());
                                    Bullet.velocity0(toXY(toF(toV(vfinal + v0, angle))));
                                    Bullet.acceleration0(toXY(toF(toV(-v0 / time, angle))));
                                    new Bullet() {

                                        public void move() {
                                            if (age == time + 1) {
                                                acceleration1(toV(0, 0));
                                            }
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                            if (false && age >= 31 && age % count == count / 4) {
                                for (double t = 0; t < 1; t += 1. / 3) {
                                    final double angle = 2 * PI * (t + n0 / 4 + i * n0 / 21);
                                    Bullet.reset0();
                                    float rand = randomFloat(0, 1);
                                    Bullet.color0(6);
                                    Bullet.delay0(0);
                                    Bullet.shoot0(SE.ESHOOT0);
                                    Bullet.type0(Bullet.Type.Outlined);
                                    Bullet.position0(position());
                                    Bullet.velocity0(toXY(toF(toV(vfinal + v0, angle))));
                                    Bullet.acceleration0(toXY(toF(toV(-v0 / time, angle))));
                                    new Bullet() {

                                        public void move() {
                                            if (age == time + 1) {
                                                acceleration1(toV(0, 0));
                                            }
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                            if (age >= 31 && age % (count / 2) == 0) {
                                i++;
                                for (double t = 0; t < 1; t += 1. / 3) {
                                    final double angle = 2 * PI * (t + i * n0 / 7+.25);
                                    Bullet.reset0();
                                    float rand = randomFloat(0, 1);
                                    Bullet.color0(11);
                                    Bullet.delay0(0);
                                    Bullet.type0(Bullet.Type.Outlined);
                                    Bullet.position0(position());
                                    Bullet.velocity0(toXY(toF(toV(vfinal + v0, angle))));
                                    Bullet.acceleration0(toXY(toF(toV(-v0 / time, angle))));
                                    new Bullet() {

                                        public void move() {
                                            if (age == time + 1) {
                                                acceleration1(toV(0, 0));
                                            }
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                            super.move();
                        }
                    };
                    speaker.position1(thisBoss.position());
                    speaker.velocity1(
                            product(diff(thisBoss.position(),
                            toV(n * speakerSecSpacing / 2 + semiWidth, speakerSecHeight)), 1. / (30 / 2.)));
                    speaker.acceleration1(
                            product(diff(thisBoss.position(),
                            toV(n * speakerSecSpacing / 2 + semiWidth, speakerSecHeight)), -1. / (30 / 2.) / (30. + 1)));
                    speaker.color = (speaker.color.Blue);
                    speaker.add();
                    i++;
                }
            }
        };



        this.declare(60, 10000);


        this.actionArray.add(startSpeaker);
        if(speakerSecOn)
        {
        this.actionArray.add(startMiniSpeaker);
        }
        this.addPause(240);
        this.actionArray.add(startDisco);
        this.addLoopStart();
        this.addPause(30);
        //this.moveBossTowardsPlayer(120, 120, 60);
        /*
         * this.actionArray.add(new Action(this) {
         *
         * public void start() { Bullet.reset0();
         * Bullet.position0(thisBoss.position());
         * Bullet.velocity0(product(Calc.diff(thisBoss.position(),
         * Char.charPosition()), .002)); Bullet.type0(Bullet.Type.MetalF);
         * Bullet.color0(11); new Bullet().add(); } });
         */

        this.addPause(150);


        this.addLoopEnd();
    }
}
