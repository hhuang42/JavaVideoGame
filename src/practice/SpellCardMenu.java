/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import practice.Scripts.*;
import java.text.DecimalFormat;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.*;
import static java.lang.Math.*;

/**
 *
 * @author Henry
 */
public class SpellCardMenu extends Menu {

    public static final String test = "This is a test text to see how well this whole thing works and see if the text is able to be lain out properly.\n Here is additional text that I am hoping will allow the text to go further down in the box so I can see how it would all turn out.";
    public SpellCardMenu(Engine thisE) {
        this.thisE = thisE;
    }

    public void init() {
        dx = .1f;
        this.type = Type.MAIN;
        this.previous = Engine.thisMM;
        options = new Option[Person.values().length];

        for (int i = 0; i < Person.values().length; i++) {
            final Person thisP = Person.values()[i];
            final String name = thisP.name;
            final int i0 = i;
            options[i] = new Option() {

                    boolean clear = true;
                    boolean tried = true;
                    boolean saw = false;
                {
                    for (Difficulty d : Difficulty.values()) {
                        int[] data = Engine.save.spellRecord(thisP, d);
                        clear = clear && (data[0] > 0);
                        tried = tried && (data[1] > 0);
                        saw = saw||(data[1] > 0);
                    }
                    if(saw)
                    {
                    this.optionName = name;
                    }
                    else{
                        this.optionName = "???";
                        this.available=false;
                    }

                }

                public Menu action() {
                    Engine.thisIDM.setPerson(thisP);
                    return Engine.thisIDM;
                }
                public String desc(){
                    
                String desc;
                if(this.available)
                    {
                        if(this.tried){
                    desc = "Spell Creation History: " + "\n\n"
                                + Engine.save.makeMap.get(thisP);
                    }
                        else{
                        desc = "Try playing on all the difficulties!";
                        }
                    }
                    else{
                        desc = "Play from Game Start to see everyone!";
                    }
                    return desc;
                }
                

                public void draw(Graphics2D g, int x, int y, int fontSize) {
                    

                    clear = true;
                    tried = true;
                    saw = false;
                    for (Difficulty d : Difficulty.values()) {
                        int[] data = Engine.save.spellRecord(thisP, d);
                        clear = clear && (data[0] > 0);
                        tried = tried && (data[1] > 0);
                        saw = saw||(data[1] > 0);
                    }
                    if(saw)
                    {
                    this.optionName = name;
                    }
                    else{
                        this.optionName = "???";
                        this.available=false;
                    }
                    super.draw(g, x, y, fontSize);


                    
                    if (tried) {

                        g.setColor(Color.DARK_GRAY);
                        if (clear) {


                            g.setColor(Color.BLUE);

                            g.drawString("+", x - 25, y);
                        } else {
                            g.setColor(Color.GREEN);

                            g.drawString("-", x - 25, y);
                        }
                    }
                    if (round(index)==i0) {
                        Calc.drawTextRectangle(g, 50, 120, 350, 450,desc()
                                );
                    }
                }
            };

        }
        options[(int) (index)].active = true;
    }

    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        paintBackground(g);
        for (int i = (int) index - 7; i < (int) index + 7; i++) {
            Option thisO = options[(i + options.length) % (options.length)];
            thisO.draw(g2,
                    (int) (580 - 200 * cos((i - index) * .15)),
                    (int) (thisE.fHeight * (1. / 2 + 1. * sin((i - index) * .15))), 40);
        }
        
        g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        g2.setComposite(Calc.opacity(1));
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        g2.drawString("Spellcard Practice", (400 - g2.getFontMetrics().stringWidth("Spellcard Practice")) / 2, 80);
    }
;
}
