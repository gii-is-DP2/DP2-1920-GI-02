package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class US05A extends Simulation {

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



	val scn = scenario("US05A")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_1")
			.get("/resources/images/favicon.png")
			.headers(headers_1))
		.pause(4)
		.exec(http("request_2")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_3")
			.get("/favicon.ico")
			.headers(headers_3)))
		.pause(9)
		.exec(http("request_4")
			.post("/login")
			.headers(headers_4)
			.formParam("username", "gfranklin")
			.formParam("password", "gfranklin")
			.formParam("_csrf", "c84ab9d2-6d44-43e1-b6fa-83c6fc5f95d3"))
		.pause(9)
		// Login
		.exec(http("request_5")
			.get("/owner/visits")
			.headers(headers_0))
		.pause(10)
		// VisitsView

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}