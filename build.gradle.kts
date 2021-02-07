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
                add("-Xopt-in=kotlinx.coroutines.FlowPreview")
                add("-Xskip-prerelease-check")
                add("-Xallow-jvm-ir-dependencies")
            }.toList()
        }
    }
}
