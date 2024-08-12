package org.example;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.util.List;

public class PageObjects {
    public WebDriver driver;
    //Constructors
    PageObjects(WebDriver driver){
        this.driver=driver;
    }
    //XPATHS
    String regitserBtn = "//a[contains(text(),'Register')]";
    String FirstName = "//input[@id='customer.firstName']";
    String LastName = "//input[@id='customer.lastName']";
    String Address = "//input[@id='customer.address.street']";
    String City = "//input[@id='customer.address.city']";
    String State = "//input[@id='customer.address.state']";
    String Zipcode = "//input[@id='customer.address.zipCode']";
    String Phone = "//input[@id='customer.phoneNumber']";
    String SSN = "//input[@id='customer.ssn']";
    String Username = "//input[@id='customer.username']";
    String password = "//input[@id='customer.password']";
    String confirmpassword = "//input[@id='repeatedPassword']";
    String SubmitBtn = "//input[@type='submit']";
    String Register = "//input[@value='Register']";
    String LoginUserName="//input[@name='username']";
    String LoginPassword="//input[@name='password']";
    String LogoutLink = "//a[contains(text(),'Log Out')]";
    String updateLink = "//a[contains(text(),'Update Contact Info')]";
    String opennewaccountlink = "//a[contains(text(),'Open New Account')]";
    String openNewAccountBtn = "//input[@value='Open New Account']";
    String newAccountNum = "//a[@id='newAccountId']";
    String amountTxtBox="//input[@id='amount']";
    String FromAccTxtBox = "//select[@id='fromAccountId']";
    String ToAccTxtBox = "//select[@id='toAccountId']";
    String monthDrpDwn = "//select[@id='month']";
    String typeDrpDwn = "//select[@id='transactionType']";


    public void enterRegisteationDetails(String fname,String Lname,String Address,String City,String State,String Zip,String Phone,String Usename,String ssn,String Password,String Cpassword){
        driver.findElement(By.xpath(regitserBtn)).click();
        driver.findElement(By.xpath(FirstName)).sendKeys(fname);
        driver.findElement(By.xpath(LastName)).sendKeys(Lname);
        driver.findElement(By.xpath(this.Address)).sendKeys(Address);
        driver.findElement(By.xpath(this.City)).sendKeys(City);
        driver.findElement(By.xpath(this.State)).sendKeys(State);
        driver.findElement(By.xpath(this.Zipcode)).sendKeys(Zip);
        driver.findElement(By.xpath(this.Phone)).sendKeys(Phone);
        driver.findElement(By.xpath(this.SSN)).sendKeys(ssn);
        driver.findElement(By.xpath(this.Username)).sendKeys(Usename);
        driver.findElement(By.xpath(this.password)).sendKeys(Password);
        driver.findElement(By.xpath(this.confirmpassword)).sendKeys(Cpassword);
        driver.findElement(By.xpath(this.Register)).click();

    }

    public void ValidateMessageOnScreen(String message) throws InterruptedException {
        Thread.sleep(2000);
        Assert.assertEquals(true,driver.findElement(By.xpath("//*[contains(text(),'"+message+"')]")).isDisplayed() || driver.findElements(By.xpath("//*[contains(text(),'"+message+"')]")).size()>0);
        System.out.println("Pass");
    }
    public void NoMessageOnScreen(String message) throws InterruptedException {
        Thread.sleep(2000);
        boolean flag=false;
        try {
            flag=driver.findElement(By.xpath("//*[contains(text(),'"+message+"')]")).isDisplayed();
        }catch (Exception e){
            flag=false;
            System.out.println("Element Not found");
        }
        Assert.assertEquals(false,flag);
        System.out.println("Pass");
    }
    public void login(String username,String password){
        driver.findElement(By.xpath(this.LoginUserName)).sendKeys(username);
        driver.findElement(By.xpath(this.LoginPassword)).sendKeys(password);
        driver.findElement(By.xpath(this.SubmitBtn)).click();
    }

    public void logout(){
        driver.findElement(By.xpath(this.LogoutLink)).click();
    }
    String updateprofilebtn="//input[@value='Update Profile']";
    public void updateUserName(String fname,String Lname,String Address,String City,String State,String Zip,String Phone,String ssn){
        driver.findElement(By.xpath(updateLink)).click();
        driver.findElement(By.xpath(FirstName)).sendKeys(fname);
        driver.findElement(By.xpath(LastName)).sendKeys(Lname);
        driver.findElement(By.xpath(this.Address)).sendKeys(Address);
        driver.findElement(By.xpath(this.City)).sendKeys(City);
        driver.findElement(By.xpath(this.State)).sendKeys(State);
        driver.findElement(By.xpath(this.Zipcode)).sendKeys(Zip);
        driver.findElement(By.xpath(this.Phone)).sendKeys(Phone);
        driver.findElement(By.xpath(updateprofilebtn)).click();
    }

