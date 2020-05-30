package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory05ADiagnosis extends Simulation {

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

	val headers_5 = Map(
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
			.headers(headers_5)
			.formParam("username", "gfranklin")
			.formParam("password", "gfraklin")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object VisitsView {
		val visitsView = exec(http("VisitsView")
			.get("/owner/visits")
			.headers(headers_0))
		.pause(10)
	}

	object AttemptToShowVisitsViewWithoutLogin {
		val attemptToShowVisitsViewWithoutLogin = exec(http("AttemptToShowVisitsViewWithoutLogin")
			.get("/owner/visits")
			.headers(headers_0))
		.pause(10)
	}

	val positiveScn = scenario("LoginAndShowView").exec(
		Home.home, 
		LoginAsOwner.loginAsOwner,
		VisitsView.visitsView
	)

	val negativeScn = scenario("DontLoginAndAttemptToShowView").exec(
		AttemptToShowVisitsViewWithoutLogin.attemptToShowVisitsViewWithoutLogin
	)

	setUp(
		positiveScn.inject(rampUsers(10000) during (100 seconds)),
		negativeScn.inject(rampUsers(10000) during (100 seconds))
	).protocols(httpProtocol)
}