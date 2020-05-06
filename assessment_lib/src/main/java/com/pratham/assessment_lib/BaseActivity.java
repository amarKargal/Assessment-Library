package com.pratham.assessment_lib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;


import com.google.android.material.snackbar.Snackbar;
import com.pratham.assessment_lib.Utility.Assessment_Constants;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

@EActivity(resName = "activity_base")
public class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
  //  public static String assessPath = "";
    //public static int defaultColor;
    public static ColorStateList colorStateList;
    public static boolean muteFlg = false;
    private static AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    assessPath= Assessment_Utility.getInternalPath(this);

      //  Assessment_Utility.setDefaultColor(defaultColor);
    }

    protected void requestRunTimePermissions(final Activity activity, final String [] permissions, final int customPermissionConstant){
        if(permissions.length==1){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0])){

                Snackbar.make(findViewById(android.R.id.content),"App needs permission to work",Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(activity,permissions,customPermissionConstant);
                            }
                        }).show();
            }else {
                ActivityCompat.requestPermissions(this,new String[]{permissions[0]},customPermissionConstant);
            }
        }else if(permissions.length>1 && customPermissionConstant== Assessment_Constants.LIBRARY_PERMISSIONS){
            final List<String> deniedPermissions=new ArrayList<String>();

            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(this,permission)== PackageManager.PERMISSION_DENIED){
                    deniedPermissions.add(permission);
                }
            }
            if(deniedPermissions.size()==1){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,deniedPermissions.get(0))){

                    Snackbar.make(findViewById(android.R.id.content),"App needs permission to work",Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String [] temp=deniedPermissions.toArray(new String[deniedPermissions.size()]);
                                    ActivityCompat.requestPermissions(activity,temp,customPermissionConstant);
                                }
                            }).show();
                }else {
                    String [] temp=deniedPermissions.toArray(new String[deniedPermissions.size()]);
                    ActivityCompat.requestPermissions(activity,temp,customPermissionConstant);
                }
            }else if(deniedPermissions.size()>1){
                final String [] temp=deniedPermissions.toArray(new String[deniedPermissions.size()]);
                Snackbar.make(findViewById(android.R.id.content),"This functionality needs multiple app permissions",Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(activity,temp,customPermissionConstant);
                            }
                        }).show();
            }
        }
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



    protected boolean checkWhetherAllPermissionsPresentForPhotoTagging(String [] permissionsNeeded){
        for(String permission: permissionsNeeded){
            if(ActivityCompat.checkSelfPermission(this,permission)==PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }

    protected String[] getDeniedPermissionsAmongPhototaggingPermissions(String [] permissionsNeeded){
        String [] deniedPermissionsArray;
        final List<String> deniedPermissions=new ArrayList<String>();
        for(String permission: permissionsNeeded){
            if(ActivityCompat.checkSelfPermission(this,permission)==PackageManager.PERMISSION_DENIED){
                deniedPermissions.add(permission);
            }
        }
        deniedPermissionsArray=deniedPermissions.toArray(new String[deniedPermissions.size()]);
        return deniedPermissionsArray;
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
