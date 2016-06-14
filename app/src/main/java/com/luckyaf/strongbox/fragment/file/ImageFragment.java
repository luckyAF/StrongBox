package com.luckyaf.strongbox.fragment.file;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.luckyaf.strongbox.util.image.ImageInformation;
import com.luckyaf.strongbox.util.image.SelectImageActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：图片
 *
 * @author Created by luckyAF on 16/3/19
 */
public class ImageFragment extends BaseFragment{

    private final String fragmentName = "ImageFragment(加密图片)";
    private Button btnAdd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_file_image, container, false);
        initWidget(view);
        initData();
        initListener();
        return view;
    }

    public static ImageFragment newInstance(Bundle args) {
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(args);
        return imageFragment;
    }

    @Override
    public void initWidget(View view) {
        btnAdd = (Button)view.findViewById(R.id.btn_add);
    }

    @Override
    public void initData() {

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
        if(requestCode == SelectImageActivity.REQUEST_PHOTO_LIST){
            if(resultCode == Activity.RESULT_OK){
                ArrayList<ImageInformation> list =(ArrayList<ImageInformation>) data.getSerializableExtra(SelectImageActivity.EXTRA_RESULT_PHOTO_LIST);
                Log.d("sizee","hhhh" + list.size());
                ImageInformation imageInformation = list.get(0);
                File file = new File(imageInformation.path);
                if(file.exists()){
                    Log.d("is exists","yes");
                }
            }
        }
        if(requestCode == Constant.REQUEST_CODE_SELECT_IMAGE){
            if(resultCode == Activity.RESULT_OK){
                FileModel fileModel = new FileModel();
                fileModel.setFileType(FileModel.FILE_FILE);
                fileModel.setContentType(Constant.CONTENT_TYPE_IMAGE);
                fileModel.setPath(data.getData().getPath());
                fileModel.setName(data.getData().getPath());
                if(FileUtils.hideFile(fileModel)){
                    ToastUtils.showMessage(getActivity(),"加密成功");
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
/*                Intent intent = new Intent(getActivity(), SelectImageActivity.class);
                intent.putExtra(SelectImageActivity.EXTRA_MODE, SelectImageActivity.MODE_MULTI_CROP);
                intent.putExtra(SelectImageActivity.EXTRA_MAX, 6);
                ArrayList<ImageInformation> imageInformations = new ArrayList<>();
                intent.putExtra(SelectImageActivity.EXTRA_PICKED, imageInformations);
                startActivityForResult(intent, SelectImageActivity.REQUEST_PHOTO_LIST);*/
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); //String VIDEO_UNSPECIFIED = "video/*";
                Intent wrapperIntent = Intent.createChooser(intent, null);
                startActivityForResult(wrapperIntent, Constant.REQUEST_CODE_SELECT_IMAGE);
                break;
            default:
                break;
        }
    }
}
