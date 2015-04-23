package com.example.makeshift.merrygoround;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Handler;


public class SplashActivity extends Constants {
    SharedPreferences mChamaSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChamaSettings =
                getSharedPreferences(CHAMA_PREFERENCES, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                if (mChamaSettings.contains(CHAMA_ADMIN_NO)
                    & mChamaSettings.contains(CHAMA_ADMIN_NAME)
                    & mChamaSettings.contains(CHAMA_NAME)
                    & mChamaSettings.contains(CHAMA_MEMBERS_LIST)
                         ){

                    Intent i = new Intent(getBaseContext(), ViewMembers.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                }
// if(mChamaSettings.contains(CHAMA_ADMIN_NAME)
//                        & mChamaSettings.contains(CHAMA_ADMIN_NO)){
//                    Intent i=new Intent(getBaseContext(),AddMembers.class);
//                    startActivity(i);
//                    finish();
//
//                }
                else {
                    // After 5 seconds redirect to another intent
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                }
            }
        }, 3 * 1000); // wait for 3 seconds


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
}
