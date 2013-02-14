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
public class EunBinAttack extends BossScript {
{
        String[] nameSet = {"Tickle Me Red","Tickle Me Yellow","Tickle Me Black","Tickle Me Blue"};
        names = nameSet;
    }
    int ring;
    int layer;
    int birdsThrown;
    

    float currentHP;
    static boolean tickled;
    double focus;
    double focusFactor;
    double minFocus;
    double maxHeight;
    double minHeight;
    float advanceSpeed;
    float retreatSpeed;
    float trackSpeed;
    Col color;

    public void start() {
        super.initializeBoss();
        focus = 1;
        currentHP = thisBoss.HP;
        birdsThrown = 1;
        tickled = false;
        retreatSpeed = -1.5f;
        trackSpeed = .2f;
        //difficulty = difficulty.NORMAL;
        switch (difficulty) {
            case LUNATIC:
                
        focusFactor = .98;
        ring = 40;
        layer = 3;
        minFocus = .3;
        color = Col.BLUE;
        maxHeight = scrHeight *.6;
        minHeight = scrHeight*.2;
        advanceSpeed = .4f;
                break;
            case HARD:
                focusFactor = .96;
        ring = 30;
        layer = 2;
        minFocus = .5;
        color = Col.BLACK;
        maxHeight = scrHeight *.5;
        minHeight = scrHeight*.2;
        advanceSpeed = .3f;
                break;
            case NORMAL:
                focusFactor = .98;
        ring = 15;
        layer = 2;
        minFocus = .2;
        color = Col.YELLOW;
        maxHeight = scrHeight *.5;
        minHeight = scrHeight*.2;
        advanceSpeed = .3f;
                break;
            case EASY:
                focusFactor = .97;
        ring = 15;
        layer = 1;
        minFocus = .4;
        color = Col.RED;
        maxHeight = scrHeight *.5;
        minHeight = scrHeight*.1;
        advanceSpeed = .2f;
                break;
        }

        Basic.BasicScript bossScript = new Basic.BasicScript() {

            int sign = 1;

            public void fill() {
                final Boss thisB = (Boss) basic;
                this.addPause(120);
                this.addLoopStart();
                this.actionArray.add(
                        new Action(this) {

                            public void start() {
                                if (!tickled) {
                                    focus = pow(focus,.88);
                                    float angle = Char.getAngle(basic.x, basic.y);
                                    double la = 1-1./layer;
                                    sign *= -1;
                                    for (double a = 0; a < 1; a += 1. / (layer * ring)) {
                                        Bullet.reset0();
                                        Bullet.shoot0(SE.ESHOOT2);
                                        Bullet.color0(Col.PURPLE);
                                        Bullet.position0(basic.position());
                                        Bullet.velocity0(toXY(toV(1.2f + .8f * (float) la, angle + sign * (float) (a * 2 * PI))));
                                        Bullet.type0(Bullet.Type.Medium);
                                        new Bullet().add();
                                                

                                        la = (la + 1. / layer) % 1.;
                                    }
                                }

                            }
                        });
                for (double t = 0; t < 1; t += 1. / 5) {
                    this.actionArray.add(
                        new Action(this) {

                            public void start() {
                                
                                tickled = thisB.HP < currentHP;
                                currentHP = thisB.HP;
                                if (tickled) {
                                    focus*=focusFactor;
                                    for (int a = 0; a < birdsThrown; a += 1) {
                                        float angle = (float)(PI/2)+(float)max(focus,minFocus)*randomFloat(-(float)PI,(float)PI);
                                        Bullet.reset0();
                                        Bullet.shoot0(SE.ESHOOT2);
                                        Bullet.position0(basic.position());
                                        Bullet.velocity0(toXY(toV(1f, angle )));
                                        switch(color){
                                            case BLUE:
                                                new BlueBird().add();
                                                break;
                                                case YELLOW:
                                                new YellowBird().add();
                                                break;
                                                case BLACK:
                                                new BlackBird().add();
                                                break;
                                                default:
                                                new RedBird().add();
                                                break;
                                        }
                                    }
                                }
                                thisB.move=null;
                                if(tickled&&thisB.y>=minHeight){

                                    thisB.velocity1(toV(trackSpeed*Math.signum(Char.charPosition()[0]-thisB.x),retreatSpeed));
                                }
                                else if((!tickled)&&thisB.y<=maxHeight){
                                    thisB.velocity1(toV(trackSpeed*Math.signum(Char.charPosition()[0]-thisB.x),advanceSpeed));
                                } else{
                                    thisB.velocity1(toV(trackSpeed*Math.signum(Char.charPosition()[0]-thisB.x),0));
                                }
                            
                            }
                        });
                    this.addPause(9);
                }
                this.addLoopEnd();
            }
        };
        thisBoss.setScript(bossScript);
    }

