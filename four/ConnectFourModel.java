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

    /**
     * Play in the column of the clicked cell
     *
     * Get the column of the clicked cell, then play in the first available cell starting at the bottom. If the column
     * is full, don't play at all and the player has to go again.
     *
     * @param clickedButton cell that was clicked by the player
     */
    void playCell(final JButton clickedButton) {
        // ignore the click if the game is over
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

    /**
     * Reset the game.
     *
     * Changes all the buttons back to default text and color. Also set the game control variables back to initial
     * values.
     */
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

    /**
     * Check the board for a four in a row.
     */
    private void checkForWin() {
        if (plays < 7) {
            return;
        }

        //TODO: change the method from a boolean to a void and just check the gameOver variable after??
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

    /**
     * Check for a win in a row.
     *
     * Builds a string from each row that contains at least one marker and checks if it has a four in a row.
     *
     * @return boolean
     */
    private boolean checkHorizontal() {
        // start checking from the bottom row
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

    /**
     * Check for a win in a column.
     *
     * Builds a string from each column and checks if it has a four in a row. We only start checking columns once one
     * column has enough markers in it. (highRow will be 0, 1, or 2.)
     *
     * @return boolean
     */
    private boolean checkVertical() {
        for (int col = 0; col < buttonArray[0].length; col++) {
            StringBuilder colString = new StringBuilder();
            //noinspection ForLoopReplaceableByForEach
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

    /**
     * Check for a diagonal win.
     *
     * Builds a string from each possible diagonal and checks for four in a row. Skips double-checking diagonals by
     * looking at the row that we're starting in and breaking the column loops if it's not the bottom row.
     */
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

    /**
     * @param rowStart row of cell to start with
     * @param colStart column of cell to start with
     * @param direction the direction to move in from the starting cell
     *
     * @return true if we found a four in a row
     */
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

    /**
     * Ensure the row and column are within bounds
     * @param row value of row to check
     * @param col value of column to check
     *
     * @return true if both row and col are within bounds
     */
    private boolean validRowCol(final int row, final int col) {
        return (row >= 0 && row < buttonArray.length &&
                col >= 0 && col < buttonArray[0].length);
    }

    /**
     * Change the background of the winning cells.
     *
     * @param row of starting cell
     * @param col of starting cell
     * @param direction to move in
     */
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
