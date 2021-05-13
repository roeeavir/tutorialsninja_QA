package test1;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcl {

    public static int rowCount;
    public static Sheet guru99Sheet;

    public void readExcel(String filePath,String fileName,String sheetName) throws IOException{

        //Create an object of File class to open xlsx file

        File file =    new File(filePath+"\\"+fileName);

        //Create an object of FileInputStream class to read excel file

        FileInputStream inputStream = new FileInputStream(file);

        Workbook guru99Workbook = null;

        //Find the file extension by splitting file name in substring  and getting only extension name

        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //Check condition if the file is xlsx file

        //  if(fileExtensionName.equals(".xlsx")){

        //If it is xlsx file then create object of XSSFWorkbook class

        // guru99Workbook = new Workbook(inputStream);

        //  }

        //Check condition if the file is xls file

        if(fileExtensionName.equals(".xls")){

            //If it is xls file then create object of HSSFWorkbook class

            guru99Workbook = new HSSFWorkbook(inputStream);

        }

        //Read sheet inside the workbook by its name

        guru99Sheet = guru99Workbook.getSheet(sheetName);

        //Find number of rows in excel file

        rowCount = guru99Sheet.getLastRowNum()-guru99Sheet.getFirstRowNum();


    }



    public static int getRowcount(){

        return rowCount;}


    public static Sheet getsheet(){
        return guru99Sheet;}

    public static class yese {
        /**
         * @param args
         */
        public static WebDriver driver;

        public static void main(String[] args) {

            // TODO Auto-generated method stub
            System.setProperty("webdriver.chrome.driver", "D:\\Afeka\\chromedriver.exe");


            driver = new ChromeDriver();


            //Logger log = Logger.getLogger("devpinoyLogger");
            Logger logger= LogManager.getLogger(yese.class);

            driver.get("http://healthunify.com/bmicalculator/");
            logger.info("opening webiste");
            //  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            logger.debug("entring weight");
            driver.findElement(By.name("wg")).sendKeys("87");
            logger.debug("selecting kilograms");
            driver.findElement(By.name("opt1")).sendKeys("kilograms");
            logger.debug("selecting height in feet");
            driver.findElement(By.name("opt2")).sendKeys("5");
            logger.debug("selecting height in inchs");
            driver.findElement(By.name("opt3")).sendKeys("10");
            logger.debug("Clicking on calculate");
            driver.findElement(By.name("cc")).click();

            logger.debug("Getting SIUnit value");
            String SIUnit = driver.findElement(By.name("si")).getAttribute("value");
            logger.debug("Getting USUnit value");
            String USUnit = driver.findElement(By.name("us")).getAttribute("value");
            logger.debug("Getting UKUnit value");
            String UKUnit = driver.findElement(By.name("uk")).getAttribute("value");
            logger.debug("Getting overall description");
            String note = driver.findElement(By.name("desc")).getAttribute("value");

            System.out.println("SIUnit = " + SIUnit);
            System.out.println("USUnit = " + USUnit);
            System.out.println("UKUnit = " + UKUnit);
            System.out.println("note = " + note);
            driver.quit();
        }
    }
}