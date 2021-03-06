package com.pratham.assessment_lib.viewpager_fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.Utility.Assessment_Constants;
import com.pratham.assessment_lib.Utility.Assessment_Utility;
import com.pratham.assessment_lib.Utility.AudioUtil;
import com.pratham.assessment_lib.custom.gif_viewer.GifView;
import com.pratham.assessment_lib.domain.AssessmentQuestion;
import com.pratham.assessment_lib.domain.SubOptions;
import com.pratham.assessment_lib.interfaces.AssessmentAnswerListener;
import com.pratham.assessment_lib.interfaces.AudioPlayerInterface;
import com.pratham.assessment_lib.science.ScienceAssessmentActivity;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import static com.pratham.assessment_lib.Utility.Assessment_Constants.assessPath;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.getFileExtension;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.selectedColor;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.setOdiaFont;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.showZoomDialog;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.wiseF;


@EFragment(resName = "layout_mcq_fill_in_the_blanks_with_options_row")
public class McqFillInTheBlanksFragment extends Fragment implements AudioPlayerInterface {

    @ViewById(resName = "tv_question")
    TextView question;
    @ViewById(resName = "iv_question_image")
    ImageView questionImage;
    @ViewById(resName = "iv_view_question_img")
    ImageView iv_view_question_img;
    @ViewById(resName = "rl_question_img")
    RelativeLayout rl_question_img;
    @ViewById(resName = "iv_question_gif")
    GifView questionGif;
    @ViewById(resName = "rg_mcq")
    RadioGroup radioGroupMcq;
    @ViewById(resName = "grid_mcq")
    GridLayout gridMcq;
    private AssessmentAnswerListener assessmentAnswerListener;
    private List<SubOptions> options;
    int clickedOption = 0;

    private static final String POS = "pos";
    private static final String SCIENCE_QUESTION = "scienceQuestion";

    private int imgCnt = 0, textCnt = 0, audioCnt = 0;
    private AssessmentQuestion assessmentQuestion;

    boolean isQuestionPlaying = false;
    boolean isOptionPlaying = false;
    View prevView;
    View currentView;

    public McqFillInTheBlanksFragment() {
        // Required empty public constructor
    }

    @AfterViews
    public void init() {
        if (getArguments() != null) {
//            pos = getArguments().getInt(POS, 0);
            assessmentQuestion = (AssessmentQuestion) getArguments().getSerializable(SCIENCE_QUESTION);
            assessmentAnswerListener = (ScienceAssessmentActivity) getActivity();

        }
        setMcqsQuestion();

    }

    public static McqFillInTheBlanksFragment newInstance(int pos, AssessmentQuestion assessmentQuestion) {
        McqFillInTheBlanksFragment_ fragmentFirst = new McqFillInTheBlanksFragment_();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putSerializable("scienceQuestion", assessmentQuestion);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            pos = getArguments().getInt(POS, 0);
            scienceQuestion = (ScienceQuestion) getArguments().getSerializable(SCIENCE_QUESTION);
            assessmentAnswerListener = (ScienceAssessmentActivity) getActivity();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_mcq_fill_in_the_blanks_with_options_row, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setMcqsQuestion();
    }*/

