package com.pratham.assessment_lib.viewpager_fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pratham.assessment_lib.Utility.Assessment_Constants;
import com.pratham.assessment_lib.adapter.MatchPairAdapter;
import com.pratham.assessment_lib.adapter.MatchPairDragDropAdapter;
import com.pratham.assessment_lib.custom.gif_viewer.GifView;
import com.pratham.assessment_lib.domain.AssessmentQuestion;
import com.pratham.assessment_lib.domain.SubOptions;
import com.pratham.assessment_lib.interfaces.StartDragListener;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.pratham.assessment_lib.Utility.Assessment_Constants.assessPath;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.getFileName;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.setOdiaFont;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.showZoomDialog;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.wiseF;


@EFragment(resName = "layout_match_the_pair_row")
public class MatchThePairFragment extends Fragment implements StartDragListener {
    @ViewById(resName ="tv_question")
    TextView question;
    @ViewById(resName ="iv_question_image")
    ImageView questionImage;
    @ViewById(resName ="iv_question_gif")
    GifView questionGif;
    @ViewById(resName ="rl_ans_options1")
    RecyclerView recyclerView1;
    @ViewById(resName ="rl_ans_options2")
    RecyclerView recyclerView2;
    private static final String POS = "pos";
    private static final String SCIENCE_QUESTION = "scienceQuestion";

    private int pos;
    private AssessmentQuestion assessmentQuestion;
    ItemTouchHelper touchHelper;

    @AfterViews
    public void init() {
        if (getArguments() != null) {
            pos = getArguments().getInt(POS, 0);
            assessmentQuestion = (AssessmentQuestion) getArguments().getSerializable(SCIENCE_QUESTION);
        }
        setMatchPairQuestion();
    }


    public MatchThePairFragment() {
        // Required empty public constructor
    }

    public static MatchThePairFragment newInstance(int pos, AssessmentQuestion assessmentQuestion) {
        MatchThePairFragment_ matchThePairFragment = new MatchThePairFragment_();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putSerializable("scienceQuestion", assessmentQuestion);
        matchThePairFragment.setArguments(args);
        return matchThePairFragment;
    }

/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pos = getArguments().getInt(POS, 0);
            scienceQuestion = (ScienceQuestion) getArguments().getSerializable(SCIENCE_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_match_the_pair_row, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setMatchPairQuestion();
    }*/

    public void setMatchPairQuestion() {
        setOdiaFont(getActivity(), question);
        question.setText(assessmentQuestion.getQname());
        final String fileName = getFileName(assessmentQuestion.getQid(), assessmentQuestion.getPhotourl());
//                String localPath = Environment.getExternalStorageDirectory() + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
        final String localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;

        if (!assessmentQuestion.getPhotourl().equalsIgnoreCase("")) {
            questionImage.setVisibility(View.VISIBLE);
//            if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {


            String path = assessmentQuestion.getPhotourl();
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
         /*   } else {
                String fileName = getFileName(scienceQuestion.getQid(), scienceQuestion.getPhotourl());
//                String localPath = Environment.getExternalStorageDirectory() + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
                String localPath = AssessmentApplication.assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
                Bitmap bitmap = BitmapFactory.decodeFile(localPath);
                questionImage.setImageBitmap(bitmap);
            }*/
        } else questionImage.setVisibility(View.GONE);


        questionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showZoomDialog(getActivity(), assessmentQuestion.getPhotourl(), localPath);
            }
        });
        questionGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showZoomDialog(getActivity(), assessmentQuestion.getPhotourl(), localPath);
            }
        });

        List<SubOptions> AnswerList = new ArrayList<>();

        if (!assessmentQuestion.getUserAnswer().equalsIgnoreCase("")) {
            String[] ansIds = assessmentQuestion.getUserAnswer().split(",");
            for (int i = 0; i < ansIds.length; i++) {
                if (ansIds[i].equalsIgnoreCase(assessmentQuestion.getMatchingNameList().get(i).getQcid()))
                    AnswerList.add(assessmentQuestion.getMatchingNameList().get(i));
            }

        }

        List<SubOptions> pairList = new ArrayList<>();
        List<SubOptions> shuffledList = new ArrayList<>();

        pairList.clear();
//        pairList = AppDatabase.getDatabaseInstance(context).getScienceQuestionChoicesDao().getQuestionChoicesByQID(scienceQuestion.getQid());
        pairList = assessmentQuestion.getLstquestionchoice();
        Log.d("wwwwwwwwwww", pairList.size() + "");
        if (!pairList.isEmpty()) {
          /*  for (int p = 0; p < pairList.size(); p++) {
                list1.add(pairList.get(p).getChoicename());
                list2.add(pairList.get(p).getMatchingname());
            }*/
            MatchPairAdapter matchPairAdapter = new MatchPairAdapter(pairList, getActivity());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView1.setLayoutManager(linearLayoutManager);
            recyclerView1.setAdapter(matchPairAdapter);
            if (assessmentQuestion.getMatchingNameList() == null) {
                shuffledList.clear();

                shuffledList.addAll(pairList);
                Collections.shuffle(shuffledList);
                Collections.shuffle(shuffledList);
            } else {
                if (AnswerList.size() > 0)
                    shuffledList.addAll(AnswerList);
                else {
                    shuffledList = assessmentQuestion.getMatchingNameList();
                    Collections.shuffle(shuffledList);
                }

            }

            MatchPairDragDropAdapter matchPairDragDropAdapter = new MatchPairDragDropAdapter(this, shuffledList, getActivity());
            ItemTouchHelper.Callback callback =
                    new ItemMoveCallback(matchPairDragDropAdapter);
            touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(null);
            touchHelper.attachToRecyclerView(recyclerView2);

            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView2.setLayoutManager(linearLayoutManager1);
            recyclerView2.setAdapter(matchPairDragDropAdapter);
            Log.d("wwwwwwwwwww", pairList.size() + "");
        }

    }


    @Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }
}
