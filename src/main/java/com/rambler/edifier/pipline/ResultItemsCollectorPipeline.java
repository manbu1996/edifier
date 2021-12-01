package com.rambler.edifier.pipline;

import com.rambler.edifier.page.Page;
import com.rambler.edifier.result.ResultItems;
import us.codecraft.webmagic.Task;

import java.util.ArrayList;
import java.util.List;

public class ResultItemsCollectorPipeline implements CollectorPipeline<ResultItems> {

    private List<ResultItems> collector = new ArrayList<ResultItems>();

    @Override
    public synchronized void process(Page page, ResultItems resultItems, Task task) {
        collector.add(resultItems);
    }

    @Override
    public List<ResultItems> getCollected() {
        return collector;
    }

}