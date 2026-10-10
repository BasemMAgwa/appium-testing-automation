package dev.testingautomation;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SettingsTest extends BaseTest {
    @Override
    protected UiAutomator2Options appOptions() {
        // These are the package and launch activity of Settings on the API 33 AVD.
        return new UiAutomator2Options()
                .setAppPackage("com.android.settings")
                .setAppActivity(".Settings");
    }

    @Test
    void searchesSettingsAndOpensDisplay() {
        // Stable resource IDs were read from the AVD's UI hierarchy.
        clickable(AppiumBy.id("com.android.settings:id/search_action_bar")).click();
        visible(AppiumBy.id("com.google.android.settings.intelligence:id/open_search_view_edit_text"))
                .sendKeys("Display");
        // The result list repeats “Display” in breadcrumbs, so narrow this XPath to its title.
        clickable(AppiumBy.xpath("//android.widget.TextView[@resource-id='android:id/title' and @text='Display']"))
                .click();
        // A page-specific setting is a stronger assertion than the changing toolbar title.
        assertTrue(visible(AppiumBy.xpath("//*[@text='Brightness level']")).isDisplayed());
    }
}
