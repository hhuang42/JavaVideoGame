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

public class KiWanAttack extends BossScript {

    int moveDistance;
    int breakTime;
    int sign;
    int pauseTime;
    int bulletNumber;
    boolean attack;
    boolean parry;
    boolean riposte;
    int loop;
    float v0;
    int chargeTime;
    
    int afterChargeTime;
    double angle;
    double dA;
    int bN2;

    public void start() {
        super.initializeBoss();
        moveDistance = 60;
        breakTime = 150;
        bulletNumber = 4;
        sign = 1;
        pauseTime = 6;
        v0=.5f;
        dA = PI/8;
        bN2 = 3;
        loop = 3;
        attack = false;
        parry = false;
        riposte = false;
        chargeTime = 0;
        afterChargeTime = 180;

        switch (difficulty) {
            case LUNATIC:
                
                
                
        moveDistance = 20;
        breakTime = 40;
        bulletNumber = 5;
        sign = 1;
        pauseTime = 6;
        v0=2f;
        dA = PI/12;
        bN2 = 4;
        loop = 4;
        chargeTime = 0;
        riposte = true;
                break;
            case HARD:
                
                
        moveDistance = 20;
        breakTime = 40;
        bulletNumber = 4;
        sign = 1;
        pauseTime = 6;
        v0=2f;
        dA = PI/8;
        bN2 = 4;
        loop = 3;
        chargeTime = 0;
        parry = true;
                break;
            case NORMAL:
                
        moveDistance = 20;
        breakTime = 60;
        bulletNumber = 3;
        sign = 1;
        pauseTime = 6;
        v0=2f;
        dA = PI/8;
        bN2 = 4;
        loop = 3;
        chargeTime = 0;
        attack = true;
                break;
            case EASY:
                
        moveDistance = 20;
        breakTime = 120;
        bulletNumber = 3;
        sign = 1;
        pauseTime = 6;
        v0=2f;
        dA = PI/8;
        bN2 = 4;
        loop = 3;
        chargeTime = 0;
                break;
        }
    }

