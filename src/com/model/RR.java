package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RR extends Scheduler {
    @Override
    public void simulate() {
        this.getProcesses().sort((Object o1, Object o2) -> {
            return Integer.compare(((Process) o1).getArrivalTime(), ((Process) o2).getArrivalTime());
        });

        List<Process> processes = getCopy(this.getProcesses());
        int time = processes.get(0).getArrivalTime();
        int timeQuantum = this.getTimeQuantum();

        while (!processes.isEmpty()) {
            Process process = processes.get(0);
            int bt = (Math.min(process.getBurstTime(), timeQuantum));
            this.getTimeline().add(new Event(process.getProcessName(), time, time + bt));
            time += bt;
            processes.remove(0);

            if (process.getBurstTime() > timeQuantum) {
                process.setBurstTime(process.getBurstTime() - timeQuantum);

                for (int i = 0; i < processes.size(); i++) {
                    if (processes.get(i).getArrivalTime() > time) {
                        processes.add(i, process);
                        break;
                    }
                    else if (i == processes.size() - 1) {
                        processes.add(process);
                        break;
                    }
                }
            }
        }

        Map map = new HashMap(); // keep track of the previous events of a process to get waiting time

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
