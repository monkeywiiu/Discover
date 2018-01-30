package com.example.discover;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.discover.adapter.SearchTagAdapter;
import com.example.discover.bean.DetailBean.Data;
import com.example.discover.bean.LitePalBean.SearchTag;
import com.example.discover.http.RequestListener;
import com.example.discover.model.SearchModel;
import com.example.discover.utils.DebugUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

public class SearchActivity extends RxAppCompatActivity {

    public static String KEYWORD = "keyword";
    private RecyclerView mTagRecyclerView;
    private EditText searchEdit;
    private SearchTagAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initRecyclerView();
        init();
        loadTrendingTag();
        onKeyListener();
    }

    private void init() {
        List<String> tagList = new ArrayList<>();
        searchEdit = findViewById(R.id.search_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        ImageButton clearButton = findViewById(R.id.clear_btn);
        final EditText searchEdit = findViewById(R.id.search_edit);
        RxView.clicks(clearButton)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        searchEdit.setText("");
                    }
                });
        //获取5个最新的搜索记录；

        if (DataSupport.count(SearchTag.class) > 0) {
            tagList.add("new");
            List<SearchTag> list = DataSupport.limit(5).order("id desc").find(SearchTag.class);
            if (list != null) {

                for (SearchTag tag : list) {
                    tagList.add(tag.getTag());
                }

                //只保留5个tag
                DataSupport.deleteAll(SearchTag.class);
                for (int i = tagList.size() - 1; i > 0; i--) {
                    SearchTag searchTag = new SearchTag();
                    searchTag.setTag(tagList.get(i));
                    searchTag.save();
                }
            }
        }

        setAdapter(tagList);

    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTagRecyclerView = findViewById(R.id.rv_hot_tag);
        mTagRecyclerView.setLayoutManager(manager);
    }

    private void setAdapter(List<String> list) {
        adapter = new SearchTagAdapter(this);
        DebugUtil.debug("trend", list.size() + "");
        adapter.setItemClickListener(new SearchTagAdapter.onItemClickListener() {
            @Override
            public void onItemClick(String s) {
                toResultActivity(s);
            }
        });
        mTagRecyclerView.setAdapter(adapter);
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }
    private void loadTrendingTag() {
        SearchModel.showTrendingTag(this, new RequestListener() {
            @Override
            public void onSuccess(Object object) {
                List<String> list = (List<String>) object;
                List<String> tagList2 = new ArrayList<>();
                tagList2.add("recommend");
                tagList2.addAll(list);
                adapter.addAll(tagList2);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Throwable t) {

                t.printStackTrace();
                DebugUtil.debug("trend", "failed");
            }

            @Override
            public void onCompleted() {

            }
        });
    }

    private void onKeyListener() {

        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN) return true;

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (searchEdit.getText().toString().isEmpty()) {
                        Toast.makeText(SearchActivity.this, "搜索不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        search(searchEdit.getText().toString());
                    }
                } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                }

                return false;
            }
        });
    }

    private void search(String key) {

        DataSupport.deleteAll(SearchTag.class, "tag = ?", key);
        SearchTag tag = new SearchTag();
        tag.setTag(key);
        tag.save();

        toResultActivity(key);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toResultActivity(String key) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(KEYWORD, key);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
