package com.khtn.mammam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.khtn.mammam.pojo.Restaurant;
import com.khtn.mammam.utils.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;

import bolts.AppLinks;

public class SuggestionActivity extends AppCompatActivity {

    private GridView gridView;
    boolean doubleBackToExitPressedOnce = false;
    private List<Restaurant> restList = new ArrayList<>();
    private List<Restaurant> searchResultList = new ArrayList<>();
    private String searchKeyword = "";
    private MyAdapter adapter;
    private GenericTypeIndicator<List<Restaurant>> myType;
    private Button btnGanToi;
    private ImageView ivSearch;
    private TextView tvSearchKeyword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        tvSearchKeyword = (TextView) findViewById(R.id.tvSearchKeyword);
        tvSearchKeyword.setVisibility(View.GONE);

        adapter = new MyAdapter(this, restList);
        FirebaseDatabase database = FirebaseUtil.getDatabase();

        // 'restaurants' is actually a path to firebase database's node (https://mammam-50af8.firebaseio.com/restaurants)
        // In firebase, it's showed mammam-50af8 (root node) -> restaurants -> <childs>
        DatabaseReference refRestaurant = database.getReference("restaurants");

        refRestaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot != null) {
                    Log.d("sayuri", dataSnapshot.getValue().toString() + "");
                    myType = new GenericTypeIndicator<List<Restaurant>>() {};
                    restList = dataSnapshot.getValue(myType);

                    Log.d("sayuri", restList.get(0).getRestName() + "");
                    adapter.swapItems(restList);

                    // Refresh data with keyword
                    if(!searchKeyword.equals("")) {
                        searchRest(searchKeyword);
                    }
                }
                else {
                    Toast.makeText(SuggestionActivity.this,"Không load được dữ liệu từ server",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        Intent intent = new Intent();
//        String action = intent.getAction();
//        final String data = intent.getDataString();
//        Toast.makeText(this,"" + data, Toast.LENGTH_SHORT).show();
        gridView = (GridView) findViewById(R.id.gridSuggest);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SuggestionActivity.this, DiaDiem_Details_Activity.class);
                Bundle bundle = new Bundle();
                Restaurant restForsend;
                if(adapter.getCount()<restList.size())
                {
                    restForsend = searchResultList.get(i);
                }
                else
                {
                    restForsend = restList.get(i);
                }
                bundle.putSerializable("resttrans",restForsend);
                intent.putExtra("bundlerest",bundle);
                for(int x=0;x<restList.size();x++)
                {
//                    if(restList.get(x).getRestDetailLink().equals(data))
//                    {
//                        intent.putExtra("restId",x);
//                        break;
//                    }
                    if(restList.get(x).getRestName().equals(restForsend.getRestName()))
                    {
                        intent.putExtra("restId",x);
                        break;
                    }
                }
                startActivity(intent);
            }
        });

        btnGanToi = (Button) findViewById(R.id.btnGanToi);
        btnGanToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuggestionActivity.this, DiaDiemGanToiActivity.class);
                startActivity(intent);
            }
        });

        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(SuggestionActivity.this, SearchRestActivity.class);
                startActivityForResult(in, 88);
            }
        });
//        //Config app link facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this.getApplication());
        AppLinkData.fetchDeferredAppLinkData(this, new AppLinkData.CompletionHandler() {
            @Override
            public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                if (appLinkData != null) {
                    String a = String.valueOf(appLinkData.getTargetUri());
                    Log.i("DEBUG_FACEBOOK_SDK", a.toString());
                } else {
                    Log.i("DEBUG_FACEBOOK_SDK", "AppLinkData is Null");
                }
            }
        });
        Uri targetUrl =
                AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i("Activity", "App Link Target URL: " + targetUrl.toString());
        }
    }
//    protected void onNewIntent(Intent intent) {
//        String action = intent.getAction();
//        String data = intent.getDataString();
//        Toast.makeText(this,"Deep link: " + data, Toast.LENGTH_SHORT).show();
////        if (Intent.ACTION_VIEW.equals(action) && data != null) {
////            String recipeId = data.substring(data.lastIndexOf("/") + 1);
////            Uri contentUri = RecipeContentProvider.CONTENT_URI.buildUpon()
////                    .appendPath(recipeId).build();
////            showRecipe(contentUri);
////        }
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Log.d("data return", data + "");
        if(resultCode == 88 && data != null) {

            searchKeyword = data.getStringExtra("keywordRest");
            searchRest(searchKeyword);
        }
    }

    protected void searchRest(String keyword) {

        searchResultList.clear();
        //Log.d("keyword", "|" + keyword + "|");
        if(keyword.trim().equals("")) {
            tvSearchKeyword.setVisibility(View.GONE);
            searchResultList.addAll(restList);
        }
        else {
            tvSearchKeyword.setVisibility(View.VISIBLE);
            tvSearchKeyword.setText("=> Tìm với từ khóa \"" + keyword + "\"");
            keyword = keyword.toLowerCase();
            for (int i = 0; i < restList.size(); i++) {
                if (restList.get(i).getRestName().toLowerCase().contains(keyword)
                        || restList.get(i).getRestAddr().toLowerCase().contains(keyword)) {
                    searchResultList.add(restList.get(i));
                }
            }
        }

        adapter.swapItems(searchResultList);
    }

    @Override
    public void onBackPressed() {

        if(!searchKeyword.equals("")) {
            searchKeyword = "";
            searchRest("");
        }
        else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Bấm back lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }
}
