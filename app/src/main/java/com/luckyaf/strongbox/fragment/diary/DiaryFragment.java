package com.luckyaf.strongbox.fragment.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.EditDiaryActivity;
import com.luckyaf.strongbox.activity.EditPassWordActivity;
import com.luckyaf.strongbox.adapter.CodeBookAdapter;
import com.luckyaf.strongbox.adapter.DiaryAdapter;
import com.luckyaf.strongbox.control.DiaryViewModel;
import com.luckyaf.strongbox.control.PasswordViewModel;
import com.luckyaf.strongbox.fragment.BaseFragment;
import com.luckyaf.strongbox.util.Constant;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.Query;
import me.luckyaf.greendao.CodeBook;
import me.luckyaf.greendao.CodeBookDao;
import me.luckyaf.greendao.Diary;
import me.luckyaf.greendao.DiaryDao;

/**
 * 类描述：日记
 *
 * @auhter. luckyAF
 * 16/3/18
 */
public class DiaryFragment extends BaseFragment {

    private final String fragmentName = "DiaryFragment(日记本)";
    private ArrayList<DiaryViewModel> diaryViewModelArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private DiaryAdapter mDiaryAdapter;
    private Button _btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        initWidget(view);
        initData();
        initListener();
        return view;
    }

    public static DiaryFragment newInstance(Bundle args) {
        DiaryFragment diaryFragment = new DiaryFragment();
        diaryFragment.setArguments(args);
        return diaryFragment;
    }

    @Override
    public void initWidget(View view) {
        _btnAdd = (Button)view.findViewById(R.id.btn_add);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_diary);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mDiaryAdapter = new DiaryAdapter(diaryViewModelArrayList);
        mRecyclerView.setAdapter(mDiaryAdapter);
    }

    @Override
    public void initData() {
        List diaries = MyApplication.daoMaster.newSession().getDiaryDao().loadAll();
        diaryViewModelArrayList.clear();
        for(int i = 0; i < diaries.size(); i ++){
            DiaryViewModel model = new DiaryViewModel(getContext(),(Diary) diaries.get(i));
            diaryViewModelArrayList.add(model);
        }
        mDiaryAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(getContext(), EditDiaryActivity.class);
                intent.putExtra(EditDiaryActivity.intent_is_update,false);
                startActivityForResult(intent, Constant.REQUEST_CODE_EDIT_DIARY);
                break;
            default:break;
        }
    }
}
