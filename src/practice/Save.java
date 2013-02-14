/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.EnumMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Henry
 */
public class Save {
    
    final static String settingsFlag = "SETTINGS";
    final static String deathCountFlag = "DEATHCOUNT";
    final static String makeFlag = "MAKE";
    final static String recordFlag = "RECORD";
    final static String saveFilePath = "Hello I am Hachiko the save file and I shall faithfully await your return.sav";
    final static String startList = "{";
    final static String endList = "}";
    final static String divider = "DO NOT TAMPER WITH";
    EnumMap<Difficulty,Integer> difficultyMap = new EnumMap<Difficulty,Integer>(Difficulty.class);
            
    EnumMap<Difficulty,Record> difficultyRecord = new EnumMap<Difficulty,Record>(Difficulty.class);
    EnumMap<Person,EnumMap<Difficulty,Record>> personMap = new EnumMap<Person,EnumMap<Difficulty,Record>>(Person.class);
    
    EnumMap<Person,EnumMap<Difficulty,String>> flavourMap = new EnumMap<Person,EnumMap<Difficulty,String>>(Person.class);
    
    EnumMap<Person, String> makeMap = new EnumMap<Person,String>(Person.class);
    
    public static boolean existsFile(String filename){
        File test = new File(filename);
        return test.exists();
    };
    
    public void init(){
        personMap = new EnumMap(Person.class);
        for(Person persona: Person.values()){
            EnumMap<Difficulty,Record> difficultyMap = new EnumMap<Difficulty,Record>(Difficulty.class);
                    for(Difficulty d :Difficulty.values()){
                        difficultyMap.put(d, new Record());
                    }
                    personMap.put(persona, difficultyMap);
        }
        difficultyMap = new EnumMap(Difficulty.class);
        for(Difficulty d:Difficulty.values()){
            difficultyMap.put(d,-1);
        }
        readFlavour();
        difficultyRecord = new EnumMap<Difficulty,Record>(Difficulty.class);
        for(Difficulty d:Difficulty.values()){
            difficultyRecord.put(d,new Record(0,Person.values().length));
        }
    }

