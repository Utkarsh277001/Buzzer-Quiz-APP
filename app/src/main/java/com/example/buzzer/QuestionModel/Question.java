package com.example.buzzer.QuestionModel;

public class Question {
    private String QuestionId;
    private Boolean isTrue;

    public Question() {
    }

    public Question(String questionId, Boolean IsTrue) {
        QuestionId = questionId;
        isTrue=IsTrue;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public Boolean getTrue() {
        return isTrue;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public void setTrue(Boolean aTrue) {
        isTrue = aTrue;
    }

    @Override
    public String toString() {
        return "Question{" +
                "QuestionId='" + QuestionId + '\'' +
                ", isTrue=" + isTrue +
                '}';
    }
}
