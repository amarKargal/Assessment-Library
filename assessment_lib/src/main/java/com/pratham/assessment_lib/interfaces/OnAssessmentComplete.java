package com.pratham.assessment_lib.interfaces;

import com.pratham.assessment_lib.domain.AssessmentQuestion;

import java.util.List;
@FunctionalInterface
public interface OnAssessmentComplete {
    public void OnResult(List<AssessmentQuestion> questionList);
}
