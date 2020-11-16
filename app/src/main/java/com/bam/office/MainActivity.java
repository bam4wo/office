package com.bam.office;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static String mDeviceIMEI = "0";
    TelephonyManager mTelephonyManager = null;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText password = findViewById(R.id.ed_password);
        CheckBox cb = findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    //顯示密碼
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //隱藏密碼
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
    }

    public void login(View view){
        EditText email = findViewById(R.id.ed_email);
        EditText password = findViewById(R.id.ed_password);
        String eemail = email.getText().toString();
        String pass = password.getText().toString();
        if(eemail.equals("wubetty2012@gmail.com") && pass.equals("123123")){
            setResult(RESULT_OK);
            finish();
            Intent intent = new Intent(MainActivity.this,PrimaryActivity.class);
            startActivity(intent);
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("登入失敗")
                    .setMessage("員工編號/Email或密碼錯誤!")
                    .setPositiveButton("OK",null)
                    .show();
        }
    }

    public void signup(View view){
        Intent intent2 = new Intent(MainActivity.this,SignupActivity.class);
        startActivity(intent2);
    }


    private void getDeviceImei() {
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if(Build.VERSION.SDK_INT >= 26) {
                mDeviceIMEI = mTelephonyManager.getImei();
            }else {
                mDeviceIMEI = mTelephonyManager.getDeviceId();
            }
        } catch (SecurityException e) {
            Log.d(TAG, "SecurityException e");
        }
        if ( ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getDeviceImei();
                }
            }
        }
    }


}


