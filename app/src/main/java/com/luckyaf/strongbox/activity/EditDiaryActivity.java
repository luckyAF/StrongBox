package com.luckyaf.strongbox.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.util.DateUtils;

import de.greenrobot.dao.query.Query;
import me.luckyaf.greendao.CodeBookDao;
import me.luckyaf.greendao.Diary;

/**
 * 类描述：
 *
 * @auhter Created by luckyAF on 16/4/10
 */
public class EditDiaryActivity extends BaseSwipeBackActivity implements TextWatcher {
    private Toolbar mToolbar;
    private MenuItem itemDone;
    private MenuItem itemDelete;
    private EditText etTitle;
    private EditText etContent;
    private TextView tvTime;
    private Diary mDiary;
    private Boolean isUpdate;
    private String diaryId;
    public static String intent_is_update = "INTENT_IS_UPDATE";
    public static String intent_diary_id = "INTENT_DIARY_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initWidget();
        initData();
        initListener();
    }



    @Override
    protected void initToolbar() {

    }

    public void initWidget(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar_edit);
        initToolBar(mToolbar);
        etTitle = (EditText)findViewById(R.id.et_title);
        etContent = (EditText)findViewById(R.id.et_content);
        tvTime = (TextView)findViewById(R.id.tv_time);

    }

    public void initData(){
        initDiary();
        isUpdate = getIntent().getBooleanExtra(EditDiaryActivity.intent_is_update,false);
        if (isUpdate) {
            itemDelete.setVisible(true);
            diaryId = getIntent().getStringExtra(EditDiaryActivity.intent_diary_id);
            // mCodeBook = MyApplication.daoMaster.newSession().getCodeBookDao().
            Query query = MyApplication.daoMaster.newSession().getDiaryDao().queryBuilder()
                    .where(CodeBookDao.Properties.Id.eq(diaryId))
                    .build();
            mDiary = (Diary) query.list().get(0);
            mToolbar.setTitle(getString(R.string.common_look));
        }else{
            mToolbar.setTitle(getString(R.string.common_new));
        }

        etTitle.setText(mDiary.getTitle());
        tvTime.setText(DateUtils.TimeMills2DateWeek(mDiary.getCreateTime()));
        etContent.setText(mDiary.getContent());
    }

    public void initListener(){

    }
    public void initDiary(){
        mDiary = new Diary();
        mDiary.setTitle(null);
        mDiary.setCreateTime(DateUtils.currentTime2String());
        mDiary.setContent(null);
        mDiary.setUpdateTime(null);
        mDiary.setWeather(null);
    }
    @Override
    public void onClick(View v) {

    }


    @Override
    protected int getContentView() {
        return R.layout.activity_edit_diary;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_diary, menu);
        itemDone = menu.findItem(R.id.done);
        itemDelete = menu.findItem(R.id.delete);
        itemDone.setVisible(false);
        itemDelete.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                break;
            case android.R.id.home:
                finish();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
