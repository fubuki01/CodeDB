package com.readboy.mentalcalculation.game.MyActivity;

import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.readboy.mentalcalculation.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.readboy.mentalcalculation.R.id.content_of_problrm;
import static com.readboy.mentalcalculation.R.id.edit_answer_below;
import static com.readboy.mentalcalculation.R.id.edit_answer_below;

public class GameActivity_fraction_integer extends BaseGameActivity {

    private TextView integer_shu_1,mark_operation_1,
            fraction_1_top,fraction_1_down,mark_operation_2,integer_shu_2;
    private EditText edit_answer, edit_answer_below;
    private boolean edit_answer_touch = true;
    private boolean edit_answer_below_touch = false;


    @Override
    protected void findChildView() {
        integer_shu_1 = (TextView) findViewById(R.id.integer_shu_1);
        mark_operation_1 = (TextView) findViewById(R.id.mark_operation_1);
        fraction_1_top = (TextView) findViewById(R.id.fraction_1_top);
        fraction_1_down = (TextView) findViewById(R.id.fraction_1_down);
        mark_operation_2 = (TextView) findViewById(R.id.mark_operation_2);
        integer_shu_2 = (TextView) findViewById(R.id.integer_shu_2);
        edit_answer = (EditText) findViewById(R.id.edit_answer);
        edit_answer_below = (EditText) findViewById(R.id.edit_answer_below);
        hintInput(edit_answer);
        hintInput(edit_answer_below);
    }

    @Override
    protected void setChiledListener() {
        edit_answer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    edit_answer_touch = true;
                    edit_answer_below_touch = false;
                }
            }
        });

        edit_answer_below.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    edit_answer_below_touch = true;
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
                }else if (edit_answer_below_touch){
                    Editable editable_yu = edit_answer_below.getText();
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
                }else if (edit_answer_below_touch){
                    edit_answer_below.append(".");
                    break;
                }

            case R.id.key_0:
                if (edit_answer_touch){
                    edit_answer.append("0");
                    break;
                }else if (edit_answer_below_touch){
                    edit_answer_below.append("0");
                    break;
                }
            case R.id.key_1:
                if (edit_answer_touch){
                    edit_answer.append("1");
                    break;
                }else if (edit_answer_below_touch){
                    edit_answer_below.append("1");
                    break;
                }
            case R.id.key_2:
                if (edit_answer_touch){
                    edit_answer.append("2");
                    break;
                }else if (edit_answer_below_touch){
                    edit_answer_below.append("2");
                    break;
                }
            case R.id.key_3:
                if (edit_answer_touch){
                    edit_answer.append("3");
                    break;
                }else if (edit_answer_below_touch){
                    edit_answer_below.append("3");
                    break;
                }
            case R.id.key_4:
                if (edit_answer_touch){
                    edit_answer.append("4");
                    break;
                }else if (edit_answer_below_touch){
                    edit_answer_below.append("4");
                    break;
                }
            case R.id.key_5:
                if (edit_answer_touch){
                    edit_answer.append("5");
                    break;
                }else if (edit_answer_below_touch){
                    edit_answer_below.append("5");
                    break;
                }
            case R.id.key_6:
                if (edit_answer_touch){
                    edit_answer.append("6");
                    break;
                }else if (edit_answer_below_touch){
                    edit_answer_below.append("6");
                    break;
                }
            case R.id.key_7:
                if (edit_answer_touch){
                    edit_answer.append("7");
                    break;
                }else if (edit_answer_below_touch){
                    edit_answer_below.append("7");
                    break;
                }
            case R.id.key_8:
                if (edit_answer_touch){
                    edit_answer.append("8");
                    break;
                }else if (edit_answer_below_touch){
                    edit_answer_below.append("8");
                    break;
                }
            case R.id.key_9:
                if (edit_answer_touch){
                    edit_answer.append("9");
                    break;
                }else if (edit_answer_below_touch){
                    edit_answer_below.append("9");
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
        String[] problem_char = new String[]{};
        problem_char = problem.split(",");
        if (problem.contains("乘")){
            fraction_1_top.setText(problem_char[0]);
            fraction_1_down.setText(problem_char[2]);
            mark_operation_2.setVisibility(View.VISIBLE);
            mark_operation_2.setText("×");
            integer_shu_2.setText(problem_char[4]);
        }else if (problem.contains("除")){
            fraction_1_top.setText(problem_char[0]);
            fraction_1_down.setText(problem_char[2]);
            mark_operation_2.setVisibility(View.VISIBLE);
            mark_operation_2.setText("÷");
            integer_shu_2.setText(problem_char[4]);
        }else if (problem.contains("整")){
            integer_shu_2.setVisibility(View.INVISIBLE);
            mark_operation_2.setVisibility(View.INVISIBLE);
            integer_shu_1.setVisibility(View.VISIBLE);
            mark_operation_1.setVisibility(View.VISIBLE);
            integer_shu_1.setText(problem_char[0]);
            mark_operation_1.setText("÷");
            fraction_1_top.setText(problem_char[2]);
            fraction_1_down.setText(problem_char[4]);
        }
        edit_answer_touch = true;
        edit_answer_below_touch = false;
        edit_answer.invalidate();
        edit_answer.setText("");
        edit_answer_below.invalidate();
        edit_answer_below.setText("");
        edit_answer.requestFocus();
        edit_answer_below.clearFocus();
    }

    @Override
    protected int InputisEmpty() {
        if ((edit_answer.getText().length() <= 0)&&(edit_answer_below.getText().length()<=0)){
            answer_count = 0;
        }else if ((edit_answer.getText().length() <= 0)||(edit_answer_below.getText().length()<=0)){
            answer_count = 1;
        }else {
            answer_count = 2;
        }
        return answer_count;

    }

    @Override
    protected void judgeAns() {
        String anwser_input,anwser_input_down;
        String[] answer_char = new String[]{};
        answer_char = answer.split(",");
        anwser_input = edit_answer.getText().toString();
        anwser_input_down = edit_answer_below.getText().toString();
        if ((anwser_input.equals(answer_char[0]))&&(anwser_input_down.equals(answer_char[2]))) {
            correct = true;
            Animal(true);
            problem_number_correct_count++;
        } else {
            correct = false;
            Animal(false);
        }
    }
}
