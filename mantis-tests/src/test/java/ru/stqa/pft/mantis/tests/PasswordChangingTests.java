package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class PasswordChangingTests extends TestBase{

    @Test
    public void passwordChanging () throws IOException {
        HttpSession session = app.newSession();
        session.login("administrator", "root");
        app.openManageTab();

    }
}
