package demo.refreshlayout.view;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import demo.refreshlayout.R;

public class ReFlashListView extends ListView implements OnScrollListener {
    View header;// ¶¥²¿²¼¾ÖÎÄ¼þ£»
    int headerHeight;// ¶¥²¿²¼¾ÖÎÄ¼þµÄ¸ß¶È£»
    int firstVisibleItem;// µ±Ç°µÚÒ»¸ö¿É¼ûµÄitemµÄÎ»ÖÃ£»
    int scrollState;// listview µ±Ç°¹ö¶¯×´Ì¬£»
    boolean isRemark;// ±ê¼Ç£¬µ±Ç°ÊÇÔÚlistview×î¶¥¶ËÞôÏÂµÄ£»
    int startY;// ÞôÏÂÊ±µÄYÖµ£»

    int state;// µ±Ç°µÄ×´Ì¬£»
    final int NONE = 0;// Õý³£×´Ì¬£»
    final int PULL = 1;// ÌáÊ¾ÏÂÀ­×´Ì¬£»
    final int RELESE = 2;// ÌáÊ¾ÊÍ·Å×´Ì¬£»
    final int REFLASHING = 3;// Ë¢ÐÂ×´Ì¬£»
//    IReflashListener iReflashListener;//Ë¢ÐÂÊý¾ÝµÄ½Ó¿Ú
    public ReFlashListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public ReFlashListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public ReFlashListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    /**
     * ³õÊ¼»¯½çÃæ£¬Ìí¼Ó¶¥²¿²¼¾ÖÎÄ¼þµ½ listview
     *
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.header_layout, null);
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        Log.i("tag", "headerHeight = " + headerHeight);
//        topPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);

//        scrollBy(0, headerHeight);


    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//
//        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
//        marginLayoutParams.topMargin = - header.getMeasuredHeight();
//        this.setLayoutParams(marginLayoutParams);
    }

    /**
     * Í¨Öª¸¸²¼¾Ö£¬Õ¼ÓÃµÄ¿í£¬¸ß£»
     *
     * @param view
     */
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight,
                    MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /**
     * ÉèÖÃheader ²¼¾Ö ÉÏ±ß¾à£»
     *
     * @param topPadding
     */
    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding,
                header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        this.firstVisibleItem = firstVisibleItem;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        this.scrollState = scrollState;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        // TODO Auto-generated method stub
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (firstVisibleItem == 0) {
//                    isRemark = true;
//                    startY = (int) ev.getY();
//                }
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                onMove(ev);
//                break;
//            case MotionEvent.ACTION_UP:
//                if (state == RELESE) {
//                    state = REFLASHING;
//                    // ¼ÓÔØ×îÐÂÊý¾Ý£»
//                    reflashViewByState();
//                    iReflashListener.onReflash();
//                } else if (state == PULL) {
//                    state = NONE;
//                    isRemark = false;
//                    reflashViewByState();
//                }
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }

    /**
     * ÅÐ¶ÏÒÆ¶¯¹ý³Ì²Ù×÷£»
     *
     * @param ev
     */
//    private void onMove(MotionEvent ev) {
//        if (!isRemark) {
//            return;
//        }
//        int tempY = (int) ev.getY();
//        int space = tempY - startY;
//        int topPadding = space - headerHeight;
//        switch (state) {
//            case NONE:
//                if (space > 0) {
//                    state = PULL;
//                    reflashViewByState();
//                }
//                break;
//            case PULL:
//                topPadding(topPadding);
//                if (space > headerHeight + 30
//                        && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
//                    state = RELESE;
//                    reflashViewByState();
//                }
//                break;
//            case RELESE:
//                topPadding(topPadding);
//                if (space < headerHeight + 30) {
//                    state = PULL;
//                    reflashViewByState();
//                } else if (space <= 0) {
//                    state = NONE;
//                    isRemark = false;
//                    reflashViewByState();
//                }
//                break;
//        }
//    }

    /**
     * ¸ù¾Ýµ±Ç°×´Ì¬£¬¸Ä±ä½çÃæÏÔÊ¾£»
     */
//    private void reflashViewByState() {
//        TextView tip = (TextView) header.findViewById(R.id.tip);
//        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
//        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
//        RotateAnimation anim = new RotateAnimation(0, 180,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        anim.setDuration(500);
//        anim.setFillAfter(true);
//        RotateAnimation anim1 = new RotateAnimation(180, 0,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        anim1.setDuration(500);
//        anim1.setFillAfter(true);
//        switch (state) {
//            case NONE:
//                arrow.clearAnimation();
//                topPadding(-headerHeight);
//                break;
//
//            case PULL:
//                arrow.setVisibility(View.VISIBLE);
//                progress.setVisibility(View.GONE);
//                tip.setText("ÏÂÀ­¿ÉÒÔË¢ÐÂ£¡");
//                arrow.clearAnimation();
//                arrow.setAnimation(anim1);
//                break;
//            case RELESE:
//                arrow.setVisibility(View.VISIBLE);
//                progress.setVisibility(View.GONE);
//                tip.setText("ËÉ¿ª¿ÉÒÔË¢ÐÂ£¡");
//                arrow.clearAnimation();
//                arrow.setAnimation(anim);
//                break;
//            case REFLASHING:
//                topPadding(50);
//                arrow.setVisibility(View.GONE);
//                progress.setVisibility(View.VISIBLE);
//                tip.setText("ÕýÔÚË¢ÐÂ...");
//                arrow.clearAnimation();
//                break;
//        }
//    }
//
//    /**
//     * »ñÈ¡ÍêÊý¾Ý£»
//     */
//    public void reflashComplete() {
//        state = NONE;
//        isRemark = false;
//        reflashViewByState();
//        TextView lastupdatetime = (TextView) header
//                .findViewById(R.id.lastupdate_time);
//        SimpleDateFormat format = new SimpleDateFormat("yyyyÄêMMÔÂddÈÕ hh:mm:ss");
//        Date date = new Date(System.currentTimeMillis());
//        String time = format.format(date);
//        lastupdatetime.setText(time);
//    }
//
//    public void setInterface(IReflashListener iReflashListener){
//        this.iReflashListener = iReflashListener;
//    }
//    /**
//     * Ë¢ÐÂÊý¾Ý½Ó¿Ú
//     * @author Administrator
//     */
//    public interface IReflashListener{
//        public void onReflash();
//    }
}
