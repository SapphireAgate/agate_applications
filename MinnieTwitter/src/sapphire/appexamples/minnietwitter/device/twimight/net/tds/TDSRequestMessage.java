/*******************************************************************************
 * Copyright (c) 2011 ETH Zurich.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Paolo Carta - Implementation
 *     Theus Hossmann - Implementation
 *     Dominik Schatzmann - Message specification
 ******************************************************************************/

package sapphire.appexamples.minnietwitter.device.twimight.net.tds;

import java.security.KeyPair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;
import sapphire.appexamples.minnietwitter.device.twimight.data.StatisticsDBHelper;
import sapphire.appexamples.minnietwitter.device.twimight.net.twitter.Tweets;
import sapphire.appexamples.minnietwitter.device.twimight.security.KeyManager;
import sapphire.appexamples.minnietwitter.device.twimight.util.Constants;

/**
 * A collection of JSONObjects to send to the Twimight Disaster Server
 * @author thossmann
 *
 */
public class TDSRequestMessage {

	private int version;
	Context context;
	
	private JSONObject authenticationObject;
	private JSONObject bluetoothObject;
	private JSONObject certificateObject;
	private JSONObject revocationObject;
	private JSONObject followerObject;
	private JSONObject statisticObject;
	private JSONObject disTweetsObject;
	
	
	/**
	 * Constructor
	 */
	public TDSRequestMessage(Context context){
		version = Constants.TDS_MESSAGE_VERSION;
		this.context=context;
	}
	
	/**
	 * creates JSONObject to authenticate with the TDS
	 * @param client
	 * @return JSON Object
	 * @throws JSONException 
	 */
	public void createAuthenticationObject(int consumerId, String twitterAccessToken, String twitterAccessTokenSecret) throws JSONException {
		authenticationObject = new JSONObject();
		authenticationObject.put("consumer_id", consumerId);
		authenticationObject.put("access_token", twitterAccessToken);
		authenticationObject.put("access_token_secret", twitterAccessTokenSecret);
	}
	

	/**
	 * creates JSONObject to push the Bluetooth MAC to the TDS
	 * @param client
	 * @return JSON Object
	 * @throws JSONException 
	 */
	public void createBluetoothObject(String mac) throws JSONException{

		// the JSON Object will contain our name values
		bluetoothObject = new JSONObject();
		bluetoothObject.put("mac", mac);
	}
	
    
	
	public void createDisTweetsObject(Cursor tweets) throws JSONException {
		
		Log.i("tdsRequest", "tweet cursor count: " + tweets.getCount());
		if(tweets != null && tweets.getCount() > 0) {			
			
			tweets.moveToFirst();
			disTweetsObject = new JSONObject();
			JSONArray disTweetsArray = new JSONArray();
			
			while(!tweets.isAfterLast()) {
				
				if (!tweets.isNull(tweets.getColumnIndex(Tweets.COL_SIGNATURE))){
					JSONObject row = new JSONObject();
					row.put(Tweets.COL_TEXT_PLAIN, tweets.getString(tweets.getColumnIndex(Tweets.COL_TEXT_PLAIN)) );					
					row.put(Tweets.COL_USER, tweets.getLong(tweets.getColumnIndex(Tweets.COL_USER)));
					row.put(Tweets.COL_DISASTERID, tweets.getLong(tweets.getColumnIndex(Tweets.COL_DISASTERID)));
					row.put(Tweets.COL_SIGNATURE, tweets.getString(tweets.getColumnIndex(Tweets.COL_SIGNATURE)));
					
					disTweetsArray.put(row);
				}
				
				tweets.moveToNext();
			}
			tweets.close();
			
			if (disTweetsArray.length() > 0)
				disTweetsObject.put("content", disTweetsArray);
			else
				disTweetsObject = null;
			
		}
	}
	

