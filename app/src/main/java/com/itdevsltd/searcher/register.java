package com.itdevsltd.searcher;
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
import android.text.*;

public class register extends AppCompatActivity
{
	
	public static final int CONNECTION_TIMEOUT=10000;
	public static final int READ_TIMEOUT=15000;
	ProgressDialog progress;
	
Toolbar toolbar;
EditText name;
EditText email;
EditText phone;
EditText pass1;
EditText pass2;
EditText Address;
	
TextInputLayout layout_name;
	TextInputLayout layout_email;
	TextInputLayout layout_phone;
	TextInputLayout layout_pass1;
	TextInputLayout layout_pass2;
	TextInputLayout layout_address;
	
	Button btn;
	Button injira;
	
	SharedPreferences shared;
	public String  info="info";
	String language;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
	toolbar=(Toolbar) findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);
		if(getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
						startActivity(new Intent(register.this,MainActivity.class));
					}
				});
        }
		name=(EditText) findViewById(R.id.input_name);
		email=(EditText) findViewById(R.id.input_email);
		phone=(EditText) findViewById(R.id.input_phone);
		pass1=(EditText) findViewById(R.id.input_password);
		pass2=(EditText) findViewById(R.id.input_password2);
	Address=(EditText) findViewById(R.id.input_address);
		
		layout_name=(TextInputLayout)findViewById(R.id.layout_name);
		layout_email=(TextInputLayout)findViewById(R.id.layout_email);
		layout_phone=(TextInputLayout)findViewById(R.id.layout_phone);
		layout_pass1=(TextInputLayout)findViewById(R.id.layout_password);
		layout_pass2=(TextInputLayout)findViewById(R.id.layout_password2);
		layout_address=(TextInputLayout)findViewById(R.id.layout_address);
		
		btn=(Button) findViewById(R.id.iyandikishe);
		injira=(Button) findViewById(R.id.injira);
		
		btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				validate();
				
			}
		});
		
		injira.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Intent in=new Intent(getApplicationContext(),login.class);
					startActivity(in);
					register.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
					
				}
			});
		shared=getSharedPreferences(info,MODE_PRIVATE);
		language=shared.getString("language","not found");
		
		
		
		}

	public boolean validate()
	{
		if(name.getText().toString().trim().equals(""))
		{
			layout_name.setError("Nta mazina mwashyizemo!");
			layout_email.setError(null);
			layout_phone.setError(null);
			layout_pass1.setError(null);
			layout_pass2.setError(null);
			layout_address.setError(null);
			
			return false;
		}
		else if(email.getText().toString().trim().equals(""))
		{
			layout_email.setError("Nta Email mwashyizemo!");
			layout_name.setError(null);
			layout_phone.setError(null);
			layout_pass1.setError(null);
			layout_pass2.setError(null);
			layout_address.setError(null);
			
			return false;
		}
		else if(phone.getText().toString().trim().equals(""))
		{
			layout_phone.setError("Nta telephone mwashyizemo!");
			layout_email.setError(null);
			layout_name.setError(null);
			layout_pass1.setError(null);
			layout_pass2.setError(null);
			layout_address.setError(null);
			
			return false;
		}
		
		if(pass1.getText().toString().trim().equals(""))
		{
			layout_pass1.setError("Mushiremo ijambo banga!");
			layout_email.setError(null);
			layout_phone.setError(null);
			layout_name.setError(null);
			layout_pass2.setError(null);
			layout_address.setError(null);
			
			return false;
		}
		else if(pass2.getText().toString().trim().equals(""))
		{
			layout_pass2.setError("Musubizemo ijambo banga!");
			layout_email.setError(null);
			layout_phone.setError(null);
			layout_pass1.setError(null);
			layout_name.setError(null);
			layout_address.setError(null);
			
			return false;
		}
		else if(!(pass1.getText().toString().trim().equals(pass2.getText().toString().trim())))
		{
			layout_pass2.setError("Ijambo banga ntirihura niryambere!");
			layout_email.setError(null);
			layout_phone.setError(null);
			layout_pass1.setError(null);
			layout_address.setError(null);
			
			return false;
		}
		
		else if(Address.getText().toString().trim().equals(""))
		{
			layout_address.setError("Musubizemo aho mutuye!");
			layout_email.setError(null);
			layout_phone.setError(null);
			layout_pass1.setError(null);
			layout_pass2.setError(null);
			
			return false;
		}
		
		else if(!isValidEmail(email.getText().toString()))
		{
			layout_email.setError("Email mwasizemo ntibaho!");
			layout_name.setError(null);
			layout_phone.setError(null);
			layout_pass1.setError(null);
			layout_pass2.setError(null);
			layout_address.setError(null);

			return false;
		}
		else
		{
			layout_name.setError(null);
			layout_email.setError(null);
			layout_phone.setError(null);
			layout_pass1.setError(null);
			layout_pass2.setError(null);
			layout_address.setError(null);
			
		Random rand=new Random();
		int a=rand.nextInt(10000)+99999;
		
		String names=name.getText().toString();
		String emai=email.getText().toString();
		String phon=phone.getText().toString();
	    String pass=pass2.getText().toString();
		String address=Address.getText().toString();
			
	send send=new send();
	send.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,""+a,names,phon,emai,encryptPassword(pass),address);
		
			return true;
		}
		
	}
	private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
	
	
	@Override
	public void onBackPressed()
	{
		if(language.equals("Kinyarwanda")){
			finish();
			startActivity(new Intent(register.this,MainActivity.class));
			register.this.overridePendingTransition(android.R.anim.slide_in_left,R.anim.abc_slide_out_bottom);
		}else if(language.equals("English")){
			finish();
			startActivity(new Intent(register.this,com.itdevsltd.searcher.english.MainActivity.class));
			register.this.overridePendingTransition(android.R.anim.slide_in_left,R.anim.abc_slide_out_bottom);
		}else{
			finish();
			startActivity(new Intent(register.this,MainActivity.class));
			register.this.overridePendingTransition(android.R.anim.slide_in_left,R.anim.abc_slide_out_bottom);
		}
		finish();
		startActivity(new Intent(register.this,MainActivity.class));
		register.this.overridePendingTransition(android.R.anim.slide_in_left,R.anim.abc_slide_out_bottom);
		
		// TODO: Implement this method
		super.onBackPressed();
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

			progress=ProgressDialog.show(register.this,"","Kwiyandikisha...",false,false);
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
					.appendQueryParameter("context", "register")
					.appendQueryParameter("id", params[0])
					.appendQueryParameter("names", params[1])
					.appendQueryParameter("phone", params[2])
					.appendQueryParameter("email", params[3])
					.appendQueryParameter("password", params[4])
					.appendQueryParameter("address", params[5]);

				


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
			Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
			
			if(result.equals("exception") || result.equals(null))
			{
				Toast.makeText(getApplicationContext(),"ikibazo k'itumanaho,mwongere mukanya",Toast.LENGTH_LONG).show();

			}
			else if(result.equals("success"))
			{
				Toast.makeText(getApplicationContext(),"Murakoze kwiyandikisha!",Toast.LENGTH_LONG).show();
		
				
				SharedPreferences.Editor editor=shared.edit();
				editor.putString("reg","yes");
				editor.putString("email",email.getText().toString());
				editor.putString("phone",phone.getText().toString());
				editor.putString("name",name.getText().toString());
				editor.putString("address",Address.getText().toString());
				editor.commit();

				
				new AlertDialog.Builder(register.this)
					.setTitle("Kwiyandikisha")
					.setMessage("Murakoze kwiyandikisha kuri Trumpet, mushobora kwandikisha ibyangombwa cg imitungo yabuze tukabafasha kuyibona,muhitemo!")
					.setPositiveButton("Ibyabuze", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							//Prompt the user once explanation has been shown

if(language.equals("Kinyarwanda")){
	finish();
	Intent in=new Intent(getApplicationContext(),MainActivity.class);
	startActivity(in);
	register.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

}else if(language.equals("English")){
	finish();
	Intent in=new Intent(getApplicationContext(),com.itdevsltd.searcher.english.MainActivity.class);
	startActivity(in);
	register.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

}else{
	finish();
	Intent in=new Intent(getApplicationContext(),MainActivity.class);
	startActivity(in);
	register.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

}
							finish();
							Intent in=new Intent(getApplicationContext(),MainActivity.class);
							startActivity(in);
							register.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
							

						}
					})
					.setNegativeButton("Ibyatoraguwe", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							//Prompt the user once explanation has been shown

							if(language.equals("Kinyarwanda")){
								finish();
								Intent in=new Intent(getApplicationContext(),MainActivity.class);


								startActivity(in);
								register.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

							}else if(language.equals("English")){
								finish();
								Intent in=new Intent(getApplicationContext(),com.itdevsltd.searcher.english.MainActivity.class);


								startActivity(in);
								register.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

							}else{
								finish();
								Intent in=new Intent(getApplicationContext(),MainActivity.class);


								startActivity(in);
								register.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

							}


						}
					})
					
					
					.create()
					.show();
				
				
					
				
				

			}
			

		}}
	
	
	
	
	
	
	
	
	
	private static String encryptPassword(String password)
	{
		String sha1 = "";
		try
		{
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(password.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return sha1;
	}

	private static String byteToHex(final byte[] hash)
	{
		Formatter formatter = new Formatter();
		for (byte b : hash)
		{
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
}
