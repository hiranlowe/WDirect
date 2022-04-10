package com.fition.wdirect;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TalkingActivity extends AppCompatActivity implements View.OnClickListener {

    Button talking_btn;
    private MicRecorder micRecorder;
    OutputStream outputStream;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        talking_btn = (Button)findViewById(R.id.start_talking);
        talking_btn.setOnClickListener(this);


        Socket socket = ConnectionHandler.getSocket();

        try {
            outputStream = socket.getOutputStream();
            Log.e("OUTPUT_SOCKET", "SUCCESS");
            startService(new Intent(getApplicationContext(), AudioStreamingService.class));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_talking:
                if(talking_btn.getText().toString().equals("TALK")){
                    // stream audio
                    talking_btn.setText("OVER");
                    talking_btn.setBackgroundTintList(getResources().getColorStateList(R.color.yellow_1));
                    micRecorder = new MicRecorder(TalkingActivity.this);
                    t = new Thread(micRecorder);
                    if(micRecorder != null) {
                        MicRecorder.keepRecording = true;
                    }
                    t.start();




                }else if(talking_btn.getText().toString().equals("OVER")){
                    talking_btn.setText("TALK");
                    talking_btn.setBackgroundTintList(getResources().getColorStateList(R.color.blue_1));
                    if(micRecorder != null) {
                        MicRecorder.keepRecording = false;
                    }

                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(micRecorder != null) {
            MicRecorder.keepRecording = false;
        }
    }
}