    public void fill() {
        new Movement();
        this.declare(120, 8000);
        this.addLoopStart();
        for(int i=0;i<loop;i++)
        {
        this.actionArray.add(new Action() {

            public void start() {
                sign *= -1;
                angle = Char.getAngle(thisBoss.x, thisBoss.y);
                Bullet.reset0();
                //Bullet.delay0(0);
                Bullet.color0(Col.GREEN);
                Bullet.type0(Bullet.Type.Laser);
                Bullet.shoot0(SE.LASER);
                for (int a = -bulletNumber+1; a <= bulletNumber-1; a += 2) {
                    float[] offset = toXY(toF(toV(180, angle + a * dA)));
                    Bullet.position0(sum(thisBoss.position(), offset));
                    Bullet.velocity0(norm(offset, -v0));
                    new Attack() {
                    }.add();
                }
            }
        });
        this.addPause(breakTime / 2);
        this.moveBossTowardsPlayer(toV(120, 150), breakTime / 2, moveDistance, 60);
        this.addPause(breakTime / 2);
        
        this.stopBoss();
        this.actionArray.add(new Action() {

            public void start() {
                sign *= -1;
                angle = Char.getAngle(thisBoss.x, thisBoss.y);
                Bullet.reset0();
                //Bullet.delay0(0);
                Bullet.color0(Col.CYAN);
                Bullet.type0(Bullet.Type.Laser);
                Bullet.shoot0(SE.LASER);
                for (int a = -bulletNumber+1; a <= bulletNumber-1; a += 2) {
                    float[] offset = toXY(toF(toV(90, angle + a * dA)));
                    Bullet.position0(thisBoss.position());
                    Bullet.velocity0(norm(offset, v0));
                    new Attack() {
                    }.add();
                }
            }
        });
        this.addPause(breakTime / 2);
        this.moveBossTowardsPlayer(toV(90, 120), breakTime / 2, moveDistance, 60);
        this.addPause(breakTime / 2);

        this.stopBoss();
        }
        
        this.addPause(chargeTime);
        if(attack)
        {
        this.actionArray.add(new Action() {

            public void start() {
                sign *= -1;
                angle = Char.getAngle(thisBoss.x, thisBoss.y);
                Bullet.reset0();
                //Bullet.delay0(0);
                Bullet.color0(Col.BLUE);
                Bullet.type0(Bullet.Type.Laser);
                for (int a = -2; a <= 2; a += 2) {
                    float[] offset = toXY(toF(toV(90, angle + a * dA/2)));
                    Bullet.position0(thisBoss.position());
                    Bullet.velocity0(norm(offset, 3));
                    new Movement() {
                    }.add();
                }
            }
        });
        
        }
        else if(parry||riposte){
            this.actionArray.add(new Action() {

            public void start() {
                sign *= -1;
                angle = Char.getAngle(thisBoss.x, thisBoss.y);
                Bullet.reset0();
                //Bullet.delay0(0);
                Bullet.color0(Col.YELLOW);
                for (int a = -3; a <= 3; a += 2) {
                    float[] offset = toXY(toF(toV(180, angle + a * dA/2)));
                    Bullet.position0(sum(thisBoss.position(), offset));
                    Bullet.velocity0(norm(offset, -3));
                    new Movement() {
                    }.add();
                }
            }
        });
            this.addPause(40);
            this.actionArray.add(new Action() {

            public void start() {
                sign *= -1;
                Bullet.reset0();
                //Bullet.delay0(0);
                Bullet.color0(Col.ORANGE);
                for (int a = -3; a <= 3; a += 2) {
                    for(int sign = -1;sign<=1;sign+=2)
                    {
                    float[] offset = toXY(toF(toV(60, angle + a * dA/2)));
                    Bullet.position0(sum(thisBoss.position(), offset));
                    Bullet.velocity0(norm(rotate(offset,PI/2*sign), -3));
                    new Movement() {
                    }.add();
                    }
                }
            }
        });
            if(riposte){
                this.addPause(40);
            this.actionArray.add(new Action() {

            public void start() {
                sign *= -1;
                Bullet.reset0();
                //Bullet.delay0(0);
                Bullet.color0(Col.RED);
                for (int a = -5; a <= 5; a += 2) {
                    float[] offset = toXY(toF(toV(120, angle + a * dA)));
                    Bullet.position0(sum(thisBoss.position(), offset));
                    Bullet.velocity0(toXY(toF(toV(3,angle - a * dA/2))));
                    new Movement() {
                    }.add();
                }
            }
        });
            }
        }
        
        this.addPause(afterChargeTime/2);
        this.moveBossAbovePlayer(120, afterChargeTime/2,60);
        this.addPause(afterChargeTime/2);
        this.stopBoss();
        this.addLoopEnd();
    }
    
    public class Movement extends Bullet {

        int spawnNumber = 5;
        
        int time = 10;
        int bulletNumber = 8;

        {
                tolerance1(60);
                shoot(SE.SLASH);
                type1(Bullet.Type.Laser);
        }

        public void move() {
            super.move();
            if (age % time == 1 && spawnNumber > 0) {

                Bullet.reset0();
                Bullet.position0(this.position());
                Bullet.color0(this.color);
                for (int a = 0; a < bulletNumber; a += 1) {
                    Bullet.velocity0(sum(norm(velocity(),.5f),
                            Calc.rotate(norm(velocity(),1.f), (a + .5) * 2 * PI / bulletNumber)));
                    Bullet.type0(Bullet.Type.Pellet);
                    new Bullet().add();
                }
                spawnNumber--;
            }
        }
    }

    public class Attack extends Bullet {

        int spawnNumber = 3;
        
        int time = 90/spawnNumber;
        int bulletNumber = bN2;

        {
            
                shoot(SE.SLASH);
                type1(Bullet.Type.Laser);
        }

        public void move() {
            super.move();
            if (age % time == 1 && spawnNumber > 0) {

                Bullet.reset0();
                Bullet.position0(this.position());
                Bullet.color0(this.color);
                for (int a = 0; a < bulletNumber; a += 1) {
                    Bullet.velocity0(sum(norm(velocity(),.25f),
                            Calc.rotate(norm(velocity(),.75f), (a + .5) * 2 * PI / bulletNumber)));
                    Bullet.type0(Bullet.Type.Pellet);
                    new Bullet().add();
                }
                spawnNumber--;
            }
        }
    }
}
