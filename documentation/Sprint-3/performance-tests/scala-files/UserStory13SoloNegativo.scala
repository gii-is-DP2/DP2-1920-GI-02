package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory13SoloNegativo extends Simulation {

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

	val headers_3 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("UserStory13SoloNegativo")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_1")
			.get("/resources/images/favicon.png")
			.headers(headers_1))
		.pause(15)
		// Home
		.exec(http("request_2")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_3")
			.get("/favicon.ico")
			.headers(headers_3)))
		.pause(12)
		// Login
		.exec(http("request_4")
			.post("/login")
			.headers(headers_4)
			.formParam("username", "vet2")
			.formParam("password", "vet2")
			.formParam("_csrf", "f8b1e0a4-a483-4ed6-95cd-36b99df777d4"))
		.pause(10)
		// LoggedInAsVet2
		.exec(http("request_5")
			.get("/vet/visits")
			.headers(headers_0))
		.pause(10)
		// Visits
		.exec(http("request_6")
			.get("/vet/visits/3")
			.headers(headers_0))
		.pause(15)
		// ShowVisitWithDiagnosis

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}