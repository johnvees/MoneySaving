package com.yvs.moneysaving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyWallet extends AppCompatActivity {

    TextView tv_myWalletTitleCode, tv_totalAmountWalletCode, tv_incomeAmountWalletCode, tv_incomePercentWalletCode, tv_outcomeAmountWalletCode, tv_outcomePercentWalletCode;
    ImageButton btn_myWalletToTransactionCode, btn_myWalletToHistoryCode, btn_myWalletTOFaqCode;


    //deklarasi variable tambahan
    private String URL = "http://192.168.1.4/PMobile/MoneySaving/select.php";
    private String selectIncomeURL = "http://192.168.1.4/PMobile/MoneySaving/selectIncome.php";
    private String selectOutcomeURL = "http://192.168.1.4/PMobile/MoneySaving/selectOutcome.php";

    //Stringrequest salah satu library volley untuk menangkap data
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    //deklarasi variable untuk JSON
    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSON
    private JSONArray jsonArray;
    String totalAmount, incomeAmount, outcomeAmount, incomePercentage, outcomePercentage;
    int incomeAmountTest, outcomeAmountTest, incomePercentageTest, totalAmountTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        tv_myWalletTitleCode = (TextView)findViewById(R.id.tv_myWalletTitle);
        tv_totalAmountWalletCode = (TextView)findViewById(R.id.tv_totalAmountWallet);
        tv_incomeAmountWalletCode = (TextView)findViewById(R.id.tv_incomeAmountWallet);
        tv_incomePercentWalletCode = (TextView)findViewById(R.id.tv_incomePercentWallet);
        tv_outcomeAmountWalletCode = (TextView)findViewById(R.id.tv_outcomeAmountWallet);
        tv_outcomePercentWalletCode = (TextView)findViewById(R.id.tv_outcomePercentWallet);
        btn_myWalletToTransactionCode = (ImageButton) findViewById(R.id.btn_myWalletToTransaction);
        btn_myWalletToHistoryCode = (ImageButton)findViewById(R.id.btn_myWalletToHistory);
        btn_myWalletTOFaqCode = (ImageButton)findViewById(R.id.btn_myWalletTOFaq);



        btn_myWalletToTransactionCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToTransaction = new Intent(MyWallet.this, NewTransactionIncome.class);
                startActivity(moveToTransaction);
            }
        });

        btn_myWalletToHistoryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToHistory = new Intent(MyWallet.this, History.class);
                startActivity(moveToHistory);
            }
        });

        btn_myWalletTOFaqCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToFaq = new Intent(MyWallet.this, FAQ.class);
                startActivity(moveToFaq);
            }
        });

        stringRequest = new StringRequest(Request.Method.POST, selectIncomeURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //instance of class JSONObject
                    jsonObj = new JSONObject(response);
                    jsonArray = jsonObj.getJSONArray("coba");
                    for (int i = 0; i < jsonArray.length(); i++){
                        //instance of class JSONObj untuk per baris, tampung ke dalam variable
                        jsonData = jsonArray.getJSONObject(i);
                        incomeAmount = jsonData.getString("SUM(amount_inc)");

                    }

                    tv_incomeAmountWalletCode.setText(incomeAmount);

                    incomeAmountTest = Integer.parseInt(tv_incomeAmountWalletCode.getText().toString());
//
//                    tv_totalAmountWalletCode.setText(String.valueOf(incomeAmountTest - Integer.parseInt(incomeAmount)));

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyWallet.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        requestQueue = Volley.newRequestQueue(MyWallet.this);
        requestQueue.add(stringRequest);

        stringRequest = new StringRequest(Request.Method.POST, selectOutcomeURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //instance of class JSONObject
                    jsonObj = new JSONObject(response);
                    jsonArray = jsonObj.getJSONArray("coba");
                    for (int i = 0; i < jsonArray.length(); i++){
                        //instance of class JSONObj untuk per baris, tampung ke dalam variable
                        jsonData = jsonArray.getJSONObject(i);
                        outcomeAmount = jsonData.getString("SUM(amount_otc)");

                    }

                    tv_outcomeAmountWalletCode.setText(outcomeAmount);

                    outcomeAmountTest = Integer.parseInt(tv_outcomeAmountWalletCode.getText().toString());

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyWallet.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        requestQueue = Volley.newRequestQueue(MyWallet.this);
        requestQueue.add(stringRequest);

        //instance of class
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //test response jika masuk pada method onResponse
                //tv_myWalletTitleCode.setText("masuk pada method onResponse");

                try {
                    //instance of class JSONObject
                    jsonObj = new JSONObject(response);
                    jsonArray = jsonObj.getJSONArray("coba");
                    for (int i = 0; i < jsonArray.length(); i++){
                        //instance of class JSONObj untuk per baris, tampung ke dalam variable
                        jsonData = jsonArray.getJSONObject(i);
                        totalAmount = jsonData.getString("SUM(amount_inc - amount_otc)");
                        incomePercentage = jsonData.getString("CONCAT((SUM(amount_inc) / SUM(amount_inc+amount_otc)*100), '%')");
                        outcomePercentage = jsonData.getString("CONCAT((SUM(amount_otc) / SUM(amount_inc+amount_otc)*100), '%')");

                    }

                    totalAmountTest = incomeAmountTest - outcomeAmountTest;
                    tv_totalAmountWalletCode.setText(String.valueOf(totalAmountTest));
                    //incomePercentageTest = incomeAmountTest/(incomeAmountTest+outcomeAmountTest)*100;
                    //tv_incomePercentWalletCode.setText(String.valueOf(incomePercentageTest));
                    //tv_totalAmountWalletCode.setText(totalAmount);
                    tv_incomePercentWalletCode.setText(incomePercentage);
                    tv_outcomePercentWalletCode.setText(outcomePercentage);


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //test response jika masuk pada method onErrorResponse
                tv_myWalletTitleCode.setText(error.toString());
            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        //request ke Volley
        requestQueue = Volley.newRequestQueue(MyWallet.this);
        requestQueue.add(stringRequest);
    }
}