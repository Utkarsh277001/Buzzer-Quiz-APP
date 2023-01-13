package com.example.buzzer.Data;

import com.example.buzzer.QuestionModel.Question;

import java.util.ArrayList;

public interface AnswerListAsync {
    void processFinish(ArrayList<Question>questionArrayList);
}
