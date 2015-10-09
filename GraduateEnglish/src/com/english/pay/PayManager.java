package com.english.pay;

import android.content.Context;
import android.widget.Toast;

import com.english.English;
import com.english.config.Profile;
import com.english.util.Logger;
import com.english.util.Util;
import com.wanpu.pay.PayConnect;
import com.wanpu.pay.PayResultListener;

/**
 * 支付管理类
 * Created by vic_ma on 15/10/9.
 */
public class PayManager {
    private static final Object mObject = new Object();
    private static final String TAG = PayManager.class.getSimpleName();
    public static PayManager mPayManager = null;

    public static PayManager getInstance() {
        if (mPayManager == null) {
            synchronized (mObject) {
                if (mPayManager == null) {
                    mPayManager = new PayManager();
                }
            }
        }
        return mPayManager;
    }

    /**
     * 初始化支付
     */
    public void init() {
        String appID = Util.getAppID();
        Logger.d(TAG, "PayManager#init() app id is " + appID);
    }

    /**
     * 支付
     */
    public void pay(float price,Context context) {
        PayConnect.getInstance(context).pay(context,
                Util.generateOrderId(), Util.generateUserId(), price,
                Profile.GOODS_ITEM_NAME, Profile.GOODS_ITEM_DESCRIPTION,
                "", new MyPayResultListener());
    }

    public class MyPayResultListener implements PayResultListener {
        @Override
        public void onPayFinish(Context payViewContext, String order_id, int resultCode, String resultString, int payType, float amount, String goods_name) {
            if (resultCode == 0) { // 支付成功
                Logger.d(TAG,"order_id=" + order_id +
                             "resultCode=" + resultCode +
                             "payType=" + payType +
                            "resultString=" + resultString + ":" + amount + "元"+
                            "goods_name=" + goods_name);
                // 支付成功时关闭当前支付界面
                PayConnect.getInstance(payViewContext).closePayView(payViewContext);
                // TODO 在客户端处理支付成功的操作
                // 未指定 notifyUrl 的情况下,交易成功后,必须发送回执
                PayConnect.getInstance(payViewContext).confirm(order_id, payType);
            }else{// 支付失败
                Toast.makeText(English.mContext, resultString, Toast.LENGTH_LONG).show();
            }
        }
    }

}
