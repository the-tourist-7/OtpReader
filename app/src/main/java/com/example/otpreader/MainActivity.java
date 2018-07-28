package com.example.otpreader;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;

public class MainActivity extends AppCompatActivity implements OTPListener {
    EditText editText;
    TextView textView, textViewWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dexter.withActivity(this).withPermission(Manifest.permission.READ_SMS).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Toast.makeText(MainActivity.this, "SMS Permission Granted !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(MainActivity.this, "SMS Permission Denied !", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "You Need To Grant the Permission !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }).check();
        OtpReader.bind(this,"9173456076");

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        textViewWaiting = findViewById(R.id.textViewWaiting);

    }

    @Override
    public void otpReceived(String messageText) {
        editText.setText(messageText);
        textViewWaiting.setVisibility(View.GONE);
    }
}
