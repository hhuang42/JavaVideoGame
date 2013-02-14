/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.image.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import static java.lang.Math.*;
import java.util.*;
import static practice.Calc.*;

/**
 *
 * @author Student
 */
public class Item extends Basic {

    public static ArrayList<Item> Ilist = new ArrayList<Item>();
    public Type type;
    private boolean home;
    static float[] position0 = toV(0, 0);
    static float[] velocity0 = toV(0, -2.5f);
    static float maxFall = 2.2f;
    static float maxHome = 12;
    static final float[] acceleration0 = toV(0, .05f);
    static Type type0 = Type.Point;

    public Item(float[] position, Type type) {
        this();
        position1(position);
        this.type = type;
    }
    public static void init(){
        Ilist = new ArrayList<Item>();
    }

    public Item() {
        position1(position0);
        this.type = type0;
        home = false;
        velocity1(velocity0);
        acceleration1(acceleration0);
    }

    public void add() {
        synchronized (Ilist) {


            Ilist.add(this);
        }
    }

    public void draw(Graphics2D g) {
        Calc.drawImage(g, type.sprite, x, y);
    }

    public static void UpdateItem() {
        synchronized (Ilist) {
            Iterator<Item> count = Ilist.iterator();
            while (count.hasNext()) {
                Item thisI = count.next();
                thisI.update();
                if (thisI.status == Status.Dead) {
                    count.remove();
                }
            }
        }
    }

    public static void setAllHome(boolean home) {
        Item[] tempList = Ilist.toArray(new Item[0]);
        for (int i = 0; i < tempList.length; i++) {
            tempList[i].setHome(home);
        }
    }

    public static void RenderItem(Graphics2D g) {
        Item[] tempList = Ilist.toArray(new Item[0]);
        for (int i = 0; i < tempList.length; i++) {
            Item a = tempList[i];
            if(a!=null)
            tempList[i].draw(g);
        }
    }
    public void collect(){
        status=status.Dead;
        type.Collect();
    }

    void setHome(boolean home) {
        if (home != this.home) {
            this.home = home;
            if (home) {
                velocity1(norm(diff(position(), Char.charPosition()), 2.5f));
                acceleration1(toV(0, 0));
            } else {
                velocity1(toV(0, 0));
                acceleration1(acceleration0);
            }
        }
    }

    void update() {
        countage();
        move();
        accelerate();
        if (y > GameEngine.scrHeight + 10) {
            status = status.Dead;
        }

    }

    void accelerate() {
        super.accelerate();
        if (home) {
            acceleration1(norm(diff(position(), Char.charPosition()), .18f));
            velocity1(norm(diff(position(), Char.charPosition()), min(mag(velocity()), maxHome)));
        } else {
            yv = min(yv, maxFall);
        }

    }

    public enum Type {

        Power(Power()){
            public void Collect(){
                Char.addPower(5);
            }
        },
        LPower(LPower()){
            public void Collect(){
                Char.addPower(100);
            }
        },
        Bomb(Bomb()) {

            public void Collect() {
                Char.ch.bombCount++;
            }
        },
        Life(LPower()),
        Point(Point()) {

            public void Collect() {
                GameEngine.point();
            }
        };
        BufferedImage sprite;

        Type(BufferedImage sprite) {
            this.sprite = sprite;
        }

        public void Collect() {
        }

