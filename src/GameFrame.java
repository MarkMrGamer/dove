import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;

public class GameFrame extends JFrame implements WindowListener {
    public GameFrame() throws IOException {
        addWindowListener(this);

        setIconImage(ImageIO.read(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/images/bird.png"))));
        setTitle("D.O.V.E");
        add(new Main());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {
        if (Main.sequencer != null) Main.sequencer.close();

        BufferedInputStream goodbye = new BufferedInputStream(Objects.requireNonNull(GameFrame.class.getResourceAsStream("resources/sounds/Goodbye3.wav")));
        AudioInputStream audioInputStream;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(goodbye);
        } catch (UnsupportedAudioFileException | IOException ex) {
            throw new RuntimeException(ex);
        }

        Clip clip;

        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }

        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException ex) {
            throw new RuntimeException(ex);
        }

        clip.start();

        try {
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
