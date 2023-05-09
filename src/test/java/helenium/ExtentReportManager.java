package helenium;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extentReports;

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            String reportPath = "test-output/ExtentReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setEncoding("utf-8");
            sparkReporter.config().setReportName("Automation Test Report");
            sparkReporter.config().setTheme(Theme.STANDARD);
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
        }
        return extentReports;
    }

    public static void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}

