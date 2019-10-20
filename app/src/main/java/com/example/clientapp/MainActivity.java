package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ComponentName;
import android.os.Build;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Messenger;

public class MainActivity extends AppCompatActivity {

    public static boolean hasPermissions(Context context, String... permissions) {
        //Check for permissions
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public  boolean setupPermissions() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.FOREGROUND_SERVICE
        };

        //Check and request for permissions
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Register receiver
        registerReceiver(broadcastReceiver, new IntentFilter("INTENT_RESPONSE"));
    }

    //Broadcast Receiver for response from server
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Enable submit button once we get the result
            Button submit = findViewById(R.id.button);
            submit.setEnabled(true);

            //Show alert with response
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Response");
            alertDialog.setMessage("Response from Server is: "+intent.getExtras().get("Res"));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    };

    public void submitClick(View v)
    {
        EditText tv = findViewById(R.id.editText);

        //Check and request for permissions
        setupPermissions();

        //Start service from server app
        Intent i = new Intent();
        i.setComponent(new ComponentName("com.example.serverapp", "com.example.serverapp.MyService"));
        i.putExtra("Id", tv.getText().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ComponentName c = getApplicationContext().startForegroundService(i);
        } else {
            ComponentName c = getApplicationContext().startService(i);
        }

        //Disable submit button when user click submit
        Button submit = findViewById(R.id.button);
        submit.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Unregister receiver
        unregisterReceiver(broadcastReceiver);
    }
}
