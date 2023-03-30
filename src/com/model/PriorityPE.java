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
        Collections.sort(this.getProcesses(), (Object o1, Object o2) -> {
            if (((Process) o1).getArrivalTime() == ((Process) o2).getArrivalTime()) {
                return 0;
            }
            else if (((Process) o1).getArrivalTime() < ((Process) o2).getArrivalTime()) {
                return -1;
            }
            else {
                return 1;
            }
        });

        List<Process> processes = getCopy(this.getProcesses());
        int time = processes.get(0).getArrivalTime();

        // enters a loop that runs until the list of processes is empty
        // creates an availableRows list and adds to it all the processes whose arrival time is less than or equal to time.
        // sorted based on their priority number, with higher priority processes being executed first.
        while (!processes.isEmpty()) {
            List<Process> availableRows = new ArrayList();

            for (Process Process : processes) {
                if (Process.getArrivalTime() <= time) {
                    availableRows.add(Process);
                }
            }

            // selects the highest priority process from the availableRows list and adds an event to the timeline
            Collections.sort(availableRows, (Object o1, Object o2) -> {
                if (((Process) o1).getPriorityNumber()== ((Process) o2).getPriorityNumber()) {
                    return 0;
                }
                else if (((Process) o1).getPriorityNumber() < ((Process) o2).getPriorityNumber()) {
                    return -1;
                }
                else {
                    return 1;
                }
            });

            Process Process = availableRows.get(0);
            this.getTimeline().add(new Event(Process.getProcessName(), time, ++time));
            Process.setBurstTime(Process.getBurstTime() - 1);

            if (Process.getBurstTime() == 0) {
                for (int i = 0; i < processes.size(); i++) {
                    if (processes.get(i).getProcessName().equals(Process.getProcessName())) {
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

        Map map = new HashMap();

        for (Process Process : this.getProcesses()) {
            map.clear();

            for (Event event : this.getTimeline()) {
                if (event.getProcessName().equals(Process.getProcessName())) {
                    if (map.containsKey(event.getProcessName())) {
                        int w = event.getStartTime() - (int) map.get(event.getProcessName());
                        Process.setWaitingTime(Process.getWaitingTime() + w);
                    }
                    else {
                        Process.setWaitingTime(event.getStartTime() - Process.getArrivalTime());
                    }

                    map.put(event.getProcessName(), event.getFinishTime());
                }
            }

            Process.setTurnaroundTime(Process.getWaitingTime() + Process.getBurstTime());
        }
    }
}