    private void setMcqsQuestion() {
        options = new ArrayList<>();
        question.setText(assessmentQuestion.getQname());
        setOdiaFont(getActivity(), question);

        question.setMovementMethod(new ScrollingMovementMethod());
        if (!assessmentQuestion.getPhotourl().equalsIgnoreCase("")) {
            String questionExtension = getFileExtension(assessmentQuestion.getPhotourl());
            String fileName = Assessment_Utility.getFileName(assessmentQuestion.getQid(), assessmentQuestion.getPhotourl());
            final String localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;


            final String path = assessmentQuestion.getPhotourl();
            if (questionExtension.equalsIgnoreCase("gif") || questionExtension.equalsIgnoreCase("png") || questionExtension.equalsIgnoreCase("jpg")) {
                questionImage.setVisibility(View.VISIBLE);
                rl_question_img.setVisibility(View.VISIBLE);
//            if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {


           /* String[] imgPath = path.split("\\.");
            int len;
            if (imgPath.length > 0)
                len = imgPath.length - 1;
            else len = 0;*/
                if (questionExtension.equalsIgnoreCase("gif")) {
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
                iv_view_question_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showZoomDialog(getActivity(), assessmentQuestion.getPhotourl(), localPath);
                  /*  MaryPopup marypopup = MaryPopup.with(getActivity())
                            .cancellable(true)
                            .blackOverlayColor(Color.parseColor("#DD444444"))
                            .backgroundColor(Color.parseColor("#EFF4F5"))
                            .center(true)
                            .content(R.layout.popup_layout)
                            .from(questionImage);
                    marypopup.show();*/

                    }
                });
          /*  questionGif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showZoomDialog(getActivity(), scienceQuestion.getPhotourl(), localPath);
                }
            });*/
           /* } else {
                String fileName = Assessment_Utility.getFileName(scienceQuestion.getQid(), scienceQuestion.getPhotourl());
                final String localPath = AssessmentApplication.assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
                setImage(questionImage, localPath);
                questionImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showZoomDialog(localPath);
                    }
                });
            }*/
            } else {
                questionImage.setVisibility(View.VISIBLE);
                iv_view_question_img.setVisibility(View.GONE);
                questionImage.setImageResource(R.drawable.ic_play_circle);
                questionImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isQuestionPlaying) {
                            isQuestionPlaying = false;
                            questionImage.setImageResource(R.drawable.ic_play_circle);
                            AudioUtil.stopPlayingAudio();
                            stopPlayer();

                        } else {
                            isQuestionPlaying = true;
                            questionImage.setImageResource(R.drawable.ic_pause);
                            if (wiseF.isDeviceConnectedToMobileOrWifiNetwork())
                                AudioUtil.playRecording(path, McqFillInTheBlanksFragment.this);
                            else
                                AudioUtil.playRecording(localPath, McqFillInTheBlanksFragment.this);
                        }
                    }
                });
            }
        } else {
            questionImage.setVisibility(View.GONE);
            rl_question_img.setVisibility(View.GONE);
        }

        options.clear();
       //todo alter
        // options = AppDatabase.getDatabaseInstance(getActivity()).getScienceQuestionChoicesDao().getQuestionChoicesByQID(scienceQuestion.getQid());
        options= assessmentQuestion.getLstquestionchoice();
        imgCnt = 0;
        textCnt = 0;
        if (options != null) {
            radioGroupMcq.removeAllViews();
            gridMcq.removeAllViews();

            for (int r = 0; r < options.size(); r++) {
                if (!options.get(r).getChoiceurl().equalsIgnoreCase("")) {
                    String ansExtension = getFileExtension(options.get(r).getChoiceurl());
                    if (ansExtension.equalsIgnoreCase("gif") ||
                            ansExtension.equalsIgnoreCase("png") ||
                            ansExtension.equalsIgnoreCase("jpg")) {
                        imgCnt++;
                    } else {
                        audioCnt++;
                    }
                }
                if (!options.get(r).getChoicename().equalsIgnoreCase("")) {
                    textCnt++;
                }

            }
            for (int r = 0; r < options.size(); r++) {

                String ans = "$";
                if (!assessmentQuestion.getUserAnswer().equalsIgnoreCase(""))
                    ans = assessmentQuestion.getUserAnswer();
                String ansId = assessmentQuestion.getUserAnswerId();

                if (textCnt == options.size()) {
                    radioGroupMcq.setVisibility(View.GONE);
                    gridMcq.setVisibility(View.VISIBLE);
                    if (options.get(r).getChoiceurl().equalsIgnoreCase("")) {

                        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mcq_single_text_item, gridMcq, false);
                        final TextView textView = (TextView) view;
//                        textView.setElevation(3);
                        textView.setMovementMethod(new ScrollingMovementMethod());
                        textView.setText(options.get(r).getChoicename());

                        setOdiaFont(getActivity(), textView);

                        gridMcq.addView(textView);
                        if (assessmentQuestion.getUserAnswerId().equalsIgnoreCase(options.get(r).getQcid())) {
                            textView.setTextColor(selectedColor);
                            textView.setBackground(getActivity().getResources().getDrawable(R.drawable.gradient_selector));
                        } else {
                            textView.setTextColor(Color.WHITE);
                            textView.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_radio_button));

                        }
                        final int finalR2 = r;
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setOnclickOnItem(v, options.get(finalR2));

                                textView.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_edit_text));
                                textView.setTextColor(selectedColor);
                            }
                        });

                    }
