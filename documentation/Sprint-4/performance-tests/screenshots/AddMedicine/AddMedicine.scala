package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AddMedicineDiagnosing extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(8)
	}
	
	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(15)
	}
	
	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "b6978d95-937c-4f10-a4fe-dd3cc0a9e6c9"))
		.pause(12)
	}
	
	object Medicines {
		val medicines = exec(http("Medicines")
			.get("/admin/medicines")
			.headers(headers_0))
		.pause(11)
	}
	
	object AddMedicine {
		val addMedicine = exec(http("AddMedicine")
			.get("/admin/medicines/new")
			.headers(headers_0))
		.pause(29)
	}
	
	object AddedMedicine {
		val addedMedicine = exec(http("AddedMedicine")
			.post("/admin/medicines/new")
			.headers(headers_3)
			.formParam("name", "Medicine J")
			.formParam("brand", "Brand J")
			.formParam("medicine.id", "")
			.formParam("_csrf", "119cf214-0a57-4d25-a2bc-f24c22fe4175"))
		.pause(8)
	}

	val scn = scenario("AddMedicine").exec(Home.home, 
										   Login.login,
										   Logged.logged,
										   Medicines.medicines,
										   AddMedicine.addMedicine,
										   AddedMedicine.addedMedicine)
		

	setUp(scn.inject(rampUsers(2000) during (100 seconds))).protocols(httpProtocol)
}