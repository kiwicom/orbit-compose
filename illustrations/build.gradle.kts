@file:Suppress("UnstableApiUsage")

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("androidx.baselineprofile")
    id("org.jetbrains.dokka")
    id("org.jmailen.kotlinter")
    id("com.vanniktech.maven.publish.base")
}

kotlin {
    explicitApi()
    androidTarget {}
    sourceSets {
        val androidMain by getting {}
    }
}

android {
    namespace = "kiwi.orbit.compose.illustrations"

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
        abortOnError = true
        warningsAsErrors = true
    }
}

kotlinter {
    reporters = arrayOf("json")
}

baselineProfile {
    baselineProfileOutputDir = "."

    filter {
        include("kiwi.orbit.compose.illustrations.**")
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
