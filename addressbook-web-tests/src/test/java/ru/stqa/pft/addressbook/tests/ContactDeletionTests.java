package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion() {

        String groupName = app.group().defineGroupName();
        app.goTo().backHome();

        if (! app.contact().isThereAContact()){
            app.contact().createContact(new ContactData().withName ("Mike").withLastName("Jordan")
                    .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withAddress("USA, Montana")
                    .withGroup(groupName));
        }

        List<ContactData> before = app.contact().getContactList();
        app.contact().delete(before);
        List<ContactData> after = app.contact().getContactList();

        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }

}
