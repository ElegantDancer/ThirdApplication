package demo.gesturedetector;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity{

    public final static String DEBUG_TAG = "MainActivity----> ";



    private GestureDetectorCompat mGestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mGestureDetectorCompat = new GestureDetectorCompat(this, this);
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

        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.i(DEBUG_TAG, "disPatchTouchevent");
        return super.dispatchTouchEvent(ev);
    }

    //    @Override
//    public boolean onDown(MotionEvent event) {
//        Log.i(DEBUG_TAG,"onDown: " + event.toString());
//        return true;
//    }
//
//    @Override
//    public boolean onFling(MotionEvent event1, MotionEvent event2,
//                           float velocityX, float velocityY) {
//        Log.i(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
//        return true;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent event) {
//        Log.i(DEBUG_TAG, "onLongPress: " + event.toString());
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//                            float distanceY) {
//        Log.i(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
//        return true;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent event) {
//        Log.i(DEBUG_TAG, "onShowPress: " + event.toString());
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent event) {
//        Log.i(DEBUG_TAG, "onSingleTapUp: " + event.toString());
//        return true;
//    }
}
