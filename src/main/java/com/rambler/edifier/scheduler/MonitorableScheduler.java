package com.rambler.edifier.scheduler;

import com.rambler.edifier.result.Request;
import us.codecraft.webmagic.Task;

public interface MonitorableScheduler extends Scheduler{
    void pushWhenNoDuplicate(Request request, Task task);

    public int getLeftRequestsCount(Task task);

    public int getTotalRequestsCount(Task task);
}
