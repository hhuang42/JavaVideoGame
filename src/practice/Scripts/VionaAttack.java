/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice.Scripts;

import java.awt.Point;
import practice.*;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.Iterator;
import practice.Basic.BasicScript;
import static practice.Calc.*;

/**
 *
 * @author Henry
 */
public class VionaAttack extends BossScript {

    final static int squareSize = 16;
    final static int boardWidth = scrWidth / squareSize;
    final static int boardHeight = scrHeight / squareSize + 6;
    static int updateFrame;
    int[][][] thePattern;
    int blockPause;
    int wavePause;
    float bulletSpeed;
    public ArrayList<Piece> pieceList = new ArrayList<Piece>();
    int I = 0;
    int J = 1;
    int L = 2;
    int S = 3;
    int Z = 4;
    int T = 5;
    int O = 6;
    public int[][][] pattern1 = {
        {{T, 1, 2}, {L, 20, 2}},
        {{J, 14, 2}, {I, 8, 0}},
        {{Z, 3, 0}, {O, 11, 1}},
        {{J, 15, 0}, {T, 5, 3}},
        {{O, 22, 0}, {O, 17, 0}},
        {{T, 0, 1}, {L, 19, 1}},
        {{Z, 6, 1}, {S, 4, 2}},
        {{J, 9, 2}, {I, 15, 0}},
        {{J, 21, 2}, {L, 7, 0}},
        {{J, 17, 0}, {L, 14, 0}},
        {{O, 11, 0}, {J, 22, 0}},
        {{T, 2, 0}, {O, 9, 0}}
    };
    public int[][][] pattern2 = {
        {{J, 21, 2}, {O, 0, 1}, {T, 10, 2}},
        {{J, 4, 3}, {L, 18, 2}, {O, 14, 0}},
        {{I, 2, 1}, {L, 5, 1}},
        {{Z, 12, 0}, {S, 8, 0}, {Z, 16, 1}},
        {{O, 0, 1}, {S, 7, 0}},
        {{S, 20, 1}, {J, 3, 1}},
        {{J, 12, 0}, {Z, 18, 1}, {S, 22, 1}},
        {{T, 10, 1}},
        {{J, 15, 2}, {S, 4, 1}, {O, 21, 0}, {L, 7, 0}},
        {{L, 21, 0}, {L, 9, 3}, {Z, 18, 1}},
        {{L, 2, 2}, {Z, 6, 0}, {J, 17, 1}},
        {{L, 1, 0}, {O, 12, 0}, {I, 8, 0}, {Z, 15, 0}},
        {{L, 12, 0}},
        {{J, 15, 0}, {I, 23, 1}}
    };
    boolean speedup = false;
    SquareSlot[][] Board = new SquareSlot[boardHeight][boardWidth];

