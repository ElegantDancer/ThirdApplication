package com.qiyi.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;


/**
 * Created by zhaozhenzhen on 2016/8/25.
 */
public class SinaEntryActivity extends Activity implements IWeiboHandler.Response {

    private IWeiboShareAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String SINA_SHARE_APP_KEY;
        if (this.getPackageName().equals("tv.pps.mobile")) {
            SINA_SHARE_APP_KEY = ShareConstans.SINA_SHARE_APP_KEY_PPS;
        } else {
            SINA_SHARE_APP_KEY = ShareConstans.SINA_SHARE_APP_KEY_IQY;
        }

        // 创建微博分享接口实例
        api = WeiboShareSDK.createWeiboAPI(this, SINA_SHARE_APP_KEY);

        api.registerApp();

        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        if (savedInstanceState != null) {
            api.handleWeiboResponse(getIntent(), this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            api.handleWeiboResponse(intent, this);
        } catch (Exception ex) {
//            finish();
        }
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(this, "分享取消", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
                break;
        }
//        finish();
    }
}
