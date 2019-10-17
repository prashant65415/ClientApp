package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*Function called when submit button is clicked*/
    public void submitClick(View v)
    {
        EditText tv = findViewById(R.id.editText);

        //Send intent with input
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.setClassName("com.example.serverapp", "com.example.serverapp.MainActivity");
        i.putExtra("Id", tv.getText().toString());
        i.setType("text/plain");
        startActivityForResult(i, REQUEST_CODE);
    }

    /* Result from other application */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                Toast.makeText(getApplicationContext(),data.getExtras().getString("Res"),Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

