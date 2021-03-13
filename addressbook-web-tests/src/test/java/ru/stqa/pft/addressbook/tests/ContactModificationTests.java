package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactEditing();
        app.getContactHelper().fillNewContactForm(new ContactData("Mike", "Jordan", "+33111222333",
                "terry.p@google.com", "USA, Montana", null), false);
        app.getContactHelper().updateEditedContact();
        app.getNavigationHelper().backToHomePage();
        app.getSessionHelper().logout();
    }
}
