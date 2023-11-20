import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import java.time.Year
import java.util.Properties
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.20" apply false
    kotlin("android") version "1.9.20" apply false
    kotlin("multiplatform") version "1.9.20" apply false
    kotlin("plugin.serialization") version "1.9.20" apply false
    id("com.android.library") version "8.1.3" apply false
    id("com.android.test") version "8.1.3" apply false
    id("androidx.baselineprofile") version "1.2.1" apply false
    id("app.cash.paparazzi") version "1.3.1" apply false
    id("com.google.firebase.appdistribution") version "4.0.1" apply false
    id("com.vanniktech.maven.publish.base") version "0.25.3" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.3" apply false
    id("org.jmailen.kotlinter") version "4.0.0" apply false
    id("org.jetbrains.dokka") version "1.9.10"
}

buildscript {
    dependencies {
        classpath(libs.kotlin.dokka)
    }
}

subprojects {
    configurations.all {
        resolutionStrategy {
            force(libs.compose.material3)
        }
    }
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            allWarningsAsErrors = true
            freeCompilerArgs = freeCompilerArgs.toMutableList().apply {
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

    plugins.withId("com.vanniktech.maven.publish.base") {
        @Suppress("UnstableApiUsage")
        configure<MavenPublishBaseExtension> {
            group = requireNotNull(project.findProperty("GROUP"))
            version = requireNotNull(project.findProperty("VERSION_NAME"))
            pomFromGradleProperties()
            publishToMavenCentral(SonatypeHost.S01, true)
            signAllPublications()
            configure(AndroidSingleVariantLibrary())
        }
    }

    tasks.withType<DokkaTaskPartial>().configureEach {
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            customStyleSheets = listOf(file("dokka/orbit-style.css"))
            customAssets = listOf(file("dokka/logo-icon.svg"))
            footerMessage = "© ${Year.now().value} Kiwi.com"
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        moduleName.set("Orbit Compose")
        customStyleSheets = listOf(file("dokka/orbit-style.css"))
        customAssets = listOf(file("dokka/logo-icon.svg"))
        footerMessage = "© ${Year.now().value} Kiwi.com"
    }
}
