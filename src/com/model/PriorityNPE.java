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

            for (Process process : rows) {
                if (process.getArrivalTime() <= time) {
                    availableRows.add(process);
                }
            }

            availableRows.sort((Object o1, Object o2) -> {
                return Integer.compare(((Process) o1).getPriorityNumber(), ((Process) o2).getPriorityNumber());
            });

            Process process = availableRows.get(0);
            this.getTimeline().add(new Event(process.getProcessName(), time, time + process.getBurstTime()));
            time += process.getBurstTime();

            for (int i = 0; i < rows.size(); i++) {
                if (rows.get(i).getProcessName().equals(process.getProcessName())) {
                    rows.remove(i);
                    break;
                }
            }
        }

        for (Process process : this.getProcesses()) {
            process.setWaitingTime(this.getEvent(process).getStartTime() - process.getArrivalTime());
            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
        }
    }
}
