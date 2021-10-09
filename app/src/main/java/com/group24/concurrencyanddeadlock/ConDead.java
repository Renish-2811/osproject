package com.group24.concurrencyanddeadlock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConDead extends AppCompatActivity {
    Button banker, lock, ostrich, binarysemaphore, countingsemaphore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_dead);
        setTitle("CONCURRENCY AND DEADLOCK");
        banker=findViewById(R.id.banker);
        lock=findViewById(R.id.lock);
        ostrich=findViewById(R.id.ostrich);
        binarysemaphore=findViewById(R.id.binarysemaphore);
        countingsemaphore=findViewById(R.id.countingsemaphore);

        banker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=1;
                Intent intent = new Intent(ConDead.this, AlgosActivity.class);
                intent.putExtra("key1",value);
                startActivity(intent);
            }
        });

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=2;
                Intent intent = new Intent(ConDead.this, AlgosActivity.class);
                intent.putExtra("key1",value);
                startActivity(intent);
            }
        });

        ostrich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=3;
                Intent intent = new Intent(ConDead.this, AlgosActivity.class);
                intent.putExtra("key1",value);
                startActivity(intent);
            }
        });

        binarysemaphore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=4;
                Intent intent = new Intent(ConDead.this, AlgosActivity.class);
                intent.putExtra("key1",value);
                startActivity(intent);
            }
        });

        countingsemaphore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=5;
                Intent intent = new Intent(ConDead.this, AlgosActivity.class);
                intent.putExtra("key1",value);
                startActivity(intent);
            }
        });
    }
}