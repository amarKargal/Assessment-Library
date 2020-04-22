package com.pratham.temp;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.pratham.assessment_lib.AssessmentLibrary;
import com.pratham.assessment_lib.domain.ScienceQuestion;
import com.pratham.assessment_lib.domain.ScienceQuestionChoice;
import com.pratham.assessment_lib.interfaces.AudioPlayerInterface;
import com.pratham.assessment_lib.interfaces.OnAssessmentComplete;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

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


        final ScienceQuestion scienceQuestion = new ScienceQuestion();
        scienceQuestion.setAnsdesc("Uttar Pradesh");
        scienceQuestion.setUpdatedby("1");
        scienceQuestion.setQlevel("2");
        scienceQuestion.setAddedby("1");
        scienceQuestion.setLanguageid("1");
        scienceQuestion.setLessonid("36");
        scienceQuestion.setQtid("1");
        scienceQuestion.setQid("1254");
        scienceQuestion.setSubjectid("21");
        scienceQuestion.setAddedtime("2019-06-11T11:33:35.763");
        scienceQuestion.setUpdatedtime("2019-06-11T11:33:35.763");
        scienceQuestion.setTopicid("21");
        scienceQuestion.setAnswer("Uttar Pradesh");
        scienceQuestion.setQname("Which is the most populated state in India?");
        scienceQuestion.setMarksPerQuestion("0");
        scienceQuestion.setIsAttempted(false);
        scienceQuestion.setIsCorrect(false);
        scienceQuestion.setPhotourl("");

        ScienceQuestion scienceQuestion2 = new ScienceQuestion();
        scienceQuestion2.setAnsdesc("2,3");
        scienceQuestion2.setUpdatedby("1");
        scienceQuestion2.setQlevel("1");
        scienceQuestion2.setAddedby("1");
        scienceQuestion2.setLanguageid("1");
        scienceQuestion2.setLessonid("42");
        scienceQuestion2.setQtid("2");
        scienceQuestion2.setQid("5341");
        scienceQuestion2.setSubjectid("27");
        scienceQuestion2.setAddedtime("2019-06-11T11:33:35.763");
        scienceQuestion2.setUpdatedtime("2019-06-11T11:33:35.763");
        scienceQuestion2.setTopicid("27");
        scienceQuestion2.setAnswer("2,3");
        scienceQuestion2.setQname("test");
        scienceQuestion2.setMarksPerQuestion("0");
        scienceQuestion2.setIsAttempted(false);
        scienceQuestion2.setIsCorrect(false);
        scienceQuestion2.setPhotourl("");

        ScienceQuestion scienceQuestionTrueFalse = new ScienceQuestion();
        scienceQuestionTrueFalse.setAnsdesc("test");
        scienceQuestionTrueFalse.setUpdatedby("1");
        scienceQuestionTrueFalse.setQlevel("1");
        scienceQuestionTrueFalse.setAddedby("1");
        scienceQuestionTrueFalse.setLanguageid("1");
        scienceQuestionTrueFalse.setLessonid("42");
        scienceQuestionTrueFalse.setQtid("3");
        scienceQuestionTrueFalse.setQid("5342");
        scienceQuestionTrueFalse.setSubjectid("27");
        scienceQuestionTrueFalse.setAddedtime("2019-06-11T11:33:35.763");
        scienceQuestionTrueFalse.setUpdatedtime("2019-06-11T11:33:35.763");
        scienceQuestionTrueFalse.setTopicid("27");
        scienceQuestionTrueFalse.setAnswer("True");
        scienceQuestionTrueFalse.setQname("test True False");
        scienceQuestionTrueFalse.setMarksPerQuestion("0");
        scienceQuestionTrueFalse.setIsAttempted(false);
        scienceQuestionTrueFalse.setIsCorrect(false);
        scienceQuestionTrueFalse.setPhotourl("");


        ScienceQuestion scienceQuestion5 = new ScienceQuestion();
        scienceQuestion5.setAnsdesc("test");
        scienceQuestion5.setUpdatedby("1");
        scienceQuestion5.setQlevel("2");
        scienceQuestion5.setAddedby("1");
        scienceQuestion5.setLanguageid("1");
        scienceQuestion5.setLessonid("42");
        scienceQuestion5.setQtid("5");
        scienceQuestion5.setQid("5382");
        scienceQuestion5.setSubjectid("27");
        scienceQuestion5.setAddedtime("2019-06-11T11:33:35.763");
        scienceQuestion5.setUpdatedtime("2019-06-11T11:33:35.763");
        scienceQuestion5.setTopicid("27");
        scienceQuestion5.setAnswer("True");
        scienceQuestion5.setQname("Fill in the blank with option (If our body did not have bones ...........)");
        scienceQuestion5.setMarksPerQuestion("0");
        scienceQuestion5.setIsAttempted(false);
        scienceQuestion5.setIsCorrect(false);
        scienceQuestion5.setPhotourl("");

        ScienceQuestion scienceQuestion6 = new ScienceQuestion();
        scienceQuestion6.setAnsdesc("Movement would have been impossible ");
        scienceQuestion6.setUpdatedby("1");
        scienceQuestion6.setQlevel("2");
        scienceQuestion6.setAddedby("1");
        scienceQuestion6.setLanguageid("1");
        scienceQuestion6.setLessonid("42");
        scienceQuestion6.setQtid("6");
        scienceQuestion6.setQid("5383");
        scienceQuestion6.setSubjectid("27");
        scienceQuestion6.setAddedtime("2019-06-11T11:33:35.763");
        scienceQuestion6.setUpdatedtime("2019-06-11T11:33:35.763");
        scienceQuestion6.setTopicid("27");
        scienceQuestion6.setAnswer("Movement would have been impossible ");
        scienceQuestion6.setQname("Fill in the blank (If there were no joints between the bones ...........)");
        scienceQuestion6.setMarksPerQuestion("0");
        scienceQuestion6.setIsAttempted(false);
        scienceQuestion6.setIsCorrect(false);
        scienceQuestion6.setPhotourl("");

        ScienceQuestion scienceQuestion7 = new ScienceQuestion();
        scienceQuestion7.setAnsdesc("Movement would have been impossible ");
        scienceQuestion7.setUpdatedby("1");
        scienceQuestion7.setQlevel("2");
        scienceQuestion7.setAddedby("1");
        scienceQuestion7.setLanguageid("1");
        scienceQuestion7.setLessonid("42");
        scienceQuestion7.setQtid("7");
        scienceQuestion7.setQid("5384");
        scienceQuestion7.setSubjectid("27");
        scienceQuestion7.setAddedtime("2019-06-11T11:33:35.763");
        scienceQuestion7.setUpdatedtime("2019-06-11T11:33:35.763");
        scienceQuestion7.setTopicid("27");
        scienceQuestion7.setAnswer("Movement would have been impossible ");
        scienceQuestion7.setQname("Arrange Sequence (1. Word  2. Paragraph  3. Sentence  4. Letters  5. Phrase)");
        scienceQuestion7.setMarksPerQuestion("0");
        scienceQuestion7.setIsAttempted(false);
        scienceQuestion7.setIsCorrect(false);
        scienceQuestion7.setPhotourl("");

        ScienceQuestion scienceQuestion8 = new ScienceQuestion();
        scienceQuestion8.setAnsdesc("We would't have been able to bend");
        scienceQuestion8.setUpdatedby("1");
        scienceQuestion8.setQlevel("2");
        scienceQuestion8.setAddedby("1");
        scienceQuestion8.setLanguageid("1");
        scienceQuestion8.setLessonid("42");
        scienceQuestion8.setQtid("8");
        scienceQuestion8.setQid("5385");
        scienceQuestion8.setSubjectid("27");
        scienceQuestion8.setAddedtime("2019-06-11T11:33:35.763");
        scienceQuestion8.setUpdatedtime("2019-06-11T11:33:35.763");
        scienceQuestion8.setTopicid("27");
        scienceQuestion8.setAnswer("We would't have been able to bend");
        scienceQuestion8.setQname("Video (If our backbone was made of one single bone .....)");
        scienceQuestion8.setMarksPerQuestion("0");
        scienceQuestion8.setIsAttempted(false);
        scienceQuestion8.setIsCorrect(false);
        scienceQuestion8.setPhotourl("");

        ScienceQuestion scienceQuestion9 = new ScienceQuestion();
        scienceQuestion9.setAnsdesc("Convection of heat");
        scienceQuestion9.setUpdatedby("1");
        scienceQuestion9.setQlevel("2");
        scienceQuestion9.setAddedby("1");
        scienceQuestion9.setLanguageid("1");
        scienceQuestion9.setLessonid("42");
        scienceQuestion9.setQtid("9");
        scienceQuestion9.setQid("5386");
        scienceQuestion9.setSubjectid("27");
        scienceQuestion9.setAddedtime("2019-06-11T11:33:35.763");
        scienceQuestion9.setUpdatedtime("2019-06-11T11:33:35.763");
        scienceQuestion9.setTopicid("27");
        scienceQuestion9.setAnswer("Convection of heat");
        scienceQuestion9.setQname("Audio (What is the main reason behind wind blowing on Earth?)");
        scienceQuestion9.setMarksPerQuestion("0");
        scienceQuestion9.setIsAttempted(false);
        scienceQuestion9.setIsCorrect(false);
        scienceQuestion9.setPhotourl("");


        ScienceQuestion scienceQuestion11 = new ScienceQuestion();
        scienceQuestion11.setAnsdesc("Mercury expands on heating");
        scienceQuestion11.setUpdatedby("1");
        scienceQuestion11.setQlevel("2");
        scienceQuestion11.setAddedby("1");
        scienceQuestion11.setLanguageid("1");
        scienceQuestion11.setLessonid("42");
        scienceQuestion11.setQtid("9");
        scienceQuestion11.setQid("5387");
        scienceQuestion11.setSubjectid("27");
        scienceQuestion11.setAddedtime("2019-06-11T11:33:35.763");
        scienceQuestion11.setUpdatedtime("2019-06-11T11:33:35.763");
        scienceQuestion11.setTopicid("27");
        scienceQuestion11.setAnswer("Mercury expands on heating");
        scienceQuestion11.setQname("Text question with Keywords (Why does the mercury in a thermometer rise with increase in temperature?)");
        scienceQuestion11.setMarksPerQuestion("0");
        scienceQuestion11.setIsAttempted(false);
        scienceQuestion11.setIsCorrect(false);
        scienceQuestion11.setPhotourl("");

        ArrayList<ScienceQuestion> scienceQuestionsList = new ArrayList<>();
        scienceQuestionsList.add(scienceQuestion);
        scienceQuestionsList.add(scienceQuestion2);
        scienceQuestionsList.add(scienceQuestionTrueFalse);
        scienceQuestionsList.add(scienceQuestion5);

        for (int i = 0; i < scienceQuestionsList.size(); i++) {
            scienceQuestionsList.get(i).setPaperid(assessmentSession);
            String qid = scienceQuestionsList.get(i).getQid();
            // ArrayList<ScienceQuestionChoice> scienceQuestionChoiceList = (ArrayList<ScienceQuestionChoice>) AppDatabase.getDatabaseInstance(ScienceAssessmentActivity.this).getScienceQuestionChoicesDao().getQuestionChoicesByQID(qid);
            ArrayList<ScienceQuestionChoice> scienceQuestionChoiceList = new ArrayList<>();
            for (int k = 0; k < 4; k++) {
                ScienceQuestionChoice scienceQuestionChoice = new ScienceQuestionChoice();
                scienceQuestionChoice.setQcid("A" + k);
                scienceQuestionChoice.setQid("1254");
                scienceQuestionChoice.setChoicename("Choicename" + k);
                if (k == 1)
                    scienceQuestionChoice.setCorrect("true");
                else
                    scienceQuestionChoice.setCorrect("false");
                scienceQuestionChoiceList.add(scienceQuestionChoice);
            }

            scienceQuestionsList.get(i).setLstquestionchoice(scienceQuestionChoiceList);
        }

        ScienceQuestion scienceQuestionMatchThePair = new ScienceQuestion();
        scienceQuestionMatchThePair.setAnsdesc("2143");
        scienceQuestionMatchThePair.setUpdatedby("1");
        scienceQuestionMatchThePair.setQlevel("1");
        scienceQuestionMatchThePair.setAddedby("1");
        scienceQuestionMatchThePair.setLanguageid("1");
        scienceQuestionMatchThePair.setLessonid("42");
        scienceQuestionMatchThePair.setQtid("4");
        scienceQuestionMatchThePair.setQid("5343");
        scienceQuestionMatchThePair.setSubjectid("27");
        scienceQuestionMatchThePair.setAddedtime("2019-06-11T11:33:35.763");
        scienceQuestionMatchThePair.setUpdatedtime("2019-06-11T11:33:35.763");
        scienceQuestionMatchThePair.setTopicid("27");
        scienceQuestionMatchThePair.setAnswer("2143");
        scienceQuestionMatchThePair.setQname("match The pair");
        scienceQuestionMatchThePair.setMarksPerQuestion("0");
        scienceQuestionMatchThePair.setIsAttempted(false);
        scienceQuestionMatchThePair.setIsCorrect(false);
        scienceQuestionMatchThePair.setPhotourl("");

        ScienceQuestionChoice scienceQuestionChoice1 = new ScienceQuestionChoice("20640", "5343", "3", "4", "false", "", "");
        ScienceQuestionChoice scienceQuestionChoice2 = new ScienceQuestionChoice("20639", "5343", "4", "3", "false", "", "");
        ScienceQuestionChoice scienceQuestionChoice3 = new ScienceQuestionChoice("20638", "5343", "1", "2", "false", "", "");
        ScienceQuestionChoice scienceQuestionChoice4 = new ScienceQuestionChoice("20637", "5343", "2", "1", "false", "", "");

        ArrayList<ScienceQuestionChoice> scienceQuestionChoiceList = new ArrayList<>();
        scienceQuestionChoiceList.add(scienceQuestionChoice1);
        scienceQuestionChoiceList.add(scienceQuestionChoice2);
        scienceQuestionChoiceList.add(scienceQuestionChoice3);
        scienceQuestionChoiceList.add(scienceQuestionChoice4);


        ArrayList<ScienceQuestionChoice> scienceQuestionChoiceList7 = new ArrayList<>();
        ScienceQuestionChoice scienceQuestionChoice11 = new ScienceQuestionChoice("20704", "5384", "1", "Letters", "true", "", "");
        ScienceQuestionChoice scienceQuestionChoice22 = new ScienceQuestionChoice("20705", "5384", "2", "Word", "true", "", "");
        ScienceQuestionChoice scienceQuestionChoice33 = new ScienceQuestionChoice("20706", "5384", "3", "Phrase", "true", "", "");
        ScienceQuestionChoice scienceQuestionChoice44 = new ScienceQuestionChoice("20707", "5384", "4", "Sentence", "true", "", "");
        ScienceQuestionChoice scienceQuestionChoice55 = new ScienceQuestionChoice("20708", "5384", "5", "Paragraph", "true", "", "");
        scienceQuestionChoiceList7.add(scienceQuestionChoice55);
        scienceQuestionChoiceList7.add(scienceQuestionChoice11);
        scienceQuestionChoiceList7.add(scienceQuestionChoice22);
        scienceQuestionChoiceList7.add(scienceQuestionChoice33);
        scienceQuestionChoiceList7.add(scienceQuestionChoice44);

        scienceQuestion7.setLstquestionchoice(scienceQuestionChoiceList7);

        scienceQuestionMatchThePair.setLstquestionchoice(scienceQuestionChoiceList);
        scienceQuestionsList.add(scienceQuestionMatchThePair);
        scienceQuestionsList.add(scienceQuestion6);
        scienceQuestionsList.add(scienceQuestion7);
        scienceQuestionsList.add(scienceQuestion8);

        AssessmentLibrary.getAssessmentLibrary(this)
                .setBackGroundColor(R.color.dark_blue)
                .setQuestionList(scienceQuestionsList)
                .setVideoMonitoring(true)
                .build()
                .setOnAssessmentCompleteListener(new OnAssessmentComplete() {
                    @Override
                    public void OnResult(List<ScienceQuestion> questionList) {
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
}
