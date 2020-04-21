package com.pratham.assessment_lib.viewpager_fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.Utility.Assessment_Constants;
import com.pratham.assessment_lib.Utility.PermissionUtils;
import com.pratham.assessment_lib.custom.gif_viewer.GifView;
import com.pratham.assessment_lib.domain.ScienceQuestion;
import com.pratham.assessment_lib.interfaces.AssessmentAnswerListener;
import com.pratham.assessment_lib.science.ScienceAssessmentActivity;
import com.pratham.assessment_lib.science.camera.VideoMonitoringService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static com.pratham.assessment_lib.BaseActivity.assessPath;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.getFileName;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.setOdiaFont;


@EFragment(resName = "layout_video_row")
public class VideoFragment extends Fragment {

    @ViewById(resName = "tv_question")
    TextView question;
    @ViewById(resName = "iv_question_image")
    ImageView questionImage;
    @ViewById(resName = "iv_question_gif")
    GifView questionGif;
    @ViewById(resName = "iv_answer_image_play_icon")
    ImageView iv_answer_image_play_icon;
    @ViewById(resName = "btn_capture_video")
    Button btn_capture_video;
    @ViewById(resName = "vv_question")
    VideoView vv_question;
    @ViewById(resName = "vv_answer_play_video")
    VideoView vv_answer;
    @ViewById(resName = "rl_answer_video")
    RelativeLayout rl_answer_video;
    AssessmentAnswerListener assessmentAnswerListener;
    String fileName;
    String questionPath;
    public static final int VIDEO_CAPTURE = 101;

    String filePath;

    private static final String POS = "pos";
    private static final String SCIENCE_QUESTION = "scienceQuestion";

    private int pos;
    private ScienceQuestion scienceQuestion;
    private String answerPath;
    private boolean VideoCaptured;
    String videoName = "";
    ScienceAssessmentActivity scienceAssessmentActivity;

    public VideoFragment() {
        // Required empty public constructor
    }

    @AfterViews
    public void init() {
        if (getArguments() != null) {
            pos = getArguments().getInt(POS, 0);
            scienceQuestion = (ScienceQuestion) getArguments().getSerializable(SCIENCE_QUESTION);
            assessmentAnswerListener = (ScienceAssessmentActivity) getActivity();
            scienceAssessmentActivity = (ScienceAssessmentActivity) getActivity();

        }
        setVideoQuestion();

    }

