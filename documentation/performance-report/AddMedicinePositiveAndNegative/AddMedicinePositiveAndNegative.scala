package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AddMedicinePositiveAndNegative extends Simulation {

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
	
	object LoginAsAdmin {
		val loginAsAdmin = exec(http("LoginAsAdmin1")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("LoginAsAdmin2")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}
	
	object LoginAsVet {
		val loginAsVet = exec(http("LoginAsVet1")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("LoginAsVet2")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}
	
	object Medicines {
		val medicines = exec(http("Medicines")
			.get("/admin/medicines")
			.headers(headers_0))
		.pause(11)
	}
	
	object AddedMedicine {
		val addedMedicine = exec(http("AddedMedicine1")
			.get("/admin/medicines/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(29)
		.exec(http("AddedMedicine2")
			.post("/admin/medicines/new")
			.headers(headers_3)
			.formParam("name", "Medicine J")
			.formParam("brand", "Brand J")
			.formParam("medicine.id", "")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}
	
	object AttemptAccessMedicine {
		val attemptAccessMedicine = exec(http("AttemptAccessMedicine")
			.get("/admin/medicines")
			.headers(headers_0)
			.check(status.is(403)))
		.pause(23)
	}

	val adminsScn = scenario("Admins").exec(Home.home, 
										   LoginAsAdmin.loginAsAdmin,
										   Medicines.medicines,
										   AddedMedicine.addedMedicine)
										   
	val vetsScn = scenario("Vets").exec(Home.home, 
										   LoginAsVet.loginAsVet,
										   AttemptAccessMedicine.attemptAccessMedicine)



	setUp(
        adminsScn.inject(atOnceUsers(1)),
        vetsScn.inject(atOnceUsers(1))
        ).protocols(httpProtocol)
}