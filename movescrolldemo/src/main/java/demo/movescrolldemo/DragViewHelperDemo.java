package demo.movescrolldemo;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by zhenzhen on 2017/2/22.
 */

public class DragViewHelperDemo extends FrameLayout {

    private ViewDragHelper mViewDraghelper;
    private View mMenu;
    private View mContent;

    public DragViewHelperDemo(Context context) {
        this(context, null);
    }

    public DragViewHelperDemo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewHelperDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDraghelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenu = getChildAt(0);
        mContent = getChildAt(1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDraghelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mViewDraghelper.processTouchEvent(event);
        return true;
    }

    private ViewDragHelper.Callback callback =  new  ViewDragHelper.Callback(){

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mContent == child;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            int  iLeft = left + dx;
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }
    };

    @Override
    public void computeScroll() {

        if(mViewDraghelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