/*                        radioGroupMcq.setVisibility(View.GONE);
                        gridMcq.setVisibility(View.VISIBLE);
                        gridMcq.setColumnCount(2);
                        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mcq_single_text_item, radioGroupMcq, false);
                        final TextView textView = (TextView) view;
                        textView.setTextColor(Assessment_Utility.colorStateList);
                        textView.setId(r);
                        textView.setElevation(3);

                       *//* if (!options.get(r).getChoiceurl().equalsIgnoreCase("")) {
                            final String path = options.get(r).getChoiceurl();
                            radioButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showZoomDialog(path);
                                }
                            });
                            radioGroupMcq.addView(radioButton);
//                            radioButton.setText("View Option " + (r + 1));
//                    radioButton.setText(options.get(r).getChoicename());
                            if (scienceQuestion.getUserAnswerId().equalsIgnoreCase(options.get(r).getQcid())) {
                                radioButton.setChecked(true);
                                radioButton.setTextColor(Assessment_Utility.selectedColor);
                            } else {
                                radioButton.setChecked(false);
                                radioButton.setTextColor(Color.WHITE);
                            }
                        } else {*//*
                        textView.setText(options.get(r).getChoicename());
                        if (scienceQuestion.getUserAnswerId().equalsIgnoreCase(options.get(r).getQcid())) {
//                            textView.setChecked(true);
                            textView.setTextColor(Assessment_Utility.selectedColor);
                        } else {
//                            textView.setChecked(false);
                            textView.setTextColor(Color.WHITE);
                        }
//                        radioGroupMcq.addView(textView);
                        gridMcq.addView(textView);

                        if (ans.equals(options.get(r).getChoicename())) {
//                            textView.setChecked(true);
                        } else {
//                            textView.setChecked(false);
                        }
//                        }
                    }*/
                } else if (imgCnt == options.size()) {
                    radioGroupMcq.setVisibility(View.GONE);
                    gridMcq.setVisibility(View.VISIBLE);
                    String fileName = Assessment_Utility.getFileName(assessmentQuestion.getQid(), options.get(r).getChoiceurl());
//                String localPath = Environment.getExternalStorageDirectory() + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
                    String localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;

                    String path = options.get(r).getChoiceurl();

                    String[] imgPath = path.split("\\.");
                    int len;
                    if (imgPath.length > 0)
                        len = imgPath.length - 1;
                    else len = 0;
                  /*  final GifView gifView;
                    ImageView imageView = null;*/

                    final String imageUrl = options.get(r).getChoiceurl();
                    final View view;
                    final RelativeLayout rl_mcq;
                    View viewRoot;
                    final ImageView tick;
                    final ImageView zoomImg;


                    if (imgPath[len].equalsIgnoreCase("gif")) {
                        viewRoot = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mcq_gif_item, gridMcq, false);
                        view = viewRoot.findViewById(R.id.mcq_gif);
                        rl_mcq = viewRoot.findViewById(R.id.rl_mcq);
                        tick = viewRoot.findViewById(R.id.iv_tick);
                        zoomImg = viewRoot.findViewById(R.id.iv_view_img);
                        /*  setImage(view, imageUrl, localPath);
                        gridMcq.addView(view);*/
                    } else {
                        viewRoot = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mcq_card_image_item, gridMcq, false);
                        view = viewRoot.findViewById(R.id.mcq_img);
                        rl_mcq = viewRoot.findViewById(R.id.rl_mcq);
                        tick = viewRoot.findViewById(R.id.iv_tick);
                        zoomImg = viewRoot.findViewById(R.id.iv_view_img);

/*setImage(view, imageUrl, localPath);
                        gridMcq.addView(view);*/
//                        if (scienceQuestion.getUserAnswerId().equalsIgnoreCase(options.get(r).getQcid())) {
//                            view.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_edit_text));
//                        } else {
//                            view.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_radio_button));
//
//                        }

                    }
                    final int finalR = r;
//                    final ImageView finalImageView = imageView;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int g = 0; g < gridMcq.getChildCount(); g++) {
                                gridMcq.getChildAt(g).setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.custom_radio_button));
                                ((CardView) ((RelativeLayout) gridMcq.getChildAt(g)).getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
                            }
                            rl_mcq.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_edit_text));
                            tick.setVisibility(View.VISIBLE);
                     /*       String fileName = Assessment_Utility.getFileName(scienceQuestion.getQid(), options.get(finalR).getChoiceurl());
                            String localPath = AssessmentApplication.assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
*/

