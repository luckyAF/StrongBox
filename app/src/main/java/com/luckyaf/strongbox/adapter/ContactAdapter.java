package com.luckyaf.strongbox.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.BR;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.control.ContactViewModel;
import com.luckyaf.strongbox.control.PasswordViewModel;

import java.util.ArrayList;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/4/27
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private ArrayList<ContactViewModel> mData = new ArrayList<>();

    public ContactAdapter(ArrayList<ContactViewModel> data) {
        mData = data;
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.item_contact, parent, false);
        ContactAdapter.ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.contact, mData.get(position));
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
