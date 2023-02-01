package com.example.mapsdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.mapsdemo.databinding.ActivityMapsBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MapsActivity
        extends FragmentActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnInfoWindowClickListener
{
    public  static final String B_S_F_A = "something";
    private IntentFilter mif;
    private GoogleMap mMap,objmap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    private double x,y;
    private String[] key_to_details_button = {"0"};
    private String Reverse_geocoded_state;
    private ArrayList<String> info_retrieved = new ArrayList<>();
    private HashMap<Marker,String[]> h_M = new HashMap<>();
    private AlertDialog.Builder maker_of_alert;
    private Intent service;
    private AlertDialog a;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ActivityMapsBinding binding;
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mif = new IntentFilter();
        mif.addAction(B_S_F_A);
        service = new Intent(this,internet_checker.class);
        startService(service);
        maker_of_alert = new AlertDialog.Builder(this);
        maker_of_alert.setCancelable(false).setTitle("Connectivity Error!").setPositiveButton("ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //do nothing
                dialogInterface.cancel();
                //Intent service_starter = new Intent(MapsActivity.this,internet_checker.class);
                startService(service);
                if(x!=0.0&&y!=0.0)
                {
                    reverse_geocoder(x,y);
                }
            }
        }).setNegativeButton("I will not turn it on!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setMessage("Please check your Internet connect!");
        a = maker_of_alert.create();

    }
    //***************************check quick response************************
    public BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(B_S_F_A))
            {
                if(intent.getStringExtra("online_status").equals("true"))
                {
                    //do nothing
                }
                else
                {
                     //= maker_of_alert.create();
                    if(a.isShowing())
                    {
                        //Toast.makeText(MapsActivity.this,"i amhere",Toast.LENGTH_SHORT).show();
                        //Intent service_stoper_starter = new Intent(MapsActivity.this,internet_checker.class);
                        stopService(service);
                    }else
                    {
                        a.show();

                    }
                }
            }

        }
    };

    @Override
    protected void onResume()
    {
        super.onResume();
        registerReceiver(broadcast_reciever,mif);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcast_reciever);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(broadcast_reciever,mif);
    }
    //***************************check quick response************************
    /****CUSTOM SEARCH DATA RETRIEVER****/
    public void custom_search_retrieved(ArrayList<String> h)
    {
        h_M.clear();
        double temp_x=x;
        double temp_y=y;
        if(h!=null)
        {
            int uiop = loader_2(h.get(0)).size();
            if(mMap!=null)
            {
                mMap.clear();
                MarkerOptions m_o = new MarkerOptions();
                LatLng l = new LatLng(x,y);
                m_o.position(l);
                m_o.title("Current Location");
                m_o.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                mMap.addMarker(m_o);
                for(int hsafs =0;hsafs<uiop;hsafs++)
                {
                    if(loader_2(h.get(0)).get(hsafs)[3].equals(h.get(1)))
                    {
                        if(loader_2(h.get(0)).get(hsafs)[4].equals(h.get(2)))
                        {
                            try
                            {
                                marker_on_map(Double.parseDouble(loader_2(h.get(0)).get(hsafs)[1]),Double.parseDouble(loader_2(h.get(0)).get(hsafs)[2]),h.get(0),loader_2(h.get(0)).get(hsafs)[0],loader_2(h.get(0)).get(hsafs)[3],loader_2(h.get(0)).get(hsafs)[4],loader_2(h.get(0)).get(hsafs)[5]);
                                temp_x =Double.parseDouble(loader_2(h.get(0)).get(hsafs)[1]);
                                temp_y = Double.parseDouble(loader_2(h.get(0)).get(hsafs)[2]);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                LatLng latLng_last_loc_cam_mover = new LatLng(temp_x,temp_y);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng_last_loc_cam_mover));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
            }
        }

    }
    /***CUSTOM SEARCH DATA RETRIEVER END****/

    /*****GETTING MAP READY*****/
    @Override
    public void onMapReady(GoogleMap googleMap) //starts when app starts and set default user location.
    {
        mMap = googleMap;
        objmap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }
    /*****GETTING MAP READY END*****/

    /******FUNCTION FOR TRACKING LOCATION*****/
    @Override                       //tracks location,puts marker on it
    public void onLocationChanged(Location location)
    {
        if(currentLocationMarker != null)
        {
            currentLocationMarker.remove();
        }
        /****HANDLING MARKER DETAILS FOR CURRENT LOCATION****/
        //this code is responsible for updating the user location continuously, {it still needs to be checked, if its updating}
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(7));

        //storing current location below for center of circle.
        x = location.getLatitude();
        y = location.getLongitude();


        /****HANDLING MARKER DETAILS FOR CURRENT LOCATION END****/
        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
        Toast.makeText(this, "got called", Toast.LENGTH_SHORT).show();
        reverse_geocoder(x,y);
    }
    /******FUNCTION FOR TRACKING LOCATION END*****/

    /******HANDLING CLICK ON BUTTONS ON MAIN MAP****/

    public void onClick(View v) throws InterruptedException
    {
        switch (v.getId())
        {
            /****MASS SEARCHING MAP BUTTON****/
            case R.id.mass_search:
            {
                h_M.clear();
                if(x==0.0&&y==0.0)
                {
                    Toast.makeText(MapsActivity.this,"retry ",Toast.LENGTH_LONG).show();
                }
                else if(x!=0.0&&y!=0.0)
                {
                    if(Reverse_geocoded_state!=null)
                    {
                        int g = loader_2(Reverse_geocoded_state).size();
                        if(mMap!=null)
                        {
                            mMap.clear();

                            MarkerOptions m_o = new MarkerOptions();
                            LatLng l = new LatLng(x,y);
                            m_o.position(l);
                            m_o.title("Current Location");

                            m_o.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            mMap.addMarker(m_o);
                            for(int i =0;i<g;i++)
                            {
                                marker_on_map(Double.parseDouble(loader_2(Reverse_geocoded_state).get(i)[1]),Double.parseDouble(loader_2(Reverse_geocoded_state).get(i)[2]),Reverse_geocoded_state,loader_2(Reverse_geocoded_state).get(i)[0],loader_2(Reverse_geocoded_state).get(i)[3],loader_2(Reverse_geocoded_state).get(i)[4],loader_2(Reverse_geocoded_state).get(i)[5]);
                            }
                            int g1 = loader_3(Reverse_geocoded_state).size();
                            ArrayList<String> sdf = new ArrayList<>();
                            for(int o = 0;o<g1;o++)
                            {
                                int g2 = loader_3(Reverse_geocoded_state).get(o).length;
                                for(int o1 = 0;o1<g2;o1++)
                                {
                                    sdf.add(loader_3(Reverse_geocoded_state).get(o)[o1]);
                                }
                            }
                            int uop = sdf.size();
                            for(int yy= 0;yy<uop;yy++)
                            {
                                int dsf = loader_2(sdf.get(yy)).size();
                                for(int hg = 0;hg<dsf;hg++)
                                {
                                    marker_on_map(Double.parseDouble(loader_2(sdf.get(yy)).get(hg)[1]),Double.parseDouble(loader_2(sdf.get(yy)).get(hg)[2]),sdf.get(yy),loader_2(sdf.get(yy)).get(hg)[0],loader_2(sdf.get(yy)).get(hg)[3],loader_2(sdf.get(yy)).get(hg)[4],loader_2(sdf.get(yy)).get(hg)[5]);
                                }
                            }
                            LatLng latLng_last_loc_cam_mover = new LatLng(x,y);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng_last_loc_cam_mover));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
                        }


                    }else
                    {
                        Toast.makeText(MapsActivity.this,"loading!",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //don nothing.
                    Toast.makeText(this,"Something Unexpected happened. Please restart the app with gps.",Toast.LENGTH_LONG).show();
                }
            }
            break;
            /****MASS SEARCHING MAP BUTTON END****/

            /*****SETTINGS BUTTON****/
            case R.id.refresh:
            {
                recreate();
            }
            break;
            case R.id.setting:
            {
                Intent settingformapactivity = new Intent(MapsActivity.this,settings_library_of_map.class);
                settingformapactivity.putExtra("currentstate",Reverse_geocoded_state);
                startActivityForResult(settingformapactivity,1);
            }
            break;
            /*****SETTINGS BUTTON END****/

            /****CLEARING MAP BUTTON****/
            case R.id.clear_search:
            {
                h_M.clear();
                if(mMap!=null)
                {
                    mMap.clear();
                    MarkerOptions m_o = new MarkerOptions();
                    LatLng l = new LatLng(x,y);
                    m_o.position(l);
                    m_o.title("Current Location");
                    m_o.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    mMap.addMarker(m_o);
                }


            }
            break;
            /****CLEARING MAP BUTTON END****/

            /*****NEAREST LOC HOSPITAL BUTTON****/
            case R.id.nearest_search:
            {
                h_M.clear();
                if(x==0.0&&y==0.0)
                {
                    Toast.makeText(MapsActivity.this,"retry ",Toast.LENGTH_LONG).show();
                }
                else if(x!=0&&y!=0)
                {
                    double loc_x= x;
                    double loc_y  =y ;
                    if(Reverse_geocoded_state!=null)
                    {
                        if(mMap!=null)
                        {
                            mMap.clear();
                            MarkerOptions m_o = new MarkerOptions();
                            LatLng l = new LatLng(x,y);
                            m_o.position(l);
                            m_o.title("Current Location");
                            m_o.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            mMap.addMarker(m_o);
                            //application_helper obj1 = new application_helper(getApplicationContext());
                            ArrayList<String[]> n_L;
                            n_L =nearest_location(Reverse_geocoded_state) ;
                            int n_L_size = n_L.size();
                            for(int ite = 0;ite<n_L_size;ite++)
                            {
                                marker_on_map(Double.parseDouble(n_L.get(ite)[0]),Double.parseDouble(n_L.get(ite)[1]),n_L.get(ite)[2],n_L.get(ite)[3],n_L.get(ite)[4],n_L.get(ite)[5],n_L.get(ite)[6]);
                                loc_x = Double.parseDouble(n_L.get(ite)[0]);
                                loc_y = Double.parseDouble(n_L.get(ite)[1]);
                            }
                        }
                        if(loc_x!=0.0&loc_y!=0.0)
                        {
                            if(mMap!=null)
                            {
                                LatLng latLng_last_loc_cam_mover = new LatLng(loc_x,loc_y);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng_last_loc_cam_mover));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
                            }

                        }
                    }
                    else
                    {
                        Toast.makeText(this, "got here", Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {
                    Toast.makeText(this,"wait!",Toast.LENGTH_LONG).show();
                }
            }
            break;
            /*****NEAREST LOC HOSPITAL BUTTON END****/

        }
    }
    /******HANDLING CLICK ON BUTTONS ON MAIN MAP END****/

    /*****RETRIEVING DATA FOR CUSTOM SEARCH****/
    public void onActivityResult(int reqCode,int resultcode,Intent data)
    {
        super.onActivityResult(reqCode, resultcode, data);
        if(reqCode==1)
        {
            switch (resultcode)
            {
                case 0:
                {
                    //do nothing
                }
                break;
                case 1:
                {
                    info_retrieved = (ArrayList<String>) data.getSerializableExtra("data1");
                    custom_search_retrieved(info_retrieved);
                }
                break;
                case 2:
                {
                    h_M.clear();
                    String[] got_loc_det;
                    String statename;
                    got_loc_det = (String[])data.getSerializableExtra("gotfromfavrte");
                    statename = (String) data.getSerializableExtra("statename");
                    try {
                        if(mMap!=null)
                        {
                            mMap.clear();
                            MarkerOptions m_o = new MarkerOptions();
                            LatLng l = new LatLng(x,y);
                            m_o.position(l);
                            m_o.title("Current Location");
                            m_o.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            mMap.addMarker(m_o);
                            marker_on_map(Double.parseDouble(got_loc_det[1]),Double.parseDouble(got_loc_det[2]),statename,got_loc_det[0],got_loc_det[3],got_loc_det[4],got_loc_det[5]);
                            LatLng latLng_last_loc_cam_mover = new LatLng(Double.parseDouble(got_loc_det[1]),Double.parseDouble(got_loc_det[2]));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng_last_loc_cam_mover));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
                        }
                        else
                        {
                            Toast.makeText(this, "Map was reset.", Toast.LENGTH_SHORT).show();
                        }

                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                break;
                case 3:
                {
                    h_M.clear();
                    String[] loc_det_from_rec;
                    String statename_from_rec;
                    loc_det_from_rec = (String[]) data.getSerializableExtra("gotfromrec");
                    statename_from_rec = (String) data.getSerializableExtra("statenametomap");
                    try
                    {
                        if(mMap!=null)
                        {
                            mMap.clear();
                            MarkerOptions m_o = new MarkerOptions();
                            LatLng l = new LatLng(x,y);
                            m_o.position(l);
                            m_o.title("Current Location");
                            m_o.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            mMap.addMarker(m_o);
                            marker_on_map(Double.parseDouble(loc_det_from_rec[1]),Double.parseDouble(loc_det_from_rec[2]),statename_from_rec,loc_det_from_rec[0],loc_det_from_rec[3],loc_det_from_rec[4],loc_det_from_rec[5]);
                            LatLng latLng_last_loc_cam_mover = new LatLng(Double.parseDouble(loc_det_from_rec[1]),Double.parseDouble(loc_det_from_rec[2]));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng_last_loc_cam_mover));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
                        }
                        else
                        {
                            Toast.makeText(this, "Map was reset.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }
    /*****RETRIEVING DATA FOR CUSTOM SEARCH END****/

    /***LOADER FOR HOSPITAL LOC PER STATE****/
    public ArrayList<String[]> loader_2(String h)
    {
        //String str ;
        //String[] spliter;
        String[] temp;
        ArrayList<String[]> Sorted_data_1 = new ArrayList<>();
        try
        {
            InputStream inputStream = getAssets().open("state/"+h+"/hospital_location");
            BufferedReader b_inputing = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = b_inputing.readLine())!=null)
            {
                line = line.replace("\'","");
                line = line.replace("\n","");
                temp = line.split(",");
                Sorted_data_1.add(temp);
            }
            /*int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            str = new String(buffer);
            str = str.replace("\"","");
            str = str.replace("\n","");
            spliter = str.split("]");
            for(int i = 0;i<spliter.length;i++)
            {
                temp = spliter[i].split(",");
                Sorted_data_1.add(temp);
            }

             */

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return Sorted_data_1;
    }

    /***LOADER FOR HOSPITAL LOC PER STATE END****/

    /***LOADER FOR ADJACENT STATE****/
    public ArrayList<String[]> loader_3(String h)
    {

        String str;
        String[] spliter;
        String[] temp;
        ArrayList<String[]> Sorted_data_2 = new ArrayList<>();
        try {
            InputStream inputStream = getAssets().open("state/"+h+"/Adjacent");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            str = new String(buffer);
            str = str.replace("\"","");
            str = str.replace("\n","");
            spliter = str.split("]");
            for(int i = 0;i<spliter.length;i++)
            {
                //spliter[i] = spliter[i].replace("\n","\0");
                temp = spliter[i].split(",");
                Sorted_data_2.add(temp);
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return Sorted_data_2;

    }
    /***LOADER FOR ADJACENT STATE END****/

    /****FUNCTION FOR PLACING MARKERS ON MAP*****/
    public void marker_on_map(double t_x,double t_y, String state_name, String name,String type, String rating, String period) throws InterruptedException
    {
        MarkerOptions mo = new MarkerOptions();

        LatLng latLng = new LatLng(t_x,t_y);
        //info_sender.add(name);
        
        String[] details_marker = {name,state_name,type,rating,period,Double.toString(t_x),Double.toString(t_y)};

        mo.position(latLng);
        String title = name;
        mo.title(title);
        mo.snippet(state_name);


        Marker m = mMap.addMarker(mo);
        h_M.put(m,details_marker);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override //need to turn this function into as a dialog or window
            public boolean onMarkerClick(@NonNull Marker marker)
            {

                String markerName = marker.getTitle();
                if(!(markerName.equals("Current Location")))
                {
                    if(markerName.equals(key_to_details_button[0]))
                    {
                        //Toast.makeText(MapsActivity.this,"inside equals",Toast.LENGTH_SHORT).show();
                        key_to_details_button[0] = "0";
                        return true;
                    }
                    else if(!(markerName.equals(key_to_details_button[0])))
                    {
                        //Toast.makeText(MapsActivity.this,"inside !equals",Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MapsActivity.this, h_M.get(marker)[0], Toast.LENGTH_SHORT).show();
                        key_to_details_button[0] = markerName;
                        //Toast.makeText(MapsActivity.this,h_M.get(marker)[0],Toast.LENGTH_SHORT).show();
                        InfoWindowAdapter markerInfoWindowAdapter = new InfoWindowAdapter(getApplicationContext());
                        mMap.setInfoWindowAdapter(markerInfoWindowAdapter);
                        mMap.setOnInfoWindowClickListener(MapsActivity.this);
                        return false;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return true;
                }

            }
        });
    }

    /****FUNCTION FOR PLACING MARKERS ON MAP END*****/

    /****NEAREST LOCATION FINDER FUNCTION****/

    public ArrayList<String[]> nearest_location(String h)
    {
        //MapsActivity obj = new MapsActivity();

        double near_loc_distance = 0.0;
        ArrayList<String[]> nearest_loc = new ArrayList<>();
        String[] arr = new String[7];
        double temp_near_x = x;
        double temp_near_y=y;
        int g = loader_2(h).size();
        for(int v = 0;v<g;v++)
        {
            double temp = Math.sqrt((Math.pow(temp_near_x-Double.parseDouble(loader_2(h).get(v)[1]),2)+Math.pow(temp_near_y-Double.parseDouble(loader_2(h).get(v)[2]),2)));
            if(v==0&&near_loc_distance==0.0)
            {
                near_loc_distance = temp;
                arr[0] = loader_2(h).get(v)[1];
                arr[1] = loader_2(h).get(v)[2];

                arr[2] = h;
                arr[3] = loader_2(h).get(v)[0];

                arr[4] = loader_2(h).get(v)[3];
                arr[5] = loader_2(h).get(v)[4];

                arr[6] = loader_2(h).get(v)[5];

                nearest_loc.add(arr);
            }
            else if(v!=0&&temp<near_loc_distance)
            {
                near_loc_distance=temp;
                nearest_loc.clear();
                arr[0] = loader_2(h).get(v)[1];
                arr[1] = loader_2(h).get(v)[2];

                arr[2] = h;
                arr[3] = loader_2(h).get(v)[0];

                arr[4] = loader_2(h).get(v)[3];
                arr[5] = loader_2(h).get(v)[4];

                arr[6] = loader_2(h).get(v)[5];
                nearest_loc.add(arr);
            }
            else if(v!=0&&temp==near_loc_distance)
            {
                near_loc_distance = temp;
                arr[0] = loader_2(h).get(v)[1];
                arr[1] = loader_2(h).get(v)[2];

                arr[2] = h;
                arr[3] = loader_2(h).get(v)[0];

                arr[4] = loader_2(h).get(v)[3];
                arr[5] = loader_2(h).get(v)[4];

                arr[6] = loader_2(h).get(v)[5];
                nearest_loc.add(arr);
            }

        }
        int f = loader_3(h).size();
        for(int as = 0;as<f;as++)
        {
            int d =loader_3(h).get(as).length;
            for(int b = 0;b<d;b++)
            {
                int xas = loader_2(loader_3(h).get(as)[b]).size();
                for(int xc = 0; xc<xas;xc++)
                {
                    double temp_var = Math.sqrt((Math.pow(temp_near_x-Double.parseDouble(loader_2(loader_3(h).get(as)[b]).get(xc)[1]),2)+Math.pow(temp_near_y-Double.parseDouble(loader_2(loader_3(h).get(as)[b]).get(xc)[2]),2)));

                    if(xc==0&&temp_var<near_loc_distance)
                    {
                        near_loc_distance = temp_var;
                        nearest_loc.clear();
                        arr[0] = loader_2(loader_3(h).get(as)[b]).get(xc)[1];
                        arr[1] = loader_2(loader_3(h).get(as)[b]).get(xc)[2];

                        arr[2] = loader_3(h).get(as)[b];
                        arr[3] = loader_2(loader_3(h).get(as)[b]).get(xc)[0];

                        arr[4] = loader_2(loader_3(h).get(as)[b]).get(xc)[3];
                        arr[5] = loader_2(loader_3(h).get(as)[b]).get(xc)[4];

                        arr[6] = loader_2(loader_3(h).get(as)[b]).get(xc)[5];
                        nearest_loc.add(arr);
                    }
                    else if(xc!=0&&temp_var<near_loc_distance)
                    {
                        near_loc_distance=temp_var;
                        nearest_loc.clear();
                        arr[0] = loader_2(loader_3(h).get(as)[b]).get(xc)[1];
                        arr[1] = loader_2(loader_3(h).get(as)[b]).get(xc)[2];

                        arr[2] = loader_3(h).get(as)[b];
                        arr[3] = loader_2(loader_3(h).get(as)[b]).get(xc)[0];

                        arr[4] = loader_2(loader_3(h).get(as)[b]).get(xc)[3];
                        arr[5] = loader_2(loader_3(h).get(as)[b]).get(xc)[4];

                        arr[6] = loader_2(loader_3(h).get(as)[b]).get(xc)[5];
                        nearest_loc.add(arr);
                    }
                    else if(xc!=0&&temp_var==near_loc_distance)
                    {
                        near_loc_distance = temp_var;
                        arr[0] = loader_2(loader_3(h).get(as)[b]).get(xc)[1];
                        arr[1] = loader_2(loader_3(h).get(as)[b]).get(xc)[2];

                        arr[2] = loader_3(h).get(as)[b];
                        arr[3] = loader_2(loader_3(h).get(as)[b]).get(xc)[0];

                        arr[4] = loader_2(loader_3(h).get(as)[b]).get(xc)[3];
                        arr[5] = loader_2(loader_3(h).get(as)[b]).get(xc)[4];

                        arr[6] = loader_2(loader_3(h).get(as)[b]).get(xc)[5];
                        nearest_loc.add(arr);
                    }
                }
            }
        }
        return nearest_loc;
    }
    /****NEAREST LOCATION FINDER FUNCTION END****/

    /*** HANDLING POPUP WINDOW CLICK ON MARKER****/
    @Override
    public void onInfoWindowClick(@NonNull Marker marker)
    {
        Intent Location_details = new Intent(MapsActivity.this,location_details.class);
        Location_details.putExtra("Details",h_M.get(marker));
        startActivity(Location_details);
    }
    /*** HANDLING POPUP WINDOW CLICK ON MARKER END****/

    /**** REVERSE GEOCODEr FOR CURRENT LocATION***/

    public void reverse_geocoder(double longLat_x,double longLat_y)
    {
        OkHttpClient client = new OkHttpClient();


        String url = "https://nominatim.openstreetmap.org/reverse?lat="+longLat_x+"&lon="+longLat_y+"&<yt>";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e)
            {
                //Log.i("bullshit","hjhv");
                //need to change here
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
            {
                Log.i("bullshit","connected");
                if(response.isSuccessful())
                {
                    String myResponse = response.body().string();

                    MapsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            //Toast.makeText(MapsActivity.this,myResponse,Toast.LENGTH_LONG).show();

                            int y = myResponse.indexOf("<state>");
                            int g = myResponse.indexOf("</state>");
                            Reverse_geocoded_state = myResponse.substring(y+7,g);
                            Reverse_geocoded_state = Reverse_geocoded_state.toLowerCase();
                            //Toast.makeText(MapsActivity.this,Reverse_geocoded_state,Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    /**** REVERSE GEOCODEr FOR CURRENT LOCATION END***/

    /****PERMISSION FOR ACCESSING LOCATITON***/
    @Override                               //for handling permission request response
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission is granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if (client == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
    /****PERMISSION FOR ACCESSING LOCATITON END***/




    /****GETTING GOOGLE API CLIEnT CONNECTION***/
    protected synchronized void buildGoogleApiClient()
    {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }

    /****GETTING GOOGLE API CLIENT CONNECTION END***/

    /*** HANDLES LoCATION REQUEST CALLS FROM ClIENt****/
    @Override//requesting loation
    public void onConnected(@Nullable Bundle bundle)
    {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }
    /****HANDLES LOCATION REQUEST CALLS FROM CLIENTS END***/


    /****CHECKS LOCATION PERMISSION***/
    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }

    /****CHECKS LOCATION PERMISSION END***/

    /***IF CONNECTION LAGS***/
    @Override
    public void onConnectionSuspended(int i)
    {

    }

    /***IF CONNECTION LAGS END***/

    /****FAILED CONNECTION****/
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }
    /****FAILED CONNECTION END****/



}

