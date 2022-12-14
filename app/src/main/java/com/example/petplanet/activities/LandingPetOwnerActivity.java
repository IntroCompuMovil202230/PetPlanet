package com.example.petplanet.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityLandingPetOwnerBinding;
import com.example.petplanet.models.Paseo;
import com.example.petplanet.models.Perro;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SuppressLint("MissingPermission")
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
    DatabaseReference myUserRef;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    protected LatLng start = null;
    protected LatLng end = null;


    private String idpa;

    public String getIdpa() {
        return idpa;
    }

    public void setIdpa(String idpa) {
        this.idpa = idpa;
    }

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
    DatabaseReference myOwner;
    DatabaseReference mywalker;
    public String idpaseo;

    public String getIdpaseo() {
        return idpa;
    }

    public void setIdpaseo(String idpaseo) {
        this.idpa = idpaseo;
    }

    Marker petwalker;


    Paseo paseoxx = new Paseo();
    ArrayList<Paseo> paseoxd = new ArrayList<>();
    ArrayList<Perro> prueba = new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPetOwnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAuth = FirebaseAuth.getInstance();


        myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task2 -> {
            if (task2.isSuccessful()) {
                Client = task2.getResult().getValue(Usuario.class);
            } else {
                Toast.makeText(getApplicationContext(), "Error al actualizar el token de perfil", Toast.LENGTH_SHORT).show();
            }
        });


        myOwner = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
        myOwner.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task2 -> {
            if (task2.isSuccessful()) {
                Client = task2.getResult().getValue(Usuario.class);
            } else {
                Toast.makeText(getApplicationContext(), "Error al actualizar el token de perfil", Toast.LENGTH_SHORT).show();
            }
        });

        myUserRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
        myUserRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).child("paseosterminados").get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                task1.getResult().getChildren().forEach(paseo -> {
                    paseoxx = paseo.getValue(Paseo.class);
                    paseoxd.add(paseoxx);
                });
            }
        });


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Log.e("asdasdasdasd", "Failed to get the token.");
                return;
            }

            //get the token from task
            String token = task.getResult();
            Log.d("asdasdasdasd", "Token2 : " + Client.getFcmToken());
            myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
            myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task2 -> {
                if (task2.isSuccessful()) {
                    Client = task2.getResult().getValue(Usuario.class);
                    Client.setFcmToken(token);
                    if (Client.getPerros() != null) {
                        if (Client.getPerros().size() > 0) {
                            prueba.addAll(Client.getPerros());
                            Client.setPerros(prueba);
                        }
                    }
                    myRef.setValue(Client);
                } else {
                    Toast.makeText(getApplicationContext(), "Error al actualizar el token de perfil", Toast.LENGTH_SHORT).show();
                }
            });
        }).addOnFailureListener(e -> Log.e("asdasdasdasd", "Failed to get the token : " + e.getLocalizedMessage()));


        humActual = 0;
        binding.bottomNavigation.setBackground(null);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.cuidadores:
                    Intent intent = new Intent(getApplicationContext(), ListaPaseosActivity.class);
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
                        new MaterialAlertDialogBuilder(LandingPetOwnerActivity.this)
                                .setTitle("posiblemente va a llover")
                                .setMessage("Si vas a salir lleva un paraguas")
                                .setPositiveButton("ok", (dialogInterface, i) -> {
                                })
                                .setNeutralButton("Cancelar", (dialogInterface, i) -> {
                                })
                                .show();

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


            SystemClock.sleep(100);
            myPaseos = database.getReference(Constants.PATH_PASEOS);
            myPaseos.getDatabase().getReference(Constants.PATH_PASEOS).get().addOnCompleteListener(task -> {
                task.getResult().getChildren().forEach(snapshot -> {
                    nPaseo = snapshot.getValue(Paseo.class);
                    if (nPaseo.getUidOwner() != null) {
                        if (nPaseo.getUidOwner().equals(mAuth.getCurrentUser().getUid()) && nPaseo.getUidWalker() != null) {
                            binding.paseoencursoBTN.setVisibility(View.VISIBLE);
                            setIdpa(snapshot.getKey());
                        }
                    }
                });
            });


            myPaseos = database.getReference(Constants.PATH_PASEOS);
            myPaseos.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        nPaseo = dataSnapshot.getValue(Paseo.class);

                        if (nPaseo.getNombredelowner() != null) {
                            if (Client.getNombre() != null) {
                                if (Client.getNombre().equals(nPaseo.getNombredelowner())) {
                                    binding.paseoencursoBTN.setVisibility(View.GONE);
                                    if (nPaseo.getNombredelwalker() != null) {
                                        if (!nPaseo.getNombredelwalker().equals("pendiente") && nPaseo.getUidWalker() != null) {
                                            binding.paseoencursoBTN.setVisibility(View.GONE);
                                            // setIdpa(snapshot.getKey());
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

                                            if (!nPaseo.isYarecibielperro()) {
                                                binding.entregarperroBTN.setVisibility(View.VISIBLE);
                                                binding.canelarpaseoBTN.setVisibility(View.GONE);
                                            } else {
                                                binding.entregarperroBTN.setVisibility(View.GONE);
                                            }
                                            if (nPaseo.isEntregadelperro()) {
                                                binding.recibirperroBTN.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        if (nPaseo.getNombredelwalker().equals("pendiente")) {
                                            binding.coordinatorLayout.setVisibility(View.VISIBLE);
                                            binding.cardpaseo.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

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
                                binding.paseoencursoBTN.setVisibility(View.GONE);
                                myPaseos = database.getReference(Constants.PATH_PASEOS);
                                myPaseos.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            Paseo paseo = dataSnapshot.getValue(Paseo.class);

                                            if (paseo.getUidOwner().equals(mAuth.getCurrentUser().getUid())) {
                                                if (paseo.getUidWalker() != null) {
                                                    if (paseo.getUidWalker().equals(nPaseo.getUidWalker())) {
                                                        Location dis3 = new Location("localizacion 1");
                                                        dis3.setLatitude(currentLat);  //latitud
                                                        dis3.setLongitude(currentLong); //longitud
                                                        Location dis2 = new Location("localizacion 2");
                                                        dis2.setLatitude(paseo.getLatitudwalker());  //latitud
                                                        dis2.setLongitude(paseo.getLongitudwalker()); //longitud
                                                        double distance = dis3.distanceTo(dis2);
                                                        DecimalFormat df = new DecimalFormat("#.##");
                                                        df.setRoundingMode(RoundingMode.FLOOR);
                                                        if (petwalker != null) {
                                                            petwalker.remove();
                                                        }
                                                        Log.d("asdasdasd", "Location update in the callback: " + nPaseo.getId());
                                                        petwalker = mMap.addMarker(new MarkerOptions()
                                                                .position(new LatLng(paseo.getLatitudwalker(), paseo.getLongitudwalker()))
                                                                .title("Walker")
                                                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.petmap_foreground)));
                                                        petwalker.showInfoWindow();
                                                        binding.distanciaTXT.setText(String.valueOf(df.format(distance)) + " metros");

                                                    }
                                                    if (paseo.getUidWalker().equals("pendiente")) {
                                                        if (petwalker != null) {
                                                            petwalker.remove();
                                                        }
                                                        binding.cardpaseo.setVisibility(View.GONE);
                                                        binding.coordinatorLayout.setVisibility(View.VISIBLE);
                                                    }
                                                }

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }

                        }
                    }
                }
            };


            binding.recibirperroBTN.setOnClickListener(view -> {

                Log.d("asdasdasd", "Location update in the callback: " + getIdpa());

                myPaseos = database.getReference(Constants.PATH_PASEOS + getIdpa());
                myPaseos.getDatabase().getReference(Constants.PATH_PASEOS + getIdpa()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Paseo paseo = task.getResult().getValue(Paseo.class);
                        if (paseo.getNombredelowner() != null) {
                            if (Client.getNombre().equals(paseo.getNombredelowner())) {
                                paseo.setSeacaboelpaseo(true);
                                paseoxd.add(paseo);
                                myPaseos.setValue(paseo);
                            }
                        }
                        binding.recibirperroBTN.setVisibility(View.GONE);
                        binding.entregarperroBTN.setVisibility(View.GONE);
                    }
                });

                crearpaseoterminado();


                myPaseos = database.getReference(Constants.PATH_PASEOS + getIdpa());
                myPaseos.getDatabase().getReference(Constants.PATH_PASEOS + getIdpa()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Paseo paseo = task.getResult().getValue(Paseo.class);
                        myPaseos.removeValue();
                        startActivity(new Intent(getApplicationContext(), LandingPetWalkerActivity.class));
                        finish();
                    }
                });

            });


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
                                                    } else {
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
            binding.paseoencursoBTN.setOnClickListener(view -> {
                myPaseos = database.getReference(Constants.PATH_PASEOS + getIdpa());
                myPaseos.getDatabase().getReference(Constants.PATH_PASEOS + getIdpa()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            nPaseo = snapshot.getValue(Paseo.class);
                            Log.d("Paseo", "cargarpaseo: " + nPaseo.getDirecciondelowner());

                            if (nPaseo.getNombredelwalker() != null) {
                                if (nPaseo.getUidOwner().equals(mAuth.getCurrentUser().getUid()) && nPaseo.getUidWalker() != null) {
                                    binding.cardpaseo.setVisibility(View.VISIBLE);
                                    binding.paseoencursoBTN.setVisibility(View.GONE);
                                    binding.coordinatorLayout.setVisibility(View.GONE);
                                    byte[] decodedString = Base64.decode(nPaseo.getFotodelperro(), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    binding.imagePerson.setImageBitmap(decodedByte);
                                    binding.Nombrewalker.setText(nPaseo.getNombredelwalker());
                                }
                            }
                        }
                    }
                });
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
                binding.paseoencursoBTN.setVisibility(View.VISIBLE);
                binding.cardpaseo.setVisibility(View.GONE);
                binding.entregarperroBTN.setVisibility(View.GONE);
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


    Usuario nUp = new Usuario();
    Usuario wal = new Usuario();

    private void crearpaseoterminado() {

        Log.d("putoelquelolea", "registrarperro: " + paseoxd.size());
        if (paseoxd.size() == 0) {

            myOwner = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
            myOwner.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    nUp = task.getResult().getValue(Usuario.class);
                    prueba.addAll(nUp.getPerros());
                    nUp.setPerros(prueba);
                    nUp.setPaseosterminados(paseoxd);
                    myOwner.setValue(nUp);
                } else {
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            });
            mywalker = database.getReference(Constants.PATH_USERS + nPaseo.getUidWalker());
            mywalker.getDatabase().getReference(Constants.PATH_USERS + nPaseo.getUidWalker()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    wal = task.getResult().getValue(Usuario.class);
                    wal.setPaseosterminados(paseoxd);
                    wal.setPaseoencurso(false);
                    mywalker.setValue(wal);
                } else {
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            myOwner = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
            myOwner.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    nUp = task.getResult().getValue(Usuario.class);
                    prueba.addAll(nUp.getPerros());
                    nUp.setPerros(prueba);

                    nUp.setPaseosterminados(paseoxd);
                    myOwner.setValue(nUp);
                } else {
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            });
            mywalker = database.getReference(Constants.PATH_USERS + nPaseo.getUidWalker());
            mywalker.getDatabase().getReference(Constants.PATH_USERS + nPaseo.getUidWalker()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    wal = task.getResult().getValue(Usuario.class);
                    wal.setPaseoencurso(false);
                    wal.setPaseosterminados(paseoxd);
                    mywalker.setValue(wal);
                } else {
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            });

        }
        binding.recibirperroBTN.setVisibility(View.GONE);

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

    private void handleNotificationData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("data1")) {
                Log.d("asdasdasd", "Data1 : " + bundle.getString("data1"));
            }
            if (bundle.containsKey("data2")) {
                Log.d("asdasdasd", "Data2 : " + bundle.getString("data2"));
            }

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("asdasdasd", "On New Intent called");
    }

    public void subsSports(View view) {
        subscribeToTopic("sports");
    }

    public void unsubsSports(View view) {
        unsubscribeToTopic("sports");
    }

    public void subsEnt(View view) {
        subscribeToTopic("entertainment");
    }

    public void unsubsEnt(View view) {
        unsubscribeToTopic("entertainment");
    }

    /**
     * method to subscribe to topic
     *
     * @param topic to which subscribe
     */
    private void subscribeToTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Subscribed to " + topic, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to subscribe", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * method to unsubscribe to topic
     *
     * @param topic to which unsubscribe
     */
    private void unsubscribeToTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "UnSubscribed to " + topic, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to unsubscribe", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void runtimeEnableAutoInit() {
        // [START fcm_runtime_enable_auto_init]
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        // [END fcm_runtime_enable_auto_init]
    }


}
