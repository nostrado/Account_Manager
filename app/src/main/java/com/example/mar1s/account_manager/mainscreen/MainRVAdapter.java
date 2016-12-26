package com.example.mar1s.account_manager.mainscreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mar1s.account_manager.R;

/**
 * Created by mar1s on 2016-12-26.
 */

public class MainRVAdapter extends RecyclerView.Adapter<MainRVAdapter.ViewHolder> {

    public final static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView user_id;
        public TextView user_name;

        public ViewHolder(View itemView) {
            super(itemView);
            user_id = (TextView) itemView.findViewById(R.id.user_id);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
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

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
