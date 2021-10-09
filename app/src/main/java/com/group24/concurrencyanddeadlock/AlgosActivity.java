package com.group24.concurrencyanddeadlock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class AlgosActivity extends AppCompatActivity {
    TextView tv;
    ImageView iv;
    Button b;
    ScrollView sview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algos);
        int a= getIntent().getIntExtra("key1",0);
        tv=findViewById(R.id.textView10);
        iv=findViewById(R.id.imageView);
        b=findViewById(R.id.button);
        sview=findViewById(R.id.scroll);

        if(a==1){
            setTitle("BANKER'S ALGORITHM");
            String temp=getResources().getString(R.string.banker_theory);
            tv.setText(temp);
            iv.setImageResource(R.drawable.banker);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AlgosActivity.this, Bankers.class);
                    startActivity(intent);
                }
            });
        }
        if(a == 2){
            setTitle("LOCK VARIABLE");
            String temp=getResources().getString(R.string.lock_theory);
            tv.setText(temp);
            iv.setImageResource(R.drawable.lock);
        }
        if(a == 3){
            setTitle("OSTRICH METHOD");
            String temp=getResources().getString(R.string.ostrich_theory);
            tv.setText(temp);
            iv.setImageResource(R.drawable.ostrich);
        }
        if(a == 4){
            setTitle("BINARY SEMAPHORE");
            String temp=getResources().getString(R.string.binary_theory);
            tv.setText(temp);
            iv.setImageResource(R.drawable.binary);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AlgosActivity.this, Binary.class);
                    startActivity(intent);
                }
            });
        }
        if(a == 5){
            setTitle("COUNTING SEMAPHORE");
            String temp=getResources().getString(R.string.counting_theory);
            tv.setText(temp);
            iv.setImageResource(R.drawable.counting);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AlgosActivity.this, Counting.class);
                    startActivity(intent);
                }
            });
        }
    }
}