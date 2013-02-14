/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.Color;

/**
 *
 * @author Henry
 */
public enum Col {
    RED(Color.red),
    ORANGE(new Color(255,140,0)),
    YELLOW(Color.yellow),
    GREEN(Color.green.darker()),
    CYAN(Color.cyan),
    BLUE(Color.blue),
    PURPLE(new Color(200,50,255)),
    PINK(new Color(255, 15, 192)),
    WHITE(Color.white),
    GRAY(Color.gray),
    BLACK(Color.black),
    BROWN(new Color(150,75,0));
    Color c;
    Col(Color c){
        this.c=c;
    }
    public Color get(){
        return c;
    }
    
}
