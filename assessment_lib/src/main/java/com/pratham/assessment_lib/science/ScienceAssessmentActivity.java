package com.pratham.assessment_lib.science;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.snackbar.Snackbar;
import com.pratham.assessment_lib.AssessmentLibrary;
import com.pratham.assessment_lib.BaseActivity;
import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.Utility.Assessment_Constants;
import com.pratham.assessment_lib.Utility.Assessment_Utility;
import com.pratham.assessment_lib.Utility.AudioUtil;
import com.pratham.assessment_lib.custom.NonSwipeableViewPager;
import com.pratham.assessment_lib.custom.cameraRecorder.CameraRecorder;
import com.pratham.assessment_lib.custom.cameraRecorder.VideoMonitor;
import com.pratham.assessment_lib.custom.circular_progress_view.AnimationStyle;
import com.pratham.assessment_lib.custom.circular_progress_view.CircleView;
import com.pratham.assessment_lib.custom.circular_progress_view.CircleViewAnimation;
import com.pratham.assessment_lib.custom.custom_dialogs.AssessmentTimeUpDialog;
import com.pratham.assessment_lib.custom.ticker.TickerView;
import com.pratham.assessment_lib.discrete_view.DiscreteScrollView;
import com.pratham.assessment_lib.domain.AssessmentPatternDetails;
import com.pratham.assessment_lib.domain.AssessmentQuestion;
import com.pratham.assessment_lib.domain.SubOptions;
import com.pratham.assessment_lib.interfaces.AssessmentAnswerListener;
import com.pratham.assessment_lib.interfaces.QuestionTrackerListener;
import com.pratham.assessment_lib.science.bottomFragment.BottomQuestionFragment;
import com.pratham.assessment_lib.science.camera.VideoMonitoringService;
import com.pratham.assessment_lib.supportive.ResponseListener;
import com.pratham.assessment_lib.viewpager_fragments.ViewpagerAdapter;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static com.pratham.assessment_lib.Utility.Assessment_Constants.ARRANGE_SEQUENCE;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.FILL_IN_THE_BLANK;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.FILL_IN_THE_BLANK_WITH_OPTION;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.IMAGE_ANSWER;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.KEYWORDS_QUESTION;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.LIBRARY_PERMISSIONS;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.MATCHING_PAIR;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.MULTIPLE_CHOICE;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.MULTIPLE_SELECT;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.TEXT_PARAGRAPH;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.TRUE_FALSE;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.assessPath;
import static com.pratham.assessment_lib.Utility.Assessment_Constants.permissionsNeededAssessmentLib;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.selectedColor;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.wiseF;


@EActivity(resName = "activity_science_assessment")
public class ScienceAssessmentActivity extends BaseActivity implements DiscreteScrollView.OnItemChangedListener, AssessmentAnswerListener, QuestionTrackerListener {


    public static ViewpagerAdapter viewpagerAdapter;
    public static int ExamTime = 0;
    static boolean isActivityRunning = false;
    @ViewById(resName = "timer_progress_bar")
    public ProgressBar timer_progress_bar;
    /*  @BindView(R.id.ll_count_down)
      public RelativeLayout ll_count_down;*/
  /*  @ViewById(resName = "rl_exam_info")
    public RelativeLayout rl_exam_info;*/
    @ViewById(resName = "rl_que")
    public RelativeLayout rl_que;
    /*List<String> examIDList = new ArrayList<>();
    List<String> topicIdList = new ArrayList<>();
    List<DownloadMedia> downloadMediaList;*/
    Intent serviceIntent;
    Fragment currentFragment;
    ProgressDialog progressDialog, mediaProgressDialog;
    List<AssessmentQuestion> assessmentQuestionList = new ArrayList<>();

    List<AssessmentPatternDetails> assessmentPatternDetails;
    /*
        @BindView(R.id.circle_progress_bar)
        public ProgressBar circle_progress_bar;*/
/*    List<String> downloadFailedExamList = new ArrayList<>();
    int mediaDownloadCnt = 0;
    int queDownloadIndex = 0;
    int paperPatternCnt = 0;*/
    int ansCnt = 0, queCnt = 0;
    boolean showSubmit = false;
    List attemptedQIds = new ArrayList();
    boolean timesUp = false;
    @ViewById(resName = "fragment_view_pager")
    NonSwipeableViewPager fragment_view_pager;
    @ViewById(resName = "dots_indicator")
    WormDotsIndicator dots_indicator;
    @ViewById(resName = "tv_timer")
    TextView tv_timer;
    @ViewById(resName = "btn_save_Assessment")
    ImageView btn_save_Assessment;
    @ViewById(resName = "btn_submit")
    Button btn_submit;
    @ViewById(resName = "circle_view")
    CircleView circle_view;
    @ViewById(resName = "texture_view")
    FrameLayout texture_view;
    @ViewById(resName = "iv_prev")
    ImageView iv_prev;
    @ViewById(resName = "txt_prev")
    TextView txt_prev;
    @ViewById(resName = "txt_next")
    TextView txt_next;
    /*@ViewById(resName = "tv_exam_name")
    TextView tv_exam_name;
    @ViewById(resName = "tv_marks")
    TextView tv_marks;
    @ViewById(resName = "tv_time")
    TextView tv_time;
    @ViewById(resName = "tv_total_que")
    TextView tv_total_que;*/
    @ViewById(resName = "frame_video_monitoring")
    FrameLayout frame_video_monitoring;

    @ViewById(resName = "tickerView")
    TickerView tickerView;
    @ViewById(resName = "txt_question_cnt")
    TextView txt_question_cnt;
    boolean isEndTimeSet = false;

    // int totalMarks = 0, outOfMarks = 0;
    String examStartTime, examEndTime;
    String answer = "", ansId = "";
    String questionType = "";
    CountDownTimer mCountDownTimer;
    String supervisorId;
    String assessmentSession;
    // private int correctAnsCnt = 0, wrongAnsCnt = 0, skippedCnt = 0;
    @Extra("AssessmentLibraryInput")
    AssessmentLibrary assessmentLibrary;

    // call back of exam completed or back pressed
    ResponseListener responseListener;


    @AfterExtras
    public void checkIntentValue() {
        assessmentQuestionList = assessmentLibrary.getQuestionList();
        Assessment_Constants.VIDEOMONITORING = assessmentLibrary.isVideoMonitoring();
        Assessment_Utility.initWiseF(getApplicationContext());
        assessPath = assessmentLibrary.getStoragePath();
        Assessment_Constants.SELECTED_LANGUAGE = assessmentLibrary.getSelectedLanguageCode();
        Assessment_Constants.EXAM_DURATION = assessmentLibrary.getSelectedLanguageCode();
        //if color not set then black color is default color
        try {
            Assessment_Utility.setDefaultColor(assessmentLibrary.getBackGroundColor());
        } catch (Exception e) {
            Assessment_Utility.setDefaultColor(getResources().getColor(R.color.black));
        }
    }

    @AfterViews
    public void init() {
//        Assessment_Constants.assessmentSession=assessmentSession;
        //= (AssessmentLibraryInput) getIntent().getSerializableExtra("AssessmentLibraryInput");

       /* iv_prev.setOnClickListener(v -> {
        });*/

        //todo alter
        rl_que.setBackgroundColor(selectedColor);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supervisorId = getIntent().getStringExtra("crlId");
        responseListener = ResponseListener.getResponseListener();
//        subjectId = getIntent().getStringExtra("subId");
        // subjectId = Assessment_Constants.SELECTED_SUBJECT_ID;
        //subjectId = FastSave.getInstance().getString("SELECTED_SUBJECT_ID", "1");
        progressDialog = new ProgressDialog(this);
        mediaProgressDialog = new ProgressDialog(this);
//        showSelectTopicDialog();
        if (checkWhetherAllPermissionsPresentForPhotoTagging(permissionsNeededAssessmentLib)) {
            if (Assessment_Constants.VIDEOMONITORING) {
                VideoMonitor.initCamera(this, texture_view);
                frame_video_monitoring.setVisibility(View.VISIBLE);
           /* btn_save_Assessment.setVisibility(View.GONE);
            txt_next.setVisibility(View.GONE);*/
                //   serviceIntent = new Intent(getApplicationContext(), VideoMonitoringService.class);
            }
            generatePaperPattern();
        } else {
            requestRunTimePermissions(ScienceAssessmentActivity.this, permissionsNeededAssessmentLib, LIBRARY_PERMISSIONS);
        }

        //downloadPaperPattern();
      /*  if (wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
            downloadPaperPattern();
        } else {
            AssessmentPaperPattern assessmentPaperPatterns = AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getAssessmentPaperPatternDao().getAssessmentPaperPatternsByExamId(Assessment_Constants.SELECTED_EXAM_ID);
            if (assessmentPaperPatterns != null) {
                generatePaperPattern();
//                showQuestions();
            } else {
                finish();
                Toast.makeText(this, "Connect to internet to download paper format.", Toast.LENGTH_SHORT).show();
            }
        }*/

     /*   AssessmentPaperPattern assessmentPaperPatterns = AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getAssessmentPaperPatternDao().getAssessmentPaperPatternsByExamId(Assessment_Constants.SELECTED_EXAM_ID);
        if (assessmentPaperPatterns != null) {
            generatePaperPattern();
            showQuestions();
        } else {
            if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
                downloadPaperPattern();
            } else {
                finish();
                Toast.makeText(this, "Check internet connection to download paper", Toast.LENGTH_SHORT).show();
            }
        }*/

        //getTopicData();
        //scienceModalClassList = fetchJson("science.json");

        // setQuestions();


        Resources res = getResources();
// Change locale settings in the app.

        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale("en");
        res.updateConfiguration(conf, dm);
    }


   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_assessment);
        ButterKnife.bind(this);
        assessmentSession = "" + UUID.randomUUID().toString();
//        Assessment_Constants.assessmentSession=assessmentSession;


        rl_que.setBackgroundColor(Assessment_Utility.selectedColor);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supervisorId = getIntent().getStringExtra("crlId");
//        subjectId = getIntent().getStringExtra("subId");
//        subjectId = Assessment_Constants.SELECTED_SUBJECT_ID;
        subjectId = FastSave.getInstance().getString("SELECTED_SUBJECT_ID", "1");
        progressDialog = new ProgressDialog(this);
        mediaProgressDialog = new ProgressDialog(this);
//        showSelectTopicDialog();
        if (Assessment_Constants.VIDEOMONITORING) {
            frame_video_monitoring.setVisibility(View.VISIBLE);
            btn_save_Assessment.setVisibility(View.GONE);
            txt_next.setVisibility(View.GONE);
            serviceIntent = new Intent(getApplicationContext(), VideoMonitoringService.class);
        }
        if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
            downloadPaperPattern();
        } else {
            AssessmentPaperPattern assessmentPaperPatterns = AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getAssessmentPaperPatternDao().getAssessmentPaperPatternsByExamId(Assessment_Constants.SELECTED_EXAM_ID);
            if (assessmentPaperPatterns != null) {
                generatePaperPattern();
//                showQuestions();
            } else {
                finish();
                Toast.makeText(this, "Connect to internet to download paper format.", Toast.LENGTH_SHORT).show();
            }
        }

     *//*   AssessmentPaperPattern assessmentPaperPatterns = AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getAssessmentPaperPatternDao().getAssessmentPaperPatternsByExamId(Assessment_Constants.SELECTED_EXAM_ID);
        if (assessmentPaperPatterns != null) {
            generatePaperPattern();
            showQuestions();
        } else {
            if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {
                downloadPaperPattern();
            } else {
                finish();
                Toast.makeText(this, "Check internet connection to download paper", Toast.LENGTH_SHORT).show();
            }
        }*//*

        //getTopicData();
        //scienceModalClassList = fetchJson("science.json");

        // setQuestions();

        Assessment_Constants.isShowcaseDisplayed = false;


        Resources res = getResources();
