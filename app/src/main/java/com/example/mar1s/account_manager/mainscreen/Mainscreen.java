package com.example.mar1s.account_manager.mainscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mar1s.account_manager.R;

public class Mainscreen extends AppCompatActivity{
    private EditText input_domain;

    private Button btn_search;
    private Button btn_addAC;
    private Button btn_control;

    private RecyclerView main_recyclerView;
    private MainRVAdapter Adapter;

    private String domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_domain = (EditText) findViewById(R.id.input_domain);

        btn_search = (Button) findViewById(R.id.btn_search);
        btn_addAC = (Button) findViewById(R.id.btn_addAC);
        btn_control = (Button) findViewById(R.id.btn_control);

        main_recyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
        Adapter = new MainRVAdapter(this, new INF_ViewHolder() {
            @Override
            public void refreshAccountList() {
                Adapter.setAccountList(domain);
                Adapter.notifyDataSetChanged();
                main_recyclerView.refreshDrawableState();
            }
        });
        main_recyclerView.setAdapter(Adapter);
        main_recyclerView.setHasFixedSize(true);
        main_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                domain = input_domain.getText().toString();
                if(domain.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"검색할 도메인을 입력하세요.",Toast.LENGTH_SHORT).show();
                    input_domain.setText("");
                }
                else {
                    // Refresh Searching Result view
                    Adapter.setAccountList(domain);
                    Adapter.notifyDataSetChanged();
                    main_recyclerView.refreshDrawableState();
                    if(Adapter.getItemCount() == 0) {
                        Toast.makeText(getApplicationContext(),"등록된 계정이 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                    }
                    input_domain.setText("");
                }
            }
        });

        btn_addAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move Account Enroll Activity
                Intent intent = new Intent(getApplicationContext(),MainAddAccount.class);
                startActivity(intent);
            }
        });

        btn_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move User Info Control Activity
                Intent intent = new Intent(getApplicationContext(), MainUserControl.class);
                startActivity(intent);
            }
        });
    }
}
