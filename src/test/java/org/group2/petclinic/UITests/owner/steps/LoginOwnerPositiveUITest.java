
package org.group2.petclinic.UITests.owner.steps;

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
public class LoginOwnerPositiveUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();


	@Given("that I am not logged in")
	public void doNothing() {

	}

	@When("I login as gfranklin with password gfranklin")
	public void login() {
		loginOwner("gfranklin", "gfranklin", driver, port);
	}

	@Then("I can access the system as an owner")
	public void checkLoggedInName() throws Exception {
		assertEquals("GFRANKLIN",
			driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		logout();
	}

	public static void loginOwner(String username, String password, WebDriver driver, int port) {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);

		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	public void logout() throws Exception {
		driver.findElement(By.xpath("//ul[2]/li/a")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("LOGIN",
			driver.findElement(By.cssSelector("ul.nav.navbar-nav.navbar-right > li > a")).getText());
		stopDriver();
	}

}
