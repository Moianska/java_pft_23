package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class PasswordChangingTests extends TestBase{

    //@BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void passwordChanging () throws IOException, MessagingException {
        app.sessionHelper().login("administrator", "root");
        app.openManageTab();

        Users before = app.db().users();
        before.removeIf(user -> user.getUsername().equals("administrator"));
        UserData candidateToChangePass = before.iterator().next();

        String email = candidateToChangePass.getEmail();
        String user = candidateToChangePass.getUsername();
        String password = "Password";

       /* app.sessionHelper().login("administrator", "root");
        app.openManageTab();*/
        app.userHelper().selectUserById(candidateToChangePass.getId());
        app.userHelper().resetPassword();
        app.sessionHelper().logout();

        long now = System.currentTimeMillis();
        /*List<MailMessage> mailMessages = app.mail().waitForMail(1, 60000);*/
        List<MailMessage> mailMessages = app.james().waitForMail(user, password, 200000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, password);
        assertTrue(app.newSession().login(user, password));
        assertTrue(app.newSession().isLoggedInAs(user));

    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression
                .regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    //@AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
