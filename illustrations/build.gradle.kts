@file:Suppress("UnstableApiUsage")

plugins {
    kotlin("android")
    id("com.android.library")
    id("org.jetbrains.dokka")
    id("org.jmailen.kotlinter")
    id("com.vanniktech.maven.publish.base")
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
}

kotlinter {
    reporters = arrayOf("json")
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.core)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
}
