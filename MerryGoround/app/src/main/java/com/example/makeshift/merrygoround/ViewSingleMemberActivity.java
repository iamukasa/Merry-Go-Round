package com.example.makeshift.merrygoround;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class ViewSingleMemberActivity extends Constants{
    TextView vNAme,VPhone;
    SharedPreferences mChamaSettings;
    ArrayList<ListItem> membersList;
  ArrayList <String>msgAdress,msgBody,ard,werd;
    ArrayList<ContributionsItems> msgList;
    Cursor c;
    SimpleCursorAdapter adapter;
    ListView bull,bullshit;
ContributionsItemAdapter adept;
    Button s;
    EditText ds,ds2;
    int drogba=0;
      String Kal,Kal2;
    ArrayList<ContributionsItems> e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_view_single_member);
        mChamaSettings =
                getSharedPreferences(CHAMA_PREFERENCES, Context.MODE_PRIVATE);
        msgBody=new ArrayList<String>();
        msgList=new ArrayList<ContributionsItems>();
        msgAdress=new ArrayList<String>();
        ard=new ArrayList<String>();
        werd=new ArrayList<String>();
        bull=(ListView)findViewById(R.id.contributionslist);
      s=(Button)findViewById(R.id.saveNewContributions);
        ds=(EditText)findViewById(R.id.editNewAmountName);
        ds2=(EditText)findViewById(R.id.editNewDate);
        bullshit=(ListView)findViewById(R.id.listContributions);

        String jsonAllMembers=mChamaSettings.getString(CHAMA_MEMBERS_LIST,null);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ListItem>>() {}.getType();
        membersList = gson.fromJson(jsonAllMembers, listType);
     vNAme=(TextView)findViewById(R.id.vName);
       VPhone=(TextView)findViewById(R.id.vPhoneNo);
       Intent i = getIntent();
        // Selected image id
        int position = i.getExtras().getInt("id");
        membersList.get(position).getPhoneNo();
        membersList.get(position).getName();
        vNAme.setText(membersList.get(position).getPhoneNo());
        VPhone.setText(membersList.get(position).getName());

//        // Create Inbox box URI
//        Uri inboxURI = Uri.parse("content://sms/inbox");
//
//        // List required columns
//        String[] reqCols = new String[] { "_id", "address", "body","subject"};
//        final String phno = membersList.get(position).getName(); // contains an "A
//        String [] selectArguments=new String[]{"body ",""};
//
//        // Get Content Resolver object, which will deal with Content Provider
//        ContentResolver cr = getContentResolver();
//
//
//        // Fetch Inbox SMS Message from Built-in Content Provider
//       c = cr.query(inboxURI,
//               reqCols,
//               "address" + "='" + "MPESA" + "'" ,
//               null,
//               null);
//
//
//
//        adapter = new SimpleCursorAdapter(this, R.layout.row, c,
//                new String[] { "body", "address" }, new int[] {
//                R.id.lblMsg, R.id.lblNumber });
//       bull.setAdapter(adapter);
//
//
//        while (c.moveToNext()) {
//            String strBody = c.getString(c.getColumnIndex("body" ));
//            String strAdress = c.getString(c.getColumnIndex("address" ));
//            msgBody.add(strBody);
//            msgAdress.add(strAdress);
//
//        }


        Kal= membersList.get(position).getPhoneNo();
        Kal2= membersList.get(position).getName();
 s.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         String e=ds.getText().toString();
        String f= ds2.getText().toString();

         werd.add(e);
         ard.add(f);

         msgList.add(new ContributionsItems(Kal2  ,werd.get(drogba),ard.get(drogba)));
         drogba++;

         adept=new ContributionsItemAdapter(getApplicationContext(),R.layout.contributions_item,msgList);
         bullshit.setAdapter(adept);

         Gson gsone = new Gson();
         String jsonContributions= gsone.toJson(msgList);
         SharedPreferences.Editor editor = mChamaSettings.edit();
         editor.putString(CHAMA_CURRENT_CONTRIBUTIONS_LIST,jsonContributions );
         editor.commit();

     }

 });





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_single_member, menu);
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
    public void onPause(){
        Gson gsone = new Gson();
        String jsonContributions= gsone.toJson(msgList);
        SharedPreferences.Editor editor = mChamaSettings.edit();
        editor.putString(CHAMA_CURRENT_CONTRIBUTIONS_LIST,jsonContributions );
        editor.commit();
        super.onPause();
    }
    public void onDestroy(){
        Gson gsone = new Gson();
        String jsonContributions= gsone.toJson(msgList);
        SharedPreferences.Editor editor = mChamaSettings.edit();
        editor.putString(CHAMA_CURRENT_CONTRIBUTIONS_LIST,jsonContributions );
        editor.commit();
        super.onDestroy();
    }
    public void onStart(){
        bullshit=(ListView)findViewById(R.id.listContributions);

        if(mChamaSettings.contains(CHAMA_CURRENT_CONTRIBUTIONS_LIST)) {
            String jsonAllMembers=mChamaSettings.getString(CHAMA_CURRENT_CONTRIBUTIONS_LIST,null);

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<ContributionsItems>>() {}.getType();
            msgList = gson.fromJson(jsonAllMembers, listType);
            ContributionsItemAdapter adapters = new ContributionsItemAdapter(getApplicationContext(),
                R.layout.contributions_item, msgList);
//            bullshit.setTextFilterEnabled(true);
//            adapters.getFilter().filter(Kal2);

          bullshit.setAdapter(adapters);

        }
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Gson gsone = new Gson();
        String jsonContributions= gsone.toJson(msgList);
        SharedPreferences.Editor editor = mChamaSettings.edit();
        editor.putString(CHAMA_CURRENT_CONTRIBUTIONS_LIST,jsonContributions );
        editor.commit();
        Intent i= new  Intent(getApplicationContext(),ViewMembers.class);
        startActivity(i);
    }
}
