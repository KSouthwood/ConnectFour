package four;

import javax.swing.*;
import java.awt.*;

public class ConnectFourModel {
    private int highRow = 0;
    private int plays = 0;
    private String player = "X";

    private JButton[][] buttonArray;

    void setButtonMap(JButton[][] array) {
        this.buttonArray = array;
    }

    void playCell(final JButton clickedButton) {
        int column = clickedButton.getName().charAt(6) - 'A';

        for (int row = buttonArray[column].length - 1; row >= 0; row--) {
            var button = buttonArray[column][row];
            if (button.getText().equals(" ")) {
                button.setText(player);
                player = player.equals("X") ? "O" : "X";
                highRow = Math.max(row, highRow);
                plays++;
                return;
            }

        }
    }

    void resetBoard() {
        for (JButton[] jButtons : buttonArray) {
            for (JButton button : jButtons) {
                button.setText(" ");
                button.setBackground(Color.LIGHT_GRAY);
                button.setEnabled(true);
            }
        }

        player = "X";
        plays = 0;
    }

    void checkForWin() {
        if (plays < 7) {
            return;
        }

        checkHorizontal();
    }

    private void checkHorizontal() {

    }
}
