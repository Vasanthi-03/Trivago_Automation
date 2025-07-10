package com.trivago.base;

import com.trivago.utils.ExtentReportUtil;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestSuiteSetup {

    @BeforeSuite
    public void beforeSuite() {
        ExtentReportUtil.initReport();
    }

    @AfterSuite
    public void afterSuite() {
        ExtentReportUtil.flushReport();
    }
}

