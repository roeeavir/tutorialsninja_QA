
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.support.ui.Select;

public class Test_HW2 {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\Afeka\\chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() throws InterruptedException {
        driver.quit();
    }

    @org.junit.Test
    public void simple() {
        driver.get("http://tutorialsninja.com/demo/");
        driver.manage().window().setSize(new Dimension(1004, 724));
//        driver.findElement(By.id("user-name")).sendKeys("standard_user");
//        driver.findElement(By.id("password")).sendKeys("secret_sauce");
//        driver.findElement(By.id("login-button")).click();
//
//        List<WebElement> products_name = driver.findElements(By.cssSelector("div.inventory_item_name"));
//        List<WebElement> products_price = driver.findElements(By.cssSelector("div.inventory_item_price"));
//
//
//        Iterator<WebElement> itr_names = products_name.iterator();
//        Iterator<WebElement> itr_prices = products_price.iterator();
//
//        int i = 0;
//
//        while (itr_names.hasNext() && itr_prices.hasNext()) {
//            i++;
//            System.out.println(itr_names.next().getText());
//            System.out.println(itr_prices.next().getText());
//            driver.findElement(By.xpath("//*[@id=\"inventory_container\"]/div/div["+i+"]/div[2]/div[2]/button")).click();
////            itr_add.next().click();
//        }

        // Register
        driver.findElement(By.className("dropdown")).click();
        driver.findElement(By.linkText("Register")).click();

        driver.findElement(By.id("input-firstname")).sendKeys("moshe");
        driver.findElement(By.id("input-lastname")).sendKeys("yese");
        driver.findElement(By.id("input-email")).sendKeys("nos1e@mama.com");
        driver.findElement(By.id("input-telephone")).sendKeys("0501122321");
        driver.findElement(By.id("input-password")).sendKeys("123456");
        driver.findElement(By.id("input-confirm")).sendKeys("123456");

        driver.findElement(By.cssSelector("#content > form > div > div > input[type=checkbox]:nth-child(2)")).click();

        driver.findElement(By.cssSelector("#content > form > div > div > input.btn.btn-primary")).click();


        // Login
//        driver.findElement(By.className("dropdown")).click();#content > form > div > div > input[type=checkbox]:nth-child(2)
//        driver.findElement(By.linkText("Login")).click();
//
//        driver.findElement(By.id("input-firstname")).sendKeys("nose@mama.com");
//        driver.findElement(By.id("input-password")).sendKeys("123456");
//        driver.findElement(By.cssSelector("#content > div > div:nth-child(2) > div > form > input")).click();


    }

    @org.junit.Test
    public void tricky() {
        driver.get("http://tutorialsninja.com/demo/");
        driver.manage().window().setSize(new Dimension(1004, 724));
//        driver.findElement(By.id("user-name")).sendKeys("standard_user");
//        driver.findElement(By.id("password")).sendKeys("secret_sauce");
//        driver.findElement(By.id("login-button")).click();
//
//        List<WebElement> products_name = driver.findElements(By.cssSelector("div.inventory_item_name"));
//        List<WebElement> products_price = driver.findElements(By.cssSelector("div.inventory_item_price"));
//
//
//        Iterator<WebElement> itr_names = products_name.iterator();
//        Iterator<WebElement> itr_prices = products_price.iterator();
//
//        int i = 0;
//
//        while (itr_names.hasNext() && itr_prices.hasNext()) {
//            i++;
//            System.out.println(itr_names.next().getText());
//            System.out.println(itr_prices.next().getText());
//            driver.findElement(By.xpath("//*[@id=\"inventory_container\"]/div/div["+i+"]/div[2]/div[2]/button")).click();
////            itr_add.next().click();
//        }

        // Register
        driver.findElement(By.className("dropdown")).click();
        driver.findElement(By.linkText("Register")).click();

        driver.findElement(By.id("input-firstname")).sendKeys("sdg");
        driver.findElement(By.id("input-lastname")).sendKeys("yese");
        driver.findElement(By.id("input-email")).sendKeys("nos1e@mama.com");
        driver.findElement(By.id("input-telephone")).sendKeys("0501122321");
        driver.findElement(By.id("input-password")).sendKeys("123456789012345678901234567890123456");
        driver.findElement(By.id("input-confirm")).sendKeys("123456");

        driver.findElement(By.cssSelector("#content > form > div > div > input[type=checkbox]:nth-child(2)")).click();

        driver.findElement(By.cssSelector("#content > form > div > div > input.btn.btn-primary")).click();


        // Login
//        driver.findElement(By.className("dropdown")).click();#content > form > div > div > input[type=checkbox]:nth-child(2)
//        driver.findElement(By.linkText("Login")).click();
//
//        driver.findElement(By.id("input-firstname")).sendKeys("nose@mama.com");
//        driver.findElement(By.id("input-password")).sendKeys("123456");
//        driver.findElement(By.cssSelector("#content > div > div:nth-child(2) > div > form > input")).click();


    }


    public static void main(String args[]) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        org.junit.runner.Result result = junit.run(Test_HW2.class); // Replace "SampleTest" with the name of your class
        if (result.getFailureCount() > 0) {
            System.out.println("Test failed.");
            System.exit(1);
        } else {
            System.out.println("Test finished successfully.");
            System.exit(0);
        }
    }
}