// Change locale settings in the app.

        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale("en");
        res.updateConfiguration(conf, dm);
    }*/


    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

    }

   /* @Override
    public void getSelectedItems(List<String> examIdList, String selectedLang, List<AssessmentToipcsModal> topics) {
        *//*this.exams = exams;
        for (int i = 0; i < examIDList.size(); i++) {
            insertTopicsToDB(examIDList.get(i));
        }*//*
        this.examIDList = examIdList;

//        String subId = AppDatabase.getDatabaseInstance(this).getSubjectDao().getIdByName(selectedSub);
        String langId = AppDatabase.getDatabaseInstance(this).getLanguageDao().getLangIdByName(selectedLang);
        //  topicIdList = AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getAssessmentPatternDetailsDao().getDistinctTopicIds();

//        AssessmentPaperPattern pattern=new AssessmentPaperPattern();
//        pattern=AppDatabase.getDatabaseInstance(this).getAssessmentPaperPatternDao().getAssessmentPaperPatternsByExamId()
*//*        List<String> examsToDownload = new ArrayList<>();
        List<AssessmentPatternDetails> patternDetails;
        if (!topicIdList.isEmpty()) {
            for (int i = 0; i < examIdList.size(); i++) {
                    String examId = examIdList.get(i);
                    patternDetails = AppDatabase.getDatabaseInstance(this).getAssessmentPatternDetailsDao()
                            .getAssessmentPatternDetailsByExamId(examId);
                    if (patternDetails.size()==0) {
                        examsToDownload.add(examId);
                    }else {

                    }
                }

            if (examsToDownload.size()>0) {
                this.examIDList = examsToDownload;
                downloadPaperPattern(examIDList.get(paperPatternCnt), langId, subId);
            }

        } else {*//*
        progressDialog.show();
        paperPatternCnt = 0;

//        downloadPaperPattern(examIdList.get(paperPatternCnt), langId, subjectId);
       *//* }


        topicIdList = AppDatabase.getDatabaseInstance(this).getAssessmentPatternDetailsDao().getDistinctTopicIds();
        downloadQuestions(topicIdList.get(queDownloadIndex), langId, subId);
*//*
    }
*/
   /* @Override
    public void getSelectedTopic(String exam, String selectedLang, SelectTopicDialog selectTopicDialog) {
//        String topicId = AppDatabase.getDatabaseInstance(this).getAssessmentTopicDao().getTopicIdByTopicName(topic);
        String examId = AppDatabase.getDatabaseInstance(this).getAssessmentPaperPatternDao().getExamIdByExamName(exam);
//        String subId = AppDatabase.getDatabaseInstance(this).getSubjectDao().getIdByName(selectedSub);
//        langId = AppDatabase.getDatabaseInstance(this).getLanguageDao().getLangIdByName(selectedLang);

//        generatePaperPattern(examId, subjectId, Assessment_Constants.SELECTED_LANGUAGE);

        showQuestions();

    }
*/

   /* private void downloadQuestions(final String topicId) {
        String questionUrl = APIs.AssessmentQuestionAPI + "languageid=" + Assessment_Constants.SELECTED_LANGUAGE + "&subjectid=" + subjectId + "&topicid=" + topicId;
        progressDialog.show();
        progressDialog.setMessage("Downloading questions...");
        AndroidNetworking.get(questionUrl)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        progressDialog.dismiss();
                        if (response.length() > 0) {
                            insertQuestionsToDB(response);
                            queDownloadIndex++;
                            if (queDownloadIndex < topicIdList.size()) {
                                if (downloadFailedExamList.size() == 0)
                                    downloadQuestions(topicIdList.get(queDownloadIndex));
                            } else {
                                progressDialog.dismiss();
                                if (downloadFailedExamList.size() == 0) {
//                                    showSelectTopicDialog();
                                    generatePaperPattern();
//                                    showQuestions();
                                }

                            }
                        } else if (response.length() == 0) {
                            queDownloadIndex++;
                            if (queDownloadIndex < topicIdList.size())
                                downloadQuestions(topicIdList.get(queDownloadIndex));
                            else {
                                progressDialog.dismiss();
                                if (downloadFailedExamList.size() == 0) {
//                                    showSelectTopicDialog();
                                    generatePaperPattern();
//                                    showQuestions();
                                }
                            }
                        } else {
                            progressDialog.dismiss();
                            if (downloadFailedExamList.size() == 0) {
//                                showSelectTopicDialog();
                                generatePaperPattern();
//                                showQuestions();
                            }
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ScienceAssessmentActivity.this, "Error in loading..Check internet connection", Toast.LENGTH_SHORT).show();
                       *//* AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getAssessmentPaperPatternDao().deletePaperPatterns();
                        progressDialog.dismiss();
                        finish();*//*
                    }
                });
    }*/

    /*  private void downloadPaperPattern(*//*final String examId, final String langId,
                                      final String subId*//*) {
        progressDialog.show();
        progressDialog.setMessage("Downloading paper pattern...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        AndroidNetworking.get(APIs.AssessmentPaperPatternAPI + Assessment_Constants.SELECTED_EXAM_ID)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        boolean isCameraQuestion = false;
                        Gson gson = new Gson();
                        AssessmentPaperPattern assessmentPaperPattern = gson.fromJson(response, AssessmentPaperPattern.class);
                        //todo alter
                        *//* if (assessmentPaperPattern != null)
                            AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getAssessmentPaperPatternDao().insertPaperPattern(assessmentPaperPattern);
*//*
                        List<AssessmentPatternDetails> assessmentPatternDetails = assessmentPaperPattern.getLstpatterndetail();
                        for (int i = 0; i < assessmentPatternDetails.size(); i++) {
                            assessmentPatternDetails.get(i).setExamId(assessmentPaperPattern.getExamid());
                        }

                       *//* if (!assessmentPatternDetails.isEmpty())
                            insertPatternDetailsToDB(assessmentPatternDetails);*//*

     *//* paperPatternCnt++;
                        if (paperPatternCnt < examIDList.size()) {
                            downloadPaperPattern();
                        } else {*//*
                        progressDialog.dismiss();
//                            for (int i = 0; i < examIDList.size(); i++) {
                        List<String> topicsList = new ArrayList<>();
                        topicsList.add("21");
                        *//*List<String> topicsList = AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this)
                                .getAssessmentPatternDetailsDao().getTopicsByExamId(Assessment_Constants.SELECTED_EXAM_ID);*//*
                        for (int j = 0; j < topicsList.size(); j++) {
                            if (!topicIdList.contains(topicsList.get(j)))
                                topicIdList.add(topicsList.get(j));
                        }
//                            }
                        if (downloadFailedExamList.size() == 0)
                            if (topicIdList.size() > 0) {
                                queDownloadIndex = 0;
                                downloadQuestions(topicIdList.get(queDownloadIndex));
                            } else finish();*//*if (!downloadTopicDialog.isShowing())
                                    downloadTopicDialog.show();*//*

//                            selectTopicDialog.show();

                        *//*if (downloadFailedExamList.size() > 0)
                            showDownloadFailedDialog(Assessment_Constants.SELECTED_LANGUAGE);*//*
//                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    *//*    downloadFailedExamList.add(AppDatabase.getDatabaseInstance
                                (ScienceAssessmentActivity.this).getTestDao().getExamNameById(Assessment_Constants.SELECTED_EXAM_ID));
                        paperPatternCnt++;
                      *//**//*  if (paperPatternCnt < examIDList.size()) {

                            downloadPaperPattern(examIDList.get(paperPatternCnt), langId, subId);
                        } else {*//**//*
                        progressDialog.dismiss();
//                            selectTopicDialog.show();


                        if (downloadFailedExamList.size() > 0)
                            showDownloadFailedDialog(Assessment_Constants.SELECTED_LANGUAGE);
                 *//*       *//* }*//*
//                        progressDialog.dismiss();
//                        Toast.makeText(ScienceAssessmentActivity.this, "Error downloading paper pattern..", Toast.LENGTH_SHORT).show();
                    }
                });


    }
*/
   /* private void showDownloadFailedDialog(final String langId) {
        String failedExams = "";
        for (int i = 0; i < downloadFailedExamList.size(); i++) {
            failedExams = failedExams + "\n" + downloadFailedExamList.get(i);
        }
        new AlertDialog.Builder(this)
                .setTitle("Download Failed")
                .setCancelable(false)
                .setMessage("Download failed for following exams : " + failedExams)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        for (int i = 0; i < examIDList.size(); i++) {
                            List<String> topicsList = AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this)
                                    .getAssessmentPatternDetailsDao().getTopicsByExamId(examIDList.get(i));
                            for (int j = 0; j < topicsList.size(); j++) {
                                if (topicIdList.contains(topicsList.get(j)))
                                    topicIdList.add(topicsList.get(j));
                            }
                        }
                        downloadFailedExamList.clear();
                        if (topicIdList.size() > 0)
                            downloadQuestions(topicIdList.get(queDownloadIndex));
                        else finish();

                            *//* if (!downloadTopicDialog.isShowing())
                            downloadTopicDialog.show();*//*
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }*/

    /*private void insertPatternDetailsToDB(List<AssessmentPatternDetails> paperPatterns) {
        for (int i = 0; i < paperPatterns.size(); i++) {
            AppDatabase.getDatabaseInstance(this).getAssessmentPatternDetailsDao().deletePatternDetailsByExamId(paperPatterns.get(i).getExamId());
        }
        AppDatabase.getDatabaseInstance(this).getAssessmentPatternDetailsDao().insertAllPatternDetails(paperPatterns);

    }*/

    /*private void insertQuestionsToDB(JSONArray response) {
        try {
            downloadMediaList = new ArrayList<>();
            Gson gson = new Gson();
            String jsonOutput = response.toString();
            Type listType = new TypeToken<List<ScienceQuestion>>() {
            }.getType();
            List<ScienceQuestion> scienceQuestionList = gson.fromJson(jsonOutput, listType);
            Log.d("hhh", scienceQuestionList.toString());

            if (scienceQuestionList.size() > 0) {
                AppDatabase.getDatabaseInstance(this).getScienceQuestionDao().deleteByLangIdSubIdTopicId(scienceQuestionList.get(0).getTopicid(), Assessment_Constants.SELECTED_LANGUAGE, Assessment_Constants.SELECTED_SUBJECT_ID);
                AppDatabase.getDatabaseInstance(this).getScienceQuestionDao().insertAllQuestions(scienceQuestionList);
                for (int i = 0; i < scienceQuestionList.size(); i++) {
                    if (scienceQuestionList.get(i).getLstquestionchoice().size() > 0) {
                        List<ScienceQuestionChoice> choiceList = scienceQuestionList.get(i).getLstquestionchoice();
                        if (choiceList.size() > 0) {
                            AppDatabase.getDatabaseInstance(this).getScienceQuestionChoicesDao().deleteQuestionChoicesByQID(scienceQuestionList.get(i).getQid());
                            AppDatabase.getDatabaseInstance(this).getScienceQuestionChoicesDao().insertAllQuestionChoices(choiceList);
                            for (int j = 0; j < choiceList.size(); j++) {
                                if (!choiceList.get(j).getChoiceurl().equalsIgnoreCase("")) {
                                    DownloadMedia downloadMedia = new DownloadMedia();
                                    downloadMedia.setPhotoUrl(*//*Assessment_Constants.loadOnlineImagePath + *//*choiceList.get(j).getChoiceurl());
                                    downloadMedia.setqId(scienceQuestionList.get(i).getQid());
                                    downloadMedia.setQtId(scienceQuestionList.get(i).getQtid());
                                    downloadMedia.setPaperId(assessmentSession);
                                    downloadMedia.setMediaType("optionImage");
                                    downloadMediaList.add(downloadMedia);
                                }
                            }
                        }
                    }
                    if (!scienceQuestionList.get(i).getPhotourl().equalsIgnoreCase("")) {
                        DownloadMedia downloadMedia = new DownloadMedia();
                        downloadMedia.setPhotoUrl(*//*Assessment_Constants.loadOnlineImagePath + *//*scienceQuestionList.get(i).getPhotourl());
                        downloadMedia.setqId(scienceQuestionList.get(i).getQid());
                        downloadMedia.setQtId(scienceQuestionList.get(i).getQtid());
                        downloadMedia.setMediaType("questionImage");
                        downloadMedia.setPaperId(assessmentSession);
                        downloadMediaList.add(downloadMedia);


                    }


                }
//                progressDialog.dismiss();
                if (downloadMediaList.size() > 0) {

                    mediaProgressDialog.setTitle("Downloading media please wait..");
//                    mediaProgressDialog.setMessage("Progress : ");

                    mediaProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mediaProgressDialog.setProgress(0);
                    mediaProgressDialog.setMax(downloadMediaList.size());
                    mediaProgressDialog.setCancelable(false);
                    mediaProgressDialog.show();
//                    if (downloadMediaList.get(mediaDownloadCnt).getQtId().contains("8") || downloadMediaList.get(mediaDownloadCnt).getQtId().contains("9"))
                    File direct = new File(AssessmentApplication.assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH);
                    if (!direct.exists())
                        direct.mkdir();
                    if (direct.exists())
                        downloadMedia(downloadMediaList.get(mediaDownloadCnt).getqId(), downloadMediaList.get(mediaDownloadCnt).getPhotoUrl());
                    else {
                        Toast.makeText(this, "Media download failed..", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            }
            BackupDatabase.backup(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /* private void insertQuestionsToDB(JSONArray response) {
     *//*  try {
            downloadMediaList = new ArrayList<>();
            Gson gson = new Gson();
            String jsonOutput = response.toString();
            Type listType = new TypeToken<List<ScienceQuestion>>() {
            }.getType();
            List<ScienceQuestion> scienceQuestionList = gson.fromJson(jsonOutput, listType);
            Log.d("hhh", scienceQuestionList.toString());

            if (scienceQuestionList.size() > 0) {
                AppDatabase.getDatabaseInstance(this).getScienceQuestionDao().deleteByLangIdSubIdTopicId(scienceQuestionList.get(0).getTopicid(), Assessment_Constants.SELECTED_LANGUAGE, Assessment_Constants.SELECTED_SUBJECT_ID);
                for (int i = 0; i < scienceQuestionList.size(); i++) {
                    if (scienceQuestionList.get(i).getLstquestionchoice().size() > 0) {
                        List<ScienceQuestionChoice> choiceList = scienceQuestionList.get(i).getLstquestionchoice();
                        if (choiceList.size() > 0) {
                            AppDatabase.getDatabaseInstance(this).getScienceQuestionChoicesDao().deleteQuestionChoicesByQID(scienceQuestionList.get(i).getQid());
                            AppDatabase.getDatabaseInstance(this).getScienceQuestionChoicesDao().insertAllQuestionChoices(choiceList);
                            for (int j = 0; j < choiceList.size(); j++) {
                                if (!choiceList.get(j).getChoiceurl().equalsIgnoreCase("")) {
                                    DownloadMedia downloadMedia = new DownloadMedia();
                                    downloadMedia.setPhotoUrl(*//**//*Assessment_Constants.loadOnlineImagePath + *//**//*choiceList.get(j).getChoiceurl());
                                    downloadMedia.setqId(scienceQuestionList.get(i).getQid());
                                    downloadMedia.setQtId(scienceQuestionList.get(i).getQtid());
                                    downloadMedia.setPaperId(assessmentSession);
                                    downloadMedia.setMediaType("optionImage");
                                    downloadMediaList.add(downloadMedia);
                                }
                                if (!choiceList.get(j).getMatchingurl().equalsIgnoreCase("")) {
                                    DownloadMedia downloadMedia = new DownloadMedia();
                                    downloadMedia.setPhotoUrl(*//**//*Assessment_Constants.loadOnlineImagePath + *//**//*choiceList.get(j).getMatchingurl());
                                    downloadMedia.setqId(scienceQuestionList.get(i).getQid());
                                    downloadMedia.setQtId(scienceQuestionList.get(i).getQtid());
                                    downloadMedia.setPaperId(assessmentSession);
                                    downloadMedia.setMediaType("optionImage");
                                    downloadMediaList.add(downloadMedia);
                                }
                                if (scienceQuestionList.get(i).getQtid().equalsIgnoreCase(MULTIPLE_CHOICE)) {
                                    if (choiceList.get(j).getCorrect().equalsIgnoreCase("true")) {
                                        if (choiceList.get(j).getChoiceurl().equalsIgnoreCase(""))
                                            scienceQuestionList.get(i).setAnswer(choiceList.get(j).getChoicename());
                                    }
                                }
                            }
                        }
                    }
                    AppDatabase.getDatabaseInstance(this).getScienceQuestionDao().insertAllQuestions(scienceQuestionList);

                    if (!scienceQuestionList.get(i).getPhotourl().equalsIgnoreCase("")) {
                        DownloadMedia downloadMedia = new DownloadMedia();
                        downloadMedia.setPhotoUrl(*//**//*Assessment_Constants.loadOnlineImagePath + *//**//*scienceQuestionList.get(i).getPhotourl());
                        downloadMedia.setqId(scienceQuestionList.get(i).getQid());
                        downloadMedia.setQtId(scienceQuestionList.get(i).getQtid());
                        downloadMedia.setMediaType("questionImage");
                        downloadMedia.setPaperId(assessmentSession);
                        downloadMediaList.add(downloadMedia);


                    }


                }
//                progressDialog.dismiss();
                if (downloadMediaList.size() > 0) {

                    mediaProgressDialog.setTitle("Downloading media please wait..");
//                    mediaProgressDialog.setMessage("Progress : ");

                    mediaProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mediaProgressDialog.setProgress(0);
                    mediaProgressDialog.setMax(downloadMediaList.size());
                    mediaProgressDialog.setCancelable(false);
                    mediaProgressDialog.show();
//                    if (downloadMediaList.get(mediaDownloadCnt).getQtId().contains("8") || downloadMediaList.get(mediaDownloadCnt).getQtId().contains("9"))
                    File direct = new File(assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH);
                    if (!direct.exists())
                        direct.mkdir();
                    if (direct.exists())
                        downloadMedia(downloadMediaList.get(mediaDownloadCnt).getqId(), downloadMediaList.get(mediaDownloadCnt).getPhotoUrl());
                    else {
                        Toast.makeText(this, "Media download failed..", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            }
            // BackupDatabase.backup(this);
        } catch (Exception e) {
            e.printStackTrace();
        }*//*
    }

    private void downloadMedia(String qid, String photoUrl) {

//        String dirPath = Environment.getExternalStorageDirectory().toString() + "/.Assessment/Content/Downloaded";
        String dirPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH;

        String fileName = getFileName(qid, photoUrl);
        AndroidNetworking.download(photoUrl, dirPath, fileName)
//                .setTag("downloadTest")
//                .setPriority(Priority.MEDIUM)
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        // do anything with progress
//                        int progress = (int) (bytesDownloaded / totalBytes);
                        mediaProgressDialog.setProgress(mediaDownloadCnt);
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
//                        progressDialog.dismiss();
//                        downloadMediaList.get(mediaDownloadCnt).setDownloadSuccessful(true);
//                        mediaProgressDialog.setMessage("Progress : " + mediaDownloadCnt + "/" + downloadMediaList.size());

                        mediaDownloadCnt++;

                        if (mediaDownloadCnt < downloadMediaList.size())
                            downloadMedia(downloadMediaList.get(mediaDownloadCnt).getqId(), downloadMediaList.get(mediaDownloadCnt).getPhotoUrl());
                        else mediaProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        progressDialog.dismiss();
//                        downloadMediaList.get(mediaDownloadCnt).setDownloadSuccessful(true);
                        Log.d("media error:::", downloadMediaList.get(mediaDownloadCnt).getPhotoUrl() + " " + downloadMediaList.get(mediaDownloadCnt).getqId());
//                        if (AssessmentApplication.wiseF.isDeviceConnectedToMobileOrWifiNetwork()) {

                        final Dialog dialog = new Dialog(ScienceAssessmentActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.exit_dialog);
                        dialog.setCanceledOnTouchOutside(false);
                        TextView title = dialog.findViewById(R.id.dia_title);
                        Button skip_btn = dialog.findViewById(R.id.dia_btn_restart);
                        Button restart_btn = dialog.findViewById(R.id.dia_btn_exit);
                        Button cancel_btn = dialog.findViewById(R.id.dia_btn_cancel);
                        cancel_btn.setVisibility(View.VISIBLE);
                        title.setText("Media download failed..!");
                        restart_btn.setText("Skip All");
                        skip_btn.setText("Skip this");
                        cancel_btn.setText("Cancel");
                        dialog.show();

                        skip_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mediaDownloadCnt++;
                                if (mediaDownloadCnt < downloadMediaList.size()) {
                                    downloadMedia(downloadMediaList.get(mediaDownloadCnt).getqId(), downloadMediaList.get(mediaDownloadCnt).getPhotoUrl());
                                } else
                                    mediaProgressDialog.dismiss();
                                dialog.dismiss();
                            }
                        });

                        restart_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mediaDownloadCnt = downloadMediaList.size();
                                *//*if (mediaDownloadCnt < downloadMediaList.size()) {
                                    downloadMedia(downloadMediaList.get(mediaDownloadCnt).getqId(), downloadMediaList.get(mediaDownloadCnt).getPhotoUrl());
                                } else*//*
                                mediaProgressDialog.dismiss();
                                dialog.dismiss();

                            }
                        });

                        cancel_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mediaProgressDialog.dismiss();
                                //todo alter
                               // AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getScienceQuestionDao().deleteQuestionByExamId(scienceQuestionList.get(0).getExamid());
                                finish();
                            }
                        });

                        *//*} else {
                            Toast.makeText(ScienceAssessmentActivity.this, "Connect to internet", Toast.LENGTH_SHORT).show();
                            finish();
                        }*//*



     *//* mediaDownloadCnt++;
                        if (mediaDownloadCnt < downloadMediaList.size()) {
                            downloadMedia(downloadMediaList.get(mediaDownloadCnt).getqId(), downloadMediaList.get(mediaDownloadCnt).getPhotoUrl());
                        } else*//*
     *//* mediaProgressDialog.dismiss();
                        AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getScienceQuestionDao().deleteQuestionByExamId(scienceQuestionList.get(0).getExamid());
                        Toast.makeText(ScienceAssessmentActivity.this, "Error downloading Media..Try again", Toast.LENGTH_SHORT).show();
                        finish();*//*
                    }
                });
    }*/


    private void generatePaperPattern() {
        //todo alter
     /*   assessmentPaperPatterns = AppDatabase.getDatabaseInstance(this).getAssessmentPaperPatternDao().getAssessmentPaperPatternsByExamId(Assessment_Constants.SELECTED_EXAM_ID);
        assessmentPatternDetails = AppDatabase.getDatabaseInstance(this).getAssessmentPatternDetailsDao().getAssessmentPatternDetailsByExamId(Assessment_Constants.SELECTED_EXAM_ID);
     */   // topicIdList = AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getAssessmentPatternDetailsDao().getDistinctTopicIds();
/*
        assessmentPaperPatterns = new AssessmentPaperPattern();
        assessmentPaperPatterns.setSubjectname("Internet Literacy");
        assessmentPaperPatterns.setExamname("Single Search- Basic");
        assessmentPaperPatterns.setExamduration("20");
        assessmentPaperPatterns.setCertificateQuestion1("Are you familiar with Single Search- Basic course?");
        assessmentPaperPatterns.setCertificateQuestion2("Do you have knowledge about Single Search- Basic course?");
        assessmentPaperPatterns.setCertificateQuestion3("Do you understand Single Search- Basic course?");
        assessmentPaperPatterns.setCertificateQuestion4("");
        assessmentPaperPatterns.setCertificateQuestion5("");
        assessmentPaperPatterns.setOutofmarks("50");
        assessmentPaperPatterns.setExamid("2035");
        assessmentPaperPatterns.setSubjectid("21");*/

        assessmentPatternDetails = new ArrayList<>();
        AssessmentPatternDetails assessmentPattern = new AssessmentPatternDetails();
        assessmentPattern.setTotalmarks("50");
        assessmentPattern.setNoofquestion("1");
        assessmentPattern.setQtname("Multiple Choice");
        assessmentPattern.setMarksperquestion("2");
        assessmentPattern.setTopicid("21");
        assessmentPattern.setQlevel("2");
        assessmentPattern.setTopicname("Lesson 2 - Single Level Search");
        assessmentPattern.setExamId("2035");
        assessmentPattern.setPatternId(1);
        assessmentPattern.setQtid("1");


        assessmentPatternDetails.add(assessmentPattern);


        boolean isCameraQuestion = false;

        if (assessmentPatternDetails.size() > 0)
            for (int j = 0; j < assessmentPatternDetails.size(); j++) {
                int noOfQues = Integer.parseInt(assessmentPatternDetails.get(j).getNoofquestion());


                List<AssessmentQuestion> assessmentQuestions = new ArrayList<>();
                /*if (!assessmentPaperPatterns.getSubjectid().equalsIgnoreCase("30") || !assessmentPaperPatterns.getSubjectname().equalsIgnoreCase("aser")) {
                    scienceQuestions = AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).
                            getScienceQuestionDao().getQuestionListByPattern(Assessment_Constants.SELECTED_LANGUAGE,
                            subjectId, assessmentPatternDetails.get(j).getTopicid(),
                            assessmentPatternDetails.get(j).getQtid(), assessmentPatternDetails.get(j).getQlevel(), noOfQues);
                } else {
                    scienceQuestions = AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).
                            getScienceQuestionDao().getQuestionListByPatternForAser(Assessment_Constants.SELECTED_LANGUAGE,
                            subjectId, assessmentPatternDetails.get(j).getTopicid(),
                            assessmentPatternDetails.get(j).getQtid(), assessmentPatternDetails.get(j).getQlevel(), noOfQues);

                }*/


/*
                scienceQuestions.add(scienceQuestion);
                scienceQuestions.add(scienceQuestion2);
*/
                for (int i = 0; i < assessmentQuestions.size(); i++) {
                    assessmentQuestions.get(i).setOutofmarks(assessmentPatternDetails.get(j).getMarksperquestion());
                    assessmentQuestions.get(i).setExamid(assessmentPatternDetails.get(j).getExamId());
                }
                if (assessmentQuestions.size() > 0)
                    assessmentQuestionList.addAll(assessmentQuestions);

                String qtid = assessmentPatternDetails.get(j).getQtid();
                if (qtid.equalsIgnoreCase("8") || qtid.equalsIgnoreCase("12"))
                    isCameraQuestion = true;

            }
        if (isCameraQuestion) {

            VideoMonitoringService.releaseMediaRecorder();
            Assessment_Constants.VIDEOMONITORING = false;
            texture_view.setVisibility(View.GONE);
            tv_timer.setTextColor(Color.BLACK);
            frame_video_monitoring.setVisibility(View.GONE);
//                btn_save_Assessment.setVisibility(View.VISIBLE);
//            Toast.makeText(this, "video monitoring not prepared", Toast.LENGTH_LONG).show();
        }


    /*    if (!assessmentPaperPatterns.getSubjectid().equalsIgnoreCase("30") || !assessmentPaperPatterns.getSubjectname().equalsIgnoreCase("aser"))
            Collections.shuffle(scienceQuestionList);*/

        if (assessmentQuestionList.size() <= 0) {
            finish();
            if (!wiseF.isDeviceConnectedToMobileOrWifiNetwork())
                Toast.makeText(ScienceAssessmentActivity.this, "Connect to internet to download questions.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(ScienceAssessmentActivity.this, "No questions.", Toast.LENGTH_SHORT).show();
        }
        showQuestions();

     /*   tv_exam_name.setText("Exam : " + assessmentPaperPatterns.getExamname());
        tv_time.setText("Time : " + assessmentPaperPatterns.getExamduration() + " mins.");
        tv_marks.setText("Marks : " + assessmentPaperPatterns.getOutofmarks());
        tv_total_que.setText("Total questions : " + scienceQuestionList.size());
*/
       /* swipe_btn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                // user has swiped the btn. Perform your async operation now
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (scienceQuestionList.isEmpty()) {
                            Toast.makeText(ScienceAssessmentActivity.this, "No questions", Toast.LENGTH_SHORT).show();
                            swipe_btn.showResultIcon(false);
                        } else {
                            swipe_btn.showResultIcon(true);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showQuestions();
                                }
                            }, 2500);
                        }


                        // task success! show TICK icon in ProSwipeButton
                        // false if task failed
                    }
                }, 2000);
            }
        });
*/

    }


    private void showQuestions() {

//        ll_count_down.setVisibility(View.GONE);
//        rl_exam_info.setVisibility(View.GONE);
        rl_que.setVisibility(View.VISIBLE);
//        showStartAssessment();
        setProgressBarAndTimer();

        if (Assessment_Constants.VIDEOMONITORING) {

            //******* Video monitoring *******

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startCameraService();
                }
            }, 1000);
        }
        // ****** circle 5 sec time ****
     /*       final long period = 50;
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //this repeats every 100 ms
                    if (i < 100) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                             *//*   selectTopicDialog.timer.setText(String.valueOf(i)+"%");
                                selectTopicDialog.timer.setText("" + time);
                                selectTopicDialog*//*
                                circle_progress_bar.setProgress(i);
                                Log.d("progress", "" + i);
                            }
                        });
//                        progressBar.setProgress(i);
                        i++;
                    } else {
                        //closing the timer
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                selectTopicDialog.dismiss();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
//                                        selectTopicDialog.dismiss();
                                        ll_count_down.setVisibility(View.GONE);
                                        rl_que.setVisibility(View.VISIBLE);
                                        setProgressBarAndTimer();
                                    }
                                }, 800);
                            }
                        });
                        timer.cancel();
                        timer.purge();
                    }
                }
            }, 0, period);
*/

