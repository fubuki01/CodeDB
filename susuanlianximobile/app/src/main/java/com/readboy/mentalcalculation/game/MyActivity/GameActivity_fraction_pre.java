package com.readboy.mentalcalculation.game.MyActivity;

import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.readboy.mentalcalculation.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.readboy.mentalcalculation.R.id.content_of_problrm;
import static com.readboy.mentalcalculation.R.id.fraction_1;

public class GameActivity_fraction_pre extends BaseGameActivity {

    private TextView fraction_1_top, fraction_1_down;
    private EditText edit_answer;
    private TextView pre_mark;

    @Override
    protected void findChildView() {
        fraction_1_top = (TextView) findViewById(R.id.fraction_1_top);
        fraction_1_down = (TextView) findViewById(R.id.fraction_1_down);
        pre_mark = (TextView) findViewById(R.id.pre_mark);
        edit_answer = (EditText) findViewById(R.id.edit_answer);
        hintInput(edit_answer);
    }

    @Override
    protected void setChiledListener() {

    }

    @Override
    protected void setKeyBoard(View view) {
        switch (view.getId()) {
            case R.id.key_delete:
                Editable editable = edit_answer.getText();
                if (editable.length() > 0) {
                    int length = editable.length();
                    editable.delete(length - 1, length);
                }
                break;
            case R.id.key_dian:
                edit_answer.append(".");
                break;
            case R.id.key_0:
                edit_answer.append("0");
                break;
            case R.id.key_1:
                edit_answer.append("1");
                break;
            case R.id.key_2:
                edit_answer.append("2");
                break;
            case R.id.key_3:
                edit_answer.append("3");
                break;
            case R.id.key_4:
                edit_answer.append("4");
                break;
            case R.id.key_5:
                edit_answer.append("5");
                break;
            case R.id.key_6:
                edit_answer.append("6");
                break;
            case R.id.key_7:
                edit_answer.append("7");
                break;
            case R.id.key_8:
                edit_answer.append("8");
                break;
            case R.id.key_9:
                edit_answer.append("9");
                break;
            default:
                break;
        }
    }

    @Override
    protected void setProAndAns(String pro, String ans) {
        problem = pro;
        answer = ans;
        String[] problem_char;
        problem_char = problem.split("/");
        fraction_1_top.setText(problem_char[0]);
        fraction_1_down.setText(problem_char[1]);
        edit_answer.invalidate();
        edit_answer.setText("");
        if(answer.indexOf(".")!=-1)
            pre_mark.getLayoutParams().width=0;
        else
            findViewById(R.id.cue_item).getLayoutParams().width=0;
    }

    @Override
    protected int InputisEmpty() {
        if (edit_answer.getText().length() <= 0) {
            answer_count = 0;
        } else {
            answer_count = 2;
        }
        return answer_count;
    }

    @Override
    protected void judgeAns() {
        String anwser_input;
        anwser_input = edit_answer.getText().toString();
        if (anwser_input.equals(answer)) {
            correct = true;
            Animal(true);
            problem_number_correct_count++;
        }
        if (!anwser_input.equals(answer)) {
            correct = false;
            Animal(false);
        }
    }
}
