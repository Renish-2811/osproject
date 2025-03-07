package com.group24.concurrencyanddeadlock;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Counting extends AppCompatActivity {
    Button add_Row, deleteRow, reset, btn_Compute;
    TableLayout tableInput;
    TableRow tableRow;
    TextView Process, E, B, F, csv;
    EditText proc, max;
    String TAG = "CHECK";
    int count = 0;
    int TABLE_ROW_ID = 0;
    int PROCESS_NO_ID = 20000;
    int ENTRY_ID = 30000;
    int BUFFER_ID = 40000;
    int FINISH_ID = 50000;
    int row_id;
    int ct=0;
    int m=0;
    int lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);
        ActionBar actionBar = getSupportActionBar();
        setTitle("IMPLEMENTATION OF ALGORITHM");
        init();

        proc=findViewById(R.id.proc);
        max=findViewById(R.id.max);
        boolean b[]=new boolean[100];
        boolean dc[]=new boolean[100];
        csv=findViewById(R.id.csv);

        tableInput.setColumnStretchable(0, true);
        tableInput.setColumnStretchable(1, true);
        tableInput.setColumnStretchable(2, true);
        tableInput.setStretchAllColumns(true);
        setAdd_Row();
        add_Row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAdd_Row();

            }
        });

        deleteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableInput.getChildCount() > 2) {
                    tableInput.removeViewAt(row_id);
                    Log.d(TAG, "onClick: " + row_id);
                    row_id--;
                    count--;

                } else {
                    Toast toast =  Toast.makeText(Counting.this, "Min One Process Required", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    count = 1;
                }

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proc.setText("");
                max.setText("");
                csv.setText("Counting Semaphore Value: ");
                ct=0;
                m=0;
                for(int j=0;j<20;j++)
                {
                    b[j]=false;
                }

                for(int j=0;j<20;j++)
                {
                    dc[j]=false;
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_Compute.invalidate();
                    }
                }, 500);


                if (tableInput.getChildCount() <= 2) {
                    Toast toast=  Toast.makeText(Counting.this, "Atleast One Process Required", Toast.LENGTH_SHORT);
                    toast.show();
                    count = 1;
                }
                else {
                    while (tableInput.getChildCount() > 1) {
                        tableInput.removeView(tableInput.getChildAt(tableInput.getChildCount() - 1));

                    }
                    count = 0;
                    setAdd_Row();
                    Toast toast= Toast.makeText(Counting.this, "Deleted All Process \n Except One", Toast.LENGTH_SHORT);
                    toast.show();
                }
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
                count = 1;
                ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
                sv.scrollTo(0, sv.getTop());
            }
        });


        for(int j=0;j<100;j++)
        {
            b[j]=false;
        }

        for(int j=0;j<100;j++)
        {
            dc[j]=false;
        }


        btn_Compute = findViewById(R.id.btn_Compute);

        btn_Compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (tableInput.getChildCount() <= 1) {
                    Toast toast=Toast.makeText(Counting.this, "No Process Available", Toast.LENGTH_SHORT);

                    toast.show();
                }

                //Formation of array where the inputs will be sent to particular algorithm



                if (proc.getText().toString().equals("")) {

                    Toast toast = Toast.makeText(Counting.this, "Input of Number of Processes Missing", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (max.getText().toString().equals("")) {

                    Toast toast = Toast.makeText(Counting.this, "Input of Semaphore Value Missing", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {


                    int p = Integer.parseInt(proc.getText().toString());

                    int y = Integer.parseInt(max.getText().toString());

                    lock=y-m;


                    // int arr[]=new int[p];
                    List<Integer> arr=new ArrayList<>();
                    for(int j=0;j<p;j++)
                    {
                        arr.add(j);
                    }


                    String[] Process_Array = new String[count];
                    int check_all_values_entered = 0;

                    String Temp_inp_Process;

                    TextView[] tmp1=new TextView[p];
                    TextView[] tmp2=new TextView[p];
                    TextView[] tmp3=new TextView[p];

                    for (int i = 0; i < count; i++) {
                        int idVal = i + 1;
                        check_all_values_entered = 0;

                        for(int j=1;j<=p;j++)
                        {
                            tmp1[j-1] = findViewById((ENTRY_ID+j));
                            tmp2[j-1] = findViewById((BUFFER_ID+j));
                            tmp3[j-1] = findViewById((FINISH_ID+j));
                        }
                        check_all_values_entered = 1;
                    }


                    String str="🔵";
                    Random r=new Random();
                    int a = r.nextInt(arr.size());

                    if(b[a]==false)
                    {
                        if (lock>0 && dc[a]==false) //buffer
                        {
                            tmp2[a].setText(str);
                            Log.d(TAG, "Buffer " + a);

                            tmp1[a].setText("");

                            csv.setText("Counting Semaphore Value: " +(lock-1));

                            if(dc[a]==false)
                            {
                                m++;
                            }
                            dc[a]=true;
                        }
                        else
                        {
                            if (tmp2[a].getText().toString() == str)       //finish
                            {

                                m--;
                                tmp3[a].setText(str);
                                Log.d(TAG, "Finish " + a);

                                tmp2[a].setText("");
                                Log.d(TAG, "Entry " + a);

                                csv.setText("Counting Semaphore Value: " +(lock+1));

                                ct++;
                                b[a]=true;
                            }
                            else {           //entry
                                tmp1[a].setText(str);
                                Log.d(TAG, "Entry " + a);
                            }
                        }

                    }
                    if(ct<p) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn_Compute.performClick();
                            }
                        }, 300);
                    }
                    else {
                        Toast toast = Toast.makeText(Counting.this, "Simulation Completed", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    public void init(){
        add_Row = findViewById(R.id.btn_addRow);
        deleteRow = findViewById(R.id.btn_deleteRow);
        reset = findViewById(R.id.btn_reset);
        tableInput = findViewById(R.id.tableInput);
    }

    public void setAdd_Row(){

        int maxLength =20;
        tableRow = new TableRow(Counting.this);
        Process = new TextView(Counting.this);
        E = new TextView(Counting.this);
        B = new TextView(Counting.this);
        F = new TextView(Counting.this);


        int idVal = count + 1;
        row_id = idVal;

        Process.setText("P" + idVal);
        Process.setGravity(Gravity.CENTER);
        Process.setTextSize(14);
        Process.setTextColor(Color.BLACK);
        Process.setTypeface(null, Typeface.BOLD);
        //set process background as it fit the window
        Process.setPadding(0,25,0,26);
        E.setText("");
        E.setGravity(Gravity.CENTER);
        E.setTextSize(14);
        B.setText("");
        B.setGravity(Gravity.CENTER);
        B.setTextSize(14);
        F.setText("");
        F.setGravity(Gravity.CENTER);
        F.setTextSize(14);




        Process.setId(PROCESS_NO_ID + idVal);
        //  Process.setBackground(ContextCompat.getDrawable(Counting.this, R.drawable.table_process_cell_bg));
        E.setId(ENTRY_ID + idVal);
        //  AT.setBackground(ContextCompat.getDrawable(Counting.this, R.drawable.table_cell_bg));
        B.setId(BUFFER_ID + idVal);
        //  MN.setBackground(ContextCompat.getDrawable(Counting.this, R.drawable.table_cell_bg));
        F.setId(FINISH_ID + idVal);
        //  MN.setBackground(ContextCompat.getDrawable(Counting.this, R.drawable.table_cell_bg));


        tableRow.setId(TABLE_ROW_ID + idVal);

        tableRow.addView(Process);
        tableRow.addView(E);
        tableRow.addView(B);
        tableRow.addView(F);
        tableRow.setPadding(0,15,0,15);
        tableInput.addView(tableRow);
        if(count%2==1)
            tableRow.setBackground(ContextCompat.getDrawable(Counting.this, R.drawable.table_row_bg));

        else
            tableRow.setBackground(ContextCompat.getDrawable(Counting.this, R.drawable.table_row_background_white));

        Log.d(TAG, "onClick: " + Process.getId());
        Log.d(TAG, "onClick: " + E.getId());
        Log.d(TAG, "onClick: " + B.getId());
        Log.d(TAG, "onClick: " + F.getId());
        Log.d(TAG, "onClick: " + tableRow.getId());
        count++;
        ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
        sv.scrollTo(0, sv.getBottom());
    }
}