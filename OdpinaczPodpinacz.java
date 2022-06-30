package Package;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

public class OdpinaczPodpinacz {

	ChromeDriver driver;
	WebDriverWait wait;
	boolean linki; 

	public void TransCheck(String min,String max,boolean hold) throws InterruptedException {

		driver.switchTo().defaultContent();

		driver.switchTo().frame("Menu");

		driver.findElementById("menu_2").click();
		driver.findElementById("submenu_2_1").click();

		driver.switchTo().parentFrame();
		driver.switchTo().frame("TabList");
		driver.findElementById("menu_9").click();

		driver.switchTo().parentFrame();
		driver.switchTo().frame("ContentFrame");
		driver.findElement(By.id("field_amountmin")).sendKeys(min);
		driver.findElement(By.id("field_amountmax")).sendKeys(max);


		driver.findElementById("field_txnstatus").click();
		TimeUnit.SECONDS.sleep(2);
		if(hold) {
			driver.findElementByCssSelector("option[value='T008']").click();
		}
		else {
			driver.findElementByCssSelector("option[value='Any']").click();
		}

		driver.findElementByName("SearchRecord").click();
		wait.until((Check) ->driver.findElement(By.id("rowcount")).getText().contains("Row")||driver.findElement(By.id("status")).getText().equals("No matching records found")); //lambda expression 
		if(driver.findElement(By.id("status")).getText().equals("No matching records found")){
			System.out.print("Nie znaleziona żadnych transakcji\n");
		}
		else {
			WebElement giga = driver.findElementByCssSelector("div[class='dojoxGridContent']");
			List <WebElement> czad = giga.findElement(By.cssSelector("div[role='presentation']")).findElements(By.tagName("div"));

			for(WebElement ultra: czad) {
				System.out.print(ultra.findElement(By.cssSelector("td[idx='3']")).getText()+"\n");
			}
		}

	}

	public void LinkCheck() {


		driver.switchTo().defaultContent();


		driver.switchTo().frame("Menu");

		driver.findElementById("menu_2").click();
		driver.findElementById("submenu_2_3").click();

		driver.switchTo().parentFrame();
		driver.switchTo().frame("ContentFrame");
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));

		wait.until(ExpectedConditions.visibilityOf(driver.findElementById("countryCode_Id")));

		Select drpCountry = new Select(driver.findElementById("countryCode_Id"));
		drpCountry.selectByVisibleText("POLAND");

		driver.findElementById("searchBtn").click();

		driver.switchTo().parentFrame();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(2));


		if(driver.findElementById("radioBtnON_Id1").isSelected()) {
			linki = true;
		}
		else {
			linki = false;
		}

	}

	public void Odpinacz() {

		LinkCheck();

		if(linki==false) {
			System.out.print("\nLinki są już odpięte mordo, co ty robisz? (Destination Server Status: OFF)");
		}
		else
		{
			driver.findElementById("radioBtnOFF_Id1").click();
			AlertiSave();
			for(int i=2; i<=3; i++) {
				LinkCheck();
				driver.findElementById("radioBtnOFF_Id"+i).click();
				AlertiSave();
			}
			System.out.print("\nLinki odpięte mordo (Destination Server Status: OFF)");
		}

	}

	public void Podpinacz() {

		LinkCheck();

		if(linki==true) {
			System.out.print("\nLinki są już podpięte mordo, co ty robisz? (Destination Server Status: ON)");
		}
		else
		{
			driver.findElementById("radioBtnON_Id1").click();
			AlertiSave();
			for(int i=2; i<=3; i++) {
				LinkCheck();
				driver.findElementById("radioBtnON_Id"+i).click();
				AlertiSave();
			}
			System.out.print("\nLinki podpięte mordo (Destination Server Status: ON)");
		}

	}

	public void AlertiSave() {

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {}


		driver.switchTo().alert().accept();
		driver.findElementByCssSelector("button[property='saveBtn']").click();

	}

	public OdpinaczPodpinacz(ChromeDriver driv, WebDriverWait wai) {

		wait = wai;
		driver = driv;
	}

}