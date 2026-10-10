package dev.testingautomation;

import io.appium.java_client.AppiumBy;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.RemoteWebElement;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TapTest extends ApiDemosBaseTest {
    @Test
    void tapsCheckboxAndVerifiesStateChanged() {
        openViews();
        clickable(AppiumBy.accessibilityId("Controls")).click();
        clickable(AppiumBy.accessibilityId("1. Light Theme")).click();
        var checkbox = visible(AppiumBy.id("io.appium.android.apis:id/check1"));
        boolean beforeTap = Boolean.parseBoolean(checkbox.getAttribute("checked"));
        driver.executeScript("mobile: clickGesture", Map.of(
                "elementId", ((RemoteWebElement) checkbox).getId()));
        assertNotEquals(beforeTap, Boolean.parseBoolean(checkbox.getAttribute("checked")));
    }
}
