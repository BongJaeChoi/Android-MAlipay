package com.madsquare.alipay;


import android.app.Activity;
import android.util.Log;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by choebongjae on 2016. 11. 8..
 */

public class AliUtil {
    private static final String TAG = AliUtil.class.getSimpleName();
    private static final String ALGORITHM = "RSA";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final String DEFAULT_CHARSET = "UTF-8";
    /**
     * get the out_trade_no for an order.
     * Unique order ID in merchant’s website of Alipay
     * 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * 알리페이 서버로보낼 파라미터
     *
     * @param productName   제품(주문) 타이틀 - Product title/transaction tile/order title/order keywords, etc. The length of this parameter is up to 128 Chinese characters.
     * @param productDetail 제품설명 - Specific description of the transaction. In case of a variety of goods, please accumulate the character strings descrbing the goods, and transmit the same to body.
     * @param price         총 가격 - A floating number ranging 0.01～1000000.00, specify the foreign price of the items If use the total_fee, don’t use the rmb_fee
     * @return
     */
    public static String getOrderInfo(String productName, String productDetail, String price, Activity activity) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + AliConfig.getPartnerID(activity) + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + AliConfig.getSeller(activity) + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + productName + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + productDetail + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + AliConfig.NOTIFY_URL+ "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"" + AliConfig.INTERFACE_NAME + "\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"" + AliConfig.PAYMENT_TYPE + "\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"" + AliConfig.INPUT_CHARSET + "\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"" + AliConfig.TRANSACTION_TIMEOUT + "\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
//        orderInfo += "&extern_token=" + "\"" + AliConfig.EXTERNAL_TOKEN + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"" + AliConfig.RETURN_URL + "\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
//        orderInfo += "&paymethod=\"" + AliConfig.EXPRESS_GATEWAY + "\"";

        Log.e(TAG, "getOrderInfo: " + orderInfo);

        return orderInfo;
    }
    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            Log.e(TAG, "sign: private key  : "+privateKey);
            Log.e(TAG, "sign: priPKCS "+priPKCS8);
            Log.e(TAG, "sign: content"+ content);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
