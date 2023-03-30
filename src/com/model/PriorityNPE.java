package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Priority Non-Preemptive Algorithm
 * 1. Arrange process by arrival time
 * 2. Assign process priority and Sort according to priority
 * 3. Execute the process with highest priority first, and so on
 * 4. Calculate the waiting time and turn around time
 */

public class PriorityNPE extends Scheduler {
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

        List<Process> rows = getCopy(this.getProcesses());
        int time = rows.get(0).getArrivalTime();

        while (!rows.isEmpty()) {
            List<Process> availableRows = new ArrayList();

            for (Process Process : rows) {
                if (Process.getArrivalTime() <= time) {
                    availableRows.add(Process);
                }
            }

            availableRows.sort((Object o1, Object o2) -> {
                if (((Process) o1).getPriorityNumber() == ((Process) o2).getPriorityNumber()) {
                    return 0;
                } else if (((Process) o1).getPriorityNumber() < ((Process) o2).getPriorityNumber()) {
                    return -1;
                } else {
                    return 1;
                }
            });

            Process Process = availableRows.get(0);
            this.getTimeline().add(new Event(Process.getProcessName(), time, time + Process.getBurstTime()));
            time += Process.getBurstTime();

            for (int i = 0; i < rows.size(); i++) {
                if (rows.get(i).getProcessName().equals(Process.getProcessName())) {
                    rows.remove(i);
                    break;
                }
            }
        }

        for (Process Process : this.getProcesses()) {
            Process.setWaitingTime(this.getEvent(Process).getStartTime() - Process.getArrivalTime());
            Process.setTurnaroundTime(Process.getWaitingTime() + Process.getBurstTime());
        }
    }
}
