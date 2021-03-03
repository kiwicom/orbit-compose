plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(23)
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
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.version
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs.toMutableList().apply {
            add("-Xexplicit-api=strict")
        }.toList()
    }
}

dependencies {
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.Compose.runtime)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.tooling)
}
