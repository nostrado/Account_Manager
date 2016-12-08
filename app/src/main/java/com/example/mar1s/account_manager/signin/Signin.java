package com.example.mar1s.account_manager.signin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eftimoff.patternview.PatternView;
import com.example.mar1s.account_manager.R;

public class Signin extends AppCompatActivity {

    private PatternView patternView_signin;
    private String user_pattern;
    private boolean check_flag;

    private EditText user_pw;
    private EditText check_pw;

    private TextView pattern_state;
    private static final String state_input = "인증에 사용할 패턴을 입력하세요.";
    private static final String state_checck = "확인을 위해 같은 패턴을 한번 더 입력하세요.";
    private static final String state_confirm = "패턴이 확인되었습니다.";

    private Button btn_enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signin);

        patternView_signin = (PatternView) findViewById(R.id.patternView_signin);
        user_pw = (EditText) findViewById(R.id.user_pw);
        check_pw = (EditText) findViewById(R.id.check_pw);
        pattern_state = (TextView) findViewById(R.id.pattern_state);
        btn_enroll = (Button) findViewById(R.id.btn_enroll);

        patternView_signin.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            @Override
            public void onPatternDetected() {
                if(check_flag == false) { // first user pattern input

                }
                else { // second user pattern input

                }
            }
        });

        user_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        check_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
