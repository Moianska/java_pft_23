package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactHomeAddressTests extends TestBase{

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
    public void testHomeAddress() {

        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getHomeAddress(), equalTo(contactInfoFromEditForm.getHomeAddress()));

        ContactData contact1 = app.db().contacts().iterator().next();
        ContactData contactInfoFromEditForm1 = app.contact().infoFromEditForm(contact1);

        assertThat(contact1.getHomeAddress(), equalTo(contactInfoFromEditForm1.getHomeAddress()));
    }
}
