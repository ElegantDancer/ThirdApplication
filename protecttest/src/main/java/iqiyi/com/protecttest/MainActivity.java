package iqiyi.com.protecttest;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.netdoc.NetDocConnector;
import com.netdoc.PlatformType;
import com.qiyi.Protect;

public class MainActivity extends AppCompatActivity {


    private TextView mTextView;
    private String emudStr;
    private String emudStr2 = "";

    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emudStr = Protect.getEmud(MainActivity.this);

//        NetDocConnector connector = new NetDocConnector("libnetdoc.so");
//        connector.initNetDoctor("AAAA", PlatformType.TYPE_ANDROID.ordinal(),"");
//
//        emudStr2 = connector.emud(MainActivity.this);

        mTextView = (TextView) findViewById(R.id.pro_tex);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(emudStr + "\n" + emudStr2);
            }
        });

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
    }
}
