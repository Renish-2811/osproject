package com.group24.concurrencyanddeadlock;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCpuScheduling, btnPageReplacement, button_con_dead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("\tDASHBOARD");
        init();
        btnPageReplacement.setOnClickListener(this);
        btnCpuScheduling.setOnClickListener(this);
        button_con_dead.setOnClickListener(this);
    }


    private void init() {
        btnCpuScheduling = findViewById(R.id.button_cpu_scheduling);
        btnPageReplacement = findViewById(R.id.button_page_replacement);
        button_con_dead= findViewById(R.id.button_con_dead);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_cpu_scheduling) {
            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.button_page_replacement) {
            Intent intent = new Intent(DashboardActivity.this, PageReplacementActivity.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.button_con_dead) {
            Intent intent = new Intent(DashboardActivity.this, ConDead.class);
            startActivity(intent);
        }
    }
}