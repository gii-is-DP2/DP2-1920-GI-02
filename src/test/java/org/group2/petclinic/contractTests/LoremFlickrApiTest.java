
package org.group2.petclinic.contractTests;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.startsWith;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import lombok.extern.java.Log;

@Log
public class LoremFlickrApiTest {

	@Test
	public void getRandomDogImage() {
		String keyword = "dog";
		int width = 320;
		int height = 240;

		when().get("https://loremflickr.com/json/" + width + "/" + height + "/" + keyword)//
			.then()//
			.statusCode(200)//
			.and()//
			.assertThat()//
			.body("file", startsWith("https://loremflickr.com/cache/resized/"))//
			.body("file", endsWith(".jpg"))//
			.body("tags", containsString(keyword))//
			.body("width", equalTo(width))//
			.body("height", equalTo(height))//
			.and()//
			.time(lessThan(20L), TimeUnit.SECONDS);
	}
}
