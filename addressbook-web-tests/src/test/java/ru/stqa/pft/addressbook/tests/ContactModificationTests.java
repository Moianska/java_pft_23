package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {

        String groupName = app.group().defineGroupName();
        app.goTo().backHome();

        if (app.contact().all().size() == 0){
            app.contact().create(new ContactData().withName ("Mike").withLastName("Jordan")
                    .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withAddress("USA, Montana")
                    .withGroup(groupName));
        }

        Contacts before = app.contact().all();

        ContactData modifiedContact = before.iterator().next();
        ContactData finalContact = new ContactData().withId(modifiedContact.getId()).withName ("LLLL").withLastName("TTTT")
                .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withAddress("USA, Montana")
                .withGroup(groupName);
        app.contact().modify(finalContact);
        Contacts after = app.contact().all();
        assertEquals(after.size(), before.size());
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(finalContact)));


    }


}
