package com.madsquare.charis.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.madsquare.alipay.MAliPay;
import com.madsquare.alipay.listener.OnMAliPayListener;

public class MainActivity extends AppCompatActivity {

    MAliPay mAliPay;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAliPay = MAliPay.getInstance(MainActivity.this);
        mAliPay.check();
        mAliPay.pay("test", "test desc", "0.01");

        mAliPay.setPayInfo(new OnMAliPayListener() {
            @Override
            public void OnResult(int resultCode, boolean isSDKChecked, Object objMsg) {
                Log.e(TAG, "OnResult() called with: resultCode = [" + resultCode + "], isSDKChecked = [" + isSDKChecked + "], msgObj = [" + objMsg + "]");
            }
        });

    }
}
