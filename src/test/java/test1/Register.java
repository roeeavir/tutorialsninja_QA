package test1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.Before;
import org.junit.After;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.util.*;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

public class Register {

    private static final String[] TEXT_FIELD_NAMES = {"input-firstname", "input-lastname", "input-email", "input-telephone", "input-password", "input-confirm"};

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    int rowCount;
    Sheet thsSheet;
    int counter = 1;


    @Before
    public void setUp() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();

        ReadExcl objExcelFile = new ReadExcl();

        objExcelFile.readExcel("ReadExcels", "RegisterVals.xls", "Sheet1");
        rowCount = ReadExcl.getRowcount();
        thsSheet = ReadExcl.getsheet();
    }

    @After
    public void tearDown() throws InterruptedException {
//        driver.quit();
    }

    @org.junit.Test
    public void simple() {

        Logger logger = LogManager.getLogger(Register.class);

        logger.info("opening website");


        driver.get("http://tutorialsninja.com/demo/");
        driver.manage().window().setSize(new Dimension(1004, 724));
        logger.debug("Moving to register page");
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[1]/a")).click();
        String currentURL = driver.getCurrentUrl();
        String registerURL = driver.getCurrentUrl();
        logger.debug("Register URL: " + registerURL);
        Row headerRow = thsSheet.getRow(0);
        for (int i = 1; i <= rowCount; i++) {

            Row row = thsSheet.getRow(i);

            //Create a loop to print cell values in a row

            logger.debug("Test Type: " + row.getCell(0).getStringCellValue());
            for (int j = 1; j < row.getLastCellNum(); j++) {

                //Print Excel data in console
                if (!(row.getCell(j) == null)) {
                    if (row.getCell(j).getCellType() == CellType.STRING) {
                        logger.debug(headerRow.getCell(j).getStringCellValue() + ": " + row.getCell(j).getStringCellValue());
                        driver.findElement(By.id(TEXT_FIELD_NAMES[j - 1])).sendKeys(row.getCell(j).getStringCellValue());
                    } else if (row.getCell(j).getCellType() == CellType.NUMERIC) {
                        logger.debug(headerRow.getCell(j).getStringCellValue() + ": " + row.getCell(j).getNumericCellValue());
                        driver.findElement(By.id(TEXT_FIELD_NAMES[j - 1])).sendKeys("" + row.getCell(j).getNumericCellValue());
                    }

                }
            }
            driver.findElement(By.cssSelector("#content > form > div > div > input[type=checkbox]:nth-child(2)")).click();

            driver.findElement(By.cssSelector("#content > form > div > div > input.btn.btn-primary")).click();

            driver.navigate().to(driver.getCurrentUrl());

            currentURL = driver.getCurrentUrl();


            if (!currentURL.equals(registerURL) && i != rowCount) {
                logger.error(row.getCell(0).getStringCellValue() + " has failed!!!  - User managed to register with invalid fields!!");
                logger.debug("Moving to register page");
                driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[2]")).click();
                driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[5]/a")).click();
                driver.navigate().to(registerURL);
            } else if (currentURL.equals(registerURL) && i != rowCount) {
                logger.info(row.getCell(0).getStringCellValue() + " has passed!!! - User failed to register with invalid fields!!");
            } else if (currentURL.equals(registerURL)) {
                logger.error(row.getCell(0).getStringCellValue() + " has failed!!! - User failed to register with valid fields!!");
            } else {
                logger.info(row.getCell(0).getStringCellValue() + " has passed!!! - User registered with valid fields!!");
            }
        }
    }

    public static void main(String args[]) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        org.junit.runner.Result result = junit.run(Register.class); // Replace "SampleTest" with the name of your class
        if (result.getFailureCount() > 0) {
            System.out.println("Test failed.");
            System.exit(1);
        } else {
            System.out.println("Test finished successfully.");
            System.exit(0);
        }
    }
}