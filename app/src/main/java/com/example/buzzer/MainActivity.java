package com.example.buzzer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buzzer.Data.AnswerListAsync;
import com.example.buzzer.Data.Repository;
import com.example.buzzer.QuestionModel.Question;
import com.example.buzzer.QuestionModel.score;
import com.example.buzzer.databinding.ActivityMainBinding;
import com.example.buzzer.util.Prefss;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int questionIndex=1;
    private int score;
    private ArrayList<Question>que_tion;
    private int prefs;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);


        //here we write code for pointing the arraylist in mainActivity to Data we retrive from the API and store this data in MainActivity's Arraylist.
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        new Repository().getQuestion(new AnswerListAsync() {
            @SuppressLint("SetTextI18n")
            @Override

            public void processFinish(ArrayList<Question> questionArrayList) {
                que_tion=questionArrayList;
                binding.QueView.setText(que_tion.get(randonNo()).getQuestionId());
                binding.que.setText(String.format("Question No. : %d/%d", questionIndex, questionArrayList.size()));
                Prefss pref =new Prefss(MainActivity.this);
                binding.hghScore.setText("High Score :"+pref.getScore());
                Log.d("MEME","onCreate"+questionArrayList);
            }
        });

        //Next Button OnClick listener and updating question
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionIndex++;
                updateQue();


                //Updating the question counter\
                queCounter();


            }
        });


        binding.trueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAns(true);

            }
        });

        binding.falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAns(false);

            }
        });

        binding.ScoreView.setText("Score : "+String.valueOf(scoreNo.getScore()));

    }

    //randon no. generator for random question
    private int randonNo(){
        int x=(int)(Math.random()*100);
        int y=x%que_tion.size();
        return y;

    }

    //Updating the question counter;
    private void queCounter(){
        binding.que.setText(String.format("Question No. : %d/%d", questionIndex, que_tion.size()));
    }

    //Updating the question
    private void updateQue() {
        binding.QueView.setText(que_tion.get(randonNo()).getQuestionId());
    }

    private void checkAns(boolean ansInput) {
        boolean correctAns=que_tion.get(questionIndex).getTrue();
        int msgId=0;
        if(ansInput== correctAns){
           addscr();
            Log.d("add", "score"+score);
           msgId=R.string.correct;
           fadeAnimation();
            Prefss pref =new Prefss(MainActivity.this);
           pref.highestScore(score);
           binding.hghScore.setText("High Score :"+pref.getScore());


        }else{
            dedscr();
            Log.d("ded", "score"+score);
            msgId=R.string.Incorrect;
            animation();

        }

        Snackbar.make(binding.QueView,msgId,Snackbar.LENGTH_SHORT).show();


        //we add timer/interval of 0.7sec for updation of the question on clicking true or false so that animation occur
        // first before change of the question;
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               questionIndex++;
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       updateQue();
                                       queCounter();
                                   }
                               });
                               timer.cancel();

                           }
                       },700
        );

    }


    //Code or function for fade animation
    private void fadeAnimation() {
        AlphaAnimation alpha=new AlphaAnimation(1.0f,0.0f);
        alpha.setDuration(300);
        alpha.setRepeatCount(1);
        alpha.setRepeatMode(Animation.REVERSE);
        binding.viewQue.startAnimation(alpha);

        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.QueView.setTextColor(Color.GREEN);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.QueView.setTextColor(Color.BLACK);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void animation(){

        Animation shake= AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_anim);
        shake.start();
        shake.setDuration(150);
        shake.setRepeatCount(1);

        binding.viewQue.startAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.QueView.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.QueView.setTextColor(Color.BLACK);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }



    //creating the object of the Score class
    score scoreNo= new score();

    //to Decrement the score on wrong answer
    private void dedscr(){
        if(score >0){
        score-=100;
        scoreNo.setScore(score);
        binding.ScoreView.setText("Score : "+String.valueOf(scoreNo.getScore()));
        }
        else{
            score=0;
        }
    }

    //to increment the score on correct answer
    private void addscr(){
        score+=100;
        scoreNo.setScore(score);
        binding.ScoreView.setText("Score : "+String.valueOf(scoreNo.getScore()));
    }
}