package com.testng_experiment;


import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.IntStream;

import org.openqa.selenium.NoSuchElementException;

public class Trivago_testng_framework {
 static    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    List<String> allHotelNames = new ArrayList<>();
    List<String> allPrices = new ArrayList<>();
    List<String> allLocations = new ArrayList<>();
    List<Double> allRatings = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
        driver.get("https://www.trivago.in/");
        System.out.println("=====Opened Trivago=========");
    }

    @Test(priority = 1)
    public void searchHotelsInMumbai() throws InterruptedException {
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-auto-complete")));
        searchBox.sendKeys("Mumbai");
        Thread.sleep(2000);
        searchBox.sendKeys(Keys.ENTER);
        System.out.println("==Entered city Mumbai=====");
    }

    @Test(priority = 2)
    public void selectDatesAndOccupancy() throws InterruptedException {
        WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("time[datetime='2025-07-27']")));
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
    }

    @Test(priority = 3)
    public void applyFiltersAndSort() throws InterruptedException {
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
    }

    @Test(priority = 4)
    public void collectHotelData() throws InterruptedException {
        boolean hasNextPage = true;

        while (hasNextPage) {
            Thread.sleep(3000);

            List<WebElement> hotelNames = driver.findElements(By.cssSelector("button[data-testid='item-name']"));
            List<WebElement> hotelPrices = driver.findElements(By.cssSelector("div[class='HjOk6Q oVtsoQ ZTIfHR']"));
            List<WebElement> ratingElements = driver.findElements(By.cssSelector("span[class='SmLAfp Eqg9Bk Dtphjk EXnXoV']"));
            List<WebElement> locationElements = driver.findElements(By.cssSelector("span[class='wcZPz9']"));

            System.out.println("Sizes => Names: " + hotelNames.size() + ", Prices: " + hotelPrices.size() +
                    ", Ratings: " + ratingElements.size() + ", Locations: " + locationElements.size());

            int minSize = Collections.min(Arrays.asList(
                hotelNames.size(), hotelPrices.size(), ratingElements.size(), locationElements.size())
            );

            for (int i = 0; i < minSize; i++) {
                String name = hotelNames.get(i).getText();
                String price = hotelPrices.get(i).getText();
                String ratingText = ratingElements.get(i).getText();
                String location = locationElements.get(i).getText();

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
    }

    @Test(priority = 5)
    public void verifyRatingsDescending() {
        boolean isDescending = true;
        for (int i = 0; i < allRatings.size() - 1; i++) {
            if (allRatings.get(i) < allRatings.get(i + 1)) {
                isDescending = false;
                System.out.println((i + 2) + " =====discontinues here for descending====");
                break;
            }
        }

        System.out.println(isDescending ? "\nRatings are in descending order." : "\nRatings are not in descending order.");
        System.out.println("=====Total hotels collected:===== " + allHotelNames.size());
    }
    @Test(priority = 6)
    public void generateSortedExcelReport() {
        List<Integer> sortedIndices = IntStream.range(0, allRatings.size())
            .boxed()
            .sorted((i, j) -> Double.compare(allRatings.get(j), allRatings.get(i))) // Descending
            .limit(10)
            .toList();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Top 10 Hotels");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Hotel Name");
            header.createCell(1).setCellValue("Location");
            header.createCell(2).setCellValue("Price");
            header.createCell(3).setCellValue("Rating");

            for (int i = 0; i < 4; i++) {
                header.getCell(i).setCellStyle(headerStyle);
            }

            for (int rowIndex = 0; rowIndex < sortedIndices.size(); rowIndex++) {
                int i = sortedIndices.get(rowIndex);
                Row row = sheet.createRow(rowIndex + 1);
                row.createCell(0).setCellValue(allHotelNames.get(i));
                row.createCell(1).setCellValue(allLocations.get(i));
                row.createCell(2).setCellValue(allPrices.get(i));
                row.createCell(3).setCellValue(allRatings.get(i));
            }

            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "C:\\driver\\Trivago_Hotel_Report_" + timestamp + ".xlsx";

            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
            }

            System.out.println(" Excel report generated at: " + fileName);
        } catch (Exception e) {
            System.err.println("Excel generation failed: " + e.getMessage());
        }
    }


    @AfterClass
    public void tearDown() {
        System.out.println(" ========I am in finally block now =======");
        if (driver != null) {
            driver.quit();
        }
    }
}
