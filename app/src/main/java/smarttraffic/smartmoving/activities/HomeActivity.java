package smarttraffic.smartmoving.activities;



import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;

import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.TilesOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import smarttraffic.smartmoving.Constants;
import smarttraffic.smartmoving.R;

import static org.osmdroid.tileprovider.util.StorageUtils.getStorage;

public class HomeActivity extends AppCompatActivity {


    private SettingsClient mSettingsClient;
    private ActionMode myActionMode;
    boolean userNotResponse = true;
    boolean dialogSendAllready = false;
    private LocationCallback mLocationCallback;
    public static String PERMISSION = "permitir ubicacion";
    private static final String LOG_TAG ="HomeActivity";
    Location gpsLoc = new Location(LocationManager.GPS_PROVIDER);
    private Marker userMarker;
    private CompassOverlay mCompassOverlay;

   // @BindView(R.id.btnreport)
   // Button btnReport;
    @BindView(R.id.mapview)
    MapView mMapView;
    @BindView(R.id.curbramppoi)
    ImageButton rampBtnPoi;
    @BindView(R.id.bustoppoi)
    ImageButton busstoBtnPoi;
    @BindView(R.id.carpoi)
    ImageButton carBtnPoi;
    @BindView(R.id.sidewalkpoi)
    ImageButton sidewalkBtnPoi;
    @BindView(R.id.obstaclepoi)
    ImageButton obstacleBtnPoi;
    @BindView(R.id.pendientepoi)
    ImageButton pendienteBtnPoi;
    @BindView(R.id.streetpoi)
    ImageButton streetBtnPoi;
    @BindView(R.id.noramppoi)
    ImageButton noramppoi;
    @BindView(R.id.nosidewalkpoi)
    ImageButton nosidewalkpoi;
    @BindView(R.id.stairpoi)
    ImageButton stairpoi;
    @BindView(R.id.confirmreport)
    ImageButton confirmReport;
    @BindView(R.id.deletereport)
    ImageButton deleteReport;


    //private MapView mMapView;
    private ActionBarDrawerToggle mToggle;
    private ScaleBarOverlay mScaleBarOverlay;
    private RotationGestureOverlay mRotationGestureOverlay;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Location mCurrentLocation;
    BroadcastReceiver broadcastReceiver;
    BroadcastReceiver geofenceReceiver;
    private MyLocationNewOverlay mLocationOverlay;
    private GeofencingClient geofencingClient;
    private PendingIntent mGeofencePendingIntent;
    private FusedLocationProviderClient mFusedLocationClient;
    private ActivityRecognitionClient mActivityRecognitionClient;
    NavigationView navigationView;
    ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
    Toolbar toolbar;
    Marker startMarker = null;
    HorizontalScrollView scrollView=null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup base map
        setContentView(R.layout.home_layout);
        ButterKnife.bind(this);

        //navigationView = (NavigationView) findViewById(R.id.navviewabout);
        //noinspection ConstantConditions
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        IConfigurationProvider osmConf = Configuration.getInstance();
        File basePath = new File(getCacheDir().getAbsolutePath(), "osmdroid");
        osmConf.setOsmdroidBasePath(basePath);
        File tileCache = new File(osmConf.getOsmdroidBasePath().getAbsolutePath(), "tile");
        osmConf.setOsmdroidTileCache(tileCache);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


//       toolbar.setNavigationIcon(R.drawable.menubtn);
//       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               if (mDrawerLayout.isDrawerOpen (Gravity.LEFT)) {
//                   mDrawerLayout.closeDrawer (Gravity.START);
//               } else {
//                   mDrawerLayout.openDrawer (Gravity.LEFT);
//               }
//           }
//       });

//        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                toolbar,R.string.open,
//                R.string.close);
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mMapView.setTilesScaledToDpi(true);
        mMapView.getZoomController().setVisibility(
                CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);

        //Copyright overlay
        String copyrightNotice = mMapView.getTileProvider().getTileSource().getCopyrightNotice();
        CopyrightOverlay copyrightOverlay = new CopyrightOverlay(this);
        copyrightOverlay.setCopyrightNotice(copyrightNotice);
        mMapView.getOverlays().add(copyrightOverlay);
        setSupportActionBar(toolbar);
        //invalidateOptionsMenu();
        mMapView.getController().setZoom(19);
        mMapView.getController().setCenter(new GeoPoint(-25.2890,-57.6453));
        mMapView.setTilesScaledToDpi(true);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapView.setFlingEnabled(true);


        // Add tiles layer
        MapTileProviderBasic provider = new MapTileProviderBasic(getApplicationContext());
        provider.setTileSource(TileSourceFactory.MAPNIK);

