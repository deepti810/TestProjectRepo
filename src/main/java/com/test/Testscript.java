package com.test;

import org.testng.annotations.Test;

public class Testscript extends utilityclass {
	@Test
	public void print() {
		
		launchBrowser();
		System.out.println("browser launched");
		driver.get("www.myntra.com");
		
		System.out.println("url opened");
	}
	
}
	



