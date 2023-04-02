package uk.ac.tees.a0547574.learnchineseapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements OnClickListener {

    private EditText et_password_first;
    private EditText et_password_second;
    private EditText et_verifycode;
    private String mVerifyCode;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_password_first = (EditText) findViewById(R.id.et_password_first);
        et_password_second = (EditText) findViewById(R.id.et_password_second);
        et_verifycode = (EditText) findViewById(R.id.et_verifycode);
        findViewById(R.id.btn_verifycode).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        mPhone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_verifycode) {
            if (mPhone==null || mPhone.length()<11) {
                Toast.makeText(this, "Please enter correct phone numbers", Toast.LENGTH_SHORT).show();
                return;
            }
            mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please remember the verification code");
            builder.setMessage("Phone numbers"+mPhone+"，This verification code is"+mVerifyCode+"，Please enter the verification code");
            builder.setPositiveButton("Okay", null);
            AlertDialog alert = builder.create();
            alert.show();
        } else if (v.getId() == R.id.btn_confirm) {
            String password_first = et_password_first.getText().toString();
            String password_second = et_password_second.getText().toString();
            if (password_first==null || password_first.length()<6 ||
                    password_second==null || password_second.length()<6) {
                Toast.makeText(this, "Please enter your new password correctly", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password_first.equals(password_second) != true) {
                Toast.makeText(this, "\n" + "Entered passwords differ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (et_verifycode.getText().toString().equals(mVerifyCode) != true) {
                Toast.makeText(this, "Please enter the correct verification code", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(this, "Successful registration", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("new_password", password_first);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

}
