import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FirstPlaywrightTest {
    static Playwright playwright;
    static Browser browser;

    @BeforeAll
    public static void setUpAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @Test
    public void firstTest() {
        Page page = browser.newPage();
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");

        Locator usernameInput = page.locator("#username");
        usernameInput.fill("user");

        Locator passwordInput = page.locator("#password");
        passwordInput.fill("user");

        page.locator("//button[@type='submit']").click();
        Assertions.assertTrue(page.locator("#success").isVisible());

        page.close();

    }

    @Test
    public void invalidDataTest() {

        Page page = browser.newPage();
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");

        Locator usernameInput = page.locator("#username");
        usernameInput.fill("invaliduser");

        Locator passwordInput = page.locator("#password");
        passwordInput.fill("invaliduser");

       //<button type="submit" class="btn btn-outline-primary mt-2">Submit</button>

       /* 1 approach
       page.getByRole(AriaRole.BUTTON).click();*/

        /* 2 approach
        page.locator("//button[@type='submit']").click();*/

        //3 approach
        page.locator("text=Submit").click();

        Assertions.assertTrue(page.locator("#invalid").textContent().contains("Invalid"));

        page.close();
    }

    @AfterAll
    public static void tearDownAll() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