    public void readFlavour() {
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("Texts/Flavour.txt");
            /*BufferedReader reader =
                    new BufferedReader(new InputStreamReader(in));*/
            String s = "";
            String line = null;
            int read = 0;
            /*while ((line = reader.readLine()) != null) {
                s+=line;
            }*/
            if(in==null){
                SE.LASER.play();
            }
            byte[] buffer = new byte[1024];
            while(read!=-1){
                s+=new String(
             Arrays.copyOfRange(buffer, 0, read));
                
             read = in.read(buffer);
             
            }
            
                        s = s.replace("\\n", "\n");
            flavourMap = new EnumMap(Person.class);
            makeMap = new EnumMap<Person,String>(Person.class);
            for(Person persona: Person.values()){
            EnumMap<Difficulty,String> flavourDMap = new EnumMap<Difficulty,String>(Difficulty.class);
                    for(Difficulty d :Difficulty.values()){
                        String flavour = getFlavour(persona,d,s);
                        flavourDMap.put(d, flavour);
                        String make = getMake(persona,s);
                        makeMap.put(persona, make);
                        //System.out.println(persona + " " + d+ " "+ flavour);
                    }
                    flavourMap.put(persona, flavourDMap);
        }
        } catch (NullPointerException x) {
            System.err.println(x);
            
            BGM.SEB.play();
        }
        catch(Exception e){
            SE.DEAD.play();
        }
    }
    public String getFlavour(Person persona, Difficulty d,String s){
        String flavour="";
        int i = s.indexOf(persona.name());
        int j = s.indexOf(d.name(), i);
        flavour = s.substring(s.indexOf("{", j)+1, s.indexOf("}", j));
        return flavour;
    }
    public String getMake(Person persona,String s){
        String flavour="";
        int i = s.indexOf(persona.name());
        int j = s.indexOf(makeFlag, i);
        flavour = s.substring(s.indexOf("{", j)+1, s.indexOf("}", j));
        return flavour;
    }
    public String flavour(Person persona, Difficulty d){
        return flavourMap.get(persona).get(d);
    }
    public boolean updateSpellRecord(Person p,Difficulty d,boolean success){
        EnumMap<Difficulty,Record> difficultyMap = this.personMap.get(p);
        Record r = difficultyMap.get(d);
        if(success)
        {
            return r.newSuccess();
        }
        else{
            return r.newAttempt();
        }
    }
    public int[] spellRecord(Person p,Difficulty d){
        EnumMap<Difficulty,Record> difficultyMap = this.personMap.get(p);
        Record r = difficultyMap.get(d);
        return r.toArray();
    }
    public Record playRecord(Difficulty d){
        Record r  = this.difficultyRecord.get(d);
        
        return r;
    }
    public boolean updateDeathRecord(Difficulty d,int currentRecord){
        if(currentRecord>=0)
        {
        difficultyMap.put(d, currentRecord);
        return true;}
        else{
            return false;
        }
        
    }
    public int deathRecord(Difficulty d){
        return difficultyMap.get(d);
        
    }
    
    public void copyToSave(){
        File logfile = new File(saveFilePath);
        try{
            logfile.createNewFile();
        
         new FileOutputStream(logfile).write(constructSave().getBytes());
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    
    public String constructSave(){
        String save = "";
        int[] mainSettings = {
            SettingsMenu.fpsIndex,
            SettingsMenu.vBGM,
            SettingsMenu.vSE
        };
        
        save+=this.toSaveFormat(settingsFlag, mainSettings);
        for(Difficulty d : Difficulty.values()){
            save+=this.toSaveFormat(dHead(d), new int[]{this.difficultyMap.get(d)});
        }
        for(Person persona :Person.values()){
            for(Difficulty d: Difficulty.values()){
                save+=this.toSaveFormat(combineHead(d,persona), personMap.get(persona).get(d).toArray());
            }
        }
        for(Difficulty d : Difficulty.values()){
            save+=this.toSaveFormat(rHead(d),this.difficultyRecord.get(d).toArray());
        }
        return save;
    }
    
    public String dHead(Difficulty d){
        return deathCountFlag+d.name();
    }
    public String rHead(Difficulty d){
        return recordFlag+d.name();
    }
    
    public String combineHead(Difficulty d,Person persona){
        return persona+d.name();
        
    }
    
    public boolean readSave(String s){
        try{
            int[] mainSettings = this.readSaveFormat(settingsFlag, s);
            SettingsMenu.setFPS(mainSettings[0]);
            SettingsMenu.setBGMVolume(mainSettings[1]);
            SettingsMenu.setSEVolume(mainSettings[2]);
        for(Difficulty d: Difficulty.values()){
            int[] deathCount = this.readSaveFormat(this.dHead(d), s);
            this.difficultyMap.put(d, deathCount[0]);
        }
        for(Person persona: Person.values()){
            for(Difficulty d:Difficulty.values())
            {
            int[] sA = this.readSaveFormat(this.combineHead(d, persona), s);
            this.personMap.get(persona).put(d, new Record(sA));
            }
        }
        for(Difficulty d: Difficulty.values()){
            int[] record = this.readSaveFormat(this.rHead(d), s);
            this.difficultyRecord.put(d, new Record(record));
        }
        }
        catch(Exception e){
            return false;
        }
        return true;
    }
    public int[] readSaveFormat(String head,String main){
        
        int n = main.indexOf(head);
        String substring = main.substring(
                main.indexOf(startList, n)+1,
                main.indexOf(endList, n));
            String[] stringData = substring.split(divider);
            int[] intData = new int[stringData.length];
            for(int i =0;i<stringData.length;i++){
                intData[i] = Integer.parseInt(stringData[i]);
            }
        
        return intData;
        
    }
    public String toSaveFormat(String head,int[] values){
        String s ="";
        s+=head;
        s+="{";
        for(int blah: values){
            
            s+=blah;
            
            s+=divider;
        }
        s+="}";
        return s;
    }
    
    
    public boolean copyFromSave(){
        File logfile = new File(saveFilePath);
        if(logfile.canRead())
        try{
            
        //System.out.println(logfile.createNewFile());
        byte[] blah = new byte[1024];
        InputStream is = new FileInputStream(logfile);
        int fileRead = 0;
        String s = "";
         while(fileRead!=-1)
         {
             
         s+=new String(
             Arrays.copyOfRange(blah, 0, fileRead));
             fileRead = is.read(blah);
         }
         readSave(s);
                 }
        catch(Exception e){
            e.printStackTrace();
        }
        return logfile.canRead();

    }

public static void main(String[] args){
    //t.readSave(t.constructSave());
}
public static class Record{
    private int success = 0;
    private int attempt = 0;
    public Record(int success, int attempt){
        if(success>=0&&attempt>=success){
            this.success = success;
            this.attempt = attempt;
        }
        
    }
    public Record(int[] successAttempt){
        this(successAttempt[0],successAttempt[1]);
        
    }
    public Record(){
        success = 0;
        attempt = 0;
    }
    public boolean newAttempt(){
        attempt++;
        return true;
    }
    public boolean newSuccess(){
        if(success<attempt)
        {
            success++;
            return true;
        }
        return false;
    }
    public int success(){
        return success;
    }
    public void success(int success){
        this.success=success;
    }
    public void attempt(int attempt){
        this.attempt=attempt;
    }
    public int attempt(){
        return attempt;
    }
    public int[] toArray(){
        return new int[]{success,attempt};
    }
}
}
