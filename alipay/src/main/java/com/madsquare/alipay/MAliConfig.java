package com.madsquare.alipay;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by choebongjae on 2016. 11. 8..
 */

class MAliConfig {
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    static final String TRANSACTION_TIMEOUT = "30M";
    public static final String EXTERNAL_TOKEN = "";
    static final String INPUT_CHARSET = "UTF-8";

    static final String NOTIFY_URL = "beta.hicharis.net/api/alipay/mobile/test";
    static final String RETURN_URL = "m.alipay.com";
    static final String INTERFACE_NAME = "mobile.securitypay.pay";
    static final String PAYMENT_TYPE = "1";
    static final String EXPRESS_GATEWAY = "expressGateway";

    @NonNull
    static String getPartnerID(Context context) {
        return context.getString(R.string.partner_id);
    }
    @NonNull
    static String getRsaPrivateKey(Context context){
        return context.getString(R.string.rsa_key);
    }
    @NonNull
    static String getSeller(Context context){
        return context.getString(R.string.seller);
    }
    @NonNull
    static String getAppId(Context context){
        return context.getString(R.string.application_id);
    }
}
