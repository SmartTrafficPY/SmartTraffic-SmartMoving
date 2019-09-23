package smarttraffic.smartmoving.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import smarttraffic.smartmoving.Constants;
import smarttraffic.smartmoving.Interceptors.AddSMovingTokenInterceptor;
import smarttraffic.smartmoving.Receivers.RegistrationReceiver;
import smarttraffic.smartmoving.SmartMovingAPI;
import smarttraffic.smartmoving.dataModels.ProfileRegistry;
import smarttraffic.smartmoving.dataModels.ProfileUser;
import smarttraffic.smartmoving.dataModels.SmartMovingProfile;


public class RegistrationService extends IntentService {


    private static final String CANNOT_CONNECT_SERVER = "favor revisar conexion!";
    public static final String PROBLEM = "No se pudo realizar la peticion";
    public static final String REGISTRATION_OK = "Registro terminado";
    public static final String BAD_REGISTRATION = "Registro no realizado";

    public RegistrationService() {super("RegistrationService");}

    @Override
    protected void onHandleIntent(Intent intent){
        ProfileRegistry profileRegistry = new ProfileRegistry();
        profileRegistry.setSmartMovingProfile(new SmartMovingProfile());
        Bundle extras = intent.getExtras();
        setRegistrationExtras(extras, profileRegistry);

        Gson gson= new GsonBuilder()
                .setLenient()
                .create();
        final OkHttpClient okHttClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new AddSMovingTokenInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()

                .client(okHttClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SmartMovingAPI smartMovingAPI = retrofit.create(SmartMovingAPI.class);
        Call<ProfileUser> call = smartMovingAPI.signUpUser(profileRegistry);
        Intent registrationIntent = new Intent("registrationIntent");
        registrationIntent.setClass(this, RegistrationReceiver.class);

        try {
            Response<ProfileUser> result = call.execute();
            Headers headers = ((Response) result).headers();
            if(result.code() == 201){
                registrationIntent.setAction(REGISTRATION_OK);
            }else{
                registrationIntent.putExtra("exist", "Profile alredy exist");
                registrationIntent.setAction(BAD_REGISTRATION);
            }
        }catch (IOException e){
            registrationIntent.putExtra(PROBLEM, CANNOT_CONNECT_SERVER);
            registrationIntent.setAction(BAD_REGISTRATION);
            e.printStackTrace();
        }
        sendBroadcast(registrationIntent);
    }
    private void setRegistrationExtras(Bundle extras, ProfileRegistry profileRegistry){
        profileRegistry.setUsername(extras.getString("username"));
        profileRegistry.setPassword(extras.getString("password"));
        profileRegistry.getSmartMovingProfile().setBirth_date(extras.getString("birth_date"));
        profileRegistry.getSmartMovingProfile().setSex(extras.getString("sex"));
        profileRegistry.getSmartMovingProfile().setTypemovement(extras.getInt("type_movement"));
    }

}
