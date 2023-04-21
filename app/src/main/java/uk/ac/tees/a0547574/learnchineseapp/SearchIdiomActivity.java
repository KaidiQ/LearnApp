package uk.ac.tees.a0547574.learnchineseapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;

import uk.ac.tees.a0547574.learnchineseapp.Database.DBManage;

import java.util.ArrayList;
import java.util.List;

public class SearchIdiomActivity extends AppCompatActivity {
    EditText cyEt;
    GridView cyGv;
    List<String> mDatas;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_idiom);
        cyEt = findViewById(R.id.searchcy_et);
        cyGv = findViewById(R.id.searchcy_gv);
        mDatas = new ArrayList<>();
//        创建适配器对象Creating an adapter object
        adapter = new ArrayAdapter<>(this, R.layout.item_searchcy_gv, R.id.item_searchcy_tv, mDatas);
        cyGv.setAdapter(adapter);
//        设置GridView的点击1事件Set the GridView's Click 1 event
        setGVListener();
    }
    /* GridView每一个Item点击事件的方法GridView method for each Item click event*/
    private void setGVListener() {
        cyGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = mDatas.get(position);
                startPage(msg);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cyEt.setText("");
        initDatas();
    }
    /**
     * 初始化GridView显示的历史记录数据Initialize the history data displayed by the GridView
     * */
    private void initDatas() {
        mDatas.clear();
        List<String> list = DBManage.queryAllCyFromCyutb();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.searchcy_iv_back:
                finish();
                break;
            case R.id.searchcy_iv_search:
                String text = cyEt.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    return;
                }
                //跳转到成语详情页面，将输入内容传递过去Jump to the idiom details page and pass the input
                startPage(text);
                break;
        }
    }
    /* 携带成语跳转到下一个页面
     Carry the idiom to the next page
    * */
    private void startPage(String text) {
        Intent intent = new Intent(this, IdiomInfoActivity.class);
        intent.putExtra("chengyu",text);
        startActivity(intent);
    }
}

