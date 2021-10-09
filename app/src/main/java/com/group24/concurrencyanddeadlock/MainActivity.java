package com.group24.concurrencyanddeadlock;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button add_Row, deleteRow, reset, btn_Compute;
    Spinner spinner;
    RadioGroup radioGroup;
    TableLayout tableCPU;
    TableRow Row_CPU,Row_CPU_time;
    EditText etTimeQuantum;
    TableLayout tableInput, tableOutput;
    TableRow tableRow, Ans_tableRow;
    TextView Process,serialNo,insert_priority;
    TextView radioNonpreemtive,radioPreemtive;
    TextView AnsTable_Process, AnsTable_AT, AnsTable_BT, AnsTable_TAT, AnsTable_WT, AnsTable_CT ;
    TextView avgWT, avgTAT;
    EditText  AT, BT, Priority;
    String TAG = "CHECK";
    TextView PriorityColumn;
    TextView tv_output,tv_gantChart;
    int count = 0;
    int TABLE_ROW_ID = 0;
    int SERIAL_NO_ID = 10000;
    int PROCESS_NO_ID = 20000;
    int ARRIVAL_TIME_ID = 30000;
    int BURST_TIME_ID = 40000;
    int PRIORITY_ID = 50000;
    int row_id;
    int SPINNER_POSITION = 0;
    int ANS_TABLE_ROW_ID = 0;
    int AnsTable_Process_ID = 1000;
    int AnsTable_AT_ID = 2000;
    int AnsTable_BT_ID = 3000;
    int AnsTable_TAT_ID = 4000;
    int AnsTable_WT_ID = 5000;
    int AnsTable_CT_ID = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("\tCPU SCHEDULING");
        actionBar.setDisplayHomeAsUpEnabled(true);
        init();
        List<String> algorithms = new ArrayList<>();
        algorithms.add(0, "FCFS");
        algorithms.add("SJF");
        algorithms.add("SRTF");
        algorithms.add("PRIORITY");
        algorithms.add("ROUND ROBIN");
        algorithms.add("LJF");
        algorithms.add("LRTF ");

        //to check radio button
        int checked_id = radioGroup.getCheckedRadioButtonId();
        if (checked_id == R.id.Nonpreemtive) {
            radioNonpreemtive.setTextColor(Color.rgb(9,135,185));
        } else if (checked_id == R.id.preemtive)
            radioNonpreemtive.setTextColor(Color.rgb(9,135,185));


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.style_spinner, algorithms);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("FCFS")) {
                    radioGroup.setVisibility(View.GONE);
                    etTimeQuantum.setVisibility(View.GONE);
                    insert_priority.setVisibility(View.GONE);

                } else {
                    String toast = adapterView.getItemAtPosition(i).toString();
                    Toast toast1 =Toast.makeText(MainActivity.this, toast + " Selected", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();

                    if (adapterView.getItemAtPosition(i).equals("PRIORITY")) {
                        radioGroup.setVisibility(View.VISIBLE);
                        etTimeQuantum.setVisibility(View.GONE);
                        PriorityColumn.setVisibility(View.VISIBLE);
                        insert_priority.setVisibility(View.VISIBLE);
                    }
                    else  if (adapterView.getItemAtPosition(i).equals("LJF")||adapterView.getItemAtPosition(i).equals("LRTF")) {
                        radioGroup.setVisibility(View.GONE);
                        etTimeQuantum.setVisibility(View.GONE);
                        insert_priority.setVisibility(View.GONE);

                    } else if (adapterView.getItemAtPosition(i).equals("ROUND ROBIN")) {
                        radioGroup.setVisibility(View.GONE);
                        etTimeQuantum.setVisibility(View.VISIBLE);
                        PriorityColumn.setVisibility(View.INVISIBLE);
                        insert_priority.setVisibility(View.GONE);

                    } else {
                        radioGroup.setVisibility(View.GONE);
                        etTimeQuantum.setVisibility(View.GONE);
                        insert_priority.setVisibility(View.GONE);
                    }
                }
                //Priority Column Hide/Show
                SPINNER_POSITION = i + 1;
                int n=count+1;
                if(SPINNER_POSITION==4){
                    PriorityColumn.setVisibility(View.VISIBLE);

                    for ( i=1 ;i<n;i++){
                        Log.d(TAG, "ID of Priority column "+(i+PRIORITY_ID));
                        EditText temp =  (EditText) findViewById(i+PRIORITY_ID);
                        temp.setVisibility(View.VISIBLE);
                        PriorityColumn.setVisibility(View.VISIBLE);
                    }
                }
                else
                { PriorityColumn.setVisibility(View.GONE);
                    for ( i=1 ;i<n;i++){
                        EditText temp =  findViewById(i+PRIORITY_ID);
                        temp.setVisibility(View.GONE);
                        Priority.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        tableInput.setColumnStretchable(0, true);
        tableInput.setColumnStretchable(1, true);
        tableInput.setColumnStretchable(2, true);
        tableInput.setColumnStretchable(3, true);
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
                    Toast toast =  Toast.makeText(MainActivity.this, "Min One Process Required", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    count = 1;
                }

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableOutput.setVisibility(View.INVISIBLE);
                tableCPU.setVisibility(View.INVISIBLE);
                avgTAT.setVisibility(View.INVISIBLE);
                avgWT.setVisibility(View.INVISIBLE);
                tv_output.setVisibility(View.INVISIBLE);
                tv_gantChart.setVisibility(View.INVISIBLE);
                if (tableInput.getChildCount() <= 2) {
                    Toast toast=  Toast.makeText(MainActivity.this, "Atleast One Process Required", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    count = 1;
                } else {
                    while (tableInput.getChildCount() > 1) {
                        tableInput.removeView(tableInput.getChildAt(tableInput.getChildCount() - 1));

                    }
                    count = 0;
                    setAdd_Row();
                    Toast toast= Toast.makeText(MainActivity.this, "Deleted All Process \n Except One", Toast.LENGTH_SHORT);
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
                    Toast toast=Toast.makeText(MainActivity.this, "No Process Available", Toast.LENGTH_SHORT);

                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                //Formation of array where the inputs will be sent to particular algorithm
                int[] AT_Array = new int[count];
                int[]  BT_Array= new int[count];
                String[] Process_Array = new String[count];
                int[] Priority_Array = new int[count];
                int[] Serial_No = new int[count];
                int check_all_values_entered = 0;

                String Temp_inp_Process;

                //For AT
                for (int i = 0; i < count; i++) {
                    int idVal = i + 1;
                    check_all_values_entered = 0;

                    EditText tmpEdit1 = findViewById(ARRIVAL_TIME_ID + idVal);
                    if (tmpEdit1.getText().toString().equals("")) {

                        Toast toast=  Toast.makeText(MainActivity.this, "Input of Arrival Time Missing in  Process " + idVal, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                    }
                    int Temp_Inp_At = Integer.parseInt(tmpEdit1.getText().toString());
                    Log.d(TAG, "At" + Temp_Inp_At);
                    AT_Array[i] = Temp_Inp_At;

                    EditText tmpEdit2 = findViewById(BURST_TIME_ID + idVal);
                    if (tmpEdit2.getText().toString().equals("")) {
                        Toast toast= Toast.makeText(MainActivity.this, "Input of Burst Time Missing in  Process " + idVal, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                    }
                    int Temp_Inp_Bt = Integer.parseInt(tmpEdit2.getText().toString());
                    Log.d(TAG, "Bt" + Temp_Inp_Bt);
                    BT_Array[i] = Temp_Inp_Bt;

                    EditText tmpEdit3 = findViewById(PRIORITY_ID + idVal);
                    if (tmpEdit3.getText().toString().equals("")) {
                        Toast toast= Toast.makeText(MainActivity.this, "Input of  Priority Missing in  Process " + idVal, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                    }
                    int Temp_Inp_Pt = Integer.parseInt(tmpEdit3.getText().toString());
                    Log.d(TAG, "Pt" + Temp_Inp_Pt);
                    Priority_Array[i] = Temp_Inp_Pt;

                    TextView tmpEdit4 = findViewById(PROCESS_NO_ID + idVal);
                    Temp_inp_Process = tmpEdit4.getText().toString();
                    if (tmpEdit4.getText().toString().equals("")) {
                        Toast toast= Toast.makeText(MainActivity.this, "Input of Priority Missing in Process " + idVal, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                    }
                    TextView tmptxt = findViewById(SERIAL_NO_ID + idVal);
                    int Temp_inp_Serial = Integer.parseInt(tmptxt.getText().toString());
                    Serial_No[i] = Temp_inp_Serial;

                    Process_Array[i] = Temp_inp_Process;
                    Log.e(TAG, "Process_Array = " + (PROCESS_NO_ID + idVal) + " = " + Process_Array[i]);

                    check_all_values_entered = 1;
                    //To Scroll down when compute is done
                    ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
                    sv.scrollTo(0, R.id.tableCPU);
                    // sv.scrollTo(0, sv.getBottom());
                }
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
                if (check_all_values_entered == 1) {
                    switch (SPINNER_POSITION) {
                        case 1: {
                            FCFS_Function(AT_Array, BT_Array, Process_Array, Serial_No);
                            break;
                        }
                        case 2: {
                            SJF_Function_Non_Preemptive(AT_Array, BT_Array, Process_Array, Serial_No);
                            break;
                        }
                        case 3: {
                            SRTF_Function_Preemptive(AT_Array, BT_Array, Process_Array, Serial_No);
                            break;
                        }
                        case 4: {
                            int temp = check_radio_button();
                            if (temp == 1)
                                PROIRITY_Function_Preemptive(AT_Array, BT_Array, Process_Array, Priority_Array, Serial_No);
                            if (temp == 2)
                                PROIRITY_Function_Non_Preemptive(AT_Array, BT_Array, Process_Array, Priority_Array, Serial_No);
                            break;
                        }
                        case 5: {
                            EditText TimeQuantum = findViewById(R.id.etTimeQuantum);
                            AT.setGravity(Gravity.CENTER);
                            if (TimeQuantum.getText().toString().equals("")) {
                                Toast.makeText(MainActivity.this, "Enter Time Quantum", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            int Temp_Inp_Pt = Integer.parseInt(TimeQuantum.getText().toString());
                            RoundRobin_Function(AT_Array, BT_Array, Process_Array, Temp_Inp_Pt, Serial_No);
                            break;
                        }
                        case 6: {
                            LJF_Function_Non_Preemptive(AT_Array, BT_Array, Process_Array, Serial_No);
                            Log.d(TAG, "onClick: LJF_Function_Non_Preemptive Selected");
                            break;
                        }
                        case 7: {
                            LRTF_Function_Preemptive(AT_Array, BT_Array, Process_Array, Serial_No);
                            break;
                        }
                    }
                }
            }

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    public void init(){
        spinner = findViewById(R.id.spinner);
        radioGroup = findViewById(R.id.radioGroup);
        etTimeQuantum = findViewById(R.id.etTimeQuantum);
        PriorityColumn = findViewById(R.id.PriorityColumn);
        tableOutput = findViewById(R.id.tableOutput);
        tableCPU = findViewById(R.id.tableCPU);
        avgTAT = findViewById(R.id.avgTAT);
        avgWT = findViewById(R.id.avgWT);
        tv_output = findViewById(R.id.tv_output_table);
        tv_gantChart = findViewById(R.id.tv_gantChart);
        insert_priority = findViewById(R.id.insert_priority);
        radioNonpreemtive = findViewById(R.id.Nonpreemtive);
        radioPreemtive = findViewById(R.id.preemtive);
        add_Row = findViewById(R.id.btn_addRow);
        deleteRow = findViewById(R.id.btn_deleteRow);
        reset = findViewById(R.id.btn_reset);
        tableInput = findViewById(R.id.tableInput);
    }

    public void setAdd_Row(){
        int maxLength =4;
        tableRow = new TableRow(MainActivity.this);
        serialNo = new TextView(MainActivity.this);
        Process = new TextView(MainActivity.this);
        AT = new EditText(MainActivity.this);
        BT = new EditText(MainActivity.this);
        Priority = new EditText(MainActivity.this);


        AT.setInputType(InputType.TYPE_CLASS_NUMBER);
        BT.setInputType(InputType.TYPE_CLASS_NUMBER);
        Priority.setInputType(InputType.TYPE_CLASS_NUMBER);

        int idVal = count + 1;
        row_id = idVal;

        serialNo.setText(idVal + "");
        serialNo.setGravity(Gravity.CENTER);
        serialNo.setTextSize(18);
        serialNo.setTextColor(Color.BLACK);
        serialNo.setTypeface(null, Typeface.BOLD);

        Process.setText("P" + idVal);
        Process.setGravity(Gravity.CENTER);
        Process.setTextSize(16);
        Process.setTextColor(Color.BLACK);
        Process.setTypeface(null, Typeface.BOLD);
        //set process background as it fit the window
        Process.setPadding(0,25,0,26);
        AT.setText("");
        AT.setGravity(Gravity.CENTER);
        AT.setTextSize(16);
        AT.setHint("AT");
        BT.setHint("BT");
        BT.setText("");
        BT.setGravity(Gravity.CENTER);
        BT.setTextSize(16);

        BT.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength) });
        AT.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength) });
        Priority.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength) });


        Priority.setText("0");
        Priority.setTextSize(16);

        Priority.setGravity(Gravity.CENTER);
        if(SPINNER_POSITION!=4) {
            Priority.setVisibility(View.GONE);
            //  Priority.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_cell_bg));
            PriorityColumn.setVisibility(View.GONE);
        }
        //Wrote this line to avoid the underline on new priority added
        //     Priority.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_cell_bg));
        serialNo.setId(SERIAL_NO_ID + idVal);

        Process.setId(PROCESS_NO_ID + idVal);
        //  Process.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_process_cell_bg));
        AT.setId(ARRIVAL_TIME_ID + idVal);
        //  AT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_cell_bg));
        BT.setId(BURST_TIME_ID + idVal);
        //  BT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_cell_bg));
        Priority.setId(PRIORITY_ID + idVal);

        tableRow.setId(TABLE_ROW_ID + idVal);

        tableRow.addView(serialNo);
        tableRow.addView(Process);
        tableRow.addView(AT);
        tableRow.addView(BT);
        tableRow.addView(Priority);
        tableRow.setPadding(0,15,0,15);
        tableInput.addView(tableRow);
        if(count%2==1)
            tableRow.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_row_bg));

        else
            tableRow.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_row_background_white));
        Log.d(TAG, "onClick: " + serialNo.getId());
        Log.d(TAG, "onClick: " + Process.getId());
        Log.d(TAG, "onClick: " + AT.getId());
        Log.d(TAG, "onClick: " + BT.getId());
        Log.d(TAG, "onClick: " + Priority.getId());
        Log.d(TAG, "onClick: " + tableRow.getId());

        count++;

    }
    public int check_radio_button() {
        //0 for not clicked any button
        //1 for  Pre-emptive
        //2 for Non Pre-emptive
        int checked_id = radioGroup.getCheckedRadioButtonId();
        if (checked_id == R.id.Nonpreemtive) {
            return 2;
        } else if (checked_id == R.id.preemtive)
            return 1;
        Toast toast= Toast.makeText(this, "Choose  Pre-emtive \nor \nNon Pre-emtive", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return 0;
    }

    public void FCFS_Function(int[] AT_Array, int[] BT_Array, String[] Process_Array, int[] Serial_No) {


        int n = Process_Array.length;
        int temp;
        String temp_process;
        float AVG_WT = 0, AVG_TAT = 0;

        int[] TAT_Array = new int[n];     // turn around times
        int[] WT_Array = new int[n];     // waiting times
        int[] CT_Array = new int[n];     // Completion time
        ArrayList<String> CPU_Process_Array =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array =  new ArrayList<>();
        //sorting according to arrival times
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - (i + 1); j++) {
                if (AT_Array[j] > AT_Array[j + 1]) {
                    temp = AT_Array[j];
                    AT_Array[j] = AT_Array[j + 1];
                    AT_Array[j + 1] = temp;
                    temp = BT_Array[j];
                    BT_Array[j] = BT_Array[j + 1];
                    BT_Array[j + 1] = temp;
                    temp_process = Process_Array[j];
                    Process_Array[j] = Process_Array[j + 1];
                    Process_Array[j + 1] = temp_process;
                }
            }
        }
        CPU_Process_Array.addAll(Arrays.asList(Process_Array).subList(0, n));
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                CT_Array[i] = AT_Array[i] + BT_Array[i];
                CPU_Time_Array.add( AT_Array[i]);
            }
            else {
                if (AT_Array[i] > CT_Array[i - 1]) {
                    CT_Array[i] = AT_Array[i] + BT_Array[i];
                }
                else {
                    CT_Array[i] = CT_Array[i - 1] + BT_Array[i];
                }

                CPU_Time_Array.add( CT_Array[i - 1]);
            }
            TAT_Array[i] = CT_Array[i] - AT_Array[i];          // turnaround time= completion time- arrival time
            WT_Array[i] = TAT_Array[i] - BT_Array[i];          // waiting time= turnaround time- burst time
            AVG_WT += WT_Array[i];                  // total waiting time
            AVG_TAT += TAT_Array[i];               // total turnaround time
        }
        int count_time =1;
        int count_process =0;
        for(int i=1;i<n;i++){
            if(CT_Array[i-1]!=AT_Array[i] && CT_Array[i-1]<AT_Array[i]){

                temp = CT_Array[i]-BT_Array[i];
                CPU_Time_Array.add(i+count_time,temp);
                CPU_Process_Array.add(i+count_process," // ");
                count_time++;
                count_process++;
            }
            Log.d(TAG, "CPU_Time_Array"+CPU_Time_Array.get(i));
        }

        CPU_Time_Array.add( CT_Array[n-1]);

        AVG_TAT /= n;
        AVG_WT /= n;

        //Call ans table
        ansTable(Process_Array, AT_Array, BT_Array, TAT_Array, WT_Array, CT_Array, AVG_WT, AVG_TAT, Serial_No);
        cpuScheduling(CPU_Process_Array,CPU_Time_Array);

    }

    public void SJF_Function_Non_Preemptive(int[] AT_Array, int[] BT_Array, String[] Process_Array, int[] Serial_No) {
        int n = Process_Array.length;
        float AVG_WT = 0, AVG_TAT = 0;
        int[] TAT_Array = new int[n];     // turn around times
        int[] WT_Array = new int[n];      // waiting times
        int[] CT_Array = new int[n];      // waiting times
        int[] f = new int[n];  // f means it is flag it checks process is completed or not
        int st = 0, tot = 0, i;
        int temp;
        String temp_process;
        ArrayList<String> CPU_Process_Array =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array =  new ArrayList<>();

        //SORTING ACCORDING TO AT AND BT
        for (i = 0; i < n; i++) {
            for (int j = 0; j < n - (i + 1); j++) {
                if (AT_Array[j] > AT_Array[j + 1]) {
                    temp = AT_Array[j];
                    AT_Array[j] = AT_Array[j + 1];
                    AT_Array[j + 1] = temp;
                    temp = BT_Array[j];
                    BT_Array[j] = BT_Array[j + 1];
                    BT_Array[j + 1] = temp;
                    temp_process = Process_Array[j];
                    Process_Array[j] = Process_Array[j + 1];
                    Process_Array[j + 1] = temp_process;
                }
                if (AT_Array[j] == AT_Array[j + 1]) {
                    if( BT_Array[j]>BT_Array[j + 1]) {

                        temp = AT_Array[j];
                        AT_Array[j] = AT_Array[j + 1];
                        AT_Array[j + 1] = temp;
                        temp = BT_Array[j];
                        BT_Array[j] = BT_Array[j + 1];
                        BT_Array[j + 1] = temp;
                        temp_process = Process_Array[j];
                        Process_Array[j] = Process_Array[j + 1];
                        Process_Array[j + 1] = temp_process;
                    }
                }
            }
        }

        int[] rem_BT = new int[n];    // it is also stores burst time
        System.arraycopy(BT_Array,0, rem_BT, 0, n);


        while (true) {
            int min = 9999, c = n;
            if (tot == n)
                break;

            for (i = 0; i < n; i++) {
                /*
                 * If i'th process arrival time <= system time and its flag=0 and burst<min
                 * That process will be executed first
                 */
                if ((AT_Array[i] <= st) && (f[i] == 0) && (rem_BT[i] < min)) {
                    min = rem_BT[i];
                    c = i;
                    Log.d(TAG, "F[i]" + f[i]);
                }
            }
            if (c == n) {
                st++;
                Log.d(TAG, "NOTHING TO change: "+st);
            }
            else {
                CT_Array[c] = st + BT_Array[c];
                st += BT_Array[c];
                TAT_Array[c] = CT_Array[c] - AT_Array[c];
                WT_Array[c] = TAT_Array[c] - BT_Array[c];
                f[c] = 1;
                tot++;

            }
        }
        for (i = 0; i < n; i++) {
            if (i == 0) {
                CT_Array[i] = AT_Array[i] + BT_Array[i];
                CPU_Time_Array.add(AT_Array[i]);
                CPU_Process_Array.add(i, Process_Array[i]);
            } else {
                CPU_Time_Array.add(i, CT_Array[i - 1]);
                CPU_Process_Array.add(i, Process_Array[i]);
            }
            Log.d(TAG, "CPU_Time_Array" + CPU_Time_Array.get(i));
            Log.d(TAG, "CPU_Process_Array" + CPU_Process_Array.get(i));
        }


        for (i = 0; i < n; i++) {
            TAT_Array[i] = CT_Array[i] - AT_Array[i];
            WT_Array[i] = TAT_Array[i] - BT_Array[i];
            AVG_WT += WT_Array[i];
            AVG_TAT += TAT_Array[i];

        }

        int count_time =1;
        int count_process =0;
        for(i=1;i<n;i++){
            if(CT_Array[i-1]!=AT_Array[i]&& CT_Array[i-1]<AT_Array[i]){

                temp = CT_Array[i]-BT_Array[i];
                CPU_Time_Array.add(i+count_time,temp);
                CPU_Process_Array.add(i+count_process," // ");
                count_time++;
                count_process++;
            }
            Log.d(TAG, "CPU_Time_Array SJF"+CPU_Time_Array.get(i));
            Log.d(TAG, "CPU_Process_Array SJF"+CPU_Process_Array.get(i));
        }

        CPU_Time_Array.add( CT_Array[n-1]);

        for (i = 0;i<CPU_Time_Array.size();i++){
            for (int j=i+1;j<CPU_Time_Array.size() ;j++){
                if(CPU_Time_Array.get(i)>CPU_Time_Array.get(j)){

                    Collections.swap(CPU_Time_Array,i,j);
                    Collections.swap(CPU_Process_Array,i-1,j-1);
                    Log.d(TAG, "SJF_Function_Non_Preemptive: "+CPU_Time_Array);
                }
            }
        }

        AVG_TAT /= n;
        AVG_WT /= n;
        Log.d(TAG, "AVG_TAT: " + AVG_TAT);
        Log.d(TAG, "AVG_WT: " + AVG_WT);
        ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
        sv.scrollTo(0, sv.getBottom());
        //Call ans table
        ansTable(Process_Array, AT_Array, BT_Array, TAT_Array, WT_Array, CT_Array, AVG_WT, AVG_TAT, Serial_No);
        cpuScheduling(CPU_Process_Array, CPU_Time_Array);

    }


    public void SRTF_Function_Preemptive(int[] AT_Array, int[] BT_Array, String[] Process_Array, int[] Serial_No) {

        int n = Process_Array.length;
        float AVG_WT = 0, AVG_TAT = 0;
        int[] TAT_Array = new int[n];     // turn around times
        int[] WT_Array = new int[n];     // waiting times
        int[] CT_Array = new int[n];     // waiting times
        int[] rem_BT = new int[n];     // Also stores BT
        ArrayList<String> CPU_Process_Array =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array_Check =  new ArrayList<>();
        ArrayList<String> CPU_Process_Array_Check =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array_Check2 =  new ArrayList<>();
        ArrayList<String> CPU_Process_Array_Check2=  new ArrayList<>();

        int[] f = new int[n];           // f means it is flag it checks process is completed or not
        int st = 0;
        int temp;
        String temp_process;
        boolean processes_rem = true;
        int[] AT_Copy = new int[n];


        for (int i = 0; i < n; i++) {
            for (int j = i  ; j < n - (i + 1); j++) {
                if (AT_Array[j] > AT_Array[j + 1]) {
                    temp = AT_Array[j];
                    AT_Array[j] = AT_Array[j + 1];
                    AT_Array[j + 1] = temp;
                    temp = BT_Array[j];
                    BT_Array[j] = BT_Array[j + 1];
                    BT_Array[j + 1] = temp;
                    temp_process = Process_Array[j];
                    Process_Array[j] = Process_Array[j + 1];
                    Process_Array[j + 1] = temp_process;
                }
            }
            f[i]=0;
        }
        System.arraycopy(BT_Array, 0, rem_BT, 0, n);
        System.arraycopy(AT_Array , 0,AT_Copy,0,n);
        //TO GET MIN AND MAX AT
        for (int i = 0; i < n; i++) {
            for (int j = i  ; j < n - (i + 1); j++) {
                if (AT_Copy[j] > AT_Copy[j + 1]) {
                    temp = AT_Copy[j];
                    AT_Copy[j] = AT_Copy[j + 1];
                    AT_Copy[j + 1] = temp;
                }
            }
        }
        int At_min, At_max;
        At_max = AT_Copy[n-1];
        Log.d(TAG, "AT_Copy = "+AT_Copy);
        Log.d(TAG, "AT_MAX = "+At_max);

        At_min = AT_Copy[0];
        Log.d(TAG, "AT_MIN = "+At_min);


        int x=0;
        CPU_Time_Array.add(AT_Array[0]);
        int temp_bt ,count = 0;
        //LOGIC
        boolean process=true;
        while (process) {
            temp_bt =9999;
            int  c=n;

            for (int i = 0; i < n; i++) {
                if (st >= AT_Array[i] && temp_bt > rem_BT[i] && rem_BT[i] != 0 && f[i] == 0) {
                    c = i;
                    Log.d(TAG, "CHAnge c " + c + " at time : " + st+ " of process " + Process_Array[c]);
                    temp_bt = rem_BT[i];
                    Log.d(TAG, "TEmp_BT" + temp_bt + " of process " + Process_Array[c]);
                }
            }
            //No process available
            if(c==n){
                st++;

                if(x<1 && st>AT_Copy[0]){
                    // CPU_Time_Array.add(st);
                    Log.e(TAG, "SYSTEM TIME"+st );
                    //  Log.d(TAG, "CPU_Time_Array: "+ CPU_Time_Array);
                    CPU_Process_Array.add(" // ");
                    Log.d(TAG, "CPU_Process_Array: "+ CPU_Process_Array);}
                x++;
            }
            else{
                if(x>0 &&AT_Copy[0]!=st) {
                    CPU_Time_Array.add(st);
                    Log.d(TAG, "CPU_Time_Array: CHange  due to // "+ CPU_Time_Array);
                }
                rem_BT[c]--;
                Log.d(TAG, "REM_BT "+rem_BT[c]+" of process "+ Process_Array[c]);
                st++;
                Log.e(TAG, "SYSTEM TIME"+st);
                CPU_Time_Array.add(st);
                Log.d(TAG, "CPU_Time_Array: "+ CPU_Time_Array);
                CPU_Process_Array.add(Process_Array[c]);
                Log.d(TAG, "CPU_Process_Array: "+ CPU_Process_Array);
                if(rem_BT[c]==0){
                    CT_Array[c]=st;
                    Log.d(TAG, "COMPLETION TIME "+Process_Array[c]+" "+CT_Array[c]);
                    Log.d(TAG, "TAT TIME "+Process_Array[c]+" "+TAT_Array[c]);
                    Log.d(TAG, "WT TIME "+Process_Array[c]+" "+WT_Array[c]);

                    count++;
                    Log.d(TAG, "NO of process completed "+ count);
                    f[c]=1;
                    Log.e(TAG, "Prcocess completed : "+Process_Array[c]);
                }
                x=0;
                int tempx;
                String tempo;
                tempx=AT_Array[c];
                AT_Array[c]=AT_Array[0];
                AT_Array[0] = tempx;

                tempx=rem_BT[c];
                rem_BT[c]=rem_BT[0];
                rem_BT[0] = tempx;

                tempx=BT_Array[c];
                BT_Array[c]=BT_Array[0];
                BT_Array[0] = tempx;

                tempo=Process_Array[c];
                Process_Array[c]=Process_Array[0];
                Process_Array[0] = tempo;

                tempx=f[c];
                f[c]=f[0];
                f[0] = tempx;

                tempx=CT_Array[c];
                CT_Array[c]=CT_Array[0];
                CT_Array[0] = tempx;


            }
            if(count==n){
                process=false;
                Log.d(TAG, "ALL PROCESS COMP SRTF "+ st);
                break;
            }

        }

        //Chunk values
        String temp_process1, temp_process2 = "";
        //CPU_Time_Array_Check.add(CPU_Time_Array.get(0));
        for (int i = 0; i < CPU_Process_Array.size(); i++) {
            temp_process1 = CPU_Process_Array.get(i);
            if (!temp_process1.equals(temp_process2)) {
                temp_process2 = temp_process1;
                CPU_Process_Array_Check.add(CPU_Process_Array.get(i));
                CPU_Time_Array_Check.add(CPU_Time_Array.get(i));

            }
        }
        CPU_Time_Array_Check.add(CPU_Time_Array.get(CPU_Time_Array.size()-1));
   /*   //COmparision value as At
       int a_value=1;
        for (int i=0;i<st;i++){

            if(AT_Copy[a_value]==i){
                int position=  CPU_Time_Array.indexOf(i);
                CPU_Time_Array_Check.add(CPU_Time_Array.get(position));
                Log.d(TAG, "  CPU_Time_Array_Check From AT_Copy = "+CPU_Time_Array.get(position));
                if(position!=0){
                    CPU_Process_Array_Check.add(CPU_Process_Array.get(position-1));
                    Log.d(TAG, "  CPU_Process_Array_Check From AT_Copy = "+CPU_Process_Array.get(position-1));
                }
                else {
                    CPU_Process_Array_Check.add(CPU_Process_Array.get(position));
                    Log.d(TAG, "  CPU_Process_Array_Check From AT_Copy = "+CPU_Process_Array_Check.get(position));
                }

                if(a_value<n-1){
                    a_value++;
                }

            }
        }
        Log.d(TAG, "SRTF_Function_Preemptive: CPU_Time_Array_Check BeforeSort "+CPU_Time_Array_Check);
        Log.d(TAG, "SRTF_Function_Preemptive: CPU_Process_Array_Check BeforeSort "+CPU_Process_Array_Check);
*/
        //   CPU_Time_Array_Check.add(CPU_Time_Array.get(CPU_Time_Array.size()-1));

        //Sorting
        /*Log.d(TAG, "CPU_Time_Array_Check.size(): "+CPU_Time_Array_Check.size());
        for(int i=0;i<CPU_Time_Array_Check.size();i++)
        {
            for (int j=i+1;j<CPU_Time_Array_Check.size();j++){
                if(CPU_Time_Array_Check.get(i)>CPU_Time_Array_Check.get(j)){
                    Collections.swap(CPU_Time_Array_Check,i,j);
                    Collections.swap(CPU_Process_Array_Check,i,j);
                    Log.d(TAG, "SJF_Function_Non_Preemptive: "+CPU_Time_Array_Check);
                }
            }
        }*/

        Log.d(TAG, "SRTF_Function_Preemptive: CPU_Time_Array_Check "+CPU_Time_Array_Check);
        Log.d(TAG, "SRTF_Function_Preemptive: CPU_Process_Array_Check "+CPU_Process_Array_Check);


        //It will discard the repeatation
       /* int temp_time1, temp_time2 = 0;
        for (int i = 0; i < CPU_Time_Array_Check.size(); i++) {
            temp_time1 = CPU_Time_Array_Check.get(i);
            for (int j = i + 1; j < CPU_Time_Array_Check.size(); j++) {
                if (temp_time1 == temp_time2) {
                    CPU_Time_Array_Check.remove(i);
                    if (i == 0)
                        CPU_Process_Array_Check.remove(i);
                    else
                        CPU_Process_Array_Check.remove(i - 1);

                }
            }
        }*/
       /* int temp_time1,temp_time2=0;
        for (int i = 0; i < CPU_Time_Array_Check.size(); i++) {
            temp_time1 = CPU_Time_Array_Check.get(i);
            if (temp_time1!=temp_time2) {
                temp_time2 = temp_time1;
                CPU_Time_Array_Check2.add(CPU_Time_Array_Check.get(i));
                CPU_Process_Array_Check2.add(CPU_Process_Array_Check.get(i-1));


            }
        }*/

        for (int i = 0; i < n; i++) {
            TAT_Array[i] = CT_Array[i] - AT_Array[i];
            WT_Array[i] = TAT_Array[i] - BT_Array[i];
            AVG_WT += WT_Array[i];
            AVG_TAT += TAT_Array[i];
        }
        AVG_TAT /= n;
        AVG_WT /= n;
        Log.d(TAG, "AVG_TAT: " + AVG_TAT);
        Log.d(TAG, "AVG_WT: " + AVG_WT);

        //Call ans table
        ansTable(Process_Array, AT_Array, BT_Array, TAT_Array, WT_Array, CT_Array, AVG_WT, AVG_TAT, Serial_No);
        cpuScheduling(CPU_Process_Array_Check, CPU_Time_Array_Check);

    }
    public void LJF_Function_Non_Preemptive(int[] AT_Array, int[] BT_Array, String[] Process_Array, int[] Serial_No) {
        int n = Process_Array.length;

        float AVG_WT = 0, AVG_TAT = 0;

        int[] TAT_Array = new int[n];     // turn around times
        int[] WT_Array = new int[n];      // waiting times
        int[] CT_Array = new int[n];      // waiting times

        ArrayList<String> CPU_Process_Array =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array =  new ArrayList<>();

        int[] f = new int[n];  // f means it is flag it checks process is completed or not
        int st = 0, tot = 0;
        int temp;
        String temp_process;


        for (int i = 0; i < n; i++) {
            f[i]=0;
            for (int j = 0; j < n - (i + 1); j++) {
                if (AT_Array[j] > AT_Array[j + 1]) {
                    temp = AT_Array[j];
                    AT_Array[j] = AT_Array[j + 1];
                    AT_Array[j + 1] = temp;
                    temp = BT_Array[j];
                    BT_Array[j] = BT_Array[j + 1];
                    BT_Array[j + 1] = temp;
                    temp_process = Process_Array[j];
                    Process_Array[j] = Process_Array[j + 1];
                    Process_Array[j + 1] = temp_process;
                }
            }
        }
        int[] rem_BT = new int[n];    // it is also stores burst time
        System.arraycopy(BT_Array, 0, rem_BT, 0, n);
        int x=0;
        CPU_Time_Array.add(AT_Array[0]);
        int count_process_compl=0;
        while (true){
            int temp_BT=0;
            int c=n;
            for (int i =0;i<n;i++) {
                if (st >= AT_Array[i] && rem_BT[i]>=temp_BT  && f[i] == 0) {
                    c = i;
                    Log.d(TAG, "C from for: "+c+ " Process name = "+Process_Array[c]+" Has BT = "+rem_BT[c]);
                    //temp_BT = rem_BT[c];
                    temp_BT=BT_Array[c];
                }
            }
            if(c==n){
                st++;

                if(x<1 && st>AT_Array[0]){

                    Log.e(TAG, "SYSTEM TIME"+st );

                    CPU_Process_Array.add(" // ");
                    Log.d(TAG, "CPU_Process_Array: "+ CPU_Process_Array);}
                x++;
            }
            else {
                if(x>0){
                    CPU_Time_Array.add(st);
                    Log.d(TAG, "CPU_Time_Array: CHange  due to // "+ CPU_Time_Array);
                }x=0;

                Log.d(TAG, "Process No : "+Process_Array[c]+" Is = "+rem_BT[c]);
                st=st+rem_BT[c];
                rem_BT[c]=0;
                Log.e(TAG, "SYSTEM TIME"+st );
                CPU_Process_Array.add(Process_Array[c]);
                Log.d(TAG, "");
                CPU_Time_Array.add(st);
                Log.d(TAG, "");

                CT_Array[c] = st;
                TAT_Array[c] = CT_Array[c] - AT_Array[c];
                WT_Array[c] = TAT_Array[c] - BT_Array[c];
                Log.d(TAG, "CT_Array"+CT_Array[c]);
                Log.d(TAG, "TAT_Array[c]"+TAT_Array[c]);
                Log.d(TAG, "WT_Array[c]"+WT_Array[c]);
                f[c]=1;
                count_process_compl++;
                Log.d(TAG, "NO of process completed "+ count_process_compl);
            }
            if(count_process_compl==n){
                break;
            }
        }

        for (int i = 0; i < n; i++) {

            AVG_WT += WT_Array[i];
            AVG_TAT += TAT_Array[i];
            Log.e(TAG, "BT_ARRARY" + BT_Array[i]);
        }

        AVG_TAT /= n;
        AVG_WT /= n;
        Log.d(TAG, "AVG_TAT: " + AVG_TAT);
        Log.d(TAG, "AVG_WT: " + AVG_WT);

        //Call ans table
        ansTable(Process_Array, AT_Array, BT_Array, TAT_Array, WT_Array, CT_Array, AVG_WT, AVG_TAT, Serial_No);
        cpuScheduling(CPU_Process_Array, CPU_Time_Array);

    }
    public void LRTF_Function_Preemptive(int[] AT_Array, int[] BT_Array, String[] Process_Array, int[] Serial_No) {

        int n = Process_Array.length;
//DONE
        float AVG_WT = 0, AVG_TAT = 0;

        int[] TAT_Array = new int[n];     // turn around times
        int[] WT_Array = new int[n];     // waiting times
        int[] CT_Array = new int[n];     // waiting times
        int[] rem_BT = new int[n];     // Also stores BT
        ArrayList<String> CPU_Process_Array =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array_Check =  new ArrayList<>();
        ArrayList<String> CPU_Process_Array_Check =  new ArrayList<>();
        int[] f = new int[n];  // f means it is flag it checks process is completed or not
        int st = 0, tot = 0;
        int temp;
        String temp_process;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - (i + 1); j++) {
                if (AT_Array[j] > AT_Array[j + 1]) {
                    temp = AT_Array[j];
                    AT_Array[j] = AT_Array[j + 1];
                    AT_Array[j + 1] = temp;
                    temp = BT_Array[j];
                    BT_Array[j] = BT_Array[j + 1];
                    BT_Array[j + 1] = temp;
                    temp_process = Process_Array[j];
                    Process_Array[j] = Process_Array[j + 1];
                    Process_Array[j + 1] = temp_process;
                }
            }
        }
        System.arraycopy(BT_Array, 0, rem_BT, 0, n);

        //LOGIC
        int x=0;
        CPU_Time_Array.add(AT_Array[0]);
        int temp_bt ,count = 0;
        //LOGIC
        while (true) {
            temp_bt =0;
            int  c=n;

            for (int i = 0; i < n; i++) {
                if (st >= AT_Array[i] && temp_bt < rem_BT[i] && rem_BT[i] != 0 && f[i] == 0) {
                    c = i;
                    Log.d(TAG, "CHAngE c " + c + " at time : " + st+ " of process " + Process_Array[c]);
                    temp_bt = rem_BT[i];
                    Log.d(TAG, "TEmp_BT" + temp_bt + " of process " + Process_Array[c]);
                }
            }
            //No process available
            if(c==n){
                st++;

                if(x<1 && st>AT_Array[0]){
                    // CPU_Time_Array.add(st);
                    Log.e(TAG, "SYSTEM TIME"+st );
                    //  Log.d(TAG, "CPU_Time_Array: "+ CPU_Time_Array);
                    CPU_Process_Array.add(" // ");
                    Log.d(TAG, "CPU_Process_Array: "+ CPU_Process_Array);}
                x++;
            }
            else{
                //     Log.d(TAG, "Change c from else " +c);
                if(x>0&& st>AT_Array[0]){
                    CPU_Time_Array.add(st);
                    Log.d(TAG, "CPU_Time_Array: CHange  due to // "+ CPU_Time_Array);
                }
                rem_BT[c]--;
                Log.d(TAG, "REM_BT "+rem_BT[c]+" of process "+ Process_Array[c]);
                st++;
                Log.e(TAG, "SYSTEM TIME"+st);
                CPU_Time_Array.add(st);
                Log.d(TAG, "CPU_Time_Array: "+ CPU_Time_Array);
                CPU_Process_Array.add(Process_Array[c]);
                Log.d(TAG, "CPU_Process_Array: "+ CPU_Process_Array);
                if(rem_BT[c]==0){
                    CT_Array[c]=st;
                    TAT_Array[c] = CT_Array[c] - AT_Array[c];
                    WT_Array[c] = TAT_Array[c] - BT_Array[c];

                    Log.d(TAG, "COMPLETION TIME "+Process_Array[c]+" "+CT_Array[c]);
                    Log.d(TAG, "TAT TIME "+Process_Array[c]+" "+TAT_Array[c]);
                    Log.d(TAG, "WT TIME "+Process_Array[c]+" "+WT_Array[c]);

                    count++;
                    Log.d(TAG, "NO of process completed "+ count);
                    f[c]=1;
                    Log.e(TAG, "Prcocess completed : "+Process_Array[c]);
                }
                x=0;

            }
            if(count==n){
                //  processes_rem=false;
                Log.d(TAG, "ALL PROCESS COMP SRTF "+ st);
                break;
            }
        }

        //Chunk values
        String temp_process1, temp_process2 = "";

        for (int i = 0; i < CPU_Process_Array.size(); i++) {
            temp_process1 = CPU_Process_Array.get(i);
            if (!temp_process1.equals(temp_process2)) {
                temp_process2 = temp_process1;
                CPU_Process_Array_Check.add(CPU_Process_Array.get(i));
                CPU_Time_Array_Check.add(CPU_Time_Array.get(i));

            }
        }
        CPU_Time_Array_Check.add(CPU_Time_Array.get(CPU_Time_Array.size()-1));
        //COmparision value as At
        /*int a_value=1;
        for (int i=0;i<st;i++){

            if(AT_Copy[a_value]==i){
                int position=  CPU_Time_Array.indexOf(i);
                CPU_Time_Array_Check.add(CPU_Time_Array.get(position));
                Log.d(TAG, "  CPU_Time_Array_Check From AT_Copy = "+CPU_Time_Array.get(position));
                if(position!=0){
                    CPU_Process_Array_Check.add(CPU_Process_Array.get(position-1));
                    Log.d(TAG, "  CPU_Process_Array_Check From AT_Copy = "+CPU_Process_Array.get(position-1));
                }
                else {
                    CPU_Process_Array_Check.add(CPU_Process_Array.get(position));
                    Log.d(TAG, "  CPU_Process_Array_Check From AT_Copy = "+CPU_Process_Array_Check.get(position));
                }

                if(a_value<n-1){
                    a_value++;
                }

            }
        }*/
       /* Log.d(TAG, "SRTF_Function_Preemptive: CPU_Time_Array_Check BeforeSort "+CPU_Time_Array_Check);
        Log.d(TAG, "SRTF_Function_Preemptive: CPU_Process_Array_Check BeforeSort "+CPU_Process_Array_Check);*/

        CPU_Time_Array_Check.add(CPU_Time_Array.get(CPU_Time_Array.size()-1));

        for (int i = 0; i < n; i++) {
           /* TAT_Array[i] = CT_Array[i] - AT_Array[i];
            WT_Array[i] = TAT_Array[i] - BT_Array[i];*/
            AVG_WT += WT_Array[i];
            AVG_TAT += TAT_Array[i];
        }
        AVG_TAT /= n;
        AVG_WT /= n;
        Log.d(TAG, "AVG_TAT: " + AVG_TAT);
        Log.d(TAG, "AVG_WT: " + AVG_WT);

        //Call ans table
        ansTable(Process_Array, AT_Array, BT_Array, TAT_Array, WT_Array, CT_Array, AVG_WT, AVG_TAT, Serial_No);
        cpuScheduling(CPU_Process_Array_Check, CPU_Time_Array_Check);

    }

    public void PROIRITY_Function_Non_Preemptive(int[] AT_Array, int[] BT_Array, String[] Process_Array, int[] Priority_Array, int[] Serial_No) {
//DONE
        int n = Process_Array.length;
        int temp;
        String temp_process;
        int st= 0;
        float AVG_WT = 0, AVG_TAT = 0;
        ArrayList<String> CPU_Process_Array =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array =  new ArrayList<>();
        int[] TAT_Array = new int[n];     // turn around times
        int[] WT_Array = new int[n];     // waiting times
        int[] CT_Array = new int[n];     // waiting times
        int[] f = new int[n];     //  f means it is flag it checks process is completed or not
        int[] rem_BT = new int[n];  // remaining  BT

        //sorting according to arrival times
        for (int i = 0; i < n; i++)
        {f[i]=0;

            for (int j = 0; j < n - i - 1; j++)
            {
                if (AT_Array[j] > AT_Array[j + 1])
                {
                    //swapping arrival time
                    temp = AT_Array[j];
                    AT_Array[j] = AT_Array[j + 1];
                    AT_Array[j + 1] = temp;

                    //swapping burst time
                    temp = BT_Array[j];
                    BT_Array[j] = BT_Array[j + 1];
                    BT_Array[j + 1] = temp;

                    //swapping priority
                    temp = Priority_Array[j];
                    Priority_Array[j] = Priority_Array[j + 1];
                    Priority_Array[j + 1] = temp;

                    //swapping process identity
                    temp_process = Process_Array[j];
                    Process_Array[j] = Process_Array[j + 1];
                    Process_Array[j + 1] = temp_process;

                }
                //sorting according to priority when arrival timings are same
                if (AT_Array[j] == AT_Array[j + 1])
                {
                    if (Priority_Array[j] < Priority_Array[j + 1])
                    {
                        //swapping arrival time
                        temp = AT_Array[j];
                        AT_Array[j] = AT_Array[j + 1];
                        AT_Array[j + 1] = temp;

                        //swapping burst time
                        temp = BT_Array[j];
                        BT_Array[j] = BT_Array[j + 1];
                        BT_Array[j + 1] = temp;

                        //swapping priority
                        temp = Priority_Array[j];
                        Priority_Array[j] = Priority_Array[j + 1];
                        Priority_Array[j + 1] = temp;

                        //swapping process identity
                        temp_process = Process_Array[j];
                        Process_Array[j] = Process_Array[j + 1];
                        Process_Array[j + 1] = temp_process;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++)
        {
            Log.d(TAG, " 1 Process_Array " + Process_Array[i]);
            Log.d(TAG, " 2 AT_Array " + AT_Array[i]);
            Log.d(TAG, " 3 BT_Array " + BT_Array[i]);
        }
        System.arraycopy(BT_Array, 0, rem_BT, 0, n);

        int x=0;
        count=0;

        CPU_Time_Array.add(AT_Array[0]);
        boolean process=true;
        while (process){
            int temp_priority=-1;
            int c=n;
            for (int i =0;i<n;i++) {
                if (st >= AT_Array[i] && Priority_Array[i]>temp_priority  /*&& rem_BT[i] != 0*/ && f[i] == 0) {
                    c = i;
                    Log.d(TAG, "C from for: "+c+ " Process name = "+Process_Array[c]+" Has BT = "+rem_BT[c]);
                    //temp_BT = rem_BT[c];
                    temp_priority=Priority_Array[c];
                }
            }
            if(c==n){
                st++;

                if(x<1 && st>AT_Array[0]){

                    Log.e(TAG, "SYSTEM TIME"+st );

                    CPU_Process_Array.add(" // ");
                    Log.d(TAG, "CPU_Process_Array: "+ CPU_Process_Array);}
                x++;
            }
            else {
                if(x>1){
                    CPU_Time_Array.add(st);
                    Log.d(TAG, "CPU_Time_Array: CHange  due to // "+ CPU_Time_Array);
                }x=0;

                Log.d(TAG, "Process No : "+Process_Array[c]+" Is = "+rem_BT[c]);
                st=st+rem_BT[c];
                rem_BT[c]=0;
                Log.e(TAG, "SYSTEM TIME"+st );
                CPU_Process_Array.add(Process_Array[c]);
                Log.d(TAG, "");
                CPU_Time_Array.add(st);
                Log.d(TAG, "");

                CT_Array[c] = st;
                TAT_Array[c] = CT_Array[c] - AT_Array[c];
                WT_Array[c] = TAT_Array[c] - BT_Array[c];
                Log.d(TAG, "CT_Array"+CT_Array[c]);
                Log.d(TAG, "TAT_Array[c]"+TAT_Array[c]);
                Log.d(TAG, "WT_Array[c]"+WT_Array[c]);
                f[c]=1;
                count++;
                Log.d(TAG, "NO of process completed "+ count);
            }
            if(count==n){
                process=false;
                break;
            }
        }

        for ( int i = 0; i < n; i++) {
            AVG_WT += WT_Array[i];
            AVG_TAT += TAT_Array[i];

        }
        AVG_TAT /= n;
        AVG_WT /= n;

        //Call ans table
        ansTable(Process_Array, AT_Array, BT_Array, TAT_Array, WT_Array, CT_Array, AVG_WT, AVG_TAT, Serial_No);
        cpuScheduling(CPU_Process_Array, CPU_Time_Array);

    }

    public void PROIRITY_Function_Preemptive(int[] AT_Array, int[] BT_Array, String[] Process_Array, int[] Priority_Array, int[] Serial_No) {
        int n = Process_Array.length;

        int st = 0;
        float AVG_WT = 0, AVG_TAT = 0;
        int temp;
        String temp_process;
        int[] AT_Copy = new int[n];     // Arrival Copy times
        int[] TAT_Array = new int[n];     // turn around times
        int[] WT_Array = new int[n];     // waiting times
        int[] CT_Array = new int[n];     // waiting times
        int[] f = new int[n];     // Flag
        ArrayList<String> CPU_Process_Array =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array_Check =  new ArrayList<>();
        ArrayList<String> CPU_Process_Array_Check=  new ArrayList<>();

        //sorting according to arrival times
        for (int i = 0; i < n; i++)
        {
            f[i]=0;
            for (int j = 0; j < n - i - 1; j++)
            {
                if (AT_Array[j] > AT_Array[j + 1])
                {
                    //swapping arrival time
                    temp = AT_Array[j];
                    AT_Array[j] = AT_Array[j + 1];
                    AT_Array[j + 1] = temp;

                    //swapping burst time
                    temp = BT_Array[j];
                    BT_Array[j] = BT_Array[j + 1];
                    BT_Array[j + 1] = temp;

                    //swapping priority
                    temp = Priority_Array[j];
                    Priority_Array[j] = Priority_Array[j + 1];
                    Priority_Array[j + 1] = temp;

                    //swapping process identity
                    temp_process = Process_Array[j];
                    Process_Array[j] = Process_Array[j + 1];
                    Process_Array[j + 1] = temp_process;

                }
                //sorting according to priority when arrival timings are same
                if (AT_Array[j] == AT_Array[j + 1])
                {
                    if (Priority_Array[j] < Priority_Array[j + 1])
                    {
                        //swapping arrival time
                        temp = AT_Array[j];
                        AT_Array[j] = AT_Array[j + 1];
                        AT_Array[j + 1] = temp;

                        //swapping burst time
                        temp = BT_Array[j];
                        BT_Array[j] = BT_Array[j + 1];
                        BT_Array[j + 1] = temp;

                        //swapping priority
                        temp = Priority_Array[j];
                        Priority_Array[j] = Priority_Array[j + 1];
                        Priority_Array[j + 1] = temp;

                        //swapping process identity
                        temp_process = Process_Array[j];
                        Process_Array[j] = Process_Array[j + 1];
                        Process_Array[j + 1] = temp_process;

                    }
                }
            }
        }
        System.arraycopy(AT_Array , 0,AT_Copy,0,n);
        for (int i = 0; i < n; i++) {
            for (int j = i  ; j < n - (i + 1); j++) {
                if (AT_Copy[j] > AT_Copy[j + 1]) {
                    temp = AT_Copy[j];
                    AT_Copy[j] = AT_Copy[j + 1];
                    AT_Copy[j + 1] = temp;
                }
            }
        }
        int At_min, At_max;
        At_max = AT_Copy[n-1];
        Log.d(TAG, "AT_Copy = "+AT_Copy);
        Log.d(TAG, "AT_MAX = "+At_max);

        At_min = AT_Copy[0];
        Log.d(TAG, "AT_MIN = "+At_min);

        int[] rem_BT = new int[n];
        System.arraycopy(BT_Array, 0, rem_BT, 0, n);
        //code
        CPU_Time_Array.add(AT_Copy[0]);
        int x=0;
        int counter=0;
        boolean process=true;
        while (process) {
            // int  temp_bt =999;
            int  c=n ;
            int temp_priority=-1;
            for (int i = 0; i < n; i++) {
                if (st >= AT_Array[i] && Priority_Array[i]>temp_priority  && f[i] == 0) {
                    c = i;
                    Log.d(TAG, "C from for: "+c+ " Process name = "+Process_Array[c]+" Has BT = "+rem_BT[c]);
                    //temp_BT = rem_BT[c];
                    temp_priority=Priority_Array[c];
                }
            }
            //No process available
            if(c==n){
                st++;

                if(x<1 && st>At_min){
                    // CPU_Time_Array.add(st);
                    Log.e(TAG, "SYSTEM TIME"+st );
                    //  Log.d(TAG, "CPU_Time_Array: "+ CPU_Time_Array);
                    CPU_Process_Array.add(" // ");
                    Log.d(TAG, "CPU_Process_Array: "+ CPU_Process_Array);}
                x++;
            }
            else{
                //     Log.d(TAG, "Change c from else " +c);
                if(  x>0 &&  st>At_min ) {
                    CPU_Time_Array.add(st);
                    Log.d(TAG, "CPU_Time_Array: CHange  due to // "+ CPU_Time_Array);
                }
                rem_BT[c]--;
                Log.d(TAG, "REM_BT "+rem_BT[c]+" of process "+ Process_Array[c]);
                st++;
                Log.e(TAG, "SYSTEM TIME"+st);
                CPU_Time_Array.add(st);
                Log.d(TAG, "CPU_Time_Array: "+ CPU_Time_Array);
                CPU_Process_Array.add(Process_Array[c]);
                Log.d(TAG, "CPU_Process_Array: "+ CPU_Process_Array);
                if(rem_BT[c]==0){
                    CT_Array[c]=st;
                    TAT_Array[c] = CT_Array[c] - AT_Array[c];
                    WT_Array[c] = TAT_Array[c] - BT_Array[c];

                    Log.d(TAG, "COMPLETION TIME "+Process_Array[c]+" "+CT_Array[c]);
                    Log.d(TAG, "TAT TIME "+Process_Array[c]+" "+TAT_Array[c]);
                    Log.d(TAG, "WT TIME "+Process_Array[c]+" "+WT_Array[c]);

                    counter++;
                    Log.d(TAG, "NO of process completed "+ counter);
                    f[c]=1;
                    Log.e(TAG, "Prcocess completed : "+Process_Array[c]);
                }
                x=0;
            }
            if(counter==n){
                process=false;
                Log.d(TAG, "ALL PROCESS COMP PP "+ st);
                break;
            }
        }

        //Chunk values
        String temp_process1, temp_process2 = "";
        //CPU_Time_Array_Check.add(CPU_Time_Array.get(0));
        for (int i = 0; i < CPU_Process_Array.size(); i++) {
            temp_process1 = CPU_Process_Array.get(i);
            if (!temp_process1.equals(temp_process2)) {
                temp_process2 = temp_process1;
                CPU_Process_Array_Check.add(CPU_Process_Array.get(i));
                CPU_Time_Array_Check.add(CPU_Time_Array.get(i));

            }
        }
        CPU_Time_Array_Check.add(CPU_Time_Array.get(CPU_Time_Array.size()-1));

        for (int i = 0; i < n; i++) {
            AVG_WT += WT_Array[i];
            AVG_TAT += TAT_Array[i];
        }
        AVG_TAT /= n;
        AVG_WT /= n;
        Log.d(TAG, "AVG_TAT: " + AVG_TAT);
        Log.d(TAG, "AVG_WT: " + AVG_WT);

        //Call ans table
        ansTable(Process_Array, AT_Array, BT_Array, TAT_Array, WT_Array, CT_Array, AVG_WT, AVG_TAT, Serial_No);
        cpuScheduling(CPU_Process_Array_Check, CPU_Time_Array_Check);
    }


    public void RoundRobin_Function(int[] AT_Array, int[] BT_Array, String[] Process_Array, int TimeQuantum, int[] Serial_No) {

        int n = Process_Array.length;
        float AVG_WT = 0, AVG_TAT = 0;
        int[] TAT_Array = new int[n];     // turn around times
        int[] AT_COPY = new int[n];     // AT COPY times
        int[] WT_Array = new int[n];      // waiting times
        int[] CT_Array = new int[n];      // Completion times
        int[] rem_BT = new int[n];      // Remaining BT
        int[] f = new int[n];             // flag

        ArrayList<String> CPU_Process_Array =  new ArrayList<>();
        ArrayList<Integer> CPU_Time_Array =  new ArrayList<>();
        ArrayList<Integer> POSITION_ARRAYLIST =  new ArrayList<>();
        ArrayList<Integer> temp_List =  new ArrayList<>();
       /* ArrayMap<Integer,Integer> temp_List = new ArrayMap<>();
        ArrayList<Map<Integer,Integer>> maps = new ArrayList<Map<Integer, Integer>>();*/
        int temp;
        String temp_process;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - (i + 1); j++) {
                if (AT_Array[j] > AT_Array[j + 1]) {
                    temp = AT_Array[j];
                    AT_Array[j] = AT_Array[j + 1];
                    AT_Array[j + 1] = temp;

                    temp = BT_Array[j];
                    BT_Array[j] = BT_Array[j + 1];
                    BT_Array[j + 1] = temp;

                    temp_process = Process_Array[j];
                    Process_Array[j] = Process_Array[j + 1];
                    Process_Array[j + 1] = temp_process;
                }
            }
        }
        System.arraycopy(BT_Array, 0, rem_BT, 0, n);
        System.arraycopy(AT_Array, 0, AT_COPY, 0, n);

        CPU_Time_Array.add(AT_COPY[0]);
        int Min_At = AT_COPY[0];
        int st=0;
        int x=0;

        int count_process_compl = 0;
        boolean process = true;
        while (process) {

            int c = n;
            for (int i = n - 1; i >= 0; i--) {
                if (st >= AT_COPY[i] && f[i] == 0) {
                    c = i;
                    //Taking   position given
                    POSITION_ARRAYLIST.add(c);
                    Log.d(TAG, "C from for: " + c + " Process name = " + Process_Array[c] + " Has BT = " + rem_BT[c]);
                }
            }
            //Taking values of AT value of position given
            for (int i = 0; i < POSITION_ARRAYLIST.size(); i++) {
                temp_List.add(AT_COPY[POSITION_ARRAYLIST.get(i)]);
            }
            for (int i = 0; i <  POSITION_ARRAYLIST.size(); i++) {
                for (int j = 0; j <  POSITION_ARRAYLIST.size() - (i + 1); j++) {
                    if (temp_List.get(j) > temp_List.get(j+1)) {
                        Collections.swap(temp_List,j,j+1);
                        Collections.swap(POSITION_ARRAYLIST,j,j+1);
                    }
                }
            }
            Log.d(TAG, "RoundRobin_Function:  POSITION_ARRAYLIST" + POSITION_ARRAYLIST);
            Log.d(TAG, "RoundRobin_Function:  temp_List" + temp_List);
            if (c == n) {
                st++;

                if (x < 1 && st > AT_COPY[0]) {

                    Log.e(TAG, "SYSTEM TIME" + st);

                    CPU_Process_Array.add(" // ");
                    Log.d(TAG, "CPU_Process_Array: " + CPU_Process_Array);
                }
                x++;
            } else {

                if (x > 1 && st >Min_At ) {
                    CPU_Time_Array.add(st);
                    Log.d(TAG, "CPU_Time_Array: CHange  due to // " + CPU_Time_Array);
                }
                x = 0;

                for (int i = 0; i < POSITION_ARRAYLIST.size(); i++) {

                    if (rem_BT[POSITION_ARRAYLIST.get(i)] >= TimeQuantum) {
                        st += TimeQuantum;
                        rem_BT[POSITION_ARRAYLIST.get(i)] -= TimeQuantum;
                        AT_COPY[POSITION_ARRAYLIST.get(i)] = st;
                        Log.d(TAG, "RoundRobin_Function: rem_BT "+rem_BT[POSITION_ARRAYLIST.get(i)]);
                    } else {
                        if (rem_BT[POSITION_ARRAYLIST.get(i)] < TimeQuantum && rem_BT[POSITION_ARRAYLIST.get(i)] != 0) {
                            st += rem_BT[POSITION_ARRAYLIST.get(i)];
                            rem_BT[POSITION_ARRAYLIST.get(i)] = 0;
                            Log.d(TAG, "RoundRobin_Function: rem_BT "+rem_BT[POSITION_ARRAYLIST.get(i)]);
                        }
                    }

                    Log.e(TAG, "SYSTEM TIME" + st);
                    CPU_Process_Array.add(Process_Array[POSITION_ARRAYLIST.get(i)]);
                    Log.d(TAG, "");
                    CPU_Time_Array.add(st);
                    Log.d(TAG, "");
                    if (rem_BT[POSITION_ARRAYLIST.get(i)] == 0) {
                        CT_Array[POSITION_ARRAYLIST.get(i)] = st;

                        Log.d(TAG, "CT_Array" + CT_Array[POSITION_ARRAYLIST.get(i)]);

                        f[POSITION_ARRAYLIST.get(i)] = 1;
                        count_process_compl++;
                        Log.d(TAG, "NO of process completed " + count_process_compl);
                    }
                    POSITION_ARRAYLIST.clear();
                    temp_List.clear();
                }
            }
            if (count_process_compl == n) {
                break;
            }
        }

        // Method to calculate average time
        // Calculate total waiting time and total turn
        // around time
        for ( int i = 0; i < n; i++) {
            TAT_Array[i] = CT_Array[i] - AT_Array[i];
            WT_Array[i] = TAT_Array[i] - BT_Array[i];
            AVG_WT = AVG_WT + WT_Array[i];
            AVG_TAT = AVG_TAT + TAT_Array[i];
        }
        AVG_TAT /= n;
        AVG_WT /= n;

        ansTable(Process_Array, AT_Array, BT_Array, TAT_Array, WT_Array, CT_Array, AVG_WT, AVG_TAT, Serial_No);
        cpuScheduling(CPU_Process_Array, CPU_Time_Array);
    }

    public void ansTable(String[] Process_Array, int[] AT_Array, int[] BT_Array, int[] TAT_Array, int[] WT_Array, int[] CT_Array, float AVG_WT, float AVG_TAT, int[] Serial_No) {
        int n = BT_Array.length;
        int[] tem = new int[n];
        String temp_String ;
        int temp_int ;

        for (int i = 0; i < n; i++) {
            tem[i] = Integer.parseInt(String.valueOf(Process_Array[i].charAt(1)));
            Log.d(TAG, "TEmp StringARRAY = " + tem[i]);
        }
        //Sorted Table
        for (int i=0;i<n;i++){
            for (int j=i+1;j<n;j++){
                if(tem[i]>tem[j]){
                    //Sorting
                    temp_int =tem[i];
                    tem[i]=tem[j];
                    tem[j] =temp_int;

                    //Arrival time
                    temp_int = AT_Array[i];
                    AT_Array[i] = AT_Array[j];
                    AT_Array[j] = temp_int;
                    Log.d(TAG, "TEmp SORTED = "+tem[i] );


                    //Burst time
                    temp_int = BT_Array[i];
                    BT_Array[i] = BT_Array[j];
                    BT_Array[j] = temp_int;

                    //Process names
                    temp_String = Process_Array[i];
                    Process_Array[i] = Process_Array[j];
                    Process_Array[j] = temp_String;

                    //Completion time
                    temp_int =CT_Array[i];
                    CT_Array[i]=CT_Array[j];
                    CT_Array[j] =temp_int;

                    //TAT time
                    temp_int = TAT_Array[i];
                    TAT_Array[i] = TAT_Array[j];
                    TAT_Array[j] = temp_int;

                    //WT time
                    temp_int = WT_Array[i];
                    WT_Array[i] = WT_Array[j];
                    WT_Array[j] = temp_int;

                }
            }
        }

        TableRow AnsTable_HeadingRow = findViewById(R.id.AnsTable_HeadingRow);
        AnsTable_HeadingRow.setVisibility(View.VISIBLE);

        //to clear the previous data if computed more than once
        if (tableOutput.getChildCount() > 1) {
            while (tableOutput.getChildCount() > 1) {
                tableOutput.removeView(tableOutput.getChildAt(tableOutput.getChildCount() - 1));

            }
        }
        tv_output.setVisibility(View.VISIBLE);

        tableOutput.setVisibility(View.VISIBLE);
        tableOutput.setColumnStretchable(0, true);
        tableOutput.setColumnStretchable(1, true);
        tableOutput.setColumnStretchable(2, true);
        tableOutput.setColumnStretchable(3, true);
        tableOutput.setColumnStretchable(4, true);
        tableOutput.setColumnStretchable(5, true);
        tableOutput.setStretchAllColumns(true);


        //Making output table
        Ans_tableRow = new TableRow(MainActivity.this);
        Ans_tableRow = new TableRow(MainActivity.this);
        AnsTable_Process = new TextView(MainActivity.this);
        AnsTable_AT = new TextView(MainActivity.this);
        AnsTable_BT = new TextView(MainActivity.this);
        AnsTable_TAT = new TextView(MainActivity.this);
        AnsTable_WT = new TextView(MainActivity.this);
        AnsTable_CT = new TextView(MainActivity.this);

        avgWT.setText(String.format("Average Waiting Time = %.2f " , AVG_WT ) );
        avgWT.setVisibility(View.VISIBLE);
        avgWT.setGravity(Gravity.CENTER);
        avgWT.setTextColor(Color.BLACK);
        avgWT.setTextSize(18);
        Log.d(TAG, "avgWT: " + avgWT.getText());

        avgTAT.setText(String.format("Average Turnaround Time = %.2f " , AVG_TAT ) );
        avgTAT.setVisibility(View.VISIBLE);
        avgTAT.setTextColor(Color.BLACK);
        avgTAT.setGravity(Gravity.CENTER);
        avgTAT.setTextSize(18);
        Log.d(TAG, "avgTAT: " + avgTAT.getText());


        for (int i = 0; i < n; i++) {
            int idVal = i + 1;
            int textsize=16;

            TextView AnsTable_Process = new TextView(MainActivity.this);
            AnsTable_Process.setId(AnsTable_Process_ID + idVal);
            Log.d(TAG, "AnsTable_Process: " + AnsTable_Process_ID + idVal);
            AnsTable_Process.setText(Process_Array[i]);
            AnsTable_Process.setGravity(Gravity.CENTER);
            AnsTable_Process.setTextSize(textsize);
            AnsTable_Process.setTextColor(Color.BLACK);
            AnsTable_Process.setTypeface(null, Typeface.BOLD);
            Log.d(TAG, "AnsTable_Process: " + AnsTable_Process.getText());

            TextView AnsTable_AT = new TextView(MainActivity.this);
            AnsTable_AT.setId(AnsTable_AT_ID + idVal);
            AnsTable_AT.setText(AT_Array[i] + "");
            AnsTable_AT.setGravity(Gravity.CENTER);
            AnsTable_AT.setTextSize(textsize);
            AnsTable_AT.setTextColor(Color.BLACK);
            Log.d(TAG, "AnsTable_AT: " + AnsTable_AT.getText());

            TextView AnsTable_BT = new TextView(MainActivity.this);
            AnsTable_BT.setId(AnsTable_BT_ID + idVal);
            AnsTable_BT.setText(BT_Array[i] + "");
            AnsTable_BT.setGravity(Gravity.CENTER);
            AnsTable_BT.setTextSize(textsize);
            AnsTable_BT.setTextColor(Color.BLACK);
            Log.d(TAG, "AnsTable_BT: " + AnsTable_BT.getText());

            TextView AnsTable_CT = new TextView(MainActivity.this);
            AnsTable_CT.setId(AnsTable_CT_ID + idVal);
            AnsTable_CT.setText(CT_Array[i] + "");
            AnsTable_CT.setGravity(Gravity.CENTER);
            AnsTable_CT.setTextSize(textsize);
            AnsTable_CT.setTextColor(Color.BLACK);
            Log.d(TAG, "AnsTable_CT: " + AnsTable_CT.getText());


            TextView AnsTable_TAT = new TextView(MainActivity.this);
            AnsTable_TAT.setId(AnsTable_TAT_ID + idVal);
            AnsTable_TAT.setText(TAT_Array[i] + "");
            AnsTable_TAT.setGravity(Gravity.CENTER);
            AnsTable_TAT.setTextSize(textsize);
            AnsTable_TAT.setTextColor(Color.BLACK);
            Log.d(TAG, "AnsTable_TAT: " + AnsTable_TAT.getText());

            TextView AnsTable_WT = new TextView(MainActivity.this);
            AnsTable_WT.setId(AnsTable_WT_ID + idVal);
            AnsTable_WT.setText(WT_Array[i] + "");
            AnsTable_WT.setGravity(Gravity.CENTER);
            AnsTable_WT.setTextSize(textsize);
            AnsTable_WT.setTextColor(Color.BLACK);
            Log.d(TAG, "AnsTable_WT: " + AnsTable_WT.getText());

            //Setting BackGround
            if(i%2==0) {
                AnsTable_Process.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_bg));
                AnsTable_AT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_bg));
                AnsTable_BT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_bg));
                AnsTable_CT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_bg));
                AnsTable_TAT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_bg));
                AnsTable_WT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_bg));
            }
            else{
                AnsTable_Process.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_gray));
                AnsTable_AT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_gray));
                AnsTable_BT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_gray));
                AnsTable_CT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_gray));
                AnsTable_TAT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_gray));
                AnsTable_WT.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_gray));
            }

            TableRow Ans_tableRow = new TableRow(MainActivity.this);
            Ans_tableRow.setId(ANS_TABLE_ROW_ID + idVal);
            int padding = 15;
            //Ans Padding:
            AnsTable_AT.setPadding(0,padding,0,padding);
            AnsTable_Process.setPadding(0,padding,0,padding);
            AnsTable_BT.setPadding(0,padding,0,padding);
            AnsTable_CT.setPadding(0,padding,0,padding);
            AnsTable_TAT.setPadding(0,padding,0,padding);
            AnsTable_WT.setPadding(0,padding,0,padding);

            // Ans_tableRow.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_row_bg));
            //  Ans_tableRow.setPadding(0,10,0,10);
            Ans_tableRow.addView(AnsTable_Process);
            Ans_tableRow.addView(AnsTable_AT);
            Ans_tableRow.addView(AnsTable_BT);
            Ans_tableRow.addView(AnsTable_CT);
            Ans_tableRow.addView(AnsTable_TAT);
            Ans_tableRow.addView(AnsTable_WT);
            tableOutput.addView(Ans_tableRow);
        }

    }

    public void cpuScheduling(ArrayList<String> CPU_Process_Array, ArrayList<Integer> CPU_Time_Array) {

        int n= CPU_Process_Array.size();
        Log.d(TAG, "Lenght of p: " + n);
        int CPU_HEADER_ID = 7000;
        int CPU_Time_ID = 8000;
        tv_gantChart.setVisibility(View.VISIBLE);
        Row_CPU = new TableRow(MainActivity.this);
        Row_CPU_time= new TableRow(MainActivity.this);

        tableCPU.setVisibility(View.VISIBLE);
        //to clear the previous data if computed more than once
        tableCPU.removeAllViews();
        tableCPU.setStretchAllColumns(true);

        Row_CPU.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_gantt_chart));

        for (int i = 0; i < n; i++) {
            int idVal = i + 1;
            TextView CPU_Process = new TextView(MainActivity.this);

            CPU_Process.setId(CPU_HEADER_ID + idVal);
            CPU_Process.setText(CPU_Process_Array.get(i));
            CPU_Process.setGravity(Gravity.CENTER);
            CPU_Process.setTextSize(45);
            CPU_Process.setTextColor(Color.BLACK);

            Row_CPU.addView(CPU_Process);
            Log.d(TAG, "CPU: " + CPU_Process.getText());
            if(i!=n-1) {
                CPU_Process.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_gantt_chart));
                CPU_Process.setGravity(Gravity.CENTER);
            }
            Log.d(TAG, "CPU: " + CPU_Process.getText());
        }
        for (int i = 0; i <=n; i++) {
            int idVal = i + 1;
            TextView CPU_Process_Time = new TextView(MainActivity.this);

            CPU_Process_Time.setId(CPU_Time_ID + idVal);
            CPU_Process_Time.setText(CPU_Time_Array.get(i) +"");

            CPU_Process_Time.setTextSize(15);
            CPU_Process_Time.setTextColor(Color.BLACK);
            // CPU_Process_Time.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.table_ans_cell_bg));
            Row_CPU_time.addView(CPU_Process_Time);

            if(i!=n){
                CPU_Process_Time.setGravity(Gravity.START);
            }
            else{
                CPU_Process_Time.setGravity(Gravity.END);
            }
            Log.d(TAG, "CPU: " + CPU_Process_Time.getText());
        }
        tableCPU.addView(Row_CPU);
        tableCPU.addView(Row_CPU_time);
        ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
        // sv.smoothScrollTo(0, R.id.tableCPU);
        sv.fullScroll(View.FOCUS_DOWN);
        //sv.scrollTo(0, R.id.tableCPU);
        //  sv.scrollTo(0, View.FOCUS_DOWN);
        //   sv.fullScroll(View.FOCUS_DOWN);
        sv.scrollTo(0,sv.getBottom());
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.ganttchart);
        tableCPU.setAnimation(animation);

    }

}