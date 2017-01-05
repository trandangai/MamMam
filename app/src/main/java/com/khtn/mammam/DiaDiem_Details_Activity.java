package com.khtn.mammam;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class DiaDiem_Details_Activity extends AppCompatActivity {

    private ViewGroup scrollViewgroup;
    private ImageView icon;
    private Integer[] thumbnails = {R.mipmap.a1,
            R.mipmap.a2,
            R.mipmap.a3,
            R.mipmap.a4,
            R.mipmap.a5,
            R.mipmap.a6,
            R.mipmap.a7};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_diem_details);

        scrollViewgroup = (ViewGroup) findViewById(R.id.viewgroup);
        for (int i = 0; i < thumbnails.length; i++) {
            final View singleFrame = getLayoutInflater().inflate(R.layout.frame_icon_caption, null);
            singleFrame.setId(i);
            ImageView icon = (ImageView) singleFrame.findViewById(R.id.icon);
            icon.setImageResource(thumbnails[i]);
            scrollViewgroup.addView(singleFrame);
        }
    }
}
