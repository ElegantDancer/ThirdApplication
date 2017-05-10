package demo.gesturedetector;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by zhenzhen on 2017/2/20.
 */

public class MyTextView extends TextView {

    private GestureDetectorCompat gestureDetectorCompat;

    public final static String DEBUG_TAG = "MyTextView----> ";
    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
//        mGestureDetectorCompat.onTouchEvent(event);

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(DEBUG_TAG, "Action was DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(DEBUG_TAG, "Action was MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(DEBUG_TAG, "Action was UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(DEBUG_TAG, "Action was CANCEL");
                break;
            case MotionEvent.ACTION_OUTSIDE:
                Log.i(DEBUG_TAG, "Movement occurred outside bounds " +
                        "of current screen element");
                break;
            default:
                break;
        }
        return true;
    }
}
