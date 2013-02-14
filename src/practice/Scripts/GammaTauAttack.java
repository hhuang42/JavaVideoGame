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

public class GammaTauAttack extends BossScript {
    {
        String[] nameSet = {"Developing Maturity : Green Apple",
            "Fruit of Knowledge : Fallen Apple",
            "Midas Judgement : Golden Apple",
            "Fridge Logic : Frozen Apple"};
        names = nameSet;
    }
    static double[][] apple3 = {{-0.185567, -0.474227}, {0.0214444, -0.49355}, {0.223133, -0.45629}, 
{0.330508, -0.277332}, {0.409729, -0.0852399}, {0.466022, 
  0.114776}, {0.431606, 0.318364}, {0.292617, 0.465628}, {0.0898148, 
  0.485689}, {-0.110624, 0.447765}, {-0.306554, 0.397838}, {-0.45481, 
  0.254126}, {-0.486214, 
  0.0542211}, {-0.407108, -0.138763}, {-0.327459, -0.331663}};
    static double[][] appleStem={{-.07,.5},{-.1,.6},{-.13,.7}};
    int moveDistance;
    int breakTime;
    boolean red;
    boolean green;
    boolean golden;
    boolean frozen;

    public void start() {
        super.initializeBoss();
        //difficulty = difficulty.NORMAL;
        moveDistance = 10;
        
        switch (difficulty) {
            case LUNATIC:
                breakTime = 15;
                green = true;
                red = true;
                golden = true;
                frozen = true;
                break;
            case HARD:

                breakTime = 20;
                green = true;
                red = true;
                golden = true;
                frozen = false;
                break;
            case NORMAL:
                
                breakTime = 30;
                green = true;
                red = true;
                golden = false;
                frozen = false;
                break;
            case EASY:
                breakTime = 80;
                green = true;
                red = false;
                golden = false;
                frozen = false;
                break;
        }
    }

