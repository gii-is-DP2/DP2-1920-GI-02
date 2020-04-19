
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
public class DoPaymentCreditcardNegativeUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();

	//private int numVisitsInicial;


	@Given("I log with {string} and see unpaid visits view neg")
	public void loginAndView(String username) throws Exception {
		SecretaryLoginAndViewsPositiveUITest.loginSecretary(username, driver, port);
		driver.findElement(By.cssSelector("a[title=\"visits\"]")).click();
		//numVisitsInicial = numVisits();
	}

	@When("Do a payment of a visit selecting creditcard and introduce a invalid one")
	public void doPaymentWithInvalidCreditcard() throws Exception {
		driver.findElement(By.linkText("Add Payment")).click();
		assertEquals("New Payment", driver.findElement(By.xpath("//h2")).getText());
		driver.findElement(By.id("finalPrice")).click();
		driver.findElement(By.id("finalPrice")).clear();
		driver.findElement(By.id("finalPrice")).sendKeys("30.00");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.id("holder")).clear();
		driver.findElement(By.id("holder")).sendKeys("Maria");
		driver.findElement(By.id("brand")).clear();
		driver.findElement(By.id("brand")).sendKeys("Visa");
		driver.findElement(By.id("number")).clear();
		driver.findElement(By.id("number")).sendKeys("1122334455667788");
		driver.findElement(By.id("expYear")).clear();
		driver.findElement(By.id("expYear")).sendKeys("15");
		driver.findElement(By.id("securityCode")).clear();
		driver.findElement(By.id("securityCode")).sendKeys("aa22");
	}

	@Then("The payment doesn't save because is invalid creditcard")
	public void creditcardWithErrors() throws Exception {
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Number is not valid.", driver.findElement(By.xpath("//form[@id='creditcard']/div/div[3]/div/span[2]")).getText());
		assertEquals("Expirated date.", driver.findElement(By.xpath("//form[@id='creditcard']/div/div[5]/div/span[2]")).getText());
		assertEquals("Security code must be a int with 3 or 4 digits.", driver.findElement(By.xpath("//form[@id='creditcard']/div/div[6]/div/span[2]")).getText());
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
		stopDriver();

		//assertTrue(numVisits() == numVisitsInicial);
	}

	private int numVisits() {
		WebElement tabla = driver.findElement(By.xpath("//table[1]"));
		List<WebElement> filasDeTabla = tabla.findElements(By.tagName("tr"));
		return filasDeTabla.size();
	}

}
