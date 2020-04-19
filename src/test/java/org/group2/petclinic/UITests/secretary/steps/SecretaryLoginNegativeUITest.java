
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
public class SecretaryLoginNegativeUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();


	@Given("I am not logged in the system and I want login like a secretary")
	public void login() throws Exception {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
	}

	@When("I try to do login as user {string} that is a secretary with an invalid password")
	public void invalidPassword(String username) throws Exception {
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("aaaa");
		driver.findElement(By.xpath("//button")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Bad credentials", driver.findElement(By.xpath("//form/div")).getText());

	}

	@Then("the login form is shown again and I'm not a secretary")
	public void backToRegistrePage() throws Exception {
		assertEquals("Bad credentials", driver.findElement(By.xpath("//form/div")).getText());
		stopDriver();

	}
}
