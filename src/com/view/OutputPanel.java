package view;

import model.Process;
import model.Event;
import model.Scheduler;
import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;
import view.component.Label;
import model.CustomTableModel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OutputPanel extends Panel{
    private final ImageButton musicButton, homeButton, playTimerButton, stopTimerButton;
    private JScrollPane tablePane;
    private JTable table;

    private DefaultTableModel model;
    private CustomPanel chartPanel;
    private boolean chartIsPlaying = false;

    public OutputPanel() {
        super("bg/output-panel-bg.png");

        musicButton = new ImageButton("button/music-on.png");
        homeButton = new ImageButton("button/home.png");
        playTimerButton = new ImageButton("button/play-timer-button.png");
        stopTimerButton = new ImageButton("button/stop-timer.png");

        musicButton.setBounds(945, 40, 47, 47);
        homeButton.setBounds(1010, 40, 47, 47);
        playTimerButton.setBounds(65, 165, 40, 40);
        stopTimerButton.setBounds(65, 165, 40, 40);
        stopTimerButton.setVisible(false);
        model = new DefaultTableModel(new String[]{"Process ID", "Burst time", "Arrival time", "Priority Number", "Waiting Time", "Turnaround time", "Avg Waiting time", "Avg Turnaround time"}, 3) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        table = new JTable(model);
        table.setDragEnabled(false);
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
        tablePane.setBounds(43, 322, 1014, 319);

        //chartPanel
        chartPanel = new CustomPanel();
        chartPanel.setBounds(79, 215, 970, 50);



        setListeners();

        this.add(musicButton);
        this.add(homeButton);
        this.add(playTimerButton);
        this.add(stopTimerButton);
        this.add(tablePane);
        this.add(chartPanel);
    }

    private void setListeners() {
        musicButton.hover("button/music-off-hover.png", "button/music-on.png");
        homeButton.hover("button/home-hover.png", "button/home.png");
        playTimerButton.hover("button/play-timer-hover.png", "button/play-timer-button.png");
        playTimerButton.addActionListener(e -> {
            chartPanel.animateTimeline();
            playTimerButton.setVisible(false);
            stopTimerButton.setVisible(true);
        });
        stopTimerButton.addActionListener( e -> {
            chartPanel.stopTimer();
            playTimerButton.setVisible(true);
            stopTimerButton.setVisible(false);
        });
    }

    public ImageButton getMusicButton() {
        return musicButton;
    }

    public ImageButton getHomeButton() {
        return homeButton;
    }

    public void setProcessesInTable(Scheduler s) {
        ArrayList processes = (ArrayList) s.getProcesses();
        System.out.println(((Process) processes.get(0)).getProcessName());
        model.setRowCount(processes.size());

        for (int i = 0; i < processes.size(); i++) {
            Process p = ((Process) processes.get(i));
            model.setValueAt(p.getProcessName(), i, 0 );
            model.setValueAt(p.getBurstTime(), i, 1);
            model.setValueAt(p.getArrivalTime(), i, 2);
            model.setValueAt(p.getPriorityNumber(), i, 3);
            model.setValueAt(p.getWaitingTime(), i, 4);
            model.setValueAt(p.getTurnaroundTime(), i, 5);
            model.setValueAt(s.getAverageWaitingTime(), i, 6);
            model.setValueAt(s.getAverageTurnAroundTime(), i, 7);

        }

        // Sort
        // create a TableRowSorter and set it to the table
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        // set the sort keys to sort by PID column in ascending order
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

    }

    public void setChartPanel(ArrayList timeline) {
        chartPanel.setTimeline(timeline);
    }

    public void cleanAllOutput() {
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 1; j < model.getColumnCount(); j++) {
                model.setValueAt(null, i, j);
            }
        }
    }

    class CustomPanel extends JPanel {
        private java.util.List<Event> timeline;
        private int currentRect = 0;
        private Timer timer;

        public CustomPanel() {
            timer = new Timer(500, e -> {
                if (currentRect < timeline.size()) {
                    repaint();
                    currentRect++;
                } else {
                    timer.stop();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (timeline != null) {

                int totalDuration = 0;
                for (int i = 0; i < currentRect; i++) {
                    Event event = timeline.get(i);
                    totalDuration += event.getFinishTime() - event.getStartTime();
                }

                boolean labelAllowed = timeline.size() < 20;
                int panelWidth = 950;
                int x = 5;


                for (int i = 0; i < currentRect; i++) {
                    Event event = timeline.get(i);
                    int y = 1;
                    double percentage = (double) (event.getFinishTime() - event.getStartTime()) / totalDuration;
                    int width = (int) (panelWidth * percentage);
                    g.setColor(event.getColor());
                    g.fillRect(x, y, width, 30);
                    g.setFont(new Font("Montserrat", Font.BOLD, 13));
                    g.setColor(Color.black);
                    if (labelAllowed) {
                        g.drawString(event.getProcessName(), x + 10, y + 20);
                        g.drawString(Integer.toString(event.getStartTime() - 1), x - 5, y + 45);
                    } else if (!labelAllowed && (i == 0)) {
                        g.setColor(Color.black);
                        g.drawString(Integer.toString(event.getStartTime()), x, y + 45);
                    }

                    if (i == currentRect) {
                        g.drawString(Integer.toString(event.getFinishTime()), x + width - 5, y + 45);
                    }

                    x += width;

                    if (currentRect <= table.getRowCount()) {

                        // find row index in table for current process name
                        int rowIndex = -1;
                        for (int j = 0; j < table.getRowCount(); j++) {
                            String processId = (String) table.getValueAt(j, 0);
                            if (processId.equals(event.getProcessName())) {
                                rowIndex = j;
                                break;
                            }
                        }
                        table.clearSelection();
                        table.addRowSelectionInterval(rowIndex, rowIndex);
                        table.setSelectionBackground(event.getColor());
                        if (table.getSelectedRow() == table.getRowCount() - 1) {
                            playTimerButton.setVisible(true);
                            stopTimerButton.setVisible(false);
                        }
                    }
                }

            }
        }

        public void animateTimeline() {
            currentRect = 0;
            timer.start();
        }
        public void setTimeline(List<Event> timeline) {
            this.timeline = timeline;
            repaint();
        }

        public void stopTimer() {
            timer.stop();
        }
    }


    public static void main(String[] args) {
        OutputPanel m = new OutputPanel();
        Frame frame = new Frame("Output Panel");
        frame.add(m);
        frame.setVisible(true);
    }

}

