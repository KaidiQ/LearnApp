package uk.ac.tees.a0547574.learn_chinese_app;

import uk.ac.tees.a0547574.learn_chinese_app.Database.UserDBhelp;
import uk.ac.tees.a0547574.learn_chinese_app.Info.UserInfo;
import uk.ac.tees.a0547574.learn_chinese_app.Util.DateUtil;
import uk.ac.tees.a0547574.learn_chinese_app.Util.ViewUtil;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginSQLiteActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{


        private RadioGroup rg_login;
        private RadioButton rb_password;
        private RadioButton rb_verifycode;
        private EditText et_phone;
        private TextView tv_password;
        private EditText et_password;
        private Button btn_forget;
        private CheckBox ck_remember;
        private Button btn_login;

        private int mRequestCode = 0;
        private int mType = 0;
        private boolean bRemember = false;
        private String mPassword = "111111";
        private String mVerifyCode;
        private UserDBhelp mHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            rg_login = (RadioGroup) findViewById(R.id.rg_login);
            rb_password = (RadioButton) findViewById(R.id.rb_password);
            rb_verifycode = (RadioButton) findViewById(R.id.rb_verifycode);
            et_phone = (EditText) findViewById(R.id.et_phone);
            tv_password = (TextView) findViewById(R.id.tv_password);
            et_password = (EditText) findViewById(R.id.et_password);
            btn_forget = (Button) findViewById(R.id.btn_forget);
            ck_remember = (CheckBox) findViewById(R.id.ck_remember);
            btn_login = (Button) findViewById(R.id.btn_login);

            rg_login.setOnCheckedChangeListener(new RadioListener());
            ck_remember.setOnCheckedChangeListener(new CheckListener());
            et_phone.addTextChangedListener(new HideTextWatcher(et_phone));
            et_password.addTextChangedListener(new HideTextWatcher(et_password));
            btn_forget.setOnClickListener(this);
            btn_login.setOnClickListener(this);
            et_password.setOnFocusChangeListener(this);

            ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
                    R.layout.item_select, typeArray);
            typeAdapter.setDropDownViewResource(R.layout.item_dropdown);
            Spinner sp_type = (Spinner) findViewById(R.id.sp_type);
            sp_type.setPrompt("Please select a user type");
            sp_type.setAdapter(typeAdapter);
            sp_type.setSelection(mType);
            sp_type.setOnItemSelectedListener(new TypeSelectedListener());
        }

        private class RadioListener implements RadioGroup.OnCheckedChangeListener {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_password) {
                    tv_password.setText("Login password：");
                    et_password.setHint("Please enter your password");
                    btn_forget.setText("Forget password");
                    ck_remember.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rb_verifycode) {
                    tv_password.setText("　Verification code：");
                    et_password.setHint("Please enter the verification code");
                    btn_forget.setText("Get Verification code");
                    ck_remember.setVisibility(View.INVISIBLE);
                }
            }
        }

        private String[] typeArray = {"Individual User", "Company User"};
        class TypeSelectedListener implements AdapterView.OnItemSelectedListener {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mType = arg2;
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        }

        private class CheckListener implements CompoundButton.OnCheckedChangeListener {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getId() == R.id.ck_remember) {
                    bRemember = isChecked;
                }
            }
        }

        private class HideTextWatcher implements TextWatcher {
            private EditText mView;
            private int mMaxLength;
            private CharSequence mStr;

            public HideTextWatcher(EditText v) {
                super();
                mView = v;
                mMaxLength = ViewUtil.getMaxLength(v);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStr = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mStr == null || mStr.length() == 0)
                    return;
                if ((mStr.length() == 11 && mMaxLength == 11) ||
                        (mStr.length() == 6 && mMaxLength == 6)) {
                    ViewUtil.hideOneInputMethod(LoginSQLiteActivity.this, mView);
                }
            }
        }

        @Override
        public void onClick(View v) {
            String phone = et_phone.getText().toString();
            if (v.getId() == R.id.btn_forget) {
                if (phone==null || phone.length()<11) {
                    Toast.makeText(this, "Please enter phone number correctly", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rb_password.isChecked() == true) {
                    Intent intent = new Intent(this, RegisterActivity.class);
                    intent.putExtra("phone", phone);
                    startActivityForResult(intent, mRequestCode);
                } else if (rb_verifycode.isChecked() == true) {
                    mVerifyCode = String.format("%06d", (int)(Math.random()*1000000%1000000));
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Please remember the code");
                    builder.setMessage("Mobile NO"+phone+"，This verification code is"+mVerifyCode+"，Please enter the code");
                    builder.setPositiveButton("OK", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } else if (v.getId() == R.id.btn_login) {
                if (phone==null || phone.length()<11) {
                    Toast.makeText(this, "Please enter the correct phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rb_password.isChecked() == true) {
                    if (et_password.getText().toString().equals(mPassword) != true) {
                        Toast.makeText(this, "Please enter the correct password", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        loginSuccess();
                    }
                } else if (rb_verifycode.isChecked() == true) {
                    if (et_password.getText().toString().equals(mVerifyCode) != true) {
                        Toast.makeText(this, "Please enter the correct verification code", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        loginSuccess();
                    }
                }
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == mRequestCode && data!=null) {
                //用户密码已改为新密码
                mPassword = data.getStringExtra("new_password");
            }
        }

        //从修改密码页面返回登录页面，要清空密码的输入框
        @Override
        protected void onRestart() {
            et_password.setText("");
            super.onRestart();
        }

        @Override
        protected void onResume() {
            super.onResume();
            mHelper = UserDBhelp.getInstance(this, 2);
            mHelper.openWriteLink();
        }

        @Override
        protected void onPause() {
            super.onPause();
            mHelper.closeLink();
        }

        private void loginSuccess() {
            String desc = String.format("Your cell phone number is %s and type is %s.Congratulations on passing the login verification, click \"OK\" button to enter the learning interface.",
                    et_phone.getText().toString(), typeArray[mType]);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Login Successfully");
            builder.setMessage(desc);
            builder.setPositiveButton("Definite Entry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setClass(LoginSQLiteActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Later", null);
            AlertDialog alert = builder.create();
            alert.show();

            if (bRemember) {
                UserInfo info = new UserInfo();
                info.phone = et_phone.getText().toString();
                info.password = et_password.getText().toString();
                info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
                mHelper.insert(info);
            }
        }

        //为什么光标进入密码框事件不选onClick？因为要点两下才会触发onClick动作（第一下是切换焦点动作）
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            String phone = et_phone.getText().toString();
            if (v.getId() == R.id.et_password) {
                if (phone.length() > 0 && hasFocus == true) {
                    UserInfo info = mHelper.queryByPhone(phone);
                    if (info != null) {
                        et_password.setText(info.password);
                    }
                }
            }
        }

    }

