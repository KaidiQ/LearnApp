package uk.ac.tees.a0547574.learnchineseapp.Util;

public class URLUtil {
    public static String pinyinurl = "http://v.juhe.cn/xhzd/querypy?key=";

    public static String bushourul = "http://v.juhe.cn/xhzd/querybs?key=";

    public static final String DICTKEY = "71d329e86fa753a7447b14d5851b120";//c

    public static String wordurl = "http://v.juhe.cn/xhzd/query?key=";

    public static final String CHENGYUKEY = "8745f858e56e8ff86be6684cc326d2c";//2
    public static String chengyuurl = "http://v.juhe.cn/chengyu/query?key=";

    public static String getChengyuurl(String word){
        String url = chengyuurl+CHENGYUKEY+"&word="+word;
        return url;
    }
    public static String getWordurl(String word){
        String url = wordurl+DICTKEY+"&word="+word;
        return url;
    }

    public static String getPinyinurl(String word,int page,int pagesize){
        String url = pinyinurl+DICTKEY+"&word="+word+"&page="+page+"&pagesize="+pagesize;
        return url;
    }

    public static String getBushouurl(String bs,int page,int pagesize){
        String url = bushourul+DICTKEY+"&word="+bs+"&page="+page+"&pagesize="+pagesize;
        return url;
    }
}
