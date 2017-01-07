package com.example.mar1s.account_manager.mainscreen;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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

public class MainUserControl extends AppCompatActivity {
    private Button btn_initapp;
    private Button btn_modipassword;
    private Button btn_modipattern;
    private Button btn_wipeaccount;

    private DAO dao;

    private Dialog dialogUPPW;
    private Button dialogUPPW_btn_cancel;
    private Button dialogUPPW_btn_update;
    private EditText dialogUPPW_formalpw_input;
    private EditText dialogUPPW_newpw_input;
    private EditText dialogUPPW_newpw_check;
    private LinearLayout dialogUPPW_formalpw_view;
    private LinearLayout dialogUPPW_newpw_view1;
    private LinearLayout dialogUPPW_newpw_view2;
    private String update_password;
    private String formal_password;

    private Dialog dialogUPPT;
    private Button dialogUPPT_btn_cancel;
    private Button dialogUPPT_btn_update;
    private TextView dialogUPPT_state_view;
    private PatternView dialogUPPT_patternview;
    private String formal_pattern;
    private String update_pattern;
    private String input_first;
    private String input_check;
    private int update_phase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_userinfo_control);

        dao = DAO.sharedInstance();

        btn_initapp = (Button) findViewById(R.id.btn_control_initapp);
        btn_modipassword = (Button) findViewById(R.id.btn_control_modipassword);
        btn_modipattern = (Button) findViewById(R.id.btn_control_modipattern);
        btn_wipeaccount = (Button) findViewById(R.id.btn_control_wipeaccount);

        dialogUPPW = new Dialog(this);
        dialogUPPW.setContentView(R.layout.activity_password_update);
        dialogUPPW.setCanceledOnTouchOutside(false);
        dialogUPPW.setTitle("비밀번호 변경");
        dialogUPPW_btn_cancel = (Button) dialogUPPW.findViewById(R.id.pwupdate_btn_cancel);
        dialogUPPW_btn_update = (Button) dialogUPPW.findViewById(R.id.pwupdate_btn_update);
        dialogUPPW_formalpw_input = (EditText) dialogUPPW.findViewById(R.id.pwupdate_formalpw_input);
        dialogUPPW_newpw_input = (EditText) dialogUPPW.findViewById(R.id.pwupdate_newpw_input);
        dialogUPPW_newpw_check = (EditText) dialogUPPW.findViewById(R.id.pwupdate_newpw_check);
        dialogUPPW_formalpw_view = (LinearLayout) dialogUPPW.findViewById(R.id.pwupdate_formalpw_view);
        dialogUPPW_newpw_view1 = (LinearLayout) dialogUPPW.findViewById(R.id.pwupdate_newpw_view1);
        dialogUPPW_newpw_view1.setVisibility(View.GONE);
        dialogUPPW_newpw_view2 = (LinearLayout) dialogUPPW.findViewById(R.id.pwupdate_newpw_view2);
        dialogUPPW_newpw_view2.setVisibility(View.GONE);

        dialogUPPT = new Dialog(this);
        dialogUPPT.setContentView(R.layout.activity_pattern_update);
        dialogUPPT.setCanceledOnTouchOutside(false);
        dialogUPPT.setTitle("패턴 변경");
        dialogUPPT_btn_cancel = (Button) dialogUPPT.findViewById(R.id.updatept_btn_cancel);
        dialogUPPT_btn_update = (Button) dialogUPPT.findViewById(R.id.updatept_btn_update);
        dialogUPPT_btn_update.setEnabled(false);
        dialogUPPT_state_view = (TextView) dialogUPPT.findViewById(R.id.updatept_state_view);
        dialogUPPT_patternview = (PatternView) dialogUPPT.findViewById(R.id.updatept_patternview);
        update_phase = 0;

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
                formal_password = dao.getUser().getPassword();
                initUPPWDialog();
                dialogUPPW.show();
            }
        });

        btn_modipattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move pattern update dialog
                formal_pattern = dao.getUser().getPattern();
                initUPPTDialog();
                dialogUPPT.show();
            }
        });

        btn_wipeaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogBox("계정 데이터 초기화","앱에 등록된 모든 계정들을 삭제하겠습니까?",1).show();
            }
        });

        dialogUPPW_formalpw_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {      }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = dialogUPPW_formalpw_input.getText().toString();
                if(input.equals(formal_password)) {
                    dialogUPPW_formalpw_view.setBackgroundColor(Color.GREEN);
                    dialogUPPW_formalpw_input.setEnabled(false);
                    dialogUPPW_newpw_view1.setVisibility(View.VISIBLE);
                    dialogUPPW_newpw_view2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {            }
        });

        dialogUPPW_newpw_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input1 = dialogUPPW_newpw_input.getText().toString();
                String input2 = dialogUPPW_newpw_check.getText().toString();
                if(!input1.isEmpty() && input2.equals(input1)) {
                    dialogUPPW_newpw_view1.setBackgroundColor(Color.GREEN);
                    dialogUPPW_newpw_view2.setBackgroundColor(Color.GREEN);
                    dialogUPPW_newpw_input.setEnabled(false);
                    dialogUPPW_newpw_check.setEnabled(false);
                    update_password = input1;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {            }
        });

        dialogUPPW_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUPPW.cancel();
                initUPPWDialog();
            }
        });

        dialogUPPW_btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!update_password.isEmpty()) {
                    dao.updateUserPassword(update_password);
                    dialogUPPW.cancel();
                    initUPPWDialog();
                    Toast.makeText(getApplicationContext(),"인증 비밀번호가 변경되었습니다.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"작성하지 않은 입력이 존재합니다.\n작성을 완료하세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogUPPT_patternview.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            @Override
            public void onPatternDetected() {
                switch (update_phase) {
                    case 0:
                        input_first = dialogUPPT_patternview.getPatternString();
                        if(input_first.equals(formal_pattern)) {
                            dialogUPPT_state_view.setText("변경할 패턴을 입력하세요.");
                            dialogUPPT_state_view.setBackgroundColor(Color.YELLOW);
                            dialogUPPT_patternview.clearPattern();
                            update_phase = 1;
                        }
                        else {
                            dialogUPPT_patternview.clearPattern();
                            Toast.makeText(getApplicationContext(),"일치하지 않습니다\n다시 입력하세요.",Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 1:
                        input_first = dialogUPPT_patternview.getPatternString();
                        dialogUPPT_state_view.setText("패턴을 다시 한번 입력하세요.");
                        dialogUPPT_patternview.clearPattern();
                        update_phase = 2;
                        break;

                    case 2:
                        input_check = dialogUPPT_patternview.getPatternString();
                        if(input_check.equals(input_first)) {
                            update_pattern = input_first;
                            dialogUPPT_state_view.setText("변경하기 버튼을 클릭하세요.");
                            dialogUPPT_state_view.setBackgroundColor(Color.GREEN);
                            dialogUPPT_patternview.disableInput();
                            dialogUPPT_btn_update.setEnabled(true);
                            update_phase = 0;
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"일치하지 않습니다. 변경할 패턴 입력 단계로 돌아갑니다.",Toast.LENGTH_SHORT).show();
                            update_phase = 1;
                            dialogUPPT_state_view.setText("변경할 패턴을 입력하세요.");
                            dialogUPPT_state_view.setBackgroundColor(Color.YELLOW);
                            dialogUPPT_patternview.clearPattern();
                        }
                        break;

                    default:
                        break;
                }
            }
        });

        dialogUPPT_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUPPT.cancel();
                initUPPTDialog();
            }
        });

        dialogUPPT_btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!update_pattern.isEmpty()) {
                    dao.updateUserPattern(update_pattern);
                    dialogUPPT.cancel();
                    initUPPTDialog();
                    update_pattern = "";
                    Toast.makeText(getApplicationContext(),"패턴이 변경되었습니다.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"변경 과정이 완료되지 않았습니다.\n변경 과정을 완료하세요.",Toast.LENGTH_SHORT).show();
                }
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

    private void initUPPWDialog() {
        dialogUPPW_formalpw_view.setBackgroundColor(Color.WHITE);
        dialogUPPW_formalpw_input.setText("");
        dialogUPPW_formalpw_input.setEnabled(true);
        dialogUPPW_newpw_view1.setBackgroundColor(Color.WHITE);
        dialogUPPW_newpw_input.setText("");
        dialogUPPW_newpw_input.setEnabled(true);
        dialogUPPW_newpw_view2.setBackgroundColor(Color.WHITE);
        dialogUPPW_newpw_check.setText("");
        dialogUPPW_newpw_check.setEnabled(true);
        dialogUPPW_newpw_view1.setVisibility(View.GONE);
        dialogUPPW_newpw_view2.setVisibility(View.GONE);
    }

    private void initUPPTDialog() {
        dialogUPPT_state_view.setText("이전 패턴을 입력하세요.");
        dialogUPPT_state_view.setBackgroundColor(Color.WHITE);
        dialogUPPT_patternview.enableInput();
        dialogUPPT_patternview.clearPattern();
        dialogUPPT_btn_update.setEnabled(false);
        update_phase = 0;
        update_pattern = "";
    }
}
