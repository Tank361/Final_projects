package com.example.final_projects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Weather extends AppCompatActivity {

    private RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        sendRequestWithHttpURLConnection();
    }
    ArrayList<Post> data = new ArrayList<>();
    private void sendRequestWithHttpURLConnection() {
        //開啟線程發起網路請求
        new Thread(new Runnable() {
            @Override
            public void run () {
                HttpURLConnection connection = null;
                BufferedReader reader;
                try {
                    //獲取HttpURLConnection實例
                    URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=taipei&APPID=9c39fa3ce9d953fdd507d7d9f77093ef&lang=zh_tw&units=metric");
                    connection = (HttpURLConnection) url.openConnection();
                    //設置請求方法和自由定制
                    connection.setUseCaches(false);
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    connection.connect();

                    InputStream in = connection.getInputStream();
                    //對輸入流進行讀取
                    reader = new BufferedReader(new InputStreamReader(in));

                    //解析JSON多層資料
                    JSONObject jsonObject = new JSONObject(reader.readLine());
                    JSONArray array = jsonObject.getJSONArray("list");
                    for (int i=0; i<array.length(); i++) {
                        String time = array.getJSONObject(i).getString("dt_txt");
                        String temp = array.getJSONObject(i).getJSONObject("main").getString("temp");
                        String humidity = array.getJSONObject(i).getJSONObject("main").getString("humidity");
                        String description = array.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");

                        //顯示各元件取得的資料
                        data.add(new Post(
                                time,
                                temp+"°",
                                description,
                                humidity+"%")); }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
        // 連結元件
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        // 設置RecyclerView為列表型態
        MyAdapter adapter = new MyAdapter(data);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        // 設置格線
        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler_view.setAdapter(adapter);
    }
}


