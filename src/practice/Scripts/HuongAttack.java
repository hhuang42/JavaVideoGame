
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice.Scripts;

/**
 *
 * @author Henry
 */
import practice.*;
import static java.lang.Math.*;
import java.util.ArrayList;
import practice.Basic.BasicScript;
import static practice.Calc.*;

public class HuongAttack extends BossScript {
    {
        String[] nameSet = {
            "Crochet \"Chain Stitch\" - E",
            "Crochet \"Chain Stitch\" - N",
            "Knitting \"Stockinette\" - H",
            "Knitting \"Stockinette\" - L"
        };
        names = nameSet;
    }
    int moveDistance;
    int breakTime;
    int sign;
    int bulletNumber;
    int xRank;
    int yRank;
    int r;
    int chainNumber;
    int gap;
    float xDistance = 60;
    float yDistance = 30;
    float fallSpeed = .78f;
    float accel = .01f;
    float cutOff;
    int period = 6;
    int releaseTime;
    int accelTime = 60;
    float chainDistance;
    float redAccel;
    float randomAccel;
    boolean knit;

    public void start() {

        super.initializeBoss();
        r = 1;
        fallSpeed = .78f / 1.5f;
        accel = .01f;
        redAccel = .8f;
        accelTime = 180;
        period = 6;
        moveDistance = 30;
        breakTime = (int) (90 * 1.5f);
        sign = 1;
        cutOff = semiHeight * .5f;
        releaseTime = 120;
        knit = false;
        switch (difficulty) {
            case LUNATIC:
                knit = true;
                fallSpeed = .78f / 1.5f;
                accel = .01f;
                redAccel = .012f;
                accelTime = 120;
                breakTime = (int) (90 * 1.5f);
                cutOff = semiHeight * .75f;
                releaseTime = 120;

                period = 6;
                break;
            case HARD:
                knit = true;
                fallSpeed = .78f / 2f;
                accel = .008f;
                redAccel = .01f;
                accelTime = 180;
                breakTime = (int) (90 * 2f);
                cutOff = semiHeight * .5f;
                releaseTime = 120;

                period = 7;
                break;
            case NORMAL:
                chainNumber = 2;
                chainDistance = scrWidth * .6f;
                accel = .1f;
                redAccel = .12f;
                accelTime = 12;
                releaseTime = 480;
                randomAccel = .03f;
                period = 6;

                breakTime = (int) (75);

                fallSpeed = 34f / breakTime;
                break;
            case EASY:
                chainNumber = 2;
                chainDistance = scrWidth * .8f;
                accel = .15f;
                redAccel = .12f;
                accelTime = 12;
                releaseTime = 600;
                randomAccel = .01f;
                period = 6;

                breakTime = (int) (90);

                fallSpeed = 34f / breakTime;
                break;
        }
    }

