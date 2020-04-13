package smarttraffic.smartmoving.Receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import smarttraffic.smartmoving.services.GeofenceTransitionsJobIntentService;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Enqueues a JobIntentService passing the context and intent as parameters
        GeofenceTransitionsJobIntentService.enqueueWork(context, intent);
    }
}

