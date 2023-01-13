package com.example.buzzer.Data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.buzzer.AppCotroller.AppControl;
import com.example.buzzer.QuestionModel.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    String Url="https://utkarsh277001.github.io/db.json/db.json";

    ArrayList<Question>questionArrayList=new ArrayList<Question>();

     //FUNCTION
    public List<Question> getQuestion  (final AnswerListAsync callback){


        JsonArrayRequest jsonArRq=new JsonArrayRequest(Request.Method.GET, Url, null, response -> {
            for(int i=0;i<response.length();i++){
                try {
                    //adding question to Question Class
                    Question question=new Question(response.getJSONArray(i).get(0).toString(),
                            response.getJSONArray(i).getBoolean(1));

                    //Adding Question to the array list
                    questionArrayList.add(question);
                 //if we try to fetch the array list outside this class or in MainActvity then there will be
                    // error or it show the empty array so to solve the problem ,before solving the problem the problem
                    //we try to understand the problem , the problem is that when we call get question from mainactivity
                    //at that time in repository class fetching questionn from the API so at same time we cannot pass the questionn
                    //it will impact the integrity of the system ad put the system in unstable state thats why androifd not alllow to do this
                    //so we ca do one thing , we can retrive the data after the data is completely fetched , for this we will use the type\
                    // of call back method ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
                    if(null!=callback) callback.processFinish(questionArrayList);
        }, error -> Log.d("hue","Failed to Fetch"));
        AppControl.getInstance().addToRequestQueue(jsonArRq);

        return questionArrayList;
    }

}
