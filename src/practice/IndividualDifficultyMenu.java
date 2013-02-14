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
import practice.Save.Record;

/**
 *
 * @author Henry
 */
public class IndividualDifficultyMenu extends Menu {

    public Person thisP;
    String test = "This is a test text to see how well this whole thing works and see if the text is able to be lain out properly.\n Here is additional text that I am hoping will allow the text to go further down in the box so I can see how it would all turn out.";
    public static IndividualDifficultyMenu menu;

    public void setPerson(Person thisP) {
        this.thisP = thisP;
    }

    public void init() {
        this.type = Type.MAIN;
        index = 0;
        menu = this;
        dx = 1f;
        this.previous = Engine.thisSC;
        options = new Option[4];
        for (int i = 0; i < Difficulty.values().length; i++) {
            final int i0 = i;
            options[i] = new Option() {

                Record r;
                {
                    r = Engine.save.personMap.get(thisP).get(Difficulty.values()[i0]);
                    this.optionName =
                            Difficulty.values()[i0].toString();
                    this.available=r.attempt()>0;
                    
                    /*
                     * thisP. thisS. names[i0];
                     */
                }
                public String desc(){
                    
                String desc;
                if(this.available)
                    {
                    desc = "Spell: " + thisP.thisS.names[i0] + "\n"
                                + "Record: " + r.success() + " / " + r.attempt() + "\n\n";
                    }
                    else{
                        desc = "Spell: " + "???" + "\n"
                                + "Record: " +"???";
                    }
                if(r.success()>0){
                    desc+=Engine.save.flavour(thisP, Difficulty.values()[i0]);
                }
                    return desc;
                }

                public Menu action() {
                    BossScript.difficulty = Difficulty.values()[i0];
                    Engine.thisGE.setScript(new MainScript(new Person[]{thisP}));
                    Engine.menu = false;
                    return null;
                }

                public void draw(Graphics2D g, int x, int y, int fontSize) {
                    super.draw(g, x, y, fontSize);

                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    if (r.success() > 0) {
                        g.setColor(Color.BLUE);
                        g.drawString("+", x - 25, y);
                    } else if (r.attempt() > 0) {
                        g.setColor(Color.GREEN);
                        g.drawString("-", x - 25, y);
                    }
                    if (this.active) {
                        Calc.drawTextRectangle(g, 250, 200, 650, 450,desc()
                                );
                    }
                }
            };
            options[(int) (index)].active = true;
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        paintBackground(g);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < options.length; i++) {
            Option thisO = options[(i + options.length) % (options.length)];
            thisO.draw(g2, 75, (int) (thisE.fHeight * (1. / 2 + .13 * i)), 36);
        }

        g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        g2.setComposite(Calc.opacity(1));
        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 72));
        g2.drawString(thisP.name, (Engine.fWidth - g2.getFontMetrics().stringWidth(thisP.name)) / 2, 120);
    }
;
}
