package smarttraffic.smartmoving.activities;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.wang.avi.AVLoadingIndicatorView;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import butterknife.ButterKnife;
import smarttraffic.smartmoving.Constants;
import smarttraffic.smartmoving.R;


public class MainActivity extends AppCompatActivity {

    private boolean withInternetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.CLIENTE_DATA, Context.MODE_PRIVATE);
        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        AVLoadingIndicatorView avLoadingIndicatorView =new AVLoadingIndicatorView(MainActivity.this);
        avLoadingIndicatorView.setIndicator("BallSpinFadeLoader");
        avLoadingIndicatorView.setIndicatorColor(R.color.blue);
        final AVLoadingIndicatorView avi = (AVLoadingIndicatorView) findViewById(R.id.avi);


        avi.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(isNetworkAvailable()){
                            setWithInternetConnection(true);
                            initializeFirstActivity(sharedPreferences);
                        }else{
                            setWithInternetConnection(false);
                            showToast("Sin conexion");
                        }

                        avi.hide();
                    }
                }, 2000);
    }
    private boolean isNetworkAvailable(){
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;
    }
    public void setWithInternetConnection(boolean withInternetConnection){
        this.withInternetConnection = withInternetConnection;
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    private void initializeFirstActivity(SharedPreferences sharedPreferences){
        String userToken = sharedPreferences.getString(Constants.USER_TOKEN, Constants.CLIENT_NOT_LOGIN);
        if(userToken.equals(Constants.CLIENT_NOT_LOGIN)){
            Intent registration = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(registration);
        }else{
            Intent registration = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(registration);
        }
        finish();
    }
    private void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.smicono);
        toastContentView.addView(imageView, 0);
        toast.show();
    }

}
