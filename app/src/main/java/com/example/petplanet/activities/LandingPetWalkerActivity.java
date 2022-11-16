package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.widget.Toast;

import com.deeplabstudio.fcmsend.FCMSend;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.petplanet.R;
import com.example.petplanet.adapters.CardAdapterUsuario;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.petplanet.databinding.ActivityLandingPetWalkerBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class LandingPetWalkerActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, RoutingListener {
    private ActivityLandingPetWalkerBinding binding;
    // Setup del logger para esta clase
    private static final String TAG = LandingPetWalkerActivity.class.getName();
    private Logger logger = Logger.getLogger(TAG);
    private List<Polyline> polylines = null;
    protected LatLng start = null;
    protected LatLng end = null;
    private GoogleMap mMap;
    private final static int INITIAL_ZOOM_LEVEL = 15;

    private Geocoder mGeocoder;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    SensorManager sensorManager;
    Sensor lightSensor;
    SensorEventListener lightSensorListener;
    private FirebaseAuth mAuth;
    Usuario Client = new Usuario();
    private LocationCallback mLocationCallback;
    private float humActual;
    private SensorEventListener humSensorListener;
    private Sensor humSensor;


    private Paseo nPaseo = new Paseo();
    DatabaseReference myPaseos;


    //Variables de permisos
    private final int LOCATION_PERMISSION_ID = 103;
    public static final int REQUEST_CHECK_SETTINGS = 201;
    String locationPerm = Manifest.permission.ACCESS_FINE_LOCATION;

    //Variables de localizacion
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;
    public Location mCurrentLocation;

    public Location getmCurrentLocation() {
        return mCurrentLocation;
    }

    public void setmCurrentLocation(Location mCurrentLocation) {
        this.mCurrentLocation = mCurrentLocation;
    }

    Location aux;

    public String direcciondelOwner;

    public String getDirecciondelOwner() {
        return direcciondelOwner;
    }

    public void setDirecciondelOwner(String direcciondelOwner) {
        this.direcciondelOwner = direcciondelOwner;
    }


    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String nombredelowner;

    public String getNombredelowner() {
        return nombredelowner;
    }

    public void setNombredelowner(String nombredelowner) {
        this.nombredelowner = nombredelowner;
    }

    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    boolean paseoencurso = false;
    Paseo paseo = new Paseo();
    String duracion;

    Usuario owner = new Usuario();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPetWalkerBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        binding.bottomNavigationWalker.setBackground(null);
        mAuth = FirebaseAuth.getInstance();
        String serverKey = String.valueOf(R.string.secretkey);
        FCMSend.SetServerKey(serverKey);

        cargardatos();
        binding.coordinatorLayout.setVisibility(View.VISIBLE);
        binding.bottomNavigationWalker.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.mascotas:
                    Intent intent = new Intent(getApplicationContext(), ListaPaseosActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.IniciarPaseoBTN:
                    Intent intent2 = new Intent(getApplicationContext(), IniciarPaseoActivity.class);
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

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                direcciondelOwner = null;

            } else {
                setDirecciondelOwner(extras.getString("direccion"));
                setNombredelowner(extras.getString("nombredelowner"));
                setId(extras.getString("id"));
                duracion = extras.getString("duracion");
            }
        } else {
            setDirecciondelOwner((String) savedInstanceState.getSerializable("direccion"));
            setNombredelowner((String) savedInstanceState.getSerializable("nombredelowner"));
            setId((String) savedInstanceState.getSerializable("id"));
            duracion = (String) savedInstanceState.getSerializable("duracion");
        }
        mLocationRequest = createLocationRequest();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapwalker);
        mapFragment.getMapAsync(this);


        //Location

        requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, "El permiso es necesario para acceder a la localizacion", LOCATION_PERMISSION_ID);


        if (sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
            humSensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            humSensorListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {

                    if (Math.abs(humActual - sensorEvent.values[0]) > 1) {
                        humActual = sensorEvent.values[0];
                        Log.d("Humedad", "Humedad: " + humActual);
                        if (humActual > 65) {
                            Toast.makeText(LandingPetWalkerActivity.this, "Cuidado puede llover, busca un paraguas!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LandingPetWalkerActivity.this, "Hace fresco, Relajao!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };
        }
        sensorManager.registerListener(humSensorListener, humSensor, SensorManager.SENSOR_DELAY_NORMAL);


        // Initialize the sensors

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Initialize the listener
        lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (mMap != null) {
                    if (event.values[0] < 100) {
                        Log.i("MAPS", "DARK MAP " + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions
                                .loadRawResourceStyle(LandingPetWalkerActivity.this, R.raw.style_night));
                    } else {
                        Log.i("MAPS", "LIGHT MAP " + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions
                                .loadRawResourceStyle(LandingPetWalkerActivity.this, R.raw.style_day));
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

                if (location != null) {
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(INITIAL_ZOOM_LEVEL));
                    // Enable touch gestures

                    LatLng clatlng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(clatlng));

                    mCurrentLocation = location;

                    currentLat = location.getLatitude();
                    currentLong = location.getLongitude();
                    start = new LatLng(currentLat, currentLong);
                }
            }
        };
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        // UI controls

        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (checkPermissions()) {
            mMap.setMyLocationEnabled(true);
            mMap.isMyLocationEnabled();
            currentlocation();

            // Set  zoom level

            mMap.setMyLocationEnabled(true);

            LatLng center = null;
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            SystemClock.sleep(200);
            myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
            myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Client = task.getResult().getValue(Usuario.class);
                    if (Client.getPaseoencurso() != null) {
                        if (Client.getPaseoencurso()) {
                            binding.paseoencursoBTN.setVisibility(View.VISIBLE);
                            binding.confirmarpaseoBTN.setVisibility(View.GONE);
                        }
                    }

                }
            });


            binding.confirmarpaseoBTN.setOnClickListener(view -> {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Elige una opcion para subir tu foto")
                        .setPositiveButton("Si, iniciar paseo ya mismo", (dialogInterface, i) -> {
                            binding.confirmarpaseoBTN.setVisibility(View.GONE);
                            binding.cardpaseo.setVisibility(View.VISIBLE);
                            binding.coordinatorLayout.setVisibility(View.GONE);
                            sacarpaseo();
                            setDirecciondelOwner(null);
                        })
                        .setNegativeButton("No, prefiero buscar otro", (dialogInterface, i) -> {
                            binding.confirmarpaseoBTN.setVisibility(View.GONE);
                            mMap.clear();
                            setDirecciondelOwner(null);
                            mMap.clear();
                        })

                        .show();
            });
            binding.paseoencursoBTN.setOnClickListener(view -> {
                myPaseos = database.getReference(Constants.PATH_PASEOS);
                myPaseos.getDatabase().getReference(Constants.PATH_PASEOS).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            paseo = snapshot.getValue(Paseo.class);
                            Log.d("Paseo", "cargarpaseo: " + paseo.getDirecciondelowner());
                            if (Client.getPaseoencurso()) {
                                if (paseo.getNombredelwalker().equals(Client.getNombre())) {
                                    binding.cardpaseo.setVisibility(View.VISIBLE);
                                    binding.paseoencursoBTN.setVisibility(View.GONE);
                                    binding.coordinatorLayout.setVisibility(View.GONE);
                                    binding.confirmarpaseoBTN.setVisibility(View.GONE);

                                    setId(snapshot.getKey());
                                    byte[] decodedString = Base64.decode(paseo.getFotodelperro(), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    binding.imagePerson.setImageBitmap(decodedByte);
                                    binding.Nombreperro.setText(paseo.getNombredelperro());
                                    binding.duracionTXT.setText(paseo.getDuracion() + " min");
                                    pintarrutahaciaelowner(paseo.getDirecciondelowner());
                                    paseoencurso = true;
                                }
                            }

                        }
                    }
                });
            });

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

                        if (getId() != null) {
                            myPaseos = database.getReference(Constants.PATH_PASEOS + getId());
                            myPaseos.getDatabase().getReference(Constants.PATH_PASEOS + getId()).get().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Paseo paseo = task.getResult().getValue(Paseo.class);
                                    if (paseo.getUidWalker() != null) {
                                        if (paseo.getUidWalker().equals(mAuth.getCurrentUser().getUid())) {
                                            paseo.setLatitudwalker(currentLat);
                                            paseo.setLongitudwalker(currentLong);
                                            myPaseos.setValue(paseo);
                                        }
                                    }

                                }
                            });

                            start = new LatLng(currentLat, currentLong);
                            Location dis3 = new Location("localizacion 1");
                            dis3.setLatitude(currentLat);  //latitud
                            dis3.setLongitude(currentLong); //longitud
                            Location dis2 = new Location("localizacion 2");
                            dis2.setLatitude(paseo.getLatitud());  //latitud
                            dis2.setLongitude(paseo.getLongitud()); //longitud
                            double distance = dis3.distanceTo(dis2);
                            DecimalFormat df = new DecimalFormat("#.##");
                            df.setRoundingMode(RoundingMode.FLOOR);

                            binding.distanciaTXT.setText(df.format(distance) + " metros");
                            if (distance < 100) {
                                binding.escriDuenoBTN.setVisibility(View.VISIBLE);

                                //Toast.makeText(LandingPetWalkerActivity.this, "Estas a " + distance + " metros de la ubicacion del paseo", Toast.LENGTH_SHORT).show();
                            }
                            if (distance < 5) {
                                myPaseos = database.getReference(Constants.PATH_PASEOS + getId());
                                myPaseos.getDatabase().getReference(Constants.PATH_PASEOS + getId()).get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Paseo paseo = task.getResult().getValue(Paseo.class);
                                        FCMSend.Builder build = new FCMSend.Builder(paseo.getFcmtokenowner())
                                                .setTitle("Tu paseador esta cerca")
                                                .setBody("Tu paseador esta a " + df.format(distance) + " metros de ti, puedes hablar con el");
                                        String result = build.send().Result();
                                        paseo.setYallegoelpaseador(true);
                                        myPaseos.setValue(paseo);
                                    }
                                });

                                myPaseos = database.getReference(Constants.PATH_PASEOS + getId());
                                myPaseos.getDatabase().getReference(Constants.PATH_PASEOS + getId()).get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Paseo paseo = task.getResult().getValue(Paseo.class);
                                        if (!paseo.isYatengoelperro()) {
                                            binding.confirmarperroBTN.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                            }
                        }
                        if (getDirecciondelOwner() != null) {
                            start = new LatLng(currentLat, currentLong);
                            pintarrutahaciaelowner(getDirecciondelOwner());
                            binding.confirmarpaseoBTN.setVisibility(View.VISIBLE);
                        }
                    }
                }
            };

            binding.confirmarperroBTN.setOnClickListener(view -> {
                myPaseos = database.getReference(Constants.PATH_PASEOS + getId());
                myPaseos.getDatabase().getReference(Constants.PATH_PASEOS + getId()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Paseo paseo = task.getResult().getValue(Paseo.class);
                        paseo.setYatengoelperro(true);
                        myPaseos.setValue(paseo);
                    }
                });
                binding.confirmarperroBTN.setVisibility(View.GONE);
            });


            binding.volveralmenuBTN.setOnClickListener(view -> {
                binding.cardpaseo.setVisibility(View.GONE);
                binding.paseoencursoBTN.setVisibility(View.VISIBLE);
                binding.coordinatorLayout.setVisibility(View.VISIBLE);
                binding.confirmarpaseoBTN.setVisibility(View.GONE);
                stopLocationUpdates();
                mMap.clear();
            });


            binding.escriDuenoBTN.setOnClickListener(view -> {
                myRef = database.getReference(Constants.PATH_USERS);
                myRef.getDatabase().getReference(Constants.PATH_USERS).get().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        task1.getResult().getChildren().forEach(snapshot -> {
                            Usuario user = snapshot.getValue(Usuario.class);
                            if (user.getNombre().equals(paseo.getNombredelowner())) {
                                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                                intent.putExtra(Constants.KEY_USER_ID, snapshot.getKey());
                                startActivity(intent);
                            }
                        });
                    }
                });
            });


            binding.canelarpaseoBTN.setOnClickListener(view -> {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Deseas cancelar el paseo?")
                        .setMessage("Si cancelas el paseo, el dueño del perro no podra ver tu ubicacion")
                        .setPositiveButton("Si, deseo cancelar el paseo", (dialogInterface, i) -> {
                            setDirecciondelOwner(null);
                            Log.d("asdasdasd33", "Loc: " + getId());
                            myPaseos = database.getReference(Constants.PATH_PASEOS + getId());
                            myPaseos.getDatabase().getReference(Constants.PATH_PASEOS + getId()).get().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Paseo paseo = task.getResult().getValue(Paseo.class);
                                    FCMSend.Builder build = new FCMSend.Builder(paseo.getFcmtokenowner())
                                            .setTitle("Cancelaron tu paseo")
                                            .setBody("Lo sentimos, tu paseador ha cancelado el paseo");
                                    String result = build.send().Result();
                                    paseo.setNombredelwalker("pendiente");
                                    paseo.setUidWalker("0");
                                    paseo.setLatitudwalker(0);
                                    paseo.setLongitudwalker(0);
                                    paseo.setYallegoelpaseador(false);
                                    myPaseos.setValue(paseo);

                                }
                            });
                            myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
                            myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Client = task1.getResult().getValue(Usuario.class);
                                    Client.setPaseoencurso(false);
                                    myRef.setValue(Client);
                                    binding.cardpaseo.setVisibility(View.GONE);
                                    binding.paseoencursoBTN.setVisibility(View.GONE);
                                    binding.coordinatorLayout.setVisibility(View.VISIBLE);
                                    binding.confirmarpaseoBTN.setVisibility(View.GONE);
                                }
                            });
                            mMap.clear();
                        })
                        .setNegativeButton("No cancelar", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })

                        .show();
            });


            // localizacion del walker


            startLocationUpdates();
        }

    }

    public void pintarrutahaciaelowner(String direcciondelOwner) {
        Log.d("Paseoasd", "cargarpaseo0: " + currentLat);
        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> addresses;
        String strAddress = direcciondelOwner;
        try {
            addresses = coder.getFromLocationName(strAddress + "Bogotá", 5);
            Log.d("Pasesso", "Location update in the callback: " + currentLat);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng latLng2 = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng2).title("Direccion del Owner"));
                Location dis3 = new Location("localizacion 1");
                dis3.setLatitude(currentLat);  //latitud
                dis3.setLongitude(currentLong); //longitud
                Location dis2 = new Location("localizacion 2");
                dis2.setLatitude(address.getLatitude());  //latitud
                dis2.setLongitude(address.getLongitude()); //longitud
                double distance = dis3.distanceTo(dis2);
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.FLOOR);

                binding.distanciaTXT.setText(df.format(distance) + " metros");
                LatLng destination = new LatLng(dis2.getLatitude(), dis2.getLongitude());
                end = destination;
                paseo.setLatitud(end.latitude);
                paseo.setLongitud(end.longitude);
                if (start == null) {
                    start = new LatLng(dis3.getLatitude(), dis3.getLongitude());
                }
                Findroutes(start, end);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sacarpaseo() {
        final String SENDER_ID;
        Log.d("paseoasd", "sacarpaseo: " + getId());
        myPaseos = database.getReference(Constants.PATH_PASEOS + getId());
        myPaseos.getDatabase().getReference(Constants.PATH_PASEOS + getId()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                nPaseo = task.getResult().getValue(Paseo.class);
                Log.d("paseoasd", "sacarpaseo: " + nPaseo.getNombredelowner());
                nPaseo.setNombredelwalker(Client.getNombre());
                nPaseo.setUidWalker(Client.getId());
                nPaseo.setDuracion(duracion);
                Log.d("paseoasd", "sacarpaseo: " + nPaseo.getNombredelwalker());

                Log.d("paseoasd", "sacarpaseo: " + nPaseo.getFcmtokenowner());
// See documentation on defining a message payload.
                FCMSend.Builder build = new FCMSend.Builder(nPaseo.getFcmtokenowner())
                        .setTitle(nPaseo.getNombredelwalker() + " ha aceptado tu paseo")
                        .setBody("El walker ya va para tu ubicacion preparate");
                String result = build.send().Result();

                // [START fcm_send_upstream]

                myPaseos.setValue(nPaseo);

                // [END fcm_send_upstream]
                byte[] decodedString = Base64.decode(nPaseo.getFotodelperro(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                binding.imagePerson.setImageBitmap(decodedByte);
                binding.Nombreperro.setText(nPaseo.getNombredelperro());
                binding.duracionTXT.setText(nPaseo.getDuracion() + " min");
                binding.confirmarpaseoBTN.setVisibility(View.GONE);



            }
        });
        myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Client = task.getResult().getValue(Usuario.class);
                Client.setPaseoencurso(true);
                myRef.setValue(Client);
            }
        });


    }


    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
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

    private void requestPermission(Activity context, String permiso, String justificacion,
                                   int idCode) {
        if (ContextCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permiso)) {
                Toast.makeText(context, justificacion, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permiso}, idCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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
                            resolvable.startResolutionForResult(LandingPetWalkerActivity.this, REQUEST_CHECK_SETTINGS);
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
        if (checkPermissions()) {
            turnOnLocationAndStartUpdates();
        }
        sensorManager.registerListener(lightSensorListener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(humSensorListener,
                humSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }


    public void cargardatos() {

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

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
                        myRef.setValue(Client);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al actualizar el token de perfil", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("asdasdasdasd", "Failed to get the token : " + e.getLocalizedMessage());
            }
        });


        myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Client = task.getResult().getValue(Usuario.class);
            }
        });
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


    public void Findroutes(LatLng Start, LatLng End) {
        if (Start == null || End == null) {
            Toast.makeText(getApplicationContext(), "Unable to get location", Toast.LENGTH_LONG).show();
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

}