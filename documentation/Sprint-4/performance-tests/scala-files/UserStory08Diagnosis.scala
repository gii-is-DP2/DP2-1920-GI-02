package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory8Diagnosis extends Simulation {

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
		.pause(12)
	}

	object Login {
		val login = exec(http("Login_get")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/favicon.ico")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(8)
		.exec(http("Login_post")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "secretary2")
			.formParam("password", "secretary2")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}

	object VisitList {
		val visitList = exec(http("VisitList")
			.get("/secretary/visits")
			.headers(headers_0))
		.pause(10)
	}

	object AddPaymentCreditcard {
		val addPaymentCreditcard = exec(http("AddPaymentCreditcard_get")
			.get("/secretary/visits/8/payments/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(7)
		.exec(http("AddPaymentCreditcard_post")
			.post("/secretary/visits/8/payments/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("method", "creditcard")
			.formParam("finalPrice", "100.00")
			.formParam("_csrf", "${stoken}"))
		.pause(30)
	}

	object AddValidCreditcard {
		val addValidCreditcard = exec(http("AddValidCreditcard")
			.post("/secretary/visits/8/payments/13/creditcards/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("holder", "Maria")
			.formParam("brand", "Lombar")
			.formParam("number", "4001463457343557")
			.formParam("expMonth", "3")
			.formParam("expYear", "25")
			.formParam("securityCode", "551")
			.formParam("_csrf", "${stoken}"))
		.pause(14)
	}

	object AddInvalidCreditcard {
		val addInvalidCreditcard = exec(http("AddInvalidCreditcard")
			.post("/secretary/visits/9/payments/15/creditcards/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("holder", "Maria")
			.formParam("brand", "Lombar")
			.formParam("number", "1122334455667788")
			.formParam("expMonth", "4")
			.formParam("expYear", "25")
			.formParam("securityCode", "218")
			.formParam("_csrf", "${stoken}"))
		.pause(13)
	}


	val scn1 = scenario("ValidCreditcard").exec(Home.home, Login.login, VisitList.visitList, AddPaymentCreditcard.addPaymentCreditcard, 
						AddValidCreditcard.addValidCreditcard)

	val scn2 = scenario("InvalidCreditcard").exec(Home.home, Login.login, VisitList.visitList, AddPaymentCreditcard.addPaymentCreditcard, 
						AddInvalidCreditcard.addInvalidCreditcard)


	setUp(scn1.inject(rampUsers(2000) during (100 seconds)), 
		  scn2.inject(rampUsers(2000) during (100 seconds))
		  ).protocols(httpProtocol)
		  .assertions(
			  global.responseTime.max.lt(5000),
			  global.responseTime.mean.lt(1000),
			  global.successfulRequests.percent.gt(95)
		  )
}