        TilesOverlay tilesOverlay = new TilesOverlay(provider, this.getBaseContext());
        mCompassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), mMapView);
        mCompassOverlay.enableCompass();
        mRotationGestureOverlay = new RotationGestureOverlay(this, mMapView);
        mRotationGestureOverlay.setEnabled(true);
        final MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(mMapView);
        myLocationoverlay.enableFollowLocation();
        myLocationoverlay.enableMyLocation();
        mMapView.getOverlays().add(tilesOverlay);
        mMapView.getOverlays().add(myLocationoverlay);

        scrollView = (HorizontalScrollView) findViewById(R.id.scrollPartition);

        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
            }
        }, 100L);
        deleteReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMarker();
            }
        });
        confirmReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMarker();
            }
        });
        final ImageButton.OnClickListener olongBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                scrollView.setVisibility(View.INVISIBLE);
                showToast();

                confirmReport.setVisibility(View.VISIBLE);
                deleteReport.setVisibility(View.VISIBLE);

                switch (v.getId()) {
                    case R.id.curbramppoi:
                        createMarker(myLocationoverlay, R.mipmap.wheelchairramp);
                        break;
                    case R.id.bustoppoi:
                        createMarker(myLocationoverlay, R.mipmap.accessiblebuspoi);
                        //saveInfoToMarker();
                        break;
                    case R.id.pendientepoi:
                        createMarker(myLocationoverlay, R.mipmap.pendiente);
                        break;
                    case R.id.streetpoi:
                        createMarker(myLocationoverlay, R.mipmap.uneven);
                        break;
                    case R.id.obstaclepoi:
                        createMarker(myLocationoverlay, R.mipmap.obstacle);
                        break;
                    case R.id.carpoi:
                        createMarker(myLocationoverlay, R.mipmap.carinsidewalk);
                        break;
                    case R.id.sidewalkpoi:
                        createMarker(myLocationoverlay, R.mipmap.sidewalkwarning);
                        break;
                    case R.id.stairpoi:
                        createMarker(myLocationoverlay, R.mipmap.stair1poi);
                        break;
                    case R.id.noramppoi:
                        createMarker(myLocationoverlay, R.mipmap.noramppoi);
                        break;
                    case R.id.nosidewalkpoi:
                        createMarker(myLocationoverlay, R.mipmap.nosidewalk1poi);
                        break;

                }
            }
        };
        rampBtnPoi.setClickable(true);
        rampBtnPoi.setOnClickListener(olongBtn);
        busstoBtnPoi.setOnClickListener(olongBtn);
        carBtnPoi.setOnClickListener(olongBtn);
        sidewalkBtnPoi.setOnClickListener(olongBtn);
        obstacleBtnPoi.setOnClickListener(olongBtn);
        pendienteBtnPoi.setOnClickListener(olongBtn);
        streetBtnPoi.setOnClickListener(olongBtn);
        stairpoi.setOnClickListener(olongBtn);
        nosidewalkpoi.setOnClickListener(olongBtn);
        noramppoi.setOnClickListener(olongBtn);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    private void deleteMarker(){
        startMarker.remove(mMapView);
        mMapView.getOverlays().remove(startMarker);
        deleteReport.setVisibility(View.INVISIBLE);
        confirmReport.setVisibility(View.INVISIBLE);
        showToast1();
        scrollView.setVisibility(View.VISIBLE);
        mMapView.invalidate();
    }

    private void saveMarker(){
        Toast toast = Toast.makeText(getApplicationContext(), "Creaste un Reporte!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.smile1);
        toastContentView.addView(imageView, 0);
        toast.show();
        scrollView.setVisibility(View.VISIBLE);
        startMarker.setDraggable(false);
        deleteReport.setVisibility(View.INVISIBLE);
        confirmReport.setVisibility(View.INVISIBLE);
    }
    private void showToast() {
        Toast toast = Toast.makeText(getApplicationContext(), "Presiona y Arrastra el Reporte", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.mipmap.handicon);
        toastContentView.addView(imageView, 0);
        toast.show();

    }
    private void showToast1() {
        Toast toast1 = Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView1 = (LinearLayout) toast1.getView();
        ImageView imageView1 = new ImageView(getApplicationContext());
        imageView1.setImageResource(R.drawable.sad);
        toastContentView1.addView(imageView1, 0);
        toast1.show();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Get user select menu id and title.
        int itemId = item.getItemId();
        String menuTitle = (String)item.getTitle();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("You clicked menu ");
        stringBuffer.append(menuTitle);

        String message = stringBuffer.toString();

        switch (itemId)
        {
            case R.id.changepassw:
                Toast toast = Toast.makeText(getApplicationContext(), "no disponible", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                LinearLayout toastContentView = (LinearLayout) toast.getView();
                ImageView imageView = new ImageView(getApplicationContext());
                toastContentView.addView(imageView, 0);
                toast.show();
                //finish();
                break;
            case R.id.logoutbtn:
                Toast toast1 = Toast.makeText(getApplicationContext(), "no disponible", Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.BOTTOM, 0, 0);
                toast1.show();
                //finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void createMarker(MyLocationNewOverlay myLocationOverlay,int imageid){

        startMarker = new Marker(mMapView);
        GeoPoint locatemarker = new GeoPoint(myLocationOverlay.getMyLocationProvider().getLastKnownLocation().getLatitude(),myLocationOverlay.getMyLocationProvider().getLastKnownLocation().getLongitude());
        startMarker.setPosition(locatemarker);
        startMarker.isDraggable();
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setIcon(getResources().getDrawable(imageid));
        mMapView.getOverlays().add(startMarker);
        mMapView.invalidate();
        startMarker.setDraggable(true);
        mMapView.getOverlays().add(startMarker);
        mMapView.invalidate();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        if (!checkPermissions()) {
            requestPermissions();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            showSnackbar(PERMISSION, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    Constants.getRequestPermissionsRequestCode());
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.getRequestPermissionsRequestCode());
        }
    }
    private void showSnackbar(final String mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(Integer.parseInt(mainTextStringId)),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }



}
