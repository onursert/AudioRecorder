package org.onursert.audiorecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.IOException;

public class AudioRecordPlay {

    public MediaRecorder mediaRecorder = null;
    public MediaPlayer mediaPlayer = null;

    private String filePath;

    public AudioRecordPlay(String filePath) {
        this.filePath = filePath;
    }

    public void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    public void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(filePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            System.out.println("Prepare Failed");
        }

        mediaRecorder.start();
    }

    public void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    public void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    public void startPlaying() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            System.out.println("Prepare Failed");
        }
    }

    public void stopPlaying() {
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
