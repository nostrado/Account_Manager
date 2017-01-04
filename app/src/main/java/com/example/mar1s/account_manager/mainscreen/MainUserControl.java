package com.example.mar1s.account_manager.mainscreen;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mar1s.account_manager.DAO;
import com.example.mar1s.account_manager.R;

public class MainUserControl extends AppCompatActivity {
    private Button btn_initapp;
    private Button btn_modipassword;
    private Button btn_modipattern;
    private Button btn_wipeaccount;

    private DAO dao;

    private Dialog dialog;
    private Button dialog_btn_cancel;
    private Button dialog_btn_update;
    private EditText dialog_formalpw_input;
    private EditText dialog_newpw_input;
    private EditText dialog_newpw_check;
    private LinearLayout dialog_formalpw_view;
    private LinearLayout dialog_newpw_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_userinfo_control);

        dao = DAO.sharedInstance();

        btn_initapp = (Button) findViewById(R.id.btn_control_initapp);
        btn_modipassword = (Button) findViewById(R.id.btn_control_modipassword);
        btn_modipattern = (Button) findViewById(R.id.btn_control_modipattern);
        btn_wipeaccount = (Button) findViewById(R.id.btn_control_wipeaccount);

        btn_initapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogBox("모든 정보 초기화","모든 정보 초기화를 진행하겠습니까?",0).show();
            }
        });

        btn_modipassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move password update dialog
            }
        });

        btn_modipattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move pattern update dialog
            }
        });

        btn_wipeaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogBox("계정 데이터 초기화","앱에 등록된 모든 계정들을 삭제하겠습니까?",1).show();
            }
        });
    }

    private AlertDialog createDialogBox(String type, String msg, final int controlnum) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(type);
        builder.setMessage(msg);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (controlnum) {
                    case 0: // 모든 데이터 삭제
                        dao.deleteAlldata();
                        Toast.makeText(getApplicationContext(),"모든 데이터가 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                        break;

                    case 1: // 등록된 계정 모두 삭제, 사용자 정보는 유지
                        dao.deleteAllAccount();
                        Toast.makeText(getApplicationContext(),"등록된 계정이 모두 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                        break;

                    default: // Error
                        Toast.makeText(getApplicationContext(),"알 수 없는 제어 번호 입니다. 확인하세요.",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void initUpdatePWDialog() {
        dialog = new Dialog(this);
    }
}
