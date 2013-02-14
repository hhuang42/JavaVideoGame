/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import static java.lang.Math.*;
import java.util.*;
import static practice.Calc.*;

/**
 *
 * @author Henry
 */
public class Laser extends Bullet {

    public Bullet[] laserBullets;
    public int length;
    public static int length0 = 80;
    public static int spacing = 16;
    public static double size = 20;
    public static int SemiWidth = 2;
    public static int SemiHeight = (int) size;
    public int timing;
    public int bulletNumber;
    public static BufferedImage[][] spriteList;
    public static BufferedImage[][] fadeList;

    public Laser() {
        super();
        length = length0;
        
    }

    public static void init() {
        //spriteList = laserSprite();
        //fadeList = Type.fadeList((int) size/3);
    }

    public void length1(int length) {
        this.length = length;
    }

    public static void length0(int length) {
        length0 = length;
    }

    public static void reset0() {
        length0 = 80;
        Bullet.reset0();
        Laser.shoot0=SE.LASER;
    }

    public void add() {
        
        timing = (int) (spacing / mag(this.velocity()));
        float actualSpacing = timing * mag(this.velocity());
        bulletNumber = (int) (length / actualSpacing);
        delay=timing*(bulletNumber+1);
        super.add();

    }

    public void move() {
        if (age % timing == 1) {
            Bullet.reset0();
            Bullet.position0(position());
            Bullet.velocity0(velocity());
            Bullet.color0(color);
            Bullet.type0(type);
            Bullet.shoot0(null);
            Bullet.delay0(15);
            Bullet.type0(Type.Laser);
            Bullet.tolerance0((int)size);
            new Bullet() {
            }.add();
            
        }
        if ((bulletNumber + 1) * (timing) < age) {
            this.status = Status.Dead;
        }
    }

    
}
