package com.madsquare.alipay;

import android.content.Context;

/**
 * Created by choebongjae on 2016. 11. 8..
 */

public class AliConfig {
    //비즈니스 PID 유니크한 알리페이 유저넘버
    public static final String PARTNER = "2088421322042198";
    // 판매자 미수금
    public static final String SELLER = "charis@madsq.net";


    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    public static final String TRANSACTION_TIMEOUT = "30M";
    public static final String EXTERNAL_TOKEN = "";
    public static final String INPUT_CHARSET = "UTF-8";


    public static final String NOTIFY_URL = "http://notify.msp.hk/notify.htm";
    public static final String RETURN_URL = "m.alipay.com";
    public static final String INTERFACE_NAME = "mobile.securitypay.pay";
    public static final String PAYMENT_TYPE = "1";
    public static final String EXPRESS_GATEWAY = "expressGateway";

    public static String getPartnerID(Context context) {
        return context.getString(R.string.partner_id);
    }
    public static String getRsaPrivateKey(Context context){
        return context.getString(R.string.rsa_key);
    }
}
