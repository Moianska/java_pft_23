package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class ContactDeletionFromGroup extends TestBase{

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
    public void testContactDeletionFromGroup () {
        Groups groupsBefore = app.db().groups();
        Contacts contactsInitialSet = app.db().contacts();
        Contacts contactsBefore = contactsInitialSet;

        ContactData candidateDeletedContact = contactsBefore.iterator().next();
        Integer countCondition = contactsBefore.size();


        while (countCondition != 0) {

            if ((candidateDeletedContact.getGroups().size() == 0) && (contactsBefore.size() > 1)) {
                contactsBefore = contactsBefore.without(candidateDeletedContact);
                candidateDeletedContact = contactsBefore.iterator().next();
                countCondition = contactsBefore.size();
            } else {
                if ((candidateDeletedContact.getGroups().size() == 0) && (contactsBefore.size() == 1)) {

                    GroupData chosenGroup = groupsBefore.iterator().next();
                    String groupId = String.valueOf(chosenGroup.getId());
                    app.goTo().backHome();
                    app.resetGroupSelector();
                    app.contact().addContactToGroup(candidateDeletedContact, groupId);
                    countCondition = 0;

                    Contacts contactsWithApdatedContact = app.db().contacts();
                    contactsWithApdatedContact.removeAll(contactsInitialSet);
                    candidateDeletedContact = contactsWithApdatedContact.iterator().next();
                } else {
                countCondition = 0;
                }
            }
        }

        Groups contactGroups = candidateDeletedContact.getGroups();
        GroupData chosenGroup = contactGroups.iterator().next();
        String groupId = String.valueOf(chosenGroup.getId());

        app.goTo().backHome();
        app.setGroupSelector(groupId);

        app.contact().deleteContactFromTheGroup(candidateDeletedContact);

        Groups contactNewGroups = app.db().singleContactByID(candidateDeletedContact.getId()).getGroups();
        assertThat(contactNewGroups, equalTo(contactGroups.without(chosenGroup)));
    }
}