    public void start() {
        super.initializeBoss();
        for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < boardWidth; j++) {
                Board[i][j] = new SquareSlot(new Point(j, i));

            }
        }
        pieceList = new ArrayList<Piece>();
        bulletSpeed = .3f;
        switch (difficulty) {
            case LUNATIC:
                thePattern = pattern2;
                updateFrame = 5;
                blockPause = 2;
                wavePause = 0;
                break;
            case HARD:
                thePattern = pattern2;
                updateFrame = 8;
                blockPause = 2;
                wavePause = 0;
                break;
            case NORMAL:
                thePattern = pattern1;
                updateFrame = 8;
                blockPause = 2;
                wavePause = 2;
                break;
            case EASY:
                thePattern = pattern1;
                updateFrame = 12;
                blockPause = 2;
                wavePause = 3;
                break;
        }
        Basic.BasicScript bossScript = new Basic.BasicScript() {

            public void fill() {
                Boss thisB = (Boss) basic;
                this.addPause(120);
                this.addLoopStart();
                this.actionArray.add(
                        new Action(this) {

                            public void start() {
                                float angle = Char.getAngle(basic.x, basic.y);
                                for (double a = 0; a < 1; a += 1. / 8) {
                                    Bullet.reset0();
                                    Bullet.color0(Col.GRAY);
                                    Bullet.shoot0(SE.ESHOOT0);
                                    Bullet.position0(basic.position());
                                    Bullet.velocity0(toXY(toV(.5f, angle + (float) (a * 2 * PI))));
                                    Bullet.type0(Bullet.Type.Outlined);
                                    new Bullet().add();
                                }

                            }
                        });
                this.addPause(600);
                this.addLoopEnd();
            }
        };
        thisBoss.setScript(bossScript);
    }

    public void run() {
        super.run();
        if (this.index % updateFrame == 0) {
            this.updateTetris();
        }

    }

    public void fill() {

        this.declare(120, 10000);
        this.addLoopStart();
        for (int[][] pattern : thePattern) {
            for (int[] block : pattern) {
                this.addPiece(Shape.values()[block[0]], block[1], block[2]);
                this.addPause(blockPause * updateFrame);
                this.moveBossAbovePlayer(90, 180);
            }
            this.addPause(wavePause * updateFrame);

        }
        this.addPause(00 * updateFrame);
        this.addLoopEnd();

    }

    public void addPiece(final Shape s, final int x, final int rotation) {
        this.actionArray.add(
                new Action(this) {

                    public void start() {
                        createPiece(s, new Point(x, 2), rotation);
                    }
                });
    }
    static final int[][][] IPoints = {{{-1, 0}, {0, 0}, {1, 0}, {2, 0}}, {{0, -1}, {0, 0}, {0, 1}, {0, -2}}};
    static final int[][][] JPoints = {{{-1, 0}, {0, 0}, {1, 0}, {-1, 1}},
        {{0, 1}, {0, 0}, {0, -1}, {1, 1}},
        {{-1, 0}, {0, 0}, {1, 0}, {1, -1}},
        {{0, 1}, {0, 0}, {0, -1}, {-1, -1}}};
    static final int[][][] LPoints = {{{-1, 0}, {0, 0}, {1, 0}, {1, 1}},
        {{0, 1}, {0, 0}, {0, -1}, {1, -1}},
        {{-1, 0}, {0, 0}, {1, 0}, {-1, -1}},
        {{0, 1}, {0, 0}, {0, -1}, {-1, 1}}};
    static final int[][][] SPoints = {{{-1, 0}, {0, 0}, {1, 1}, {0, 1}}, {{0, 1}, {0, 0}, {1, 0}, {1, -1}}};
    static final int[][][] ZPoints = {{{-1, 1}, {0, 0}, {0, 1}, {1, 0}}, {{1, 1}, {0, 0}, {1, 0}, {0, -1}}};
    static final int[][][] TPoints = {{{0, 0}, {1, 0}, {-1, 0}, {0, 1}},
        {{0, 1}, {0, 0}, {1, 0}, {0, -1}},
        {{-1, 0}, {0, 0}, {1, 0}, {0, -1}},
        {{0, 1}, {0, 0}, {0, -1}, {-1, 0}}};
    static final int[][][] OPoints = {{{0, 1}, {0, 0}, {1, 0}, {1, 1}}};

    public enum Shape {

        I(Col.CYAN, IPoints),
        J(Col.BLUE, LPoints),
        L(Col.ORANGE, JPoints),
        S(Col.RED, ZPoints),
        Z(Col.GREEN, SPoints),
        T(Col.PINK, TPoints),
        O(Col.YELLOW, OPoints);

        Shape(Col c, int[][][] points) {
            this.c = c;
            this.points = points;
        }
        Col c;
        int[][][] points;
    }

    public boolean createPiece(Shape s, Point position, int rotation) {
        int[][] pts = s.points[rotation % s.points.length];
        boolean goThrough = true;
        SquareSlot[] slots = new SquareSlot[4];
        for (int i = 0; i < 4; i++) {
            slots[i] = getSlot(position.x + pts[i][0], position.y + pts[i][1]);
            if (slots[i] == null || slots[i].thisS != null) {
                goThrough = false;
            }
        }
        if (goThrough) {
            new Piece(slots, s.c).add();

        }
        return goThrough;

    }

    public void updatePiece() {

        synchronized (pieceList) {

            Iterator<Piece> count = pieceList.iterator();

            while (count.hasNext()) {
                Piece thisP = count.next();

                thisP.update();

                if (!thisP.falling) {

                    count.remove();

                }

            }
        }
    }

    public SquareSlot getSlot(int x, int y) {
        if (x >= 0 && x < boardWidth && y >= 0 && y < Board.length) {
            return Board[y][x];
        } else {
            return null;
        }
    }

    public SquareSlot getSlot(Point p) {
        return getSlot(p.x, p.y);
    }

    public void updateTetris() {
        this.updatePiece();
        for (int i = Board.length - 1; i >= 0; i--) {
            boolean clearAll = true;
            boolean scrollDown = true;
            for (int j = 0; j < boardWidth; j++) {
                SquareSlot slot = Board[i][j];

                if (slot.thisS == null) {
                    clearAll = false;
                } else {
                    scrollDown = false;
                    if (slot.thisS.partOfPiece) {
                        clearAll = false;
                    }
                }

            }
            for (int j = 0; j < boardWidth; j++) {
                SquareSlot slot = Board[i][j];
                if (clearAll || scrollDown) {
                    if (slot.thisS != null) {
                        slot.thisS.clear();
                    }
                    for (int m = i; m >= 0; m--) {
                        SquareSlot newSlot = Board[m][j];
                        if (newSlot.thisS != null) {
                            newSlot.thisS.falling = true;
                        }
                    }
                } else {
                    if (slot.thisS != null) {
                        slot.thisS.update();
                    }
                }




            }
        }
    }

    public class Piece {

        Square[] squares = new Square[4];
        boolean falling = true;

        public Piece(SquareSlot[] slots, Col color) {
            for (int i = 0; i < 4; i++) {
                slots[i].thisS = new Square(slots[i].position, color);
                squares[i] = slots[i].thisS;
            }
        }

        public void add() {
            synchronized (pieceList) {
                pieceList.add(this);
                
                SE.ESHOOT1.play();
            }
        }

        public boolean checkBelow() {
            for (Square square : squares) {
                square.lift();
            }
            boolean goAhead = true;
            for (Square square : squares) {
                if (!square.checkBelow()) {
                    goAhead = false;
                };
            }
            for (Square square : squares) {
                square.place();
            }
            return goAhead;
        }

        public void update() {
            if (falling) {
                falling = checkBelow();
            }
            if (!falling) {
                for (Square square : squares) {
                    square.partOfPiece = false;
                    square.falling = false;
                }

            }

        }
    }

    public class SquareSlot {

        Square thisS;
        Point position;

        public SquareSlot(Point position) {
            this.position = position;
        }
    }

    public class Square {

        Bullet thisB;
        Point position;
        Col color;
        boolean partOfPiece;
        boolean falling;

        public Square(Point position, Col color) {
            this.position = position;
            this.color = color;
            partOfPiece = true;
            falling = true;
            
        }

        public float[] toBulletPosition(Point p) {
            float x = (p.x + .5f) * squareSize;
            float y = scrHeight - (boardHeight - p.y - .5f) * squareSize;
            return toV(x, y);
        }

        public void update() {
            if (falling) {
                falling = this.fall();
            }
            if (thisB == null || thisB.status == Bullet.Status.Dead || thisB.status == Bullet.Status.Fade) {
                Bullet.reset0();
                Bullet.position0(toBulletPosition(position));
                Bullet.velocity0(toV(0, 0));
                Bullet.delay0(0);
                Bullet.type0(Bullet.Type.Outlined);
                Bullet.shoot0(null);
                Bullet.tolerance0(120);
                Bullet.color0(this.color);
                thisB = new Bullet();
                thisB.add();
            } else {
                if (falling) {
                    thisB.velocity1(toV(0, (float) squareSize / updateFrame));
                } else {
                    thisB.velocity1(toV(0, 0));
                }
            }
            if (!partOfPiece) {
                falling = false;
            }

        }

        public void clear() {
            thisB.status = Bullet.Status.Fade;
            slot().thisS = null;
            for (double i = 0; i < 1; i += 1. / 1) {
                Bullet.reset0();
                Bullet.delay0(20);
                Bullet.color0(this.color);
                Bullet.shoot0(SE.KIRA);
                Bullet.position0(this.toBulletPosition(position));
                Bullet.velocity0(toXY(toV(bulletSpeed, randomFloat(0, 2 * (float) PI))));
                Bullet.type0(Bullet.Type.Pellet);
                new Bullet().add();
            }
        }

        public SquareSlot slot() {
            return getSlot(position);
        }

        public void lift() {
            slot().thisS = null;
        }

        public void place() {
            slot().thisS = this;
        }

        public void moveDown() {
            this.position.y++;
        }

        public boolean fall() {
            SquareSlot newSlot = getSlot(position.x, position.y + 1);
            if (newSlot != null) {
                if (newSlot.thisS == null) {
                    newSlot.thisS = this;
                    slot().thisS = null;
                    this.position = newSlot.position;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        public boolean checkBelow() {
            SquareSlot newSlot = getSlot(position.x, position.y + 1);
            if (newSlot != null) {
                if (newSlot.thisS == null) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
