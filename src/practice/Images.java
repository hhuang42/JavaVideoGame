/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;


import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
   

/**
 *
 * @author Henry
 */
public enum Images {
    BG1("src/Images/background1.png"),
    
    BG2("src/Images/background2.png"),
    
    BG3("src/Images/background3.png"),
    
    BG4("Images/background4.png"),
    
    BG5("Images/background5.png"),
    BOSS1("Images/BevPic1.png"),
    
    BOSS2("Images/BevPic2.png"),
    BOSS3("Images/BevPic3.png"),
    BOSS4("Images/BevPic4.png"),
    BOSS5("Images/BevPic5.png"),
    BOSS6("Images/BevPic6.png"),
    BOSS7("Images/BevPic7.png"),
    BOSS8("Images/BevPic8.png"),
    BOSS9("Images/BevPic9.png"),
    BOSS10("Images/BevPic10.png"),
    BOSS11("Images/BevPic11.png"),
    TITLE("Images/title.png",1),
    
    MINITITLE("Images/title.png",.4),
    EASY("Images/easy.png",.3),
    
    NORMAL("Images/normal.png",.3),
    HARD("Images/hard.png",.3),
    LUNATIC("Images/lunatic.png",.3),
    FARBG("Images/farBackground.png"),
    MENUCOVER("Images/menuBackgroundCover.png"),
    
    MENUCOVERSMALL("Images/menuBackgroundCoverSmall.png"),
    
    MENUCOVERLARGE("Images/menuBackgroundCoverLarge.png"),
    
    MENUBACK("Images/menuBackgroundBack.png"),
    
    MENUBACK2("Images/menuBackgroundBack2.png")
    ;
    BufferedImage image;
    
    public static void main(String args[]){
        Images.BG1.readImages("BevFade");
    }
    public static Images Images(Difficulty d){
        return Images.valueOf(d.name());
    }
    public BufferedImage[] readImages(String baseName){
        int n = 1;
        String urlString = "Images/"+baseName+n+".png";
        InputStream thisStream = this.getClass().getClassLoader().getResourceAsStream(urlString);
        ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
        BufferedImage image = null;
        while(thisStream!=null){
            try{
            image = ImageIO.read(thisStream);
            }
            catch(Exception e){
                ;
            }
            if(image!=null)
            {
                BufferedImage bi = Calc.newBImage(image.getWidth(), image.getHeight());
                bi.getGraphics().drawImage(image, 0, 0, null);
                image = bi;
            }
            temp.add(image);
            n++;
            urlString = "Images/"+baseName+n+".png";
            thisStream = this.getClass().getClassLoader().getResourceAsStream(urlString);
        }
        return temp.toArray(new BufferedImage[]{});
    }
        
        Images(String fileName){
            File thisF = new File(fileName);
            
            InputStream thisStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
            try{
            image = ImageIO.read(thisStream);
            }
            catch(Exception e){
                ;
            }
            if(image!=null)
            {
                BufferedImage bi = Calc.newBImage(image.getWidth(), image.getHeight());
                bi.getGraphics().drawImage(image, 0, 0, null);
                image = bi;
            }
         
        }
        Images(String fileName,double ratio){
            File thisF = new File(fileName);
            
            InputStream thisStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
            try{
            image = ImageIO.read(thisStream);
            }
            catch(Exception e){
                ;
            }
            if(image!=null)
            {
                BufferedImage bi = Calc.newBImage(image.getWidth()*ratio, image.getHeight()*ratio);
                bi.getGraphics().drawImage(image, 0, 0,(int)(image.getWidth()*ratio), (int)(image.getHeight()*ratio), null);
                image = bi;
            }
         
        }
        public BufferedImage getImage(){
            return image;
        }
    
    
}
