package hackathon.airtel;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by swati on 9/10/15.
 */


public class Offers_adapter extends BaseAdapter {

    Context context;
    int pos;
    JSONArray FeedItems;
    private static LayoutInflater inflater = null;

    public Offers_adapter(Context context, JSONArray FeedItems) {
        this.context = context;
        this.FeedItems = FeedItems;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return FeedItems.length();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        try {
            return FeedItems.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.service_listitem, null);

        TextView Title = (TextView) vi.findViewById(R.id.VideoTitle);


        pos = position;
        try {
            Title.setText(FeedItems.getJSONObject(position).getString("service"));


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("eroro", e.getMessage() + " ");
        }

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent browserIntent = null;
                try {
                    if (FeedItems.getJSONObject(position).getInt("flag") == 1) {

                        if(FeedItems.getJSONObject(position).getString("service").contains("Recharge")){
                            Intent i = new Intent(context, recharge.class);
                            context.startActivity(i);
                        }else {
                            Intent i = new Intent(context, ListActi.class);
                            i.putExtra(Consts.servicetype, FeedItems.getJSONObject(position).getString("url"));
                            context.startActivity(i);
                        }
                    }

                    else {


                        new getOffer().execute();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return vi;
    }



    public class getOffer extends AsyncTask<String, Void, String> {
        SweetAlertDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("in here", "stareefw");
            pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            pDialog.setTitleText("Loading..");
            pDialog.setCancelable(false);
            pDialog.show();   Log.e("vdslmvdspo","started");
        }

        protected String doInBackground(String... urls) {
            try {


                String uri = "http://csinsit.org/prabhakar/airtel/services/get-token.php" ;
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

                    final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
                    pDialog.setTitleText("You need to wait " + res.getString("estimated_time") +
                            " minutes" );
                    pDialog.setContentText("This is service is available at counter number : " +
                            Integer.toString(pos+10)+"\n"+
                    "Your token number is : " + res.getString("token number"));
                    pDialog.setCancelable(true);
                    pDialog.show();


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("vsdfkvaes", e.getMessage() + " dsv");
            }
        }
    }


}