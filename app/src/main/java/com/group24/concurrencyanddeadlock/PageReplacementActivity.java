package com.group24.concurrencyanddeadlock;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PageReplacementActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerAlgorithm;
    private TableLayout tableLayout;
    private String currentSelected = "FIFO";
    private EditText refNumber, frame;
    private ConstraintLayout resultContainer;
    private TextView tvPages, tvFrames, tvHits, tvFaults, tvHitRatio, tvMissRation, tvErrorReference, tvErrorFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_replacement);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("\tPAGE REPLACEMENT");
        actionBar.setDisplayHomeAsUpEnabled(true);
        init();
        List<String> algorithms = new ArrayList<>();
        algorithms.add("FIFO");
        algorithms.add("LIFO");
        algorithms.add("MRU");
        algorithms.add("LRU");
        algorithms.add("OPTIMAL");
        algorithms.add("RANDOM");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.style_spinner, algorithms);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerAlgorithm.setAdapter(arrayAdapter);

        spinnerAlgorithm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                resultContainer.setVisibility(View.GONE);
                currentSelected = adapterView.getItemAtPosition(i).toString();
                reset();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void init() {
        spinnerAlgorithm = findViewById(R.id.spinner_select_algorithm);
        tableLayout = findViewById(R.id.tableView);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        refNumber = findViewById(R.id.etReferenceNumber);
        frame = findViewById(R.id.etFrame);
        resultContainer = findViewById(R.id.resultContainer);
        tvPages = findViewById(R.id.tvPages);
        tvFrames = findViewById(R.id.tvFrames);
        tvHits = findViewById(R.id.tvHits);
        tvFaults = findViewById(R.id.tvFaults);
        tvHitRatio = findViewById(R.id.tvHitRatio);
        tvMissRation = findViewById(R.id.tvMissRation);
        tvErrorReference = findViewById(R.id.tvErrorReference);
        tvErrorFrame = findViewById(R.id.tvErrorFrame);
    }

    private void reset() {
        refNumber.setText("");
        frame.setText("");
        frame.clearFocus();
        refNumber.requestFocus();
        tvErrorFrame.setVisibility(View.GONE);
        tvErrorReference.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            try {
                if (!refNumber.getText().toString().isEmpty() && !frame.getText().toString().isEmpty()) {
                    tvErrorReference.setVisibility(View.GONE);
                    tvErrorFrame.setVisibility(View.GONE);
                    String[] refNumbers = refNumber.getText().toString().split(",");
                    int[] reference = new int[refNumbers.length];
                    int frames = Integer.parseInt(frame.getText().toString());
                    for (int i = 0; i < refNumbers.length; i++) {
                        reference[i] = Integer.parseInt(refNumbers[i]);
                    }
                    tableLayout.removeAllViews();
                    hideKeyboardFrom(PageReplacementActivity.this, v);
                    switch (currentSelected) {
                        case "FIFO":
                            FIFO(reference, frames);
                            break;
                        case "LIFO":
                            LIFO(reference, frames);
                            break;
                        case "MRU":
                            MRU(reference, frames);
                            break;
                        case "LRU":
                            LRU(reference, frames);
                            break;
                        case "OPTIMAL":
                            OPTIMAL(reference, frames);
                            break;
                        case "RANDOM":
                            RANDOM(reference, frames);
                            break;
                    }

                } else {
                    if (refNumber.getText().toString().isEmpty())
                        tvErrorReference.setVisibility(View.VISIBLE);
                    if (frame.getText().toString().isEmpty())
                        tvErrorFrame.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                tvErrorReference.setVisibility(View.VISIBLE);
            }

        }
    }

    private void FIFO(int[] reference, int frames) {
        int pointer = 0, hit = 0, fault = 0, ref_len = reference.length;
        int[] buffer;
        int[][] mem_layout;

        mem_layout = new int[ref_len][frames];
        buffer = new int[frames];

        for (int j = 0; j < frames; j++)
            buffer[j] = -1;

        for (int i = 0; i < ref_len; i++) {
            int search = -1;
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == reference[i]) {
                    search = j;
                    hit++;
                    break;
                }
            }
            if (search == -1) {
                buffer[pointer] = reference[i];
                fault++;
                pointer++;
                if (pointer == frames)
                    pointer = 0;
            }
            for (int j = 0; j < frames; j++)
                mem_layout[i][j] = buffer[j];
        }

        displayResult(reference, frames, hit, fault, mem_layout);
    }

    private void LIFO(int[] reference, int frames) {
        int pointer = 0, hit = 0, fault = 0, ref_len = reference.length;
        boolean isAscending = true;
        int[] buffer;
        int[][] mem_layout;

        mem_layout = new int[ref_len][frames];
        buffer = new int[frames];

        for (int j = 0; j < frames; j++)
            buffer[j] = -1;

        for (int i = 0; i < ref_len; i++) {
            int search = -1;
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == reference[i]) {
                    search = j;
                    hit++;
                    break;
                }
            }
            if (search == -1) {
                buffer[pointer] = reference[i];
                fault++;
                if (isAscending)
                    pointer++;
                else
                    pointer--;
                if (pointer == frames) {
                    pointer = frames - 1;
                    isAscending = false;
                } else if (pointer == -1) {
                    pointer = 0;
                    isAscending = true;
                }
            }
            for (int j = 0; j < frames; j++)
                mem_layout[i][j] = buffer[j];
        }

        displayResult(reference, frames, hit, fault, mem_layout);
    }

    private void MRU(int[] reference, int frames) {
        int pointer = 0, hit = 0, fault = 0, ref_len = reference.length;
        int[] buffer;
        int[][] mem_layout;

        mem_layout = new int[ref_len][frames];
        buffer = new int[frames];

        for (int j = 0; j < frames; j++)
            buffer[j] = -1;

        for (int i = 0; i < ref_len; i++) {
            int search = -1;
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == reference[i]) {
                    search = j;
                    hit++;
                    break;
                }
            }
            if (search == -1) {
                if (pointer != frames) {
                    buffer[pointer] = reference[i];
                    fault++;
                    pointer++;
                } else {
                    for (int k = 0; k < buffer.length; k++) {
                        if (buffer[k] == reference[i - 1]) {
                            buffer[k] = reference[i];
                            fault++;
                            break;
                        }
                    }
                }
            }
            for (int j = 0; j < frames; j++)
                mem_layout[i][j] = buffer[j];
        }

        displayResult(reference, frames, hit, fault, mem_layout);
    }

    private void LRU(int[] reference, int frames) {
        int pointer = 0, hit = 0, fault = 0, ref_len = reference.length;
        int[] buffer;
        int[][] mem_layout;

        mem_layout = new int[ref_len][frames];
        buffer = new int[frames];

        for (int j = 0; j < frames; j++)
            buffer[j] = -1;

        for (int i = 0; i < ref_len; i++) {
            int search = -1;
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == reference[i]) {
                    search = j;
                    hit++;
                    break;
                }
            }
            if (search == -1) {
                if (pointer != frames) {
                    buffer[pointer] = reference[i];
                    fault++;
                    pointer++;
                } else {
                    int rp = i - 1;
                    int current = 0;
                    for (int p = 0; p < buffer.length; p++) {
                        for (int l = i - 1; l >= 0; l--) {
                            if (buffer[p] == reference[l]) {
                                if (rp > l) {
                                    rp = l;
                                    current = p;
                                }
                                break;
                            }
                        }
                    }
                    buffer[current] = reference[i];
                    fault++;
                }
            }
            for (int j = 0; j < frames; j++)
                mem_layout[i][j] = buffer[j];
        }

        displayResult(reference, frames, hit, fault, mem_layout);
    }


    private void OPTIMAL(int[] reference, int frames) {
        int pointer = 0, hit = 0, fault = 0, ref_len = reference.length;
        int[] buffer;
        int[][] mem_layout;
        boolean isFull = false;

        mem_layout = new int[ref_len][frames];
        buffer = new int[frames];

        for (int j = 0; j < frames; j++)
            buffer[j] = -1;

        for (int i = 0; i < ref_len; i++) {
            int search = -1;
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == reference[i]) {
                    search = j;
                    hit++;
                    break;
                }
            }

            if (search == -1) {
                if (isFull) {
                    int index[] = new int[frames];
                    boolean index_flag[] = new boolean[frames];
                    for (int j = i + 1; j < ref_len; j++) {
                        for (int k = 0; k < frames; k++) {
                            if ((reference[j] == buffer[k]) && (index_flag[k] == false)) {
                                index[k] = j;
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }
                    int max = index[0];
                    pointer = 0;
                    if (max == 0) {
                        max = 200;
                    }

                    for (int j = 0; j < frames; j++) {
                        if (index[j] == 0) {
                            index[j] = 200;
                        }

                        if (index[j] > max) {
                            max = index[j];
                            pointer = j;
                        }
                    }
                }
                buffer[pointer] = reference[i];
                fault++;
                if (!isFull) {
                    pointer++;
                    if (pointer == frames) {
                        pointer = 0;
                        isFull = true;
                    }
                }
            }

            for (int j = 0; j < frames; j++) {
                mem_layout[i][j] = buffer[j];
            }
        }

        displayResult(reference, frames, hit, fault, mem_layout);
    }

    private void RANDOM(int[] reference, int frames) {
        int pointer = 0, hit = 0, fault = 0, ref_len = reference.length;
        int[] buffer;
        int[][] mem_layout;

        mem_layout = new int[ref_len][frames];
        buffer = new int[frames];

        for (int j = 0; j < frames; j++)
            buffer[j] = -1;

        for (int i = 0; i < ref_len; i++) {
            int search = -1;
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == reference[i]) {
                    search = j;
                    hit++;
                    break;
                }
            }
            if (search == -1) {
                if (pointer != frames) {
                    buffer[pointer] = reference[i];
                    pointer++;
                } else {
                    Random rand = new Random();
                    int randIndex = rand.nextInt(frames);
                    buffer[randIndex] = reference[i];
                }
                fault++;

            }
            for (int j = 0; j < frames; j++)
                mem_layout[i][j] = buffer[j];
        }

        displayResult(reference, frames, hit, fault, mem_layout);
    }


    void displayResult(int[] reference, int frames, int hit, int fault, int[][] mem_layout) {
        int ref_len = reference.length;
        resultContainer.setVisibility(View.VISIBLE);
        TableRow tr1 = new TableRow(this);
        tr1.addView(getColumnTextView("Reference"));
        for (int value : reference) {
            tr1.addView(getColumnTextView(String.valueOf(value)));
        }
        tableLayout.addView(tr1);

        for (int i = 0; i < frames; i++) {
            TableRow tr = new TableRow(this);
            tr.addView(getColumnTextView("Frame " + (i + 1)));
            for (int j = 0; j < ref_len; j++) {
                if (mem_layout[j][i] != -1)
                    tr.addView(getTextView(String.valueOf(mem_layout[j][i])));
                else
                    tr.addView(getTextView(""));
            }
            tableLayout.addView(tr);
        }

        tvHits.setText(getString(R.string.text_hit, hit));
        tvFaults.setText(getString(R.string.text_fault, fault));
        tvHitRatio.setText(getString(R.string.text_hit_ration, ((float) hit / ref_len)));
        tvFrames.setText(getString(R.string.text_frames, frames));
        tvPages.setText(getString(R.string.text_pages, reference.length));
        tvMissRation.setText(getString(R.string.text_miss_ration, (float) (ref_len - hit) / ref_len));
    }

    private TextView getTextView(String value) {
        TextView tv = new TextView(this);
        tv.setText(value);
        tv.setPadding(40, 40, 40, 40);
        tv.setBackgroundResource(R.drawable.cell_shape);
        return tv;
    }

    private TextView getColumnTextView(String value) {
        TextView tv = new TextView(this);
        tv.setText(value);
        tv.setPadding(40, 40, 40, 40);
        tv.setBackgroundResource(R.drawable.cell_header);
        return tv;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}