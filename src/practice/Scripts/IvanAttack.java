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

public class IvanAttack extends BossScript {
{
        String[] nameSet = {
            "Slaughter \"Zombie Charge\"",
            "Slaughter \"NightStalker Hoard\"",
            "Apocalypse \"Double Tap Zombie\"",
            "Apocalypse \"Bone Zombie Rush\""
        };
        names = nameSet;
    }
    int moveDistance;
    int breakTime;
    int sign;
    int bulletNumber;
    float killSpeed;
    float chargeSpeed;
    
        float triggerRatio;
        float killRadius;
    Col[] colorList;
    boolean regular;
    boolean doubleTap;
    boolean bone;
            
    
    

    public void start() {
        super.initializeBoss();
        moveDistance = 60;
        breakTime = 60;
        bulletNumber = 4;
        killSpeed = 2.5f;
        regular = false;
        doubleTap = false;
        bone = false;
        chargeSpeed = .8f;
        sign = 1;
        
            triggerRatio =2;
            killRadius = 20;
        
        switch (difficulty) {
            case LUNATIC:
                
        breakTime = 60;
                bone = true;
        bulletNumber = 4;
        killSpeed = 3f;
        chargeSpeed = .8f;
            triggerRatio =5;
                break;
            case HARD:
        breakTime = 60;
                
        bulletNumber = 3;
        killSpeed = 2.5f;
        chargeSpeed = .8f;
            triggerRatio =2.5f;
                doubleTap = true;
                break;
            case NORMAL:
        breakTime = 60;
                
        bulletNumber = 3;
        killSpeed = 2.5f;
        chargeSpeed = .8f;
            triggerRatio =2;
                regular = true;
                break;
            case EASY:
        breakTime = 90;
                
                
        bulletNumber = 2;
        killSpeed = 2.5f;
        chargeSpeed = .6f;
            triggerRatio =2;
                regular = true;
                break;
        }
    }

    public void fill() {
        Action zombieRun = 
                new Action() {

                    public void start() {
                        sign *= -1;
                        for(int i=0;i<bulletNumber;i++)
                        {
                            float[] position = sum(thisBoss.position(),toXY(toV(randomFloat(150,225),2*(float)PI*(sign/4f+i+randomFloat(0,.5f))/bulletNumber)));
                        Bullet.reset0();
                        Bullet.shoot0(null);
                        Bullet.delay0(20);
                        Bullet.tolerance0(1500);
                        Bullet.position0(position);
                        Bullet.velocity0(norm(diff(position,thisBoss.position()),chargeSpeed));
                        if(bone){
                        new BoneZombie().add();
                        }
                        if(regular){
                            new Zombie().add();
                        }
                        if(doubleTap){
                            new DoubleTap().add();
                        }
                        }
                        
                    }
                };
        this.declare(120, 8000);
        this.addLoopStart();
                this.addPause(breakTime / 2);
                this.moveBossTowardsPlayer(toV(90,120), breakTime/2, moveDistance, 60);

        this.actionArray.add(zombieRun);
                this.addPause(breakTime / 2);
        this.actionArray.add(zombieRun);

        this.stopBoss();

        this.addLoopEnd();
    }
    
    public class Zombie extends Bullet{
        Bullet killShot;
        
        float spreadAngle;
        int pieces;
        {
            
                        color1(Col.PURPLE);
                        type1(Bullet.Type.MetalF);
            pieces = 8;
            spreadAngle = (float)PI/20;
        }
        public void move(){
            super.move();
            if(triggerRatio*age+20>=Calc.distance(this.position(), thisBoss.position()) &&(killShot==null||killShot.status==Status.Dead))
            {
                Bullet.reset0();
                Bullet.position0(thisBoss.position());
                float[] velocity = sum(norm(diff(thisBoss.position(),this.position()),killSpeed+chargeSpeed),this.velocity());
                Bullet.velocity0(velocity);
                Bullet.shoot0(SE.ESHOOT1);
                Bullet.type0(Bullet.Type.Arrow);
                Bullet.color0(Col.YELLOW);
                thisBoss.velocity1(sum(thisBoss.velocity(),norm(velocity,-.3f)));
                killShot = new Bullet();
                killShot.add();
            }
            if(
                    killShot!=null&&
                    killShot.status==Status.Active&&
                    mag(diff(this.position(),killShot.position()))<killRadius)
            {
                explode();
            }
        }
        
