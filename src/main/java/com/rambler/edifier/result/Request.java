package com.rambler.edifier.result;

import com.rambler.edifier.httpclient.HttpConstant;
import com.rambler.edifier.work.Downloader;
import com.rambler.edifier.work.WorkDownloader;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.Experimental;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Request implements Serializable {


    public static final String CYCLE_TRIED_TIMES = "_cycle_tried_times";
    private static final long serialVersionUID = -7585546472602528451L;
    private volatile Map<String, String> urlContext;
    private volatile Map<String, String> param;
    private volatile String paramText;

    private volatile String method;

    private String url;


    private HttpRequestBody requestBody;

    /**
     * this req use this downloader 使用自己的downloader
     */
    private WorkDownloader downloader;

    /**
     * Store additional information in extras.
     */
    private Map<String, Object> extras;

    /**
     * cookies for current url, if not set use Site's cookies
     */
    private Map<String, String> cookies = new HashMap<String, String>();

    private Map<String, String> headers = new HashMap<String, String>();

    /**
     * Priority of the request.<br>
     * The bigger will be processed earlier. <br>
     *
     * @see us.codecraft.webmagic.scheduler.PriorityScheduler
     */
    private long priority;

    /**
     * When it is set to TRUE, the downloader will not try to parse response body to text.
     */
    private boolean binaryContent = false;

    private String charset;

    public Request(String url,String method,Map<String, String>  headers,Map<String, String>  param){
            this.url=url;
            this.method=method;
            this.headers=headers;
            this.param=param;
    };

    public Request(String url,String method,Map<String, String>  headers){
        this.url=url;
        this.method=method;
        this.headers=headers;
    };
    public void addUrlContext(Map<String, String> urlContext) {
        this.urlContext = urlContext;
    }



    public void addParam(Map<String, String> urlContext) {
        this.param = urlContext;
    }
    public void addParam(String k , String v) {
        this.param .put(k,v);
    }

    public void addParam(String paramText) {
        this.paramText=paramText;
    }
    public Map<String,String> getParam(){
        return param;
    }
    public String getParamText(){
        return paramText;
    }

    public Map<String, String> getUrlContext() {
        return this.urlContext;
    }
    public Request() {
    }

    public Request(String url) {
        this.url = url;
    }

    public long getPriority() {
        return priority;
    }

    /**
     * Set the priority of request for sorting.<br>
     * Need a scheduler supporting priority.<br>
     *
     * @param priority priority
     * @return this
     * @see us.codecraft.webmagic.scheduler.PriorityScheduler
     */
    @Experimental
    public Request setPriority(long priority) {
        this.priority = priority;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getContextMapValue(String key) {
        if (extras == null) {
            return null;
        }
        return (T) extras.get(key);
    }


    public <T> Request putContextMap(Map<String, String> urlContext) {
        if (urlContext == null) {
            urlContext = new HashMap<String, String>();
        }
        urlContext.putAll(urlContext);
        return this;
    }

    public <T> Request putContextMap(String key, T value) {
        if (extras == null) {
            extras = new HashMap<String, Object>();
        }
        extras.put(key, value);
        return this;
    }

    public Request addUrlContextMap(Map<String, String> urlContext) {
        if (this.urlContext == null) {
            this.urlContext = new HashMap<String, String>();
        }
        this.urlContext.putAll(urlContext);
        return this;
    }

    /*public Map<String,Object>  getContextMap() {
        if (extras == null) {
            extras=new HashMap<String,Object>();
        }
        return extras;
    }  */
    public Map<String, String> getContextMap() {
        if (urlContext == null) {
            urlContext = new HashMap<String, String>();
        }
        return urlContext;
    }

    public <T> Request getContextMap(String key) {
        if (extras == null) {
            return null;
        }
        extras.get(key);
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

   /* public Request setExtras(Map<String, String> contextMap) {
        contextHashMap = contextMap;
        if (CollectionUtils.isNotEmpty(Collections.singleton(contextMap))) {
            for (String key : contextMap.keySet()) {
                contextHashMap.put(key, contextMap.get(key));
            }
        }
        return this;
    }*/

    public Request setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * The http method of the request. Get for default.
     *
     * @return httpMethod
     * @see us.codecraft.webmagic.utils.HttpConstant.Method
     * @since 0.5.0
     */
    public String getMethod() {
        return method;
    }

    public Request setMethod(String method) {
        this.method = method;
        return this;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (url != null ? !url.equals(request.url) : request.url != null) return false;
        return method != null ? method.equals(request.method) : request.method == null;
    }

    public Request addCookie(String name, String value) {
        cookies.put(name, value);
        return this;
    }

    public Request addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public Request addHeader(Map<String, String> head) {
        headers.putAll(head);
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpRequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(HttpRequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public boolean isBinaryContent() {
        return binaryContent;
    }

    public Downloader getDownloader() {
        return downloader;
    }

    public Request setBinaryContent(boolean binaryContent) {
        this.binaryContent = binaryContent;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public Request setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", extras=" + extras +
                ", priority=" + priority +
                ", headers=" + headers +
                ", cookies=" + cookies +
                '}';
    }
}
