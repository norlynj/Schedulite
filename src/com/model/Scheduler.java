package model;

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

        return avg / processes.size();
    }

    public double getAverageTurnAroundTime()
    {
        double avg = 0.0;

        for (Process process : processes)
        {
            avg += process.getTurnaroundTime();
        }

        return avg / processes.size();
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
