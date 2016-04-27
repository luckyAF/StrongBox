package com.luckyaf.strongbox.fragment.program;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.AddLockProgramActivity;
import com.luckyaf.strongbox.activity.EditDiaryActivity;
import com.luckyaf.strongbox.fragment.BaseFragment;
import com.luckyaf.strongbox.util.Constant;

/**
 * 类描述：程序
 *
 * @auhter. luckyAF
 * 16/3/18
 */
public class ProgramFragment extends BaseFragment{
    private final String fragmentName = "ProgramFragment(加密程序)";
    private Button _btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_program, container, false);
        initWidget(view);
        initData();
        initListener();
        return view;
    }

    public static ProgramFragment newInstance(Bundle args) {
        ProgramFragment programFragment = new ProgramFragment();
        programFragment.setArguments(args);
        return programFragment;
    }

    @Override
    public void initWidget(View view) {
        _btnAdd = (Button)view.findViewById(R.id.btn_add);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        _btnAdd.setOnClickListener(this);
    }

    @Override
    public String getFragmentName() {
        return fragmentName;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                Intent intent = new Intent(getContext(), AddLockProgramActivity.class);
                startActivityForResult(intent, Constant.REQUEST_CODE_EDIT_DIARY);
                break;
            default:break;
        }
    }
}
