package com.itdevsltd.searcher.english;

import android.support.v7.app.*;
import android.os.*;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog;

import android.widget.EditText;
import android.support.design.widget.*;
import android.widget.EditText;
import android.widget.Button;
import android.view.View.*;
import android.view.*;
import android.content.*;
import android.location.*;
import java.security.*;
import java.io.*;
import java.util.*;
import android.app.*;
import java.net.*;
import android.net.*;
import android.widget.*;

import com.itdevsltd.searcher.R;

import org.json.*;

public class login extends AppCompatActivity
{

	public static final int CONNECTION_TIMEOUT=10000;
	public static final int READ_TIMEOUT=15000;
	ProgressDialog progress;

	Toolbar toolbar;
	
	EditText phone;
	EditText pass;
	

	TextInputLayout layout_phone;
	TextInputLayout layout_pass;
	
	Button injira;
	SharedPreferences shared;
	public String  info="info";
Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
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
		
		context=this;
		this.setTitle("Injira");
		phone=(EditText) findViewById(R.id.input_name);
		pass=(EditText) findViewById(R.id.input_email);
		layout_phone=(TextInputLayout)findViewById(R.id.layout_name);
		layout_pass=(TextInputLayout)findViewById(R.id.layout_email);
		injira=(Button) findViewById(R.id.injira);
		shared=getSharedPreferences(info,MODE_PRIVATE);
		
		injira.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					validate();

				}
			});
		
		}
			
	public boolean validate()
	{
		if(phone.getText().toString().trim().equals(""))
		{
			layout_phone.setError("Enter your Phone number!");
			layout_pass.setError(null);
			
			return false;
		}
		else if(pass.getText().toString().trim().equals(""))
		{
			layout_phone.setError("Enter your password!");
			layout_pass.setError(null);
			
			return false;
		}
		else 
		{
			
			layout_phone.setError(null);
			layout_pass.setError(null);
	
			log login=new log();
			login.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,phone.getText().toString(),pass.getText().toString());
			return true;
		}
		}
			
		
	////////////////////////////////////////////////
//////////////////////////////////////////////////////////


	private class log extends AsyncTask<String, String, String>
	{
		HttpURLConnection conn;
		URL url = null;
		String question="hey";

		@Override
		protected void onPreExecute()
		{

			progress=ProgressDialog.show(context,"","Logging in...",false,false);
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
					.appendQueryParameter("context", "login")
					.appendQueryParameter("phone", params[0])
					.appendQueryParameter("pass", params[1]);
					



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
				Toast.makeText(getApplicationContext(),"Problem connection try again",Toast.LENGTH_LONG).show();

			}
			else if(result.substring(0,1).equals("["))
			{
				decode(result);
			}
			else
			{
				
				Toast.makeText(getApplicationContext(),"Incorrect username or password",Toast.LENGTH_LONG).show();
				
			}
			}
		}
		
		
		public void decode(String json)
		{
			try
			{
			JSONArray array=new JSONArray(json);
			JSONObject obj=array.getJSONObject(0);
			
			String names=obj.getString("names");
			String email=obj.getString("email");
			String phone=obj.getString("telephone1");
			String address=obj.getString("address");
				
			SharedPreferences.Editor editor=shared.edit();
				editor.putString("reg","yes");
				editor.putString("email",email);
				editor.putString("phone",phone);
				editor.putString("name",names);
				editor.putString("address",address);
				editor.commit();
				
				
				new AlertDialog.Builder(context)
					.setTitle("Register")
					.setMessage("Thank you for your registration,you can report your missed or found properties for our help!")
					.setPositiveButton("Missed equipment", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							//Prompt the user once explanation has been shown


							finish();
							Intent in=new Intent(getApplicationContext(),MainActivity.class);
							startActivity(in);
							login.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);


						}
					})
					.setNegativeButton("Founded equipment", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							//Prompt the user once explanation has been shown


							finish();
							Intent in=new Intent(getApplicationContext(),MainActivity.class);


							startActivity(in);
							login.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);


						}
					})


					.create()
					.show();
				
				
				
				
			
			}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
		}
		
		}
		
