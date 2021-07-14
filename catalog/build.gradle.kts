plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jmailen.kotlinter")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "kiwi.orbit.compose.catalog"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.main.get()
    }

    lint {
        disable.add("ObsoleteLintCustomCheck")
        isAbortOnError = true
        isWarningsAsErrors = true
    }
}

kotlinter {
    reporters = arrayOf("json")
    experimentalRules = true
    disabledRules = arrayOf("experimental:argument-list-wrapping") // https://github.com/pinterest/ktlint/issues/1112
}

dependencies {
    implementation(projects.ui)
    implementation(projects.icons)
    implementation(projects.illustrations)

    implementation(libs.androidx.core)
    implementation(libs.androidx.activityCompose)

    implementation(libs.compose.animation)
    implementation(libs.compose.foundation)
    implementation(libs.compose.layout)
    implementation(libs.compose.material)
    implementation(libs.compose.materialIconsExtended)
    implementation(libs.compose.navigation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.runtimeLivedata)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.datetime)

    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.insetsUi)
    implementation(libs.accompanist.systemController)

    debugImplementation(libs.compose.tooling)
}
