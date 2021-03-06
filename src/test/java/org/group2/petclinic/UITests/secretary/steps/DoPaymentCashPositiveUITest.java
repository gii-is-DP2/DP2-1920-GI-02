
package org.group2.petclinic.UITests.secretary.steps;

import java.util.regex.Pattern;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.group2.petclinic.UITests.AbstractStep;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@ExtendWith(SpringExtension.class)
public class DoPaymentCashPositiveUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();

	private int			numVisitsInicial;


	@Given("I log with {string} and go to the unpaid visits view")
	public void loginAndView(String username) throws Exception {
		SecretaryLoginAndViewsPositiveUITest.loginSecretary(username, driver, port);
		driver.findElement(By.cssSelector("a[title=\"visits\"]")).click();
		numVisitsInicial = numVisits();
	}

	@When("Do a payment of a visit selecting cash and the positive quantity")
	public void doPaymentCash() throws Exception {
		driver.findElement(By.linkText("Add Payment")).click();
		assertEquals("New Payment", driver.findElement(By.xpath("//h2")).getText());
		driver.findElement(By.id("method")).click();
		new Select(driver.findElement(By.id("method"))).selectByVisibleText("cash");
		driver.findElement(By.id("method")).click();
		driver.findElement(By.id("finalPrice")).click();
		driver.findElement(By.id("finalPrice")).clear();
		driver.findElement(By.id("finalPrice")).sendKeys("30.00");
	}

	@Then("The payment is saved")
	public void savePayment() throws Exception {
		driver.findElement(By.cssSelector("button.btn.btn-default")).click();

		assertTrue(numVisits() < numVisitsInicial);
		stopDriver();
	}

	private int numVisits() {
		WebElement tabla = driver.findElement(By.xpath("//table[1]"));
		List<WebElement> filasDeTabla = tabla.findElements(By.tagName("tr"));
		return filasDeTabla.size();
	}

}
