# Contributing

## Screenshot Testing

Screenshot tests are `androidTest` and run either on emulator or an physical device. Yet we observe different results, so using **only emulator** is recommended.

Recommended device:

- Pixel 3 (440 dpi, min 275 dpi)
- API 30

To generate new screenshots, run the gradle task

```bash
./gradlew :ui:executeScreenshotTests -Precord
```

Do not forget disable all animations. Use either debug settings (Settings -> Accessibility -> Remove animations) or adb commands.

However, sometimes the differences are quite extreme, you may utilize CI build, download artifacts after failed screenshot test build, commit them and repush.
