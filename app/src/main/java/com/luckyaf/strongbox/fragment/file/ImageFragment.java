package com.luckyaf.strongbox.fragment.file;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.BaseFragment;
import com.luckyaf.strongbox.util.image.ImageInformation;
import com.luckyaf.strongbox.util.image.SelectImageActivity;

import java.util.ArrayList;

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                Intent intent = new Intent(getActivity(), SelectImageActivity.class);
                intent.putExtra(SelectImageActivity.EXTRA_MODE, SelectImageActivity.MODE_MULTI_CROP);
                intent.putExtra(SelectImageActivity.EXTRA_MAX, 6);
                ArrayList<ImageInformation> imageInformations = new ArrayList<>();
                intent.putExtra(SelectImageActivity.EXTRA_PICKED, imageInformations);
                startActivityForResult(intent, SelectImageActivity.REQUEST_PHOTO_LIST);
                break;
            default:
                break;
        }
    }
}
