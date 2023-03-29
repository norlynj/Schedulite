package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class Scheduler {
    private final List<Process> processes;
    private final List<Event> timeline;
    private int timeQuantum;

    public Scheduler(){
        processes = new ArrayList();
        timeline = new ArrayList();
        timeQuantum = 1;
    }

    public boolean add(Process process) {
        return processes.add(process);
    }

    public void setTimeQuantum(int timeQuantum)
    {
        this.timeQuantum = timeQuantum;
    }

    public int getTimeQuantum()
    {
        return timeQuantum;
    }

    public double getAverageWaitingTime()
    {
        double avg = 0.0;

        for (Process process : processes)
        {
            avg += process.getWaitingTime();
        }

        DecimalFormat df = new DecimalFormat("#.##");
        double roundedResult = Double.parseDouble(df.format(avg / processes.size()));

        return roundedResult;
    }

    public double getAverageTurnAroundTime()
    {
        double avg = 0.0;

        for (Process process : processes)
        {
            avg += process.getTurnaroundTime();
        }

        DecimalFormat df = new DecimalFormat("#.##");
        double roundedResult = Double.parseDouble(df.format(avg / processes.size()));

        return roundedResult;
    }

    public Event getEvent(Process p)
    {
        for (Event event : timeline)
        {
            if (p.getProcessName().equals(event.getProcessName()))
            {
                return event;
            }
        }

        return null;
    }

    public Process getProcess(String p)
    {
        for (Process process : processes)
        {
            if (process.getProcessName().equals(p))
            {
                return process;
            }
        }

        return null;
    }


    public static List<Process> getCopy(List<Process> oldList)
    {
        List<Process> newList = new ArrayList();

        for (Process Process : oldList)
        {
            newList.add(new Process(Process.getProcessName(), Process.getBurstTime(), Process.getArrivalTime(), Process.getPriorityNumber()));
        }

        return newList;
    }

    public List<Process> getProcesses()
    {
        return processes;
    }

    public List<Event> getTimeline()
    {
        return timeline;
    }

    public abstract void simulate();
}
