/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.*;
import static java.lang.Math.*;
import java.util.*;
import static practice.Calc.*;

/**
 *
 * @author Student
 */
public class Move {

    //float x;
    //float y;
    //float[][][] movelist;
    int phase = 0;
    int progress = 0;
    float a = .5f;
    int age=0;
    int scrWidth = GameEngine.scrWidth;
    Basic thisB;
    
    public Move(Basic thisB){
        this.thisB = thisB;
    }
    
    public void action(){
        
    }
    public void age(){
        age++;
    }
    Move(float[][][] movelist) {
    }
    

    public float[] hermiteT(float[] pt1, float[] pt2, float[] t1, float[] t2, double per) {
        float[] position = new float[2];
        for (int i = 0; i <= 1; i++) {
            position[i] = (float) (
                    (2 * pow(per, 3) - 3 * pow(per, 2) + 1)*pt1[i]
                    + (-2 * pow(per, 3) + 3 * pow(per, 2))*pt2[i]
                    + (pow(per, 3) - 2 * pow(per, 2) + per)*t1[i]
                    + (pow(per, 3) - pow(per, 2))*t2[i]);
        }
        return position;
    }
    public float[] hermiteP(float[][] pt0, float[][] pt1, float[][] pt2, float[][] pt3,double per)
    {
        return hermiteT(
                pt1[0],
                pt2[0],
                toV(
                a*(pt2[0][0]-pt0[0][0])*(2*pt2[1][0]/(pt1[1][0]+pt2[1][0])),
                a*(pt2[0][1]-pt0[0][1])*(2*pt2[1][0]/(pt1[1][0]+pt2[1][0]))),
                toV(
                a*(pt3[0][0]-pt1[0][0])*(2*pt2[1][0]/(pt3[1][0]+pt2[1][0])),
                a*(pt3[0][1]-pt1[0][1])*(2*pt2[1][0]/(pt3[1][0]+pt2[1][0]))),
                per);
    }

    public Move reflect()
    {/*
        for(int i = 0;i<movelist.length;i++)
        {
            movelist[i][0][0]=scrWidth-movelist[i][0][0];
        }*/
        return this;
    }
    public Move reflect(float axis)
    {/*
        for(int i = 0;i<movelist.length;i++)
        {
            movelist[i][0][0]=2*axis-movelist[i][0][0];
        }*/
        return this;
    }
    public Move translate(float[] shift)
    {/*
        for(int i = 0;i<movelist.length;i++)
        {
            movelist[i][0][0]=movelist[i][0][0]+shift[0];
            movelist[i][0][1]=movelist[i][0][1]+shift[1];
        }*/
        return this;
    }



    public float[] update() {
        /*
        if(phase+1<movelist.length)
        {
            
            float[] vect = hermiteP(
                        movelist[max(phase-1,0)],
                        movelist[phase],
                        movelist[phase+1],
                        movelist[min(phase+2,movelist.length-1)],
                        (double)progress/movelist[phase+1][1][0]
                        );
            progress++;
            if(!(progress<movelist[phase+1][1][0]))
            {
                phase++;
                progress=0;
            }
            return vect;
            }
 else
        {
        return null;
        }*/
        return null;
    }

}
