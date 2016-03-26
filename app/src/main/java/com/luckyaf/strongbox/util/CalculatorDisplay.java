package com.luckyaf.strongbox.util;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luckyaf.strongbox.R;

/**
 * 类描述：
 *
 * @auhter Created by luckyAF on 16/3/22
 */
public class CalculatorDisplay implements View.OnClickListener{
    private TextView _tvAboveText;//上方文本
    private TextView _tvUnderText;//下方文本
    private Button _btnDigit0;//数字0
    private Button _btnDigit1;//数字1
    private Button _btnDigit2;//数字2
    private Button _btnDigit3;//数字3
    private Button _btnDigit4;//数字4
    private Button _btnDigit5;//数字5
    private Button _btnDigit6;//数字6
    private Button _btnDigit7;//数字7
    private Button _btnDigit8;//数字8
    private Button _btnDigit9;//数字9
    private Button _btnDot;   //小数点
    private Button _btnEqual; //等号
    private Button _btnDelete;  //删除
    private Button _btnClear; //清除
    private Button _btnLeftParen; //左括号
    private Button _btnRightParen;//右括号
    private Button _btnDiv;       //   除
    private Button _btnMul;       //   乘
    private Button _btnMinus;     //   剪
    private Button _btnPlus;      //   加
    private Button _btnFactorial; //   阶乘
    private Button _btnSin;       //   sin
    private Button _btnCos;       //   cos
    private Button _btnTan;       //   tan
    private Button _btnLn;        //   ln
    private Button _btnLog;       //   log
    private Button _btnPower;     //   次方
    private Button _btnSqrt;      //  开方
    private Button _btnPi;        //  圆周率
    private Button _btnE;         //  e   自然常数
    private ImageButton _imgBtnChange;   // 更换显示

    private LinearLayout _layMiss;

    public CalculatorDisplay(View view){
        initWidget(view);
        initListener();

    }

    public void initWidget(View view){
       // _tvAboveText = (TextView) view.findViewById(R.id.tv_above);
       // _tvUnderText = (TextView) view.findViewById(R.id.tv_under);
        _layMiss = (LinearLayout)view.findViewById(R.id.lay_miss);
        _imgBtnChange = (ImageButton) view.findViewById(R.id.btn_change);
        _btnDigit0 = (Button)view.findViewById(R.id.btn_digit0);
        _btnDigit1 = (Button)view.findViewById(R.id.btn_digit1);
        _btnDigit2 = (Button)view.findViewById(R.id.btn_digit2);
        _btnDigit3 = (Button)view.findViewById(R.id.btn_digit3);
        _btnDigit4 = (Button)view.findViewById(R.id.btn_digit4);
        _btnDigit5 = (Button)view.findViewById(R.id.btn_digit5);
        _btnDigit6 = (Button)view.findViewById(R.id.btn_digit6);
        _btnDigit7 = (Button)view.findViewById(R.id.btn_digit7);
        _btnDigit8 = (Button)view.findViewById(R.id.btn_digit8);
        _btnDigit9 = (Button)view.findViewById(R.id.btn_digit9);
        _btnDot = (Button)view.findViewById(R.id.btn_dot);
        _btnEqual = (Button)view.findViewById(R.id.btn_equal); //等号
        _btnDelete = (Button)view.findViewById(R.id.btn_delete);  //删除
        _btnClear = (Button)view.findViewById(R.id.btn_clear); //清除
        _btnLeftParen = (Button)view.findViewById(R.id.btn_left_paren); //左括号
        _btnRightParen = (Button)view.findViewById(R.id.btn_right_paren);//右括号
        _btnDiv = (Button)view.findViewById(R.id.btn_div);       //   除
        _btnMul = (Button)view.findViewById(R.id.btn_mul);       //   乘
        _btnMinus = (Button)view.findViewById(R.id.btn_minus);     //   剪
        _btnPlus = (Button)view.findViewById(R.id.btn_plus);      //   加
        _btnFactorial = (Button)view.findViewById(R.id.btn_factorial); //   阶乘
        _btnSin = (Button)view.findViewById(R.id.btn_sin);       //   sin
        _btnCos = (Button)view.findViewById(R.id.btn_cos);       //   cos
        _btnTan = (Button)view.findViewById(R.id.btn_tan);       //   tan
        _btnLn = (Button)view.findViewById(R.id.btn_ln);        //   ln
        _btnLog = (Button)view.findViewById(R.id.btn_log);       //   log
        _btnPower = (Button)view.findViewById(R.id.btn_power);     //   次方
        _btnSqrt = (Button)view.findViewById(R.id.btn_sqrt);      //  开方
        _btnPi = (Button)view.findViewById(R.id.btn_pi);        //  圆周率
        _btnE = (Button)view.findViewById(R.id.btn_e);         //  e   自然常数
    }

    public void initListener(){

    }

    public void showDetail(){

    }

    public void showSimple(){

    }



    @Override
    public void onClick(View v) {

    }
}
