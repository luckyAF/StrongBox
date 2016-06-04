package com.luckyaf.strongbox.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.bean.MyProgram;
import com.luckyaf.strongbox.service.ProgramProtectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.greenrobot.dao.query.QueryBuilder;
import me.luckyaf.greendao.MyApps;
import me.luckyaf.greendao.MyAppsDao;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/4/28
 */
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder>{
    private ArrayList<MyProgram> mData = new ArrayList<>();
    private Context mContext;
    private int selectedPosition;

    public ProgramAdapter(Context context,ArrayList<MyProgram> data){
        this.mContext = context;
        this.mData = data;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_program, parent,
                false);
        ViewHolder holder = new ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imgAppIcon.setImageDrawable(mData.get(position).getResolveInfo().loadIcon(mContext.getPackageManager()));
        holder.tvAppName.setText(mData.get(position).getResolveInfo().loadLabel(mContext.getPackageManager()));
        if(mData.get(position).isLock){
            holder.imgBtnIsLock.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_lock_open));
        }else{
            holder.imgBtnIsLock.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_lock));
        }
        holder.imgBtnIsLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mData.get(position).isLock){
                    selectedPosition = position;
                    unlockProgram();
                }else{
                    MyApps newApp = new MyApps();
                    newApp.setLock(true);
                    newApp.setPackageName(mData.get(position).getResolveInfo().activityInfo.applicationInfo.packageName);
                    newApp.setClassName(mData.get(position).getResolveInfo().activityInfo.applicationInfo.className);
                    MyApplication.daoMaster.newSession().getMyAppsDao().insert(newApp);
                    mData.remove(position);
                }

                notifyDataSetChanged();
            }
        });
    }


    public void unlockProgram(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.common_tip));
        builder.setMessage(mContext.getString(R.string.message_confirm_unlock));//
        builder.setPositiveButton(mContext.getString(R.string.common_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mData.remove(selectedPosition);
                notifyDataSetChanged();
                QueryBuilder qb = MyApplication.daoMaster.newSession().getMyAppsDao().queryBuilder();
                List apps = qb.where(MyAppsDao.Properties.PackageName.eq(mData.get(selectedPosition).getResolveInfo().activityInfo.applicationInfo.packageName)).list();
                if(apps!= null && apps.size() != 0){
                    MyApps thisApp = (MyApps) apps.get(0);
                    MyApplication.daoMaster.newSession().getMyAppsDao().delete(thisApp);
                    mContext.stopService(new Intent(mContext, ProgramProtectService.class));
                    mContext.stopService(new Intent(mContext, ProgramProtectService.class));
                }
                dialog.dismiss();

            }
        });
        builder.setNegativeButton(mContext.getString(R.string.common_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
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
