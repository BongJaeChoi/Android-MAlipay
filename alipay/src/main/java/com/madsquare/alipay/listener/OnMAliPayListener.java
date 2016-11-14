package com.madsquare.alipay.listener;

/**
 * Created by choebongjae on 2016. 11. 8..
 */

public interface OnMAliPayListener {
    void OnResult(int resultCode, boolean isSDKChecked, Object objMsg);
}
