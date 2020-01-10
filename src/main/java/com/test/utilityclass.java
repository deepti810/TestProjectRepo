package com.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

//this is a utility class
public class utilityclass {
	
	
	public static WebDriver driver;
	
	public static WebDriver launchChromeBrowser()
	{
		//System.setProperty("webdriver.chrome.driver", "D:\\Drivers\\chromedriver.exe");
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
	 //   driver= new ChromeDriver();
		return driver;
		//driver.manage().window().maximize();
		
		
	}
	
	public static void launchFirefoxBrowser()
		{
			System.setProperty("webdriver.gecko.driver", "D:\\Drivers\\geckodriver.exe");
			driver= new FirefoxDriver();
			
		}
		
public static Properties prop;
	
	static{
		InputStream input = null;
		try {
			input = new FileInputStream("resources/xpaths_and_data.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 prop = new Properties();
		try {
			prop.load(input);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
	}
	

