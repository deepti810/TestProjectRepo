package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.FileUtil;

//this is a utility class
public class utilityclass {
	
	
	public static WebDriver driver;
	public static ExtentHtmlReporter reporter;
	public static ExtentReports extent;
	public static ExtentTest logger1;
	
public static void launchBrowser()
	
	{
		   //    System.setProperty("webdriver.chrome.driver", "D:\\Drivers\\chromedriver.exe");
	        //   driver = new ChromeDriver();
	
		        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
		         ChromeOptions options=new ChromeOptions();
				 options.addArguments("start-maximized"); // open Browser in maximized mode
				 options.addArguments("--headless");
				 options.addArguments("disable-infobars"); // disabling infobars
				 options.addArguments("--disable-extensions"); // disabling extensions
				 options.addArguments("--disable-gpu"); // applicable to windows os only
				 options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
				 options.addArguments("--no-sandbox"); // Bypass OS security model
				 driver = new ChromeDriver(options);  
				 
			
				 
				 System.out.println("Browser is initiated");
	
	}
	
	public static void type(String Locator, String key) {
		if (Locator.endsWith("_Xpath")) {
			driver.findElement(By.xpath(properties.getProperty(Locator))).sendKeys(properties.getProperty(key));

		} else if (Locator.endsWith("_ID")) {
			driver.findElement(By.id(properties.getProperty(Locator))).sendKeys(properties.getProperty(key));
		}
	}

	public static void click(String Locator) {
		if (Locator.endsWith("_Xpath")) {
			driver.findElement(By.xpath(properties.getProperty(Locator))).click();

		} else if (Locator.endsWith("_ID")) {
			driver.findElement(By.id(properties.getProperty(Locator))).click();
			

		}
	}

	public static String getObject(String Data) throws IOException {
		String data = properties.getProperty(Data);
		return data;

	}
	
	public static String getPropertyValue(String key) {

		return properties.getProperty(key);
}

	public static String capture(WebDriver driver, String path) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		
	   String dest = System.getProperty("user.dir")+"/Screenshots/"+path+"/"+"screenshot.png";
		System.out.println("ScreenShot Destination : "+dest);
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);

		return dest;
	}
		
public static Properties properties;
	
	static{
		InputStream input = null;
		try {
			input = new FileInputStream("resources/xpaths_and_data.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 properties = new Properties();
		try {
			properties.load(input);
			
		} catch (IOException e) {
			e.printStackTrace();
	
		}

	
	
	}	
	
	@BeforeTest
	public void createReport() {
		reporter = new ExtentHtmlReporter("./extent.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}

	@AfterTest
	public void flush() throws IOException {
		extent.flush();
		}
	
	}
	

