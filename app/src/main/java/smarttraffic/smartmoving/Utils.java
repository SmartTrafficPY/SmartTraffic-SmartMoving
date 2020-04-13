package smarttraffic.smartmoving;

import android.app.AlarmManager;
import android.app.PendingIntent;
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


import com.google.android.gms.location.DetectedActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.osmdroid.util.GeoPoint;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import smarttraffic.smartmoving.Receivers.AlarmGeoFencing;
import smarttraffic.smartmoving.dataModels.Reports.ReportPoi;


import static android.content.Context.ALARM_SERVICE;


public class Utils {

    public static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_location_updates";

    public static boolean returnEnterLotFlag(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.ENTER_DEST_FLAG,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.HAS_ARRIVED, false);
    }

    public static void settingsHasChanged(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SETTINGS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.LOCATIONS_REQUEST_SETTINGS_CHANGE, true).apply();
        editor.commit();
    }

    public static Calendar timeToRemoveGeofences(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        return calendar;
    }
    public static void hasArrivedFlag(Context context, boolean flag){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.ENTER_DEST_FLAG,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.HAS_ARRIVED, flag).apply();
        editor.commit();
    }

    public static void addAlarmsGeofencingTask(Context context){
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent removeAlarmIntent = new Intent(context, AlarmGeoFencing.class);
        PendingIntent removeAlarmPendingIntent = PendingIntent.getBroadcast(context,
                Constants.REMOVE_ALARM_REQUEST_CODE, removeAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC, timeToRemoveGeofences().getTimeInMillis(),
                AlarmManager.INTERVAL_DAY * 7, removeAlarmPendingIntent);
    }

    public static void saveGeofencesTrigger(Context context, ArrayList<String> namesOfGeofencesTrigger) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.GEOFENCES_TRIGGER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<String>(namesOfGeofencesTrigger);
        editor.putStringSet(Constants.GEOFENCES_TRIGGER, set).apply();
        editor.commit();
    }
    public static ArrayList<String> getGeofencesTrigger(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.GEOFENCES_TRIGGER, Context.MODE_PRIVATE);
        ArrayList<String> list = new ArrayList<String>(sharedPreferences.getStringSet(
                Constants.GEOFENCES_TRIGGER, new HashSet<String>()));
        return list;
    }
    public static String detectedActivitiesToJson(ArrayList<DetectedActivity> detectedActivitiesList) {
        Type type = new TypeToken<ArrayList<DetectedActivity>>() {}.getType();
        return new Gson().toJson(detectedActivitiesList, type);
    }


    public static void ReportsPoiSharedPreferences(Context context, List<ReportPoi> reportspoi) {
        SharedPreferences prefs = context.getSharedPreferences("REPORTS SYSTEM",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for(ReportPoi reportPoi : reportspoi){
            editor.putInt(String.valueOf(reportPoi.getProperties().getIdFromUrl()),reportPoi.getProperties().getIdFromUrl());
            editor.apply();
        }
    }
    public static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
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

    public static void geofencesSetUp(Context context, Boolean added){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.GEOFENCES_SETUP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.GEOFENCES_ADD, added).apply();
        editor.commit();
    }

    public static boolean getGeofenceStatus(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.GEOFENCES_SETUP, Context.MODE_PRIVATE);
        boolean isGeofenceAddes = sharedPreferences.getBoolean(
                Constants.GEOFENCES_ADD, false);
        return  isGeofenceAddes;
    }


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