    public String addNewAccount(String accountType) throws InterruptedException {
        driver.findElement(By.xpath(opennewaccountlink)).click();
        Select sel = new Select(driver.findElement(By.xpath("//select[@id='type']")));
        sel.selectByVisibleText(accountType);
        Thread.sleep(3000);
        driver.findElement(By.xpath(openNewAccountBtn)).click();
        String accnum="";
        try {
            Thread.sleep(3000);
            accnum = driver.findElement(By.xpath(newAccountNum)).getText();
        }catch (Exception e){
            System.out.println("Account number not found");
        }
        return accnum;
    }
    public void navigateToLink(String link){
        driver.findElement(By.xpath("//a[contains(text(),'"+link+"')]")).click();
    }
    public void validateAllAccountDetails(String accountNum) throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> list=driver.findElements(By.xpath("//a[contains(text(),'"+accountNum+"')]/ancestor::tr/td"));
        Assert.assertEquals(accountNum,list.get(0).getText());
        Thread.sleep(1000);
        Assert.assertEquals("$100.00",list.get(1).getText());
        Thread.sleep(1000);
        Assert.assertEquals("$100.00",list.get(2).getText());
    }
    String transferFundsBtn = "//input[@value='Transfer']";
    public void transferFunds(String amount,String from,String to) throws InterruptedException {
        driver.findElement(By.xpath(amountTxtBox)).sendKeys(amount);
        Thread.sleep(3000);
        Select sel1=new Select(driver.findElement(By.xpath(FromAccTxtBox)));
        Select sel2 = new Select(driver.findElement(By.xpath(ToAccTxtBox)));
        sel1.selectByVisibleText(from);
        sel2.selectByVisibleText(to);
        Thread.sleep(2000);
        driver.findElement(By.xpath(transferFundsBtn)).click();
    }
    public String getAccountNumber() throws InterruptedException {
        Thread.sleep(5000);
        return driver.findElement(By.xpath("(//tbody/tr[1]/td)[1]")).getText();
    }
    public void clickOnElement(String txt,String ind) throws InterruptedException {
        Thread.sleep(3500);
        driver.findElement(By.xpath("(//*[contains(text(),'"+txt+"')])["+ind+"]")).click();
    }
    public void selectAccountActivity(String month,String type){
        Select sel1 = new Select(driver.findElement(By.xpath(monthDrpDwn)));
        Select sel2 = new Select(driver.findElement(By.xpath(typeDrpDwn)));
        sel1.selectByVisibleText(month);
        sel2.selectByVisibleText(type);
    }
    public  void validateTransactionDetails(String TransId,String Date, String Desc, String Type, String amt) throws InterruptedException {
        ValidateMessageOnScreen(TransId);
        ValidateMessageOnScreen(Date);
        ValidateMessageOnScreen(Desc);
        ValidateMessageOnScreen(Type);
        ValidateMessageOnScreen(amt);
    }
    String payeename= "//input[@name='payee.name']";
    String paddress ="//input[@name='payee.address.street']";
    String pcity = "//input[@name='payee.address.city']";
    String pstate = "//input[@name='payee.address.state']";
    String zip ="//input[@name='payee.address.zipCode']";
    String pphone ="//input[@name='payee.phoneNumber']";
    String payacc="//input[@name='payee.accountNumber']";
    String vpayacc="//input[@name='verifyAccount']";
    String payamt="//input[@name='amount']";
    String accn="//select[@name='fromAccountId']";
    String sendpaymentbtn = "//input[@value='Send Payment']";


    public void BillPayment(String payee,String address,String city,String state,String zip, String phone,String acc,String vacc,String amt,String dacc) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath(payeename)).sendKeys(payee);
        Thread.sleep(1000);
        driver.findElement(By.xpath(paddress)).sendKeys(address);
        Thread.sleep(1000);
        driver.findElement(By.xpath(pcity)).sendKeys(city);
        Thread.sleep(1000);
        driver.findElement(By.xpath(pstate)).sendKeys(state);
        Thread.sleep(1000);
        driver.findElement(By.xpath(this.zip)).sendKeys(zip);
        Thread.sleep(1000);
        driver.findElement(By.xpath(pphone)).sendKeys(phone);
        Thread.sleep(1000);
        driver.findElement(By.xpath(payacc)).sendKeys(acc);
        Thread.sleep(1000);
        driver.findElement(By.xpath(vpayacc)).sendKeys(vacc);
        Thread.sleep(1000);
        driver.findElement(By.xpath(payamt)).sendKeys(amt);
        Thread.sleep(1000);
        Select sel = new Select(driver.findElement(By.xpath(accn)));
        Thread.sleep(1000);
        sel.selectByVisibleText(dacc);
        Thread.sleep(1000);
        driver.findElement(By.xpath(sendpaymentbtn)).click();
    }

    public  void verifyBillPaymentsNegativeScenarioes(int row,int col1,int col2,String accountNumber) throws IOException, InterruptedException {
        GenericFunction gf= new GenericFunction();
        for(int i=col1;i<=col2;i++){
            String arr[]=gf.getExcelDataFromCell("Data","Sheet1",row,i).split(";");
            this.BillPayment(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6],arr[7],arr[8],accountNumber);
            NoMessageOnScreen("Bill Payment Complete");
            this.navigateToLink("Bill Pay");
        }
    }
    String Lamt = "//input[@id='amount']";
    String Dwnamt ="//input[@id='downPayment']";
    String Accnum="//select[@id='fromAccountId']";
    String ReqLoanBtn ="//input[@value='Apply Now']";

    public  void RequestLoan(String LoanAmt,String DownPay,String Acc){
        driver.findElement(By.xpath(Lamt)).sendKeys(LoanAmt);
        driver.findElement(By.xpath(Dwnamt)).sendKeys(DownPay);
        Select sel = new Select(driver.findElement(By.xpath(Accnum)));
        sel.selectByVisibleText(Acc);
        driver.findElement(By.xpath(ReqLoanBtn)).click();
    }


    public  void validateLoanStatus(String status,String errorMessage) throws InterruptedException {
        ValidateMessageOnScreen(status);
        ValidateMessageOnScreen(errorMessage);
    }
    String findByDate="//input[@id='transactionDate']";
    String ButtonFindTransactionByDate="//button[@id='findByDate']";
    public  void FindTransactionByDate(String date){
        driver.findElement(By.xpath(findByDate)).sendKeys(date);
        driver.findElement(By.xpath(ButtonFindTransactionByDate)).click();
    }
    //methods

}
