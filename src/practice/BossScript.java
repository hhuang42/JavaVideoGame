/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Exception;
import static practice.Calc.*;
import static java.lang.Math.*;
import java.util.ArrayList;
import static practice.GameEngine.*;
import practice.Save.Record;

/**
 *
 * @author Henry
 */
public class BossScript extends Script {

    public static Boss thisBoss;
    public int maxHP;
    public int finishWait;
    Person persona = null;
    int currentDeath;
    public enum Type {

        SpellCard,
        NonSpell
    }
    public Type thisT;
    public int timeLimit;
    public int time;
    public int holdNameTime;
    public int pullNameTime;
    public int flipNameTime;
    final int flipNameMax = 30;
    final int holdNameMax = 60;
    final int pullNameMax = 30;
    public boolean declaring;
    private double visibleHP = 0;
    //public int age;
    public static Difficulty difficulty = null;
    public static Record mainRecord;
    public String[] names = {"PLACEHOLDER", "PLACEHOLDER", "PLACEHOLDER", "PLACEHOLDER"};
    public BufferedImage spellNameImage;
    public BufferedImage record;
    public Record r;

    public BossScript() {
    }

    public void init() {
        visibleHP = 0;
        declaring = false;
        super.init();
        Bullet.Type.values();
        String name = "SPELLNAME";
        switch (difficulty) {
            case EASY:
                name = names[0];
                break;
            case NORMAL:
                name = names[1];
                break;
            case HARD:
                name = names[2];
                break;
            case LUNATIC:
                name = names[3];
                break;

        }
        spellNameImage = spellNameImage(name);

        finishWait = 180;
        if(persona!=null){
            r = Engine.save.personMap.get(persona).get(difficulty);
            r.newAttempt();
            currentDeath = GameEngine.deathCount;
            record = Calc.newBImage(100, 30);
            this.drawRecord(r, record);
        }
    }

    public BufferedImage spellNameImage(String s) {
        int fontSize = 12;
        double ratio = 1;
        double charHeight = (ratio * fontSize);

        double charWidth = fontSize * ratio;
        double height = 3 * charHeight;
        double width = charWidth * s.length();
        BufferedImage image = Calc.newBImage(width, height);
        Graphics2D g2 = image.createGraphics();
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, fontSize);
        FontMetrics blah = g2.getFontMetrics(f);
        double actualWidth = blah.stringWidth(s);
        double actualHeight = blah.getHeight();
        int layer = 40;

        g2.setColor(Color.BLACK);

        g2.setComposite(Calc.opacity(.5));
        /*g2.fillRect(
                    (int) round(width-actualWidth),
                    (int) round(0),
                    (int) round(actualWidth),
                    (int) round( actualHeight));*/
        
        g2.setComposite(Calc.opacity(.05));
        double pow = .5;
        double rat = 1.2;
        for (double i = 1; i < layer; i++) {
            g2.fillRect(
                    (int) round(rat*(pow(i/layer,1/pow)-1) * actualWidth + width),
                    (int) round(-rat*(pow(i/layer,pow)) * actualHeight+actualHeight),
                    (int) round(rat*(1-pow(i/layer,1/pow)) * actualWidth),
                    (int) round(rat*(pow(i / layer,pow)) * actualHeight));
        }
        
