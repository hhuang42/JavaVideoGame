/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

/**
 *
 * @author Ping
 */
public abstract class Event {
    public double start;

    public abstract boolean check();

    public abstract void action();

    public void add() {
    }
    
}
