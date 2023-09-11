@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    kotlin("android")
    id("com.android.test")
    id("androidx.baselineprofile")
    id("org.jmailen.kotlinter")
}

android {
    namespace = "kiwi.orbit.compose.baselineprofile"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = 28 // required by the BaselineProfileRule
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lint {
        abortOnError = true
        warningsAsErrors = true
    }

    targetProjectPath = ":catalog"

    testOptions {
        animationsDisabled = true

        managedDevices.devices {
            create<ManagedVirtualDevice>("pixel6Api33") {
                device = "Pixel 6"
                apiLevel = 34
                systemImageSource = "google"
            }
        }
    }
}

kotlinter {
    reporters = arrayOf("json")
}

baselineProfile {
    managedDevices += "pixel6Api33"
    useConnectedDevices = false
}

dependencies {
    implementation(projects.catalog.semantics)

    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.uiAutomator)
}
