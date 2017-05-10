package iqiyi.com.uploadrefreshrecycleview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleView;
    private List<String> mDatas;
    private MyAdapter mAdapter;
    private LinearLayoutManager mLayoutManger;

    private int lastVisibleItem = 0;
    public static final int PAGE_COUNT = 10;


    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initDatas();
        initRefrshLayout();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new MyAdapter(this, getData(0, PAGE_COUNT), mDatas.size() > 0);
        mLayoutManger = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManger);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    if(!mAdapter.isFadeTips() && lastVisibleItem + 1 == mAdapter.getItemCount()){
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(mAdapter.getRealLastPosition(), mAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (mAdapter.isFadeTips() && lastVisibleItem + 2 == mAdapter.getItemCount()){
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(mAdapter.getRealLastPosition(), mAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                lastVisibleItem = mLayoutManger.findLastVisibleItemPosition();
            }
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mDatas.add("条目" + i);
        }
    }

    private void initRefrshLayout() {

        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                                    ,android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setOnRefreshListener(this);
    }

    private void initView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_listview);
    }


    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);

        mAdapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
               mRefreshLayout.setRefreshing(false);
            }
        }, 1000);

    }

    private void updateRecyclerView(int fromIndex, int toIndex){

        List<String> newDatas = getData(fromIndex, toIndex);
        if(newDatas.size() > 0){
            mAdapter.updateList(newDatas, true);
        }else {
            mAdapter.updateList(null, false);
        }
    }

    private List<String> getData(int fromIndex, int toIndex){

        List<String> newList = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            newList.add(mDatas.get(i));
        }

        return newList;
    }
}
