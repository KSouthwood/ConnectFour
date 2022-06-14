package four;

import javax.swing.*;

public class ConnectFourController {
    private static final ConnectFourModel model = new ConnectFourModel();

    void setButtonMap(JButton[][] array) {
        model.setButtonMap(array);
    }

    void playCell(final JButton cell) {
        model.playCell(cell);
    }

    void resetBoard() {
        model.resetBoard();
    }
}
