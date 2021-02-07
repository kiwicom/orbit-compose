plugins {
    id("com.android.library")
    id("kotlin-android")
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
}

dependencies {
    implementation(Libs.AndroidX.Compose.runtime)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.tooling)
}