//                            if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
//                            showZoomDialog(getActivity(), options.get(finalR).getChoiceurl(), localPath);
                            /*} else {
                                 showZoomDialog(localPath);
                            }*/
                            List<SubOptions> ans = new ArrayList<>();
                            ans.add(options.get(finalR));
                            assessmentQuestion.setMatchingNameList(ans);
                            assessmentAnswerListener.setAnswerInActivity("", "", assessmentQuestion.getQid(), ans);
                        }
                    });

                    zoomImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String fileName = Assessment_Utility.getFileName(assessmentQuestion.getQid(), options.get(finalR).getChoiceurl());
                            String localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
                            showZoomDialog(getActivity(), options.get(finalR).getChoiceurl(), localPath);

                        }
                    });


                    if (assessmentQuestion.getUserAnswerId().equalsIgnoreCase(options.get(r).getQcid())) {
                        rl_mcq.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_edit_text));
                        tick.setVisibility(View.VISIBLE);

                    } else {
                        rl_mcq.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_radio_button));
                        tick.setVisibility(View.GONE);

                    }
                    setImage(view, imageUrl, localPath);
                    gridMcq.addView(viewRoot);

                } else if (imgCnt != 0 && imgCnt < options.size()) {
                    gridMcq.setColumnCount(2);
                    final int finalR1 = r;
                    if (!options.get(r).getChoiceurl().equalsIgnoreCase("")) {

//                        final String imageUrl = options.get(r).getChoiceurl();
                        final View view;
                        final RelativeLayout rl_mcq;
                        View viewRoot;
                        final ImageView tick;
                        final ImageView zoomImg;


                        String path = options.get(r).getChoiceurl();

                        String[] imgPath = path.split("\\.");
                        int len;
                        if (imgPath.length > 0)
                            len = imgPath.length - 1;
                        else len = 0;


                        if (imgPath[len].equalsIgnoreCase("gif")) {
                            viewRoot = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mcq_gif_item, gridMcq, false);
                            view = viewRoot.findViewById(R.id.mcq_gif);
                            rl_mcq = viewRoot.findViewById(R.id.rl_mcq);
                            tick = viewRoot.findViewById(R.id.iv_tick);
                            zoomImg = viewRoot.findViewById(R.id.iv_view_img);


                        /*  setImage(view, imageUrl, localPath);
                        gridMcq.addView(view);*/
                        } else {
                            viewRoot = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mcq_card_image_item, gridMcq, false);
                            view = viewRoot.findViewById(R.id.mcq_img);
                            rl_mcq = viewRoot.findViewById(R.id.rl_mcq);
                            tick = viewRoot.findViewById(R.id.iv_tick);
                            zoomImg = viewRoot.findViewById(R.id.iv_view_img);

/*setImage(view, imageUrl, localPath);
                        gridMcq.addView(view);*/
//                        if (scienceQuestion.getUserAnswerId().equalsIgnoreCase(options.get(r).getQcid())) {
//                            view.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_edit_text));
//                        } else {
//                            view.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_radio_button));
//
//                        }

                        }


//                        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mcq_image_item, gridMcq, false);

                        String fileName = Assessment_Utility.getFileName(assessmentQuestion.getQid(), options.get(r).getChoiceurl());
//                String localPath = Environment.getExternalStorageDirectory() + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
                        String localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;

//                        final ImageView imageView = (ImageView) view;
//                        if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
                        final String imageUrl = options.get(r).getChoiceurl();
                        setImage(view, imageUrl, localPath);

                        gridMcq.addView(viewRoot);

                        if (assessmentQuestion.getUserAnswerId().equalsIgnoreCase(options.get(r).getQcid())) {
                            rl_mcq.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_edit_text));
                            tick.setVisibility(View.VISIBLE);

                        } else {
                            rl_mcq.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_radio_button));
                            tick.setVisibility(View.GONE);
                        }

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setOnclickOnItem(v, options.get(finalR1));
                                    tick.setVisibility(View.VISIBLE);

                                rl_mcq.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_edit_text));
