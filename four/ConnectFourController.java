package four;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourController implements ActionListener {
    private String player = "X";

    @Override
    public void actionPerformed(ActionEvent e) {
        markCell((JButton) e.getSource());
    }

    void markCell(JButton button) {
        if (button.getText().equals(" ")) {
            button.setText(player);
            player = player.equals("X") ? "O" : "X";
        }
    }
}
