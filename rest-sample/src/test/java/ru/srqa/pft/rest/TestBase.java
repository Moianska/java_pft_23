package ru.srqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;

import java.io.IOException;
import java.util.Set;

public class TestBase {

    public Executor getExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId) == true) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues/"+issueId+".json"))
                .returnContent().asString();
        JsonElement parsedList = new JsonParser().parse(json);
        JsonElement issueData = parsedList.getAsJsonObject().get("issues");
        String state_name = issueData.getAsJsonArray().get(0).getAsJsonObject().get("state_name").getAsString();

        return !state_name.equals("closed");
    }
}
