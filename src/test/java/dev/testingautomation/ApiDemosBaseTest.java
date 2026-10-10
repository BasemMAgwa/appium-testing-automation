package dev.testingautomation;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.nio.file.Files;
import java.nio.file.Path;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class ApiDemosBaseTest extends BaseTest {
    @Override
    protected UiAutomator2Options appOptions() {
        Path apk = Path.of("apps/ApiDemos-debug.apk").toAbsolutePath();
        assertTrue(Files.isRegularFile(apk), "Download ApiDemos as described in README.md");
        return new UiAutomator2Options()
                .setApp(apk.toString())
                .setAppPackage("io.appium.android.apis")
                .setAppActivity(".ApiDemos");
    }

    protected void openViews() {
        clickable(AppiumBy.accessibilityId("Views")).click();
    }

    protected WebElement scrollViewsTo(String label) {
        // UiScrollable locates a row without depending on screen size or row coordinates.
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                        + ".scrollIntoView(new UiSelector().text(\"" + label + "\"))"));
    }
}
