package a.b.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.widget.TextView;

import dalvik.system.UserFlowPolicy;



public class Server extends Activity {

	TextView info, infoip, msg;
	String message = "";
	ServerSocket serverSocket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		info = (TextView) findViewById(R.id.info);
		infoip = (TextView) findViewById(R.id.infoip);
		msg = (TextView) findViewById(R.id.msg);

		infoip.setText(getIpAddress());

		Thread socketServerThread = new Thread(new SocketServerThread());
		socketServerThread.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class SocketServerThread extends Thread {

		static final int SocketServerPORT = 8080;
		int count = 0;

		@Override
		public void run() {

			try {
				serverSocket = new ServerSocket(SocketServerPORT);
				Server.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						info.setText("Server started on port: "
								+ serverSocket.getLocalPort());
					}
				});

				while (true) {
					Socket socket = serverSocket.accept();
					count++;
					message += "#" + count + " from " + socket.getInetAddress()
							+ ":" + socket.getPort() + "\n";

					Server.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							msg.setText(message);
						}
					});

					SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
							socket, count);
					socketServerReplyThread.run();

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private class SocketServerReplyThread extends Thread {

		private Socket hostThreadSocket;
		int cnt;

		SocketServerReplyThread(Socket socket, int c) {
			hostThreadSocket = socket;
			cnt = c;
		}

		@Override
		public void run() {
			String deviceId = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID); //*** use for tablets  


			OutputStream outputStream;

			String msgReply = "My deviceId is: " + deviceId;

			// Set policy on the device Id
			UserFlowPolicy.addPolicyString(msgReply, UserFlowPolicy.POLICY_3);

			try {
				outputStream = hostThreadSocket.getOutputStream();
				PrintStream printStream = new PrintStream(outputStream);
				printStream.print(msgReply);
				printStream.close();

				message += "replayed: " + msgReply + "\n";

				Server.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						msg.setText(message);
					}
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message += "Something wrong! " + e.toString() + "\n";
			}

			Server.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					msg.setText(message);
				}
			});
		}

	}

	private String getIpAddress() {
		String ip = "";
		try {
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (enumNetworkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterfaces
						.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface
						.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress.nextElement();

					if (inetAddress.isSiteLocalAddress()) {
						ip += "SiteLocalAddress: " 
								+ inetAddress.getHostAddress() + "\n";
					}

				}

			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip += "Something Wrong! " + e.toString() + "\n";
		}

		return ip;
	}
}
