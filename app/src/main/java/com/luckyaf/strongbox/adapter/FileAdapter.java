package com.luckyaf.strongbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.bean.FileModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.luckyaf.greendao.MyDocument;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/6/4
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> implements View.OnClickListener {

    protected LayoutInflater mInflater;
    protected Context context;
    protected List<?> mFileList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public FileAdapter(Context context, List<?> fileList) {
        this.context = context;
        this.mFileList = fileList;
        mInflater = LayoutInflater.from(this.context);
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(MyDocument) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    //define interface
    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , MyDocument myDocument);
    }

    public void setPreViewFiles(List<?> list) {
        mFileList = list;
        notifyDataSetChanged();
    }



    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_encrypt_file, parent,
                false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(final FileAdapter.ViewHolder holder, final int position) {
        final MyDocument myDocument = (MyDocument) mFileList.get(position);
        holder.itemView.setTag(myDocument);
        holder.tvFileName.setText(myDocument.getOldFileName());
    }

    @Override
    public int getItemCount() {
        if (mFileList != null) {
            return mFileList.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFileIcon;
        TextView tvFileName;
        public ViewHolder(View itemView) {
            super(itemView);
            imgFileIcon = (ImageView)itemView.findViewById(R.id.img_file_icon);
            tvFileName = (TextView)itemView.findViewById(R.id.tv_file_name);


        }

    }
}
