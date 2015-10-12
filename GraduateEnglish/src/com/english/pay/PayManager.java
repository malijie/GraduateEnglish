package com.english.pay;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.english.English;
import com.english.util.Logger;
import com.english.util.SharedPreferenceUtil;
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
    private static Context mContext = null;
    //支付结果消息－－失败
    private static final int PAY_RESULT_MSG_FAILED = 0;

    //支付结果消息－－成功
    private static final int PAY_RESULT_MSG_SUCCESS = 1;

    //支付结果  0代表成功，-1代表失败
    private int mPayResultCode = -1;

    //支付商品类型
    private int mPayGoodsType;

    public PayManager(Context context){
        mContext = context;
    }

    /**
     * 初始化支付
     */
    public static void init(Context context) {
        PayConnect.getInstance(PayConst.APP_ID, PayConst.APP_ID, context);
    }

    /**
     *
     * @param type 支付类型
     * @param price 价格
     */
    public void handlePayEvent(int type, float price) {
        //设置当前支付类型
        setPayGoodsType(type);
        switch (type){
            case PayConst.PAY_TYPE_READING:
                pay(mContext, PayConst.GOODS_ITEM_NAME, PayConst.GOODS_ITEM_DESCRIPTION, price);
                break;
            case PayConst.PAY_TYPE_WRITTING:
                break;
        }

    }

    /**
     * 设置支付商品类型
     * 目前包括阅读，写作
     */
    private void setPayGoodsType(int type){
        mPayGoodsType = type;
    }

    /**
     * 获取支付商品类型
     * @return
     */
    private int getPayGoodsType(){
        return mPayGoodsType;
    }

    /**
     * 支付
     * @param context
     * @param goodsName  商品名称
     * @param goodsDescription 商品描述
     * @param price
     */
    private void pay(Context context,String goodsName, String goodsDescription, float price){
        PayConnect.getInstance(context).pay(context,Util.generateOrderId(), Util.generateUserId(),
                                            price,goodsName,goodsDescription,"", new MyPayResultListener());
    }

    /**
     *保存阅读支付结果
     */
    private void savePayReadingResult(){
       if(mPayResultCode==0){
           SharedPreferenceUtil.saveReadingPayResult(mContext,true);
       }else{
           SharedPreferenceUtil.saveReadingPayResult(mContext, false);
       }
        mPayResultCode = -1;
    }

    private class PayResultHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //支付成功
                case PAY_RESULT_MSG_SUCCESS:
                    //根据不同的支付类型保存相应的支付结果
                    if(getPayGoodsType() == PayConst.PAY_TYPE_READING){
                        savePayReadingResult();
                    }else if(getPayGoodsType() == PayConst.PAY_TYPE_WRITTING){

                    }

                    break;
                //支付失败
                case PAY_RESULT_MSG_FAILED:
                    Toast.makeText(English.mContext, "支付失败，请重试...", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    /**
     * 支付结果回调
     */
    public class MyPayResultListener implements PayResultListener {
        @Override
        public void onPayFinish(Context payViewContext, String order_id, int resultCode, String resultString, int payType, float amount, String goods_name) {
            mPayResultCode = resultCode;
            if (resultCode == 0) { // 支付成功
                Logger.d(TAG, "order_id=" + order_id +
                        "resultCode=" + resultCode +
                        "payType=" + payType +
                        "resultString=" + resultString + ":" + amount + "元" +
                        "goods_name=" + goods_name);
                // 支付成功时关闭当前支付界面
                PayConnect.getInstance(payViewContext).closePayView(payViewContext);

                // TODO 在客户端处理支付成功的操作

                // 未指定 notifyUrl 的情况下,交易成功后,必须发送回执
                PayConnect.getInstance(payViewContext).confirm(order_id, payType);
                //处理支付成功操作
                new PayResultHandler().sendEmptyMessage(PAY_RESULT_MSG_SUCCESS);

            }else{// 支付失败
                new PayResultHandler().sendEmptyMessage(PAY_RESULT_MSG_FAILED);
            }
        }
    }

    /**
     * 是否完成了阅读支付
     * @return
     */
    public static boolean isCompleteReadingPay(){
        return SharedPreferenceUtil.getReadingPayResult(English.mContext);
    }

}
