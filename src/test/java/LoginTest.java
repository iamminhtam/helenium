import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static drivers.DriverFactory.createDriver;

public class LoginTest {

    @Test
    void loginTest() {
        WebDriver driver = createDriver("chrome");
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        System.out.println("end.");
        driver.close();
        driver.quit();
    }

}
