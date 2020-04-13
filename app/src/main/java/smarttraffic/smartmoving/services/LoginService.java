package smarttraffic.smartmoving.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import smarttraffic.smartmoving.Constants;
import smarttraffic.smartmoving.Interceptors.AddSMovingTokenInterceptor;
import smarttraffic.smartmoving.Receivers.LoginReceiver;
import smarttraffic.smartmoving.SmartMovingAPI;
import smarttraffic.smartmoving.dataModels.Credentials;
import smarttraffic.smartmoving.dataModels.UserToken;

public class LoginService extends IntentService {

    public static final String PROBLEM = "Ha fallado el proceso de ingreso!";
    public static final String CANNOT_LOGIN = "Revise email y contraseña";
    public static final String CANNOT_CONNECT_SERVER = "Revisar conexion!";
    public static final String ULI = "User Login Information";
    public static final String IULI = "IDENTIFICADOR USUARIO LOGGED IN";

    public LoginService(){ super("LoginService");}

    public static final String LOGIN_ACTION = "Login exitoso!";
    public static final String BAD_LOGIN_ACTION = "Credenciales incorrectas";
    public static final String SERVER_PROBLEM = "Existe un error con la comunicacion con el servidor!";

    @Override
    protected void onHandleIntent(Intent intent){
        Credentials userCredentials = new Credentials();
        userCredentials.setUsername(intent.getStringExtra("username"));
        userCredentials.setPassword(intent.getStringExtra("password"));
        Gson gson = new GsonBuilder().setLenient().create();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new AddSMovingTokenInterceptor())
                .build();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.CLIENTE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SmartMovingAPI smartMovingAPI = retrofit.create(SmartMovingAPI.class);
        Call<UserToken> call = smartMovingAPI.getUserToken(userCredentials);
        Intent loginIntent = new Intent("loginIntent");
        loginIntent.setClass(this, LoginReceiver.class);

        try{
            Response<UserToken> result = call.execute();
            if (result.code() == 200){
                loginIntent.setAction(LOGIN_ACTION);
                editor.putString(Constants.USER_TOKEN, result.body().getToken()).apply();
                editor.putString(Constants.USER_PASSWORD,
                        intent.getStringExtra("password")).apply();
                editor.putInt(Constants.USER_ID, result.body().getIdFromUrl()).apply();
                editor.apply();
            }else if (result.code() == 400){
                ResponseBody error = result.errorBody();
                loginIntent.putExtra(PROBLEM, "No se puede iniciar sesión " +
                        "con las credenciales proporcionadas");
                loginIntent.setAction(BAD_LOGIN_ACTION);
            }
            else {
                loginIntent.putExtra(PROBLEM, result.errorBody().string());
                loginIntent.setAction(SERVER_PROBLEM);
            }
        } catch (IOException e) {
            loginIntent.putExtra(PROBLEM, CANNOT_CONNECT_SERVER);
            e.printStackTrace();
            loginIntent.setAction(BAD_LOGIN_ACTION);
            e.printStackTrace();
        }
        sendBroadcast(loginIntent);
    }

    private int getIdFromUrl(String url){
        String[] parts= url.split("/");
        return Integer.parseInt(parts[parts.length - 1]);

    }


}