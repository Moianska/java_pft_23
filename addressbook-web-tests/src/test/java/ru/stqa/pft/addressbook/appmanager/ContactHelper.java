package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ContactHelper extends HelperBase {
    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void fillNewContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("address"), contactData.getAddress());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
     }

    public void submitNewContactForm() {
        click(By.name("submit"));
    }


    public void updateEditedContact() {
        click(By.name("update"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[id='" + id + "']")).click();
    }

    public void initContactEditing(int index) {
        int id = index +1;
        wd.findElement(By.xpath("//tr["+id+"]/td[8]/a/img")).click();
    }

    public void acceptWarningOk() {
        wd.switchTo().alert().accept();
        /*wd.findElement(By.cssSelector("div.msgbox"));*/
    }

    public void backHome() {
        click(By.linkText("home"));

    }

    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void create(ContactData contact) {
       initContactCreation();
        fillNewContactForm(contact, true);
        submitNewContactForm();
        backToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        timeOut(1);
        acceptWarningOk();
        backHome();
        timeOut(3);
    }

    public void modify(ContactData finalContact) {
        selectContactById(finalContact.getId());
        initContactEditing(finalContact.getId());
        fillNewContactForm(finalContact, false);
        updateEditedContact();
        backToHomePage();
        timeOut(2);
    }

    private void backToHomePage() {
        if (isElementPresent(By.id("maintable"))) {
            return;
        } else {
            click(By.linkText("home page"));
        }
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.xpath(".//*[@id='maintable']/tbody/tr"));
        elements.remove(0);

        for (WebElement element: elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));

            String first_name = cells.get(2).getText();
            String last_name = cells.get(1).getText();

            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData().withId(id).withName(first_name).withLastName(last_name);
            contacts.add(contact);
        }
        return contacts;
    }

    public Contacts all() {
        Contacts contacts;
        contacts = new Contacts();

        List<WebElement> elements = wd.findElements(By.xpath(".//*[@id='maintable']/tbody/tr"));
        elements.remove(0);

        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));

            String first_name = cells.get(2).getText();
            String last_name = cells.get(1).getText();
            String allPhones = cells.get(5).getText();
            String allEmails = cells.get(4).getText();
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));

            ContactData contact = new ContactData().withId(id).withName(first_name).withLastName(last_name)
                    .withAllPhones(allPhones).withAllEmails(allEmails);
            contacts.add(contact);
        }
        return contacts;
    }

    public void timeOut(int i) {
        wd.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        selectContactById(contact.getId());
        initContactEditing(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        backHome();
        return new ContactData().withId(contact.getId()).withName(firstname).withLastName(lastName)
                .withMobilePhone(mobile).withHomePhone(home).withWorkPhone(work)
                .withEmail(email).withEmail2(email2).withEmail3(email3);

    }
}
