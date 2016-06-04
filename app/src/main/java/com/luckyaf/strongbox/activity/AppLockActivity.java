package com.luckyaf.strongbox.activity;

/**
 * 类描述：
 *
 * @auhter Created by luckyAF on 16/6/2
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.util.AppSettings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/6/2
 */
public  class AppLockActivity extends Activity implements View.OnClickListener{
    private Button[][] buttons;
    private String[] buttonTexts = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "DEL", "OK"};
    private EditText mEditText;
    private String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_lock_app);
        initWidget();
        initData();
        initListener();

    }



    public void initWidget() {
        mEditText = (EditText) findViewById(R.id.et_password);
        buttons = new Button[4][3];
        buttons[0][0] = (Button) findViewById(R.id.btn_00);
        buttons[0][1] = (Button) findViewById(R.id.btn_01);
        buttons[0][2] = (Button) findViewById(R.id.btn_02);
        buttons[1][0] = (Button) findViewById(R.id.btn_10);
        buttons[1][1] = (Button) findViewById(R.id.btn_11);
        buttons[1][2] = (Button) findViewById(R.id.btn_12);
        buttons[2][0] = (Button) findViewById(R.id.btn_20);
        buttons[2][1] = (Button) findViewById(R.id.btn_21);
        buttons[2][2] = (Button) findViewById(R.id.btn_22);
        buttons[3][0] = (Button) findViewById(R.id.btn_30);
        buttons[3][1] = (Button) findViewById(R.id.btn_31);
        buttons[3][2] = (Button) findViewById(R.id.btn_32);
        List list = Arrays.asList(buttonTexts);
        if (AppSettings.getRandomBoard()) {
            Collections.shuffle(list);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText((String) list.get(i * 3 + j));
                buttons[i][j].setOnClickListener(this);
            }
        }

        Log.d("StrongBox","come in");
    }

    public void initData() {
        input = "";
    }

    public void initListener() {

    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) findViewById(v.getId());
        if (button.getText().equals("DEL")) {
            int size = input.length();
            if (size > 0) {
                input = input.substring(0, size - 1);
            }
        } else if (button.getText().equals("OK")) {
            try {
                int result = Integer.parseInt(input);
                int loginNumber = AppSettings.getLoginNumber();
                if (result == loginNumber) {
                    finish();
                } else {
                    input = "";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        } else {
            input += button.getText();
        }
        setInput(input);
    }

    public void setInput(String string) {
        mEditText.setText(string);
    }
}
