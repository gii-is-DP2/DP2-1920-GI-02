
package org.group2.petclinic.UITests.admin.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.group2.petclinic.UITests.AbstractStep;
import org.group2.petclinic.UITests.admin.steps.AdminLoginAndViewsPositiveUITest;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.java.Log;

@Log
@ExtendWith(SpringExtension.class)
public class AdminAddMedicineNegativeUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();


	@Given("I am logged as an {string}")
	public void loggin(String username) throws Exception {
		AdminLoginAndViewsPositiveUITest.loginAdmin(username, driver, port);
	}

	@When("I go to the medicine list view and add a medicine with incorrect parameters")
	public void addMedicine() throws Exception {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		driver.findElement(By.linkText("Add medicine")).click();
		driver.findElement(By.id("name")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("Medicina T");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("The medicine form is shown again with the errors")
	public void logout() throws Exception {
		assertEquals("no puede estar vacío", driver.findElement(By.xpath("//form[@id='medicine']/div/div[2]/div/span[2]")).getText());
		stopDriver();
	}
}
