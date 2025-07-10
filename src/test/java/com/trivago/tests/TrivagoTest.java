package com.trivago.tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.trivago.base.BaseTest;
import com.trivago.pages.TrivagoFiltersPage;
import com.trivago.pages.TrivagoHomePage;
import com.trivago.utils.ExcelReportUtil;
import com.trivago.utils.ExtentReportUtil;
import com.trivago.utils.HotelCollectorUtil;

public class TrivagoTest extends BaseTest {

    @Test(priority = 1)
    public void searchAndFilterHotels() throws InterruptedException {
    	ExtentTest test = ExtentReportUtil.createTest("Search and Filter Hotels");
        TrivagoHomePage homePage = new TrivagoHomePage(driver, wait);
        homePage.searchCity("Mumbai");

        TrivagoFiltersPage  filtersPage = new TrivagoFiltersPage(driver, wait);
        filtersPage.selectDatesAndOccupancy();
    }

    @Test(priority = 2)
    public void collectAndExportHotelData() throws InterruptedException {

       ExtentTest test = ExtentReportUtil.createTest("Collect and Export Hotel Data");

        HotelCollectorUtil.collectHotelData(driver);
        ExcelReportUtil.writeTop10HotelsToExcel(
                HotelCollectorUtil.hotelNames,
                HotelCollectorUtil.hotelLocations,
                HotelCollectorUtil.hotelPrices,
                HotelCollectorUtil.hotelRatings
        );
    }
}
