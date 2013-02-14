/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.image.*;
import java.awt.*;
import static java.lang.Math.*;
import java.util.*;
import static practice.Calc.*;
import static practice.Item.Type.*;
/**
 *
 * @author Henry
 */
public class Familiar extends Enemy {
    public Familiar(){
        status = Status.Form;
    }
    static BufferedImage[][] sprites = sprites();
    
    public void update() {
            countage();
            switch(status){
                case Form:
                    active();
                    break;
                case Fade:
                    fade();
                    break;
                default:
                    break;

            }

        }
    public void active(){
            move();
            checkDead();
    }
    public void toFade(){
            status = status.Fade;
            age = -fade;
            SE.POP.play();
            deathAction();
        }
    
    public void draw(Graphics2D g) {
            switch(status){
                case Form:
                    activeDraw(g);
                    break;
                case Fade:
                    fadeDraw(g);
                    break;
                default:
                    break;
            }
        }
    
    public void activeDraw(Graphics2D g)
    {
            drawImage(g,sprites[color.ordinal()][(int)age%sprites[0].length],x,y);
        }
    
    public static BufferedImage[][] sprites() {
        int angle = 60;
        double size = 36;
        int aura = 12;
        int index = 0;
        BufferedImage[][] imageList = new BufferedImage[ColorType.values().length][angle];


        for (ColorType c : ColorType.values()) {
            for (int t = 0; t < angle; t++) {
                BufferedImage thisI = newBImage(size, size);
                Graphics2D tempg = thisI.createGraphics();
                tempg.setComposite(Calc.opacity(1));
                tempg.rotate(2 * PI / angle * t, size / 2, size / 2);


                tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                tempg.setColor(c.getC());
                tempg.setComposite(opacity(.05));
                for (int i = 0; i <= aura; i++) {
                    Calc.fillCircle(tempg, size / 2, size / 2, size / 2 - i);

                }
                tempg.setComposite(opacity(1));
                imageList[index][t] = thisI;
            }
            index++;
        }
        return imageList;
    }
}
