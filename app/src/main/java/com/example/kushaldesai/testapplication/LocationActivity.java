package com.example.kushaldesai.testapplication;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kushaldesai.HttpConnection.HttpConnection;
import com.example.kushaldesai.HttpConnection.OnTaskCompleted;
import com.example.kushaldesai.Parser.DataObject;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by kushal.desai on 3/3/2016.
 */
public class LocationActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Spinner spinner;
    private TextView modeT1, modeT2;
    private Button navigate;
    private ProgressBar progressIndicator;
    private GoogleApiClient googleApiClient;
    private SupportMapFragment mSupportMapFragment;
    private GoogleMap googleMap;
    private int positionSelected=0;
    private LinearLayout llMain;
    private ScrollView llBase;
    private ArrayList<DataObject> dataObjects;
    private final String url = "http://express-it.optusnet.com.au/sample.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && !savedInstanceState.getParcelableArrayList("Obj").isEmpty()) {
            dataObjects = savedInstanceState.getParcelableArrayList("Obj");
            llMain.setVisibility(View.VISIBLE);
            progressIndicator.setVisibility(View.GONE);
            spinner.setAdapter(new ArrayAdapter(LocationActivity.this, android.R.layout.simple_dropdown_item_1line, getName(dataObjects)));
        }
    }

    @Override
    public void initialize() {
        llBase = (ScrollView) inflater.inflate(R.layout.show_location, null);
        llBaseMid.addView(llBase);
        toolbar.setVisibility(View.VISIBLE);
        dataObjects = new ArrayList<DataObject>();
        defineView();

        initializeMap();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();

        new HttpConnection(this, url, new OnTaskCompleted() {
            @Override
            public void onTaskStarted() {
                // showloader
                progressIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTaskCompleted(Object result) {
                //hideLoader
                if (result != null) {
                    try {
                        dataObjects = (ArrayList<DataObject>) result;
                        llMain.setVisibility(View.VISIBLE);
                        progressIndicator.setVisibility(View.GONE);
                        spinner.setAdapter(new ArrayAdapter(LocationActivity.this, android.R.layout.simple_dropdown_item_1line, getName(dataObjects)));
                    } catch (Exception e) {
                        Toast.makeText(LocationActivity.this, "There seems some data connectivity issue, Please try later !", Toast.LENGTH_SHORT).show();
                        progressIndicator.setVisibility(View.GONE);
                        finish();
                        e.printStackTrace();
                    }
                } else {
                    finish();
                    progressIndicator.setVisibility(View.GONE);
                    Toast.makeText(LocationActivity.this, "There seems some data connectivity issue, Please try later !", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }


    private void defineView() {
        CustomSizes  customSizes = new CustomSizes(getResources().getDisplayMetrics());
        spinner = (Spinner) findViewById(R.id.spinner);
        modeT1 = (TextView) findViewById(R.id.modeTransport);
        modeT2 = (TextView) findViewById(R.id.modeTransportOther);
        navigate = (Button) findViewById(R.id.navigate);
        llMain = (LinearLayout) findViewById(R.id.ll_main);
        progressIndicator = (ProgressBar) findViewById(R.id.progressIndicator);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            navigate.getLayoutParams().height = customSizes.getRectViewHeightSize(210);
            spinner.getLayoutParams().height = customSizes.getRectViewHeightSize(120);

        }else{
            navigate.getLayoutParams().height = customSizes.getRectViewHeightSize(130);
            spinner.getLayoutParams().height = customSizes.getRectViewHeightSize(120);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (dataObjects.get(position).modeOfTransport != null && dataObjects.get(position).modeOfTransport.length() > 1) {
                    modeT1.setVisibility(View.VISIBLE);
                    modeT1.setText("Car - " + dataObjects.get(position).modeOfTransport);
                } else {
                    modeT1.setVisibility(View.GONE);
                }
                if (dataObjects.get(position).modeOfTransportTrain != null && dataObjects.get(position).modeOfTransportTrain.length() > 1) {
                    modeT2.setVisibility(View.VISIBLE);
                    modeT2.setText("Train - " + dataObjects.get(position).modeOfTransportTrain);
                } else {
                    modeT2.setVisibility(View.GONE);
                }
                positionSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataObjects!=null && dataObjects.size()>0) {
                    moveCameraPositionToLatLong(new LatLng(dataObjects.get(positionSelected).lat, dataObjects.get(positionSelected).lng), 13.0f);
                }
            }
        });
    }

    private ArrayList<String> getName(ArrayList<DataObject> dataObject) {
        ArrayList<String> tempData = new ArrayList<String>();
        for (int i = 0; i < dataObject.size(); i++) {
            tempData.add(dataObject.get(i).name);
        }
        return tempData;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("Obj", dataObjects);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (googleApiClient.isConnected()) {
            PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                    .getCurrentPlace(googleApiClient, null);
            result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                        double fromLat = placeLikelihood.getPlace().getLatLng().latitude;
                        double fromLng = placeLikelihood.getPlace().getLatLng().longitude;
                        if(googleMap != null)
                        {
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(fromLat, fromLng)));
                            googleMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));
                        }
                        break;
                    }
                    likelyPlaces.release();
                }
            });
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void initializeMap()
    {
        if(mSupportMapFragment == null)
        {
            mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.find_location_search);

            if(mSupportMapFragment != null)
            {
                googleMap = mSupportMapFragment.getMap();
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.getUiSettings().setZoomControlsEnabled(false);
                googleMap.getUiSettings().setCompassEnabled(false);
                googleMap.getUiSettings().setRotateGesturesEnabled(false);
                googleMap.getUiSettings().setZoomGesturesEnabled(true);
            }
        }
    }

    private void moveCameraPositionToLatLong(LatLng latLng, float zoomLevel) {
        if (googleMap != null) {
            CameraPosition myLocation = null;
            myLocation = new CameraPosition.Builder()
                    .target(new LatLng(latLng.latitude, latLng.longitude))
                    .zoom(zoomLevel).bearing(0).tilt(0).build();

            googleMap.moveCamera(CameraUpdateFactory
                    .newCameraPosition(myLocation));

            googleMap.addMarker(new MarkerOptions().position(
                    new LatLng(latLng.latitude, latLng.longitude)).icon(
                    BitmapDescriptorFactory
                            .fromResource(R.drawable.locationpin_icon)));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }
}
