package steps;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.github.sukgu.Shadow;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StepDefinition {

	public WebDriver driver;
	
	
	
		@Given("Open the browser")
		public void openBrowser() {
			System.setProperty(
		            "webdriver.chrome.driver",
		            "C:\\TestLeaf\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.get("http://www.automationpractice.pl/index.php");
			driver.manage().window().maximize();
			
	//		ChromeDriver driver = new ChromeDriver();
		}
		
		@Given("Create New Account (.*) (.*) (.*)$")
		public void createNewAccount(String emailID, String firstName, String lastName) throws InterruptedException {

			String title = driver.getTitle();
			System.out.println("title-"+title);
			if (title.contains("My Shop")) {
				System.out.println("Homepage is displayed");
			} else {
				System.out.println("Homepage is not displayed");
			}
				driver.findElement(By.xpath("//a[@title=\"Log in to your customer account\"]")).click();
				String authentication =driver.findElement(By.xpath("//h1[text()=\"Authentication\"]")).getText();
				if (authentication.contains("Authentication")) {
					System.out.println("Authentication page is displayed");
				} else {
					System.out.println("Authentication page is not displayed");
				}
				

				driver.findElement(By.xpath("//input[@id=\"email_create\"]")).sendKeys(emailID);
				driver.findElement(By.xpath("//button[@id=\"SubmitCreate\"]")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.xpath("//input[@id=\"customer_firstname\"]")).sendKeys(firstName);
				driver.findElement(By.xpath("//input[@id=\"customer_lastname\"]")).sendKeys(lastName);
				String email=driver.findElement(By.xpath("//input[@id=\"email\"]")).getAttribute("value");
				if (email.contains(emailID)) {
					System.out.println("Correct email id is displayed");
				} else {
					System.out.println("Correct email id is not displayed");
				}
				driver.findElement(By.xpath("//input[@id=\"passwd\"]")).sendKeys("Test1234");
				driver.findElement(By.xpath("//button[@id=\"submitAccount\"]")).click();
				
				String successMessage=driver.findElement(By.xpath("//p[contains(text(),\"Your account has been created\")]")).getText();
				if (successMessage.contains("Your account has been created")) {
					System.out.println("Account is created successfully");
				} else {
					System.out.println("Account is not created");
				}
				driver.findElement(By.xpath("//a[@title=\"Log me out\"]")).click();
				
		}
		
		@Given("Login to the application (.*)$")
		public void loginFunction(String emailID) {

			driver.findElement(By.xpath("//a[@title=\"Log in to your customer account\"]")).click();
			driver.findElement(By.xpath("//input[@id=\"email\"]")).sendKeys(emailID);
			driver.findElement(By.xpath("//input[@id=\"passwd\"]")).sendKeys("Test1234");
			driver.findElement(By.xpath("//button[@id=\"SubmitLogin\"]")).click();
			System.out.println("Login Successfull!");
		}
	
		
		
		@And("Tab Validation")
		public void tabValidation() {
	
			String[] tabs = {"ADD MY FIRST ADDRESS","ORDER HISTORY AND DETAILS","MY CREDIT SLIPS","MY ADDRESSES","MY PERSONAL INFORMATION","testtab"};
			List<WebElement> myAccountTabs = driver.findElements(By.className("myaccount-link-list"));
			for(WebElement each:myAccountTabs)
			{
				String tabName = each.getText();
				for (int i = 0; i < tabs.length; i++) {
						if (tabName.contains(tabs[i])) {
					
						System.out.println("Tab- "+tabs[i]+" is available");
					}
						else {
							System.out.println("Tab- "+tabs[i]+" is not available");	
						}
				}
				
			}
	
			
			driver.findElement(By.xpath("//a[@title=\"Home\"]")).click();
		}
		
		@And("Find the list of products")
		public void getListOfProdutcs() {
			
			List<WebElement> homepageTabs = driver.findElements(By.xpath("//ul[contains(@class,\"menu-content\")]"));
			System.out.println("Home Page Tabs");
			System.out.println("--------------");
			for(WebElement each:homepageTabs)
			{
				String hometabName = each.getText();
				System.out.println(hometabName);
	
			}
			
			driver.findElement(By.id("search_query_top")).sendKeys("dress", Keys.ENTER);
			List<WebElement> products = driver.findElements(By.xpath("//div[@class=\"right-block\"]/h5/a[@class=\"product-name\"]"));
			int noOfproducts =products.size();
			System.out.println("No of products:"+noOfproducts);
			System.out.println("List of products:");
			System.out.println("-----------------");

			for(WebElement each:products)
			{
				String productName = each.getText();
				System.out.println(productName);
				
			}
			
//			List<WebElement> productsAvailable = driver.findElements(By.xpath("//span[@class=\"available-dif\"]"));
			List<WebElement> productsAvailable = driver.findElements(By.xpath("//span[@class=\"available-dif\"]/preceding::div[@class=\"right-block\"]/h5/a[@class=\"product-name\"]"));

			int noOfproductsAvailable =productsAvailable.size();
			System.out.println("No of products In Stock:"+noOfproductsAvailable);
			System.out.println("List of products In Stock:");
			System.out.println("-----------------");
			for(WebElement each:productsAvailable)
			{
				String productName = each.getText();
				System.out.println(productName);
				
			}
				
		}
		
		@And("Find the lowest price")
		public void getLowestPrice() {
			
			driver.findElement(By.id("search_query_top")).clear();
			driver.findElement(By.id("search_query_top")).sendKeys("dress", Keys.ENTER);
			List<WebElement> productPrice = driver.findElements(By.xpath("//div[@class=\"right-block\"]/div/span[@class=\"price product-price\"]"));
			List<Integer> list = new ArrayList<Integer>();
			System.out.println("List of products prices:");
			System.out.println("-----------------");
			
			for (WebElement each : productPrice) {
				String pricevalue = each.getText();
				System.out.println(pricevalue);
				String price = pricevalue.substring(1);
				if (!price.isEmpty()) {
					int salesAmt = Integer.parseInt(price);
					list.add(salesAmt);
						
				}
				
			}
			
			Collections.sort(list);
			int minVal = list.get(0);
			System.out.println("The min price is : $"+minVal);
			
				
		}
		
		@And("Add to the Cart")
		public void addToCart() throws InterruptedException  {
			driver.findElement(By.xpath("//a[@title=\"Women\"]")).click();
			driver.findElement(By.xpath("//a[@title=\"Faded Short Sleeve T-shirts\"]/img")).click();

			
			driver.switchTo().frame(0);
			driver.findElement(By.xpath("//a[@name=\"Orange\"]")).click();
			WebElement dressSize = driver.findElement(By.xpath("//select[@id=\"group_1\"]"));
			Select sizeSelect = new Select(dressSize);
			sizeSelect.selectByVisibleText("S");
			String availability = driver.findElement(By.xpath("//span[@id=\"availability_value\"]")).getText();
			if(availability.contains("In stock")) {
				System.out.println("Faded Short Sleeve T-shirts - Orange, Size-S : Stock is available");
			}
			else {
				System.out.println("Faded Short Sleeve T-shirts - Orange, Size-S : Stock is not available");
			}
			
			driver.findElement(By.xpath("//a[@name=\"Blue\"]")).click();
			sizeSelect.selectByVisibleText("M");
			availability = driver.findElement(By.xpath("//span[@id=\"availability_value\"]")).getText();
			if(availability.contains("In stock")) {
				System.out.println("Faded Short Sleeve T-shirts - Blue, Size-M : Stock is available");
			}
			else {
				System.out.println("Faded Short Sleeve T-shirts - Blue, Size-M : Stock is not available");
			}

			driver.findElement(By.xpath("//span[text()=\"Add to cart\"]")).click();
			Shadow dom = new Shadow(driver);
			dom.setImplicitWait(10);
			//WebElement sample = dom.findElementByXPath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a/span");
			WebElement one = dom.findElementByXPath("//span[contains(text(),\"Proceed to checkout\")]");
			one.click();
//			WebElement trialHead = dom.findElementByXPath("//h2/i/following-sibling::text()");
//			Actions builder = new Actions(driver);
//			builder.scrollToElement(trialHead).moveToElement(trialHead).perform();
			
//			Thread.sleep(3000);
//			//*[@id="layer_cart"]/div[1]/div[1]/h2/text()
//			//h2/i/following-sibling::text()
//			driver.findElement(By.xpath("//a[@title=\"Proceed to checkout\"]/span")).click();
//
//			String addToCartText=driver.findElement(By.xpath("//a[@title=\"Proceed to checkout\"]/span")).getText();
//			if(addToCartText.contains("Proceed to checkout")) {
//				System.out.println(addToCartText);
//			}
//			else {
//				System.out.println("Product is not added to zour shopping cart");
//			}
		}
	
		@And("Enter the Automation Practice URL")
		public void loadURL() throws InterruptedException {
			driver.get("http://www.automationpractice.pl/index.php");
			driver.manage().window().maximize();
			String title = driver.getTitle();
			//String title = driver.findElement(By.xpath("//a[@title='My Shop']")).gett
			System.out.println("title-"+title);
			if (title.contains("My Shop")) {
				System.out.println("Homepage is displayed");
			} else {
				System.out.println("Homepage is not displayed");
			}
				driver.findElement(By.xpath("//a[@title=\"Log in to your customer account\"]")).click();
				String authentication =driver.findElement(By.xpath("//h1[text()=\"Authentication\"]")).getText();
				if (authentication.contains("Authentication")) {
					System.out.println("Authentication page is displayed");
				} else {
					System.out.println("Authentication page is not displayed");
				}
				
		//Registration
				String emailID = "TestInt1234e@quipuinterview.com";
//				driver.findElement(By.xpath("//input[@id=\"email_create\"]")).sendKeys(emailID);
//				driver.findElement(By.xpath("//button[@id=\"SubmitCreate\"]")).click();
//				Thread.sleep(1000);
//				
//				driver.findElement(By.xpath("//input[@id=\"customer_firstname\"]")).sendKeys("John");
//				driver.findElement(By.xpath("//input[@id=\"customer_lastname\"]")).sendKeys("Tom");
//				String email=driver.findElement(By.xpath("//input[@id=\"email\"]")).getAttribute("value");
//				if (email.contains(emailID)) {
//					System.out.println("Correct email id is displayed");
//				} else {
//					System.out.println("Correct email id is not displayed");
//				}
//				driver.findElement(By.xpath("//input[@id=\"passwd\"]")).sendKeys("Test1234");
//				driver.findElement(By.xpath("//button[@id=\"submitAccount\"]")).click();
//				
//				String successMessage=driver.findElement(By.xpath("//p[contains(text(),\"Your account has been created\")]")).getText();
//				if (successMessage.contains("Your account has been created")) {
//					System.out.println("Account is created successfully");
//				} else {
//					System.out.println("Account is not created");
//				}
//				driver.findElement(By.xpath("//a[@title=\"Log me out\"]")).click();
//				
		//Login
				driver.findElement(By.xpath("//input[@id=\"email\"]")).sendKeys(emailID);
				driver.findElement(By.xpath("//input[@id=\"passwd\"]")).sendKeys("Test1234");
				driver.findElement(By.xpath("//button[@id=\"SubmitLogin\"]")).click();
				
		//Tab Validation
						
//				String[] tabs = {"ADD MY FIRST ADDRESS","ORDER HISTORY AND DETAILS","MY CREDIT SLIPS","MY ADDRESSES","MY PERSONAL INFORMATION","testtab"};
//				List<WebElement> myAccountTabs = driver.findElements(By.className("myaccount-link-list"));
//				for(WebElement each:myAccountTabs)
//				{
//					String tabName = each.getText();
//					for (int i = 0; i < tabs.length; i++) {
//							if (tabName.contains(tabs[i])) {
//						
//							System.out.println("Tab- "+tabs[i]+" is available");
//						}
//							else {
//								System.out.println("Tab- "+tabs[i]+" is not available");	
//							}
//					}
//					
//				}
//		
				
				driver.findElement(By.xpath("//a[@title=\"Home\"]")).click();
		
		
		//Browse Functionality
				List<WebElement> homepageTabs = driver.findElements(By.xpath("//ul[contains(@class,\"menu-content\")]"));
				for(WebElement each:homepageTabs)
				{
					String hometabName = each.getText();
					System.out.println(hometabName);
		
				}
				driver.findElement(By.xpath("//a[@title=\"Women\"]")).click();
				driver.findElement(By.xpath("//a[@title=\"Faded Short Sleeve T-shirts\"]/img")).click();
	
				
				driver.switchTo().frame(0);
				driver.findElement(By.xpath("//a[@name=\"Orange\"]")).click();
				WebElement dressSize = driver.findElement(By.xpath("//select[@id=\"group_1\"]"));
				Select sizeSelect = new Select(dressSize);
				sizeSelect.selectByVisibleText("S");
				String availability = driver.findElement(By.xpath("//span[@id=\"availability_value\"]")).getText();
				if(availability.contains("In stock")) {
					System.out.println("Faded Short Sleeve T-shirts - Orange, Size-S : Stock is available");
				}
				else {
					System.out.println("Faded Short Sleeve T-shirts - Orange, Size-S : Stock is not available");
				}
				
				driver.findElement(By.xpath("//a[@name=\"Blue\"]")).click();
				sizeSelect.selectByVisibleText("M");
				availability = driver.findElement(By.xpath("//span[@id=\"availability_value\"]")).getText();
				if(availability.contains("In stock")) {
					System.out.println("Faded Short Sleeve T-shirts - Blue, Size-M : Stock is available");
				}
				else {
					System.out.println("Faded Short Sleeve T-shirts - Blue, Size-M : Stock is not available");
				}
		}
}
