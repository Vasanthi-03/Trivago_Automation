package com.trivago.pages;



import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TrivagoHomePage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "input-auto-complete")
    private WebElement searchBox;
    
    public TrivagoHomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver; 
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void searchCity(String city) throws InterruptedException {

        wait.until(ExpectedConditions.elementToBeClickable(searchBox)).sendKeys(city);
        Thread.sleep(2000);
        searchBox.sendKeys(Keys.ENTER);
        
    }

//	//@SuppressWarnings("unused")
//	private Object xpath(String string) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	public void searchCity(String city) {
//		// TODO Auto-generated method stub
//		
//	}
}
