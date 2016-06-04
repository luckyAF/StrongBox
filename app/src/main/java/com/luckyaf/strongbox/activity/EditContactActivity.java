package com.luckyaf.strongbox.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.base.BaseActivity;
import com.luckyaf.strongbox.util.ToastUtils;

import de.greenrobot.dao.query.Query;
import me.luckyaf.greendao.MyContact;
import me.luckyaf.greendao.MyContactDao;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/4/27
 */
public class EditContactActivity extends BaseActivity implements TextWatcher {

    private Toolbar mToolbar;
    private Button deleteButton;
    private EditText etName;
    private EditText etPhone;
    private EditText etRemark;
    private EditText etEmail;
    private EditText etAddress;
    private MyContact mContact;
    private String contactId;
    private MenuItem menuItem;

    public static String intent_is_update = "INTENT_IS_UPDATE";
    public static String intent_contact_id = "INTENT_CONTACT_ID";
    private Boolean isUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        initWidget();
        initData();
        initListener();
    }

    public void initWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_edit);
        initToolBar(mToolbar);
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etRemark = (EditText) findViewById(R.id.et_remark);
        etEmail = (EditText) findViewById(R.id.et_email);
        etAddress = (EditText) findViewById(R.id.et_address);
        deleteButton = (Button) findViewById(R.id.deleteButton);
    }

    public void initData() {
        initContact();
        isUpdate = getIntent().getBooleanExtra(EditContactActivity.intent_is_update, false);
        if (isUpdate) {
            contactId = getIntent().getStringExtra(EditContactActivity.intent_contact_id);
            Query query = MyApplication.daoMaster.newSession().getMyContactDao().queryBuilder()
                    .where(MyContactDao.Properties.Id.eq(contactId))
                    .build();
            mContact = (MyContact) query.list().get(0);
            mToolbar.setTitle(getString(R.string.common_look));
        } else {
            mToolbar.setTitle(getString(R.string.common_new));
        }

        etName.setText(mContact.getName());
        etPhone.setText(mContact.getPhone());
        etRemark.setText(mContact.getRemark());
        etEmail.setText(mContact.getEmail());
        etAddress.setText(mContact.getAddress());

    }

    public void initListener() {
        etName.addTextChangedListener(this);
        etAddress.addTextChangedListener(this);
        etEmail.addTextChangedListener(this);
        etRemark.addTextChangedListener(this);
        etPhone.addTextChangedListener(this);
        deleteButton.setOnClickListener(this);
    }

    public void initContact() {
        mContact = new MyContact();
        mContact.setRemark(null);
        mContact.setAddress(null);
        mContact.setEmail(null);
        mContact.setName(null);
        mContact.setPhone(null);
    }

    public void View2Data() {
        mContact.setName(etName.getText().toString().trim());
        mContact.setPhone(etPhone.getText().toString().trim());
        mContact.setRemark(etRemark.getText().toString().trim());
        mContact.setEmail(etEmail.getText().toString().trim());
        mContact.setAddress(etAddress.getText().toString().trim());


    }


    @Override
    protected void initToolbar() {

    }

    @Override
    protected boolean isApplyTranslucency() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteButton:
                deleteData();
                break;
            default:
                break;
        }

    }

    public void saveData() {
        View2Data();
        if (isUpdate) {
            MyApplication.daoMaster.newSession().getMyContactDao().update(mContact);
            ToastUtils.showMessage(getBaseContext(), getString(R.string.common_save_succeed));
        } else {
            MyApplication.daoMaster.newSession().getMyContactDao().insert(mContact);
            ToastUtils.showMessage(getBaseContext(), getString(R.string.common_update_succeed));
        }
        setResult(RESULT_OK);
        finish();
    }

    public void deleteData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.common_tip));
        builder.setMessage(getString(R.string.message_confirm_delete));//
        builder.setPositiveButton(getString(R.string.common_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyApplication.daoMaster.newSession().getMyContactDao().delete(mContact);
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
            default:
                break;
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
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            menuItem.setVisible(false);
        } else {
            menuItem.setVisible(true);
        }
    }
}
