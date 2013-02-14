/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Henry
 */
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
public class MichaelAttack extends BossScript {

    int boardHeight;
    int boardWidth;
    float shotSpeed;
    float curve;
    int shotType;
    Col barrierColor;
    float barrierSpeed;
    
    int linePeriod;

    public void start() {
        boardHeight = (int) (scrHeight * 1.0);
        boardWidth = (int) (scrWidth * .8);

        super.initializeBoss();
        switch (difficulty) {
            case LUNATIC:
                
                linePeriod = 20;
                barrierColor = Col.BLACK;
                shotSpeed = 6;
                curve = 0;
                shotType = 3;
                barrierSpeed = .5f;
                break;
            case HARD:
                
                linePeriod = 20;
                barrierColor = Col.PURPLE;
                shotSpeed = 6;
                curve = 0;
                shotType = 2;
                barrierSpeed = .5f;
                break;
            case NORMAL:
                
                linePeriod = 25;
                barrierColor = Col.RED;
                shotSpeed = 4;
                curve = .003f;
                shotType = 1;
                barrierSpeed = .5f;
                break;
            case EASY:
                
                linePeriod = 30;
                barrierColor = Col.RED;
                shotSpeed = 5;
                curve = 0;
                shotType = 0;
                barrierSpeed = .4f;
                break;
        }

        Basic.BasicScript bossScript = new Basic.BasicScript() {

            int sign = 1;

            public void fill() {
                final Boss thisB = (Boss) basic;
                this.addPause(300);
                this.addLoopStart();

                this.actionArray.add(new Action(this) {

                    public void start() {
                        sign *= -1;
                        thisB.moveTo(90, toV((.5f + .1f * sign) * scrWidth, 60));
                    }
                });
                this.addPause(90);
                this.actionArray.add(new Action(this) {

                    public void start() {
                        thisB.stop();
                    }
                });
                for (double t = 0; t < 1; t += 1. / 3) {
                    this.actionArray.add(
                            new Action(this) {

                                public void start() {
                                    float angle = (float) PI * (.5f + sign * randomFloat(.08f, .18f));

                                    Bullet.reset0();
                                    Bullet.w0(sign * curve);
                                    Bullet.tolerance0(50);
                                    Bullet.position0(toV(scrWidth * (.5f + sign * .2f), -30));
                                    Bullet.velocity0(toXY(toV(shotSpeed, angle)));
                                    switch (shotType) {
                                        case 0:
                                            new FastShot().add();
                                            break;
                                        case 1:
                                            new CurveShot().add();
                                            break;
                                        case 2:
                                            new AquaJet().add();
                                            break;
                                        case 3:
                                            new Gungnir().add();
                                            break;
                                    }
                                }
                            });
                    this.addPause(60);
                }
                this.addPause(120);

                this.addLoopEnd();
            }
        };
        thisBoss.setScript(bossScript);
    }

    public void fill() {




        this.declare(40, 10000);
        this.actionArray.add(
                new Action(this) {

                    public void start() {
                        Familiar corner;
                        float[][] infos = {
                            {0, 1, 1, 0, -1, -1, 0, 1, boardWidth / 2, boardHeight / 2},
                            {1, 1, 0, 0, -1, -1, 1, 0, -boardWidth / 2, boardHeight / 2},
                            {0, 0, 1, 1, 1, 0, -1, -1, boardWidth / 2, -boardHeight / 2},
                            {1, 0, 0, 1, 0, 1, -1, -1, -boardWidth / 2, -boardHeight / 2},
                            {0, 1, 1, 1, 1, 0, 0, 1, boardWidth / 2, 0},
                            {1, 1, 0, 1, 0, 1, 1, 0, -boardWidth / 2, 0},
                            {0, 1, 0, 1, 0, 0, 0, 0, 0, -boardHeight / 4},
                            {0, 1, 0, 1, 0, 0, 0, 0, 0, boardHeight / 4},
                            {1, 0, 1, 0, 0, 0, 0, 0, 0, 0}
                        };
                        for (final float[] info : infos) {
                            corner = new TableEdge() {

                                {
                                    right = (info[0] == 1);
                                    up = (info[1] == 1);
                                    left = (info[2] == 1);
                                    down = (info[3] == 1);
                                    q1 = (info[4] == 1);
                                    q2 = (info[5] == 1);
                                    q3 = (info[6] == 1);
                                    q4 = (info[7] == 1);
                                }
                            };
                            corner.position1(thisBoss.position());
                            corner.color = (corner.color.Green);
                            corner.velocity1(
                                    product(diff(thisBoss.position(),
                                    sum(toV(semiWidth, semiHeight), toV(info[8], info[9]))), 1. / (30 / 2.)));
                            corner.acceleration1(
                                    product(diff(thisBoss.position(),
                                    sum(toV(semiWidth, semiHeight), toV(info[8], info[9]))), -1. / (30 / 2.) / (30. + 1)));

                            corner.add();
                        }
                    }
                });
    }

