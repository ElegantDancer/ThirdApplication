package iqiyi.com.selfviewbetter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhenzhen on 2017/3/16.
 */

public class CircleView extends View {

    private static String TAG = "CircleView----->";
    private static final int DEFAULT_SIZE = 300;

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int widthSize;
    private int heightSize;

    private int color;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleView);

        color = array.getColor(R.styleable.CircleView_my_color, Color.GREEN);

        array.recycle();
        initView();

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();

    }

    private void initView() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            widthSize = DEFAULT_SIZE;
            heightSize = DEFAULT_SIZE;
        }else if(widthMode == MeasureSpec.AT_MOST){
            widthSize = DEFAULT_SIZE;
        }else if(heightMode == MeasureSpec.AT_MOST){
            heightSize = DEFAULT_SIZE;
        }

        setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        mWidth = right - left;
        mHeight = bottom - top;
//
//        ViewGroup.LayoutParams params = getLayoutParams();
//        params.width = mWidth;
//        params.height = mHeight;
//        setLayoutParams(params);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingRight = getPaddingRight();

        int width = getWidth() - paddingLeft - paddingRight;
        int heigth = getHeight() - paddingTop - paddingBottom;

        canvas.drawCircle(paddingLeft + width / 2, paddingTop + heigth / 2, Math.min( width, heigth) / 2, mPaint);
        invalidate();

    }
}
