package com.pratham.assessment_lib.viewpager_fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.Utility.Assessment_Constants;
import com.pratham.assessment_lib.Utility.Assessment_Utility;
import com.pratham.assessment_lib.custom.gif_viewer.GifView;
import com.pratham.assessment_lib.domain.ScienceQuestion;
import com.pratham.assessment_lib.interfaces.AssessmentAnswerListener;
import com.pratham.assessment_lib.science.ScienceAssessmentActivity;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.FileInputStream;
import java.io.InputStream;

import static com.pratham.assessment_lib.BaseActivity.assessPath;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.getFileName;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.setOdiaFont;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.wiseF;


@EFragment(resName ="layout_true_false_row")
public class TrueFalseFragment extends Fragment {
    @ViewById(resName ="tv_question")
    TextView question;
    @ViewById(resName ="rg_true_false")
    RadioGroup rg_true_false;
    @ViewById(resName ="iv_question_image")
    ImageView questionImage;
    @ViewById(resName ="iv_question_gif")
    GifView questionGif;
    @ViewById(resName ="rb_true")
    Button radioButtonTrue;
    @ViewById(resName ="rb_false")
    Button radioButtonFalse;
    AssessmentAnswerListener assessmentAnswerListener;

    private static final String POS = "pos";
    private static final String SCIENCE_QUESTION = "scienceQuestion";

    private int pos;
    private ScienceQuestion scienceQuestion;

    public TrueFalseFragment() {
        // Required empty public constructor
    }

    @AfterViews
    public void init(){
        if (getArguments() != null) {
            pos = getArguments().getInt(POS, 0);
            scienceQuestion = (ScienceQuestion) getArguments().getSerializable(SCIENCE_QUESTION);
            assessmentAnswerListener = (ScienceAssessmentActivity) getActivity();

        }
        setTrueFalseQuestion();

    }

    public static TrueFalseFragment newInstance(int pos, ScienceQuestion scienceQuestion) {
        TrueFalseFragment_ trueFalseFragment = new TrueFalseFragment_();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putSerializable("scienceQuestion", scienceQuestion);
        trueFalseFragment.setArguments(args);
        return trueFalseFragment;
    }

/*
    @Override
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
        return inflater.inflate(R.layout.layout_true_false_row, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setTrueFalseQuestion();
    }
*/

    public void setTrueFalseQuestion() {

        setOdiaFont(getActivity(), question);
        question.setText(scienceQuestion.getQname());
        if (!scienceQuestion.getPhotourl().equalsIgnoreCase("")) {
            questionImage.setVisibility(View.VISIBLE);
//            if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {

            String fileName = getFileName(scienceQuestion.getQid(), scienceQuestion.getPhotourl());
//                String localPath = Environment.getExternalStorageDirectory() + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
            String localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;


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
//                    zoomImg.setVisibility(View.VISIBLE);
                    } else {
                        gif = new FileInputStream(localPath);
                        questionImage.setVisibility(View.GONE);
                        questionGif.setVisibility(View.VISIBLE);
                        questionGif.setGifResource(gif);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Glide.with(getActivity())
                        .load(path)
                        .apply(new RequestOptions()
                                .placeholder(Drawable.createFromPath(localPath)))
                        .into(questionImage);
            }
/*            } else {
                String fileName = getFileName(scienceQuestion.getQid(), scienceQuestion.getPhotourl());
//                String localPath = Environment.getExternalStorageDirectory() + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
                String localPath = AssessmentApplication.assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
                Bitmap bitmap = BitmapFactory.decodeFile(localPath);
                questionImage.setImageBitmap(bitmap);
            }*/
        } else questionImage.setVisibility(View.GONE);


        radioButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                questionTypeListener.setAnswer("", radioButtonTrue.getText().toString(), scienceQuestion.getQid(), null);
                assessmentAnswerListener.setAnswerInActivity("", radioButtonTrue.getText().toString(), scienceQuestion.getQid(), null);
                radioButtonTrue.setSelected(true);
                radioButtonTrue.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_corner_dialog));
                radioButtonFalse.setBackground(getActivity().getResources().getDrawable(R.drawable.ripple_rectangle));
                radioButtonTrue.setTextColor(Assessment_Utility.selectedColor);
                radioButtonFalse.setSelected(false);
                radioButtonFalse.setTextColor(getActivity().getResources().getColor(R.color.white));
            }
        });

        radioButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assessmentAnswerListener.setAnswerInActivity("", radioButtonFalse.getText().toString(), scienceQuestion.getQid(), null);
//                questionTypeListener.setAnswer("", radioButtonFalse.getText().toString(), scienceQuestion.getQid(), null);
                radioButtonFalse.setSelected(true);
                radioButtonFalse.setTextColor(Assessment_Utility.selectedColor);
                radioButtonTrue.setSelected(false);
                radioButtonTrue.setBackground(getActivity().getResources().getDrawable(R.drawable.ripple_rectangle));
                radioButtonFalse.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_corner_dialog));
                radioButtonTrue.setTextColor(getActivity().getResources().getColor(R.color.white));
            }
        });
       /* rg_true_false.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == radioButtonTrue.getId()*//* && (!isFirstLoad)*//*) {
                    questionTypeListener.setAnswer("", radioButtonTrue.getText().toString(), scienceQuestion.getQid(), null);
                    radioButtonTrue.setSelected(true);
                    radioButtonTrue.setTextColor(Assessment_Utility.selectedColor);
                    radioButtonFalse.setSelected(false);
                    radioButtonFalse.setTextColor(context.getResources().getColor(R.color.white));

                } else if (checkedId == radioButtonFalse.getId() *//*&& (!isFirstLoad)*//*) {
                    questionTypeListener.setAnswer("", radioButtonFalse.getText().toString(), scienceQuestion.getQid(), null);
                    radioButtonFalse.setSelected(true);
                    radioButtonFalse.setTextColor(Assessment_Utility.selectedColor);
                    radioButtonTrue.setSelected(false);
                    radioButtonTrue.setTextColor(context.getResources().getColor(R.color.white));


                } else {
                    Toast.makeText(context, "Select Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        if (scienceQuestion.getUserAnswer().equalsIgnoreCase("true")) {
            radioButtonTrue.setSelected(true);
            radioButtonTrue.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_corner_dialog));
            radioButtonFalse.setBackground(getActivity().getResources().getDrawable(R.drawable.ripple_rectangle));
            radioButtonTrue.setTextColor(Assessment_Utility.selectedColor);
            radioButtonFalse.setSelected(false);
            radioButtonFalse.setTextColor(getActivity().getResources().getColor(R.color.white));


        } else if (scienceQuestion.getUserAnswer().equalsIgnoreCase("false")) {
            radioButtonFalse.setSelected(true);
            radioButtonFalse.setTextColor(Assessment_Utility.selectedColor);
            radioButtonTrue.setSelected(false);
            radioButtonTrue.setBackground(getActivity().getResources().getDrawable(R.drawable.ripple_rectangle));
            radioButtonFalse.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_corner_dialog));
            radioButtonTrue.setTextColor(getActivity().getResources().getColor(R.color.white));

        } else {
            radioButtonFalse.setSelected(false);
            radioButtonTrue.setSelected(false);
            radioButtonTrue.setTextColor(getActivity().getResources().getColor(R.color.white));
            radioButtonFalse.setTextColor(getActivity().getResources().getColor(R.color.white));

        }

    }

}
