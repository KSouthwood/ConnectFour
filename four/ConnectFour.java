package four;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConnectFour extends JFrame {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int GAP  = 2; // gap between the components on the GridLayout
    private static final int SIZE = 60; // size for the JButton's

    private static final int PANEL_HEIGHT = 50;

    private static final int WIN_WIDTH  = (SIZE * COLS) + (GAP * (COLS - 1));
    private static final int WIN_HEIGHT = (SIZE * ROWS) + (GAP * (ROWS - 1)) + PANEL_HEIGHT;

    private final ConnectFourController controller = new ConnectFourController();

    public ConnectFour() {
        initWindow();
        addComponents();
        setVisible(true);
    }

    private void initWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIN_WIDTH, WIN_HEIGHT);
        setLayout(new GridLayout(ROWS + 1, COLS, GAP, GAP));
        setTitle("Connect Four");
        setLocationRelativeTo(null);
    }

    private void addComponents() {
        JButton[][] buttonArray = new JButton[ROWS][COLS];

        List<String> rowNames = List.of("6", "5", "4", "3", "2", "1");
        List<String> colNames = List.of("A", "B", "C", "D", "E", "F", "G");

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                var button = createButton(colNames.get(col) + rowNames.get(row));
                this.add(button);
                buttonArray[row][col] = button;
            }
        }

        controller.setButtonMap(buttonArray);
        controller.resetBoard();

        this.add(createPanel());
    }

    private JButton createButton(final String name) {
        var button = new JButton(" ");
        button.setName("Button" + name);
        button.addActionListener(e -> controller.playCell((JButton) e.getSource()));
        button.setFocusPainted(false);
        button.setForeground(Color.BLACK);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        return button;
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setSize(new Dimension(WIN_WIDTH, PANEL_HEIGHT));

        JButton reset = new JButton("Reset");
        reset.setName("ButtonReset");
        reset.setEnabled(true);
        reset.addActionListener(e -> controller.resetBoard());

        panel.add(reset);
        return panel;
    }
}
