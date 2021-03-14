package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {

    String groupName = app.getGroupHelper().defineGroupName();
    app.getNavigationHelper().backHome();

    if (! app.getContactHelper().isThereAContact()){
      app.getContactHelper().createContact(new ContactData("Mia", "Jordan", "+33111222333",
              "terry.p@google.com", "USA, Montana", groupName));
    }

    app.getSessionHelper().logout();
  }
}
