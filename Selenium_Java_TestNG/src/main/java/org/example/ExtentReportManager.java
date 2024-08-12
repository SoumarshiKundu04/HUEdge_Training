package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExtentReportManager implements ITestListener {
    public static ExtentReports extent;
    public static ThreadLocal<ExtentTest> test=new ThreadLocal<>();
    @Override
    public  void onStart(ITestContext context){
        ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter("extent.html");
        htmlReporter.config().setDocumentTitle("Report");
        htmlReporter.config().setReportName("TestReport");
        extent=new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
    public  void  onTestStart(ITestResult result){
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }
    public  void  onTestSuccess(ITestResult result){
        test.get().pass("Test Passed");
        Object tc=result.getInstance();
        WebDriver driver = ((TestMethod)tc).getDriver();
        if(driver!=null){
            try {
                takeScreenshot(driver,result.getMethod().getMethodName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public  void  onTestFailure(ITestResult result){
        test.get().fail(result.getThrowable());
        Object tc=result.getInstance();
        WebDriver driver = ((TestMethod)tc).getDriver();
        if(driver!=null){
            try {
                takeScreenshot(driver,result.getMethod().getMethodName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public  void  onTestSkipped(ITestResult result){
        test.get().skip(result.getThrowable());

    }
    public  void  onFinish(ITestContext context){
        if(extent!=null) extent.flush();

    }
    public void onTestFailedButWithinSuccessPercentage(ITestResult result){

    }
    public void takeScreenshot(WebDriver driver,String method) throws IOException {
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String ScreenshotDirectory = "Screenshots";
        File destination = new File(ScreenshotDirectory,method+"_"+timeStamp+".png");
        FileUtils.copyFile(screenshot,destination);
    }
}
