import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32" apply false
    kotlin("android") version "1.4.32" apply false
    kotlin("plugin.serialization") version "1.4.30" apply false
    id("com.android.application") version "7.0.0-alpha14" apply false
    id("org.jmailen.kotlinter") version "3.4.0" apply false
}

subprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://kotlin.bintray.com/kotlinx/") }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            useIR = true
            allWarningsAsErrors = true
            freeCompilerArgs = freeCompilerArgs.toMutableList().apply {
                add("-Xopt-in=kotlin.RequiresOptIn")
                add("-Xskip-prerelease-check")
            }.toList()
        }
    }

    configurations.configureEach {
        resolutionStrategy.eachDependency {
            if (requested.group == "androidx.lifecycle" && requested.name != "lifecycle-viewmodel-compose") {
                useVersion("2.3.0-rc01")
            }
            if (requested.group == "androidx.savedstate") {
                useVersion("1.1.0-rc01")
            }
        }
    }
}
