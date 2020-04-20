
package org.group2.petclinic.UITests.owner.steps;

import static org.junit.Assert.assertEquals;

import org.group2.petclinic.UITests.AbstractStep;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@ExtendWith(SpringExtension.class)
public class OwnerScheduleVisitPositiveUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();


	@Given("that I am logged in as an owner")
	public void logInAsOwner() {
		LoginOwnerPositiveUITest.loginOwner("gfranklin", "gfranklin", driver, port);
	}

	@When("I schedule a correct visit")
	public void scheduleCorrectVisit() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
		driver.findElement(By.id("description")).click();
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("descr");
		driver.findElement(By.id("moment")).click();
		driver.findElement(By.id("moment")).clear();
		driver.findElement(By.id("moment")).sendKeys("2021/01/11 13:00");
		driver.findElement(By.id("visit")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("the visit shows up in my schedule")
	public void checkVisitInSchedule() throws Exception {

		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[5]/a/span[2]")).click();
		assertEquals("descr",
			driver.findElement(By.xpath("//table[@id='visitsTable']/tbody/tr[2]/td[2]")).getText());
		logout();
	}

	public void logout() throws Exception {
		driver.findElement(By.xpath("//ul[2]/li/a")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("LOGIN",
			driver.findElement(By.cssSelector("ul.nav.navbar-nav.navbar-right > li > a")).getText());
		stopDriver();
	}

}
