package uk.ac.tees.a0547574.learn_chinese_app.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
public class DBUtils {
    //数据库的工具类
    public static final String DATABASE_NAME="notepad.db";  //数据库名称
    public static final String DATABASE_TABLE="note";   //表名
    public static final int DATABASE_VERSION=2; //数据库版本号
    //数据库中的列名
    public static final String NOTEPAD_ID="id";
    public static final String NOTEPAD_CONTENT="content";
    public static final String NOTEPAD_TIME="notetime";
    //获取当前的日期
    public static final String getTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
