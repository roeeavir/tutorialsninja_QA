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
    private static final String[] CURRENCY = {"Euro", "Pound", "US"};
    private static final String[] PRICES = {"472.33", "368.73", "602.00"};
    private WebDriver driver;
    private int rowCount;
    private Sheet thsSheet;
    private final String homePageURL = "http://tutorialsninja.com/demo/index.php?route=common/home";
    private final String shoppingCartURL = "http://tutorialsninja.com/demo/index.php?route=checkout/cart";
    private String currentURL;
    private String loginURL = "http://tutorialsninja.com/demo/index.php?route=account/login";
    private ReadExcl objExcelFile = new ReadExcl();
    private boolean isLogged = false;


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
        rowCount = ReadExcl.getRowcount();
        thsSheet = ReadExcl.getsheet();
    }

    @After
    public void tearDown() {
        //driver.quit();
    }

    @org.junit.Test
    public void simple() throws InterruptedException, IOException {
        Logger logger = LogManager.getLogger(Test_HW2.class);
        logger.info("opening website");
        driver.get("http://tutorialsninja.com/demo/");
        driver.manage().window().setSize(new Dimension(1004, 724));
        sanityCheck(logger);
        itemSearch(logger);
        wishList(logger);
        quantity(logger);
        currency(logger);
    }

    private void logout() {
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[5]/a")).click();
        isLogged = false;
    }

    private void loginIntoUser() {
        driver.navigate().to(loginURL);
        driver.findElement(By.id(TEXT_FIELD_NAMES_LOGIN_PAGE[0])).sendKeys("12@1.com");
        driver.findElement(By.id(TEXT_FIELD_NAMES_LOGIN_PAGE[1])).sendKeys("1234");
        isLogged = true;
    }

    private void sanityCheck(Logger logger) throws IOException {
        register(logger);
        login(logger);
        shoppingCart(logger);
    }

    private void register(Logger logger) throws IOException {
        logger.info("Register Tests");
        objExcelFile.readExcel("ReadExcels", "Data.xls", "Register");
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
                logout();
                driver.navigate().to(registerURL);
            } else if (currentURL.equals(registerURL) && i != rowCount) {
                logger.debug(row.getCell(0).getStringCellValue() + " has passed!!! - User failed to register with invalid fields!!");
            } else if (currentURL.equals(registerURL)) {
                logger.error(row.getCell(0).getStringCellValue() + " has failed!!! - User failed to register with valid fields!!");
            } else {
                logger.debug(row.getCell(0).getStringCellValue() + " has passed!!! - User registered with valid fields!!");
                isLogged = true;
            }
        }
    }

    private void login(Logger logger) throws IOException {
        logger.info("Login Tests");
        objExcelFile = new ReadExcl();
        objExcelFile.readExcel("ReadExcels", "Data.xls", "Login");
        rowCount = ReadExcl.getRowcount();
        thsSheet = ReadExcl.getsheet();
        if (isLogged) {
            logout();
        }
        logger.debug("Moving to login page");
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[2]/a")).click();
        loginURL = driver.getCurrentUrl();
        logger.debug("Login URL: " + loginURL);
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
                logout();
                logger.debug("Moving to login page");
                driver.navigate().to(loginURL);
            } else if (currentURL.equals(loginURL) && i != rowCount) {
                logger.debug(row.getCell(0).getStringCellValue() + " has passed!!! - User failed to login with invalid fields!!");
            } else if (currentURL.equals(loginURL)) {
                logger.error(row.getCell(0).getStringCellValue() + " has failed!!! - User failed to login with valid fields!!");
            } else {
                logger.debug(row.getCell(0).getStringCellValue() + " has passed!!! - User login with valid fields!!");
                isLogged = true;
            }
        }
    }


    private void shoppingCart(Logger logger) {
        logger.info("Shopping Cart Tests");
        logger.debug("Test Type: Adding item without filling required fields");
        driver.navigate().to(homePageURL);
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[3]/div/div[2]/h4/a")).click();
        driver.findElement(By.xpath("//*[@id=\"button-cart\"]")).click();
        driver.navigate().to(shoppingCartURL);
        if (driver.findElement(By.xpath("/html/body/div[2]/div/div/p")).getText().equals("Your shopping cart is empty!"))
            logger.debug("Test Type: Adding item without filling required fields - Pass!! - Missing fields item was not added to shopping cart");
        else
            logger.debug("Test Type: Adding item without filling required fields - Failed!! - Missing fields item was added to shopping cart");
        logger.debug("Test Type: Adding item that out of stock");
        driver.navigate().to(homePageURL);
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[2]/div/div[3]/button[1]")).click();
        driver.navigate().to(shoppingCartURL);
        if (driver.findElement(By.xpath("/html/body/div[2]/div[1]")).getText().equals("Products marked with *** are not available in the desired quantity or not in stock!\n" +
                "×")) {
            logger.debug("Test Type:Adding item that out of stock - Pass!! - Out of stock item was not added to shopping cart");
            driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr/td[4]/div/span/button[2]")).click();
        } else
            logger.debug("Test Type: Adding item that out of stock - Failed!! - ");

        logger.debug("Test Type: Adding item to shopping cart");
        driver.get("http://tutorialsninja.com/demo/index.php?route=product/product&path=25_28&product_id=33");
        driver.get("http://tutorialsninja.com/demo/index.php?route=product/product&path=25_28&product_id=33");
        driver.findElement(By.xpath("//*[@id=\"button-cart\"]")).click();
        driver.navigate().to(shoppingCartURL);
        if (driver.findElement(By.xpath("/html/body/div[2]/div/div/p")).getText().equals("Your shopping cart is empty!"))
            logger.debug("Test Type: Adding item to shopping cart - Failed!! - Wasn't able to add item to shopping cart");
        else
            logger.debug("Test Type: Adding item to shopping cart - Pass!! - Item added to shopping cart");
        logger.debug("Test Type: Adding item to shopping cart that already exists in the shopping cart");
        driver.get("http://tutorialsninja.com/demo/index.php?route=product/product&path=25_28&product_id=33");
        driver.findElement(By.xpath("//*[@id=\"button-cart\"]")).click();
        driver.navigate().to(shoppingCartURL);
        if (driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr/td[4]/div/input")).getAttribute("value").equals("2")) {
            logger.debug("Test Type: Adding item to shopping cart that already exists in the shopping cart - Pass!! - Quantity of item raised by 1 in shopping cart");
            driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr/td[4]/div/span/button[2]")).click();
        } else
            logger.debug("Test Type: Adding item to shopping cart that already exists in the shopping cart - Failed!! - Quantity of item was not raised by 1 in shopping cart");
    }

    private void itemSearch(Logger logger) throws IOException {
        logger.info("Item Search Tests");
        objExcelFile = new ReadExcl();
        objExcelFile.readExcel("ReadExcels", "Data.xls", "Item Search");
        rowCount = ReadExcl.getRowcount();
        thsSheet = ReadExcl.getsheet();
        logger.debug("Moving to home page");
        driver.navigate().to(homePageURL);
        Row headerRow = thsSheet.getRow(0);
        for (int i = 1; i <= rowCount; i++) {
            Row row = thsSheet.getRow(i);
            //Create a loop to print cell values in a row
            logger.debug("Test Type: " + row.getCell(0).getStringCellValue());
            //Print Excel data in console
            if (!(row.getCell(1) == null)) {
                if (row.getCell(1).getCellType() == CellType.STRING) {
                    logger.debug(headerRow.getCell(1).getStringCellValue() + ": " + row.getCell(1).getStringCellValue());
                    driver.findElement(By.xpath("//*[@id=\"search\"]/input")).sendKeys(row.getCell(1).getStringCellValue());
                } else if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                    logger.debug(headerRow.getCell(1).getStringCellValue() + ": " + row.getCell(1).getNumericCellValue());
                    driver.findElement(By.id("//*[@id=\"search\"]/input")).sendKeys("" + row.getCell(1).getNumericCellValue());
                }

            }

            if (i != 1)
                driver.findElement(By.xpath("//*[@id=\"search\"]/span/button")).click();
            currentURL = driver.getCurrentUrl();

            switch (i) {
                case 1:
                    if (currentURL.equals(homePageURL))
                        logger.debug(row.getCell(0).getStringCellValue() + " has passed!!! - Stayed at home page and no item was found!!");
                    else
                        logger.error(row.getCell(0).getStringCellValue() + " has failed!!! - Item was found when it should not have!!");
                    break;
                case 2:
                    if (driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]")).getText().equals("There is no product that matches the search criteria."))
                        logger.debug(row.getCell(0).getStringCellValue() + " has passed!!! - No item was found!!");
                    else
                        logger.error(row.getCell(0).getStringCellValue() + " has failed!!! - Item was found when it should not have!!");
                    break;
                case 3:
                    String temp = "" + row.getCell(1).getStringCellValue();
                    if (driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div[1]/div/div[2]/div[1]/h4/a")).getText().contains(temp))
                        logger.debug(row.getCell(0).getStringCellValue() + " has passed!!! - An item matching the search text was found: "
                                + driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div[1]/div/div[2]/div[1]/h4/a")).getText());
                    else
                        logger.error(row.getCell(0).getStringCellValue() + " has failed!!! - No item was found when it should have been!!");
                    break;
            }
            if (i != rowCount) {
                logger.debug("Moving to home page");
                driver.navigate().to(homePageURL);
            }
        }
    }

    private void wishList(Logger logger) throws InterruptedException {
        logger.info("Wishlist Tests");
        if (isLogged)
            logout();
        logger.info("Wishlist Tests");
        logger.debug("Test Type: Adding item to wishlist while not connected");
        driver.navigate().to(homePageURL);
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[3]/div/div[3]/button[2]")).click();
        Thread.sleep(500);
        if (!driver.findElement(By.xpath("//*[@id=\"wishlist-total\"]")).getAttribute("title").contains("(0)"))
            logger.debug("Test Type: Adding item to wishlist while not connected - Failed!! - Item was added to wishlist while not connected");
        else
            logger.debug("Test Type: Adding item to wishlist while not connected - Pass!! - Item was not added to wishlist while not connected");
        logger.debug("Login to the system to perform the rest of wishlist tests");
        loginIntoUser();
        driver.findElement(By.cssSelector("#content > div > div:nth-child(2) > div > form > input")).click();
        logger.debug("Test Type: Adding item to wishlist while connected");
        driver.navigate().to(homePageURL);
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[3]/div/div[3]/button[2]")).click();
        String wishListURL = "http://tutorialsninja.com/demo/index.php?route=account/wishlist";
        driver.navigate().to(wishListURL);
        if (driver.getCurrentUrl().equals(wishListURL)) {
            if (driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr/td[2]/a")).getText().equals("Apple Cinema 30\""))
                logger.debug("Test Type: Adding item to wishlist while connected - Pass!! - Item was added to wishlist");
            else
                logger.debug("Test Type: Adding item to wishlist while connected - Failed!! - Item was not added to wishlist");

        } else
            logger.debug("Test Type: Adding item to wishlist while connected - Failed!! - Item was not added to wishlist");

        logger.debug("Test Type: Adding item to wishlist That existing in the wishlist");
        driver.navigate().to(homePageURL);
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[3]/div/div[3]/button[2]")).click();
        if (driver.findElement(By.xpath("//*[@id=\"wishlist-total\"]")).getAttribute("title").contains("(1)"))
            logger.debug("Test Type: Adding item to wishlist That existing in the wishlist - Pass!! - Item was not added to wishlist, as it was already there");
        else
            logger.debug("Test Type: Adding item to wishlist That existing in the wishlist - Failed!! - Item was added to wishlist, despite it already being there");

    }

    private void quantity(Logger logger)  {
        logger.info("Quantity Tests");
        if (!isLogged) {
            loginIntoUser();
        }
        String tempURL;
        logger.debug("Test Type: Adding invalid quantity of items");
        driver.navigate().to(homePageURL);
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div/div[2]/h4/a")).click();
        tempURL = driver.getCurrentUrl();
        driver.findElement(By.id("input-quantity")).clear();
        driver.findElement(By.id("input-quantity")).sendKeys("s");
        driver.findElement(By.xpath("//*[@id=\"button-cart\"]")).click();
        driver.navigate().to(shoppingCartURL);
        if (driver.findElement(By.xpath("/html/body/div[2]/div/div/p")).getText().equals("Your shopping cart is empty!"))
            logger.debug("Test Type: Adding invalid quantity of items - Pass!! - Did not add items of invalid quantity to shopping cart");
        else
            logger.debug("Test Type: Adding invalid quantity of items - Failed!! - Added items of invalid quantity to shopping cart");
        logger.debug("Test Type: Adding valid quantity of items");
        driver.navigate().to(tempURL);
        driver.findElement(By.id("input-quantity")).clear();
        driver.findElement(By.id("input-quantity")).sendKeys("4");
        driver.findElement(By.xpath("//*[@id=\"button-cart\"]")).click();
        driver.navigate().to(shoppingCartURL);
        if (driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr/td[2]/a")).getText().equals("MacBook")) {
            if (driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr/td[4]/div/input")).getAttribute("value").equals("4")) {
                logger.debug("Test Type: Adding valid quantity of items - Pass!! - Added items of valid quantity to shopping cart");
            } else
                logger.debug("Test Type: Adding valid quantity of items - Failed!! - Failed to add items of valid quantity to shopping cart");
        } else
            logger.debug("Test Type: Adding valid quantity of items - Failed!! - Failed to add items of valid quantity to shopping cart");

        logger.debug("Test Type: Adding valid quantity of items but greater then the current stock");
        driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr/td[4]/div/input")).clear();
        driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr/td[4]/div/input")).sendKeys("1000");
        driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr/td[4]/div/span/button[1]")).click();
        driver.navigate().to(shoppingCartURL);
        if (driver.findElement(By.xpath("/html/body/div[2]/div[1]")).getText().equals("Products marked with *** are not available in the desired quantity or not in stock!\n" +
                "×"))
            logger.debug("Test Type:Adding valid quantity of items but greater then the current stock - Pass!! - Adds items to shopping cart with warning of unavailable quantity");
        else {
            logger.debug("Test Type: Adding valid quantity of items but greater then the current stock - Failed!! - Adds items to shopping cart without warning of unavailable quantity");
        }
        driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr/td[4]/div/span/button[2]")).click();
    }

    private void currency(Logger logger){
        logger.info("Currency Tests");
        if (!isLogged)
            loginIntoUser();
        driver.navigate().to(homePageURL);
        for (int i = 0; i < CURRENCY.length; i++) {
            logger.debug("Test Type: Change currency to " + CURRENCY[i]);
            driver.findElement(By.xpath("//*[@id=\"form-currency\"]/div/button")).click();
            driver.findElement(By.xpath("//*[@id=\"form-currency\"]/div/ul/li[" + (i + 1) + "]/button")).click();
            if (driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div/div[2]/p[2]")).getText().contains(PRICES[i]))
                logger.debug("Test Type: Change currency to " + CURRENCY[i] + " - Pass!! - Price shown in correct currency");
            else
                logger.debug("Test Type: Change currency to " + CURRENCY[i] + " - Failed!! - Price shown in wrong currency");
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