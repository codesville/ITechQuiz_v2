package com.justbytes.itechquiz.data;

import android.content.Context;
import android.util.Log;

import com.justbytes.itechquiz.QAndA;
import com.justbytes.itechquiz.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RemoteDataFetcher {

	public static final String TAG = RemoteDataFetcher.class.getName();

	// default URI
	public static String URI = "http://www.justbytes.info/ITechQuiz/getLatestQandA?ver=";

	public List<QAndA> fetchLatestQandA(Context ctx) throws Exception {
		List<QAndA> qandaList = new ArrayList<QAndA>();

		URI = ctx.getString(R.string.fetchURL);

		DbAdapter dbAdapter = new DbAdapter(ctx);
		int ver = dbAdapter.getCurrentVersion();
		Log.i(TAG, "Current max(topics) version=" + ver);



		StringBuilder jsonString = new StringBuilder();


		URL urlObj = new URL(URI + ver);
		HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
		try {
			InputStream is = urlConnection.getInputStream();
			Log.d(TAG, "Sending request to remote server: " + URI + ver);
			int status = urlConnection.getResponseCode();
			Log.d(TAG,
					"Response code from server=" + status);

			if (status == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String line = "";

				while ((line = reader.readLine()) != null) {
					jsonString.append(line);
				}
				reader.close();
				// Log.d(TAG, jsonString.toString());

			} else {
				throw new Exception(
						"Failed to fetch new questions with status code="
								+ status);
			}
		} catch (Exception ex) {
			Log.e(this.getClass().getName(),
					"Error fetching latest questions.", ex);
			throw new Exception("Failed to fetch new questions");
		} finally {
			urlConnection.disconnect();
		}

		if (jsonString != null && jsonString.length() > 0) {
			JsonAdapter jsonAdapter = new JsonAdapter();
			qandaList = jsonAdapter.parseJsonString(jsonString.toString());
		}

		return qandaList;
	}

}
