package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactHomeAddressTests extends TestBase{

    @Test
    public void testHomeAddress() {

        if (app.contact().all().size() == 0){
            String groupName = app.group().defineGroupName();
            app.goTo().backHome();
            app.contact().create(new ContactData().withName ("Mike").withLastName("Jordan")
                    .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withHomeAddress("USA, Montana")
                    .withGroup(groupName).withWorkPhone("121 5545").withHomePhone("444 5555"));
        }

        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getHomeAddress(), equalTo(contactInfoFromEditForm.getHomeAddress()));
    }
}
