plugins {
    kotlin("android")
    id("com.android.library")
    id("org.jmailen.kotlinter")
}

android {
    namespace = "kiwi.orbit.compose.catalog.semantics"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lint {
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
