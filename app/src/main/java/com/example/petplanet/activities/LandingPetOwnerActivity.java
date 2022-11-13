package com.example.petplanet.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityLandingPetOwnerBinding;
import com.example.petplanet.models.Paseo;
import com.example.petplanet.models.Usuario;
import com.example.petplanet.utilities.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.directions.route.RoutingListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;


public class LandingPetOwnerActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, RoutingListener {
    private ActivityLandingPetOwnerBinding binding;
    public final static double RADIUS_OF_EARTH_KM = 6371;

    // Setup del logger para esta clase
    private static final String TAG = LandingPetOwnerActivity.class.getName();
    private Logger logger = Logger.getLogger(TAG);

    private GoogleMap mMap;
    private final static int INITIAL_ZOOM_LEVEL = 15;
    private Geocoder mGeocoder;

    private SensorManager sensorManager;
    Sensor lightSensor;
    private Sensor humSensor;
    private SensorEventListener lightSensorListener;
    private List<Polyline> polylines = null;

    public static final double lowerLeftLatitude = 4.4542324059959295;
    public static final double lowerLeftLongitude = -74.31798356566968;
    public static final double upperRightLatitude = 4.978316663093684;
    public static final double upperRightLongitude = -73.89683495545846;

    //Variables de permisos
    private final int LOCATION_PERMISSION_ID = 103;
    public static final int REQUEST_CHECK_SETTINGS = 201;
    String locationPerm = Manifest.permission.ACCESS_FINE_LOCATION;

    //Variables de localizacion
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    Location mCurrentLocation;
    Location aux;

    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    protected LatLng start = null;
    protected LatLng end = null;


    private float humActual;
    private SensorEventListener humSensorListener;

    //Variables de localizacion
    private FusedLocationProviderClient fusedLocationClient;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth mAuth;
    Usuario Client = new Usuario();
    Usuario Walker = new Usuario();

    private Paseo nPaseo = new Paseo();
    DatabaseReference myPaseos;
    DatabaseReference myRef;

    public String idpaseo;

    public String getIdpaseo() {
        return idpaseo;
    }

