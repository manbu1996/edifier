package com.rambler.edifier.main;

import com.rambler.edifier.core.MySpider;
import com.rambler.edifier.core.Script;
import com.rambler.edifier.groovy.HaodouScriptTest;
import com.rambler.edifier.page.Page;
import com.rambler.edifier.proxy.Proxy;
import com.rambler.edifier.proxy.ProxyProvider;
import com.rambler.edifier.result.Request;
import com.rambler.edifier.work.WorkDownloader;
import groovy.lang.GroovyClassLoader;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;

public class XiangHaMain {
     // 脚本统一入口œ
    public static void main(String[] args) {

        // 测试脚本数据采集地址 https://www.xiangha.com/fenlei/
        // 上层可以接数据源

        //目前 缺失资源  1、代理服务
       // run(1);
        // 线上热部署执行脚本
        run1();
    }

    /**
     *
     * @param thread 开启多线程模式 设置线程数
     *  编写爬虫脚本固定步骤
     *          1、 创建Groovy 类型 java文件
     *          2、 实现 Script 接口
     *          3、重写 seed 和process 方法
     *          4、seed 用于构建第一层 request
     *          5、process用于全局解析
     *          6、启动类拿到脚本text内容 或者 实例对象
     *          7、将需要启动的脚本放入主启动方法 配置代理和线程数 即可通过一行run启动
     *          8、数据输出方面： 继承 Result 类 重写 addData() 方法在脚本中 使用自己定义的Result 方法
     *
     */
    public static void run(Integer thread){
        Script haoDouScript = new HaodouScriptTest();
        String url = "https://www.xiangha.com/fenlei/";
        Request seed = haoDouScript.seed(url);

        WorkDownloader workDownloader = new WorkDownloader();
        // 设置代理
        workDownloader.setProxyProvider(new ProxyProvider() {
            @Override
            public void returnProxy(Proxy proxy, Page page, Task task) {

            }
            @Override
            public Proxy getProxy(Task task) {
                return null;
            }
        });
        MySpider.create(haoDouScript).setSite(Site.me()).thread(thread).addRequest(seed).run();
    }
    // 脚本实体
    static String  text="package com.rambler.edifier.groovy\n" +
            "\n" +
            "import com.rambler.edifier.core.Script\n" +
            "import com.rambler.edifier.page.Page\n" +
            "import com.rambler.edifier.result.Request\n" +
            "import com.rambler.edifier.result.down.Result\n" +
            "import net.minidev.json.JSONObject\n" +
            "import us.codecraft.webmagic.selector.Html\n" +
            "import us.codecraft.webmagic.selector.Selectable\n" +
            "\n" +
            "class HaodouScriptTest implements Script{\n" +
            "    @Override\n" +
            "    public Result process(Page page) {\n" +
            "        Request request = page.getRequest();\n" +
            "        Map<String, String> contextMap = request.getContextMap();\n" +
            "        String type = contextMap.get(\"type\");\n" +
            "        Result result = new Result();\n" +
            "        switch (type){\n" +
            "            case \"page1\":\n" +
            "                result =context1(page,contextMap,result);\n" +
            "                break;\n" +
            "            case \"page2\":\n" +
            "                result =context2(page,contextMap,result);\n" +
            "                break;\n" +
            "        }\n" +
            "        return result;\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    private Result context2(Page page, Map<String, String> contextMap, Result result) {\n" +
            "        Html html = page.getHtml();\n" +
            "\n" +
            "        String foodTitle = contextMap.get(\"foodTitle\")\n" +
            "        String foodName =  contextMap.get(\"foodName\");\n" +
            "        String detailUrl = contextMap.get(\"detailUrl\");\n" +
            "        List<Selectable> nodes = html.$(\".s_list\").xpath(\"/div/ul/li\").nodes();\n" +
            "        for (Selectable selectable:nodes) {\n" +
            "            String image = selectable.xpath(\"a/@href\")\n" +
            "            String name = selectable.$(\".name.kw\").xpath(\"a/@href\")\n" +
            "            String detail = selectable.$(\".info\").xpath(\"p/@text()\")\n" +
            "            JSONObject json = new JSONObject()\n" +
            "            json.put(\"image\",image)\n" +
            "            json.put(\"name\",name)\n" +
            "            json.put(\"detail\",detail)\n" +
            "            json.put(\"foodTitle\",foodTitle)\n" +
            "            json.put(\"foodName\",foodName)\n" +
            "            json.put(\"detailUrl\",detailUrl)\n" +
            "            result.addData(json.toJSONString())\n" +
            "        }\n" +
            "        return  result;\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    public Result context1(Page page, Map<String,String> urlContext, Result result){\n" +
            "        Html html = page.getHtml();\n" +
            "        List<Selectable> nodes = html.$(\".snack_classify_con.clearfix\").xpath(\"/div/dl\").nodes();\n" +
            "        for (Selectable selectable:nodes){\n" +
            "            String dt = selectable.xpath(\"dl/dt/h2/a/text()\").get();\n" +
            "            String dd =selectable.xpath(\"dl/dd/p/a/text()\").get();\n" +
            "            String nextUrl =selectable.xpath(\"dl/dd/p/a/@href\").get();\n" +
            "            urlContext.put(\"foodTitle\",dt);\n" +
            "            urlContext.put(\"foodName\",dd);\n" +
            "            urlContext.put(\"detailUrl\",nextUrl);\n" +
            "            // 构建详情页请求\n" +
            "            Request request =  childUrl(urlContext);\n" +
            "            // 发送详情页\n" +
            "            result.addChildLinked(request);\n" +
            "            System.out.println(1);\n" +
            "        }\n" +
            "        System.out.println(1);\n" +
            "\n" +
            "        return result;\n" +
            "    }\n" +
            "    private Request childUrl(Map<String, String> urlContext) {\n" +
            "\n" +
            "        urlContext.put(\"type\",\"page2\");\n" +
            "        String nextUrl = urlContext.get(\"detailUrl\");\n" +
            "        Map<String, String> head = new HashMap<>();\n" +
            "        head.put(\":authority\",\"www.xiangha.com\");\n" +
            "        head.put(\":method\",\"GET\");\n" +
            "        head.put(\":path\",\"/caipu/z-recai/\");\n" +
            "        head.put(\":scheme\",\"https\");\n" +
            "        head.put(\"accept\",\"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\");\n" +
            "        head.put(\"accept-encoding\",\"gzip, deflate, br\");\n" +
            "        head.put(\"accept-language\",\"zh-CN,zh;q=0.9\");\n" +
            "        head.put(\"cache-control\",\"max-age=0\");\n" +
            "        head.put(\"sec-ch-ua-mobile\",\"?0\");\n" +
            "        head.put(\"sec-ch-ua-platform\",\"\\\"macOS\\\"\");\n" +
            "        head.put(\"sec-fetch-dest\",\"document\");\n" +
            "        head.put(\"sec-fetch-mode\",\"navigate\");\n" +
            "        head.put(\"sec-fetch-site\",\"none\");\n" +
            "        head.put(\"sec-fetch-user\",\"?1\");\n" +
            "        head.put(\"upgrade-insecure-requests\",\"1\");\n" +
            "        head.put(\"user-agent\",\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36\");\n" +
            "        Request request = new Request();\n" +
            "        request.addHeader(head);\n" +
            "        request.putUrlContextMap(urlContext);\n" +
            "        request.setUrl(nextUrl);\n" +
            "        return request;\n" +
            "    }\n" +
            "    @Override\n" +
            "    public Request seed(String seed) {\n" +
            "\n" +
            "        String url = \"https://www.xiangha.com/fenlei/\";\n" +
            "        Map<String, String> head = new HashMap<>();\n" +
            "        head.put(\"accept-encoding\",\"gzip, deflate, br\");\n" +
            "        head.put(\"accept-language\",\"zh-CN,zh;q=0.9\");\n" +
            "        head.put(\"cache-control\",\"max-age=0\");\n" +
            "        head.put(\"sec-ch-ua-mobile\",\"?0\");\n" +
            "        head.put(\"user-agent\",\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36\");\n" +
            "        Request request = new Request(url).addHeader(head);\n" +
            "        request.putContextMap(\"type\",\"url1\");\n" +
            "        Map<String, String> urlContext = new HashMap<>();\n" +
            "        urlContext.put(\"type\",\"page1\");\n" +
            "        request.putUrlContextMap(urlContext);\n" +
            "        return request;\n" +
            "    }\n" +
            "}\n";
    public static  void run1(){
        GroovyClassLoader classLoader = new GroovyClassLoader();
        Class groovyClass = classLoader.parseClass(text);
        try {
            String url = "https://www.xiangha.com/fenlei/";
            Script groovyObject = (Script) groovyClass.newInstance();
            // 获取类名
            String simpleName = groovyObject.getClass().getSimpleName();
            //获取根路径
            String path = System.getProperty("user.dir");
            Request seed = groovyObject.seed(url);
            WorkDownloader workDownloader = new WorkDownloader();
            // 设置代理
            workDownloader.setProxyProvider(new ProxyProvider() {
                @Override
                public void returnProxy(Proxy proxy, Page page, Task task) {

                }
                @Override
                public Proxy getProxy(Task task) {
                    return null;
                }
            });
            MySpider.create(groovyObject).setSite(Site.me()).thread(1).addRequest(seed).run();
            //Request o = groovyObject.invokeMethod("seed", "null");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