    public void fill() {
        Action greenApple = new Action() {

                            public void start() {
                                float x = randomFloat(0, scrWidth);
                                float y = 60;
                                float angle = randomFloat(0,2*(float)PI);
                                float radius = randomFloat(40, 200);
                                float yv = 100f/radius;
                                    Bullet.reset0();
                                    Bullet.tolerance0(100);
                                    Bullet.velocity0(toV(0,yv));
                                    Bullet.type0(Bullet.Type.Medium);
                                    Bullet.color0(Col.GREEN);
                                for (double[] doublepts : apple3) {
                                    float[] floatpts = toF(doublepts);
                                    float[] position = sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                                    Bullet.position0(position);
                                    new Bullet().add();

                                }
                                
                                    Bullet.color0(Col.BROWN);
                                for (double[] doublepts : appleStem) {
                                    float[] floatpts = toF(doublepts);
                                    float[] position = sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                                    Bullet.position0(position);
                                    Bullet.type0(Bullet.Type.Medium);
                                    new Bullet().add();

                                }/*
                                    
                                    Bullet.color0(Col.BROWN);
                                    float[] floatpts = toV(-.07f,.5f);
                                    float[] position = sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                                    Bullet.position0(position);
                                    new Bullet().add();
                                floatpts = toV(-.1f,.6f);
                                    position = sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                                    Bullet.position0(position);
                                    new Bullet().add();
                                    
                                    
                                    floatpts = toV(-.13f,.7f);
                                    position = sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                                    Bullet.position0(position);
                                    new Bullet().add();*/
                            }
                        };
        Action redApple = new Action() {

                            public void start() {
                                final float centerX = randomFloat(0, scrWidth);
                                final float centerY = 60;
                                float angle = randomFloat(0,2*(float)PI);
                                float radius = 40;
                                    Bullet.reset0();
                                    Bullet.tolerance0(100);
                                    Bullet.type0(Bullet.Type.Medium);
                                    Bullet.color0(Col.GREEN);
                                    final float aim = Char.getAngle(centerX, centerY);
                                for (double[] doublepts : apple3) {
                                    float[] floatpts = toF(doublepts);
                                    float[] position = sum(product(rotate(floatpts,angle ),radius),toV(centerX,centerY));
                                    Bullet.position0(position);
                                    
                                    
                                    new Bullet(){
                                        public void move(){
                                            if(age==30){
                                                color1(Col.RED);
                                            }
                                            if(age==60)
                                            {
                                                velocity1(toXY(toV(2,aim)));
                                            }
                                            super.move();
                                        }
                                    }.add();

                                }
                                
                                    Bullet.color0(Col.BROWN);
                                for (double[] doublepts : appleStem) {
                                    float[] floatpts = toF(doublepts);
                                    float[] position = sum(product(rotate(floatpts,angle ),radius),toV(centerX,centerY));
                                    Bullet.position0(position);
                                    Bullet.type0(Bullet.Type.Medium);
                                    new Bullet(){
                                        float angle = Char.getAngle(x, y);
                                        public void move(){
                                            if(age==60)
                                            {
                                                velocity1(toXY(toV(2,aim)));
                                            }
                                            super.move();
                                        }
                                    }.add();

                                }/*
                                    
                                    Bullet.color0(Col.BROWN);
                                    float[] floatpts = toV(-.07f,.5f);
                                    float[] position = sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                                    Bullet.position0(position);
                                    new Bullet().add();
                                floatpts = toV(-.1f,.6f);
                                    position = sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                                    Bullet.position0(position);
                                    new Bullet().add();
                                    
                                    
                                    floatpts = toV(-.13f,.7f);
                                    position = sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                                    Bullet.position0(position);
                                    new Bullet().add();*/
                            }
                        };
        Action goldenApple = new Action() {

                            public void start() {
                                final float centerX = randomFloat(0, scrWidth);
                                final float centerY = 60;
                                final float angle = randomFloat(0,2*(float)PI);
                                final int sign = randomFloat(0,1)<.5?-1:1;
                                float radius = 60;
                                    Bullet.reset0();
                                    Bullet.tolerance0(100);
                                    Bullet.type0(Bullet.Type.Medium);
                                    Bullet.color0(Col.GREEN);
                                    final float aim = Char.getAngle(centerX, centerY);
                                for (double[] doublepts : apple3) {
                                    final float[] floatpts = toF(doublepts);
                                    final float[] position = sum(product(rotate(floatpts,angle ),radius),toV(centerX,centerY));
                                    Bullet.position0(position);
                                    
                                    
                                    new Bullet(){
                                        public void move(){
                                            if(age==30){
                                                color1(Col.YELLOW);
                                            }
                                            if(age==60)
                                            {
                                                Laser.reset0();
                                                Laser.position0(this.position());
                                                Laser.velocity0(Calc.norm(Calc.rotate(floatpts,angle+sign*(float)PI/2), 3));
                                                Laser.color0(this.color);
                                                Laser.length0(40);
                                                new Laser().add();
                                                this.status = Status.Fade;
                                            }
                                            super.move();
                                        }
                                    }.add();

                                }
                                
                                    Bullet.color0(Col.BROWN);
                                for (double[] doublepts : appleStem) {
                                    final float[] floatpts = toF(doublepts);
                                    final float[] position = sum(product(rotate(floatpts,angle ),radius),toV(centerX,centerY));
                                    Bullet.position0(position);
                                    Bullet.type0(Bullet.Type.Medium);
                                    new Bullet(){
                                        public void move(){
                                            if(age==60)
                                            {
                                                
                                                Laser.reset0();
                                                Laser.position0(this.position());
                                                Laser.velocity0(Calc.norm(Calc.rotate(floatpts,angle+sign*(float)PI/2), 3));
                                                Laser.color0(this.color);
                                                Laser.length0(40);
                                                new Laser().add();
                                                this.status = Status.Fade;
                                            }
                                            super.move();
                                        }
                                    }.add();

                                }/*
                                    
                                    Bullet.color0(Col.BROWN);
                                    float[] floatpts = toV(-.07f,.5f);
                                    float[] position = sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                                    Bullet.position0(position);
                                    new Bullet().add();
                                floatpts = toV(-.1f,.6f);
                                    position = sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                                    Bullet.position0(position);
                                    new Bullet().add();
                                    
                                    
                                    floatpts = toV(-.13f,.7f);
                                    position = sum(product(rotate(floatpts,angle ),radius),toV(x,y));
                                    Bullet.position0(position);
                                    new Bullet().add();*/
                            }
                        };
        Action frozenApple = new Action() {

                            public void start() {
                                final float centerX = randomFloat(0, scrWidth);
                                final float centerY = 60;
                                final float angle = randomFloat(0,2*(float)PI);
                                final int sign = randomFloat(0,1)<.5?-1:1;
                                float radius = 80;
                                
                                    final int explodeNumber=5;
                                    Bullet.reset0();
                                    Bullet.tolerance0(100);
                                    Bullet.type0(Bullet.Type.Medium);
                                    Bullet.color0(Col.GREEN);
                                    final float aim = Char.getAngle(centerX, centerY);
                                for (double[] doublepts : apple3) {
                                    final float[] floatpts = toF(doublepts);
                                    final float[] position = sum(product(rotate(floatpts,angle ),radius),toV(centerX,centerY));
                                    Bullet.position0(position);
                                    
                                    new Bullet(){
                                        public void move(){
                                            if(age==30){
                                                color1(Col.CYAN);
                                            }
                                            if(age==60)
                                            {
                                                for(double i = 0;i<1;i+=1./explodeNumber)
                                                {
                                                    Bullet.reset0();
                                                    Bullet.position0(this.position());
                                                    Bullet.velocity0(toXY(toV(1,randomFloat(0,2*(float)PI))));
                                                    Bullet.color0(this.color);
                                                    Bullet.shoot0(SE.POP);
                                                    Bullet.type0(Bullet.Type.Pellet);
                                                    new Bullet().add();
                                                }
                                                this.status = Status.Fade;
                                            }
                                            super.move();
                                        }
                                    }.add();

                                }
                                
                                    Bullet.color0(Col.BROWN);
                                for (double[] doublepts : appleStem) {
                                    final float[] floatpts = toF(doublepts);
                                    final float[] position = sum(product(rotate(floatpts,angle ),radius),toV(centerX,centerY));
                                    Bullet.position0(position);
                                    Bullet.type0(Bullet.Type.Medium);
                                    new Bullet(){
                                        public void move(){
                                            if(age==60)
                                            {
                                                
                                                for(double i = 0;i<1;i+=1./explodeNumber)
                                                {
                                                    Bullet.reset0();
                                                    Bullet.position0(this.position());
                                                    Bullet.velocity0(toXY(toV(1,randomFloat(0,2*(float)PI))));
                                                    Bullet.color0(this.color);
                                                    Bullet.shoot0(SE.POP);
                                                    Bullet.type0(Bullet.Type.Pellet);
                                                    new Bullet().add();
                                                }
                                                this.status = Status.Fade;
                                            }
                                            super.move();
                                        }
                                    }.add();

                                }
                            }
                        };
        this.declare(60, 10000);
        this.addLoopStart();

        //this.moveBossTowardsPlayer(120, 120, 60);

        for (int sign = -1; sign <= 1; sign += 2) {
            for (double t = 0; t <= 1; t += 1. / (5 - 1)) {
                if(green)
                {
                this.actionArray.add(greenApple);

                this.moveBossTowardsPlayer(60, breakTime, moveDistance, 60);
                this.addPause(breakTime);
                }
                if(red)
                {
                this.actionArray.add(redApple);

                this.moveBossTowardsPlayer(60, breakTime, moveDistance, 60);
                this.addPause(breakTime);
                }
                if(golden)
                {
                this.actionArray.add(goldenApple);

                this.moveBossTowardsPlayer(60, breakTime, moveDistance, 60);
                this.addPause(breakTime);
                }
                if(frozen)
                {
                this.actionArray.add(frozenApple);

                this.moveBossTowardsPlayer(60, breakTime, moveDistance, 60);
                this.addPause(breakTime);
                }
            }
            this.stopBoss();

        }

        this.addLoopEnd();
    }
}
