package share.iqiyi.com.eventbusdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import share.iqiyi.com.eventbusdemo.message.MainMessage;

/**
 * Created by zhenzhen on 2017/5/10.
 */

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_layout);

        findViewById(R.id.sec_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EventBus.getDefault().register(this);
                EventBus.getDefault().post(new MainMessage("这是从secondActivity发送的消息哦"));
            }
        });
    }
}
