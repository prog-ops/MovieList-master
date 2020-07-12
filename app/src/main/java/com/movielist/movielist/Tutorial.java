package com.movielist.movielist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.movielist.movielist.feature.search.SearchActivity;

/**
 Sen 31-12-18 132225
 */

public class Tutorial extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        Thread tutorial = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {

                } finally {
                    Intent intent = new Intent(
                            Tutorial.this,
                            SearchActivity.class
                    );
                    startActivity(intent);
                }
            }
        };
        tutorial.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
