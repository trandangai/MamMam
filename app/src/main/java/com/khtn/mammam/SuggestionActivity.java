package com.khtn.mammam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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

public class SuggestionActivity extends AppCompatActivity {

    private GridView gridView;

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
                intent.putExtra("restId",i);
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
    }

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
            super.onBackPressed();
        }
    }
}
