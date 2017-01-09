package com.example.mar1s.account_manager.mainscreen;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mar1s.account_manager.DAO;
import com.example.mar1s.account_manager.R;
import com.example.mar1s.account_manager.models.Account;

import org.w3c.dom.Text;

import io.realm.RealmResults;

/**
 * Created by mar1s on 2016-12-26.
 */

public class MainRVAdapter extends RecyclerView.Adapter<MainRVAdapter.ViewHolder> {

    private RealmResults<Account> accountList;
    private DAO dao;
    private Context context;
    private INF_ViewHolder inf_viewHolder;

    public final static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView user_id;
        public TextView user_name;
        public TextView user_domain;
        public TextView user_pw;

        private Context context;
        private Account account;
        private INF_ViewHolder inf_viewHolder;

        public ViewHolder(View itemView, Context context, INF_ViewHolder infViewHolder) {
            super(itemView);
            user_id = (TextView) itemView.findViewById(R.id.user_id);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            user_domain = (TextView) itemView.findViewById(R.id.user_domain);
            user_pw = (TextView) itemView.findViewById(R.id.user_pw);

            this.context = context;
            this.inf_viewHolder = infViewHolder;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createAlertDialog().show();
                }
            });
        }

        private AlertDialog createAlertDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("계정삭제");
            builder.setMessage("선택한 계정을 삭제하시겠습니까?");

            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // delete One Account
                    DAO.sharedInstance().deleteOneAccount(account);
                    inf_viewHolder.refreshAccountList();
                    Toast.makeText(context,"계정이 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {                }
            });

            AlertDialog dialog = builder.create();
            return dialog;
        }

        public void setAccountonItem(Account account) {
            this.account = account;
        }
    }

    public MainRVAdapter(Context context, INF_ViewHolder infViewHolder) {
        dao = DAO.sharedInstance();
        this.inf_viewHolder = infViewHolder;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View view = mInflater.inflate(R.layout.activity_main_viewitem, parent,false);
        return new ViewHolder(view,context,inf_viewHolder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.user_id.setText("ID: " + accountList.get(position).getUserID());
        holder.user_name.setText("OWNER: " + accountList.get(position).getUserName());
        holder.user_domain.setText("DOMAIN: " + accountList.get(position).getDomain());
        holder.user_pw.setText("PW: "+accountList.get(position).getUserPW());
        holder.setAccountonItem(accountList.get(position));
    }

    @Override
    public int getItemCount() {
        if(accountList == null){
            return 0;
        }
        return accountList.size();
    }

    public void setAccountList(String _domain) {
        accountList = dao.getAccountList(_domain);
    }
}
