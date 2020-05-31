package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory14 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Pragma" -> "no-cache",
		"Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("UserStory14")
		.exec(http("request_0")
			.get("/vet/visits")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/resources/images/favicon.png")
			.headers(headers_1)))
		.pause(10)
		// Visits
		.exec(http("request_2")
			.get("/vet/visits/3")
			.headers(headers_0))
		.pause(16)
		// ShowVisit
		.exec(http("request_3")
			.get("/vet/visits/3/prescriptions/new")
			.headers(headers_0))
		.pause(16)
		// AddPrescription
		.exec(http("request_4")
			.post("/vet/visits/3/prescriptions/new")
			.headers(headers_4)
			.formParam("frequency", "Frecuencia")
			.formParam("duration", "Duraci�n")
			.formParam("medicine", "Betadine")
			.formParam("prescription.id", "")
			.formParam("_csrf", "fe41f5c7-a862-4171-951a-06cdb8234b10"))
		.pause(15)
		// AddedPrescription
		.exec(http("request_5")
			.get("/vet/visits/3/prescriptions/new")
			.headers(headers_0))
		.pause(16)
		// AddPrescription
		.exec(http("request_6")
			.post("/vet/visits/3/prescriptions/new")
			.headers(headers_4)
			.formParam("frequency", "")
			.formParam("duration", "")
			.formParam("medicine", "Betadine")
			.formParam("prescription.id", "")
			.formParam("_csrf", "fe41f5c7-a862-4171-951a-06cdb8234b10"))
		.pause(12)
		// AttemptedToAddEmptyPrescription

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}