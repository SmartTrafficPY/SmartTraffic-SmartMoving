package smarttraffic.smartmoving;

public class Constants {


    public Constants(){
    }

    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String CLIENT_NOT_LOGIN = "CLIENT_NOT_LOGIN";
    public static final String CLIENTE_DATA = "CLIENTE_DATA";
    public static final String REPORTS_POI ="REPORTS_POI";
    public static final String USER_ID = "USER_ID";
    public static final String KEY_DETECTED_ACTIVITIES = "KEY_DETECTED_ACTIVITIES";

    private static final long SECONDS_IN_MILLISECONDS = 1000 * 1;
    private static final long MINUTES_IN_MILLISECONDS = SECONDS_IN_MILLISECONDS * 60;
    private static final long HOURS_IN_MILLISECONDS = MINUTES_IN_MILLISECONDS * 60;
    public static final String BASE_URL_HOME = "http://192.168.1.253:8000/api/";
    public static final String BASE_URL = "https://api.smarttraffic.com.py/api/";
    public static final String TILE_SERVER = "https://api.smarttraffic.com.py/tile/";
    private static final String CHANNEL_ID = "SMARTMOVING_CHANNEL_ID";
    private static final String NOTIFICATION_SERVICE = "notification";
    private static final String FROM_PROXIMITY_INTENT = "FROM_PROXIMITY_INTENT";
    private static final String INTENT_FROM = "INTENT_FROM";
    private static final String LATITUD = "LATITUD";
    private static final String LONGITUD = "LONGITUD";
    private static final String RADIOUS = "RADIOUS";
    private static final String BROADCAST_GEOFENCE_TRIGGER_INTENT = "BROADCAST_GEOFENCE_TRIGGER_INTENT";
    public static final String BROADCAST_TRANSITION_ACTIVITY_INTENT = "BROADCAST_TRANSITION_ACTIVITY_INTENT";
    public static final String GEOFENCE_TRIGGER_ID = "geofenceTriggerId";
    private static final String BROADCAST_LOCATION_INTENT = "BROADCAST_LOCATION_INTENT";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    public static String getBroadcastLocationIntent() {
        return BROADCAST_LOCATION_INTENT;
    }

    public static String getBroadcastGeofenceTriggerIntent() {
        return BROADCAST_GEOFENCE_TRIGGER_INTENT;
    }

    public static String getBroadcastTransitionActivityIntent() {
        return BROADCAST_TRANSITION_ACTIVITY_INTENT;
    }

    public static long getSecondsInMilliseconds() {
        return SECONDS_IN_MILLISECONDS;
    }

    public static long getMinutesInMilliseconds() {
        return MINUTES_IN_MILLISECONDS;
    }

    public static long getHoursInMilliseconds() {
        return HOURS_IN_MILLISECONDS;
    }

    public static int getRequestPermissionsRequestCode() {
        return REQUEST_PERMISSIONS_REQUEST_CODE;
    }

    public static String getGeofenceTriggerId() {
        return GEOFENCE_TRIGGER_ID;
    }

    public static String getUserPassword() { return USER_PASSWORD; }

    public static String getBaseUrl() { return BASE_URL; }

    public static String getBaseUrlHome() { return BASE_URL_HOME; }

    public static String getChannelId() { return CHANNEL_ID; }

    public static String getTileServer() { return TILE_SERVER;    }

    public static String getUserId() { return USER_ID; }

    public static String getFromProximityIntent() { return FROM_PROXIMITY_INTENT; }

    public static String getIntentFrom() { return INTENT_FROM; }

    public static String getLATITUD() { return LATITUD; }

    public static String getLONGITUD() { return LONGITUD; }

    public static String getRADIOUS() {  return RADIOUS;  }

    public static String getNotificationService() {
        return NOTIFICATION_SERVICE;
    }

}
