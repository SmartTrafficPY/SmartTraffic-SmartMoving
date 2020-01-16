package smarttraffic.smartmoving.activities;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import smarttraffic.smartmoving.Constants;
import smarttraffic.smartmoving.R;
import smarttraffic.smartmoving.Interceptors.TokenUserInterceptor;
import smarttraffic.smartmoving.SmartMovingAPI;
import smarttraffic.smartmoving.dataModels.ProfileUser;


public class ChangePasswActivity extends AppCompatActivity {

    private static final String PASSWORDS_NOT_MATCH = "Las contraseñas no coinciden!";
    private static final String CHANGE_SUCCESS = "Nueva constraseña";
    private static final String CHANGE_NOT_SUCCESS = "La contraseña no es correcta";
    private static final String SERVER_MISTAQUE = "Sin cambios en la contraseña";
    private static final String LOG_TAG = "ChangePasswordActivity";

    @BindView(R.id.changePasswordButton)
    Button changePassBtn;
    @BindView(R.id.currentPassword)
    EditText oldPassw;
    @BindView(R.id.newPassword1)
    EditText newPassw;
    @BindView(R.id.newPassword2)
    EditText newPass1;
    @BindView(R.id.passwordNotMatch)
    TextView noMatchPassw;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassw_layout);
        ButterKnife.bind(this);
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.CLIENTE_DATA, Context.MODE_PRIVATE);
        final String userPassw = sharedPreferences.getString(Constants.USER_PASSWORD, "");

        newPassw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!newPassw.getText().toString().equals(newPass1.getText().toString())) {
                    noMatchPassw.setVisibility(View.VISIBLE);
                } else {
                    noMatchPassw.setVisibility(View.INVISIBLE);
                }
            }
        });

        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oldPassw.getText().toString().equals(userPassw)) {
                    changeProfileUser(sharedPreferences);
                } else {
                    showToast(PASSWORDS_NOT_MATCH);
                }
            }
        });
    }
        private void changeProfileUser(SharedPreferences sharedPreferences) {
            Password password = new Password();
            password.setNewPassword(newPassw.getText().toString());
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new TokenUserInterceptor(this))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.BASE_URL_HOME)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            int userId = sharedPreferences.getInt(Constants.USER_ID, -1);

            SmartMovingAPI smartMovingAPI = retrofit.create(SmartMovingAPI.class);
            Call<ProfileUser> call = smartMovingAPI.updateUserProfile(userId, password);

            call.enqueue(new Callback<ProfileUser>() {
                @Override
                public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                    switch (response.code()) {
                        case 200:
                            showToast(CHANGE_SUCCESS);
                            Intent intent = new Intent(ChangePasswActivity.this,
                                    HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            break;
                        case 400:
                            showToast(CHANGE_NOT_SUCCESS);
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ProfileUser> call, Throwable t) {
                    t.printStackTrace();
                    showToast(SERVER_MISTAQUE);

                }
            });
        }
            public class Password {
                private String password;

                public String getNewPassword() {
                    return password;
                }

                public void setNewPassword(String newPassword) {
                    this.password = newPassword;
                }
            }
            @Override
            public void onStart() {
                super.onStart();
            }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.smicono);
        toastContentView.addView(imageView, 0);
        toast.show();
    }
}
