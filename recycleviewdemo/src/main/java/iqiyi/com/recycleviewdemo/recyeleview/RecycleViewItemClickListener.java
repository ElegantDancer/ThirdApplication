package iqiyi.com.recycleviewdemo.recyeleview;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.View;

/**
 * Created by zhenzhen on 2017/4/10.
 */

public class RecycleViewItemClickListener extends RecyclerView.SimpleOnItemTouchListener {


    /**
     * 经过对比发现 还是 直接用 recycleView 的item 设置点击事件 比较省事儿
     */
    private OnItemClickListener itemClickListener;
    private GestureDetectorCompat mGestureDetector;

    public interface OnItemClickListener{

        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public RecycleViewItemClickListener(final RecyclerView recyclerView, OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
