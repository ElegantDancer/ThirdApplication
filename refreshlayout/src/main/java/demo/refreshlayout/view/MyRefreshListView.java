package demo.refreshlayout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;

import demo.refreshlayout.R;

/**
 * Created by zhenzhen on 2017/2/23.
 */

public class MyRefreshListView extends ListView {


    private final static int STATE_NULL = 0x1;
    private final static int STATE_DOWN = 0x2;
    private final static int STATE_RELEASE = 0x3;
    private int curState;
    private int slop;

    private int lastX;
    private int lastY;
    private int height;

    private int headerHeight;
    private int headerWidth;
    private View header;
    private int viewGroupWidthSpec;


    public MyRefreshListView(Context context) {
        this(context, null);
    }

    public MyRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        addHeaderView(context);
    }

    private void addHeaderView(Context context) {

        header = LayoutInflater.from(context).inflate(R.layout.header, null);
        measureChild(header);

        height = header.getMeasuredHeight();

        Log.i("----->", String.valueOf(height));

        addHeaderView(header);

//        paddingTop(-height);
        header.setTranslationY(-height);
        setTranslationY(-height);
        invalidate();
        slop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private void paddingTop(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding,
                header.getPaddingRight(), header.getPaddingBottom());

        Log.i("----->", String.valueOf(header.getPaddingLeft()));

        header.invalidate();
    }

    /**
     * Measure the provided child.
     *
     * @param child The child.
     */
    private void measureChild(View child) {

        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int widthSpec = ViewGroup.getChildMeasureSpec(viewGroupWidthSpec, getPaddingTop() + getPaddingBottom(), layoutParams.width);
        int heigthSpec;
        if(layoutParams.height > 0){
            heigthSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
        }else{
            heigthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }

        child.measure(widthSpec, heigthSpec);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewGroupWidthSpec = widthMeasureSpec;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = MotionEventCompat.getActionMasked(ev);
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                curState = STATE_NULL;
                lastX = (int) ev.getX();
                lastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                int offsetY = y - lastY;

                if(curState == STATE_DOWN){

                    if(offsetY > slop){
//                        paddingTop(-height + offsetY);
                        header.setTranslationY(-height + offsetY);
                        setTranslationY(-height + offsetY);
                        curState = STATE_DOWN;
                    }

                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onTouchEvent(ev);
    }
}
