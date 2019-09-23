package smarttraffic.smartmoving.Interceptors;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import smarttraffic.smartmoving.Constants;

public class TokenUserInterceptor implements Interceptor {

    private Context context;

    public TokenUserInterceptor(Context context){this.context=context; }

    @Override
    public Response intercept(Chain chain) throws IOException{
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.CLIENTE_DATA, Context.MODE_PRIVATE);

        String tokenUsr = sharedPreferences.getString(Constants.USER_TOKEN, Constants.CLIENT_NOT_LOGIN);

        Request newReq = chain.request().newBuilder().addHeader("Authorization", "Token " + tokenUsr).build();

        return chain.proceed(newReq);
    }

}
