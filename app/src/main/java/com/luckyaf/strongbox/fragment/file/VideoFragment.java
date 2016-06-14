package com.luckyaf.strongbox.fragment.file;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.bean.FileModel;
import com.luckyaf.strongbox.fragment.BaseFragment;
import com.luckyaf.strongbox.util.Constant;
import com.luckyaf.strongbox.util.FileUtils;
import com.luckyaf.strongbox.util.ToastUtils;

/**
 * 类描述：视频
 *
 * @author Created by luckyAF on 16/3/19
 */
public class VideoFragment extends BaseFragment{
    private final String fragmentName = "ImageFragment(加密视频)";
    private Button btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_file_video, container, false);
        initWidget(view);
        initData();
        initListener();
        return view;
    }

    public static VideoFragment newInstance(Bundle args) {
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(args);
        return videoFragment;
    }
    @Override
    public void initWidget(View view) {
        btnAdd = (Button)view.findViewById(R.id.btn_add);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constant.REQUEST_CODE_SELECT_VIDEO ){
                    FileModel fileModel = new FileModel();
                    fileModel.setFileType(FileModel.FILE_FILE);
                    fileModel.setContentType(Constant.CONTENT_TYPE_VIDEO);
                    fileModel.setPath(data.getData().getPath());
                    fileModel.setName(data.getData().getPath());
                    if(FileUtils.hideFile(fileModel)){
                        ToastUtils.showMessage(getActivity(),"加密成功");
                    }
            }
        }

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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_add:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*"); //String VIDEO_UNSPECIFIED = "video/*";
                Intent wrapperIntent = Intent.createChooser(intent, null);
                startActivityForResult(wrapperIntent, Constant.REQUEST_CODE_SELECT_VIDEO);
                break;
            default:
                break;
        }
    }
}
