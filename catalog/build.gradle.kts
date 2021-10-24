import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jmailen.kotlinter")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "kiwi.orbit.compose.catalog"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = 31
        versionName = project.findProperty("VERSION_NAME").toString()

        val bits = versionName!!.split('.').map { it.toInt() }
        check(bits.size == 3)
        versionCode = bits[0] * 1_00_00 + bits[1] * 1_00 + bits[2]

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val propertiesFile = File("$projectDir/../release/signing.properties")
            if (!propertiesFile.exists()) return@create

            val properties = loadProperties(propertiesFile.absolutePath)
            storeFile = properties.getProperty("store.path")?.let { file(it) }
            storePassword = properties.getProperty("store.password")
            keyAlias = properties.getProperty("key.alias")
            keyPassword = properties.getProperty("key.password")
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
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
    implementation(libs.compose.materialIconsExtended)
    implementation(libs.compose.navigation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.runtimeLivedata)
    implementation(libs.compose.toolingPreview)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)

    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.systemController)

    debugImplementation(libs.compose.tooling)
}
