package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.HelperBase;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion() {

        String groupName = app.getGroupHelper().defineGroupName();
        app.getNavigationHelper().backHome();

        if (! app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Mike", "Jordan", "+33111222333",
                    "terry.p@google.com", "USA, Montana", groupName));
        }

        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().acceptWarningOk();
        app.getNavigationHelper().backHome();
        app.getContactHelper().tumeOut(1);

        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);

        app.getSessionHelper().logout();
    }
}