//            selectTopicDialog.dismiss();

        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(), this, assessmentQuestionList);
//        fragment_view_pager.setOffscreenPageLimit(scienceQuestionList.size());
        fragment_view_pager.setSaveFromParentEnabled(true);
        fragment_view_pager.setAdapter(viewpagerAdapter);
        dots_indicator.setViewPager(fragment_view_pager);
        currentFragment = viewpagerAdapter.getItem(0);
        examStartTime = Assessment_Utility.getCurrentDateTime();
        assessmentQuestionList.get(0).setStartTime(Assessment_Utility.getCurrentDateTime());

        iv_prev.setVisibility(View.GONE);
        txt_prev.setVisibility(View.GONE);
        txt_question_cnt.setText("Question : " + "1" + "/" + assessmentQuestionList.size());

        fragment_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                  /*  Toast.makeText(ScienceAssessmentActivity.this,
                            "Selected page position: " + position, Toast.LENGTH_SHORT).show();
                  */
                if (position > 0) {
                    assessmentQuestionList.get(position - 1).setEndTime(Assessment_Utility.getCurrentDateTime());
                    assessmentQuestionList.get(position).setStartTime(Assessment_Utility.getCurrentDateTime());
                }
                if (!showSubmit)
                    btn_submit.setVisibility(View.GONE);
                else
                    btn_submit.setVisibility(View.VISIBLE);


                Assessment_Utility.HideInputKeypad(ScienceAssessmentActivity.this);
                if (position == 0) {
                    iv_prev.setVisibility(View.GONE);
                    txt_prev.setVisibility(View.GONE);
                } else {
                    iv_prev.setVisibility(View.VISIBLE);
                    txt_prev.setVisibility(View.VISIBLE);
                }

