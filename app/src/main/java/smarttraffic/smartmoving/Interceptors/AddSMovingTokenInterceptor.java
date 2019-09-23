package smarttraffic.smartmoving.Interceptors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import smarttraffic.smartmoving.SMovingInitialToken;

import java.io.IOException;
public class AddSMovingTokenInterceptor implements Interceptor {

    public AddSMovingTokenInterceptor(){
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Token "
                + SMovingInitialToken.getToken())
                .build();
        return chain.proceed(newRequest);
    }

}
