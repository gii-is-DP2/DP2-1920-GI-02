package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory20Diagnosis extends Simulation {

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

	val headers_1 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Pragma" -> "no-cache",
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
		val loginAsAdmin = exec(http("LoginAsVet1")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("LoginAsVet2")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object ShowVisitTypes {
		val showVisitTypes = exec(http("ShowVisitTypes")
			.get("/admin/visitTypes")
			.headers(headers_0))
		.pause(10)
	}

	object AddVisitType {
		val addVisitType = exec(http("AddVisitType1")
			.get("/admin/visitTypes/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
		.exec(http("AddVisitType2")
		.post("/admin/visitTypes/new")
			.headers(headers_3)
			.formParam("name", "VisitaTipo")
			.formParam("duration", "20")
			.formParam("price", "20")
			.formParam("visitType.id", "")
			.formParam("_csrf", "${stoken}"))
		.pause(13)
	}

	object AttemptToAddInvalidVisitType {
		val attemptToAddInvalidVisitType = exec(http("AttemptToAddInvalidVisitType1")
			.get("/admin/visitTypes/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
		.exec(http("AttemptToAddInvalidVisitType2")
		.post("/admin/visitTypes/new")
			.headers(headers_3)
			.formParam("name", "hola")
			.formParam("duration", "hola")
			.formParam("price", "hola")
			.formParam("visitType.id", "")
			.formParam("_csrf", "${stoken}"))
		.pause(13)
	}

	val positiveScn = scenario("AddValidVisitType").exec(
		Home.home, 
		LoginAsAdmin.loginAsAdmin,
		ShowVisitTypes.showVisitTypes,
		AddVisitType.addVisitType
	)

	val negativeScn = scenario("AteemptToAddIncorrectVisitType").exec(
		Home.home, 
		LoginAsAdmin.loginAsAdmin,
		ShowVisitTypes.showVisitTypes,
		AttemptToAddInvalidVisitType.attemptToAddInvalidVisitType
	)

	setUp(
		positiveScn.inject(rampUsers(75000) during (10 seconds)),
		negativeScn.inject(rampUsers(75000) during (10 seconds))
	).protocols(httpProtocol)
}