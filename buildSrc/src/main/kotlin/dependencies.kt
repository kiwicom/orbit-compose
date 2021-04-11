object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.0-alpha14"
    const val ktlintGradlePlugin = "org.jmailen.kotlinter"
    const val ktlintVersion = "3.4.0"

    const val kotlinPoet = "com.squareup:kotlinpoet:1.8.0"
    const val androidSdkCommon = "com.android.tools:sdk-common:27.1.3"

    object Kotlin {
        const val version = "1.4.32"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object KotlinX {
        object Coroutines {
            private const val version = "1.4.3"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }

        object Datetime {
            private const val version = "0.1.1"
            const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:$version"
        }

        object Serialization {
            const val pluginVersion = "1.4.30"
            const val gradlePlugin = "plugin.serialization"
            private const val version = "1.1.0"
            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$version"
        }
    }

    object AndroidX {
        const val activityCompose = "androidx.activity:activity-compose:1.3.0-alpha06"
        const val appcompat = "androidx.appcompat:appcompat:1.3.0-rc01"
        const val core = "androidx.core:core-ktx:1.5.0-rc01"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha05"

        object Compose {
            const val snapshot = ""
            const val version = "1.0.0-beta04"
            const val animation = "androidx.compose.animation:animation:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val navigation = "androidx.navigation:navigation-compose:1.0.0-alpha09"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val test = "androidx.compose.ui:ui-test:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:$version"
            const val viewBinding = "androidx.compose.ui:ui-viewbinding:$version"
        }
    }

    object Accompanist {
        private const val version = "0.7.1"
        const val coil = "com.google.accompanist:accompanist-coil:$version"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val systemController = "com.google.accompanist:accompanist-systemuicontroller:$version"
    }
}

object Urls {
    const val composeSnapshotRepo = "https://androidx.dev/snapshots/builds/" +
        "${Libs.AndroidX.Compose.snapshot}/artifacts/repository/"
}
