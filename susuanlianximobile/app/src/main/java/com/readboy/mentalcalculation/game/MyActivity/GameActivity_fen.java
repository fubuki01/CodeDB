package com.readboy.mentalcalculation.game.MyActivity;

import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.readboy.mentalcalculation.R;

public class GameActivity_fen extends BaseGameActivity {

    private EditText edit_answer_top;
    private EditText edit_answer_down;
    private boolean edit_answer_top_touch = true;
    private boolean edit_answer_down_touch = false;
    private TextView[] pronumber = new TextView[8];

    @Override
    protected void findChildView() {

        edit_answer_top = (EditText) findViewById(R.id.figure_4_top);
        edit_answer_down = (EditText) findViewById(R.id.figure_4_down);
        hintInput(edit_answer_top);
        hintInput(edit_answer_down);
        pronumber[0] = (TextView)findViewById(R.id.figure_1_top);
        pronumber[1] = (TextView)findViewById(R.id.figure_1_down);
        pronumber[2] = (TextView)findViewById(R.id.operation_character_1);
        pronumber[3] = (TextView)findViewById(R.id.figure_2_top);
        pronumber[4] = (TextView)findViewById(R.id.figure_2_down);
        pronumber[5] = (TextView)findViewById(R.id.operation_character_2);
        pronumber[6] = (TextView)findViewById(R.id.figure_3_top);
        pronumber[7] = (TextView)findViewById(R.id.figure_3_down);

    }

    @Override
    protected void setChiledListener() {
        edit_answer_top.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    edit_answer_top_touch = true;
                    edit_answer_down_touch = false;
                }
            }
        });

        edit_answer_down.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    edit_answer_top_touch = false;
                    edit_answer_down_touch = true;
                }
            }
        });
    }
    
    @Override
    protected void setKeyBoard(View view) {
        switch (view.getId()) {
            case R.id.key_delete:
                if (edit_answer_top_touch) {
                    Editable editable = edit_answer_top.getText();
                    if (editable.length() > 0) {
                        int length = editable.length();
                        editable.delete(length - 1, length);
                    }
                    break;
                }else if (edit_answer_down_touch){
                    Editable editable_yu = edit_answer_down.getText();
                    if (editable_yu.length() > 0) {
                        int length = editable_yu.length();
                        editable_yu.delete(length - 1, length);
                    }
                    break;
                }

            case R.id.key_dian:
                if (edit_answer_top_touch){
                    edit_answer_top.append(".");
                    break;
                }else if (edit_answer_down_touch){
                    edit_answer_down.append(".");
                    break;
                }

            case R.id.key_0:
                if (edit_answer_top_touch){
                    edit_answer_top.append("0");
                    break;
                }else if (edit_answer_down_touch){
                    edit_answer_down.append("0");
                    break;
                }
            case R.id.key_1:
                if (edit_answer_top_touch){
                    edit_answer_top.append("1");
                    break;
                }else if (edit_answer_down_touch){
                    edit_answer_down.append("1");
                    break;
                }
            case R.id.key_2:
                if (edit_answer_top_touch){
                    edit_answer_top.append("2");
                    break;
                }else if (edit_answer_down_touch){
                    edit_answer_down.append("2");
                    break;
                }
            case R.id.key_3:
                if (edit_answer_top_touch){
                    edit_answer_top.append("3");
                    break;
                }else if (edit_answer_down_touch){
                    edit_answer_down.append("3");
                    break;
                }
            case R.id.key_4:
                if (edit_answer_top_touch){
                    edit_answer_top.append("4");
                    break;
                }else if (edit_answer_down_touch){
                    edit_answer_down.append("4");
                    break;
                }
            case R.id.key_5:
                if (edit_answer_top_touch){
                    edit_answer_top.append("5");
                    break;
                }else if (edit_answer_down_touch){
                    edit_answer_down.append("5");
                    break;
                }
            case R.id.key_6:
                if (edit_answer_top_touch){
                    edit_answer_top.append("6");
                    break;
                }else if (edit_answer_down_touch){
                    edit_answer_down.append("6");
                    break;
                }
            case R.id.key_7:
                if (edit_answer_top_touch){
                    edit_answer_top.append("7");
                    break;
                }else if (edit_answer_down_touch){
                    edit_answer_down.append("7");
                    break;
                }
            case R.id.key_8:
                if (edit_answer_top_touch){
                    edit_answer_top.append("8");
                    break;
                }else if (edit_answer_down_touch){
                    edit_answer_down.append("8");
                    break;
                }
            case R.id.key_9:
                if (edit_answer_top_touch){
                    edit_answer_top.append("9");
                    break;
                }else if (edit_answer_down_touch){
                    edit_answer_down.append("9");
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
        int length = problem.length();
        int j=0,num=0;
        char t;
        String temp="";
        for(int i=0;i<length;i++)
        {
            t=problem.charAt(i);
            if('0'<=t && t<='9')
                temp+=t;
            else if(t=='+'||t=='-'||t=='×'||t=='÷')
            {
                pronumber[j++].setText(temp);
                temp="";
                pronumber[j++].setText(t+"");
            }
            else if(t=='/')
            {
                pronumber[j++].setText(temp);
                temp="";
                num++;
            }
            else
            {
                pronumber[j].setText(temp);
                break;
            }
        }
        if(num<3)
        {
            findViewById(R.id.figure_3).getLayoutParams().width=0;
            findViewById(R.id.operation_character_2).getLayoutParams().width=0;
            for(int i=0;i<pronumber.length;i++)
                pronumber[i].setTextSize(33);
            edit_answer_top.setTextSize(33);
            edit_answer_down.setTextSize(33);
            ((TextView)findViewById(R.id.bracket1)).setTextSize(33);
            ((TextView)findViewById(R.id.bracket2)).setTextSize(33);
            ((TextView)findViewById(R.id.bracket3)).setTextSize(33);
            ((TextView)findViewById(R.id.bracket4)).setTextSize(33);

        }
        edit_answer_top.setText("");
        edit_answer_down.setText("");
        edit_answer_top_touch = true;
        edit_answer_down_touch = false;
        edit_answer_top.requestFocus();
        edit_answer_down.clearFocus();
    }

    @Override
    protected int InputisEmpty() {
        if ((edit_answer_top.getText().length() <= 0)&&(edit_answer_down.getText().length()<=0))
            return 0;
        else if ((edit_answer_top.getText().length() <= 0)||(edit_answer_down.getText().length()<=0))
            return 1;
        else
            return 2;
    }

    @Override
    protected void judgeAns() {
        String anwser_input;
        anwser_input = edit_answer_top.getText().toString()+"/"+edit_answer_down.getText().toString();
        if (anwser_input.equals(answer)) {
            correct = true;
            Animal(true);
            problem_number_correct_count++;
        } else {
            correct = false;
            Animal(false);
        }
    }

}
