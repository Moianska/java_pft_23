package ru.stqa.pft.mantis.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;

public class SoapTests extends TestBase {
    /*@Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        System.out.println(projects.size());
        for (Project project : projects) {
            System.out.println(project.getName());
        }
    }*/

    @BeforeMethod
    public void ifIssueOpen() throws RemoteException, MalformedURLException, com.google.protobuf.ServiceException, ServiceException {
        skipIfNotFixed(0000002);
    }


    @Test
    public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
        Set<Project> projects = app.soap().getProjects();

        Issue issue = new Issue()
                .withSummary("Test issue")
                .withDescription("test issue description")
                .withProject(projects.iterator().next());

        Issue created_issue = app.soap().addIssue(issue);
        assertEquals(issue.getSummary(), created_issue.getSummary());
    }
}
