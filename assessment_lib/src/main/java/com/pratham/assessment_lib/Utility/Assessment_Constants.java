package com.pratham.assessment_lib.Utility;


import android.Manifest;

public class Assessment_Constants {
    // public static String STORING_IN;
    public static final String [] permissionsNeededAssessmentLib =new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};


    public static boolean VIDEO_MONITORING = false;
    public static String assessPath;
    public static int LIBRARY_PERMISSIONS=100;
    //public static boolean isShowcaseDisplayed=false;
    //  public static boolean isTablet=false;
    //  public static String SELECTED_EXAM_ID="2035";
    public static String SELECTED_LANGUAGE = "1";
    public static String EXAM_DURATION;
    //public static final String ASSESSMENT_TYPE = "practice";
    //public static final String SELECTED_SUBJECT_ID = "21";
    public static final String currentSession = "211111";
    //public static final String currentStudentID = "11111111";


    //Assessment question types
    public static final String MULTIPLE_CHOICE = "1";
    public static final String MULTIPLE_SELECT = "2";
    public static final String TRUE_FALSE = "3";
    public static final String MATCHING_PAIR = "4";
    public static final String FILL_IN_THE_BLANK_WITH_OPTION = "5";
    public static final String FILL_IN_THE_BLANK = "6";
    public static final String ARRANGE_SEQUENCE = "7";
    /*public static final String VIDEO = "8";
    public static final String AUDIO = "9";*/
    public static final String KEYWORDS_QUESTION = "11";
    public static final String IMAGE_ANSWER = "12";
    public static final String TEXT_PARAGRAPH = "13";

    /*public static String DOWNLOAD_MEDIA_TYPE_ANSWER_VIDEO = "answerVideo";
    public static String DOWNLOAD_MEDIA_TYPE_ANSWER_AUDIO = "answerAudio";
    public static String DOWNLOAD_MEDIA_TYPE_ANSWER_IMAGE = "answerImage";*/
    public static String STORE_DOWNLOADED_MEDIA_PATH = "/.Assessment/Content/Downloaded";
    public static String DOWNLOAD_MEDIA_TYPE_VIDEO_MONITORING = "videoMonitoring";
    public static String STORE_VIDEO_MONITORING_PATH = "/.Assessment/Content/VideoMonitoring";
    public static String STORE_ANSWER_MEDIA_PATH = "/.Assessment/Content/Answers";
    /*public static final String STT_REGEX = "[\"\\-+\\.'^?!@#%-_;&*,:|।<>()]";*/


    public static String ENGLISH_CODE = "en";
    public static String HINDI_CODE = "hi";
    public static String MARATHI_CODE = "mr";
    public static String GUJARATI_CODE = "gu";
    public static String KANNADA_CODE = "kn";
    public static String ASSAMESE_CODE = "en";
    public static String BENGALI_CODE = "bn";
    public static String PUNJABI_CODE = "en";
    public static String ODIA_CODE = "en";
    public static String TAMIL_CODE = "ta";
    public static String TELUGU_CODE = "te";
    public static String URDU_CODE = "ur";

    public static String ENGLISH_ID = "1";
    public static String HINDI_ID = "2";
    public static String MARATHI_ID = "3";
    public static String GUJARATI_ID = "7";
    public static String KANNADA_ID = "8";
    public static String ASSAMESE_ID = "9";
    public static String BENGALI_ID = "10";
    public static String PUNJABI_ID = "11";
    public static String ODIA_ID = "12";
    public static String TAMIL_ID = "13";
    public static String TELUGU_ID = "14";
    public static String URDU_ID = "15";


}
