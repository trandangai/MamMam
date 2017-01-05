package com.khtn.mammam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CommentActivity extends AppCompatActivity {

    private Button btnXacNhan;
    private EditText txtNoiDung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        btnXacNhan= (Button) findViewById(R.id.btnXacNhan);
        txtNoiDung = (EditText) findViewById(R.id.editTextNoiDung);


        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("noidungbinhluan",txtNoiDung.getText().toString());
                setResult(88,i);
                finish();
            }
        });
    }
}