//                                if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
//                                showZoomDialog(getActivity(), options.get(finalR1).getChoiceurl(), localPath);

                               /* } else {
                                    showZoomDialog(localPath);
                                }*/
                            }
                        });

                        zoomImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String fileName = Assessment_Utility.getFileName(assessmentQuestion.getQid(), options.get(finalR1).getChoiceurl());
                                String localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
                                showZoomDialog(getActivity(), options.get(finalR1).getChoiceurl(), localPath);

                            }
                        });
                    } else {
                        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mcq_single_text_item, gridMcq, false);
                        final TextView textView = (TextView) view;
                        textView.setElevation(3);
                        textView.setMovementMethod(new ScrollingMovementMethod());
                        textView.setText(options.get(r).getChoicename());
                        gridMcq.addView(textView);
                        if (assessmentQuestion.getUserAnswerId().equalsIgnoreCase(options.get(r).getQcid())) {
                            textView.setTextColor(selectedColor);
                            textView.setBackground(getActivity().getResources().getDrawable(R.drawable.gradient_selector));
                        } else {
                            textView.setTextColor(Color.WHITE);
                            textView.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_radio_button));

                        }
                        setOdiaFont(getActivity(), textView);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setOnclickOnItem(v, options.get(finalR1));

                                textView.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_edit_text));
                                textView.setTextColor(selectedColor);
                            }
                        });
                    }


                } else if (audioCnt == options.size()) {
                    radioGroupMcq.setVisibility(View.GONE);
                    gridMcq.setVisibility(View.VISIBLE);
                    String fileName = Assessment_Utility.getFileName(assessmentQuestion.getQid(), options.get(r).getChoiceurl());
//                String localPath = Environment.getExternalStorageDirectory() + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
                    final String localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;

//                    String path = options.get(r).getChoiceurl();
                    String pathExtension = getFileExtension(options.get(r).getChoiceurl());
                  /*  String[] imgPath = path.split("\\.");
                    int len;
                    if (imgPath.length > 0)
                        len = imgPath.length - 1;
                    else len = 0;*/
                  /*  final GifView gifView;
                    ImageView imageView = null;*/

                    final String imageUrl = options.get(r).getChoiceurl();

                    final ImageView audioImage;
                    final RelativeLayout rl_mcq;
                    View viewRoot;
                    final ImageView tick;
                    final TextView audioText;


//                    if (pathExtension.equalsIgnoreCase("gif")) {
                       /* viewRoot = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mcq_gif_item, gridMcq, false);
                        view = viewRoot.findViewById(R.id.mcq_gif);
                        rl_mcq = viewRoot.findViewById(R.id.rl_mcq);
                        tick = viewRoot.findViewById(R.id.iv_tick);
                        zoomImg = viewRoot.findViewById(R.id.iv_view_img);*/
                        /*  setImage(view, imageUrl, localPath);
                        gridMcq.addView(view);*/
//                    } else {
                    viewRoot = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mcq_audio_item, gridMcq, false);
                    audioImage = viewRoot.findViewById(R.id.mcq_audio);
                    rl_mcq = viewRoot.findViewById(R.id.rl_mcq);
                    tick = viewRoot.findViewById(R.id.iv_tick);
                    audioText = viewRoot.findViewById(R.id.txt_audio_title);


//                    }
                    final int finalR = r;
                    audioText.setText("Audio " + (r + 1));
                    setOdiaFont(getActivity(), audioText);

                    rl_mcq.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int g = 0; g < gridMcq.getChildCount(); g++) {
//                                gridMcq.getChildAt(g).setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.custom_radio_button));
                                ((CardView) ((RelativeLayout) gridMcq.getChildAt(g)).getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
//                                ((ImageView) ((LinearLayout) ((CardView) ((RelativeLayout) gridMcq.getChildAt(g)).getChildAt(0)).getChildAt(0)).getChildAt(1)).setImageResource(R.drawable.ic_play);
                            }
                            tick.setVisibility(View.VISIBLE);
                            List<SubOptions> ans = new ArrayList<>();
                            ans.add(options.get(finalR));
                            assessmentQuestion.setMatchingNameList(ans);
                            assessmentAnswerListener.setAnswerInActivity("", "", assessmentQuestion.getQid(), ans);

                        }
                    });
//                    final ImageView finalImageView = imageView;
                    audioImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int g = 0; g < gridMcq.getChildCount(); g++) {
//                                gridMcq.getChildAt(g).setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.custom_radio_button));
//                                ((CardView) ((RelativeLayout) gridMcq.getChildAt(g)).getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
                                ((ImageView) ((LinearLayout) ((CardView) ((RelativeLayout) gridMcq.getChildAt(g)).getChildAt(0)).getChildAt(0)).getChildAt(1)).setImageResource(R.drawable.ic_play);

                            }
                            currentView = v;
                            setAudioToOption(v, imageUrl, localPath);
//                            rl_mcq.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_edit_text));

                        }
                    });