    public void setIdpaseo(String idpaseo) {
        this.idpaseo = idpaseo;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPetOwnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAuth = FirebaseAuth.getInstance();

        humActual = 0;
        binding.bottomNavigation.setBackground(null);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.cuidadores:
                    Intent intent = new Intent(getApplicationContext(), ListarCuidadoresActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.AgendarPaseoBTN:
                    Intent intent2 = new Intent(getApplicationContext(), AgendarPaseosActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.chat:
                    Intent intent3 = new Intent(getApplicationContext(), ListaDeChatsActivity.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.perfilA:
                    Intent intent4 = new Intent(getApplicationContext(), PerfilUsuarioActivity.class);
                    startActivity(intent4);
                    finish();
                    break;
            }
            return true;
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mLocationRequest = createLocationRequest();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapowner);
        mapFragment.getMapAsync(this);


        //Location

        requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, "El permiso es necesario para acceder a la localizacion", LOCATION_PERMISSION_ID);


        humSensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        humSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if (Math.abs(humActual - sensorEvent.values[0]) > 1) {
                    humActual = sensorEvent.values[0];
                    Log.d("Humedad", "Humedad: " + humActual);
                    if (humActual > 65) {
                        Toast.makeText(LandingPetOwnerActivity.this, "Cuidado puede llover, busca un paraguas!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LandingPetOwnerActivity.this, "Hace fresco, Relajao!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        // Initialize the sensors

        currentlocation();

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Initialize the listener
        lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (mMap != null) {
                    if (event.values[0] < 100) {
                        Log.i("MAPS", "DARK MAP " + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions
                                .loadRawResourceStyle(LandingPetOwnerActivity.this, R.raw.style_night));
                    } else {
                        Log.i("MAPS", "LIGHT MAP " + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions
                                .loadRawResourceStyle(LandingPetOwnerActivity.this, R.raw.style_day));
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        // Initialize geocoder
        mGeocoder = new Geocoder(getBaseContext());
    }

    private double currentLat = 0;
    private double currentLong = 0;

    public void currentlocation() {

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                aux = location;
                Log.d("asdasdasd", "Location update in the callback: " + location);
                if (location != null) {
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(INITIAL_ZOOM_LEVEL));
                    // Enable touch gestures
                    mMap.getUiSettings().setAllGesturesEnabled(true);
                    // UI controls

                    mMap.getUiSettings().setCompassEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    LatLng clatlng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(clatlng));
                    binding.progressmaps.setVisibility(View.GONE);

                    mCurrentLocation = location;

                    currentLat = location.getLatitude();
                    currentLong = location.getLongitude();
                }
            }
        };
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setBuildingsEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        // UI controls

        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (checkPermissions()) {
            mMap.setMyLocationEnabled(true);
            mMap.isMyLocationEnabled();


            // Set  zoom level

            myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
            myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Client = task.getResult().getValue(Usuario.class);
                }
            });
            myPaseos = database.getReference(Constants.PATH_PASEOS);
            myPaseos.getDatabase().getReference(Constants.PATH_PASEOS).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        nPaseo = snapshot.getValue(Paseo.class);
                        if (nPaseo.getNombredelowner() != null) {
                            if (Client.getNombre().equals(nPaseo.getNombredelowner())) {
                                if (nPaseo.getNombredelwalker() != null) {
                                    if (!nPaseo.getNombredelwalker().equals("pendiente")) {
                                        //mostrar imagen del walker y el nombre del walker y la distancia en la que esta y ademas se muestra la localizacion en tiempo real
                                        binding.coordinatorLayout.setVisibility(View.GONE);
                                        binding.cardpaseo.setVisibility(View.VISIBLE);
                                        setIdpaseo(snapshot.getKey());
                                        myRef = database.getReference(Constants.PATH_USERS);
                                        myRef.getDatabase().getReference(Constants.PATH_USERS + nPaseo.getUidWalker()).get().addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                Walker = task1.getResult().getValue(Usuario.class);
                                                binding.Nombrewalker.setText(Walker.getNombre());
                                                byte[] decodedString = Base64.decode(Walker.getFoto(), Base64.DEFAULT);
                                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                                binding.imagePerson.setImageBitmap(decodedByte);
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            });
            Log.d("asdasdasd", "Location update in the callback: " + getIdpaseo());


            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    aux = location;

                    if (location != null) {
                        mMap.moveCamera(CameraUpdateFactory.zoomTo(INITIAL_ZOOM_LEVEL));
                        // Enable touch gestures

                        LatLng clatlng = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(clatlng));

                        mCurrentLocation = location;

                        currentLat = location.getLatitude();
                        currentLong = location.getLongitude();
                        if (nPaseo.getNombredelwalker() != null) {
                            if (!nPaseo.getNombredelwalker().equals("pendiente")) {
                                Location dis3 = new Location("localizacion 1");
                                dis3.setLatitude(currentLat);  //latitud
                                dis3.setLongitude(currentLong); //longitud
                                Location dis2 = new Location("localizacion 2");
                                dis2.setLatitude(nPaseo.getLatitudwalker());  //latitud
                                dis2.setLongitude(nPaseo.getLongitudwalker()); //longitud
                                double distance = dis3.distanceTo(dis2);
                                DecimalFormat df = new DecimalFormat("#.##");
                                df.setRoundingMode(RoundingMode.FLOOR);

                                binding.distanciaTXT.setText(String.valueOf(df.format(distance)) + " metros");

                            }
                            if (!nPaseo.isYarecibielperro()) {
                                binding.entregarperroBTN.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            };


            binding.canelarpaseoBTN.setOnClickListener(view -> {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Deseas cancelar el paseo?")
                        .setPositiveButton("Si, deseo cancelar el paseo", (dialogInterface, i) -> {
                            myPaseos = database.getReference(Constants.PATH_PASEOS);
                            myPaseos.getDatabase().getReference(Constants.PATH_PASEOS).get().addOnCompleteListener(task -> {
                                task.getResult().getChildren().forEach(snapshot -> {
                                    nPaseo = snapshot.getValue(Paseo.class);

                                    if (nPaseo.getNombredelowner() != null) {
                                        if (Client.getNombre().equals(nPaseo.getNombredelowner())) {
                                            nPaseo.setId(snapshot.getKey());
                                            myPaseos = database.getReference(Constants.PATH_PASEOS + nPaseo.getId());
                                            myPaseos.getDatabase().getReference(Constants.PATH_PASEOS + nPaseo.getId()).get().addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    nPaseo = task1.getResult().getValue(Paseo.class);
                                                    if (nPaseo.isYatengoelperro()) {
                                                        new MaterialAlertDialogBuilder(this)
                                                                .setMessage("No puedes cancelar el paseo ya que el walker ya tiene el perro")
                                                                .setPositiveButton("Ok", (dialogInterface1, i1) -> {
                                                                    dialogInterface.dismiss();
                                                                });
                                                    }else{
                                                        myPaseos.removeValue().getResult().equals(nPaseo.getId());
                                                        binding.coordinatorLayout.setVisibility(View.VISIBLE);
                                                        binding.cardpaseo.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            });
                            mMap.clear();
                        })
                        .setNegativeButton("No cancelar", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })

                        .show();
            });



            binding.entregarperroBTN.setOnClickListener(v -> {
                myPaseos = database.getReference(Constants.PATH_PASEOS);
                myPaseos.getDatabase().getReference(Constants.PATH_PASEOS).get().addOnCompleteListener(task -> {
                    task.getResult().getChildren().forEach(snapshot -> {
                        nPaseo = snapshot.getValue(Paseo.class);
                        if (nPaseo.getNombredelowner() != null) {
                            if (Client.getNombre().equals(nPaseo.getNombredelowner())) {
                                myPaseos = database.getReference(Constants.PATH_PASEOS + snapshot.getKey());
                                myPaseos.getDatabase().getReference(Constants.PATH_PASEOS + snapshot.getKey()).get().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        nPaseo = task1.getResult().getValue(Paseo.class);
                                        if (nPaseo.isYallegoelpaseador()) {
                                            nPaseo.setYarecibielperro(true);
                                            myPaseos.setValue(nPaseo);
                                            binding.entregarperroBTN.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }
                        }
                    });
                });
            });

            binding.volveralmenuBTN.setOnClickListener(view -> {
                binding.coordinatorLayout.setVisibility(View.VISIBLE);
                binding.cardpaseo.setVisibility(View.GONE);
            });
            startLocationUpdates();
        }
    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions();
            return false;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(mLocationCallback);
    }


    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(3000)
                .setFastestInterval(500)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    public void Findroutes(LatLng Start, LatLng End) {
        if (Start == null || End == null) {
            Toast.makeText(LandingPetOwnerActivity.this, "Unable to get location", Toast.LENGTH_LONG).show();
        } else {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyCydYqy3SwNNILEiFKMZRyloqvEVUuTkFU")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }


    private void requestPermission(Activity context, String permiso, String justificacion, int idCode) {
        if (ContextCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permiso)) {
                Toast.makeText(context, justificacion, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permiso}, idCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_ID: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Ya hay permiso para acceder a la localizacion", Toast.LENGTH_LONG).show();
                    turnOnLocationAndStartUpdates();
                } else {
                    Toast.makeText(this, "Permiso de ubicacion denegado", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void turnOnLocationAndStartUpdates() {
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task =
                client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, locationSettingsResponse -> {
            startLocationUpdates(); //condiciones localizaciones
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(LandingPetOwnerActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. No way to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS: {
                if (resultCode == RESULT_OK) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "Sin acceso a localizacion", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        turnOnLocationAndStartUpdates();
        startLocationUpdates();
        sensorManager.registerListener(lightSensorListener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(humSensorListener,
                humSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
        sensorManager.unregisterListener(lightSensorListener);
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //Routing call back functions.
    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
//        Findroutes(start,end);
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(LandingPetOwnerActivity.this, "Finding Route...", Toast.LENGTH_LONG).show();
    }

    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if (polylines != null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i < route.size(); i++) {

            if (i == shortestRouteIndex) {
                polyOptions.color(getResources().getColor(R.color.purple_500));
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng = polyline.getPoints().get(0);
                int k = polyline.getPoints().size();
                polylineEndLatLng = polyline.getPoints().get(k - 1);
                polylines.add(polyline);

            } else {

            }

        }


    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(start, end);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Findroutes(start, end);

    }


}
