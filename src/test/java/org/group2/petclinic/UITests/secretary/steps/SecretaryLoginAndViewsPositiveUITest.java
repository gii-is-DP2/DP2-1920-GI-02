
package org.group2.petclinic.UITests.secretary.steps;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.group2.petclinic.UITests.AbstractStep;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecretaryLoginAndViewsPositiveUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();


	@Given("I log in to the system with user {string} with a valid password like a secretary")
	public void login(String username) throws Exception {
		loginSecretary(username, driver, port);
	}

	@When("I go to the visits view")
	public void showViews() throws Exception {
		driver.findElement(By.xpath("//li[2]/a/span[2]")).click();

		assertEquals("Visits without payment", driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Moment", driver.findElement(By.xpath("//th")).getText());
		assertEquals("Description", driver.findElement(By.xpath("//th[2]")).getText());
		assertEquals("Pet", driver.findElement(By.xpath("//th[3]")).getText());
		assertEquals("Do payment", driver.findElement(By.xpath("//th[4]")).getText());
		driver.findElement(By.cssSelector("a[title=\"home page\"]")).click();
	}

	@Then("I can do logout like a secretary")
	public void logout() throws Exception {
		driver.findElement(By.xpath("//ul[2]/li/a")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("LOGIN", driver.findElement(By.cssSelector("ul.nav.navbar-nav.navbar-right > li > a")).getText());
		stopDriver();
	}

	public static void loginSecretary(String username, WebDriver driver, int port) {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("secretary2");
		driver.findElement(By.cssSelector("button.btn.btn-lg.btn-primary.btn-block")).click();
		assertEquals("SECRETARY2", driver.findElement(By.cssSelector("a.dropdown-toggle")).getText());
	}

}
