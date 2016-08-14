package hackathon.airtel;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import functs.MyCalls;
import functs.Utils;
import me.everything.providers.android.calllog.Call;
import me.everything.providers.android.calllog.CallsProvider;
import me.everything.providers.android.telephony.Conversation;
import me.everything.providers.android.telephony.Sms;
import me.everything.providers.android.telephony.TelephonyProvider;

public class MyLogs extends AppCompatActivity {

    TextView num1,num2,num3;
    ListView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_logs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e("here", "started");
        // getCallDetails();

        setTitle("My Logs");
        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        num3 = (TextView) findViewById(R.id.num3);
        tv = (ListView) findViewById(R.id.text);


        new getSLogs().execute();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }





    public class getSLogs extends AsyncTask<String, Void, String> {
        SweetAlertDialog pDialog;
        List<MyCalls> mynum;
        ArrayList<String > items;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("in here", "stareefw");
            pDialog = new SweetAlertDialog(MyLogs.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            pDialog.setTitleText("Calculating Scores...");
            pDialog.setCancelable(false);
            pDialog.show();
            Log.e("vdslmvdspo", "started");
        }

        protected String doInBackground(String... urls) {

            CallsProvider callsProvider = new CallsProvider(MyLogs.this);
            List<Call> calendars = callsProvider.getCalls().getList();
            mynum = new ArrayList<>();
            MyCalls abc;

            long x;
            for (int i = 0; i < calendars.size(); i++) {
                Boolean find = false;

                x =  (calendars.get(i).duration);

                if(x<=0)
                    continue;

                for(int j=0;j<mynum.size();j++){
                    abc = mynum.get(j);




                    if(abc.getNumber().equals(calendars.get(i).number) || (
                            abc.getName() !=null && calendars.get(i).name!=null&&
                                    abc.getName().equals(calendars.get(i).name))){
                        find = true;
                        mynum.remove(j);
                        long m = abc.getScore();
                        abc.setscore(x + m);
                        mynum.add(abc);
                        break;
                    }
                }

                if(find==false){

                    abc = new MyCalls();
                    abc.setid(calendars.get(i).id);
                    abc.setname(calendars.get(i).name);
                    abc.setnumber(calendars.get(i).number);
                    abc.setscore(calendars.get(i).duration);
                    mynum.add(abc);
                }

            }




            Collections.sort(mynum, new Comparator<MyCalls>() {

                public int compare(MyCalls o1, MyCalls o2) {
                    return (o2.getScore()).compareTo(o1.getScore());
                }
            });



            items = new ArrayList<>();

            for(int i=3;i<mynum.size();i++){
                if(mynum.get(i).getName()!=null)
                    items.add(mynum.get(i).getName() + " " + mynum.get(i).getNumber());
                else
                    items.add( mynum.get(i).getNumber());
            }




            return null;
        }

        @Override
        protected void onPostExecute(String Result) {
            pDialog.dismiss();
            num1.setText(mynum.get(0).getName() + "\n" + mynum.get(0).getNumber());
            num2.setText(mynum.get(1).getName()+"\n"+mynum.get(1).getNumber());
            num3.setText(mynum.get(2).getName() + "\n" + mynum.get(2).getNumber());
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(MyLogs.this, android.R.layout.simple_list_item_1, items);

            tv.setAdapter(itemsAdapter);

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
