package com.khtn.mammam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private ImageView imgShare,imgDirection,imgComment;

    private LinearLayout layoutHidden;
    private TextView txtTextHidden;
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

        imgShare = (ImageView) findViewById(R.id.imgViewShare);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Check-in, tui đang ở nhà hàng Biển Đông 05 nè");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Chia sẻ cho bạn bè :"));
            }
        });

        imgDirection = (ImageView) findViewById(R.id.imgViewDirection);
        imgDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String label = "Nhà hàng biển đông";
//                String uriBegin = "geo:10.7590109,106.6772865";
//                String query = "10.7590109,106.6772865(" + label + ")";
//                String encodedQuery = Uri.encode(query);
//                String uriString = uriBegin + "?q=" + encodedQuery;
//                Uri uri = Uri.parse(uriString);
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
//                startActivity(intent);

                Uri gmmIntentUri = Uri.parse("google.navigation:q=Quán biển đông 5");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        imgComment = (ImageView) findViewById(R.id.imgViewComment);
        imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(DiaDiem_Details_Activity.this,CommentActivity.class);
                Intent i = new
                        Intent(DiaDiem_Details_Activity.this,Comment_Evaluate_Activity.class);
                startActivityForResult(i,99);
            }
        });

        txtTextHidden = (TextView) findViewById(R.id.txtTextHidden);
        layoutHidden = (LinearLayout) findViewById(R.id.layoutHidden);

        layoutHidden.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==88)
        {
            //Toast.makeText(DiaDiem_Details_Activity.this,data.getStringExtra("noidungbinhluan"),Toast.LENGTH_LONG).show();
            txtTextHidden.setText("NGỌC TRINH\n05/01/2017 07:15 PM\n"+data.getStringExtra("noidungbinhluan"));
            layoutHidden.setVisibility(View.VISIBLE);
        }
    }
}
