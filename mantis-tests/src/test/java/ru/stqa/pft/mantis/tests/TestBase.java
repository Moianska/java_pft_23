package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import com.google.protobuf.ServiceException;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestBase {

    protected static ApplicationManager app;

    static {
        try {
            app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        app.init();
        app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php",
                "config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws IOException {
        app.ftp().restore("config_inc.php.bak", "config_inc.php");
        app.stop();
    }

    public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException, javax.xml.rpc.ServiceException {
        if (isIssueOpen(issueId) == true) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

        private boolean isIssueOpen(int issueId) throws RemoteException, javax.xml.rpc.ServiceException, MalformedURLException {
            IssueData issue = app.soap().getIssueById(issueId);
            return !issue.getStatus().getName().equals("close");
        }

}


