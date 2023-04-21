package uk.ac.tees.a0547574.learnchineseapp;

import android.widget.Toast;

import uk.ac.tees.a0547574.learnchineseapp.Util.MD5Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/*
 * 传入参数：1.要翻译的单词query
 *         2.源语言
 *         3.目标语言
 * 返回值：完整的请求字符串
 * */
public class CallBaiduTranslate {
    public static String translate(String query,String sourceLanguage,String targetLanguage){
        String requestString;
        String q=query;    //请求翻译query
        String from=sourceLanguage; //源语言
        String to=targetLanguage;   //目标语言
        String appid="20220524001227417";
        String key="MCN482PWHGOLy7dGvKmC";  //appid和密钥用于识别百度开发者身份
        String salt=(int)(Math.random()*100+1)+"";  //随机数，大概是为了使api调用更为安全
        String sign=MD5Util.getMD5Code(appid+q+salt+key);  //sign签名需拼接几个参数,并进行MD5加密
        requestString="https://api.fanyi.baidu.com/api/trans/vip/translate?q="+q+"&from="+from+"&to="+to+"&" +
                "appid="+appid+"&salt="+salt+"&sign="+sign;
        return requestString;
    }
}