//                if (!Assessment_Constants.VIDEOMONITORING) {
                if (position == assessmentQuestionList.size() - 1) {
                    btn_save_Assessment.setVisibility(View.GONE);
                    showDoneAnimation();
                } else {
                    if (!Assessment_Constants.VIDEOMONITORING) {
//                        btn_save_Assessment.setBackground(getResources().getDrawable(R.drawable.ripple_round));
                        btn_save_Assessment.setImageDrawable(getResources().getDrawable(R.drawable.ic_right_arrow));
                        btn_save_Assessment.setVisibility(View.VISIBLE);
                        txt_next.setVisibility(View.VISIBLE);
                    } else {
//                        btn_save_Assessment.setVisibility(View.GONE);
//                        txt_next.setVisibility(View.GONE);
                    }
//                    }
                }
                queCnt = position;
                txt_question_cnt.setText("Question : " + (position + 1) + "/" + assessmentQuestionList.size());

                if (currentFragment != null) {
                    currentFragment.onPause();
                }
                currentFragment = viewpagerAdapter.getItem(position);

            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });


    /*    fragment_view_pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                *//*if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else*//*
                if (position <= 1) { // [-1,1]
                    view.setAlpha(1);
                    // Counteract the default slide transition
                    view.setTranslationX(view.getWidth() * -position);

                    //set Y position to swipe in from top s
                    float yPosition = position * view.getHeight();
                    view.setTranslationY(yPosition);

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }

            }
        });*/


        fragment_view_pager.setCurrentItem(0);

//            ScienceAdapter scienceAdapter = new ScienceAdapter(this, scienceQuestionList);
       /* discreteScrollView.setOrientation(DSVOrientation.HORIZONTAL);
        discreteScrollView.addOnItemChangedListener(this);
        discreteScrollView.setItemTransitionTimeMillis(200);
        discreteScrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.5f)
                .build());
//            discreteScrollView.setAdapter(scienceAdapter);

        discreteScrollView.addScrollListener(new DiscreteScrollView.ScrollListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {
                scienceQuestionList.get(currentPosition).setEndTime(Assessment_Utility.getCurrentDateTime());
                scienceQuestionList.get(newPosition).setStartTime(Assessment_Utility.getCurrentDateTime());
                Log.d("bbbbbbb", currentPosition + "");
            }
        });
        discreteScrollView.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
//                queCnt = adapterPosition + 1;
                currentCount.setText(queCnt + "/" + scienceQuestionList.size());
                currentViewHolder = viewHolder;

                Assessment_Utility.HideInputKeypad(ScienceAssessmentActivity.this);
//                    step_view.go(adapterPosition, true);

            }

        });*/
