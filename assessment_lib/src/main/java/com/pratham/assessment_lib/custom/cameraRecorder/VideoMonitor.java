package com.pratham.assessment_lib.custom.cameraRecorder;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.widget.FrameLayout;

import java.io.File;

public class VideoMonitor {
    static GLSurfaceView sampleGLView;
    static CameraRecorder cameraRecorder;
    static FrameLayout mframeLayout;

    public static void initCamera(Activity activity, FrameLayout frameLayout) {
        mframeLayout = frameLayout;
        sampleGLView = new GLSurfaceView(activity.getApplicationContext());
        // FrameLayout frameLayout = findViewById(R.id.texture_view);
        mframeLayout.addView(sampleGLView);
        cameraRecorder = new CameraRecorderBuilder(activity, sampleGLView)
                .lensFacing(LensFacing.FRONT)
                .build();
    }

    public static void startRecording(String path) {
        File sd = new File(Environment.getExternalStorageDirectory() + "/PrathamBackups/");
        if (!sd.exists())
            sd.mkdir();

        cameraRecorder.start(Environment.getExternalStorageDirectory() + "/PrathamBackups/frfrf");
    }

    public static void stopRecording() {
        cameraRecorder.stop();
    }

    public static void realease() {
        sampleGLView.onPause();
        cameraRecorder.stop();
        cameraRecorder.release();
        cameraRecorder = null;
        mframeLayout.removeView(sampleGLView);
        sampleGLView = null;
    }
}
