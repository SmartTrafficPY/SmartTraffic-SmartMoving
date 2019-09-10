package smarttraffic.smartmoving.activities;


import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import smarttraffic.smartmoving.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.emaillogin)
    EditText etEmail;
    @BindView(R.id.passwlogin)
    EditText etPassword;
    @BindView(R.id.linkSignUp)
    TextView tvRegister;
    @BindView(R.id.forgotPassword)
    TextView tvForgotpassw;
    @BindView(R.id.btnlogin)
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        /*etEmail = (EditText) findViewById(R.id.emaillogin);
        etPassword = (EditText) findViewById(R.id.passwlogin);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        tvRegister = (TextView) findViewById(R.id.linkSignUp);
        tvForgotpassw = (TextView) findViewById(R.id.forgotPassword);*/

        tvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i;
                i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

            }
        });

        /*tvForgotpassw.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
                Intent i;
                i = new Intent(LoginActivity.this, RecoverPassActivity.class);
            startActivity(i);
        });*/

    }
}
