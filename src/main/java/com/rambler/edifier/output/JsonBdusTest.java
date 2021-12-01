package com.rambler.edifier.output;

import com.alibaba.fastjson.JSONObject;
import net.minidev.json.JSONArray;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonBdusTest {
    /**
     * 将字符串追加到文件已有内容后面
     *
     * @param fileFullPath 文件完整地址：D:/test.txt
     * @param content      需要写入的
     */
    public static void writeFile(String fileFullPath, String content) {
        FileOutputStream fos = null;
        try {
            //true不覆盖已有内容
            fos = new FileOutputStream(fileFullPath, true);
            //写入
            fos.write(content.getBytes());
            // 写入一个换行
            fos.write("\r\n".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读文件
     *
     * @param path
     * @return
     */
    public static JSONArray readFile(String path) {
        //String path = "/Users/wanghan/Desktop/output.json";
        File file = new File(path);
        JSONArray result = new JSONArray();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));//构造一个BufferedReader类来读取文件

            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.add(JSONObject.parse(s));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    static Set<String> set = new HashSet<String>();
    static List<JSONObject> skus = new ArrayList<JSONObject>();

    public static List<JSONObject> resultBuild(String path) throws IOException {
        JSONArray arr = readFile(path);
        // 去重
        List<Boolean> datilUrl = arr.stream().map(a -> {
            JSONObject json = (JSONObject) a;
            if (set.add(json.getString("datilUrl"))) {
                skus.add(json);
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        System.out.println(1);
        return skus;
    }

}

