/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.image.*;
import java.awt.*;
import java.util.*;
import static java.lang.Math.*;

/**
 *
 * @author Student
 */
public class Calc {

    static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    static GraphicsDevice device = env.getDefaultScreenDevice();
    static GraphicsConfiguration config = device.getDefaultConfiguration();
    static BufferedImage bulletPic = config.createCompatibleImage(16, 16, Transparency.TRANSLUCENT);
    static Random rand = new Random();
    private static float a = .5f;

    public static void drawTextRectangle(Graphics g, int xMin, int yMin, int xMax, int yMax, String text) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.setComposite(Calc.opacity(.8));
        int bound = 25;
        g2.fillRoundRect(xMin, yMin, xMax - xMin, yMax - yMin, bound*2, bound*2);
        int maxWidth = xMax - xMin - 2*bound;
        g2.setFont(new Font(Font.SERIF,Font.BOLD,16));
        FontMetrics f = g2.getFontMetrics();
        String[] lines = text.split("\n");
        int height = yMin+bound+f.getAscent();
        g.setColor(Color.BLACK);
        
        g2.setComposite(Calc.opacity(1));
        for (String line : lines) {
            String[] words = line.split(" ");
            String thisLine = "";
            for (String word : words) {
                if (f.stringWidth(thisLine + word) <= maxWidth+f.stringWidth(" ")) {
                    thisLine += word+" ";
                } else {
                    g.drawString(thisLine, xMin+bound, height);
                    height += f.getHeight();
                    thisLine = word + " ";
                }
            }
            g.drawString(thisLine, xMin+bound, height);
            height += f.getHeight();
        }


    }

    public static BufferedImage newBImage(double width, double height) {
        return config.createCompatibleImage((int) round(width), (int) round(height), Transparency.TRANSLUCENT);
    }

    public static void drawBackground(Graphics g, BufferedImage image, int x, int y) {

        g.drawImage(image, Engine.thisE.app.getInsets().left - 3 + x, y, null);
    }

    public static int sign(double number) {
        if (number == 0) {
            return 1;
        } else {
            return (int) Math.signum(number);
        }

    }

    public static BufferedImage textToImageR(String s, Color c, int fontSize) {
        double ratio = 1;
        double charHeight = (ratio * fontSize);

        double charWidth = fontSize * ratio;
        double width = charWidth * s.length();
        BufferedImage image = config.createCompatibleImage((int) round(width), (int) round(2 * charHeight), Transparency.TRANSLUCENT);
        Graphics2D g2 = image.createGraphics();
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, fontSize);
        FontMetrics blah = g2.getFontMetrics(f);
        double actualWidth = blah.stringWidth(s);
        double actualHeight = blah.getHeight();
        g2.setColor(c);
        g2.setFont(f);
        g2.drawString(s, round(width - actualWidth), round(actualHeight));
        return image;
    }

    public static BufferedImage textToImageL(String s, Color c, int fontSize) {
        double ratio = 1;
        double charHeight = (ratio * fontSize);

        double charWidth = fontSize * ratio;
        double width = charWidth * s.length();
        BufferedImage image = config.createCompatibleImage((int) round(width), (int) round(2 * charHeight), Transparency.TRANSLUCENT);
        Graphics2D g2 = image.createGraphics();
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, fontSize);
        FontMetrics blah = g2.getFontMetrics(f);
        double actualHeight = blah.getHeight();
        g2.setColor(c);
        g2.setFont(f);
        g2.drawString(s, 0, round(actualHeight));
        return image;
    }

    public static double toAngle(double[] pt1, double[] pt2) {
        return atan2(pt2[1] - pt1[1], pt2[0] - pt1[0]);
    }

    public static float toAngle(float[] pt1, float[] pt2) {
        return (float) atan2(pt2[1] - pt1[1], pt2[0] - pt1[0]);
    }

    public static float mag(float[] pt) {
        return (float) hypot(pt[0], pt[1]);
    }

    public static float diffAngle(float[] vector1, float[] vector0) {
        return (float) ((toAngle(toV(0, 0.), toD(vector1)) - toAngle(toV(0, 0.), toD(vector0))) % (2 * PI));
    }

    public static float angle0(float angle) {
        return (float) ((angle + 3 * PI) % (2 * PI) - PI);
    }

    public static float[] norm(float[] pt) {
        float mag = mag(pt);
        if (mag != 0) {
            return toV(pt[0] / mag, pt[1] / mag);
        } else {
            return pt;
        }
    }

    public static float[] norm(float[] pt, float mag) {
        float[] unit = norm(pt);
        return toV(unit[0] * mag, unit[1] * mag);
    }

    public static float[] diff(float[] startpt, float[] endpt) {
        return toV(endpt[0] - startpt[0], endpt[1] - startpt[1]);
    }

    public static float[] sum(float[] startpt, float[] endpt) {
        return toV(endpt[0] + startpt[0], endpt[1] + startpt[1]);
    }

    public static float[] product(float[] startpt, float[] endpt) {
        return toV(endpt[0] * startpt[0], endpt[1] * startpt[1]);
    }

    public static float[] product(float[] startpt, double mult) {
        return toF(toV(mult * startpt[0], mult * startpt[1]));
    }

    public static double[] toD(float[] list) {
        double[] newlist = new double[list.length];
        for (int n = 0; n < list.length; n++) {
            newlist[n] = (double) list[n];
        }
        return newlist;
    }

    public static float[] homeConstVel(float[] target, float[] position, float[] velocity, double react) {
        float[] temp = toV(target[0] - position[0], target[1] - position[1]);
        temp = norm(temp, (float) react);
        return norm(toV(temp[0] + norm(velocity)[0], temp[1] + norm(velocity)[1]), mag(velocity));
    }

    public static boolean collide(float[] circleC, double radiusC, float[] ellipseC, float[] maxMinSemiAxes, float angle) {
        float[] newD = rotate(toV(circleC[0] - ellipseC[0], circleC[1] - ellipseC[1]), -angle);
        if (pow(newD[0] * (maxMinSemiAxes[1] + radiusC), 2) + pow(newD[1] * (maxMinSemiAxes[0] + radiusC), 2)
                <= pow((maxMinSemiAxes[1] + radiusC) * (maxMinSemiAxes[0] + radiusC), 2)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean collide(float[] circleC, double radiusC, float[] ellipseC, float radius2) {
        float[] newD = toV(circleC[0] - ellipseC[0], circleC[1] - ellipseC[1]);
        if (pow(newD[0], 2) + pow(newD[1], 2)
                <= pow((radius2 + radiusC), 2)) {
            return true;
        } else {
            return false;
        }
    }

    public static float[] toF(double[] list) {
        float[] newlist = new float[list.length];
        for (int n = 0; n < list.length; n++) {
            newlist[n] = (float) list[n];
        }
        return newlist;
    }

    public static float[] hermiteT(float[] pt1, float[] pt2, float[] t1, float[] t2, double per) {
        float[] position = new float[2];
        for (int i = 0; i <= 1; i++) {
            position[i] = (float) ((2 * pow(per, 3) - 3 * pow(per, 2) + 1) * pt1[i]
                    + (-2 * pow(per, 3) + 3 * pow(per, 2)) * pt2[i]
                    + (pow(per, 3) - 2 * pow(per, 2) + per) * t1[i]
                    + (pow(per, 3) - pow(per, 2)) * t2[i]);
        }
        return position;
    }

    public static float[] hermiteP(float[][] pt0, float[][] pt1, float[][] pt2, float[][] pt3, double per) {
        return hermiteT(
                pt1[0],
                pt2[0],
                toV(
                a * (pt2[0][0] - pt0[0][0]) * (2 * pt2[1][0] / (pt1[1][0] + pt2[1][0])),
                a * (pt2[0][1] - pt0[0][1]) * (2 * pt2[1][0] / (pt1[1][0] + pt2[1][0]))),
                toV(
                a * (pt3[0][0] - pt1[0][0]) * (2 * pt2[1][0] / (pt3[1][0] + pt2[1][0])),
                a * (pt3[0][1] - pt1[0][1]) * (2 * pt2[1][0] / (pt3[1][0] + pt2[1][0]))),
                per);
    }

    public static float[] toV(float x, float y) {
        float[] vector = {x, y};
        return vector;
    }

    public static double[] toV(double x, double y) {
        double[] vector = {x, y};
        return vector;
    }

    public static float[] toRT(float[] xy) {
        float[] vector = {(float) pow(distanceSquare(xy), .5), (float) atan2(xy[1], xy[0])};
        return vector;
    }

    public static float[] toXY(float[] rt) {
        float[] vector = {(float) (rt[0] * cos(rt[1])), (float) (rt[0] * sin(rt[1]))};
        return vector;
    }

    public static float[] rotate(float[] vector, double angle) {
        if (angle != 0) {
            float[] rotated = {(float) (vector[0] * cos(angle) - (float) (vector[1] * sin(angle))),
                (float) (vector[1] * cos(angle) + (float) (vector[0] * sin(angle)))};

            return rotated;
        } else {
            return vector;
        }
    }

    public static double[] rotate(double[] vector, double angle) {
        if (angle != 0) {
            double[] rotated = {(vector[0] * cos(angle) - (vector[1] * sin(angle))),
                (vector[1] * cos(angle) + (vector[0] * sin(angle)))};
            return rotated;
        } else {
            return vector;
        }
    }

    public static boolean outBound(float x, float y, float margin) {
        return (GameEngine.scrWidth + margin <= x || x <= 1 - margin || GameEngine.scrHeight + margin <= y || y <= 1 - margin);
    }

    public static boolean outBound(float x, float y, float[] margin) {
        return (GameEngine.scrWidth + margin[0] <= x
                || x <= 1 - margin[0]
                || GameEngine.scrHeight + margin[1] <= y
                || y <= 1 - margin[1]);
    }

    public static float distanceSquare(float x1, float y1, float x2, float y2) {

        return (float) (pow(x1 - x2, 2) + pow(y1 - y2, 2));

    }

    public static float distanceSquare(float x1, float y1) {

        return (float) (pow(x1, 2) + pow(y1, 2));

    }

    public static float distanceSquare(float[] position1, float[] position2) {

        return (float) (pow(position1[0] - position2[0], 2) + pow(position1[1] - position2[1], 2));

    }

    public static float distance(float[] position1, float[] position2) {

        return (float) hypot(position1[0] - position2[0], position1[1] - position2[1]);

    }

    public static float distanceSquare(float[] distance) {

        return (float) (pow(distance[0], 2) + pow(distance[1], 2));

    }

    public static AlphaComposite opacity(double alpha) {
        return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha);

    }

    public static AlphaComposite clear() {
        return AlphaComposite.getInstance(AlphaComposite.CLEAR, (float) 0);
    }

    public static void fillCircle(Graphics g, double x, double y, double radius) {
        g.fillOval(round((float) x - (float) radius), round((float) (y - radius)), round((float) (2 * radius)), round((float) (2 * radius)));
    }

    public static void fillEllipse(Graphics g, double x, double y, double xradius, double yradius) {
        g.fillOval(round((float) x - (float) xradius), round((float) (y - yradius)), round((float) (2 * xradius)), round((float) (2 * yradius)));
    }

    public static void fillEllipse(Graphics g, double x, double y, double xradius, double yradius, double theta0, double theta1) {
        g.fillArc(
                round((float) x - (float) xradius),
                round((float) (y - yradius)),
                round((float) (2 * xradius)),
                round((float) (2 * yradius)),
                (int) round(theta0 * 360. / (2 * PI)),
                (int) round(theta1 * 360. / (2 * PI)));
    }

    public static void fillPolygon(Graphics g, double[][] list) {
        int[] xlist = new int[list.length];
        int[] ylist = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            xlist[i] = (int) round(list[i][0]);
            ylist[i] = (int) round(list[i][1]);
        }
        g.fillPolygon(xlist, ylist, list.length);
    }

    public static void fillPolygon(Graphics g, float[][] list) {
        int[] xlist = new int[list.length];
        int[] ylist = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            xlist[i] = (int) round(list[i][0]);
            ylist[i] = (int) round(list[i][1]);
        }
        g.fillPolygon(xlist, ylist, list.length);
    }

    public static void drawCircle(Graphics g, double x, double y, double radius) {
        g.drawOval((int) round(x - radius), (int) round(y - radius), (int) round(2 * radius), (int) round(2 * radius));
    }

    public static void drawEllipse(Graphics g, double x, double y, double xradius, double yradius) {
        g.drawOval((int) round(x - xradius), (int) round(y - yradius), (int) round(2 * xradius), (int) round(2 * yradius));
    }

    public static void drawEllipse(Graphics g, double x, double y, double xradius, double yradius, double theta0, double theta1) {
        g.drawArc(
                round((float) x - (float) xradius),
                round((float) (y - yradius)),
                round((float) (2 * xradius + 1)),
                round((float) (2 * yradius + 1)),
                (int) round(theta0 * 360 / (2 * PI)),
                (int) round(theta1 * 360 / (2 * PI)));
    }

    public static void drawImage(Graphics g, BufferedImage i, double x, double y) {
        g.drawImage(i, (int) round(x - i.getWidth() / 2), (int) round(y - i.getHeight() / 2), null);
    }

    public static int randomInt(int min, int max) {
        return min + rand.nextInt(max - min + 1);
    }

    public static float randomFloat(float min, float max) {
        return min + rand.nextFloat() * (max - min);
    }

    public static int mod(int num, int base) {
        if (num < 0) {
            return num % base + base;
        } else {
            return num % base;
        }
    }

    public static boolean inRange(int age, int start, int duration, int d, int period) {
        return (((age % period) >= start)
                && ((age - start) % period <= duration)
                && ((age - start) % period) % d == 0);
    }
}
