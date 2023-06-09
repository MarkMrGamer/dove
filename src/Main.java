import javax.imageio.ImageIO;
import javax.sound.midi.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;

public class Main extends JComponent {
    // Westerbur doesn't know my programming skills on Java is terrible
    // TODO: make the ending to level 3 and advance to level 4

    // main character
    public static int xPlayer = 300;
    public static int yPlayer = 200;
    public static int wPlayer = 32;
    public static int hPlayer = 32;
    public static int playerHP = 100;
    public static boolean flappy = false;
    public static boolean inBattle = false;
    public static int flapVelo = 0;

    // dove
    public static int xDove = 100;
    public static int yDove = 200;
    public static int wDove = 100;
    public static int hDove = 100;

    // pigeon
    public static int xPige = 400;
    public static int yPige = 300;
    public static int wPige = 32;
    public static int hPige = 32;
    public static boolean alerted = false;
    public static boolean screwed = false;
    public static boolean jumpscared = false;
    public static int steps = 0;

    // potato
    public static int xPotato = 400;
    public static int yPotato = 250;
    public static int wPotato = 32;
    public static int hPotato = 32;

    // tree 1
    public static int xTree1 = 600;

    // tree 2
    public static int xTree2 = 600;

    // window
    public static int xWindow = 420;
    public static int yWindow = 0;
    public static int wWindow = 346;
    public static int hWindow = 484;
    public static int windowCount = 0;

    // food
    public static int xFood = 0;
    public static int yFood = 0;
    public static int wFood = 32;
    public static int hFood = 32;
    public static boolean foodSpawned = false;
    public static int foodTime = 0;

    // man
    public static int manHP = 100;
    public static int xMan = 400;
    public static int yMan = 320;
    public static int wMan = 95;
    public static int hMan = 120;

    // magic
    public static int xMagic = 999;
    public static int yMagic = 999;
    public static int wMagic = 32;
    public static int hMagic = 32;

    // watergun
    public static int xGun = 200;
    public static int yGun = 200;
    public static int wGun = 72;
    public static int hGun = 45;
    public static boolean gEquipped = false;
    public static boolean pew = false;
    public static double angle;
    public static int xDrop = 999;
    public static int yDrop = 999;
    public static int xOldD = 999;
    public static int yOldD = 999;
    public static double aDrop;

    // mugman1
    public static int xMug1 = 53;
    public static int yMug1 = 330;
    public static int wMug1 = 100;
    public static int hMug1 = 100;
    public static boolean mug1Dead = false;
    public static int m1xMove = 3;
    public static int m1yMove = 3;

    // sprites
    public BufferedImage charImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/bird.png")));
    public BufferedImage doveImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/dove.gif")));
    public BufferedImage pigeImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/pigeon.png")));
    public BufferedImage potatoImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/potato.png")));
    public BufferedImage treeImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/tree.png")));
    public BufferedImage windowImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/window.png")));
    public BufferedImage winBrokImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/windowbroken.png")));
    public BufferedImage foodImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/food.png")));
    public BufferedImage doveBg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/dove.jpg")));
    public BufferedImage coffeeBg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/coffee.png")));
    public BufferedImage roomBg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/room.png")));
    public BufferedImage roomBg2 = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/room2.png")));
    public BufferedImage jumpscareBg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/bjumpscar.png")));
    public BufferedImage deadBg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/dovedown.png")));
    public BufferedImage gaugeImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/gauge.png")));
    public BufferedImage sliderImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/slider.png")));
    public BufferedImage mailImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/mail.jpg")));
    public BufferedImage sambagImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/sambag.jpg")));
    public BufferedImage mugManImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/mugman.png")));
    public BufferedImage waterGImg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/watergun.png")));
    public BufferedImage bathBg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/bathroom.png")));
    public Image mrunImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/images/manrun.gif"))).getImage();

    // sounds
    public static Clip clip;
    public static AudioInputStream gameOverSnd;
    public static AudioInputStream jumpScareSnd;
    public static AudioInputStream alertedSnd;
    public static AudioInputStream hohoSnd;
    public static AudioInputStream crashSnd;
    public static AudioInputStream breakSnd;
    public static AudioInputStream gobbleSnd;
    public static AudioInputStream deadSnd;
    public static AudioInputStream goodbyeSnd;

