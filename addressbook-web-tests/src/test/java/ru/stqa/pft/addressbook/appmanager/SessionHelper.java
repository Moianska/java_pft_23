package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase{

    public SessionHelper(WebDriver wd) {
        super(wd);
    }

    public void login(String userName, String userPassword) {
        type(By.name("user"),userName);
        type(By.name("pass"),userPassword);
        click(By.xpath("//input[@value='Login']"));
    }
}
