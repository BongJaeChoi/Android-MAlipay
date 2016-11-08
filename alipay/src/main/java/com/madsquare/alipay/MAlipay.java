package com.madsquare.alipay;

/**
 * Created by choebongjae on 2016. 11. 8..
 */
public class MAlipay {
    private static MAlipay ourInstance = new MAlipay();
    private static final String TAG = MAlipay.class.getSimpleName();

    public static MAlipay getInstance() {
        if (ourInstance ==null){
            ourInstance = new MAlipay();
        }
        return ourInstance;
    }

    private MAlipay() {
    }
}
