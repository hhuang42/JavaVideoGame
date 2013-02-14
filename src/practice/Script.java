/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Henry
 */
public class Script {

    public final static int scrWidth = GameEngine.scrWidth;
    public final static int scrHeight = GameEngine.scrHeight;
    public final static int semiWidth = scrWidth / 2;
    public final static int semiHeight = scrHeight / 2;
    
    public int index = 0;
    public int actionIndex = 0;
    public Action[] actionList = {};
    public Script thisScript = this;
    public ArrayList<Action> actionArray;
    public boolean active = false;
    public boolean done;
    
    public void finish(){};

    public enum ActionType {

        START,
        WAIT,
        EVENT,
        LOOPSTART,
        LOOPEND,
        END
    }

    public class Action {

        public ActionType type;
        public Script thisS;

        public Action() {
            this.type = ActionType.EVENT;
        }

        public Action(Script thisS) {
            this.type = ActionType.EVENT;
            this.thisS = thisS;
        }

        public Action(Script thisS, ActionType type) {
            this.type = type;
            this.thisS = thisS;
        }

        public void start() {
        }

        public boolean done() {
            return true;
        }
    };

    public Action currentAction() {
        return this.actionList[this.actionIndex];
    }

    public Action pause(final int frames) {
        return new Action(this, ActionType.WAIT) {

            int waitPeriod = frames;
            int waitStart;

            public void start() {
                waitStart = thisS.index;
            }

            ;
            public boolean done() {
                return thisS.index == waitStart + waitPeriod;

            }
        ;
    }

    ;
    }
    public void addPause(int frames){
            actionArray.add(this.pause(frames));
        }
    public Action loopStart() {
        return new Action(this, ActionType.LOOPSTART);
    }
    public void addLoopStart(){
        actionArray.add(loopStart());
    }

    public Action loopBasicEnd() {
        return new Action(this, ActionType.LOOPEND) {

            public void start() {
                int startCount = 1;
                while (startCount != 0) {
                    thisS.actionIndex--;
                    if (thisS.currentAction().type == ActionType.LOOPSTART) {
                        startCount--;
                    } else if (thisS.currentAction().type == ActionType.LOOPEND) {
                        startCount++;
                    }
                }
            }
        };
    }
    public void addLoopEnd(){
        actionArray.add(loopBasicEnd());
    }
    
    public void paint(Graphics g){
       
    }
    public boolean isDone(){
        return done;
    }
    public void makeDone(){
        done = true;
    }

    public void fill() {
    }

    public void start() {
    }

    ;

    public void init() {
        index = 0;
    actionIndex = 0;
    actionList = new Action[0];
    thisScript = this;
    active = false;
        done = false;
        active = true;
        start();
        actionArray = new ArrayList<Action>();
        actionArray.add(this.pause(0));
        fill();
        actionList = actionArray.toArray(actionList);
        actionList[actionIndex].start();

    }

    public void run() {
        
        if (active) {
            while (actionIndex < actionList.length &&
                    actionList[actionIndex].done()) {
                actionIndex++;
                if (actionIndex < actionList.length) {
                    actionList[actionIndex].start();
                }
            }
            index++;
        }

    }
}
