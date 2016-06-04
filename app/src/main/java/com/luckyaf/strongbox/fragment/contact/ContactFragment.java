package com.luckyaf.strongbox.fragment.contact;

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
import com.luckyaf.strongbox.activity.EditContactActivity;
import com.luckyaf.strongbox.activity.EditPassWordActivity;
import com.luckyaf.strongbox.adapter.ContactAdapter;
import com.luckyaf.strongbox.adapter.DiaryAdapter;
import com.luckyaf.strongbox.control.ContactViewModel;
import com.luckyaf.strongbox.control.DiaryViewModel;
import com.luckyaf.strongbox.fragment.BaseFragment;
import com.luckyaf.strongbox.util.Constant;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.Query;
import me.luckyaf.greendao.Diary;
import me.luckyaf.greendao.DiaryDao;
import me.luckyaf.greendao.MyContact;
import me.luckyaf.greendao.MyContactDao;

/**
 * 类描述：通讯
 *
 * @author luckyAF
 * 16/3/18
 */
public class ContactFragment extends BaseFragment{
    private final String fragmentName = "ContactFragment(通讯锁)";
    private ArrayList<ContactViewModel> contactViewModelArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ContactAdapter mContactAdapter;
    private Button btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        initWidget(view);
        initData();
        initListener();
        return view;
    }

    public static ContactFragment newInstance(Bundle args) {
        ContactFragment contactFragment = new ContactFragment();
        contactFragment.setArguments(args);
        return contactFragment;
    }

    @Override
    public void initWidget(View view) {
        btnAdd = (Button)view.findViewById(R.id.btn_add);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_contact);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mContactAdapter = new ContactAdapter(contactViewModelArrayList);
        mRecyclerView.setAdapter(mContactAdapter);
    }

    @Override
    public void initData() {
        List contacts = MyApplication.daoMaster.newSession().getMyContactDao().loadAll();
        contactViewModelArrayList.clear();
        for(int i = 0; i < contacts.size(); i ++){
            ContactViewModel model = new ContactViewModel(getContext(),(MyContact) contacts.get(i));
            contactViewModelArrayList.add(model);
        }
        mContactAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        btnAdd.setOnClickListener(this);
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
                Intent intent = new Intent(getActivity(), EditContactActivity.class);
                intent.putExtra(EditContactActivity.intent_is_update,false);
                startActivityForResult(intent, Constant.REQUEST_CODE_EDIT_CONTACT);
                break;
            default:break;
        }

    }
}
