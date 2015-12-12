package hackathon.airtel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListActi extends AppCompatActivity {

    ListView lv;
    String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.music_list);

        Intent i = getIntent();
        uri = i.getStringExtra(Consts.servicetype);

        new getStrores().execute();

        setTitle("Airtel");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public class getStrores extends AsyncTask<String, Void, String> {
        SweetAlertDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("in here", "stareefw");
            pDialog = new SweetAlertDialog(ListActi.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            pDialog.setTitleText("Fetching data...");
            pDialog.setCancelable(false);
            pDialog.show();
            Log.e("vdslmvdspo", "started");
        }

        protected String doInBackground(String... urls) {
            try {



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
                JSONArray arr = res.getJSONArray("results");
                Shop_adapter s = new Shop_adapter(ListActi.this, arr);
                lv.setAdapter(s);


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("vsdfkvaes", e.getMessage() + " dsv");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
