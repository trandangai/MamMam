package com.khtn.mammam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chieclamuadong on 1/21/2017.
 */
public class Comment_Evaluate_Activity extends AppCompatActivity {

    private Button btnXacNhan;
    private Button btnHuy;
    private EditText txtNoiDung;

    private RatingBar ratingBar;
    private TextView txtRatingValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_evaluate);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addListenerOnRatingBar();
        addListenerOnButton();

    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                txtRatingValue.setText(String.valueOf(rating));

            }
        });
    }

    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnXacNhan= (Button) findViewById(R.id.btnXacNhan);
        btnHuy= (Button)findViewById(R.id.btnHuy);
        txtNoiDung = (EditText)findViewById(R.id.editTextNoiDung);
        //if click on me, then display the current rating value.
        btnXacNhan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String Noidung = txtNoiDung.getText().toString() +" , "+ ratingBar.getRating();
                Intent i = new Intent();
                i.putExtra("noidungbinhluan",Noidung);
                setResult(88,i);
                finish();
                Toast.makeText(Comment_Evaluate_Activity.this, String.valueOf("Noi dung: " + Noidung), Toast.LENGTH_SHORT).show();

            }

        });
        btnHuy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(88,i);
                finish();
            }

        });

    }
}
