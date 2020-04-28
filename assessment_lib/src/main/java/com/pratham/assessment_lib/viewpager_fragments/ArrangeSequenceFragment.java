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
import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.Utility.Assessment_Constants;
import com.pratham.assessment_lib.Utility.Assessment_Utility;
import com.pratham.assessment_lib.adapter.ArrangeSeqDragDropAdapter;
import com.pratham.assessment_lib.custom.gif_viewer.GifView;
import com.pratham.assessment_lib.domain.ScienceQuestion;
import com.pratham.assessment_lib.domain.ScienceQuestionChoice;
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
import static com.pratham.assessment_lib.Utility.Assessment_Utility.setOdiaFont;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.showZoomDialog;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.wiseF;


@EFragment(resName = "layout_arrange_seq_row")
public class ArrangeSequenceFragment extends Fragment implements StartDragListener {
    @ViewById(resName = "tv_question")
    TextView question;
    @ViewById(resName = "iv_question_image")
    ImageView questionImage;
    @ViewById(resName = "iv_question_gif")
    GifView questionGif;
    @ViewById(resName = "rl_arrange_seq")
    RecyclerView recyclerArrangeSeq;

    private static final String POS = "pos";
    private static final String SCIENCE_QUESTION = "scienceQuestion";

    private int pos;
    private ScienceQuestion scienceQuestion;
    ItemTouchHelper touchHelper;


    public ArrangeSequenceFragment() {
        // Required empty public constructor
    }

    public static ArrangeSequenceFragment newInstance(int pos, ScienceQuestion scienceQuestion) {
        ArrangeSequenceFragment_ arrangeSequenceFragment = new ArrangeSequenceFragment_();
        Bundle args = new Bundle();
        args.putInt(POS, pos);
        args.putSerializable(SCIENCE_QUESTION, scienceQuestion);
        arrangeSequenceFragment.setArguments(args);
        return arrangeSequenceFragment;
    }


    @AfterViews
    public void init() {
        if (getArguments() != null) {
            pos = getArguments().getInt(POS, 0);
            scienceQuestion = (ScienceQuestion) getArguments().getSerializable(SCIENCE_QUESTION);
        }
        setArrangeSeqQuestion();

    }

    /*  @Override
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
          return inflater.inflate(R.layout.layout_arrange_seq_row, container, false);
      }

      @Override
      public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
          super.onViewCreated(view, savedInstanceState);
          ButterKnife.bind(this, view);
          setArrangeSeqQuestion();

      }
  */
    public void setArrangeSeqQuestion() {
        question.setText(scienceQuestion.getQname());
        setOdiaFont(getActivity(), question);

        final String fileName = Assessment_Utility.getFileName(scienceQuestion.getQid(), scienceQuestion.getPhotourl());
        final String localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;
        if (!scienceQuestion.getPhotourl().equalsIgnoreCase("")) {
            questionImage.setVisibility(View.VISIBLE);


//            if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
            //                    zoomImg.setVisibility(View.VISIBLE);
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
           /* } else {
           //                String localPath= Environment.getExternalStorageDirectory()+Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH+"/"+fileName;
                Bitmap bitmap = BitmapFactory.decodeFile(localPath);
                questionImage.setImageBitmap(bitmap);
            }*/
        } else questionImage.setVisibility(View.GONE);

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


        List<ScienceQuestionChoice> AnswerList = new ArrayList<>();

        if (!scienceQuestion.getUserAnswer().equalsIgnoreCase("")) {
            String[] ansIds = scienceQuestion.getUserAnswer().split(",");
            for (int i = 0; i < ansIds.length; i++) {
                if (ansIds[i].equalsIgnoreCase(scienceQuestion.getMatchingNameList().get(i).getQcid()))
                    AnswerList.add(scienceQuestion.getMatchingNameList().get(i));
            }

        }


//        List list1 = new ArrayList();
        List<ScienceQuestionChoice> shuffledList = new ArrayList<>();
        //todo alter
         //    List<ScienceQuestionChoice> pairList = AppDatabase.getDatabaseInstance(getActivity()).getScienceQuestionChoicesDao().getQuestionChoicesByQID(scienceQuestion.getQid());
        List<ScienceQuestionChoice> pairList=scienceQuestion.getLstquestionchoice();
        Log.d("wwwwwwwwwww", pairList.size() + "");
        if (!pairList.isEmpty()) {
          /*  for (int p = 0; p < pairList.size(); p++) {
                list1.add(pairList.get(p).getChoicename());
            }*/

            if (scienceQuestion.getMatchingNameList() == null) {
                shuffledList.clear();

                shuffledList.addAll(pairList);
                Collections.shuffle(shuffledList);
                Collections.shuffle(shuffledList);
            } else {
                if (AnswerList.size() > 0)
                    shuffledList.addAll(AnswerList);
                else {
                    shuffledList = scienceQuestion.getMatchingNameList();
                    Collections.shuffle(shuffledList);
                }

            }


            ArrangeSeqDragDropAdapter dragDropAdapter = new ArrangeSeqDragDropAdapter(this, shuffledList, getActivity());
            ItemTouchHelper.Callback callback =
                    new ItemMoveCallback(dragDropAdapter);
            touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(null);
            touchHelper.attachToRecyclerView(recyclerArrangeSeq);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerArrangeSeq.setLayoutManager(linearLayoutManager1);
            recyclerArrangeSeq.setAdapter(dragDropAdapter);

        }
    }

    @Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);

    }


}
