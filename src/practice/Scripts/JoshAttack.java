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

public class JoshAttack extends BossScript {
    {
    }
    int moveDistance;
    int breakTime;
    int sign;
    int pauseTime;
    int bulletNumber;
    double angle;
    Col[] colList;
    int step;
    int stepPeriod;
    int mult;
    boolean gottIstTot;
    boolean derWilleZurMacht;
    boolean ubermensch;

    public void start() {
        super.initializeBoss();
        colList = new Col[] {Col.GRAY,Col.YELLOW,Col.GRAY,Col.YELLOW,Col.GRAY,Col.YELLOW};
        moveDistance = 60;
        breakTime = 600;
        bulletNumber = 24;
        step = 0;
        stepPeriod = 1;
        sign = 1;
        mult = 3;
        ubermensch = false;
        gottIstTot = false;
        derWilleZurMacht = false;
        switch (difficulty) {
            case LUNATIC:
                moveDistance = 5;
        breakTime = 60;
        bulletNumber = 24;
        mult = 1;
        colList = new Col[] {Col.RED,Col.BLUE,Col.GREEN};
                ubermensch = true;
                break;
            case HARD:
                
        moveDistance = 5;
        breakTime = 60;
        bulletNumber = 20;
        mult = 1;
        colList = new Col[] {Col.RED,Col.ORANGE,Col.YELLOW,Col.GREEN,Col.BLUE,Col.PURPLE};
        this.derWilleZurMacht=true;
                break;
            case NORMAL:
                
        breakTime = 150;
        bulletNumber = 24;
        mult = 4;
        colList = new Col[] {Col.BLUE,Col.GREEN,Col.CYAN};
                break;
            case EASY:
                
        breakTime = 600;
        gottIstTot = true;
        bulletNumber = 24;
        mult = 3;
        colList = new Col[] {Col.GRAY,Col.YELLOW};
                break;
        }
    }

