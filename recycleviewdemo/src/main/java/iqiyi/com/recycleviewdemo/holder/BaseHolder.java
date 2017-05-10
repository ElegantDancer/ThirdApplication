package iqiyi.com.recycleviewdemo.holder;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhenzhen on 2017/4/10.
 */

public class BaseHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewSparseArray;
    public BaseHolder(View itemView) {
        super(itemView);
        mViewSparseArray = new SparseArray<>();
    }

    public BaseHolder(ViewGroup parent, @LayoutRes int resId) {

        super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
        mViewSparseArray = new SparseArray<>();
    }

    public <T extends View> T getView(@IdRes int viewId){

        View view = mViewSparseArray.get(viewId);
        if(null == view){
            view = itemView.findViewById(viewId);
            mViewSparseArray.put(viewId, view);
        }

        return (T) view;
    }

    public Context getContext(){
        return itemView.getContext();
    }
}
