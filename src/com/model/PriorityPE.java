package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriorityPE extends Scheduler {
    @Override
    public void simulate() {
        // Sorts this.getProcesses() collection in ascending order using arrival time
        this.getProcesses().sort((Object o1, Object o2) -> {
            return Integer.compare(((Process) o1).getArrivalTime(), ((Process) o2).getArrivalTime());
        });

        List<Process> processes = getCopy(this.getProcesses());
        int time = processes.get(0).getArrivalTime();

        // enters a loop that runs until the list of processes is empty
        // creates an availableProcesses list and adds to it all the processes whose arrival time is less than or equal to time.
        // sorted based on their priority number, with higher priority processes being executed first.
        while (!processes.isEmpty()) {
            List<Process> availableProcesses = new ArrayList();

            for (Process process : processes) {
                if (process.getArrivalTime() <= time) {
                    availableProcesses.add(process);
                }
            }

            // selects the highest priority process from the availableProcesses list and adds an event to the timeline
            availableProcesses.sort((Object o1, Object o2) -> {
                return Integer.compare(((Process) o1).getPriorityNumber(), ((Process) o2).getPriorityNumber());
            });

            Process process = availableProcesses.get(0);
            this.getTimeline().add(new Event(process.getProcessName(), time, ++time));
            process.setBurstTime(process.getBurstTime() - 1);

            if (process.getBurstTime() == 0) {
                for (int i = 0; i < processes.size(); i++) {
                    if (processes.get(i).getProcessName().equals(process.getProcessName())) {
                        processes.remove(i);
                        break;
                    }
                }
            }
        }

        for (int i = this.getTimeline().size() - 1; i > 0; i--) {
            List<Event> timeline = this.getTimeline();

            if (timeline.get(i - 1).getProcessName().equals(timeline.get(i).getProcessName())) {
                timeline.get(i - 1).setFinishTime(timeline.get(i).getFinishTime());
                timeline.remove(i);
            }
        }

        Map map = new HashMap(); // keep track of the previous events of a process to get

        // calculate the waiting time and turnaround time for each process
        for (Process process : this.getProcesses()) {
            map.clear();

            for (Event event : this.getTimeline()) {
                if (event.getProcessName().equals(process.getProcessName())) {
                    if (map.containsKey(event.getProcessName())) {
                        int w = event.getStartTime() - (int) map.get(event.getProcessName());
                        process.setWaitingTime(process.getWaitingTime() + w);
                    }
                    else {
                        process.setWaitingTime(event.getStartTime() - process.getArrivalTime());
                    }

                    map.put(event.getProcessName(), event.getFinishTime());
                }
            }

            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
        }
    }
}