            public void explode(){
                for(int a = -pieces+1;a<=pieces-1;a+=2){
                    Bullet.reset0();
                    
                    this.status=Status.Fade;
                    Bullet.position0(this.position());
                    Bullet.type0(Bullet.Type.Medium);
                    Bullet.color0(randomFloat(0,1)<.25?Col.PURPLE:randomFloat(0,1)<0?Col.RED:Col.GREEN);
                    Bullet.shoot0(SE.POP);
                    Bullet.delay0(0);
                    Bullet.velocity0(product(Calc.rotate(killShot.velocity(),a*spreadAngle),randomFloat(.5f,.9f)));
                    new Bullet().add();
                }
            }
    }
    
    
    public class DoubleTap extends Zombie{
        
        {
            
                        color1(Col.BLUE);
                        type1(Bullet.Type.MetalF);
            pieces = 6;
            spreadAngle = (float)PI/10;
        }
        
            public void explode(){
                for(int a = -pieces+1;a<=pieces-1;a+=2){
                    Bullet.reset0();
                    
                    this.status=Status.Fade;
                    Bullet.position0(this.position());
                    Bullet.type0(Bullet.Type.Medium);
                    Bullet.color0(Col.BLUE);
                    Bullet.shoot0(null);
                    Bullet.delay0(0);
                    Bullet.velocity0(product(Calc.rotate(killShot.velocity(),a*spreadAngle),randomFloat(.1f,.5f)));
                    new Bullet().add();
                }
                    Bullet.reset0();
                    Bullet.shoot0(null);
                    //Bullet.shoot0(SE.KIRA);
                    Bullet.position0(this.position());
                    Bullet.velocity0(this.velocity());
                    Bullet.delay0(0);
                    new Zombie(){
                        {
                            pieces = 8;
                            this.type1(Bullet.Type.Outlined);
                            
            spreadAngle = (float)PI/20;
                        }
                        public void explode(){
                for(int a = -pieces+1;a<=pieces-1;a+=2){
                    Bullet.reset0();
                    
                    this.status=Status.Dead;
                    Bullet.position0(this.position());
                    Bullet.type0(Bullet.Type.Small);
                    Bullet.color0(randomFloat(0,1)<.25?Col.PURPLE:randomFloat(0,1)<0?Col.RED:Col.GREEN);
                    Bullet.shoot0(SE.POP);
                    Bullet.delay0(0);
                    Bullet.velocity0(product(Calc.rotate(killShot.velocity(),a*spreadAngle),randomFloat(.3f,.8f)));
                    new Bullet().add();
                }}
                        
                    }.add();
                
            }
    }
    public class BoneZombie extends Zombie{
        
        Zombie holder = this;
        {
            
                        //color1(Col.PURPLE);
                        //type1(Bullet.Type.Outlined);
            pieces = 12;
            spreadAngle = (float)PI/50;
        }
        public void move(){
            super.move();
            if(age==1){
                Bullet.reset0();
                Bullet.velocity0(this.velocity());
                Bullet.color0(randomFloat(0,1)<=.5?Col.BROWN:Col.GRAY);
                Bullet.shoot0(null);
                Bullet.tolerance0(tolerance);
                Bullet.type0(Bullet.Type.Medium);
                for(int i = -1;i<=1;i++){
                    float[] position = sum(this.position(),sum(norm(this.velocity(),20),Calc.rotate(norm(this.velocity(),i*12),PI/2)));
                    Bullet.position0(position);
                    new Bullet(){
                        public void move(){
                            super.move();
                            if((killShot!=null&&
                    killShot.status==Status.Active&&
                    mag(diff(this.position(),killShot.position()))<killRadius))
                            {
                                this.status=Status.Fade;
                                for(int a = -3;a<=3;a+=2){
                    Bullet.reset0();
                    Bullet.position0(this.position());
                    Bullet.type0(Bullet.Type.Small);
                    Bullet.color0(this.color);
                    Bullet.shoot0(null);
                    Bullet.delay0(0);
                    Bullet.velocity0(product(Calc.rotate(killShot.velocity()
                            ,2*a*spreadAngle),randomFloat(.3f,.3f)));
                    new Bullet().add();
                    
                                
                }
                                killShot.status=Status.Dead;
                                killShot = null;
                            }
                        }
                    }.add();
                }
            }}
    }
}

