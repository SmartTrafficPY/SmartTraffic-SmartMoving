package smarttraffic.smartmoving;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;



import org.osmdroid.util.GeoPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smarttraffic.smartmoving.dataModels.Reports.ReportPoi;


public class Utils {


    public static void ReportsPoiSharedPreferences(Context context, List<ReportPoi> reportspoi) {
        SharedPreferences prefs = context.getSharedPreferences("REPORTS SYSTEM",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for(ReportPoi reportPoi : reportspoi){
            editor.putInt(String.valueOf(reportPoi.getProperties().getIdFromUrl()),reportPoi.getProperties().getIdFromUrl());
            editor.apply();
        }
    }


    public static Intent getTwitterIntent(Context ctx, String shareText, GeoPoint positionMarker)
    {
        Intent shareIntent;
        Double gp = positionMarker.getLatitude();
        Double gpp = positionMarker.getLongitude();
        String url1 = "https://maps.google.com/?q="+gp+","+gpp;
        String url = "https://www.openstreetmap.org/search?whereami=1&query="+gp+"%2C"+gpp+"#map=19/"+gp+"/"+gpp;
            String tweetUrl = "https://twitter.com/intent/tweet?text=" + shareText+ "  "+ url1;
            Uri uri = Uri.parse(tweetUrl);
            shareIntent = new Intent(Intent.ACTION_VIEW, uri);
            return shareIntent;
    }



    public static void setTileServerCredentials(Context context){
        final String basic =
                "Basic " + Base64.encodeToString(SmovingData.getCredentials().getBytes(), Base64.NO_WRAP);
        final Map<String, String> AuthHeader = new HashMap<>();
        AuthHeader.put("Authorization", basic);
        SharedPreferences preferencesManager = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferencesManager.edit();
        for (final Map.Entry<String, String> entry : AuthHeader.entrySet()) {
            final String key = "osmdroid.additionalHttpRequestProperty." + entry.getKey();
            editor.putString(key, entry.getValue()).apply();
        }
        editor.commit();
    }


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
