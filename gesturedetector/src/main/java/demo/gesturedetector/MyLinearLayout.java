package demo.gesturedetector;

import android.content.Context;
import android.graphics.LightingColorFilter;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;

/**
 * Created by zhenzhen on 2017/2/20.
 */

public class MyLinearLayout extends LinearLayout implements GestureDetector.OnGestureListener{

    public final static String TAG = "MyLinearLayout-----> ";
    public final static String DEBUG_TAG = "MyLinearLayout-----> ";
    public final static String TAG_INTERRUPT = "My->rupt-----> ";

    private GestureDetector mGestureDetector;
    private VelocityTracker tracker;
    private int piontId;


    public MyLinearLayout(Context context) {
        this(context, null);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(context, this);
        tracker = VelocityTracker.obtain();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = MotionEventCompat.getActionMasked(ev);
        tracker.addMovement(ev);
        int index = MotionEventCompat.getActionIndex(ev);

        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG_INTERRUPT, "ACTION_DOWN");
                piontId = ev.getPointerId(index);

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG_INTERRUPT, "ACTION_POINTER_DOWN");

                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG_INTERRUPT, "ACTION_MOVE");
                tracker.computeCurrentVelocity(1000);
                Log.i(TAG, "X velocityX is :" + VelocityTrackerCompat.getXVelocity(tracker, piontId));
                Log.i(TAG, "Y velocityY is :" + VelocityTrackerCompat.getYVelocity(tracker, piontId));

                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG_INTERRUPT, "ACTION_POINTER_UP");

                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG_INTERRUPT, "ACTION_CANCEL");

                break;
            case MotionEvent.ACTION_UP:
                tracker.recycle();
                Log.i(TAG_INTERRUPT, "ACTION_UP");

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        mGestureDetector.onTouchEvent(event);

        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "ACTION_POINTER_DOWN");

                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");

                VelocityTracker tracker = VelocityTracker.obtain();


                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, "ACTION_POINTER_UP");

                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "ACTION_CANCEL");

                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP");

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.i(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.i(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.i(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Log.i(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.i(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.i(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }
}
