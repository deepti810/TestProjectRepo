package com.test;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Testscript {
	public static WebDriver driver;
	public static ExtentHtmlReporter reporter;
    public static ExtentReports extent;
    public static ExtentTest logger;
    
    //test script class
	
	@Test
	public void openBrowser() throws InterruptedException  {
		
		reporter= new ExtentHtmlReporter("./Reports/extent.html");
		extent=new ExtentReports();
		extent.attachReporter(reporter);
		logger=extent.createTest("sellingprofile");
		
		
		driver= utilityclass.launchChromeBrowser();		
		logger.log(Status.PASS, "browser launched");
		System.out.println("Browser launched in chrome");
	
		driver.navigate().to(utilityclass.prop.getProperty("sellingUrl"));
		logger.log(Status.PASS, "url opened");
		System.out.println("frontlineselling URL opened");
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("rajesh4321!");
		System.out.println("email entered");
		
/*		WebElement linkProfile=driver.findElement(By.xpath(utilityclass.prop.getProperty("profile_xpath")));
		logger.log(Status.PASS, "moved to profile");
		
		Actions builder= new Actions(driver);
		Action mouseoverprofile=builder.moveToElement(linkProfile).build();
		
		mouseoverprofile.perform();
		Thread.sleep(1000);
		
		WebElement login=driver.findElement(By.xpath(utilityclass.prop.getProperty("login_xpath")));
		
		login.click();
		logger.log(Status.PASS, "clicked on login"); */
		System.out.println("yes");
		extent.flush();
		
	}
}
	



