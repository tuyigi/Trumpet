package com.itdevsltd.searcher.english;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.*;
import java.util.*;
import android.os.*;
import java.net.*;
import android.net.*;
import java.io.*;
import android.text.*;
import android.widget.*;
import org.json.*;
import android.view.animation.*;
import android.content.*;
import android.app.*;
import android.service.notification.*;
import java.security.acl.*;
import android.support.design.widget.TextInputLayout;
import android.widget.SearchView.*;
import android.view.*;
import android.view.inputmethod.*;
import com.itdevsltd.searcher.R;
import com.itdevsltd.searcher.settings;


public class MainActivity extends AppCompatActivity {
	public static final int CONNECTION_TIMEOUT=10000;
	public static final int READ_TIMEOUT=15000;
	
	RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
    private static RecyclerView recyclerView;
	CardView cv;
	CardView cv2;
	List<getData> getdata1;
	getData getdata;
	String fullserial=null;
	EditText query;
	ProgressBar progress;
	ImageView close;
	ImageView close2;
	TextView loading;
	Button natoye;
	Button nataye;
	
	
	public TextView title;
	public TextView status;
	public TextView seriall;
	public ImageView tp;
	public TextView owner;
	public TextView description;
	public TextView infs;
	RelativeLayout rl;
	TextInputLayout layout_serial;
	EditText input_serial;
	Button checkbtn;
	SharedPreferences shared;
	public String  info="info";
	public String check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().getThemedContext();


		getdata1=new ArrayList<>();
		recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
       recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
		recyclerViewadapter = new RecyclerViewAdapter(getdata1,this);
        recyclerView.setAdapter(recyclerViewadapter);
		
		nataye=(Button) findViewById(R.id.nataye);
		nataye.setText("Lost");
		natoye=(Button) findViewById(R.id.natoye);
		natoye.setText("Found");
		close=(ImageView) findViewById(R.id.close);
		
		close2=(ImageView) findViewById(R.id.close2);
		loading=(TextView) findViewById(R.id.loading);
		cv=(CardView) findViewById(R.id.card_view2);
		
		cv2=(CardView) findViewById(R.id.card_view3);
		query=(EditText) findViewById(R.id.query);
		progress=(ProgressBar) findViewById(R.id.progress);
		query.addTextChangedListener(new MyTextWatcher(query));
		TextView desc=(TextView)findViewById(R.id.desc);
		desc.setText("Welcome on this application that allow you to find your lost properties in easy way ");
		
		title= (TextView) findViewById(R.id.title) ;
		status= (TextView) findViewById(R.id.status) ;
		seriall= (TextView) findViewById(R.id.serial) ;
		tp=(ImageView)findViewById(R.id.tp);
		owner= (TextView)findViewById(R.id.owner) ;
		description= (TextView)findViewById(R.id.description) ;
		infs= (TextView)findViewById(R.id.infs) ;
		rl=(RelativeLayout) findViewById(R.id.hidden);
		input_serial=(EditText) findViewById(R.id.input_serial);
		layout_serial=(TextInputLayout) findViewById(R.id.layout_serial);
		checkbtn=(Button) findViewById(R.id.checkbtn);
		
     // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				Animation anim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.abc_slide_out_top);
				cv.setAnimation(anim);
                cv.setVisibility(View.INVISIBLE);
				}
        });
		close2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Animation anim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.abc_slide_out_top);
					cv2.setAnimation(anim);
					cv2.setVisibility(View.INVISIBLE);
				
						
					}
			});
		shared=getSharedPreferences(info,MODE_PRIVATE);
		 check=shared.getString("reg","no");
		if(!check.equals("yes"))
		{
		
		}
		
	natoye.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					
			
			
			if(check.equals("yes"))
			{
				Intent in=new Intent(getApplicationContext(),natoye.class);
				startActivity(in);
				MainActivity.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
				
			}
			else
			{
				finish();
				Intent intent =new Intent(getApplicationContext(),register.class);
				startActivity(intent);
				
				MainActivity.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
				
			}
			}});
			
			
			
		nataye.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {


					
					if(check.equals("yes"))
					{
						Intent in=new Intent(getApplicationContext(),nataye.class);
						startActivity(in);
						MainActivity.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

					}
					else
					{
						finish();
						Intent intent =new Intent(getApplicationContext(),register.class);
						startActivity(intent);

						MainActivity.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

					}
				}});
				
				
				
		recyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, recyclerView, new RecyclerTouchListener.ClickListener() {


					 @Override
					 public void onClick(View view, int position) {

					 getData getValue=getdata1.get(position);
					  String type=getValue.getType();					
				        String tit=getValue.getTitle();					
						 String own=getValue.getOwner();					
						 String stat=getValue.getStatus();					
						 String ser=getValue.getSerial();					
						 fullserial=ser;
                         String des=getValue.getDesc();
						 String phone=getValue.getPhone();
						 String address=getValue.getAddress();
						 
							
						 final Bundle bundle=new Bundle();
						 bundle.putString("property_name",tit);
						 bundle.putString("property_serial_num",ser);
						 bundle.putString("property_status",stat);
						 bundle.putString("property_owner_names",own);
						 bundle.putString("property_owner_address",address);
						 bundle.putString("property_owner_phone",phone);
						 bundle.putString("property_address",des);


						 final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
						 builder.setTitle("Needed");
						 builder.setMessage("Is this your equipment or you have founded it? enter its serial number!");
						 
						 final EditText input = new EditText(MainActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
						 input.setInputType(InputType.TYPE_CLASS_TEXT);
						 input.setHint("Number of equipment");
						 builder.setView(input);

// Set up the buttons
						 builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
								 @Override
								 public void onClick(DialogInterface dialog, int which) {
									 String text = input.getText().toString();
									 if(text.trim().equals(fullserial))
									 {
										 Intent in=new Intent(MainActivity.this,HomeActivity.class);
										 in.putExtras(bundle);
										 startActivity(in);
										 MainActivity.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
										 
									 }
									 else
									 {
										 Toast.makeText(MainActivity.this,"Incorrect serial number!",Toast.LENGTH_LONG).show();
								
										 }
								 }
							 });
						 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								 @Override
								 public void onClick(DialogInterface dialog, int which) {
									 dialog.cancel();
								 }
							 });

						 builder.show();


									 

						 
						 
						  
				 }

				 @Override
		 public void onLongClick(View view, int position) {



													 }
												 }));	
				
				
	checkbtn.setOnClickListener(new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			if(input_serial.getText().toString().equals(""))
			{
				layout_serial.setError("Enter number");
				
			}
			else if(!(input_serial.getText().toString().trim().equals(fullserial.trim())))
			{
				layout_serial.setError("Incorrect number");
				
			}
			else
			{
				rl.setVisibility(View.VISIBLE);
				seriall.setText("Serial number: "+fullserial);
				checkbtn.setVisibility(View.GONE);
				layout_serial.setVisibility(View.GONE);
			}
				
			
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
			Intent in=new Intent(MainActivity.this,com.itdevsltd.searcher.settings.class);
			startActivity(in);
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	
	
	////////////////////////////////////////////////
//////////////////////////////////////////////////////////


	private class loader extends AsyncTask<String, String, String>
	{
		HttpURLConnection conn;
		URL url = null;
		String question="hey";

		@Override
		protected void onPreExecute()
		{
       progress.setVisibility(View.VISIBLE);
			
			cv.setVisibility(View.VISIBLE);
			loading.setText("Loading...");

			// TODO: Implement this method
			super.onPreExecute();
		}


		protected String doInBackground(String... params) {

			try {


					url=new URL("https://www.trumpet.rw/app_files/app_api.php");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "exception";
			}
			try {
				// Setup HttpURLConnection class to send and receive data from php and mysql
				conn = (HttpURLConnection)url.openConnection();
				conn.setReadTimeout(READ_TIMEOUT);
				conn.setConnectTimeout(CONNECTION_TIMEOUT);
				conn.setRequestMethod("POST");

				// setDoInput and setDoOutput method depict handling of both send and receive
				conn.setDoInput(true);
				conn.setDoOutput(true);


				// Append parameters to URL
				Uri.Builder builder = new Uri.Builder()
					.appendQueryParameter("context", "search")
				.appendQueryParameter("query", params[0]);
					
				String query = builder.build().getEncodedQuery();

				// Open connection for sending data
				OutputStream os = conn.getOutputStream();
				BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(os, "UTF-8"));
				writer.write(query);
				writer.flush();
				writer.close();
				os.close();
				conn.connect();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return "exception";
			}

			try {

				int response_code = conn.getResponseCode();

				// Check if successful connection made
				if (response_code == HttpURLConnection.HTTP_OK) {

					// Read data sent from server
					InputStream input = conn.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(input));
					StringBuilder result = new StringBuilder();
					String line;

					while ((line = reader.readLine()) != null) {
						result.append(line);
					}

					// Pass data to onPostExecute method
					return(result.toString());

				}else{

					return("unsuccessful");
				}

			} catch (IOException e) {
				e.printStackTrace();
				return "exception";
			} finally {
				conn.disconnect();
			}


		}

		@Override
		protected void onPostExecute(String result) {
			
	//Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
	progress.setVisibility(View.GONE);
			
	if(result.equals("exception"))
	{
		getdata1.clear();
		loading.setText("Connection problem");
		recyclerViewadapter.notifyDataSetChanged();
		
	}
	else if(result.equals("no document found"))
	{
		
		getdata1.clear();
		loading.setText("Not found");
		recyclerViewadapter.notifyDataSetChanged();
		
	}
	else
	{
		loading.setText("Found:");
		decoder(result);
		
	}
	}}
	
	
	public void decoder(String json)
	{
		getdata1.clear();
		
		try
		{
			
			JSONArray array=new JSONArray(json);
			for(int a=0;a<array.length();a++)
			{
				JSONObject obj=array.getJSONObject(a);
				String tit=obj.getString("property_name");
				String serial=obj.getString("property_serial_num");
				String status=obj.getString("property_status");
				String owner=obj.getString("property_owner_names");
				
				String address=obj.getString("property_owner_address");
				String phone=obj.getString("property_owner_phone");
				String dec=obj.getString("property_address");
				
				getdata=new getData();
				getdata.setTitle(tit);
				getdata.setType("document");
				getdata.setStatus(status);
				getdata.setSerial(serial);
				getdata.setOwner(owner);
				getdata.setAddress(address);
				getdata.setDesc(dec);
				getdata.setPhone(phone);
				
				getdata1.add(getdata);
			
				recyclerViewadapter.notifyDataSetChanged();
				
				
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence txt, int i, int i1, int i2) {

			
			
			}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



		}

        public void afterTextChanged(Editable editable) {

			loader loader=new loader();
			
			if(check.equals("yes"))
			{
			if(!editable.toString().trim().equals(""))
			{
			loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,editable.toString());
             }
			 }
			 else
			 {
				 
				 cv.setVisibility(View.VISIBLE);
				 loading.setText("Loading...");
				 
				 getdata1.clear();
				 loading.setText("Register first!");
				 
				 recyclerViewadapter.notifyDataSetChanged();
			 }

        }
    }	
	
	
}
