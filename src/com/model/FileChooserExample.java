package model;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooserExample {
    public static void main(String[] args) {
        // Create a file chooser dialog to select the input file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select input file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        int result = fileChooser.showOpenDialog((Component) null);
        if (result != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "No file selected");
            return;
        }

        // Read the input file and store the values in a 2-dimensional array
        String inputFileName = fileChooser.getSelectedFile().getPath();
        ArrayList<int[]> valuesList = new ArrayList<int[]>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length != 3) {
                    JOptionPane.showMessageDialog(null, "Invalid line: " + line);
                    continue;
                }
                int[] values = new int[3];
                for (int i = 0; i < 3; i++) {
                    values[i] = Integer.parseInt(tokens[i]);
                }
                valuesList.add(values);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
            return;
        }

        // Convert the list of values to a 2-dimensional array
        int numRows = valuesList.size();
        int numCols = 3;
        int[][] valuesArray = new int[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            valuesArray[i] = valuesList.get(i);
        }

        // Print the values as rows and columns
        System.out.println("Rows:");
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(valuesArray[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Columns:");
        for (int j = 0; j < numCols; j++) {
            for (int i = 0; i < numRows; i++) {
                System.out.print(valuesArray[i][j] + " ");
            }
            System.out.println();
        }
    }
}

