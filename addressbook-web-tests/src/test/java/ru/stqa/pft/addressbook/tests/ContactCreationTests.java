package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {

    String groupName = app.group().defineGroupName();
    app.goTo().backHome();

    Contacts before = app.contact().all();
    ContactData contact = new ContactData().withName ("Luke").withLastName("Skywalker")
            .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withAddress("USA, Montana")
            .withGroup(groupName);
    app.contact().create(contact);
    app.goTo().backHome();
    app.contact().timeOut(1);
    Contacts after = app.contact().all();

    Assert.assertEquals(after.size(), before.size() + 1);
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }
}
