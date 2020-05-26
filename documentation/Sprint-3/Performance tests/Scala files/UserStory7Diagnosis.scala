package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory7Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""",""".*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept-Language" -> "es-ES,en,*",
		"User-Agent" -> "Mozilla/5.0")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_5 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_6 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

    val uri2 = "http://149.154.167.92/api"

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_3))
		.pause(12)
	}

	object Login {
		val login = exec(http("Login_get")
			.get("/login")
			.headers(headers_3)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(2)
		.exec(http("request_5")
			.get("/favicon.ico")
			.headers(headers_5))
		.pause(8)
		.exec(http("Login_post")
			.post("/login")
			.headers(headers_6)
			.formParam("username", "secretary2")
			.formParam("password", "secretary2")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object VisitList {
		val visitList = exec(http("VisitList")
			.get("/secretary/visits")
			.headers(headers_3))
		.pause(11)
	}

	object AddPaymentCash {
		val addPaymentCash = exec(http("AddPaymentCash_get")
			.get("/secretary/visits/6/payments/new")
			.headers(headers_3)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(9)
		.exec(http("AddPaymentCash_post")
			.post("/secretary/visits/6/payments/new")
			.headers(headers_6)
			.formParam("id", "")
			.formParam("method", "cash")
			.formParam("finalPrice", "100.00")
			.formParam("_csrf", "${stoken}"))
		.pause(17)
	}

	object AddPaymentCreditcard {
		val addPaymentCreditcard = exec(http("AddPaymentCreditcard_get")
			.get("/secretary/visits/7/payments/new")
			.headers(headers_3)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("AddPaymentCreditcard_post")
			.post("/secretary/visits/7/payments/new")
			.headers(headers_6)
			.formParam("id", "")
			.formParam("method", "creditcard")
			.formParam("finalPrice", "150.00")
			.formParam("_csrf", "${stoken}"))
		.pause(67)
	}

	object AddCreditcard {
		val addCreditcard = exec(http("AddCreditcard")
			.post("/secretary/visits/7/payments/7/creditcards/new")
			.headers(headers_6)
			.formParam("id", "")
			.formParam("holder", "Maria")
			.formParam("brand", "Lombar")
			.formParam("number", "4647952753170169")
			.formParam("expMonth", "5")
			.formParam("expYear", "25")
			.formParam("securityCode", "554")
			.formParam("_csrf", "${stoken}"))
		.pause(13)
	}

	val scn1 = scenario("PaymentCash").exec(Home.home, Login.login, VisitList.visitList, AddPaymentCash.addPaymentCash)

	val scn2 = scenario("PaymentCreditcard").exec(Home.home, Login.login, VisitList.visitList, 
						AddPaymentCreditcard.addPaymentCreditcard, AddCreditcard.addCreditcard)


	setUp(scn1.inject(rampUsers(2000) during (100 seconds)), 
		  scn2.inject(rampUsers(2000) during (100 seconds))
		  ).protocols(httpProtocol)
		  .assertions(
			  global.responseTime.max.lt(5000),
			  global.responseTime.mean.lt(1000),
			  global.successfulRequests.percent.gt(95)
		  )
}