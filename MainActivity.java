package org.onursert.audiorecorder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Request Record Audio Permission for Android 6+
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    private AudioRecordPlay audioRecordPlay;

    private Button audioRecordButton;
    private Button audioPlayButton;

    boolean startRecordingBool = true;
    boolean startPlayingBool = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        String filePath = getExternalCacheDir().getAbsolutePath() + "/audiorecordtest.3gp";
        audioRecordPlay = new AudioRecordPlay(filePath);

        audioRecordButton = (Button) findViewById(R.id.audioRecordButton);
        audioRecordButton.setOnClickListener(this);

        audioPlayButton = (Button) findViewById(R.id.audioPlayButton);
        audioPlayButton.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (audioRecordPlay.mediaRecorder != null) {
            audioRecordPlay.mediaRecorder.release();
            audioRecordPlay.mediaRecorder = null;
        }

        if (audioRecordPlay.mediaPlayer != null) {
            audioRecordPlay.mediaPlayer.release();
            audioRecordPlay.mediaPlayer = null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.audioRecordButton:
                audioRecordPlay.onRecord(startRecordingBool);
                if (startRecordingBool) {
                    audioRecordButton.setText("Stop Recording");
                } else {
                    audioRecordButton.setText("Start Recording");
                }
                startRecordingBool = !startRecordingBool;

            case R.id.audioPlayButton:
                audioRecordPlay.onPlay(startPlayingBool);
                if (startPlayingBool) {
                    audioPlayButton.setText("Stop Playing");
                } else {
                    audioPlayButton.setText("Start Playing");
                }
                startPlayingBool = !startPlayingBool;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) {
            finish();
        }
    }
}
