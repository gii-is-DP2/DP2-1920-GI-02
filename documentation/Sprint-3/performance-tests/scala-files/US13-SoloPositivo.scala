package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class US13 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(), WhiteList())
		.acceptHeader("image/webp,image/apng,image/*,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Pragma" -> "no-cache",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map("Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("US13")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/resources/images/favicon.png")
			.headers(headers_1)))
		.pause(1)
		.exec(http("request_2")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_3")
			.get("/favicon.ico")
			.headers(headers_3)))
		.pause(9)
		// Login
		.exec(http("request_4")
			.post("/login")
			.headers(headers_4)
			.formParam("username", "vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "ab9e21f4-754f-461b-b763-3a79a4da5a8f"))
		.pause(11)
		// LoggedAsVet
		.exec(http("request_5")
			.get("/vet/visits")
			.headers(headers_0))
		.pause(25)
		// VisitsView
		.exec(http("request_6")
			.get("/vet/visits/6")
			.headers(headers_0))
		.pause(25)
		// VisitWithoutDiagnosis
		.exec(http("request_7")
			.get("/vet/visits/6/diagnosis/new")
			.headers(headers_0)
			.resources(http("request_8")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_highlight-soft_100_eeeeee_1x100.png")
			.headers(headers_3)))
		.pause(8)
		// AddDiagnosis
		.exec(http("request_9")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_gloss-wave_35_f6a828_500x100.png")
			.headers(headers_3)
			.resources(http("request_10")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_glass_100_f6f6f6_1x400.png")
			.headers(headers_3),
            http("request_11")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_highlight-soft_75_ffe45c_1x100.png")
			.headers(headers_3),
            http("request_12")
			.get("/webjars/jquery-ui/1.11.4/images/ui-icons_ffffff_256x240.png")
			.headers(headers_3)))
		.pause(3)
		.exec(http("request_13")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_glass_100_fdf5ce_1x400.png")
			.headers(headers_3))
		.pause(16)
		.exec(http("request_14")
			.post("/vet/visits/6/diagnosis/new")
			.headers(headers_4)
			.formParam("date", "2020/05/25")
			.formParam("description", "description")
			.formParam("diagnosis.id", "")
			.formParam("_csrf", "925a74f2-86db-46a4-9ffb-68285502ee3f"))
		.pause(10)
		// AddedDiagnosis

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}