package tatoc;
import java.io.File;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TatocTest {
	WebDriver driver;
	String mainWindow;
	String expectedWebpageUrl;
	WebElement element;
	@BeforeClass
	  public void beforeClass() {
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+"chromedriver");
        driver = new ChromeDriver();
		 	driver.get("http://10.0.1.86//tatoc");
		 	mainWindow = driver.getWindowHandle();}

	@Test
  public void basicCource() {
		 Assert.assertEquals(driver.findElement(By.linkText("Basic Course")).isDisplayed(), true);
				driver.findElement(By.linkText("Basic Course")).click();
  }
	@Test (dependsOnMethods = {"basicCource"})
    public void greenBox() {
        Assert.assertEquals(this.driver.findElement(By.className("greenbox")).isDisplayed(), true);
        this.driver.findElement(By.className("greenbox")).click();
         expectedWebpageUrl = "http://10.0.1.86/tatoc/basic/frame/dungeon";

        Assert.assertEquals(expectedWebpageUrl, driver.getCurrentUrl(), "Not as expected webpage");

    }
	@Test (dependsOnMethods = {"greenBox"})
	public void matchBox()
	{
		this.driver.switchTo().frame(0);
		String box1 = this.driver.findElement(By.id("answer")).getAttribute("class");
		while(true){
			this.driver.findElement(By.cssSelector("a")).click();
			this.driver.switchTo().frame("child");
			String box2 = this.driver.findElement(By.id("answer")).getAttribute("class");
			this.driver.switchTo().parentFrame();
			if(box1.equals(box2))
			{
				this.driver.findElements(By.cssSelector("a")).get(1).click();
			break;
			}
			}
	
		expectedWebpageUrl = "http://10.0.1.86/tatoc/basic/drag"; //url of next page
    Assert.assertEquals(expectedWebpageUrl, driver.getCurrentUrl(),"Not as expected webpage");
	System.out.println("abc");
	}
	
	
	@Test(dependsOnMethods = {"matchBox"})
    public void dragAndDrop() {
        Actions actions = new Actions(driver);

        WebElement element = driver.findElement(By.id("dragbox")); 

    	WebElement target = driver.findElement(By.id("dropbox"));

    	(new Actions(driver)).dragAndDrop(element, target).perform();
    	driver.findElement(By.cssSelector("a")).click();
    	expectedWebpageUrl = "http://10.0.1.86/tatoc/basic/windows"; //url of next page
        Assert.assertEquals(expectedWebpageUrl, driver.getCurrentUrl(), "Not as expected webpage");

    }
	@Test(dependsOnMethods = {"dragAndDrop"})
    public void launchPopUp() {
		driver.findElement(By.linkText("Launch Popup Window")).click();
		
		
		String SecWindow = null;
		 // getting other (ALL) windows
	    Set<String> handles = driver.getWindowHandles();
	    System.out.println(handles);
	    
	    Iterator<String> iterator = handles.iterator();
	    while (iterator.hasNext()){
	    	
	    	SecWindow = iterator.next();
	    }
	 // switch to popup window
	    driver.switchTo().window(SecWindow); 
	    driver.findElement(By.id("name")).sendKeys("Aditya");
	    driver.findElement(By.id("submit")).click();
	    
	    driver.switchTo().window(mainWindow);
	    driver.findElements(By.cssSelector("a")).get(1).click();
	    
	    expectedWebpageUrl = "http://10.0.1.86/tatoc/basic/cookie"; //url of next page
        Assert.assertEquals(expectedWebpageUrl, driver.getCurrentUrl(), "Not as expected webpage");

    }
	
	@Test(dependsOnMethods = {"launchPopUp"})
    public void cookieAdd() {
	
driver.findElement(By.cssSelector("a")).click();
    
    //String value = driver.findElement(By.id("token")).getText().split("Token: ")[1];    
    
    
    String Tokenvalue = driver.findElement(By.id("token")).getText();
     Tokenvalue = (Tokenvalue.substring(7));
    
    System.out.println(Tokenvalue);
    
    //adding cookie
    Cookie ck = new Cookie("Token", Tokenvalue);
    driver.manage().addCookie(ck);
    
	
    
    //driver.manage().addCookie( new Cookie("Token", Tokenvalue, "/")); 
    driver.findElements(By.cssSelector("a")).get(1).click(); 
    expectedWebpageUrl = "http://10.0.1.86/tatoc/end"; //url of next page
    Assert.assertEquals(expectedWebpageUrl, driver.getCurrentUrl(), "Not as expected webpage");
	}
	
}