package com.pratham.assessment_lib.interfaces;


import com.pratham.assessment_lib.domain.SubOptions;

import java.util.List;

public interface AssessmentAnswerListener {


    void setAnswerInActivity(String ansId, String answer, String qid, List<SubOptions> list);
//    void setVideoResult(Intent intent, int videoCapture, ScienceQuestion scienceQuestion);
//    void setImageCaptureResult(ScienceQuestion scienceQuestion);
    void setAudio(String fileName, boolean isRecording);
}
