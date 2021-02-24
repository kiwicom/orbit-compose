object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.0-alpha08"

    const val kotlinPoet = "com.squareup:kotlinpoet:1.7.2"
    const val androidSdkCommon = "com.android.tools:sdk-common:27.1.2"

    object Kotlin {
        const val version = "1.4.30"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object KotlinX {
        object Coroutines {
            private const val version = "1.4.2"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }

        object Datetime {
            private const val version = "0.1.1"
            const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:$version"
        }

        object Serialization {
            private const val version = "1.0.1"
            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$version"
        }
    }

    object AndroidX {
        const val activityCompose = "androidx.activity:activity-compose:1.3.0-SNAPSHOT"
        const val appcompat = "androidx.appcompat:appcompat:1.3.0-beta01"
        const val core = "androidx.core:core-ktx:1.5.0-beta01"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:1.0.0-SNAPSHOT"

        object Compose {
            const val snapshot = "7129581"
            const val version = "1.0.0-SNAPSHOT"
            const val animation = "androidx.compose.animation:animation:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val test = "androidx.compose.ui:ui-test:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:$version"
            const val viewBinding = "androidx.compose.ui:ui-viewbinding:$version"
        }
    }
}

object Urls {
    const val composeSnapshotRepo = "https://androidx.dev/snapshots/builds/" +
            "${Libs.AndroidX.Compose.snapshot}/artifacts/repository/"
}
