package com.itdevsltd.searcher;
import android.support.v7.app.*;
import android.os.*;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.support.design.widget.*;
import android.support.v7.app.AlertDialog;

import android.widget.EditText;
import android.widget.Button;
import android.view.View.*;
import android.view.*;
import android.content.*;
import android.provider.*;
import android.widget.Spinner;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.*;
import java.net.*;
import android.app.*;
import android.net.*;
import java.io.*;
import org.json.*;
import android.support.v7.widget.*;
import android.view.animation.*;
import android.text.*;

public class natoye extends AppCompatActivity
{
	public static final int CONNECTION_TIMEOUT=10000;
	public static final int READ_TIMEOUT=15000;
	ProgressDialog progress;

	Toolbar toolbar;
	EditText sel;
	EditText desc;
	EditText doc;
	
	TextInputLayout layout_doc;
	TextInputLayout layout_sel;
	TextInputLayout layout_desc;
	Button btn;

	SharedPreferences shared;
	public String  info="info";
	public String language;

	String names;
	String email;
	String address;
	String phone;
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.natoye);
		
		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		
		toolbar=(Toolbar) findViewById(R.id.toolbar);
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
		this.setTitle("Andikisha ibyo watoye");

		shared=getSharedPreferences(info,MODE_PRIVATE);
		language=shared.getString("language","not found");
		names=shared.getString("name","No name");
		email=shared.getString("email","No email");
		phone=shared.getString("phone","No phone");
		address=shared.getString("address","No address");

		Button cancel=(Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					finish();
				}
			});

		doc=(EditText) findViewById(R.id.input_doc);
		
		sel=(EditText) findViewById(R.id.input_serial);
		desc=(EditText) findViewById(R.id.input_desc);
		btn=(Button) findViewById(R.id.save);
		layout_sel=(TextInputLayout)findViewById(R.id.layout_serial);
		layout_desc=(TextInputLayout)findViewById(R.id.layout_desc);
		layout_doc=(TextInputLayout)findViewById(R.id.layout_doc);
		

		
		
		btn.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					validate();
				}
			});

		
	}

	public void validate()
	{
	if(doc.getText().toString().equals(""))
		{
			layout_doc.setError("Andika icyo watoye");
			layout_sel.setError(null);
			layout_desc.setError(null);
			
		}
		else if(sel.getText().toString().equals(""))
		{
			layout_sel.setError("Shyiramo nimero ikiranga");
			layout_desc.setError(null);
			layout_doc.setError(null);
			
		}
		else if(desc.getText().toString().equals(""))
		{
			layout_desc.setError("Andika ibindi bicyerekeyeho");
			layout_sel.setError(null);
			layout_doc.setError(null);
			
		}
		
		
		else
		{
			send send=new send();
			send.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,doc.getText().toString(),sel.getText().toString(),names,phone,address,desc.getText().toString(),"none","found");

		}

	}


	////////////////////////////////////////////////
//////////////////////////////////////////////////////////


	private class send extends AsyncTask<String, String, String>
	{
		HttpURLConnection conn;
		URL url = null;
		String question="hey";

		@Override
		protected void onPreExecute()
		{

			progress=ProgressDialog.show(natoye.this,"","Searching...",false,true);
			// TODO: Implement this method
			super.onPreExecute();
		}


		protected String doInBackground(String... params) {

			try {


				//url = new URL("https://bazaltd.000webhostapp.com/search.php");
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
					.appendQueryParameter("context", "lost")
					.appendQueryParameter("p_name", params[0])
					.appendQueryParameter("serial", params[1])
					.appendQueryParameter("names", params[2])
					.appendQueryParameter("phone", params[3])
					.appendQueryParameter("address", params[4])

					.appendQueryParameter("p_address", params[5])
					.appendQueryParameter("prize", params[6])
					.appendQueryParameter("status", params[7])
				.appendQueryParameter("st", "lost");

				

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

			progress.dismiss();

			if(result.equals("exception") || result.equals(null))
			{
				Toast.makeText(getApplicationContext(),"ikibazo k'itumanaho,mwongere mukanya",Toast.LENGTH_LONG).show();

			}
			else if(result.equals("success"))
			{
				Toast.makeText(getApplicationContext(),"Murakoze, nyiracyo naboneka tuzabamenyesha!",Toast.LENGTH_LONG).show();
				sel.setText("");
				desc.setText("");
				doc.setText("");
			}
			else if(result.substring(0,1).equals("["))
			{
				Toast.makeText(getApplicationContext(),"Hari uwakibuze, dushobora kubahuza",Toast.LENGTH_LONG).show();
				sel.setText("");
				desc.setText("");
				decoder(result);
				doc.setText("");
			}

		}}


	public void decoder(String json)
	{
	

		try
		{

			JSONArray array=new JSONArray(json);
			
				JSONObject obj=array.getJSONObject(0);
				String tit=obj.getString("property_name");
				String ser=obj.getString("property_serial_num");
				String stat=obj.getString("property_status");
				String own=obj.getString("property_owner_names");

				String addre=obj.getString("property_owner_address");
				String phon=obj.getString("property_owner_phone");
				String des=obj.getString("property_address");

				

				final Bundle bundle=new Bundle();
				bundle.putString("property_name",tit);
				bundle.putString("property_serial_num",ser);
				bundle.putString("property_status",stat);
				bundle.putString("property_owner_names",own);
				bundle.putString("property_owner_address",addre);
				bundle.putString("property_owner_phone",phon);
				bundle.putString("property_address",des);
				
				
			new AlertDialog.Builder(natoye.this)
				.setTitle("Hari ikibonetse")
				.setMessage("Hari umuntu wamenyekanishije ko yabuze iki igikoresho "+tit+" gifite nimero "+ser)
				.setPositiveButton("Menya byinshi", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						//Prompt the user once explanation has been shown

if(language.equals("Kinyarwanda")){
	Intent in=new Intent(natoye.this,HomeActivity.class);
	in.putExtras(bundle);
	startActivity(in);
	natoye.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

}else if(language.equals("English")){
	Intent in=new Intent(natoye.this,com.itdevsltd.searcher.english.HomeActivity.class);
	in.putExtras(bundle);
	startActivity(in);
	natoye.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

}else{
	Intent in=new Intent(natoye.this,HomeActivity.class);
	in.putExtras(bundle);
	startActivity(in);
	natoye.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

}
						


					}
				})
				
				.create()
				.show();
				
				
				
				
				/*Animation anim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.abc_slide_in_top);
				cv2.setAnimation(anim);
				cv2.setVisibility(View.VISIBLE);*/


				
				
			
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
	}
	

}
