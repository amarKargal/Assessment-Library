package com.pratham.assessment_lib;

/**
 * Used for input for a library
 *
 * @auther pratham
 * @Version 1.0
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pratham.assessment_lib.domain.ScienceQuestion;
import com.pratham.assessment_lib.science.ScienceAssessmentActivity_;
import com.pratham.assessment_lib.supportive.ResponseListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AssessmentLibrary implements Serializable {
    private int BackGroundColor;
    private List<ScienceQuestion> questionList;
    private static AssessmentLibrary assessmentLibrary;
    private static Context mContext;
    private boolean isVideoMonitoring = false;
    private String storagePath;
    private String selectedLanguageCode = "1";

    private AssessmentLibrary() {
    }

    public static AssessmentLibrary getAssessmentLibrary(Context context) {
        if (assessmentLibrary == null && context != null) {
            mContext = context;
            assessmentLibrary = new AssessmentLibrary();
        }
        return assessmentLibrary;
    }


    public static void destroy() {
        assessmentLibrary = null;
    }

    public String getSelectedLanguageCode() {
        return selectedLanguageCode;
    }

    /**
     * Sets the language code
     *
     * @param selectedLanguageCode language code default is 1
     * @return object of AssessmentLibraryInput
     */
    public AssessmentLibrary setSelectedLanguageCode(String selectedLanguageCode) {
        this.selectedLanguageCode = selectedLanguageCode;
        return assessmentLibrary;
    }

    /**
     * gets the assigned background color code
     *
     * @return String contains path of local storage
     */
    public String getStoragePath() {
        return storagePath;
    }


    /**
     * Sets the assigned background color code
     *
     * @param storagePath String contains path of local storage
     * @return object of AssessmentLibraryInput
     */
    public AssessmentLibrary setStoragePath(String storagePath) {
        this.storagePath = storagePath;
        return assessmentLibrary;
    }

    /**
     * gets the assigned background color code
     *
     * @return A int representing color-code
     */
    public int getBackGroundColor() {
        return BackGroundColor;
    }

    /**
     * Sets the assigned background color code
     *
     * @param backGroundColor int contains background color of questions color-code
     * @return object of AssessmentLibraryInput
     */
    public AssessmentLibrary setBackGroundColor(int backGroundColor) {
        BackGroundColor = backGroundColor;
        return assessmentLibrary;
    }

    /**
     * gets the questions ArrayList
     *
     * @return A ArrayList representing all assignment questions
     */
    public List<ScienceQuestion> getQuestionList() {
        return questionList;
    }

    /**
     * gets the questions ArrayList
     *
     * @param questionList representing all questions to show in assignment
     * @return object of AssessmentLibraryInput
     */
    public AssessmentLibrary setQuestionList(ArrayList<ScienceQuestion> questionList) {
        this.questionList = questionList;
        return assessmentLibrary;
    }

    public ResponseListener build() {
        if (assessmentLibrary.questionList != null && assessmentLibrary.questionList.size() > 0) {
            if (storagePath != null && !storagePath.isEmpty()) {
                Intent intent = new Intent(mContext, ScienceAssessmentActivity_.class);
                intent.putExtra("AssessmentLibraryInput", assessmentLibrary);
                mContext.startActivity(intent);
                return ResponseListener.getResponseListener();
            } else {
                Log.e("AssessmentLibrary", "storage path may be null or empty");
                return null;
            }
        } else {
            Log.e("AssessmentLibrary", "question list is may be null or empty");
            return null;
        }
    }

    public boolean isVideoMonitoring() {
        return isVideoMonitoring;
    }

    /**
     * gets the flag for video Monitoring
     *
     * @param videoMonitoring is true then record video otherwise not default is false
     * @return object of AssessmentLibraryInput
     */
    public AssessmentLibrary setVideoMonitoring(boolean videoMonitoring) {
        isVideoMonitoring = videoMonitoring;
        return assessmentLibrary;
    }
}
