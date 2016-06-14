package com.luckyaf.strongbox.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.base.BaseActivity;
import com.luckyaf.strongbox.util.DateUtils;
import com.luckyaf.strongbox.util.FileUtils;
import com.luckyaf.strongbox.util.ToastUtils;

import me.luckyaf.greendao.MyDocument;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/6/6
 */
public class EditHideFileActivity extends BaseActivity {
    private Toolbar mToolbar;
    private String documentId;
    private String documentOldName;
    private String documentNewName;
    private String documentTime;

    public static String intent_document_id = "INTENT_DOCUMENT_ID";
    public static String intent_document_old = "INTENT_DOCUMENT_OLD";
    public static String intent_document_new = "INTENT_DOCUMENT_NEW";
    public static String intent_document_time = "INTENT_DOCUMENT_TIME";

    private TextView tvFileOldName;
    private TextView tvFileNowName;
    private TextView tvTime;
    private Button btnDecryption;
    private Button btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hide_file);
        initWidget();
        initData();
        initListener();
    }

    public void initWidget(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar_edit);
        initToolBar(mToolbar);
        tvFileOldName = (TextView)findViewById(R.id.tv_file_old_name);
        tvFileNowName = (TextView)findViewById(R.id.tv_file_now_name);
        tvTime = (TextView)findViewById(R.id.tv_encrypt_time);
        btnDecryption = (Button)findViewById(R.id.btn_decryption);
        btnDelete = (Button)findViewById(R.id.btn_delete);

    }

    public void initData(){
        mToolbar.setTitle(getString(R.string.file_deal_file));
        documentId = getIntent().getStringExtra(intent_document_id);
        documentNewName = getIntent().getStringExtra(intent_document_new);
        documentOldName = getIntent().getStringExtra(intent_document_old);
        documentTime = getIntent().getStringExtra(intent_document_time);
        tvFileOldName.setText(documentOldName);
        tvFileNowName.setText(documentNewName);
        tvTime.setText(DateUtils.TimeMills2Date(documentTime));
    }

    public void initListener(){
        btnDelete.setOnClickListener(this);
        btnDecryption.setOnClickListener(this);

    }

    @Override
    protected void initToolbar() {

    }

    public void decryptionFile(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.common_tip));
        builder.setMessage(getString(R.string.message_confirm_decryption_file));//
        builder.setPositiveButton(getString(R.string.common_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDocument myDocument = new MyDocument();
                myDocument.setId(Long.valueOf(documentId));
                myDocument.setOldFileName(documentOldName);
                myDocument.setNewFilename(documentNewName);
                if(FileUtils.unHideFile(myDocument)){
                    ToastUtils.showMessage(getBaseContext(),"解密文件成功");
                    dialog.dismiss();
                    setResult(RESULT_OK);
                    finish();
                }else{
                    ToastUtils.showMessage(getBaseContext(),"解密文件失败");
                    dialog.dismiss();
                }
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

    public void deleteFile(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.common_tip));
        builder.setMessage(getString(R.string.message_confirm_delete_file));//
        builder.setPositiveButton(getString(R.string.common_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDocument myDocument = new MyDocument();
                myDocument.setId(Long.valueOf(documentId));
                myDocument.setOldFileName(documentOldName);
                myDocument.setNewFilename(documentNewName);
                if(FileUtils.deleteAudioByPath(myDocument)){
                    ToastUtils.showMessage(getBaseContext(),"删除文件成功");
                    dialog.dismiss();
                    setResult(RESULT_OK);
                    finish();
                }else{
                    ToastUtils.showMessage(getBaseContext(),"删除文件失败");
                    dialog.dismiss();
                }
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
    protected boolean isApplyTranslucency() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_decryption:
                decryptionFile();
                break;
            case R.id.btn_delete:
                deleteFile();
                break;
            default:
                break;
        }

    }
}
