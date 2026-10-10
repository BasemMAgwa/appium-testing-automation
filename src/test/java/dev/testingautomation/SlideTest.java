package dev.testingautomation;

import io.appium.java_client.AppiumBy;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SlideTest extends ApiDemosBaseTest {
    @Test
    void slidesSeekBarAndVerifiesProgress() {
        openViews();
        scrollViewsTo("Seek Bar").click();
        var seekBar = visible(AppiumBy.id("io.appium.android.apis:id/seek"));
        var rect = seekBar.getRect();
        Point start = new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
        Point end = new Point(rect.x + rect.width * 8 / 10, start.y);

        // W3C touch actions move the visible thumb across the actual SeekBar.
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence slide = new Sequence(finger, 0);
        slide.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), start.x, start.y));
        slide.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        slide.addAction(finger.createPointerMove(Duration.ofMillis(700),
                PointerInput.Origin.viewport(), end.x, end.y));
        slide.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(slide));

        String progress = visible(AppiumBy.id("io.appium.android.apis:id/progress")).getText();
        assertTrue(progress.matches("[0-9]+ from touch=true"), "SeekBar did not report a touch: " + progress);
    }
}
