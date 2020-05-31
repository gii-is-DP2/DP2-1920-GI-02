package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory13Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
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


	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(8)
	}

	object LoginAsVet {
		val loginAsVet = exec(http("LoginAsVet1")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("LoginAsVet2")
			.post("/login")
			.headers(headers_4)
			.formParam("username", "vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object VisitsView {
		val visitsView = exec(http("VisitsView")
			.get("/vet/visits")
			.headers(headers_0))
		.pause(10)
	}

	object ShowVisitWithoutDiagnosis {
		val showVisitWithoutDiagnosis = exec(http("ShowVisitWithoutDiagnosis")
			.get("/vet/visits/6")
			.headers(headers_0))
		.pause(10)
	}

	object AddDiagnosis {
		val addDiagnosis = exec(http("AddDiagnosis")
			.get("/vet/visits/6/diagnosis/new")
			.headers(headers_0)
			.resources(http("request_8")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_highlight-soft_100_eeeeee_1x100.png")
			.headers(headers_3)))
			.check(css("input[name=_csrf]", 
		.pause(8)
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
			.formParam("_csrf", "${stoken}"))
		.pause(10)
	}

	object ShowVisitWithDiagnosis {
		val showVisitWithDiagnosis = exec(http("ShowVisitWithDiagnosis")
			.get("/vet/visits/3")
			.headers(headers_0))
		.pause(10)
	}


	val positiveScn = scenario("CorrectlyAddDiagnosis").exec(
		Home.home, 
		LoginAsVet.loginAsVet,
		VisitsView.visitsView,
		ShowVisitWithoutDiagnosis.showVisitWithoutDiagnosis,
		AddDiagnosis.addDiagnosis
	)

	val negativeScn = scenario("AttemptToAddDiagnosisToVisitWithDiagnosis").exec(
		Home.home, 
		LoginAsVet.loginAsVet,
		VisitsView.visitsView,
		ShowVisitWithDiagnosis.showVisitWithDiagnosis
	)

	setUp(
		positiveScn.inject(rampUsers(5000) during (100 seconds)),
		negativeScn.inject(rampUsers(5000) during (100 seconds))
	).protocols(httpProtocol)
}