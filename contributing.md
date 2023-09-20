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
- add documentation to docs (component.md) including UI testing documentation; resolve the correct name using [the Orbit repo](https://github.com/kiwicom/orbit/tree/master/docs/src/documentation) and naming pattern using the dir name as a file name;
- add baseline profile generating function to `BaselineProfileGenerator`; make sure the test uses all the code of the new component and is **stable**;

## Screenshot Testing

Screenshot tests are simple JVM  `test`s and run directly on JVM. We
use [Paparazzi](https://github.com/cashapp/paparazzi) to validate changes to our components.

To generate new screenshots, run the **gradle task**:

`./gradlew :ui:recordPaparazziDebug`

To verify the implementation matches the recorded screenshots, run

`./gradlew :ui:verifyPaparazziDebug`

## Release management

### Next release's version

The next release version is auto-resolved by the merged pull requests. After each push to main branch, there is a workflow that considers merged PRs since the last release and bumps/changes the next version if needed.

PRs can be labeled with `semver:major`, `semver:minor` or `semver:patch` labels. The workflow considers the "biggest" needed bump. If there are no labeled PRs, it uses a minor version bump by default.

The workflow also updates the next "drafted" release with the proper version title and description (list of merged PRs). Those PRs are grouped by labels like `feature`, `bug` or `chore`.

### Releasing the next version

1. Edit the drafted release at https://github.com/kiwicom/orbit-compose/releases.
2. Make edits in the description — if needed.
3. Press "Publish release" button.

This action will:
- publish the GitHub release,
- tag the latest commit with the appropriate git tag,
- build the commit and publish the libraries to Maven's staging & auto-promote it to the public,
- build the catalog app and upload it to the GitHub release and publish it automatically on Google Play.
