package com.luckyaf.strongbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.bean.MyProgram;

import java.util.ArrayList;

/**
 * 类描述：
 *
 * @auhter Created by luckyAF on 16/4/28
 */
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder>{
    private ArrayList<MyProgram> mData = new ArrayList<>();
    private Context mContext;
    public ProgramAdapter(Context context,ArrayList<MyProgram> data){
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_program, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imgAppIcon.setImageDrawable(mData.get(position).getResolveInfo().loadIcon(mContext.getPackageManager()));
        holder.tvAppName.setText(mData.get(position).getResolveInfo().loadLabel(mContext.getPackageManager()));
        //holder.imgBtnIsLock.setc
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAppIcon;
        TextView tvAppName;
        ImageButton imgBtnIsLock;
        public ViewHolder(View itemView) {
            super(itemView);
            imgAppIcon = (ImageView)itemView.findViewById(R.id.img_app_icon);
            tvAppName = (TextView)itemView.findViewById(R.id.tv_app_name);
            imgBtnIsLock = (ImageButton)itemView.findViewById(R.id.imgBtn_Lock);


        }


    }
}
