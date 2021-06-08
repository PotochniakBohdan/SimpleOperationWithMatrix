package kursova;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ElementaryOperationOnMatrix implements ActionListener {
    private static int columns, rows;
    private static double myMatrix[][];
    private static double tempMatrix[][];
    private static JTextField inputField[][];
    private static int result;
    private static JButton minusB, plusB,
            multiplyB, showMatrix, newMatrix;
    private static JPanel choosePanel[] = new JPanel[7];
    private static int lastCol, lastRow;


    ElementaryOperationOnMatrix() {
        columns = rows = 0;
        myMatrix = new double[0][0];
        ChooseOperation();
    }


    private static void getSize() {
        JTextField rowField = new JTextField(3);
        JTextField columnField = new JTextField(3);

        JPanel choosePanel[] = new JPanel[2];
        choosePanel[0] = new JPanel();
        choosePanel[1] = new JPanel();
        choosePanel[0].add(new JLabel("Введіть розміри матриці"));
        choosePanel[1].add(new JLabel("Рядки:"));
        choosePanel[1].add(rowField);
        choosePanel[1].add(Box.createHorizontalStrut(15));
        choosePanel[1].add(new JLabel("Стовпці:"));
        choosePanel[1].add(columnField);

        result = JOptionPane.showConfirmDialog(null, choosePanel,
                null, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        lastCol = columns;
        lastRow = rows;

        if (result == 0) {

            if (columnField.getText().equals(""))
                columns = 0;
            else {
                if (isInt(columnField.getText())) {
                    columns = Integer.parseInt(columnField.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Неправильні розміри");
                    columns = lastCol;
                    rows = lastRow;
                    return;
                }

                if (isInt(rowField.getText())) {
                    rows = Integer.parseInt(rowField.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Неправильні розміри");
                    columns = lastCol;
                    rows = lastRow;
                    return;
                }

            }
            if (columns < 1 || rows < 1) {
                JOptionPane.showConfirmDialog(null, "Ви ввели неправильні розміри",
                        "Помилка", JOptionPane.PLAIN_MESSAGE);
                columns = lastCol;
                rows = lastRow;

            } else {
                tempMatrix = myMatrix;
                myMatrix = new double[rows][columns];
                if (!setElements(myMatrix, "Заповніть свою нову матрицю")) {

                    myMatrix = tempMatrix;
                }
            }
        } else if (result == 1) {
            columns = lastCol;
            rows = lastRow;
        }
    }

    private static boolean setElements(double matrix[][], String title) {
        int temp, temp1;
        String tempString;

        JPanel choosePanel[] = new JPanel[rows + 2];
        choosePanel[0] = new JPanel();
        choosePanel[0].add(new Label(title));
        choosePanel[choosePanel.length - 1] = new JPanel();
        inputField = new JTextField[matrix.length][matrix[0].length];

        for (temp = 1; temp <= matrix.length; temp++) {
            choosePanel[temp] = new JPanel();

            for (temp1 = 0; temp1 < matrix[0].length; temp1++) {
                inputField[temp - 1][temp1] = new JTextField(3);
                choosePanel[temp].add(inputField[temp - 1][temp1]);

                if (temp1 < matrix[0].length - 1) {
                    choosePanel[temp].add(Box.createHorizontalStrut(15));
                }
            }
        }

        result = JOptionPane.showConfirmDialog(null, choosePanel,
                null, JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == 0) {
            checkTextField(inputField);
            for (temp = 0; temp < matrix.length; temp++) {
                for (temp1 = 0; temp1 < matrix[0].length; temp1++) {
                    tempString = inputField[temp][temp1].getText();


                    if (isDouble(tempString)) {
                        matrix[temp][temp1] = Double.parseDouble(inputField[temp][temp1].getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Ви ввели неправильні елементи");

                        columns = lastCol;
                        rows = lastRow;

                        return false;
                    }
                }
            }
            return true;
        } else
            return false;


    }

    private static void checkTextField(JTextField field[][]) {
        for (int temp = 0; temp < field.length; temp++) {
            for (int temp1 = 0; temp1 < field[0].length; temp1++) {
                if (field[temp][temp1].getText().equals(""))
                    field[temp][temp1].setText("0");
            }
        }
    }

    private void ChooseOperation() {
        int temp;


        for (temp = 0; temp < choosePanel.length; temp++) {
            choosePanel[temp] = new JPanel();
        }

        choosePanel[1].add(Box.createHorizontalStrut(15));
        choosePanel[6].add(Box.createHorizontalStrut(15));


        showMatrix = new JButton("Показати матрицю");
        showMatrix.setPreferredSize(new Dimension(175, 35));
        showMatrix.addActionListener(this);
        choosePanel[1].add(showMatrix);

        plusB = new JButton("Додавання матриці");
        plusB.setPreferredSize(new Dimension(175, 35));
        plusB.addActionListener(this);
        choosePanel[2].add(plusB);

        minusB = new JButton("Віднімання матриці");
        minusB.setPreferredSize(new Dimension(175, 35));
        minusB.addActionListener(this);
        choosePanel[2].add(minusB);

        multiplyB = new JButton("Множення на матрицю");
        multiplyB.setPreferredSize(new Dimension(175, 35));
        multiplyB.addActionListener(this);
        choosePanel[3].add(multiplyB);


        newMatrix = new JButton("Додати матрицю");
        newMatrix.setPreferredSize(new Dimension(175, 35));
        newMatrix.addActionListener(this);
        choosePanel[0].add(newMatrix);

        JOptionPane.showConfirmDialog(null, choosePanel, null,
                JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showMatrix) {
            showMatrix(myMatrix, "");
        }
        if (e.getSource() == plusB) {
            matrixPlusMatrix();
        } else if (e.getSource() == minusB) {
            matrixMinusMatrix();
        } else if (e.getSource() == multiplyB) {
            multiplyByMatrix();
        } else if (e.getSource() == newMatrix) {
            newMatrix();
        }
    }

    private static void showMatrix(double[][] matrix, String title) {
        int temp, temp1;

        JPanel choosePanel[] = new JPanel[matrix.length + 1];
        choosePanel[0] = new JPanel();
        choosePanel[0].add(new JLabel(title));

        for (temp = 1; temp <= matrix.length; temp++) {
            choosePanel[temp] = new JPanel();

            for (temp1 = 0; temp1 < matrix[0].length; temp1++) {
                if (matrix[temp - 1][temp1] == -0) {
                    matrix[temp - 1][temp1] = 0;
                }
                choosePanel[temp].add(new JLabel(String.format("%.2f", matrix[temp - 1][temp1])));

                if (temp1 < matrix[0].length - 1) {
                    choosePanel[temp].add(Box.createHorizontalStrut(15)); // a spacer
                }
            }
        }
        if (columns == 0 || rows == 0) {
            JOptionPane.showMessageDialog(null, "Ви не ввели жодної матриці");
        } else {
            JOptionPane.showMessageDialog(null, choosePanel, null,
                    JOptionPane.PLAIN_MESSAGE, null);
        }
    }

    private static void matrixPlusMatrix() {
        if (myMatrix.length < 1) {
            JOptionPane.showMessageDialog(null, "Ви не ввели жодної матриці");
        } else {
            double m2[][] = new double[rows][columns];
            double sum[][] = new double[rows][columns];

            if (setElements(m2, "Створіть другу матрицю")) {

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        sum[i][j] = myMatrix[i][j] + m2[i][j];
                    }
                }
                showMatrix(sum, "Результат");
            }
        }
    }

    private static void matrixMinusMatrix() {
        if (myMatrix.length < 1) {
            JOptionPane.showMessageDialog(null, "Ви не ввели жодної матриці");
        } else {
            double m2[][] = new double[rows][columns];
            double sub[][] = new double[rows][columns];
            double temp[][] = new double[rows][columns];

            if (setElements(m2, "Створіть другу матрицю")) {

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        temp[i][j] = (-1 * m2[i][j]);
                        sub[i][j] = myMatrix[i][j] + temp[i][j];
                    }
                }
                showMatrix(sub, "Результат");
            }
        }
    }

    private static void multiplyByMatrix() {
        JTextField wField = new JTextField(5);
        int col2 = 0;
        double m2[][], results[][];
        int sum;

        if (myMatrix.length < 1) {
            JOptionPane.showMessageDialog(null, "Ви не ввели жодної матриці");
        } else {

            JPanel choosePanel[] = new JPanel[2];
            choosePanel[0] = new JPanel();
            choosePanel[1] = new JPanel();

            choosePanel[0].add(new JLabel("Введіть розмір"));

            choosePanel[1].add(new JLabel("Рядки:"));
            choosePanel[1].add(new JLabel("" + columns));
            choosePanel[1].add(Box.createHorizontalStrut(15)); // a spacer
            choosePanel[1].add(new JLabel("Стовпці:"));
            choosePanel[1].add(wField);

            result = JOptionPane.showConfirmDialog(null, choosePanel,
                    null, JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == 0) {
                if (wField.getText().equals("")) {
                    col2 = 0;
                } else {
                    if (isInt(wField.getText())) {
                        col2 = Integer.parseInt(wField.getText());

                    }
                }

                m2 = new double[columns][col2];
                results = new double[rows][col2];
                if (setElements(m2, "Заповніть матрицю для множення")) {

                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < col2; j++) {
                            sum = 0;
                            for (int k = 0; k < columns; k++) {
                                sum += myMatrix[i][k] * m2[k][j];
                            }

                            results[i][j] = sum;

                        }
                    }

                    showMatrix(results, "Результат");
                }
            } else
                return;
        }
    }

    private static boolean isInt(String str) {
        int temp;
        if (str.length() == '0')
            return false;

        for (temp = 0; temp < str.length(); temp++) {
            if (str.charAt(temp) != '+' && str.charAt(temp) != '-'
                    && !Character.isDigit(str.charAt(temp))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDouble(String str) {
        int temp;
        if (str.length() == '0')
            return false;
        for (temp = 0; temp < str.length(); temp++) {
            if (str.charAt(temp) != '+' && str.charAt(temp) != '-'
                    && str.charAt(temp) != '.'
                    && !Character.isDigit(str.charAt(temp))
            ) {
                return false;
            }
        }
        return true;
    }

    private static void newMatrix() {
        getSize();
    }

    public static void main(String[] args) {
        ElementaryOperationOnMatrix m1 = new ElementaryOperationOnMatrix();
    }
}


