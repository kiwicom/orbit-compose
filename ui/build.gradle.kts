@file:Suppress("UnstableApiUsage")

import org.jmailen.gradle.kotlinter.tasks.ConfigurableKtLintTask

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("androidx.baselineprofile")
    id("org.jetbrains.dokka")
    id("org.jmailen.kotlinter")
    id("com.vanniktech.maven.publish.base")
    id("app.cash.paparazzi")
    id("io.gitlab.arturbosch.detekt")
}

kotlin {
    explicitApi()
    androidTarget {}
    sourceSets {
        val androidMain by getting {}
    }
}

android {
    namespace = "kiwi.orbit.compose.ui"

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    buildFeatures {
        compose = true
        buildConfig = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    lint {
        disable.add("ObsoleteLintCustomCheck")
        disable.add("ComposeUnstableCollections") // not suitable requirement for library, for now
        disable.add("ComposeCompositionLocalUsage") // theming uses this a lot
        abortOnError = true
        warningsAsErrors = true
    }

    packaging {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
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

    lintChecks(libs.slack.composeLintChecks)

    lintPublish(projects.lint)
}