        public static BufferedImage LPower() {
            int size = 16;
            BufferedImage thisI = newBImage(size, size);
            Graphics2D tempg = thisI.createGraphics();
            tempg.setComposite(Calc.opacity(1));


            tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            tempg.setColor(Color.red);
            //tempg.setStroke(new BasicStroke(10,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND));
            //tempg.drawRect(0, 0, size, size);
            //tempg.draw(new Rectangle(0,0,size,size));
            tempg.fillRoundRect(0, 0, size, size, 4, 4) ;
            //tempg.fill3DRect(1,1, size-2, size-2, true);
            //tempg.setComposite(Calc.opacity(0));
            tempg.setColor(new Color(220, 220, 220));
            tempg.fillRoundRect(1, 1, size - 2, size - 2,4,4);
            //tempg.fill3DRect(3,3, size-6, size-6, false);
            tempg.setColor(Color.red);
            tempg.fillRoundRect(3, 3, size - 6, size - 6,4,4);
            tempg.setColor(Color.white);
           
            tempg.setFont(new Font("sanserif", Font.BOLD, 11));
            tempg.drawString("P", 5, size - 4);
            tempg.drawString("P", 6, size - 4);
            tempg.setComposite(opacity(.2));
            tempg.setColor(Color.red);
            tempg.fillRect(3, 3, size - 7, size - 7);
            return thisI;
        }

        public static BufferedImage Bomb() {
            int size = 18;
            BufferedImage thisI = newBImage(size, size);
            Graphics2D tempg = thisI.createGraphics();
            tempg.setComposite(Calc.opacity(1));


            tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            tempg.setColor(Color.green.darker());
            tempg.fill3DRect(0, 0, size, size, true);
            //tempg.fill3DRect(1,1, size-2, size-2, true);
            tempg.setColor(new Color(220, 220, 220));
            tempg.fillRect(1, 1, size - 3, size - 3);
            //tempg.fill3DRect(3,3, size-6, size-6, false);
            tempg.setColor(Color.green.darker());
            tempg.fillRect(2, 2, size - 5, size - 5);
            tempg.setColor(Color.white);
            tempg.setFont(new Font("sanserif", Font.BOLD, 12));
            tempg.drawString("B", 5, size - 5);
            tempg.setComposite(opacity(.2));
            tempg.setColor(Color.green.darker());
            tempg.fillRect(2, 2, size - 5, size - 5);
            return thisI;
        }

        public static BufferedImage Point() {
            int size = 12;
            BufferedImage thisI = newBImage(size, size);
            Graphics2D tempg = thisI.createGraphics();
            tempg.setComposite(Calc.opacity(1));


            tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            tempg.setColor(Color.blue);
            tempg.fillRoundRect(0, 0, size, size,3,3);
            //tempg.fill3DRect(1,1, size-2, size-2, true);
            tempg.setColor(new Color(220, 220, 220));
            tempg.fillRoundRect(1, 1, size - 2, size - 2,3,3);
            //tempg.fill3DRect(3,3, size-6, size-6, false);
            tempg.setColor(Color.blue);
            tempg.fillRoundRect(2, 2, size - 4, size - 4,3,3);
            tempg.setColor(Color.white);
            
            tempg.setFont(new Font("sanserif", Font.BOLD, 10));
            //tempg.drawString("x", 4, size - 4);
            //tempg.drawString("+", 2, size - 4);
            tempg.setStroke(new BasicStroke(2));
            tempg.drawLine(3, 3, size-4, size-4);
            tempg.drawLine(3, size-4, size-4,3);
            tempg.setComposite(opacity(.2));
            tempg.setColor(Color.blue);
            tempg.fillRect(2, 2, size - 2, size - 2);
            return thisI;
        }

        public static BufferedImage Power() {
            int size = 12;
            BufferedImage thisI = newBImage(size, size);
            Graphics2D tempg = thisI.createGraphics();
            tempg.setComposite(Calc.opacity(1));


            tempg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            /*tempg.setColor(Color.red);
            tempg.fill3DRect(0, 0, size, size, true);
            //tempg.fill3DRect(1,1, size-2, size-2, true);
            tempg.setColor(new Color(230,230,230));
            tempg.fillRect(1, 1, size-3, size-3);
            //tempg.fill3DRect(3,3, size-6, size-6, false);
            tempg.setColor(Color.red);
            tempg.fillRect(2, 2, size-5, size-5);
            tempg.setColor(Color.white);
            tempg.setFont( new Font("Courier",Font.BOLD,10));
            tempg.drawString("P", 3, size-4);*/
            tempg.drawImage(LPower(), 0, 0, size, size, null);
            return thisI;
        }
    }
}
