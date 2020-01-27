package com.test;

import java.io.IOException;

import org.testng.annotations.Test;

public class Testscript extends utilityclass {
	@Test(priority=1)
	public void print() throws IOException {
		
		launchBrowser();
		System.out.println("browser launched");
		driver.get("https://login.salesforce.com/");
		
		System.out.println("url opened");
		capture(driver,"login");
	}
	
	@Test(priority=2)
	public void testing() throws IOException 
	{
		launchBrowser();
		driver.get("https://www.browserstack.com/guide/take-screenshots-in-selenium");
		capture(driver,"browserstack");
	}
}
	



