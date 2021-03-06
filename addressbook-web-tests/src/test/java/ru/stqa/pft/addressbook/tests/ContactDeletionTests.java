package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            Groups groups = app.db().groups();
            app.goTo().backHome();
            app.contact().create(new ContactData().withName ("Mike").withLastName("Jordan")
                    .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withHomeAddress("USA, Montana")
                    .inGroup(groups.iterator().next()));
        }
    }

    @Test
    public void testContactDeletion() {

        app.goTo().backHome();

        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        Contacts after = app.db().contacts();

        assertEquals(after.size(), before.size() - 1);
        assertThat(after, equalTo(before.without(deletedContact)));
    }

}
