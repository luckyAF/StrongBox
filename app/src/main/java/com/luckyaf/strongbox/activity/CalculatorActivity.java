package com.luckyaf.strongbox.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.control.CalculatorViewModel;
import com.luckyaf.strongbox.databinding.ActivityCalculatorBinding;

public class CalculatorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCalculatorBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_calculator);
        CalculatorViewModel calculatorViewModel = new CalculatorViewModel(this,getResources());
        binding.setCalculator(calculatorViewModel);

    }


    @Override
    protected void initToolbar() {

    }

    @Override
    protected boolean isApplyTranslucency() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
