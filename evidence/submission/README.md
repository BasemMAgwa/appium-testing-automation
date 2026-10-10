# Submission evidence

These PNG files are screenshots saved by passing Appium test runs on the Android 13 `Phone` emulator:

| Image | Verified result |
| --- | --- |
| `settings-emulator.png` | Settings search opened the Display page. |
| `connectme-emulator.png` | The built ConnectMe APK reached its login form and accepted an email address. |
| `tap-emulator.png` | The first checkbox changed state after a tap gesture. |
| `scroll-emulator.png` | Scrolling exposed the Seek Bar item. |
| `slide-emulator.png` | Sliding the SeekBar changed its reported progress. |
| `drag-drop-emulator.png` | Dragging one dot onto another displayed “Dropped!”. |
| `chrome-emulator.png` | Chrome entered “Appium” and showed “Hello, Appium”. |
| `connectme-phone.png` | A physical Xiaomi 2109119DG opened the ConnectMe login form. The test also asserted the entered email through the UI element. |
| `chrome-phone.png` | Chrome on the physical phone entered “Appium” and showed “Hello, Appium”. |

`emulator-test-results.txt` records the seven emulator JUnit results. `phone-test-results.txt` records the two physical-device results. The doctor diagnostic is in `../appium-doctor.txt` and reports zero required fixes. A terminal screenshot of that output still needs an interactive Windows console.

Appium Inspector locator verification is captured in `inspector-settings-search.png`, `inspector-settings-display.png`, `inspector-connectme-start.png`, `inspector-connectme-login.png`, `inspector-connectme-email.png`, and `inspector-connectme-email-hint.png`. The first five show the selected element and its locator panel; the last shows the email field's `hint=Email` attribute. Each locator search returned one matching element.
