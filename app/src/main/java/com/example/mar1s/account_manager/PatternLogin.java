package com.example.mar1s.account_manager;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eftimoff.patternview.PatternView;
import com.example.mar1s.account_manager.mainscreen.Mainscreen;
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
    private Button dialog_btn_cancle;
    private Button dialog_btn_access;
    private EditText dialog_edit_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_login);

        dao = DAO.sharedInstance();
        dao.initDAO(this);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_password_login);
        dialog.setCanceledOnTouchOutside(false);

        dialog_btn_cancle = (Button) dialog.findViewById(R.id.btn_cancle);
        dialog_btn_access = (Button) dialog.findViewById(R.id.btn_access);
        dialog_edit_input = (EditText) dialog.findViewById(R.id.input_pw);

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
                    patternView.clearPattern();
                    Intent intent = new Intent(getApplicationContext(), Mainscreen.class);
                    startActivity(intent);
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

        dialog_btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                dialog_edit_input.setText("");
            }
        });

        dialog_btn_access.setOnClickListener(new View.OnClickListener() {
            String input;
            @Override
            public void onClick(View view) {
                input = dialog_edit_input.getText().toString();
                if(!input.isEmpty() && input.equals(password)) {
                    dialog.cancel();
                    dialog_edit_input.setText("");
                    Intent intent = new Intent(getApplicationContext(),Mainscreen.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"비밀번호가 틀렸습니다.\n다시 입력하세요.",Toast.LENGTH_SHORT).show();
                    dialog_edit_input.setText("");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        User user = dao.getUser();
        if(user != null) {
            pattern = user.getPattern();
            password = user.getPassword();
            btn_signin.setEnabled(false);
            patternView.enableInput();
            btn_pwlogin.setEnabled(true);
        }
        else {
            Toast.makeText(getApplicationContext(),"사용자 등록이 필요합니다.",Toast.LENGTH_SHORT).show();
            patternView.disableInput();
            btn_pwlogin.setEnabled(false);
            btn_signin.setEnabled(true);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        dao.colseDAO();
        super.onDestroy();
    }
}
