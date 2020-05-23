package com.example.final_projects;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
*/
public class rate_exchangeRateFragment extends Fragment {
    private View mainView;
    private EditText etNum1,etNum2,etNum3,etNum4,etNum5,etNum6;
    private TextView txv14,txv16,txv18,txv20,txv22;
    private RadioGroup rg;
    private RadioButton spot, cash;
    double USDcash,USDspot,JPYcash,JPYspot,EURcash,EURspot,CNYcash,CNYspot,AUDcash,AUDspot,eqlTWD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sendRequestWithHttpURLConnection();

        mainView =  inflater.inflate(R.layout.rate_exchange_rate, container, false);
        Button exchange = mainView.findViewById(R.id.exchange);
        etNum1 = mainView.findViewById(R.id.editNum1);
        etNum2 = mainView.findViewById(R.id.editNum2);
        etNum3 = mainView.findViewById(R.id.editNum3);
        etNum4 = mainView.findViewById(R.id.editNum4);
        etNum5 = mainView.findViewById(R.id.editNum5);
        etNum6 = mainView.findViewById(R.id.editNum6);
        txv14 = mainView.findViewById(R.id.textView14);
        txv16 = mainView.findViewById(R.id.textView16);
        txv18 = mainView.findViewById(R.id.textView18);
        txv20 = mainView.findViewById(R.id.textView20);
        txv22 = mainView.findViewById(R.id.textView22);
        spot = mainView.findViewById(R.id.spot);
        cash = mainView.findViewById(R.id.cash);
        spot.setOnCheckedChangeListener(mOnCheckedChangeListener);
        cash.setOnCheckedChangeListener(mOnCheckedChangeListener);
        rg = mainView.findViewById(R.id.radioGroup1);

        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rg.getCheckedRadioButtonId()) {
                    case R.id.spot:
                        exchange(); //顯示結果
                        break;
                    case R.id.cash:
                        exchange2(); //顯示結果
                        break;
                }
            }
        });
        return mainView;
    }
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.spot:
                    txv14.setText(String.valueOf(USDcash));
                    txv16.setText(String.valueOf(JPYcash));
                    txv18.setText(String.valueOf(EURcash));
                    txv20.setText(String.valueOf(CNYcash));
                    txv22.setText(String.valueOf(AUDcash));
                    etNum2.setHint(String.valueOf(USDcash));
                    etNum3.setHint(String.valueOf(JPYcash));
                    etNum4.setHint(String.valueOf(EURcash));
                    etNum5.setHint(String.valueOf(CNYcash));
                    etNum6.setHint(String.valueOf(AUDcash));
                    break;
                case R.id.cash:
                    txv14.setText(String.valueOf(USDspot));
                    txv16.setText(String.valueOf(JPYspot));
                    txv18.setText(String.valueOf(EURspot));
                    txv20.setText(String.valueOf(CNYspot));
                    txv22.setText(String.valueOf(AUDspot));

                    etNum2.setHint(String.valueOf(USDspot));
                    etNum3.setHint(String.valueOf(JPYspot));
                    etNum4.setHint(String.valueOf(EURspot));
                    etNum5.setHint(String.valueOf(CNYspot));
                    etNum6.setHint(String.valueOf(AUDspot));
                    break;
            }
        }
    };


    public void exchange() {
        DecimalFormat mDecimalFormat = new DecimalFormat("#,###.##");
        if (etNum1.getText().toString().equals("")){
            eqlTWD=0.0;
        }
        else{
            eqlTWD=Double.parseDouble(etNum1.getText().toString());
        }
        double TWD = eqlTWD;
        double USD = USDspot;
        double JPY = JPYspot;
        double EUR = EURspot;
        double CNY = CNYspot;
        double AUD = AUDspot;
        double USDtotal = TWD/USD;
        double JPYtotal = TWD/JPY;
        double EURtotal = TWD/EUR;
        double CNYtotal = TWD/CNY;
        double AUDtotal = TWD/AUD;
        etNum2.setText(mDecimalFormat.format(USDtotal));
        etNum3.setText(mDecimalFormat.format(JPYtotal));
        etNum4.setText(mDecimalFormat.format(EURtotal));
        etNum5.setText(mDecimalFormat.format(CNYtotal));
        etNum6.setText(mDecimalFormat.format(AUDtotal));
    }
    public void exchange2() {
        DecimalFormat mDecimalFormat = new DecimalFormat("#,###.##");
        if (etNum1.getText().toString().equals("")){
            eqlTWD=0.0;
        }
        else{
            eqlTWD=Double.parseDouble(etNum1.getText().toString());
        }
        double TWD = eqlTWD;
        double USD = USDcash;
        double JPY = JPYcash;
        double EUR = EURcash;
        double CNY = CNYcash;
        double AUD = AUDcash;
        double USDtotal = TWD*USD;
        double JPYtotal = TWD*JPY;
        double EURtotal = TWD*EUR;
        double CNYtotal = TWD*CNY;
        double AUDtotal = TWD*AUD;
        etNum2.setText(mDecimalFormat.format(USDtotal));
        etNum3.setText(mDecimalFormat.format(JPYtotal));
        etNum4.setText(mDecimalFormat.format(EURtotal));
        etNum5.setText(mDecimalFormat.format(CNYtotal));
        etNum6.setText(mDecimalFormat.format(AUDtotal));
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
