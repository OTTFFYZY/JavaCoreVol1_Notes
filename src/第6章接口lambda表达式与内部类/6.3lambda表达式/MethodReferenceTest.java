import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class MethodReferenceTest {
    public static void main(String[] args) {
        TimedGreeter obj = new TimedGreeter();
        obj.greet(); 
    }
}
class Greeter {
    public void greet(ActionEvent e) {
        System.out.println("Hello, world!");
    }
}
class TimedGreeter extends Greeter {
    public void greet() {
        Timer t = new Timer(1000, super::greet);
        t.start();
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}