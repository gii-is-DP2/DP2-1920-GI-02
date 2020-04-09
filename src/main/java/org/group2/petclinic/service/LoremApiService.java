
package org.group2.petclinic.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoremApiService {

	@Autowired
	public LoremApiService() {

	}

	public String getRandomImageURL(String keyword) {

		String loremImageURL = "";

		try {

			URL url = new URL("https://loremflickr.com/json/320/240/pet");
			System.setProperty("http.agent", "Mozilla/5.0");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			JSONObject loremResponse = new JSONObject(content.toString());
			con.disconnect();

			loremImageURL = loremResponse.getString("file");

		} catch (Exception e) {

		}

		return loremImageURL;
	}

}
