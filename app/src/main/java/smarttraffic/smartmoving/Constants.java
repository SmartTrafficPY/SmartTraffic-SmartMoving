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
    public static final String SETTINGS = "SETTINGS";
    public static final int ADD_ALARM_REQUEST_CODE=130;
    public static final int REMOVE_ALARM_REQUEST_CODE=131;
    public static final String ARRIVED = "ARRIVED";
    public static final String  LOCATION_TIME_UPDATE_SETTINGS = "LOCATION_TIME_UPDATE_SETTINGS";
    public static final String LOCATIONS_REQUEST_SETTINGS_CHANGE = "LOCATIONS_REQUEST_SETTINGS_CHANGE";
    public static final String KEY_DETECTED_ACTIVITIES = "KEY_DETECTED_ACTIVITIES";
    public  static final String REPORT_SERVERE = "REPORT_SEVERE";
    public static final String GEOFENCE_TRIGGED = "GEOFENCE_TRIGGED";
    public  static final String REPORT_LIGHT = "REPORT_LIGHT";
    public static final String GEOFENCES_TRIGGER = "GEOFENCES_TRIGGER";
    public static final String DESTINATION = "DESTINATION";

    private static final long SECONDS_IN_MILLISECONDS = 1000 * 1;
    private static final long MINUTES_IN_MILLISECONDS = SECONDS_IN_MILLISECONDS * 25;
    private static final long HOURS_IN_MILLISECONDS = MINUTES_IN_MILLISECONDS * 60;
    public static final String BASE_URL_HOME = "http://192.168.43.155:8000/api/";
    public static final String BASE_URL = "https://api.smarttraffic.com.py/api/";
    public static final String TILE_SERVER = "https://api.smarttraffic.com.py/tile/";
    public static final String GEOFENCES_SETUP = "GEOFENCES_SETUP";
    private static final String BROADCAST_GEOFENCE_TRIGGER_INTENT = "BROADCAST_GEOFENCE_TRIGGER_INTENT";
    public static final String BROADCAST_TRANSITION_ACTIVITY_INTENT = "BROADCAST_TRANSITION_ACTIVITY_INTENT";
    public static final String GEOFENCE_TRIGGER_ID = "geofenceTriggerId";
    public static final String GEOFENCES_ADD = "GEOFENCES_ADD";
    public static final long DETECTION_INTERVAL_IN_MILLISECONDS =  SECONDS_IN_MILLISECONDS;
    private static final String BROADCAST_LOCATION_INTENT = "BROADCAST_LOCATION_INTENT";
    public static final String HAS_ARRIVED = "HAS_ARRIVED";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public static final String KEY_ACTIVITY_UPDATES_REQUESTED = "KEY_ACTIVITY_UPDATES_REQUESTED";
    public static final String ENTER_DEST_FLAG = "ENTER_DEST_FLAG";
    public static final String ACTIVITY_TYPE_TRANSITION = "ACTIVITY_TYPE_TRANSITION";
    public static final String ACTIVITY_CONFIDENCE_TRANSITION = "ACTIVITY_CONFIDENCE_TRANSITION";

    public static String getBroadcastGeofenceTriggerIntent() {
        return BROADCAST_GEOFENCE_TRIGGER_INTENT;
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


}
