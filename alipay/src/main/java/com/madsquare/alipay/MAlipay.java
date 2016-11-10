package com.madsquare.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.madsquare.alipay.AliConfig.getRsaPrivateKey;

/**
 * Created by choebongjae on 2016. 11. 8..
 */
public class MAlipay {
    private static MAlipay ourInstance = new MAlipay();
    private static final String TAG = MAlipay.class.getSimpleName();
    private Activity mActivity;


    /*ALIPAY SDK FLAG CODE*/
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    /*ALIPAY RESULT CODE*/
    private static final int CODE_SUCCESS = 9000;
    private static final int CODE_UNDER_PROCESSING = 8000;
    private static final int CODE_ORDER_FAIL = 4000;
    private static final int CODE_USER_CANCEL = 6001;
    private static final int CODE_NETWORK_ERROR = 6002;
    private static final int CODE_CHECK_FLAG = -1;


    private PayInterface mPayInterface;

    public static MAlipay getInstance(Activity activity) {
        if (ourInstance == null) {
            ourInstance = new MAlipay();
        }
            ourInstance.mActivity = activity;
        return ourInstance;
    }

    private MAlipay() {
        mPayInterface = null;
    }

    public void setPayInfo(PayInterface payInterface) {
        this.mPayInterface = payInterface;
    }

    /**
     * 받아온 파라미터(orderinfo)를 가지고 알리페이 서버에 전달
     * 서브쓰레드를 만들어서 돌려야하며 핸들러를 통해 메인쓰레드에 전달 하는방식으로 구현해야함(Must!).
     *
     * @param productName   제품(주문) 타이틀 - Product title/transaction tile/order title/order keywords, etc. The length of this parameter is up to 128 Chinese characters.
     * @param productDetail 제품설명 - Specific description of the transaction. In case of a variety of goods, please accumulate the character strings descrbing the goods, and transmit the same to body.
     * @param price         총 가격 - A floating number ranging 0.01～1000000.00, specify the foreign price of the items If use the total_fee, don’t use the rmb_fee
     */
    public void pay(String productName, String productDetail, String price) {
        String orderInfo = AliUtil.getOrderInfo(productName, productDetail, price);
        String sign = sign(orderInfo);
        Log.e(TAG, "pay: orderinfo : "+orderInfo );
        Log.e(TAG, "pay: sign sign : "+sign );
        try {
            // 그냥 URL 인코딩을 수행 할에 서명
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 구조PayTask 사물
                PayTask alipay = new PayTask(mActivity);
                // 결제의 결과를 얻기 위해 지불 인터페이스를 호출
                String result = alipay.pay(payInfo);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;

                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {

                    PayResult payResult = new PayResult((String) msg.obj);

                    String resultStatus = payResult.getResultStatus();

                    if (TextUtils.equals(resultStatus, "9000")) {
                        showErrorMessage();

                        //주문 결제 성공
                        Log.d(TAG, "handleMessage: pay success");
                        mPayInterface.OnResult(CODE_SUCCESS, true, payResult.getMemo());
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            showErrorMessage();

                            //under processing 주문 진행중...
                            Log.d(TAG, "handleMessage: pay under processing");
                            mPayInterface.OnResult(CODE_UNDER_PROCESSING, true, payResult.getMemo());
                        } else if (TextUtils.equals(resultStatus, "4000")) {
                            showErrorMessage();

                            //주문 결제 실패
                            Log.e(TAG, "handleMessage: order fail");
                            mPayInterface.OnResult(CODE_ORDER_FAIL, true, payResult.getMemo());
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            showErrorMessage();

                            //사용자가 결제 취소
                            mPayInterface.OnResult(CODE_USER_CANCEL, true, payResult.getMemo());
                            Log.e(TAG, "handleMessage: user cancle order");
                        } else if (TextUtils.equals(resultStatus, "6002")) {
                            showErrorMessage();

                            //네트워크 문제
                            mPayInterface.OnResult(CODE_NETWORK_ERROR, true, payResult.getMemo());
                            Log.e(TAG, "handleMessage: network error");
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    //sdk 확인해야함
                    mPayInterface.OnResult(CODE_CHECK_FLAG, false, msg.obj);
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void showErrorMessage() {
        if (mPayInterface == null)
            throw new NullPointerException("You must set Listener");
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return AliUtil.sign(content, getRsaPrivateKey(mActivity));
    }

    /**
     * get the sign type we use. 获取签名方式
     * only support RSA type
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * 알리페이 계정이 디바이스에 체크되어있는지를 판단 후 핸들러를 통해 데이터 전달
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     */
    public void check(View v) {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(mActivity);
                // 调用查询接口，获取查询结果
                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();

    }
}
