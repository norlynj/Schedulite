package view;


import model.*;
import model.Process;
import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;
import view.component.Label;

import java.util.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class InputPanel extends Panel {
    private final ImageButton musicOnButton, musicOffButton, homeButton, runButton, resetButton, processNumMinusButton, processNumPlusButton, timeQuantumMinusButton, timeQuantumPlusButton, removeButton, randomizeButton;
    private JTextField processNum, timeQuantum;
    private JComboBox algorithmChoice;
    private JScrollPane tablePane;
    private JTable table;
    private CustomTableModel model;
    private JLabel algoLabel;



    public InputPanel() {

        super("bg/input-panel.png");

        model = new CustomTableModel(new String[]{"Process ID", "Burst time", "Arrival time", "Priority Number"}, 3);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Montserrat", Font.PLAIN, 15));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Set the renderer for each column in the table
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set the font of the JTable header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Montserrat", Font.BOLD, 15));
        tableHeader.setBackground(new Color(223, 235, 246));

        //Add table to scrollpane
        tablePane = new JScrollPane(table);
        tablePane.setBounds(110, 426, 880, 267);

        musicOnButton = new ImageButton("button/music-on.png");
        musicOffButton = new ImageButton("button/music-off.png");
        homeButton = new ImageButton("button/home.png");
        runButton = new ImageButton("button/run.png");
        resetButton = new ImageButton("button/reset.png");
        processNumMinusButton = new ImageButton("button/minus.png");
        processNumPlusButton = new ImageButton("button/add.png");
        timeQuantumMinusButton = new ImageButton("button/minus.png");
        timeQuantumPlusButton = new ImageButton("button/add.png");

        processNum = new JTextField("3", 2);
        processNum.setName("processNum");
        processNum.setBorder(null);
        processNum.setHorizontalAlignment(SwingConstants.CENTER);
        processNum.setFont(new Font("Montserrat", Font.BOLD, 20));

        timeQuantum = new JTextField(2);
        timeQuantum.setName("timeQuantum");
        timeQuantum.setBorder(null);
        timeQuantum.setHorizontalAlignment(SwingConstants.CENTER);
        timeQuantum.setFont(new Font("Montserrat", Font.BOLD, 20));

        enableTimeQuantum(false);

        algorithmChoice = new JComboBox(new String[]{"FCFS", "RR", "SJF(PE)", "SJF(NPE)", "Priority(PE)", "Priority(NPE)"});
        algorithmChoice.setRenderer(new CustomComboBoxRenderer());
        algorithmChoice.setBackground(new Color(118, 138, 150));
        algorithmChoice.setForeground(Color.white);
        algorithmChoice.setBorder(null);
        algorithmChoice.setFont(new Font("Montserrat", Font.BOLD, 18));

        removeButton = new ImageButton("button/remove.png");
        randomizeButton = new ImageButton("button/randomize.png");
        algoLabel = new Label("First Come First Served will execute processes in the order which they arrived", true, SwingConstants.LEFT);

        musicOnButton.setBounds(945, 40, 47, 47);
        musicOffButton.setBounds(945, 40, 47, 47);
        homeButton.setBounds(1010, 40, 47, 47);
        runButton.setBounds(682, 333, 94, 42);
        resetButton.setBounds(869, 333, 94, 42);
        processNumMinusButton.setBounds(354, 271, 44, 44);
        processNumPlusButton.setBounds(510, 272, 44, 44);
        timeQuantumMinusButton.setBounds(354, 347, 44, 44);
        timeQuantumPlusButton.setBounds(510, 347, 44, 44);
        processNum.setBounds(398, 272, 112, 43);
        timeQuantum.setBounds(398, 347, 112, 43);
        algorithmChoice.setBounds(203, 250, 130, 41);
        removeButton.setBounds(897, 712, 94, 42);
        randomizeButton.setBounds(751, 712, 130, 42);
        algoLabel.setBounds(71, 303, 243, 75);

//        randomizeButton.setVisible(false);
        runButton.setEnabled(false); // should have inputs first

        musicOffButton.setVisible(false);

        setListeners();

        this.add(musicOnButton);
        this.add(musicOffButton);
        this.add(homeButton);
        this.add(runButton);
        this.add(resetButton);
        this.add(processNumPlusButton);
        this.add(processNumMinusButton);
        this.add(timeQuantumPlusButton);
        this.add(timeQuantumMinusButton);
        this.add(processNum);
        this.add(timeQuantum);
        this.add(algorithmChoice);
        this.add(tablePane);
        this.add(removeButton);
        this.add(randomizeButton);
        this.add(algoLabel);

    }

    private void setListeners() {
        musicOffButton.hover("button/music-on-hover.png", "button/music-off.png");
        homeButton.hover("button/home-hover.png", "button/home.png");
        homeButton.hover("button/home-hover.png", "button/home.png");
        runButton.hover("button/run-hover.png", "button/run.png");
        resetButton.hover("button/reset-hover.png", "button/reset.png");
        processNumMinusButton.hover("button/minus-hover.png", "button/minus.png");
        processNumPlusButton.hover("button/add-hover.png", "button/add.png");
        timeQuantumMinusButton.hover("button/minus-hover.png", "button/minus.png");
        timeQuantumPlusButton.hover("button/add-hover.png", "button/add.png");
        removeButton.hover("button/remove-hover.png", "button/remove.png");
        randomizeButton.hover("button/randomize-hover.png", "button/randomize.png");
        algorithmChoice.addActionListener(e -> updateAlgoLabel((String) algorithmChoice.getSelectedItem()));
        processNumPlusButton.addActionListener(e -> processNum.setText(String.valueOf(Integer.parseInt(processNum.getText()) + 1)));
        processNumMinusButton.addActionListener(e -> processNum.setText(String.valueOf(Integer.parseInt(processNum.getText()) - 1)));
        timeQuantumPlusButton.addActionListener(e -> timeQuantum.setText(String.valueOf(Integer.parseInt(timeQuantum.getText()) + 1)));
        timeQuantumMinusButton.addActionListener(e -> timeQuantum.setText(String.valueOf(Integer.parseInt(timeQuantum.getText()) - 1)));
        removeButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row > -1 && table.getRowCount() > 3) {
                model.removeRow(row);
                processNum.setText(String.valueOf(Integer.parseInt(processNum.getText()) - 1));
            }
        });
        randomizeButton.addActionListener(e -> populateRandomly());
        listenToUserInput();
        resetButton.addActionListener(e -> model.resetTable());
    }

    public ImageButton getRandomizeButton() {
        return randomizeButton;
    }

    // generate random input on process number
    private void populateRandomly() {
        Random rand = new Random();
        Set<Integer> generatedPrioNumbers = new HashSet<>();
        for (int row = 0; row < table.getRowCount(); row++) {

        int bt = rand.nextInt(30) + 1;
        int at = rand.nextInt(30) + 1;
        int tq = rand.nextInt(10) + 1;
        int pn = 1;

        do {
            pn = rand.nextInt(20) + 1;
            if (!generatedPrioNumbers.contains(pn)) {
                generatedPrioNumbers.add(pn);
                break;  // exit loop once a unique number is generated
            }
        } while (true);

            model.setValueAt(bt, row, 1);
            model.setValueAt(at, row, 2);
            String selected = (String) algorithmChoice.getSelectedItem();
            if (((Objects.equals(selected, "Priority(PE)"))|| (Objects.equals(selected, "Priority(NPE)")))){
                model.setValueAt(pn, row, 3);
            } else {
                model.setValueAt("", row, 3);
            }
            if (Objects.equals(selected, "RR")) {
                timeQuantum.setText(String.valueOf(tq));
            }
        }

    }

    public void populateFromATextFile(ArrayList<int[]> textfile) {
        int numRows = textfile.size();
        model.setRowCount(numRows);
        processNum.setText(String.valueOf(numRows));
        int numCols = 3;
        int[][] valuesArray = new int[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            valuesArray[i] = textfile.get(i);
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                model.setValueAt(valuesArray[i][j], i, j+1);
            }
        }
    }

    private void enableTimeQuantum(boolean value) {
        timeQuantum.setEnabled(value);
        timeQuantumPlusButton.setEnabled(value);
        timeQuantumMinusButton.setEnabled(value);
    }

    private void updateAlgoLabel(String selected){
        switch (selected) {
            case "FCFS":
                algoLabel.setText("First Come First Served will execute processes in the order in which they arrived");
                enableTimeQuantum(false);
                break;
            case "RR":
                algoLabel.setText("Round Robin will execute each process for the duration of the time quantum. It will then move on to the next proccess.");
                timeQuantum.setText("1");
                enableTimeQuantum(true);
                break;
            case "SJF(PE)":
                algoLabel.setText("Shortest Job First will execute processes from smallest to biggest preemptively");
                enableTimeQuantum(false);
                break;
            case "SJF(NPE)":
                algoLabel.setText("Shortest Job First will execute processes from smallest to biggest non-preemptively");
                enableTimeQuantum(false);
                break;
            case "Priority(PE)":
                algoLabel.setText("Priority Scheduling will execute each process according to the assigned priority preemptively.");
                enableTimeQuantum(false);
                break;
            case "Priority(NPE)":
                algoLabel.setText("Priority Scheduling will execute each process according to the assigned priority nonpreemptively.");
                enableTimeQuantum(false);
                break;
            default:
                return;
        }
        updateRunButton();
    }

    private void updateRunButton() {
        if (validTable()) {
            runButton.setEnabled(true);
        } else{
            runButton.setEnabled(false);
        }
    }
    private void listenToUserInput() {
        inputValidator(processNum, 3, 30);
        inputValidator(timeQuantum, 1, 10);
        model.addTableModelListener(e -> updateRunButton());
    }

    private void inputValidator(JTextField input, int minimum, int maximum) {
        input.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateInput();
            }

            private void validateInput() {
                try {
                    String text = input.getText();
                    int value = Integer.parseInt(text);
                    if (value < minimum || value > maximum) {
                        // If the value is out of range, highlight the text field
                        input.setBackground(new Color(255, 202, 202));
                        runButton.setEnabled(false);
                    } else {
                        // Otherwise, clear the highlighting
                        input.setBackground(UIManager.getColor("TextField.background"));
                        if (input.getName().equals("processNum")) {
                            model.setNumRows(value);
                        }
                        if (validTable()){
                            runButton.setEnabled(true);
                        }
                    }
                } catch (NumberFormatException ex) {
                    // If the input cannot be parsed as an integer, highlight the text field
                    input.setBackground(new Color(255, 202, 202));
                }
            }
        });
    }

    private boolean validTable() {
        String selected = (String) algorithmChoice.getSelectedItem();
        int columnCount = table.getColumnCount();
        if (!((Objects.equals(selected, "Priority(PE)"))|| (Objects.equals(selected, "Priority(NPE)")))){
            columnCount = table.getColumnCount() - 1;
        }
        boolean hasNullorBlank = false;
        for (int row = 0; row < table.getRowCount(); row++) {
            for (int col = 0; col < columnCount; col++) {
                Object value = table.getValueAt(row, col);
                if (value == null || value.toString().trim().isEmpty()) {
                    hasNullorBlank = true;
                    break;
                }
            }
            if (hasNullorBlank) {
                break;
            }
        }
        if (hasNullorBlank) {
            return false;
        } else {
           return true;
        }
    }

    public Scheduler getScheduler() {
        Scheduler scheduler = new FCFS();
        String selected = (String) algorithmChoice.getSelectedItem();
        switch (selected) {
            case "FCFS":
                scheduler = new FCFS();
                break;
            case "RR":
                scheduler = new RR();
                scheduler.setTimeQuantum(Integer.parseInt(timeQuantum.getText()));
                break;
            case "SJF(PE)":
                scheduler = new SJFPE();
                break;
            case "SJF(NPE)":
                scheduler = new SJFNPE();
                break;
            case "Priority(PE)":
                scheduler = new PriorityPE();
                break;
            case "Priority(NPE)":
                scheduler = new PriorityNPE();
                break;
            default:
                return scheduler;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            String process = (String) model.getValueAt(i, 0);
            int burstTime = Integer.parseInt(model.getValueAt(i, 1).toString());
            int arrivalTime = Integer.parseInt(model.getValueAt(i, 2).toString());
            int priorityNum;

            if (selected.equals("Priority(PE)") || selected.equals("Priority(NPE)")) {
                if (!model.getValueAt(i, 3).equals("")) {
                    priorityNum = Integer.parseInt(model.getValueAt(i, 3).toString());
                }
                else {
                    priorityNum = 1;
                }
            }
            else {
                priorityNum = 1;
            }
            scheduler.add(new Process(process, burstTime, arrivalTime, priorityNum));
        }
        scheduler.simulate();
        return scheduler;
    }

    public void cleanAllInputs() {
        algorithmChoice.setSelectedIndex(0);
        enableTimeQuantum(false);
        processNum.setText("3");
        timeQuantum.setText("1");
        model.resetTable();
    }

    public void musicClick() {
        if (musicOffButton.isVisible()){
            musicOnButton.setVisible(true);
            musicOffButton.setVisible(false);
        } else {
            musicOnButton.setVisible(false);
            musicOffButton.setVisible(true);
        }
    }

    private static class CustomComboBoxRenderer extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setHorizontalAlignment(SwingConstants.CENTER);
            if (isSelected) {
                setBackground(new Color(223, 235, 246)); // set selected item background color
                setForeground(new Color(118, 138, 150)); // set item text color

            } else {
                setBackground(new Color(118, 138, 150)); // set unselected item background color
                setForeground(Color.WHITE); // set item text color
            }
            setFont(new Font("Montserrat", Font.BOLD, 14));
            return this;
        }
    }

    public static void main(String[] args) {
        InputPanel m = new InputPanel();
        Frame frame = new Frame("Input Panel");
        frame.add(m);
        frame.setVisible(true);
    }

    public ImageButton getMusicOnButton() {
        return musicOnButton;
    }
    public ImageButton getMusicOffButton() {
        return musicOffButton;
    }

    public ImageButton getHomeButton() {
        return homeButton;
    }

    public ImageButton getRunButton() {
        return runButton;
    }

}

