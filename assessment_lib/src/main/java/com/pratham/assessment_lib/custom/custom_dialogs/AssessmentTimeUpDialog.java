package com.pratham.assessment_lib.custom.custom_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.interfaces.QuestionTrackerListener;


public class AssessmentTimeUpDialog extends Dialog {


    Button btn_ok;
    Context context;
    QuestionTrackerListener questionTrackerListener;

    public AssessmentTimeUpDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        this.context = context;
        questionTrackerListener = (QuestionTrackerListener) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_time_up_dialog);
       btn_ok=findViewById(R.id.btn_ok_time_up);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        setCanceledOnTouchOutside(false);

    }

 /*  // @OnClick(R.id.btn_ok_time_up)
    public void closeDialog(View v) {
        ((Activity) context).finish();
        dismiss();
        questionTrackerListener.onSaveAssessmentClick();
    }*/
}

