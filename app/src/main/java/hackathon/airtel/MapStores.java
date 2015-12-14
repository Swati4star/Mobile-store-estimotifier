package hackathon.airtel;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MapStores extends AppCompatActivity {

    Double latitude, longitude;
    com.google.android.gms.maps.SupportMapFragment fragment;
    GoogleMap map;

    List<String> zone = new ArrayList<String>();
    List<String> sadd = new ArrayList<String>();
    List<String> ladd = new ArrayList<String>();
    List<String> lat = new ArrayList<String>();
    List<String> lon = new ArrayList<String>();

    LinearLayout l;
    TextView name, add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_stores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        l = (LinearLayout) findViewById(R.id.con);
        name = (TextView) findViewById(R.id.name);
        add = (TextView) findViewById(R.id.store_add);

        FragmentManager fm = getSupportFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }

        map = fragment.getMap();



    }

    public void ShowMarker(Double LocationLat, Double LocationLong, String LocationName, Integer LocationIcon) {
        LatLng Coord = new LatLng(LocationLat, LocationLong);

        if (map != null) {


            if(LocationLat == 12.9745 || LocationLat == 31.9608)
                return;

            Log.e("Showing marker", LocationLat +" "+LocationLong+" "+LocationName);


            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(Coord, 8));

            MarkerOptions abc = new MarkerOptions();
            MarkerOptions x = abc
                    .title(LocationName)
                    .position(Coord)
                    .icon(BitmapDescriptorFactory.fromResource(LocationIcon));
            map.addMarker(x);

        }
    }


    public class getStrores extends AsyncTask<String, Void, String> {
        SweetAlertDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("in here", "stareefw");
            pDialog = new SweetAlertDialog(MapStores.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            pDialog.setTitleText("Fetching stores...");
            pDialog.setCancelable(false);
            pDialog.show();
            Log.e("vdslmvdspo", "started");
        }

        protected String doInBackground(String... urls) {
            try {


                String uri = "http://csinsit.org/prabhakar/airtel/get-all-stores.php";

                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                String readStream = Utils.readStream(con.getInputStream());

                Log.e("here", uri + " ");
                return readStream;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String Result) {
            pDialog.dismiss();
            try {
                JSONObject res = new JSONObject(String.valueOf(Result));
                JSONArray arr = res.getJSONArray("stores");
                for (int i = 0; i < arr.length(); i++) {
                    zone.add(arr.getJSONObject(i).getString("zone"));
                    sadd.add(arr.getJSONObject(i).getString("short_add"));
                    ladd.add(arr.getJSONObject(i).getString("long_add"));
                    lat.add(arr.getJSONObject(i).getString("lat"));
                    lon.add(arr.getJSONObject(i).getString("lng"));


                  ShowMarker(Double.parseDouble(lat.get(i)),
                            Double.parseDouble(lon.get(i)),
                            zone.get(i),
                            R.drawable.ic_person_pin_circle_black_24dp);

                }


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("vsdfkvaes", e.getMessage() + " dsv");
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (map == null) {
            map = fragment.getMap();

            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {


                @Override
                public boolean onMarkerClick(Marker marker) {
                    l.setVisibility(View.VISIBLE);

                    int position = 0;

                    for (int i = 0; i < zone.size(); i++) {
                        if (zone.get(i).equals(marker.getTitle())) {
                            position = i;
                            break;
                        }
                    }

                    Log.e("in here clicked", marker.getPosition().latitude+" ");

                    name.setText(zone.get(position));
                    add.setText(ladd.get(position));


                    return false;
                }
            });

            new getStrores().execute();

        }


    }
}
