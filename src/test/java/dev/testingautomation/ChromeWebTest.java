package dev.testingautomation;

import com.sun.net.httpserver.HttpServer;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChromeWebTest extends BaseTest {
    private static final String PAGE = """
            <!doctype html><html><head><meta name="viewport" content="width=device-width"></head>
            <body><h1>Appium browser test</h1>
            <label for="name">Name</label><input id="name">
            <button id="greet" onclick="document.getElementById('result').textContent =
              'Hello, ' + document.getElementById('name').value">Greet</button>
            <p id="result"></p></body></html>
            """;

    @Override
    protected UiAutomator2Options appOptions() {
        // browserName starts Chrome in a web context; do not set an APK in this session.
        UiAutomator2Options options = new UiAutomator2Options();
        options.setCapability("browserName", "chrome");
        return options;
    }

    @Test
    void opensLocalPageAndSubmitsName() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            byte[] body = PAGE.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
            exchange.sendResponseHeaders(200, body.length);
            try (var out = exchange.getResponseBody()) { out.write(body); }
        });
        server.start();
        int port = server.getAddress().getPort();
        String udid = System.getProperty("device.udid", "emulator-5554");
        String adb = Path.of(System.getenv("ANDROID_HOME"), "platform-tools", "adb.exe").toString();
        try {
            // adb reverse makes this deterministic local page reachable from emulator or USB phone.
            Process reverse = new ProcessBuilder(adb, "-s", udid, "reverse",
                    "tcp:" + port, "tcp:" + port).inheritIO().start();
            assertTrue(reverse.waitFor(10, TimeUnit.SECONDS) && reverse.exitValue() == 0);
            driver.get("http://127.0.0.1:" + port + "/");
            visible(By.cssSelector("#name")).sendKeys("Appium");
            clickable(By.cssSelector("#greet")).click();
            assertEquals("Hello, Appium", visible(By.cssSelector("#result")).getText());
        } finally {
            new ProcessBuilder(adb, "-s", udid, "reverse", "--remove",
                    "tcp:" + port).start().waitFor(10, TimeUnit.SECONDS);
            server.stop(0);
        }
    }
}
