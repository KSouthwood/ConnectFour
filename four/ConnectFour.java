package four;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectFour extends JFrame {
    private static final int WIDTH  = 420;
    private static final int HEIGHT = 360;

    private final ConnectFourController controller = new ConnectFourController();

    public ConnectFour() {
        initWindow();
        addComponents();
        setVisible(true);
    }

    private void initWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(6, 7, 2, 2));
        setTitle("Connect Four");
        setLocationRelativeTo(null);
    }

    private void addComponents() {
        List<String> rowNames = List.of("6", "5", "4", "3", "2", "1");
        List<String> colNames = List.of("A", "B", "C", "D", "E", "F", "G");
        Map<String, ArrayList<JButton>> buttonGrid = new HashMap<>();

        for (var row : rowNames) {
            for (var col : colNames) {
                var button = createButton(col + row);
                this.add(button);
                if (!buttonGrid.containsKey(col)) {
                    buttonGrid.put(col, new ArrayList<>());
                }
                buttonGrid.get(col).add(button);
            }
        }

        controller.setButtonMap(buttonGrid);
    }

    private JButton createButton(final String name) {
        var button = new JButton(" ");
        button.setName("Button" + name);
        button.setFocusPainted(false);
        button.addActionListener(controller);
        return button;
    }
}
