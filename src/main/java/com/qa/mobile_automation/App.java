package com.qa.mobile_automation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		try {
			// Appium Intialization
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("platformName", "Android");
			cap.setCapability("automationName", "UiAutomator2");
			cap.setCapability("udid", "emulator-5554");
			cap.setCapability("platformName", "Android");
			cap.setCapability("appPackage", "com.airasia.mobile.debug");
			cap.setCapability("appActivity", "com.airasia.mobile.MainActivity2");
			cap.setCapability("unicodeKeyboard", true);
			cap.setCapability("noSign", true);
			System.out.println("Android Application is running. . .!");

			URL url = new URL("http://127.0.0.1:4723/wd/hub");

			AppiumDriver<io.appium.java_client.MobileElement> driver = new AppiumDriver<io.appium.java_client.MobileElement>(
					url, cap);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			Wait<AppiumDriver<io.appium.java_client.MobileElement>> wait = new FluentWait<AppiumDriver<io.appium.java_client.MobileElement>>(
					driver).withTimeout(30, TimeUnit.SECONDS);
			
			

			// Clicking the Maybe Later Button
			driver.findElement(By.id("com.airasia.mobile.debug:id/button2")).click();
			// Clicking the Show all Category
			driver.findElement(By.id("android:id/content")).click();
			// Clicking the Beauty
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Beauty']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Beauty']")).click();

			// After waiting to add to cart I want to show how many add to cart button
			// appeared and then click the first add to cart			
			addToCart(driver);

			// Checking for WebView and Switch to WebView
			Set<String> contextNames = driver.getContextHandles();

			for (String contextName : contextNames) {
				System.out.println(contextName);
				if (contextName.toLowerCase().contains("webview")) {
					driver.context(contextName);
					break;
				}
			}

			int inputTagSize = driver.findElements(By.tagName("input")).size();
			System.out.println("inputTagSize: " + inputTagSize);
			driver.findElements(By.tagName("input")).get(0).sendKeys("bigset16acc.1@gmail.com");
			driver.findElements(By.tagName("input")).get(1).sendKeys("P@55w0rd!23");
			driver.findElements(By.tagName("input")).get(2).click();

			driver.context("NATIVE_APP");
			
			addToCart(driver);
			
			
			Dimension windowSize = driver.manage().window().getSize();
			int start_x = (int) (windowSize.width * 0.50);
			System.out.println("windowSize.height: " + windowSize.height);
			int start_y = (int) (windowSize.height* 0.19);
			System.out.println("start_x: " + start_x);
			System.out.println("start_y: " + start_y);
			

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(start_x, start_y)).release().perform();
			Thread.sleep(1000);
			
			//Click Add to Cart button
			addToCart(driver);
			
			//Click Add to cart header icon
			driver.findElements(By.xpath("//android.widget.Image[@index=0]")).get(0).click();
			Thread.sleep(5000);
			
			
			
			//Click Select Button for Delivery Date
			int selectButtons = driver.findElements(By.xpath("//android.widget.Button[@text='Select']")).size();
			System.out.println("Select Buttons: " + selectButtons);
			driver.findElement(By.xpath("//android.widget.Button[@text='Select']")).click();
			
			
			//Click Confirm Delivery Date
			driver.findElement(By.xpath("//android.widget.TextView[@text='Confirm']")).click();
			
			//Click Continue to pay
			driver.findElement(By.xpath("//android.widget.TextView[@text='Continue to pay']")).click();
			
			Thread.sleep(10000);
			
			

		} catch (Exception e) {
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
	

	public static void addToCart(AppiumDriver driver) throws InterruptedException {
		
		Wait<AppiumDriver<io.appium.java_client.MobileElement>> wait = new FluentWait<AppiumDriver<io.appium.java_client.MobileElement>>(
				driver).withTimeout(30, TimeUnit.SECONDS);
		//Click Add to Cart button
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.view.View[@content-desc='Add to cart']")));
		int addToCartCount = driver.findElements(By.xpath("//android.view.View[@content-desc='Add to cart']"))
				.size();
		System.out.println("addToCartCount: " + addToCartCount);
		((RemoteWebElement) driver.findElements(By.xpath("//android.view.View[@content-desc='Add to cart']")).get(0)).click();
		Thread.sleep(5000);
	}

}
