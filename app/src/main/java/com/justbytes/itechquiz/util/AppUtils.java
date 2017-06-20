package com.justbytes.itechquiz.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.justbytes.itechquiz.ITechQuizActivity;
import com.justbytes.itechquiz.R;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class AppUtils {

	public static String[] keywordArr = new String[] { "Java", "C#", "Unix",
			"J2EE", "Dice", "SQL", "Oracle", "Monster jobs", "Hibernate", "IT",
			"jobs", "Spring", "Oracle", "Microsoft", "Silverlight", ".NET",
			"Android", "Google", "Oasis", "Tennis", "Guitar", "Deals",
			"Groupon", "Careers", "Information Technology", "IT", "Vacation",
			"women", "money", "trading", "parttime", "jQuery" };

	//public static final String AD_ID = R.string.adMobAppId;
	// "a1519648ebada10"; // mr. legacy pub id
	// "a14f4c6b1e03bee"; //codes
	public static final String PLAIN_TEXT = "plain/text";

	/**
     public static AdRequest getAdRequest() {
		AdRequest adReq = new AdRequest();
		Set<String> keywords = new HashSet<String>();
		keywords.addAll(Arrays.asList(keywordArr));
		adReq.setKeywords(keywords);
		// adReq.addTestDevice(AdRequest.TEST_EMULATOR);
		return adReq;
	}
     */

	public static void createAdView(AdView adView) {
		try{
        	AdRequest adRequest = new AdRequest.Builder().build();
			adView.loadAd(adRequest);
		} catch (Exception ex){
			Log.e("ADVIEW", "Error creating AdView", ex);
		}
//		LayoutParams layoutParams = new LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT);
//		adView.setLayoutParams(layoutParams);
//		adView.loadAd(AppUtils.getAdRequest());

	}

	public static Intent createMailIntent(String[] to, String subject,
			String body, String mimeType) {
		Intent mailIntent = new Intent(android.content.Intent.ACTION_SEND);
		mailIntent.setType(mimeType);
		mailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, to);
		mailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		mailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
		// since we are calling from outside main context
		// mailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Log.d("EMAIL", "Sending email to " + to + " with subject=" + subject
				+ " with body = " + body);
		return mailIntent;
	}

	public static int postHttpRequest(String url, Map<String, String> params)
			throws Exception {
		URL urlObj = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
		urlConnection.setDoOutput(true);
		urlConnection.setChunkedStreamingMode(0);
		urlConnection.setRequestMethod("POST");
		OutputStream outputStream = urlConnection.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
		Log.i("POST", "Sending request to " + url);
		writer.write(getPostDataString(params));
		writer.flush();
		writer.close();
		outputStream.close();
		int rc = urlConnection.getResponseCode();
		Log.i("POST: Response Code=", rc + "");
		return rc;
	}

	private static String getPostDataString(Map<String, String> params){
		Uri.Builder builder = new Uri.Builder();
		for(Map.Entry<String, String> me : params.entrySet()){
			builder.appendQueryParameter(me.getKey(), me.getValue());
		}
		return builder.build().getEncodedQuery();
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	public static SharedPreferences getGCMPreferences(Context context) {
		return context.getSharedPreferences(
				ITechQuizActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}

}
