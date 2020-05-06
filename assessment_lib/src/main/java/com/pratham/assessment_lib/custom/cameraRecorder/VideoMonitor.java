package com.pratham.assessment_lib.custom.cameraRecorder;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.widget.FrameLayout;

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
        cameraRecorder.start(path);
    }

    public static void stopRecording(String path) {
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
