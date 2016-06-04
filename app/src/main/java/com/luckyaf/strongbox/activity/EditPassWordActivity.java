package com.luckyaf.strongbox.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.base.BaseActivity;
import com.luckyaf.strongbox.util.DateUtils;
import com.luckyaf.strongbox.util.ToastUtils;

import de.greenrobot.dao.query.Query;
import me.luckyaf.greendao.CodeBook;
import me.luckyaf.greendao.CodeBookDao;


/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/3/30
 */
public class EditPassWordActivity extends BaseActivity implements TextWatcher{
    private Toolbar mToolbar;
    private Button deleteButton;
    private ImageButton changeButton;
    private EditText etTitle;
    private EditText etUserName;
    private EditText etPassWord;
    private EditText etDescribe;
    private TextView txtUpdateTime;
    private CodeBook mCodeBook;
    private String codeBookId;
    private Boolean canSee;
    private MenuItem menuItem;

    public static String intent_is_update = "INTENT_IS_UPDATE";
    public static String intent_codeBook_id = "INTENT_CODE_BOOK_ID";
    private Boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        initWidget();
        initData();
        initListener();
    }


    @Override
    protected void initToolbar() {

    }

    @Override
    protected boolean isApplyTranslucency() {
        return false;
    }

    public void initWidget() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar_edit);
        initToolBar(mToolbar);
        etTitle = (EditText) findViewById(R.id.et_title);
        etUserName = (EditText) findViewById(R.id.et_userName);
        etPassWord = (EditText) findViewById(R.id.et_password);
        etDescribe = (EditText) findViewById(R.id.et_describe);
        txtUpdateTime = (TextView) findViewById(R.id.tv_time);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        changeButton = (ImageButton) findViewById(R.id.ib_change);
    }

    public void initListener() {
        deleteButton.setOnClickListener(this);
        changeButton.setOnClickListener(this);
        etTitle.addTextChangedListener(this);
        etUserName.addTextChangedListener(this);
        etPassWord.addTextChangedListener(this);
        etDescribe.addTextChangedListener(this);

    }

    public void initData() {
        canSee = false;
        initCodeBook();
        isUpdate = getIntent().getBooleanExtra(EditPassWordActivity.intent_is_update, false);
        if (isUpdate) {
            deleteButton.setVisibility(View.VISIBLE);
            codeBookId = getIntent().getStringExtra(EditPassWordActivity.intent_codeBook_id);
            // mCodeBook = MyApplication.daoMaster.newSession().getCodeBookDao().
            Query query = MyApplication.daoMaster.newSession().getCodeBookDao().queryBuilder()
                    .where(CodeBookDao.Properties.Id.eq(codeBookId))
                    .build();
            mCodeBook = (CodeBook) query.list().get(0);
            canSee = mCodeBook.getCanSee();
            mToolbar.setTitle(getString(R.string.common_look));
        }else{
            mToolbar.setTitle(getString(R.string.common_new));
        }

        setChangeButton();

        etTitle.setText(mCodeBook.getTitle());
        etUserName.setText(mCodeBook.getUsername());
        etPassWord.setText(mCodeBook.getPassword());
        etDescribe.setText(mCodeBook.getDescribe());

        if (isUpdate) {
            txtUpdateTime.setText("上次修改时间：" + DateUtils.TimeMills2Date(mCodeBook.getUpdateTime()));
        } else {
            txtUpdateTime.setText("当前创建时间：" + DateUtils.TimeMills2Date(DateUtils.currentTime2String()));
        }


    }

    public void initCodeBook() {
        mCodeBook = new CodeBook();
        mCodeBook.setDescribe(null);
        mCodeBook.setPassword(null);
        mCodeBook.setUsername(null);
        mCodeBook.setTitle(null);
        mCodeBook.setCanSee(false);
    }

    public void View2Data(){
        mCodeBook.setCanSee(canSee);
        mCodeBook.setPassword(etPassWord.getText().toString().trim());
        mCodeBook.setUpdateTime(DateUtils.currentTime2String());
        mCodeBook.setTitle(etTitle.getText().toString().trim());
        mCodeBook.setUsername(etUserName.getText().toString().trim());
        mCodeBook.setDescribe(etDescribe.getText().toString().trim());
    }


    public void setChangeButton() {
        if (canSee) {
            etPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            changeButton.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_password_visibility));
        } else {
            etPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            changeButton.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_password_visibility_off));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_password, menu);
        menuItem = menu.findItem(R.id.done);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                saveData();

                break;
            case android.R.id.home:
                finish();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveData(){
        View2Data();
        if(isUpdate){
            MyApplication.daoMaster.newSession().getCodeBookDao().update(mCodeBook);
            ToastUtils.showMessage(getBaseContext(),getString(R.string.common_save_succeed));
        }else{
            mCodeBook.setCreateTime(DateUtils.currentTime2String());
            MyApplication.daoMaster.newSession().getCodeBookDao().insert(mCodeBook);
            ToastUtils.showMessage(getBaseContext(),getString(R.string.common_update_succeed));
        }
        setResult(RESULT_OK);
        finish();
    }



    /**
     *
     * 删除
    * */
    public void deleteData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.common_tip));
        builder.setMessage(getString(R.string.message_confirm_delete));//
        builder.setPositiveButton(getString(R.string.common_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyApplication.daoMaster.newSession().getCodeBookDao().delete(mCodeBook);
                dialog.dismiss();
                setResult(RESULT_OK);
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




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteButton:
                deleteData();
                break;
            case R.id.ib_change:
                canSee = !canSee;
                setChangeButton();
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        String title = etTitle.getText().toString().trim();
        String name = etUserName.getText().toString().trim();
        String password = etPassWord.getText().toString().trim();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            menuItem.setVisible(false);
        }else{
            menuItem.setVisible(true);
        }
    }


}
