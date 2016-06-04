package com.luckyaf.strongbox.util.image;

import android.content.Context;
import android.database.Cursor;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.luckyaf.strongbox.R;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/4/24
 */
public class ImageGridAdapter extends CursorAdapter {

    final int itemWidth;
    LayoutInflater mInflater;
    SelectImageActivity mActivity;
    View.OnClickListener mCheckItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mActivity.clickPhotoCheck(v);
        }
    };
    float dimension;

    ImageGridAdapter(Context context, Cursor c, boolean autoRequery, SelectImageActivity activity) {
        super(context, c, autoRequery);

        mInflater = LayoutInflater.from(context);
        mActivity = activity;
        dimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics());
        itemWidth = (int) (context.getResources().getDisplayMetrics().widthPixels/3 - dimension);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View convertView = mInflater.inflate(R.layout.item_photo_grid_list, parent, false);
        ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        layoutParams.height = itemWidth;
        layoutParams.width = itemWidth;
        convertView.setLayoutParams(layoutParams);


        GridViewHolder holder = new GridViewHolder();
        holder.icon = (ImageView) convertView.findViewById(R.id.icon);

        holder.iconFore = (ImageView) convertView.findViewById(R.id.iconFore);
        holder.check = (CheckBox) convertView.findViewById(R.id.check);
        SelectImageActivity.GridViewCheckTag checkTag = new SelectImageActivity.GridViewCheckTag(holder.iconFore);
        holder.check.setTag(checkTag);

        holder.check.setOnClickListener(mCheckItem);
        convertView.setTag(holder);

        ViewGroup.LayoutParams iconParam = holder.icon.getLayoutParams();
        iconParam.width = itemWidth;
        iconParam.height = itemWidth;
        holder.icon.setLayoutParams(iconParam);

        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        GridViewHolder holder;
        holder = (GridViewHolder) view.getTag();

        final String path = ImageInformation.pathAddPreFix(cursor.getString(1));
        String temPath = path.substring(path.indexOf("file://") + 7);
        holder.icon.setTag(R.id.icon,temPath);
        //ImageLoad.getInstance().load(temPath, holder.icon, itemWidth,itemWidth);
        Glide.with(mActivity)//同时接受Activity，Fragment
                .load(temPath)
                .into(holder.icon);
        ((SelectImageActivity.GridViewCheckTag) holder.check.getTag()).path = path;
        if(mActivity.getPhotoMode() == SelectImageActivity.MODE_SINGLE_CROP){
            holder.check.setVisibility(View.GONE);
            holder.iconFore.setVisibility(View.GONE);
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.clickPhotoItem(path);
                }
            });
        }else{
            boolean isPick = mActivity.isPicked(path);
            holder.check.setChecked(isPick);
            holder.iconFore.setVisibility(holder.check.isChecked() ? View.VISIBLE : View.GONE);
        }
    }

    static class GridViewHolder {
        ImageView icon;
        ImageView iconFore;
        CheckBox check;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}

