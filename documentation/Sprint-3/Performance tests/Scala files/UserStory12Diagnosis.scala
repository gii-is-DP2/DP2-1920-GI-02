package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory12Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""",""".*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
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
		.pause(5)
	}

	object LoginSecretary {
		val loginSecretary = exec(http("LoginSecretary_get")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(9)
		.exec(http("LoginSecretary_post")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "secretary2")
			.formParam("password", "secretary2")
			.formParam("_csrf", "${stoken}"))
		.pause(7)
	}

	object ListUnpaidVisitsAccepted {
		val listUnpaidVisitsAccepted = exec(http("ListUnpaidVisitsAccepted")
			.get("/secretary/visits")
			.headers(headers_0))
		.pause(23)
	}

	object LoginOwner {
		val loginOwner = exec(http("LoginOwner_get")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(11)
		.exec(http("LoginOwner_post")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "gfranklin")
			.formParam("password", "gfranklin")
			.formParam("_csrf", "${stoken}"))
		.pause(9)
	}

	object ListUnpaidVisitsRefused {
		val listUnpaidVisitRefused = exec(http("ListUnpaidVisitsRefused")
			.get("/secretary/visits")
			.headers(headers_0)
			.resources(http("request_12")
			.get("/favicon.ico")
			.headers(headers_2)
			.check(status.is(403)))
			.check(status.is(403)))
		.pause(18)
	}


	val scn1 = scenario("UnpaidVisitsAccepted").exec(Home.home, LoginSecretary.loginSecretary, ListUnpaidVisitsAccepted.listUnpaidVisitsAccepted)

	val scn2 = scenario("UnpaidVisitsRefused").exec(Home.home, LoginOwner.loginOwner, ListUnpaidVisitsRefused.listUnpaidVisitRefused)



	setUp(scn1.inject(rampUsers(2000) during (100 seconds)), 
		  scn2.inject(rampUsers(2000) during (100 seconds))
		  ).protocols(httpProtocol)
		  .assertions(
			  global.responseTime.max.lt(5000),
			  global.responseTime.mean.lt(1000),
			  global.successfulRequests.percent.gt(95)
		  )
}