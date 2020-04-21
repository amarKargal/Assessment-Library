package com.pratham.assessment_lib.viewpager_fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.Utility.Assessment_Constants;
import com.pratham.assessment_lib.Utility.AudioUtil;
import com.pratham.assessment_lib.custom.gif_viewer.GifView;
import com.pratham.assessment_lib.domain.ScienceQuestion;
import com.pratham.assessment_lib.interfaces.AssessmentAnswerListener;
import com.pratham.assessment_lib.interfaces.AudioPlayerInterface;
import com.pratham.assessment_lib.science.ScienceAssessmentActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static com.pratham.assessment_lib.BaseActivity.assessPath;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.getFileName;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.setOdiaFont;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.showZoomDialog;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.wiseF;

@EFragment(resName = "layout_audio_row")
public class AudioFragment extends Fragment implements AudioPlayerInterface {
    @ViewById(resName = "tv_question")
    TextView question;
    @ViewById(resName = "iv_question_image")
    ImageView questionImage;
    @ViewById(resName = "iv_question_gif")
    GifView questionGif;
    @ViewById(resName = "iv_question_audio")
    ImageView iv_question_audio;
    @ViewById(resName = "iv_start_audio")
    ImageView iv_start_audio;
    @ViewById(resName = "rl_answer_audio")
    RelativeLayout rl_answer_audio;
    @ViewById(resName = "iv_answer_audio")
    ImageView iv_answer_audio;
    boolean isPlaying;
    boolean isAnsPlaying;
    boolean isAudioRecording;

    String fileName;
    String localPath;

    private static final String POS = "pos";
    private static final String SCIENCE_QUESTION = "scienceQuestion";

    private int pos;
    private ScienceQuestion scienceQuestion;
    AssessmentAnswerListener assessmentAnswerListener;


    public AudioFragment() {
        // Required empty public constructor
    }


    public static AudioFragment newInstance(int pos, ScienceQuestion scienceQuestion) {
        AudioFragment_ audioFragment = new AudioFragment_();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putSerializable("scienceQuestion", scienceQuestion);
        audioFragment.setArguments(args);
        return audioFragment;
    }

    @AfterViews
    public void init() {
        if (getArguments() != null) {
            pos = getArguments().getInt(POS, 0);
            scienceQuestion = (ScienceQuestion) getArguments().getSerializable(SCIENCE_QUESTION);
            assessmentAnswerListener = (ScienceAssessmentActivity) getActivity();

        }
        setAudioQuestion();

    }


    /* @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         if (getArguments() != null) {
             pos = getArguments().getInt(POS, 0);
             scienceQuestion = (ScienceQuestion) getArguments().getSerializable(SCIENCE_QUESTION);
             assessmentAnswerListener = (ScienceAssessmentActivity) getActivity();

         }
         setAudioQuestion();

     }
 */
  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_audio_row, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setAudioQuestion();

    }
