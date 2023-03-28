# Contributing

If you want to fix a bug or implement a feature, you are welcome to do so. But read this first before implementing something.

Possible contributions:

- bugfixes (visual bugs, documentation bugs, API bugs),
- modifications to existing components,
- adding more documentation,
- implementing new components.

Please always open an issue first to be sure your work will be accepted (with the exception to documentation bugs). Especially all feature development (changes/new components/API) has to be discussed first. Thank you for understanding.

Our code review will focus on:

- Consistency with existing components.
- Visual matching with the design proposal.
- Compliance with our API requirements.

## Creating new component

**The component development has to be approved, see the previous section.**

- discuss the API of the composable first;
- add the component - composables with KDoc;
- add screenshots tests covering basic rendering of the component; Locally recorded screenshots may produce changes to all PNG files, push only the relevant ones. Don't forget to install Git LFS, if installed properly, screenshots are automatically committed as LFS files.
- add catalog demonstration;
- add entry into `component-status.yaml`;
- add documentation to docs (component.mdx) including UI testing documentation; resolve the correct name using [the Orbit repo](https://github.com/kiwicom/orbit/tree/master/docs/src/documentation) and naming pattern using the dir name as a file name;

## Screenshot Testing

Screenshot tests are simple JVM  `test`s and run directly on JVM. We
use [Paparazzi](https://github.com/cashapp/paparazzi) to validate changes to our components.

To generate new screenshots, run the **gradle task**:

`./gradlew :ui:recordPaparazziDebug`

To verify the implementation matches the recorded screenshots, run

`./gradlew :ui:verifyPaparazziDebug`
