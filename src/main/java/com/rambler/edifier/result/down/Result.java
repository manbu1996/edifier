package com.rambler.edifier.result.down;

import java.util.ArrayList;
import java.util.List;
import com.rambler.edifier.output.JsonBdusTest;
import com.rambler.edifier.result.Request;

/**
 * 目前用于
 */

public class Result {

    protected Request request;
    protected List<Request> targetRequests = new ArrayList<Request>();


    public void addChildLinked(Request request){
        targetRequests.add(request);
    }

    public void addChildLinkeds(List<Request> requests){
        targetRequests.addAll(requests);
    }
    public   List<Request> getChildLinkeds(){
        return targetRequests;
    }
    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public List<Request> getTargetRequests() {
        return targetRequests;
    }

    public void setTargetRequests(List<Request> targetRequests) {
        this.targetRequests = targetRequests;
    }
    // 数据落地
    public void addData(String string){
        JsonBdusTest.writeFile("/Users/wanghan/Desktop/food1.json",string);

    }
}
