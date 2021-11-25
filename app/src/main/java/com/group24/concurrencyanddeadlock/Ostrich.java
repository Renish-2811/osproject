package com.group24.concurrencyanddeadlock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;


public class Ostrich extends AppCompatActivity {
TextView infinite;
ImageButton war;
ScrollView vs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ostrich);
        infinite=findViewById(R.id.infinite);
        war=findViewById(R.id.war);
        vs=findViewById(R.id.vs);
        setTitle("IMPLEMENTATION OF ALGORITHM");
        war.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0; i<50; i++){
                    String tmp="You need to restart the app to remove this deadlock ";
                    infinite.append(tmp+"\n");
                    vs.fullScroll(View.FOCUS_DOWN);
                }



                // Create the object of
                // AlertDialog Builder class
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(Ostrich.this);

                // Set the message show for the Alert time
                builder.setMessage("App Crashed due to deadlock!!!\nPlease restart the app.");

                // Set Alert Title
                builder.setTitle("WARNING!");

                // Set Cancelable false
                // for when the user clicks on the outside
                // the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name
                // OnClickListener method is use of
                // DialogInterface interface.

                builder
                        .setPositiveButton(
                                "Restart",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // When the user click yes button
                                        // then app will close
                                        Intent intent = new Intent(Ostrich.this, SplashScreen.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });


                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();
            }
        });
    }
}