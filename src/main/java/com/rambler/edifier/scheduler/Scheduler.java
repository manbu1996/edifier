package com.rambler.edifier.scheduler;

import com.rambler.edifier.result.Request;
import us.codecraft.webmagic.Task;

public interface Scheduler {

    /**
     * add a url to fetch
     *
     * @param request request
     * @param task task
     */
    public void push(Request request, Task task);

    /**
     * get an url to crawl
     *
     * @param task the task of spider
     * @return the url to crawl
     */
    public Request poll(Task task);
}
