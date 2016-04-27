package com.luckyaf.strongbox.fragment.codeBook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.EditPassWordActivity;
import com.luckyaf.strongbox.adapter.CodeBookAdapter;
import com.luckyaf.strongbox.control.PasswordViewModel;
import com.luckyaf.strongbox.fragment.BaseFragment;
import com.luckyaf.strongbox.util.Constant;
import com.luckyaf.strongbox.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.Query;
import me.luckyaf.greendao.CodeBook;
import me.luckyaf.greendao.CodeBookDao;

/**
 * 类描述： 密码本
 * Created by luckyAF on 16/3/18.
 */
public class CodeBookFragment extends BaseFragment{
    private final String fragmentName = "CodeBookFragment(密码本)";
    private ArrayList<PasswordViewModel> passwordViewModelArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CodeBookAdapter mCodeBookAdapter;
    private Button _btnAdd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_code_book, container, false);

        initWidget(view);
        initData();
        initListener();
        return view;
    }

    public static CodeBookFragment newInstance(Bundle args) {
        CodeBookFragment codeBookFragment = new CodeBookFragment();
        codeBookFragment.setArguments(args);
        return codeBookFragment;
    }

    @Override
    public void initWidget(View view) {
        _btnAdd = (Button)view.findViewById(R.id.btn_add);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_code_book);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        mCodeBookAdapter = new CodeBookAdapter(passwordViewModelArrayList);
        mRecyclerView.setAdapter(mCodeBookAdapter);
    }

    @Override
    public void initData() {
        List passwords = MyApplication.daoMaster.newSession().getCodeBookDao().loadAll();
        passwordViewModelArrayList.clear();
        for(int i = 0; i < passwords.size(); i ++){
            PasswordViewModel model = new PasswordViewModel(getContext(),(CodeBook) passwords.get(i));
            passwordViewModelArrayList.add(model);
        }
        mCodeBookAdapter.notifyDataSetChanged();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            initData();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                Intent intent = new Intent(getActivity(), EditPassWordActivity.class);
                intent.putExtra(EditPassWordActivity.intent_is_update,false);
                startActivityForResult(intent, Constant.REQUEST_CODE_EDIT_PASSWORD);
                break;
            default:break;
        }
    }
}
