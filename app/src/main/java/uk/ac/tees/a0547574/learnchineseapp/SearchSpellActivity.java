package uk.ac.tees.a0547574.learnchineseapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import uk.ac.tees.a0547574.learnchineseapp.Bean.PinBuWordBean;
import uk.ac.tees.a0547574.learnchineseapp.Database.DBManage;
import uk.ac.tees.a0547574.learnchineseapp.Util.CommonUtils;
import uk.ac.tees.a0547574.learnchineseapp.Util.URLUtil;

public class SearchSpellActivity extends BaseSearchActivity {

    String url;   //获取指定拼音对应的网址
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(CommonUtils.FILE_PINYIN,CommonUtils.TYPE_PINYIN);
        setExLvListener(CommonUtils.TYPE_PINYIN);
        exLv.expandGroup(0);   //默认展开第一组
        word = "a";     //默认进去时获取的是第一个 a
        url = URLUtil.getPinyinurl(word,page,pagesize);
        // 加载网络数据
        loadData(url);
        setGVListener(CommonUtils.TYPE_PINYIN);
    }
    /*
     * 网络获取失败时会调用的接口
     * 因为拼音查询和部首查询使用的获取数据的方法不一样，所以需要分开写。
     * 所以就把onError的方法写入到子类当中
     * */
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        List<PinBuWordBean.ResultBean.ListBean> list = DBManage.queryPyWordFromPywordtb(word, page, pagesize);
        refreshDataByGV(list);
    }
}
