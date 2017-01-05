package com.khtn.mammam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class SuggestionActivity extends AppCompatActivity {

    private GridView gridView;
    private static int[] prgmImages = {
            R.mipmap.a1,
            R.mipmap.a2,
            R.mipmap.a3,
            R.mipmap.a4,
            R.mipmap.a5,
            R.mipmap.a6,
            R.mipmap.a7};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);


        gridView = (GridView) findViewById(R.id.gridSuggest);
        MyAdapter adapter = new MyAdapter(this,prgmImages);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SuggestionActivity.this,DiaDiem_Details_Activity.class);
                startActivity(intent);
            }
        });
    }
}
