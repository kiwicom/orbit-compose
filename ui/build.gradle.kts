@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

import org.jmailen.gradle.kotlinter.tasks.ConfigurableKtLintTask

plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.paparazzi)
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

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs.toMutableList().apply {
            add("-Xexplicit-api=strict")
        }.toList()
    }

    lint {
        disable.add("ObsoleteLintCustomCheck")
        abortOnError = true
        warningsAsErrors = true
    }

    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

kotlinter {
    reporters = arrayOf("json")
    experimentalRules = true
}

tasks.withType<ConfigurableKtLintTask> {
    exclude { it.file.absoluteFile.endsWith("SwipeableV2.kt") }
}

dependencies {
    implementation(projects.icons)

    implementation(libs.androidx.core)
    implementation(libs.coil)
    implementation(libs.compose.animationGraphics)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.materialRipple)
    implementation(libs.compose.material3)
    implementation(libs.compose.toolingPreview)
    implementation(libs.kotlin.stdlib)

    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.androidx.activityCompose)
    debugImplementation(libs.androidx.customView)
    debugImplementation(libs.androidx.customViewPoolingContainer)

    testImplementation(libs.robolectric)
    testImplementation(libs.compose.uiTest)
    testImplementation(libs.compose.uiTestManifest)
    testImplementation(libs.hamcrest) // ui test fails if not added

    lintPublish(projects.lint)
}
