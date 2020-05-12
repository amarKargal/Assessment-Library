package com.pratham.assessment_lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.domain.AssessmentQuestion;
import com.pratham.assessment_lib.interfaces.QuestionTrackerListener;
import com.pratham.assessment_lib.science.bottomFragment.BottomQuestionFragment;

import java.util.List;

public class QuestionTrackerAdapter extends RecyclerView.Adapter<QuestionTrackerAdapter.MyViewHolder> {
    List<AssessmentQuestion> assessmentQuestionList;
    Context context;
    QuestionTrackerListener questionTrackerListener;
    BottomQuestionFragment bottomQuestionFragment;

    public QuestionTrackerAdapter(BottomQuestionFragment bottomQuestionFragment, Context context, List<AssessmentQuestion> assessmentQuestions) {
        this.assessmentQuestionList = assessmentQuestions;
        this.context = context;
        questionTrackerListener = (QuestionTrackerListener) context;
        this.bottomQuestionFragment=bottomQuestionFragment;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
//        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_question_no);
//            imageView = itemView.findViewById(R.id.iv_choice_image);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_question_tracker_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        AssessmentQuestion assessmentQuestion = assessmentQuestionList.get(i);
        final int queNo = i + 1;
        myViewHolder.text.setText(queNo + "");
        if (assessmentQuestion.getIsAttempted())
//            myViewHolder.text.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            myViewHolder.text.setBackground(context.getResources().getDrawable(R.drawable.ripple_round_attempted));
        else
            myViewHolder.text.setBackground(context.getResources().getDrawable(R.drawable.ripple_round_not_attempted));
//            myViewHolder.text.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        myViewHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionTrackerListener.onQuestionClick(queNo);
                bottomQuestionFragment.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return assessmentQuestionList.size();
    }
}
