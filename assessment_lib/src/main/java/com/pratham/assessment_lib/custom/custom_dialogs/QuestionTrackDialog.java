package com.pratham.assessment_lib.custom.custom_dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.domain.ScienceQuestion;

import java.util.List;




public class QuestionTrackDialog extends Dialog {

    TextView cancel;
    ImageButton btn_close;
    TextView tv_topics;
    RecyclerView rvQuestion;


    Context context;
    List<ScienceQuestion> scienceQuestionList;


    public QuestionTrackDialog(@NonNull Context context, List<ScienceQuestion> scienceQuestions) {
        super(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
        this.scienceQuestionList = scienceQuestions;
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_tracker_dialog);
       // initViews();
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        tv_topics.setText("Question Tracker");
       /* QuestionTrackerAdapter questionTrackerAdapter = new QuestionTrackerAdapter(this, context, scienceQuestionList);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(context, 5);
        rvQuestion.setLayoutManager(linearLayoutManager);
        rvQuestion.setAdapter(questionTrackerAdapter);*/

    }

  /*  private void initViews() {

        cancel = findViewById(R.id.txt_cancel);
        btn_close = findViewById(R.id.btn_close);
        tv_topics = findViewById(R.id.txt_message);
        rvQuestion = findViewById(R.id.rv_questions);
    }*/


   // @OnClick(R.id.btn_close)
    public void closeDialog(View v) {
        dismiss();

    }

    //@OnClick(R.id.txt_cancel)
    public void cancel(View v) {
        dismiss();
    }

    //@OnClick(R.id.txt_save)
    public void ok(View v) {

    }


}

