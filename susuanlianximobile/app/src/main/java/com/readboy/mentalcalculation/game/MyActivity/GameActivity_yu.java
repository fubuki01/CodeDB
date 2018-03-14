package com.readboy.mentalcalculation.game.MyActivity;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.readboy.mentalcalculation.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.security.auth.login.LoginException;

import static android.R.attr.editable;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.readboy.mentalcalculation.R.id.edit_answer_below;
import static java.lang.Integer.getInteger;

public class GameActivity_yu extends BaseGameActivity {

    private TextView content_of_problrm;
    private EditText edit_answer, edit_answer_yu;
    private boolean edit_answer_touch = false;
    private boolean edit_answer_yu_touch = false;
    protected int answer_integer,answer_yu;


    @Override
    protected void findChildView() {
        content_of_problrm = (TextView) findViewById(R.id.content_of_problrm);
        edit_answer = (EditText) findViewById(R.id.edit_answer);
        edit_answer_yu = (EditText) findViewById(R.id.edit_answer_yu);
        hintInput(edit_answer);
        hintInput(edit_answer_yu);
    }



    @Override
    protected void setChiledListener() {
        edit_answer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    edit_answer_touch = true;
                    edit_answer_yu_touch = false;
                }
            }
        });
        edit_answer_yu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    edit_answer_yu_touch = true;
                    edit_answer_touch = false;
                }
            }
        });
    }


    @Override
    protected void setKeyBoard(View view) {
        switch (view.getId()) {
            case R.id.key_delete:
                if (edit_answer_touch) {
                    Editable editable = edit_answer.getText();
                    if (editable.length() > 0) {
                        int length = editable.length();
                        editable.delete(length - 1, length);
                    }
                    break;
                }else if (edit_answer_yu_touch){
                    Editable editable_yu = edit_answer_yu.getText();
                    if (editable_yu.length() > 0) {
                        int length = editable_yu.length();
                        editable_yu.delete(length - 1, length);
                    }
                    break;
                }

            case R.id.key_dian:
                if (edit_answer_touch){
                    edit_answer.append(".");
                    break;
                }else if (edit_answer_yu_touch){
                    edit_answer_yu.append(".");
                    break;
                }

            case R.id.key_0:
                Log.e("111111", edit_answer_touch+""+edit_answer_yu_touch);
                if (edit_answer_touch){
                    edit_answer.append("0");
                    break;
                }else if (edit_answer_yu_touch){
                    edit_answer_yu.append("0");
                    break;
                }
            case R.id.key_1:
                if (edit_answer_touch){
                    edit_answer.append("1");
                    break;
                }else if (edit_answer_yu_touch){
                    edit_answer_yu.append("1");
                    break;
                }
            case R.id.key_2:
                if (edit_answer_touch){
                    edit_answer.append("2");
                    break;
                }else if (edit_answer_yu_touch){
                    edit_answer_yu.append("2");
                    break;
                }
            case R.id.key_3:
                if (edit_answer_touch){
                    edit_answer.append("3");
                    break;
                }else if (edit_answer_yu_touch){
                    edit_answer_yu.append("3");
                    break;
                }
            case R.id.key_4:
                if (edit_answer_touch){
                    edit_answer.append("4");
                    break;
                }else if (edit_answer_yu_touch){
                    edit_answer_yu.append("4");
                    break;
                }
            case R.id.key_5:
                if (edit_answer_touch){
                    edit_answer.append("5");
                    break;
                }else if (edit_answer_yu_touch){
                    edit_answer_yu.append("5");
                    break;
                }
            case R.id.key_6:
                if (edit_answer_touch){
                    edit_answer.append("6");
                    break;
                }else if (edit_answer_yu_touch){
                    edit_answer_yu.append("6");
                    break;
                }
            case R.id.key_7:
                if (edit_answer_touch){
                    edit_answer.append("7");
                    break;
                }else if (edit_answer_yu_touch){
                    edit_answer_yu.append("7");
                    break;
                }
            case R.id.key_8:
                if (edit_answer_touch){
                    edit_answer.append("8");
                    break;
                }else if (edit_answer_yu_touch){
                    edit_answer_yu.append("8");
                    break;
                }
            case R.id.key_9:
                if (edit_answer_touch){
                    edit_answer.append("9");
                    break;
                }else if (edit_answer_yu_touch){
                    edit_answer_yu.append("9");
                    break;
                }
            default:
                break;
        }
    }

    @Override
    protected void setProAndAns(String pro, String ans) {
        problem = pro;
        answer = ans;
        content_of_problrm.setText(problem);
        edit_answer_touch = true;
        edit_answer_yu_touch = false;
        edit_answer.invalidate();
        edit_answer.setText("");
        edit_answer_yu.invalidate();
        edit_answer_yu.setText("");
        edit_answer.requestFocus();
        edit_answer_yu.clearFocus();
    }

    @Override
    protected int InputisEmpty() {
        if ((edit_answer.getText().length() <= 0)&&(edit_answer_yu.getText().length()<=0)){
            answer_count = 0;
        }else if ((edit_answer.getText().length() <= 0)||(edit_answer_yu.getText().length()<=0)){
            answer_count = 1;
        }else {
            answer_count = 2;
        }
        return answer_count;

    }

    @Override
    protected void judgeAns() {
        String anwser_input,anwser_input_yu,string_answer_integer,string_answer_yu;
        anwser_input = edit_answer.getText().toString();
        anwser_input_yu = edit_answer_yu.getText().toString();
        Log.e("111111", answer+"");
        answer_integer = (int)(Integer.parseInt(answer))/10;
        answer_yu = Integer.parseInt(answer)%10;
        string_answer_integer = String.valueOf(answer_integer);
        string_answer_yu = String.valueOf(answer_yu);
        if ((anwser_input.equals(string_answer_integer))&&(anwser_input_yu.equals(string_answer_yu))) {
            correct = true;
            Animal(true);
            problem_number_correct_count++;
        } else {
            correct = false;
            Animal(false);
        }
    }
}
