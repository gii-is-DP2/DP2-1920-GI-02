
package org.group2.petclinic.unitTests.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ApiPruebaTests {

	@Test
	void prueba() {
		System.out.println("\n\nPRUEBA API=======");

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

			String loremImageURL = loremResponse.getString("file");
			System.out.println(loremImageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
