package com.rambler.edifier.work;

import com.rambler.edifier.page.Page;
import com.rambler.edifier.result.Request;
import us.codecraft.webmagic.Task;

public interface Downloader {

    /**
     * Downloads web pages and store in Page object.
     *
     * @param request request
     * @param task task
     * @return page
     */
    public Page download(Request request, Task task);

    /**
     * Tell the downloader how many threads the spider used.
     * @param threadNum number of threads
     */
    public void setThread(int threadNum);
}
