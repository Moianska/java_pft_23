package ru.stqa.pft.mantis.appmanager;

import javafx.animation.AnimationTimer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.Select;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private final Properties properties;
    private WebDriver wd;
    private FtpHelper ftp;
    private MailHelper mailHelper;
    private JamesHelper jamesHelper;
    private DbHelper dbHelper;
    private SessionHelper sessionHelper;

    private String browser;
    private RegistrationHelper registrationHelper;
    private UserHelper userHelper;

    public ApplicationManager(String browser) throws IOException {

        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {

        String target = System.getProperty("target", "local");
        properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
        dbHelper = new DbHelper();
        }

    public void stop() {

        if (wd != null) {
            wd.quit();}
    }

    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public RegistrationHelper registration() {
        if (registrationHelper == null) {
        registrationHelper = new RegistrationHelper(this);}
        return registrationHelper;
    }

    public UserHelper userHelper() {
        if (userHelper == null) {
            userHelper = new UserHelper(this);}
        return userHelper;
    }

    public SessionHelper sessionHelper() {
        if (sessionHelper == null) {
            sessionHelper = new SessionHelper(this);
        }
        return sessionHelper;
    }

    public FtpHelper ftp() {
        if (ftp == null) {
        ftp = new FtpHelper(this);
        }
        return ftp;
    }

    public MailHelper mail() {
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    public WebDriver getDriver() {
        if (wd == null) {
            if (browser.equals(BrowserType.FIREFOX)) {
                wd = new FirefoxDriver();
            } else if (browser.equals(BrowserType.CHROME)) {
                wd = new ChromeDriver();
            } else if (browser.equals(BrowserType.IE)) {
                wd = new InternetExplorerDriver();
            } else {
                /*wd = new FirefoxDriver();*/
                wd = new InternetExplorerDriver();
            }
            wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            wd.get(properties.getProperty("web.baseUrl"));
        }
        return wd;
    }

    public JamesHelper james() {
        if (jamesHelper == null) {
            jamesHelper = new JamesHelper(this);
        }
        return jamesHelper;
    }

    public DbHelper db() {
         return dbHelper;
    }

    public void openManageTab() {
        click(By.cssSelector(String.format("a[href='/mantisbt-2.25.0/manage_overview_page.php']")));
        click(By.cssSelector(String.format("a[href='/mantisbt-2.25.0/manage_user_page.php']")));
    }

    private void click(By locator) {
        wd.findElement(locator).click();
    }
}
