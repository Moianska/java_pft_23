package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            String groupName = app.group().defineGroupName();
            app.goTo().backHome();
            app.contact().create(new ContactData().withName ("Mike").withLastName("Jordan")
                    .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withHomeAddress("USA, Montana")
                    .withGroup(groupName));
        }
    }

    @Test
    public void testContactModification() {

        String groupName = app.group().defineGroupName();
        app.goTo().backHome();

        Contacts before = app.db().contacts();

        ContactData modifiedContact = before.iterator().next();
        ContactData finalContact = new ContactData().withId(modifiedContact.getId()).withName ("LLLL").withLastName("TTTT")
                .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withHomeAddress("USA, Montana")
                .withGroup(groupName);

        app.contact().modify(finalContact);

        Contacts after = app.db().contacts();
        assertEquals(after.size(), before.size());
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(finalContact)));
    }
}