//            scienceAdapter.notifyDataSetChanged();
//        }
    }

    private void showDoneAnimation() {
        Animation animation;
        /*animation = AnimationUtils.loadAnimation(this, R.anim.pop_out);
        btn_save_Assessment.startAnimation(animation);*/
        btn_save_Assessment.setVisibility(View.GONE);
        txt_next.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                btn_save_Assessment.setBackgroundResource(android.R.color.transparent);
//                if (scienceQuestionList.size() == (queCnt + 1)) {
                btn_save_Assessment.setVisibility(View.GONE);
//                    btn_save_Assessment.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_36dp));
//                }
                /* else {
                    btn_save_Assessment.setBackground(getResources().getDrawable(R.drawable.ripple_round));
                }*/
                Animation animation = AnimationUtils.loadAnimation(ScienceAssessmentActivity.this, R.anim.zoom_in);
                if (!Assessment_Constants.VIDEOMONITORING) {
//                    btn_save_Assessment.setVisibility(View.VISIBLE);
//                    btn_save_Assessment.startAnimation(animation);
                    if (assessmentQuestionList.size() == (queCnt + 1)) {
                        btn_submit.setVisibility(View.VISIBLE);
                        btn_submit.startAnimation(animation);
                        txt_next.setVisibility(View.GONE);
                        dots_indicator.setVisibility(View.GONE);
                    }
                } else {
                    if (assessmentQuestionList.size() == (queCnt + 1)) {
                        btn_submit.setVisibility(View.VISIBLE);
                        dots_indicator.setVisibility(View.GONE);

                    }
                }
                showSubmit = true;
            }
        }, 500);
    }

    @Click(resName = "btn_submit")
    public void onSubmitClick() {
       /* if (mCountDownTimer != null)
            mCountDownTimer.cancel();*/
        assessmentQuestionList.get(queCnt).setEndTime(Assessment_Utility.getCurrentDateTime());
        //todo alter
        BottomQuestionFragment bottomQuestionFragment = new BottomQuestionFragment();
        if (!bottomQuestionFragment.isVisible()) {
            bottomQuestionFragment.show(getSupportFragmentManager(), BottomQuestionFragment.class.getSimpleName());
            Bundle args = new Bundle();
            args.putSerializable("questionList", (Serializable) assessmentQuestionList);
            bottomQuestionFragment.setArguments(args);
        }
    }


    private void setProgressBarAndTimer() {
//        progressBarTimer.setProgress(100);
        ExamTime = Integer.parseInt(Assessment_Constants.EXAM_DURATION);
//        ExamTime = 1;
        if (ExamTime == 0)
            ExamTime = 30;
        final int timer = ExamTime * 60000;

      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startCameraService();
            }
        }, 800);
*/



     /*   new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {*/

         /*   }
        }, 1000);*/


        setCircleViewAnimation();


        final long period = 1000;
        final int n = (int) (ExamTime * 60000 / period);
        tickerView.setCharacterLists("" + ExamTime);
        final Timer examTimer = new Timer();
        circle_view.setMaximumValue(n);
