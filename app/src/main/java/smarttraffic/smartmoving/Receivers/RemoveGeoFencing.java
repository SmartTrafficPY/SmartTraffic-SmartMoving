package smarttraffic.smartmoving.Receivers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;

import smarttraffic.smartmoving.Utils;

public class RemoveGeoFencing extends BroadcastReceiver {

    private GeofencingClient geofencingClient;
    private PendingIntent mGeofencePendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        geofencingClient = LocationServices.getGeofencingClient(context);
        geofencingClient.removeGeofences(getGeofencePendingIntent(context));
        Utils.geofencesSetUp(context,false);
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
}
