package smarttraffic.smartmoving.activities;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import smarttraffic.smartmoving.R;
import smarttraffic.smartmoving.Utils;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.wang.avi.AVLoadingIndicatorView;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.simpleListView)
    ListView list_address;
    @BindView(R.id.search_et)
    EditText search_et;
    ArrayList<String> address = new ArrayList<>();
    @BindView(R.id.button_search)
    Button btnSearch;
    private ArrayAdapter adapter;
    Double latt=null;
    Double longg=null;
    int band = 0;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search1);
        ButterKnife.bind(this);
        Context mContext;
        InputMethodManager inputMethodManager =  (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(search_et,InputMethodManager.SHOW_IMPLICIT);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!search_et.getText().toString().isEmpty()) {
                    List<Address> strings = findAddresses(search_et.getText().toString());
                    if(!strings.isEmpty()) {
                        latt = strings.get(0).getLatitude();
                        longg = strings.get(0).getLongitude();
                        Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                        intent.putExtra("lat", latt);
                        intent.putExtra("long", longg);
                        startActivity(intent);
                    }else{
                        Utils.showToast("Error", SearchActivity.this);
                    }
                }else{
                    Utils.showToast("Error", SearchActivity.this);
                }
            }
        });
        list_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String stringS = address.get(i);
                List<Address> finalloc = findAddresses(stringS);
                latt = finalloc.get(0).getLatitude();
                longg=finalloc.get(0).getLongitude();
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                intent.putExtra("latt",latt);
                intent.putExtra("longg",longg);
                startActivity(intent);
            }
        });
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()>2) {
                    band =1;

                    ArrayList<Address> ad = findAddresses(search_et.getText().toString());

                    if(!ad.isEmpty()) {
                        address.add(ad.get(0).getFeatureName());
                    }
                    //address.add(ad.get(0).getCountryName());
                    adapter = new ArrayAdapter(SearchActivity.this, R.layout.activity_search,R.id.list_search,address);
                    list_address.setAdapter(adapter);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()>3 && findAddresses(search_et.getText().toString())!=null) {
                    (SearchActivity.this).adapter.getFilter().filter(str);
                }

            }
        });




    }

    private ArrayList<Address> findAddresses(String address) {
        Geocoder geocoder = new Geocoder(SearchActivity.this);
        try {
            address += ", Asuncion Paraguay";
            return (ArrayList<Address>) geocoder.getFromLocationName(address, 10);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public LatLng getLocation(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }

        return p1;
    }


    public String getCompleteAddress(double latitude, double longitude) {
        String location = "";
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String state, city, zip, street;
                if (address.getAdminArea() != null) {
                    state = address.getAdminArea();
                } else {
                    state = "";
                }
                if (address.getLocality() != null) {
                    city = address.getLocality();
                } else {
                    city = "";
                }
                if (address.getPostalCode() != null) {
                    zip = address.getPostalCode();
                } else {
                    zip = "";
                }

                if (address.getThoroughfare() != null) {
                    street = address.getSubLocality() + "," + address.getThoroughfare();
                } else {
                    street = address.getSubLocality() + "," + address.getFeatureName();
                }
                location = street + "," + city + "," + zip + "," + state;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return location;
    }

    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress + ", Paraguay", 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            return lat + "," + lng;
        } catch (Exception e) {
            return null;
        }
    }

}