/*        examTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100 ms
                if (tick < n) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            circle_view.setProgressValue(tick);
                        }
                    });
                    tick++;
                } else {
                    //closing the timer
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timesUp = true;
                            if (isActivityRunning) {
                                try {
                                    AssessmentTimeUpDialog timeUpDialog = new AssessmentTimeUpDialog(ScienceAssessmentActivity.this);
                                    timeUpDialog.show();
                                    if (mCountDownTimer != null)
                                        mCountDownTimer.cancel();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    examTimer.cancel();
                    examTimer.purge();
                }
            }
        }, 0, period);*/


        mCountDownTimer = new CountDownTimer(timer, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                tickerView.setText(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                ));

                tv_timer.setText(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                ));
            }

            @Override
            public void onFinish() {
                timesUp = true;
                isEndTimeSet = true;
                examEndTime = Assessment_Utility.getCurrentDateTime();
                if (isActivityRunning) {
                    try {
                        AssessmentTimeUpDialog timeUpDialog = new AssessmentTimeUpDialog(ScienceAssessmentActivity.this);
                        timeUpDialog.show();
//                        progressBarTimer.setProgress(0);
//                        isActivityRunning=false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                onSaveAssessmentClick();
//                Toast.makeText(ScienceAssessmentActivity.this, "Time up...", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mCountDownTimer.start();
    }


    private void setCircleViewAnimation() {
        CircleViewAnimation circleViewAnimation = new CircleViewAnimation()
                .setCircleView(circle_view)
                .setAnimationStyle(AnimationStyle.CONTINUOUS)
                .setDuration(circle_view.getProgressValue())
                .setCustomAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Animation Starts

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Animation Ends
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                }).setTimerOperationOnFinish(new Runnable() {
                    @Override
                    public void run() {
                        // Run when the duration reaches 0. Regardless of the AnimationLifecycle or main thread.
                        // Runs and triggers on background.
                    }
                })

                .setCustomInterpolator(new LinearInterpolator());

        circle_view.setAnimation(circleViewAnimation);
        circle_view.setProgressStep(10);
        circleViewAnimation.start();
    }

    @Override
    public void onBackPressed() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.exit_dialog);
        dialog.setCanceledOnTouchOutside(false);
        TextView title = dialog.findViewById(R.id.dia_title);
        Button exit_btn = dialog.findViewById(R.id.dia_btn_exit);
        Button restart_btn = dialog.findViewById(R.id.dia_btn_restart);
        Button cancel_btn = dialog.findViewById(R.id.dia_btn_cancel);
        cancel_btn.setVisibility(View.GONE);

        title.setText("Do you want to leave assessment?");
        restart_btn.setText("No");
        exit_btn.setText("Yes");
        dialog.show();

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ScienceAssessmentActivity.super.onBackPressed();
//                chronometer.stop();
                if (mCountDownTimer != null)
                    mCountDownTimer.cancel();
                saveAttemptedQuestionsInDB();
                endTestSession();

            }
        });

        restart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }


    @Click(resName = "iv_prev")
    public void prevClick() {

        Assessment_Utility.HideInputKeypad(this);
        Animation animation;

        if (queCnt > 0) {

            assessmentQuestionList.get(queCnt - 1).setEndTime(Assessment_Utility.getCurrentDateTime());
            queCnt--;
            fragment_view_pager.setCurrentItem(queCnt);
//            discreteScrollView.smoothScrollToPosition(queCnt - 1);
            assessmentQuestionList.get(queCnt).setStartTime(Assessment_Utility.getCurrentDateTime());
        }


//        currentCount.setText(queCnt + "/" + scienceQuestionList.size());
//        step_view.go(queCnt, true);


    }


    //    @OnClick(R.id.iv_next)
    public void nextClick() {

        Assessment_Utility.HideInputKeypad(this);
        Animation animation;

        if (queCnt < assessmentQuestionList.size()) {

            assessmentQuestionList.get(queCnt).setEndTime(Assessment_Utility.getCurrentDateTime());
//            discreteScrollView.smoothScrollToPosition(queCnt);
            queCnt++;
            fragment_view_pager.setCurrentItem(queCnt);
            assessmentQuestionList.get(queCnt).setStartTime(Assessment_Utility.getCurrentDateTime());

        } /*else if (queCnt == scienceQuestionList.size()) {
            queCnt = scienceQuestionList.size();


        }*/
//        currentCount.setText(queCnt + "/" + scienceQuestionList.size());
//        step_view.go(queCnt, true);


    }


    @Override
    public void setAnswerInActivity(String ansId, String answer, String qid, List<SubOptions> list) {
        if (!ansId.equalsIgnoreCase("") || !answer.equalsIgnoreCase("")) {
            if (!attemptedQIds.contains(qid)) {
                attemptedQIds.add(qid);
                ansCnt++;
            }
            for (int i = 0; i < assessmentQuestionList.size(); i++) {
                if (assessmentQuestionList.get(i).getQid().equalsIgnoreCase(qid)) {
                    assessmentQuestionList.get(i).setIsAttempted(true);
                    if (answer != null) {
                        assessmentQuestionList.get(i).setUserAnswer(answer);
                        assessmentQuestionList.get(i).setUserAnswerId(ansId);
//                        scienceQuestionList.get(i).setMarksPerQuestion(marksPerQuestion);
                    } else {
                        assessmentQuestionList.get(i).setUserAnswer("");
                        assessmentQuestionList.get(i).setUserAnswerId("");
                        assessmentQuestionList.get(i).setUserAnswerId("");
                    }
                    break;
                }
            }
        } else if (list != null) {
            for (int i = 0; i < assessmentQuestionList.size(); i++) {
                if (assessmentQuestionList.get(i).getQid().equalsIgnoreCase(qid)) {
                    assessmentQuestionList.get(i).setMatchingNameList(list);
                }
            }
            if (!attemptedQIds.contains(qid)) {
                attemptedQIds.add(qid);
                ansCnt++;
            }
            for (int i = 0; i < assessmentQuestionList.size(); i++) {
                if (assessmentQuestionList.get(i).getQid().equalsIgnoreCase(qid)) {
                    assessmentQuestionList.get(i).setIsAttempted(true);
                    if (assessmentQuestionList.get(i).getMatchingNameList().size() > 0) {
                        if (assessmentQuestionList.get(i).getQtid().getQuestionID().equalsIgnoreCase(MATCHING_PAIR) || assessmentQuestionList.get(i).getQtid().getQuestionID().equalsIgnoreCase(MULTIPLE_SELECT) || assessmentQuestionList.get(i).getQtid().getQuestionID().equalsIgnoreCase(ARRANGE_SEQUENCE)) {
                            String ans = "";
                            for (int m = 0; m < assessmentQuestionList.get(i).getMatchingNameList().size(); m++) {
                                if (assessmentQuestionList.get(i).getQtid().getQuestionID().equalsIgnoreCase(MULTIPLE_SELECT)) {
                                    if (assessmentQuestionList.get(i).getMatchingNameList().get(m).getMyIscorrect().equalsIgnoreCase("true"))
                                        ans += assessmentQuestionList.get(i).getMatchingNameList().get(m).getQcid() + ",";

                                } else
                                    ans += assessmentQuestionList.get(i).getMatchingNameList().get(m).getQcid() + ",";
                            }
                            assessmentQuestionList.get(i).setUserAnswer(ans);
                        } else {
                            assessmentQuestionList.get(i).setUserAnswer(assessmentQuestionList.get(i).getMatchingNameList().get(0).getChoicename());
                            assessmentQuestionList.get(i).setUserAnswerId(assessmentQuestionList.get(i).getMatchingNameList().get(0).getQcid());
//                        scienceQuestionList.get(i).setMarksPerQuestion(marksPerQuestion);
                        }
                    } else {
                        assessmentQuestionList.get(i).setUserAnswer("");
                        assessmentQuestionList.get(i).setUserAnswerId(ansId);
                    }
                    break;
                }
            }
        }
        this.answer = answer;
        if (ansId.equalsIgnoreCase(""))
            this.ansId = "-1";
        checkAssessment(queCnt);
    }

    /*  @Override
      public void setImageCaptureResult(final ScienceQuestion scienceQuestion) {
          final ChooseImageDialog chooseImageDialog = new ChooseImageDialog(this);
          imageFileName = scienceQuestion.getQid() + "_" + scienceQuestion.getPaperid() + ".jpg";

  //            String path = Environment.getExternalStorageDirectory().toString() + "/.Assessment/Content/Answers/" + fileName;
          imageFilePath = AssessmentApplication.assessPath + Assessment_Constants.STORE_ANSWER_MEDIA_PATH;

          chooseImageDialog.btn_take_photo.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  chooseImageDialog.cancel();
                  if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                      String[] permissionArray = new String[]{PermissionUtils.Manifest_CAMERA};

                      if (!isPermissionsGranted(ScienceAssessmentActivity.this, permissionArray)) {
                          Toast.makeText(ScienceAssessmentActivity.this, "Give Camera permissions through settings and restart the app.", Toast.LENGTH_SHORT).show();
                      } else {
  //                        imageName = Assessment_Utility.getFileName(scienceQuestion.getQid())
                          scienceQuestion.setUserAnswer(imageFileName);
  //                        selectedImage = selectedImageTemp;
                          Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                          startActivityForResult(takePicture, CAPTURE_IMAGE);
                      }
                  } else {
  //                    imageName = entryID + "_" + dde_questions.getQuestionId() + ".jpg";
                      scienceQuestion.setUserAnswer(imageFileName);
  //                    selectedImage = selectedImageTemp;
                      Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                      startActivityForResult(takePicture, CAPTURE_IMAGE);
                  }
              }
          });

          chooseImageDialog.btn_choose_from_gallery.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  chooseImageDialog.cancel();
                  if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                      String[] permissionArray = new String[]{PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE};

                      if (!isPermissionsGranted(ScienceAssessmentActivity.this, permissionArray)) {
                          Toast.makeText(ScienceAssessmentActivity.this, "Give Storage permissions through settings and restart the app.", Toast.LENGTH_SHORT).show();
                      } else {
  //                        imageName = entryID + "_" + dde_questions.getQuestionId() + ".jpg";
                          scienceQuestion.setUserAnswer(imageFileName);
  //                        selectedImage = selectedImageTemp;
                          Intent intent = new Intent();
                          intent.setType("image/*");
                          intent.setAction(Intent.ACTION_GET_CONTENT);
                          startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_FROM_GALLERY);
                      }
                  } else {
  //                    imageName = entryID + "_" + dde_questions.getQuestionId() + ".jpg";
                      scienceQuestion.setUserAnswer(imageFileName);
  //                    selectedImage = selectedImageTemp;
                      Intent intent = new Intent();
                      intent.setType("image/*");
                      intent.setAction(Intent.ACTION_GET_CONTENT);
                      startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_FROM_GALLERY);
                  }
              }
          });
          chooseImageDialog.show();
      }
  */
    /* @Override
     public void setVideoResult(Intent intent, int videoCapture, ScienceQuestion scienceQuestion) {
         if (hasCamera()) {
             if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                 String[] permissionArray = new String[]{PermissionUtils.Manifest_CAMERA};

                 if (!isPermissionsGranted(this, permissionArray)) {
                     Toast.makeText(this, "Give Camera permissions through settings and restart the app.", Toast.LENGTH_LONG).show();
                 } else {
                     videoName = scienceQuestion.getPaperid() + "_" + scienceQuestion.getQid() + ".mp4";
                     scienceQuestion.setUserAnswer(videoName);
 //                    Intent takePicture = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                     intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3 * 60);
                     intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
 //                    startActivityForResult(intent, VIDEO_CAPTURE);
                 }
             } else {
                 videoName = scienceQuestion.getPaperid() + "_" + scienceQuestion.getQid() + ".mp4";
                 scienceQuestion.setUserAnswer(videoName);

 //                Intent takePicture = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                 intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3 * 60);
 //                startActivityForResult(intent, VIDEO_CAPTURE);
             }
         } else {
             Toast.makeText(this, "Camera not found", Toast.LENGTH_LONG).show();
         }
         filePath = AssessmentApplication.assessPath + Assessment_Constants.STORE_ANSWER_MEDIA_PATH + "/" + videoName;

         startActivityForResult(intent, videoCapture);
     }
 */
    @Override
    public void setAudio(String path, boolean isAudioRecording) {
        if (isAudioRecording) {
            AudioUtil.startRecording(path);
        } else {
            AudioUtil.stopRecording();
        }
    }

    /*private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }*/


    private void checkAssessment(int queCnt) {
        AssessmentQuestion assessmentQuestion = assessmentQuestionList.get(queCnt);
        questionType = assessmentQuestion.getQtid().getQuestionID();
        switch (questionType) {
            case FILL_IN_THE_BLANK_WITH_OPTION:
            case MULTIPLE_CHOICE:

                if (assessmentQuestion.getIsAttempted()) {
                    if (assessmentQuestion.getMatchingNameList().get(0).getCorrect().equalsIgnoreCase("true") || assessmentQuestion.getUserAnswerId().equalsIgnoreCase(ansId)) {
                        assessmentQuestionList.get(queCnt).setIsCorrect(true);
                        assessmentQuestionList.get(queCnt).
                                setMarksPerQuestion(assessmentQuestionList.get(queCnt).getOutofmarks());

                    } else {
                        assessmentQuestionList.get(queCnt).setIsCorrect(false);
                        assessmentQuestionList.get(queCnt).
                                setMarksPerQuestion("0");
                    }
                } else {
                    assessmentQuestionList.get(queCnt).setIsCorrect(false);
                    assessmentQuestionList.get(queCnt).
                            setMarksPerQuestion("0");
                }
                //todo alter
              /*  if (scienceQuestion.getIsAttempted())
                    if (scienceQuestion.getAnswer().equalsIgnoreCase(answer)) {
                        scienceQuestionList.get(queCnt - 1).setIsCorrect(true);
                        scienceQuestionList.get(queCnt - 1).
                                setMarksPerQuestion(scienceQuestionList.get(queCnt - 1).getOutofmarks());
                    } else {
                        scienceQuestionList.get(queCnt - 1).setIsCorrect(false);
                        scienceQuestionList.get(queCnt - 1).
                                setMarksPerQuestion("0");
                    }*/
                break;
            case MULTIPLE_SELECT:
                boolean flag = true;
                if (assessmentQuestion.getIsAttempted()) {
                    for (SubOptions subOptions : assessmentQuestion.getMatchingNameList()) {
                        if (!subOptions.getCorrect().trim().equals(subOptions.getMyIscorrect().trim())) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        assessmentQuestionList.get(queCnt).setIsCorrect(true);
                        assessmentQuestionList.get(queCnt).
                                setMarksPerQuestion(assessmentQuestionList.get(queCnt).getOutofmarks());

                    } else {
                        assessmentQuestionList.get(queCnt).setIsCorrect(false);
                        assessmentQuestionList.get(queCnt).
                                setMarksPerQuestion("0");
                    }
                }
                break;
            case FILL_IN_THE_BLANK:
                if (assessmentQuestion.getIsAttempted()) {
                    if ((assessmentQuestion.getAnswer().equalsIgnoreCase(answer.trim()))) {
                        assessmentQuestionList.get(queCnt).setIsCorrect(true);
                        assessmentQuestionList.get(queCnt).
                                setMarksPerQuestion(assessmentQuestionList.get(queCnt).getOutofmarks());
                    } else {
                        assessmentQuestionList.get(queCnt).setIsCorrect(false);
                        assessmentQuestionList.get(queCnt).
                                setMarksPerQuestion("0");
                    }
                }
                break;
            case TRUE_FALSE:
                if (assessmentQuestion.getIsAttempted())
                    if (assessmentQuestion.getAnswer().equalsIgnoreCase(answer.trim())) {
                        assessmentQuestionList.get(queCnt).setIsCorrect(true);
                        assessmentQuestionList.get(queCnt).
                                setMarksPerQuestion(assessmentQuestionList.get(queCnt).getOutofmarks());
                    } else {
                        assessmentQuestionList.get(queCnt).setIsCorrect(false);
                        assessmentQuestionList.get(queCnt).
                                setMarksPerQuestion("0");
                    }
                break;
            /*case ARRANGE_SEQUENCE:
            case MATCHING_PAIR:
                if (scienceQuestion.getIsAttempted()) {
                    int matchPairCnt = 0;
                    List<ScienceQuestionChoice> queOptions = AppDatabase.getDatabaseInstance(this).getScienceQuestionChoicesDao().getQuestionChoicesByQID(scienceQuestion.getQid());
                    for (int i = 0; i < queOptions.size(); i++) {
                        if (queOptions.get(i).getQcid().equalsIgnoreCase(scienceQuestion.getMatchingNameList().get(i).getQcid())) {
                            matchPairCnt++;
                        }
                    }
                    if (matchPairCnt == queOptions.size()) {
                        scienceQuestionList.get(queCnt).setIsCorrect(true);
                        scienceQuestionList.get(queCnt).
                                setMarksPerQuestion(scienceQuestionList.get(queCnt).getOutofmarks());

                    } else {
                        scienceQuestionList.get(queCnt).setIsCorrect(false);
                        scienceQuestionList.get(queCnt).
                                setMarksPerQuestion("0");
                    }
                }
                break;
           case VIDEO:
                //todo decide marks,isCorrect for video,audio
                scienceQuestionList.get(queCnt).setUserAnswer(answer);
                scienceQuestionList.get(queCnt).setIsAttempted(true);
                scienceQuestionList.get(queCnt).setIsCorrect(true);
                scienceQuestionList.get(queCnt).
                        setMarksPerQuestion(scienceQuestionList.get(queCnt).getOutofmarks());
                DownloadMedia downloadMedia = new DownloadMedia();
                downloadMedia.setPhotoUrl(answer);
                downloadMedia.setqId(scienceQuestionList.get(queCnt).getQid());
                downloadMedia.setQtId(scienceQuestionList.get(queCnt).getQtid());
                downloadMedia.setMediaType(DOWNLOAD_MEDIA_TYPE_ANSWER_VIDEO);
                downloadMedia.setPaperId(assessmentSession);
                AppDatabase.getDatabaseInstance(this).getDownloadMediaDao().deleteByPaperIdAndQid(assessmentSession, scienceQuestionList.get(queCnt).getQid());
                AppDatabase.getDatabaseInstance(this).getDownloadMediaDao().insert(downloadMedia);

                break;*/
            /*case AUDIO:
//                scienceQuestionList.get(queCnt).setUserAnswer(filePath);
                scienceQuestionList.get(queCnt).setIsAttempted(true);
                scienceQuestionList.get(queCnt).setIsCorrect(true);
                scienceQuestionList.get(queCnt).
                        setMarksPerQuestion(scienceQuestionList.get(queCnt).getOutofmarks());
                DownloadMedia downloadMediaAudio = new DownloadMedia();
                downloadMediaAudio.setPhotoUrl(answer);
                downloadMediaAudio.setqId(scienceQuestionList.get(queCnt).getQid());
                downloadMediaAudio.setQtId(scienceQuestionList.get(queCnt).getQtid());
                downloadMediaAudio.setMediaType(DOWNLOAD_MEDIA_TYPE_ANSWER_AUDIO);
                downloadMediaAudio.setPaperId(assessmentSession);
                AppDatabase.getDatabaseInstance(this).getDownloadMediaDao().deleteByPaperIdAndQid(assessmentSession, scienceQuestionList.get(queCnt).getQid());
                AppDatabase.getDatabaseInstance(this).getDownloadMediaDao().insert(downloadMediaAudio);
                break;*/
            case TEXT_PARAGRAPH:
            case KEYWORDS_QUESTION:
                if (assessmentQuestion.getIsAttempted()) {
                    if (!answer.equalsIgnoreCase("")) {
                        assessmentQuestionList.get(queCnt).setIsCorrect(true);
                        assessmentQuestionList.get(queCnt).
                                setMarksPerQuestion(assessmentQuestionList.get(queCnt).getOutofmarks());
                    } else {
                        assessmentQuestionList.get(queCnt).setIsCorrect(false);
                        assessmentQuestionList.get(queCnt).
                                setMarksPerQuestion("0");
                    }
                }
                break;
            case IMAGE_ANSWER:
              /*  if (scienceQuestion.getIsAttempted()) {
                    if (!answer.equalsIgnoreCase("")) {
                        scienceQuestionList.get(queCnt).setIsCorrect(true);
                        scienceQuestionList.get(queCnt).
                                setMarksPerQuestion(scienceQuestionList.get(queCnt).getOutofmarks());
                    } else {
                        scienceQuestionList.get(queCnt).setIsCorrect(false);
                        scienceQuestionList.get(queCnt).
                                setMarksPerQuestion("0");
                    }

                    DownloadMedia imageMedia = new DownloadMedia();
                    imageMedia.setPhotoUrl(answer);
                    imageMedia.setqId(scienceQuestionList.get(queCnt).getQid());
                    imageMedia.setQtId(scienceQuestionList.get(queCnt).getQtid());
                    imageMedia.setMediaType(DOWNLOAD_MEDIA_TYPE_ANSWER_IMAGE);
                    imageMedia.setPaperId(assessmentSession);
                    AppDatabase.getDatabaseInstance(this).getDownloadMediaDao().deleteByPaperIdAndQid(assessmentSession, scienceQuestionList.get(queCnt).getQid());
                    AppDatabase.getDatabaseInstance(this).getDownloadMediaDao().insert(imageMedia);
                }
                break;*/

        }
    }

    @Click(resName = "btn_save_Assessment")
    public void saveAssessment() {
        //todo uncomment while adding practice mode
/*        if (Assessment_Constants.isPracticeModeOn) {
            if (!scienceQuestionList.get(queCnt).getIsAttempted()) {
                if (queCnt < scienceQuestionList.size() - 1)
                    nextClick();
                else {
//            mCountDownTimer.cancel();
                    scienceQuestionList.get(queCnt).setEndTime(Assessment_Utility.getCurrentDateTime());
                    BottomQuestionFragment bottomQuestionFragment = new BottomQuestionFragment();
                    bottomQuestionFragment.show(getSupportFragmentManager(), BottomQuestionFragment.class.getSimpleName());
                    Bundle args = new Bundle();
                    args.putSerializable("questionList", (Serializable) scienceQuestionList);
                    bottomQuestionFragment.setArguments(args);
                }
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (queCnt < scienceQuestionList.size() - 1)
                            nextClick();
                        else {
//            mCountDownTimer.cancel();
                            scienceQuestionList.get(queCnt).setEndTime(Assessment_Utility.getCurrentDateTime());
                            BottomQuestionFragment bottomQuestionFragment = new BottomQuestionFragment();
                            bottomQuestionFragment.show(getSupportFragmentManager(), BottomQuestionFragment.class.getSimpleName());
                            Bundle args = new Bundle();
                            args.putSerializable("questionList", (Serializable) scienceQuestionList);
                            bottomQuestionFragment.setArguments(args);
                        }
                    }
                }, 3000);
                 FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.detach(currentFragment);
                ft.attach(currentFragment);
                ft.commit();
            }


        } else {*/
        if (queCnt < assessmentQuestionList.size() - 1)
            nextClick();
        else {
//            mCountDownTimer.cancel();
            assessmentQuestionList.get(queCnt).setEndTime(Assessment_Utility.getCurrentDateTime());
            //todo alter
           /* BottomQuestionFragment bottomQuestionFragment = new BottomQuestionFragment();
            bottomQuestionFragment.show(getSupportFragmentManager(), BottomQuestionFragment.class.getSimpleName());
            Bundle args = new Bundle();
            args.putSerializable("questionList", (Serializable) scienceQuestionList);
            bottomQuestionFragment.setArguments(args);*/
        }
//        }
        // }
    }

    @Override
    public void onQuestionClick(int pos) {
//        questionTrackDialog.dismiss();
        queCnt = pos;
//        currentCount.setText(queCnt + "/" + scienceQuestionList.size());
//        discreteScrollView.smoothScrollToPosition(pos - 1);
        fragment_view_pager.setCurrentItem(pos - 1);
    }

    @Override
    public void onSaveAssessmentClick() {
//        responseListener.OnResult(scienceQuestionList);
        //insertInDB(scienceQuestionList, " Exam completed");
        //  AssessmentPaperForPush paper = createPaperToSave();
      /*  stopService(new Intent(this, BkgdVideoRecordingService.class));
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (!timesUp) {
            isEndTimeSet = true;
            examEndTime = Assessment_Utility.getCurrentDateTime();
        }
        calculateMarks();
        skippedCnt = correctAnsCnt = wrongAnsCnt = 0;
        calculateCorrectWrongCount();
        AssessmentPaperForPush paper = new AssessmentPaperForPush();
        paper.setPaperStartTime(examStartTime);
        paper.setPaperEndTime(examEndTime);
        paper.setLanguageId(Assessment_Constants.SELECTED_LANGUAGE);
        paper.setExamId(scienceQuestionList.get(0).getExamid());
        paper.setSubjectId(scienceQuestionList.get(0).getSubjectid());
//        paper.setPaperId(scienceQuestionList.get(0).getPaperid());
        paper.setOutOfMarks("" + outOfMarks);
        paper.setPaperId(assessmentSession);
        paper.setTotalMarks("" + totalMarks);
        paper.setExamTime("" + ExamTime);
        paper.setCorrectCnt(correctAnsCnt);
        paper.setWrongCnt(wrongAnsCnt);
        paper.setSkipCnt(skippedCnt);
        String currentSession = FastSave.getInstance().getString("currentSession", "");
//        paper.setSessionID(Assessment_Constants.currentSession);
        paper.setSessionID(currentSession);
        String currentStudentID = FastSave.getInstance().getString("currentStudentID", "");
//        paper.setStudentId("" + Assessment_Constants.currentStudentID);
        paper.setStudentId("" + currentStudentID);
        paper.setExamName(assessmentPaperPatterns.getExamname());

        ArrayList<Score> scores = new ArrayList<>();
        for (int i = 0; i < scienceQuestionList.size(); i++) {
            Score score = new Score();
            score.setQuestionId(Integer.parseInt(scienceQuestionList.get(i).getQid()));
//            score.setLevel(getLevel(scienceQuestionList.get(i).getQlevel()));
            score.setLevel(Integer.parseInt(scienceQuestionList.get(i).getQlevel()));
            score.setIsAttempted(scienceQuestionList.get(i).getIsAttempted());
            score.setIsCorrect(scienceQuestionList.get(i).getIsCorrect());
            score.setTotalMarks(Integer.parseInt(scienceQuestionList.get(i).getOutofmarks()));
            score.setExamId(scienceQuestionList.get(i).getExamid());
            score.setStartDateTime(scienceQuestionList.get(i).getStartTime());
            if (Assessment_Constants.ASSESSMENT_TYPE.equalsIgnoreCase("practice"))
                score.setLabel("practice");
            else score.setLabel("supervised assessment");
            score.setEndDateTime(scienceQuestionList.get(i).getEndTime());
//            score.setStudentID(Assessment_Constants.currentStudentID);
            score.setStudentID(currentStudentID);
            score.setDeviceID(AppDatabase.getDatabaseInstance(this).getStatusDao().getValue("DeviceId"));
//            score.setSessionID(Assessment_Constants.assessmentSession);
            score.setPaperId(assessmentSession);
//            score.setSessionID(Assessment_Constants.currentSession);
            score.setSessionID(currentSession);
            score.setScoredMarks(Integer.parseInt(scienceQuestionList.get(i).getMarksPerQuestion()));
            if (!scienceQuestionList.get(i).getUserAnswer().equalsIgnoreCase(""))
                score.setUserAnswer(scienceQuestionList.get(i).getUserAnswer());
            else if (scienceQuestionList.get(i).getUserAnswer().equalsIgnoreCase("") && !scienceQuestionList.get(i).getUserAnswerId().equalsIgnoreCase(""))
                score.setUserAnswer(scienceQuestionList.get(i).getUserAnswerId());
            scores.add(score);
        }
        paper.setScoreList(scores);
        AppDatabase.getDatabaseInstance(this).getScoreDao().insertAllScores(scores);
        AppDatabase.getDatabaseInstance(this).getAssessmentPaperForPushDao().insertPaperForPush(paper);
*/
        // generateResultData(paper);


        Toast.makeText(this, "Assessment saved successfully", Toast.LENGTH_SHORT).show();
        //BackupDatabase.backup(this);

    }

  /*  private AssessmentPaperForPush createPaperToSave() {
        AssessmentPaperForPush paper = new AssessmentPaperForPush();
        paper.setPaperStartTime(examStartTime);
        paper.setPaperEndTime(examEndTime);
        paper.setLanguageId(Assessment_Constants.SELECTED_LANGUAGE);
        paper.setExamId(scienceQuestionList.get(0).getExamid());
        paper.setSubjectId(scienceQuestionList.get(0).getSubjectid());
//        paper.setPaperId(scienceQuestionList.get(0).getPaperid());
        paper.setOutOfMarks("" + outOfMarks);
        paper.setPaperId(assessmentSession);
        paper.setTotalMarks("" + totalMarks);
        paper.setExamTime("" + ExamTime);
        paper.setCorrectCnt(correctAnsCnt);
        paper.setWrongCnt(wrongAnsCnt);
        paper.setSkipCnt(skippedCnt);
        //todo alter
        //  String currentSession = FastSave.getInstance().getString("currentSession", "");
//        paper.setSessionID(Assessment_Constants.currentSession);
        paper.setSessionID(currentSession);
        //todo alter
        // String currentStudentID = FastSave.getInstance().getString("currentStudentID", "");
//        paper.setStudentId("" + Assessment_Constants.currentStudentID);
        paper.setStudentId("" + currentStudentID);
        //todo alter
        Student student = null;//= AppDatabase.getDatabaseInstance(this).getStudentDao().getStudent(currentStudentID);
        if (student != null) {
            paper.setFullName(student.getFullName());
            paper.setAge(student.getAge());
            paper.setGender(student.getGender());
        }
        paper.setExamName(assessmentPaperPatterns.getExamname());
        return paper;
    }*/


    private void saveAttemptedQuestionsInDB() {
        List<AssessmentQuestion> attemptedQuestion = new ArrayList<>();
        for (int i = 0; i < assessmentQuestionList.size(); i++) {
            if (assessmentQuestionList.get(i).getIsAttempted())
                attemptedQuestion.add(assessmentQuestionList.get(i));
        }

        responseListener.OnResult(assessmentQuestionList);
        VideoMonitor.stopRecording();
        VideoMonitor.realease();
        //insertInDB(attemptedQuestion, " Exam incomplete");
    }


  /*  private void insertInDB(List<ScienceQuestion> scienceQuestionList, String examStatus) {
        stopService(new Intent(this, BkgdVideoRecordingService.class));
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (!timesUp) {
            isEndTimeSet = true;
            examEndTime = Assessment_Utility.getCurrentDateTime();
        }
        calculateMarks();
        skippedCnt = correctAnsCnt = wrongAnsCnt = 0;
        calculateCorrectWrongCount();
        AssessmentPaperForPush paper = createPaperToSave();
        //todo alter
        // String currentSession = FastSave.getInstance().getString("currentSession", "");
        // String currentStudentID = FastSave.getInstance().getString("currentStudentID", "");

        *//*AssessmentPaperForPush paper = new AssessmentPaperForPush();
        paper.setPaperStartTime(examStartTime);
        paper.setPaperEndTime(examEndTime);
        paper.setLanguageId(Assessment_Constants.SELECTED_LANGUAGE);
        paper.setExamId(scienceQuestionList.get(0).getExamid());
        paper.setSubjectId(scienceQuestionList.get(0).getSubjectid());
//        paper.setPaperId(scienceQuestionList.get(0).getPaperid());
        paper.setOutOfMarks("" + outOfMarks);
        paper.setPaperId(assessmentSession);
        paper.setTotalMarks("" + totalMarks);
        paper.setExamTime("" + ExamTime);
        paper.setCorrectCnt(correctAnsCnt);
        paper.setWrongCnt(wrongAnsCnt);
        paper.setSkipCnt(skippedCnt);
        String currentSession = FastSave.getInstance().getString("currentSession", "");
//        paper.setSessionID(Assessment_Constants.currentSession);
        paper.setSessionID(currentSession);
        String currentStudentID = FastSave.getInstance().getString("currentStudentID", "");
//        paper.setStudentId("" + Assessment_Constants.currentStudentID);
        paper.setStudentId("" + currentStudentID);
        paper.setExamName(assessmentPaperPatterns.getExamname());
*//*
        ArrayList<Score> scores = new ArrayList<>();
        for (int i = 0; i < scienceQuestionList.size(); i++) {
            Score score = new Score();
            score.setQuestionId(Integer.parseInt(scienceQuestionList.get(i).getQid()));
//            score.setLevel(getLevel(scienceQuestionList.get(i).getQlevel()));
            score.setLevel(Integer.parseInt(scienceQuestionList.get(i).getQlevel()));
            score.setIsAttempted(scienceQuestionList.get(i).getIsAttempted());
            score.setIsCorrect(scienceQuestionList.get(i).getIsCorrect());
            score.setTotalMarks(Integer.parseInt(scienceQuestionList.get(i).getOutofmarks()));
            score.setExamId(scienceQuestionList.get(i).getExamid());
            score.setStartDateTime(scienceQuestionList.get(i).getStartTime());
            if (Assessment_Constants.ASSESSMENT_TYPE.equalsIgnoreCase("practice"))
                score.setLabel("practice" + examStatus);
            else score.setLabel("supervised assessment" + examStatus);
            score.setEndDateTime(scienceQuestionList.get(i).getEndTime());
//            score.setStudentID(Assessment_Constants.currentStudentID);
            score.setStudentID(currentStudentID);
            //todo alter
            //score.setDeviceID(AppDatabase.getDatabaseInstance(this).getStatusDao().getValue("DeviceId"));
//            score.setSessionID(Assessment_Constants.assessmentSession);
            score.setPaperId(assessmentSession);
//            score.setSessionID(Assessment_Constants.currentSession);
            score.setSessionID(currentSession);
            score.setScoredMarks(Integer.parseInt(scienceQuestionList.get(i).getMarksPerQuestion()));
            if (!scienceQuestionList.get(i).getUserAnswer().equalsIgnoreCase(""))
                score.setUserAnswer(scienceQuestionList.get(i).getUserAnswer());
            else if (scienceQuestionList.get(i).getUserAnswer().equalsIgnoreCase("") && !scienceQuestionList.get(i).getUserAnswerId().equalsIgnoreCase(""))
                score.setUserAnswer(scienceQuestionList.get(i).getUserAnswerId());
            scores.add(score);
        }
        paper.setScoreList(scores);
        //todo alter
       *//* AppDatabase.getDatabaseInstance(this).getScoreDao().insertAllScores(scores);
        AppDatabase.getDatabaseInstance(this).getAssessmentPaperForPushDao().insertPaperForPush(paper);*//*

    }
*/

