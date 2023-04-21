package uk.ac.tees.a0547574.learnchineseapp;

import android.os.Bundle;

import java.util.List;

import uk.ac.tees.a0547574.learnchineseapp.Bean.PinBuWordBean;
import uk.ac.tees.a0547574.learnchineseapp.Database.DBManage;
import uk.ac.tees.a0547574.learnchineseapp.Util.CommonUtils;
import uk.ac.tees.a0547574.learnchineseapp.Util.URLUtil;

public class SearchRadicalsActivity extends BaseSearchActivity {

    String url;   //获取指定部首对应的网址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTv.setText(R.string.main_tv_bushou);
        initData(CommonUtils.FILE_BUSHOU, CommonUtils.TYPE_BUSHOU);
        setExLvListener(CommonUtils.TYPE_BUSHOU);
        exLv.expandGroup(0);   //默认展开第一组
        word = "丨";     //默认进去时获取的是第一个 a
        url = URLUtil.getBushouurl(word,page,pagesize);
        // 加载网络数据
        loadData(url);
        setGVListener(CommonUtils.TYPE_BUSHOU);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        List<PinBuWordBean.ResultBean.ListBean> list = DBManage.queryBsWordFromPywordtb(word, page, pagesize);
        refreshDataByGV(list);
    }
}

