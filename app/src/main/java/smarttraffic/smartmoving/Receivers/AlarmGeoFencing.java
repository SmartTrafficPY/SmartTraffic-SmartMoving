package smarttraffic.smartmoving.Receivers;


import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;


import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import smarttraffic.smartmoving.Constants;
import smarttraffic.smartmoving.Utils;

public class AlarmGeoFencing extends BroadcastReceiver {

    private GeofencingClient geofencingClient;
    private PendingIntent mGeofencePendingIntent;


    @Override
    public void onReceive(Context context, Intent intent) {
        geofencingClient = LocationServices.getGeofencingClient(context);

    }

    private Geofence generateGeofence(double latitude, double longitud, float radius, String nameId,
                                      boolean isSpotGeofence) {
        Geofence.Builder builder = new Geofence.Builder()
                .setRequestId(nameId)
                .setCircularRegion(
                        latitude,
                        longitud,
                        radius
                )
                .setLoiteringDelay(1000 * 60 * 10)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT |
                        Geofence.GEOFENCE_TRANSITION_DWELL)
        .setExpirationDuration(Constants.getHoursInMilliseconds() * 24);


        Geofence geofence = builder.build();
        return geofence;
    }

    @SuppressWarnings("MissingPermission")
    private void addGeofences(Context context, ArrayList<Geofence> geofenceArrayList) {
        if (!checkPermissions(context)) {
            return;
        }
        geofencingClient.addGeofences(getGeofencingRequest(geofenceArrayList), getGeofencePendingIntent(context));
        Utils.geofencesSetUp(context,true);
    }

    private boolean checkPermissions(Context context) {
        return  PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private PendingIntent getGeofencePendingIntent(Context context) {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(context, GeofenceBroadcastReceiver.class);

        mGeofencePendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }

    private GeofencingRequest getGeofencingRequest(ArrayList<Geofence> geofenceList) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

}