    public void fill() {
        Action crochet = new Action() {

            float angle = 0 * (float) PI / 2;

            public void start() {
                sign *= -1;
                float rand = randomFloat(0, 2 * (float) PI);
                final float range = 15;

                float rad = 15;

                r = (r + chainNumber + 2) % 3;
                for (int i = -(chainNumber - 1); i <= chainNumber - 1; i += 2) {
                    Bullet.reset0();
                    Bullet.velocity0(toV(36f / 4 * sign, 0));
                    Col[] cols = {Col.RED, Col.GREEN, Col.GRAY};
                    float[] position = {semiWidth + i * chainDistance / 2, -60};
                    Bullet.reset0();

                    Bullet.tolerance0(300);
                    Bullet.type0(Bullet.Type.Medium);
                    Bullet.velocity0(toXY(toV(fallSpeed, angle + (float) PI / 2)));
                    int max = 25;
                    Bullet.color0(cols[r]);
                    r = (r + 2) % 3;
                    for (double a = 0; a < 1; a += 1. / 5) {
                        final float[] offset = toXY(toV(rad, (float) (-(a + 1) * PI / 2)));
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (breakTime * ((14 + a * 5.) / max));
                        new ThreadBullet(t, rotateOffset).add();
                    }

                    r = (r + 1) % 3;

                    Bullet.color0(cols[r]);
                    for (double a = 0; a < 1; a += 1. / 8) {
                        final float[] offset = sum(product(toV(-rad, 0), 1 - a), product(toV(0, 40), a));
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (breakTime * ((19 + a * 8.) / max));
                        new ThreadBullet(t, rotateOffset).add();
                    }
                    {
                        final float[] offset = toV(0, 32);
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (breakTime * (27. / max));
                        new ThreadBullet(t, rotateOffset).add();
                    }
                    r = (r + 1) % 3;

                    Bullet.color0(cols[r]);
                    {
                        final float[] offset = toV(0, 43);
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (0);
                        new ThreadBullet(t, rotateOffset).add();
                    }
                    for (double a = 0; a < 1; a += 1. / 8) {
                        final float[] offset = sum(product(toV(rad, 0), a), product(toV(0, 40), 1 - a));
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (breakTime * ((1 + a * 8.) / max));
                        new ThreadBullet(t, rotateOffset).add();
                    }

                    r = (r + 1) % 3;
                    for (double a = 0; a < 1; a += 1. / 5) {
                        Bullet.color0(cols[r]);
                        final float[] offset = toXY(toV(rad, (float) (-a * PI / 2)));
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (breakTime * ((9 + a * 5.) / max));
                        new ThreadBullet(t, rotateOffset).add();
                    }

                }
            }
        };
        Action crochetStart = new Action() {

            float angle = 0 * (float) PI / 2;

            public void start() {
                sign *= -1;
                float rand = randomFloat(0, 2 * (float) PI);
                final float range = 15;

                float rad = 15;

                r = (r + chainNumber + 2) % 3;
                for (int i = -(chainNumber - 1); i <= chainNumber - 1; i += 2) {
                    for(float layer = 3.5f;layer>=0;layer--)
                    {
                    Bullet.reset0();
                    Bullet.velocity0(toV(36f / 4 * sign, 0));
                    Col[] cols = {Col.RED, Col.GREEN, Col.GRAY};
                    float[] position = {semiWidth + i * chainDistance / 2, -60+34*layer};
                    Bullet.reset0();

                    Bullet.tolerance0(300);
                    Bullet.type0(Bullet.Type.Medium);
                    Bullet.velocity0(toXY(toV(fallSpeed, angle + (float) PI / 2)));
                    int max = 25;
                    Bullet.color0(cols[r]);
                    r = (r + 2) % 3;
                    for (double a = 0; a < 1; a += 1. / 5) {
                        final float[] offset = toXY(toV(rad, (float) (-(a + 1) * PI / 2)));
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (breakTime * ((14 + a * 5.) / max));
                        new ThreadBullet(t-(int)(layer*breakTime), rotateOffset).add();
                    }

                    r = (r + 1) % 3;

                    Bullet.color0(cols[r]);
                    for (double a = 0; a < 1; a += 1. / 8) {
                        final float[] offset = sum(product(toV(-rad, 0), 1 - a), product(toV(0, 40), a));
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (breakTime * ((19 + a * 8.) / max));
                        new ThreadBullet(t-(int)(layer*breakTime), rotateOffset).add();
                    }
                    {
                        final float[] offset = toV(0, 32);
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (breakTime * (27. / max));
                        new ThreadBullet(t-(int)(layer*breakTime), rotateOffset).add();
                    }
                    r = (r + 1) % 3;

                    Bullet.color0(cols[r]);
                    {
                        final float[] offset = toV(0, 43);
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (0);
                        new ThreadBullet(t-(int)(layer*breakTime), rotateOffset).add();
                    }
                    for (double a = 0; a < 1; a += 1. / 8) {
                        final float[] offset = sum(product(toV(rad, 0), a), product(toV(0, 40), 1 - a));
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (breakTime * ((1 + a * 8.) / max));
                        new ThreadBullet(t-(int)(layer*breakTime), rotateOffset).add();
                    }

                    r = (r + 1) % 3;
                    for (double a = 0; a < 1; a += 1. / 5) {
                        Bullet.color0(cols[r]);
                        final float[] offset = toXY(toV(rad, (float) (-a * PI / 2)));
                        final float[] rotateOffset = Calc.rotate(offset, angle);
                        Bullet.position0(sum(position, rotateOffset));
                        int t = (int) (breakTime * ((9 + a * 5.) / max));
                        new ThreadBullet(t-(int)(layer*breakTime), rotateOffset).add();
                    }
                }
                }
            }
        };
        Action knitting = new Action() {

            float rat = (float) sqrt(2);
            float radius = 30;

            public void start() {
                float y = -30;
                sign = (sign + 1) % 3;
                int sign2 = -1;
                float v0 = (float) (radius * 2 * PI / 120);
                for (float x = semiWidth - 1.2f * semiWidth; x < semiWidth + 1.2 * semiWidth; x += radius * rat) {

                    sign2 *= -1;
                    Bullet.reset0();
                    Bullet.tolerance0(100);
                    for (int sign3 = -1; sign3 <= 1; sign3 += 2) {
                        Bullet.position0(toV(x, y + (rat / 2 + .5f) / 2 * radius * sign2));
                        Bullet.velocity0(toV(sign3 * v0, 0));
                        Bullet.w0(-sign2 * sign3 * 2 * (float) PI / 120);
                        if ((sign - sign2 + 3) % 3 == 0) {
                            new RedYarn().add();

                        } else if ((sign - sign2 + 3) % 3 == 1) {

                            new WhiteYarn().add();
                        } else {

                            new GreenYarn().add();
                        }
                    }
                }
            }
        };
        Action knittingStart = new Action() {

            float rat = (float) sqrt(2);
            float radius = 30;

            public void start() {
                for(int layer=1;layer>=0;layer--)
                {
                float y = -30+(layer+.5f)*fallSpeed*(breakTime);
                sign = (sign + 1) % 3;
                int sign2 = -1;
                float v0 = (float) (radius * 2 * PI / 120);
                for (float x = semiWidth - 1.2f * semiWidth; x < semiWidth + 1.2 * semiWidth; x += radius * rat) {

                    sign2 *= -1;
                    Bullet.reset0();
                    Bullet.tolerance0(100);
                    for (int sign3 = -1; sign3 <= 1; sign3 += 2) {
                        Bullet.position0(toV(x, y + (rat / 2 + .5f) / 2 * radius * sign2));
                        Bullet.velocity0(toV(sign3 * v0, 0));
                        Bullet.w0(-sign2 * sign3 * 2 * (float) PI / 120);
                        if ((sign - sign2 + 3) % 3 == 0) {
                            new RedYarn().add();

                        } else if ((sign - sign2 + 3) % 3 == 1) {

                            new WhiteYarn().add();
                        } else {

                            new GreenYarn().add();
                        }
                    }}
                }
            }
        };
        this.declare(.5*semiHeight, 10000);
        this.actionArray.add(knit?knittingStart:crochetStart);
        this.addLoopStart();
        this.moveBossTowardsPlayer(toV(60, 120), breakTime / 2, moveDistance, 60);

        this.addPause(breakTime / 2);
        this.actionArray.add(
                knit?knitting:crochet);

        this.stopBoss();

        this.addPause(breakTime / 2);
        this.addLoopEnd();
    }

