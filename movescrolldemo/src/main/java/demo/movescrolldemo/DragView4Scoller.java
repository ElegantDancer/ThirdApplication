package demo.movescrolldemo;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by zhenzhen on 2017/2/21.
 */

public class DragView4Scoller extends TextView {

    private Scroller mScroller;
    private int lastX;
    private int lastY;
    public DragView4Scoller(Context context) {
        this(context, null);
    }

    public DragView4Scoller(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView4Scoller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                int offsetX = x - lastX;
                int offsetY = y - lastY;
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                // 手指离开时，执行滑动过程
                View viewGroup = ((View) getParent());
                mScroller.startScroll(
                        viewGroup.getScrollX(),
                        viewGroup.getScrollY(),
                        -viewGroup.getScrollX(),
                        -viewGroup.getScrollY());

                Log.i("getScrollX----->", String.valueOf(viewGroup.getScrollX()));
                Log.i("getScrollY----->", String.valueOf(viewGroup.getScrollY()));
                invalidate();
                break;
        }
        return true;
    }


    @Override
    public void computeScroll() {

        if(mScroller.computeScrollOffset()){
            int curX = mScroller.getCurrX();
            int curY = mScroller.getCurrY();

            ((ViewGroup)getParent()).scrollTo(curX, curY);
            postInvalidate();
        }
    }
}
