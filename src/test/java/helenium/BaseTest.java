package helenium;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseTest {

    protected WebDriver driver;
    protected Logger logger;
    protected ExtentReports extentReports;
    protected ExtentTest extentTest;

    @BeforeTest
    public void beforeTest() {
        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/extent-report.html");
        extentReports.attachReporter(reporter);
    }

    @AfterSuite
    public void afterSuite() {
        extentReports.flush();
    }
    @BeforeMethod
    public void setUp(Method method) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger = LogManager.getLogger(method.getName());
        driver.manage().window().maximize();
        extentTest = extentReports.createTest(method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotName = result.getName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File("screenshots/" + screenshotName + ".png");
            FileUtils.copyFile(source, destination);
            extentTest.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(destination.getAbsolutePath()).build());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.skip(result.getThrowable().getMessage());
        }
        extentReports.flush();
    }
}
