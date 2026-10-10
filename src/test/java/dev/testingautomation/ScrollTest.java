package dev.testingautomation;

import io.appium.java_client.AppiumBy;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.RemoteWebElement;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScrollTest extends ApiDemosBaseTest {
    @Test
    void scrollsViewsListUntilSeekBarIsVisible() {
        openViews();
        var list = visible(AppiumBy.id("android:id/list"));
        String listId = ((RemoteWebElement) list).getId();
        for (int attempt = 0; attempt < 4
                && driver.findElements(AppiumBy.accessibilityId("Seek Bar")).isEmpty(); attempt++) {
            driver.executeScript("mobile: scrollGesture", Map.of(
                    "elementId", listId, "direction", "down", "percent", 0.85));
        }
        assertTrue(visible(AppiumBy.accessibilityId("Seek Bar")).isDisplayed());
    }
}
