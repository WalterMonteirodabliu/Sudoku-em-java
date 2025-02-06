import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGUI extends JFrame {
    private JTextField[][] cells;
    private Sudoku sudoku;

    public SudokuGUI() {
        sudoku = new Sudoku();
        setTitle("Sudoku");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(9, 9));
        cells = new JTextField[9][9];

        int[][] board = sudoku.getBoard();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                if (board[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(Color.LIGHT_GRAY);
                }
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                boardPanel.add(cells[row][col]);
            }
        }

        JButton validateButton = new JButton("Validar Sudoku");
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSudoku();
            }
        });

        add(boardPanel, BorderLayout.CENTER);
        add(validateButton, BorderLayout.SOUTH);
    }

    private void checkSudoku() {
        int[][] userBoard = new int[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String text = cells[row][col].getText();
                if (!text.isEmpty()) {
                    try {
                        int num = Integer.parseInt(text);
                        if (num < 1 || num > 9) {
                            JOptionPane.showMessageDialog(this, "Número inválido na posição (" + (row + 1) + "," + (col + 1) + ")");
                            return;
                        }
                        userBoard[row][col] = num;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Entrada inválida em (" + (row + 1) + "," + (col + 1) + ")");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Ainda há células vazias.");
                    return;
                }
            }
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!sudoku.isValid(row, col, userBoard[row][col])) {
                    JOptionPane.showMessageDialog(this, "Erro no Sudoku! Verifique suas respostas.");
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Parabéns! Você completou o Sudoku corretamente!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuGUI gui = new SudokuGUI();
            gui.setVisible(true);
        });
    }
}
