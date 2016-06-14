package com.luckyaf.strongbox.activity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import android.content.ContentResolver;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.base.BaseActivity;
import com.luckyaf.strongbox.adapter.FolderAdapter;
import com.luckyaf.strongbox.bean.FileModel;
import com.luckyaf.strongbox.util.AppSettings;
import com.luckyaf.strongbox.util.FileUtils;
import com.luckyaf.strongbox.util.ToastUtils;

import java.io.File;
import java.util.List;

/**
 * 类描述：新增隐藏文件
 *
 * @author Created by luckyAF on 16/6/5
 */
public class AddHideFileActivity extends BaseActivity {
    private Toolbar mToolbar;
    /**
     * SD卡根路径
     */
    private static String SD_URL = FileUtils.getSDPath();
    private RecyclerView mRecyclerView;
    private FolderAdapter mFolderAdapter;
    private TextView tvFolderName;
    /**
     * 当前所在的文件夹信息（没人默认为空）
     */
    private File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hide_file);
        initWidget();
        initData();
        initListener();
    }

    public void initWidget(){
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView_file);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mToolbar = (Toolbar)findViewById(R.id.toolbar_edit);
        tvFolderName = (TextView)findViewById(R.id.tv_folder_name);
        initToolBar(mToolbar);
        mToolbar.setTitle(getString(R.string.file_add_encrypt_file));
    }

    public void initData(){
        mFolderAdapter = new FolderAdapter(this,null);
        mRecyclerView.setAdapter(mFolderAdapter);
        //mRecyclerView.set
        tvFolderName.setText(SD_URL);
        openFolder();


    }

    public void initListener(){
        mFolderAdapter.setOnItemClickListener(new FolderAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, FileModel fileModel) {
                if(fileModel.getFileType() == FileModel.FILE_DIR){
                    openFolder(fileModel);
                    tvFolderName.setText(fileModel.getPath());
                    Log.v("OnItemClick",fileModel.getName());
                }

            }
        });
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected boolean isApplyTranslucency() {
        return false;
    }

    public void openFolder(FileModel fileModel) {
        if (fileModel != null)
            openFolder(fileModel.getPath());
        else
            openFolder(AddHideFileActivity.SD_URL);
    }

    public void openFolder() {
        openFolder(AddHideFileActivity.SD_URL);
    }

    /**
     * 打开某个文件夹
     *
     * @param url
     */
    public void openFolder(String url) {
        openFolder(new File(url));
    }

    /**
     * 返回/退出页面
     *
     * @return 是否停留
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mFile != null && !mFile.getPath().equals(AddHideFileActivity.SD_URL)) {
                mToolbar.setTitle(mFile.getParent());
                openFolder(mFile.getParent());
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_file, menu);
        return true;
    }


    public void hideFile(){
        final List<FileModel> list = mFolderAdapter.getSelectedFile();
        if(list == null || list.size() == 0){
            ToastUtils.showMessage(getBaseContext(),"请选择文件");
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.common_tip));
            builder.setMessage(getString(R.string.file_confirm_encrypt_file));//
            builder.setPositiveButton(getString(R.string.common_confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for(FileModel fileModel:list){
                        FileUtils.hideFile(fileModel);
                    }
                    dialog.dismiss();
                    ToastUtils.showMessage(getBaseContext(),"加密成功");
                    finish();
                }
            });
            builder.setNegativeButton(getString(R.string.common_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.done:
                hideFile();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openFolder(File file) {
        mFile = file;
        List<FileModel> list = FileUtils.getFilesByDir(file.getPath());


       // List<FileModel> list = FileUtils.getFilesByDir("/storage/emulated/0/");

        //if (list != null && list.size() > 0) {
            List<FileModel> list2 = FileUtils.transList(list);
        //    if (list2.size() > 0)


        mFolderAdapter.setPreViewFiles(list2);

        //}
       // Log.v("list size","list = " + list.size());
        Log.v("list size","list2 = " + list2.size());
        Log.v("path", AppSettings.getMyDir());
    }

    @Override
    public void onClick(View v) {

    }
}
