/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import practice.Scripts.*;

/**
 *
 * @author Henry
 */
public enum Person {
    YUKUN(new YukunAttack(),
            "Send redcoats to the chokepoint!",
            "Let's build outposts there too!",
            "Why don't we put walls there?",
            "Dude, let's get more outposts.",
            "Fool"),
    MICHAEL(new MichaelAttack(),
            "Straight Serve \"Flat Shot\"",
            "Curving Serve \"Slice Shot\"",
            "Priority Serve \"Aqua Jet\"",
            "Fated Serve \"Gungnir\"",
            "Magician"), 
    HUONG(new HuongAttack(),
            "Chain Stitch \"Unraveling Yarn\"",
            "Chain Stitch \"Starter Chain\"",
            "Stockinette \"Knitted Scarf\"",
            "Stockinette \"Christmas Scarf\"",
            "Priestess"),
    JISU(new JiSuAttack(),
            "Quick Sketch \"Lovable Loyalty\"",
            "Skillful Portrait \"Collar of Shame\"",
            "Lifelike Image \"Minion of Science\"",
            "Picture Finish \"Poster Pose\"",
            "Empress"), 
    JAMES(new JamesAttack(),
            "Izanagi \"Zio\"",
            "Jiraiya \"Magarula\"",
            "Tomoe \"Bufudyne\"",
            "Konohana Sakuya \"Maragidyne\"",
            "Emperor"), 
    IVAN(new IvanAttack(),
            "Perfect Aim \"Zombie Rush\"",
            "Rapid Fire \"Nightstalker Hoard\"",
            "Ceaseless Cover \"Double Tap Ambush\"",
            "Final Stand \"Bone Zombie Apocalypse\"",
            "Hierophant"),
    MICHELLE(new MichelleAttack(),
            "Chemical Bond \"Reduction\"",
            "Dipole-Dipole Bond \"-q\"",
            "Ionic Bond \"Anion\"",
            "Electrical Complement \"Anode\"",
            "Lovers"),
    SACHIT(new SachitAttack(),
            "Dance Floor \"Reno Night\"",
            "Party Animal \"Disorientation\"",
            "Light Show \"Slippery When Wet\"",
            "Yolo \"Club Two 300\"",
            "Chariot"), 
    JOSH(new JoshAttack(),
            "Emptiness \"Gott Ist Tot\"",
            "Jealousy \"Ressentiment\"",
            "Motivation \"Wille Zur Macht\"",
            "Creation \"Ubermensch\"",
            "Strength"),
    KIWAN(new KiWanAttack(),
            "Dance of Blades \"Conversation\"",
            "Offense \"Fleche\"",
            "Deflection \"Parry\"",
            "Counterattack \"Riposte\"",
            "Hermit"), 
    VIONA(new VionaAttack(),
            "Hobbyist \"Fresh Beginner\"",
            "Journeyman \"Casual Player\"",
            "Pioneer \"Seasoned Regular\"",
            "Veteran \"Hardcore Professional\"",
            "Fortune"
            ),
    DAEUN(new DaEunAttack(),
            "Second Chair \"Pizzicato\"",
            "First Chair \"Vibrato\"",
            "Principal Second \"Tremelo\"",
            "Concertmaster \"Arpeggio\"",
            "Justice"),
    TEO(new TeoAttack(),
            "lolnoob",
            "ysosrs",
            "trololol",
            "umad",
            "Hanged Man"),
    SAM(new SamAttack(),
            "Symmetry \"Fractal\"",
            "Determinism \"Dynamic System\"",
            "Mapping \"Linear Transformation\"",
            "Realized \"Matrix Product\"",
            "Death"),
    TONGJIA(new TongjiaAttack(),
            "Iron Sand \"砂鉄結襲\"",
            "Gathering \"砂鉄結襲\"",
            "Magnet Release \"砂鉄界法\"",
            "World Order \"砂鉄界法\"",
            "Temperance"
            ),
    BEN(new BenAttack(),
            "Darkness \"Dark Firaga\"",
            "Eternal Session \"Dark Firaga\"",
            "Soul Eater \"Dark Barrage\"",
            "Heart of a Hero \"Dark Barrage\"",
            "Devil"),
    GAMMATAU(new GammaTauAttack(),
            "Unripe Apple \"Immature Fruit\"",
            "Fallen Apple \"Fruit of Knowledge\"",
            "Golden Apple \"Midas Judgement\"",
            "Frozen Apple \"Fridge Logic\"",
            "Tower"), 
    KAEW(new KaewAttack(),
            "Nova Remnant \"T Pyx\"",
            "H II Region \"NGC 2070\"",
            "Planetary Nebula \"NGC 6720\"",
            "Supernova Remnant \"SN 1054\"",
            "Star"), 
    EUNBIN(new EunBinAttack(),
            "Ticklish Anger \"Crushing Red\"",
            "Ticklish Anger \"Piercing Yellow\"",
            "Ticklish Anger \"Explosive Black\"",
            "Ticklish Anger \"Shotgun Blue\"",
            "Moon"), 
    STEPHANIE(new StephanieAttack(),
            "Chemical Bond \"Oxidization\"",
            "Dipole-Dipole Bond \"+q\"",
            "Ionic Bond \"Cation\"",
            "Electrical Complement \"Cathode\"",
            "Sun");

    Person(BossScript thisS,String easyName,String normalName, String hardName, String lunaticName,String name) {
        this.thisS = thisS;
        String[] names = {easyName,normalName,hardName,lunaticName};
        thisS.names = names;
        this.name = name;
        thisS.persona=this;
    }
    Person(BossScript thisS,String name) {
        this.thisS = thisS;
        this.name = name;
        thisS.persona=this;
    }
    public static void init(){
        Person.values();
    }
    public BossScript thisS;
    public String name;
    public int success=0;
    public int attempt=0;
    
}
