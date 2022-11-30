package com.example.finals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Share{
    ArrayList<String> arrayList;
    String a;

    public Share(String a) {
        arrayList = new ArrayList<String>();
        this.a = a;
    }

    public ArrayList<String> getShare() {

        try {
            Document doc = Jsoup.connect("https://finance.naver.com/item/main.nhn?code=" + a).get();
            Elements contents;

            // 수치 데이터를 가져올 위치(arrayList 인덱스 0 ~ 3)
            String where[] = {"#chart_area > div.rate_info > div > p.no_today > em",
                    "#chart_area > div.rate_info > table > tbody > tr:nth-child(1) > td:nth-child(2) > em.no_up",
                    "table > tbody > tr:nth-child(2) > td.first > em",
                    "#chart_area > div.rate_info > table > tbody > tr:nth-child(2) > td:nth-child(2) > em.no_down",
                    "#middle > div.h_company > div.wrap_company > h2 > a"};


            for(int i = 0; i < 4; i++){
                contents = doc.select(where[i]);
                String a = contents.text().substring(0, contents.text().length()/2); // 내용물이 두번씩 출력되는데 한번만 출력되게 함
                arrayList.add(a);
            }

            // 제목(arrayList 인덱스 4)
            contents = doc.select("#middle > div.h_company > div.wrap_company > h2 > a");
            arrayList.add(contents.text());

            // 그림(arrayList 인덱스 5)
            contents = doc.select("#img_chart_area[src]");
            String day = contents.attr("src");
            String year = day.replace("day", "year"); // 기본 설정으로 일 단위 대신 연 단위 그래프 사용
            arrayList.add(year);

            // 기업 개요 데이터를 가져올 위치(arrayList 인덱스 6~8)
            String whereOverView[] = {"#summary_info > p:nth-child(2)",
                    "#summary_info > p:nth-child(3)",
                    "#summary_info > p:nth-child(4)"};

            for(int i = 0; i < 3; i++){
                contents = doc.select(whereOverView[i]);
                String a = contents.text();
                arrayList.add(a);
            }

            // 해당 기업 관련 뉴스
            String whereNews = "#content > div.section.new_bbs > div.sub_section.news_section > ul:nth-child(2) > li:nth-child(1) > span > a";

            for(int i = 1; i < 6; i++){
                contents = doc.select(whereNews.replace("li:nth-child(1)", "li:nth-child(" + i + ")"));
                String a = contents.text();
                arrayList.add(a);
                a = "https://finance.naver.com" + contents.attr("href");
                arrayList.add(a);
            }


        } catch (IOException e) {
            //e.printStackTrace();
            Log.d("getShare()함수 에러 : ", e.getMessage());
        }

        return arrayList;
    }
}