/*    private void calculateCorrectWrongCount() {
        for (int i = 0; i < scienceQuestionList.size(); i++) {
            if (scienceQuestionList.get(i).getIsCorrect()) correctAnsCnt++;
            else if (!scienceQuestionList.get(i).getIsCorrect() && scienceQuestionList.get(i).getIsAttempted())
                wrongAnsCnt++;
            if (!scienceQuestionList.get(i).getIsAttempted()) skippedCnt++;
        }
    }*/

    /*private void generateResultData(AssessmentPaperForPush paper) {
        ArrayList<ResultModalClass> resultList = new ArrayList<>();
        ResultOuterModalClass outerModalClass = new ResultOuterModalClass();

        outerModalClass.setOutOfMarks("" + outOfMarks);
        outerModalClass.setMarksObtained("" + totalMarks);
        //todo alter
        //   String currentStudentID = FastSave.getInstance().getString("currentStudentID", "");
//        outerModalClass.setStudentId(Assessment_Constants.currentStudentID);
        outerModalClass.setStudentId(currentStudentID);
        outerModalClass.setExamStartTime(examStartTime);
        outerModalClass.setExamId(paper.getExamId());
        outerModalClass.setSubjectId(paper.getSubjectId());
        outerModalClass.setExamEndTime(examEndTime);
        outerModalClass.setPaperId(assessmentSession);
        for (int i = 0; i < scienceQuestionList.size(); i++) {
            ResultModalClass result = new ResultModalClass();
            result.setQuestion(scienceQuestionList.get(i).getQname());
            result.setqId(scienceQuestionList.get(i).getQid());
            result.setUserAnswer(scienceQuestionList.get(i).getUserAnswer());
            result.setUserAnswerId(scienceQuestionList.get(i).getUserAnswerId());
            result.setCorrectAnswer(scienceQuestionList.get(i).getAnswer());
            result.setCorrect(scienceQuestionList.get(i).getIsCorrect());
            result.setAttempted(scienceQuestionList.get(i).getIsAttempted());
            result.setQuestionImg(scienceQuestionList.get(i).getPhotourl());
            resultList.add(result);
        }
        outerModalClass.setResultList(resultList);
        if (resultList.size() > 0) {
            endTestSession();
            //todo alter
          *//*  Intent intent = new Intent(this, ResultActivity_.class);
            intent.putExtra("result", outerModalClass);
           *//**//* intent.putExtra("outOfMarks", "" + outOfMarks);
            intent.putExtra("marksObtained", "" + totalMarks);
            intent.putExtra("studentId", Assessment_Constants.currentStudentID);
            intent.putExtra("examStartTime", examStartTime);
            intent.putExtra("examId", paper.getExamId());
            intent.putExtra("subjectId", paper.getSubjectId());
            intent.putExtra("examEndTime", examEndTime);
            intent.putExtra("paperId", assessmentSession);*//**//*
            startActivity(intent);
            finish();*//*
        }
    }*/

