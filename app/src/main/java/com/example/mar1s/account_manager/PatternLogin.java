package com.example.mar1s.account_manager;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eftimoff.patternview.PatternView;
import com.example.mar1s.account_manager.models.User;
import com.example.mar1s.account_manager.signin.Signin;

public class PatternLogin extends AppCompatActivity {

    private PatternView patternView;

    private Button btn_pwlogin;
    private Button btn_signin;
    private Button btn_info;

    private String pattern;
    private String password;

    private DAO dao;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_login);

        dao = DAO.sharedInstance();
        dao.initDAO(this);

        patternView = (PatternView) findViewById(R.id.patternView);
        btn_pwlogin = (Button) findViewById(R.id.btn_pwlogin);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_info = (Button) findViewById(R.id.btn_info);

        patternView.setTactileFeedbackEnabled(false);
        patternView.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            String input;
            @Override
            public void onPatternDetected() {
                input = patternView.getPatternString();
                if(pattern.equals(input)){
                    Toast.makeText(getApplicationContext(),"인증되었습니다.",Toast.LENGTH_SHORT).show();
                    patternView.clearPattern();
                }
                else {
                    Toast.makeText(getApplicationContext(),"일치하지 않습니다.\n다시 입력하세요.",Toast.LENGTH_SHORT).show();
                    patternView.clearPattern();
                }
            }
        });

        btn_pwlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
                dialog.show();
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signin.class);
                startActivity(intent);
            }
        });

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Help_info.class);
                startActivity(intent);
            }
        });
    }

    private void createDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_password_login);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onResume() {
        User user = dao.getUser();
        if(user != null) {
            pattern = user.getPattern();
            password = user.getPassword();
            btn_signin.setEnabled(false);
            patternView.enableInput();
        }
        else {
            Toast.makeText(getApplicationContext(),"사용자 등록이 필요합니다.",Toast.LENGTH_SHORT).show();
            patternView.disableInput();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        dao.deleteAlldata();
        dao.colseDAO();
        super.onDestroy();
    }
}
