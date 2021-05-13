package test1;

import org.junit.Test;
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

import java.io.IOException;


import org.openqa.selenium.JavascriptExecutor;

import java.util.*;

import org.apache.logging.log4j.*;

public class Twat_ex {


    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;


    @After
    public void tearDown() {
        //  driver.quit();
    }


    @Before
    public void setUp() throws IOException {
        System.setProperty("webdriver.chrome.driver", "D:\\Afeka\\chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();


    }


    @Test
    public void simple() throws InterruptedException {
        Logger logger = LogManager.getLogger(Twat_ex.class);

        logger.info("opening website");

        driver.get("https://www.saucedemo.com");
        driver.manage().window().setSize(new Dimension(1004, 724));


        logger.debug("Sending username: \"standard_user\"");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");

        driver.findElement(By.id("password")).sendKeys("secret_sauce");

        driver.findElement(By.id("login-button")).click();
        ;


    }

    public static void main(String args[]) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        org.junit.runner.Result result = junit.run(Twat_ex.class); // Replace "SampleTest" with the name of your class
        if (result.getFailureCount() > 0) {
            System.out.println("Test failed.");
            System.exit(1);
        } else {
            System.out.println("Test finished successfully.");
            System.exit(0);
        }
    }
}

