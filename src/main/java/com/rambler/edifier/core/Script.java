package com.rambler.edifier.core;

import com.rambler.edifier.page.Page;
import com.rambler.edifier.result.Request;
import com.rambler.edifier.result.down.Result;
import us.codecraft.webmagic.Site;

public interface Script {
    public Result process(Page page);

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    public Request seed(String seed);
}
