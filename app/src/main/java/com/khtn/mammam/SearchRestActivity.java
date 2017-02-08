package com.khtn.mammam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Sayuri on 1/21/2017.
 */
public class SearchRestActivity extends AppCompatActivity {

    private Button btnXacNhan;
    private Button btnHuy;
    private EditText edtKeyword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_restaurant);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        btnXacNhan= (Button) findViewById(R.id.btnXacNhan);
        btnHuy= (Button)findViewById(R.id.btnHuy);
        edtKeyword = (EditText)findViewById(R.id.edtKeyword);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String noiDung = edtKeyword.getText().toString();
                Intent i = new Intent();
                i.putExtra("keywordRest", noiDung);
                setResult(88, i);
                finish();

            }

        });
        btnHuy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(99, i);
                finish();
            }

        });

    }
}
