package smarttraffic.smartmoving;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import smarttraffic.smartmoving.activities.ChangePasswActivity;
import smarttraffic.smartmoving.dataModels.Credentials;
import smarttraffic.smartmoving.dataModels.ProfileRegistry;
import smarttraffic.smartmoving.dataModels.ProfileUser;
import smarttraffic.smartmoving.dataModels.UserToken;

public interface SmartMovingAPI {

    @POST("smartmoving/auth-token/")
    Call<UserToken> getUserToken(@Body Credentials userCredentials);

    @POST("smartmoving/users/")
    Call<ProfileUser> signUpUser(@Body ProfileRegistry profileRegistry);

    @PATCH("smartmoving/users/{identifier}/")
    Call<ProfileUser> updateUserProfile(@Path("identifier") Integer userId,
                                        @Body ChangePasswActivity.Password newProfile);


}
