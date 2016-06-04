package com.luckyaf.strongbox.util.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luckyaf.strongbox.R;

import java.util.ArrayList;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/4/23
 */
public class ImageFolderAdapter extends BaseAdapter {

    private String mSelect = "";
    private Context mContext;
    ArrayList<ImageFolder> mFolderData = new ArrayList<>();

    public ImageFolderAdapter(ArrayList<ImageFolder> mFolderData,Context context) {
        this.mFolderData = mFolderData;
        this.mContext = context;
    }

    public String getSelect() {
        return mSelect;
    }

    public void setSelect(int pos) {
        if (pos >= getCount()) {
            return;
        }

        mSelect = mFolderData.get(pos).getmName();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFolderData.size();
    }

    @Override
    public Object getItem(int position) {
        return mFolderData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_photo_folder, parent, false);
            holder = new ViewHolder();
            holder.foldIcon = (ImageView) convertView.findViewById(R.id.foldIcon);
            holder.foldName = (TextView) convertView.findViewById(R.id.foldName);
            holder.photoCount = (TextView) convertView.findViewById(R.id.photoCount);
            holder.check = convertView.findViewById(R.id.check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageFolder data = mFolderData.get(position);
        String uri = data.getPath();
        int count = data.getCount();

        holder.foldName.setText(data.getmName());
        holder.photoCount.setText(String.format("%d张", count));

        String path = ImageInformation.pathAddPreFix(uri);
        String temPath = path.substring(path.indexOf("file://") + 7);
        holder.foldIcon.setTag(R.id.foldIcon,temPath);
       // ImageLoad.getInstance().load(temPath,holder.foldIcon,holder.foldIcon.getLayoutParams().width,holder.foldIcon.getLayoutParams().height);
        Glide.with(mContext)//同时接受Activity，Fragment
                .load(temPath)
                .into(holder.foldIcon);
        if (data.getmName().equals(mSelect)) {
            holder.check.setVisibility(View.VISIBLE);
        } else {
            holder.check.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView foldIcon;
        TextView foldName;
        TextView photoCount;
        View check;
    }
}