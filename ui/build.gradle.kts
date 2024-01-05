import org.jmailen.gradle.kotlinter.tasks.ConfigurableKtLintTask

plugins {
    id("kiwi.orbit.compose.buildlogic.library")
    id("kiwi.orbit.compose.buildlogic.publish")
    id("androidx.baselineprofile")
    id("org.jmailen.kotlinter")
    id("app.cash.paparazzi")
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    buildUponDefaultConfig = true
    config.setFrom(file("$rootDir/detekt.yml"))
    baseline = file("$projectDir/detekt-baseline.xml")
}

kotlinter {
    reporters = arrayOf("json")
}

tasks.withType<ConfigurableKtLintTask> {
    exclude { it.file.absoluteFile.endsWith("SwipeableV2.kt") }
}

baselineProfile {
    baselineProfileOutputDir = "."

    filter {
        include("kiwi.orbit.compose.ui.**")
    }
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(projects.icons)

    implementation(libs.androidx.core)
    implementation(libs.coil)
    implementation(libs.compose.animationGraphics)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.materialRipple)
    implementation(libs.compose.material3)
    implementation(libs.compose.toolingPreview)
    implementation(libs.compose.uiUtil)
    implementation(libs.kotlin.stdlib)

    baselineProfile(projects.baselineprofile)

    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.androidx.activityCompose)
    debugImplementation(libs.androidx.customView)
    debugImplementation(libs.androidx.customViewPoolingContainer)

    testImplementation(kotlin("test"))
    testImplementation(libs.robolectric)
    testImplementation(libs.compose.uiTest)
    testImplementation(libs.compose.uiTestManifest)
    testImplementation(libs.hamcrest) // ui test fails if not added
    testImplementation(libs.mockk)

    lintPublish(projects.lint)
}
