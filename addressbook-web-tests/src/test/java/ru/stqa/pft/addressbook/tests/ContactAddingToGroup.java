package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddingToGroup extends TestBase{

    @BeforeMethod
    public void ensurePreconditionsForContacts() {

        if (app.db().contacts().size() == 0) {

            app.goTo().backHome();
            app.contact().create(new ContactData().withName ("Mike").withLastName("Jordan")
                    .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withHomeAddress("USA, Montana"));
        }
    }

    @BeforeMethod
    public void ensurePreconditionsForGroups() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testAddingContactToGroup () {
        Groups groupsBefore = app.db().groups();
        Contacts contactsBefore = app.db().contacts();
        ContactData addedContact = contactsBefore.iterator().next();

        while ((addedContact.getGroups().size() !=0)) {
            contactsBefore = contactsBefore.without(addedContact);
            if (contactsBefore.size() == 0) {
                app.contact().createWithoutPhoto(new ContactData().withName ("Mike").withLastName("Jordan")
                        .withMobilePhone("+33111222333").withEmail("terry.p@google.com")
                        .withHomeAddress("USA, Montana"));
                contactsBefore = app.db().contacts();
                addedContact = contactsBefore.iterator().next();
            } else {
                addedContact = contactsBefore.iterator().next();
            }
        }

        GroupData chosenGroup = groupsBefore.iterator().next();
        String groupId = String.valueOf(chosenGroup.getId());

        app.goTo().backHome();
        app.resetGroupSelector();
        app.contact().addContactToGroup(addedContact, groupId);
        app.goTo().backHome();

        ContactData finalContact = app.db().singleContactByID(addedContact.getId());
        Groups groupsFromFinalContact = finalContact.getGroups();
        Groups groupsToCompare = addedContact.getGroups().withAdded(chosenGroup);

        assertThat(groupsFromFinalContact, equalTo(groupsToCompare));




        /*ContactData addedContactWithGroup = addedContact.withGroups(chosenGroup);*/



    }
}
