package dev.testingautomation;

import io.appium.java_client.AppiumBy;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.RemoteWebElement;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DragDropTest extends ApiDemosBaseTest {
    @Test
    void dragsFirstDotOntoSecondDotAndVerifiesResult() {
        openViews();
        clickable(AppiumBy.accessibilityId("Drag and Drop")).click();
        var source = visible(AppiumBy.id("io.appium.android.apis:id/drag_dot_1"));
        var target = visible(AppiumBy.id("io.appium.android.apis:id/drag_dot_2"));
        var destination = target.getRect();
        driver.executeScript("mobile: dragGesture", Map.of(
                "elementId", ((RemoteWebElement) source).getId(),
                "endX", destination.x + destination.width / 2,
                "endY", destination.y + destination.height / 2));
        String result = visible(AppiumBy.id("io.appium.android.apis:id/drag_result_text")).getText();
        assertEquals("Dropped!", result);
    }
}