    public class TableEdge extends Familiar {

        public class TableEdgeLine extends Bullet {

            public void move() {
                super.move();
                if (this.age >= boardWidth / 2 / (mag(velocity())) && yv == 0) {
                    this.status = Status.Fade;
                }
                if (this.age >= boardHeight / 4 / (mag(velocity())) && xv == 0) {
                    this.status = Status.Fade;
                }
            }
        }
        boolean up;
        boolean right;
        boolean left;
        boolean down;
        boolean q1;
        boolean q2;
        boolean q3;
        boolean q4;
        int outPeriod = 120;
        int deployTime = 30;
        float speed = barrierSpeed;

        public void move() {
            super.move();
            if (age == deployTime) {
                this.velocity1(toV(0, 0));
                this.acceleration1(toV(0, 0));
            }
            if (age % (int)(linePeriod/barrierSpeed) == 0 && age > deployTime) {
                Bullet.reset0();
                Bullet.position0(this.position());
                Bullet.type0(Bullet.Type.Pellet);
                Bullet.color0(Col.GREEN);
                Bullet.shoot0(null);
                if (up) {
                    Bullet.velocity0(toV(0, -speed));
                    new TableEdgeLine().add();
                }
                if (down) {
                    Bullet.velocity0(toV(0, speed));
                    new TableEdgeLine().add();
                }
                if (left) {
                    Bullet.velocity0(toV(-speed, 0));
                    new TableEdgeLine().add();
                }
                if (right) {
                    Bullet.velocity0(toV(speed, 0));
                    new TableEdgeLine().add();
                }
            }
            if (age % outPeriod == 0 && age > deployTime) {
                double angle = 0;
                Bullet.reset0();
                Bullet.position0(this.position());
                Bullet.color0(barrierColor);
                Bullet.shoot0(null);
                if (q1) {
                    for (double a = 0; a < 1; a += 1. / 10) {
                        Bullet.velocity0(toXY(toV(1, (float) (PI / 2 * (a + angle)))));
                        new Bullet().add();
                    }
                }
                angle += 1;
                if (q2) {
                    for (double a = 0; a < 1; a += 1. / 10) {
                        Bullet.velocity0(toXY(toV(1, (float) (PI / 2 * (a + angle)))));
                        new Bullet().add();
                    }
                }
                angle += 1;
                if (q3) {
                    for (double a = 0; a < 1; a += 1. / 10) {
                        Bullet.velocity0(toXY(toV(1, (float) (PI / 2 * (a + angle)))));
                        new Bullet().add();
                    }
                }
                angle += 1;
                if (q4) {
                    for (double a = 0; a < 1; a += 1. / 10) {
                        Bullet.velocity0(toXY(toV(1, (float) (PI / 2 * (a + angle)))));
                        new Bullet().add();
                    }
                }
                angle += 1;


            }
        }
    }

    public class FastShot extends Bullet {

        {

            this.shoot(SE.ESHOOT0);
            this.color1(Col.GRAY);
            this.type1(Type.Outlined);

        }

        public void move() {
            if (age % 20 == 0) {
                float angle = randomFloat(0, 2 * (float) PI);
                for (double t = 0; t < 1; t += 1. / 5) {
                    double a = angle + t * 2 * PI;
                    Bullet.reset0();
                    Bullet.color0(Col.BLACK);
                    Bullet.type0(Type.Pellet);
                    Bullet.position0(this.position());
                    Bullet.velocity0(toXY(toV(.6f, (float) a)));
                    new Bullet().add();
                }
            }
            super.move();
        }
    }

    public class CurveShot extends Bullet {

        {
            Bullet.color0(Col.WHITE);

            this.shoot(SE.ESHOOT0);
            this.color1(Col.GRAY);
            this.type1(Type.Outlined);

        }

        public void move() {

            if (age == 60) {
                w *= -4;
                tolerance = 10;
            }
            if (age % 10 == 0) {
                float angle = randomFloat(0, 2 * (float) PI);
                for (double t = 0; t < 1; t += 1. / 5) {
                    double a = -angle + t * 2 * PI;
                    Bullet.reset0();
                    Bullet.color0(Col.GRAY);
                    Bullet.type0(Type.Pellet);
                    Bullet.position0(this.position());
                    Bullet.velocity0(toXY(toV(.8f, (float) a)));
                    new Bullet().add();
                }
            }
            super.move();
        }
    }
    public class AquaJet extends Bullet {

