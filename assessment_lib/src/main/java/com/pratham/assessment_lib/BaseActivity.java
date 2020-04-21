package com.pratham.assessment_lib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;

import com.pratham.assessment_lib.Utility.Assessment_Utility;

import org.androidannotations.annotations.EActivity;

@EActivity(resName = "activity_base")
public class BaseActivity extends AppCompatActivity {
    public static String assessPath = "";
    //public static int defaultColor;
    public static ColorStateList colorStateList;
    public static boolean muteFlg = false;
    private static AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assessPath= Assessment_Utility.getInternalPath(this);

      //  Assessment_Utility.setDefaultColor(defaultColor);
    }
    public boolean isPermissionsGranted(Context context, String permissions[]) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        boolean granted = true;

        for (String permission : permissions) {
            if (!(ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED))
                granted = false;
        }

        return granted;
    }

    public static void setMute(int m) {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            if (m == 1 && !muteFlg) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
                muteFlg = true;
            } else if (m == 0 && muteFlg) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
                muteFlg = false;
            }
        } else {

            if (m == 1 && !muteFlg) {
//            audioManager.adjustVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
//                audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
//                audioManager.setStreamMute(AudioManager.STREAM_ALARM, true);
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
//                audioManager.setStreamMute(AudioManager.STREAM_RING, true);
//                audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                muteFlg = true;
            } else if (m == 0 && muteFlg) {
//            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
//                audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
//                audioManager.setStreamMute(AudioManager.STREAM_ALARM, false);
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
//                audioManager.setStreamMute(AudioManager.STREAM_RING, false);
//                audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
                muteFlg = false;
            }
        }
    }
}
