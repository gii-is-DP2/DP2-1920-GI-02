
package org.group2.petclinic.UITests.vet.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.group2.petclinic.UITests.AbstractStep;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.java.Log;

@Log
@ExtendWith(SpringExtension.class)
public class LoginVetNegativeUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();


	@Given("I am not logged in the system and I want login like a vet")
	public void login() throws Exception {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
	}

	@When("I try to do login as user \"vet1\" that is a vet with an invalid password")
	public void invalidPassword() throws Exception {
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("vet1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("aaaa");
		driver.findElement(By.xpath("//button")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Bad credentials", driver.findElement(By.xpath("//form/div")).getText());

	}

	@Then("The login form is shown again and I'm not a vet")
	public void backToRegistrePage() throws Exception {
		assertEquals("Bad credentials", driver.findElement(By.xpath("//form/div")).getText());
		stopDriver();

	}

}
