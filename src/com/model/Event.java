package model;

import java.awt.*;
import java.util.Random;

public class Event {
    private final String processName;
    private final int startTime;
    private int finishTime;
    private Color color;

    public Event(String processName, int startTime, int finishTime)
    {
        this.processName = processName;
        this.startTime = startTime;
        this.finishTime = finishTime;
        Random rand = new Random();
        this.color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public String getProcessName()
    {
        return processName;
    }

    public int getStartTime()
    {
        return startTime;
    }

    public int getFinishTime()
    {
        return finishTime;
    }

    public void setFinishTime(int finishTime)
    {
        this.finishTime = finishTime;
    }

    public Color getColor() {
        return color;
    }
}
