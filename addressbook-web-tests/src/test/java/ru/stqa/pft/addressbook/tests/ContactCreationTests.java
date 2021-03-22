package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {

    String groupName = app.getGroupHelper().defineGroupName();
    app.getNavigationHelper().backHome();

    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("Mia", "Jordan", "+33111222333",
            "terry.p@google.com", "USA, Montana", groupName);
    app.getContactHelper().createContact(contact);
    app.getNavigationHelper().backHome();
    app.getContactHelper().timeOut(1);
    List<ContactData> after = app.getContactHelper().getContactList();

    Assert.assertEquals(after.size(), before.size() + 1);
    before.add(contact);

    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId); /*(g1, g2) -> Integer.compare(g1.getId(), g2.getId());*/
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }
}
