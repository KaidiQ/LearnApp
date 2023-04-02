package uk.ac.tees.a0547574.learnchineseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import uk.ac.tees.a0547574.learn_chinese_app.adapter.NotepadAdapter;
import uk.ac.tees.a0547574.learn_chinese_app.Info.NotepadBean;
import uk.ac.tees.a0547574.learn_chinese_app.Database.SQLiteHelper;

public class UserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);





        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    case R.id.dictionary:
                        intent.setClass(UserActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.transition:
                        intent.setClass(UserActivity.this,NotepadActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.user:
                        intent.setClass(UserActivity.this,UserActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;    //这里返回true，表示事件已经被处理。如果返回false，为了达到条目选中效果，还需要下面的代码
                // item.setChecked(true);  不论点击了哪一个，都手动设置为选中状态true（该控件并没有默认实现)
                // 。如果不设置，只有第一个menu展示的时候是选中状态，其他的即便被点击选中了，图标和文字也不会做任何更改
            }


        });
        bnv.getMenu().getItem(3).setChecked(true);
    }

}
