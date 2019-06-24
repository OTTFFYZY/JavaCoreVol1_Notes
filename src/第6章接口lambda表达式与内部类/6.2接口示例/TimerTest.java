import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;// conflict with java.util.Timer

public class TimerTest {
    public static void main(String[] args) {
        ActionListener listener = new TimePrinter();

        Timer t = new Timer(1000, listener); // 1000 ms interval
        t.start();
        // start() start Timer
        // end() end Timer
        JOptionPane.showMessageDialog(null, "Quit program?");
        // block
        System.exit(0);
    }
}
class TimePrinter implements ActionListener {
    public void actionPerformed(ActionEvent event) {
        System.out.println("At the tone, the time is " + new Date());
        //Toolkit.getDefaultToolkit().beep();
        // beep sound
    }
}