package com.example.huaixiaohai.rxjavademo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.huaixiaohai.rxjavademo.App;
import com.example.huaixiaohai.rxjavademo.service.GankService;
import com.example.huaixiaohai.rxjavademo.R;
import com.example.huaixiaohai.rxjavademo.model.SearchModel;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zht";

    private RecyclerView recyclerView;
    private List<String> picPathList;
    private MyAdapter adapter;
    private int page;
    private int count;
    private GankService service;



    private Action1<List<SearchModel.ResultsBean>> action1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        page = 1;
        count = 10;

        service = App.getInstance().getGankService();

        picPathList = new ArrayList<>();
        adapter = new MyAdapter();
        adapter.openLoadMore(count);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        search();
                    }

                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        action1 = new Action1<List<SearchModel.ResultsBean>>() {
            @Override
            public void call(List<SearchModel.ResultsBean> resultsBeen) {
                if(page == 1) {
                    picPathList.clear();
                }
                if(resultsBeen == null || resultsBeen.size() <= 0){
                    adapter.loadComplete();
                } else {
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < resultsBeen.size(); i++) {
                        SearchModel.ResultsBean item = resultsBeen.get(i);
                        Log.e(TAG, "call: " + item.getUrl());
                        list.add(item.getUrl());
                    }
                    adapter.addData(list);
                }
            }
        };
        search();
    }

    private void search() {
        service.search("福利", count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<SearchModel, List<SearchModel.ResultsBean>>() {
                    @Override
                    public List<SearchModel.ResultsBean> call(SearchModel searchModel) {
                        return searchModel.getResults();
                    }
                })
                .subscribe(action1);
    }

    public class MyAdapter extends BaseQuickAdapter<String> {

        public MyAdapter() {
            super(R.layout.item_imageview, picPathList);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, String s) {
            Glide.with(MainActivity.this).load(s).error(R.mipmap.ic_launcher).into((ImageView) baseViewHolder.getView(R.id.image));
        }
    }
}
