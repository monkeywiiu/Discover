package com.example.discover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AuthorHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_home);
        int id = getIntent().getIntExtra("AuthorId", 0);
        String name = getIntent().getStringExtra("AuthorName");

        TextView textView1 = findViewById(R.id.test1);
        TextView textView2 = findViewById(R.id.test2);
        textView1.setText(id + "");
        textView2.setText(name);
    }
}
