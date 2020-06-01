package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory9Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""",""".*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_4 = Map(
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft-WNS/10.0")

    val uri2 = "http://tile-service.weather.microsoft.com/es-ES/livetile/preinstall"

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(8)
	}

	object LoginSecretary {
		val loginSecretary = exec(http("LoginSecretary_get")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/favicon.ico")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(8)
		.exec(http("LoginSecretary_post")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "secretary2")
			.formParam("password", "secretary2")
			.formParam("_csrf", "${stoken}"))
		.pause(4)
	}

	object VisitListAccepted {
		val visitListAccepted = exec(http("VisitListAccepted")
			.get("/secretary/visits")
			.headers(headers_0))
		.pause(19)
	}

	object PaymentCash {
		val paymentCash = exec(http("PaymentCash_get")
			.get("/secretary/visits/6/payments/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(11)
		.exec(http("PaymentCash_post")
			.post("/secretary/visits/6/payments/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("method", "cash")
			.formParam("finalPrice", "200.00")
			.formParam("_csrf", "${stoken}"))
		.pause(16)
	}

	object Logout {
		val logout = exec(http("Logout_get")
			.get("/logout")
			.headers(headers_0)
			.resources(http("request_12")
			.get("/favicon.ico")
			.headers(headers_2)
			.check(status.is(403)))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(9)
		.exec(http("Logout_post")
			.post("/logout")
			.headers(headers_3)
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object LoginOwner {
		val loginOwner = exec(http("LoginOwner_get")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_15")
			.get("/favicon.ico")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
		.exec(http("LoginOwner_post")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "gfranklin")
			.formParam("password", "gfranklin")
			.formParam("_csrf", "${stoken}"))
		.pause(16)
	}

	object VisitListRefused {
		val visitListRefused = exec(http("VisitListRefused")
			.get("/secretary/visits")
			.headers(headers_0)
			.resources(http("request_18")
			.get("/favicon.ico")
			.headers(headers_2)
			.check(status.is(403)))
			.check(status.is(403)))
		.pause(17)
	}

	val scn1 = scenario("PaymentWithSecretary").exec(Home.home, LoginSecretary.loginSecretary, VisitListAccepted.visitListAccepted, 
						PaymentCash.paymentCash, Logout.logout)

	val scn2 = scenario("PaymentWithOwner").exec(Home.home, LoginOwner.loginOwner, VisitListRefused.visitListRefused)


	setUp(scn1.inject(rampUsers(1700) during (100 seconds)), 
		  scn2.inject(rampUsers(1700) during (100 seconds))
		  ).protocols(httpProtocol)
		  .assertions(
			  global.responseTime.max.lt(5000),
			  global.responseTime.mean.lt(1000),
			  global.successfulRequests.percent.gt(95)
		  )
}