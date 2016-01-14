package hackathon.airtel;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import functs.Utils;

public class chat extends AppCompatActivity {
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    SharedPreferences s;
    long  time;
    SharedPreferences.Editor e;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Live Chat");
        initControls();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);







    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        s = PreferenceManager.getDefaultSharedPreferences(chat.this);
        e = s.edit();
        uid = s.getString(Consts.userid, null);

        adapter = new ChatAdapter(chat.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        new getOldChat().execute();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                new sendMessage(messageText).execute();
                messageET.setText("");

                // displayMessage(chatMessage);
            }
        });


        /*while(true) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 10000);
        }*/




        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{
                    //do your code here
                    new getOldChat2().execute();
                }
                catch (Exception e) {
                }
                finally{
                    //also call the same runnable
                    handler.postDelayed(this, 10000);
                }
            }
        };
        handler.postDelayed(runnable, 10000);
    }







    public class getOldChat extends AsyncTask<String, Void, String> {
        SweetAlertDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("in here", "stareefw");
            pDialog = new SweetAlertDialog(chat.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            pDialog.setTitleText("Fetching Previous Chats...");
            pDialog.setCancelable(false);
            pDialog.show();
            Log.e("vdslmvdspo", "started");
        }

        protected String doInBackground(String... urls) {
            try {

                String uri =
                        "http://csinsit.org/prabhakar/airtel/chat/recieve.php?user="+uid+"&time=0";

                time = (System.currentTimeMillis())/1000;



                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                String readStream = Utils.readStream(con.getInputStream());

                Log.e("here", uri + " ");
                return readStream;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("erro",e.getMessage()+" ");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String Result) {
            pDialog.dismiss();
            if(Result==null)
                return;

            Log.e("here",Result+" ");
            try {
                JSONArray arr = new JSONArray(Result);
                JSONObject ob;
                for(int i=0;i<arr.length();i++){

                    ob = arr.getJSONObject(i);

                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setId(122);//dummy
                    chatMessage.setMessage(ob.getString("message"));
                    Date d = new Date(ob.getLong("time")*1000);
                    chatMessage.setDate(DateFormat.getDateInstance().format(d));
                    if(ob.getInt("flag")==1)
                        chatMessage.setMe(true);
                    else
                        chatMessage.setMe(false);

                    Log.e("dispalyin",ob.getString("message"));


                    displayMessage(chatMessage);

                }

                loadDummyHistory();

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.getMessage() + " dsv");
            }
        }
    }


    public class sendMessage extends AsyncTask<String, Void, String> {
        SweetAlertDialog pDialog;
        String msg;

        sendMessage(String x){
            msg=x;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("in here", "stareefw");
            pDialog = new SweetAlertDialog(chat.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            pDialog.setTitleText("Sending Message...");
            pDialog.setCancelable(false);
            pDialog.show();
            Log.e("vdslmvdspo", "started");
        }

        protected String doInBackground(String... urls) {
            try {

                String uri =
                        "http://csinsit.org/prabhakar/airtel/chat/send.php?user="+uid+"&message="+msg;
                uri = uri.replace(" ","%20");

                time = ((System.currentTimeMillis())/1000)+1;
                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                String readStream = Utils.readStream(con.getInputStream());

                Log.e("here", url + " ");
                return readStream;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String Result) {
            pDialog.dismiss();
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setId(122);//dummy
            chatMessage.setMessage(msg);
            chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatMessage.setMe(true);
            displayMessage(chatMessage);
        }
    }



    public class getOldChat2 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("checking", "started");
        }

        protected String doInBackground(String... urls) {
            try {

                String uri =
                        "http://csinsit.org/prabhakar/airtel/chat/recieve.php?user="+uid+"&time="+time;
                time = (System.currentTimeMillis())/1000;


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
            if(Result==null)
                return;

            Log.e("here", Result + " ");
            try {
                JSONArray arr = new JSONArray(Result);
                JSONObject ob;
                for(int i=0;i<arr.length();i++){

                    ob = arr.getJSONObject(i);

                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setId(122);//dummy
                    chatMessage.setMessage(ob.getString("message"));
                    Date d = new Date(ob.getLong("time")*1000);
                    chatMessage.setDate(DateFormat.getDateInstance().format(d));
                    if(ob.getInt("flag")==1)
                        chatMessage.setMe(true);
                    else
                        chatMessage.setMe(false);

                    Log.e("dispalyin",ob.getString("message"));

                    displayMessage(chatMessage);

                }


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.getMessage() + " dsv");
            }
        }
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }
    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }
    private void loadDummyHistory(){

        chatHistory = new ArrayList<>();

        ChatMessage msg1 = new ChatMessage();
        msg1.setId(0);
        msg1.setMe(false);
        msg1.setMessage("Hello! How can we help you?");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);


        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
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
