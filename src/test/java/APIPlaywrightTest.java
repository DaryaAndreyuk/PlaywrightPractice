import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class APIPlaywrightTest {

    private Playwright playwright;
    private APIRequestContext apiRequestContext;

    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();
        apiRequestContext = playwright.request().newContext();

    }

    @Test
    public void testGetUsers(){
        APIResponse response = apiRequestContext.get("https://jsonplaceholder.typicode.com/users");
        assertEquals(200, response.status());

        String users = response.text();
        assertNotNull(users);
        assertTrue(users.startsWith("["));
    }

    @Test
    public void testGetUserById() {
        APIResponse response = apiRequestContext.get("https://jsonplaceholder.typicode.com/users/1");
        assertEquals(200, response.status());

        String user = response.text();
        assertTrue(user.contains("\"id\": 1"));
        assertTrue(user.contains("\"name\": \"Leanne Graham\""));
    }

    @Test
    public void testGetNonexistentEndpoint() {
        APIResponse response = apiRequestContext.get("https://jsonplaceholder.typicode.com/nonexistent");
        assertEquals(404, response.status());
    }


    @AfterEach
    public void tearDown() {
        apiRequestContext.dispose();
        playwright.close();
    }
}
