package com.khtn.mammam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.khtn.mammam.pojo.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class SuggestionActivity extends AppCompatActivity {

    private GridView gridView;

    private List<Restaurant> restList = new ArrayList<>();
    private MyAdapter adapter;
    private GenericTypeIndicator<List<Restaurant>> myType;
    private Button btnGanToi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        adapter = new MyAdapter(this, restList);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // 'restaurants' is actually a path to firebase database's node (https://mammam-50af8.firebaseio.com/restaurants)
        // In firebase, it's showed mammam-50af8 (root node) -> restaurants -> <childs>
        DatabaseReference refRestaurant = database.getReference("restaurants");

        refRestaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TextView tv = (TextView) rowView.findViewById(R.id.tvHello);
                //tv.setText(dataSnapshot.getValue().toString());
                if(dataSnapshot!=null) {
                    Log.d("sayuri", dataSnapshot.getValue().toString() + "");
                    myType = new GenericTypeIndicator<List<Restaurant>>() {
                    };
                    restList = dataSnapshot.getValue(myType);

                    Log.d("sayuri", restList.get(0).getRestName() + "");
                    adapter.swapItems(restList);
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
                Intent intent = new Intent(SuggestionActivity.this,DiaDiem_Details_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("resttrans",restList.get(i));
                intent.putExtra("bundlerest",bundle);
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
    }
}
