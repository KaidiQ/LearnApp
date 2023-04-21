package uk.ac.tees.a0547574.learnchineseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import uk.ac.tees.a0547574.learnchineseapp.Database.SQLiteHelper;
import uk.ac.tees.a0547574.learnchineseapp.Util.DBUtils;


public class RecordActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView note_back;
    TextView note_time;
    EditText content;
    ImageView delete;
    ImageView note_save;
    SQLiteHelper mSQLiteHelper;
    TextView noteName;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        init(); //初始化控件
        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);
        initData(); //获取数据库工具类
    }

    //初始化控件
    protected void init(){
        note_back=findViewById(R.id.note_back);
        note_time=findViewById(R.id.tv_time);
        content=findViewById(R.id.note_content);
        delete=findViewById(R.id.delete);
        note_save=findViewById(R.id.note_save);
        noteName=findViewById(R.id.note_name);
    }

    //获取到数据库
    protected void initData(){
        mSQLiteHelper=new SQLiteHelper(this);
        noteName.setText("添加记录");
        Intent intent=getIntent();
        if (intent!=null){
            id=intent.getStringExtra("id");
            if(id!=null){
                noteName.setText("修改记录");
                content.setText(intent.getStringExtra("content"));
                note_time.setText(intent.getStringExtra("time"));
                note_time.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.note_back:    //后退按钮的点击事件
                finish();
                break;
            case R.id.delete:   //”清空“按钮的点击事件
                content.setText("");
                break;
            case R.id.note_save:    //”保存“按钮的点击事件
                //获取输入内容
                String noteContent=content.getText().toString().trim();
                if(id!=null){   //修改数据
                    if(noteContent.length()>0){
                        if(mSQLiteHelper.updateData(id,noteContent,DBUtils.getTime())){
                            showToast("修改成功");
                            setResult(2);
                            finish();
                        }else{
                            showToast("修改失败");
                        }
                    }else{
                        showToast("修改后内容不能为空！");
                    }
                }else {
                    //向数据库中添加数据
                    if(noteContent.length()>0){
                        if (mSQLiteHelper.insertData(noteContent, DBUtils.getTime())){
                            showToast("保存成功");
                            setResult(2);
                            finish();
                        }else {
                            showToast("保存失败");
                        }
                    }else{
                        showToast("添加内容不能为空！");
                    }
                }

                break;
        }
    }
    public void showToast(String message){
        Toast.makeText(RecordActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
