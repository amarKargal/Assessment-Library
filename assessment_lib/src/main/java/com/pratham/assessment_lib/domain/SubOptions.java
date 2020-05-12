package com.pratham.assessment_lib.domain;


import androidx.annotation.NonNull;


import java.io.Serializable;

//@Entity
public class SubOptions implements Serializable {

    @NonNull
   // @PrimaryKey
    private String qcid;

    private String qid;
    private String matchingname;
    private String choicename;
    private String correct;
    private String matchingurl;
    private String choiceurl;
    //@Ignore
    private String myIscorrect = "false";


    //todo #alter remove
    public SubOptions(String qcid, String qid, String matchingname, String choicename, String correct, String matchingurl, String choiceurl) {
        this.qcid = qcid;
        this.qid = qid;
        this.matchingname = matchingname;
        this.choicename = choicename;
        this.correct = correct;
        this.matchingurl = matchingurl;
        this.choiceurl = choiceurl;
    }

    public SubOptions() {
    }

    public String getMyIscorrect() {
        return myIscorrect;
    }

    public void setMyIscorrect(String myIscorrect) {
        if (myIscorrect == null) {
            this.myIscorrect = "false";
        } else
            this.myIscorrect = myIscorrect;
    }


    public String getMatchingurl() {
        return matchingurl;
    }

    public void setMatchingurl(String matchingurl) {
        this.matchingurl = matchingurl;
    }

    public String getChoiceurl() {
        if (choiceurl == null) {
            return "";
        }
        return choiceurl;
    }

    public void setChoiceurl(String choiceurl) {
        this.choiceurl = choiceurl;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getQcid() {
        return qcid;
    }

    public void setQcid(String qcid) {
        this.qcid = qcid;
    }

    public String getChoicename() {
        return choicename;
    }

    public void setChoicename(String choicename) {
        this.choicename = choicename;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getMatchingname() {
        return matchingname;
    }

    public void setMatchingname(String matchingname) {
        this.matchingname = matchingname;
    }

}