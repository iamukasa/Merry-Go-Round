package com.example.makeshift.merrygoround;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class AddMembers extends Constants {
    SharedPreferences mChamaSettings;
    EditText newMemberNo,newMemberName;
    Button saveNewMember,btnDone;
    ListView memberList;
    ArrayList <String> newMemberNameArr;
    ArrayList <String> newMemberNoArr;
    ArrayList<ListItem> fullmembersList;
    static final int OTHER_REASON = 0;
    Spinner listS;
    int timep;
    int it=0;


    String a,b;

    final static private long ONE_SECOND = 1000;
     PendingIntent pi;
     BroadcastReceiver br;
    AlarmManager am;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members);
        newMemberNameArr=new ArrayList<String>();
        newMemberNoArr=new ArrayList<String>();
        fullmembersList=new ArrayList<ListItem>();



        mChamaSettings =
                getSharedPreferences(CHAMA_PREFERENCES, Context.MODE_PRIVATE);
        newMemberName=(EditText)findViewById(R.id.editNewMemberName);
        newMemberNo=(EditText)findViewById(R.id.editNewMemberNo);
        saveNewMember=(Button)findViewById(R.id.saveNewMember);
        memberList=(ListView)findViewById(R.id.listMembers);
        btnDone=(Button)findViewById(R.id.DoneAdding);
               saveNewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=newMemberName.getText().toString();
                b=newMemberNo.getText().toString();
                newMemberNameArr.add(a);
                newMemberNoArr.add(b);

          sendMessages();
                memberList=(ListView)findViewById(R.id.listMembers);



    fullmembersList.add(new ListItem(newMemberNameArr.get(it),newMemberNoArr.get(it)));
    ListItemAdapter adapter = new ListItemAdapter(getApplicationContext(),
            R.layout.list_item, fullmembersList);
    memberList.setAdapter(adapter);
    it++;



            }
        });
       listS =(Spinner)findViewById(R.id.listdialogTimes);
        String [] options={"Once a day","Once every Two Days","Weekly ","Monthly"};
        ArrayAdapter<String> adapterStates=
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options);
        listS.setAdapter(adapterStates);
    listS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    timep =86400*1000;
                    SharedPreferences.Editor editor = mChamaSettings.edit();
                    editor.putString(CHAMA_TIME_SET,String.valueOf(timep) );
                    editor.commit();


                }else if (position==1){
                    timep =86400*1000*2;

                    SharedPreferences.Editor editor = mChamaSettings.edit();
                    editor.putString(CHAMA_TIME_SET,String.valueOf(timep) );
                    editor.commit();


                }else if(position==2){
                    timep =86400*1000*7;
                    SharedPreferences.Editor editor = mChamaSettings.edit();
                    editor.putString(CHAMA_TIME_SET,String.valueOf(timep) );
                    editor.commit();
                    Intent Ill=new Intent(getApplicationContext(),ViewMembers.class);
                    startActivity(Ill);

                }else if(position==3){
                    timep =86400*1000*28;
                    SharedPreferences.Editor editor = mChamaSettings.edit();
                    editor.putString(CHAMA_TIME_SET,String.valueOf(timep) );
                    editor.commit();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                timep =86400*1000;
                SharedPreferences.Editor editor = mChamaSettings.edit();
                editor.putString(CHAMA_TIME_SET,String.valueOf(timep) );
                editor.commit();

            }
        });


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String jsonMembers= gson.toJson(fullmembersList);
                SharedPreferences.Editor editor = mChamaSettings.edit();
                editor.putString(CHAMA_MEMBERS_LIST,jsonMembers );
                editor.commit();
             Intent inte=new Intent(getApplicationContext(),ViewMembers.class);
                      startActivity(inte);

                              finish();
                      }
        });

    }

    private void sendMessages() {
        String sender=mChamaSettings.getString(CHAMA_ADMIN_NAME,null).toString();
        String chamaname=mChamaSettings.getString(CHAMA_NAME,null).toString();
        String message="Hi I ,"+sender+" ,have added you to my  merry-go-round group "+chamaname+" Please dont forget to make contributions as we agreed";



        /** Creating a pending intent which will be broadcasted when an sms message is successfully sent */
        PendingIntent piSent = PendingIntent.getBroadcast(getBaseContext(), 0,
                new Intent("in.merrygoround.sent") , 0);

        /** Creating a pending intent which will be broadcasted when an sms message is successfully delivered */
        PendingIntent piDelivered = PendingIntent.getBroadcast(getBaseContext(), 0,
                new Intent("in.merrygoround.delivered"), 0);

        /** Getting an instance of SmsManager to sent sms message from the application*/
        SmsManager smsManager = SmsManager.getDefault();

        /** Sending the Sms message to the intended party */
        smsManager.sendTextMessage(b, null, message, piSent, piDelivered);
        newMemberNo.clearComposingText();
        newMemberName.clearComposingText();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_members, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
 //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

             return true;
         }
  return super.onOptionsItemSelected(item);
    }
    public void onRestart(){

        super.onRestart();



    }
    public void onStart(){
       if(mChamaSettings.contains(CHAMA_MEMBERS_LIST)) {
            String jsonAllMembers=mChamaSettings.getString(CHAMA_MEMBERS_LIST,null);

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ListItem>>() {}.getType();
        fullmembersList = gson.fromJson(jsonAllMembers, listType);
        ListItemAdapter adapters = new ListItemAdapter(getApplicationContext(),
                R.layout.list_item, fullmembersList);
        memberList.setAdapter(adapters);

        }

        super.onStart();


    }
    public void onStop(){
        super.onStop();
        Gson gson = new Gson();

        String jsonMembers= gson.toJson(fullmembersList);
        SharedPreferences.Editor editor = mChamaSettings.edit();
        editor.putString(CHAMA_MEMBERS_LIST,jsonMembers );
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
