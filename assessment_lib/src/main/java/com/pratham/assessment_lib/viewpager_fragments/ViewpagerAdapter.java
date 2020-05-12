package com.pratham.assessment_lib.viewpager_fragments;


import android.content.Context;

import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pratham.assessment_lib.Utility.QuestionType;
import com.pratham.assessment_lib.domain.AssessmentQuestion;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerAdapter extends FragmentPagerAdapter {
    //    Context context;
    private static int NUM_ITEMS = 0;
    private List<Fragment> fragmentList;
    Fragment currentFragment;

    public ViewpagerAdapter(FragmentManager fm, Context context, List<AssessmentQuestion> assessmentQuestionList) {
        super(fm);
//        this.context = context;
        NUM_ITEMS = assessmentQuestionList.size();
        fragmentList = new ArrayList<>();
        for (int i = 0; i < assessmentQuestionList.size(); i++) {
            AssessmentQuestion assessmentQuestion = assessmentQuestionList.get(i);
            //String questionType = scienceQuestion.getQtid();
            QuestionType questionType = assessmentQuestion.getQtid();

            //todo #alter
            /*switch (questionType) {
                case "1":
                    fragmentList.add(McqFillInTheBlanksFragment.newInstance(i, scienceQuestion));
                    break;
                case "2":
                    fragmentList.add(MultipleSelectFragment.newInstance(i, scienceQuestion));
                    break;
                case "3":
                    fragmentList.add(TrueFalseFragment.newInstance(i, scienceQuestion));
                    break;
               case "4":
                    fragmentList.add(MatchThePairFragment.newInstance(i, scienceQuestion));
                    break;
                 case "5":
                    fragmentList.add(McqFillInTheBlanksFragment.newInstance(i, scienceQuestion));
                    break;
              case "6":
                    fragmentList.add(FillInTheBlanksWithoutOptionFragment.newInstance(i, scienceQuestion));
                    break;
                  case "7":
                    fragmentList.add(ArrangeSequenceFragment.newInstance(i, scienceQuestion));
                    break;
                case "8":
                    fragmentList.add(VideoFragment.newInstance(i, scienceQuestion));
                    break;
                case "9":
                    fragmentList.add(AudioFragment.newInstance(i, scienceQuestion));
                    break;
                case "11":
                    fragmentList.add(FillInTheBlanksWithoutOptionFragment.newInstance(i, scienceQuestion));
                    break;
               case "12":
                    fragmentList.add(ImageAnswerFragment.newInstance(i, scienceQuestion));
                    break;
                *//* case "13":
                    fragmentList.add(TextParagraphFragment.newInstance(i, scienceQuestion));
                    break;*//*
                default:
                    break;
            }*/

            switch (questionType) {
                case MULTIPLE_CHOICE:
                    fragmentList.add(McqFillInTheBlanksFragment.newInstance(i, assessmentQuestion));
                    break;
                case MULTIPLE_SELECT:
                    fragmentList.add(MultipleSelectFragment.newInstance(i, assessmentQuestion));
                    break;
                case TRUE_FALSE:
                    fragmentList.add(TrueFalseFragment.newInstance(i, assessmentQuestion));
                    break;
                case MATCHING_PAIR:
                    fragmentList.add(MatchThePairFragment.newInstance(i, assessmentQuestion));
                    break;
                case FILL_IN_THE_BLANK_WITH_OPTION:
                    fragmentList.add(McqFillInTheBlanksFragment.newInstance(i, assessmentQuestion));
                    break;
                case FILL_IN_THE_BLANK:
                    fragmentList.add(FillInTheBlanksWithoutOptionFragment.newInstance(i, assessmentQuestion));
                    break;
                case ARRANGE_SEQUENCE:
                    fragmentList.add(ArrangeSequenceFragment.newInstance(i, assessmentQuestion));
                    break;
                case VIDEO:
                    fragmentList.add(VideoFragment.newInstance(i, assessmentQuestion));
                    break;
                case AUDIO:
                    fragmentList.add(AudioFragment.newInstance(i, assessmentQuestion));
                    break;
                case KEYWORDS_QUESTION:
                    fragmentList.add(FillInTheBlanksWithoutOptionFragment.newInstance(i, assessmentQuestion));
                    break;
                case IMAGE_ANSWER:
                    fragmentList.add(ImageAnswerFragment.newInstance(i, assessmentQuestion));
                    break;
                case TEXT_PARAGRAPH:
                    fragmentList.add(TextParagraphFragment.newInstance(i, assessmentQuestion));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        currentFragment = fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