        pow = 2;
        rat = 1;
        /*for (double i = 1; i < layer; i++) {
            g2.fillRect(
                    (int) round(rat*(pow(i/layer,1/pow)-1) * actualWidth + width),
                    (int) round(-rat*(1-pow(i/layer,1/pow)) * actualHeight+actualHeight),
                    (int) round(rat*(1-pow(i/layer,1/pow)) * actualWidth),
                    (int) round(rat*(1-pow(i / layer,1/pow)) * actualHeight));
        }*/
        g2.setComposite(Calc.opacity(1));
        g2.setColor(Color.YELLOW);
        g2.setFont(f);
        g2.drawString(s, round(width - actualWidth), round(blah.getAscent()));
        return image;
    }
    public void drawRecord(Record r, BufferedImage b){
        
        int fontSize = 10;
        Graphics2D g = b.createGraphics();
        g.setComposite(Calc.clear());
        g.fillRect(0, 0, b.getWidth(), b.getHeight());
        String s ="Record: " + r.success()+" / "+r.attempt();
        
        Font f = new Font(Font.SERIF, Font.PLAIN, fontSize);
        g.setComposite(Calc.opacity(1));
        g.setColor(Color.WHITE);
        g.setFont(f);
        
        FontMetrics blah = g.getFontMetrics(f);
        double actualWidth = blah.stringWidth(s);
        
        g.drawString(s, round(b.getWidth() - actualWidth), round(blah.getAscent()));
    }
    public void fillHP(int HP) {
        maxHP = HP;
        if (thisBoss != null) {
            thisBoss.HP = maxHP;
        }
    }

    public void initializeBoss() {
        if (thisBoss != null && Enemy.Elist.contains(thisBoss)) {

            thisBoss.setScript(null);
        } else {
            thisBoss = new Boss(this) {

                {
                    position1(toV(semiWidth, 0));
                }
            };
            thisBoss.add();
        }
    }

    public void moveBoss(final double x, final double y, final int frames) {
        actionArray.add(new Action(this) {

            public void start() {
                thisBoss.moveTo(frames, toF(toV(x, y)));
            }
        });
    }

    public void moveBossAbovePlayer(final double y, final int frames) {
        actionArray.add(new Action(this) {

            public void start() {
                thisBoss.moveTo(frames, toF(toV(Char.ch.x, y)));
            }
        });
    }

    public void declare(final double y, final int HP) {

        actionArray.add(new Action(this, ActionType.WAIT) {

            public void start() {
                holdNameTime = holdNameMax;
                pullNameTime = pullNameMax;
                flipNameTime = flipNameMax;
                fillHP(HP);
                SE.DECLARE.play();
                thisBoss.moveTo(holdNameMax + pullNameMax + flipNameMax, toF(toV(semiWidth, y)));

            }

            public boolean done() {
                if (pullNameTime <= 0) {
                    thisBoss.stop();
                    return true;
                } else {
                    if (holdNameTime <= 0) {
                        pullNameTime--;
                    } else {
                        if (flipNameTime <= 0) {
                            holdNameTime--;
                        } else {
                            flipNameTime--;
                        }
                    }

                    return false;
                }

            }
        });
    }

    public void moveBossAbovePlayer(final double y, final int frames, final double border) {
        actionArray.add(new Action(this) {

            public void start() {
                thisBoss.moveTo(frames, toF(toV(max(min(Char.ch.x, scrWidth - border), border), y)));
            }
        });
    }

    public void moveBossTowardsPlayer(final double y, final int frames, final double dx) {
        actionArray.add(new Action(this) {

            public void start() {

                double x = dx * sign(Char.ch.x - thisBoss.x) + thisBoss.x;
                thisBoss.moveTo(frames, toF(toV(x, y)));
            }
        });
    }

    public void moveBossTowardsPlayer(final double y, final int frames, final double dx, final double border) {
        actionArray.add(new Action(this) {

            public void start() {

                double x = dx * sign(Char.ch.x - thisBoss.x) + thisBoss.x;
                if (x >= scrWidth - border || x < border) {
                    x = -dx * sign(Char.ch.x - thisBoss.x) + thisBoss.x;
                }
                thisBoss.moveTo(frames, toF(toV(x, y)));
            }
        });
    }

    public void moveBossTowardsPlayer(final float[] yRange, final int frames, final double dx, final double border) {
        actionArray.add(new Action(this) {

            public void start() {

                double x = dx * sign(Char.ch.x - thisBoss.x);
                if (x + thisBoss.x >= scrWidth - border || x + thisBoss.x < border) {
                    x = -dx * sign(Char.ch.x - thisBoss.x);
                }
                float dy = (randomFloat(yRange[0], yRange[1]) - thisBoss.y);
                float[] dPosition = norm(toV((float) x, (float) dy), (float) dx);
                thisBoss.moveTo(frames, sum(thisBoss.position(), dPosition));
            }
        });
    }
    public void moveBossTowardsPlayer(final float[] yRange, final int frames, final double[] dxy, final double border) {
        actionArray.add(new Action(this) {

            public void start() {
                
                double dx = dxy[0];
                
                double dy = dxy[1];
                double x = dx * sign(Char.ch.x - thisBoss.x);
                if (x + thisBoss.x >= scrWidth - border || x + thisBoss.x < border) {
                    x = -dx * sign(Char.ch.x - thisBoss.x);
                }
                dy = sign(randomFloat(yRange[0], yRange[1]) - thisBoss.y)*dy;
                float[] dPosition = norm(toV((float) x, (float) dy), (float) dx);
                thisBoss.moveTo(frames, sum(thisBoss.position(), dPosition));
            }
        });
    }

    public void stopBoss() {
        actionArray.add(new Action(this) {

            public void start() {
                thisBoss.stop();
            }
        });
    }

    public void paintHPBar(Graphics g) {
        int yOffset = 10;
        int xOffset = 10;
        int thickness = 4;
        g.setColor(Color.ORANGE.darker());
        g.fill3DRect(xOffset, yOffset, (int) max((scrWidth - 2 * xOffset) * visibleHP, 0), thickness, true);
        
        g.setColor(Color.YELLOW);
        g.fill3DRect(xOffset, yOffset, (int) max((scrWidth - 2 * xOffset) * visibleHP, 0), 1, false);
    }

    public void paint(Graphics g) {
        paintHPBar(g);
        paintName(g);

    }

    public void paintName(Graphics g) {
        if (pullNameTime == 0) {
            g.drawImage(spellNameImage,
                    scrWidth - 10 - spellNameImage.getWidth(),
                    20, null);
            if(record!=null)
            {
            g.drawImage(record,
                    scrWidth - 10 - record.getWidth(),
                    40, null);
            }
        } else {
            g.drawImage(spellNameImage,
                    scrWidth - 10 - spellNameImage.getWidth(),
                    20 + (int) (1 * semiHeight * (1. * pullNameTime / pullNameMax)),
                    spellNameImage.getWidth(),
                    (int) (spellNameImage.getHeight() * (1 - 1. * flipNameTime / flipNameMax)), null);
            if(record!=null)
            {
            g.drawImage(record,
                    scrWidth - 10 - record.getWidth(),
                    40 + (int) (1 * semiHeight * (1. * pullNameTime / pullNameMax)),
                    record.getWidth(),
                    (int) (record.getHeight() * (1 - 1. * flipNameTime / flipNameMax)), null);
            }
        }
    }

    public void run() {
        super.run();
        checkDead();
        if ((visibleHP + .01) * maxHP < thisBoss.HP) {
            visibleHP += .01;
        } else if ((visibleHP - .003) * maxHP > thisBoss.HP) {
            visibleHP -= .003;
        }
    }

    public void finish() {
        if (thisBoss.status == Boss.Status.Active) {
            thisBoss.resistFade = false;
            thisBoss.toFade();
        }
        if (finishWait > 0) {
            Char.ch.invincible = true;
            finishWait--;
        } else {

            Engine.thisGE.thisM = new EndMenu(Engine.thisM.thisE);
            Engine.thisGE.thisM.init();
            Engine.thisGE.menu = true;
            BGM.pause();
            SE.PSHOOT.loop(false);
        }
    }

    public void checkDead() {
        
        if (thisBoss.HP < 0) {
            if(persona!=null){
                if(currentDeath==GameEngine.deathCount){
                r.newSuccess();
                SE.CAPTURE.play();
                this.drawRecord(r, record);
                }
            }
            this.makeDone();
        }
    }

    /*
     * public void run() { declare(); try { runEx(); } catch (Exception e) {
     * return; } }
     *//*
     *
     * public void runEx() throws Exception { }
     *
     * ;
     * public void yieldEx() throws Exception { if (thisBoss.HP <= 0) { throw
     * new Exception(); } super.yield(); }
     *
     * public void waitEx(int times) throws Exception { for (int i = 0; i <
     * times; i++) { yieldEx(); } }
     */

    public static class test extends BossScript {

        int we;

        {
            maxHP = 100000;
        }

        public void fill() {
            actionArray.add(this.loopStart());
            actionArray.add(this.pause(240));
            actionArray.add(
                    new Action(this) {

                        public void start() {
                            thisBoss.moveTo(120, toV(Char.ch.x, 120));
                        }
                    });
            actionArray.add(this.pause(120));
            actionArray.add(
                    new Action(this) {

                        public void start() {
                            thisBoss.stop();
                        }
                    });
            for (we = 0; we < 4; we++) {
                actionArray.add(
                        new Action(this) {

                            public void start() {
                                for (int i2 = -1; i2 <= 1; i2 += 2) {
                                    final int p2 = i2;

                                    new Bullet() {

                                        {
                                            reset();
                                            velocity1(Calc.rotate(toV(0, 2), p2 * randomFloat((float) -PI / 10, (float) PI / 3) + p2 * PI / 2 + 0 * toAngle(thisBoss.position(), Char.charPosition())));
                                            acceleration1(toV(0, .02f));
                                            color1(2);
                                            type1(Bullet.Type.Outlined);
                                            position1(thisBoss.position());
                                        }
                                        public int we2 = we;
                                        public int p = p2;

                                        @Override
                                        public void move() {
                                            if (Calc.inRange(age, 1, 0, 1, 11) && age <= 180) {
                                                Bullet.reset0();

                                                Bullet.type0(Bullet.Type.Pellet);
                                                Bullet.color0(1);

                                                Bullet.position0(position());
                                                for (double i = 0; i < 1; i += 1. / 11) {
                                                    Bullet.velocity0(toXY(toF(toV(1.5, p * (2 * PI * i + (age) * PI / 37 + we2 * PI / 4)))));
                                                    final float age1 = age;
                                                    new Bullet() {

                                                        public int age2 = (int) age1;

                                                        public void move() {
                                                            this.changeSpeed(1, 10, 0.001f);
                                                            if (age == 11) {
                                                                this.color = 8;
                                                            }
                                                            if (age == 211 - age2) {
                                                                this.color = 7;
                                                                SE.KIRA.play();
                                                            }
                                                            this.changeSpeed(211 - age2, 240 - age2, min(1.f + 0 * age2 * .005f, 4));

                                                            super.move();
                                                        }
                                                    }.add();
                                                }
                                            }
                                            super.move();
                                        }
                                    }.add();
                                }
                            }
                        });


                actionArray.add(this.pause(30));
            }

            actionArray.add(this.pause(60));
            actionArray.add(this.loopBasicEnd());

        }
    }
}
