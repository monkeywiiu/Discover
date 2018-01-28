package com.example.discover;

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
import android.widget.Toast;

import com.example.discover.adapter.SearchTagAdapter;
import com.example.discover.bean.DetailBean.Data;
import com.example.discover.bean.LitePalBean.SearchTag;
import com.example.discover.http.RequestListener;
import com.example.discover.model.SearchModel;
import com.example.discover.utils.DebugUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends RxAppCompatActivity {

    private RecyclerView mTagRecyclerView;
    private EditText searchEdit;
    private List<String> tagList;
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
        tagList = new ArrayList<>();


        searchEdit = findViewById(R.id.search_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
        //获取5个最新的搜索记录；

        if (DataSupport.count(SearchTag.class) > 0) {
            tagList.add("new");
            List<SearchTag> list = DataSupport.limit(5).order("id desc").find(SearchTag.class);
            if (list != null) {

                for (SearchTag tag : list) {
                    tagList.add(tag.getTag());
                }


                //test
                for (String tag :tagList) {
                    DebugUtil.debug("tags", tag + "" );
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
        mTagRecyclerView.setAdapter(adapter);
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }
    private void loadTrendingTag() {
        SearchModel.showTrendingTag(this, new RequestListener() {
            @Override
            public void onSuccess(Object object) {
                List<String> list = (List<String>) object;
                DebugUtil.debug("trend", list.get(0)+list.size());
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

    private void search(String s) {

        DataSupport.deleteAll(SearchTag.class, "tag = ?", s);
        SearchTag tag = new SearchTag();
        tag.setTag(s);
        tag.save();

        Toast.makeText(SearchActivity.this, s, Toast.LENGTH_SHORT).show();
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
