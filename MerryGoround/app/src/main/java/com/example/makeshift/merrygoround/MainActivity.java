package com.example.makeshift.merrygoround;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Constants {
    EditText editentNo,editentName,editentChama;
    Button saveNO,saveChama;
    TextView disChama,showSpinner;
    SharedPreferences mChamaSettings;
    Spinner chCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChamaSettings =
                getSharedPreferences(CHAMA_PREFERENCES, Context.MODE_PRIVATE);
        editentName=(EditText)findViewById(R.id.editAdminName);
        editentNo=(EditText)findViewById(R.id.editAdminNo);
        editentChama=(EditText)findViewById(R.id.editChamaName);
        saveNO=(Button)findViewById(R.id.btnEnterAdminNo);
        saveChama=(Button)findViewById(R.id.btnEnterChamaName);
        disChama=(TextView)findViewById(R.id.txtChamaName);
        showSpinner=(TextView)findViewById(R.id.txtSpinShow);

        String chCles[]={"Daily","Weekly","Monthly"};
        chCycle=(Spinner)findViewById(R.id.spinnerstart);
        ArrayAdapter<String> adapterStates=
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,chCles);
        chCycle.setAdapter(adapterStates);

        editentChama.setVisibility(View.INVISIBLE);
        saveChama.setVisibility(View.INVISIBLE);
        disChama.setVisibility(View.INVISIBLE);
        saveNO.setVisibility(View.INVISIBLE);
        showSpinner.setVisibility(View.INVISIBLE);
        chCycle.setVisibility(View.INVISIBLE);




        editentNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                saveNO.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==10 ){
                    saveNO.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                saveNO.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editentChama.setVisibility(View.VISIBLE);
                saveChama.setVisibility(View.VISIBLE);
                disChama.setVisibility(View.VISIBLE);
                final SharedPreferences.Editor editor = mChamaSettings.edit();
                editor.putString(CHAMA_ADMIN_NO,editentNo.getText().toString());
                editor.commit();
                final SharedPreferences.Editor editor2 = mChamaSettings.edit();
                editor.putString(CHAMA_ADMIN_NAME,editentName.getText().toString());
                editor.commit();
                Toast.makeText(getApplicationContext(),"Number saved",Toast.LENGTH_SHORT).show();
                saveNO.setVisibility(View.INVISIBLE);
                editentNo.setVisibility(View.INVISIBLE);
                editentName.setVisibility(View.INVISIBLE);
                saveNO.setVisibility(View.INVISIBLE);
                showSpinner.setVisibility(View.VISIBLE);
                chCycle.setVisibility(View.VISIBLE);

            }
        });
        chCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Integer toCycle=86400;
                    String a=String.valueOf(toCycle);
                    final SharedPreferences.Editor editor = mChamaSettings.edit();
                    editor.putString(CHAMA_CYCLE, a);
                    editor.commit();


                }else if (position==1){
                    Integer toCycle=86400 *7;
                    String a=String.valueOf(toCycle);
                    final SharedPreferences.Editor editor = mChamaSettings.edit();
                    editor.putString(CHAMA_CYCLE, a);
                    editor.commit();

                }else if(position==2){

                    Integer toCycle=86400 *28;
                    String a=String.valueOf(toCycle);
                    final SharedPreferences.Editor editor = mChamaSettings.edit();
                    editor.putString(CHAMA_CYCLE, a);
                    editor.commit();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Integer toCycle=86400;
                String a=String.valueOf(toCycle);
                final SharedPreferences.Editor editor = mChamaSettings.edit();
                editor.putString(CHAMA_CYCLE, a);
                editor.commit();

            }
        });


saveChama.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(getApplicationContext(), AddMembers.class);
        startActivity(i);
        final SharedPreferences.Editor editor = mChamaSettings.edit();
        editor.putString(CHAMA_NAME, editentChama.getText().toString());
        editor.commit();
        Toast.makeText(getApplicationContext(),"Chama name Saved",Toast.LENGTH_SHORT).show();
    }
});

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
