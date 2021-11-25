package com.group24.concurrencyanddeadlock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class AlgosActivity extends AppCompatActivity {
    TextView tv;
    Button b;
    ScrollView sview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algos);
        int a= getIntent().getIntExtra("key1",0);
        tv=findViewById(R.id.textView10);
        b=findViewById(R.id.button);
        sview=findViewById(R.id.scroll);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);


        if(a==1){
            setTitle("BANKER'S ALGORITHM");
            String temp=getResources().getString(R.string.banker_theory);
            tv.setText(temp);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = "7gMLNiEz3nw";

                    youTubePlayer.loadVideo(videoId, 0);
                    youTubePlayer.pause();
                }
            });

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
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = "TrV_dOX_YHw";
                    youTubePlayer.loadVideo(videoId, 0);
                    youTubePlayer.pause();
                }
            });
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AlgosActivity.this, LockVariable.class);
                    startActivity(intent);
                }
            });
        }
        if(a == 3){
            setTitle("OSTRICH METHOD");
            String temp=getResources().getString(R.string.ostrich_theory);
            tv.setText(temp);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = "CLLgtpnRWvc";
                    youTubePlayer.loadVideo(videoId, 0);
                    youTubePlayer.pause();
                }
            });
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AlgosActivity.this, OstrichNew.class);
                    startActivity(intent);
                }
            });
        }
        if(a == 4){
            setTitle("BINARY SEMAPHORE");
            String temp=getResources().getString(R.string.binary_theory);
            tv.setText(temp);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = "l5-3mbBV1BQ";
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });
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
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = "fnqo3SKuxJY";
                    youTubePlayer.loadVideo(videoId, 0);
                    youTubePlayer.pause();
                }
            });
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