package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SJFPE extends Scheduler {
    @Override
    public void simulate()
    {
        Collections.sort(this.getProcesses(), (Object o1, Object o2) -> {
            if (((Process) o1).getArrivalTime() == ((Process) o2).getArrivalTime())
            {
                return 0;
            }
            else if (((Process) o1).getArrivalTime() < ((Process) o2).getArrivalTime())
            {
                return -1;
            }
            else
            {
                return 1;
            }
        });

        List<Process> rows = getCopy(this.getProcesses());
        int time = rows.get(0).getArrivalTime();

        while (!rows.isEmpty())
        {
            List<Process> availableRows = new ArrayList();

            for (Process Process : rows)
            {
                if (Process.getArrivalTime() <= time)
                {
                    availableRows.add(Process);
                }
            }

            Collections.sort(availableRows, (Object o1, Object o2) -> {
                if (((Process) o1).getBurstTime() == ((Process) o2).getBurstTime())
                {
                    return 0;
                }
                else if (((Process) o1).getBurstTime() < ((Process) o2).getBurstTime())
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            });

            Process Process = availableRows.get(0);
            this.getTimeline().add(new Event(Process.getProcessName(), time, ++time));
            Process.setBurstTime(Process.getBurstTime() - 1);

            if (Process.getBurstTime() == 0)
            {
                for (int i = 0; i < rows.size(); i++)
                {
                    if (rows.get(i).getProcessName().equals(Process.getProcessName()))
                    {
                        rows.remove(i);
                        break;
                    }
                }
            }
        }

        for (int i = this.getTimeline().size() - 1; i > 0; i--)
        {
            List<Event> timeline = this.getTimeline();

            if (timeline.get(i - 1).getProcessName().equals(timeline.get(i).getProcessName()))
            {
                timeline.get(i - 1).setFinishTime(timeline.get(i).getFinishTime());
                timeline.remove(i);
            }
        }

        Map map = new HashMap();

        for (Process Process : this.getProcesses())
        {
            map.clear();

            for (Event event : this.getTimeline())
            {
                if (event.getProcessName().equals(Process.getProcessName()))
                {
                    if (map.containsKey(event.getProcessName()))
                    {
                        int w = event.getStartTime() - (int) map.get(event.getProcessName());
                        Process.setWaitingTime(Process.getWaitingTime() + w);
                    }
                    else
                    {
                        Process.setWaitingTime(event.getStartTime() - Process.getArrivalTime());
                    }

                    map.put(event.getProcessName(), event.getFinishTime());
                }
            }

            Process.setTurnaroundTime(Process.getWaitingTime() + Process.getBurstTime());
        }
    }
}
