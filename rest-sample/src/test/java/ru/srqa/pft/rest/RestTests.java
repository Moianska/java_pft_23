package ru.srqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;
import static org.testng.Assert.assertEquals;

public class RestTests {
    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = getIssue();
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssue();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

    private int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                        new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        JsonElement parsedList = new JsonParser().parse(json);
        return parsedList.getAsJsonObject().get("issue_id").getAsInt();
    }

    private Set<Issue> getIssue() throws IOException {
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
                        .returnContent().asString();
        JsonElement parsedList = new JsonParser().parse(json);
        JsonElement listOfIssues = parsedList.getAsJsonObject().get("issues");

        return new Gson().fromJson(listOfIssues, new TypeToken<Set<Issue>>(){}.getType());
    }

    private Executor getExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490","");
    }
}
