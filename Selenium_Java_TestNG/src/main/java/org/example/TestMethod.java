package org.example;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import  org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

@Listeners(ExtentReportManager.class)
public class TestMethod {
    public WebDriver driver;
    private static Logger log=LogManager.getLogger(TestMethod.class);
    GenericFunction gf;
    PageObjects obj;
    String username;
    String data;
    String arr[];
    String accnum;
    String val[];
    String val1;
    String parr[];
    public String accountNumber;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeClass
    public void Initialize() throws IOException, InterruptedException {
        log.info("Before Start");
        String path = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver",path+"\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        //options.addArguments("user-agent=YOUR_USER_AGENT");
        options.addArguments("--incognito");

        driver=new ChromeDriver(options);
        log.info("Browser Launched");
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        log.info("URL Launched");
        driver.manage().window().maximize();
        log.info("Maximized Screen");

        ///////////////////////////////////////////////////////////
        gf=new GenericFunction();
        obj=new PageObjects(driver);
        log.info("on test");
        //GenericFunction gf=new GenericFunction();
        data = gf.getExcelDataFromCell("Data","Sheet1",0,0);
        arr=data.split(";");
        username = arr[7]+gf.randomKeyGenerator();
        obj.enterRegisteationDetails(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6],username,arr[8],arr[9],arr[10]);
        //String message1 = gf.getExcelDataFromCell("Data","Sheet1",0,1);
        String message2 = gf.getExcelDataFromCell("Data","Sheet1",0,2);
        //obj.ValidateMessageOnScreen(message1);
        obj.ValidateMessageOnScreen(message2);
        log.info("Registration SuccessFull");
        obj.logout();
    }
    @BeforeMethod
    public void login() throws InterruptedException {

        obj.login(username,arr[9]);


    }
    @AfterMethod
    public void logout(){
        obj.logout();
    }
    @Test(priority = -1)
    public void LoginTest() throws IOException, InterruptedException {
        accountNumber=obj.getAccountNumber();
        Assert.assertEquals(true,driver.findElement(By.xpath("//h1[contains(text(),'Accounts Overview')]")).isDisplayed());
        log.info("Login SuccessFull");
    }

    @Test(priority = 1)
    public  void updateDetailsTest() throws IOException, InterruptedException {
        obj.updateUserName(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6],arr[7]);
        obj.ValidateMessageOnScreen(gf.getExcelDataFromCell("Data","Sheet1",0,9));
        log.info("Updation SuccessFull");
    }

    @Test(priority = 2)
    public void addNewAccountTest() throws IOException, InterruptedException {
        accnum=obj.addNewAccount(gf.getExcelDataFromCell("Data","Sheet1",0,10));
        obj.ValidateMessageOnScreen(gf.getExcelDataFromCell("Data","Sheet1",0,11));
        log.info("New Account Addition SuccessFull");
    }
    @Test(priority = 3)
    public void transferFundsSuccessfull() throws IOException, InterruptedException {
        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,8));
        obj.validateAllAccountDetails(accnum);
        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,12));
        obj.transferFunds("100",accountNumber,accnum);
        obj.ValidateMessageOnScreen(gf.getExcelDataFromCell("Data","Sheet1",0,12));
        log.info("Transfer SuccessFull");
    }

    @Test(priority = 4)
    public void transactionDetailsVerification() throws IOException, InterruptedException {
        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,8));
        obj.clickOnElement(accnum,"1");
        Thread.sleep(4000);
        obj.selectAccountActivity(Calendar.getInstance().getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault()),gf.getExcelDataFromCell("Data","Sheet1",0,14));
        obj.clickOnElement(gf.getExcelDataFromCell("Data","Sheet1",0,15),"1");
        val=gf.getExcelDataFromCell("Data","Sheet1",0,16).split(";");
        obj.validateTransactionDetails(val[0],gf.returnTodaysDate(val[1]),val[2],val[3],val[4]);
        log.info("Transaction details verified");
    }

    @Test(priority = 5)
    public void billPayment() throws IOException, InterruptedException {
        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,17));
        val1 = gf.getExcelDataFromCell("Data","Sheet1",0,0);
        parr=val1.split(";");
        obj.BillPayment(parr[0],parr[1],parr[2],parr[3],parr[4],parr[5],"12345","12345","100",accountNumber);
        obj.ValidateMessageOnScreen(gf.getExcelDataFromCell("Data","Sheet1",0,17));
        log.info("Bill payment suceess");
    }

    @Test(priority = 6)
    public void negativeBillPaymentScenarioes() throws IOException, InterruptedException {
        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,17));
        obj.verifyBillPaymentsNegativeScenarioes(0,5,7,accountNumber);
        log.info("Bill Payment negative scenarioes passed");
    }
    @Test(priority = 7)
    public void LoanRequestNegativeScenario() throws IOException, InterruptedException {
        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,19));
        obj.RequestLoan("$$","2",accnum);
        obj.NoMessageOnScreen(gf.getExcelDataFromCell("Data","Sheet1",0,20));
        log.info("Loan Unsuccess");
    }

    @Test(priority = 8)
    public void FindTransactionByDate() throws IOException, InterruptedException {
        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,21));
        obj.FindTransactionByDate(gf.returnTodaysDate(val[1]));
        obj.clickOnElement(gf.getExcelDataFromCell("Data","Sheet1",0,15),"2");
        obj.validateTransactionDetails(val[0],gf.returnTodaysDate(val[1]),val[2],val[3],val[4]);
        log.info("transaction validation done");
    }

    @AfterClass
    public void teardown(){
        driver.quit();
    }
