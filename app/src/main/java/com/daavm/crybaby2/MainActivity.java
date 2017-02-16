package com.daavm.crybaby2;

import android.Manifest;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.hue.sdk.heartbeat.PHHeartbeatManager;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder recorder = null;
    private String outputFile = null;
    private double db = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/test2.mp4";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recorder = new MediaRecorder();
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 200);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(mFileName);
        try {
            recorder.prepare();
            recorder.getMaxAmplitude();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Handler h = new Handler();
        final int delay = 1000; //milliseconds
        for (int ii = 0; ii < 1; ii++) {
            h.postDelayed(new Runnable() {
                public void run() {
                    //do something
                    double amplitude = recorder.getMaxAmplitude();

                    TextView text = (TextView) findViewById(R.id.text);
                    db = 20 * Math.log10(amplitude/ 0.68);


                    text.setText(db + "");

                    h.postDelayed(this, delay);

                }
            }, delay);


        }
    }




}

