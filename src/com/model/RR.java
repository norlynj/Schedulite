package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RR extends Scheduler {
    @Override
    public void simulate() {
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
        int timeQuantum = this.getTimeQuantum();

        while (!processes.isEmpty()) {
            Process Process = processes.get(0);
            int bt = (Process.getBurstTime() < timeQuantum ? Process.getBurstTime() : timeQuantum);
            this.getTimeline().add(new Event(Process.getProcessName(), time, time + bt));
            time += bt;
            processes.remove(0);

            if (Process.getBurstTime() > timeQuantum) {
                Process.setBurstTime(Process.getBurstTime() - timeQuantum);

                for (int i = 0; i < processes.size(); i++) {
                    if (processes.get(i).getArrivalTime() > time) {
                        processes.add(i, Process);
                        break;
                    }
                    else if (i == processes.size() - 1) {
                        processes.add(Process);
                        break;
                    }
                }
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
