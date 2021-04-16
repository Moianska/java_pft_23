package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddingToGroup extends TestBase{

    @BeforeMethod
    public void ensurePreconditionsForContacts() {

        if (app.db().contacts().size() == 0) {

            app.goTo().backHome();
            app.contact().createWithoutPhoto(new ContactData().withName ("Mike").withLastName("Jordan")
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
        Contacts contactsInitialSet = app.db().contacts();
        Contacts contactsBefore = contactsInitialSet;
        ContactData candidateAddedContact = contactsBefore.iterator().next();
        Integer countCondition = contactsBefore.size();

        while (countCondition !=0) {
            if ((candidateAddedContact.getGroups().size() !=0)&&(contactsBefore.size() > 1)) {
                contactsBefore = contactsBefore.without(candidateAddedContact);
                candidateAddedContact = contactsBefore.iterator().next();
                countCondition = contactsBefore.size();
            } else {
                if ((candidateAddedContact.getGroups().size() != 0) && (contactsBefore.size() == 1)) {
                    app.contact().createWithoutPhoto(new ContactData().withName("Mike").withLastName("Jordan")
                            .withMobilePhone("+33111222333").withEmail("terry.p@google.com")
                            .withHomeAddress("USA, Montana"));

                    Contacts contactsWithAddedContact = app.db().contacts();
                    contactsWithAddedContact.removeAll(contactsInitialSet);
                    candidateAddedContact = contactsWithAddedContact.iterator().next();
                } else {
                    countCondition = 0;
                }
            }
        }

        GroupData chosenGroup = groupsBefore.iterator().next();
        String groupId = String.valueOf(chosenGroup.getId());

        app.goTo().backHome();
        app.resetGroupSelector();
        app.contact().addContactToGroup(candidateAddedContact, groupId);
        app.goTo().backHome();

        ContactData finalContact = app.db().singleContactByID(candidateAddedContact.getId());
        Groups groupsFromFinalContact = finalContact.getGroups();
        Groups groupsToCompare = candidateAddedContact.getGroups().withAdded(chosenGroup);

        assertThat(groupsFromFinalContact, equalTo(groupsToCompare));
    }
}
