package smarttraffic.smartmoving;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import smarttraffic.smartmoving.activities.ChangePasswActivity;
import smarttraffic.smartmoving.dataModels.Credentials;
import smarttraffic.smartmoving.dataModels.ProfileRegistry;
import smarttraffic.smartmoving.dataModels.ProfileUser;
import smarttraffic.smartmoving.dataModels.Reports.CreateContributionPoi;
import smarttraffic.smartmoving.dataModels.Reports.Properties;
import smarttraffic.smartmoving.dataModels.Reports.ReportPoi;
import smarttraffic.smartmoving.dataModels.Reports.ReportsList;
import smarttraffic.smartmoving.dataModels.UserToken;
import smarttraffic.smartmoving.dataModels.Events;
import smarttraffic.smartmoving.dataModels.navigations.NavigationRequest;

public interface SmartMovingAPI {

    @POST("smartmoving/auth-token/")
    Call<UserToken> getUserToken(@Body Credentials userCredentials);

    @POST("smartmoving/users/")
    Call<ProfileUser> signUpUser(@Body ProfileRegistry profileRegistry);

    @PATCH("smartmoving/users/{identifier}/")
    Call<ProfileUser> updateUserProfile(@Path("identifier") Integer userId,
                                        @Body ChangePasswActivity.Password newProfile);
    @POST("events/")
    Call<ResponseBody> setUserEvent(@Header("Content-Type") String content_type, @Body Events event);

    @POST("smartmoving/reports/")
    Call<ReportPoi> setReportPoi(@Header("Content-Type") String content_type,
                                 @Body Properties createReportPoi);

    @GET("smartmoving/reports/")
    Call<ReportsList> getReportsPoi();

    @GET("smartmoving/reports/{identifier}/")
    Call<ReportPoi> getIndividualReport(@Path ("identifier") Integer reportid);

    @POST("smartmoving/statusupdates/")
    Call<CreateContributionPoi> setContributionReport(@Body CreateContributionPoi createContribution);

    @POST("smartmoving/navigationrequests/")
    Call<NavigationRequest> setNavigationRequest(@Body NavigationRequest createNavigation);



}
