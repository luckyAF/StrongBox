package com.luckyaf.strongbox.fragment.file;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.AddHideFileActivity;
import com.luckyaf.strongbox.activity.EditContactActivity;
import com.luckyaf.strongbox.activity.EditHideFileActivity;
import com.luckyaf.strongbox.adapter.FileAdapter;
import com.luckyaf.strongbox.fragment.BaseFragment;
import com.luckyaf.strongbox.util.Constant;
import com.luckyaf.strongbox.util.FileUtils;

import me.luckyaf.greendao.MyDocument;

/**
 * 类描述：文档
 *
 * @author Created by luckyAF on 16/3/19
 */
public class DocumentFragment extends BaseFragment{

    private final String fragmentName = "DocumentFragment(加密文档)";
    private RecyclerView mRecyclerView;
    private FileAdapter mFileAdapter;
    private Button _btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_file_document, container, false);
        initWidget(view);
        initData();
        initListener();
        return view;

    }

    public static DocumentFragment newInstance(Bundle args) {
        DocumentFragment documentFragment = new DocumentFragment();
        documentFragment.setArguments(args);
        return documentFragment;
    }
    @Override
    public void initWidget(View view) {
        _btnAdd = (Button)view.findViewById(R.id.btn_add);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_document);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFileAdapter = new FileAdapter(getActivity(),null);
        mFileAdapter.setOnItemClickListener(new FileAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, MyDocument myDocument) {
                Intent intent = new Intent(getActivity(), EditHideFileActivity.class);
                intent.putExtra(EditHideFileActivity.intent_document_id,String.valueOf(myDocument.getId()));
                intent.putExtra(EditHideFileActivity.intent_document_new,myDocument.getNewFilename());
                intent.putExtra(EditHideFileActivity.intent_document_old,myDocument.getOldFileName());
                intent.putExtra(EditHideFileActivity.intent_document_time,myDocument.getEncodeTime());
                startActivityForResult(intent, Constant.REQUEST_CODE_EDIT_FILE);
            }
        });
        mRecyclerView.setAdapter(mFileAdapter);
    }

    @Override
    public void initData() {
        mFileAdapter.setPreViewFiles(FileUtils.getHideFiles());
        mFileAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        _btnAdd.setOnClickListener(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(resultCode == Activity.RESULT_OK){
        initData();
        //}
    }

    @Override
    public String getFragmentName() {
        return fragmentName;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_add:
                Intent intent = new Intent(getActivity(), AddHideFileActivity.class);
                startActivityForResult(intent,Constant.REQUEST_CODE_EDIT_FILE);
                break;
            default:
                break;
        }
    }
}
