package smarttraffic.smartmoving;

public class Constants {


    public Constants(){
    }

    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String CLIENT_NOT_LOGIN = "CLIENT_NOT_LOGIN";
    public static final String CLIENTE_DATA = "CLIENTE_DATA";
    public static final String USER_ID = "USER_ID";
    public static final String KEY_DETECTED_ACTIVITIES = "KEY_DETECTED_ACTIVITIES";

    private static final long SECONDS_IN_MILLISECONDS = 1000 * 1;
    private static final long MINUTES_IN_MILLISECONDS = SECONDS_IN_MILLISECONDS * 60;
    private static final long HOURS_IN_MILLISECONDS = MINUTES_IN_MILLISECONDS * 60;
    public static final String BASE_URL = "http://10.20.2.232:8000/";
    private static final String BASE_URL_HOME = "http://192.168.0.10:8000/";
    private static final String CHANNEL_ID = "SMARTMOVING_CHANNEL_ID";
    private static final String NOTIFICATION_SERVICE = "notification";
    private static final String FROM_PROXIMITY_INTENT = "FROM_PROXIMITY_INTENT";
    private static final String INTENT_FROM = "INTENT_FROM";
    private static final String LATITUD = "LATITUD";
    private static final String LONGITUD = "LONGITUD";
    private static final String RADIOUS = "RADIOUS";

    private static final String TILE_SERVER_URL_HOME = "http://192.168.100.49:80/tile/";


    public static String getUserPassword() { return USER_PASSWORD; }

    public static String getBaseUrl() { return BASE_URL; }

    public static String getBaseUrlHome() { return BASE_URL_HOME; }

    public static String getChannelId() { return CHANNEL_ID; }

    public static String getTileServerUrlHome() { return TILE_SERVER_URL_HOME; }

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
