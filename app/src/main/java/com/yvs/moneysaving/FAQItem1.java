package com.yvs.moneysaving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FAQItem1 extends AppCompatActivity {

    ImageButton btn_faqItem1ToFaqCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqitem1);

        btn_faqItem1ToFaqCode = (ImageButton)findViewById(R.id.btn_faqItem1ToFaq);

        btn_faqItem1ToFaqCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toFaq = new Intent(FAQItem1.this, FAQ.class);
                startActivity(toFaq);
            }
        });
    }
}