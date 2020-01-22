package smarttraffic.smartmoving.activities;



import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;

import android.view.Menu;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.stmt.query.In;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
//import org.osmdroid.bonuspack.routing.OSRMRoadManager;
//import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.CopyrightOverlay;
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
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import smarttraffic.smartmoving.SmartMovingAPI;
import smarttraffic.smartmoving.Utils;
import smarttraffic.smartmoving.dataModels.Point;
import smarttraffic.smartmoving.dataModels.Reports.CreateContributionPoi;
import smarttraffic.smartmoving.dataModels.Reports.CreateReportPoi;
import smarttraffic.smartmoving.dataModels.Reports.Properties;
import smarttraffic.smartmoving.dataModels.Reports.ReportPoi;
import smarttraffic.smartmoving.dataModels.Reports.ReportsList;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;


public class HomeActivity extends AppCompatActivity  {


    private SettingsClient mSettingsClient;
    private ActionMode myActionMode;
    boolean userNotResponse = true;
    boolean dialogSendAllready = false;
    private LocationCallback mLocationCallback;
    public static String PERMISSION = "permitir ubicacion";
    private static final String LOG_TAG ="HomeActivity";
    private Marker userMarker;
    private CompassOverlay mCompassOverlay;

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
    EditText searchdestination;
    @BindView(R.id.startnav)
    ImageButton startNav;
    @BindView(R.id.cancelnav)
    ImageButton cancelNav;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.markerdestination)
    ImageView markerDestination;        


    int carp, badsidewp, unevenp, obstaclp, pendp, stairp, nosdwp, normpp;

    //private MapView mMapView;
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
    NavigationView navigationView;
    String reporteTipo= null;
    ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
    //Toolbar toolbar;
    Marker startMarker = null;
    Marker markercheck = null;
    HorizontalScrollView scrollView=null;
    boolean banmark=false;
    Integer idmarker = null;
    IGeoPoint coord = null;
    List<Double> lista = new ArrayList<>();
    int counter  = 0;
    int mid = 0;
    HashMap<Object, Integer> meMap=new HashMap<Object, Integer>();
    HashMap<Integer, String> namereport = new HashMap<Integer, String>();
    HashMap<Integer, String> lastupt = new HashMap<Integer, String>();
    //RoadManager roadManager = new OSRMRoadManager(this);
    ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
    Marker markerToShare = null;
    GeoPoint destination = null;
    Boolean banddestination = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        Utils.setTileServerCredentials(this);
        ButterKnife.bind(this);
        setReportsPoiOnMap();
        setMapView();
        mActivityRecognitionClient = new ActivityRecognitionClient(this);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.REPORTS_POI, Context.MODE_PRIVATE);
        //sharedPreferences.getAll().clear();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

            final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

            if (getIntent().hasExtra("carBtnPoi")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                carBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("carBtnPoi"));
                carp = getIntent().getExtras().getInt("carBtnPoi");
                editor.putInt("CAR", carp);
                pendienteBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("pendienteBtnPoi"));
                pendp = getIntent().getExtras().getInt("pendienteBtnPoi");
                editor.putInt("PENDT", pendp);
                sidewalkBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("sidewalkBtnPoi"));
                badsidewp = getIntent().getExtras().getInt("sidewalkBtnPoi");
                editor.putInt("BADS", badsidewp);
                obstacleBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("obstacleBtnPoi"));
                obstaclp = getIntent().getExtras().getInt("obstacleBtnPoi");
                editor.putInt("OBSTC", obstaclp);
                streetBtnPoi.setBackgroundResource(getIntent().getExtras().getInt("streetBtnPoi"));
                unevenp = getIntent().getExtras().getInt("streetBtnPoi");
                editor.putInt("UNEV", unevenp);
                stairpoi.setBackgroundResource(getIntent().getExtras().getInt("stairpoi"));
                stairp = getIntent().getExtras().getInt("stairpoi");
                editor.putInt("STAIR", stairp);
                nosidewalkpoi.setBackgroundResource(getIntent().getExtras().getInt("nosidewalkpoi"));
                nosdwp = getIntent().getExtras().getInt("nosidewalkpoi");
                editor.putInt("NOSW", nosdwp);
                noramppoi.setBackgroundResource(getIntent().getExtras().getInt("noramppoi"));
                normpp = getIntent().getExtras().getInt("noramppoi");
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
                    banmark = false;
                    saveMarker();
                }
            });
            sharereport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(Utils.getTwitterIntent(HomeActivity.this, "aquí "+ reporteTipo + " %23smartMovingApp", startMarker.getPosition()));
                    saveMarker();

                    hideKeyboard(HomeActivity.this);
                }
            });
            searchdestination.setOnTouchListener(new View.OnTouchListener() {
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
                    toolbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    startNav.setVisibility(View.INVISIBLE);
                    cancelNav.setVisibility(View.INVISIBLE);
                }
            });
            cancelNav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toolbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    startNav.setVisibility(View.INVISIBLE);
                    cancelNav.setVisibility(View.INVISIBLE);
                    banddestination=false;
                }
            });

        if(getIntent().hasExtra("latt")) {
            toolbar.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
            startNav.setVisibility(View.VISIBLE);
            cancelNav.setVisibility(View.VISIBLE);


            //GeoPoint newdes = new GeoPoint(getIntent().getExtras().getDouble("latt"),getIntent().getExtras().getDouble("longg"));
            //Marker mmarker = new Marker(mMapView);
            //Marker markerToShare = new Marker(mMapView);
            banddestination = true;
           // mmarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            //markerToShare.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
          //  mmarker.setPosition(newdes);
           // markerToShare.setPosition(newdes);
         //   mmarker.setIcon(getResources().getDrawable(R.drawable.target));
           // markerToShare.setIcon(getResources().getDrawable(R.drawable.target));
            markerDestination.setVisibility(View.VISIBLE);
            mMapView.getOverlays().add(markerToShare);
            mLocationOverlay.disableFollowLocation();
            mMapView.invalidate();
            CameraPosition aCameraPosition = new CameraPosition.Builder().target(
                    new LatLng(getIntent().getExtras().getDouble("latt"), getIntent().getExtras().getDouble("longg"))).zoom(15).build();
            //mMapView.setAnimation(CameraUpdateFactory.newCameraPosition(aCameraPosition));
            final IGeoPoint mapCenter =new GeoPoint(getIntent().getExtras().getDouble("latt"), getIntent().getExtras().getDouble("longg"));

            mMapView.getController().setCenter(mapCenter);
            //mMapView.getMapCenter(newdes);
            //mLocationOverlay.disableFollowLocation();
            mMapView.invalidate();
        }





            final ImageButton.OnClickListener olongBtn = new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {

                    scrollView.setVisibility(View.INVISIBLE);
                    showToast();
                    sharereport.setVisibility(View.VISIBLE);
                    confirmReport.setVisibility(View.VISIBLE);
                    deleteReport.setVisibility(View.VISIBLE);
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setVisibility(View.VISIBLE);
                    switch (v.getId()) {
                        case R.id.curbramppoi:
                            createMarker(R.mipmap.wheelchairramp);
                            reporteTipo = "hay una rampa adecuada";
                            idmarker = 3;
                            break;
                        case R.id.bustoppoi:
                            createMarker(R.mipmap.accessiblebuspoi);
                            reporteTipo ="hay parada en que pasa bus accesible";
                            idmarker = 5;
                            break;
                        case R.id.pos2dra:
                            createMarker(pendp);
                            reporteTipo ="hay una pendiente";
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
                            reporteTipo ="hay un auto estacionado en la vereda";
                            idmarker = 6;
                            break;
                        case R.id.pos1dra:
                            createMarker(badsidewp);
                            reporteTipo ="la vereda esta deteriorada";
                            idmarker = 2;
                            break;
                        case R.id.pos7dra:
                            createMarker(stairp);
                            reporteTipo ="hay una escalera";
                            idmarker = 1;
                            break;
                        case R.id.pos8dra:
                            createMarker(normpp);
                            reporteTipo ="hay una rampa inadecuada o inexistente";
                            idmarker = 10;
                            break;
                        case R.id.pos6dra:
                            createMarker(nosdwp);
                            reporteTipo ="no existe vereda";
                            idmarker = 9;
                            break;

                    }
                }
            };

            searchdestination.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
            txtTitle.setText("Tipo de reporte: " + namereport.get(idmarker));
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
                .baseUrl(Constants.BASE_URL_HOME)
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
                    Utils.showToast("No hay conexion", HomeActivity.this);
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
                .baseUrl(Constants.BASE_URL_HOME)
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
                        Utils.showToast1("Gracias por colaborar!",HomeActivity.this);

                        break;
                    case 400:
                        Utils.showToast("Ya contribuyo con este reporte",HomeActivity.this);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onFailure(Call<CreateContributionPoi> call, Throwable t) {
                if(!Utils.isNetworkConnected(HomeActivity.this)){
                    Utils.showToast("No hay conexion", HomeActivity.this);
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
            lastupt.put(mid, reportPoi.getProperties().getModified());
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
        sharereport.setVisibility(View.INVISIBLE);
        tv1.setVisibility(View.INVISIBLE);tv2.setVisibility(View.INVISIBLE);tv3.setVisibility(View.INVISIBLE);
        showToast1();
        scrollView.setVisibility(View.VISIBLE);
        mMapView.invalidate();
        banmark = false;
    }

    private void saveMarker(){
        if(coord!=null && Utils.isNetworkConnected(HomeActivity.this)) {
            setReport();
            Toast toast = Toast.makeText(getApplicationContext(), "Creaste un Reporte!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastContentView = (LinearLayout) toast.getView();
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(R.drawable.smile1);
            toastContentView.addView(imageView, 0);
            toast.show();
            scrollView.setVisibility(View.VISIBLE);
            deleteReport.setVisibility(View.INVISIBLE);
            sharereport.setVisibility(View.INVISIBLE);
            confirmReport.setVisibility(View.INVISIBLE);
            tv1.setVisibility(View.INVISIBLE);tv2.setVisibility(View.INVISIBLE);tv3.setVisibility(View.INVISIBLE);

        }else{
            showToast();
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
                .baseUrl(Constants.BASE_URL_HOME)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SmartMovingAPI smartMovingAPI = retrofit.create(SmartMovingAPI.class);
        Call<ReportPoi> call = smartMovingAPI.setReportPoi("application/vnd.geo+json", reportPoiC);

        try {
            Response<ReportPoi> result = call.execute();
            Headers headers = ((Response) result).headers();
            if(result.code() == 201){

            }else{
                showToast1();
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
    private void showToast() {
        Toast toast = Toast.makeText(getApplicationContext(), "Presiona para colocar en el mapa", Toast.LENGTH_LONG);
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
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.CLIENTE_DATA, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        String message = stringBuffer.toString();

        switch (itemId)
        {
            case R.id.connectfacebook:

               Utils.connectToSocialNetwork(HomeActivity.this,"facebook");
                break;
            case R.id.connecttwiter:
                //startActivity(Utils.getTwitterIntent(HomeActivity.this, "aqui hay una escalera", mLocationOverlay.getMyLocationProvider().getLastKnownLocation()));

                break;
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
        //mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.REPORTS_POI, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("CAR",carp);
        editor.putInt("PENDT",pendp);
        editor.putInt("BADS",badsidewp);
        editor.putInt("OBSTC",obstaclp);
        editor.putInt("UNEV",unevenp);
        editor.putInt("STAIR",stairp);
        editor.putInt("NOSW",nosdwp);
        editor.putInt("NORMP",normpp);
        editor.apply();

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


