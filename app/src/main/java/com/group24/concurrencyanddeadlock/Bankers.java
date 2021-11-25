package com.group24.concurrencyanddeadlock;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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


public class Bankers extends AppCompatActivity {
    Button add_Row, deleteRow, reset, btn_Compute;
    TableLayout tableInput;
    TableRow tableRow;
    TextView Process, txt;
    EditText  AT, MN, proc, resource, total;
    String TAG = "CHECK";
    int count = 0;
    int TABLE_ROW_ID = 0;
    int PROCESS_NO_ID = 20000;
    int ALLOCATION_ID = 30000;
    int MAXNEED_ID = 40000;
    int row_id;
    int co=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankers);
        ActionBar actionBar = getSupportActionBar();
        setTitle("IMPLEMENTATION OF ALGORITHM");
        init();

        txt=findViewById(R.id.textView6);
        proc=findViewById(R.id.proc);
        resource=findViewById(R.id.resource);
        total=findViewById(R.id.total);

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
                    Toast toast =  Toast.makeText(Bankers.this, "Min One Process Required", Toast.LENGTH_SHORT);
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
                resource.setText("");
                total.setText("");
                txt.setText("");
                if (tableInput.getChildCount() <= 2) {
                    Toast toast=  Toast.makeText(Bankers.this, "Atleast One Process Required", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    count = 1;
                }
                else {
                    while (tableInput.getChildCount() > 1) {
                        tableInput.removeView(tableInput.getChildAt(tableInput.getChildCount() - 1));

                    }
                    count = 0;
                    setAdd_Row();
                    Toast toast= Toast.makeText(Bankers.this, "Deleted All Process \n Except One", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
                count = 1;
                ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
                sv.scrollTo(0, sv.getTop());
            }
        });




      btn_Compute = findViewById(R.id.btn_Compute);
        btn_Compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tableInput.getChildCount() <= 1) {
                    Toast toast=Toast.makeText(Bankers.this, "No Process Available", Toast.LENGTH_SHORT);

                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                //Formation of array where the inputs will be sent to particular algorithm



                if (proc.getText().toString().equals("")) {

                    Toast toast = Toast.makeText(Bankers.this, "Input of Number of Processes Missing", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else if (resource.getText().toString().equals("")) {

                    Toast toast = Toast.makeText(Bankers.this, "Input of Number of Resources Missing", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity
                            .CENTER, 0, 0);
                    toast.show();
                }
                else {

                    int p = Integer.parseInt(proc.getText().toString());
                    int r = Integer.parseInt(resource.getText().toString());

                    int[] TR_Array = new int[r];
                    int[][] alloc = new int[p][r];
                    int[][] max = new int[p][r];


                    String[] Process_Array = new String[count];
                    int check_all_values_entered = 0;

                    String Temp_inp_Process;

                    for (int i = 0; i < count; i++) {
                        int idVal = i + 1;
                        check_all_values_entered = 0;


                        if (total.getText().toString().equals("")) {

                            Toast toast = Toast.makeText(Bankers.this, "Input of Total Available Resources Missing", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            break;
                        }

                        EditText tmpEdit1 = findViewById(ALLOCATION_ID + idVal);
                        if (tmpEdit1.getText().toString().equals("")) {

                            Toast toast = Toast.makeText(Bankers.this, "Input of Allocation Resources Missing in  Process " + idVal, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            break;
                        }


                        String[] val = new String[100];
                        String[] val2 = new String[100];
                        String[] val3 = new String[100];

                        String s = (tmpEdit1.getText().toString());
                        val = s.split(" ");
                        for (int j = 0; j < val.length; j++) {
                            alloc[i][j] = Integer.parseInt(val[j]);
                        }


                        EditText tmpEdit2 = findViewById(MAXNEED_ID + idVal);
                        if (tmpEdit2.getText().toString().equals("")) {
                            Toast toast = Toast.makeText(Bankers.this, "Input of Maximum Need Missing in  Process " + idVal, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            break;
                        }


                        String s2 = (tmpEdit2.getText().toString());
                        val2 = s2.split(" ");
                        for (int j = 0; j < val2.length; j++) {
                            max[i][j] = Integer.parseInt(val2[j]);
                        }


                        String tr = (total.getText().toString());
                        val3 = tr.split(" ");
                        for (int b = 0; b < r; b++) {
                            TR_Array[b] = Integer.parseInt(val3[b]);
                        }

                        check_all_values_entered = 1;


                        int cou = 0;
                        int safeSequence[] = new int[p];
                        int need[][] = new int[p][r];
                        for (int u = 0; u < p; u++) {
                            for (int j = 0; j < r; j++) {
                                need[u][j] = max[u][j] - alloc[u][j];
                            }
                        }


                        //visited array to find the already allocated process
                        boolean visited[] = new boolean[p];
                        for (int u = 0; u < p; u++) {
                            visited[u] = false;
                        }

                        //work array to store the copy of available resources
                        int work[] = new int[r];
                        for (int u = 0; u < r; u++) {
                            int sum = 0;
                            for (int j = 0; j < p; j++) {
                                sum += alloc[j][u];
                            }
                            work[u] = TR_Array[u] - sum;
                        }
                        while (cou < p) {
                            boolean flag = false;
                            for (int u = 0; u < p; u++) {
                                if (visited[u] == false) {
                                    int j;
                                    for (j = 0; j < r; j++) {
                                        if (need[u][j] > work[j])
                                            break;
                                    }
                                    if (j == r) {
                                        safeSequence[cou++] = u;
                                        visited[u] = true;
                                        flag = true;

                                        for (j = 0; j < r; j++) {
                                            work[j] = work[j] + alloc[u][j];
                                        }
                                    }
                                }
                            }
                            if (flag == false) {
                                break;
                            }
                        }
                        String str = "";
                        if (cou < p) {
                            Log.d(TAG, "The System is UnSafe!");
                            txt.setText("The System is UnSafe!");
                        } else {
                            //System.out.println("The given System is Safe");
                            //System.out.println("Following is the SAFE Sequence");
                            Log.d(TAG, "The System is Safe!");
                            str="Following is the Safe Sequence: \n";
                            for (int u = 0; u < p; u++) {
                                String temp = ("P" + (safeSequence[u] + 1));
                                str = str + temp;
                                if (u != p - 1)
                                    str = str + (" -> ");
                            }
                            txt.setText(str);
                        }
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
        tableRow = new TableRow(Bankers.this);
        Process = new TextView(Bankers.this);
        AT = new EditText(Bankers.this);
        MN = new EditText(Bankers.this);

        MN.setInputType(InputType.TYPE_CLASS_PHONE);
        AT.setInputType(InputType.TYPE_CLASS_PHONE);

        int idVal = count + 1;
        row_id = idVal;

        Process.setText("P" + idVal);
        Process.setGravity(Gravity.CENTER);
        Process.setTextSize(14);
        Process.setTextColor(Color.BLACK);
        Process.setTypeface(null, Typeface.BOLD);
        //set process background as it fit the window
        Process.setPadding(0,25,0,26);
        AT.setText("");
        AT.setGravity(Gravity.CENTER);
        AT.setTextSize(14);
        AT.setHint("Allocated Resources");
        MN.setHint("Maximum Need");
        MN.setText("");
        MN.setGravity(Gravity.CENTER);
        MN.setTextSize(14);

        MN.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength) });
        AT.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength) });


        Process.setId(PROCESS_NO_ID + idVal);
        //  Process.setBackground(ContextCompat.getDrawable(Bankers.this, R.drawable.table_process_cell_bg));
        AT.setId(ALLOCATION_ID + idVal);
        //  AT.setBackground(ContextCompat.getDrawable(Bankers.this, R.drawable.table_cell_bg));
        MN.setId(MAXNEED_ID + idVal);
        //  MN.setBackground(ContextCompat.getDrawable(Bankers.this, R.drawable.table_cell_bg));


        tableRow.setId(TABLE_ROW_ID + idVal);

        tableRow.addView(Process);
        tableRow.addView(AT);
        tableRow.addView(MN);
        tableRow.setPadding(0,15,0,15);
        tableInput.addView(tableRow);
        if(count%2==1)
            tableRow.setBackground(ContextCompat.getDrawable(Bankers.this, R.drawable.table_row_bg));

        else
            tableRow.setBackground(ContextCompat.getDrawable(Bankers.this, R.drawable.table_row_background_white));

        Log.d(TAG, "onClick: " + Process.getId());
        Log.d(TAG, "onClick: " + AT.getId());
        Log.d(TAG, "onClick: " + MN.getId());
        Log.d(TAG, "onClick: " + tableRow.getId());
        count++;
        ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
        sv.scrollTo(0, sv.getBottom());
    }
}