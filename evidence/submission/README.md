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

`emulator-test-results.txt` records the seven JUnit results. The doctor diagnostic is in `../appium-doctor.txt` and reports zero required fixes. A terminal screenshot of that output needs an interactive Windows console. A physical-device screenshot will be added after the device permits Appium's helper APK installation.