*/
    public void setAudioQuestion() {
        setOdiaFont(getActivity(), question);

        if (scienceQuestion.getQname().equalsIgnoreCase(""))
            question.setText("Play the audio");
        else
            question.setText(scienceQuestion.getQname());
        rl_answer_audio.setVisibility(View.GONE);
        if (!scienceQuestion.getPhotourl().equalsIgnoreCase("")) {
            questionImage.setVisibility(View.VISIBLE);

            fileName = getFileName(scienceQuestion.getQid(), scienceQuestion.getPhotourl());
//            path = Environment.getExternalStorageDirectory().toString() + "/.Assessment/Content/Downloaded" + "/" + fileName;
            localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;

            String path = scienceQuestion.getPhotourl();
            String[] imgPath = path.split("\\.");
            int len;
            if (imgPath.length > 0)
                len = imgPath.length - 1;
            else len = 0;
            if (imgPath[len].equalsIgnoreCase("gif")) {
                try {
                    InputStream gif;
                    if (wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
                        Glide.with(getActivity()).asGif()
                                .load(path)
                                .apply(new RequestOptions()
                                        .placeholder(Drawable.createFromPath(localPath)))
                                .into(questionImage);
                    } else {
                        gif = new FileInputStream(localPath);
                        questionImage.setVisibility(View.GONE);
                        questionGif.setVisibility(View.VISIBLE);
                        questionGif.setGifResource(gif);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

           /*     Glide.with(getActivity()).asGif()
                        .load(path)
                        .apply(new RequestOptions()
                                .placeholder(Drawable.createFromPath(localPath)))
                        .into(questionImage);*/
//                    zoomImg.setVisibility(View.VISIBLE);
            } else {
                Glide.with(getActivity())
                        .load(path)
                        .apply(new RequestOptions()
                                .placeholder(Drawable.createFromPath(localPath)))
                        .into(questionImage);
            }

            questionImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showZoomDialog(getActivity(), scienceQuestion.getPhotourl(), localPath);
                }
            });
            questionGif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showZoomDialog(getActivity(), scienceQuestion.getPhotourl(), localPath);
                }
            });


        } else questionImage.setVisibility(View.GONE);

        if (!scienceQuestion.getUserAnswer().equalsIgnoreCase("")) {
            rl_answer_audio.setVisibility(View.VISIBLE);
        }


        try {

//            File direct = new File(AssessmentApplication.assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH);
            File direct = new File(assessPath);
            if (!direct.exists()) direct.mkdir();
//            direct = new File(Environment.getExternalStorageDirectory().toString() + "/.Assessment/Science/Content/Answers");
            direct = new File(assessPath + Assessment_Constants.STORE_ANSWER_MEDIA_PATH);
            if (!direct.exists()) direct.mkdir();
    /*        iv_question_audio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fileName = scienceQuestion.getPaperid() + "_" + scienceQuestion.getQid() + ".mp3";

                    String path = Environment.getExternalStorageDirectory().toString() + "/.Assessment/Content/Answers/" + fileName;
                    if (isAudioRecording) {
                        isAudioRecording = false;
                        iv_start_audio.setImageResource(R.drawable.ic_play_circle);

                    } else {
                        isAudioRecording = true;
                        iv_start_audio.setImageResource(R.drawable.ic_pause);

                    }
                    assessmentAnswerListener.setAudio(path, isAudioRecording);
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Click(resName = "iv_question_audio")
    public void playQuestionAudio() {
//        String fileName =scienceQuestion.getQid() + "_" + scienceQuestion.getPaperid()  + ".mp3";
        try {
//            String path = Environment.getExternalStorageDirectory().toString() + "/.Assessment/Content/Downloaded/" + fileName;
            if (isPlaying) {
                isPlaying = false;
                iv_question_audio.setImageResource(R.drawable.ic_play_circle);
                AudioUtil.stopPlayingAudio();

            } else {
                isPlaying = true;
                iv_question_audio.setImageResource(R.drawable.ic_pause);
                AudioUtil.playRecording(localPath, this);


            }
//            assessmentAnswerListener.setAudio(path, isAudioRecording);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Click(resName = "iv_start_audio")
    public void startAudio() {
        try {
            String fileName = scienceQuestion.getQid() + "_" + scienceQuestion.getPaperid() + ".mp3";

//            String path = Environment.getExternalStorageDirectory().toString() + "/.Assessment/Content/Answers/" + fileName;
            String path = assessPath + Assessment_Constants.STORE_ANSWER_MEDIA_PATH + "/" + fileName;
            if (isAudioRecording) {
                isAudioRecording = false;
                iv_start_audio.setImageResource(R.drawable.ic_mic_24dp);
                AudioUtil.stopRecording();
                scienceQuestion.setUserAnswer(path);
                rl_answer_audio.setVisibility(View.VISIBLE);
                assessmentAnswerListener.setAnswerInActivity("", path, scienceQuestion.getQid(), null);


            } else {
                isAudioRecording = true;
                iv_start_audio.setImageResource(R.drawable.ic_pause);
                AudioUtil.startRecording(path);
                rl_answer_audio.setVisibility(View.GONE);

            }
//            assessmentAnswerListener.setAudio(path, isAudioRecording);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Click(resName = "iv_answer_audio")
    public void onAnswerPlayClick() {
        try {
            String fileName = scienceQuestion.getQid() + "_" + scienceQuestion.getPaperid() + ".mp3";
            String path = assessPath + Assessment_Constants.STORE_ANSWER_MEDIA_PATH + "/" + fileName;
            if (isAnsPlaying) {
                isAnsPlaying = false;
                iv_answer_audio.setImageResource(R.drawable.ic_play_circle);
                AudioUtil.stopPlayingAudio();

            } else {
                isAnsPlaying = true;
                iv_answer_audio.setImageResource(R.drawable.ic_pause);
                AudioUtil.playRecording(path, this);


            }
//            assessmentAnswerListener.setAudio(path, isAudioRecording);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopPlayer() {
        if (isAnsPlaying)
            iv_answer_audio.setImageResource(R.drawable.ic_play_circle);
        if (isPlaying)
            iv_question_audio.setImageResource(R.drawable.ic_play_circle);

    }

    @Override
    public void onPause() {
        super.onPause();
//        if(audioPlayerInterface!=null)
        String fileName = scienceQuestion.getQid() + "_" + scienceQuestion.getPaperid() + ".mp3";

//            String path = Environment.getExternalStorageDirectory().toString() + "/.Assessment/Content/Answers/" + fileName;
        String path = assessPath + Assessment_Constants.STORE_ANSWER_MEDIA_PATH + "/" + fileName;

        if (isAudioRecording) {
            isAudioRecording = false;
            iv_start_audio.setImageResource(R.drawable.ic_mic_24dp);
            AudioUtil.stopRecording();
            scienceQuestion.setUserAnswer(path);
            rl_answer_audio.setVisibility(View.VISIBLE);
            assessmentAnswerListener.setAnswerInActivity("", path, scienceQuestion.getQid(), null);


        }

        if (isPlaying || isAnsPlaying)
            AudioUtil.stopPlayingAudio();
        stopPlayer();
    }
}
