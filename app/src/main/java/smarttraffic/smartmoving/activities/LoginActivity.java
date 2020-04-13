package smarttraffic.smartmoving.activities;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.wang.avi.AVLoadingIndicatorView;

import smarttraffic.smartmoving.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import smarttraffic.smartmoving.Receivers.LoginReceiver;
import smarttraffic.smartmoving.services.LoginService;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText etUsername;
    @BindView(R.id.passwlogin)
    EditText etPassword;
    @BindView(R.id.linkSignUp)
    TextView tvRegister;
    @BindView(R.id.btnlogin)
    Button btnLogin;

    private static final String LOG_TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCredentialsInput()) {
                    makeLoginHappen();
                }
            }
        });


        IntentFilter filter = new IntentFilter();
        filter.addAction(LoginService.LOGIN_ACTION);
        filter.addAction(LoginService.BAD_LOGIN_ACTION);
        LoginReceiver loginReceiver = new LoginReceiver();
        registerReceiver(loginReceiver, filter);

        Intent intent = getIntent();
        String statusRegistry = intent.getStringExtra("status_registro");
        if (statusRegistry != null) {
            showToast(statusRegistry);
        }

    }
    private void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.alerticon);
        toastContentView.addView(imageView, 0);
        toast.show();
    }

    private void makeLoginHappen() {


        btnLogin.setEnabled(false);
        AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(LoginActivity.this);
        avLoadingIndicatorView.setIndicator("BallSpinFadeLoader");
        avLoadingIndicatorView.setIndicatorColor(R.color.white);

        sendLoginRequest();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        /**Here the service get the request of Login...**/
                        btnLogin.setEnabled(true);

                    }
                }, 2000);
        //eraseCredentials();
    }

    //private void eraseCredentials() {
    //    etUsername.setText("");
    //   etPassword.setText("");
    //}

    private void sendLoginRequest() {
        Intent loginIntent = new Intent(LoginActivity.this, LoginService.class);
        loginIntent.putExtra("username", etUsername.getText().toString());
        loginIntent.putExtra("password", etPassword.getText().toString());
        startService(loginIntent);
    }

    @Override
    public void onBackPressed() {
        // disable going back...
        moveTaskToBack(true);
    }

    private boolean checkCredentialsInput(){
        if(etUsername.getText().toString() != null){
            return true;
        }else{
            showToast("no puede estar vacio!");
            return false;
        }
    }
}