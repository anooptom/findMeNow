package com.project.playsound;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.media.RingtoneManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), ringtoneUri);

        if (mediaPlayer != null) {
            try {
                // Create audio attributes to set the playback properties
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();

                // Set the audio attributes for the media player
                mediaPlayer.setAudioAttributes(audioAttributes);

                // Set the volume to play the media sound even in silent mode
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

                // Start playing the media sound
                mediaPlayer.start();
            } catch (SecurityException e) {
                e.printStackTrace();
                // Handle the security exception
            } catch (NullPointerException e) {
                e.printStackTrace();
                // Handle the null pointer exception
            }
        }
    }
}
