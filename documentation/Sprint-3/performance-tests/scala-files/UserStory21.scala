package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory21 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")



	val scn = scenario("UserStory21")
		.exec(http("request_0")
			.get("/admin/visitTypes")
			.headers(headers_0))
		.pause(14)
		// VisitTypes
		.exec(http("request_1")
			.get("/admin/visitTypes/3/edit")
			.headers(headers_0))
		.pause(20)
		// EditVisitType
		.exec(http("request_2")
			.post("/admin/visitTypes/3/edit")
			.headers(headers_2)
			.formParam("name", "operationHola")
			.formParam("duration", "60")
			.formParam("price", "100.0")
			.formParam("_csrf", "e21afa96-7135-4806-9199-1be06361c069"))
		.pause(20)
		// EditedVisitType
		.exec(http("request_3")
			.get("/admin/visitTypes/3/edit")
			.headers(headers_0))
		.pause(13)
		// EditVisitType
		.exec(http("request_4")
			.post("/admin/visitTypes/3/edit")
			.headers(headers_2)
			.formParam("name", "operationHola")
			.formParam("duration", "60hola")
			.formParam("price", "100.0")
			.formParam("_csrf", "e21afa96-7135-4806-9199-1be06361c069"))
		.pause(20)
		// AttemptedInvalidEditOfVisitType

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}