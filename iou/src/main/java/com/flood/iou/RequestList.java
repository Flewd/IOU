package com.flood.iou;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.parse.ParseUser;

public class RequestList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                LogoutButtonPressed();

            }
        });

    }

    private void LogoutButtonPressed(){

        ParseUser currentUser = ParseUser.getCurrentUser();

        ParseUser.logOut();

        int BREAKHERE = 0;

        currentUser = ParseUser.getCurrentUser();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
