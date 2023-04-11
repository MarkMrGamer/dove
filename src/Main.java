import javax.imageio.ImageIO;
import javax.sound.midi.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main extends JComponent {
    // Westerbur doesn't know my programming skills on Java is terrible
    // TODO: make battle music
    // TODO: create a turn-based battle system in level 3

    // main character
    public static int xPlayer = 300;
    public static int yPlayer = 200;
    public static int wPlayer = 32;
    public static int hPlayer = 32;
    public static int playerHP = 100;
    public static boolean flappy = false;
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
    public BufferedImage jumpscareBg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/bjumpscar.png")));
    public BufferedImage deadBg = ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/dovedown.png")));
    public Image mrunImg = new ImageIcon(getClass().getResource("resources/images/manrun.gif")).getImage();

    // sounds
    public static Clip clip;
    public static AudioInputStream gameOverSnd;
    public static AudioInputStream jumpScareSnd;
    public static AudioInputStream alertedSnd;
    public static AudioInputStream hohoSnd;
    public static AudioInputStream crashSnd;
    public static AudioInputStream breakSnd;
    public static AudioInputStream gobbleSnd;

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

    // stuff
    public static boolean held = false;
    public static String direction;
    public static Sequencer sequencer;

    public static int level = 0;
    public static int score = 0;
    public static boolean showScore;
    public static boolean dead;
    public static boolean preloading = true;
    public static int damageRoll = 0;

    // fonts

    public Font boldFont = new Font("bold", Font.BOLD, 14);
    public Font regularFont = new Font("default", Font.PLAIN, 12);

    public void paint(Graphics g) {
        super.paint(g);

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

        if (dead) {
            g.drawImage(deadBg, 0, 0, null);
        }
    }
    static class MListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Click");
            flapVelo = 12;
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

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                held = true;
                direction = "left";
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                held = true;
                direction = "right";
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                held = true;
                direction = "up";
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                held = true;
                direction = "down";
            }
        }

        public void keyReleased(KeyEvent e) {
            if (flappy) return;

            System.out.println("Key released");

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                held = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                held = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                held = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                held = false;
            }
        }
    }

    public Main() throws IOException {
        addKeyListener(new KBListener());
        addMouseListener(new MListener());
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
        clip = AudioSystem.getClip();
    }

    public static void gameOver(JFrame frame) throws LineUnavailableException, IOException, InterruptedException, UnsupportedAudioFileException, MidiUnavailableException, InvalidMidiDataException {
        flappy = false;
        windowCount = 0;
        foodSpawned = false;
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

    public static void main(String[] args) throws IOException, MidiUnavailableException, InvalidMidiDataException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        JFrame frame = new GameFrame();
        soundEffects();

        Thread.sleep(5000);

        preloading = false;

        sequencer = MidiSystem.getSequencer(true);
        sequencer.open();

        sequencer.setSequence(doveMus);
        sequencer.setLoopCount(99999);
        sequencer.start();

        while (!preloading) {
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

            // object collision

            if (xPlayer <= (xPotato + wPotato) && (xPlayer + wPlayer) >= xPotato && yPlayer <= (yPotato + hPotato) && (yPlayer + hPlayer) >= yPotato && level == 1) {
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

            if (xPlayer <= (xWindow + wWindow) && (xPlayer + wPlayer) >= xWindow && yPlayer <= (yWindow + hWindow) && (yPlayer + hPlayer) >= yWindow && level == 2) {
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

            if (xPlayer <= (xMan + wMan) && (xPlayer + wPlayer) >= xMan && yPlayer <= (yMan + hMan) && (yPlayer + hPlayer) >= yMan && level == 3) {
                if (clip.isOpen()) clip.close();
                score += 10;

                JOptionPane.showMessageDialog(null, "Running golden man: I see you broke into my house", "DOVE", JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null, "Running golden man: I will fight you", "DOVE", JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null, "level 4", "DOVE", JOptionPane.PLAIN_MESSAGE);

                sequencer.stop();
                sequencer.setSequence(doveMus7);
                sequencer.start();

                xPlayer = 100;
                yPlayer = 100;
                
                level = 4;
            }

            if (xPlayer <= (xFood + wFood) && (xPlayer + wPlayer) >= xFood && yPlayer <= (yFood + hFood) && (yPlayer + hPlayer) >= yFood && level == 2) {
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

            if (xPlayer <= (xDove + wDove) && (xPlayer + wPlayer) >= xDove && yPlayer <= (yDove + hDove) && (yPlayer + hPlayer) >= yDove) {
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

            if (xPlayer <= (xPige + wPige) && (xPlayer + wPlayer) >= xPige && yPlayer <= (yPige + hPige) && (yPlayer + hPlayer) >= yPige && level == 1) {
                jumpscare(frame);
            }

            frame.repaint();

            if (flappy) {
                Thread.sleep(25);
            } else Thread.sleep(10);
        }
    }
}

