package dev.testingautomation;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectMeApkTest extends BaseTest {
    @Override
    protected UiAutomator2Options appOptions() {
        Path apk = Path.of(System.getProperty("connectme.apk",
                "connectme/build/app/outputs/flutter-apk/app-debug.apk")).toAbsolutePath();
        assertTrue(Files.isRegularFile(apk), "Build ConnectMe or set -Dconnectme.apk=<path>");
        // app installs the actual APK; its package/activity are verified with aapt dump badging.
        return new UiAutomator2Options()
                .setApp(apk.toString())
                .setAppPackage("com.example.connectme")
                .setAppActivity(".MainActivity");
    }

    @Test
    void opensLoginAndTypesEmailWithoutUsingAnAccount() {
        // Flutter exposes this label as an accessibility description.
        clickable(AppiumBy.accessibilityId("GET STARTED")).click();
        assertTrue(visible(AppiumBy.accessibilityId("LOG IN")).isDisplayed());
        // ConnectMe currently exposes two unlabeled EditTexts; the first is Email.
        var email = visible(AppiumBy.xpath("(//android.widget.EditText)[1]"));
        email.sendKeys("automation@example.com");
        assertEquals("automation@example.com", email.getText());
    }
}
