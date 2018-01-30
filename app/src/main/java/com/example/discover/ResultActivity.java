package com.example.discover;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.discover.adapter.ResultAdapter;
import com.example.discover.bean.DetailBean.ItemList;
import com.example.discover.bean.ResultBean;
import com.example.discover.http.RequestListener;
import com.example.discover.model.SearchModel;
import com.example.discover.utils.DebugUtil;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

public class ResultActivity extends RxAppCompatActivity {

    private  String keyword;
    private int start = 1;
    private boolean loading = false;
    private ResultAdapter adapter;
    private List<ItemList> resultList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        keyword = getIntent().getStringExtra(SearchActivity.KEYWORD);
        init();
        initRecyclerView();
        showResult();
    }

    public void init() {
        //设置toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleView = findViewById(R.id.title);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
        titleView.setText(keyword);

        ImageButton searchBtn = findViewById(R.id.search_btn);
        RxView.clicks(searchBtn)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(ResultActivity.this, SearchActivity.class);
                        startActivity(intent);
                    }
                });
    }

    public void initRecyclerView() {
        RecyclerView resultRecyclerView = findViewById(R.id.rv_result);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        resultRecyclerView.setLayoutManager(layoutManager);
        adapter = new ResultAdapter(this, resultList);
        resultRecyclerView.setAdapter(adapter);

        //上拉加载
        resultRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                DebugUtil.debug("resultTest", lastVisibleItem + "  " + totalItemCount);
                if (lastVisibleItem == totalItemCount - 5 && !loading) {

                    loading = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            showResult();
                        }
                    }, 1000);

                }
            }
        });
    }
    public void showResult() {
        SearchModel.showResult(this, keyword, start, new RequestListener() {
            @Override
            public void onSuccess(Object object) {
                ResultBean resultBean = (ResultBean) object;
                DebugUtil.debug("resultTest", "size" + resultBean.getItemList().size() +"start" +start);
                resultList.addAll(resultBean.getItemList());
                adapter.notifyDataSetChanged();
                loading = false;
                start += resultBean.getItemList().size();
            }

            @Override
            public void onFailed(Throwable t) {

                DebugUtil.debug("resultsss", "" + "failed");
            }

            @Override
            public void onCompleted() {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
