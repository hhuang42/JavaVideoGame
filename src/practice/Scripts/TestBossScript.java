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
public class TestBossScript extends Script {
    BossScript test = new Attack1();
    {
        test.init();
    }
    public void run() {
        test.run();
    }

    public static class Attack1 extends BossScript {

        final int layer = 20;
        final int ring = 6;
        double t = 1;
        int test = 0;

        public void init(){
            super.init();
            initializeBoss();
            //BGM.S4B.play();
        }

        public void fill() {
            actionArray.add(
                    new Action(this){
                        public void start(){
                            System.out.println("sdfsdf");
                    thisBoss.moveTo(120, toV(semiWidth, 120));}
                    });
            actionArray.add(this.pause(120));
            actionArray.add(
                    new Action(this){
                        public void start(){
                    thisBoss.stop();}
                    });
            actionArray.add(this.loopStart());
                for (int k = 0; k < 3; k++) {
                    actionArray.add(
                    new Action(this){
                        public void start(){
                            t-=PI*(1/7.);
                    thisBoss.stop();


                    for (int r = 0; r < ring; r++) {
                        for (int i = 0; i < layer; i += 1) {
                            final double angle = 
                                    
                                    (2 * PI * r / ring + 
                                    2 * PI / ring * i / layer)
                                    + PI;
                            //final int layer0 = i;
                            final double newangle = 
                                    angle+t + (2*PI * 
                                    ((double) i / layer));

                            Bullet thisB = new Bullet() {

                                double angle0 = newangle;
                                double t0 = t;

                                public void move() {
                                    this.changeSpeed(1, 60, 0);
                                    this.changeSpeed(61, 150, toXY(toF(toV(1.5, angle0))));
                                    if (age == 61) {
                                        SE.KIRA.play();
                                    }
                                    //System.out.println(age);
                                    super.move();
                                }
                            };
                            thisB.reset();
                            thisB.position1(thisBoss.position());
                            thisB.velocity1(toXY(toF(toV(3, angle))));
                            thisB.add();
                            test++;

                        }

                    }
                        }
                    });
                    for(int m = 0; m<0;m++){
                    
                    actionArray.add(
                            new Action(this){
                                
                                public void start(){
                                    int t =1;
                                    for(double a = 0; a<1;a+=1./20){
                                        t*=-1;
                                        final int t0 = t;
                                        Bullet thisB = new Bullet() {
                                
                                public void move() {
                                    if(age==1){w=t0*(float)PI/(121f);}
                                    if(age==61){w=0f;}
                                    super.move();
                                    
                                }
                            };
                            
                            thisB.reset();
                            thisB.color1(3);
                            thisB.position1(thisBoss.position());
                            thisB.velocity1(toXY(toF(toV(1.5, a*2*PI))));
                            thisB.add();
                                    }
                                }
                            }
                            );
                    actionArray.add(this.pause(10));
                
                }
                    actionArray.add(this.pause(90));
                }
                actionArray.add(this.pause(0));
                
                
                System.out.println(this.actionIndex);
                actionArray.add(this.pause(120));

            actionArray.add(this.loopBasicEnd());
        }
    }
}
