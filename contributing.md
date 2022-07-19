# Contributing

If you want to directly fix a bug or implement a feature, you are welcome to do so.

Our code review will focus on:

- Consistency with existing components
- Visual matching with the design proposal
- Compliance with our API requirements.

Don't forget to add any new components to our showcase app!

## Snapshot Testing

Snapshot tests are simple JVM  `test`s and run directly on JVM. We
use [Paparazzi](https://github.com/cashapp/paparazzi) to validate changes to our components.

To generate new screenshots, run the **gradle task**:

`./gradlew :ui:recordPaparazziDebug`
