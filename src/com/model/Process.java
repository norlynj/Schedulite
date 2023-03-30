package model;

public class Process {
    private String processName;
    private int burstTime;
    private int arrivalTime;
    private int priorityNumber;
    private int waitingTime;
    private int turnaroundTime;

    public Process(String processName, int burstTime, int arrivalTime, int priorityNumber, int waitingTime, int turnaroundTime) {
        this.processName = processName;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priorityNumber = priorityNumber;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;

    }

    public Process(String processName, int burstTime, int arrivalTime, int priorityNumber)  {
        this(processName, burstTime, arrivalTime, priorityNumber, 0, 0);
    }


    public Process(String processName, int burstTime, int arrivalTime)  {
        this(processName, burstTime, arrivalTime, 0, 0, 0);
    }

    public void setBurstTime(int burstTime)
    {
        this.burstTime = burstTime;
    }

    public void setWaitingTime(int waitingTime)
    {
        this.waitingTime = waitingTime;
    }

    public void setTurnaroundTime(int turnaroundTime)
    {
        this.turnaroundTime = turnaroundTime;
    }

    public String getProcessName()
    {
        return this.processName;
    }

    public int getArrivalTime()
    {
        return this.arrivalTime;
    }

    public int getBurstTime()
    {
        return this.burstTime;
    }

    public int getPriorityNumber()
    {
        return this.priorityNumber;
    }

    public int getWaitingTime()
    {
        return this.waitingTime;
    }

    public int getTurnaroundTime()
    {
        return this.turnaroundTime;
    }

}
