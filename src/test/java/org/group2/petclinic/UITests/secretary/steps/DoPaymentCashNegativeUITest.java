
package org.group2.petclinic.UITests.secretary.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.group2.petclinic.UITests.AbstractStep;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@ExtendWith(SpringExtension.class)
public class DoPaymentCashNegativeUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();

	private int			numVisitsInicial;


	@Given("I log with {string} and go to the unpaid visits view neg")
	public void loginAndView(String username) throws Exception {
		SecretaryLoginAndViewsPositiveUITest.loginSecretary(username, driver, port);
		driver.findElement(By.xpath("//li[2]/a/span[2]")).click();
		numVisitsInicial = numVisits();
	}

	@When("Do a payment of a visit selecting cash and the negative quantity")
	public void doPaymentCash() throws Exception {
		driver.findElement(By.xpath("//a[contains(text(),'Add Payment')]")).click();
		assertEquals("New Payment", driver.findElement(By.xpath("//h2")).getText());
		driver.findElement(By.id("method")).click();
		new Select(driver.findElement(By.id("method"))).selectByVisibleText("cash");
		driver.findElement(By.id("method")).click();
		driver.findElement(By.id("finalPrice")).click();
		driver.findElement(By.id("finalPrice")).clear();
		driver.findElement(By.id("finalPrice")).sendKeys("-15.00");
	}

	@Then("The payment doesn't save")
	public void quantityIsNegativeError() throws Exception {
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Final price must be bigger than 0.",
			driver.findElement(By.xpath("//form[@id='payment']/div/div[2]/div/span[2]")).getText());
		driver.findElement(By.xpath("//li[2]/a/span[2]")).click();

		assertTrue(numVisits() == numVisitsInicial);
		stopDriver();
	}

	private int numVisits() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		WebElement tabla = driver.findElement(By.xpath("//table[1]"));
		List<WebElement> filasDeTabla = tabla.findElements(By.tagName("tr"));
		return filasDeTabla.size();
	}

	// WebElement tabla = driver.findElement(By.xpath("//table[@id='visitsTable']/thead/tr/th"));

}