    public void fill() {



        
        this.declare(60, 8000);
    }
    public class Bird extends Bullet{
        boolean alive;
        {
            this.acceleration1(toV(0,.015f));
            alive = true;
        }
        
        public void move() {
            super.move();
            if (age % 10 == 0&&alive) {
                Bullet.reset0();
                Bullet.shoot0(null);
                Bullet.position0(position());
                Bullet.type0(Type.Pellet);
                Bullet.velocity0(product(norm(velocity()), .001));
                Bullet.delay0(0);
                Bullet.color0(Col.WHITE);
                new Bullet() {

                    public void move() {
                        super.move();
                        if (age == 60) {
                            status = Status.Dead;
                        }
                    }
                }.add();
            }
            if (y >scrHeight&&yv>1f) {
                alive=false;
                yv*=-.2f;
            }
        }
        public void explode() {
                Bullet.reset0();
                Bullet.shoot0(null);
                Bullet.position0(position());
                Bullet.type0(Type.Outlined);
                Bullet.delay0(10);
                Bullet.color0(Col.WHITE);
                Bullet.shoot0(SE.POP);
                for(double i = 0;i<1;i+=1./9)
                {
                    Bullet.velocity0(toXY(toV(1.3f,(float)(i*2*PI))));
                new Bullet() {

                    public void move() {
                        super.move();
                        if (age == 9) {
                            status = Status.Dead;
                        }
                    }
                }.add();
                }
        }
    }

    public class RedBird extends Bird {

        {
            this.type1(Type.Outlined);
            this.color1(Col.RED);
        }
    }
    

    public class YellowBird extends Bird {
        int explodeTime = 90;
        {
            this.type1(Type.Arrow);
            this.color1(Col.YELLOW);
        }

        public void move() {
            if (age == explodeTime) {
                explode();
                velocity1(Calc.product(velocity(), 2));
            }
            super.move();
        }
    }

    public class BlackBird extends Bird {
        int explodeTime = 90;
        int explodePiece = 18;
        {
            this.type1(Type.MetalF);
            this.color1(Col.BLACK);
        }
        public void move() {
            if (age == explodeTime-30) {
                this.color1(Col.RED);
            }
            if (age == explodeTime) {
                float angle = randomFloat(0,2*(float)PI);
                float speed = randomFloat(.5f,2.5f);
                for(double a = 0;a<1;a+=1./explodePiece){
                    Bullet.reset0();
                Bullet.type0(Type.Outlined);
                Bullet.position0(this.position());
                Bullet.velocity0(toXY(toV(speed,angle + (float)(a*2*PI))));
                Bullet.color0(Col.ORANGE);
                new Bullet().add();
                }
                this.status=Status.Fade;
                
            explode();
            }
            super.move();
        }
    }

    public class BlueBird extends Bird {
        int explodeTime = 60;
        double explodeAngle = PI/8;
        {
            this.type1(Type.Medium);
            this.color1(Col.CYAN);
        }
        public void move() {
            if (age == explodeTime) {
                for(int a = -1;a<=1;a+=1){
                    Bullet.reset0();
                Bullet.type0(Type.Small);
                Bullet.position0(this.position());
                Bullet.velocity0(Calc.rotate(velocity(), a*explodeAngle));
                Bullet.color0(Col.CYAN);
                Bullet.shoot0(null);
                new Bird().add();
                }
                this.status=Status.Dead;
                
            explode();
            }
            super.move();
        }
    }
}
