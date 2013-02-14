/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

/**
 *
 * @author Ping
 */
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.*;
import java.util.*;
import static java.lang.Math.*;
import static practice.Calc.*;

public abstract class Boss extends Enemy {
    public HPBar thisHP;
    public ArrayList<HPBar> HPBars = new ArrayList<HPBar>();
    public int count = 0;
    public boolean resistFade = true;
    public BossScript bossScript;
    public Basic.BasicScript thisS;
    public final static int indicatorHeight = Engine.fHeight-GameEngine.heightOffset-GameEngine.scrHeight;
    public static BufferedImage indicatorBar = Calc.newBImage(Engine.fWidth, indicatorHeight);
    public final static BufferedImage indicator = indicator();
    public final static BufferedImage[] sprites = sprite();
    public static boolean bev = Save.existsFile("BEV_IS_BOSS.txt");
    public final static BufferedImage[] fadeSprites = Images.BG1.readImages("BevFade");
    public Boss(BossScript bossScript) {
        this.shift = toV(0, 0);
        status=status.Form;
        this.bossScript = bossScript;
        fade = 15;
    }
    

    @Override
    public void update() {
        if(this.thisS!=null){
            thisS.run();
        }
        switch (status) {
            case Form:
                form();
                break;
            case Active:
                active();
                break;
            case Fade:
                fade();
                break;
        }
        
        countage();
    }
    public void setScript(Basic.BasicScript thisS){
        this.thisS = thisS;
        if(thisS!=null)
        {
        this.thisS.setBasic(this);
        this.thisS.init();
        }
    }
    public void dropScript(){
        this.thisS = null;
    }
    public void toFade(){
        if(!resistFade){
            SE.EXPLODE.play();
            super.toFade();
        }
    }

    public void toActive() {
        status = status.Active;
        
    }

    public void form() {
        formScript();
        move();
    }

    ;
    
    
    
    public void active() {
        super.active();
        //moveScript();
    }

    ;
    public void fade() {
        super.fade();
        fadeScript();
        //move();
    }

    public void formScript(){
        toActive();
    };

    public void fadeScript(){
    };

    public void moveScript() {
//        thisSpell.update();
    }

  /*  public static class SpellCard {

        public int maxHP;

        public enum Type {

            SpellCard,
            NonSpell
        }
        public Type thisT;
        public int timeLimit;
        public int time;
        public int age;
        public Boss thisB;
        public String spellName;
        public Sprite spellNameImage;

        SpellCard(Boss thisB) {
            this.thisB = thisB;
        }

        public void declare() {
            thisB.HP = maxHP;
            Bullet.ClearAll();
        }

        public void update() {
            age++;
        }
    }*/

    public static class HPBar {

        public int count = 0;
    }

    @Override
    public void checkDead() {
        if (HP < 0) {
        /*    thisSpell = nextSpell();
            if (thisSpell == null) {
                this.toFade();
            }
            else{
                thisSpell.declare();
            }*/
        }
    }

    /*public SpellCard nextSpell() {
        SpellCard temp = thisHP.nextSpell();
        if (temp == null) {
            thisHP = nextHP();
            if (thisHP == null) {
                return null;
            } else {
                return nextSpell();
            }
        } else {
            return temp;
        }
    }*/
    
    public void shoot(){};

    public HPBar nextHP() {
        if (count<HPBars.size()) {
            return HPBars.get(count++);
        } else {
            return null;
        }

    }
    public void draw(Graphics2D g){
        if(false && bev)
        {
        Calc.drawImage(g, getSprite(), x, y);
        }
        else{
            super.draw(g);
        }
        drawIndicator();
    }
    public BufferedImage getSprite(){
        BufferedImage sprite = null;
        if(status==Status.Active){
            sprite = sprites[(int)min((pow(sin(age*.03),2)*sprites.length),sprites.length-1)];}
        
        if(status==Status.Fade){
            sprite = fadeSprites[(int)min(max(0,fadeSprites.length*(1-abs((double)age/fade))),fadeSprites.length-1)];}
        return sprite;
    }
    
    public void drawIndicator(){
        Graphics2D g2 = indicatorBar.createGraphics();
        g2.drawImage(indicator, (int)(x+GameEngine.widthOffset-indicator.getWidth()/2), 0, null);
    }
    public static void drawIndicatorBar(Graphics g){
        g.drawImage(indicatorBar, 0, Engine.fHeight-indicatorHeight, null);
        Graphics2D gBar = indicatorBar.createGraphics();
        gBar.setComposite(Calc.clear());
        gBar.fillRect(0, 0, indicatorBar.getWidth(), indicatorBar.getHeight());
    }
    
    public static BufferedImage indicator(){
        int width = 60;
        int height = indicatorHeight;
        BufferedImage indicator = Calc.newBImage(width, height);
        Graphics2D g2 = indicator.createGraphics();
        g2.setColor(Color.RED);
        g2.setComposite(Calc.opacity(.05));
        for(double i =0;i<1;i+=1./10){
            g2.fillRect((int)(i*width/2), 0, (int)(width*(1-i)), height);
        }
        g2.setFont(new Font(Font.SANS_SERIF,Font.BOLD,12));
        FontMetrics f = g2.getFontMetrics();
        
        g2.setColor(Color.WHITE);
        g2.setComposite(Calc.opacity(.5));
        String boss = "Boss";
        g2.drawString(boss, (width-f.stringWidth(boss))/2, height-(height-f.getAscent())/2);
        return indicator;
    }
    public static BufferedImage[] sprite(){
        return new BufferedImage[]{Images.BOSS1.image,Images.BOSS2.image,Images.BOSS3.image,Images.BOSS4.image,Images.BOSS5.image,Images.BOSS6.image,Images.BOSS7.image,Images.BOSS8.image,Images.BOSS9.image,Images.BOSS10.image,Images.BOSS11.image};
    }

    public void add(HPBar thisHP) {
        HPBars.add(thisHP);
    }
}
