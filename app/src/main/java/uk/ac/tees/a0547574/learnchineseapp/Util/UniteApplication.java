package uk.ac.tees.a0547574.learnchineseapp.Util;

import android.app.Application;

import uk.ac.tees.a0547574.learnchineseapp.Database.DBManage;

import org.xutils.*;

public class UniteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);   //初始化xUtils的模块数据
//        初始化数据库对象
        DBManage.initDB(this);
    }
}
