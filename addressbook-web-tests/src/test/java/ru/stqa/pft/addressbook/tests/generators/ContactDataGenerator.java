package ru.stqa.pft.addressbook.tests.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter (names = "-f", description = "Target file")
    public String file;

    @Parameter (names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContact(count);
        if (format.equals("csv")) {
            saveAsCSV(contacts, new File(file));
        } else if (format.equals("xml")){
            saveAsXML(contacts, new File(file)); }
        else {
            System.out.println("Unrecognized format" + format);
        }
    }

    private void saveAsXML(List<ContactData> contacts, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        String xml = xstream.toXML(contacts);
        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();
    }

    private void saveAsCSV(List<ContactData> contacts, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts) {
            writer.write(String.format("%s;%s;%s\n", contact.getFirstName(), contact.getLastName(), contact.getGroup()));
        }
        writer.close();
    }

    private List<ContactData> generateContact(int count) {
        List<ContactData> contacts= new ArrayList<ContactData>();
        File photo = new File("src/test/resources/stru.png");
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData().withName(String.format("first name %s",i))
                    .withLastName(String.format("last name %s",i))
                    .withGroup(String.format("test 1")).withHomePhone(String.format("12345%s", i))
                    .withWorkPhone(String.format("044295511%s", i))
                    .withMobilePhone(String.format("044295511%s", i))
                    .withEmail(String.format("test@test%s.com", i))
                    .withHomePhone(String.format("USA, Street %s", i))
                    .withPhoto(photo));
        }
        return contacts;
    }
}
