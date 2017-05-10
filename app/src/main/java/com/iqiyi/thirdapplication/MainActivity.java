package com.iqiyi.thirdapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ScrollLinearLayout.IScrollControlListener {

//    @BindView(R.id.button1)
//    Button button1;
//    @BindView(R.id.button2)
//    Button button2;
//    private int heigth;
//
//
//    @OnClick(R.id.button2) void doTrans(){
//        heigth += 30;
//        button1.setTranslationY(heigth);
//        button2.setTranslationY(heigth);
//    }
//
//    @BindView(R.id.app_tex)
//    TextView textView;
//
//    @BindView(R>)
//
//    @OnClick(R.id.app_tex) void doClick(){

//        String str = "The 8 is-->: " + Long.valueOf("100", 8) + "The 2 is-->" + Long.valueOf("100", 2) + "The Value is-->" + Long.valueOf("100", 10);
//        textView.setText(str);

//        textView.setText("leftpadding---->" + String.valueOf(textView.getPaddingLeft()) + "\n" + "getLeft---->" + String.valueOf(textView.getLeft()) + "\n"
//             + "topPadding----->" + String.valueOf(textView.getPaddingTop()));

//        topPadding(heigth / 20);

//    }

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tranlate_demo);
        mContext = this;
//        ButterKnife.bind(this);
//        File file = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "main3.txt");
//
//        if(!file.exists()){
//            file.getParentFile().mkdirs();
//        }

//        Map<String, String> map = new HashMap<>();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////                if(Looper.myLooper() != null){
////                    Toast.makeText(MainActivity.this, "我来自于子线程", Toast.LENGTH_SHORT).show();
////                }
//                Looper.prepare();
//                ToastUtils.toast(MainActivity.this, "我来自子线程");
//                Looper.loop();
//            }
//        }).start();

        /**
         * 子线程中 发送 toast
         */
//        Button button = (Button) findViewById(R.id.button1);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                View view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.activity_main2, null);
//                View view = View.inflate(mContext, R.layout.activity_main2, null);
//                Dialog dialog = new Dialog(mContext);
//                dialog.setContentView(view);
//                dialog.show();
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (Looper.myLooper() != null) {
//                            Toast.makeText(MainActivity.this, "我来自于子线程", Toast.LENGTH_SHORT).show();
//                        }
//                        Looper.prepare();
//                        ToastUtils.toast(MainActivity.this, "我来自子线程2");
//                        Looper.loop();
//                    }
//                }).start();
//            }
//        });

        Activity activity = this;
        this.finish();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void measureView(View view) {

    }

    @Override
    public boolean shouldDispatchTouch() {
        return false;
    }

    @Override
    public int getScrollDistance() {
        return 300;
    }

//    private void topPadding(int topPadding) {
//        textView.setPadding(textView.getPaddingLeft(), topPadding, textView.getPaddingRight(), textView.getPaddingBottom());
//    }
}
