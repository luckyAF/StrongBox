package com.luckyaf.strongbox.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.BR;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.control.DiaryViewModel;

import java.util.ArrayList;

/**
 * 类描述：
 *
 * @auhter Created by luckyAF on 16/4/10
 */
public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {

    private ArrayList<DiaryViewModel> mData = new ArrayList<>();
    public DiaryAdapter(ArrayList<DiaryViewModel> data) {
        mData = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.item_diary, parent, false);
        DiaryAdapter.ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.diary,mData.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return this.binding;
        }

    }
}
