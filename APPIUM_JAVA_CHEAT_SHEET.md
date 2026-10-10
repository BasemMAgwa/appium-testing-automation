# Appium Java cheat sheet

Examples use Appium Java Client 10 and UiAutomator2. `driver` is an `AndroidDriver`, `element` is a `WebElement`, and imports are shown where useful. For a broader API list, see the [Java client](https://github.com/appium/java-client) and [UiAutomator2 driver](https://github.com/appium/appium-uiautomator2-driver) references.

## Start a native app session

```java
UiAutomator2Options options = new UiAutomator2Options()
    .setUdid("emulator-5554")
    .setAppPackage("com.android.settings")
    .setAppActivity(".Settings");
AndroidDriver driver = new AndroidDriver(URI.create("http://127.0.0.1:4723").toURL(), options);
```

`platformName=Android` and `automationName=UiAutomator2` are provided by `UiAutomator2Options`. Use `udid` to select a specific connected device. Modern Java client code uses options instead of the older `DesiredCapabilities` class.

## Install and launch an APK

```java
UiAutomator2Options options = new UiAutomator2Options().setApp(
    Path.of("apps/ApiDemos-debug.apk").toAbsolutePath().toString());
```

The `app` path is on the machine running the Appium server. `appPackage` and `appActivity` may also be set if launch detection needs help.

## Find elements

```java
WebElement byId = driver.findElement(AppiumBy.id("io.appium.android.apis:id/seek"));
WebElement byAccessibility = driver.findElement(AppiumBy.accessibilityId("Views"));
WebElement byXPath = driver.findElement(AppiumBy.xpath("//android.widget.EditText[1]"));
WebElement byUiAutomator = driver.findElement(AppiumBy.androidUIAutomator(
    "new UiSelector().text(\"Seek Bar\")"));
```

Prefer resource ID or accessibility ID. XPath is useful when the app exposes neither, but it depends more on the view hierarchy.

## Wait and interact

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
    AppiumBy.accessibilityId("GET STARTED")));
button.click();
driver.findElement(AppiumBy.xpath("(//android.widget.EditText)[1]"))
    .sendKeys("automation@example.com");
```

## Common driver commands

```java
String source = driver.getPageSource();
byte[] png = driver.getScreenshotAs(OutputType.BYTES);
driver.navigate().back();
driver.terminateApp("io.appium.android.apis");
driver.activateApp("io.appium.android.apis");
driver.quit();
```

Always call `quit()` in teardown. `getPageSource()` helps inspect a failed locator; screenshots provide visual evidence.

## Tap

```java
driver.executeScript("mobile: clickGesture", Map.of(
    "elementId", ((RemoteWebElement) element).getId()));
```

Assert the resulting state, such as `checkbox.isSelected()`.

## Scroll

```java
boolean canScrollMore = (Boolean) driver.executeScript("mobile: scrollGesture", Map.of(
    "elementId", ((RemoteWebElement) list).getId(),
    "direction", "down", "percent", 0.85));
```

Repeat until the target appears or `canScrollMore` is false.

## Slide or swipe

```java
driver.executeScript("mobile: swipeGesture", Map.of(
    "elementId", ((RemoteWebElement) carousel).getId(),
    "direction", "left", "percent", 0.75));
```

For a slider thumb, use W3C touch pointer actions with exact start and end positions, as in `SlideTest.java`.

## Drag and drop

```java
Rectangle target = destination.getRect();
driver.executeScript("mobile: dragGesture", Map.of(
    "elementId", ((RemoteWebElement) source).getId(),
    "endX", target.x + target.width / 2,
    "endY", target.y + target.height / 2));
```

Assert a changed result label or drop target state. Legacy `TouchAction` APIs are removed from current UiAutomator2 versions.

## Chrome on Android

```java
UiAutomator2Options options = new UiAutomator2Options().setUdid("emulator-5554");
options.setCapability("browserName", "chrome");
AndroidDriver driver = new AndroidDriver(URI.create("http://127.0.0.1:4723").toURL(), options);
driver.get("https://example.com/");
WebElement heading = driver.findElement(By.cssSelector("h1"));
```

Do not also set `app` for a browser session. Chrome automation depends on a compatible Chromedriver; `ChromeWebTest` uses a local deterministic page and an `adb reverse` tunnel.
