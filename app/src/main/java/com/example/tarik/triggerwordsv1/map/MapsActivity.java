package com.example.tarik.triggerwordsv1.map;

import android.Manifest;
import com.example.tarik.triggerwordsv1.ActionMenu.MenuActivity;
import com.example.tarik.triggerwordsv1.R;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnMarkerClickListener {


    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private LocationRequest mLocationRequest;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private int buttonTag;
    private Marker preMarker;

    //....
    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 30000;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    //private PlaceAutocompleteAdapter mAdapter;

    //   private AutoCompleteTextView mAutocompleteView;

    private TextView mPlaceDetailsText;

    private TextView mPlaceTitle;


    private TextView mText;

    private ImageView mImageView;

    private View bottomPanel;

    private BottomSheetBehavior behavior;

    // set the progress bar time

    public ProgressBar progressbar;

    private SupportMapFragment mapFragment;
//
//    private int progressStatus = 0;
//
//    private Handler mHandler = new Handler();
//
//    private static final int PROGRESS = 0x1;








    //private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
    // new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));


    @Override
    protected void onCreate (Bundle savedInstanceState){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        /*if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        }
        else {
            Log.d("onCreate","Google Play Services available.");
        }*/


        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1678);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .addApi(Places.GEO_DATA_API)
//                .build();
//
//        //....set api client connection
//        mGoogleApiClient.connect();


        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)        // 10 seconds, in milliseconds
                .setFastestInterval(2000); // 1 second, in milliseconds

        //....
        // mAutocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete);

        //   mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
        // mPlaceDetailsAttribution = (TextView) findViewById(R.id.place_attribution);
        //mPlaceTitle = (TextView) findViewById(R.id.place_title);

        bottomPanel = findViewById(R.id.bottom_sheet);

        behavior = BottomSheetBehavior.from(bottomPanel);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_DRAGGING");

                        ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
                        int x;
                        x = dpToPx(580);
                        //x = dpToPx(1128);
                        params.height = x;
                        mapFragment.getView().setLayoutParams(params);

                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_SETTLING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:

                        if (preMarker != null) {
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 500, null);

                        }

                        params = mapFragment.getView().getLayoutParams();
                        x = dpToPx(170);
                        //x = dpToPx(710);
                        params.height = x;
                        mapFragment.getView().setLayoutParams(params);


                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        if (preMarker != null) {
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(11), 500, null);

                        }

                        params = mapFragment.getView().getLayoutParams();
                        x = dpToPx(580);
                       // x = dpToPx(1128);
                        params.height = x;
                        mapFragment.getView().setLayoutParams(params);

                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_HIDDEN");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("BottomSheetCallback", "slideOffset: " + slideOffset);
            }
        });

        //.....set adapter
        //mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
        //null);
//        mAutocompleteView.setAdapter(mAdapter);

        mImageView = (ImageView) findViewById(R.id.mImageView);

        mText = (TextView) findViewById(R.id.mText);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        //buildGoogleApiClient();



    }






    //    private AdapterView.OnItemClickListener mAutocompleteClickListener
//            = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            /*
//             Retrieve the place ID of the selected item from the Adapter.
//             The adapter stores each Place suggestion in a AutocompletePrediction from which we
//             read the place ID and title.
//              */
//            final AutocompletePrediction item = mAdapter.getItem(position);
//            final String placeId = item.getPlaceId();
//            final CharSequence primaryText = item.getPrimaryText(null);
//
//            Log.i(TAG, "Autocomplete item selected: " + primaryText);
//
//            /*
//             Issue a request to the Places Geo Data API to retrieve a Place object with additional
//             details about the place.
//              */
//            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
//                    .getPlaceById(mGoogleApiClient, placeId);
//            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
//
//            Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
//                    Toast.LENGTH_SHORT).show();
//            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
//        }
//    };
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            // Format details of the place for display and show it in a TextView.
            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));

            // Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
