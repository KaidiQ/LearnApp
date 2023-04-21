package uk.ac.tees.a0547574.learnchineseapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import uk.ac.tees.a0547574.learnchineseapp.Database.SQLiteHelper;
import uk.ac.tees.a0547574.learnchineseapp.Util.DBUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TranslateActivity  extends AppCompatActivity implements View.OnClickListener{

    int x=1;
    Button jiance;         //对话框选择翻译模式直接将内容传给b.Text,可通过b.getText()获得选项的字符串
    //也可通过对话框中的确定按钮的点击事件，直接添加更换选择后触发的事件。
    Button fanyi;//翻译按钮
    EditText contentEdit;
    TextView resultText;
    ImageView shouCang;
    SQLiteHelper mSQLiteHelper;

    String[] str={"自动检测语言->英文","英文->中文","中文->繁体中文","中文->英语","中文->日语"};
    String[] fromArray={"auto","en","zh","zh","zh"};
    String[] toArray={"en","zh","cht","en","jp"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        jiance=findViewById(R.id.jiance);
        jiance.setOnClickListener(this);
        fanyi=findViewById(R.id.translate);
        resultText=findViewById(R.id.result);
        shouCang=findViewById(R.id.shouCang);
        mSQLiteHelper=new SQLiteHelper(this);
        contentEdit=findViewById(R.id.content);
        ImageView translateBack=findViewById(R.id.translate_back);
        translateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });

        //收藏按钮点击事件
        shouCang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shouCangWord();
            }
        });

        //翻译按钮点击事件
        fanyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contentEdit.getText().toString().equals("")){
                    Toast.makeText(TranslateActivity.this,"翻译内容为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                resultText.setText("翻译中...");
                String neirong=contentEdit.getText().toString();
                int i=0;
                for (i =0;i<str.length;i++){
                    if(str[i]==jiance.getText()){
                        break;
                    }
                }
                if(i>=5){
                    i=0;
                }
                String from=fromArray[i];
                String to=toArray[i];
                //此处添加对翻译内容的处理
                //获取请求字符串
                final String requestString = CallBaiduTranslate.translate(neirong,from,to);
                //Toast.makeText(MainActivity2.this,requestString,Toast.LENGTH_LONG).show();
                //开启线程访问网络
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BufferedReader reader=null;
                        try {
                            URL url = new URL(requestString);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(5000);
                            int responseCode = connection.getResponseCode();    //获取状态码
                            if (responseCode == 200) {
                                InputStream in=connection.getInputStream();
                                reader=new BufferedReader(new InputStreamReader(in));
                                StringBuilder response=new StringBuilder();
                                String line;
                                while((line=reader.readLine())!=null){
                                    response.append(line);
                                }
                                showResponse(response.toString());
                            } else {
                                showResponse("失败");
                            }
                        } catch (Exception e) {
                            showResponse("异常");
                        }
                    }
                }).start();
            }
        });

    }

    private void showResponse(final String msg){
        //回归主线程，对主线程进行UI操作
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作
                try {
                    //解析一下json数据
                    JSONObject resultObject=new JSONObject(msg);
                    JSONArray resultJsonArray=resultObject.optJSONArray("trans_result");
                    JSONObject result=resultJsonArray.getJSONObject(0);
                    String sou=result.optString("src");
                    String res=result.optString("dst");
                    resultText.setText(sou+"\n"+res);
                }catch (Exception e){
                    resultText.setText("UI操作异常");
                }
            }
        });
    }


    //收藏方法
    private void shouCangWord(){
        //获取内容
        //添加到数据库
        if(resultText.length()>0&&!resultText.getText().toString().trim().equals("翻译结果：")){
            if (mSQLiteHelper.insertData(resultText.getText().toString(), DBUtils.getTime())){
                Toast.makeText(TranslateActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(TranslateActivity.this,"收藏失败",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(TranslateActivity.this,"还未翻译",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        AlertDialog dialog;

        AlertDialog.Builder builder=new AlertDialog.Builder(this).setTitle("语言选择").setIcon(R.mipmap.ic_launcher).setSingleChoiceItems(str, x,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        x=i;
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {


            //点击对话框确定按钮后触发的事件
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                jiance.setText(str[x]);
            }

        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            //点击对话框取消按钮触发事件
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setIcon(R.mipmap.trans);
        dialog=builder.create();
        dialog.show();
    }

}
