package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory02 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

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



	val scn = scenario("UserStory02")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0))
		.pause(4)
		.exec(http("request_1")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(17)
		// Login
		.exec(http("request_3")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "gfranklin")
			.formParam("password", "gfranklin")
			.formParam("_csrf", "d585ae0c-00de-4b13-b48f-2a2a0ddaaf1a"))
		.pause(23)
		// LoggedInAsOwner
		.exec(http("request_4")
			.get("/owner/schedule-visit")
			.headers(headers_0))
		.pause(54)
		// ScheduleNewVisit
		.exec(http("request_5")
			.post("/owner/schedule-visit")
			.headers(headers_3)
			.formParam("pet", "Leo")
			.formParam("description", "description")
			.formParam("visitType", "consultation")
			.formParam("vet", "James Carter")
			.formParam("moment", "2022/02/26 05:30")
			.formParam("petId", "")
			.formParam("_csrf", "ed7fb321-cd50-435a-a481-0cfcd20a455c"))
		.pause(27)
		// AttemptedToScheduleOutsideWorkingHours

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}