package com.pratham.assessment_lib.domain;



import androidx.annotation.NonNull;

import java.io.Serializable;

public class Score implements Serializable {

    private int ScoreId;
    private String SessionID;
    private String StudentID;
    private String DeviceID;
    private String ResourceID;
    private int QuestionId;
    private int ScoredMarks;
    private int TotalMarks;
    private String StartDateTime;
    private String EndDateTime;
    private int Level;
    private String Label;
    private int sentFlag;
    private boolean isAttempted;
    private boolean isCorrect;
    private String userAnswer;
    private String examId;
    private String paperId;


    @Override
    public String toString() {
        return "Score{" +
                "ScoreId='" + ScoreId + '\'' +
                ", SessionID='" + SessionID + '\'' +
                ", StudentID='" + StudentID + '\'' +
                ", DeviceId='" + DeviceID + '\'' +
                ", ResourceID='" + ResourceID + '\'' +
                ", QuestionId=" + QuestionId +
                ", ScoredMarks=" + ScoredMarks +
                ", TotalMarks=" + TotalMarks +
                ", StartDateTime='" + StartDateTime + '\'' +
                ", EndDateTime='" + EndDateTime + '\'' +
                ", Level=" + Level +
                '}';
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean getIsAttempted() {
        return isAttempted;
    }

    public void setIsAttempted(boolean attempted) {
        isAttempted = attempted;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getSentFlag() {
        return sentFlag;
    }

    public void setSentFlag(int sentFlag) {
        this.sentFlag = sentFlag;
    }

    @NonNull
    public int getScoreId() {
        return ScoreId;
    }

    public void setScoreId(@NonNull int scoreId) {
        ScoreId = scoreId;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String sessionID) {
        SessionID = sessionID;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getResourceID() {
        return ResourceID;
    }

    public void setResourceID(String resourceID) {
        ResourceID = resourceID;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }

    public int getScoredMarks() {
        return ScoredMarks;
    }

    public void setScoredMarks(int scoredMarks) {
        ScoredMarks = scoredMarks;
    }

    public int getTotalMarks() {
        return TotalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        TotalMarks = totalMarks;
    }

    public String getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        StartDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return EndDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        EndDateTime = endDateTime;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }
}
