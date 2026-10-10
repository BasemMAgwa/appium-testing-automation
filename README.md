# Android automation with Appium

This Maven/JUnit project exercises Android Settings, the built `ConnectMe` APK, four gestures in Appium's ApiDemos app, and Chrome on Android. Each scenario has its own Java test class. `BaseTest` owns driver setup, waits, screenshots, and cleanup.

## Windows setup

The verified machine uses Windows 11, Node.js 22.14, JDK 21, Maven 3.9.16, Appium 3.8.0, UiAutomator2 driver 8.7.0, Appium Inspector 2026.9.2, and an Android 13 AVD named `Phone`. Android Studio and its SDK were already present; the JDK, Maven, Appium, driver, and Inspector were configured for this project.

1. Install Node.js and check `node --version` and `npm --version` in a new PowerShell window.
2. Install Android Studio. In **SDK Manager**, install Android SDK Platform Tools, an Android platform, and an emulator system image. In **Device Manager**, create and start an AVD; this project used the Android 13 `Phone` AVD.
3. Install JDK 21 and Apache Maven. After setting `JAVA_HOME` in the next step, check that `mvn -version` reports Java 21.
4. In Windows Environment Variables, set `ANDROID_HOME` to the Android SDK directory and `JAVA_HOME` to the JDK directory. Add `%ANDROID_HOME%\platform-tools` and Maven's `bin` directory to `Path`. Open a **new** PowerShell window after changing these variables. On the verified machine, the SDK is `%LOCALAPPDATA%\Android\Sdk`.
5. Install and check Appium's Android driver:

   ```powershell
   npm install --global appium
   appium driver install uiautomator2
   appium driver doctor uiautomator2
   adb devices -l
   ```

   Appium 3 uses `appium driver doctor uiautomator2` for this check. On the setup machine it reported **0 required fixes**; the actual output is in [`evidence/appium-doctor.txt`](evidence/appium-doctor.txt). Bundletool, FFmpeg, and GStreamer were optional warnings.

6. Install Appium Inspector, then start the Android Studio AVD named `Phone`. Verify `adb devices -l` lists `emulator-5554` as `device`. Start Appium in a separate terminal with `appium`. For this AVD's older Chrome 109, start it with `appium --allow-insecure=uiautomator2:chromedriver_autodownload` so Appium can obtain a matching Chromedriver.

## APK fixtures

