package com.madsquare.alipay;

import android.content.Context;

/**
 * Created by choebongjae on 2016. 11. 8..
 */

public class AliConfig {
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    public static final String TRANSACTION_TIMEOUT = "30M";
    public static final String EXTERNAL_TOKEN = "";
    public static final String INPUT_CHARSET = "UTF-8";

    public static final String NOTIFY_URL = "http://notify.msp.hk/notify.htm";
    public static final String RETURN_URL = "m.alipay.com";
    public static final String INTERFACE_NAME = "mobile.securitypay.pay";
    public static final String PAYMENT_TYPE = "1";
    public static final String EXPRESS_GATEWAY = "expressGateway";

    static String getPartnerID(Context context) {
        return context.getString(R.string.partner_id);
    }
    static String getRsaPrivateKey(Context context){
        return context.getString(R.string.rsa_key);
    }
    static String getSeller(Context context){
        return context.getString(R.string.seller);
    }
}
