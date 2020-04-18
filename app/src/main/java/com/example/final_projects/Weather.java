package com.example.final_projects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Weather extends AppCompatActivity {

    private RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                .Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<Post> data = new ArrayList<>();
        // JSON連接伺服器資料
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://163.17.9.130/weather_15day.php");
            HttpResponse response = client.execute(get);
            String jsonText = EntityUtils.toString(response.getEntity());

            JSONArray array = new JSONArray(jsonText);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String day = obj.getString("day");
                String description = obj.getString("description");
                Integer high = obj.getInt("high");
                Integer low = obj.getInt("low");
                Integer precip = obj.getInt("precip");
                Integer humidity = obj.getInt("humidity");
                data.add(new Post(
                        "日期："+day,
                        "天氣："+description,
                        "最高溫："+high+"°",
                        "最低溫："+low+"°",
                        "降雨機率："+precip+"%",
                        "濕度："+humidity+"%" ));
            }

        } catch (Exception e) {
            Log.e("aaa", e.getMessage());
            e.printStackTrace();
        }

        // 連結元件
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        // 設置RecyclerView為列表型態
        MyAdapter adapter = new MyAdapter(this, data);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        // 設置格線
        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recycler_view.setAdapter(adapter);

    }

}