The gesture tests use [Appium's ApiDemos APK](https://github.com/appium/appium/blob/master/packages/appium/sample-code/apps/ApiDemos-debug.apk). Download it into `apps/ApiDemos-debug.apk`:

```powershell
New-Item -ItemType Directory -Force apps | Out-Null
Invoke-WebRequest 'https://raw.githubusercontent.com/appium/appium/master/packages/appium/sample-code/apps/ApiDemos-debug.apk' -OutFile apps/ApiDemos-debug.apk
```

`ConnectMeApkTest` uses the existing `connectme/build/app/outputs/flutter-apk/app-debug.apk` in this workspace. If the APK lives elsewhere, pass `-Dconnectme.apk=C:\path\to\app-debug.apk`. The APK is excluded from Git because it is 168 MB. The test covers onboarding and the login form without creating an account or changing Firebase data.

## Run

```powershell
mvn test
mvn '-Dtest=SettingsTest,ConnectMeApkTest' test
mvn '-Dtest=TapTest,ScrollTest,SlideTest,DragDropTest' test
mvn -Dtest=ChromeWebTest test
```

Set `-Dappium.url=http://127.0.0.1:4723` if the server differs. For a USB phone, enable Developer Options and USB debugging, accept its authorization prompt, find its serial in `adb devices -l`, then run, for example:

```powershell
mvn '-Ddevice.udid=YOUR_SERIAL' '-Ddevice.label=phone' '-Dtest=ConnectMeApkTest' test
```

On the tested Xiaomi 2109119DG (Android 14), Appium Settings could be installed with a normal `adb install -r` command, but the phone rejected Appium's automatic `install -g` permission grant and would not start that helper during initialization. With **Install via USB** enabled, the following setup and run pattern passed for this phone:

```powershell
$settingsApk = Join-Path $env:USERPROFILE '.appium\node_modules\appium-uiautomator2-driver\node_modules\io.appium.settings\apks\settings_apk-debug.apk'
adb -s YOUR_SERIAL install -r $settingsApk
mvn '-Ddevice.udid=YOUR_SERIAL' '-Ddevice.label=phone' '-Ddevice.skipInitialization=true' '-Dtest=ConnectMeApkTest' test
mvn '-Ddevice.udid=YOUR_SERIAL' '-Ddevice.label=phone' '-Ddevice.skipInitialization=true' '-Dtest=ChromeWebTest' test
```

`device.skipInitialization` sets Appium's `skipDeviceInitialization` capability. Use it only if normal session creation fails at the optional Appium Settings setup. Replace the serial with the one shown by `adb devices -l` for your phone.

The Chrome test serves a small page from Java and uses `adb reverse` to make it reachable on the Android device. Chrome must be installed on that device. No external website or account is needed.

JUnit reports are in `target/surefire-reports/`. Each test saves the final device screen to `evidence/runs/<device>/`. Screenshots are saved even if an assertion fails. The `evidence/runs/` directory is generated and ignored by Git; verified screenshots are copied into [`evidence/submission/`](evidence/submission/).

## Inspector and locators

Appium Inspector 2026.9.2 is installed under `%LOCALAPPDATA%\Programs\AppiumInspector` on the setup machine. Connect it to `http://127.0.0.1:4723` with base path `/`. Use these W3C capabilities for the Settings session:

```json
{
  "platformName": "Android",
  "appium:automationName": "UiAutomator2",
  "appium:udid": "emulator-5554",
  "appium:appPackage": "com.android.settings",
  "appium:appActivity": ".Settings"
}
```

For ConnectMe, replace package/activity with `com.example.connectme` / `.MainActivity` and add `appium:app` pointing to the APK. For Chrome, use `browserName: chrome` instead of app capabilities. The tests use stable IDs for native controls, accessibility descriptions for labeled Flutter and ApiDemos controls, and XPath only when the UI exposes no useful ID or when a result title must be distinguished from repeated breadcrumb text. Reinspect these locators if the app or Android image changes.

| Test | Element | Locator observed on the emulator |
| --- | --- | --- |
| Settings | Search bar | ID `com.android.settings:id/search_action_bar` |
| Settings | Display result | XPath `//android.widget.TextView[@resource-id='android:id/title' and @text='Display']` |
| ConnectMe | Get Started | Accessibility ID `GET STARTED` |
| ConnectMe | Login button | Accessibility ID `LOG IN` |
| ConnectMe | Email field | XPath `(//android.widget.EditText)[1]` because the current Flutter build exposes no resource ID for it |

The locators above were confirmed against the emulator UI hierarchy and visually rechecked in Appium Inspector. The search bar ID, Display result XPath, GET STARTED and LOG IN accessibility IDs, and first email EditText XPath each returned one matching element. Inspector views are saved in [`evidence/submission/`](evidence/submission/). Reinspect them if the app or Android image changes.

## Evidence checklist

- Doctor output: `evidence/appium-doctor.txt`; capture the terminal as a PNG for submission.
- Emulator: passing Settings and ConnectMe test reports and screenshots in `evidence/submission/`.
- Phone: passing ConnectMe and Chrome runs on the Xiaomi 2109119DG, with screenshots and the JUnit report in `evidence/submission/`.
- Gestures: one passing report and screenshot for each test; a screen recording frame can additionally show pointer movement.
- Chrome: passing report and screenshot.
- Reference: [`APPIUM_JAVA_CHEAT_SHEET.md`](APPIUM_JAVA_CHEAT_SHEET.md).

Some vendor phones also require **Install via USB** and **USB debugging (Security settings)** in Developer Options before Appium can install its helper APKs.
