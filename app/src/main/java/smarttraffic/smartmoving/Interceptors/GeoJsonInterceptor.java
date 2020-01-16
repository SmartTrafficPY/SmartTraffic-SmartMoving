package smarttraffic.smartmoving.Interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class GeoJsonInterceptor implements Interceptor {

    public GeoJsonInterceptor(){
        // Persistence Constructor
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest  = chain.request().newBuilder()
                .addHeader("Accept", "application/vnd.geo+json")
                .build();
        return chain.proceed(newRequest);
    }

}
