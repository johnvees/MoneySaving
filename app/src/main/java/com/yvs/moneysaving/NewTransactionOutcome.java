package com.yvs.moneysaving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.Map;

public class NewTransactionOutcome extends AppCompatActivity {

    Spinner spn_categoryOutcomeCode;
    EditText et_descOutcomeCode, et_dateOutcomeCode, et_amountOutcomeCode;
    TextView tv_outcomeTransactionTitleCode;
    Button btn_saveOutcomeCode;
    ImageButton btn_outcomeTransactionToMyWalletCode, btn_outcomeTransactionToHistoryCode, ibtn_menuIncomeInactiveCode;

    String category, description, date, amount;

    ArrayList<String> outcomeCategoryList = new ArrayList<>();
    ArrayAdapter<String> outcomeCategoryAdapter;

    //Stringrequest salah satu library volley untuk menangkap data
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    //deklarasi variable untuk JSON
    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSON
    private JSONArray jsonArray;

    //deklarasi variable tambahan
    private String URL = "http://192.168.1.4/PMobile/MoneySaving/selectSpinnerOutcome.php";
    private String insertOutcomeURL = "http://192.168.1.4/PMobile/MoneySaving/insertOutcome.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction_outcome);

        spn_categoryOutcomeCode = (Spinner)findViewById(R.id.spn_categoryOutcome);
        et_descOutcomeCode = (EditText)findViewById(R.id.et_descOutcome);
        et_dateOutcomeCode = (EditText)findViewById(R.id.et_dateOutcome);
        et_amountOutcomeCode = (EditText)findViewById(R.id.et_amountOutcome);
        tv_outcomeTransactionTitleCode = (TextView)findViewById(R.id.tv_outcomeTransactionTitle);
        btn_saveOutcomeCode = (Button)findViewById(R.id.btn_saveOutcome);
        btn_outcomeTransactionToMyWalletCode = (ImageButton)findViewById(R.id.btn_outcomeTransactionToMyWallet);
        btn_outcomeTransactionToHistoryCode = (ImageButton)findViewById(R.id.btn_outcomeTransactionToHistory);
        ibtn_menuIncomeInactiveCode = (ImageButton)findViewById(R.id.ibtn_menuIncomeInactive);

        btn_outcomeTransactionToMyWalletCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToMyWallet = new Intent(NewTransactionOutcome.this, MyWallet.class);
                startActivity(moveToMyWallet);
            }
        });

        btn_outcomeTransactionToHistoryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToHistory = new Intent(NewTransactionOutcome.this, History.class);
                startActivity(moveToHistory);
            }
        });

        ibtn_menuIncomeInactiveCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToIncomeTransaction = new Intent(NewTransactionOutcome.this, NewTransactionIncome.class);
                startActivity(moveToIncomeTransaction);
            }
        });

        //instance of class
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //test response jika masuk pada method onResponse
//                tv_incomeTransactionTitleCode.setText("masuk pada method onResponse");

                try{
                    //instance of class JSONObject
                    jsonObj = new JSONObject(response);
                    jsonArray = jsonObj.getJSONArray("coba");
                    for (int i = 0; i < jsonArray.length(); i++){
                        //instance of class JSONObj untuk per baris, tampung ke dalam variable
                        jsonData = jsonArray.getJSONObject(i);
                        String categoryName = jsonData.getString("category_name");
                        outcomeCategoryList.add(categoryName);
                        outcomeCategoryAdapter = new ArrayAdapter<>(NewTransactionOutcome.this, android.R.layout.simple_spinner_item, outcomeCategoryList);
                        outcomeCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spn_categoryOutcomeCode.setAdapter(outcomeCategoryAdapter);

                        //Set the text color of the Spinner's selected view (not a drop down list view)
                        spn_categoryOutcomeCode.setSelection(0, true);
                        View v = spn_categoryOutcomeCode.getSelectedView();
                        ((TextView)v).setTextColor(Color.WHITE);

                        spn_categoryOutcomeCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //Change the selected item's text color
                                ((TextView) view).setTextColor(Color.WHITE);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //test response jika masuk pada method onErrorResponse
                tv_outcomeTransactionTitleCode.setText(error.toString());

            }
        });
        //request ke Volley
        requestQueue = Volley.newRequestQueue(NewTransactionOutcome.this);
        requestQueue.add(stringRequest);

        btn_saveOutcomeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //untuk memastikan data yang terkirim sudah terisi di EditText, maka EditText tidak boleh kosong
                //tampung data dari EditText ke variable
                category = spn_categoryOutcomeCode.getSelectedItem().toString().trim();
                description = et_descOutcomeCode.getText().toString().trim();
                date = et_dateOutcomeCode.getText().toString().trim();
                amount = et_amountOutcomeCode.getText().toString().trim();

                //error handling, jika username dan password belom terisi
                if (category.equals("") || description.equals("") || date.equals("") || amount.equals("")){
                    Toast.makeText(NewTransactionOutcome.this, "Isilah Semua Inputan Yang Tidak Lengkap", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(NewTransactionOutcome.this, "Data Tersimpan", Toast.LENGTH_LONG).show();

                    stringRequest = new StringRequest(Request.Method.POST, insertOutcomeURL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //test response jika masuk pada method onResponse
                            tv_outcomeTransactionTitleCode.setText("masuk pada method onResponse");

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //test response jika masuk pada method onErrorResponse
                            tv_outcomeTransactionTitleCode.setText(error.toString());
                        }
                    })
                    {
                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("category_otc", category);
                            data.put("description_otc", description);
                            data.put("date_otc", date);
                            data.put("amount_otc",amount);
                            return data;
                        }
                    };
                    //request ke Volley
                    requestQueue = Volley.newRequestQueue(NewTransactionOutcome.this);
                    requestQueue.add(stringRequest);

                    Intent moveToMyWallet = new Intent(NewTransactionOutcome.this, MyWallet.class);
                    startActivity(moveToMyWallet);

                }

            }

        });
    }
}