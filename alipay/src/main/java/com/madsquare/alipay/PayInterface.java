package com.madsquare.alipay;

/**
 * Created by choebongjae on 2016. 11. 8..
 */

public interface PayInterface {
    void OnResult(int resultCode, boolean isSDKChecked,Object msgObj);
}
