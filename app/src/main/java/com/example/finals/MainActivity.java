package com.example.finals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView autoTextMain;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("주식 검색 어플리케이션");

        btnSearch = (Button) findViewById(R.id.btnSearch);
        autoTextMain = (AutoCompleteTextView) findViewById(R.id.autoTextMain);

        // 해쉬맵 자료형에 있는 키들 호출해서 자동완성뷰에 사용
        Hashmap data = new Hashmap();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data.getKey());
        autoTextMain.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AfterSearch.class);
                intent.putExtra("Name", autoTextMain.getText().toString());

                startActivityForResult(intent, 0);

            }
        });
    }
}