//    public void EndToEndTestCase() throws IOException, InterruptedException {
//        log.info("on test");
//        GenericFunction gf=new GenericFunction();
//        String data = gf.getExcelDataFromCell("Data","Sheet1",0,0);
//        String arr[]=data.split(";");
//        PageObjects obj=new PageObjects(driver);
//        String username = arr[7]+gf.randomKeyGenerator();
//        obj.enterRegisteationDetails(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6],username,arr[8],arr[9],arr[10]);
//        //String message1 = gf.getExcelDataFromCell("Data","Sheet1",0,1);
//        String message2 = gf.getExcelDataFromCell("Data","Sheet1",0,2);
//        //obj.ValidateMessageOnScreen(message1);
//        obj.ValidateMessageOnScreen(message2);
//        log.info("Registration SuccessFull");
//
//
//        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,8));
//        accountNumber=obj.getAccountNumber();
//        obj.logout();
//        obj.login(username,arr[9]);
//        log.info("Login SuccessFull");
//
//
//        obj.updateUserName(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6],arr[7]);
//        obj.ValidateMessageOnScreen(gf.getExcelDataFromCell("Data","Sheet1",0,9));
//        log.info("Updation SuccessFull");
//
//
//        String accnum=obj.addNewAccount(gf.getExcelDataFromCell("Data","Sheet1",0,10));
//        obj.ValidateMessageOnScreen(gf.getExcelDataFromCell("Data","Sheet1",0,11));
//        log.info("New Account Addition SuccessFull");
//
//
//        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,8));
//        obj.validateAllAccountDetails(accnum);
//        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,12));
//        obj.transferFunds("100",accountNumber,accnum);
//        obj.ValidateMessageOnScreen(gf.getExcelDataFromCell("Data","Sheet1",0,12));
//        log.info("Transfer SuccessFull");
//
//
//        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,8));
//        obj.clickOnElement(accnum,"1");
//        obj.selectAccountActivity(Calendar.getInstance().getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault()),gf.getExcelDataFromCell("Data","Sheet1",0,13));
//        obj.clickOnElement(gf.getExcelDataFromCell("Data","Sheet1",0,15),"1");
//        String val[]=gf.getExcelDataFromCell("Data","Sheet1",0,16).split(";");
//        obj.validateTransactionDetails(val[0],gf.returnTodaysDate(val[1]),val[2],val[3],val[4]);
//        log.info("Transaction details verified");
//
//
//        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,16));
//        String val1 = gf.getExcelDataFromCell("Data","Sheet1",0,0);
//        String parr[]=val1.split(";");
//        obj.BillPayment(parr[0],parr[1],parr[2],parr[3],parr[4],parr[5],"12345","12345","100",accountNumber);
//        obj.ValidateMessageOnScreen(gf.getExcelDataFromCell("Data","Sheet1",0,17));
//        log.info("Bill payment suceess");
//
//
//        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,16));
//        obj.verifyBillPaymentsNegativeScenarioes(0,5,7,accountNumber);
//        log.info("Bill Payment negative scenarioes passed");
//
//        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,18));
//        obj.RequestLoan("$$","2",accnum);
//        obj.NoMessageOnScreen(gf.getExcelDataFromCell("Data","Sheet1",0,19));
//        log.info("Loan Unsuccess");
//
//
//        obj.navigateToLink(gf.getExcelDataFromCell("Data","Sheet1",0,20));
//        obj.FindTransactionByDate(gf.returnTodaysDate(val[1]));
//        obj.clickOnElement(gf.getExcelDataFromCell("Data","Sheet1",0,15),"2");
//        obj.validateTransactionDetails(val[0],gf.returnTodaysDate(val[1]),val[2],val[3],val[4]);
//        log.info("transaction validation done");
//
//
//        obj.logout();
//        log.info("Logout Success");
//    }
}
