package com.example.mar1s.account_manager.signin;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.patternview.PatternView;
import com.example.mar1s.account_manager.DAO;
import com.example.mar1s.account_manager.R;

public class Signin extends AppCompatActivity {

    private PatternView patternView_signin;
    private boolean check_flag;
    private boolean check_seq_pt;
    private boolean check_seq_pw;

    private EditText user_pw;
    private EditText check_pw;

    private LinearLayout pw;
    private LinearLayout pwcf;

    private TextView pattern_state;
    private static final String state_input = "인증에 사용할 패턴을 입력하세요.";
    private static final String state_check = "확인을 위해 같은 패턴을 한번 더 입력하세요.";
    private static final String state_confirm = "패턴이 확인되었습니다.";

    private Button btn_enroll;
    private Button btn_reset;

    private DAO dao;
    private String pattern;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signin);

        patternView_signin = (PatternView) findViewById(R.id.patternView_signin);
        user_pw = (EditText) findViewById(R.id.user_pw);
        pw = (LinearLayout) findViewById(R.id.password);
        pwcf = (LinearLayout) findViewById(R.id.passwordconfirm);
        check_pw = (EditText) findViewById(R.id.check_pw);
        pattern_state = (TextView) findViewById(R.id.pattern_state);
        btn_enroll = (Button) findViewById(R.id.btn_enroll);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        dao = DAO.sharedInstance();
        check_flag = false;
        check_seq_pw = false;
        check_seq_pt = false;

        patternView_signin.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            String pattern1;
            String pattern2;
            @Override
            public void onPatternDetected() {
                if(check_flag == false) { // first user pattern input
                    pattern1 = patternView_signin.getPatternString();
                    check_flag = true;
                    patternView_signin.clearPattern();
                    pattern_state.setText(state_check);
                    pattern_state.setBackgroundColor(Color.YELLOW);
                }
                else { // second user pattern input
                    pattern2 = patternView_signin.getPatternString();
                    if(pattern1.equals(pattern2)){
                        pattern = pattern2;
                        pattern_state.setText(state_confirm);
                        pattern_state.setBackgroundColor(Color.GREEN);
                        patternView_signin.disableInput();
                        check_seq_pt = true;
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"패턴이 일치하지 않습니다.\n새로운 패턴을 입력하세요.",Toast.LENGTH_SHORT).show();
                        check_flag = false;
                        pattern_state.setText(state_input);
                        pattern_state.setBackgroundColor(Color.parseColor("#ff50bfff"));
                        patternView_signin.clearPattern();
                    }
                }
            }
        });

        check_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {      }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pw_temp1 = user_pw.getText().toString();
                String pw_temp2 = check_pw.getText().toString();
                if(!pw_temp1.isEmpty() && pw_temp1.equals(pw_temp2)){
                    password = pw_temp2;
                    Toast.makeText(getApplicationContext(),"비밀번호 확인이 완료되었습니다.\n다음 단계를 진행하세요.",Toast.LENGTH_SHORT).show();
                    user_pw.setEnabled(false);
                    check_pw.setEnabled(false);
                    pw.setBackgroundColor(Color.GREEN);
                    pwcf.setBackgroundColor(Color.GREEN);
                    check_seq_pw = true;
                }
                else {
                    pw.setBackgroundColor(Color.RED);
                    pwcf.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {            }
        });

        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_seq_pw==true && check_seq_pt==true) {
                    dao.enrollUser(pattern, password);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"등록 절차가 완료되지 않았습니다.\n등록 절차를 완료 하거나,\n처음부터 다시 진행하세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_pw.setEnabled(true);
                user_pw.setText("");
                check_pw.setEnabled(true);
                check_pw.setText("");
                pw.setBackgroundColor(Color.WHITE);
                pwcf.setBackgroundColor(Color.WHITE);
                pattern_state.setText(state_input);
                pattern_state.setBackgroundColor(Color.parseColor("#ff50bfff"));
                patternView_signin.clearPattern();
                patternView_signin.enableInput();
                check_flag = false;
                check_seq_pw = false;
                check_seq_pt = false;
                pattern="";
                password="";
            }
        });
    }
}
