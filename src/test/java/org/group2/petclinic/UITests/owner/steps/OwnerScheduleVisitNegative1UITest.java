
package org.group2.petclinic.UITests.owner.steps;

import static org.junit.Assert.assertEquals;

import org.group2.petclinic.UITests.AbstractStep;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@ExtendWith(SpringExtension.class)
public class OwnerScheduleVisitNegative1UITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();


	@When("I attempt to schedule a visit in the past")
	public void scheduleVisitInPast() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
		driver.findElement(By.id("description")).click();
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("descr");
		driver.findElement(By.id("moment")).click();
		driver.findElement(By.id("moment")).clear();
		driver.findElement(By.id("moment")).sendKeys("2020/04/13 13:00");
		driver.findElement(By.xpath("//body/div/div")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("an error message is shown indicating that visits cannot be in the past.")
	public void checkVisitInSchedule() throws Exception {
		assertEquals("Visit cannot be in the past.",
			driver.findElement(By.xpath("//form[@id='visit']/div/div[5]/div/span[2]")).getText());
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
