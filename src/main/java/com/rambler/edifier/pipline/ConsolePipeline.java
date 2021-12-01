package com.rambler.edifier.pipline;
import com.rambler.edifier.page.Page;
import com.rambler.edifier.result.ResultItems;
import us.codecraft.webmagic.Task;

import java.util.Map;

public class ConsolePipeline implements Pipeline {

    @Override
    public void process(Page page, ResultItems resultItems, Task task) {
        String rawText = page.getRawText();
        System.out.println("send request:"+resultItems.getRequest().getUrl() +"\n get data:"+rawText);
        //System.out.println("get page: " + resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    }
}