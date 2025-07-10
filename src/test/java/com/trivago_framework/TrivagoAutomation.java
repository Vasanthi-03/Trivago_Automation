package com.trivago_framework;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.*;
import org.openqa.selenium.NoSuchElementException;

//import java.util.noSuchElementException;

public class TrivagoAutomation {
    static WebDriver driver;

    public static void main(String[] args) {
       // System.setProperty("webdriver.chrome.driver", "C:\\Driver\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        try {
            driver.get("https://www.trivago.in/");
            System.out.println("=====Opened Trivago=========");

            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-auto-complete")));
            searchBox.sendKeys("Mumbai");
            Thread.sleep(2000);
            searchBox.sendKeys(Keys.ENTER);
            System.out.println("==Entered city Mumbai=====");

            WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("time[datetime='2025-07-27']")));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", dateElement);
            Thread.sleep(2000);

            WebElement dateElement2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("time[datetime='2025-07-28']")));
            js.executeScript("arguments[0].click();", dateElement2);
            Thread.sleep(2000);

            WebElement occupancyButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-testid='adults-amount-minus-button']")));
            occupancyButton.click();
            Thread.sleep(2000);

            WebElement applyButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Apply')]")));
            applyButton.click();
            System.out.println("==Set 1 adult, 1 room=========");

            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-testid='search-button-with-loader']")));
            searchButton.click();
            System.out.println("=====Clicked Search======");
            Thread.sleep(2000);
 
            WebElement sortButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[1]/div/button/span/span[1]/span")));
            sortButton.click();
            Thread.sleep(2000);

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[1]/div/div/div/div/section/div/ul/li[5]/label"))).click();
            Thread.sleep(2000);

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[1]/div/div/div/div/section/footer/button"))).click();
            System.out.println("==Sorted by Rating=======");
            Thread.sleep(2000);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[5]/div/button/span/span[1]/span"))).click();
            Thread.sleep(2000);

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[5]/div/div/div/div/section/div/section/ul/li[1]/div/span/label/input"))).click();
           
            Thread.sleep(2000);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[5]/div/div/div/div/section/footer/button[2]"))).click();

            System.out.println("======Only hotels are considered========");
           

            List<String> allHotelNames = new ArrayList<>();
            List<String> allPrices = new ArrayList<>();
            List<String> allLocations = new ArrayList<>();
            List<Double> allRatings = new ArrayList<>();

            boolean hasNextPage = true;

            while (hasNextPage) {
                Thread.sleep(3000);

                List<WebElement> hotelNames = driver.findElements(By.cssSelector("button[data-testid='item-name']"));
                List<WebElement> hotelPrices = driver.findElements(By.cssSelector("div[class='HjOk6Q oVtsoQ ZTIfHR']"));
                List<WebElement> ratingElements = driver.findElements(By.cssSelector("span[class='SmLAfp Eqg9Bk Dtphjk EXnXoV']"));
                List<WebElement> locationElements = driver.findElements(By.cssSelector("span[class='wcZPz9']"));

                for (int i = 0; i < hotelNames.size(); i++) {
                    String name = hotelNames.get(i).getText();
                    String price =  hotelPrices.get(i).getText() ;
                    String ratingText = i < ratingElements.size() ? ratingElements.get(i).getText() : "0.0";
                    String location =  locationElements.get(i).getText();

                    double rating = 0.0;
                    try {
                        rating = Double.parseDouble(ratingText);
                    } catch (NumberFormatException ignored) {}

                    allHotelNames.add(name);
                    allPrices.add(price);
                    allLocations.add(location);
                    allRatings.add(rating);

                    System.out.println(allHotelNames.size() + ". " + name);
                    System.out.println("   Location: " + location);
                    System.out.println("   Price: " + price);
                    System.out.println("   Rating: " + rating);
                }

                try {
                    WebElement nextButton = driver.findElement(By.cssSelector("span[class=\"Ji89fk xHuPmg XsCgXv\"]"));
                    if (nextButton.isEnabled()) {
                        nextButton.click();
                    } else {
                        hasNextPage = false;
                    }
                } catch (NoSuchElementException e) {
                    hasNextPage = false;
                }
            }

            boolean isDescending = true;
            for (int i = 0; i < allRatings.size() - 1; i++) {
                if (allRatings.get(i) < allRatings.get(i + 1)) {
                    isDescending = false;
                    System.out.println(i+2 +" =====discontinues here for descending====");
                    break;
                }
            }

            System.out.println(isDescending ? "\nRatings are in descending order." : "\n Ratings are not in descending order.");
            System.out.println("=====Total hotels collected:===== " + allHotelNames.size());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}