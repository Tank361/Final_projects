package com.example.final_projects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Register extends AppCompatActivity {
    String mysql_ip = "163.17.9.130";
    int mysql_port = 3306; // Port 預設為 3306
    String db_name = "mydb";
    String url = "jdbc:mysql://"+mysql_ip+":"+mysql_port+"/"+db_name;
    String db_user = "user";
    String db_password = "im123456";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_register);
        Button ok_button = findViewById(R.id.btn_regist);
        ok_button.setOnClickListener(ok_btn);
    }
    public void insertData(String name,String email,String password,String age) {
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "INSERT INTO `register` (`name`,`email`,`password`,`age`) VALUES ('"+name+"','" + email+ "','"+password+"','"+age+"')";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            st.close();
            Log.v("DB", "寫入資料完成：" + name+email+password+age);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB", "寫入資料失敗");
            Log.e("DB", e.toString());
        }
    }
    private Button.OnClickListener ok_btn = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Register.this,Login.class);
            startActivity(intent);
            Toast.makeText(Register.this,"成功註冊",Toast.LENGTH_LONG).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 取得 EditText 資料
                    final EditText edit_text =  findViewById(R.id.name);
                    final EditText edit_text2 = findViewById(R.id.email1);
                    final EditText edit_text3 = findViewById(R.id.password1);
                    final EditText edit_text4 = findViewById(R.id.age);
                    String stringdata1 = edit_text.getText().toString();
                    String stringdata2 = edit_text2.getText().toString();
                    String stringdata3 = edit_text3.getText().toString();
                    String stringdata4 = edit_text4.getText().toString();
                    // 清空 EditText
                    edit_text.post(new Runnable() {
                        public void run() {
                            edit_text.setText("");
                            edit_text2.setText("");
                            edit_text3.setText("");
                            edit_text4.setText("");
                        }
                    });
                    // 將資料寫入資料庫
                    insertData(stringdata1,stringdata2,stringdata3,stringdata4);
                }
            }).start();
        }
    };
}
