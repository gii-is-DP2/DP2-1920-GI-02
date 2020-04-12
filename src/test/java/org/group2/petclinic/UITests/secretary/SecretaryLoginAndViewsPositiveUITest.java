
package org.group2.petclinic.UITests.secretary;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecretaryLoginAndViewsPositiveUITest {

	@LocalServerPort
	private int				port;

	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		String pathToChromeDriver = "C:\\Users\\CLAUDIA\\Desktop\\Universidad\\5º año\\2 cuatrimestre\\DP";
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver + "\\chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testUntitledTestCase() throws Exception {
		loginSecretary(driver, port);
		showVisitsSecretaryView();
		logoutSecretary();
	}

	public static void loginSecretary(WebDriver driver, int port) {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("secretary2");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("secretary2");
		driver.findElement(By.cssSelector("button.btn.btn-lg.btn-primary.btn-block")).click();
		assertEquals("SECRETARY2", driver.findElement(By.cssSelector("a.dropdown-toggle")).getText());
	}

	private void showVisitsSecretaryView() {
		driver.findElement(By.xpath("//li[2]/a/span[2]")).click();

		assertEquals("Visits without payment", driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Moment", driver.findElement(By.xpath("//th")).getText());
		assertEquals("Description", driver.findElement(By.xpath("//th[2]")).getText());
		assertEquals("Pet", driver.findElement(By.xpath("//th[3]")).getText());
		assertEquals("Do payment", driver.findElement(By.xpath("//th[4]")).getText());
		driver.findElement(By.cssSelector("a[title=\"home page\"]")).click();
	}

	private void logoutSecretary() {
		driver.findElement(By.xpath("//ul[2]/li/a")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
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
