package com.yvs.moneysaving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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

public class UpdateHistory extends AppCompatActivity {

    ImageButton btn_updateDataToDetailHistoryCode;
    Button btn_confirmUpdateDataCode;
    TextView tv_updateDataTitleCode, tv_descUpdateDataCode, tv_amountUpdateDataCode, tv_idUpdateDataCode;
    EditText et_descUpdateDataBelowCode, et_dateUpdateDataBelowCode, et_amountUpdateDataBelowCode;
    Spinner spn_categoryUpdateDataBelowCode;

    String category, description, date, amount;

    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    ArrayList<String> CategoryList = new ArrayList<>();
    ArrayAdapter<String> CategoryAdapter;

    private JSONObject jsonObj, jsonData; //digunakan untuk proses pengambilan data JSON
    private JSONArray jsonArray;

    private String URLIncome = "http://192.168.1.4/PMobile/MoneySaving/selectSpinnerIncome.php";
    private String URLOutcome = "http://192.168.1.4/PMobile/MoneySaving/selectSpinnerOutcome.php";
    private String updateIncomeURL = "http://192.168.1.4/PMobile/MoneySaving/updateIncome.php";
    private String updateOutcomeURL = "http://192.168.1.4/PMobile/MoneySaving/updateOutcome.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_history);

        //btn_updateDataToDetailHistoryCode = (ImageButton)findViewById(R.id.btn_updateDataToDetailHistory);
        //btn_confirmUpdateDataCode = (Button)findViewById(R.id.btn_confirmUpdateData);
        tv_updateDataTitleCode = (TextView)findViewById(R.id.tv_updateDataTitle);
        tv_descUpdateDataCode = (TextView)findViewById(R.id.tv_descUpdateData);
        tv_amountUpdateDataCode = (TextView)findViewById(R.id.tv_amountUpdateData);
        tv_idUpdateDataCode = (TextView)findViewById(R.id.tv_idUpdateData);
        et_descUpdateDataBelowCode = (EditText)findViewById(R.id.et_descUpdateDataBelow);
        et_dateUpdateDataBelowCode = (EditText)findViewById(R.id.et_dateUpdateDataBelow);
        et_amountUpdateDataBelowCode = (EditText)findViewById(R.id.et_amountUpdateDataBelow);
        spn_categoryUpdateDataBelowCode = (Spinner)findViewById(R.id.spn_categoryUpdateDataBelow);

        tv_updateDataTitleCode.setText(getIntent().getStringExtra("category_inc"));
        tv_descUpdateDataCode.setText(getIntent().getStringExtra("description_inc"));
        tv_amountUpdateDataCode.setText(getIntent().getStringExtra("amount_inc"));
        tv_idUpdateDataCode.setText(getIntent().getStringExtra("inc_id"));
        tv_idUpdateDataCode.setText(getIntent().getStringExtra("otc_id"));
        et_descUpdateDataBelowCode.setText(getIntent().getStringExtra("description_inc"));
        et_dateUpdateDataBelowCode.setText(getIntent().getStringExtra("date_inc"));
        et_amountUpdateDataBelowCode.setText(getIntent().getStringExtra("amount_inc"));

//        btn_updateDataToDetailHistoryCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent moveToHistory = new Intent(UpdateHistory.this, History.class);
//                startActivity(moveToHistory);
//            }
//        });

