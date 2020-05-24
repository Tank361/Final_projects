package com.example.final_projects;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class rate_currencyFragment extends Fragment {
    private View mainView;
    double USDcash,USDspot,JPYcash,JPYspot,EURcash,EURspot,CNYcash,CNYspot,AUDcash,AUDspot;
    private TextView txv3,txv4,txv5,txv6,txv7,txv8,txv9,txv10,txv11,txv12;
    public rate_currencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        sendRequestWithHttpURLConnection();



        mainView=inflater.inflate(R.layout.rate_currency, container, false);
        txv3 = mainView.findViewById(R.id.textView3);
        txv4 = mainView.findViewById(R.id.textView4);
        txv5 = mainView.findViewById(R.id.textView5);
        txv6 = mainView.findViewById(R.id.textView6);
        txv7 = mainView.findViewById(R.id.textView7);
        txv8 = mainView.findViewById(R.id.textView8);
        txv9 = mainView.findViewById(R.id.textView9);
        txv10 = mainView.findViewById(R.id.textView10);
        txv11 = mainView.findViewById(R.id.textView11);
        txv12 = mainView.findViewById(R.id.textView12);


        txv4.setText(String.valueOf(USDspot));
        txv6.setText(String.valueOf(JPYspot));
        txv8.setText(String.valueOf(EURspot));
        txv10.setText(String.valueOf(CNYspot));
        txv12.setText(String.valueOf(AUDspot));


        txv3.setText(String.valueOf(USDcash));
        txv5.setText(String.valueOf(JPYcash));
        txv7.setText(String.valueOf(EURcash));
        txv9.setText(String.valueOf(CNYcash));
        txv11.setText(String.valueOf(AUDcash));
        return mainView;
    }
    private void sendRequestWithHttpURLConnection() {
        //开启线程发起网络请求
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader ;
                try {
                    //获取HttpRULConnection实例
                    URL url = new URL("http://163.17.9.130/currency.php");
                    connection = (HttpURLConnection) url.openConnection();
                    //设置请求方法和自由定制
                    connection.setUseCaches(false);
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(20000);
                    connection.setReadTimeout(20000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                    connection.connect();



                    InputStream in = connection.getInputStream();
                    //对输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    JSONArray array = new JSONArray(reader.readLine());

                    //////////美金
                    JSONObject jsonObject0 = array.getJSONObject(0);
                    Double USDcashRateBuying = jsonObject0.getDouble("cashRateBuying");
                    Double USDspotRateBuying = jsonObject0.getDouble("spotRateBuying");
                    USDcash =USDcashRateBuying;
                    USDspot =USDspotRateBuying;

                    ////////日幣
                    JSONObject jsonObject7 = array.getJSONObject(7);
                    Double JPYcashRateBuying = jsonObject7.getDouble("cashRateBuying");
                    Double JPYspotRateBuying = jsonObject7.getDouble("spotRateBuying");
                    JPYcash =JPYcashRateBuying;
                    JPYspot =JPYspotRateBuying;

                    ////////歐元
                    JSONObject jsonObject14 = array.getJSONObject(14);
                    Double EURcashRateBuying = jsonObject14.getDouble("cashRateBuying");
                    Double EURspotRateBuying = jsonObject14.getDouble("spotRateBuying");
                    EURcash =EURcashRateBuying;
                    EURspot =EURspotRateBuying;

                    ////////人民幣
                    JSONObject jsonObject18 = array.getJSONObject(18);
                    Double CNYcashRateBuying = jsonObject18.getDouble("cashRateBuying");
                    Double CNYspotRateBuying = jsonObject18.getDouble("spotRateBuying");
                    CNYcash =CNYcashRateBuying;
                    CNYspot =CNYspotRateBuying;

                    ////////澳幣
                    JSONObject jsonObject3 = array.getJSONObject(3);
                    Double AUDcashRateBuying = jsonObject3.getDouble("cashRateBuying");
                    Double AUDspotRateBuying = jsonObject3.getDouble("spotRateBuying");
                    AUDcash =AUDcashRateBuying;
                    AUDspot =AUDspotRateBuying;


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
}
