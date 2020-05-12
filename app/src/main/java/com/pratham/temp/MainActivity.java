package com.pratham.temp;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.pratham.assessment_lib.AssessmentLibrary;
import com.pratham.assessment_lib.Utility.QuestionType;
import com.pratham.assessment_lib.domain.AssessmentQuestion;
import com.pratham.assessment_lib.domain.SubOptions;
import com.pratham.assessment_lib.interfaces.OnAssessmentComplete;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @AfterViews
    protected void onCreate() {
        // super.onCreate(savedInstanceState);


    }

    @Click(R.id.text)
    public void set() {
   /* String digits = "1234";
    int n = digits.length();
    Log.d("log",""+countDecoding(digits, n));*/
        String assessmentSession = "" + UUID.randomUUID().toString();


        final AssessmentQuestion assessmentQuestion = new AssessmentQuestion();
        assessmentQuestion.setAnsdesc("Uttar Pradesh");
        assessmentQuestion.setUpdatedby("1");
        assessmentQuestion.setQlevel("2");
        assessmentQuestion.setAddedby("1");
        assessmentQuestion.setLanguageid("1");
        assessmentQuestion.setLessonid("36");
        assessmentQuestion.setQtid(QuestionType.MULTIPLE_CHOICE);
        assessmentQuestion.setQid("1254");
        assessmentQuestion.setSubjectid("21");
        assessmentQuestion.setAddedtime("2019-06-11T11:33:35.763");
        assessmentQuestion.setUpdatedtime("2019-06-11T11:33:35.763");
        assessmentQuestion.setTopicid("21");
        assessmentQuestion.setAnswer("Uttar Pradesh");
        assessmentQuestion.setQname("Which is the most populated state in India?");
        assessmentQuestion.setMarksPerQuestion("0");
        assessmentQuestion.setIsAttempted(false);
        assessmentQuestion.setIsCorrect(false);
        assessmentQuestion.setPhotourl("");

        AssessmentQuestion assessmentQuestion2 = new AssessmentQuestion();
        assessmentQuestion2.setAnsdesc("2,3");
        assessmentQuestion2.setUpdatedby("1");
        assessmentQuestion2.setQlevel("1");
        assessmentQuestion2.setAddedby("1");
        assessmentQuestion2.setLanguageid("1");
        assessmentQuestion2.setLessonid("42");
        assessmentQuestion2.setQtid(QuestionType.MULTIPLE_SELECT);
        assessmentQuestion2.setQid("5341");
        assessmentQuestion2.setSubjectid("27");
        assessmentQuestion2.setAddedtime("2019-06-11T11:33:35.763");
        assessmentQuestion2.setUpdatedtime("2019-06-11T11:33:35.763");
        assessmentQuestion2.setTopicid("27");
        assessmentQuestion2.setAnswer("2,3");
        assessmentQuestion2.setQname("test");
        assessmentQuestion2.setMarksPerQuestion("0");
        assessmentQuestion2.setIsAttempted(false);
        assessmentQuestion2.setIsCorrect(false);
        assessmentQuestion2.setPhotourl("");

        AssessmentQuestion assessmentQuestionTrueFalse = new AssessmentQuestion();
        assessmentQuestionTrueFalse.setAnsdesc("test");
        assessmentQuestionTrueFalse.setUpdatedby("1");
        assessmentQuestionTrueFalse.setQlevel("1");
        assessmentQuestionTrueFalse.setAddedby("1");
        assessmentQuestionTrueFalse.setLanguageid("1");
        assessmentQuestionTrueFalse.setLessonid("42");
        assessmentQuestionTrueFalse.setQtid(QuestionType.TRUE_FALSE);
        assessmentQuestionTrueFalse.setQid("5342");
        assessmentQuestionTrueFalse.setSubjectid("27");
        assessmentQuestionTrueFalse.setAddedtime("2019-06-11T11:33:35.763");
        assessmentQuestionTrueFalse.setUpdatedtime("2019-06-11T11:33:35.763");
        assessmentQuestionTrueFalse.setTopicid("27");
        assessmentQuestionTrueFalse.setAnswer("True");
        assessmentQuestionTrueFalse.setQname("test True False");
        assessmentQuestionTrueFalse.setMarksPerQuestion("0");
        assessmentQuestionTrueFalse.setIsAttempted(false);
        assessmentQuestionTrueFalse.setIsCorrect(false);
        assessmentQuestionTrueFalse.setPhotourl("");


        AssessmentQuestion assessmentQuestion5 = new AssessmentQuestion();
        assessmentQuestion5.setAnsdesc("test");
        assessmentQuestion5.setUpdatedby("1");
        assessmentQuestion5.setQlevel("2");
        assessmentQuestion5.setAddedby("1");
        assessmentQuestion5.setLanguageid("1");
        assessmentQuestion5.setLessonid("42");
        assessmentQuestion5.setQtid(QuestionType.FILL_IN_THE_BLANK_WITH_OPTION);
        assessmentQuestion5.setQid("5382");
        assessmentQuestion5.setSubjectid("27");
        assessmentQuestion5.setAddedtime("2019-06-11T11:33:35.763");
        assessmentQuestion5.setUpdatedtime("2019-06-11T11:33:35.763");
        assessmentQuestion5.setTopicid("27");
        assessmentQuestion5.setAnswer("True");
        assessmentQuestion5.setQname("Fill in the blank with option (If our body did not have bones ...........)");
        assessmentQuestion5.setMarksPerQuestion("0");
        assessmentQuestion5.setIsAttempted(false);
        assessmentQuestion5.setIsCorrect(false);
        assessmentQuestion5.setPhotourl("");

        AssessmentQuestion assessmentQuestion6 = new AssessmentQuestion();
        assessmentQuestion6.setAnsdesc("Movement would have been impossible ");
        assessmentQuestion6.setUpdatedby("1");
        assessmentQuestion6.setQlevel("2");
        assessmentQuestion6.setAddedby("1");
        assessmentQuestion6.setLanguageid("1");
        assessmentQuestion6.setLessonid("42");
        assessmentQuestion6.setQtid(QuestionType.FILL_IN_THE_BLANK);
        assessmentQuestion6.setQid("5383");
        assessmentQuestion6.setSubjectid("27");
        assessmentQuestion6.setAddedtime("2019-06-11T11:33:35.763");
        assessmentQuestion6.setUpdatedtime("2019-06-11T11:33:35.763");
        assessmentQuestion6.setTopicid("27");
        assessmentQuestion6.setAnswer("Movement would have been impossible ");
        assessmentQuestion6.setQname("Fill in the blank (If there were no joints between the bones ...........)");
        assessmentQuestion6.setMarksPerQuestion("0");
        assessmentQuestion6.setIsAttempted(false);
        assessmentQuestion6.setIsCorrect(false);
        assessmentQuestion6.setPhotourl("");

        AssessmentQuestion assessmentQuestion7 = new AssessmentQuestion();
        assessmentQuestion7.setAnsdesc("Movement would have been impossible ");
        assessmentQuestion7.setUpdatedby("1");
        assessmentQuestion7.setQlevel("2");
        assessmentQuestion7.setAddedby("1");
        assessmentQuestion7.setLanguageid("1");
        assessmentQuestion7.setLessonid("42");
        assessmentQuestion7.setQtid(QuestionType.ARRANGE_SEQUENCE);
        assessmentQuestion7.setQid("5384");
        assessmentQuestion7.setSubjectid("27");
        assessmentQuestion7.setAddedtime("2019-06-11T11:33:35.763");
        assessmentQuestion7.setUpdatedtime("2019-06-11T11:33:35.763");
        assessmentQuestion7.setTopicid("27");
        assessmentQuestion7.setAnswer("Movement would have been impossible ");
        assessmentQuestion7.setQname("Arrange Sequence (1. Word  2. Paragraph  3. Sentence  4. Letters  5. Phrase)");
        assessmentQuestion7.setMarksPerQuestion("0");
        assessmentQuestion7.setIsAttempted(false);
        assessmentQuestion7.setIsCorrect(false);
        assessmentQuestion7.setPhotourl("");

        AssessmentQuestion assessmentQuestion8 = new AssessmentQuestion();
        assessmentQuestion8.setAnsdesc("We would't have been able to bend");
        assessmentQuestion8.setUpdatedby("1");
        assessmentQuestion8.setQlevel("2");
        assessmentQuestion8.setAddedby("1");
        assessmentQuestion8.setLanguageid("1");
        assessmentQuestion8.setLessonid("42");
        assessmentQuestion8.setQtid(QuestionType.VIDEO);
        assessmentQuestion8.setQid("5385");
        assessmentQuestion8.setSubjectid("27");
        assessmentQuestion8.setAddedtime("2019-06-11T11:33:35.763");
        assessmentQuestion8.setUpdatedtime("2019-06-11T11:33:35.763");
        assessmentQuestion8.setTopicid("27");
        assessmentQuestion8.setAnswer("We would't have been able to bend");
        assessmentQuestion8.setQname("Video (If our backbone was made of one single bone .....)");
        assessmentQuestion8.setMarksPerQuestion("0");
        assessmentQuestion8.setIsAttempted(false);
        assessmentQuestion8.setIsCorrect(false);
        assessmentQuestion8.setPhotourl("");

        AssessmentQuestion assessmentQuestion9 = new AssessmentQuestion();
        assessmentQuestion9.setAnsdesc("Convection of heat");
        assessmentQuestion9.setUpdatedby("1");
        assessmentQuestion9.setQlevel("2");
        assessmentQuestion9.setAddedby("1");
        assessmentQuestion9.setLanguageid("1");
        assessmentQuestion9.setLessonid("42");
        assessmentQuestion9.setQtid(QuestionType.AUDIO);
        assessmentQuestion9.setQid("5386");
        assessmentQuestion9.setSubjectid("27");
        assessmentQuestion9.setAddedtime("2019-06-11T11:33:35.763");
        assessmentQuestion9.setUpdatedtime("2019-06-11T11:33:35.763");
        assessmentQuestion9.setTopicid("27");
        assessmentQuestion9.setAnswer("Convection of heat");
        assessmentQuestion9.setQname("Audio (What is the main reason behind wind blowing on Earth?)");
        assessmentQuestion9.setMarksPerQuestion("0");
        assessmentQuestion9.setIsAttempted(false);
        assessmentQuestion9.setIsCorrect(false);
        assessmentQuestion9.setPhotourl("");


        AssessmentQuestion assessmentQuestion11 = new AssessmentQuestion();
        assessmentQuestion11.setAnsdesc("Mercury expands on heating");
        assessmentQuestion11.setUpdatedby("1");
        assessmentQuestion11.setQlevel("2");
        assessmentQuestion11.setAddedby("1");
        assessmentQuestion11.setLanguageid("1");
        assessmentQuestion11.setLessonid("42");
        assessmentQuestion11.setQtid(QuestionType.AUDIO);
        assessmentQuestion11.setQid("5387");
        assessmentQuestion11.setSubjectid("27");
        assessmentQuestion11.setAddedtime("2019-06-11T11:33:35.763");
        assessmentQuestion11.setUpdatedtime("2019-06-11T11:33:35.763");
        assessmentQuestion11.setTopicid("27");
        assessmentQuestion11.setAnswer("Mercury expands on heating");
        assessmentQuestion11.setQname("Text question with Keywords (Why does the mercury in a thermometer rise with increase in temperature?)");
        assessmentQuestion11.setMarksPerQuestion("0");
        assessmentQuestion11.setIsAttempted(false);
        assessmentQuestion11.setIsCorrect(false);
        assessmentQuestion11.setPhotourl("");

        ArrayList<AssessmentQuestion> assessmentQuestionsList = new ArrayList<>();
        assessmentQuestionsList.add(assessmentQuestion);
        assessmentQuestionsList.add(assessmentQuestion2);
        assessmentQuestionsList.add(assessmentQuestionTrueFalse);
        assessmentQuestionsList.add(assessmentQuestion5);

        for (int i = 0; i < assessmentQuestionsList.size(); i++) {
            assessmentQuestionsList.get(i).setPaperid(assessmentSession);
            String qid = assessmentQuestionsList.get(i).getQid();
            // ArrayList<ScienceQuestionChoice> scienceQuestionChoiceList = (ArrayList<ScienceQuestionChoice>) AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getScienceQuestionChoicesDao().getQuestionChoicesByQID(qid);
            ArrayList<SubOptions> subOptionsList = new ArrayList<>();
            for (int k = 0; k < 4; k++) {
                SubOptions subOptions = new SubOptions();
                subOptions.setQcid("A" + k);
                subOptions.setQid("1254");
                subOptions.setChoicename("Choicename" + k);
                if (k == 1)
                    subOptions.setCorrect("true");
                else
                    subOptions.setCorrect("false");
                subOptionsList.add(subOptions);
            }

            assessmentQuestionsList.get(i).setLstquestionchoice(subOptionsList);
        }

        AssessmentQuestion assessmentQuestionMatchThePair = new AssessmentQuestion();
        assessmentQuestionMatchThePair.setAnsdesc("2143");
        assessmentQuestionMatchThePair.setUpdatedby("1");
        assessmentQuestionMatchThePair.setQlevel("1");
        assessmentQuestionMatchThePair.setAddedby("1");
        assessmentQuestionMatchThePair.setLanguageid("1");
        assessmentQuestionMatchThePair.setLessonid("42");
        assessmentQuestionMatchThePair.setQtid(QuestionType.MATCHING_PAIR);
        assessmentQuestionMatchThePair.setQid("5343");
        assessmentQuestionMatchThePair.setSubjectid("27");
        assessmentQuestionMatchThePair.setAddedtime("2019-06-11T11:33:35.763");
        assessmentQuestionMatchThePair.setUpdatedtime("2019-06-11T11:33:35.763");
        assessmentQuestionMatchThePair.setTopicid("27");
        assessmentQuestionMatchThePair.setAnswer("2143");
        assessmentQuestionMatchThePair.setQname("match The pair");
        assessmentQuestionMatchThePair.setMarksPerQuestion("0");
        assessmentQuestionMatchThePair.setIsAttempted(false);
        assessmentQuestionMatchThePair.setIsCorrect(false);
        assessmentQuestionMatchThePair.setPhotourl("");

        SubOptions subOptions1 = new SubOptions("20640", "5343", "3", "4", "false", "", "");
        SubOptions subOptions2 = new SubOptions("20639", "5343", "4", "3", "false", "", "");
        SubOptions subOptions3 = new SubOptions("20638", "5343", "1", "2", "false", "", "");
        SubOptions subOptions4 = new SubOptions("20637", "5343", "2", "1", "false", "", "");

        ArrayList<SubOptions> subOptionsList = new ArrayList<>();
        subOptionsList.add(subOptions1);
        subOptionsList.add(subOptions2);
        subOptionsList.add(subOptions3);
        subOptionsList.add(subOptions4);


        ArrayList<SubOptions> subOptionsList7 = new ArrayList<>();
        SubOptions subOptions11 = new SubOptions("20704", "5384", "1", "Letters", "true", "", "");
        SubOptions subOptions22 = new SubOptions("20705", "5384", "2", "Word", "true", "", "");
        SubOptions subOptions33 = new SubOptions("20706", "5384", "3", "Phrase", "true", "", "");
        SubOptions subOptions44 = new SubOptions("20707", "5384", "4", "Sentence", "true", "", "");
        SubOptions subOptions55 = new SubOptions("20708", "5384", "5", "Paragraph", "true", "", "");
        subOptionsList7.add(subOptions55);
        subOptionsList7.add(subOptions11);
        subOptionsList7.add(subOptions22);
        subOptionsList7.add(subOptions33);
        subOptionsList7.add(subOptions44);

        assessmentQuestion7.setLstquestionchoice(subOptionsList7);

        assessmentQuestionMatchThePair.setLstquestionchoice(subOptionsList);
        assessmentQuestionsList.add(assessmentQuestionMatchThePair);
        assessmentQuestionsList.add(assessmentQuestion6);
        assessmentQuestionsList.add(assessmentQuestion7);
        assessmentQuestionsList.add(assessmentQuestion8);

        String path = getInternalPath();

        AssessmentLibrary.getAssessmentLibrary(this)
                .setQuestionList(assessmentQuestionsList)
                .setStoragePath(path)
                .setBackGroundColor(R.color.dark_blue)//default black
                .setSelectedLanguageCode("1")//default is 1
                .setVideoMonitoring(true)    //default false
                .setExamDuration("20")      //default 30
                .build()
                .setOnAssessmentCompleteListener(new OnAssessmentComplete() {
                    @Override
                    public void OnResult(List<AssessmentQuestion> questionList) {
                        if (questionList.size() > 0) {
                            Log.d("OnResult:::", "OnResult: " + questionList);
                        }
                    }
                });

    }
   /* int countDecoding(String digits, int n)
    {
        // base cases
        if (n == 0 || n == 1)
            return 1;
        if(digits.charAt(0)=='0')
            return 0;
        //for base condition "01123" should return 0
        int count = 0;  // Initialize count

        // If the last digit is not 0,
        // then last digit must add to the number of words
        if (digits.charAt(n-1)> '0')
            count =  countDecoding(digits, n-1);

        // If the last two digits form a number smaller
        // than or equal to 26, then consider
        // last two digits and recur
        if (digits.charAt(n-2) == '1' ||
                (digits.charAt(n-2) == '2' && digits.charAt(n-1) < '7') )
            count +=  countDecoding(digits, n-2);

        return count;
    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getInternalPath() {
        String STORING_IN;

        File[] intDir = getExternalFilesDirs("");
        try {
            if (intDir.length > 1) {
                try {
                    File file = new File(intDir[1].getAbsolutePath(), "hello.txt");
                    if (!file.exists())
                        file.createNewFile();
                    file.delete();
                    STORING_IN = "SD-Card";
                    return intDir[1].getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                    STORING_IN = "Internal Storage";
                    return intDir[0].getAbsolutePath();
                }
            } else {
                STORING_IN = "Internal Storage";
                return intDir[0].getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("getInternalPath@@@", e.getMessage());
            return intDir[0].getAbsolutePath();
        }
    }

}
