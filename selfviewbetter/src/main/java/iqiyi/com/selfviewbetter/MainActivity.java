package iqiyi.com.selfviewbetter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity--->";

    private String str;
    private View view;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_drag_test);

//        view = findViewById(R.id.circleView);
//        textView = (TextView) findViewById(R.id.self_tex);
//
//        ViewGroup.LayoutParams params = view.getLayoutParams();
//
//        str = "params.width-->" + params.width + "params.height-->" + params.height;
//
//        view.post(new Runnable() {
//            @Override
//            public void run() {
//                str += "getWidth-->" + view.getWidth() + "getHeight-->" + view.getHeight()
//                   + "paddingLeft  " + view.getPaddingLeft() + "paddingRight  " + view.getPaddingRight();
//                textView.setText(str);
//            }
//        });
//
//        Log.i(TAG, String.valueOf(params.width));
//        Log.i(TAG, String.valueOf(params.height));
//
//        Log.i(TAG, String.valueOf(view.getWidth()));
//        Log.i(TAG, String.valueOf(view.getHeight()));
    }
}
