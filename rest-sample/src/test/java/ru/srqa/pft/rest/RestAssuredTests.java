package ru.srqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;
import static org.testng.Assert.assertEquals;

public class RestAssuredTests {

    @BeforeClass

    public void init() {
        RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    @Test
    public void testCreateIssue() {
        Set<Issue> oldIssues = getIssue();
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssue();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

    private int createIssue(Issue newIssue) {
        String json = RestAssured.given()
                .parameter("subject", newIssue.getSubject())
                .parameter("description", newIssue.getDescription())
                .post("https://bugify.stqa.ru/api/issues.json").asString();
        JsonElement parsedList = new JsonParser().parse(json);
        return parsedList.getAsJsonObject().get("issue_id").getAsInt();
    }

    private Set<Issue> getIssue() {
        String json = RestAssured.get("https://bugify.stqa.ru/api/issues.json").asString();
        JsonElement parsedList = new JsonParser().parse(json);
        JsonElement listOfIssues = parsedList.getAsJsonObject().get("issues");

        return new Gson().fromJson(listOfIssues, new TypeToken<Set<Issue>>(){}.getType());
    }

}
