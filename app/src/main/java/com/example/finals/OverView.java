package com.example.finals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OverView extends AppCompatActivity {

    TextView tvOverView;
    Button btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        setTitle("기업 정보");

        tvOverView = (TextView)findViewById(R.id.tvOverView);
        btnBack = (Button)findViewById(R.id.btnBack);

        Intent inIntent = getIntent();
        final String OverView1 = inIntent.getStringExtra("OverView1");
        final String OverView2 = inIntent.getStringExtra("OverView2");
        final String OverView3 = inIntent.getStringExtra("OverView3");

        tvOverView.setText(OverView1 + "\r\n" + OverView2 + "\r\n" + OverView3);

        // 뒤로 가기 버튼
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
