
package org.group2.petclinic.UITests.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminLoginAndViewsPositiveUITest {

	@LocalServerPort
	private int				port;

	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		driver = new ChromeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testUntitledTestCase() throws Exception {
		loginAdmin(driver, port);
		showEstadisticView();
		logoutAdmin();
	}

	public static void loginAdmin(WebDriver driver, int port) {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("admin1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button")).click();

		assertEquals("ADMIN1", driver.findElement(By.xpath("//a[contains(@href, '#')]")).getText());
	}

	private void showEstadisticView() {
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

	private void logoutAdmin() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a/span[2]")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		driver.findElement(By.cssSelector("button.btn.btn-lg.btn-primary.btn-block")).click();
		assertEquals("LOGIN", driver.findElement(By.cssSelector("ul.nav.navbar-nav.navbar-right > li > a")).getText());
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
