package com.yvs.moneysaving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FAQ extends AppCompatActivity {

    ImageButton btn_faqToMyWalletCode, btn_faqToFaqItem1Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        btn_faqToMyWalletCode = (ImageButton)findViewById(R.id.btn_faqToMyWallet);
        btn_faqToFaqItem1Code = (ImageButton)findViewById(R.id.btn_faqToFaqItem1);

        btn_faqToMyWalletCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movetoMyWallet = new Intent(FAQ.this, MyWallet.class);
                startActivity(movetoMyWallet);
            }
        });

        btn_faqToFaqItem1Code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToFaqItem1 = new Intent(FAQ.this, FAQItem1.class);
                startActivity(moveToFaqItem1);
            }
        });
    }
}