package com.luckyaf.strongbox.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.BR;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.control.CalculatorViewModel;
import com.luckyaf.strongbox.control.PasswordViewModel;

import java.util.ArrayList;

import me.luckyaf.greendao.CodeBook;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/4/6
 */
public class CodeBookAdapter extends RecyclerView.Adapter<CodeBookAdapter.ViewHolder> {

    private ArrayList<PasswordViewModel> mData = new ArrayList<>();
    public CodeBookAdapter(ArrayList<PasswordViewModel> data) {
        mData = data;
    }

    @Override
    public CodeBookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.item_code_book, parent, false);
        CodeBookAdapter.ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(CodeBookAdapter.ViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.password,mData.get(position));
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
