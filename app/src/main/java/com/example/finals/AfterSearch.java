package com.example.finals;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AfterSearch extends AppCompatActivity {

    TextView tvName;
    TextView tvYesterdayPrice, tvStartPrice, tvHighestPrice, tvLowestPrice;
    Button btnInfo, btnNews;
    Button btnToMain;

    ArrayList<String> arrayList;

    Handler handler2 = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aftersearch);

        tvName = (TextView) findViewById(R.id.tvName);
        tvYesterdayPrice = (TextView) findViewById(R.id.tvYesterdayPrice);
        tvStartPrice = (TextView) findViewById(R.id.tvStartPrice);
        tvHighestPrice = (TextView) findViewById(R.id.tvHighestPrice);
        tvLowestPrice = (TextView) findViewById(R.id.tvLowestPrice);
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnNews = (Button) findViewById(R.id.btnNews);
        btnToMain = (Button) findViewById(R.id.btnToMain);


        Intent inIntent = getIntent();
        final String Name = inIntent.getStringExtra("Name");
        setTitle("주식 검색 어플리케이션(검색 후)");
        tvName.setText(Name);
        Toast.makeText(getApplication(), "검색 완료", Toast.LENGTH_SHORT).show();

        final Bundle bun = new Bundle();


        // 해쉬맵 자료형에 데이터 저장, 검색된 단어의 종목코드 불러오기
        Hashmap data = new Hashmap();
        data.putValue();
        final String code = data.getValue(Name);

        // 쓰레드를 추가로 만들어서 데이터 가져오는 작업 따로 처리(당일 가격, 고가, 시가, 저가, 그림)
        new Thread() {
            @Override
            public void run() {

                bun.clear();

                Share share = new Share(code);
                arrayList = share.getShare();

                bun.putStringArrayList("share",arrayList);
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);

                // 외부 이미지 출력 arrayList 인덱스 5

                final ImageView imgView = (ImageView)findViewById(R.id.imgView);
                try{
                    URL url = new URL(arrayList.get(5));
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler2.post(new Runnable() {
                        @Override
                        public void run() {
                            imgView.setImageBitmap(bm);
                        }
                    });
                    imgView.setImageBitmap(bm);

                } catch(Exception e){
                }

                // 기업 개요 버튼 눌렀을 때
                btnInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent2 = new Intent(getApplicationContext(), OverView.class);
                        intent2.putExtra("OverView1", arrayList.get(6));
                        intent2.putExtra("OverView2", arrayList.get(7));
                        intent2.putExtra("OverView3", arrayList.get(8));

                        startActivityForResult(intent2, 0);

                    }
                });

                // 관련 뉴스 버튼 눌렀을 때 (9, 11, 13, 15,17 기사 제목,  10, 12, 14, 16, 18 기사 링크)
                btnNews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent3 = new Intent(getApplicationContext(), News.class);

                        for (int i = 0; i < 5; i++) {
                            intent3.putExtra("News" + i, arrayList.get(i*2 + 9));
                            intent3.putExtra("NewsUrl" + i, arrayList.get(i*2 + 10));
                        }

                        startActivityForResult(intent3, 0);
                    }
                });

            }
        }.start();


        // 뒤로 가기 버튼
        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    // 당일 가격, 시가, 고가, 저가, 종목이름(arrayList 인덱스 1, 2, 3, 4, 5)
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();

            String str1 = "";
            String str2 = "";
            String str3 = "";
            String str4 = "";

            ArrayList<String> arrayList = bun.getStringArrayList("share");

            str1 = arrayList.get(0);
            str2 = arrayList.get(1);
            str3 = arrayList.get(2);
            str4 = arrayList.get(3);


            tvYesterdayPrice.setText(str1);
            tvHighestPrice.setText(str2);
            tvStartPrice.setText(str3);
            tvLowestPrice.setText(str4);

            tvName.setText(arrayList.get(4));
        }
    };

}
