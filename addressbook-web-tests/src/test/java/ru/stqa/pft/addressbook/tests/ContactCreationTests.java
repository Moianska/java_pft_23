package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillNewContactForm(new ContactData("Terry", "Pratchet", "+33111222333", "terry.p@google.com", "USA, Montana"));
    app.getContactHelper().submitNewContactForm();
    app.getNavigationHelper().backToHomePage();
    app.getSessionHelper().logout();
  }
}