/*    private void calculateMarks() {
        totalMarks = outOfMarks = 0;
        for (int i = 0; i < scienceQuestionList.size(); i++) {
            totalMarks += Integer.parseInt(scienceQuestionList.get(i).getMarksPerQuestion());
            outOfMarks += Integer.parseInt(scienceQuestionList.get(i).getOutofmarks());
        }
    }*/

    /*private int getLevel(String qlevel) {
        int level = 0;
        if (qlevel.equalsIgnoreCase("easy"))
            level = 0;
        else if (qlevel.equalsIgnoreCase("normal"))
            level = 1;
        else if (qlevel.equalsIgnoreCase("difficult"))
            level = 2;
        return level;
    }*/


    @Override
    public void onStart() {
        super.onStart();
        isActivityRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isActivityRunning = false;
        /*if (speech != null)
            speech.stopListening();*/
//        speech = null;
        if (Assessment_Constants.VIDEOMONITORING)
            VideoMonitoringService.releaseMediaRecorder();
//        stopService(serviceIntent);

    }

    @Override
    protected void onPause() {
        super.onPause();
       /* if (speech != null)
            speech.stopListening();*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timesUp) {
            if (!isEndTimeSet)
                examEndTime = Assessment_Utility.getCurrentDateTime();
            if (isActivityRunning) {
                AssessmentTimeUpDialog timeUpDialog = new AssessmentTimeUpDialog(this);
                timeUpDialog.show();
                if (mCountDownTimer != null)
                    mCountDownTimer.cancel();
            }
        }
//todo
//        resetSpeechRecognizer(); remove speech=null from onStop

    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == -1 && requestCode == VIDEO_CAPTURE) {
                showCapturedVideo();
                AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                FileInputStream in = videoAsset.createInputStream();
          *//*  File direct = new File(Environment.getExternalStorageDirectory().toString() + "/.Assessment");

            if (!direct.exists()) direct.mkdir();
           *//*
//                File direct = new File(Environment.getExternalStorageDirectory().toString() + Assessment_Constants.STORE_ANSWER_MEDIA_PATH);
                File direct = new File(AssessmentApplication.assessPath + Assessment_Constants.STORE_ANSWER_MEDIA_PATH);

                if (!direct.exists()) direct.mkdir();

                File fileName = new File(direct, videoName);
                if (fileName.exists())
                    fileName.delete();
                OutputStream out = new FileOutputStream(fileName);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/


    public void endTestSession() {
        try {
            new AsyncTask<Object, Void, Object>() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    //todo alter
                   /* try {
                        String currentSession = FastSave.getInstance().getString("currentSession", "");

//                        String toDateTemp = appDatabase.getSessionDao().getToDate(Assessment_Constants.currentSession);
                        String toDateTemp = appDatabase.getSessionDao().getToDate(currentSession);

                        if (toDateTemp.equalsIgnoreCase("na")) {
//                            appDatabase.getSessionDao().UpdateToDate(Assessment_Constants.currentSession, AssessmentApplication.getCurrentDateTime());
                            appDatabase.getSessionDao().UpdateToDate(currentSession, AssessmentApplication.getCurrentDateTime());
                        }
                        //BackupDatabase.backup(ScienceAssessmentActivity.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    return null;
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void startCameraService() {
        if (serviceIntent != null)
            startService(serviceIntent);
        String fileName = "session" + "_" + Assessment_Utility.getCurrentDateTime() + ".mp4";
        String videoPath = assessPath + Assessment_Constants.STORE_VIDEO_MONITORING_PATH + "/" + fileName;

        VideoMonitor.startRecording(videoPath);
        // VideoMonitoringService.releaseMediaRecorder();
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (VideoMonitoringService.prepareVideoRecorder(texture_view, fileName)) {
                    VideoMonitoringService.startCapture();
                    DownloadMedia video = new DownloadMedia();
                    video.setMediaType(DOWNLOAD_MEDIA_TYPE_VIDEO_MONITORING);
                    video.setPhotoUrl(videoPath);
                    video.setPaperId(assessmentSession);
                    //AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getDownloadMediaDao().insert(video);

                } else {
                    Assessment_Constants.VIDEOMONITORING = false;
                    texture_view.setVisibility(View.GONE);
                    tv_timer.setTextColor(Color.BLACK);
                    frame_video_monitoring.setVisibility(View.GONE);
                    btn_save_Assessment.setVisibility(View.VISIBLE);
                    Toast.makeText(ScienceAssessmentActivity.this, "video monitoring not prepared", Toast.LENGTH_LONG).show();
                }

            }
        }, 300);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewpagerAdapter = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LIBRARY_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (checkWhetherAllPermissionsPresentForPhotoTagging(permissionsNeededAssessmentLib)) {
                if (Assessment_Constants.VIDEOMONITORING) {
                    VideoMonitor.initCamera(this, texture_view);
                    frame_video_monitoring.setVisibility(View.VISIBLE);
                }
                generatePaperPattern();
            } else if (requestCode == LIBRARY_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Snackbar.make(findViewById(android.R.id.content), "Permission denied, photo tagging will not work, to enable now click here",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(ScienceAssessmentActivity.this, getDeniedPermissionsAmongPhototaggingPermissions(permissionsNeededAssessmentLib), LIBRARY_PERMISSIONS);
                    }
                }).show();
            }
        } else if (permissions.length > 1) {
            if (requestCode == LIBRARY_PERMISSIONS) {
                if (checkWhetherAllPermissionsPresentForPhotoTagging(permissionsNeededAssessmentLib)) {
                    if (checkWhetherAllPermissionsPresentForPhotoTagging(permissionsNeededAssessmentLib)) {
                        if (Assessment_Constants.VIDEOMONITORING) {
                            VideoMonitor.initCamera(this, texture_view);
                            frame_video_monitoring.setVisibility(View.VISIBLE);
                        }
                        generatePaperPattern();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.Permissions_denied_message),
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(ScienceAssessmentActivity.this, getDeniedPermissionsAmongPhototaggingPermissions(permissionsNeededAssessmentLib), LIBRARY_PERMISSIONS);
                            }
                        }).show();
                    }
                }
            }
        }
    }
}