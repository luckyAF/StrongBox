package com.luckyaf.strongbox.control;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.luckyaf.strongbox.BR;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.CalculatorActivity;
import com.luckyaf.strongbox.activity.MainActivity;
import com.luckyaf.strongbox.util.VibrateUtils;

import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;
import org.javia.arity.Util;

/**
 * 类描述：控制计算器显示
 *
 * @auhter Created by luckyAF on 16/3/22
 */
public class CalculatorViewModel extends BaseObservable {
    private Symbols mSymbols = new Symbols();
    private String input;
    private String result;
    private Boolean isAll;
    private Boolean isError;

    private int mLineLength = 10;

    private static final String INFINITY_UNICODE = "\u221e";

    private static final String INFINITY = "Infinity";
    private static final String NAN      = "NaN";
    private static final char MINUS = '\u2212';
    private static String mErrorString = "Error";
    private static final int ROUND_DIGITS = 1;

    private Context mContext;
    private Resources mResources;


    public CalculatorViewModel(Context context , Resources resources){
        mContext = context;
        mResources = resources;
        setAll(false);
        setError(false);
        setInput("");
        setResult("");
    }



    @Bindable
    public String getInput() {
        return input;
    }

    @Bindable
    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
        notifyPropertyChanged(BR.error);
    }

    @Bindable
    public Boolean getAll() {
        return isAll;
    }

    public void setAll(Boolean all) {
        isAll = all;
        notifyPropertyChanged(BR.all);
    }

    @Bindable
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
        notifyPropertyChanged(BR.result);

    }

    public void setInput(String input) {
        this.input = input;
        notifyPropertyChanged(BR.input);

    }





    public void change(String text){
        if(!isMinus(text) && !isPlus(input.charAt(input.length()-1))){
            input = input.substring(0, input.length() - 1);
        }

        input += text;
        setInput(input);
    }
    public void insert(String text){
        if(text.length() >= 2){
            text += "(";
        }
        input += text;
        setInput(input);
    }

    public void onChangeClick(View view){
        VibrateUtils.vShort(mContext);
        setAll(!isAll);
    }

    public void onDigitClick(View view){
        VibrateUtils.vShort(mContext);
        if (view instanceof Button) {
            String text = ((Button) view).getText().toString();
            insert(text);
        }
        realTimeCalculator();

    }
    public void onMathClick(View view){
        VibrateUtils.vShort(mContext);
        if (view instanceof Button) {
            String text = ((Button) view).getText().toString();
            insert(text);
        }
        realTimeCalculator();
    }

    public void onOperateClick(View view){
        VibrateUtils.vShort(mContext);
        if (view instanceof Button) {
            String text = ((Button) view).getText().toString();
            if(input.length() >= 1 && isOperator(input.charAt(input.length()-1))){
                change(text);
            }else{
                insert(text);
            }

        }

    }

    public void onEqualsClick(View view){
        VibrateUtils.vShort(mContext);
        calculator();
        if(input.equals("123456")){
            Intent intent = new Intent(mContext, MainActivity.class);
            mContext.startActivity(intent);
            ((CalculatorActivity)mContext).finish();
        }
    }

    public void onDeleteClick(View view){
        VibrateUtils.vShort(mContext);
        int size = input.length();
        if (size > 0 ) {
            input = input.substring(0, size - 1);
            setInput(input);
        }
        realTimeCalculator();
    }

    public void onClearClick(View view){
        VibrateUtils.vShort(mContext);
        setInput("");
        setResult("");
        setError(false);
    }

    /**
    * 实时计算
    * */
    public void realTimeCalculator(){
        String text = input;
        String history = result;
        try {
            result = evaluate(text);
        }catch (SyntaxException e){
            result = history;
        }
        setResult(result);
    }

    public void calculator(){
        String text = input;
        if (text.equals(result)) {
            //clearWithHistory(false); //clear after an Enter on result
        } else {
           // mHistory.enter(text);
            try {
                result = evaluate(text);
            } catch (SyntaxException e) {
                isError = true;

                result = mErrorString;
            }
            if (text.equals(result)) {
                //no need to show result, it is exactly what the user entered
               // clearWithHistory(true);
            } else {
                //setText(mResult);
                //mEqualButton.setText(mEnterString);
            }
            if(isError){
                setError(isError);
                setResult(result);
            }else{
                input = result;
                result = "";
                setInput(input);
                setResult(result);
            }

        }

    }

    public String evaluate(String input) throws SyntaxException{
        if (input.trim().equals("")) {
            return "";
        }
        int size = input.length();
        while (size > 0 && isOperator(input.charAt(size - 1))) {
            input = input.substring(0, size - 1);
            --size;
        }

        String result = Util.doubleToString(mSymbols.eval(input), mLineLength, ROUND_DIGITS);
        if (result.equals(NAN)) { // treat NaN as Error
            isError = true;
            return mErrorString;
        }
        return result.replace('-', MINUS).replace(INFINITY, INFINITY_UNICODE);
    }

    static boolean isOperator(String text) {
        return text.length() == 1 && isOperator(text.charAt(0));
    }

    static boolean isOperator(char c) {
        //plus minus times div
        return "+\u2212\u00d7\u00f7/*".indexOf(c) != -1;
    }

    static boolean isMinus(String text){
        return text.length() == 1 && isMinus(text.charAt(0));
    }

    static boolean isMinus(char c){
        return "\u2212".indexOf(c) != -1;
    }

    static boolean isPlus(String text){
        return text.length() == 1 && isOperator(text.charAt(0));
    }

    static boolean isPlus(char c){
        return "+".indexOf(c) != -1;
    }


}
