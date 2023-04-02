package uk.ac.tees.a0547574.learnchineseapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import uk.ac.tees.a0547574.learn_chinese_app.adapter.NotepadAdapter;
import uk.ac.tees.a0547574.learn_chinese_app.Info.NotepadBean;
import uk.ac.tees.a0547574.learn_chinese_app.Database.SQLiteHelper;

import java.util.List;

public class NotepadActivity extends AppCompatActivity {
    ListView listView;
    List<NotepadBean> list;
    SQLiteHelper mSQLiteHelper;
    NotepadAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        //用于显示记录的列表
        listView=(ListView) findViewById(R.id.listview);
        ImageView add=(ImageView) findViewById(R.id.add);
        //点击添加记录按钮触发的事件
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到添加记录的页面
                Intent intent=new Intent(NotepadActivity.this,RecordActivity.class);
                startActivityForResult(intent,1);
            }
        });


        initData();

        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    case R.id.dictionary:
                        intent.setClass(NotepadActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.user:
                        intent.setClass(NotepadActivity.this,UserActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;    //这里返回true，表示事件已经被处理。如果返回false，为了达到条目选中效果，还需要下面的代码
                // item.setChecked(true);  不论点击了哪一个，都手动设置为选中状态true（该控件并没有默认实现)
                // 。如果不设置，只有第一个menu展示的时候是选中状态，其他的即便被点击选中了，图标和文字也不会做任何更改
            }


        });
        bnv.getMenu().getItem(1).setChecked(true);
    }

    protected void initData(){
        mSQLiteHelper=new SQLiteHelper(this);   //创建一个数据库
        showQueryData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //点击记录触发事件：修改记录
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                NotepadBean notepadBean=list.get(position);
                Intent intent=new Intent(NotepadActivity.this,RecordActivity.class);
                intent.putExtra("id",notepadBean.getId());  //记录点击的item的id
                intent.putExtra("time",notepadBean.getNotepadTime());   //记录点击的item的时间
                intent.putExtra("content",notepadBean.getNotepadContent()); //记录点击的item的内容
                NotepadActivity.this.startActivityForResult(intent,1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog alertDialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(NotepadActivity.this)
                        .setMessage("是否删除该记录？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NotepadBean notepadBean=list.get(position);
                                if(mSQLiteHelper.deleteData(notepadBean.getId())){
                                    list.remove(position);  //删除对应的item
                                    adapter.notifyDataSetChanged(); //更新记事本界面
                                    Toast.makeText(NotepadActivity.this,"删除成功",Toast.LENGTH_SHORT).show();

                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                alertDialog=builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    //这个方法就是更新显示记事本数据
    private void showQueryData(){
        if(list!=null){
            list.clear();
        }
        //从数据库中查询数据
        list=mSQLiteHelper.query();
        adapter=new NotepadAdapter(this,list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            showQueryData();
        }
    }
}
