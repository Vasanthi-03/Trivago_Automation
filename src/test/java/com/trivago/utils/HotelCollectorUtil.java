package com.trivago.utils;



import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HotelCollectorUtil {
    public static List<String> hotelNames = new ArrayList<>();
    public static List<String> hotelPrices = new ArrayList<>();
    public static List<String> hotelLocations = new ArrayList<>();
    public static List<Double> hotelRatings = new ArrayList<>();

    public static void collectHotelData(WebDriver driver) throws InterruptedException {
    	boolean hasNextPage = true;

        while (hasNextPage) {
        Thread.sleep(3000);
        List<WebElement> names = driver.findElements(By.cssSelector("button[data-testid='item-name']"));
        List<WebElement> prices = driver.findElements(By.cssSelector("div[class='HjOk6Q oVtsoQ ZTIfHR']"));
        List<WebElement> ratings = driver.findElements(By.cssSelector("span[class='SmLAfp Eqg9Bk Dtphjk EXnXoV']"));
        List<WebElement> locations = driver.findElements(By.cssSelector("span[class='wcZPz9']"));

        int min = Collections.min(List.of(names.size(), prices.size(), ratings.size(), locations.size()));
 
        for (int i = 0; i < min; i++) {
            hotelNames.add(names.get(i).getText());
            hotelPrices.add(prices.get(i).getText());
            hotelLocations.add(locations.get(i).getText());
            try {
                hotelRatings.add(Double.parseDouble(ratings.get(i).getText()));
            } catch (NumberFormatException e) {
                hotelRatings.add(0.0);
            }
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
        for (int i = 0; i < hotelRatings.size() - 1; i++) {
            if (hotelRatings.get(i) < hotelRatings.get(i + 1)) {
                isDescending = false;
                System.out.println((i + 2) + " =====discontinues here for descending====");
                break;
            }
        }

        System.out.println(isDescending ? "Ratings are  in descending order." : " Ratings are not  in descending order.");
        System.out.println("=====Total hotels collected:=====\n " + hotelNames.size());

    
    }
}
