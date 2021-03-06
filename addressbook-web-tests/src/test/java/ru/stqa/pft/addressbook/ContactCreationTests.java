package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ContactCreationTests {
  private WebDriver wd;

  @BeforeClass(alwaysRun = true)
  public void setUp() {
    wd = new FirefoxDriver();
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    wd.get("http://localhost/addressbook/index.php");
    login("admin", "secret");
  }

  private void login(String userName, String userPassword) {
    wd.findElement(By.name("user")).clear();
    wd.findElement(By.name("user")).sendKeys(userName);
    wd.findElement(By.name("pass")).clear();
    wd.findElement(By.name("pass")).sendKeys(userPassword);
    wd.findElement(By.xpath("//input[@value='Login']")).click();
  }

  @Test
  public void testUntitledTestCase() {
    initContectCreation();
    fillNewContactForm(new ContactData("Terry", "Pratchet", "+33111222333", "terry.p@google.com", "USA, Montana"));
    submitNewContactForm();
    backToHomePage();
    logout();
  }

  private void backToHomePage() {
    wd.findElement(By.linkText("home page")).click();
  }

  private void submitNewContactForm() {
    wd.findElement(By.name("submit")).click();
  }

  private void fillNewContactForm(ContactData contactData) {
    wd.findElement(By.name("firstname")).clear();
    wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstName());
    wd.findElement(By.name("lastname")).clear();
    wd.findElement(By.name("lastname")).sendKeys(contactData.getLastName());
    wd.findElement(By.name("mobile")).clear();
    wd.findElement(By.name("mobile")).sendKeys(contactData.getMobilePhone());
    wd.findElement(By.name("email")).clear();
    wd.findElement(By.name("email")).sendKeys(contactData.getEmail());
    wd.findElement(By.name("address")).clear();
    wd.findElement(By.name("address")).sendKeys(contactData.getAddress());
  }

  private void initContectCreation() {
    wd.findElement(By.linkText("add new")).click();
  }

  private void logout() {
    wd.findElement(By.linkText("Logout")).click();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    wd.quit();
  }
}
