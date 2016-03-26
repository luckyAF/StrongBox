package com.luckyaf.strongbox.activity;

import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.control.CalculatorViewModel;
import com.luckyaf.strongbox.databinding.ActivityCalculatorBinding;
import com.luckyaf.strongbox.util.CalculatorDisplay;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCalculatorBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_calculator);
        CalculatorViewModel calculatorViewModel = new CalculatorViewModel();
        binding.setCalculator(calculatorViewModel);

    }
}
