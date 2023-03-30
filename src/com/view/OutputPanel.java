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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OutputPanel extends Panel{
    private final ImageButton musicButton, homeButton, playTimerButton;
    private JScrollPane tablePane;
    private JTable table;

    private DefaultTableModel model;
    private CustomPanel chartPanel;

    public OutputPanel() {
        super("bg/output-panel-bg.png");

        musicButton = new ImageButton("button/music-on.png");
        homeButton = new ImageButton("button/home.png");
        playTimerButton = new ImageButton("button/play-timer-button.png");

        musicButton.setBounds(945, 40, 47, 47);
        homeButton.setBounds(1010, 40, 47, 47);
        playTimerButton.setBounds(65, 165, 40, 40);

        model = new DefaultTableModel(new String[]{"PID", "Burst time", "Arrival time", "Priority Number", "Waiting Time", "Turnaround time", "Avg Waiting time", "Avg Turnaround time"}, 3) {

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
        chartPanel.setBounds(79, 215, 960, 50);



        setListeners();

        this.add(musicButton);
        this.add(homeButton);
        this.add(playTimerButton);
        this.add(tablePane);
        this.add(chartPanel);
    }

    private void setListeners() {
        musicButton.hover("button/music-off-hover.png", "button/music-on.png");
        homeButton.hover("button/home-hover.png", "button/home.png");
        playTimerButton.hover("button/play-timer-hover.png", "button/play-timer-button.png");
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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (timeline != null) {

                int totalDuration = 0;
                for (Event event : timeline) {
                    totalDuration += event.getFinishTime() - event.getStartTime();
                }
                int panelWidth = 950;
                int x = 0;

                for (int i = 0; i < timeline.size(); i++) {
                    Event event = timeline.get(i);
                    int y = 1;
                    double percentage = (double) (event.getFinishTime() - event.getStartTime()) / totalDuration;
                    int width = (int) (panelWidth * percentage);
                    g.drawRect(x, y, width, 30);
                    g.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    g.drawString(event.getProcessName(), x + 10, y + 20);
                    g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    g.drawString(Integer.toString(event.getStartTime()), x - 5, y + 45);

                    if (i == timeline.size() - 1) {
                        g.drawString(Integer.toString(event.getFinishTime()), x + width - 5, y + 45);
                    }

                    x += width;
                }

            }
        }

        public void setTimeline(List<Event> timeline) {
            this.timeline = timeline;
            repaint();
        }
    }


    public static void main(String[] args) {
        OutputPanel m = new OutputPanel();
        Frame frame = new Frame("Output Panel");
        frame.add(m);
        frame.setVisible(true);
    }

}

