package Listener;

import Base.BaseTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import reports.ExtentReport;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//Duoc su dung de kiem trouc cac test case
public class TestListener implements ITestListener {// interface
    BaseTest base = new BaseTest();
    @Override
    public void onTestStart(ITestResult iTestResult) {
        ExtentReport.startTest(iTestResult.getName(), iTestResult.getMethod().getDescription())
                .assignCategory(base.getPlatform() + "_" + base.getDeviceName())
                .assignAuthor("BACH");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {


        File file = base.getDriver().getScreenshotAs(OutputType.FILE);

        byte[] encoded = null;
        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();

        }
        Map<String, String> params = new HashMap<String, String>();
        params = iTestResult.getTestContext().getCurrentXmlTest().getAllParameters();

        String imagePath = "Screenshots" + File.separator + params.get("platformName")
                + "_" + params.get("deviceName") + File.separator + base.getDateTime() + File.separator
                + iTestResult.getTestClass().getRealClass().getSimpleName() + File.separator + iTestResult.getName() + ".png";



        String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;




        try {
            FileUtils.copyFile(file, new File(imagePath));
            Reporter.log("This is the sample screenshot");
            Reporter.log("<a href='"+ completeImagePath + "'> <img src='"+ completeImagePath + "' height='400' width='400'/> </a>");
            ExtentReport.getTest().log(Status.PASS, "Test Passed");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        if (iTestResult.getThrowable() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            iTestResult.getThrowable();
        }
        byte[] encoded = null;
        Map<String, String> params = new HashMap<String, String>();
        params = iTestResult.getTestContext().getCurrentXmlTest().getAllParameters();

        String imagePath = "Screenshots" + File.separator + params.get("platformName")
                + "_" + params.get("deviceName") + File.separator + base.getDateTime() + File.separator
                + iTestResult.getTestClass().getRealClass().getSimpleName() + File.separator + iTestResult.getName() + ".png";



        String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;


        ExtentReport.getTest().fail("Test Failed",
                MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
        ExtentReport.getTest().fail("Test Failed",
                MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
        ExtentReport.getTest().fail(iTestResult.getThrowable());

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        ExtentReport.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        ExtentReport.getReporter().flush();
    }
}
