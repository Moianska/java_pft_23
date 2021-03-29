package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
    @Test (enabled = false)
    public void testContactModification() {

        String groupName = app.group().defineGroupName();
        app.goTo().backHome();

        if (! app.contact().isThereAContact()){
            app.contact().create(new ContactData().withName ("Mike").withLastName("Jordan")
                    .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withAddress("USA, Montana")
                    .withGroup(groupName));
        }

        List<ContactData> before = app.contact().getContactList();

        app.contact().selectContact(before.size() - 1);
        app.contact().initContactEditing(before.size()+1);
        ContactData contact = new ContactData().withId(before.get(before.size() - 1).getId()).withName ("Price").withLastName("One")
                .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withAddress("USA, Montana");
        app.contact().fillNewContactForm(contact, false);
        app.contact().updateEditedContact();
        app.goTo().backToHomePage();
        app.contact().timeOut(1);
        List<ContactData> after = app.contact().getContactList();
        before.remove(before.size() - 1);
        before.add(contact);

        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before, after);

        app.getSessionHelper().logout();
    }
}
