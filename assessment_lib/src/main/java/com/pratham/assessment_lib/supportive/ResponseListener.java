package com.pratham.assessment_lib.supportive;

import com.pratham.assessment_lib.domain.AssessmentQuestion;
import com.pratham.assessment_lib.interfaces.OnAssessmentComplete;

import java.util.ArrayList;
import java.util.List;

public class ResponseListener implements OnAssessmentComplete {
    public static ResponseListener responseListener;
    private OnAssessmentComplete onAssessmentComplete;

    private ResponseListener() {

    }

    public static ResponseListener getResponseListener() {
        if (responseListener == null) {
            responseListener = new ResponseListener();
        }
        return responseListener;
    }

    public void setOnAssessmentCompleteListener(OnAssessmentComplete onAssessmentComplete) {
        this.onAssessmentComplete = onAssessmentComplete;
    }

    @Override
    public void OnResult(List questionList) {
        if (onAssessmentComplete != null) {
            if (questionList != null)
                onAssessmentComplete.OnResult(questionList);
            else
                onAssessmentComplete.OnResult(new ArrayList<AssessmentQuestion>());
        }
    }
}
