package a.b.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dalvik.agate.PolicyManagementModule;
import dalvik.agate.UserManagementModule;

public class Client extends Activity {

	TextView textResponse;
	EditText editTextUsername, editTextPassword; 
	Button buttonConnect, buttonClear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client);

		editTextUsername = (EditText)findViewById(R.id.username);
		editTextPassword = (EditText)findViewById(R.id.password);
		buttonConnect = (Button)findViewById(R.id.connect);
		buttonClear = (Button)findViewById(R.id.clear);
		textResponse = (TextView)findViewById(R.id.response);

		buttonConnect.setOnClickListener(buttonConnectOnClickListener);

		buttonClear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				textResponse.setText("");
			}});
	}

	OnClickListener buttonConnectOnClickListener = 
			new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			MyClientTask myClientTask = new MyClientTask(
			  editTextUsername.getText().toString(),
			  editTextPassword.getText().toString());
			myClientTask.execute();
		}};

		public class MyClientTask extends AsyncTask<Void, Void, Void> {

			String dstAddress = "128.208.7.21";
			int dstPort = 8080;
			String response = "";

			MyClientTask(String username, String password){
				UserManagementModule.login(username, password);
			}

			@Override
			protected Void doInBackground(Void... arg0) {

				Socket socket = null;

				try {
					socket = new Socket(dstAddress, dstPort);

					ByteArrayOutputStream byteArrayOutputStream = 
							new ByteArrayOutputStream(1024);
					byte[] buffer = new byte[1024];

					int bytesRead;
					InputStream inputStream = socket.getInputStream();

					/*
					 * notice:
					 * inputStream.read() will block if no data return
					 */
					while ((bytesRead = inputStream.read(buffer)) != -1){
						byteArrayOutputStream.write(buffer, 0, bytesRead);
						response += byteArrayOutputStream.toString("UTF-8");
					}

					byte[] a = byteArrayOutputStream.toByteArray();

					System.out.println("Client got response: " + response);
					PolicyManagementModule.log("Policy on response: " + PolicyManagementModule.getPolicyString(response));

				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response = "UnknownHostException: " + e.toString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response = "IOException: " + e.toString();
				}finally{
					if(socket != null){
						try {
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				textResponse.setText(response);
				super.onPostExecute(result);
			}

		}

}
