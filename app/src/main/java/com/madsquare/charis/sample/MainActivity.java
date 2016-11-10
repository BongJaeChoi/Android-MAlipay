package com.madsquare.charis.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.madsquare.alipay.MAlipay;
import com.madsquare.alipay.PayInterface;

public class MainActivity extends AppCompatActivity {

    MAlipay mAlipay;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAlipay = MAlipay.getInstance(MainActivity.this);
        mAlipay.pay("test", "test desc", "0.01");

        mAlipay.setPayInfo(new PayInterface() {
            @Override
            public void OnResult(int resultCode, boolean isSDKChecked, Object msgObj) {
                Log.e(TAG, "OnResult() called with: resultCode = [" + resultCode + "], isSDKChecked = [" + isSDKChecked + "], msgObj = [" + msgObj + "]");
            }
        });

    }
}
