package demo.movescrolldemo;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by zhenzhen on 2017/2/21.
 */

public class DragView1Layout extends TextView {

    private int lastX;
    private int lastY;
    public DragView1Layout(Context context) {
        this(context, null);
    }

    public DragView1Layout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView1Layout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

                layout(getLeft() + dx, getTop() + dy, getRight() + dx, getBottom() + dy);

                break;


        }

        return true;
    }
}