//                    zoomImg.setVisibility(View.GONE);
                    if (assessmentQuestion.getUserAnswerId().equalsIgnoreCase(options.get(r).getQcid())) {
//                        rl_mcq.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_edit_text));
                        tick.setVisibility(View.VISIBLE);

                    } else {
//                        rl_mcq.setBackground(getActivity().getResources().getDrawable(R.drawable.custom_radio_button));
                        tick.setVisibility(View.GONE);

                    }
//                    setImage(view, imageUrl, localPath);
                    gridMcq.addView(viewRoot);

                }

            }
        }
        radioGroupMcq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //((RadioButton) radioGroupMcq.getChildAt(checkedId)).setChecked(true);
                RadioButton rb = group.findViewById(checkedId);
                if (rb != null) {
                    rb.setChecked(true);
                    rb.setTextColor(selectedColor);
                }

                for (int i = 0; i < group.getChildCount(); i++) {
                    if ((group.getChildAt(i)).getId() == checkedId) {
                        ((RadioButton) group.getChildAt(i)).setTextColor(selectedColor);

                        List<SubOptions> ans = new ArrayList<>();
                        ans.add(options.get(i));
                        assessmentQuestion.setMatchingNameList(ans);
                        assessmentAnswerListener.setAnswerInActivity("", "", assessmentQuestion.getQid(), ans);
                    } else {
                        ((RadioButton) group.getChildAt(i)).setTextColor(getActivity().getResources().getColor(R.color.white));
                    }
                }
            }
        });
    }

    private void setAudioToOption(View v, String onlinePath, String localPath) {
        if (prevView != null && v != prevView) {
            isOptionPlaying = false;
            AudioUtil.stopPlayingAudio();
            stopPlayer();
        }
        prevView = v;
        if (isOptionPlaying) {
            isOptionPlaying = false;
            ((ImageView) v).setImageResource(R.drawable.ic_play);
            AudioUtil.stopPlayingAudio();
            stopPlayer();

        } else {
            isOptionPlaying = true;
            ((ImageView) v).setImageResource(R.drawable.ic_pause);
            if (wiseF.isDeviceConnectedToMobileOrWifiNetwork())
                AudioUtil.playRecording(onlinePath, this);
            else AudioUtil.playRecording(localPath, this);
        }
    }


    private void setOnclickOnItem(View v, SubOptions subOptions) {
        for (int g = 0; g < gridMcq.getChildCount(); g++) {
            gridMcq.getChildAt(g).setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.custom_radio_button));
            View view = gridMcq.getChildAt(g);
            if (view instanceof TextView)
                ((TextView) view).setTextColor(Color.WHITE);
            if (view instanceof RelativeLayout) {
                ((CardView) ((RelativeLayout) view).getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
            }

        }

        List<SubOptions> ans = new ArrayList<>();
        ans.add(subOptions);
        assessmentQuestion.setMatchingNameList(ans);
        assessmentAnswerListener.setAnswerInActivity("", "", assessmentQuestion.getQid(), ans);

    }

    private void setImage(View view, final String choiceurl, String placeholder) {
//        if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
        String path = choiceurl;
        String[] imgPath = path.split("\\.");
        int len;
        if (imgPath.length > 0)
            len = imgPath.length - 1;
        else len = 0;


        if (imgPath[len].equalsIgnoreCase("gif")) {

            try {
                GifView gifView = (GifView) view;
                InputStream gif = new FileInputStream(placeholder);
                gifView.setGifResource(gif);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    /*        Glide.with(getActivity()).asGif()
                    .load(path)
                    .apply(new RequestOptions()
                            .placeholder(Drawable.createFromPath(placeholder)))
                    .into(imageView);*/
        } else {
            ImageView imageView = (ImageView) view;
            Glide.with(getActivity())
                    .load(path)
                    .apply(new RequestOptions()
                            .placeholder(Drawable.createFromPath(placeholder)))
                    .into(imageView);
        }


//        }
    }


    @Override
    public void onPause() {
        super.onPause();
//        if(audioPlayerInterface!=null)
        if (isOptionPlaying || isQuestionPlaying)
            AudioUtil.stopPlayingAudio();
        stopPlayer();
    }

    @Override
    public void stopPlayer() {
        if (isQuestionPlaying) {
            questionImage.setImageResource(R.drawable.ic_play_circle);
            isQuestionPlaying = false;
        }
        if (isOptionPlaying) {
            ((ImageView) currentView).setImageResource(R.drawable.ic_play);
            isOptionPlaying = false;

        }
    }
}

