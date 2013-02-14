
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

public class KaewAttack extends BossScript {

    int moveDistance;
    int breakTime;
    int sign;
    int bulletNumber;
    float speed0;
    float dSpeed;
    int speedLayer;
    int radiusLayer;
    float dRadius;
    float radius0;
    Col[] colorList;
    
    

    public void start() {
        super.initializeBoss();
        moveDistance = 60;
        breakTime = 180;
        bulletNumber = 5;
        speed0 = .5f;
        dSpeed = .25f;
        speedLayer = 10;
        dRadius = 5;
        radius0 = 0;
        radiusLayer = 10;
        sign = 1;
        Col[] cList1 = {Col.BLUE,Col.CYAN,Col.CYAN,Col.WHITE,Col.WHITE,Col.YELLOW,Col.ORANGE,Col.ORANGE,Col.RED,Col.RED};
        Col[] cList2 = {Col.BLUE,Col.CYAN,Col.CYAN,Col.WHITE,Col.YELLOW,Col.ORANGE,Col.RED,Col.RED};
        Col[] cList3 = {Col.GRAY,Col.GRAY,Col.PINK,Col.PINK,Col.PURPLE,Col.PURPLE};
        Col[] cList4 = {Col.YELLOW,Col.GRAY,Col.GRAY,Col.BLACK};
        switch (difficulty) {
            case LUNATIC:
                moveDistance = 60;
        breakTime = 240;
        bulletNumber = 6;
        speed0 = .7f;
        dSpeed = .25f;
        speedLayer = 10;
        dRadius = 5;
        radius0 = 0;
        radiusLayer = 11;
        sign = 1;
        colorList = cList1;
                break;
            case HARD:
                moveDistance = 60;
        breakTime = 180;
        bulletNumber = 6;
        speed0 = .5f;
        dSpeed = .25f;
        speedLayer = 8;
        dRadius = 5;
        radius0 = 0;
        radiusLayer = 7;
        sign = 1;
        colorList = cList2;
                break;
            case NORMAL:
                moveDistance = 60;
        breakTime = 180;
        bulletNumber = 3;
        speed0 = .4f;
        dSpeed = .4f;
        speedLayer = 6;
        dRadius = 3;
        radius0 = 0;
        radiusLayer = 10;
        sign = 1;
        colorList = cList3;
                break;
            case EASY:
                moveDistance = 60;
        breakTime = 180;
        bulletNumber = 5;
        speed0 = .5f;
        dSpeed = .5f;
        speedLayer = 4;
        dRadius = 10;
        radius0 = 0;
        radiusLayer = 3;
        sign = 1;
        colorList = cList4;
                break;
        }
    }

    public void fill() {
        this.declare(120, 10000);
        this.moveBoss(Script.semiWidth, 120, 120);
        this.addPause(120);
        this.stopBoss();
        this.addLoopStart();
                this.addPause(breakTime / 2);
                this.moveBossTowardsPlayer(toV(90,120), breakTime/2, moveDistance, 60);

                this.addPause(breakTime / 2);
        this.actionArray.add(
                new Action() {

                    public void start() {
                        sign *= -1;
                        float rand = Char.getAngle(thisBoss.x, thisBoss.y);
                        int sign2 = 1;
                        
                            for(int v = 0;v<speedLayer;v++)
                            {
                                
                                sign2*=-1;
                                for(int r = radiusLayer-1;r>=0;r--)
                            {
                                rand+=sign2*sign;
                                for (double t = 0; t < 1; t += 1. / bulletNumber) {
                            sign2*=-1;
                            float angle = rand + (float) (t * 2 * PI)+randomFloat(0,2*(float)PI);
                            float[] position = sum(thisBoss.position(),
                                    sum(
                                    toXY(toF(toV((double)(r*dRadius+radius0),angle))),
                                    toXY(toF(toV(sign2*(double)(2*r*dRadius+radius0),angle+(float)PI/2)))
                                    )
                                    );
                            Bullet.reset0();
                            Bullet.tolerance0(50);
                            Bullet.delay0(20);
                            Bullet.color0(colorList[v]);
                            Bullet.type0(Bullet.Type.Medium);
                            Bullet.position0(position);
                            Bullet.velocity0(toXY(toF(toV(-sign2*(double)(v*dSpeed+speed0),angle+(float)PI/2))));
                            new Bullet().add();
                            }
                            }
                        }
                    }
                });

        this.stopBoss();

        this.addLoopEnd();
    }
}
