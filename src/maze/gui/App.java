package maze.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class App {
    public static void main(String[] args) {
        initLaF();
        final View view = new View();
        final Controller controller = new Controller(view);
        view.addMouseListener(controller);
        JFrame frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(BorderLayout.CENTER, view);
        frame.getContentPane().add(BorderLayout.SOUTH, controls(controller));
        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel controls(Controller controller) {
        final JPanel ctrl = new JPanel();
        ctrl.setLayout(new FlowLayout(FlowLayout.CENTER));
        final JTextField height = new JTextField(4);
        final JTextField width = new JTextField(4);
        height.setText("10");
        width.setText("10");
        final JButton create = new JButton("New maze");
        create.addActionListener(e -> controller.createMaze(height.getText(), width.getText()));
        final JButton open = new JButton("Open dead ends");
        open.addActionListener(e -> controller.openDeadEnds());
        ctrl.add(new JLabel("Height:"));
        ctrl.add(height);
        ctrl.add(new JLabel("Width:"));
        ctrl.add(width);
        ctrl.add(create);
        ctrl.add(open);
        return ctrl;
    }

    private static void initLaF() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
            System.err.println("Can't set look and feel");
            System.exit(2);
        }
    }
}