    public class ThreadBullet extends Bullet {

        float[] rotateOffset;
        int t0;

        public ThreadBullet(int t0, float[] rotateOffset) {
            this.t0 = t0;
            this.rotateOffset = rotateOffset;
        }

        public void move() {
            super.move();
            if (age == releaseTime + t0) {
                float angle = randomFloat(0, 1 * (float) PI);
                this.acceleration1(sum(sum(norm(rotateOffset, accel),
                        toXY(toV(randomAccel, randomFloat(0, 2 * (float) PI)))), toV(0, -1 * fallSpeed / accelTime)));
            }

            if (age == releaseTime + t0 + accelTime) {
                this.acceleration1(toV(0, 0));
            }
        }
    }

    public class RedYarn extends Bullet {

        {
            this.color1(Col.RED);
        }

        public void move() {
            super.move();
            if (age % period == 1) {
                Bullet.reset0();
                Bullet.color0(this.color);
                Bullet.position0(this.position());
                Bullet.tolerance0(100);

                Bullet.shoot0(null);

                Bullet.type0(Bullet.Type.Medium);
                final int t0 = age;
                new Bullet() {

                    int t = t0;
                    int rTime = -100;

                    public void move() {
                        super.move();
                        if (age == 50 - t0) {
                            velocity1(toV(0, fallSpeed));
                        }
                        if (y > cutOff && rTime < 0) {
                            rTime = age;
                            float angle = randomFloat(0, 1 * (float) PI);
                            this.acceleration1(sum(toV(.6f * randomFloat(-redAccel, redAccel), randomFloat(0, redAccel)), toV(0, 0 * fallSpeed / accelTime)));
                        }
                        if (age == rTime + accelTime) {
                            this.acceleration1(toV(0, 0));
                        }
                    }
                }.add();
            }
            if (age == 45) {
                this.status = Status.Fade;
            }
        }
    }

