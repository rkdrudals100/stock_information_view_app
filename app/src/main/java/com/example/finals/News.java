package com.example.finals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class News extends AppCompatActivity {

    Button btnBack2;
    List<String> a;
    ArrayList<String> b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        setTitle("관련 뉴스");

        btnBack2 = (Button)findViewById(R.id.btnBack2);

        final ArrayList<String> midList = new ArrayList<String>();
        ListView list = (ListView) findViewById(R.id.listView1);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList);
        list.setAdapter(adapter);

        final Intent inIntent = getIntent();
        for (int i = 0; i < 5; i++) {
            midList.add(inIntent.getStringExtra("News" + i));
        }


        //해당 항목 클릭 시 해당 뉴스 띄움
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Uri uri = Uri.parse(inIntent.getStringExtra("NewsUrl" + position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });

        // 뒤로 가기 버튼
        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
