package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    public WebDriver wd;

    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;

    public void init() {
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wd.get("http://localhost/addressbook/");
        groupHelper = new GroupHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        login();
    }

    public void login() {
      login("admin", "secret");
    }

    public void login(String userName, String userPassword) {
      wd.findElement(By.name("user")).clear();
      wd.findElement(By.name("user")).sendKeys(userName);
      wd.findElement(By.name("pass")).clear();
      wd.findElement(By.name("pass")).sendKeys(userPassword);
      wd.findElement(By.xpath("//input[@value='Login']")).click();
    }

    public void stop() {
        wd.quit();
    }

    public void logout() {
      wd.findElement(By.linkText("Logout")).click();
    }

    public GroupHelper getGroupHelper() {
        return groupHelper;
    }

    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }
}