        {

            this.shoot(SE.ESHOOT0);
            this.color1(Col.BLUE);
            this.type1(Type.Outlined);

        }

        public void move() {
            if(age % 25 ==0){
                float angle = randomFloat(0, 2 * (float) PI);
                for (double t = 0; t < 1; t += 1. / 6) {
                    double a = angle + t * 2 * PI;
                    Bullet.reset0();
                    Bullet.color0(Col.BLUE);
                    Bullet.type0(Type.MetalF);
                    
                    Bullet.position0(this.position());
                    Bullet.velocity0(toXY(toV(1.2f, (float) a)));
                    new Bullet().add();
                    Bullet.type0(Type.Outlined);
                    Bullet.color0(Col.CYAN);
                    Bullet.velocity0(toXY(toV(.9f, (float) (a))));
                    new Bullet().add();
                    
                    Bullet.type0(Type.Pellet);
                    Bullet.velocity0(toXY(toV(.6f, (float) a)));
                    new Bullet().add();
                }
                
            }
            if (age % 5 == 0) {
                float angle = randomFloat(0, 2 * (float) PI);
                for (double t = 0; t < 1; t += 1. / 3) {
                    double a = angle + t * 2 * PI;
                    Bullet.reset0();
                    Bullet.color0(Col.CYAN);
                    Bullet.type0(Type.Pellet);
                    Bullet.position0(this.position());
                    Bullet.velocity0(toXY(toV(.6f, (float) a)));
                    new Bullet().add();
                }
            }
            super.move();
        }
    }
    
     public class Gungnir extends Bullet {

        {

            this.shoot(SE.ESHOOT0);
            this.color1(Col.RED);
            this.type1(Type.Arrow);

        }

        public void move() {
            if(age % 5 ==0){
                
                float angle = randomFloat(0, 2 * (float) PI);
                for (double t = 0; t < 1; t += 1. / 3) {
                    double a = angle + t * 2 * PI;
                    Bullet.reset0();
                    Bullet.color0(Col.RED);
                    Bullet.type0(Type.Medium);
                    Bullet.position0(this.position());
                    Bullet.velocity0(toXY(toV(randomFloat(.2f,1.f), (float) a)));
                    new Bullet().add();
                }
                for (double t = 0; t < 1; t += 1. / 2) {
                    double a = angle + t * 2 * PI;
                    Bullet.reset0();
                    Bullet.color0(Col.RED);
                    Bullet.type0(Type.Small);
                    Bullet.position0(this.position());
                    Bullet.velocity0(toXY(toV(randomFloat(.4f,1.6f), (float) a)));
                    new Bullet().add();
                }
                angle = randomFloat(0, 2 * (float) PI);
                for (double t = 0; t < 1; t += 1. / 1) {
                    double a = angle + t * 2 * PI;
                    Bullet.reset0();
                    Bullet.color0(Col.RED);
                    Bullet.type0(Type.MetalF);
                    Bullet.delay0(20);
                    Bullet.position0(this.position());
                    Bullet.velocity0(toXY(toV(randomFloat(.5f,1.5f), (float) a)));
                    new Bullet().add();
                }
            }
            if(age%3==0){
                for (double t = 0; t < 1; t += 1. / 1) {
                    Bullet.reset0();
                    Bullet.color0(Col.RED);
                    Bullet.type0(Type.Laser);
                    Bullet.delay0(0);
                    Bullet.position0(sum(this.position(),toXY(toV(0,randomFloat(0,2*(float)PI)))));
                    if(age%12==0)
                    {
                    Bullet.velocity0(norm(Calc.rotate(velocity(),PI/10*randomFloat(-1,1)),.5f));
                    }
                    else{
                        
                    Bullet.velocity0(norm(Calc.rotate(velocity(),PI/100*randomFloat(-1,1)),4f));
                    }
                    new Bullet().add();
                }
                
            }
            if (age % 1 == 0) {
                for (int sign = -1; sign <= 1; sign += 2) {
                    Bullet.reset0();
                    Bullet.color0(Col.RED);
                    Bullet.delay0(0);
                    Bullet.type0(Type.Pellet);
                    Bullet.position0(sum(this.position(),norm(velocity(),20)));
                    Bullet.velocity0(norm(Calc.rotate(velocity(),sign*pow(randomFloat(0,1),.2)*PI/30),4f));
                    new Bullet(){
                        public void move(){
                            if(age==60){
                                this.status=Status.Dead;
                            }
                            super.move();
                        }
                    }.add();
                }
            }
            super.move();
        }
    }
}
