`  
package Package;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {

	ChromeDriver driver;
	ChromeOptions options;
	WebDriverWait wait;
	String Login;
	String Pass;
	
	public boolean login () throws Exception {

		String loginVal;
		
		////wejscie na stronke i przygotowanie
		driver.get("https://emeatreasurytradecashexchangeuat.emea.citigroup.net//rcx//");


		//LOGIN
		driver.findElement(By.className("textinput")).sendKeys(Login);
		driver.findElement(By.name("PASSWORD")).sendKeys(Pass);
		driver.findElement(By.className("ButtonSm")).click();
		
		//LOGIN VALIDATION
		
		if(!driver.findElementsByClassName("pageheader").isEmpty()) {
			loginVal = driver.findElementByClassName("pageheader").getAttribute("innerHTML");
			if(loginVal.toLowerCase().contains("fail"))
			{
				driver.quit();
				System.out.print("Zły login lub hasło");
				return true;
			}
			else if(loginVal.toLowerCase().equals("password change request"))
			{
				//driver.quit();
				System.out.print("Musisz zmienić hasło, uruchom ponownie po zmianie");
				return true;
			}

		}
		
		wait.until(ExpectedConditions.elementToBeClickable(By.name("myradio")));
		driver.findElementByCssSelector("input[value='1']").click();
		driver.findElementByCssSelector("input[value='SUBMIT']").click();
		
		return false;
		
	}
	
public Login(String log,String pass, ChromeDriver driv, WebDriverWait wai) {
		
		
		Login=log;
		Pass=pass;
		driver=driv;
		wait=wai;
		

		
	}

}