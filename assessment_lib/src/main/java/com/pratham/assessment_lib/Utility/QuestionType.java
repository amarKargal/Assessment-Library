package com.pratham.assessment_lib.Utility;

public enum QuestionType {
 /*   //Assessment question types
    public static final String MULTIPLE_CHOICE = "1";
    public static final String MULTIPLE_SELECT = "2";
    public static final String TRUE_FALSE = "3";
    public static final String MATCHING_PAIR = "4";
    public static final String FILL_IN_THE_BLANK_WITH_OPTION = "5";
    public static final String FILL_IN_THE_BLANK = "6";
    public static final String ARRANGE_SEQUENCE = "7";
    public static final String VIDEO = "8";
    public static final String AUDIO = "9";
    public static final String KEYWORDS_QUESTION = "11";
    public static final String IMAGE_ANSWER = "12";
    public static final String TEXT_PARAGRAPH = "13";*/


    MULTIPLE_CHOICE("1"),
    MULTIPLE_SELECT("2"),
    TRUE_FALSE("3"),
    MATCHING_PAIR("4"),
    FILL_IN_THE_BLANK_WITH_OPTION("5"),
    FILL_IN_THE_BLANK("6"),
    ARRANGE_SEQUENCE("7"),
    VIDEO("8"),
    AUDIO("9"),
    KEYWORDS_QUESTION("11"),
    IMAGE_ANSWER("12"),
    TEXT_PARAGRAPH("13");

    private String questionID;

    QuestionType(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestionID() {
        return questionID;
    }
}
