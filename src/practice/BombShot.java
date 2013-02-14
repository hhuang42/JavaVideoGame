/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package practice;

import java.awt.image.*;
import java.awt.geom.*;
import java.awt.*;
import java.util.*;
import static java.lang.Math.*;
import static practice.Calc.*;

/**
 *
 * @author Student
 */
public class BombShot extends Shot {

    public void collide(){
        super.collide();

        Object[] tempList = Bullet.Blist.toArray();
        for(int i=0;i<tempList.length;i++)
        {
            Bullet thisB = (Bullet) tempList[i];
            if(thisB.status==Bullet.Status.Active)
                {
                if (Calc.collide(thisB.position(), 0, this.position(),toV(semiWidth,semiHeight),this.orient())) {

                    thisB.status=Bullet.Status.Fade;

                }
                }
        }
        /*synchronized (Bullet.Blist) {
            
            Bullet.Blist.clear();
            Bullet.Blist.addAll(Arrays.asList(tempList));
        }*/
    }


}
