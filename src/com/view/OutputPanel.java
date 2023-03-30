package view;

import model.Process;
import model.Event;
import model.Scheduler;
import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;
import view.component.Label;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;

public class OutputPanel extends Panel{
    private final ImageButton musicOnButton, musicOffButton, homeButton, playTimerButton, stopTimerButton;
    private JScrollPane tablePane;
    private JTable table;

    private DefaultTableModel model;
    private CustomPanel chartPanel;
    private JLabel timerLabel;
    private long startTime = System.currentTimeMillis();

    public OutputPanel() {
        super("bg/output-panel-bg.png");

        musicOnButton = new ImageButton("button/music-on.png");
        musicOffButton = new ImageButton("button/music-off.png");
        homeButton = new ImageButton("button/home.png");
        playTimerButton = new ImageButton("button/play-timer-button.png");
        stopTimerButton = new ImageButton("button/stop-timer.png");
        timerLabel = new Label("00:00");

        musicOnButton.setBounds(945, 40, 47, 47);
        musicOffButton.setBounds(945, 40, 47, 47);
        homeButton.setBounds(1010, 40, 47, 47);
        playTimerButton.setBounds(70, 162, 40, 40);
        stopTimerButton.setBounds(70, 162, 40, 40);
        timerLabel.setBounds(215, 170, 130, 33);
        timerLabel.setFont(new Font("Montserrat", Font.BOLD, 27));
        stopTimerButton.setVisible(false);

        musicOffButton.setVisible(false);

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
        tablePane.setBounds(43, 322, 1014, 400);

        //chartPanel
        chartPanel = new CustomPanel();
        chartPanel.setBounds(79, 215, 970, 50);



        setListeners();

        this.add(musicOnButton);
        this.add(musicOffButton);
        this.add(homeButton);
        this.add(playTimerButton);
        this.add(stopTimerButton);
        this.add(tablePane);
        this.add(chartPanel);
        this.add(timerLabel);
    }

    private void setListeners() {
        musicOnButton.hover("button/music-off-hover.png", "button/music-on.png");
        musicOffButton.hover("button/music-on-hover.png", "button/music-off.png");
        homeButton.hover("button/home-hover.png", "button/home.png");
        playTimerButton.hover("button/play-timer-hover.png", "button/play-timer-button.png");
        playTimerButton.addActionListener(e -> {
            startTime = System.currentTimeMillis();
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

        // set the sort keys to sort by PID column numerically in ascending order
        sorter.setComparator(0, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                int num1 = Integer.parseInt(s1.substring(1));
                int num2 = Integer.parseInt(s2.substring(1));
                return Integer.compare(num1, num2);
            }
        });
        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

    }

    public void setChartPanel(ArrayList timeline) {
        chartPanel.setTimeline(timeline);
    }

    public void cleanAllOutput() {
        timerLabel.setText("00:00");
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
                if (currentRect <= timeline.size()) {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long seconds = (elapsedTime / 1000) % 60;
                    String time = String.format("%02d:00", seconds);
                    timerLabel.setText(String.format(time));
                    repaint();
                    currentRect++;
                } else {
                    homeButton.setEnabled(true);
                    timer.stop();
                    currentRect = 0;
                    timerLabel.setText("00:00");

                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            homeButton.setEnabled(false);

            if (timeline != null) {

                int totalDuration = 0;
                for (int i = 0; i < currentRect - 1; i++) {
                    Event event = timeline.get(i);
                    totalDuration += event.getFinishTime() - event.getStartTime();
                }

                boolean labelAllowed = timeline.size() < 20;
                int panelWidth = 950;
                int x = 5;


                for (int i = 0; i < currentRect - 1; i++) {
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

                    if (currentRect <= table.getRowCount() + 1) {

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
            homeButton.setEnabled(true);
            currentRect = 0;
        }
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

    public static void main(String[] args) {
        OutputPanel m = new OutputPanel();
        Frame frame = new Frame("Output Panel");
        frame.add(m);
        frame.setVisible(true);
    }

    public ImageButton getMusicOnButton() {
        return musicOnButton;
    }
    public ImageButton getMusicOffButton() {
        return musicOffButton;
    }


}