    public class GreenYarn extends Bullet {

        {
            this.color1(Col.GREEN);
        }

        public void move() {
            super.move();
            if (age % period == 1) {
                Bullet.reset0();
                Bullet.color0(this.color);
                Bullet.position0(this.position());
                Bullet.tolerance0(100);
                Bullet.shoot0(null);
                Bullet.type0(Bullet.Type.Medium);
                final int t0 = age;
                final float[] v = norm(velocity(), yv * w);
                new Bullet() {

                    int t = t0;
                    int rTime = -100;

                    public void move() {
                        super.move();
                        if (age == 50 - t0) {
                            velocity1(toV(0, fallSpeed));
                        }
                        if (y > cutOff && rTime < 0) {
                            rTime = age;
                            this.acceleration1(
                                    sum(norm(Calc.rotate(v, (float) -0 * PI / 2), accel), toV(0, -1 * fallSpeed / accelTime)));
                        }
                        if (age == rTime + accelTime) {
                            this.acceleration1(toV(0, 0));
                        }
                    }
                }.add();
            }
            if (age == 45) {
                this.status = Status.Fade;
            }
        }
    }

    public class WhiteYarn extends Bullet {

        {
            this.color1(Col.GRAY);
        }

        public void move() {
            super.move();
            if (age % period == 1) {
                Bullet.reset0();
                Bullet.color0(this.color);
                Bullet.position0(this.position());
                Bullet.tolerance0(100);
                Bullet.shoot0(null);
                Bullet.type0(Bullet.Type.Medium);
                final int t0 = age;
                final float[] v = norm(velocity(), yv * w);
                new Bullet() {

                    int t = t0;
                    int rTime = -100;

                    public void move() {
                        super.move();
                        if (age == 50 - t0) {
                            velocity1(toV(0, fallSpeed));
                        }
                        if (y > cutOff && rTime < 0) {
                            rTime = age;
                            this.acceleration1(
                                    sum(norm(Calc.rotate(v, (float) +1 * PI / 2), accel), toV(0, -1 * fallSpeed / accelTime)));
                        }
                        if (age == rTime + accelTime) {
                            this.acceleration1(toV(0, 0));
                        }
                    }
                }.add();
            }
            if (age == 45) {
                this.status = Status.Fade;
            }
        }
    }
}
