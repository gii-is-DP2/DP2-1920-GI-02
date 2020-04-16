
package org.group2.petclinic.UITests.secretary;

import java.util.regex.Pattern;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoPaymentCreditcardNegativeUITest {

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
		SecretaryLoginAndViewsPositiveUITest.loginSecretary(driver, port);

		driver.findElement(By.cssSelector("a[title=\"visits\"]")).click();

		//int numVisitsInicial = numVisits();

		driver.findElement(By.linkText("Add Payment")).click();
		assertEquals("New Payment", driver.findElement(By.xpath("//h2")).getText());
		driver.findElement(By.id("finalPrice")).click();
		driver.findElement(By.id("finalPrice")).clear();
		driver.findElement(By.id("finalPrice")).sendKeys("30.00");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.id("holder")).clear();
		driver.findElement(By.id("holder")).sendKeys("Maria");
		driver.findElement(By.id("brand")).clear();
		driver.findElement(By.id("brand")).sendKeys("Visa");
		driver.findElement(By.id("number")).clear();
		driver.findElement(By.id("number")).sendKeys("1122334455667788");
		driver.findElement(By.id("expYear")).clear();
		driver.findElement(By.id("expYear")).sendKeys("15");
		driver.findElement(By.id("securityCode")).clear();
		driver.findElement(By.id("securityCode")).sendKeys("aa22");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Number is not valid.", driver.findElement(By.xpath("//form[@id='creditcard']/div/div[3]/div/span[2]")).getText());
		assertEquals("Expirated date.", driver.findElement(By.xpath("//form[@id='creditcard']/div/div[5]/div/span[2]")).getText());
		assertEquals("Security code must be a int with 3 or 4 digits.", driver.findElement(By.xpath("//form[@id='creditcard']/div/div[6]/div/span[2]")).getText());
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();

		//assertTrue(numVisits() == numVisitsInicial);
	}

	private int numVisits() {
		WebElement tabla = driver.findElement(By.xpath("//table[1]"));
		List<WebElement> filasDeTabla = tabla.findElements(By.tagName("tr"));
		return filasDeTabla.size();
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
