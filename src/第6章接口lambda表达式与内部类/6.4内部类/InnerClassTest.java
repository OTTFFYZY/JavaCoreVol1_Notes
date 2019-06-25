import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class InnerClassTest {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(1000, false);
        clock.start();
        JOptionPane.showMessageDialog(null, "Quit Program?");
        System.exit(0);
    }
}
class TalkingClock {
    private int interval;
    private boolean beep;
    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }
    public void start() {
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }
    
    private class TimePrinter implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.out.println("Time " + new Date());
            if(beep) Toolkit.getDefaultToolkit().beep();
        }
    }
}