package com.luckyaf.strongbox.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.bean.FileModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/6/5
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> implements View.OnClickListener {

    protected LayoutInflater mInflater;
    protected Context context;
    protected List<?> mFileList;
    protected Boolean[] arraySelected;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public FolderAdapter(Context context, List<?> fileList) {
        this.context = context;
        this.mFileList = fileList;
        mInflater = LayoutInflater.from(this.context);
    }

    /**
     * 获取选中的项
     *
     * @return
     */
    public Boolean[] getEnablePreViewFiles() {
        return arraySelected;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(FileModel) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    //define interface
    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , FileModel fileModel);
    }

    public void setPreViewFiles(List<?> list) {
        mFileList = list;
        arraySelected = new Boolean[mFileList.size()];
        for(int i = 0; i < arraySelected.length; i ++){
            arraySelected[i] = false;
        }
        notifyDataSetChanged();
    }

    public List<FileModel> getSelectedFile(){
        List<FileModel> list = new ArrayList<>();
        for(int i = 0; i < arraySelected.length; i ++){
            if(arraySelected[i]){
                list.add((FileModel) mFileList.get(i));
            }
        }
        return  list;
    }

    @Override
    public FolderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_folder, parent,
                false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(final FolderAdapter.ViewHolder holder, final int position) {
        final FileModel fileModel = (FileModel)mFileList.get(position);
        holder.itemView.setTag(fileModel);
        holder.tvFolderName.setText(fileModel.getName());
        if(fileModel.getFileType() == FileModel.FILE_DIR){
            holder.imgFolderIcon.setImageResource(R.drawable.ic_folder);
            holder.checkBox.setVisibility(View.INVISIBLE);
        }else{
            holder.imgFolderIcon.setImageResource(R.drawable.ic_document);
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arraySelected[position] = holder.checkBox.isChecked();
                }
            });
        }
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
        ImageView imgFolderIcon;
        TextView tvFolderName;
        CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            imgFolderIcon = (ImageView)itemView.findViewById(R.id.img_folder_icon);
            tvFolderName = (TextView)itemView.findViewById(R.id.tv_folder_name);
            checkBox = (CheckBox)itemView.findViewById(R.id.cb_is_selected);


        }

    }
}
