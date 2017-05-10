package demo.movescrolldemo;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by zhenzhen on 2017/2/21.
 */

public class DragView3ScrollBy extends TextView {

    private Scroller mScroller;
    private int lastX;
    private int lastY;
    public DragView3ScrollBy(Context context) {
        this(context, null);
    }

    public DragView3ScrollBy(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView3ScrollBy(Context context, AttributeSet attrs, int defStyleAttr) {
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

                int dx = x - lastX;
                int dy = y - lastY;

//                ViewGroup viewGroup = (ViewGroup) getParent();
//
//                mScroller.startScroll(lastX, lastY, dx, dy);
//                postInvalidate();
//                lastX = x;
//                lastY = y;

                ((View)getParent()).scrollBy(-dx, -dy);
                break;
        }
        return true;
    }

//
//    @Override
//    public void computeScroll() {
//
//        if(mScroller.computeScrollOffset()){
//            int curX = mScroller.getCurrX();
//            int curY = mScroller.getCurrY();
//
//            ((ViewGroup)getParent()).scrollTo(curX, curY);
//            postInvalidate();
//        }
//    }
}
