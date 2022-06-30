package Package;

import java.util.Scanner;
import java.io.Console;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class mainer {

	public static void Menuprinter() {
		System.out.print("\n\nCzego potrzebujesz? (Wprowadz numer wybranej czynnosci)\n"+
				"1) Odepnij linki\n" +
				"2) Podepnij linki\n" +
				"3) Status linków\n" +
				"4) Znajdz transakcje\n"+
				"5) Wychodze\n");
	}

	public static boolean StartHandler(Login step1,Scanner sc, String Temp1, String Temp2, ChromeDriver driver, WebDriverWait wait) throws Exception {

		try {
			
		    Console console = System.console();

			
			System.out.print("Wprowadz 'Q' na dowolnym etapie aby wyjść\n");
			System.out.flush();

			System.out.print("Podaj SSO...\n");
			Temp1 = sc.nextLine();
			if(Temp1.toLowerCase().equals("q")) {return true;}
			System.out.flush();

			System.out.print("Podaj hasło UAT...\n");
			char[] pw = console.readPassword();
			Temp2 = new String(pw);
			if(Temp2.toLowerCase().equals("q")) {return true;}
			System.out.flush();

			step1 =new Login(Temp1,Temp2, driver, wait);

			return step1.login();

		} catch (Exception e) {

			throw e;
		}

	}

	public static void main(String[] args) throws Exception {

		Scanner sc;
		OdpinaczPodpinacz Linki;
		Login Proces =null;

		String Temp1=null;
		String Temp2=null;
		String Temp3=null;

		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--ignore-ssl-errors=yes","--ignore-certificate-errors");
		ChromeDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, 10);

		boolean exit;

		sc = new Scanner(System.in);

		exit =StartHandler(Proces,sc,Temp1,Temp2, driver, wait);

		while(!exit)
		{
			Menuprinter();
			Temp1 = sc.nextLine();
			switch(Temp1) {

			case "1":
				Linki = new OdpinaczPodpinacz(driver, wait);
				Linki.Odpinacz();
				break;

			case "2":
				Linki = new OdpinaczPodpinacz(driver, wait);
				Linki.Podpinacz();
				break;

			case "3":
				Linki = new OdpinaczPodpinacz(driver, wait);
				Linki.LinkCheck();

				if(Linki.linki) {
					System.out.print("\nLinki są Podpięte (Destination Server Status: ON)");
				}
				else {
					System.out.print("\nLinki są Odpięte (Destination Server Status: OFF)");
				}
				break;

			case "4":
				Linki = new OdpinaczPodpinacz(driver, wait);
				System.out.print("\nWprowadz dolny zakres waluty: (Separatorem jest kropka)\n");
				Temp1 = sc.nextLine();
				System.out.print("Wprowadz górny zakres waluty: (Separatorem jest kropka)\n");
				Temp2 =sc.nextLine();
				System.out.print("Stasus 'Hold'(Y) czy 'Any'(N)\n");
				Temp3 =sc.nextLine();
				
				if(Temp3.toLowerCase().equals("y")) {
					
					Linki.TransCheck(Temp1,Temp2, true);
				}
				else {
					
					Linki.TransCheck(Temp1,Temp2, false);
				}

				

				break;


			case "5":
			case "q":
			case "Q":
				
				System.out.print("Zamknąć RCX? Y/N \n");
				Temp3 =sc.nextLine();
				if(!Temp3.toLowerCase().equals("y")) {					
				}
				else {
					driver.quit();
				}
				exit =true;
				break;
			}

		}
		System.out.print("\n **********************Closing...*************************");
		sc.close();
	}


}