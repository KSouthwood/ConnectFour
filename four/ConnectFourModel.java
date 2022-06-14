package four;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class ConnectFourModel {
    private int     highRow  = 6;
    private int     plays    = 0;
    private String  player   = "X";
    private boolean gameOver = false;

    private static final Pattern WIN_STRING = Pattern.compile("(XXXX|OOOO)");

    private JButton[][] buttonArray;

    void setButtonMap(JButton[][] array) {
        this.buttonArray = array;
    }

    void playCell(final JButton clickedButton) {
        if (gameOver) {
            return;
        }

        int column = clickedButton.getName().charAt(6) - 'A';

        for (int row = buttonArray.length - 1; row >= 0; row--) {
            var button = buttonArray[row][column];
            if (button.getText().equals(" ")) {
                button.setText(player);
                player = player.equals("X") ? "O" : "X";
                highRow = Math.min(row, highRow);
                plays++;
                checkForWin();
                return;
            }

        }
    }

    void resetBoard() {
        for (JButton[] jButtons : buttonArray) {
            for (JButton button : jButtons) {
                button.setText(" ");
                button.setBackground(Color.LIGHT_GRAY);
            }
        }

        player = "X";
        plays = 0;
        highRow = 6;
        gameOver = false;
    }

    private void checkForWin() {
        if (plays < 7) {
            return;
        }

        if (checkHorizontal()) {
            return;
        }

        // no point in checking for vertical/diagonal wins if no column has more than four markers in it
        // highRow would be at 2 if that's the case
        if (highRow < 3) {
            if (checkVertical()) {
                return;
            }

            checkDiagonals();
        }
    }

    private boolean checkHorizontal() {
        for (int row = buttonArray.length - 1; row >= highRow; row--) {
            StringBuilder rowString = new StringBuilder();
            for (int col = 0; col < buttonArray[row].length; col++) {
                rowString.append(buttonArray[row][col].getText());
            }
            var win = WIN_STRING.matcher(rowString.toString());
            if (win.find()) {
                markWinningLine(row, win.start(), WinDirection.HORIZONTAL);
                return true;
            }
        }
        return false;
    }

    private boolean checkVertical() {
        for (int col = 0; col < buttonArray[0].length; col++) {
            StringBuilder colString = new StringBuilder();
            for (int row = 0; row < buttonArray.length; row++) {
                colString.append(buttonArray[row][col].getText());
            }
            var win = WIN_STRING.matcher(colString.toString());
            if (win.find()) {
                markWinningLine(win.start(), col, WinDirection.VERTICAL);
                return true;
            }
        }
        return false;
    }

    private void checkDiagonals() {
        for (int row = buttonArray.length - 1; row >= 3; row--) {
            for (int col = 0; col < 4; col++) {
                if (checkDiagonal(row, col, WinDirection.DIAGONAL_R)) {
                    return;
                }
                // if we're not in the bottom row, no need to go past the first column as it'll have been checked
                // already
                if (row < 5) {
                    break;
                }
            }

            for (int col = buttonArray[0].length - 1; col > 2; col--) {
                if (checkDiagonal(row, col, WinDirection.DIAGONAL_L)) {
                    return;
                }

                if (row < 5) {
                    break;
                }
            }
        }

    }

    private boolean checkDiagonal(final int rowStart, final int colStart, WinDirection direction) {
        int row = rowStart;
        int col = colStart;
        StringBuilder diagonal = new StringBuilder();
        while (validRowCol(row, col)) {
            diagonal.append(buttonArray[row][col].getText());
            row += direction.rowDir;
            col += direction.colDir;
        }
        var win = WIN_STRING.matcher(diagonal.toString());
        if (win.find()) {
            markWinningLine(rowStart - win.start(), colStart + win.start(), direction);
            return true;
        }
        return false;
    }

    private boolean validRowCol(final int row, final int col) {
        return (row >= 0 && row < buttonArray.length &&
                col >= 0 && col < buttonArray[0].length);
    }

    private void markWinningLine(int row, int col, WinDirection direction) {
        for (int cell = 0; cell < 4; cell++) {
            buttonArray[row][col].setBackground(Color.YELLOW);
            row += direction.rowDir;
            col += direction.colDir;
        }

        gameOver = true;
    }

    enum WinDirection {
        HORIZONTAL(0, 1),
        VERTICAL(1, 0),
        DIAGONAL_R(-1, 1),
        DIAGONAL_L(-1, -1);

        private final int rowDir;
        private final int colDir;

        WinDirection(int rowDir, int colDir) {
            this.rowDir = rowDir;
            this.colDir = colDir;
        }
    }

}
