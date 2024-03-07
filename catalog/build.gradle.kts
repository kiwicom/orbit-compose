@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    kotlin("android")
    kotlin("plugin.serialization")
    id("com.android.application")
    id("androidx.baselineprofile")
    id("org.jmailen.kotlinter")
    id("com.google.firebase.appdistribution")
    kotlin("plugin.parcelize")
}

android {
    namespace = "kiwi.orbit.compose.catalog"

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "kiwi.orbit.compose.catalog"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
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
            isPseudoLocalesEnabled = true
        }
        create("ci") {
            matchingFallbacks.add("release")
            applicationIdSuffix = ".ci"
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            firebaseAppDistribution {
                appId = "1:442962334472:android:3a8ac71491745d2d37d2bd"
                artifactType = "APK"
                groups = "main"
                // A different service account as the Orbit Compose project is not the linked project
                // to Google Play.
                serviceCredentialsFile = "release/orbit-service-account.json"
            }
        }
        release {
            isPseudoLocalesEnabled = true
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    applicationVariants.configureEach {
        outputs.configureEach {
            if (buildType.name == "ci") {
                this as ApkVariantOutputImpl
                versionNameOverride = "${versionName}-${System.getenv("GITHUB_REF_NAME")}"
                versionCodeOverride = System.getenv("GITHUB_RUN_NUMBER")?.toInt() ?: 1
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
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
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    experimentalProperties["android.experimental.r8.dex-startup-optimization"] = true

    lint {
        disable.add("ObsoleteLintCustomCheck")
        disable.add("GradleDependency")
        baseline = file("lint-baseline.xml")
        abortOnError = true
        warningsAsErrors = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kotlinter {
    reporters = arrayOf("json")
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(projects.ui)
    implementation(projects.icons)
    implementation(projects.illustrations)
    implementation(projects.catalog.semantics)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.activityComposeCatalog)

    implementation(libs.compose.animation)
    implementation(libs.compose.foundation)
    implementation(libs.compose.layout)
    implementation(libs.compose.material3)
    implementation(libs.compose.materialIconsExtended)
    implementation(libs.compose.navigation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.runtimeLivedata)
    implementation(libs.compose.toolingPreview)

    implementation(libs.kotlin.datetime)
    implementation(libs.kotlin.serialization.core)
    implementation(libs.kotlin.stdlib)

    implementation(libs.coil)
    implementation(libs.kiwi.navigationComposeTyped)

    baselineProfile(projects.baselineprofile)

    coreLibraryDesugaring(libs.android.desugarJdk)

    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.androidx.customView)
    debugImplementation(libs.androidx.customViewPoolingContainer)

    lintChecks(libs.slack.composeLintChecks)
    lintChecks(projects.lint)
}
