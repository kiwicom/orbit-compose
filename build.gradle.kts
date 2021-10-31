import com.vanniktech.maven.publish.MavenPublishPluginExtension
import com.vanniktech.maven.publish.SonatypeHost
import java.util.Properties
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31" apply false
    kotlin("android") version "1.5.31" apply false
    kotlin("plugin.serialization") version "1.5.31" apply false
    id("com.android.application") version "7.0.3" apply false
    id("org.jmailen.kotlinter") version "3.6.0" apply false
    id("com.vanniktech.maven.publish") version "0.18.0" apply false
    id("com.google.devtools.ksp") version "1.5.31-1.0.0" apply false
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
            allWarningsAsErrors = true
            freeCompilerArgs = freeCompilerArgs.toMutableList().apply {
                add("-Xopt-in=kotlin.RequiresOptIn")
                add("-Xskip-prerelease-check")
            }.toList()
        }
    }

    val signingPropsFile = rootProject.file("release/signing.properties")
    if (signingPropsFile.exists()) {
        val localProperties = Properties()
        with(signingPropsFile.inputStream()) {
            localProperties.load(this)
        }
        localProperties.forEach { key, value ->
            if (key == "signing.secretKeyRingFile") {
                project.ext.set(key as String, rootProject.file(value).absolutePath)
            } else {
                project.ext.set(key as String, value)
            }
        }
    }

    plugins.withId("com.vanniktech.maven.publish") {
        configure<MavenPublishPluginExtension> {
            sonatypeHost = SonatypeHost.S01
        }
    }
}
