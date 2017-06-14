package com.heepay.test.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.mergepay.sdk.api.HMApi;
import com.mergepay.sdk.costant.Constant;
import com.mergepay.sdk.utils.DeviceInfoUtil;
import com.mergepay.sdk.utils.HttpUtil;
import com.wangyin.wepay.TradeResultInfo;
import com.wangyin.wepay.WePay;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity implements PaymentTypeAdapter.OnItemClickListener {

    private EditText etAmount;
    private RecyclerView rvPaymentType;
    private String[] mPaymentNames;
    private int[] mPaymentIcons;
    private String serverUrl = "https://pay.heemoney.com/v1/ApplyPay";;

    private static final int REQUEST_SUCCESS = 0x0001;
    private static final int REQUEST_FAIL = 0x0002;
    private static final int REQUEST_ERROR = 0x0003;
    private static final int UN_REQUEST_SUCCESS = 0x0004;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REQUEST_SUCCESS:
                    Log.d("请求成功", msg.obj.toString());
                    HMApi.doPayment(MainActivity.this, msg.obj.toString());
                    break;
                case REQUEST_FAIL:
                    Log.d("请求失败", msg.obj.toString());
                    Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_ERROR:
                    Log.d("请求异常", msg.obj.toString());
                    Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                /**
                 * 测试银联添加
                 */
                case UN_REQUEST_SUCCESS:
                    //银联初始化接口请求成功
                    try {
                        JSONObject object = new JSONObject();
                        object.put(Constant.Response.CHANNEL_TYPE, Constant.ChannelType.UP_APP);
                        object.put(Constant.TEST_ORDERINFO, msg.obj.toString());
                        Log.d("银联支付参数", object.toString());
//                        HMApi.doPayment(MainActivity.this, object.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPaymentIcons = new int[]{R.mipmap.icon_alipay
                , R.mipmap.icon_alipay
                , R.mipmap.icon_wechat
                , R.mipmap.icon_wechat
                , R.mipmap.icon_bdpay
                , R.mipmap.icon_unionpay
                , R.mipmap.icon_qqpay
                , R.mipmap.icon_jdpay
                , R.mipmap.icon_alipay};
        mPaymentNames = new String[]{getResources().getString(R.string.ALI_APP)
                , getResources().getString(R.string.ALI_WAP)
                , getResources().getString(R.string.WX_APP)
                , getResources().getString(R.string.WX_H5)
                , getResources().getString(R.string.bfb)
                , getResources().getString(R.string.unipay)
                , getResources().getString(R.string.qqpay)
                , getResources().getString(R.string.jdpay)
                , getResources().getString(R.string.scanner)};
        PaymentTypeAdapter adapter = new PaymentTypeAdapter(mPaymentNames, mPaymentIcons);
        adapter.setOnItemClickListener(this);
        etAmount = (EditText) findViewById(R.id.etAmount);
        etAmount.setText("1");
        rvPaymentType = (RecyclerView) findViewById(R.id.rvPaymentType);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPaymentType.setLayoutManager(manager);
        rvPaymentType.setHasFixedSize(true);
        rvPaymentType.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Snackbar.make(rvPaymentType, mPaymentNames[position], Snackbar.LENGTH_SHORT).show();
        switch (position) {
            case 0: //支付宝
                setParameters(Constant.ChannelType.ALI_APP);
                break;
            case 1: //支付宝
                setParameters(Constant.ChannelType.ALI_WAP);
                break;
            case 2://微信
                setParameters(Constant.ChannelType.WX_APP);
                break;
            case 3://微信
                setParameters(Constant.ChannelType.WX_H5);
                break;
            case 4: //百度钱包
                setParameters(Constant.ChannelType.BD_APP);
                break;
            case 5://银联支付
                setParameters(Constant.ChannelType.UP_APP);
                break;
            case 6: //QQ钱包
                setParameters(Constant.ChannelType.QQ_APP);
                break;
            case 7: //京东支付
                setParameters(Constant.ChannelType.JD_APP);
                break;
            case 8:
                HMApi.scanner(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //建议对resultCode进行校验
        if (data != null && resultCode == Constant.Common.RESULT_CODE_PAYMENT) {
            switch (requestCode) {
                case Constant.SandBox.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "测试(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, Constant.Extra.DEFAULT_VALUE)
                            + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    break;
                case Constant.ALI_WAP.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "支付宝WAP(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, Constant.Extra.DEFAULT_VALUE)
                            + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    break;
                case Constant.WX_APP.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "微信(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, Constant.Extra.DEFAULT_VALUE)
                            + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    break;
                case Constant.WX_H5.REQUEST_CODE_PAYMENT:
                    //0：處理中 1：成功 -1：失敗
                    Toast.makeText(this, "微信H5(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, Constant.Extra.DEFAULT_VALUE)
                            + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    break;
                case Constant.BD.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "百度(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, Constant.Extra.DEFAULT_VALUE)
                            + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    break;
                case Constant.UN.REQUEST_CODE_PAYMENT:
                    if (data.getIntExtra(Constant.Extra.RESULT_CODE, Constant.Extra.DEFAULT_VALUE) == Constant.Common.PAYMENT_ERROR) {//内部异常校验请勿省略
                        Toast.makeText(this, "银联(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, Constant.Extra.DEFAULT_VALUE)
                                + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                                + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    } else { // 银联支付返回结果
                        Toast.makeText(this, "银联(" + data.getStringExtra(Constant.UN.PAY_RESULT) + ")", Toast.LENGTH_LONG).show();
                    }
                    break;
                case Constant.QQ.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "QQ(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, Constant.Extra.DEFAULT_VALUE)
                            + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    break;
                case Constant.JD.REQUEST_CODE_PAYMENT:
                    if (data.getIntExtra(Constant.Extra.RESULT_CODE, Constant.Extra.DEFAULT_VALUE) == Constant.Common.PAYMENT_ERROR) {//内部异常校验请勿省略
                        Toast.makeText(this, "京东(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, Constant.Extra.DEFAULT_VALUE)
                                + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                                + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    } else { // 京东支付返回结果
                        TradeResultInfo info = (TradeResultInfo) data
                                .getExtras().getSerializable(WePay.PAY_RESULT);
                        if (info != null) {
                            if (info.isResultSuccess()) {
                                Toast.makeText(this, "京东(" + "支付成功)", Toast.LENGTH_LONG).show();
                            } else if (info.isResultFail()) {
                                Toast.makeText(this, "京东(" + "支付失败)", Toast.LENGTH_LONG).show();
                            } else if (info.isUndo()) {
                                Toast.makeText(this, "京东(" + "取消支付)", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    break;
                case Constant.Scanner.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "扫码结果：" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            , Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }


    private String app_id = "hyp17041449458900000057698DAAB00";
    private String sign_type = "MD5";
    private String method = "heemoney.pay.applypay";
    private String charset = "utf-8";
    private String version = "1.0";
    private String timestamp;
    private String mch_uid = "4945892094734";
    private String key = "5454778B83484969ACFB20BA";


    /**
     * 此处只供演示，需开发者自行实现
     *
     * @param channelType
     */
    private void setParameters(String channelType) {
        timestamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        final JSONObject requestParams = new JSONObject();
        try {
            requestParams.put("app_id", app_id);
            requestParams.put("sign_type", sign_type);
            requestParams.put("method", method);
            requestParams.put("charset", charset);
            requestParams.put("version", version);
            JSONObject biz_content = new JSONObject();
            biz_content.put("subject", "Nexus Pixel");
            biz_content.put("timeout_express", "6");
            biz_content.put("client_ip", "127.0.0.1");
            biz_content.put("total_fee", etAmount.getText().toString());
            biz_content.put("channel_Type", channelType);
            biz_content.put("client_ip", "127.0.0.1");//新加
            biz_content.put("return_url", "http://localhost/TestMergepay/Api/RecNotifyUrl.aspx");
            biz_content.put("notify_url", "http://localhost/TestMergepay/Api/RecNotifyUrl.aspx");
            biz_content.put("out_trade_no", getUUID());
            biz_content.put("body", "Nexus_Pixel_Black");
            JSONObject terminal_info = new JSONObject();
            terminal_info.put("is_encrypt", "true");
            terminal_info.put("encrypt_ver", "1.0.0");
            terminal_info.put("encrypt_data", DeviceInfoUtil.getInstance().getDeviceInfo(this));//需要进行权限校验,sdk不提供权限校验功能
            biz_content.put("terminal_info", terminal_info.toString());
            requestParams.put("biz_content", biz_content.toString());
            requestParams.put("timestamp", timestamp);
            requestParams.put("mch_uid", mch_uid);
            StringBuilder builder = new StringBuilder();
            builder.append("app_id").append("=").append(app_id).append("&")
                    .append("biz_content").append("=").append(biz_content.toString()).append("&")
                    .append("charset").append("=").append(charset).append("&")
                    .append("mch_uid").append("=").append(mch_uid).append("&")
                    .append("method").append("=").append(method).append("&")
                    .append("sign_type").append("=").append(sign_type).append("&")
                    .append("timestamp").append("=").append(timestamp).append("&")
                    .append("version").append("=").append(version).append("&")
                    .append("key").append("=").append(key);
            Log.d("---->1", builder.toString());
            Log.d("---->2", mD5(builder.toString()));
            requestParams.put("sign", mD5(builder.toString()));


            Executors.newScheduledThreadPool(1).execute(new Runnable() {

                @Override
                public void run() {
                    HttpUtil.getInstance().doPost(requestParams, serverUrl, mHandler, REQUEST_SUCCESS, REQUEST_FAIL, REQUEST_ERROR);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public final String mD5(String data) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = data.getBytes();
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 订单号使用UUID 保持订单号的唯一性
     *
     * @return
     */
    private String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").toUpperCase();
    }
}

