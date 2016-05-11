package com.luckyaf.strongbox.util.image;

import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.base.BaseActivity;
import com.luckyaf.strongbox.util.AnimationUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 图片选择控件,有两种模式
 * <p>1.MODE_SINGLE_CROP,选择一张图片并且裁剪返回,用于头像选择</p>
 * <h1>intent.putExtra(SelectImageActivity.EXTRA_MODE, SelectImageActivity.MODE_SINGLE_CROP);</h1>
 * <h1>startActivityForResult(intent, SelectImageActivity.REQUEST_PHOTO_CROP);</h1>
 *
 * <p>2.MODE_MULTI_CROP,选择多张图片,并且返回一个list集合 用于批量图片加密处理.</p>
 * <h1>intent.putExtra(SelectImageActivity.EXTRA_MODE, SelectImageActivity.MODE_MULTI_CROP);</h1>
 * <h1>intent.putExtra(SelectImageActivity.EXTRA_MAX, 6);//最大选择数</h1>
 * <h1>intent.putExtra(SelectImageActivity.EXTRA_PICKED, ImageInformations);当前已经选择的图片list</h1>
 *
 * <p>在onActivityResult中获取数据</p>
 *
 * <p>if(requestCode == SelectImageActivity.REQUEST_PHOTO_CROP && resultCode == Activity.RESULT_OK)</p>
 * <p>bundle.getString(SelectImageActivity.EXTRA_RESULT_CROP_PHOTO);//裁剪之后的图片路径</p>
 *
 * <p>if(requestCode == SelectImageActivity.REQUEST_PHOTO_LIST && resultCode == Activity.RESULT_OK)</p>
 * <p>ArrayList<ImageInformation> ImageInformations = (ArrayList<ImageInformation>) data.getSerializableExtra(SelectImageActivity.EXTRA_RESULT_PHOTO_LIST);//多个图片的路径集合</p>
 *
 *
 * @auhter Created by luckyAF on 16/4/23
 */
public class SelectImageActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * 最大选择数
     */
    public static final String EXTRA_MAX = "EXTRA_MAX";//
    /**
     * 当前已经选择个数
     */
    public static final String EXTRA_PICKED = "EXTRA_PICKED"; //
    /**
     * 选择图片模式
     */
    public static final String EXTRA_MODE = "EXTRA_MODE";//
    /**
     * 图片裁剪模式
     */
    public static final int MODE_SINGLE_CROP = 11;
    /**
     * 多图片选择模式
     */
    public static final int MODE_MULTI_CROP = 12;//
    /**
     * onActivityResult返回数据参数 （裁剪的图片）
     */
    public static final String EXTRA_RESULT_CROP_PHOTO = "cropPhoto"; //
    /**
     * onActivityResult返回数据参数（选择的图片list）
     */
    public static final String EXTRA_RESULT_PHOTO_LIST = "photoList"; //

    /**
     *resultCode图片裁剪
     */
    public static final int REQUEST_PHOTO_CROP = 13;
    /**
     * resultCode图片list
     */
    public static final int REQUEST_PHOTO_LIST = 14;
    private static final int REQUEST_STORAGE_PERMISSION = 1;

    private int mPhotoMode;//当前图片模式
    private static final int REQUEST_RESULT_PHOTO_CROP = 666; //
    private static final int REQUEST_RESULT_PHOTO = 665; //
    private static final int REQUEST_PHOTO_DETAIL = 664; //


    private static final int GRID_NUMCOLUMNS = 3;//列数
    private final String allPhotos = "所有图片";
    private MenuItem mMenuItem;
    private int mMaxPick;
    private TextView mFoldName;
    private View mListViewGroup;
    private ListView mRecycleTitle;
    private ImageFolderAdapter mFolderAdapter;
    private ImageGridAdapter photoAdapter;
    private GridView mRecycleItem;
    private int mFolderId;
    private Uri fileCropUri;//裁剪的图片路径
    private Uri fileUri;//照相机照片路径
    private ArrayList<ImageInformation> mPickData = new ArrayList<>();


    //点击图片目录,显示.隐藏
    View.OnClickListener mOnClickFoldName = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListViewGroup.getVisibility() == View.VISIBLE) {
                hideFolderList();
            } else {
                showFolderList();
            }
        }
    };




    private void showFolderList() {
        Animation translateAnimation = AnimationUtils.translateAnimation(0, 0, 1.0f, 0f, 150, false, null);
        Animation alphaAnimation = AnimationUtils.alphaAnimation(0f, 1f, 150, false, null);
        mListViewGroup.setAnimation(translateAnimation);
        mRecycleTitle.setAnimation(alphaAnimation);
        mListViewGroup.setVisibility(View.VISIBLE);
    }

    private void hideFolderList() {
        Animation translateAnimation = AnimationUtils.translateAnimation(0, 0, 0.0f, 1.0f, 150, false, null);
        Animation alphaAnimation = AnimationUtils.alphaAnimation(1f, 0f, 150, false, null);
        mListViewGroup.setAnimation(translateAnimation);
        mRecycleTitle.setAnimation(alphaAnimation);
        mListViewGroup.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_image);
        initActionbar();
        init();
        initDirectory();
