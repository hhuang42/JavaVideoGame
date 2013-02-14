/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package practice;
import java.awt.*;
import java.util.*;
import static java.lang.Math.*;
import static practice.Calc.*;
import java.awt.image.*;
/**
 *
 * @author Student
 */
abstract public class Sprite {
BufferedImage[] sprite;

Sprite(){
}

public Sprite set(){
    sprite = drawSprite();
return this;}
abstract public BufferedImage[] drawSprite();

public BufferedImage image(double index){
    return sprite[(int)min(max(index,0),sprite.length-1)];
}

}
