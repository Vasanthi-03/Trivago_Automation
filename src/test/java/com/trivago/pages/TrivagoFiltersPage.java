package com.trivago.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TrivagoFiltersPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public TrivagoFiltersPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        this.js = (JavascriptExecutor) driver;
    }

    public void selectDatesAndOccupancy() throws InterruptedException {
        
    	// Calculate check-in and check-out dates
        
//        LocalDate checkIn = LocalDate.now().plusDays(30);
//        LocalDate checkOut = checkIn.plusDays(1);
//
//        // Format dates to match the datetime attribute used by Trivago
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String checkInDate = checkIn.format(formatter);
//        String checkOutDate = checkOut.format(formatter);
//
//        // Wait for and click on check-in date
//        WebElement checkInElement = wait.until(ExpectedConditions.elementToBeClickable(
//            By.cssSelector("time[datetime='" + checkInDate + "']")
//        ));
//        js.executeScript("arguments[0].click();", checkInElement);
//        Thread.sleep(1000);
//
//        // Wait for and click on check-out date
//        WebElement checkOutElement = wait.until(ExpectedConditions.elementToBeClickable(
//            By.cssSelector("time[datetime='" + checkOutDate + "']")
//        ));
//        js.executeScript("arguments[0].click();", checkOutElement);
//        Thread.sleep(1000);
    	
    	 
        WebElement checkin = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("time[datetime='2025-07-27']")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", checkin);
        Thread.sleep(2000);

        WebElement checkout = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("time[datetime='2025-07-28']")));
        js.executeScript("arguments[0].click();", checkout);
        Thread.sleep(2000);


        // Reduce adults to 1 (optional: validate current value first)
        WebElement minusAdultButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button[data-testid='adults-amount-minus-button']")));
        minusAdultButton.click();
        Thread.sleep(1000);

        // Click Apply
        WebElement applyButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(),'Apply')]")));
        applyButton.click();

        // Click Search
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button[data-testid='search-button-with-loader']")));
        searchButton.click();
        Thread.sleep(2000);

        System.out.println("==Dates and occupancy set successfully==");
        
       // Sort by Rating
        WebElement sortButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[1]/div/button/span/span[1]/span")));
        sortButton.click();
        Thread.sleep(2000);

        WebElement ratingCheckbox =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[1]/div/div/div/div/section/div/ul/li[5]/label")));
        ratingCheckbox.click();
        Thread.sleep(2000);

        WebElement ratingapply=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[1]/div/div/div/div/section/footer/button")));
        ratingapply.click();
        System.out.println("Sorted by Rating");
        
        // Selecting Property Type
        Thread.sleep(2000);
        WebElement propertyType=wait.until(ExpectedConditions.elementToBeClickable(By.xpath(" /html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[5]/div/button/span/span[1]/span")));
        propertyType.click();
        Thread.sleep(2000);

        WebElement Hotels=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[5]/div/div/div/div/section/div/section/ul/li[1]/div/span/label/input")));
        Hotels.click();
        WebElement ApplyHotels=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[5]/div/div/div/div/section/footer/button[2]")));
        ApplyHotels.click();

        System.out.println("Only hotels");
        
    }
}
