package com.yvs.moneysaving;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class History extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;

    List<HistoryData> historyDataList;

    ImageButton btn_historyToTransactionCode, btn_historyToMyWalletCode;

    TextView tv_outcomeAmountHistoryCode, tv_incomeAmountHistoryCode;

    String id, category, description, date, amount, incomeAmount, outcomeAmount;
    String icon;

    //Stringrequest salah satu library volley untuk menangkap data
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    //deklarasi variable untuk JSON
    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSON
    private JSONArray jsonArray;

    //deklarasi variable tambahan
    private String historyIncomeDataURl = "http://192.168.1.4/PMobile/MoneySaving/selectHistoryIncome.php";
    private String historyOutcomeDataURl = "http://192.168.1.4/PMobile/MoneySaving/selectHistoryOutcome.php";
    private String selectIncomeURL = "http://192.168.1.4/PMobile/MoneySaving/selectIncome.php";
    private String selectOutcomeURL = "http://192.168.1.4/PMobile/MoneySaving/selectOutcome.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyDataList = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.rv_transactionHistory);
        btn_historyToTransactionCode = (ImageButton)findViewById(R.id.btn_historyToTransaction);
        btn_historyToMyWalletCode = (ImageButton)findViewById(R.id.btn_historyToMyWallet);
        tv_incomeAmountHistoryCode = (TextView)findViewById(R.id.tv_incomeAmountHistory);
        tv_outcomeAmountHistoryCode = (TextView)findViewById(R.id.tv_outcomeAmountHistory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_historyToMyWalletCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToMyWallet = new Intent(History.this, MyWallet.class);
                startActivity(moveToMyWallet);
            }
        });

        btn_historyToTransactionCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToTransaction = new Intent(History.this, NewTransactionIncome.class);
                startActivity(moveToTransaction);
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

                    tv_incomeAmountHistoryCode.setText(incomeAmount);

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(History.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        requestQueue = Volley.newRequestQueue(History.this);
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

                    tv_outcomeAmountHistoryCode.setText(outcomeAmount);

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(History.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        requestQueue = Volley.newRequestQueue(History.this);
        requestQueue.add(stringRequest);

        stringRequest = new StringRequest(Request.Method.POST, historyIncomeDataURl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //instance of class JSONObject
                    jsonObj = new JSONObject(response);
                    jsonArray = jsonObj.getJSONArray("coba");
                    for (int i =0; i< jsonArray.length(); i++){
                        jsonData = jsonArray.getJSONObject(i);

                        id = jsonData.getString("inc_id");
                        category = jsonData.getString("category_inc");
                        description = jsonData.getString("description_inc");
                        date =jsonData.getString("date_inc");
                        amount = jsonData.getString("amount_inc");

                        HistoryData history = new HistoryData(id, category, description, date, amount);
                        historyDataList.add(history);
                    }

                    historyAdapter = new HistoryAdapter(historyDataList, History.this );
                    recyclerView.setAdapter(historyAdapter);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(History.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

        };
        //request ke Volley
        requestQueue = Volley.newRequestQueue(History.this);
        requestQueue.add(stringRequest);

        stringRequest = new StringRequest(Request.Method.POST, historyOutcomeDataURl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //instance of class JSONObject
                    jsonObj = new JSONObject(response);
                    jsonArray = jsonObj.getJSONArray("coba");
                    for (int i =0; i< jsonArray.length(); i++){
                        jsonData = jsonArray.getJSONObject(i);

                        id = jsonData.getString("otc_id");
                        category = jsonData.getString("category_otc");
                        description = jsonData.getString("description_otc");
                        date =jsonData.getString("date_otc");
                        amount = jsonData.getString("amount_otc");

                        HistoryData history = new HistoryData(id, category, description, date, amount);
                        historyDataList.add(history);
                    }

                    historyAdapter = new HistoryAdapter(historyDataList, History.this );
                    recyclerView.setAdapter(historyAdapter);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(History.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

        };
        //request ke Volley
        requestQueue = Volley.newRequestQueue(History.this);
        requestQueue.add(stringRequest);
    }
}