package org.group2.petclinic.UITests.admin.steps;

import static org.junit.Assert.assertEquals;

import org.group2.petclinic.UITests.AbstractStep;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@ExtendWith(SpringExtension.class)
public class AdminLoginAndViewsPositiveUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();


	@Given("I log in to the system with user {string} with a valid password like an admin")
	public void loggin(String username) throws Exception {
		loginAdmin(username, driver, port);
	}

	@When("I go to the estadistics views")
	public void showViews() throws Exception {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span")).click();
		assertEquals("Visits without payment", driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Moment", driver.findElement(By.xpath("//table[@id='visitsTable']/thead/tr/th")).getText());
		assertEquals("Description", driver.findElement(By.xpath("//table[@id='visitsTable']/thead/tr/th[2]")).getText());
		assertEquals("See payment", driver.findElement(By.xpath("//table[@id='visitsTable']/thead/tr/th[6]")).getText());
		assertEquals("See diagnosis", driver.findElement(By.xpath("//table[@id='visitsTable']/thead/tr/th[7]")).getText());

		if (driver.findElement(By.xpath("//a[contains(text(),'Payment')]")) != null) {
			driver.findElement(By.xpath("//a[contains(text(),'Payment')]")).click();
			assertEquals("Data payments of that visit", driver.findElement(By.xpath("//h2")).getText());
		}

		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span")).click();

		if (driver.findElement(By.xpath("//a[contains(text(),'Diagnosis')]")) != null) {
			driver.findElement(By.xpath("//a[contains(text(),'Diagnosis')]")).click();
			assertEquals("Data diagnosis of that visit", driver.findElement(By.xpath("//h2")).getText());
		}

		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[7]/a/span[2]")).click();
		assertEquals("Revenues by month", driver.findElement(By.xpath("//h1")).getText());
		assertEquals("Month", driver.findElement(By.xpath("//table[@id='revenueTable']/thead/tr/th")).getText());
		assertEquals("Revenues", driver.findElement(By.xpath("//table[@id='revenueTable']/thead/tr/th[2]")).getText());

		driver.findElement(By.xpath("//a/span[2]")).click();
	}

	@Then("I can do logout like an admin")
	public void logout() throws Exception {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a/span[2]")).click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		driver.findElement(By.cssSelector("button.btn.btn-lg.btn-primary.btn-block")).click();
		assertEquals("LOGIN", driver.findElement(By.cssSelector("ul.nav.navbar-nav.navbar-right > li > a")).getText());
		stopDriver();
	}
	
	public static void loginAdmin(String username, WebDriver driver, int port) {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button")).click();

		assertEquals("ADMIN1", driver.findElement(By.xpath("//a[contains(@href, '#')]")).getText());
	}

}