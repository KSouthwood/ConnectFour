package four;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class ConnectFourController implements ActionListener {
    private String player = "X";
    private Map<String, ArrayList<JButton>> buttonMap;

    @Override
    public void actionPerformed(ActionEvent e) {
        playCell((JButton) e.getSource());
    }

    void setButtonMap(Map<String, ArrayList<JButton>> map) {
        this.buttonMap = map;
    }

    void playCell(final JButton clickedButton) {
        var columnName = clickedButton.getName().substring(6, 7);
        var columnList = buttonMap.get(columnName);

        for (int index = columnList.size() - 1; index >= 0; index--) {
            var button = columnList.get(index);
            if (button.getText().equals(" ")) {
                button.setText(player);
                player = player.equals("X") ? "O" : "X";
                return;
            }
        }
    }
}