    public static VideoFragment newInstance(int pos, ScienceQuestion scienceQuestion) {
        VideoFragment_ videoFragment = new VideoFragment_();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putSerializable("scienceQuestion", scienceQuestion);
        videoFragment.setArguments(args);
        return videoFragment;
    }

/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pos = getArguments().getInt(POS, 0);
            scienceQuestion = (ScienceQuestion) getArguments().getSerializable(SCIENCE_QUESTION);
            assessmentAnswerListener = (ScienceAssessmentActivity) getActivity();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_video_row, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        rl_answer_video = view.findViewById(R.id.rl_answer_video);
        setVideoQuestion();
    }*/

    public void setVideoQuestion() {
        setOdiaFont(getActivity(), question);

        fileName = getFileName(scienceQuestion.getQid(), scienceQuestion.getPhotourl());
        rl_answer_video.setVisibility(View.GONE);
//        questionPath = Environment.getExternalStorageDirectory().toString() + "/.Assessment/Content/Downloaded" + "/" + fileName;
        questionPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
        if (scienceQuestion.getQname().equalsIgnoreCase(""))
            question.setText("Watch the video");
        else question.setText(scienceQuestion.getQname());
        rl_answer_video.setVisibility(View.GONE);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(questionPath, MediaStore.Images.Thumbnails.MICRO_KIND);
        BitmapDrawable ob = new BitmapDrawable(getResources(), thumb);
        questionImage.setBackgroundDrawable(ob);
        if (VideoCaptured) {
            rl_answer_video.setVisibility(View.VISIBLE);
        } else rl_answer_video.setVisibility(View.GONE);
//        question.setText(scienceQuestion.getQname());
      /*  if (!scienceQuestion.getPhotourl().equalsIgnoreCase("")) {
            questionImage.setVisibility(View.VISIBLE);
            Glide.with(context).asBitmap().
                    load(Assessment_Constants.loadOnlineImagePath + scienceQuestion.getPhotourl()).apply(new RequestOptions()
                    .fitCenter()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .override(Target.SIZE_ORIGINAL))
                    .into(new SimpleTarget<Bitmap>(100, 100) {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            Drawable bd = new BitmapDrawable(resource);
                            questionImage.setImageDrawable(bd);
                        }
                    });
        } else questionImage.setVisibility(View.GONE);*/
        questionImage.setVisibility(View.VISIBLE);


       /* btn_capture_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureVideo();
            }
        });*/


    }

    @Click(resName = "btn_capture_video")
    public void captureVideo() {
        if (Assessment_Constants.VIDEOMONITORING) {
//            assessmentAnswerListener.pauseVideoMonitoring();
            VideoMonitoringService.releaseMediaRecorder();
        }
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        assessmentAnswerListener.setVideoResult(intent, VIDEO_CAPTURE, scienceQuestion);
        setVideoResult(intent, VIDEO_CAPTURE, scienceQuestion);
       /* if (VideoCaptured) {
            rl_answer_video.setVisibility(View.VISIBLE);
        } else rl_answer_video.setVisibility(View.GONE);
*/
    }

    @Click(resName = {"iv_answer_image_play_icon","vv_answer_play_video"})
    public void onAnswerVideoClicked() {
        MediaController mediaController = new MediaController(getActivity());

        iv_answer_image_play_icon.setVisibility(View.GONE);
        vv_answer.setVisibility(View.VISIBLE);
        vv_answer.setVideoPath(answerPath);
        vv_answer.setMediaController(mediaController);
        mediaController.setAnchorView(vv_answer);
        vv_answer.setZOrderOnTop(true);
        vv_answer.setZOrderMediaOverlay(true);
        vv_answer.start();


      /*  ZoomImageDialog zoomImageDialog = new ZoomImageDialog(getActivity(), path, scienceQuestion.getQtid());
        zoomImageDialog.show();*/
    }

    @Click(resName = "iv_question_image")
    public void onVideoClicked() {
     /*   ZoomImageDialog zoomImageDialog = new ZoomImageDialog(getActivity(), path, scienceQuestion.getQtid());
        zoomImageDialog.show();*/

        MediaController mediaController = new MediaController(getActivity());
//            mediaController.setAnchorView(videoView);

        //specify the location of media file
//        Uri uri = Uri.parse(path);

        //Setting MediaController and URI, then starting the videoView
           /* videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();*/
        questionImage.setVisibility(View.GONE);
        vv_question.setVisibility(View.VISIBLE);
        vv_question.setVideoPath(questionPath);
        vv_question.setMediaController(mediaController);
        mediaController.setAnchorView(vv_question);
        vv_question.setZOrderOnTop(true);
        vv_question.setZOrderMediaOverlay(true);
        vv_question.start();

    }


    @Override
    public void onPause() {
        super.onPause();
        if (vv_question != null)
            vv_question.pause();
        if (vv_answer != null)
            vv_answer.pause();
    }

    public void showAnswerVideo() {
        try {

            rl_answer_video.setVisibility(View.VISIBLE);
//        answerPath = Environment.getExternalStorageDirectory().toString() + Assessment_Constants.STORE_ANSWER_MEDIA_PATH + "/" +scienceQuestion.getUserAnswer();
            answerPath = assessPath + Assessment_Constants.STORE_ANSWER_MEDIA_PATH + "/" + scienceQuestion.getUserAnswer();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(answerPath, MediaStore.Images.Thumbnails.MICRO_KIND);
            BitmapDrawable ob = new BitmapDrawable(getResources(), thumb);
            iv_answer_image_play_icon.setBackgroundDrawable(ob);
            VideoCaptured = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setVideoResult(Intent intent, int videoCapture, ScienceQuestion scienceQuestion) {

        try {
            if (hasCamera()) {
                if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                    String[] permissionArray = new String[]{PermissionUtils.Manifest_CAMERA};

                    if (!((ScienceAssessmentActivity) getActivity()).isPermissionsGranted(getActivity(), permissionArray)) {
                        Toast.makeText(getActivity(), "Give Camera permissions through settings and restart the app.", Toast.LENGTH_LONG).show();
                    } else {
                        videoName = scienceQuestion.getPaperid() + "_" + scienceQuestion.getQid() + ".mp4";
                        scienceQuestion.setUserAnswer(videoName);
                        //                    Intent takePicture = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3 * 60);
                        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                        //                    startActivityForResult(intent, VIDEO_CAPTURE);
                    }
                } else {
                    videoName = scienceQuestion.getPaperid() + "_" + scienceQuestion.getQid() + ".mp4";
                    scienceQuestion.setUserAnswer(videoName);

                    //                Intent takePicture = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3 * 60);
                    //                startActivityForResult(intent, VIDEO_CAPTURE);
                }
            } else {
//                assessmentAnswerListener.showCameraError();

//                Toast.makeText(getActivity(), "Camera not found", Toast.LENGTH_LONG).show();
            }

            filePath = assessPath + Assessment_Constants.STORE_ANSWER_MEDIA_PATH + "/" + videoName;

            startActivityForResult(intent, videoCapture);
        } catch (Exception e) {
            e.printStackTrace();
//            assessmentAnswerListener.showCameraError();

        }
    }

    private boolean hasCamera() {
        return getActivity().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == -1 && requestCode == VIDEO_CAPTURE) {
                AssetFileDescriptor videoAsset = getActivity().getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                FileInputStream in = videoAsset.createInputStream();
           /* File direct = new File(Environment.getExternalStorageDirectory().toString() + "/.Assessment");

            if (!direct.exists()) direct.mkdir();*/

//                File direct = new File(Environment.getExternalStorageDirectory().toString() + Assessment_Constants.STORE_ANSWER_MEDIA_PATH);
                File direct = new File(assessPath + Assessment_Constants.STORE_ANSWER_MEDIA_PATH);

                if (!direct.exists()) direct.mkdir();

                File fileName = new File(direct, videoName);
                if (fileName.exists())
                    fileName.delete();
                OutputStream out = new FileOutputStream(fileName);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                showCapturedVideo();
//                assessmentAnswerListener.resumeVideoMonitoring();
                scienceAssessmentActivity.startCameraService();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCapturedVideo() {
        try {

            showAnswerVideo();
            assessmentAnswerListener.setAnswerInActivity("", filePath, scienceQuestion.getQid(), null);
//        checkAssessment(queCnt);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
