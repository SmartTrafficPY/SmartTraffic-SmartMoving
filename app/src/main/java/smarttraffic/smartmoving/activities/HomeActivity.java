package smarttraffic.smartmoving.activities;


import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;

import android.view.LayoutInflater;
import android.view.Menu;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.gpkg.overlay.OsmMapShapeConverter;
import org.osmdroid.gpkg.overlay.features.PolylineOptions;
import org.osmdroid.tileprovider.TileStates;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import smarttraffic.smartmoving.Constants;
import smarttraffic.smartmoving.Interceptors.AddSMovingTokenInterceptor;
import smarttraffic.smartmoving.Interceptors.TokenUserInterceptor;
import smarttraffic.smartmoving.R;
import smarttraffic.smartmoving.Receivers.AlarmGeoFencing;
import smarttraffic.smartmoving.Receivers.GeofenceBroadcastReceiver;
import smarttraffic.smartmoving.Receivers.RemoveGeoFencing;
import smarttraffic.smartmoving.SmartMovingAPI;
import smarttraffic.smartmoving.Utils;
import smarttraffic.smartmoving.dataModels.Point;
import smarttraffic.smartmoving.dataModels.Reports.CreateContributionPoi;
import smarttraffic.smartmoving.dataModels.Reports.CreateReportPoi;
import smarttraffic.smartmoving.dataModels.Reports.Properties;
import smarttraffic.smartmoving.dataModels.Reports.ReportPoi;
import smarttraffic.smartmoving.dataModels.Reports.ReportsList;
import smarttraffic.smartmoving.dataModels.navigations.ChangeScore;
import smarttraffic.smartmoving.dataModels.navigations.CreateNavigationRequest;
import smarttraffic.smartmoving.dataModels.navigations.NavigationChangeScore;
import smarttraffic.smartmoving.dataModels.navigations.NavigationRequest;
import smarttraffic.smartmoving.dataModels.navigations.Rquest;
import smarttraffic.smartmoving.services.DetectedActivitiesService;
import smarttraffic.smartmoving.services.GeofenceTransitionsJobIntentService;
import smarttraffic.smartmoving.services.LocationUpdatesService;


public class HomeActivity extends AppCompatActivity {


    private SettingsClient mSettingsClient;
    private ActionMode myActionMode;
    boolean userNotResponse = true;
    boolean dialogSendAllready = false;
    Runnable cronJob;
    private LocationCallback mLocationCallback;
    public static String PERMISSION = "permitir ubicacion";
    private static final String LOG_TAG = "HomeActivity";
    private Marker userMarker;
    final Handler handler = new Handler();
    private CompassOverlay mCompassOverlay;
    Integer idnav;
    String severes = null;
    String lights = null;