        stringRequest = new StringRequest(Request.Method.POST, URLIncome, new Response.Listener<String>() {
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
                        CategoryList.add(categoryName);
                        CategoryAdapter = new ArrayAdapter<>(UpdateHistory.this, android.R.layout.simple_spinner_item, CategoryList);
                        CategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spn_categoryUpdateDataBelowCode.setAdapter(CategoryAdapter);

                        //Set the text color of the Spinner's selected view (not a drop down list view)
                        spn_categoryUpdateDataBelowCode.setSelection(0, true);
                        View v = spn_categoryUpdateDataBelowCode.getSelectedView();
                        ((TextView)v).setTextColor(Color.WHITE);

                        spn_categoryUpdateDataBelowCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                Toast.makeText(UpdateHistory.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        //request ke Volley
        requestQueue = Volley.newRequestQueue(UpdateHistory.this);
        requestQueue.add(stringRequest);

        stringRequest = new StringRequest(Request.Method.POST, URLOutcome, new Response.Listener<String>() {
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
                        CategoryList.add(categoryName);
                        CategoryAdapter = new ArrayAdapter<>(UpdateHistory.this, android.R.layout.simple_spinner_item, CategoryList);
                        CategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spn_categoryUpdateDataBelowCode.setAdapter(CategoryAdapter);

                        //Set the text color of the Spinner's selected view (not a drop down list view)
                        spn_categoryUpdateDataBelowCode.setSelection(0, true);
                        View v = spn_categoryUpdateDataBelowCode.getSelectedView();
                        ((TextView)v).setTextColor(Color.WHITE);

                        spn_categoryUpdateDataBelowCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                Toast.makeText(UpdateHistory.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        //request ke Volley
        requestQueue = Volley.newRequestQueue(UpdateHistory.this);
        requestQueue.add(stringRequest);

        btn_confirmUpdateDataCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = spn_categoryUpdateDataBelowCode.getSelectedItem().toString().trim();
                description = et_descUpdateDataBelowCode.getText().toString().trim();
                date = et_dateUpdateDataBelowCode.getText().toString().trim();
                amount = et_amountUpdateDataBelowCode.getText().toString().trim();

                if (tv_updateDataTitleCode.getText().equals("Gift") || tv_updateDataTitleCode.getText().equals("Salary")){
                    if (tv_idUpdateDataCode.getText().equals(tv_idUpdateDataCode.getText())){
                        if (category.equals("") || description.equals("") || date.equals("") || amount.equals("")){
                            Toast.makeText(UpdateHistory.this, "Isilah Semua Inputan Yang Tidak Lengkap", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(UpdateHistory.this, "Data Berhasil DiUpdate", Toast.LENGTH_LONG).show();

                            stringRequest = new StringRequest(Request.Method.POST, updateIncomeURL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(UpdateHistory.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            {
                                @Override
                                public Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> updateDataIncome = new HashMap<>();
                                    updateDataIncome.put("category_inc", category);
                                    updateDataIncome.put("description_inc", description);
                                    updateDataIncome.put("date_inc", date);
                                    updateDataIncome.put("amount_inc",amount);
                                    return updateDataIncome;
                                }
                            };
                            //request ke Volley
                            requestQueue = Volley.newRequestQueue(UpdateHistory.this);
                            requestQueue.add(stringRequest);

                            Intent moveToHistory = new Intent(UpdateHistory.this, History.class);
                            startActivity(moveToHistory);
                        }
                    }
                }else if (tv_updateDataTitleCode.equals("Entertainment") || tv_updateDataTitleCode.equals("Medicine") || tv_updateDataTitleCode.equals("Maintenance") || tv_updateDataTitleCode.equals("Transportation") || tv_updateDataTitleCode.equals("Hobby") || tv_updateDataTitleCode.equals("Food & Beverage")){
                    Toast.makeText(UpdateHistory.this, "Isilah Semua Inputan Yang Tidak Lengkap", Toast.LENGTH_LONG).show();
                    if (category.equals("") || description.equals("") || date.equals("") || amount.equals("")){
                        Toast.makeText(UpdateHistory.this, "Isilah Semua Inputan Yang Tidak Lengkap", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(UpdateHistory.this, "Data Berhasil DiUpdate", Toast.LENGTH_LONG).show();

                        stringRequest = new StringRequest(Request.Method.POST, updateOutcomeURL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(UpdateHistory.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        {
                            @Override
                            public Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> updateDataOutcome = new HashMap<>();
                                updateDataOutcome.put("category_otc", category);
                                updateDataOutcome.put("description_otc", description);
                                updateDataOutcome.put("date_otc", date);
                                updateDataOutcome.put("amount_otc",amount);
                                return updateDataOutcome;
                            }
                        };
                        //request ke Volley
                        requestQueue = Volley.newRequestQueue(UpdateHistory.this);
                        requestQueue.add(stringRequest);

                        Intent moveToHistory = new Intent(UpdateHistory.this, History.class);
                        startActivity(moveToHistory);
                    }
                }
            }
        });
    }
}