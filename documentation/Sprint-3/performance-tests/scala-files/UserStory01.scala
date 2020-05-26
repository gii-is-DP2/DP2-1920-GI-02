package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory01 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpg"""), WhiteList())
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_7 = Map(
		"A-IM" -> "x-bm,gzip",
		"Proxy-Connection" -> "keep-alive")

    val uri1 = "http://clientservices.googleapis.com/chrome-variations/seed"

	val scn = scenario("UserStory01")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0))
		.pause(11)
		// Home
		.exec(http("request_1")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(16)
		// Login
		.exec(http("request_3")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "gfranklin")
			.formParam("password", "gfranklin")
			.formParam("_csrf", "9a32d4e0-c8a3-4443-968a-411a18e629d2"))
		.pause(24)
		// LoggedInAsOwner
		.exec(http("request_4")
			.get("/owner/schedule-visit")
			.headers(headers_0))
		.pause(53)
		// ScheduleNewVisit
		.exec(http("request_5")
			.post("/owner/schedule-visit")
			.headers(headers_3)
			.formParam("pet", "Leo")
			.formParam("description", "description")
			.formParam("visitType", "consultation")
			.formParam("vet", "James Carter")
			.formParam("moment", "2022/02/01 10:30")
			.formParam("petId", "")
			.formParam("_csrf", "95800450-451b-4b71-a35a-7e6a28dbb6ad"))
		.pause(21)
		// ScheduledCorrectVisit
		.exec(http("request_6")
			.get("/owner/schedule-visit")
			.headers(headers_0))
		.pause(39)
		// ScheduleNewVisit
		.exec(http("request_7")
			.get(uri1 + "?osname=win&channel=stable&milestone=83")
			.headers(headers_7))
		.pause(5)
		.exec(http("request_8")
			.post("/owner/schedule-visit")
			.headers(headers_3)
			.formParam("pet", "Leo")
			.formParam("description", "description")
			.formParam("visitType", "consultation")
			.formParam("vet", "James Carter")
			.formParam("moment", "2025/04/01 10:30")
			.formParam("petId", "")
			.formParam("_csrf", "95800450-451b-4b71-a35a-7e6a28dbb6ad"))
		.pause(24)
		// AttemptedToScheduleOccupiedSlot

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}