    @BindView(R.id.mapview)
    MapView mMapView;
    @BindView(R.id.curbramppoi)
    ImageButton rampBtnPoi;
    @BindView(R.id.bustoppoi)
    ImageButton busstoBtnPoi;
    @BindView(R.id.pos3dra)
    ImageButton carBtnPoi;
    @BindView(R.id.pos1dra)
    ImageButton sidewalkBtnPoi;
    @BindView(R.id.pos4dra)
    ImageButton obstacleBtnPoi;
    @BindView(R.id.pos2dra)
    ImageButton pendienteBtnPoi;
    @BindView(R.id.pos5dra)
    ImageButton streetBtnPoi;
    @BindView(R.id.pos8dra)
    ImageButton noramppoi;
    @BindView(R.id.pos6dra)
    ImageButton nosidewalkpoi;
    @BindView(R.id.pos7dra)
    ImageButton stairpoi;
    @BindView(R.id.confirmreport)
    ImageButton confirmReport;
    @BindView(R.id.sharebtn)
    ImageButton sharereport;
    @BindView(R.id.deletereport)
    ImageButton deleteReport;
    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.textview1)
    TextView textV1;
    @BindView(R.id.textview2)
    TextView textV2;
    @BindView(R.id.textview3)
    TextView textV3;
    @BindView(R.id.textview4)
    TextView textV4;
    @BindView(R.id.textview5)
    TextView textV5;
    @BindView(R.id.textview6)
    TextView textV6;
    @BindView(R.id.textview7)
    TextView textV7;
    @BindView(R.id.textview8)
    TextView textV8;
    @BindView(R.id.searchDestinationEditText)
    EditText searchDestination;
    @BindView(R.id.startnav)
    Button startNav;
    @BindView(R.id.cancelnav)
    Button cancelNav;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.markerdestination)
    ImageView markerDestination;
    @BindView(R.id.linearlayout)
    LinearLayout toolbarnav;
    @BindView(R.id.cancelnavigation)
    ImageView cancelNavBtn;
    @BindView(R.id.lytPartition1)
    LinearLayout linearLayout1;
    @BindView(R.id.advertencia)
    LinearLayout advertencia;
    @BindView(R.id.recomendacion)
    TextView recomendacion;
    @BindView(R.id.lytPartitionReport)
    LinearLayout lytPartitionReport;

    AlarmGeoFencing addAlarmReceiver = new AlarmGeoFencing();
    RemoveGeoFencing removeAlarmReceiver = new RemoveGeoFencing();


    int carp, badsidewp, unevenp, obstaclp, pendp, stairp, nosdwp, normpp;

    //private MapView mMapView;
    int activityTransition;
    int geofenceTransition;
    int confidence;
    private ArrayList<String> geofencesTrigger = new ArrayList<>();
    private ActionBarDrawerToggle mToggle;
    private ScaleBarOverlay mScaleBarOverlay;
    private RotationGestureOverlay mRotationGestureOverlay;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Location mCurrentLocation;
    private MyLocationNewOverlay mLocationOverlay;
    private GeofencingClient geofencingClient;
    private PendingIntent mGeofencePendingIntent;
    private BroadcastReceiver locationReceiver;
    private FusedLocationProviderClient mFusedLocationClient;
    private ActivityRecognitionClient mActivityRecognitionClient;
    private BroadcastReceiver broadcastReceiver;
    private BroadcastReceiver geofenceReceiver;
    float score=0;
    LatLng orig;
    GeoPoint dest=null;
    String reporteTipo = null;
    ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
    //Toolbar toolbar;
    Marker startMarker = null;
    Marker markercheck = null;
    HorizontalScrollView scrollView = null;
    boolean banmark = false;
    Integer idmarker = null;
    IGeoPoint coord = null;
    List<Double> lista = new ArrayList<>();
    int counter = 0;
    int mid = 0;
    HashMap<Object, Integer> meMap = new HashMap<Object, Integer>();
    HashMap<Integer, String> namereport = new HashMap<Integer, String>();
    HashMap<Integer, String> lastupt = new HashMap<Integer, String>();
    ArrayList<Geofence> geofenceList = new ArrayList<>();
    ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
    Polyline polyline = new Polyline();
    Marker markerToShare = null;
    Boolean banddestination = false;
    ArrayList<String> sendseveres = new ArrayList<>();
    ArrayList<String> sendlights = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        Utils.setTileServerCredentials(this);
        ButterKnife.bind(this);
        //removeGeofences();
        //setReportsPoiOnMap();
        setMapView();
        setReportsPoiOnMap();

        mActivityRecognitionClient = new ActivityRecognitionClient(this);
        geofencingClient = LocationServices.getGeofencingClient(this);

        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.REPORTS_POI, Context.MODE_PRIVATE);
        final SharedPreferences sharedSevere = getApplicationContext().getSharedPreferences(
                Constants.REPORT_SERVERE, Context.MODE_PRIVATE);
        final SharedPreferences sharefLight = getApplicationContext().getSharedPreferences(
                Constants.REPORT_LIGHT, Context.MODE_PRIVATE);

        //sharedPreferences.getAll().clear();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setVisibility(View.VISIBLE);
        Utils.addAlarmsGeofencingTask(this);

        hideKeyboard(HomeActivity.this);
        if (savedInstanceState == null) {
            //it is the first time the fragment is being called
            counter = 0;
        } else {
            //not the first time so we will check SavedInstanceState bundle
            counter = savedInstanceState.getInt("value", 0); //here zero is the default value

        }
        saveMarkersConfig(sharedPreferences);
        //noinspection ConstantConditions
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        IConfigurationProvider osmConf = Configuration.getInstance();
        //File basePath = new File(getCacheDir().getAbsolutePath(), "osmdroid");
        //osmConf.setOsmdroidBasePath(basePath);
        //File tileCache = new File(osmConf.getOsmdroidBasePath().getAbsolutePath(), "tile");
        //osmConf.setOsmdroidTileCache(tileCache);

        //final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        severes = sharedSevere.getAll().toString();
        lights = sharefLight.getAll().toString();
        String[] severee = severes.split(", ,");
        String[] lightt = lights.split(", ,");
        if (severee.length > 2) {
            for (int i = 0; i < severee.length; i++) {
                String id = severee[i];
                String[] id1 = id.split("=");
                String idd = id1[1];
                idd = idd.replace(", }", "");
                sendseveres.add(idd);

            }
        } else {
            sendseveres.add("0");
        }
        if (lightt.length > 2) {
            for (int i = 0; i < lightt.length; i++) {
                String id = lightt[i];
                String[] id1 = id.split("=");
                String idd = id1[1];
                idd = idd.replace(", }", "");
                sendlights.add(idd);

            }
        } else {
            sendlights.add("0");
        }
        if (getIntent().hasExtra("carBtnPoiY") || getIntent().hasExtra("carBtnPoiR")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (getIntent().hasExtra("carBtnPoiY")) {
                carBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("carBtnPoiY"));
                carp = getIntent().getExtras().getInt("carBtnPoiY");
            } else {
                carBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("carBtnPoiR"));
                carp = getIntent().getExtras().getInt("carBtnPoiR");
            }
            if (getIntent().hasExtra("pendienteBtnPoiY")) {
                pendienteBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("pendienteBtnPoiY"
                ));
                pendp = getIntent().getExtras().getInt("pendienteBtnPoiY");
            } else {
                pendienteBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("pendienteBtnPoiR"));
                pendp = getIntent().getExtras().getInt("pendienteBtnPoiR");
            }
            if (getIntent().hasExtra("sidewalkBtnPoiY")) {
                sidewalkBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("sidewalkBtnPoiY"));
                badsidewp = getIntent().getExtras().getInt("sidewalkBtnPoiY");
            } else {
                sidewalkBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("sidewalkBtnPoiR"));
                badsidewp = getIntent().getExtras().getInt("sidewalkBtnPoiR");
            }
            if (getIntent().hasExtra("obstacleBtnPoiY")) {
                obstacleBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("obstacleBtnPoiY"));
                obstaclp = getIntent().getExtras().getInt("obstacleBtnPoiY");
            } else {
                obstacleBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("obstacleBtnPoiR"));
                obstaclp = getIntent().getExtras().getInt("obstacleBtnPoiR");
            }
            if (getIntent().hasExtra("streetBtnPoiY")) {
                streetBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("streetBtnPoiY"));
                unevenp = getIntent().getExtras().getInt("streetBtnPoiY");

            } else {
                streetBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("streetBtnPoiR"));
                unevenp = getIntent().getExtras().getInt("streetBtnPoiR");
            }
            if (getIntent().hasExtra("stairpoiY")) {
                stairpoi.setBackgroundResource(getIntent().getExtras().getInt("stairpoiY"));
                stairp = getIntent().getExtras().getInt("stairpoiY");
            } else {
                stairpoi.setBackgroundResource(getIntent().getExtras().getInt("stairpoiR"));
                stairp = getIntent().getExtras().getInt("stairpoiR");
            }
            if (getIntent().hasExtra("nosidewalkpoiY")) {
                nosidewalkpoi.setBackgroundResource(getIntent().getExtras().getInt("nosidewalkpoiY"));
                nosdwp = getIntent().getExtras().getInt("nosidewalkpoiY");
            } else {
                nosidewalkpoi.setBackgroundResource(getIntent().getExtras().getInt("nosidewalkpoiR"));
                nosdwp = getIntent().getExtras().getInt("nosidewalkpoiR");
            }
            if (getIntent().hasExtra("noramppoiY")) {
                noramppoi.setBackgroundResource(getIntent().getExtras().getInt("noramppoiY"));
                normpp = getIntent().getExtras().getInt("noramppoiY");
            } else {
                noramppoi.setBackgroundResource(getIntent().getExtras().getInt("noramppoiR"));
                normpp = getIntent().getExtras().getInt("noramppoiR");
            }
            editor.putInt("CAR", carp);
            editor.putInt("PENDT", pendp);
            editor.putInt("BADS", badsidewp);
            editor.putInt("OBSTC", obstaclp);
            editor.putInt("UNEV", unevenp);
            editor.putInt("STAIR", stairp);
            editor.putInt("NOSW", nosdwp);
            editor.putInt("NORMP", normpp);
            editor.apply();

        }


        setReportsPoiOnMap();
        //setSupportActionBar(toolBar);
        //Copyright overlay
        String copyrightNotice = mMapView.getTileProvider().getTileSource().getCopyrightNotice();
        CopyrightOverlay copyrightOverlay = new CopyrightOverlay(this);
        copyrightOverlay.setCopyrightNotice(copyrightNotice);


        scrollView = (HorizontalScrollView) findViewById(R.id.scrollPartition);

        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
            }
        }, 100L);
        deleteReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banmark = false;
                deleteMarker();
            }
        });
        confirmReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnected(HomeActivity.this)) {
                    banmark = false;
                    saveMarker();
                } else {
                    showCustomToast("Sin conexion", 0);
                }
            }
        });
        sharereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnected(HomeActivity.this)) {
                    startActivity(Utils.getTwitterIntent(HomeActivity.this, "aquí " + reporteTipo + " %23smartMovingApp", startMarker.getPosition()));
                    saveMarker();
                } else {
                    showCustomToast("Sin conexion", 0);
                    mLocationOverlay.enableFollowLocation();
                    mMapView.invalidate();
                }
                hideKeyboard(HomeActivity.this);
            }
        });
        searchDestination.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent i = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(i);
                hideKeyboard(HomeActivity.this);
                return false;
            }
        });
        startNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String all_severe = "";
                String all_light = "";
                linearLayout1.setVisibility(View.INVISIBLE);

                searchDestination.setVisibility(View.INVISIBLE);
                if (severes.isEmpty()) {
                    all_severe = "0";
                }
                if (lights.isEmpty()) {
                    all_light = "0";
                }

                final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Verificando...");
                progressDialog.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                /**Here the service get the request of Login...**/
                                progressDialog.dismiss();
                            }
                        }, 5 * Constants.getSecondsInMilliseconds());
                if(!progressDialog.isShowing()){
                    showCustomToast("Circule por el lado de la vereda \n donde vea menos reportes",0);
                }

                toolbarnav.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                startNav.setVisibility(View.INVISIBLE);
                cancelNav.setVisibility(View.INVISIBLE);
                advertencia.setVisibility(View.VISIBLE);
                markerToShare = new Marker(mMapView);
                GeoPoint geoPoint = new GeoPoint(mMapView.getMapCenter().getLatitude(), mMapView.getMapCenter().getLongitude());
                markerToShare.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                markerToShare.setIcon(getResources().getDrawable(R.drawable.target));
                markerToShare.setPosition(geoPoint);
                mMapView.getOverlays().add(markerToShare);
                mMapView.invalidate();
                markerDestination.setVisibility(View.INVISIBLE);
                startNavigation(sendseveres.toString(), sendlights.toString());


            }
        });
        cancelNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markerToShare.remove(mMapView);
                waypoints.clear();
                mLocationOverlay.enableFollowLocation();
                mMapView.getOverlays().remove(polyline);
                toolbarnav.setVisibility(View.INVISIBLE);
                linearLayout1.setVisibility(View.INVISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                searchDestination.setVisibility(View.VISIBLE);
                mMapView.invalidate();
                advertencia.setVisibility(View.INVISIBLE);

            }
        });

        cancelNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar.setVisibility(View.VISIBLE);
                searchDestination.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                startNav.setVisibility(View.INVISIBLE);
                cancelNav.setVisibility(View.INVISIBLE);
                linearLayout1.setVisibility(View.INVISIBLE);
                //markerToShare.remove(mMapView);
                markerDestination.setVisibility(View.INVISIBLE);
                mLocationOverlay.enableFollowLocation();
                mMapView.invalidate();
                banddestination = false;
            }
        });

        if (getIntent().hasExtra("latt")) {
            //showCustomToast("Mueve el mapa", 0);
            toolbar.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
            startNav.setVisibility(View.VISIBLE);
            cancelNav.setVisibility(View.VISIBLE);
            linearLayout1.setVisibility(View.VISIBLE);
            banddestination = true;
            markerDestination.setVisibility(View.VISIBLE);
            mLocationOverlay.disableFollowLocation();
            IGeoPoint mapCenter = new GeoPoint(getIntent().getExtras().getDouble("latt"), getIntent().getExtras().getDouble("longg"));
            mMapView.getController().setCenter(mapCenter);
            mMapView.invalidate();
            recomendacion.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recomendacion.setVisibility(View.INVISIBLE);
                }
            },20000);

        } else {
            mLocationOverlay.enableFollowLocation();
            mMapView.getOverlays().add(mLocationOverlay);
            mMapView.invalidate();
        }

        final ImageButton.OnClickListener olongBtn = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                scrollView.setVisibility(View.INVISIBLE);
                linearLayout1.setVisibility(View.INVISIBLE);
                showCustomToast("Presione en el mapa \n donde desee colocar", 0);
                lytPartitionReport.setVisibility(View.VISIBLE);
                switch (v.getId()) {
                    case R.id.curbramppoi:
                        createMarker(R.mipmap.wheelchairramp);
                        reporteTipo = "hay una rampa adecuada";
                        idmarker = 3;
                        break;
                    case R.id.bustoppoi:
                        createMarker(R.mipmap.accessiblebuspoi);
                        reporteTipo = "hay parada en que pasa bus accesible";
                        idmarker = 5;
                        break;
                    case R.id.pos2dra:
                        createMarker(pendp);
                        reporteTipo = "hay una pendiente";
                        idmarker = 4;
                        break;
                    case R.id.pos5dra:
                        createMarker(unevenp);
                        reporteTipo = "la calle es empedrada";
                        idmarker = 8;
                        break;
                    case R.id.pos4dra:
                        createMarker(obstaclp);
                        reporteTipo = "hay un obstáculo";
                        idmarker = 7;
                        break;
                    case R.id.pos3dra:
                        createMarker(carp);
                        reporteTipo = "hay un auto estacionado en la vereda";
                        idmarker = 6;
                        break;
                    case R.id.pos1dra:
                        createMarker(badsidewp);
                        reporteTipo = "la vereda esta deteriorada";
                        idmarker = 2;
                        break;
                    case R.id.pos7dra:
                        createMarker(stairp);
                        reporteTipo = "hay una escalera";
                        idmarker = 1;
                        break;
                    case R.id.pos8dra:
                        createMarker(normpp);
                        reporteTipo = "hay una rampa inadecuada o inexistente";
                        idmarker = 10;
                        break;
                    case R.id.pos6dra:
                        createMarker(nosdwp);
                        reporteTipo = "no existe vereda";
                        idmarker = 9;
                        break;

                }
            }
        };

        searchDestination.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
        mMapView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                return false;
            }
        });


        mMapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (banmark == true) {
                    Projection proj = mMapView.getProjection();
                    GeoPoint loc = (GeoPoint) proj.fromPixels((int) motionEvent.getX(), (int) motionEvent.getY());
                    startMarker.setPosition(loc);

                    coord = loc;

                }
                return false;
            }
        });



        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROADCAST_TRANSITION_ACTIVITY_INTENT)) {
                    activityTransition = intent.getIntExtra(Constants.ACTIVITY_TYPE_TRANSITION, -1);
                    confidence = intent.getIntExtra(Constants.ACTIVITY_CONFIDENCE_TRANSITION, -1);
                }
            }
        };

        geofenceReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.getBroadcastGeofenceTriggerIntent())) {
                    geofencesTrigger = intent.getStringArrayListExtra(
                            Constants.GEOFENCE_TRIGGED);
                    geofenceTransition = intent.getIntExtra(
                            GeofenceTransitionsJobIntentService.TRANSITION,
                            -1);
                    managerOfTransitions();
                }
            }
        };

        locationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               // mLocationOverlay.onLocationChanged(mLocationOverlay.getMyLocationProvider().getLastKnownLocation(),mLocationOverlay.getMyLocationProvider());
                mCurrentLocation = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
                final SharedPreferences destinationSharedPreferences = getApplicationContext().getSharedPreferences(
                        Constants.DESTINATION, Context.MODE_PRIVATE);
                String destinn = destinationSharedPreferences.getString("Dest","Def");

                if (mCurrentLocation != null) {
                    Location destin=new Location(destinn);
                    //destin.setLongitude(dest.getLongitude());
                   // destin.setLatitude(dest.getLatitude());
                    checkForUserLocation(mCurrentLocation,destin);
                }
            }
        };



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

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        mGeofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }


    private GeofencingRequest getGeofencingRequest(ArrayList<Geofence> geofenceList) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }




    @SuppressLint("MissingPermission")
    private void addGeofences(ArrayList<Geofence> geofenceArrayList) {
        if (!checkPermissions()) {
            showCustomToast("Permisos insuficientes", 0);
            return;
        }

        geofencingClient.addGeofences(getGeofencingRequest(geofenceArrayList), getGeofencePendingIntent());
        Utils.geofencesSetUp(HomeActivity.this,true);
    }

    private void managerOfTransitions() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                Constants.SETTINGS, MODE_PRIVATE);
        //getSpotsFromGeofence(geofencesTrigger, false);
        final long delay =  Constants.getSecondsInMilliseconds() * 45;
        cronJob = new Runnable() {
            public void run() {
                handler.postDelayed(this, delay);
            }
        };
        switch (geofenceTransition) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                startLocationService();
                handler.postDelayed(cronJob, delay);
                Log.d("myTag", "Entro a loc");
                requestActivityUpdates();
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                if(Utils.returnEnterLotFlag(this)){
                    Utils.hasArrivedFlag(this, false);
                }
                handler.removeCallbacks(cronJob);
                stopLocationService();
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:

            default:
                Log.d("myTag", "no hay ");
                removeActivityUpdates();
                handler.removeCallbacks(cronJob);
                stopLocationService();
                break;
        }
    }
    public void removeActivityUpdates() {
        @SuppressLint("MissingPermission")
        Task<Void> task = mActivityRecognitionClient.removeActivityUpdates(
                getActivityDetectionPendingIntent());
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void result) {
                setUpdatesRequestedState(false);
                // Reset the display.
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                setUpdatesRequestedState(true);
            }
        });
    }
    public void requestActivityUpdates() {
        Task<Void> task = mActivityRecognitionClient.requestActivityUpdates(
                Constants.DETECTION_INTERVAL_IN_MILLISECONDS,
                getActivityDetectionPendingIntent());

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void result) {
                setUpdatesRequestedState(true);
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                setUpdatesRequestedState(false);
            }
        });
    }
    private void setUpdatesRequestedState(boolean requesting) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(Constants.KEY_ACTIVITY_UPDATES_REQUESTED, requesting)
                .apply();
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(this, DetectedActivitiesService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // requestActivityUpdates() and removeActivityUpdates().
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startNavigation(String severes, String lights){

        severes = severes.replace("[","");
        severes = severes.replace("]","");
        lights = lights.replace("[","");
        lights = lights.replace("]","");
        if(Utils.isNetworkConnected(HomeActivity.this)) {
            CreateNavigationRequest navRequest = new CreateNavigationRequest();
            NavigationRequest navigationRequestP = new NavigationRequest();
            navRequest.setOrigin("POINT(" + mLocationOverlay.getMyLocation().getLongitude() + ' ' + mLocationOverlay.getMyLocation().getLatitude() + ')');
            SharedPreferences userid = getSharedPreferences(Constants.CLIENTE_DATA, Context.MODE_PRIVATE);
            navRequest.setUser_requested(userid.getInt(Constants.USER_ID, Context.MODE_PRIVATE));
            navRequest.setDestination("POINT(" + markerToShare.getPosition().getLongitude() + ' ' + markerToShare.getPosition().getLatitude() + ')');
            navRequest.setFinished(false);
            navRequest.setReport_light(severes);
            navRequest.setReport_severe(lights);
            navigationRequestP.setGeometry(null);
            navigationRequestP.setType("Feature");
            navigationRequestP.setProperties(navRequest);
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new TokenUserInterceptor(HomeActivity.this))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            SmartMovingAPI smartMovingAPI = retrofit.create(SmartMovingAPI.class);
            Call<NavigationRequest> call = smartMovingAPI.setNavigationRequest("application/vnd.geo+json", navigationRequestP);
            ArrayList<String> routee = new ArrayList<>();
            String origin, destiny;
            GeoPoint destyn1 = new GeoPoint(0, 0);


            try {
                Response<NavigationRequest> result = call.execute();
                if (result.code() == 201) {
                    routee = result.body().getProperties().getRoute();
                    origin = result.body().getProperties().getOrigin();
                    String pk = result.raw().header("Location");
                        String[] parts = pk.split("/");
                     idnav=   Integer.parseInt(parts[parts.length - 1]);
                    linearLayout1.setVisibility(View.INVISIBLE);
                    destiny = result.body().getProperties().getDestination();
                    origin = origin.replace("SRID=4326;POINT (", "");
                    origin = origin.replace(")", "");
                    String[] db = origin.split(" ");
                    Double dblon = Double.parseDouble(db[0]);
                    Double dblat = Double.parseDouble(db[1]);

                    destiny = destiny.replace("SRID=4326;POINT (", "");
                    destiny = destiny.replace(")", "");
                    String[] db1 = destiny.split(" ");
                    Double db1lon = Double.parseDouble(db1[0]);
                    Double db1lat = Double.parseDouble(db1[1]);
                    GeoPoint origin1 = new GeoPoint(dblat, dblon);
                    destyn1.setLongitude(db1lon);
                    destyn1.setLatitude(db1lat);
                    waypoints.add(origin1);

                } else {
                    showCustomToast("Algo salió mal", R.drawable.sad);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            int count = 0;
            int size = routee.size();


            if (!routee.isEmpty()) {

                for (String ad : routee) {
                    String last = routee.get(routee.size() - 1);
                    String[] lastnode = last.split(",");
                    String endnode = lastnode[1];
                    String[] separated = ad.split(",");
                    String seq = separated[0];
                    seq = seq.replace("(", "");
                    String node = separated[1];
                    if (!node.equals(endnode)) {
                        String lon = separated[2];
                        lon = lon.replace("Decimal(", "");
                        lon = lon.replace(")", "");
                        lon = lon.replace("'", "");

                        String lat = separated[3];
                        lat = lat.replace("Decimal(", "");
                        lat = lat.replace(")", "");
                        lat = lat.replace("'", "");
                        GeoPoint ggp = new GeoPoint(Double.parseDouble(lat), Double.parseDouble(lon));
                        waypoints.add(ggp);
                        count = count + 1;
                    } else {
                        String lon = separated[2];
                        lon = lon.replace("Decimal(", "");
                        lon = lon.replace(")", "");
                        lon = lon.replace("'", "");

                        String lat = separated[3];
                        lat = lat.replace("Decimal(", "");
                        lat = lat.replace(")", "");
                        lat = lat.replace("'", "");
                        GeoPoint ggp = new GeoPoint(Double.parseDouble(lat), Double.parseDouble(lon));
                        waypoints.add(ggp);
                        waypoints.add(destyn1);
                        dest=destyn1;
                        break;
                    }
                }
                mLocationOverlay.setDrawAccuracyEnabled(true);
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                polyline.setPoints(waypoints);
                orig = new LatLng(waypoints.get(0).getLatitude(),waypoints.get(0).getLongitude());
                //dest = new LatLng(waypoints.get(waypoints.size()-1).getLatitude(),waypoints.get(waypoints.size()-1).getLongitude());
                //istance = SphericalUtil.computeDistanceBetween(orig,dest);

                dialogSendAllready=false;
                polyline.setWidth(24);
                PolylineOptions polyop = new PolylineOptions();
                polyop.setColor(getResources().getColor(R.color.bluee));
                String dist = String.valueOf(polyline.getDistance()+50);
                polyline.setColor(R.color.colorAccent);
                polyline.setGeodesic(true);
                mMapView.getOverlays().add(polyline);
               // mMapView.getOverlays().add(mDirectionalArrows);
                polyline.setColor(getResources().getColor(R.color.bluee));
                polyline.setGeodesic(true);
                mMapView.invalidate();
                polyline.setGeodesic(true);
                //showCustomToast("Circule por el lado de la vereda \n donde vea menos reportes",0);
                geofenceList.add(new Geofence.Builder()
                        // Set the request ID of the geofence. This is a string to identify this
                        // geofence.
                        .setRequestId("dest")

                        .setCircularRegion(
                                dest.getLatitude(),
                                dest.getLongitude(),
                                Float.parseFloat(dist)
                        )
                        .setExpirationDuration(6000*100*4 )
                        .setLoiteringDelay(1000 * 60 * 20)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                                Geofence.GEOFENCE_TRANSITION_EXIT |
                        Geofence.GEOFENCE_TRANSITION_DWELL)
                        .build());
                addGeofences(geofenceList);
                Utils.addAlarmsGeofencingTask(this);
                //checkForUserLocation(lattlongg);
                mLocationOverlay.enableFollowLocation();
                mMapView.animate();
                mMapView.getController().animateTo(mLocationOverlay.getMyLocation());
                mMapView.getController().setCenter(mLocationOverlay.getMyLocation());
                mMapView.getController().setZoom(20);
                mMapView.invalidate();
                Log.d("myTag", "empezo");
                final SharedPreferences destinationSharedPreferences = getApplicationContext().getSharedPreferences(
                        Constants.DESTINATION, Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = destinationSharedPreferences.edit();
                editor.putString("Dest", String.valueOf(dest.getLatitude()+","+dest.getLongitude()));
                editor.apply();
                //6confirmationOfActionDialog();
                advertencia.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        advertencia.setVisibility(View.INVISIBLE);
                    }
                },20000);

            } else {
                showCustomToast("Algo salió mal", 0);
                toolbarnav.setVisibility(View.INVISIBLE);
                searchDestination.setVisibility(View.VISIBLE);
                advertencia.setVisibility(View.INVISIBLE);


            }
        }else{
            showCustomToast("Sin conexion",0);
            toolbarnav.setVisibility(View.INVISIBLE);
            advertencia.setVisibility(View.INVISIBLE);
            searchDestination.setVisibility(View.VISIBLE);
        }


        }

        public void  showCustomToast(CharSequence string, int imageid){
            if (!string.equals("Sin conexion") && !string.equals("Active GPS") && !string.equals("Algo salió mal")) {
                LayoutInflater li = getLayoutInflater();
                View layout = li.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                TextView textViewt = layout.findViewById(R.id.texttoast);
                ImageView imageViewt = layout.findViewById(R.id.imageViewtoast);
                if (imageid!=0) {
                    imageViewt.setImageDrawable(getResources().getDrawable(imageid));
                }
                textViewt.setTextSize(15f);
                textViewt.setText(string);
                imageViewt.setBackgroundColor(getResources().getColor(R.color.transp));
                textViewt.setBackgroundColor(getResources().getColor(R.color.transp));
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,Gravity.CLIP_HORIZONTAL,Gravity.CENTER_VERTICAL);
                toast.setView(layout);//setting the view of custom toast layout
                toast.show();

            }else{
                LayoutInflater li1 = getLayoutInflater();
                View layout1 = li1.inflate(R.layout.other_toast_layout, (ViewGroup) findViewById(R.id.custom_toast_layout_id1));
                Toast toast1 = new Toast(getApplicationContext());
                TextView textViewt1 = layout1.findViewById(R.id.texttoast1);
                textViewt1.setText(string);
                textViewt1.setBackgroundColor(getResources().getColor(R.color.transp));
                toast1.setGravity(Gravity.CENTER,Gravity.CLIP_HORIZONTAL,Gravity.CENTER_VERTICAL);
                toast1.setDuration(Toast.LENGTH_LONG);
                toast1.setView(layout1);//setting the view of custom toast layout
                toast1.show();
            }



        }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void saveMarkersConfig(SharedPreferences sharedPreferences){
        if (sharedPreferences.getAll().isEmpty()) {

            carp = R.mipmap.carinsidewalk;
            pendp = R.mipmap.pendiente;
            badsidewp = R.mipmap.sidewalkwarning;
            obstaclp = R.mipmap.obstacle;
            unevenp = R.mipmap.uneven;
            stairp = R.mipmap.stairredpoii;
            nosdwp = R.mipmap.nosidewalkredpoii;
            normpp = R.mipmap.norampredpoii;

        } else {

            carp = sharedPreferences.getInt("CAR", 0);
            carBtnPoi.setBackgroundResource(carp);
            pendp = sharedPreferences.getInt("PENDT", 0);
            pendienteBtnPoi.setBackgroundResource(pendp);
            badsidewp = sharedPreferences.getInt("BADS", 0);
            sidewalkBtnPoi.setBackgroundResource(badsidewp);
            obstaclp = sharedPreferences.getInt("OBSTC", 0);
            obstacleBtnPoi.setBackgroundResource(obstaclp);
            unevenp = sharedPreferences.getInt("UNEV", 0);
            streetBtnPoi.setBackgroundResource(unevenp);
            stairp = sharedPreferences.getInt("STAIR", 0);
            stairpoi.setBackgroundResource(stairp);
            nosdwp = sharedPreferences.getInt("NOSW", 0);
            nosidewalkpoi.setBackgroundResource(nosdwp);
            normpp = sharedPreferences.getInt("NORMP", 0);
            noramppoi.setBackgroundResource(normpp);

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setMapView() {
        Utils.setTileServerCredentials(this);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(
                this));

        mMapView.setTileSource(new OnlineTileSourceBase("SMARTPARKING CartoDB",
                16, 25, 256, ".png",
                new String[]{Constants.TILE_SERVER}) {
            @Override
            public String getTileURLString(long pMapTileIndex) {
                return getBaseUrl()
                        + MapTileIndex.getZoom(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + "/" + MapTileIndex.getY(pMapTileIndex)
                        + mImageFilenameEnding;
            }
        });
        IMapController mapController = mMapView.getController();

        setMapConfiguration(mapController);
        //add all overlays
        mMapView.setBuiltInZoomControls(false);
    }

    private class MyInfoWindow extends MarkerInfoWindow{
        public MyInfoWindow(int layoutResId, MapView mapView) {
            super(layoutResId, mapView);
            //onOpen(mid);
        }
        public void onClose() {
            close();
        }


        public void onOpen(Object arg0) {

            LinearLayout layout = (LinearLayout) mView.findViewById(R.id.windowinfo);

           final TextView txtTitle = (TextView) mView.findViewById(R.id.title);
           TextView txtDescription = (TextView) mView.findViewById(R.id.lastupdate);
           TextView txtSubdescription = (TextView) mView.findViewById(R.id.question);
            Button yes = (Button) mView.findViewById(R.id.yesexist);
            Button no = (Button) mView.findViewById(R.id.noexist);
            final int idmarker = meMap.get(arg0);
            txtTitle.setText(Html.fromHtml("Tipo de reporte:<b>"+namereport.get(idmarker)+"</b>"));
            txtDescription.setText("Ultima vez actualizado: " + lastupt.get(idmarker));

            final SharedPreferences sp = getSharedPreferences(Constants.CLIENTE_DATA,Context.MODE_PRIVATE);
            final int id= sp.getInt(Constants.USER_ID,0);
            layout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onClose();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setColaboration(true,idmarker, id);
                    onClose();
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setColaboration(false,idmarker, id);
                    onClose();
                }
            });

        }
    }

    public void resetFlags(boolean positiveResponse){
        final Handler resetDialogtimer = new Handler();
        resetDialogtimer.postDelayed(new Runnable() {
            @Override
            public void run() {
                userNotResponse = true;
                dialogSendAllready = false;
            }
        }, positiveResponse ? Constants.getMinutesInMilliseconds() * 3 : Constants.getSecondsInMilliseconds() * 20);
    }


    private void checkForUserLocation(Location mCurrentLocation, Location destin) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.ENTER_DEST_FLAG,
                Context.MODE_PRIVATE);
        boolean flag = sharedPreferences.getBoolean(Constants.HAS_ARRIVED,false);
            if(mCurrentLocation!=null){
                if (flag==true){
                    /*LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
                    LocalBroadcastManager.getInstance(this).unregisterReceiver(geofenceReceiver);
                    LocalBroadcastManager.getInstance(this).unregisterReceiver(locationReceiver);
                    unregisterReceiver(addAlarmReceiver);
                    unregisterReceiver(removeAlarmReceiver);*/
                    mMapView.getOverlays().remove(polyline);
                    markerToShare.remove(mMapView);
                    waypoints.clear();
                    toolBar.setVisibility(View.VISIBLE);
                    mLocationOverlay.enableFollowLocation();
                    mMapView.getOverlays().remove(polyline);
                    toolbarnav.setVisibility(View.INVISIBLE);
                    searchDestination.setVisibility(View.VISIBLE);
                    mMapView.invalidate();
                    if(!dialogSendAllready) {
                        dialogSendAllready=true;
                        confirmationOfActionDialog();
                    }
                }


            }


    }
    private void confirmationOfActionDialog() {

        final AlertDialog.Builder mBuild = new AlertDialog.Builder(HomeActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.rank_dialog,null);

        final RatingBar ratebar = (RatingBar)mView.findViewById(R.id.dialog_ratingbar);
        ratebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float rateValue = rating;
                score=2;
                score=rating;

            }
        });


        mBuild.setView(mView);
        final AlertDialog dialog=mBuild.create();
        dialog.show();

        Button btnSubmit=(Button)mView.findViewById(R.id.rank_dialog_button);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitScore(score);
                geofencingClient.removeGeofences(getGeofencePendingIntent());
                Utils.geofencesSetUp(HomeActivity.this,false);
                dialog.dismiss();
                //showCustomToast("Guardado",R.drawable.smile1);
            }
        });

    }

    public void submitScore(float score){
        final SharedPreferences sp = getSharedPreferences(Constants.CLIENTE_DATA,Context.MODE_PRIVATE);
        final int id= sp.getInt(Constants.USER_ID,0);

        NavigationChangeScore navScore = new NavigationChangeScore();
        ChangeScore changeScore = new ChangeScore();
        changeScore.setFinished(true);
        changeScore.setScore((int) score);
        changeScore.setUser_requested(id);
        navScore.setProperties(changeScore);
        navScore.setGeometry(null);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new TokenUserInterceptor(HomeActivity.this))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SmartMovingAPI smartMovingAPI = retrofit.create(SmartMovingAPI.class);
        Call<NavigationChangeScore> call = smartMovingAPI.updateNavigationRequest("application/vnd.geo+json",idnav,navScore);

        call.enqueue(new Callback<NavigationChangeScore>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<NavigationChangeScore> call, Response<NavigationChangeScore> response) {
                switch (response.code()) {
                    case 200:
                        showCustomToast("Gracias por contribuir", R.drawable.smile1);

                        break;
                    case 400:
                        showCustomToast("Ya contribuyó",0);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onFailure(Call<NavigationChangeScore> call, Throwable t) {
                if(!Utils.isNetworkConnected(HomeActivity.this)){
                    showCustomToast("Sin conexion", 0);
                }
                t.printStackTrace();
            }
        });



    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("value",counter);
        outState.putInt("CAR",carp);
        outState.putInt("PENDT",pendp);
        outState.putInt("BADS",badsidewp);
        outState.putInt("OBSTC",obstaclp);
        outState.putInt("UNEV",unevenp);
        outState.putInt("STAIR",stairp);
        outState.putInt("NOSW",nosdwp);
        outState.putInt("NORMP",normpp);
    }
    public void setReportsPoiOnMap(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new TokenUserInterceptor(HomeActivity.this))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SmartMovingAPI smartMovingAPI = retrofit.create(SmartMovingAPI.class);
        Call<ReportsList> call = smartMovingAPI.getReportsPoi();

        call.enqueue(new Callback<ReportsList>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ReportsList> call, Response<ReportsList> response) {
                switch (response.code()) {
                    case 200:
                        List<ReportPoi> reportsPois = response.body().getFeatures();

                        Utils.ReportsPoiSharedPreferences(HomeActivity.this, reportsPois);
                        for (ReportPoi reportpoi : reportsPois) {

                            GeoPoint gp = new GeoPoint(reportpoi.getGeometry().getPointCoordinates().getLatitud(),reportpoi.getGeometry().getPointCoordinates().getLongitud());

                            int id = reportpoi.getProperties().getReport_type();
                            switch (id){
                                case 1:
                                    setIndividualReportPoi(stairp,gp,reportpoi,"Escalera o grada");
                                    break;
                                case 2:
                                    setIndividualReportPoi(badsidewp,gp,reportpoi,"Vereda en mal estado");
                                    break;
                                case 3:
                                    setIndividualReportPoi(R.mipmap.wheelchairramp,gp,reportpoi,"Rampa adecuada");
                                    break;
                                case 4:
                                    setIndividualReportPoi(pendp,gp,reportpoi,"Pendiente");
                                    break;
                                case 5:
                                    setIndividualReportPoi(R.mipmap.accessiblebuspoi,gp,reportpoi,"Parada en que pasa \n bus accesible");
                                    break;
                                case 6:
                                    setIndividualReportPoi(carp,gp,reportpoi,"Auto en vereda");
                                    break;
                                case 7:
                                    setIndividualReportPoi(obstaclp,gp,reportpoi,"Obstáculo");
                                    break;
                                case 8:
                                    setIndividualReportPoi(unevenp,gp,reportpoi,"Calle empedrado");
                                    break;
                                case 9:
                                    setIndividualReportPoi(nosdwp,gp,reportpoi,"Vereda inexistente");
                                    break;
                                case 10:
                                    setIndividualReportPoi(normpp,gp,reportpoi,"Rampa inadecuada o inexistente");
                                    break;
                            }

                        }

                        //Utils.saveListOfGateways(HomeActivity.this, response.body());
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onFailure(Call<ReportsList> call, Throwable t) {
                if(!Utils.isNetworkConnected(HomeActivity.this)){
                    showCustomToast("Sin conexion",0);
                }
                t.printStackTrace();
            }
        });

    }
    public void setColaboration(Boolean statusr, Integer reportid, Integer userid){
        CreateContributionPoi colaboration = new CreateContributionPoi();
        colaboration.setReportid(reportid);
        colaboration.setUser(userid);
        colaboration.setValue(statusr);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new TokenUserInterceptor(HomeActivity.this))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SmartMovingAPI smartMovingAPI = retrofit.create(SmartMovingAPI.class);
        Call<CreateContributionPoi> call = smartMovingAPI.setContributionReport(colaboration);

        call.enqueue(new Callback<CreateContributionPoi>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<CreateContributionPoi> call, Response<CreateContributionPoi> response) {
                switch (response.code()) {
                    case 201:
                        showCustomToast("Gracias por contribuir", R.drawable.smile1);

                        break;
                    case 400:
                        showCustomToast("Ya contribuyó",0);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onFailure(Call<CreateContributionPoi> call, Throwable t) {
                if(!Utils.isNetworkConnected(HomeActivity.this)){
                    showCustomToast("Sin conexion", 0);
                }
                t.printStackTrace();
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setIndividualReportPoi(int id, GeoPoint point, ReportPoi reportPoi, String reportType) {
        if(!reportPoi.getProperties().getStatus().equals("R")) {
            Marker newmarker = new Marker(mMapView);
            newmarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            newmarker.setIcon(getResources().getDrawable(id));

            int mid = reportPoi.getProperties().getIdFromUrl();
            newmarker.setId(String.valueOf(reportPoi.getProperties().getIdFromUrl()));
            newmarker.setPosition(point);
            MarkerInfoWindow infoWindow = new MyInfoWindow(R.layout.window_info_marker_layout, mMapView);
            newmarker.setInfoWindow(infoWindow);

            meMap.put(newmarker,mid);
            namereport.put(mid,reportType);
            String updated = reportPoi.getProperties().getModified();
            String[] splitt = updated.split("T");
            String date = splitt[0];
            lastupt.put(mid, date);
            infoWindow.setRelatedObject(newmarker);

            mMapView.getOverlays().add(newmarker);
            if (reportPoi.getProperties().getStatus().equals("C")) {
                markercheck = new Marker(mMapView);
                markercheck.setIcon(getResources().getDrawable(R.drawable.checkclip));
                markercheck.setAnchor(Marker.ANCHOR_TOP, Marker.ANCHOR_RIGHT);
                markercheck.setTitle("Reporte verificado");
                markercheck.setPosition(point);
                mMapView.getOverlays().add(markercheck);
            }
            mMapView.invalidate();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setMapConfiguration(IMapController mapController) {
        mMapView.setTilesScaledToDpi(true);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapView.setFlingEnabled(true);
        mapController.setZoom(19);
        //scale bar
        final DisplayMetrics dm = this.getResources().getDisplayMetrics();
        mScaleBarOverlay = new ScaleBarOverlay(mMapView);
        mScaleBarOverlay.setCentred(true);
        //play around with these values to get the location on screen in the right place for your application
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mCompassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), mMapView);
        mCompassOverlay.enableCompass();
        //rotation gestures
        mRotationGestureOverlay = new RotationGestureOverlay(this, mMapView);
        mRotationGestureOverlay.setEnabled(true);
        mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mMapView);
        mLocationOverlay.enableMyLocation();
        mLocationOverlay.enableFollowLocation();
        Drawable person = getDrawable(R.drawable.person);



        Bitmap personBitmap = Bitmap.createBitmap(person.getIntrinsicWidth(),person.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas personCanvas = new Canvas(personBitmap);
        person.setBounds(0, 0, personCanvas.getWidth(), personCanvas.getHeight());
        person.draw(personCanvas);
        //mLocationOverlay.setDirectionArrow(personBitmap);
        mLocationOverlay.setOptionsMenuEnabled(true);
        mMapView.getOverlays().add(mRotationGestureOverlay);
        mMapView.getOverlays().add(mCompassOverlay);
        mMapView.getOverlays().add(mScaleBarOverlay);
        mMapView.getOverlays().add(mLocationOverlay);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        mLocationOverlay.enableFollowLocation();
        hideKeyboard(HomeActivity.this);
        mMapView.invalidate();
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
        lytPartitionReport.setVisibility(View.INVISIBLE);
        showCustomToast("Eliminado",R.drawable.sad);
        scrollView.setVisibility(View.VISIBLE);
        mMapView.invalidate();
        banmark = false;
    }

    private void saveMarker(){
        if(coord!=null) {
            setReport();
            showCustomToast("Creaste un reporte!",R.drawable.smile1);
            scrollView.setVisibility(View.VISIBLE);
            lytPartitionReport.setVisibility(View.INVISIBLE);

        }else{
            showCustomToast("Presione en el mapa \n donde desee colocar",0);
            banmark=true;
        }


    }
    private void setReport(){
        Properties reportPoiC = new Properties();
        CreateReportPoi reportPoi = new CreateReportPoi();
        Point geometry = new Point();
        SharedPreferences userid = getSharedPreferences(Constants.CLIENTE_DATA,Context.MODE_PRIVATE);
        reportPoiC.setType("Feature");
        reportPoi.setUser_created((userid.getInt(Constants.USER_ID, Context.MODE_PRIVATE)));
        reportPoi.setReport_type(idmarker);
        reportPoi.setStatus("U");
        reportPoi.setGid(0);
        MarkerInfoWindow infoWindow = new MyInfoWindow(R.layout.window_info_marker_layout, mMapView);
        startMarker.setInfoWindow(infoWindow);
        reportPoiC.setProperties(reportPoi);
        lista.add(coord.getLongitude());
        lista.add(coord.getLatitude());
        geometry.setCoordinates(lista);
        reportPoiC.setGeometry(geometry);

        Gson gson= new GsonBuilder()
                .setLenient()
                .create();
        final OkHttpClient okHttClient = new OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .addInterceptor(new AddSMovingTokenInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()

                .client(okHttClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SmartMovingAPI smartMovingAPI = retrofit.create(SmartMovingAPI.class);
        Call<ReportPoi> call = smartMovingAPI.setReportPoi("application/vnd.geo+json", reportPoiC);

        try {
            Response<ReportPoi> result = call.execute();
            Headers headers = ((Response) result).headers();
            if(result.code() == 201){

            }else{
                showCustomToast("Cancelado",R.drawable.sad);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        banmark=false;
        //coord=null;
        lista.remove(coord.getLongitude());
        lista.remove(coord.getLatitude());
        setReportsPoiOnMap();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Get user select menu id and title.
        int itemId = item.getItemId();
        String menuTitle = (String)item.getTitle();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("You clicked menu ");
        stringBuffer.append(menuTitle);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.CLIENTE_DATA, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        String message = stringBuffer.toString();

        switch (itemId)
        {
            case R.id.logoutbtn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Estas seguro que quieres Cerrar Sesion?")
                        .setPositiveButton("Cerrar Sesion", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                editor.putString(Constants.USER_TOKEN, Constants.CLIENT_NOT_LOGIN).apply();
                                editor.commit();
                                Intent logoutIntent = new Intent(HomeActivity.this,
                                        LoginActivity.class);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(logoutIntent);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create().show();
                //finish();
                break;
            case R.id.configreports:
                Intent i = new Intent(HomeActivity.this, ConfigReportsActivity.class);
                startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private void createMarker(int imageid){

        startMarker = new Marker(mMapView);
        markercheck = new Marker(mMapView);
        banmark=true;
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setIcon(getResources().getDrawable(imageid));
        markercheck.setAnchor(Marker.ANCHOR_TOP,Marker.ANCHOR_RIGHT);
        markercheck.setIcon(getResources().getDrawable(R.drawable.checkclip));
        idmarker = imageid;
        mMapView.getOverlays().add(startMarker);
        mMapView.invalidate();


    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        hideKeyboard(HomeActivity.this);
        return true;
    }
    @Override
    public void onStart() {
        Boolean gpsStatus = false;
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        super.onStart();
        if (!checkPermissions()) {
            requestPermissions();
        }
        if(gpsStatus==false){
            showCustomToast("Active GPS", R.drawable.gps);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(Constants.BROADCAST_TRANSITION_ACTIVITY_INTENT));
        LocalBroadcastManager.getInstance(this).registerReceiver(geofenceReceiver,
                new IntentFilter(Constants.getBroadcastGeofenceTriggerIntent()));
        LocalBroadcastManager.getInstance(this).registerReceiver(locationReceiver,
                new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));
        registerReceiver(addAlarmReceiver, new IntentFilter());
        registerReceiver(removeAlarmReceiver, new IntentFilter());
        //mLocationOverlay.enableFollowLocation();
        //mMapView.getOverlays().add(mLocationOverlay);
        //mMapView.invalidate();
        hideKeyboard(HomeActivity.this);
        //mLocationOverlay.disableFollowLocation();
        //mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.REPORTS_POI, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(geofenceReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(locationReceiver);
        //unregisterReceiver(addAlarmReceiver);
        //unregisterReceiver(removeAlarmReceiver);
        editor.putInt("CAR",carp);
        editor.putInt("PENDT",pendp);
        editor.putInt("BADS",badsidewp);
        editor.putInt("OBSTC",obstaclp);
        editor.putInt("UNEV",unevenp);
        editor.putInt("STAIR",stairp);
        editor.putInt("NOSW",nosdwp);
        editor.putInt("NORMP",normpp);
        editor.apply();
       // mLocationOverlay.enableFollowLocation();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                boolean hasBackPressed = data.getBooleanExtra("hasBackPressed",false);
                if(hasBackPressed && !mLocationOverlay.isFollowLocationEnabled()){
                    mLocationOverlay.enableFollowLocation();
                    mMapView.getOverlays().add(mLocationOverlay);
                    mMapView.invalidate();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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
    public void startLocationService() {
        Intent serviceIntent = new Intent(this, LocationUpdatesService.class);
        startService(serviceIntent);
    }

    public void stopLocationService() {
        Intent serviceIntent = new Intent(this, LocationUpdatesService.class);
        stopService(serviceIntent);
    }



}


