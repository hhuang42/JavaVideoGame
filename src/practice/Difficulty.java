/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

/**
 *
 * @author Henry
 */
public enum Difficulty {
    EASY("For Pass/Fail Froshlings"
            ), NORMAL("For Summer Math Scholars"), HARD("For P-Chem Survivors"), LUNATIC("For Engr/CS-Math Double Majors");
    String description;
    Difficulty(String description){
        this.description=description;
    }
    
}
