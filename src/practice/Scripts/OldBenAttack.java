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
public class OldBenAttack extends BossScript {
    
    

    int bulletWaves;
    int bulletRings;
    int centerHeight;
    Familiar Sora;
    Familiar Riku;
    Familiar[] Heroes = {Sora,Riku};
    public void start() {
        super.initializeBoss();
        //difficulty = difficulty.NORMAL;
        
        switch(difficulty){
            case LUNATIC:
                bulletWaves = 1;
                bulletRings = 20;
                centerHeight = 175;
                break;
            case HARD:
                bulletWaves = 2;
                bulletRings = 16;
                centerHeight = 160;
                break;
            case NORMAL:
                bulletWaves = 1;
                bulletRings = 14;
                centerHeight = 135;
                break;
            case EASY:
                bulletWaves = 1;
                bulletRings = 6;
                centerHeight = 60;
                break;
        }
    }

    public void fill() {
        this.moveBoss(Script.semiWidth, 60, 120);
        this.addPause(120);
        this.moveBoss(Script.semiWidth, 60, 120);
        this.addLoopStart();
        this.actionArray.add(
                new Action(){
                    public void start(){
                        for(int i=0;i<2;i++)
                        {
                        Heroes[i] = new Familiar(){
                            public void move(){
                                if(age==30){
                                    velocity1(toV(0,0));
                                    acceleration1(toV(0,0));
                                }
                                if(age==300){
                                    this.toFade();
                                }
                                super.move();
                            }
                        };
                        Heroes[i].position1(thisBoss.position());
                        Heroes[i].velocity1(
                                product(diff(thisBoss.position(),
                                toV(semiWidth+(i==1?-120:120),centerHeight)),1./(30/2.) ));
                        Heroes[i].acceleration1(
                                product(diff(thisBoss.position(),
                                toV(semiWidth+(i==1?-120:120),centerHeight)),-1./(30/2.)/(30.+1)));
                        Heroes[i].color=(i==0?Heroes[i].color.Blue:Heroes[i].color.Yellow);
                        Heroes[i].add();
                        }
                    }
                }
                );
        this.addPause(30);
        //this.moveBossTowardsPlayer(120, 120, 60);
        for(double t=0;t<1;t+=1./50)
        {
            final double t0=t;
        actionArray.add(
                new Script.Action(this) {

                    public void start() {
                        Bullet.reset0();
                        
                        for(int t= 0;t<bulletWaves;t++){
                        for (int sign = -1; sign <= 1; sign += 2) {
                            for (int sign2 = -1; sign2 <= 1; sign2 += 2) {
                                Bullet.position0(sum(Heroes[sign==-1?1:0].position(),toV(sign*15,0)));
                                Bullet.velocity0(toV(sign * randomFloat(6, 7), sign2 * Calc.randomFloat(.6f, 2.1f)));
                                Bullet.acceleration0(toV(sign * -.3f, 0));
                                Bullet.tolerance0(50);
                                Bullet newB = new Bullet() {
                                    public void move(){
                                        if(age==60){
                                            this.status=Status.Fade;
                                            if(true||Calc.randomFloat(0, 1)<.2+t0*.8){
                                            double spin = randomFloat(0,1);
                                        for (double n = 0; n < 1;n+= 1./7) {
                                                            Bullet.reset0();
                                                            Bullet.type0(Type.Pellet);
                                                            Bullet.shoot0(SE.KIRA);
                                                            Bullet newB = new Bullet(){
                                                                public void move(){
                                                                    if(age==11){
                                                                        if(Calc.randomFloat(0, 1)>.2+t0*.8){
                                                                            this.status=Status.Fade;
                                                                        }
                                                                    }
                                                                    super.move();
                                                                }
                                                            };
                                                            newB.position1(this.position());
                                                            if(randomFloat(0,1)<1)
                                                            {
                                                            newB.color1(9);
                                                            }
                                                            else if(randomFloat(0,1)<.5){
                                                                newB.color1(3);
                                                            }
                                                            else{
                                                                newB.color1(2);
                                                            }
                                                            newB.velocity1(toXY(toV(.4f+1.2f*(float)t0, (float)(2*PI*(n+spin)))));
                                                            newB.add();
                                                        }
                                            }
                                        }
                                        super.move();
                                    }
                                };
                                if (sign == 1) {
                                    newB.color1(6);
                                } else {
                                    newB.color1(3);
                                }
                                newB.type1(Bullet.Type.Arrow);
                                newB.add();
                            }
                        }
                    }
                    }});
        this.addPause(2);
        
            actionArray.add(
                    new Action(this){
                        public void start(){
                            Bullet.reset0();
                            for(double a = 0;a<1;a+=1./bulletRings){
                            for (int sign = -1; sign <= 1; sign += 2) {
                                Bullet.position0(Heroes[sign==-1?1:0].position());
                                
                                    float rand = randomFloat(-1,1);
                                    Bullet.velocity0(product(toXY(toV(sign*8.5f,(float)(PI+1*PI*pow(rand,3)))),toV(1,1.3f)));
                                    //Bullet.acceleration0(toXY(toV(-3f/60,(float)(a*2*PI))));
                                    //Bullet.w0(sign*2*(float)PI/61);
                                    Bullet.tolerance0(0);
                                    Bullet.type0(Bullet.Type.Outlined);
                                    final int stage1 = 30;
                                    final int stage2 = 300;
                                    Bullet.delay0(stage1-1);
                                    Bullet newB = new Bullet(){
                                        float fate;
                                        public void move(){
                                            if(age==1){
                                                    acceleration1(Calc.product(velocity(), -1./(stage1)));
                                            }
                                            if(age==stage1){
                                                acceleration1(toV(0,0));
                                                this.status=Status.Dead;
                                            }
                                            if(age==stage2){
                                                acceleration1(Calc.product(velocity(),1));
                                                //this.status=Status.Dead;
                                            }
                                            super.move();
                                        }
                                    };
                                    if (sign == 1) {
                                    newB.color1(6);
                                } else {
                                    newB.color1(3);
                                }
                                    newB.add();
                                }
                            }
                        }
                    }
                    );
            this.addPause(2);
    }        
        this.addPause(120);


        this.addLoopEnd();
    }
}
