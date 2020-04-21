package com.pratham.assessment_lib.custom.custom_dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;



import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pratham.assessment_lib.BaseActivity;
import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.Utility.AudioUtil;
import com.pratham.assessment_lib.custom.gif_viewer.GifViewZoom;
import com.pratham.assessment_lib.custom.zoom_image.ZoomageView;
import com.pratham.assessment_lib.interfaces.AudioPlayerInterface;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.FileInputStream;
import java.io.InputStream;

import static com.pratham.assessment_lib.Utility.Assessment_Utility.getFileExtension;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.wiseF;


@EActivity(resName = "zoom_image_dialog")
public class ZoomImageDialog extends BaseActivity implements AudioPlayerInterface {

    @ViewById(resName = "btn_ok_img")
    ImageButton btn_ok;
    @ViewById(resName = "iv_zoom_img")
    ZoomageView zoomImg;
    @ViewById(resName = "iv_img")
    GifViewZoom gifView;
    @ViewById(resName = "vv_video")
    VideoView videoView;
    @ViewById(resName = "audio_view")
    ImageView audio_view;
    //    private Context context;
    private String path;
    private String localPath;
    boolean isAudioPlaying = false;

    /*public ZoomImageDialog(@NonNull Context context, String path, String localPath) {
//        super(context,android.R.style.Theme_NoTitleBar_Fullscreen);
//        super(context, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
//        super(context,android.R.style.Theme_Holo_NoActionBar_Fullscreen);
        this.context = context;
        this.path = path;
        this.localPath = localPath;
    }*/

    /*public ZoomImageDialog(Context context, String path, String qtid, String localPath) {
        super(context);
        this.context = context;
        this.path = path;
        this.qtid = qtid;
        this.localPath = localPath;
}*/

    @AfterViews
    public void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        path = getIntent().getStringExtra("onlinePath");
        localPath = getIntent().getStringExtra("localPath");
        String extension = "";
        if (!localPath.equalsIgnoreCase(""))
            extension = getFileExtension(localPath);
        else if (!path.equalsIgnoreCase(""))
            extension = getFileExtension(path);

        if (!extension.equalsIgnoreCase("") && extension.equalsIgnoreCase("mp4")) {

            zoomImg.setVisibility(View.GONE);
            gifView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);

            //Creating MediaController
            final MediaController mediaController = new MediaController(this);
//            mediaController.setAnchorView(videoView);

            //specify the location of media file

            //Setting MediaController and URI, then starting the videoView
           /* videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();*/

            videoView.setVideoPath(path);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            videoView.setZOrderOnTop(true);
            videoView.setZOrderMediaOverlay(true);
            videoView.start();

        } else {
            if (path != null) {
                String[] imgPath = path.split("\\.");
                int len;
                if (imgPath.length > 0)
                    len = imgPath.length - 1;
                else len = 0;
                if (imgPath[len].equalsIgnoreCase("gif")) {
                    if (wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
                        Glide.with(this).asGif()
                                .load(path)
                                .apply(new RequestOptions()
                                        .placeholder(Drawable.createFromPath(localPath)))
                                .into(zoomImg);
                        zoomImg.setVisibility(View.VISIBLE);
                        gifView.setVisibility(View.GONE);
                    } else {
                        try {
                            InputStream gif = new FileInputStream(localPath);
                            zoomImg.setVisibility(View.GONE);
                            gifView.setVisibility(View.VISIBLE);
                            gifView.setGifResource(gif);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (imgPath[len].equalsIgnoreCase("jpg") || imgPath[len].equalsIgnoreCase("png")) {
                    zoomImg.setVisibility(View.VISIBLE);
//                    Glide.get(context).clearDiskCache();

                    Glide.with(this)
                            .load(path)
//                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .placeholder(Drawable.createFromPath(localPath)))
                            .into(zoomImg);
                    gifView.setVisibility(View.GONE);

                } else if (imgPath[len].equalsIgnoreCase("mp3")) {
                    zoomImg.setVisibility(View.GONE);
                    gifView.setVisibility(View.GONE);
                    audio_view.setVisibility(View.VISIBLE);

                }
            }
        }
    }


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_image_dialog);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        path = getIntent().getStringExtra("onlinePath");
        localPath = getIntent().getStringExtra("localPath");
        String extension = "";
        if (!localPath.equalsIgnoreCase(""))
            extension = getFileExtension(localPath);
        else if (!path.equalsIgnoreCase(""))
            extension = getFileExtension(path);

        if (!extension.equalsIgnoreCase("") && extension.equalsIgnoreCase("mp4")) {

            zoomImg.setVisibility(View.GONE);
            gifView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);

            //Creating MediaController
            final MediaController mediaController = new MediaController(this);
//            mediaController.setAnchorView(videoView);

            //specify the location of media file

            //Setting MediaController and URI, then starting the videoView
           *//* videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();*//*

            videoView.setVideoPath(path);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            videoView.setZOrderOnTop(true);
            videoView.setZOrderMediaOverlay(true);
            videoView.start();

        } else {
            if (path != null) {
                String[] imgPath = path.split("\\.");
                int len;
                if (imgPath.length > 0)
                    len = imgPath.length - 1;
                else len = 0;
                if (imgPath[len].equalsIgnoreCase("gif")) {
                    if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
                        Glide.with(this).asGif()
                                .load(path)
                                .apply(new RequestOptions()
                                        .placeholder(Drawable.createFromPath(localPath)))
                                .into(zoomImg);
                        zoomImg.setVisibility(View.VISIBLE);
                        gifView.setVisibility(View.GONE);
                    } else {
                        try {
                            InputStream gif = new FileInputStream(localPath);
                            zoomImg.setVisibility(View.GONE);
                            gifView.setVisibility(View.VISIBLE);
                            gifView.setGifResource(gif);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (imgPath[len].equalsIgnoreCase("jpg") || imgPath[len].equalsIgnoreCase("png")) {
                    zoomImg.setVisibility(View.VISIBLE);
//                    Glide.get(context).clearDiskCache();

                    Glide.with(this)
                            .load(path)
//                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .placeholder(Drawable.createFromPath(localPath)))
                            .into(zoomImg);
                    gifView.setVisibility(View.GONE);

                } else if (imgPath[len].equalsIgnoreCase("mp3")) {
                    zoomImg.setVisibility(View.GONE);
                    gifView.setVisibility(View.GONE);
                    audio_view.setVisibility(View.VISIBLE);

                }
            }
        }
    }
*/
    @Click(resName = "btn_ok_img")
    public void closeDialog() {
        if (isAudioPlaying) {
            stopPlayer();
            AudioUtil.stopPlayingAudio();
        }
//        dismiss();
        finish();
    }

    @Click(resName = "audio_view")
    public void listenAudio() {
        if (isAudioPlaying) {
            isAudioPlaying = false;
            audio_view.setImageResource(R.drawable.ic_play);
            AudioUtil.stopPlayingAudio();
            stopPlayer();

        } else {
            isAudioPlaying = true;
            audio_view.setImageResource(R.drawable.ic_pause);
            if (wiseF.isDeviceConnectedToMobileOrWifiNetwork())
                AudioUtil.playRecording(path, this);
            else AudioUtil.playRecording(localPath, this);
        }
    }

    @Override
    public void stopPlayer() {
        if (isAudioPlaying) {
            isAudioPlaying = false;
            AudioUtil.stopPlayingAudio();
            audio_view.setImageResource(R.drawable.ic_play);
        }
    }


}

