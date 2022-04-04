plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jmailen.kotlinter")
    id("com.vanniktech.maven.publish.base")
}

android {
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
        kotlinCompilerExtensionVersion = libs.versions.compose.main.get()
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs.toMutableList().apply {
            add("-Xexplicit-api=strict")
        }.toList()
    }

    lint {
        disable.add("ObsoleteLintCustomCheck")
        disable.add("UnusedResources")
        baseline = file("lint-baseline.xml")
        abortOnError = true
        warningsAsErrors = true
    }
}

kotlinter {
    reporters = arrayOf("json")
    experimentalRules = true
    disabledRules = arrayOf("experimental:trailing-comma") // https://github.com/pinterest/ktlint/issues/1367 ?
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.core)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
}
