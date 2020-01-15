package com.test;



import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;



public class Testscript  extends utilityclass{

    
    static DateTimeFormatter globalFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a z");
    static DateTimeFormatter etFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a '(EST)'");
     
    static ZoneId istZoneId = ZoneId.of("Asia/Kolkata");	
    static ZoneId etZoneId = ZoneId.of("America/New_York");
	
    
    //test script class
	
	@Test
	public void openBrowser() throws InterruptedException  {
		createReport();
		logger1 = extent.createTest("OutgoingCalls");

		
		launchBrowser();
		System.out.println("browser launched");
		
		driver.get(properties.getProperty("loginURL"));
		driver.manage().window().maximize();
		
		
		type("userName_Xpath","duserName");
		logger1.info("UserName is entered in the text box");
		System.out.println("UserName is entered");
		
		
		type("password_Xpath","dpassword");
		logger1.info("Password is entered in the text box");
		System.out.println("Password is entered");
		
		
		click("login_Xpath");
		logger1.info("Log In button is clicked");
		System.out.println("Log In button is clicked");
		
		Thread.sleep(2000);
			
		click("dialogtechCallsTab_Xpath");
		System.out.println("Clicked on dialogtech calls object");
		
		 WebDriverWait wait = new WebDriverWait(driver, 60);       //timeout in Seconds
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SoftphoneIframe")));
		
		
		try {
		WebElement iFrame= driver.findElement(By.id("SoftphoneIframe"));
		driver.switchTo().frame(iFrame);
	    Thread.sleep(2000);
		System.out.println("Switched to iframe");
	      
		   
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='agent_id']")));  

			type("agentId_Xpath","agentId");
			logger1.info("Agent ID is entered in the text box");
			
			type("agentPassword_Xpath","agentPassword");
			logger1.info("Agent Password is entered in the text box");
			
			click("agentLoginButton_Xpath");
			logger1.info("Login button is clicked");
			System.out.println("Agent logged in");
			
			
			performCalling();
			
			
		}
		
		
		catch(Exception e)
		{ 
			
			logger1.fail(e);
			driver.close();
		}
		
		}
		
		public void performCalling() throws InterruptedException
		{
			
			System.out.println("Looking for number entry field");
			Thread.sleep(5000);
			driver.navigate().refresh();
			WebElement iFrame= driver.findElement(By.id("SoftphoneIframe"));
			driver.switchTo().frame(iFrame);
			
		    WebDriverWait wait = new WebDriverWait(driver, 90);      //timeout in Seconds
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='number_entry']")));
			
			Actions actions = new Actions(driver);
			WebElement mainMode = driver.findElement(By.xpath("//div[@id='mode']"));
			actions.moveToElement(mainMode);

			WebElement subMode = driver.findElement(By.xpath("//div[@id='mode_menu']//li[text()='OPEN']"));
			actions.moveToElement(subMode);
			actions.click().build().perform();
			
			type("numberEntry_Xpath","number");
			logger1.info("Number is entered");
			System.out.println("number is entered");
			
			
			
			click("clickToCall_Xpath");
			logger1.info("Click to Call");
			System.out.println("click to call");
			
			Thread.sleep(3000);
		    //driver.switchTo().defaultContent();
			
		    //converting time from IST to EST and using those
	        LocalDateTime currentDateTime = LocalDateTime.now();
	         
	        ZonedDateTime currentISTime = currentDateTime.atZone(istZoneId);                //India Time
	        ZonedDateTime currentETime = currentISTime.withZoneSameInstant(etZoneId);       //EST Time
	         
	        System.out.println(globalFormat.format(currentISTime));
	        System.out.println(etFormat.format(currentETime));
	        
	        String estTime=etFormat.format(currentETime);
			
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='OPEN']")));
	        
	        
			driver.navigate().refresh();
			Thread.sleep(20000);   //Sleeping for 20 Sec to wait for call records to be generated
			driver.navigate().refresh();


		    try {
		    	
		     WebDriverWait wait1 = new WebDriverWait(driver, 20);
		     WebElement call_log= wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Call to Unknown Recipient on "+estTime+"']")));
		   
		   // WebElement call_log= wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Call to Unknown Recipient on December 24, 2019 at 2:13 AM (EST)']")));
		     
		     call_log.click();
		  
		   System.out.println("Clicked on Call log");
		   logger1.info("Call Record Found");
		   System.out.println("Call record found");
		   
		   checkObject();      //Calling checkObject method to verify whether the value is displayed in the field or not!!
			
		    }
		    catch(Exception e1)
		    {
		    	logger1.info("Call Record not generated within expected time");
		    	System.out.println("Call Record not generated within expected time");
		    	
		    	logger1.fail(e1);
		    	
				driver.close();
		    	
		    	
		    }
			
		}
		
		
		public void checkObject() throws InterruptedException
		{
			    Actions action = new Actions(driver);
			
				WebElement ele =driver.findElement(By.xpath("//div[@id='00N2v00000UyzlO_ileinner']"));
				
			    action.doubleClick(ele).build().perform();
			    Thread.sleep(2000);
			    
				 String transferValue=driver.findElement(By.xpath("//div//input[@id='00N2v00000UyzlO']")).getAttribute("value");
				    if(transferValue.isEmpty())
				    {
				    	logger1.info("Value at 'Transfer to Number' object is not displayed");
				    	System.out.println("Value at 'Transfer to Number' object is not displayed");
				    	driver.close();
				    }
				    else 
				    {
					logger1.info("'Transfer to Number' Value for outboung calling is "+transferValue+".");
					
					driver.close();
				    }
			
		
	}
}
	



