package com.yvs.moneysaving;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<HistoryData> historyDataList;
    Context context;

    public HistoryAdapter(List<HistoryData> historyDataList, Context context) {
        this.historyDataList = historyDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_history, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        HistoryData historyData = historyDataList.get(position);
        holder.tv_categoryHistoryCode.setText(historyData.getTransactionCategoryName());
        holder.tv_descHistoryCode.setText(historyData.getTransactionDescription());
        holder.tv_dateHistoryCode.setText(historyData.getTransactionDate());
        holder.tv_amountHistoryCode.setText(historyData.getTransactionAmount());
        holder.tv_idHistoryCode.setText(historyData.getTransactionId());
        holder.id = historyData.getTransactionId();
        holder.category = historyData.getTransactionCategoryName();
        holder.description = historyData.getTransactionDescription();
        holder.date = historyData.getTransactionDate();
        holder.amount = historyData.getTransactionAmount();
        //holder.iv_icCategoryHistoryCode.setImageDrawable(context.getResources().getDrawable(historyData.getIcTransactionCategory(), null));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToHistory = new Intent(context, DetailHistory.class);
                moveToHistory.putExtra("category_inc",historyData.getTransactionCategoryName());
                moveToHistory.putExtra("description_inc", historyData.getTransactionDescription());
                moveToHistory.putExtra("date_inc",historyData.getTransactionDate());
                moveToHistory.putExtra("amount_inc",historyData.getTransactionAmount());
                moveToHistory.putExtra("category_otc",historyData.getTransactionCategoryName());
                moveToHistory.putExtra("description_otc", historyData.getTransactionDescription());
                moveToHistory.putExtra("date_otc",historyData.getTransactionDate());
                moveToHistory.putExtra("amount_otc",historyData.getTransactionAmount());
                moveToHistory.putExtra("inc_id",historyData.getTransactionId());
                moveToHistory.putExtra("otc_id",historyData.getTransactionId());
                context.startActivity(moveToHistory);
            }
        });



    }

    @Override
    public int getItemCount() {
        return historyDataList.size();
    }


    public class  ViewHolder extends RecyclerView.ViewHolder{
        public String id, category, description, date, amount;
        ImageView iv_icCategoryHistoryCode;
        TextView tv_idHistoryCode, tv_categoryHistoryCode, tv_descHistoryCode, tv_amountHistoryCode, tv_dateHistoryCode;

        private String deleteIncomeURL = "http://192.168.1.4/PMobile/MoneySaving/deleteIncome.php";
        private String updateIncomeURL = "http://192.168.1.4/PMobile/MoneySaving/updateIncome.php";
        private String deleteOutcomeURL = "http://192.168.1.4/PMobile/MoneySaving/deleteOutcome.php";
        private String updateOutcomeURL = "http://192.168.1.4/PMobile/MoneySaving/updateOutcome.php";

        private StringRequest stringRequest;
        private RequestQueue requestQueue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icCategoryHistoryCode = itemView.findViewById(R.id.iv_icCategoryHistory);
            tv_categoryHistoryCode = itemView.findViewById(R.id.tv_categoryHistory);
            tv_descHistoryCode = itemView.findViewById(R.id.tv_descHistory);
            tv_dateHistoryCode = itemView.findViewById(R.id.tv_dateHistory);
            tv_amountHistoryCode = itemView.findViewById(R.id.tv_amountHistory);
            tv_idHistoryCode = itemView.findViewById(R.id.tv_idHistory);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    dialogBuilder.setMessage("Choose Operation You Want To Do");
                    dialogBuilder.setCancelable(true);

                    dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
                            dialogDelete.setMessage("Are You Sure To Delete This Item");
                            dialogDelete.setCancelable(true);
                            dialogDelete.setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            dialogDelete.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (category.equals("Gift") || category.equals("Salary")){
                                        if (id.equals(id)){
                                            stringRequest = new StringRequest(Request.Method.POST, deleteIncomeURL, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Toast.makeText(context.getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();

                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            {
                                                @Override
                                                public Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> dataIncome = new HashMap<>();
                                                    dataIncome.put("inc_id", id);
                                                    return dataIncome;
                                                }
                                            };
                                            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                                            requestQueue.add(stringRequest);
                                        }
                                    }else if(category.equals("Entertainment") || category.equals("Medicine") || category.equals("Maintenance") || category.equals("Transportation") || category.equals("Hobby") || category.equals("Food & Beverage")){
                                        if (id.equals(id)){
                                            stringRequest = new StringRequest(Request.Method.POST, deleteOutcomeURL, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Toast.makeText(context.getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                                                }
                                            })
                                            {
                                                @Override
                                                public Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> dataOutcome = new HashMap<>();
                                                    dataOutcome.put("otc_id", id);
                                                    return dataOutcome;
                                                }
                                            };
                                            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                                            requestQueue.add(stringRequest);
                                        }
                                    }
                                }
                            });

                            dialogDelete.show();
                        }
                    });

                    dialogBuilder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            View editLayout = LayoutInflater.from(context).inflate(R.layout.activity_update_history, null);
                            TextView tv_detailTitleUpdate = editLayout.findViewById(R.id.tv_updateDataTitle);
                            TextView tv_descUpdateData = editLayout.findViewById(R.id.tv_descUpdateData);
                            TextView tv_amountUpdateData = editLayout.findViewById(R.id.tv_amountUpdateData);
                            Spinner spn_categoryUpdateHistory = editLayout.findViewById(R.id.spn_categoryUpdateDataBelow);
                            EditText et_descUpdateHistory = editLayout.findViewById(R.id.et_descUpdateDataBelow);
                            EditText et_dateUpdateHistory = editLayout.findViewById(R.id.et_dateUpdateDataBelow);
                            EditText et_amountUpdateHistory = editLayout.findViewById(R.id.et_amountUpdateDataBelow);
                            //spn_categoryUpdateHistory.setText(category.toString());
                            tv_detailTitleUpdate.setText(category);
                            tv_descUpdateData.setText(description);
                            tv_amountUpdateData.setText(amount);
                            et_descUpdateHistory.setText(description);
                            et_dateUpdateHistory.setText(date);
                            et_amountUpdateHistory.setText(amount);
                            AlertDialog.Builder dialogUpdate = new AlertDialog.Builder(context);
                            //dialogUpdate.setTitle("Update " + category);
                            dialogUpdate.setView(editLayout);
                            dialogUpdate.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    final String category = tv_detailTitleUpdate.getText().toString().trim();
                                    final String description = et_descUpdateHistory.getText().toString().trim();
                                    final String date = et_dateUpdateHistory.getText().toString().trim();
                                    final String amount = et_amountUpdateHistory.getText().toString().trim();

                                    if (category.equals("Gift") || category.equals("Salary")){
                                        if (id.equals(id)){
                                            if (category.equals("") || description.equals("") || date.equals("") || amount.equals("")){
                                                Toast.makeText(context.getApplicationContext(), "Isilah Semua Inputan Yang Tidak Lengkap", Toast.LENGTH_LONG).show();
                                            }else {
                                                Toast.makeText(context.getApplicationContext(), "Data Berhasil DiUpdate", Toast.LENGTH_LONG).show();
                                                stringRequest = new StringRequest(Request.Method.POST, updateIncomeURL, new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(context.getApplicationContext(), "Data Berhasil DiUpdate", Toast.LENGTH_SHORT).show();

                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                {
                                                    @Override
                                                    public Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> dataIncome = new HashMap<>();
                                                        dataIncome.put("inc_id", id);
                                                        dataIncome.put("category_inc", category);
                                                        dataIncome.put("description_inc", description);
                                                        dataIncome.put("date_inc", date);
                                                        dataIncome.put("amount_inc",amount);
                                                        return dataIncome;
                                                    }
                                                };
                                                requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                                                requestQueue.add(stringRequest);
                                            }
                                        }
                                    }else if(category.equals("Entertainment") || category.equals("Medicine") || category.equals("Maintenance") || category.equals("Transportation") || category.equals("Hobby") || category.equals("Food & Beverage")){
                                        if (id.equals(id)){
                                            if (category.equals("") || description.equals("") || date.equals("") || amount.equals("")){
                                                Toast.makeText(context.getApplicationContext(), "Isilah Semua Inputan Yang Tidak Lengkap", Toast.LENGTH_LONG).show();
                                            }else {
                                                Toast.makeText(context.getApplicationContext(), "Data Berhasil DiUpdate", Toast.LENGTH_LONG).show();
                                                stringRequest = new StringRequest(Request.Method.POST, updateOutcomeURL, new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(context.getApplicationContext(), "Data Berhasil DiUpdate", Toast.LENGTH_SHORT).show();
                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                                                    }
                                                })
                                                {
                                                    @Override
                                                    public Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> dataOutcome = new HashMap<>();
                                                        dataOutcome.put("otc_id", id);
                                                        dataOutcome.put("category_otc", category);
                                                        dataOutcome.put("description_otc", description);
                                                        dataOutcome.put("date_otc", date);
                                                        dataOutcome.put("amount_otc",amount);
                                                        return dataOutcome;
                                                    }
                                                };
                                                requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                                                requestQueue.add(stringRequest);
                                            }
                                        }
                                    }
                                }
                            });

                            dialogUpdate.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                            AlertDialog dialog = dialogUpdate.create();
                            dialog.show();
//                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    if (category.equals("") || description.equals("") || date.equals("") || amount.equals("")){
//                                        Toast.makeText(context.getApplicationContext(), "Isilah Semua Inputan Yang Tidak Lengkap", Toast.LENGTH_LONG).show();
//                                    }else {
//                                        Toast.makeText(context.getApplicationContext(), "Data Berhasil DiUpdate", Toast.LENGTH_LONG).show();
//                                        dialog.dismiss();
//                                    }
//                                }
//                            });
                        }

                    });

                    dialogBuilder.show();

                    return false;
                }

            });

        }

    }
}
