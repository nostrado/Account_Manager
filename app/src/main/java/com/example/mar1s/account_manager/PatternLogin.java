package com.example.mar1s.account_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eftimoff.patternview.PatternView;
import com.example.mar1s.account_manager.signin.Signin;

public class PatternLogin extends AppCompatActivity {

    private PatternView patternView;

    private Button btn_pwlogin;
    private Button btn_signin;
    private Button btn_info;

    private String patternString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_login);

        patternView = (PatternView) findViewById(R.id.patternView);
        btn_pwlogin = (Button) findViewById(R.id.btn_pwlogin);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_info = (Button) findViewById(R.id.btn_info);

        patternView.setTactileFeedbackEnabled(false);
        patternView.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            @Override
            public void onPatternDetected() {
                patternString = patternView.getPatternString();
                Toast.makeText(getApplicationContext(),patternString,Toast.LENGTH_LONG).show();
                patternView.clearPattern();
            }
        });

        btn_pwlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}
