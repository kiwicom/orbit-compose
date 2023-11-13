@file:Suppress("UnstableApiUsage")

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("androidx.baselineprofile")
    id("org.jetbrains.dokka")
    id("org.jmailen.kotlinter")
    id("com.vanniktech.maven.publish.base")
}

kotlin {
    applyDefaultHierarchyTemplate()

    explicitApi()

    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.ui)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
    }
}

android {
    namespace = "kiwi.orbit.compose.icons"

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        disable.add("UnusedResources")
        disable.add("VectorPath")
        disable.add("UnusedAttribute")
        abortOnError = true
        warningsAsErrors = true
    }

    sourceSets {
        named("main") {
            resources.srcDirs("src/commonMain/resources")
        }
    }
}

kotlinter {
    reporters = arrayOf("json")
}

baselineProfile {
    baselineProfileOutputDir = "."

    filter {
        include("kiwi.orbit.compose.icons.**")
    }
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.core)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)

    baselineProfile(projects.baselineprofile)
}
