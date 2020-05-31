package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory14Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
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

	val headers_4 = Map(
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

	object ShowVisitWithDiagnosis {
		val showVisitWithDiagnosis = exec(http("ShowVisitWithDiagnosis")
			.get("/vet/visits/3")
			.headers(headers_0))
		.pause(10)
	}

	object AddPrescription {
		val addPrescription = exec(http("AddPrescription")
			.get("/vet/visits/3/prescriptions/new")
			.headers(headers_0))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
			.post("/vet/visits/3/prescriptions/new")
			.headers(headers_4)
			.formParam("frequency", "Frecuencia")
			.formParam("duration", "Duraci�n")
			.formParam("medicine", "Betadine")
			.formParam("prescription.id", "")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object AttemptToAddEmptyPrescription {
		val attemptToAddEmptyPrescription = exec(http("AttemptToAddEmptyPrescription")
			.get("/vet/visits/3/prescriptions/new")
			.headers(headers_0))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
			.post("/vet/visits/3/prescriptions/new")
			.headers(headers_4)
			.formParam("frequency", "")
			.formParam("duration", "")
			.formParam("medicine", "Betadine")
			.formParam("prescription.id", "")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}
	
	val positiveScn = scenario("CorrectlyAddPrescription").exec(
		Home.home, 
		LoginAsVet.loginAsVet,
		VisitsView.visitsView,
		ShowVisitWithDiagnosis.showVisitWithDiagnosis,
		AddDiagnosis.addDiagnosis
	)

	val negativeScn = scenario("AttemptToAddIncorrectPrescription").exec(
		Home.home, 
		LoginAsVet.loginAsVet,
		VisitsView.visitsView,
		AttemptToAddEmptyPrescription.attemptToAddEmptyPrescription
	)

	setUp(
		positiveScn.inject(rampUsers(5000) during (100 seconds)),
		negativeScn.inject(rampUsers(5000) during (100 seconds))
	).protocols(httpProtocol)
}