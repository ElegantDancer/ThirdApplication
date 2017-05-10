package iqiyi.com.recycleviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import iqiyi.com.recycleviewdemo.holder.BaseHolder;

/**
 * Created by zhenzhen on 2017/4/10.
 */

public abstract class AbsAdapter<VH extends BaseHolder> extends RecyclerView.Adapter<BaseHolder>{

    public static final String TAG = "AbsAdapter";

    public static final int VIEW_TYPE_HEADER = 1024;
    public static final int VIEW_TYPE_FOOTER = 1025;

    protected View headerView;
    protected View footerView;
    protected Context context;

    public AbsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case VIEW_TYPE_HEADER:
                return new BaseHolder(headerView);
            case VIEW_TYPE_FOOTER:
                return new BaseHolder(footerView);
            default:
                return createViewHolder(parent, viewType);
        }
    }

    protected abstract VH createCustomViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {

        switch (holder.getItemViewType()){

            case VIEW_TYPE_HEADER:
            case VIEW_TYPE_FOOTER:
                break;
            default:
                bindCustomViewHolder((VH) holder, position);
                break;
        }
    }

    protected abstract VH bindCustomViewHolder(VH holder, int position);

    public void addHeaderView(View headerView){
        if(null == headerView){
            return;
        }

        this.headerView = headerView;
        notifyDataSetChanged();
    }

    public void removeHeaderView(View headerView){

        if(null != headerView){
            this.headerView = null;
            notifyDataSetChanged();
        }
    }

    public void addFooterView(View footerView){

        if(null == footerView){
            return;
        }

        this.footerView = footerView;
        notifyDataSetChanged();
    }

    public int getExtraViewCount(){
        int extraViewCount = 0;
        if(headerView != null){
            extraViewCount++;
        }

        if(footerView != null){
            extraViewCount++;
        }

        return extraViewCount;
    }

    public int getHeaderExtraViewCount(){
        return headerView == null ? 0 :1;
    }

    public int getFooterExtraViewCount(){
        return footerView == null ? 0 : 1;
    }

    @Override
    public abstract long getItemId(int position);
}