//

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected boolean isApplyTranslucency() {
        return false;
    }

    private void init() {
        mPhotoMode = getIntent().getIntExtra(EXTRA_MODE,MODE_SINGLE_CROP);
        mMaxPick = getIntent().getIntExtra(EXTRA_MAX, 1);
        Object extraPicked = getIntent().getSerializableExtra(EXTRA_PICKED);
        if (extraPicked != null) {
            mPickData = (ArrayList<ImageInformation>) extraPicked;
        }

        mRecycleTitle = (ListView) findViewById(R.id.rv_photo_title_list);
        mRecycleTitle.setOnItemClickListener(mOnItemClick);
//        mRecycleTitle.setLayoutManager(new WrapContentLinearLayoutManager(this));


        mRecycleItem = (GridView) findViewById(R.id.rv_photo_list);
//        mRecycleItem.setLayoutManager(new GridLayoutManager(this,GRID_NUMCOLUMNS));

        mListViewGroup = findViewById(R.id.listViewParent);
        mListViewGroup.setOnClickListener(mOnClickFoldName);

        mFoldName = (TextView) findViewById(R.id.foldName);
        mFoldName.setText(allPhotos);

        findViewById(R.id.selectFold).setOnClickListener(mOnClickFoldName);


    }



    //初始化所有图片文件目录
    private void initDirectory() {

        int checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(checkSelfPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){//是否设置显示dialog
                new AlertDialog.Builder(SelectImageActivity.this)
                        .setMessage("你需要启动权限WRITE_EXTERNAL_STORAGE")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(SelectImageActivity.this,"你需要启动权限WRITE_EXTERNAL_STORAGE",Toast.LENGTH_SHORT).show();
                                ActivityCompat.requestPermissions(SelectImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create()
                        .show();
            }
        }else{
            getCameraData();
        }
    }

    public void getCameraData(){

        final String[] needInfos = {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
        };
        //存储每个类别及他里面图片的数量
        LinkedHashMap<String, Integer> mNames = new LinkedHashMap<>();
        //存储每个类别的第一张图片信息,在目录显示需要
        LinkedHashMap<String, ImageInformation> mData = new LinkedHashMap<>();
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, needInfos, "", null, MediaStore.MediaColumns.DATE_ADDED + " DESC");

        while (cursor.moveToNext()) {
            String name = cursor.getString(2);
            if (!mNames.containsKey(name)) {
                mNames.put(name, 1);//每个类别的数目
                ImageInformation ImageInformation = new ImageInformation(cursor.getString(1));
                mData.put(name, ImageInformation);//每个类别的第一张照片
            } else {
                int newCount = mNames.get(name) + 1;
                mNames.put(name, newCount);
            }
        }

        ArrayList<ImageFolder> mFolderData = new ArrayList<>();//所有图片
        if (cursor.moveToFirst()) {//保存第一个项（所有图片的目录项）
            ImageInformation ImageInformation = new ImageInformation(cursor.getString(1));
            int allImagesCount = cursor.getCount();
            mFolderData.add(new ImageFolder(allPhotos, ImageInformation, allImagesCount));
        }

        for (String item : mNames.keySet()) {//保存每个项目图片
            ImageInformation info = mData.get(item);
            Integer count = mNames.get(item);
            mFolderData.add(new ImageFolder(item, info, count));
        }
        cursor.close();

        //显示目录列表信息
        mFolderAdapter = new ImageFolderAdapter(mFolderData,this);
        mRecycleTitle.setAdapter(mFolderAdapter);

        getLoaderManager().initLoader(mFolderId, null, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode){
            case REQUEST_STORAGE_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getCameraData();
                }else{
                    Toast.makeText(SelectImageActivity.this,"获取权限失败",Toast.LENGTH_SHORT).show();
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private ListView.OnItemClickListener mOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mFolderAdapter.setSelect((int) id);
            String folderName = mFolderAdapter.getSelect();
            mFoldName.setText(folderName);
            hideFolderList();

            if (mFolderId != position) {
                getLoaderManager().destroyLoader(mFolderId);
                mFolderId = position;
            }
            getLoaderManager().initLoader(mFolderId, null, SelectImageActivity.this);
        }
    };

   

    private void initActionbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("选择图片");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void updatePickCount() {

        if(mPhotoMode == MODE_MULTI_CROP){
            String format = "完成(%d/%d)";
            mMenuItem.setTitle(String.format(format, mPickData.size(), mMaxPick));
        }

    }

    //判断在已选择的图片list中有没有该图片路径
    public boolean isPicked(String path) {
        for (ImageInformation item : mPickData) {
            if (item.path.equals(path)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mPhotoMode == MODE_MULTI_CROP){
            getMenuInflater().inflate(R.menu.menu_select_image, menu);
            mMenuItem = menu.getItem(0);
            updatePickCount();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_finish){//图片选择确定
            Intent intent = new Intent();
            intent.putExtra(EXTRA_RESULT_PHOTO_LIST,mPickData);
            setResult(Activity.RESULT_OK, intent);
            finish();
            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
     * 选择了listview的第一个项，gridview的第一个是照相机
     */
    private boolean isAllPhotoMode() {
        return mFolderId == 0;
    }

    private String[] projection = {
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.WIDTH,
            MediaStore.Images.ImageColumns.HEIGHT
    };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String where ;
        //选的不是第一个项目（指全部图片）
        if(!isAllPhotoMode()){
            String select = ((ImageFolderAdapter) mRecycleTitle.getAdapter()).getSelect();
            where = String.format("%s='%s'",
                    MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                    select);
        }else{
            where = "";
        }

        return new CursorLoader(SelectImageActivity.this,MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                where,
                null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(isAllPhotoMode()){
            photoAdapter = new ImageAllAdapter(this, data, false, this);
        }else {
            photoAdapter = new ImageGridAdapter(this, data, false, this);
        }
        mRecycleItem.setAdapter(photoAdapter);
//        photoAdapter.setOnItemClick(mOnPhotoItemClick);
    }

    public void clickPhotoItem(String path) {

        if(mPhotoMode == MODE_SINGLE_CROP){
            Uri uri = Uri.parse(path);
            fileCropUri = CameraImageUtil.getOutputMediaFileUri();
            cropImageUri(uri, fileCropUri,640,640,REQUEST_RESULT_PHOTO_CROP);
        }
    }

    public void clickPhotoCheck(View v) {
        GridViewCheckTag tag = (GridViewCheckTag) v.getTag();
        if (((CheckBox) v).isChecked()) {
            if (mPickData.size() >= mMaxPick) {
                ((CheckBox) v).setChecked(false);
                String s = String.format("最多只能选择%d张", mMaxPick);
                Toast.makeText(this, s, Toast.LENGTH_LONG).show();
                return;
            }

            addPicked(tag.path);
            tag.iconFore.setVisibility(View.VISIBLE);
        } else {
            removePicked(tag.path);
            tag.iconFore.setVisibility(View.INVISIBLE);
        }
        ((BaseAdapter) mRecycleItem.getAdapter()).notifyDataSetChanged();

        updatePickCount();
    }

    private void removePicked(String path) {
        for (int i = 0; i < mPickData.size(); ++i) {
            if (mPickData.get(i).path.equals(path)) {
                mPickData.remove(i);
                return;
            }
        }
    }

    private void addPicked(String path) {
        if (!isPicked(path)) {
            mPickData.add(new ImageInformation(path));
        }
    }

    public void camera() {
        if(mPickData.size() >= mMaxPick){
            Toast.makeText(SelectImageActivity.this,String.format("最多只能选择%s张",mMaxPick),Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = CameraImageUtil.getOutputMediaFileUri();
        String filePath = CameraImageUtil.getPath(SelectImageActivity.this, fileUri);
        File file = new File(filePath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQUEST_RESULT_PHOTO);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public int getPhotoMode(){
        return mPhotoMode;
    }

    /**
     * 启动图片裁剪工具
     * @param uri 原图片Uri
     * @param outputUri 裁剪后图片uri
     * @param outputX 水平长度
     * @param outputY 垂直长度
     * @param requestCode 请求码
     */
    public void cropImageUri( Uri uri, Uri outputUri, int outputX, int outputY, int requestCode) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", outputX);
            intent.putExtra("outputY", outputY);
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            intent.putExtra("return-data", false);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESULT_PHOTO_CROP) {//裁剪图片回调
            if (resultCode == Activity.RESULT_OK) {
                String filePath = CameraImageUtil.getPath(this, fileCropUri);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_RESULT_CROP_PHOTO,filePath);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }else if(requestCode == REQUEST_RESULT_PHOTO){//接受系统拍照回调
            if(resultCode == Activity.RESULT_OK){
                //单图片模式启动裁剪工具
                if(mPhotoMode == MODE_SINGLE_CROP){
                    Log.d("unlock","uri.getPath()"+(fileUri==null));//这里有时会为空
                    String filePath = CameraImageUtil.getPath(this, fileUri);
                    File file = new File(filePath);
                    fileCropUri = CameraImageUtil.getOutputMediaFileUri();
                    cropImageUri(fileUri, fileCropUri,640,640,REQUEST_RESULT_PHOTO_CROP);
                }
                //多图片时将它和之前选择的图片都返回
                else{
                    String filePath = CameraImageUtil.getPath(this, fileUri);
                    ImageInformation ImageInformation = new ImageInformation(filePath);
                    mPickData.add(ImageInformation);
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_RESULT_PHOTO_LIST, mPickData);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static class GridViewCheckTag {
        View iconFore;
        String path = "";

        GridViewCheckTag(View iconFore) {
            this.iconFore = iconFore;
        }
    }
}
