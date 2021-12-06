package com.rambler.edifier.groovy

import com.rambler.edifier.core.Script
import com.rambler.edifier.page.Page
import com.rambler.edifier.result.Request
import com.rambler.edifier.result.down.Result
import net.minidev.json.JSONObject
import us.codecraft.webmagic.selector.Html
import us.codecraft.webmagic.selector.Selectable

class HaodouScriptTest implements Script{
    @Override
    public Result process(Page page) {
        Request request = page.getRequest();
        Map<String, String> contextMap = request.getContextMap();
        String type = contextMap.get("type");
        Result result = new Result();
        switch (type){
            case "page1":
                result =context1(page,contextMap,result);
                break;
            case "page2":
                result =context2(page,contextMap,result);
                break;
        }
        return result;

    }

    private Result context2(Page page, Map<String, String> contextMap, Result result) {
        Html html = page.getHtml();

        String foodTitle = contextMap.get("foodTitle")
        String foodName =  contextMap.get("foodName");
        String detailUrl = contextMap.get("detailUrl");
        List<Selectable> nodes = html.$(".s_list").xpath("/div/ul/li").nodes();
        for (Selectable selectable:nodes) {
            String image = selectable.xpath("a/@href")
            String name = selectable.$(".name.kw").xpath("a/@href")
            String detail = selectable.$(".info").xpath("p/@text()")
            JSONObject json = new JSONObject()
            json.put("image",image)
            json.put("name",name)
            json.put("detail",detail)
            json.put("foodTitle",foodTitle)
            json.put("foodName",foodName)
            json.put("detailUrl",detailUrl)
            result.addData(json.toJSONString())
        }
        return  result;

    }

    public Result context1(Page page, Map<String,String> urlContext, Result result){
        Html html = page.getHtml();
        List<Selectable> nodes = html.$(".snack_classify_con.clearfix").xpath("/div/dl").nodes();
        for (Selectable selectable:nodes){
            String dt = selectable.xpath("dl/dt/h2/a/text()").get();
            String dd =selectable.xpath("dl/dd/p/a/text()").get();
            String nextUrl =selectable.xpath("dl/dd/p/a/@href").get();
            urlContext.put("foodTitle",dt);
            urlContext.put("foodName",dd);
            urlContext.put("detailUrl",nextUrl);
            // 构建详情页请求
            Request request =  childUrl(urlContext);
            // 发送详情页
            result.addChildLinked(request);
            System.out.println(1);
        }
        System.out.println(1);

        return result;
    }
    private Request childUrl(Map<String, String> urlContext) {

        urlContext.put("type","page2");
        String nextUrl = urlContext.get("detailUrl");
        Map<String, String> head = new HashMap<>();
        head.put(":authority","www.xiangha.com");
        head.put(":method","GET");
        head.put(":path","/caipu/z-recai/");
        head.put(":scheme","https");
        head.put("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        head.put("accept-encoding","gzip, deflate, br");
        head.put("accept-language","zh-CN,zh;q=0.9");
        head.put("cache-control","max-age=0");
        head.put("sec-ch-ua-mobile","?0");
        head.put("sec-ch-ua-platform","\"macOS\"");
        head.put("sec-fetch-dest","document");
        head.put("sec-fetch-mode","navigate");
        head.put("sec-fetch-site","none");
        head.put("sec-fetch-user","?1");
        head.put("upgrade-insecure-requests","1");
        head.put("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");
        Request request = new Request();
        request.addHeader(head);
        request.addUrlContext(urlContext);
        request.setUrl(nextUrl);
        return request;
    }
    @Override
    public Request seed(String seed) {

        String url = "https://www.xiangha.com/fenlei/";
        Map<String, String> head = new HashMap<>();
        head.put("accept-encoding","gzip, deflate, br");
        head.put("accept-language","zh-CN,zh;q=0.9");
        head.put("cache-control","max-age=0");
        head.put("sec-ch-ua-mobile","?0");
        head.put("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");
        Request request = new Request(url).addHeader(head);
        request.putContextMap("type","url1");
        Map<String, String> urlContext = new HashMap<>();
        urlContext.put("type","page1");
        request.addUrlContextMap(urlContext);
        return request;
    }
}
