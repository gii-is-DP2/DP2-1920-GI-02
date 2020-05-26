package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory01 extends Simulation {

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

	object LoginAsOwner {
		val loginAsOwner = exec(http("LoginAsOwner1")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("LoginAsOwner2")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "gfranklin")
			.formParam("password", "gfraklin")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object ScheduleCorrectVisist{
		val scheduleCorrectVisist = exec(http("scheduleCorrectVisist1")
			.get("/owner/schedule-visit")
			.headers(headers_0))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(11)
		.exec(http("scheduleCorrectVisist2")
			.post("/owner/schedule-visit")
			.headers(headers_3)
			.formParam("pet", "Leo")
			.formParam("description", "description")
			.formParam("visitType", "consultation")
			.formParam("vet", "James Carter")
			.formParam("moment", "2022/02/01 10:30")
			.formParam("petId", "")
			.formParam("_csrf", "${stoken}"))
		.pause(21)
	}

	object AttemptToScheduleOccupiedSlot{
		val attemptToScheduleOccupiedSlot = exec(http("AttemptToScheduleOccupiedSlot1")
			.get("/owner/schedule-visit")
			.headers(headers_0))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(11)
		.exec(http("AttemptToScheduleOccupiedSlot2")
			.post("/owner/schedule-visit")
			.headers(headers_3)
			.formParam("pet", "Leo")
			.formParam("description", "description")
			.formParam("visitType", "consultation")
			.formParam("vet", "James Carter")
			.formParam("moment", "2025/04/01 10:30")
			.formParam("petId", "")
			.formParam("_csrf", "${stoken}"))
		.pause(21)
	}

	val positiveScn = scenario("CorrectScheduling").exec(
		Home.home, 
		LoginAsOwner.loginAsOwner,
		ScheduleCorrectVisist.scheduleCorrectVisist
	)
	
	val negativeScn = scenario("IncorrectScheduling").exec(
		Home.home, 
		LoginAsOwner.loginAsOwner,
		AttemptToScheduleOccupiedSlot.attemptToScheduleOccupiedSlot
	)
	
	setUp(
		positiveScn.inject(atOnceUsers(1)),
		negativeScn.inject(atOnceUsers(1))
	).protocols(httpProtocol)
}