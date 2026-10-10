package dev.testingautomation;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

abstract class BaseTest {
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    protected abstract UiAutomator2Options appOptions();

    @BeforeEach
    void createDriver() throws Exception {
        UiAutomator2Options options = appOptions();
        // UDID selects one device when an emulator and a USB phone are both connected.
        String udid = System.getProperty("device.udid", "emulator-5554");
        options.setUdid(udid);
        options.setDeviceName("Android");
        // Some vendor phones deny shell writes to hidden-API settings during session startup.
        if (!udid.startsWith("emulator-")) {
            options.setCapability("appium:ignoreHiddenApiPolicyError", true);
        }
        // Use this only when a phone blocks Appium Settings during optional initialization.
        if (Boolean.getBoolean("device.skipInitialization")) {
            options.setCapability("appium:skipDeviceInitialization", true);
        }
        driver = new AndroidDriver(
                URI.create(System.getProperty("appium.url", "http://127.0.0.1:4723")).toURL(),
                options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    @AfterEach
    void saveEvidenceAndQuit(TestInfo testInfo) throws Exception {
        if (driver == null) return;
        try {
            String device = System.getProperty("device.label", "emulator");
            String name = testInfo.getTestClass().orElseThrow().getSimpleName()
                    + "-" + testInfo.getTestMethod().orElseThrow().getName();
            Path screenshot = Path.of("evidence", "runs", device, name + ".png");
            Files.createDirectories(screenshot.getParent());
            Files.write(screenshot, driver.getScreenshotAs(OutputType.BYTES));
        } finally {
            driver.quit();
        }
    }
}
