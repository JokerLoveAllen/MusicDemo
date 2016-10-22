package com.joker.allenmp3.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/8/17.
 */
public class HttpUtil {
    public static String HttpGet(String...strings){
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        StringBuilder sbf = new StringBuilder();

        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",
                    "5b46143955a4b1ff1b470a94315625cd");
            if(connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String strRead1 = null;
                while ((strRead1 = reader.readLine()) != null) {
                    sbf.append(strRead1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader !=null ){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection !=null){
                connection.disconnect();
            }
        }
        return sbf.toString();
    }
}
