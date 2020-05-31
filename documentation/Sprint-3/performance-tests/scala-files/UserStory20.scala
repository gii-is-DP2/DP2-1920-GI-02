package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory20 extends Simulation {

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
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("UserStory20")
		.exec(http("request_0")
			.get("/admin/visitTypes")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/resources/images/favicon.png")
			.headers(headers_1)))
		.pause(8)
		// VisitTypes
		.exec(http("request_2")
			.get("/admin/visitTypes/new")
			.headers(headers_0))
		.pause(27)
		// AddVisitType
		.exec(http("request_3")
			.post("/admin/visitTypes/new")
			.headers(headers_3)
			.formParam("name", "VisitaTipo")
			.formParam("duration", "20")
			.formParam("price", "20")
			.formParam("visitType.id", "")
			.formParam("_csrf", "e21afa96-7135-4806-9199-1be06361c069"))
		.pause(13)
		// AddedVisitType
		.exec(http("request_4")
			.get("/admin/visitTypes/new")
			.headers(headers_0))
		.pause(20)
		// AddVisitType
		.exec(http("request_5")
			.post("/admin/visitTypes/new")
			.headers(headers_3)
			.formParam("name", "hola")
			.formParam("duration", "hola")
			.formParam("price", "hola")
			.formParam("visitType.id", "")
			.formParam("_csrf", "e21afa96-7135-4806-9199-1be06361c069"))
		.pause(18)
		// AttemptedToAddInvalidVisitType

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}