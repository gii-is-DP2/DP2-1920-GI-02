package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory10Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""",""".*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,en,*")
		.userAgentHeader("Mozilla/5.0")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_4 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

    val uri2 = "http://149.154.167.92/api"


	object Home {
		val home = exec(http("request_0")
			.get("/")
			.headers(headers_0))
		.pause(13)
	}

	object Login {
		val login = exec(http("Login_get")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(2)
		.exec(http("Login_post")
			.post("/login")
			.headers(headers_4)
			.formParam("username", "secretary2")
			.formParam("password", "secretary2")
			.formParam("_csrf", "${stoken}"))
		.pause(3)
	}

	object VisitList {
		val visitList = exec(http("VisitList")
			.get("/secretary/visits")
			.headers(headers_0))
		.pause(5)
	}

	object PaymentWithEstimatePrice {
		val paymentWithEstimatePrice = exec(http("PaymentWithEstimatePrice_get")
			.get("/secretary/visits/7/payments/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(6)
		.exec(http("PaymentWithEstimatePrice_post")
			.post("/secretary/visits/7/payments/new")
			.headers(headers_4)
			.formParam("id", "")
			.formParam("method", "cash")
			.formParam("finalPrice", "20.0")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object PaymentWith0EstimatePrice {
		val paymentWith0EstimatePrice = exec(http("PaymentWith0EstimatePrice_get")
			.get("/secretary/visits/8/payments/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(7)
		.exec(http("PaymentWith0EstimatePrice_post")
			.post("/secretary/visits/8/payments/new")
			.headers(headers_4)
			.formParam("id", "")
			.formParam("method", "cash")
			.formParam("finalPrice", "0.00")
			.formParam("_csrf", "${stoken}"))
		.pause(18)
	}

	val scn1 = scenario("WithEstimatePrice").exec(Home.home, Login.login, VisitList.visitList, PaymentWithEstimatePrice.paymentWithEstimatePrice)

	val scn2 = scenario("With0EstimatePrice").exec(Home.home, Login.login, VisitList.visitList, PaymentWith0EstimatePrice.paymentWith0EstimatePrice)



	setUp(scn1.inject(rampUsers(1500) during (100 seconds)), 
		  scn2.inject(rampUsers(1500) during (100 seconds))
		  ).protocols(httpProtocol)
		  .assertions(
			  global.responseTime.max.lt(5000),
			  global.responseTime.mean.lt(1000),
			  global.successfulRequests.percent.gt(95)
		  )
}