package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactModification() {

        String groupName = app.getGroupHelper().defineGroupName();
        app.getNavigationHelper().backHome();

        if (! app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Mike", "Jordan", "+33111222333",
                    "terry.p@google.com", "USA, Montana", groupName));
        }

        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().acceptWarningOk();
        app.getNavigationHelper().backToHomePage();
        app.getSessionHelper().logout();
    }
}
