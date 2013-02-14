/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice.Scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Exception;
import static practice.Calc.*;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.*;
import static practice.GameEngine.*;
import practice.*;
import practice.Save.Record;

/**
 *
 * @author Henry
 */
public class MainScript extends BossScript {

    ArrayList<Person> orderList;
    Script currentS;
    boolean main = false;
    public int remaining;
    private static final BufferedImage newBar = newBar();
    
    public MainScript(Person[] personList){
        super();
        
        orderList = new ArrayList<Person>(Arrays.asList(personList));
        
    }
    
    public static void initialize(){
        newBar.getData();
    }

    public class ScriptAction extends Action {

        Script thisS;
        Person persona;
        int deathCount;
        public ScriptAction(Script thisS,Person persona) {
            this.thisS = thisS;
            this.persona=persona;
        }

        public void start() {
            remaining--;
            Engine.thisGE.clearAll();
            thisS.init();
            currentS = thisS;
            deathCount = GameEngine.deathCount;
            if(!main){
                mainRecord = ((BossScript)thisS).r;
            }
        }

        public boolean done() {
            
            thisS.run();
            if(thisS.isDone()){
                if(deathCount==GameEngine.deathCount&&r!=null)
                {
                    r.newSuccess();
                }
                return true;
            }else{
                return false;
            }
        }
    }
    
    public static MainScript allScript(){
        
        ArrayList<Person> orderList = new ArrayList(Arrays.asList(Person.values()));
        java.util.Collections.shuffle(orderList);
        orderList.remove(Person.MICHELLE);
        orderList.add(orderList.indexOf(Person.STEPHANIE) + (randomFloat(0,1)>.5?1:0), Person.MICHELLE);
        return new MainScript(orderList.toArray(new Person[]{})){
            {main = true;
            this.r=new Record(0,Person.values().length);
            mainRecord = r;
            }
            public void makeDone(){
                super.makeDone();
                if(Engine.save.deathRecord(difficulty)==-1||GameEngine.deathCount<Engine.save.deathRecord(difficulty))
                {
                Engine.save.updateDeathRecord(difficulty, GameEngine.deathCount);
                }
                Record prevRecord = Engine.save.playRecord(difficulty);
                if(main&&(mainRecord.success()>prevRecord.success())){
                    prevRecord.success(mainRecord.success());
                }
            }
        };
    }

    public void start() {
    }
    public void finish(){
        if(main||deathCount==0){
            super.finish();
        }
        else{
            if (finishWait > 0) {
            Char.ch.forbidRespawn = true;
            finishWait-=3;
        } else {

            Engine.thisGE.thisM = new EndMenu(Engine.thisE);
            Engine.thisGE.thisM.init();
            Engine.thisGE.menu = true;
            BGM.pause();
            SE.PSHOOT.loop(false);
        }
        }
    }

    public void fill() {
        remaining = 0;
        for (Person a : orderList.toArray(new Person[]{})) {
            remaining++;
            this.actionArray.add(new ScriptAction(a.thisS,a));
            this.actionArray.add(
                    new Action() {

                        public void start() {
                            
                            currentS=null;
                            Engine.thisGE.clearAll();
                            double angle = randomFloat(0, 2 * (float) PI);
                            double move = 10;
                            thisBoss.moveTo(30, sum(thisBoss.position(), toXY(toF(toV(move, angle)))));
                            thisBoss.setScript(null);
                        }
                    });
            this.addPause(30);
        }
        actionArray.add(new Action() {

            public void start() {
                makeDone();
            }
        });
    }
    public boolean isDone(){
        return done||(!main&&deathCount>0);
    }

    public void paint(Graphics g) {
        
        if (currentS != null) {
            currentS.paint(g);
        }
        paintNewBar(g);
    }
    public void paintNewBar(Graphics g){
        int y=20;
        for(int h = 0;h<remaining/5;h++){
            
            int x= newBar.getHeight()/2 + 5;
            for(int w = 0;w<5;w++)
            {
            g.drawImage(newBar, x, y, null);
            
                x+=newBar.getHeight()*1.2;
            }
            y+=newBar.getHeight()*1.2;
        }
        
                int x = newBar.getHeight()/2 + 5;
        for(int w = 0;w<remaining%5;w++)
            {
            g.drawImage(newBar, x, y, null);
            
                x+=newBar.getHeight()*1.2;
            }
        
    }
    public void checkDead(){};
    public static BufferedImage newBar(){
        int diam = 8;
        BufferedImage spot = Calc.newBImage(diam, diam);
        Graphics2D g2 = spot.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(int i = diam/2;i>=0;i--){
            g2.setColor(i%2==0?Color.ORANGE.darker():Color.YELLOW);
            
        g2.setComposite(i%2==0?opacity(.5):opacity(.5));
            Calc.fillCircle(g2, diam/2, diam/2, i);
        }
        return spot;
    }

}
