package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory21Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")

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
			.headers(headers_4)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object ShowVisitTypes {
		val showVisitTypes = exec(http("ShowVisitTypes")
			.get("/admin/visitTypes")
			.headers(headers_0)
		.pause(10)
	}

	object EditVisitType {
		val editVisitType = exec(http("EditVisitType")
			.get("/admin/visitTypes/3/edit")
			.headers(headers_0))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
			post("/admin/visitTypes/3/edit")
			.headers(headers_2)
			.formParam("name", "operationHola")
			.formParam("duration", "60")
			.formParam("price", "100.0")
			.formParam("_csrf", "${stoken}"))
	}

	object AttemptInvalidEditOfVisitType {
		val attemptInvalidEditOfVisitType = exec(http("AttemptInvalidEditOfVisitType")
			.get("/admin/visitTypes/3/edit")
			.headers(headers_0))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
			post("/admin/visitTypes/3/edit")
			.headers(headers_2)
			.formParam("name", "operationHola")
			.formParam("duration", "60hola")
			.formParam("price", "100.0")
			.formParam("_csrf", "${stoken}"))
	}

	val positiveScn = scenario("EditVisitTypeValid").exec(
		Home.home, 
		LoginAsAdmin.loginAsAdmin,
		ShowVisitTypes.showVisitTypes,
		EditVisitType.editVisitType
	)

	val negativeScn = scenario("EditVisitTypeInvalid").exec(
		Home.home, 
		LoginAsAdmin.loginAsAdmin,
		ShowVisitTypes.showVisitTypes,
		AttemptInvalidEditOfVisitType.attemptInvalidEditOfVisitType
	)

	setUp(
		positiveScn.inject(rampUsers(5000) during (100 seconds)),
		negativeScn.inject(rampUsers(5000) during (100 seconds))
	).protocols(httpProtocol)
}