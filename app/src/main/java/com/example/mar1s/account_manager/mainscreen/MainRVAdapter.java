package com.example.mar1s.account_manager.mainscreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mar1s.account_manager.R;
import com.example.mar1s.account_manager.models.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mar1s on 2016-12-26.
 */

public class MainRVAdapter extends RecyclerView.Adapter<MainRVAdapter.ViewHolder> {

    private List<Account> accountList = new ArrayList<>();

    public final static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView user_id;
        public TextView user_name;
        public TextView user_domain;

        public ViewHolder(View itemView) {
            super(itemView);
            user_id = (TextView) itemView.findViewById(R.id.user_id);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            user_domain = (TextView) itemView.findViewById(R.id.user_domain);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View view = mInflater.inflate(R.layout.activity_main_viewitem, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.user_id.setText("ID: " + accountList.get(position).getUserID());
        holder.user_name.setText("OWNER: " + accountList.get(position).getUserName());
        holder.user_domain.setText("DOMAIN: " + accountList.get(position).getDomain());
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public void setAccountList(List<Account> list) {
        accountList = list;
    }
}
