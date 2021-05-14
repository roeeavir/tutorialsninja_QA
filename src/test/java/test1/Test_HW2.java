package test1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.After;
import org.junit.Before;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class Test_HW2 {

    private static final String[] TEXT_FIELD_NAMES_REGISTER_PAGE = {"input-firstname", "input-lastname", "input-email", "input-telephone", "input-password", "input-confirm"};
    private static final String[] TEXT_FIELD_NAMES_LOGIN_PAGE = {"input-email", "input-password"};
    private WebDriver driver;
    JavascriptExecutor js;
    //private final ReadExcl objExcelFile = new ReadExcl();
    private int rowCount;
    Sheet thsSheet;
    private final String homePageURL = "http://tutorialsninja.com/demo/";
    private String currentURL;
    private String loginURL;
    ReadExcl objExcelFile = new ReadExcl();

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        rowCount = ReadExcl.getRowcount();
        thsSheet = ReadExcl.getsheet();
    }

    @After
    public void tearDown() {
//        driver.quit();
    }

    @org.junit.Test
    public void simple() throws IOException {
        Logger logger = LogManager.getLogger(Test_HW2.class);
        logger.info("opening website");
        driver.get("http://tutorialsninja.com/demo/");
        driver.manage().window().setSize(new Dimension(1004, 724));
        register(logger);
        login(logger);
    }

    private void login(Logger logger) throws IOException {
        logger.info("Login Tests");
        objExcelFile = new ReadExcl();
        objExcelFile.readExcel("ReadExcels", "RegisterVals.xls", "Login");
        rowCount = ReadExcl.getRowcount();
        thsSheet = ReadExcl.getsheet();
        logger.debug("Logout");
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[5]/a")).click();
        logger.debug("Moving to login page");
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[2]/a")).click();
        loginURL=driver.getCurrentUrl();
        logger.debug("Register URL: " + loginURL);
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
                        driver.findElement(By.id(TEXT_FIELD_NAMES_LOGIN_PAGE[j - 1])).sendKeys(row.getCell(j).getStringCellValue());
                    } else if (row.getCell(j).getCellType() == CellType.NUMERIC) {
                        logger.debug(headerRow.getCell(j).getStringCellValue() + ": " + row.getCell(j).getNumericCellValue());
                        driver.findElement(By.id(TEXT_FIELD_NAMES_LOGIN_PAGE[j - 1])).sendKeys("" + row.getCell(j).getNumericCellValue());
                    }
                }
            }

            driver.findElement(By.cssSelector("#content > div > div:nth-child(2) > div > form > input")).click();

            driver.navigate().to(driver.getCurrentUrl());
            currentURL = driver.getCurrentUrl();

            if (!currentURL.equals(loginURL) && i != rowCount) {
                logger.error(row.getCell(0).getStringCellValue() + " has failed!!!  - User managed to login with invalid fields!!");
                logger.debug("Moving to login page");
                driver.navigate().to(loginURL);
            } else if (currentURL.equals(loginURL) && i != rowCount) {
                logger.info(row.getCell(0).getStringCellValue() + " has passed!!! - User failed to login with invalid fields!!");
            } else if (currentURL.equals(loginURL)) {
                logger.error(row.getCell(0).getStringCellValue() + " has failed!!! - User failed to login with valid fields!!");
            } else {
                logger.info(row.getCell(0).getStringCellValue() + " has passed!!! - User login with valid fields!!");
            }
        }
    }

    private void register(Logger logger) throws IOException {
        logger.info("Register Tests");
        objExcelFile.readExcel("ReadExcels", "RegisterVals.xls", "Register");
        rowCount = ReadExcl.getRowcount();
        thsSheet = ReadExcl.getsheet();
        logger.debug("Moving to register page");
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[1]/a")).click();
        currentURL = driver.getCurrentUrl();
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
                        driver.findElement(By.id(TEXT_FIELD_NAMES_REGISTER_PAGE[j - 1])).sendKeys(row.getCell(j).getStringCellValue());
                    } else if (row.getCell(j).getCellType() == CellType.NUMERIC) {
                        logger.debug(headerRow.getCell(j).getStringCellValue() + ": " + row.getCell(j).getNumericCellValue());
                        driver.findElement(By.id(TEXT_FIELD_NAMES_REGISTER_PAGE[j - 1])).sendKeys("" + row.getCell(j).getNumericCellValue());
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


    public static void main(String[] args) {
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