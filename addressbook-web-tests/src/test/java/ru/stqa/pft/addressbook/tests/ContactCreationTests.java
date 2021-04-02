package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {

    String groupName = app.group().defineGroupName();
    app.goTo().backHome();

    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/pict.png");
    ContactData contact = new ContactData().withName ("Luke").withLastName("Skywalker")
            .withMobilePhone("+33111222333").withEmail("terry.p@google.com").withHomeAddress("USA, Montana")
            .withPhoto(photo).withGroup(groupName);
    app.contact().create(contact);
    app.goTo().backHome();
    app.contact().timeOut(1);
    Contacts after = app.contact().all();

    Assert.assertEquals(after.size(), before.size() + 1);
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }

  /*@Test
  public void testCurrentDir() {
    File currentDir = new File(".");
    currentDir.getAbsolutePath();
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/pict.png");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }*/
}
