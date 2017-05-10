package com.iqiyi.thirdapplication;

/**
 * Created by zhenzhen on 2017/2/26.
 */


import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

/**
 * 可滑动的LinearLayout，用于顶部空间的显示和隐藏
 *
 * @author zhaokaiyuan
 */
public class ScrollLinearLayout extends LinearLayout {

    private final static int STATE_NULL = 0; // 无状态
    private final static int STATE_HORIZONTAL = 1; // 横滑
    private final static int STATE_VERTICAL = 2; // 竖滑
    private final static int ANIMATOR_DURATION = 200; // 默认动画时长
    private final int mTouchSlop; // 滑动阈值
    private final ValueAnimator.AnimatorUpdateListener ul = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) animation.getAnimatedValue();
            translationY(value);
        }
    };

    private int mGestureState = STATE_NULL; // 记录手势状态 0：无状态 1：横滑 2：竖滑
    private ValueAnimator mAnimator; // 显示隐藏的动画
    private float mLastY = 0; // 上次Y位置
    private float mInitialX = 0; // 初始X位置
    private float mInitialY = 0; // 初始Y位置
    private int mScrollPointerId = MotionEvent.INVALID_POINTER_ID; // 当前手指
    private boolean mHasDownEvent = false; // 是否接收到了Down事件
    private IScrollControlListener mControlListener; // 滑动控制

    public ScrollLinearLayout(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public ScrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public ScrollLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 检查是否需要滑动
        if (mControlListener != null && mControlListener.shouldDispatchTouch()) {
            int action = ev.getActionMasked();
            int eventIndex = ev.getActionIndex();
            int scrollDistance = mControlListener.getScrollDistance();
            float translationY = getCurrentTranslationY();
            // 停止当前的动画
            stopAnimator();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // 第一个手指按下时 初始化状态
                    mScrollPointerId = ev.getPointerId(0);
                    mInitialX = ev.getX();
                    mInitialY = mLastY = ev.getY();

                    mHasDownEvent = true;
                    mGestureState = STATE_NULL;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    // 记录新按下的手指的状态
                    mScrollPointerId = ev.getPointerId(eventIndex);
                    mInitialX = ev.getX(eventIndex);
                    mInitialY = mLastY = ev.getY(eventIndex);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    // 当前滑动手指离开屏幕
                    if (ev.getPointerId(eventIndex) == mScrollPointerId) {
                        final int newIndex = eventIndex == 0 ? 1 : 0;
                        mScrollPointerId = ev.getPointerId(newIndex);
                        mInitialX = ev.getX(newIndex);
                        mInitialY = mLastY = ev.getY(newIndex);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!mHasDownEvent) {
                        break;
                    }
                    final int index = ev.findPointerIndex(mScrollPointerId);
                    final float currentX = ev.getX(index);
                    final float currentY = ev.getY(index);
                    // 判断手指滑动状态 横向滑动的时候不处理
                    if (mGestureState == STATE_NULL) {
                        float xDiff = Math.abs(currentX - mInitialX);
                        float yDiff = Math.abs(currentY - mInitialY);
                        if (xDiff > mTouchSlop || yDiff > mTouchSlop) {
                            if (yDiff > xDiff) {
                                mGestureState = STATE_VERTICAL;
                            } else {
                                mGestureState = STATE_HORIZONTAL;
                            }
                        }
                    } else if (mGestureState == STATE_VERTICAL) {
                        float deltaY = ev.getY(index) - mLastY;
                        if (deltaY > 0) {
                            // 下拉，显示top
                            if (translationY < 0) {
                                if (translationY + deltaY > 0) {
                                    translationY(0);
                                } else {
                                    translationY(translationY + deltaY);
                                }
                            }
                        } else {
                            // 上滑，隐藏top
                            if (translationY > -scrollDistance) {
                                if (translationY + deltaY < -scrollDistance) {
                                    translationY(-scrollDistance);
                                } else {
                                    translationY(translationY + deltaY);
                                }
                            }
                        }
                    }
                    mLastY = currentY;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (!mHasDownEvent) {
                        break;
                    }
                    // 松手的时候自动显示完整或者隐藏
                    if (translationY < 0 && translationY > -scrollDistance) {
                        if (translationY < -scrollDistance / 2f) {
                            hideScrollSpace();
                        } else {
                            showScrollSpace();
                        }
                    }
                    mHasDownEvent = false;
                    break;
                default:
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 对每一个ChildView在Y轴上位移
     *
     * @param translationY Y轴位移距离
     */
    public void translationY(float translationY) {
        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            View view = getChildAt(i);
            view.setTranslationY(translationY);
        }
    }

    /**
     * 停止动画
     */
    private void stopAnimator() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }

    /**
     * 显示隐藏的顶部空间
     */
    public void showScrollSpace() {
        if (mControlListener == null || !mControlListener.shouldDispatchTouch()) {
            return;
        }
        float translationY = getCurrentTranslationY();

        mAnimator = ValueAnimator.ofFloat(translationY, 0).setDuration(ANIMATOR_DURATION);
        mAnimator.addUpdateListener(ul);
        mAnimator.setInterpolator(new DecelerateInterpolator(2));
        mAnimator.start();
    }

    /**
     * 隐藏顶部空间
     */
    public void hideScrollSpace() {
        if (mControlListener == null || !mControlListener.shouldDispatchTouch()) {
            return;
        }
        int distance = mControlListener.getScrollDistance();
        float translationY = getCurrentTranslationY();
        mAnimator = ValueAnimator.ofFloat(translationY, -distance).setDuration(ANIMATOR_DURATION);
        mAnimator.addUpdateListener(ul);
        mAnimator.setInterpolator(new AccelerateInterpolator(2));
        mAnimator.start();
    }

    /**
     * 设置ScrollControlListener
     */
    public void setIScrollControlListener(IScrollControlListener controlListener) {
        if (this.mControlListener != controlListener) {
            this.mControlListener = controlListener;
        }
    }

    /**
     * 获取当前的Y轴位移
     *
     * @return 当前的Y轴位移
     */
    public float getCurrentTranslationY() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                return child.getTranslationY();
            }
        }
        return 0;
    }

    /**
     * 滑动控制的interface
     */
    public interface IScrollControlListener {

        /**
         * @return 是否应该滑动
         */
        boolean shouldDispatchTouch();

        /**
         * @return 滑动距离
         */
        int getScrollDistance();

    }
}
