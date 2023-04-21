package uk.ac.tees.a0547574.learnchineseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

public class PoemActivity extends AppCompatActivity {

    //需要适配的数据
    private String[] titles = {"望庐山瀑布", "静夜思", "春晓", "山行", "独坐敬亭山", "咏柳"};
    private String[] prices = {"日照香炉生紫烟，遥看瀑布挂前川。飞流直下三千尺，疑是银河落九天。", "床前明月光，疑是地上霜。举头望明月，低头思故乡。", "春眠不觉晓，处处闻啼鸟。夜来风雨声，花落知多少。", "远上寒山石径斜，白云深处有人家。停车坐爱枫林晚，霜叶红于二月花。", "众鸟高飞尽，孤云独去闲。相看两不厌，只有敬亭山。", "碧玉妆成一树高，万条垂下绿丝绦。不知细叶谁裁出，二月春风似剪刀。"};
    //图片集合
    private int[] icons = {R.drawable.poem1, R.drawable.poem2, R.drawable.poem3,
            R.drawable.poem4, R.drawable.poem5, R.drawable.poem6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poem_main);

        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    case R.id.dictionary:
                        intent.setClass(PoemActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.transition:
                        intent.setClass(PoemActivity.this,NotepadActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.learn:
                        intent.setClass(PoemActivity.this,PoemActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.user:
                        intent.setClass(PoemActivity.this,UserActivity.class);
                        startActivity(intent);
                        break;


                }

                return true;    //这里返回true，表示事件已经被处理。如果返回false，为了达到条目选中效果，还需要下面的代码
                // item.setChecked(true);  不论点击了哪一个，都手动设置为选中状态true（该控件并没有默认实现)
                // 。如果不设置，只有第一个menu展示的时候是选中状态，其他的即便被点击选中了，图标和文字也不会做任何更改
            }

        });
        bnv.getMenu().getItem(2).setChecked(true);

        //初始化ListView控件
        ListView listView = findViewById(R.id.lv);
        //创建一个Adapter的实例
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        //设置Adapter
        listView.setAdapter(mAdapter);


    }

    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {       //得到item的总数
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position]; //返回item的数据对象
        }

        @Override
        public long getItemId(int position) {
            return position;         //返回item的id
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {//获取item中的View视图
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(PoemActivity.this, R.layout.poem_list_sample, null);
                holder = new ViewHolder();
                holder.title = convertView.findViewById(R.id.title);
                holder.price = convertView.findViewById(R.id.price);
                holder.iv = convertView.findViewById(R.id.iv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(titles[position]);
            holder.price.setText(prices[position]);
            holder.iv.setImageResource(icons[position]);
            return convertView;
        }
    }

    class ViewHolder {
        TextView title;
        TextView price;
        ImageView iv;
    }
}