package com.itdevsltd.searcher.english;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.net.*;
import android.os.*;
import android.net.*;
import java.io.*;
import android.widget.*;
import android.util.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v4.app.*;
import android.content.*;
import android.text.*;
import android.support.v7.widget.*;
import android.view.*;
import android.graphics.drawable.*;
import android.view.View.*;

import com.itdevsltd.searcher.R;

public class HomeActivity extends AppCompatActivity
        {
SharedPreferences shared;
	public String  info="info";	
	
	CardView cv2;
	public TextView title;
	public TextView status;
	public TextView seriall;
	public ImageView tp;
	public TextView owner;
	public TextView description;
	public TextView infs;
	RelativeLayout rl;	
	TextView found;
	TextView found2;
	String phoneNumber;
	Button call;
	Button message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//Window window = getWindow();
		//Drawable colorDrawable = new ColorDrawable(R.color.transparent);
		
		
		setContentView(R.layout.activity_main);
		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		if(getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
        }
		
		this.setTitle("Found");
        found2=(TextView)findViewById(R.id.found2);
        found2.setText("Is this yours or you have found it,enter the full number in order to be recognize");
        TextView inputserial=(TextView)findViewById(R.id.input_serial);
        inputserial.setHint("Enter its full number");
        Button komeza=(Button)findViewById(R.id.checkbtn);
        komeza.setText("Continue");
		title= (TextView) findViewById(R.id.title) ;
		status= (TextView) findViewById(R.id.status) ;
		seriall= (TextView) findViewById(R.id.serial) ;
		tp=(ImageView)findViewById(R.id.tp);
		owner= (TextView)findViewById(R.id.owner) ;
		description= (TextView)findViewById(R.id.description) ;
		infs= (TextView)findViewById(R.id.infs) ;
		rl=(RelativeLayout) findViewById(R.id.hidden);
		cv2=(CardView) findViewById(R.id.card_view3);
		found=(TextView) findViewById(R.id.found);
	call=(Button) findViewById(R.id.call);
	message=(Button) findViewById(R.id.message);
		
	call.setOnClickListener(new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
			startActivity(intent);
		}
	});
		 
		message.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Uri sms_uri = Uri.parse("sms to:"+phoneNumber);
					Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri); 
					sms_intent.putExtra("Write your message here", "Talking request needed");
					startActivity(sms_intent); 
				}
			});
	
	
	
		Bundle bundle=getIntent().getExtras();
		if(bundle.getString("property_name").equals("none"))
		{
			
		}
		else
		{
		cv2.setVisibility(View.VISIBLE);
		String tit=bundle.getString("property_name");
		String ser=bundle.getString("property_serial_num");
		String stat=bundle.getString("property_status");
		String own=bundle.getString("property_owner_names");

		String addre=bundle.getString("property_owner_address");
		String phon=bundle.getString("property_owner_phone");
		String des=bundle.getString("property_address");
		phoneNumber=phon;
		
		String who;
		if(stat.equals("lost"))
		{
			found.setText("There is someone who missed this equipment, you talk with him!");
			
			who="Loser's address\n";
		}
		else
		{
			found.setText("There is someone who found this equipment,you can talk with him!");
			
			who="Founder's address\n";

		}
		String fullinfo=who+"Full names: "+own+"\nTelephone: "+phon+"\nAddress: "+addre;
		infs.setText(fullinfo);
		title.setText(tit);

		// Toast.makeText(MainActivity.this,des,Toast.LENGTH_LONG).show();

		




		seriall.setText("Serial number :"+ser);

		String stat2=null;
		String own2=own;
		String aho;
		if(stat.equals("lost"))
		{
			aho="Lost place: "+des;
			own2="Loser: "+own2;
			stat2="<font color='red'>lost</font>";
		}
		else
		{
			own2="Founder: "+own2;
			aho="Found place: "+des;
			stat2="<font color='green'>Found</font>";

		}
			description.setText(aho);
			
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			status.setText(Html.fromHtml(stat2,Html.FROM_HTML_MODE_LEGACY));
		} else {
			status.setText(Html.fromHtml(stat2));
		}

		owner.setText(own2);

		
		}
		}

    @Override
    public void onBackPressed() {
             super.onBackPressed();
        
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

