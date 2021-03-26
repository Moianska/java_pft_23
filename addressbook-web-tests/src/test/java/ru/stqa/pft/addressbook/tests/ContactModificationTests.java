package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
    @Test (enabled = false)
    public void testContactModification() {

        String groupName = app.getGroupHelper().defineGroupName();
        app.getNavigationHelper().backHome();

        if (! app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Mike", "Jordan", "+33111222333",
                    "terry.p@google.com", "USA, Montana", groupName));
        }

        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().initContactEditing(before.size()+1);
        ContactData contact = new ContactData(before.get(before.size() - 1).getId(),"Price", "One", "+33111222333",
                "terry.p@google.com", "USA, Montana", null);
        app.getContactHelper().fillNewContactForm(contact, false);
        app.getContactHelper().updateEditedContact();
        app.getNavigationHelper().backToHomePage();
        app.getContactHelper().timeOut(1);
        List<ContactData> after = app.getContactHelper().getContactList();
        before.remove(before.size() - 1);
        before.add(contact);

        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before, after);

        app.getSessionHelper().logout();
    }
}
