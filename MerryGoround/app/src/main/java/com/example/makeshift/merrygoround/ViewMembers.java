package com.example.makeshift.merrygoround;

import android.annotation.SuppressLint;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;


public class ViewMembers extends Constants {
    SharedPreferences mChamaSettings;
    ListView members;

    ArrayList<ListItem> membersList;
    PendingIntent pi,pi2;

    BroadcastReceiver br,br2;
    Button more;
    AlarmManager am,am2;
    private PendingIntent pendingIntent,pendingIntent2;
    int decider=-1;
    static final int MEMBER_DIALOG_ID = 0;
    TextView e,f;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_members);
        mChamaSettings =
                getSharedPreferences(CHAMA_PREFERENCES, Context.MODE_PRIVATE);
        members=(ListView)findViewById(R.id.viewChama);
        TextView title=(TextView)findViewById(R.id.title_chama);
        title.setText(mChamaSettings.getString(CHAMA_NAME,null).toString()+" members");
           membersList=new ArrayList<ListItem>();

 String jsonAllMembers=mChamaSettings.getString(CHAMA_MEMBERS_LIST,null);
          Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ListItem>>() {}.getType();
       membersList = gson.fromJson(jsonAllMembers, listType);

 ListItemAdapter adapters = new ListItemAdapter(getApplicationContext(),
                R.layout.list_item, membersList);
        members.setAdapter(adapters);
        members.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent orb=new Intent(getApplicationContext(),ViewSingleMemberActivity.class);
                onPause();
            orb.putExtra("id", position);
                startActivity(orb);
                savePreferences();

            }
        });


        String timeCycleString=mChamaSettings.getString(CHAMA_TIME_SET,null);
        Long timeCyclesetLong=Long.parseLong(timeCycleString);

  /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(ViewMembers.this,ViewMembers.AlarmReceiver.class);
        Intent alarmIntent2 = new Intent(ViewMembers.this,ViewMembers.AlarmReceiver2.class);

        pendingIntent = PendingIntent.getBroadcast(ViewMembers.this, 0, alarmIntent, 0);
        pendingIntent2 = PendingIntent.getBroadcast(ViewMembers.this, 0, alarmIntent2, 0);
        start();

        more=(Button)findViewById(R.id.btnAddMore);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AddMembers.class);
                startActivity(i);
                savePreferences();
            }
        });


    }

    private void savePreferences() {
        Gson gson = new Gson();
        String jsonMembers= gson.toJson(membersList);
        SharedPreferences.Editor editor = mChamaSettings.edit();
        editor.putString(CHAMA_MEMBERS_LIST,jsonMembers );
        editor.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_members, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
    public void onStop(){
        super.onStop();
        Gson gson = new Gson();
 String jsonMembers= gson.toJson(membersList);
        SharedPreferences.Editor editor = mChamaSettings.edit();
        editor.putString(CHAMA_MEMBERS_LIST,jsonMembers );
        editor.commit();


    }

    public void onDestroy(){
        super.onDestroy();
    }
    public void start() {
     String timeSetString=mChamaSettings.getString(CHAMA_TIME_SET,null);
    int timesetLong=Integer.parseInt(timeSetString);


        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
      manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timesetLong, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();

//        String timeCycleString=mChamaSettings.getString(CHAMA_TIME_SET,null);
//        int timeCyclesetLong=Integer.parseInt(timeCycleString);
        int timeCyclesetLong=2000;


        AlarmManager manager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager2.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timeCyclesetLong, pendingIntent2);
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }




    private class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            for(int e=0;e<membersList.size();e++){
                String phone=membersList.get(e).getName();
                String mNAme=membersList.get(e).getPhoneNo();
                String chama=mChamaSettings.getString(CHAMA_NAME,null);
                String message="Hi "+mNAme+" this is a reminder.Remember to make your contribuions to our chama "+ chama+" Thank You";


                /** Creating a pending intent which will be broadcasted when an sms message is successfully sent */
                PendingIntent piSent = PendingIntent.getBroadcast(getBaseContext(), 0,
                        new Intent("in.merrygoround.sent") , 0);

                /** Creating a pending intent which will be broadcasted when an sms message is successfully delivered */
                PendingIntent piDelivered = PendingIntent.getBroadcast(getBaseContext(), 0,
                        new Intent("in.merrygoround.delivered"), 0);

                /** Getting an instance of SmsManager to sent sms message from the application*/
                SmsManager smsManager = SmsManager.getDefault();

                /** Sending the Sms message to the intended party */
                smsManager.sendTextMessage(phone, null, message, piSent, piDelivered);
                Toast.makeText(getApplicationContext(),"Sucess update",Toast.LENGTH_SHORT).show();
            }
            // For our recurring task, we'll just display a message
            Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        }
    }
    private class AlarmReceiver2 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplication(),"this did work",Toast.LENGTH_SHORT).show();

            if(decider==-1){
                String ert=mChamaSettings.getString(CHAMA_ADMIN_NO,null);
                final SharedPreferences.Editor editor = mChamaSettings.edit();
                editor.putString(CHAMA_CURRENT_RECIEVER_NAME, "You");
                editor.commit();
                final SharedPreferences.Editor editor2 = mChamaSettings.edit();
                editor.putString(CHAMA_CURRENT_RECIEVER_NO, ert);
                editor.commit();

                ViewMembers.this.showDialog(MEMBER_DIALOG_ID);



            }else if(decider<membersList.size()){
                String ert=membersList.get(decider).getPhoneNo();
                String art=membersList.get(decider).getName();
                final SharedPreferences.Editor editor = mChamaSettings.edit();
                editor.putString(CHAMA_CURRENT_RECIEVER_NAME, ert);
                editor.commit();
                final SharedPreferences.Editor editor2 = mChamaSettings.edit();
                editor.putString(CHAMA_CURRENT_RECIEVER_NO, art);
                editor.commit();

                ViewMembers.this.showDialog(MEMBER_DIALOG_ID);


            }else if(decider>membersList.size()){
                decider=-1;
                String ert=mChamaSettings.getString(CHAMA_ADMIN_NO,null);
                final SharedPreferences.Editor editor = mChamaSettings.edit();
                editor.putString(CHAMA_CURRENT_RECIEVER_NAME, "You");
                editor.commit();
                final SharedPreferences.Editor editor2 = mChamaSettings.edit();
                editor.putString(CHAMA_CURRENT_RECIEVER_NO, ert);
                editor.commit();

                ViewMembers.this.showDialog(MEMBER_DIALOG_ID);

            }

        }


    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case MEMBER_DIALOG_ID:
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View layoutscore =
                        inflater.inflate(R.layout.layout_dialog_member_tomoney,
                                (ViewGroup) findViewById(R.id.bilboBaggins));
                  e=(TextView)layoutscore.findViewById(R.id.LuckyUserNAme);
                  f=(TextView)layoutscore.findViewById(R.id.LuckyUserNo);
                String we=mChamaSettings.getString(CHAMA_CURRENT_RECIEVER_NAME,null);
                String wewe=mChamaSettings.getString(CHAMA_CURRENT_RECIEVER_NO,null);
                e.setText(we);
                f.setText(wewe);


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(layoutscore);

                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                decider++;
                                e.clearComposingText();
                                f.clearComposingText();
                                ViewMembers.this.removeDialog(MEMBER_DIALOG_ID);


                            }
                        });


                AlertDialog lDialog = builder.create();
                return lDialog;
        }
            return null;

    }



}
