import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.Kotlin.gradlePlugin)
    }
}

subprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://kotlin.bintray.com/kotlinx/") }
        if (Libs.AndroidX.Compose.snapshot.isNotEmpty()) {
            maven { url = uri(Urls.composeSnapshotRepo) }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            useIR = true
            allWarningsAsErrors = true
            freeCompilerArgs = freeCompilerArgs.toMutableList().apply {
                add("-Xopt-in=kotlin.RequiresOptIn")
                add("-Xopt-in=kotlinx.coroutines.FlowPreview")
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
