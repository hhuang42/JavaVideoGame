/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice.Scripts;

import practice.*;
import static java.lang.Math.*;
import java.util.ArrayList;
import static practice.Calc.*;

/**
 *
 * @author Henry
 */
public class Attack2 extends BossScript {

    public void start() {
        //super.initializeBoss(10000);

    }

    public void fill() {
        this.moveBoss(Script.semiWidth, 120, 120);
        this.addPause(120);
        this.moveBoss(Script.semiWidth, 120, 120);
        this.addLoopStart();
        //this.moveBossTowardsPlayer(120, 120, 60);
        this.moveBoss(Script.semiWidth, 120, 60);
        this.addPause(60);
        this.moveBoss(Script.semiWidth, 120, 60);

        for (double blah = -1; blah <= 2; blah += 2. / 20) {
            final double fBlah = blah;
            actionArray.add(
                    new Action(this) {

                        public void start() {
                            Bullet.reset0();
                            for (int sign = -1; sign <= 1; sign += 2) {
                                for (double row = -1; row <= 1; row += 2. / 9) {
                                    Bullet.position0(Calc.sum(thisBoss.position(),
                                            Calc.toXY(
                                            toF(toV(60, PI / 2 + sign * (fBlah + row * 1) * PI / 2)))));
                                    Bullet.velocity0(
                                            Calc.toXY(
                                            toF(toV(2, -PI / 2 + sign * (fBlah + 1 + row * 1) * PI / 2))));
                                    Bullet newB = new Bullet();
                                    if (sign == 1) {
                                        newB.color1(5);
                                    }
                                    newB.add();
                                }
                            }
                        }
                    });
            this.addPause(10);

        }
        this.moveBossAbovePlayer(120, 60);
        for(int u = 0;u<4;u++)
        {
        for(int k = 0;k<10;k++)
        {
        actionArray.add(
                new Action(this) {

                    public void start() {
                        for (double t = 0; t < 1; t += 1. / 10) {
                            Laser.reset0();
                            Laser.length0(40);
                            Laser.type0(Bullet.Type.Pellet);
                            Laser.color0(randomInt(1,10));        
                            Laser.position0(thisBoss.position());
                            Laser.velocity0(toXY(toV(randomFloat(1,4), randomFloat(0,2*(float)PI))));
                            Laser newB = new Laser();
                            newB.add();
                        }
                    }
                });
        this.addPause(6);
        }
        this.moveBossAbovePlayer(120, 60);
        }
        this.addPause(00);
        this.addLoopEnd();
    }
}
