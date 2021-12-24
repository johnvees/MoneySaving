package com.yvs.moneysaving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class DetailHistory extends AppCompatActivity {

    ImageButton btn_detailHistoryToHistoryCode;
    TextView tv_detailHistoryTitleCode, tv_descHistoryDetailCode, tv_amountHistoryDetailCode;
    TextView tv_categoryDetailsBelowCode, tv_descDetailBelowCode, tv_dateDetailBelowCode, tv_amountDetailBelowCode;
    Button btn_updateDataCode, btn_deleteDataCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        btn_detailHistoryToHistoryCode = (ImageButton) findViewById(R.id.btn_detailHistoryToHistory);
        tv_detailHistoryTitleCode = (TextView) findViewById(R.id.tv_detailHistoryTitle);
        tv_descHistoryDetailCode = (TextView) findViewById(R.id.tv_descHistoryDetail);
        tv_amountHistoryDetailCode = (TextView) findViewById(R.id.tv_amountHistoryDetail);
        tv_categoryDetailsBelowCode = (TextView) findViewById(R.id.tv_categoryDetailsBelow);
        tv_descDetailBelowCode = (TextView) findViewById(R.id.tv_descDetailBelow);
        tv_dateDetailBelowCode = (TextView) findViewById(R.id.tv_dateDetailBelow);
        tv_amountDetailBelowCode = (TextView) findViewById(R.id.tv_amountDetailBelow);
        //btn_updateDataCode = (Button) findViewById(R.id.btn_updateData);
        //btn_deleteDataCode = (Button) findViewById(R.id.btn_deleteData);

        tv_detailHistoryTitleCode.setText(getIntent().getStringExtra("category_inc"));
        tv_descHistoryDetailCode.setText(getIntent().getStringExtra("description_inc"));
        tv_amountHistoryDetailCode.setText(getIntent().getStringExtra("amount_inc"));
        tv_categoryDetailsBelowCode.setText(getIntent().getStringExtra("category_inc"));
        tv_descDetailBelowCode.setText(getIntent().getStringExtra("description_inc"));
        tv_dateDetailBelowCode.setText(getIntent().getStringExtra("date_inc"));
        tv_amountDetailBelowCode.setText(getIntent().getStringExtra("amount_inc"));

        btn_detailHistoryToHistoryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToHistory = new Intent(DetailHistory.this, History.class);
                startActivity(moveToHistory);
            }
        });

    }
}