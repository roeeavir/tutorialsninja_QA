package test1;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.Before;
import org.junit.After;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import java.util.*;

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
        register();
        login();
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

        driver.findElement(By.id("user-name")).sendKeys("standard_user");


    }

    private void login() {
    }

    private void register() {


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