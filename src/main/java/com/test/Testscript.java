package com.test;



import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;



public class Testscript  extends utilityclass{
	
 // Find your Account Sid and Token at twilio.com/console
	
 public static String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");  //set the twilio account sid in system environment variable
 public static String AUTH_TOKEN =  System.getenv("TWILIO_AUTH_TOKEN");   //set the twilio auth token in system environment variable

 
 static DateTimeFormatter globalFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a z");
 static DateTimeFormatter etFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a '(EST)'");
  
 static ZoneId istZoneId = ZoneId.of("Asia/Kolkata");	
 static ZoneId etZoneId = ZoneId.of("America/New_York");
 
 
 @Test
 public static void login() throws InterruptedException, URISyntaxException {
	 
	 logger1 = extent.createTest("IncomingCalls");   

		//launching browser and logging in
	 
		launchBrowser();
		driver.get(properties.getProperty("loginURL"));
		driver.manage().window().maximize();
		
		type("userName_Xpath","duserName");
		logger1.info("UserName is entered in the text box");
		
		
		type("password_Xpath","dpassword");
		logger1.info("Password is entered in the text box");
		
		
		click("login_Xpath");
		logger1.info("Log In button is clicked");
		
		Thread.sleep(2000);
			
		click("dialogtechCallsTab_Xpath");
		
		 WebDriverWait wait = new WebDriverWait(driver, 60);       //timeout in Seconds
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SoftphoneIframe")));
		 
			try {
				WebElement iFrame= driver.findElement(By.id("SoftphoneIframe"));
				driver.switchTo().frame(iFrame);
			    Thread.sleep(2000);
				System.out.println("Switched to iframe");
			      
				   
			    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='agent_id']")));  

			    	//logging into agent panel
				
					type("agentId_Xpath","agentId");
					logger1.info("Agent ID is entered in the text box");
					
					type("agentPassword_Xpath","agentPassword");
					logger1.info("Agent Password is entered in the text box");
					
					click("agentLoginButton_Xpath");
					logger1.info("Agent Login button is clicked");
					System.out.println("Agent entered");
					
					Thread.sleep(5000);
					driver.navigate().refresh();
					System.out.println("Page refreshed");
					
					WebElement iFrame1= driver.findElement(By.id("SoftphoneIframe"));
					driver.switchTo().frame(iFrame1);
                    System.out.println("Frame switched and looking for number entry field");
                    
					
					WebDriverWait wait1 = new WebDriverWait(driver, 60);      //timeout in Seconds
					wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='number_entry']")));
					
					//changing the mode to Open status and store date of inbound calling in a variable
					
					Actions actions = new Actions(driver);
					WebElement mainMode = driver.findElement(By.xpath("//div[@id='mode']"));
					actions.moveToElement(mainMode);

					WebElement subMode = driver.findElement(By.xpath("//div[@id='mode_menu']//li[text()='OPEN']"));
					actions.moveToElement(subMode);
					actions.click().build().perform();
					
					calling();
					
					 //converting time from IST to EST and using those to check the call logs
					
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
				    	
				     WebDriverWait wait2 = new WebDriverWait(driver, 20);
				     WebElement call_log= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Call to Unknown Recipient on "+estTime+"']")));
				   
				 //   WebElement call_log= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Call to Unknown Recipient on December 24, 2019 at 2:13 AM (EST)']")));
				     
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
				
				
				catch(NoSuchElementException e)
				{ 
					
					logger1.fail(e);
					driver.close();
				}
 }
 
 
 

 public static void calling() throws URISyntaxException {
     Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

     String from = properties.getProperty("fromNumber");      
     String to =   properties.getProperty("toNumber");        

     Call call = Call.creator(new PhoneNumber(to), new PhoneNumber(from),
             new URI("http://demo.twilio.com/docs/voice.xml")).create();

     System.out.println(call.getSid());
 }
 
 
 public static void checkObject() throws InterruptedException
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
	