	/**
	 * creates JSONObject to push Statistics to the TDS
	 * @param 
	 * @return JSON Object
	 * @throws JSONException 
	 */
	public void createStatisticObject(Cursor stats, long follCount) throws JSONException{

				
		if(stats != null) {			
			statisticObject = new JSONObject();
			JSONArray statisticArray = new JSONArray();
			
			while(!stats.isAfterLast()) {
				
				JSONObject row = new JSONObject();
				row.put("latitude", Double.toString(stats.getDouble(stats.getColumnIndex(StatisticsDBHelper.KEY_LOCATION_LAT))));
				row.put("longitude", Double.toString(stats.getDouble(stats.getColumnIndex(StatisticsDBHelper.KEY_LOCATION_LNG))));
				row.put("accuracy", Integer.toString(stats.getInt(stats.getColumnIndex(StatisticsDBHelper.KEY_LOCATION_ACCURACY))) );
				row.put("provider", stats.getString(stats.getColumnIndex(StatisticsDBHelper.KEY_LOCATION_PROVIDER)));			
				row.put("timestamp", Long.toString(stats.getLong(stats.getColumnIndex(StatisticsDBHelper.KEY_TIMESTAMP))));
				row.put("network", stats.getString(stats.getColumnIndex(StatisticsDBHelper.KEY_NETWORK)));
				row.put("event", stats.getString(stats.getColumnIndex(StatisticsDBHelper.KEY_EVENT)));
				row.put("link", stats.getString(stats.getColumnIndex(StatisticsDBHelper.KEY_LINK)));
				row.put("isDisaster", Integer.toString(stats.getInt(stats.getColumnIndex(StatisticsDBHelper.KEY_ISDISASTER))) );
				row.put("followers_count", follCount);
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
				row.put("dis_mode_used", prefs.getBoolean(Constants.DIS_MODE_USED, false) );
				
				statisticArray.put(row);
				stats.moveToNext();
			}
			stats.close();
			
			statisticObject.put("content", statisticArray);
			
		}
	}

	
	
	/**
	 * Creates a JSON object for the revocation list update request
	 * @param lastUpdate
	 */
	public void createRevocationObject(int currentVersion) throws JSONException {
		revocationObject = new JSONObject();
		revocationObject.put("version", currentVersion);
	}

	
	/**
	 * Creates the JSON object containing the public key to send to the TDS
	 * @return
	 * @throws JSONException 
	 */
	public void createCertificateObject(KeyPair toSign, KeyPair toRevoke) throws JSONException {
		
		certificateObject = new JSONObject();
		if(toSign!=null){
			certificateObject.put("public_key", KeyManager.getPemPublicKey(toSign));
		}
		
		if(toRevoke!=null){
			certificateObject.put("revoke", KeyManager.getPemPublicKey(toRevoke));
		}
	}
	
	/**
	 * Creates the JSON object for a request for follower keys
	 * @param lastUpdate
	 * @throws JSONException
	 */
	public void createFollowerObject(long lastUpdate) throws JSONException{
		followerObject = new JSONObject();
		followerObject.put("last_update", lastUpdate);
	}
	
	
	/**
	 * is the authentication object set?
	 * @return
	 */
	public boolean hasAuthenticationObject(){
		return authenticationObject != null;
	}
	
	/**
	 * is the Bluetooth object set?
	 * @return
	 */
	public boolean hasBluetoothObject(){
		return bluetoothObject != null;
	}
	

	
	/**
	 * is the Bluetooth object set?
	 * @return
	 */
	public boolean hasStatisticObject(){
		return statisticObject != null;
	}
	

	/**
	 * is the Disaster tweets object set?
	 * @return
	 */
	public boolean hasDisTweetsObject(){
		return disTweetsObject != null;
	}
	
	
	/**
	 * Is the version field set?
	 * @return
	 */
	public boolean hasVersion(){ 
		return version != 0;
	}
	
	/**
	 * Is the Certificate object set?
	 */
	public boolean hasCertificatObject(){
		return certificateObject != null;
	}
	

	/**
	 * Is the Revocation object set?
	 * @return
	 */
	public boolean hasRevocationObject() {
		return revocationObject != null;
	}

	/**
	 * Is the Follower object set?
	 * @return
	 */
	public boolean hasFollowerObject() {
		return followerObject != null;
	}

	
	/**
	 * Getter
	 * @return
	 */
	public int getVersion(){
		return version;
	}
	
	/**
	 * Getter
	 * @return
	 */
	public JSONObject getAuthenticationObject(){
		return authenticationObject;
	}
	
	/**
	 * Getter
	 * @return
	 */
	public JSONObject getBluetoothObject(){
		return bluetoothObject;
	}
	
	

	/**
	 * Getter
	 * @return
	 */
	public JSONObject getDisTweetsObject(){
		return disTweetsObject;
	}
	
	/**
	 * Getter
	 * @return
	 */
	public JSONObject getStatisticObject(){
		return statisticObject;
	}
	

	
	/**
	 * Getter
	 * @return
	 */
	public JSONObject getCertificateObject(){
		return certificateObject;
	}

	/**
	 * Getter
	 * @return
	 */
	public JSONObject getRevocationObject(){
		return revocationObject;
	}

	/**
	 * Getter
	 * @return
	 */
	public JSONObject getFollowerObject(){
		return followerObject;
	}
}
