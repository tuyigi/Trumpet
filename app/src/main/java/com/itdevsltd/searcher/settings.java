package com.itdevsltd.searcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by Teacher on 30-Mar-18.
 */

public class settings extends AppCompatActivity {
    Spinner language;
    SharedPreferences shared;
    Toolbar toolbar;
    public String  info="info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shared=getSharedPreferences("info",0);
        if(getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shared=getSharedPreferences(info,MODE_PRIVATE);
                    String ururimi=shared.getString("language","not found");
                    if(ururimi.equals("Kinyarwanda")){
                        Intent in=new Intent(settings.this,MainActivity.class);
                        startActivity(in);
                        settings.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                        finish();
                    }else if(ururimi.equals("English")){
                        Intent in=new Intent(settings.this,com.itdevsltd.searcher.english.MainActivity.class);
                        startActivity(in);
                        settings.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                        finish();
                    }else{
                        Intent in=new Intent(settings.this,MainActivity.class);
                        startActivity(in);
                        settings.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                        finish();
                    }

                }
            });
        }
        language=(Spinner) findViewById(R.id.spinner);

        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String lan= language.getSelectedItem().toString();
                SharedPreferences.Editor editor=shared.edit();
                try {
                    editor.remove("language");
                }catch (Exception e){
                    e.printStackTrace();
                }
                editor.putString("language",lan);
                editor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}

