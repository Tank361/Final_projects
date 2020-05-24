package com.example.final_projects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_login);
        Button login =findViewById(R.id.btn_Login);
        login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        sendRequestWithHttpURLConnection();

    }
    private void sendRequestWithHttpURLConnection() {
        //开启线程发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                TextView ta=findViewById(R.id.textView1);
                HttpURLConnection connection = null;
                BufferedReader reader ;
                try {
                    //获取HttpRULConnection实例
                    URL url = new URL("http://163.17.9.130/login.php");
                    connection = (HttpURLConnection) url.openConnection();
                    //设置请求方法和自由定制
                    connection.setUseCaches(false);
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                    connection.connect();
                    InputStream in = connection.getInputStream();
                    //对输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    JSONArray array = new JSONArray(reader.readLine());
                    for (int i =0; i< array.length();i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        String email = jsonObject.getString("email");
                        String password = jsonObject.getString("password");
                        EditText ed =findViewById(R.id.email1);
                        EditText ed1 =findViewById(R.id.password1);
                        if (email.equals(ed.getText().toString())&&password.equals(ed1.getText().toString())){
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            finish();
                            startActivity(intent);
                            ta.setText("成功登入");
                            break;
                        }else{
                            ta.setText("登入失敗");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public void Register(View view) {
        Intent intent = new Intent(Login.this,Register.class);
        //finish();
        startActivity(intent);
    }
}
