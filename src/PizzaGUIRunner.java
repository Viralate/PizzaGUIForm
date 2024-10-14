import javax.swing.*;

public class PizzaGUIRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PizzaGUIFrame frame = new PizzaGUIFrame();
                frame.setVisible(true);
            }
        });
    }
}
