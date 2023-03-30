package model;
import java.util.Collections;
import java.util.List;

public class FCFS extends Scheduler{
    @Override
    public void simulate() {
        this.getProcesses().sort((Object o1, Object o2) -> {
            if (((Process) o1).getArrivalTime() == ((Process) o2).getArrivalTime()) {
                return 0;
            } else if (((Process) o1).getArrivalTime() < ((Process) o2).getArrivalTime()) {
                return -1;
            } else {
                return 1;
            }
        });

        List<Event> timeline = this.getTimeline();

        for (Process process : this.getProcesses())
        {
            if (timeline.isEmpty())
            {
                timeline.add(new Event(process.getProcessName(), process.getArrivalTime(), process.getArrivalTime() + process.getBurstTime()));
            }
            else
            {
                Event event = timeline.get(timeline.size() - 1);
                timeline.add(new Event(process.getProcessName(), event.getFinishTime(), event.getFinishTime() + process.getBurstTime()));
            }
        }

        for (Process process : this.getProcesses())
        {
            process.setWaitingTime(this.getEvent(process).getStartTime() - process.getArrivalTime());
            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
        }
    }
}
