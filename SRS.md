# Mobile Testing Automation with Appium

## Task General Description

You are tasked with setting up a complete Appium mobile automation testing environment on Windows and writing automated test scripts against both a built-in app and a real APK. Starting from installation and configuration, you will progressively write your first automated tests, run them on an emulator and a real device, automate mobile gestures, and finally run a web test on Chrome using Appium. By the end you will have a working automation framework and a reference cheat sheet covering the full Appium Java API.

## Tools You Will Use

Appium Server
Appium Inspector
Java & Appium Java Client
Android SDK & Android Studio (AVD Emulator)
Node.js & npm
Real Android Device (USB Debugging enabled)
Chrome Browser on Android (for web testing)
GitHub

## Functional Requirements

### Phase 1 — Environment Setup on Windows for Android

Install and configure the full Appium testing environment on a Windows machine targeting the Android platform.

- Install Node.js, Appium Server, and the Appium Java Client.
- Install Android Studio and configure the Android SDK and a working AVD emulator.
- Set all required environment variables (ANDROID_HOME, JAVA_HOME, Platform Tools in PATH).
- Verify the setup by running appium-doctor and confirming all checks pass.
- Document every installation step and screenshot the final appium-doctor output.

### Phase 2 — Run First Test Using a Built-in App & an APK

Write and execute your first two automated tests — one against an Android built-in app and one against a real APK file.

- Configure the correct Desired Capabilities (platformName, deviceName, appPackage, appActivity) for the built-in app test.
- Write a test script that launches the built-in app, locates at least one element, and performs an action on it.
- Configure Desired Capabilities for an APK file and write a test that installs and launches the APK, then interacts with at least two elements.
- Use Appium Inspector to identify element locators (ID, XPath, Accessibility ID) for both tests.
- Run both tests successfully on the AVD emulator and capture the output.

### Phase 3 — Run Tests on a Real Device & Automate Mobile Gestures

Execute your tests on a physical Android device and automate all four core mobile gestures.

- Connect a real Android device via USB with Developer Options and USB Debugging enabled.
- Update your Desired Capabilities to target the real device and re-run one of the previous tests successfully on it.
- Write separate test methods automating all four mobile gestures: Tap, Scroll, Slide, and Drag & Drop.
- Each gesture test must target a visible element on the screen and produce a verifiable result.

### Phase 4 — Run a Web Test on Chrome Using Appium

Use Appium to automate a browser-based test on the Chrome app installed on the Android device.

- Configure Desired Capabilities to target Chrome on Android (browserName: chrome).
- Write a test that opens a URL in Chrome, locates a web element, and interacts with it.
- Run the test successfully and capture a screenshot showing the browser automation in action.

### Phase 5 — Appium Java Cheat Sheet

Produce a personal reference document summarizing the Appium Java API from the full course.

- Create a structured cheat sheet covering: Desired Capabilities setup, element locator strategies, common driver commands, gesture automation methods, and web testing configuration.
- Include a short code snippet for each entry showing correct Java syntax.
- Submit as a .md or .pdf file alongside the code.

## Non-Functional Requirements

- Upload all test scripts to a public GitHub repository with a username similar to your real name.
- Include a README.md documenting the environment setup steps and how to run the tests.
- Each test scenario must be in its own separate Java file or test class.
- Use clear and self-explanatory names for all test methods, variables, and classes.
- Add short comments to explain each Desired Capabilities configuration and locator strategy used.
- Avoid duplicate setup code — use a base test class for shared driver initialization.
- Remove all unused code and commented-out blocks before submission.

## Final Deliverables

- Public GitHub repository with all test scripts and a README.md.
- Screenshot of the appium-doctor output confirming a clean setup.
- Screenshot of the built-in app test running successfully on the emulator.
- Screenshot of the APK test running successfully on the emulator.
- Screenshot of a test running on a real physical device.
- Screenshot showing each of the four gesture automations (Tap, Scroll, Slide, Drag & Drop) in action.
- Screenshot of the Chrome web test executing on the Android device.
- The Appium Java Cheat Sheet as a .md or .pdf file.
