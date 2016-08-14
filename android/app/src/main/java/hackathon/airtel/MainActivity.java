package hackathon.airtel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import functs.Utils;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private BeaconManager beaconManager;
    private Region region;
    TextView visit;
    SharedPreferences s;
    LinearLayout nobea,bea;
    SharedPreferences.Editor e;
    Boolean discovered = false;
    String beaconmajor;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Log.e("here", System.currentTimeMillis()+" ");



        s = PreferenceManager.getDefaultSharedPreferences(this);
        e = s.edit();
        visit = (TextView) findViewById(R.id.visit);
        nobea = (LinearLayout) findViewById(R.id.noestimote);
        bea = (LinearLayout) findViewById(R.id.estimote);
        lv = (ListView) findViewById(R.id.music_list);

        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MapStores.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (Utils.isNetworkAvailable(this))
            if (s.getString(Consts.userid, null) == null)
                new getID().execute();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);



        beaconManager = new BeaconManager(this);
        region = new Region("Minion region", UUID.fromString(Consts.UID), null, null);



        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });


        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (discovered == false && list.size()>0) {
                    Beacon nearestBeacon = list.get(0);
                    beaconmajor = Integer.toString(nearestBeacon.getMajor());
                    Log.e("Discovered", "Nearest places: " + nearestBeacon.getMajor());
                    discovered = true;
                    nobea.setVisibility(View.GONE);
                    bea.setVisibility(View.VISIBLE);
                    Snackbar.make(visit,"Nearby airtel store detected",Snackbar.LENGTH_LONG).show();
                    new getOffer().execute();

                    new getServices().execute();

                }
            }


        });

        if(discovered == false){
            Snackbar.make(visit,"No nearby airtel store detected",Snackbar.LENGTH_LONG).show();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_buy) {
            Intent i = new Intent(MainActivity.this,ListActi.class);
            i.putExtra(Consts.servicetype,"http://csinsit.org/prabhakar/airtel/services/buy.php");
            startActivity(i);
        } else if (id == R.id.nav_store) {
            Intent i = new Intent(MainActivity.this,MapStores.class);
            startActivity(i);

        } else if (id == R.id.nav_rec) {
            Intent i = new Intent(MainActivity.this,recharge.class);
            startActivity(i);

        } else if (id == R.id.nav_logs) {
            Intent i = new Intent(MainActivity.this,MyLogs.class);
            startActivity(i);

        } else if (id == R.id.nav_chat) {
            Intent i = new Intent(MainActivity.this,chat.class);
            startActivity(i);

        } else if (id == R.id.nav_app) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.myairtelapp&hl=en"));
            startActivity(browserIntent);

        } else if (id == R.id.nav_help) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.airtel.in/"));
            startActivity(browserIntent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public class getID extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("in here","stareefw");
         /*   progressDialog = new ProgressDialog(CityInfo.this);
            progressDialog.setMessage("Fetching data, Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();     Log.e("vdslmvdspo","started");*/
        }

        protected String doInBackground(String... urls) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String id = telephonyManager.getDeviceId();
                String carrierName = telephonyManager.getSimOperatorName();

                String uri = "http://csinsit.org/prabhakar/airtel/get-device-id.php?device=" + id+"&service="+carrierName
                        ;
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
            try {
                JSONObject res = new JSONObject(String.valueOf(Result));

                e.putString(Consts.userid,res.getString("user_id"));
                e.commit();
                Log.e("DONE","user regis"+ res.getString("user_id"));


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("vsdfkvaes", e.getMessage() + " dsv");
            }
        }
    }


    public class getOffer extends AsyncTask<String, Void, String> {
        SweetAlertDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("in here", "stareefw");
           pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            pDialog.setTitleText("Loading..");
            pDialog.setCancelable(false);
            pDialog.show();
            Log.e("vdslmvdspo","started");
        }

        protected String doInBackground(String... urls) {
            try {

                String uid = s.getString(Consts.userid, null);

                String uri = "http://csinsit.org/prabhakar/airtel/main-function.php?estimote=" +
                        beaconmajor +
                        "&user=" + uid;
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
                if(res.getInt("special_offer") == 1){
                    Log.e("USER", "Special offer");

                    final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
                    pDialog.setTitleText("Congratulations!");
                    pDialog.setContentText("You have won special offer.Click here to claim!");
                    pDialog.setCancelable(false);
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.airtel.in/"));
                            startActivity(browserIntent);
                            pDialog.dismiss();

                        }
                    });



                    pDialog.show();



                }else{
                    Log.e("USER", "no special offer");


                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("vsdfkvaes", e.getMessage() + " dsv");
            }
        }
    }

    public class getServices extends AsyncTask<String, Void, String> {
        SweetAlertDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("in here", "stareefw");
            pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            pDialog.setTitleText("Loading Services..");
            pDialog.setCancelable(false);
            pDialog.show();
            Log.e("vdslmvdspo","started");
        }

        protected String doInBackground(String... urls) {
            try {


                String uri = "http://csinsit.org/prabhakar/airtel/get-services.php?estimote=" +
                        beaconmajor ;
                Log.e("hitting",uri);
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
                JSONArray arr = res.getJSONArray("services");

                Services_adapter s = new Services_adapter(MainActivity.this, arr);
                lv.setAdapter(s);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("vsdfkvaes", e.getMessage() + " dsv");
            }
        }
    }

}