//                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
//                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
//                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence name,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name,  address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, address, phoneNumber,
                websiteUri));

    }


    //Check google play services
    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result, 0).show();
            }
            return false;
        }
        return true;
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, INSERT_ID, 0, R.string.menu_insert);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:

                return true;
            case DELETE_ID:

                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);

        //mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.setBuildingsEnabled(true);

        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);

        /*Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        double currentlatitude = location.getLatitude();
        double currentlongtitude = location.getLongitude();



        LatLng latLng = new LatLng(currentlatitude,currentlongtitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,8));*/



        //LatLng latLng2 = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2,4));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);

            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);

        }

        final Button btnRestaurant = (Button) findViewById(R.id.btnOrganisation);
        final Button btnHospital = (Button) findViewById(R.id.btnHospital);
        final Button btnSchool = (Button) findViewById(R.id.btnSchool);


        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            //String type1 = "hospital";
            String keyDOrganisation = "kids+organisation";
            @Override
            public void onClick(View v) {
                if (buttonTag == 1) {
                    return;
                }
                //progressbar.setVisibility(View.VISIBLE);

                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(latitude, longitude, keyDOrganisation);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                // Toast.makeText(MapsActivity.this,"Nearby Organisation", Toast.LENGTH_LONG).show();

                mMap.animateCamera(CameraUpdateFactory.zoomTo(11), 2000, null);
                buttonTag = 1;
                btnRestaurant.setTextColor(getResources().getColor(R.color.white));
                btnSchool.setTextColor(getResources().getColor(R.color.black_semi_transparent));
                btnHospital.setTextColor(getResources().getColor(R.color.black_semi_transparent));

                preMarker = null;

            }
        });

        btnHospital.setOnClickListener(new View.OnClickListener() {
            // String type2 = "hospital";
            String keyDHospital = "dyslexia";

            @Override
            public void onClick(View v) {
                if (buttonTag == 2) {
                    return;
                }
                //progressbar.setVisibility(View.VISIBLE);
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(latitude, longitude, keyDHospital);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                //Toast.makeText(MapsActivity.this,"Nearby Hospitals", Toast.LENGTH_LONG).show();

                mMap.animateCamera(CameraUpdateFactory.zoomTo(11), 2000, null);
                buttonTag = 2;
                btnHospital.setTextColor(getResources().getColor(R.color.white));
                btnRestaurant.setTextColor(getResources().getColor(R.color.black_semi_transparent));
                btnSchool.setTextColor(getResources().getColor(R.color.black_semi_transparent));


                preMarker = null;

            }
        });

        btnSchool.setOnClickListener(new View.OnClickListener() {

            // String type3 = "school";
            String keyDSchool = "learning+disability";
            @Override
            public void onClick(View v) {
                if (buttonTag == 3) {
                    return;
                }
                // progressbar.setVisibility(View.VISIBLE);

                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                String url = getUrl(latitude, longitude, keyDSchool);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                //Toast.makeText(MapsActivity.this,"Nearby Schools", Toast.LENGTH_LONG).show();
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11), 2000, null);
                buttonTag = 3;
                btnSchool.setTextColor(getResources().getColor(R.color.white));
                btnRestaurant.setTextColor(getResources().getColor(R.color.black_semi_transparent));
                btnHospital.setTextColor(getResources().getColor(R.color.black_semi_transparent));


                preMarker = null;


            }
        });




        //setUpMap();

        // Add a marker in Sydney and move the camera
//        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
        //..marker listener

        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);


    }



    @Override
    public void onMapClick(LatLng arg0) {

        // TODO Auto-generated method stub
        Log.d("arg0", arg0.latitude + "-" + arg0.longitude);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.w("Click", "test");
        // Log.d("Market test", marker.getTitle());
        String placeId = marker.getSnippet();
        //,,,,
            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                .getPlaceById(mGoogleApiClient, placeId);
        placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);

        //mPlaceTitle.setText(marker.getTitle());