    public void fill() {
        this.declare(120, 10000);
        this.addLoopStart();
        if(ubermensch){
            this.actionArray.add(new Action() {

            public void start() {
                step++;
                sign*=-1;
                angle = randomFloat(0,2*(float)PI);
                Bullet.reset0();
                Bullet.type0(Bullet.Type.DarkPellet);
                for(int i = 0;i<colList.length*mult;i++){
                    
                    final int timeTaken = randomInt(200,1200);
                    final float[] centerPoint = toV(randomFloat(0,scrWidth),randomFloat(semiWidth/2,2*semiWidth/2));
                    for(int m = -bulletNumber+1;m<=bulletNumber-1;m+=2)
                    {
                    //final float a = (float)(PI*(i/(colList.length*mult)));
                    final float n = (float)(PI*(m/(bulletNumber-1.))+angle);
                    final float[] finalVelocity = sum(toXY(toF(toV(1.5,n))),toV(0,0));
                    final float[] finalPosition = centerPoint;
                    final float[] initialPosition = toV(randomFloat(0,scrWidth),-20);
                    final float [] averageVelocity = product(diff(initialPosition,finalPosition),1./timeTaken);
                    final float [] initialVelocity = diff(diff(averageVelocity,finalVelocity),averageVelocity);
                    final float [] acceleration = product(diff(initialVelocity,finalVelocity),1./timeTaken);
                    Bullet.velocity0(initialVelocity);
                    Bullet.delay0(0);
                    
                   
                    Bullet.shoot0(null);
                    Bullet.tolerance0(1000);
                    Bullet.position0(initialPosition);
                    Bullet.acceleration0(acceleration);
                    final Col col = colList[i%colList.length];
                    Bullet.color0(col);
                    new Bullet(){
                        public void move(){
                            super.move();
                            if(age==timeTaken+1){
                                Bullet.reset0();
                                Bullet.shoot0(SE.KIRA);
                                Bullet.velocity0(velocity());
                                Bullet.color0(this.color);
                                Bullet.position0(this.position());
                                Bullet.type0(Bullet.Type.Pellet);
                                Bullet.delay0(0);
                                new Bullet().add();
                                this.status=Status.Dead;
                            }
                        };
                    }.add();
                    }
                }
            }
        });
        
        this.addPause(breakTime / 2);
        this.moveBossTowardsPlayer(toV(120, 150), breakTime / 2, moveDistance, 60);
        this.addPause(breakTime / 2);
        }else
        if(derWilleZurMacht){
        this.actionArray.add(new Action() {

            public void start() {
                step++;
                sign*=-1;
                angle = Char.getAngle(thisBoss.x, thisBoss.y);
                Bullet.reset0();
                Bullet.type0(Bullet.Type.DarkPellet);
                for(int i = 0;i<colList.length*mult;i++){
                    for(double m = 0;m<1;m+=1./bulletNumber)
                    {
                    final float a = (float)(2*PI*i/(colList.length*mult)+angle);
                    final float n = (float)(2*PI*m)+a;
                    float[] velocity = product(sum(toXY(toV(-.25f,a)),toXY(toV(1f*(float)sin(m*PI),n))),1.);
                    Bullet.velocity0(toV(-sign*randomFloat(.01f,3f),0));
                    Bullet.delay0(0);
                    
                   
                    Bullet.shoot0(null);
                    Bullet.tolerance0(50);
                    Bullet.position0(toV(semiWidth+sign*(semiWidth+10),randomFloat(0,semiHeight*.7f)));
                    final Col col = colList[i%colList.length];
                    Bullet.color0(col);
                    new Bullet(){
                        boolean released = false;
                        Col thisC = col;
                        public void move(){
                            super.move();
                            if(!released&&step%stepPeriod==0&&thisC == colList[(step/stepPeriod)%colList.length]){
                                released = true;
                                this.acceleration1(toV(0,.0005f*(float)sqrt(semiHeight*.7f-y)));
                                this.type1(Bullet.Type.Pellet);
                                SE.KIRA.play();
                            }
                        };
                    }.add();
                    }
                }
            }
        });
        
        this.addPause(breakTime / 2);
        this.moveBossTowardsPlayer(toV(120, 150), breakTime / 2, moveDistance, 60);
        this.addPause(breakTime / 2);
        
        this.stopBoss();}
        else{
        this.actionArray.add(new Action() {

            public void start() {

                angle = Char.getAngle(thisBoss.x, thisBoss.y);
                Bullet.reset0();
                Bullet.position0(thisBoss.position());
                Bullet.type0(Bullet.Type.Pellet);
                for(int i = 0;i<colList.length*mult;i++){
                    for(double m = 0;m<1;m+=1./bulletNumber)
                    {
                    final float a = (float)(2*PI*i/(colList.length*mult)+angle);
                    final float n = (float)(2*PI*m)+a;
                    float[] velocity = product(sum(toXY(toV(-.25f,a)),toXY(toV(1f*(float)sin(m*PI),n))),1.);
                    Bullet.velocity0(velocity);
                    
                    Bullet.color0(colList[i%colList.length]);
                    
                    Bullet.acceleration0(product(velocity,-1f/240));
                    if(gottIstTot)
                    {
                    new Bullet(){
                        
                            int time;
                            {
                                time = (int)(50/(mag(velocity())));
                            }
                        public void move(){
                            super.move();
                            if(age==200){
                                SE.KIRA.play();
                                this.type=Bullet.Type.DarkPellet;
                                this.acceleration1(
                                        toXY(toV(1f/randomFloat(100,1000),randomFloat(0,2*(float)PI)))
                                        );
                            }
                        }
                    }.add();
                    }
                    else{
                        new Bullet(){
                            float[] initialV;
                            boolean repelled;
                            {
                                initialV = velocity();
                                repelled = false;
                            }
                        public void move(){
                            super.move();
                            
                            if(age==200&&!repelled){
                                this.acceleration1(
                                        toV(0,0)
                                        );
                            }
                            if(age==(int)(mag(initialV)*300)){
                                SE.KIRA.play();
                                repelled = true;
                                this.type=Bullet.Type.DarkPellet;
                                this.acceleration1(norm(initialV,.04f));
                                this.velocity1(norm(initialV,.0001f));
                                
                            }
                            if(age==40+(int)(mag(initialV)*300)){
                                this.acceleration1(toV(0,0));
                                
                            }
                        }
                    }.add();
                    }
                    }
                }
            }
        });
        
        this.addPause(breakTime / 2);
        this.moveBossTowardsPlayer(toV(120, 150), breakTime / 2, moveDistance, 60);
        this.addPause(breakTime / 2);
        
        this.stopBoss();
        }

        this.addLoopEnd();
    }
}
