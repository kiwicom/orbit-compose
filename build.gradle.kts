@file:Suppress("DSL_SCOPE_VIOLATION")

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
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.agp.application) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.mavenPublish) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.appDistribution) apply false
    alias(libs.plugins.dokka)
}

buildscript {
    dependencies {
        classpath(libs.kotlin.dokka)
    }
}

subprojects {
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
