package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test (enabled = false)
  public void testContactCreation() {

    String groupName = app.group().defineGroupName();
    app.goTo().backHome();

    List<ContactData> before = app.contact().getContactList();
    ContactData contact = new ContactData().withName ("Mike").withLastName("Jordan")
            .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withAddress("USA, Montana")
            .withGroup(groupName);
    app.contact().createContact(contact);
    app.goTo().backHome();
    app.contact().timeOut(1);
    List<ContactData> after = app.contact().getContactList();

    Assert.assertEquals(after.size(), before.size() + 1);
    before.add(contact);

    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId); /*(g1, g2) -> Integer.compare(g1.getId(), g2.getId());*/
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }
}