    // music

    public static Sequence doveMus;

    static {
        try {
            doveMus = MidiSystem.getSequence(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/music/what.mid")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Sequence doveMus2;

    static {
        try {
            doveMus2 = MidiSystem.getSequence(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/music/his.mid")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Sequence doveMus3;

    static {
        try {
            doveMus3 = MidiSystem.getSequence(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/music/does.mid")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Sequence doveMus4;

    static {
        try {
            doveMus4 = MidiSystem.getSequence(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/music/are.mid")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Sequence doveMus5;

    static {
        try {
            doveMus5 = MidiSystem.getSequence(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/music/these.mid")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Sequence doveMus6;

    static {
        try {
            doveMus6 = MidiSystem.getSequence(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/music/they.mid")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Sequence doveMus7;

    static {
        try {
            doveMus7 = MidiSystem.getSequence(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/music/disaster.mid")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Sequence doveMus8;

    static {
        try {
            doveMus8 = MidiSystem.getSequence(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/music/houston.mid")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // stuff
    public static boolean held = false;
    public static String direction;
    public static Sequencer sequencer;

    public static int level = 0;
    public static int score = 0;
    public static boolean showScore;
    public static boolean dead;
    public static boolean preloading = true;
    public static double damageRoll = 0;
    public static boolean playerTurn = true;
    public static boolean playerAttack = false;
    public static boolean enemyAttack = false;
    public static boolean damageView = false;
    public static int dmgTime = 0;
    public static boolean gaugeDamage = false;
    public static int dmgViewX = 0;
    public static int dmgViewY = 0;
    public static int gaugeY = 0;
    public static String dialogueMessage = "What will you do?";
    public static int battleSelect = 0;
    public static int cheatLevel = 0;
    public static float jackpot = 0;

    // fonts

    public Font boldFont = new Font("bold", Font.BOLD, 14);
    public Font regularFont = new Font("default", Font.PLAIN, 12);

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g.create();

        if (preloading) {
            g.setColor(Color.black);
            g.drawString("preloading..", 400, 400);
        }

        if (jumpscared && !dead) {
            g.drawImage(jumpscareBg, 0, 0, null);
        }

        if (level == 0 && !dead && !preloading) {
            g.drawImage(doveBg, 0, 0, null);

            g.setColor(Color.black);
            g.setFont(boldFont);
            g.drawString("D.O.V.E", 100, 100);
            g.setFont(regularFont);
            g.drawString("Degenerated", 100, 115);
            g.drawString("Ordinary", 100, 127);
            g.drawString("Verbose", 100, 138);
            g.drawString("Environment", 100, 149);
            g.drawString("Move your bird with arrow keys", 100, 310);
            g.drawString("to the dove above", 100, 324);

            g.drawImage(doveImg, xDove, yDove, wDove, hDove, this);
            g.drawImage(charImg, xPlayer, yPlayer, wPlayer, hPlayer, this);
        }

        if (level == 1 && !dead) {
            g.drawImage(coffeeBg, 0, 0, null);
            g.drawImage(potatoImg, xPotato, yPotato, wPotato, hPotato, this);
            g.drawImage(pigeImg, xPige, yPige, wPige, hPige, this);
            g.drawImage(charImg, xPlayer, yPlayer, wPlayer, hPlayer, this);
            g.drawString("Move slowly, don't wake bird", 100, 314);
            g.drawString("Get the potato", 100, 300);

            if (alerted) {
                g.setColor(Color.red);
                g.drawString("you woke the bird", 100, 328);
            }
        }

        if (level == 2 && !dead) {
            g.setColor(Color.CYAN);
            g.fillRect(0,0,640,480);
            g.setColor(Color.GREEN);
            g.fillRect(0,300,640,480);
            g.setColor(Color.GRAY);
            g.fillRect(0,320,640,480);

            g.drawImage(treeImg, xTree1, 300, 128, 128, this);
            g.drawImage(treeImg, xTree2, 300, 256, 256, this);
            g.drawImage(charImg, xPlayer, yPlayer, wPlayer, hPlayer, this);
            if (foodSpawned) g.drawImage(foodImg, xFood, yFood, wFood, hFood, this);
            if (windowCount > 1000) g.drawImage(windowImg, xWindow, yWindow, wWindow, hWindow, this);
        }

        if (level == 3 && !dead) {
            g.drawImage(roomBg, 0, 0, null);
            g.drawImage(winBrokImg, 200, 130, 100, 100, this);
            g.drawImage(mrunImg, xMan, yMan, wMan, hMan, this);
            g.drawImage(charImg, xPlayer, yPlayer, wPlayer, hPlayer, this);
        }

        if (level == 4 && !dead) {
            g.drawImage(roomBg2, 0, 0, null);
            g.drawImage(mrunImg, xMan, yMan, wMan, hMan, this);
            g.drawImage(charImg, xPlayer, yPlayer, wPlayer, hPlayer, this);
            g.setColor(Color.white);
            g.drawString("You HP: " + playerHP, 80, 339);
            g.drawString("golden running man HP: " + manHP, 420, 269);

            g.drawString(dialogueMessage, 24, 261);

            if (playerTurn) {
                if (battleSelect == 0) g.drawString("> Fight", 24, 276); else g.drawString("Fight", 24, 276);
                if (battleSelect == 1) g.drawString("> Magic", 24, 291); else g.drawString("Magic", 24, 291);
            }

            g.drawImage(gaugeImg, 100, 100,381, gaugeY, null);
            g.drawImage(mailImg, xMagic, yMagic, wMagic, hMagic, null);

            if (gaugeDamage) {
                g.drawImage(sliderImg, 90 + dmgTime, 100, null);
            }

            if (damageView) {
                g.setColor(Color.red);
                g.drawString("-" + damageRoll, dmgViewX, dmgViewY);
            }
        }

        if (level == 5 && !dead) {
            g.drawImage(bathBg, 0, 0, null);
            g.drawImage(charImg, xPlayer, yPlayer, wPlayer, hPlayer, this);
            if (gEquipped) {
                AffineTransform old = g2d.getTransform();
                g2d.rotate(Math.toRadians(angle), xPlayer, yPlayer);
                g2d.drawImage(waterGImg, xPlayer, yPlayer, wGun, hGun, this);
                g2d.rotate(-Math.toRadians(angle), xPlayer, yPlayer);
                g2d.setTransform(old);
            } else {
                g2d.drawImage(waterGImg, xGun, yGun, wGun, hGun, this);
            }

            if (!mug1Dead) g.drawImage(mugManImg, xMug1, yMug1, wMug1, hMug1, this);

            AffineTransform old = g2d.getTransform();
            g2d.rotate(Math.toRadians(aDrop), xOldD, yOldD);
            g2d.setColor(Color.cyan);
            g2d.fillRect(xDrop, yDrop, 43, 5);
            g2d.rotate(-Math.toRadians(aDrop), xOldD, yOldD);
            g2d.setTransform(old);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, jackpot));
            g2d.drawImage(sambagImg,0, 0, 640, 480, this);
        }

        if (dead) {
            g.drawImage(deadBg, 0, 0, null);
        }
    }
    class MMListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            angle = Math.toDegrees(Math.atan2(mouseY - yPlayer, mouseX - xPlayer));
            if (angle < 0) angle += 360;

            repaint();
        }
    }

    static class MListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Click");

            if (flappy) {
                flapVelo = 12;
            }

            if (gEquipped && !flappy && !pew) {
                System.out.println("Pew");
                pew = true;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    static class KBListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if (flappy) return;

            System.out.println("Key pressed");

            if (e.getKeyCode() == KeyEvent.VK_LEFT && !inBattle || e.getKeyCode() == KeyEvent.VK_A && !inBattle) {
                held = true;
                direction = "left";
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT && !inBattle || e.getKeyCode() == KeyEvent.VK_D && !inBattle) {
                held = true;
                direction = "right";
            }

            if (e.getKeyCode() == KeyEvent.VK_UP && !inBattle || e.getKeyCode() == KeyEvent.VK_W && !inBattle) {
                held = true;
                direction = "up";
            } else if (e.getKeyCode() == KeyEvent.VK_UP && inBattle && playerTurn || e.getKeyCode() == KeyEvent.VK_W && inBattle && playerTurn) {
                battleSelect -= 1;
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN && !inBattle || e.getKeyCode() == KeyEvent.VK_S && !inBattle) {
                held = true;
                direction = "down";
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && inBattle && playerTurn || e.getKeyCode() == KeyEvent.VK_S && inBattle && playerTurn) {
                battleSelect += 1;
            }

            if (e.getKeyCode() == KeyEvent.VK_ENTER && inBattle || e.getKeyCode() == KeyEvent.VK_X && inBattle) {
                if (battleSelect == 0 && playerTurn)  {
                    playerTurn = false;
                    gaugeDamage = true;
                    damageRoll = 0;
                    return;
                }

                if (battleSelect == 1 && playerTurn)  {
                    playerTurn = false;
                    playerAttack = true;
                    return;
                }

                if (gaugeDamage && dmgTime < 401) {
                    dmgTime = 0;
                    gaugeDamage = false;
                    playerAttack = true;
                    return;
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_F && !preloading) {
                sequencer.stop();
                try {
                    sequencer.setSequence(doveMus8);
                } catch (InvalidMidiDataException ex) {
                    throw new RuntimeException(ex);
                }
                sequencer.start();

                level = 5;
            }
        }

        public void keyReleased(KeyEvent e) {
            if (flappy || inBattle) return;

            System.out.println("Key released");

            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                held = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                held = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                held = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                held = false;
            }
        }
    }

    public Main() throws IOException {
        addKeyListener(new KBListener());
        addMouseListener(new MListener());
        addMouseMotionListener(new MMListener());
        setFocusable(true);
    }

    public static void soundEffects() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        gameOverSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/gameOver.wav"))));
        jumpScareSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/scare.wav"))));
        alertedSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/alerted.wav"))));
        hohoSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/hohoho.wav"))));
        crashSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/crash.wav"))));
        breakSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/glassbreak.wav"))));
        gobbleSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/gobble.wav"))));
        deadSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/dead.wav"))));
        goodbyeSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/goodbye.wav"))));
        clip = AudioSystem.getClip();
    }

    public static void gameOver(JFrame frame) throws LineUnavailableException, IOException, InterruptedException, UnsupportedAudioFileException, MidiUnavailableException, InvalidMidiDataException {
        inBattle = false;
        flappy = false;
        playerTurn = false;
        playerAttack = false;
        enemyAttack = false;
        windowCount = 0;
        foodSpawned = false;
        gEquipped = false;
        xTree1 = 600;
        xTree2 = 600;

        dead = true;
        frame.repaint();

        showScore = false;
        frame.setTitle("D.O.V.E | Game over!");

        clip.open(gameOverSnd);
        clip.start();

        JOptionPane.showMessageDialog(null, "game over \n Score: " + score, "DOVE", JOptionPane.PLAIN_MESSAGE);

        Thread.sleep(5000);
        clip.close();

        sequencer.open();
        sequencer.setSequence(doveMus);
        sequencer.setLoopCount(99999);
        sequencer.start();

        soundEffects();

        frame.setTitle("D.O.V.E");
        dead = false;
        score = 0;
        level = 0;

        xPlayer = 400;
        yPlayer = 250;
        playerHP = 100;

        xDove = 100;
        yDove = 200;
    }

    public static void jumpscare(JFrame frame) throws LineUnavailableException, IOException, InterruptedException, UnsupportedAudioFileException, MidiUnavailableException, InvalidMidiDataException {
        jumpscared = true;
        alerted = false;
        frame.repaint();

        xPlayer = 0;
        yPlayer = 0;

        xPige = 400;
        yPige = 300;

        steps = 0;

        screwed = false;
        level = -1;

        sequencer.stop();
        clip.close();

        clip.open(jumpScareSnd);
        clip.start();

        Thread.sleep(700);
        clip.close();

        gameOver(frame);
    }

    public static boolean coil(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        return x1 <= (x2 + w2) && (x1 + w1) >= x2 && y1 <= (y2 + h2) && (y1 + h1) >= y2;
    }

    public static void playHitSnd() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        int randHitSnd = (int) (Math.random() * 3 + 1);

        AudioInputStream hit1Snd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/hit1.wav"))));
        AudioInputStream hit2Snd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/hit2.wav"))));
        AudioInputStream hit3Snd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/hit3.wav"))));

        System.out.println("randHitSnd = " + randHitSnd);

        switch (randHitSnd) {
            case 1 -> {
                clip.close();
                clip.open(hit1Snd);
                clip.setFramePosition(0);
                clip.start();
            }

            case 2 -> {
                clip.close();
                clip.open(hit2Snd);
                clip.setFramePosition(0);
                clip.start();
            }

            case 3 -> {
                clip.close();
                clip.open(hit3Snd);
                clip.setFramePosition(0);
                clip.start();
            }
        }
    }

    public static void main(String[] args) throws IOException, MidiUnavailableException, InvalidMidiDataException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        JFrame frame = new GameFrame();
        soundEffects();

        sequencer = MidiSystem.getSequencer(true);
        sequencer.open();

        Thread.sleep(5000);

        preloading = false;

        sequencer.setSequence(doveMus);
        sequencer.setLoopCount(99999);
        sequencer.start();

        while (!preloading) {
            if (inBattle) {
                if (gaugeDamage && dmgTime <= 200) {
                    dmgTime += 5;
                    damageRoll += 0.5;
                } else if (gaugeDamage && dmgTime <= 400) {
                    dmgTime += 5;
                    damageRoll -= 0.5;
                } else if (gaugeDamage) {
                    dmgTime = 0;
                    damageRoll = 0;
                    gaugeDamage = false;
                    playerAttack = true;
                }

                if (playerAttack && battleSelect == 1) {
                    dialogueMessage = "You tried magic";

                    AudioInputStream woohSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/wooh.wav"))));
                    AudioInputStream fartSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/fart.wav"))));
                    AudioInputStream beepSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/beep.wav"))));

                    clip.close();
                    clip.open(woohSnd);
                    clip.start();

                    xMagic = xPlayer;
                    yMagic = yPlayer;

                    for (int i = 0; i < 23; i++) {
                        xMagic += i;
                        Thread.sleep(10);
                    }

                    damageRoll = (int) (Math.random() * 30 + 0);

                    if (damageRoll >= 10) {
                        clip.close();
                        clip.open(beepSnd);
                        clip.start();

                        dialogueMessage = "Man has been hit with mail for " + damageRoll + " damage";
                        damageView = true;

                        dmgViewX = xMan;
                        dmgViewY = yMan;

                        for (int i = 0; i < 8; i++) {
                            xMagic += 1;
                            yMagic -= 1;
                            wMagic += i;
                            hMagic += i;
                            xMan += i;
                            Thread.sleep(10);
                        }

                        for (int i = 0; i < 8; i++) {
                            xMagic += 1;
                            yMagic -= 1;
                            wMagic += i;
                            hMagic += i;
                            xMan -= i;
                            Thread.sleep(10);
                        }

                        for (int i = 0; i < 8; i++) {
                            xMagic += 1;
                            yMagic -= 1;
                            wMagic += i;
                            hMagic += i;
                            Thread.sleep(10);
                        }

                        wMagic = 32;
                        hMagic = 32;
                        xMagic = 999;
                        yMagic = 999;

                        for (int g = 0; g < 25; g++) {
                            dmgViewY -= 1;
                            Thread.sleep(10);
                        }

                        for (int g = 0; g < damageRoll; g++) {
                            manHP -= 1;
                            Thread.sleep(10);
                        }

                        dmgViewX = 0;
                        dmgViewY = 0;
                        damageView = false;
                    } else {
                        clip.close();
                        clip.open(fartSnd);
                        clip.start();

                        for (int i = 0; i < 16; i++) {
                            xMagic += 1;
                            yMagic -= 1;
                            wMagic += i;
                            hMagic += i;
                            Thread.sleep(10);
                        }

                        wMagic = 32;
                        hMagic = 32;
                        xMagic = 999;
                        yMagic = 999;

                        dialogueMessage = "That doesn't seem to affect Man that much.";
                    }

                    if (manHP <= 0) {
                        playerTurn = false;
                        playerAttack = false;
                        enemyAttack = false;

                        dialogueMessage = "Golden running man dies. You win.";

                        Thread.sleep(clip.getMicrosecondLength() / 1000);

                        clip.close();
                        clip.open(goodbyeSnd);
                        clip.start();

                        for (int i = 0; i < 23; i++) {
                            yMan += i;
                            Thread.sleep(10);
                        }

                        Thread.sleep(1000);

                        sequencer.stop();
                        clip.close();
                        inBattle = false;

                        sequencer.setSequence(doveMus8);
                        sequencer.start();
                        score += 30;
                        JOptionPane.showMessageDialog(null, "level 5", "DOVE", JOptionPane.PLAIN_MESSAGE);
                        xPlayer = 150;
                        yPlayer = 150;
                        level = 5;
                    } else {
                        Thread.sleep(2000);

                        enemyAttack = true;
                        playerAttack = false;
                    }
                }

                if (playerAttack && gaugeY <= 0 && battleSelect == 0) {
                    dialogueMessage = "You pecked";

                    for (int i = 0; i < 23; i++) {
                        xPlayer += i;
                        Thread.sleep(10);
                    }

                    for (int i = 0; i < 8; i++) {
                        xPlayer += 2;
                        xMan += i;
                        Thread.sleep(10);
                    }

                    for (int i = 0; i < 8; i++) {
                        xPlayer -= 2;
                        xMan -= i;
                        Thread.sleep(10);
                    }

                    if (damageRoll >= 10) {
                        if (damageRoll < manHP) {
                            playHitSnd();
                        } else {
                            clip.close();
                            clip.open(deadSnd);
                            clip.setFramePosition(0);
                            clip.start();
                        }

                        dialogueMessage = "Man has been pecked for " + damageRoll + " damage";
                        damageView = true;

                        dmgViewX = xMan;
                        dmgViewY = yMan;

                        for (int g = 0;g < 25;g++) {
                            dmgViewY -= 1;
                            Thread.sleep(10);
                        }

                        for (int g = 0;g < damageRoll;g++) {
                            manHP -= 1;
                            Thread.sleep(10);
                        }

                        dmgViewX = 0;
                        dmgViewY = 0;
                        damageView = false;
                    } else {
                        dialogueMessage = "Whoops missed!";
                    }

                    for (int i = 0; i < 23; i++) {
                        xPlayer -= i;
                        Thread.sleep(10);
                    }

                    if (manHP <= 0) {
                        playerTurn = false;
                        playerAttack = false;
                        enemyAttack = false;

                        dialogueMessage = "Golden running man dies. You win.";

                        Thread.sleep(clip.getMicrosecondLength() / 1000);

                        clip.close();
                        clip.open(goodbyeSnd);
                        clip.setFramePosition(0);
                        clip.start();

                        for (int i = 0; i < 23; i++) {
                            yMan += i;
                            Thread.sleep(10);
                        }

                        Thread.sleep(2000);

                        sequencer.stop();
                        clip.close();
                        inBattle = false;

                        sequencer.setSequence(doveMus8);
                        sequencer.start();

                        score += 30;
                        JOptionPane.showMessageDialog(null, "level 5", "DOVE", JOptionPane.PLAIN_MESSAGE);
                        xPlayer = 150;
                        yPlayer = 150;
                        level = 5;
                    } else {
                        Thread.sleep(1000);

                        enemyAttack = true;
                        playerAttack = false;
                    }
                }

                if (enemyAttack) {
                    dialogueMessage = "The golden running man hits";

                    for (int i = 0; i < 23; i++) {
                        xMan -= i;
                        Thread.sleep(10);
                    }

                    for (int i = 0; i < 8; i++) {
                        xMan -= 2;
                        xPlayer -= i;
                        Thread.sleep(10);
                    }

                    for (int i = 0; i < 8; i++) {
                        xMan += 2;
                        xPlayer += i;
                        Thread.sleep(10);
                    }

                    damageRoll = (int) (Math.random() * 20 + 0);

                    if (damageRoll >= 10) {
                        if (damageRoll < playerHP) {
                            playHitSnd();
                        } else {
                            clip.close();
                            clip.open(deadSnd);
                            clip.setFramePosition(0);
                            clip.start();
                        }

                        dialogueMessage = "You have been hit for " + damageRoll + " damage";
                        damageView = true;

                        dmgViewX = xPlayer;
                        dmgViewY = yPlayer;

                        for (int g = 0;g < 25;g++) {
                            dmgViewY -= 1;
                            Thread.sleep(10);
                        }

                        for (int g = 0;g < damageRoll;g++) {
                            playerHP -= 1;
                            Thread.sleep(10);
                        }

                        dmgViewX = 0;
                        dmgViewY = 0;
                        damageView = false;
                    } else {
                        dialogueMessage = "Whoops missed!";
                    }

                    for (int i = 0; i < 23; i++) {
                        xMan += i;
                        Thread.sleep(10);
                    }

                    if (playerHP <= 0) {
                        enemyAttack = false;
                        sequencer.stop();
                        clip.close();
                        gameOver(frame);
                    } else {
                        Thread.sleep(1000);

                        dialogueMessage = "What will you do?";
                        playerTurn = true;
                        enemyAttack = false;
                    }
                }
            }

            if (flappy) {
                if (yPlayer > 420) {
                    if (clip.isOpen()) clip.close();
                    sequencer.stop();
                    clip.open(crashSnd);
                    clip.start();

                    Thread.sleep(clip.getMicrosecondLength() / 1000);

                    clip.close();
                    gameOver(frame);
                }

                flapVelo -= 1;
                System.out.println(yPlayer);
                yPlayer -= flapVelo;
                xTree1 -= 1;
                xTree2 -= 2;
                if (xTree1 < 0) xTree1 = 600;
                if (xTree2 < 0) xTree2 = 600;
            }

            if (showScore) frame.setTitle("D.O.V.E | Score: " + score);

            if (windowCount > 1000) xWindow -= 3;

            if (level == 2) {
                windowCount += 1;
                if (!foodSpawned) {
                    xFood = 597;
                    foodTime += 1;
                } else {
                    xFood -= 3;
                }

                if (foodTime == 50 && !foodSpawned) {
                    yFood = (int)(Math.random() * 412 + 0);
                    foodTime = 0;
                    foodSpawned = true;
                }

                if (xFood < 0) foodSpawned = false;
            }

            if (steps > 10 && level == 1) alerted = true;

            if (alerted) {
                if (xPige > xPlayer) {
                    xPige -= 5;
                }

                if (xPige < xPlayer) {
                    xPige += 5;
                }

                if (yPige > yPlayer) {
                    yPige -= 5;
                }

                if (yPige < yPlayer) {
                    yPige += 5;
                }
            }

            if (alerted && !screwed && !dead) {
                sequencer.stop();
                clip.open(alertedSnd);
                clip.start();

                screwed = true;
            }

            if (held) {
                if (level == 1) steps += 1;

                switch (direction) {
                    case "left" -> xPlayer -= 2;
                    case "right" -> xPlayer += 2;
                    case "up" -> yPlayer -= 2;
                    case "down" -> yPlayer += 2;
                }
            }

            if (!held) steps = 0;

            // outside collision
            if (xPlayer > 597) xPlayer -= 2;
            if (xPlayer < 0) xPlayer += 2;

            if (yPlayer > 412) yPlayer -= 2;
            if (yPlayer < 0) yPlayer += 2;

            if (xMug1 > 597) xMug1 = -2;
            if (xMug1 < 0) xMug1 = 2;

            if (yMug1 > 412) yMug1 = -2;
            if (yMug1 < 0) yMug1 = 2;

            // object collision

            if (coil(xPlayer, yPlayer, wPlayer, hPlayer, xPotato, yPotato, wPotato, hPotato) && level == 1) {
                score += 10;

                xPlayer = 100;
                yPlayer = 100;

                sequencer.stop();
                sequencer.setSequence(doveMus3);
                sequencer.start();

                clip.open(hohoSnd);
                clip.start();

                JOptionPane.showMessageDialog(null, "level 2 (use the mouse and left click)", "DOVE", JOptionPane.PLAIN_MESSAGE);

                Thread.sleep(clip.getMicrosecondLength() / 1000);

                clip.close();

                held = false;
                flappy = true;

                level = 2;
            }

            if (coil(xPlayer, yPlayer, wPlayer, hPlayer, xWindow, yWindow, wWindow, hWindow) && level == 2) {
                if (clip.isOpen()) clip.close();
                score += 10;

                xPlayer = 220;
                yPlayer = 160;

                sequencer.stop();
                sequencer.setSequence(doveMus4);
                sequencer.start();

                clip.open(breakSnd);
                clip.start();

                Thread.sleep(clip.getMicrosecondLength() / 1000);

                JOptionPane.showMessageDialog(null, "level 3", "DOVE", JOptionPane.PLAIN_MESSAGE);

                clip.close();

                held = false;
                windowCount = 0;
                flappy = false;
                foodSpawned = false;

                level = 3;
            }

            if (coil(xPlayer, yPlayer, wPlayer, hPlayer, xMan, yMan, wMan, hMan) && level == 3 || cheatLevel == 4) {
                if (clip.isOpen()) clip.close();

                JOptionPane.showMessageDialog(null, "Running golden man: I see you broke into my house", "DOVE", JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null, "Running golden man: I will fight you", "DOVE", JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null, "level 4", "DOVE", JOptionPane.PLAIN_MESSAGE);

                sequencer.stop();

                xPlayer = 144;
                yPlayer = 369;
                sequencer.setSequence(doveMus7);
                sequencer.start();


                xMan = 350;
                yMan = 291;

                held = false;
                inBattle = true;
                playerTurn = true;
                cheatLevel = -1;
                level = 4;
            }

            // battle button Selection
            if (battleSelect < 0) battleSelect = 0;
            if (battleSelect > 1) battleSelect = 1;

            if (coil(xPlayer, yPlayer, wPlayer, hPlayer, xFood, yFood, wFood, hFood) && level == 2) {
                score += 5;
                foodSpawned = false;

                if (!clip.isOpen()) {
                    clip.open(gobbleSnd);
                    clip.setFramePosition(0);
                    clip.start();
                } else if (clip.isRunning()) {
                    clip.stop();
                    clip.setFramePosition(0);
                    clip.start();
                } else {
                    clip.setFramePosition(0);
                    clip.start();
                }
            }

            if (coil(xPlayer, yPlayer, wPlayer, hPlayer, xDove, yDove, wDove, hDove) && level == 0) {
                xDove = -999;
                yDove = -999;

                sequencer.stop();
                sequencer.setSequence(doveMus2);
                sequencer.start();

                JOptionPane.showMessageDialog(null, "level 1", "DOVE", JOptionPane.PLAIN_MESSAGE);
                showScore = true;

                held = false;

                xPlayer = 100;
                yPlayer = 100;

                level = 1;
            }

            if (coil(xPlayer, yPlayer, wPlayer, hPlayer, xPige, yPige, wPige, hPige) && level == 1) {
                jumpscare(frame);
            }

            if (coil(xPlayer, yPlayer, wPlayer, hPlayer, xGun, yGun, wGun, hGun) && level == 5 && !gEquipped) {
                gEquipped = true;
            }

            if (coil(xPlayer, yPlayer, wPlayer, hPlayer, xMug1, yMug1, wMug1, hMug1) && !mug1Dead && level == 5) {
                jackpot = 1;

                xPlayer = 10;
                yPlayer = 10;

                AudioInputStream jackpotSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/jackpot.wav"))));

                if (!clip.isRunning()) {
                    clip.close();
                    clip.open(jackpotSnd);
                    clip.setFramePosition(0);
                    clip.start();
                }
            }

            if (jackpot > 0) jackpot -= 0.01f;
            if (jackpot <= 0) jackpot = 0;

            if (gaugeDamage && gaugeY < 70) {
                gaugeY += 5;
            } else if (!gaugeDamage && gaugeY > 0) {
                gaugeY -= 5;
            }

            if (!pew && xDrop > 597 || !pew && xDrop < 0 || !pew && yDrop > 412 || !pew && yDrop < 0) {
                xDrop = 999;
                yDrop = 999;
            } else {
                xDrop += 5;
            }
            
            if (pew) {
                AudioInputStream squirtSnd = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/squirt.wav"))));

                if (!clip.isRunning()) {
                    clip.close();
                    clip.open(squirtSnd);
                    clip.setFramePosition(0);
                    clip.start();
                }

                xOldD = xPlayer;
                yOldD = yPlayer - 13;

                xDrop = xPlayer;
                yDrop = yPlayer;
                aDrop = angle;
                pew = false;
            }

            frame.repaint();

            if (flappy) {
                Thread.sleep(25);
            } else Thread.sleep(10);
        }
    }
}

