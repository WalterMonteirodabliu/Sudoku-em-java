import java.util.Random;

public class Sudoku {
    private int[][] board;
    private static final int SIZE = 9;

    public Sudoku() {
        board = new int[SIZE][SIZE];
        generateSudoku();
    }

    private void generateSudoku() {
        fillDiagonal();
        fillRemaining(0, 3);
        removeNumbers(40);
    }

    private void fillDiagonal() {
        for (int i = 0; i < SIZE; i += 3) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int col) {
        Random rand = new Random();
        boolean[] used = new boolean[SIZE + 1];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num;
                do {
                    num = rand.nextInt(SIZE) + 1;
                } while (used[num]);
                used[num] = true;
                board[row + i][col + j] = num;
            }
        }
    }

    private boolean fillRemaining(int row, int col) {
        if (row == SIZE - 1 && col == SIZE)
            return true;
        if (col == SIZE) {
            row++;
            col = 0;
        }
        if (board[row][col] != 0)
            return fillRemaining(row, col + 1);

        for (int num = 1; num <= SIZE; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                if (fillRemaining(row, col + 1))
                    return true;
                board[row][col] = 0;
            }
        }
        return false;
    }

    private void removeNumbers(int count) {
        Random rand = new Random();
        while (count > 0) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                count--;
            }
        }
    }

    public boolean isValid(int row, int col, int num) {
        for (int x = 0; x < SIZE; x++) {
            if (board[row][x] == num || board[x][col] == num)
                return false;
        }
        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num)
                    return false;
            }
        }
        return true;
    }

    public boolean setNumber(int row, int col, int num) {
        if (isValid(row, col, num)) {
            board[row][col] = num;
            return true;
        }
        return false;
    }

    public boolean isComplete() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0 || !isValid(i, j, board[i][j]))
                    return false;
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }
}