//        if (marker.isInfoWindowShown()) {
//            marker.hideInfoWindow();
//        } else {
//            marker.showInfoWindow();
//        }
//
        if (preMarker != null) {
            preMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        }
        if (marker != null) {
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }

        placePhotosTask(placeId);
        placePhotosAsync(placeId);

        mMap.getUiSettings().setMapToolbarEnabled(true);

        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()), 2000, null);

        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        preMarker = marker;

        //change the map animation





        return false;
    }


    /**
     * convert dp to px
     * @param dp
     * @return
     */
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


   /*private void setUpMap() {

       if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           // TODO: Consider calling
            //    ActivityCompat#requestPermissions
           // here to request the missing permissions, and then overriding
             // public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                    //int[] grantResults)
             //to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
       }
       mMap.setMyLocationEnabled(true);

    }*/



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Location services connected.");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        handleNewLocation(location);
        //.....
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }



    }

    //....
    private String getUrl(double latitude, double longitude, String name) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        //googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&keyword=" + name);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyCN13FCxPHweUuIpi-EBtG9O2StnKXSaXk");
        Log.d("getUrl", googlePlacesUrl.toString());


        return (googlePlacesUrl.toString());
    }




    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }

  /*  @Override
  protected void onResume() {
        super.onResume();
       //setUpMapIfNeeded();
        mGoogleApiClient.connect();

       //....
    }

    @Override
    protected void onPause() {
        super.onPause();
      if (mGoogleApiClient.isConnected()) {

           mGoogleApiClient.disconnect();
       }
   }
*/
//    public double getDistance(double a,double b) {
//        float[] results = new float[1];
//        Location.distanceBetween(a, b, -37.877,145.044, results);
//        return results[0];
//
//    }


    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

       // double currentLatitude = -37.884;
       // double currentLongitude = 145.027;

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
        /*MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);*/
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,8));

    }


    @Override
    public void onLocationChanged(Location location) {
//        mMap.clear();
//        handleNewLocation(location);
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        // mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), 2000, null);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        //Toast.makeText(MapsActivity.this,"Your Current Location", Toast.LENGTH_LONG).show();

        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f",latitude,longitude));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");

        buttonTag = 0;

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user asynchronously -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    //handle the photo
    private void placePhotosTask(String placeId) {
        // Australian Cruise Group
        int width = mImageView.getLayoutParams().width;
        int height = mImageView.getLayoutParams().height;

        // Create a new AsyncTask that displays the bitmap and attribution once loaded.
        new PhotoTask(width, height, mGoogleApiClient) {
            @Override
            protected void onPreExecute() {
                // Display a temporary image to show while bitmap is loading.
                mImageView.setImageResource(R.drawable.finalbackground);
            }

            @Override
            protected void onPostExecute(AttributedPhoto attributedPhoto) {
                if (attributedPhoto != null) {
                    // Photo has been loaded, display it.
                    mImageView.setImageBitmap(attributedPhoto.bitmap);

                    // Display the attribution as HTML content if set.
                    if (attributedPhoto.attribution == null) {
                        mText.setVisibility(View.GONE);
                    } else {
                        mText.setVisibility(View.VISIBLE);
                        mText.setText(Html.fromHtml(attributedPhoto.attribution.toString()));
                    }

                }
            }
        }
                .execute(placeId);
    }

    private ResultCallback<PlacePhotoResult> mDisplayPhotoResultCallback
            = new ResultCallback<PlacePhotoResult>() {
        @Override
        public void onResult(PlacePhotoResult placePhotoResult) {
            if (!placePhotoResult.getStatus().isSuccess()) {
                //...
                return;
            }
            mImageView.setImageBitmap(placePhotoResult.getBitmap());
            //...
        }
    };

    /**
     * Load a bitmap from the photos API asynchronously
     * by using buffers and result callbacks.
     */
    private void placePhotosAsync(String placeId) {
        //final String placeId = "ChIJrTLr-GyuEmsRBfy61i59si0"; // Australian Cruise Group
        Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {


                    @Override
                    public void onResult(PlacePhotoMetadataResult photos) {
                        if (!photos.getStatus().isSuccess()) {
                            return;
                        }

                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                        if (photoMetadataBuffer.getCount() > 0) {
                            // Display the first bitmap in an ImageView in the size of the view
                            photoMetadataBuffer.get(0)
                                    .getScaledPhoto(mGoogleApiClient, mImageView.getWidth(),
                                            mImageView.getHeight())
                                    .setResultCallback(mDisplayPhotoResultCallback);
                        }
                        photoMetadataBuffer.release();
                    }
                });




        //try to use zoom control to control progress bar


    }

//     mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener(){
//        @Override
//        public void onCameraMoveStarted(int i) {
//            progressbar.setVisibility(View.GONE);
//        }
//    });

    @Override
    public void onCameraIdle() {
//        Toast.makeText(this, "The camera has stopped moving.",
//                Toast.LENGTH_SHORT).show();

        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void onCameraMoveStarted(int reason) {

        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            progressbar.setVisibility(View.VISIBLE);
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            progressbar.setVisibility(View.VISIBLE);
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            progressbar.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onBackPressed() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
            int x;
            x = dpToPx(580);
           // x = dpToPx(1128);
            params.height = x;
            mapFragment.getView().setLayoutParams(params);
        }
        else {
            Intent setIntent = new Intent(this, MenuActivity.class);
            startActivity(setIntent);
        }
    }
}