
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

public class SamAttack extends BossScript {

    int moveDistance;
    int breakTime;
    int sign;
    int maxIteration;
    int iterateTime;
    int sides;
    float v0;
    double[][] matrix;
    
    double[][] matrix1 = {{.9,1.3},{.95,.8},{-.95,.8},{-.9,1.3}/*,{.7,.9},{-.7,.9}*/};
    double[][] matrix2 = {{.6,.8},{-.6,.8},{-.5,1.2},{-.4,1.3}};
    
    double[][] matrix6 = {{.6,1.1},{-.6,.7},{-.5,.9},{-.4,1.3}};
    double[][] matrix3 = {{-.5,.8},{-.6,.8},{-.5,1.2},{-.4,1.3}};
    double[][] matrix4 = {{.5,.9},{-.5,.6},{-.5,1.},{-.5,1.4}};
    double[][] matrix5 = {{-.45,.6},{-.55,1.},{-.65,1.4}};

    public void start() {
        super.initializeBoss();
        moveDistance = 10;
        breakTime = 300;
        v0 = 1.2f;
        maxIteration = 3;
        iterateTime = 60;
        sign = 1;
        sides = 6;
        matrix = matrix3;
        switch (difficulty) {
            case LUNATIC:
                
        v0 = 1.4f;
        matrix = matrix6;
        sides = 7;
        iterateTime = 45;
        
        breakTime = 210;
                break;
            case HARD:
v0 = 1.25f;
        matrix = matrix6;
        sides = 5;
        iterateTime = 45;
        
        breakTime = 240;
                break;
            case NORMAL:
v0 = 1.20f;
        matrix = matrix5;
        sides = 6;
        iterateTime = 90;
        
        breakTime = 300;
                break;
            case EASY:
                v0 = 1f;
        sides = 4;
        iterateTime = 90;
        matrix = matrix5;
        
        breakTime = 420;
                break;
        }
    }

    public void fill() {
        this.declare(semiHeight*.6, 10000);
        this.addLoopStart();
            this.actionArray.add(
                    new Action() {

                        public void start() {
                            sign*=-1;
                            float rand = randomFloat(0,2*(float)PI);

                            for (double t = 0; t < 1; t += 1. / sides) {
                                Bullet.reset0();
                                Bullet.tolerance0(100);
                                Bullet.position0(thisBoss.position());
                                Bullet.type0(Bullet.Type.Medium);
                                Bullet.color0(Col.PURPLE);
                                Bullet.velocity0(toXY(toV(v0,rand+(float)(t*2*PI))));
                                new MatrixBullet(maxIteration,sign) .add();
                            }
                        }
                    });
            this.addPause(breakTime);

            this.stopBoss();

        this.addLoopEnd();
    }
    public class MatrixBullet extends Bullet{
        int iteration;
        int sign;
        public MatrixBullet(int iteration, int sign){
            this.iteration = iteration;
            this.sign = sign;
        }
                                    public void move(){
                                        super.move();
                                    if((iteration==maxIteration?age==iterateTime/2:age==iterateTime)&&iteration>0)
                                    {
                                        for(double[] data : matrix ){
                                            float[] floatData = toF(data);
                                            Bullet.reset0();
                                            Bullet.position0(this.position());
                                            Bullet.velocity0(Calc.rotate(product(velocity(),floatData[1]),sign*PI*floatData[0]));
                                            //Bullet.delay0(0);
                                            Bullet.type0(this.type);
                                            Bullet.color0(color-(int)(2*floatData[1]-1));
                                            new MatrixBullet(iteration -1,sign).add();
                                        }
                                        //this.status=Status.Dead;
                                    }
                                    
                                        }
                                }
}
