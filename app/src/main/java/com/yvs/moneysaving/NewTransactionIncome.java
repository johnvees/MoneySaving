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

public class NewTransactionIncome extends AppCompatActivity {

    Spinner spn_categoryIncomeCode;
    EditText et_descIncomeCode, et_dateIncomeCode, et_amountIncomeCode;
    TextView tv_incomeTransactionTitleCode;
    Button btn_saveIncomeCode;
    ImageButton btn_incomeTransactionToMyWalletCode, btn_incomeTransactionToHistoryCode, ibtn_menuOutcomeActiveCode;

    String category, description, date, amount;

    ArrayList<String> incomeCategoryList = new ArrayList<>();
    ArrayAdapter<String> incomeCategoryAdapter;

    //Stringrequest salah satu library volley untuk menangkap data
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    //deklarasi variable untuk JSON
    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSON
    private JSONArray jsonArray;

    //deklarasi variable tambahan
    private String URL = "http://192.168.1.4/PMobile/MoneySaving/selectSpinnerIncome.php";
    private String insertIncomeURL = "http://192.168.1.4/PMobile/MoneySaving/insertIncome.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction_income);

        spn_categoryIncomeCode = (Spinner)findViewById(R.id.spn_categoryIncome);
        et_descIncomeCode = (EditText)findViewById(R.id.et_descIncome);
        et_dateIncomeCode = (EditText)findViewById(R.id.et_dateIncome);
        et_amountIncomeCode = (EditText)findViewById(R.id.et_amountIncome);
        tv_incomeTransactionTitleCode = (TextView)findViewById(R.id.tv_incomeTransactionTitle) ;
        btn_saveIncomeCode = (Button)findViewById(R.id.btn_saveIncome);
        btn_incomeTransactionToMyWalletCode = (ImageButton) findViewById(R.id.btn_incomeTransactionToMyWallet);
        btn_incomeTransactionToHistoryCode =(ImageButton)findViewById(R.id.btn_incomeTransactionToHistory);
        ibtn_menuOutcomeActiveCode = (ImageButton)findViewById(R.id.ibtn_menuOutcomeActive);

        btn_incomeTransactionToMyWalletCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToMyWallet = new Intent(NewTransactionIncome.this, MyWallet.class);
                startActivity(moveToMyWallet);
            }
        });

        btn_incomeTransactionToHistoryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToHistory = new Intent(NewTransactionIncome.this, History.class);
                startActivity(moveToHistory);
            }
        });

        ibtn_menuOutcomeActiveCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToOutcomeTransaction = new Intent(NewTransactionIncome.this, NewTransactionOutcome.class);
                startActivity(moveToOutcomeTransaction);
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
                        incomeCategoryList.add(categoryName);
                        incomeCategoryAdapter = new ArrayAdapter<>(NewTransactionIncome.this, android.R.layout.simple_spinner_item, incomeCategoryList);
                        incomeCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spn_categoryIncomeCode.setAdapter(incomeCategoryAdapter);

                        //Set the text color of the Spinner's selected view (not a drop down list view)
                        spn_categoryIncomeCode.setSelection(0, true);
                        View v = spn_categoryIncomeCode.getSelectedView();
                        ((TextView)v).setTextColor(Color.WHITE);

                        spn_categoryIncomeCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                tv_incomeTransactionTitleCode.setText(error.toString());

            }
        });
        //request ke Volley
        requestQueue = Volley.newRequestQueue(NewTransactionIncome.this);
        requestQueue.add(stringRequest);


        btn_saveIncomeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //untuk memastikan data yang terkirim sudah terisi di EditText, maka EditText tidak boleh kosong
                //tampung data dari EditText ke variable
                category = spn_categoryIncomeCode.getSelectedItem().toString().trim();
                description = et_descIncomeCode.getText().toString().trim();
                date = et_dateIncomeCode.getText().toString().trim();
                amount = et_amountIncomeCode.getText().toString().trim();

                //error handling, jika username dan password belom terisi
                if (category.equals("") || description.equals("") || date.equals("") || amount.equals("")){
                    Toast.makeText(NewTransactionIncome.this, "Isilah Semua Inputan Yang Tidak Lengkap", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(NewTransactionIncome.this, "Data Tersimpan", Toast.LENGTH_LONG).show();

                    stringRequest = new StringRequest(Request.Method.POST, insertIncomeURL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //test response jika masuk pada method onResponse
                            tv_incomeTransactionTitleCode.setText("masuk pada method onResponse");

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //test response jika masuk pada method onErrorResponse
                            tv_incomeTransactionTitleCode.setText(error.toString());
                        }
                    })
                    {
                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("category_inc", category);
                            data.put("description_inc", description);
                            data.put("date_inc", date);
                            data.put("amount_inc",amount);
                            return data;
                        }
                    };
                    //request ke Volley
                    requestQueue = Volley.newRequestQueue(NewTransactionIncome.this);
                    requestQueue.add(stringRequest);

                    Intent moveToMyWallet = new Intent(NewTransactionIncome.this, MyWallet.class);
                    startActivity(moveToMyWallet);

                }

            }

        });
    }
}