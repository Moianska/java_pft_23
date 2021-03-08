package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {
        app.getContactHelper().initContactEditing();
        app.getContactHelper().fillNewContactForm(new ContactData("Mike", "Jordan", "+33111222333", "terry.p@google.com", "USA, Montana"));
        app.getContactHelper().updateEditedContact();
        app.getNavigationHelper().backToHomePage();
        app.getSessionHelper().logout();
    }
}
