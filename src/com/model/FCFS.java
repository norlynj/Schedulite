package model;
import java.util.Collections;
import java.util.List;

/* First Come First Serve Algorithm
* 1. Arrange processes by arrival time
* 2. Execute the process in the order they are arranged
* 3. Set the waiting time and the turnaround time for each process
*       waiting time - start time - arrival time
*       turnaround time - arrival time + burst time
*/

public class FCFS extends Scheduler{
    @Override
    public void simulate() {
        this.getProcesses().sort((Object o1, Object o2) -> {
            return Integer.compare(((Process) o1).getArrivalTime(), ((Process) o2).getArrivalTime());
        });

        List<Event> timeline = this.getTimeline();

        for (Process process : this.getProcesses()) {
            if (timeline.isEmpty()) {
                timeline.add(new Event(process.getProcessName(), process.getArrivalTime(), process.getArrivalTime() + process.getBurstTime()));
            }
            else {
                Event event = timeline.get(timeline.size() - 1);
                timeline.add(new Event(process.getProcessName(), event.getFinishTime(), event.getFinishTime() + process.getBurstTime()));
            }
        }

        for (Process process : this.getProcesses()) {
            process.setWaitingTime(this.getEvent(process).getStartTime() - process.getArrivalTime());
            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
        }
    }
}
