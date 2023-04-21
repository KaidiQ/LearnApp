package uk.ac.tees.a0547574.learnchineseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.setting_iv_back:
                finish();
                break;
            case R.id.setting_tv_about:

                break;
            case R.id.setting_tv_collect:
                Intent intent = new Intent(this, CollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_tv_feedback:

                break;
            case R.id.setting_tv_good:

                break;
            case R.id.setting_tv_share:
                shareSoftware();
                break;
        }
    }

    private void shareSoftware() {
        // 分享这个软件到其他用户
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String msg = "Want to find the details of Chinese characters and idioms at any time? " +
                "Come and download the Chinese Dictionary APP!";
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        startActivity(Intent.createChooser(intent,"Share to ..."));
    }
}
