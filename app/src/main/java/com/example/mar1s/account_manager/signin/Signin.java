package com.example.mar1s.account_manager.signin;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.patternview.PatternView;
import com.example.mar1s.account_manager.R;
import com.example.mar1s.account_manager.models.User;

import io.realm.Realm;

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

    private Realm realm;
    private String pattern;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signin);

        patternView_signin = (PatternView) findViewById(R.id.patternView_signin);
        user_pw = (EditText) findViewById(R.id.user_pw);
        check_pw = (EditText) findViewById(R.id.check_pw);
        pattern_state = (TextView) findViewById(R.id.pattern_state);
        btn_enroll = (Button) findViewById(R.id.btn_enroll);
        realm = Realm.getDefaultInstance();
        check_flag = false;

        patternView_signin.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            String pattern1;
            String pattern2;
            @Override
            public void onPatternDetected() {
                if(check_flag == false) { // first user pattern input
                    pattern1 = patternView_signin.getPatternString();
                    check_flag = true;
                    patternView_signin.clearPattern();
                    pattern_state.setText(state_checck);
                    pattern_state.setBackgroundColor(Color.YELLOW);
                }
                else { // second user pattern input
                    pattern2 = patternView_signin.getPatternString();
                    if(pattern1.equals(pattern2)){
                        pattern = pattern2;
                        pattern_state.setText(state_confirm);
                        pattern_state.setBackgroundColor(Color.GREEN);
                        patternView_signin.disableInput();
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
                enrollUser();
            }
        });
    }

    private void enrollUser(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.createObject(User.class);
                user.setPattern(pattern);
                user.setPassword(password);
            }
        });
    }
}
