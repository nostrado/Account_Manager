package com.example.mar1s.account_manager.mainscreen;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mar1s.account_manager.DAO;
import com.example.mar1s.account_manager.R;

public class MainAddAccount extends AppCompatActivity {
    private EditText add_domain;
    private EditText add_id;
    private EditText add_pw;
    private EditText add_check;
    private EditText add_name;

    private Button btn_add_reset;
    private Button btn_add_enroll;

    private LinearLayout add_pw_view;
    private LinearLayout add_check_view;

    private DAO dao;
    private String domain;
    private String id;
    private String pw;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_account);

        add_pw_view = (LinearLayout) findViewById(R.id.pw_input);
        add_check_view = (LinearLayout) findViewById(R.id.pw_check);

        add_domain = (EditText) findViewById(R.id.add_domain);
        add_id = (EditText) findViewById(R.id.add_id);
        add_pw = (EditText) findViewById(R.id.add_pw);
        add_check = (EditText) findViewById(R.id.add_check);
        add_name = (EditText) findViewById(R.id.add_name);

        btn_add_reset = (Button) findViewById(R.id.btn_add_reset);
        btn_add_enroll = (Button) findViewById(R.id.btn_add_enroll);

        dao = DAO.sharedInstance();

        add_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {      }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String temp1 = add_pw.getText().toString();
                String temp2 = add_check.getText().toString();
                if(!temp1.isEmpty() && temp1.equals(temp2)) {
                    add_pw_view.setBackgroundColor(Color.GREEN);
                    add_check_view.setBackgroundColor(Color.GREEN);
                    add_pw.setEnabled(false);
                    add_check.setEnabled(false);
                    pw = temp1;
                }
                else {
                    add_pw_view.setBackgroundColor(Color.RED);
                    add_check_view.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {            }
        });

        btn_add_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                domain = "";
                id = "";
                pw = "";
                name = "";

                add_domain.setText("");
                add_id.setText("");
                add_pw.setText("");
                add_pw.setEnabled(true);
                add_check.setText("");
                add_check.setEnabled(true);
                add_name.setText("");

                add_pw_view.setBackgroundColor(Color.WHITE);
                add_check_view.setBackgroundColor(Color.WHITE);
            }
        });

        btn_add_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                domain = add_domain.getText().toString();
                id = add_id.getText().toString();
                name = add_name.getText().toString();

                if(domain.isEmpty() || id.isEmpty() || name.isEmpty() || pw.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"입력되지 않은 사항이 있습니다.\n계정 정보 입력을 완료하세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    // 데이터베이스에 게정 추가
                    dao.enrollAccount(domain, id, pw, name);
                    Toast.makeText(getApplicationContext(),"계정 등록이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
